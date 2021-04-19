package com.classmanagement.resourceserver.services.implementations;

import com.classmanagement.resourceserver.dtos.CreateBookingDto;
import com.classmanagement.resourceserver.entities.BookingRequest;
import com.classmanagement.resourceserver.entities.Status;
import com.classmanagement.resourceserver.repositories.BookingRequestRepository;
import com.classmanagement.resourceserver.repositories.ClassroomRepository;
import com.classmanagement.resourceserver.repositories.UserRepository;
import com.classmanagement.resourceserver.services.BookingRequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Service
public class BookingRequestServiceImpl implements BookingRequestService {

    private final BookingRequestRepository bookingRequestRepository;
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;

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
}
