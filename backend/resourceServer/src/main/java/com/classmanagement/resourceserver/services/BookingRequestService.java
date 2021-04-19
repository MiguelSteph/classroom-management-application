package com.classmanagement.resourceserver.services;

import com.classmanagement.resourceserver.dtos.CreateBookingDto;
import com.classmanagement.resourceserver.entities.BookingRequest;

public interface BookingRequestService {
    BookingRequest createBookingRequest(CreateBookingDto createBookingDto);
}
