package com.classmanagement.resourceserver.services.implementations;

import com.classmanagement.resourceserver.dtos.BookingRequestSummaryDto;
import com.classmanagement.resourceserver.dtos.BookingRequestsPageDto;
import com.classmanagement.resourceserver.dtos.CreateBookingDto;
import com.classmanagement.resourceserver.entities.BookingRequest;
import com.classmanagement.resourceserver.entities.Role;
import com.classmanagement.resourceserver.entities.Status;
import com.classmanagement.resourceserver.entities.User;
import com.classmanagement.resourceserver.repositories.BookingRequestRepository;
import com.classmanagement.resourceserver.repositories.ClassroomRepository;
import com.classmanagement.resourceserver.repositories.UserRepository;
import com.classmanagement.resourceserver.services.BookingRequestService;
import com.classmanagement.resourceserver.util.mappers.DtoMapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookingRequestServiceImpl implements BookingRequestService {

    private final BookingRequestRepository bookingRequestRepository;
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final ExecutorService executorService;

    @Override
    public BookingRequest cancelBookingRequest(Long id) {
        BookingRequest bookingRequest = bookingRequestRepository.getOne(id);
        bookingRequest.setStatus(Status.Cancelled);
        bookingRequest.setRejectionReason("Cancelled by Group Leader");
        bookingRequest.setLastUpdatedDate(LocalDate.now());
        return this.updateBookingRequest(bookingRequest);
    }

    @Override
    public BookingRequest approveBookingRequest(Long id) {
        BookingRequest bookingRequest = bookingRequestRepository.getOne(id);
        bookingRequest.setStatus(Status.Approved);
        bookingRequest.setLastUpdatedDate(LocalDate.now());
        return this.updateBookingRequest(bookingRequest);
    }

    @Override
    public BookingRequest rejectBookingRequest(Long id, String reason) {
        BookingRequest bookingRequest = bookingRequestRepository.getOne(id);
        bookingRequest.setStatus(Status.Rejected);
        bookingRequest.setRejectionReason(reason);
        bookingRequest.setLastUpdatedDate(LocalDate.now());
        return this.updateBookingRequest(bookingRequest);
    }

    @Override
    public BookingRequestSummaryDto getSummary(String username) {
        User currentUser = userRepository.findByEmail(username);
        String roleName = currentUser.getRole().getName();
        if (roleName != null && roleName.contains(Role.GROUP_LEADER_ROLE)) {
            return getGroupLeaderSummary(currentUser);
        } else if (roleName != null && roleName.contains(Role.SUPERVISOR_ROLE)) {
            return getSupervisorSummary(currentUser);
        }
        return null;
    }

    private BookingRequestSummaryDto getSupervisorSummary(User currentUser) {
        int pendingCount = bookingRequestRepository.countByStatusAndAssignedTo(Status.Pending, currentUser);
        int cancelledCount = bookingRequestRepository.countByStatusAndAssignedTo(Status.Cancelled, currentUser);
        int approvedCount = bookingRequestRepository.countByStatusAndAssignedTo(Status.Approved, currentUser);
        int rejectedCount = bookingRequestRepository.countByStatusAndAssignedTo(Status.Rejected, currentUser);

        return BookingRequestSummaryDto.builder()
                .approvedCount(approvedCount)
                .cancelledCount(cancelledCount)
                .pendingCount(pendingCount)
                .rejectedCount(rejectedCount)
                .totalCount(pendingCount + cancelledCount + approvedCount + rejectedCount).build();
    }

    private BookingRequestSummaryDto getGroupLeaderSummary(User currentUser) {
        int pendingCount = bookingRequestRepository.countByStatusAndCreatedBy(Status.Pending, currentUser);
        int cancelledCount = bookingRequestRepository.countByStatusAndCreatedBy(Status.Cancelled, currentUser);
        int approvedCount = bookingRequestRepository.countByStatusAndCreatedBy(Status.Approved, currentUser);
        int rejectedCount = bookingRequestRepository.countByStatusAndCreatedBy(Status.Rejected, currentUser);

        return BookingRequestSummaryDto.builder()
                .approvedCount(approvedCount)
                .cancelledCount(cancelledCount)
                .pendingCount(pendingCount)
                .rejectedCount(rejectedCount)
                .totalCount(pendingCount + cancelledCount + approvedCount + rejectedCount).build();
    }

    @Override
    public BookingRequest createBookingRequest(CreateBookingDto createBookingDto) {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setStatus(Status.Pending);
        bookingRequest.setCreatedDate(LocalDate.now());
        bookingRequest.setLastUpdatedDate(LocalDate.now());

        bookingRequest.setPurpose(createBookingDto.getPurpose());
        bookingRequest.setBookingDate(createBookingDto.getDate());
        bookingRequest.setFromTime(LocalTime.of(createBookingDto.getStartHour(), 0));
        bookingRequest.setToTime(LocalTime.of(createBookingDto.getEndHour(), 0));
        bookingRequest.setClassroom(classroomRepository.getOne(createBookingDto.getClassroomId()));
        bookingRequest.setAssignedTo(userRepository.getOne(createBookingDto.getSupervisorId()));
        bookingRequest.setCreatedBy(userRepository.findByEmail(createBookingDto.getUsername()));

        return bookingRequestRepository.save(bookingRequest);
    }

    @Override
    public BookingRequest updateBookingRequest(BookingRequest bookingRequest) {
        return bookingRequestRepository.save(bookingRequest);
    }

    @Override
    public void updateBookingRequest(List<BookingRequest> bookingRequest) {
        bookingRequestRepository.saveAll(bookingRequest);
    }

    @Override
    public BookingRequestsPageDto getRequestByStatus(String username, Status status, int page, int pageSize) {
        User currentUser = userRepository.findByEmail(username);
        Page<BookingRequest> pageResult;
        String roleName = currentUser.getRole().getName();
        if (roleName != null && roleName.contains(Role.GROUP_LEADER_ROLE)) {
            pageResult = bookingRequestRepository.findByStatusAndCreatedBy(status,
                    currentUser , PageRequest.of(page, pageSize));
        } else {
            pageResult = bookingRequestRepository.findByStatusAndAssignedTo(status,
                    currentUser , PageRequest.of(page, pageSize));
        }

        BookingRequestsPageDto bookingRequestsPageDto = new BookingRequestsPageDto();
        bookingRequestsPageDto.setCurrentPage(pageResult.getNumber());
        bookingRequestsPageDto.setHasNextPage(pageResult.hasNext());
        bookingRequestsPageDto.setHasPreviousPage(pageResult.hasPrevious());
        bookingRequestsPageDto.setPageSize(pageResult.getSize());
        bookingRequestsPageDto.setTotalPages(pageResult.getTotalPages());

        List<BookingRequest> bookingRequestList = new ArrayList<>();
        List<BookingRequest> toCancelList = new ArrayList<>();
        pageResult.forEach(bookingRequest -> {
            if (bookingRequest.getStatus().equals(Status.Pending) &&
                    bookingRequest.getBookingDate().compareTo(LocalDate.now()) < 0) {
                bookingRequest.setStatus(Status.Cancelled);
                bookingRequest.setRejectionReason("Outdated request");
                toCancelList.add(bookingRequest);
            } else {
                bookingRequestList.add(bookingRequest);
            }
        });

        bookingRequestsPageDto.setData(bookingRequestList
                .stream().map(DtoMapperUtil::convertToBookingRequestDto)
                .collect(Collectors.toList()));

        executorService.submit(() -> this.updateBookingRequest(toCancelList));

        return bookingRequestsPageDto;
    }
}
