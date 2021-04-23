package com.classmanagement.resourceserver.services;

import com.classmanagement.resourceserver.dtos.BookingRequestsPageDto;
import com.classmanagement.resourceserver.dtos.CreateBookingDto;
import com.classmanagement.resourceserver.entities.BookingRequest;
import com.classmanagement.resourceserver.entities.Status;

import java.util.List;

public interface BookingRequestService {
    BookingRequest createBookingRequest(CreateBookingDto createBookingDto);
    BookingRequestsPageDto getRequestByStatus(String username, Status status, int page, int pageSize);
    void updateBookingRequest(BookingRequest bookingRequest);
    void updateBookingRequest(List<BookingRequest> bookingRequest);
}
