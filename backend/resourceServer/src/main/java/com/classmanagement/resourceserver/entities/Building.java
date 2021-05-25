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
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    private String name;

    private boolean isEnabled;

    @ManyToOne
    private Site site;

    @OneToMany(mappedBy = "building", fetch = FetchType.EAGER)
    private List<Classroom> classrooms = new ArrayList<>();
}

