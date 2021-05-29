package com.classmanagement.resourceserver.rest;

import com.classmanagement.resourceserver.dtos.BookingRequestSummaryDto;
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
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class BookingRequestResource {

    private final BookingRequestService bookingRequestService;

    @GetMapping("/bookingRequests/{status}")
    public BookingRequestsPageDto getRequestByStatus(@PathVariable("status") Status status,
                                                    @RequestParam String username,
                                                    @RequestParam int page,
                                                    @RequestParam int size) {
        return bookingRequestService.getRequestByStatus(username, status, page, size);
    }

    @GetMapping("/bookingRequests/summary")
    public BookingRequestSummaryDto getGroupLeaderSummary(@RequestParam String username) {
        return bookingRequestService.getSummary(username);
    }

    @PutMapping("/bookingRequests/{id}/approve")
    public ResponseEntity<Object> approveBookingRequest(@PathVariable long id, @RequestBody String username) {
        /*
        todo: Add extra security by making sure that the user has the right to approve that booking request
         */
        bookingRequestService.approveBookingRequest(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/bookingRequests/{id}/reject")
    public ResponseEntity<Object> rejectBookingRequest(@PathVariable long id, @RequestBody Map<String, Object> requestBody) {
        /*
        todo: Add extra security by making sure that the user has the right to reject that booking request
         */
        bookingRequestService.rejectBookingRequest(id, (String) requestBody.get("reason"));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/bookingRequests/{id}/cancel")
    public ResponseEntity<Object> cancelBookingRequest(@PathVariable long id, @RequestBody String username) {
        /*
        todo: Add extra security by making sure that the user has the right to cancel that booking request
         */
        bookingRequestService.cancelBookingRequest(id);
        return ResponseEntity.noContent().build();
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
