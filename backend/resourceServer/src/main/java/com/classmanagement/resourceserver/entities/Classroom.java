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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String code;

    private String name;

    @OneToOne
    @Column(name = "availabilityId")
    private Availability availability;

    @ManyToOne
    @Column(name = "buildingId")
    private Building building;

    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
    private List<ClassroomSupervisor> classroomSupervisors = new ArrayList<>();

    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
    private List<BookingRequest> bookingRequests = new ArrayList<>();
}

