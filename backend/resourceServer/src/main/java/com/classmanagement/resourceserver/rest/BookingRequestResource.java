package com.classmanagement.resourceserver.rest;

import com.classmanagement.resourceserver.dtos.CreateBookingDto;
import com.classmanagement.resourceserver.entities.BookingRequest;
import com.classmanagement.resourceserver.services.BookingRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
public class BookingRequestResource {

    private final BookingRequestService bookingRequestService;

    @PostMapping("/bookingRequests")
    public ResponseEntity<Object> createBookingRequest(@RequestBody CreateBookingDto createBookingDto) {
        BookingRequest createdBookingRequest = bookingRequestService.createBookingRequest(createBookingDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBookingRequest.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

}
