package com.classmanagement.resourceserver.repositories;

import com.classmanagement.resourceserver.entities.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRequestRepository extends JpaRepository<BookingRequest, Long> {
}
