package com.classmanagement.resourceserver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClassroomSupervisor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @Column(name = "supervisorId")
    private User supervisor;

    @ManyToOne
    @Column(name = "classroomId")
    private Classroom classroom;

    private LocalDate assignedDate;

    private boolean isValid;

    @ManyToOne
    @Column(name = "whoAssigned")
    private User whoAssigned;

    @ManyToOne
    @Column(name = "whoRevoke")
    private User whoRevoke;
}

