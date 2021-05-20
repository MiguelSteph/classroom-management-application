package com.classmanagement.resourceserver.services.implementations;

import com.classmanagement.resourceserver.dtos.ClassroomAvailabilityDto;
import com.classmanagement.resourceserver.dtos.TimeRangeDto;
import com.classmanagement.resourceserver.dtos.UserDto;
import com.classmanagement.resourceserver.entities.*;
import com.classmanagement.resourceserver.repositories.AvailableTimeIntervalRepository;
import com.classmanagement.resourceserver.repositories.BookingRequestRepository;
import com.classmanagement.resourceserver.repositories.ClassroomRepository;
import com.classmanagement.resourceserver.services.ClassroomService;
import com.classmanagement.resourceserver.util.mappers.DtoMapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final AvailableTimeIntervalRepository availableTimeIntervalRepository;
    private final BookingRequestRepository bookingRequestRepository;

    @Override
    public void shrinkClassroomAvailability(int classroomId, LocalDate fromDate, LocalDate toDate) {
        List<AvailableTimeInterval> classroomAvailabilities = availableTimeIntervalRepository
                .getClassroomAvailabilities(classroomId, fromDate, toDate);
        List<BookingRequest> validBookingRequestList = bookingRequestRepository
                .getClassroomBookingRequests(classroomId, fromDate, toDate);

        if (validBookingRequestList == null || validBookingRequestList.isEmpty()) {
            removeClassroomAvailabilities(classroomAvailabilities);
        } else {
            validBookingRequestList.sort(Comparator.comparing(BookingRequest::getBookingDate).reversed());
            BookingRequest latestBookingRequest = validBookingRequestList.get(0);
            if (!latestBookingRequest.getBookingDate().isEqual(toDate)) {
                classroomAvailabilities.forEach(item -> item.setToDate(latestBookingRequest.getBookingDate()));
                availableTimeIntervalRepository.saveAll(classroomAvailabilities);
            }
        }
    }

    private void removeClassroomAvailabilities(List<AvailableTimeInterval> availableTimeIntervalList) {
        availableTimeIntervalRepository.deleteAll(availableTimeIntervalList);
    }

    @Override
    public List<ClassroomAvailabilityDto> getClassroomAllCurrentAvailability(int id) {
        List<AvailableTimeInterval> currentAvailabilities = availableTimeIntervalRepository
                .getClassroomAllCurrentAvailability(id, LocalDate.now());

        Map<LocalDate, ClassroomAvailabilityDto> map = new HashMap<>();

        for (AvailableTimeInterval interval : currentAvailabilities) {
            TimeRangeDto timeRangeDto = new TimeRangeDto();
            timeRangeDto.setFromTime(interval.getFromTime());
            timeRangeDto.setToTime(interval.getToTime());

            if (!map.containsKey(interval.getFromDate())) {
                ClassroomAvailabilityDto classroomAvailabilityDto = new ClassroomAvailabilityDto();
                classroomAvailabilityDto.setStartDate(interval.getFromDate());
                classroomAvailabilityDto.setEndDate(interval.getToDate());
                classroomAvailabilityDto.setTimeRangeByDayOfWeek(new HashMap<>());
                map.put(interval.getFromDate(), classroomAvailabilityDto);
            }

            if (!map.get(interval.getFromDate()).getTimeRangeByDayOfWeek().containsKey(interval.getWeekDay())) {
                map.get(interval.getFromDate()).getTimeRangeByDayOfWeek().put(interval.getWeekDay(), new ArrayList<>());
            }
            map.get(interval.getFromDate()).getTimeRangeByDayOfWeek().get(interval.getWeekDay()).add(timeRangeDto);
        }

        List<ClassroomAvailabilityDto> availabilityDtoList = new ArrayList<>(map.values());
        availabilityDtoList.forEach(item -> item.getTimeRangeByDayOfWeek().forEach((key, value) -> Collections.sort(value)));

        return availabilityDtoList;
    }

    @Override
    public List<TimeRangeDto> getClassroomAvailability(int id, LocalDate date) {
        String weekDay = date.getDayOfWeek().toString();
        List<AvailableTimeInterval> availableTimeIntervals =
                availableTimeIntervalRepository.getClassroomAvailabilityTimeInterval(id, date, weekDay);

        List<TimeRangeDto> initialTimeRange = availableTimeIntervals.stream()
                .map(DtoMapperUtil::convertToTimeRangeDto)
                .collect(Collectors.toList());

        List<BookingRequest> bookingRequestList = bookingRequestRepository.findByBookingDate(date);
        bookingRequestList = bookingRequestList.stream()
                .filter(r -> r.getStatus() != Status.Cancelled && r.getStatus() != Status.Rejected)
                .collect(Collectors.toList());
        List<TimeRangeDto> takenTimes = new ArrayList<>();
        bookingRequestList.forEach(bookingRequest -> {
            TimeRangeDto timeRangeDto = new TimeRangeDto();
            timeRangeDto.setFromTime(bookingRequest.getFromTime());
            timeRangeDto.setToTime(bookingRequest.getToTime());
            takenTimes.add(timeRangeDto);
        });

        if (takenTimes.isEmpty()) {
            return initialTimeRange;
        }

        TreeSet<TimeRangeDto> treeSet = new TreeSet<>(initialTimeRange);

        for (TimeRangeDto takenTimeRange : takenTimes) {
            List<TimeRangeDto> toDeleteList = new ArrayList<>();
            List<TimeRangeDto> toAddList = new ArrayList<>();
            for (TimeRangeDto timeRange : treeSet.tailSet(new TimeRangeDto(LocalTime.MIN, takenTimeRange.getFromTime()))) {
                if (containTime(takenTimeRange, timeRange)) {
                    toDeleteList.add(timeRange);
                } else if (timeOverLap(takenTimeRange, timeRange)) {
                    toDeleteList.add(timeRange);
                    if (takenTimeRange.getFromTime().compareTo(timeRange.getFromTime()) > 0) {
                        toAddList.add(new TimeRangeDto(timeRange.getFromTime(), takenTimeRange.getFromTime()));
                    }
                    if (takenTimeRange.getToTime().compareTo(timeRange.getToTime()) < 0) {
                        toAddList.add(new TimeRangeDto(takenTimeRange.getToTime(), timeRange.getToTime()));
                    }
                } else {
                    break;
                }
            }
            toDeleteList.forEach(treeSet::remove);
            treeSet.addAll(toAddList);
        }

        return new ArrayList<>(treeSet);
    }

    /*
        Check if the time range is contains in the taken time
     */
    private boolean containTime(TimeRangeDto taken, TimeRangeDto timeRange) {
        return timeRange.getFromTime().compareTo(taken.getFromTime()) >= 0
                && timeRange.getToTime().compareTo(taken.getToTime()) <=0;
    }

    /*
        Check if the taken time overlap with the actual time range
     */
    private boolean timeOverLap(TimeRangeDto taken, TimeRangeDto timeRange) {
        return (taken.getFromTime().compareTo(timeRange.getFromTime()) >= 0
                && taken.getFromTime().compareTo(timeRange.getToTime()) < 0) ||
                (taken.getToTime().compareTo(timeRange.getFromTime()) > 0
                        && taken.getToTime().compareTo(timeRange.getToTime()) <= 0);
    }

    @Override
    public List<UserDto> classroomSupervisors(int id) {
        Optional<Classroom> classroomOptional = classroomRepository.findById(id);
        if (classroomOptional.isEmpty()) return Collections.emptyList();
        Classroom classroom = classroomOptional.get();
        return classroom.getClassroomSupervisors()
                .stream()
                .map(ClassroomSupervisor::getSupervisor)
                .map(DtoMapperUtil::convertToUserDto)
                .collect(Collectors.toList());
    }
}
