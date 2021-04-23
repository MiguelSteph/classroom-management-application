package com.classmanagement.resourceserver.services.implementations;

import com.classmanagement.resourceserver.dtos.BookingRequestsPageDto;
import com.classmanagement.resourceserver.dtos.CreateBookingDto;
import com.classmanagement.resourceserver.entities.BookingRequest;
import com.classmanagement.resourceserver.entities.Status;
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
    public void updateBookingRequest(BookingRequest bookingRequest) {
        bookingRequestRepository.save(bookingRequest);
    }

    @Override
    public void updateBookingRequest(List<BookingRequest> bookingRequest) {
        bookingRequestRepository.saveAll(bookingRequest);
    }

    @Override
    public BookingRequestsPageDto getRequestByStatus(String username, Status status, int page, int pageSize) {
        Page<BookingRequest> pageResult = bookingRequestRepository.findByStatusAndCreatedBy(status,
                userRepository.findByEmail(username), PageRequest.of(page, pageSize));

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
