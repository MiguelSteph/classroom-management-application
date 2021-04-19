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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User supervisor;

    @ManyToOne
    private Classroom classroom;

    private LocalDate assignedDate;

    private boolean isValid;

    @ManyToOne
    private User whoAssigned;

    @ManyToOne
    private User whoRevoke;
}

