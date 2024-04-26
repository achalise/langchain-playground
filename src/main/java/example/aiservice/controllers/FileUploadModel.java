package example.aiservice.controllers;

import org.springframework.web.multipart.MultipartFile;

public record FileUploadModel(String user, MultipartFile file) {
}
