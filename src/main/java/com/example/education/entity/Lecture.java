package com.example.education.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lectures")
@Getter
@Setter
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lectures_gen")
    @SequenceGenerator(
            name = "lectures_gen",
            sequenceName = "lectures_seq",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "notes" ,columnDefinition = "TEXT")
    private String notes;

    @Column(name = "video_url")
    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
