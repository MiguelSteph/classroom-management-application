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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String code;

    private String name;

    @Column(name = "siteId")
    @ManyToOne
    private Site site;

    @OneToMany(mappedBy = "building")
    private List<Classroom> classrooms = new ArrayList<>();
}

