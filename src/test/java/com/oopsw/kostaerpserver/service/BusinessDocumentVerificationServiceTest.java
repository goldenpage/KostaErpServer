package com.oopsw.kostaerpserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.oopsw.kostaerpserver.dto.ocr.BusinessRegistrationValidationResult;
import com.oopsw.kostaerpserver.dto.ocr.BusinessVerificationResult;
import com.oopsw.kostaerpserver.dto.ocr.PythonOcrResponse;
import com.oopsw.kostaerpserver.service.client.BusinessRegistrationClient;
import com.oopsw.kostaerpserver.service.client.PythonOcrClient;
import com.oopsw.kostaerpserver.service.entity.ocr.BusinessDocumentVerificationServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

class BusinessDocumentVerificationServiceTest {

    private static final String BUSINESS_NUMBER = "0000000006";

    private BusinessDocumentVerificationServiceImpl service;
    private MultipartFile document;
    private BusinessRegistrationValidationResult validationResult;
    private RuntimeException validationException;
    private PythonOcrResponse ocrResponse;
    private String validatedStartDate;
    private String validatedRepresentativeName;
    private String validatedCompanyName;

    @BeforeEach
    void setUp() {
        PythonOcrClient pythonOcrClient = new PythonOcrClient(RestClient.create()) {
            @Override
            public PythonOcrResponse extractBusinessNumber(MultipartFile file) {
                return ocrResponse;
            }
        };
        BusinessRegistrationClient businessRegistrationClient =
            new BusinessRegistrationClient(RestClient.builder()) {
                @Override
                public BusinessRegistrationValidationResult validate(
                    String businessNumber,
                    String startDate,
                    String representativeName,
                    String companyName
                ) {
                    if (validationException != null) {
                        throw validationException;
                    }
                    validatedStartDate = startDate;
                    validatedRepresentativeName = representativeName;
                    validatedCompanyName = companyName;
                    return validationResult;
                }
            };
        service = new BusinessDocumentVerificationServiceImpl(
            pythonOcrClient,
            businessRegistrationClient
        );
        document = new MockMultipartFile(
            "document",
            "registration.png",
            "image/png",
            new byte[]{1}
        );
        ocrResponse = autoAcceptedResponse();
    }

    @Test
    void verifyRejectsUnregisteredBusinessNumber() {
        validationResult = new BusinessRegistrationValidationResult(
            false,
            "국세청에 등록되지 않은 사업자등록번호입니다."
        );

        BusinessVerificationResult result = verify();

        assertEquals(BusinessVerificationResult.Status.REJECTED, result.status());
        assertEquals("국세청에 등록되지 않은 사업자등록번호입니다.", result.message());
    }

    @Test
    void verifyRequestsReviewWhenValidationResponseIsInvalid() {
        validationException = new IllegalStateException("알 수 없는 검증 코드");

        BusinessVerificationResult result = verify();

        assertEquals(BusinessVerificationResult.Status.RETRY, result.status());
        assertEquals("사업자등록번호 검증 서버 요청에 실패했습니다.", result.message());
    }

    @Test
    void verifyUsesRegistrationFormNamesInsteadOfUnreliableOcrNames() {
        ocrResponse = reviewRequiredResponse();
        validationResult = new BusinessRegistrationValidationResult(true, "");

        BusinessVerificationResult result = verify();

        assertEquals(BusinessVerificationResult.Status.APPROVED, result.status());
        assertEquals("20250101", validatedStartDate);
        assertEquals("김사장", validatedRepresentativeName);
        assertEquals("김밥전문점", validatedCompanyName);
    }

    @Test
    void verifyRequestsReviewWhenReviewRequiredDataFailsBusinessValidation() {
        ocrResponse = reviewRequiredResponse();
        validationResult = new BusinessRegistrationValidationResult(
            false,
            "입력하신 정보가 일치하지 않습니다."
        );

        BusinessVerificationResult result = verify();

        assertEquals(BusinessVerificationResult.Status.NEED_REVIEW, result.status());
        assertEquals(
            "OCR 인식 정보와 사업자등록정보가 일치하지 않아 관리자 확인이 필요합니다.",
            result.message()
        );
    }

    @Test
    void verifyRequestsReviewWhenOcrStartDateIsMissing() {
        ocrResponse = new PythonOcrResponse(
            "REVIEW_REQUIRED",
            null,
            "김밥전문점",
            "김사장",
            null,
            "2026-12-31",
            null,
            List.of(candidate())
        );

        BusinessVerificationResult result = verify();

        assertEquals(BusinessVerificationResult.Status.NEED_REVIEW, result.status());
        assertEquals(
            "OCR에서 사업자등록정보 검증에 필요한 개업일자를 확인하지 못했습니다.",
            result.message()
        );
    }

    @Test
    void verifyUsesCandidateMatchingExpectedBusinessNumber() {
        ocrResponse = new PythonOcrResponse(
            "REVIEW_REQUIRED",
            null,
            "김밥전문점",
            "김사장",
            "2025-01-01",
            "2026-12-31",
            candidate("1234567890"),
            List.of(
                candidate("1234567890"),
                candidate(BUSINESS_NUMBER)
            )
        );
        validationResult = new BusinessRegistrationValidationResult(true, "");

        BusinessVerificationResult result = verify();

        assertEquals(BusinessVerificationResult.Status.APPROVED, result.status());
    }

    private BusinessVerificationResult verify() {
        return service.verify(
            BUSINESS_NUMBER,
            "김사장",
            "김밥전문점",
            document
        );
    }

    private PythonOcrResponse autoAcceptedResponse() {
        return new PythonOcrResponse(
            "AUTO_ACCEPTED",
            BUSINESS_NUMBER,
            "김밥전문점",
            "김사장",
            "2025-01-01",
            "2026-12-31",
            null,
            List.of()
        );
    }

    private PythonOcrResponse reviewRequiredResponse() {
        return new PythonOcrResponse(
            "REVIEW_REQUIRED",
            null,
            "김남진",
            "김사장",
            "2025-01-01",
            "2026-12-31",
            null,
            List.of(candidate())
        );
    }

    private PythonOcrResponse.OcrCandidate candidate() {
        return candidate(BUSINESS_NUMBER);
    }

    private PythonOcrResponse.OcrCandidate candidate(String businessNumber) {
        return new PythonOcrResponse.OcrCandidate(
            businessNumber,
            "000-00-00006",
            false,
            0.4291,
            1,
            14,
            3,
            0.79,
            "사업자등록번호 : 000-00-00006",
            List.of(1.0, 2.0, 3.0, 4.0),
            List.of()
        );
    }
}
