package com.oopsw.kostaerpserver.service.entity.ocr;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class RegistrationDocumentStorageService {

    private static final Map<String, String> ALLOWED_CONTENT_TYPES = Map.of(
        "image/jpeg", ".jpg",
        "image/png", ".png",
        "application/pdf", ".pdf"
    );

    private final Path storageDirectory;

    public RegistrationDocumentStorageService(
        @Value("${registration.documents.directory:uploads/registration}")
        String storageDirectory
    ) {
        this.storageDirectory = Path.of(storageDirectory).toAbsolutePath().normalize();
    }

    public String store(String businessId, MultipartFile document) {
        String extension = ALLOWED_CONTENT_TYPES.get(document.getContentType());
        if (extension == null) {
            throw new IllegalArgumentException("사업자등록증은 jpg, png, pdf 파일만 가능합니다.");
        }

        String safeBusinessId = businessId.replaceAll("\\D", "");
        Path target = storageDirectory.resolve(
            safeBusinessId + "-" + UUID.randomUUID() + extension
        ).normalize();

        try {
            Files.createDirectories(storageDirectory);
            try (InputStream inputStream = document.getInputStream()) {
                Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
            }
            return target.toString();
        } catch (IOException exception) {
            throw new IllegalStateException("검토용 사업자등록증 저장에 실패했습니다.", exception);
        }
    }

    public void delete(String documentPath) {
        if (documentPath == null || documentPath.isBlank()) {
            return;
        }

        Path target = Path.of(documentPath).toAbsolutePath().normalize();
        if (!target.startsWith(storageDirectory)) {
            return;
        }

        try {
            Files.deleteIfExists(target);
        } catch (IOException ignored) {
            log.error(String.valueOf(ignored));
        }
    }

    public Resource loadAsResource(String documentPath) {
        if (documentPath == null || documentPath.isBlank()) {
            throw new IllegalArgumentException("제출 서류 경로가 없습니다.");
        }

        Path path = Path.of(documentPath)
            .toAbsolutePath()
            .normalize();

        if (!path.startsWith(storageDirectory)) {
            throw new IllegalArgumentException("허용되지 않은 제출 서류 경로입니다.");
        }

        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("제출 서류를 찾을 수 없습니다.");
        }

        return new FileSystemResource(path);
    }
}
