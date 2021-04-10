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
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(mappedBy = "availability")
    @Column(name = "classroomId")
    private Classroom classroom;

    @ManyToOne
    @Column(name = "supervisorId")
    private User supervisor;

    @OneToMany(mappedBy = "availability")
    private List<TimeInterval> intervals = new ArrayList<>();
}

