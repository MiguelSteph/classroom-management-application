package com.classmanagement.resourceserver.repositories;

import com.classmanagement.resourceserver.entities.AvailableTimeInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AvailableTimeIntervalRepository extends JpaRepository<AvailableTimeInterval, Long> {

    @Query(value = "SELECT * " +
            "FROM available_time_interval " +
            "WHERE classroom_id=?1 " +
            "AND from_date <= ?2 " +
            "AND to_date >= ?2 " +
            "AND week_day=?3",
            nativeQuery = true)
    List<AvailableTimeInterval> getClassroomAvailabilityTimeInterval(int classroomId, LocalDate date, String weekDay);

    @Query(value = "SELECT * " +
            "FROM available_time_interval " +
            "WHERE classroom_id=?1 " +
            "AND to_date >= ?2 ",
            nativeQuery = true)
    List<AvailableTimeInterval> getClassroomAllCurrentAvailability(int classroomId, LocalDate date);

    @Query(value = "SELECT * " +
            "FROM available_time_interval " +
            "WHERE classroom_id=?1 " +
            "AND from_date = ?2 " +
            "AND to_date = ?3 ",
            nativeQuery = true)
    List<AvailableTimeInterval> getClassroomAvailabilities(int classroomId, LocalDate fromDate, LocalDate toDate);
}
