package com.classmanagement.resourceserver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    private String name;

    private boolean isEnabled;

    @ManyToOne
    private Building building;

    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
    private List<AvailableTimeInterval> availableTimeIntervals = new ArrayList<>();

    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
    private List<ClassroomSupervisor> classroomSupervisors = new ArrayList<>();

    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
    private List<BookingRequest> bookingRequests = new ArrayList<>();
}

