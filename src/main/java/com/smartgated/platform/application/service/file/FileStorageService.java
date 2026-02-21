package com.smartgated.platform.application.service.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    public String saveFacilityImage(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        try {
            Path root = Paths.get("uploads/facilities");
            Files.createDirectories(root);

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path dest = root.resolve(filename);
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/facilities/" + filename;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save facility image", e);
        }
    }

    public String saveGroupImage(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        try {
            Path root = Paths.get("uploads/groupImage");
            Files.createDirectories(root);

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path dest = root.resolve(filename);
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/groupImage/" + filename;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save groupImage image", e);
        }
    }

    public String savePostImage(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        try {
            Path root = Paths.get("uploads/posts");
            Files.createDirectories(root);

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path dest = root.resolve(filename);
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/posts/" + filename;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save post image", e);
        }
    }

    public String saveReportImage(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        try {
            Path root = Paths.get("uploads/reports");
            Files.createDirectories(root);

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path dest = root.resolve(filename);
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/reports/" + filename;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save report image", e);
        }
    }

    public String saveUserImage(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        try {
            Path root = Paths.get("uploads/users");
            Files.createDirectories(root);

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path dest = root.resolve(filename);
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/users/" + filename;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user image", e);
        }
    }
}
