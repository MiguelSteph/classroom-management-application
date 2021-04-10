package com.classmanagement.resourceserver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String password;

    private boolean isDefaultPwd;

    private boolean isEnabled;

    private boolean isEmailVerified;

    @ManyToOne
    @Column(name = "createdBy")
    private User createdBy;

    @Column(name = "roleId")
    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "supervisor", fetch = FetchType.LAZY)
    private List<ClassroomSupervisor> classroomSupervisors;

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY)
    private List<BookingRequest> bookingRequestsAssignedTo = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<BookingRequest> bookingRequestsCreatedBy = new ArrayList<>();
}

