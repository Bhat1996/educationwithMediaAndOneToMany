package com.example.education.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "courses")
//@DynamicInsert
//@DynamicUpdate
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courses_gen")
    @SequenceGenerator(
            name = "courses_gen",
            sequenceName = "courses_seq",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

}
