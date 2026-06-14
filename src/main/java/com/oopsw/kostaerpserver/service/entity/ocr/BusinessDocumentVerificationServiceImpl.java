package com.oopsw.kostaerpserver.service.entity.ocr;

import com.oopsw.kostaerpserver.dto.ocr.BusinessRegistrationValidationResult;
import com.oopsw.kostaerpserver.dto.ocr.BusinessVerificationResult;
import com.oopsw.kostaerpserver.dto.ocr.PythonOcrResponse;
import com.oopsw.kostaerpserver.service.client.BusinessRegistrationClient;
import com.oopsw.kostaerpserver.service.client.PythonOcrClient;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessDocumentVerificationServiceImpl implements
    BusinessDocumentVerificationService {

    private static final long MAX_DOCUMENT_SIZE = 5 * 1024 * 1024;
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
        "application/pdf",
        "image/jpeg",
        "image/png"
    );

    private final PythonOcrClient pythonOcrClient;
    private final BusinessRegistrationClient businessRegistrationClient;

    @Override
    public BusinessVerificationResult verify(
        String expectedBusinessNumber,
        String representativeName,
        String companyName,
        MultipartFile document
    ) {
        validate(document);

        String expected = normalize(expectedBusinessNumber);
        String requestedRepresentativeName = normalizeText(representativeName);
        String requestedCompanyName = normalizeText(companyName);

        PythonOcrResponse ocrResponse;
        try {
            ocrResponse = pythonOcrClient.extractBusinessNumber(document);
        } catch (Exception exception) {
            log.warn("OCR 서버 요청에 실패했습니다.", exception);
            return BusinessVerificationResult.retry(
                null, "OCR 서버 요청에 실패했습니다."
            );
        }

        if (ocrResponse == null) {
            return BusinessVerificationResult.retry(
                null, "OCR 응답이 없습니다."
            );
        }

        String extracted = getCandidateNumber(ocrResponse, expected);
        if (extracted == null) {
            return BusinessVerificationResult.needReview(
                null,
                "서류에서 사업자등록번호를 확정하지 못했습니다."
            );
        }
        if (!expected.equals(extracted)) {
            return BusinessVerificationResult.rejected(
                extracted, "입력한 사업자등록번호와 서류 번호가 일치하지 않습니다."
            );
        }

        String startDate = normalizeDate(ocrResponse.validityStartDate());
        if (startDate == null) {
            return BusinessVerificationResult.needReview(
                extracted,
                "OCR에서 사업자등록정보 검증에 필요한 개업일자를 확인하지 못했습니다."
            );
        }
        try {
            BusinessRegistrationValidationResult result =
                businessRegistrationClient.validate(
                    extracted,
                    startDate,
                    requestedRepresentativeName,
                    requestedCompanyName
                );
            if (!result.valid()) {
                if (!isAutoAccepted(ocrResponse)) {
                    return BusinessVerificationResult.needReview(
                        extracted,
                        "OCR 인식 정보와 사업자등록정보가 일치하지 않아 관리자 확인이 필요합니다."
                    );
                }
                return BusinessVerificationResult.rejected(
                    extracted, result.message()
                );
            }
        } catch (Exception exception) {
            log.warn(
                "사업자등록번호 검증 서버 요청에 실패했습니다. businessNumber={}",
                extracted,
                exception
            );
            return BusinessVerificationResult.retry(
                extracted, "사업자등록번호 검증 서버 요청에 실패했습니다."
            );
        }

        return BusinessVerificationResult.approved(extracted);
    }

    private boolean isAutoAccepted(PythonOcrResponse response) {
        return "AUTO_ACCEPTED".equalsIgnoreCase(response.status());
    }

    private String getCandidateNumber(PythonOcrResponse response, String expected) {
        String confirmed = normalizeOrNull(response.businessRegistrationNumber());
        if (confirmed != null) {
            return confirmed;
        }

        String matchingCandidate = findMatchingCandidate(response, expected);
        if (matchingCandidate != null) {
            return matchingCandidate;
        }

        if (response.selectedCandidate() != null) {
            return normalizeOrNull(response.selectedCandidate().number());
        }

        if (response.candidates() == null || response.candidates().isEmpty()) {
            return null;
        }

        return normalizeOrNull(response.candidates().get(0).number());
    }

    private String findMatchingCandidate(PythonOcrResponse response, String expected) {
        if (response.selectedCandidate() != null
            && expected.equals(normalizeOrNull(response.selectedCandidate().number()))) {
            return expected;
        }

        if (response.candidates() == null) {
            return null;
        }

        return response.candidates().stream()
            .map(PythonOcrResponse.OcrCandidate::number)
            .map(this::normalizeOrNull)
            .filter(expected::equals)
            .findFirst()
            .orElse(null);
    }

    private String normalizeDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }

        String normalized = date.replaceAll("\\D", "");
        return normalized.length() == 8 ? normalized : null;
    }

    private String normalizeText(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private String normalize(String number) {
        String normalized = normalizeOrNull(number);

        if (normalized == null) {
            throw new IllegalArgumentException("사업자등록번호 형식이 올바르지 않습니다.");
        }

        return normalized;
    }

    private String normalizeOrNull(String number) {
        if (number == null || number.isBlank()) {
            return null;
        }

        String normalized = number.replaceAll("\\D", "");
        return normalized.length() == 10 ? normalized : null;
    }


    private void validate( MultipartFile document) {
        if (document == null || document.isEmpty()) {
            throw new IllegalArgumentException("사업자등록증 파일은 필수입니다.");
        }

        if (document.getSize() > MAX_DOCUMENT_SIZE) {
            throw new IllegalArgumentException("파일은 5MB 이하만 업로드할 수 있습니다.");
        }

        String contentType = document.getContentType();
        if (!ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("PDF, JPG, PNG 파일만 업로드할 수 있습니다.");

        }
    }
}
