package com.classmanagement.resourceserver.rest;

import com.classmanagement.resourceserver.dtos.BookingRequestsPageDto;
import com.classmanagement.resourceserver.dtos.CreateBookingDto;
import com.classmanagement.resourceserver.entities.BookingRequest;
import com.classmanagement.resourceserver.entities.Status;
import com.classmanagement.resourceserver.services.BookingRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
public class BookingRequestResource {

    private final BookingRequestService bookingRequestService;

    @GetMapping("/bookingRequests/{status}")
    public BookingRequestsPageDto getPendingRequest(@PathVariable("status") Status status,
                                                    @RequestParam String username,
                                                    @RequestParam int page,
                                                    @RequestParam int size) {
        return bookingRequestService.getRequestByStatus(username, status, page, size);
    }

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
