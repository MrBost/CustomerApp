package com.fbn.case_study.utils;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CloudinaryUtil {
    private final Cloudinary cloudinary;

    public List<String> uploadFiles(List<MultipartFile> multipartFiles) {
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            try {
                File file = convertMultipartFileToFile(multipartFile);

                // Upload to Cloudinary
                Map<String, Object> params = new HashMap<>();
                params.put("folder", "fbn/Uploads");
                params.put("resource_type", "raw");

                Map<?, ?> cloudinaryResponse = cloudinary.uploader().upload(file, params);

                String url = cloudinaryResponse.get("url").toString();
                log.info("cloudinary url {}", url);
                uploadedUrls.add(url);

                file.delete();

            } catch (Exception e) {
                throw new RuntimeException("Failed to upload file: " + multipartFile.getOriginalFilename(), e);
            }
        }

        return uploadedUrls;
    }
    public String uploadFile(MultipartFile multipartFile) {
        String uploadedUrl = "";

            try {
                File file = convertMultipartFileToFile(multipartFile);

                // Upload to Cloudinary
                Map<String, Object> params = new HashMap<>();
                params.put("folder", "fbn/Uploads");
                params.put("resource_type", "raw");

                Map<?, ?> cloudinaryResponse = cloudinary.uploader().upload(file, params);

                String url = cloudinaryResponse.get("url").toString();
                log.info("cloudinary url {}", url);
                uploadedUrl = url;

                file.delete();

            } catch (Exception e) {
                throw new RuntimeException("Failed to upload file: " + multipartFile.getOriginalFilename(), e);
            }

        return uploadedUrl;
    }
    public File convertMultipartFileToFile(MultipartFile multipartFile) {
        String extension = getFileExtension(multipartFile.getOriginalFilename());
        try {
            File tempFile = File.createTempFile("temp-file", extension);

            long size = multipartFile.getSize();
            long max = 10L * 1024 * 1024; // 10MB
            if (size > max) {
                throw new RuntimeException("File size exceeded. Please upload a document with size less than 10MB");
            }

            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(multipartFile.getBytes());
            }

            return tempFile;
        } catch (IOException e) {
            log.error("Error converting MultipartFile to File: {}", e.getMessage());
            throw new RuntimeException("Failed to convert MultipartFile to File", e);
        }
    }
    private String getFileExtension(String filename) {
        int lastDot = filename.lastIndexOf(".");
        return (lastDot != -1) ? filename.substring(lastDot) : "";
    }

    public void deleteFile(String documentId) {
        Map<String, String> params = new HashMap<>();
        params.put("public_id", documentId);

        try {
            log.info("Deleting file with publicId {} ", documentId);
            cloudinary.uploader().destroy(documentId, params);
            log.info("Successfully deleted file with publicId {} ",documentId);
        } catch (Exception e) {
            log.info("Error deleting file with publicId {} ", documentId);
            throw new RuntimeException("Failed to delete file: " + documentId, e);
        }
    }

}
