package com.example.education.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Table(name = "media_files")
@DynamicUpdate
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaFile  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "media_files_gen")
    @SequenceGenerator(
        name = "application_media_files_gen",
        sequenceName = "media_files_seq",
        allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "unique_file_name")
    private String uniqueFileName;

    @Column(name = "media_type")
    private String mediaType;

    @Column(name = "file_path", columnDefinition = "TEXT")
    private String filePath;

    @Column(name = "deleted")
    private boolean deleted;

}