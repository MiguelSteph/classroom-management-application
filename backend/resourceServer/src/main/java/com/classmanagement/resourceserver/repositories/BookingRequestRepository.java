package com.classmanagement.resourceserver.repositories;

import com.classmanagement.resourceserver.entities.BookingRequest;
import com.classmanagement.resourceserver.entities.Status;
import com.classmanagement.resourceserver.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRequestRepository extends JpaRepository<BookingRequest, Long> {
    Page<BookingRequest> findByStatusAndCreatedBy(Status status, User grpLeader, Pageable pageRequest);
    Page<BookingRequest> findByStatusAndAssignedTo(Status status, User grpLeader, Pageable pageRequest);
    List<BookingRequest> findByBookingDate(LocalDate bookingDate);
    int countByStatusAndCreatedBy(Status status, User user);
    int countByStatusAndAssignedTo(Status status, User user);

    @Query(value = "SELECT * " +
            "FROM booking_request " +
            "WHERE classroom_id=?1 " +
            "AND booking_date >= ?2 " +
            "AND booking_date <= ?3 " +
            "AND status in ('Pending', 'Approved')",
            nativeQuery = true)
    List<BookingRequest> getClassroomBookingRequests(int classroomId, LocalDate fromDate, LocalDate toDate);
}
