package com.example.education.repository;


import com.example.education.entity.MediaFile;
import com.example.education.utils.TikaFileUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MediaFileSystemPersistence {
    public static final String DIRECTORY = "Documents/ManagementFiles/";

    private final MediaRepository mediaRepository;

    private String homeDirectory;

    @PostConstruct
    void init() {
        homeDirectory = System.getProperty("user.home");
//        log.info("Home Directory: {}", homeDirectory);
        Path path = Paths.get(homeDirectory, DIRECTORY);
        try {
            boolean folderPathExists = Files.exists(path);
            if (!folderPathExists) {
//                log.info("Creating directory on Path: {}", path.toAbsolutePath());
                Files.createDirectories(path);
            }
        } catch (IOException e) {
//            log.error("Error while creating directory", e);
            throw new RuntimeException( "Could not create directory", e);
        }
    }

    public Long persist(MultipartFile file) {
        String realMimeType = TikaFileUtils.getRealMimeType(file);
        String originalFilename = StringUtils.defaultIfBlank(file.getOriginalFilename(), "no_name");
        String uniqueFileName = UUID.randomUUID() + "__" + System.currentTimeMillis();

        Path filePath = Paths.get(homeDirectory, DIRECTORY + uniqueFileName);
        try {
            Files.createFile(filePath);
            Files.write(filePath, file.getBytes());

            MediaFile entity = MediaFile.builder()
                    .originalFileName(originalFilename)
                    .uniqueFileName(uniqueFileName)
                    .mediaType(realMimeType)
                    .filePath(filePath.toString())
                    .build();

            MediaFile savedMedia = mediaRepository.save(entity);

            return savedMedia.getId();
        } catch (IOException e) {
//            log.error("Error while writing file to file system", e);
            throw new RuntimeException( "Could not write file to file system", e);
        }
    }

    public byte[] getFileData(String filePath) {
        try {
            Path path = Paths.get(filePath);
            boolean filePathExists = Files.exists(path);
            if (!filePathExists) {
//                log.info("Creating directory on Path: {}", path.toAbsolutePath());
                System.out.println(path.toAbsolutePath());
            }
            return Files.readAllBytes(path);
        } catch (IOException e) {
//            log.error("Error while writing file to file system", e);
            throw new RuntimeException( "Could not write file to file system", e);
        }
    }

    public void softDelete(Long id) {
        MediaFile mediaFileEntity =
                mediaRepository.findById(id).orElseThrow(() -> new RuntimeException( "File not found"));
        mediaFileEntity.setDeleted(true);
        mediaRepository.save(mediaFileEntity);
    }

    public void hardDelete(Long id) {
        mediaRepository.deleteById(id);
    }

}
