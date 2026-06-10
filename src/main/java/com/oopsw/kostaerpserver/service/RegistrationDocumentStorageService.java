package com.oopsw.kostaerpserver.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RegistrationDocumentStorageService {

    private static final Set<String> ALLOWED_EXTENSIONS =
        Set.of("png", "jpg", "jpeg");

    private final Path storageRoot;

    public RegistrationDocumentStorageService(
        @Value("${registration.document-storage-path:uploads/registration}") String storagePath
    ) {
        this.storageRoot = Path.of(storagePath).toAbsolutePath().normalize();
    }

    public String store(String bId, MultipartFile document) {
        if (document == null || document.isEmpty()) {
            throw new IllegalArgumentException("사업자등록증 파일은 필수입니다.");
        }

        String extension = getExtension(document.getOriginalFilename());
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("PNG, JPG 파일만 업로드할 수 있습니다.");
        }

        String normalizedBid = bId.replaceAll("\\D", "");
        Path target = storageRoot.resolve(
            normalizedBid + "-" + UUID.randomUUID() + "." + extension
        ).normalize();

        if (!target.startsWith(storageRoot)) {
            throw new IllegalArgumentException("올바르지 않은 파일 경로입니다.");
        }

        try {
            Files.createDirectories(storageRoot);
            Files.copy(document.getInputStream(), target);
            return target.toString();
        } catch (IOException e) {
            throw new IllegalStateException("가입 심사 서류 저장에 실패했습니다.", e);
        }
    }

    private String getExtension(String originalFilename) {
        if (originalFilename == null) {
            return "";
        }

        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == originalFilename.length() - 1) {
            return "";
        }
        return originalFilename.substring(dotIndex + 1).toLowerCase(Locale.ROOT);
    }
}
