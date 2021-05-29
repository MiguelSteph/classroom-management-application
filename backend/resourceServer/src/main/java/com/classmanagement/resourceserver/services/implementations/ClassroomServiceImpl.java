package com.classmanagement.resourceserver.services.implementations;

import com.classmanagement.resourceserver.dtos.*;
import com.classmanagement.resourceserver.entities.*;
import com.classmanagement.resourceserver.exceptions.UserCausedBackendException;
import com.classmanagement.resourceserver.repositories.*;
import com.classmanagement.resourceserver.services.ClassroomService;
import com.classmanagement.resourceserver.util.mappers.DtoMapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final static int CLASSROOM_CODE_LIMIT = 20;

    private final ClassroomRepository classroomRepository;
    private final AvailableTimeIntervalRepository availableTimeIntervalRepository;
    private final BookingRequestRepository bookingRequestRepository;
    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;

    @Override
    public List<ClassroomDto> getClassroomsInBuilding(int buildingId) {
        List<Classroom> classroomList = buildingRepository.getOne(buildingId).getClassrooms();
        return classroomList.stream()
                .map(DtoMapperUtil::convertToClassroomDto)
                .collect(Collectors.toList());
    }

    private Building getClassroomBuilding(ClassroomDto classroomDto) {
        if (classroomDto.getBuildingId() == null) {
            throw new UserCausedBackendException("Unexpected error. Please try again.");
        }
        return buildingRepository.getOne(classroomDto.getBuildingId());
    }

    @Override
    public Classroom addNewClassroom(ClassroomDto classroomDto) {
        Building building = getClassroomBuilding(classroomDto);
        checkForAddNewClassroom(classroomDto, building);

        Classroom classroom = new Classroom();
        classroom.setCode(classroomDto.getCode());
        classroom.setName(classroomDto.getName());
        classroom.setBuilding(building);
        classroom.setEnabled(true);

        return classroomRepository.save(classroom);
    }

    private void checkForAddNewClassroom(ClassroomDto classroomDto, Building building) {
        if (classroomDto.getCode() == null || classroomDto.getCode().isEmpty()) {
            throw new UserCausedBackendException("Classroom Code is not provided");
        }

        if (classroomDto.getCode().length() > CLASSROOM_CODE_LIMIT) {
            throw new UserCausedBackendException("Classroom Code is too long. Classroom Code should not be more than 20 characters.");
        }

        if (classroomDto.getName() == null || classroomDto.getName().isEmpty()) {
            throw new UserCausedBackendException("Classroom Name is not provided");
        }

        Classroom classroomByName = classroomRepository.findByNameAndBuilding(classroomDto.getName(), building);
        Classroom classroomByCode = classroomRepository.findByCodeAndBuilding(classroomDto.getCode(), building);

        if (classroomByName != null) {
            throw new UserCausedBackendException("Classroom Name already exists.");
        }

        if (classroomByCode != null) {
            throw new UserCausedBackendException("Classroom Code already exists.");
        }
    }

    @Override
    public Classroom updateClassroom(ClassroomDto classroomDto) {
        Building building = getClassroomBuilding(classroomDto);
        checkForUpdateClassroom(classroomDto, building);

        Classroom classroom = classroomRepository.getOne(classroomDto.getId());
        classroom.setCode(classroomDto.getCode());
        classroom.setName(classroomDto.getName());
        classroom.setBuilding(building);
        classroom.setEnabled(classroomDto.isEnabled());

        return classroomRepository.save(classroom);
    }

    private void checkForUpdateClassroom(ClassroomDto classroomDto, Building building) {
        if (classroomDto.getId() == null) {
            throw new UserCausedBackendException("Unexpected error. Please try again.");
        }

        if (classroomDto.getCode() == null || classroomDto.getCode().isEmpty()) {
            throw new UserCausedBackendException("Classroom Code is not provided");
        }

        if (classroomDto.getCode().length() > CLASSROOM_CODE_LIMIT) {
            throw new UserCausedBackendException("Classroom Code is too long. Classroom Code should not be more than 20 characters.");
        }

        if (classroomDto.getName() == null || classroomDto.getName().isEmpty()) {
            throw new UserCausedBackendException("Classroom Name is not provided");
        }

        Classroom classroomByName = classroomRepository.findByNameAndBuilding(classroomDto.getName(), building);
        Classroom classroomByCode = classroomRepository.findByCodeAndBuilding(classroomDto.getCode(), building);

        if (classroomByName != null && !classroomByName.getId().equals(classroomDto.getId())) {
            throw new UserCausedBackendException("Classroom Name already exists.");
        }

        if (classroomByCode != null && !classroomByCode.getId().equals(classroomDto.getId())) {
            throw new UserCausedBackendException("Classroom Code already exists.");
        }
    }

    @Override
    public void createClassroomAvailabilities(NewClassroomAvailabilityDto availabilityDto) {
        if (this.isDateRangeValid(availabilityDto.getClassroomId(),
                availabilityDto.getFromDate(), availabilityDto.getToDate())) {
            List<AvailableTimeInterval> availableTimeIntervalList = new ArrayList<>();
            Classroom classroom = classroomRepository.getOne(availabilityDto.getClassroomId());
            User supervisor = userRepository.findByEmail(availabilityDto.getSupervisorUsername());
            for (Map.Entry<String, List<RangeDto>> timeRangesEntry : availabilityDto.getTimeRanges().entrySet()) {
                for (RangeDto rangeDto : timeRangesEntry.getValue()) {
                    AvailableTimeInterval availableTimeInterval = new AvailableTimeInterval();
                    availableTimeInterval.setClassroom(classroom);
                    availableTimeInterval.setSupervisor(supervisor);
                    availableTimeInterval.setCreatedDate(LocalDate.now());
                    availableTimeInterval.setFromDate(availabilityDto.getFromDate());
                    availableTimeInterval.setToDate(availabilityDto.getToDate());
                    availableTimeInterval.setFromTime(LocalTime.of(rangeDto.getStart(), 0));
                    availableTimeInterval.setToTime(LocalTime.of(rangeDto.getEnd(), 0));
                    availableTimeInterval.setWeekDay(DayOfWeek.valueOf(timeRangesEntry.getKey().toUpperCase()));
                    availableTimeIntervalList.add(availableTimeInterval);
                }
            }
            availableTimeIntervalRepository.saveAll(availableTimeIntervalList);
        } else {
            throw new RuntimeException("Invalid Date Range");
        }
    }

    @Override
    public boolean isDateRangeValid(int classroomId, LocalDate fromDate, LocalDate toDate) {
        List<AvailableTimeInterval> availableTimeIntervals = availableTimeIntervalRepository
                .getClassroomAllCurrentAvailability(classroomId, LocalDate.now());

        for (AvailableTimeInterval item : availableTimeIntervals) {
            if (isOverlapInterval(item, fromDate, toDate)) {
                return false;
            }
        }

        return true;
    }

    private boolean isOverlapInterval(AvailableTimeInterval interval, LocalDate fromDate, LocalDate toDate) {
        final boolean fromDateIsInInterval = interval.getFromDate().compareTo(fromDate) <= 0
                && interval.getToDate().compareTo(fromDate) >= 0;
        final boolean toDateIsInInterval = interval.getFromDate().compareTo(toDate) <= 0
                && interval.getToDate().compareTo(toDate) >= 0;
        final boolean intervalIsIn = interval.getFromDate().compareTo(fromDate) >=0
                && interval.getToDate().compareTo(toDate) <= 0;
        return fromDateIsInInterval || toDateIsInInterval || intervalIsIn;
    }

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
        availabilityDtoList.forEach(item ->
                item.getTimeRangeByDayOfWeek().forEach((key, value) -> Collections.sort(value)));

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
