package com.oopsw.kostaerpserver.service;


import com.oopsw.kostaerpserver.dto.ocr.BusinessVerificationResult;
import com.oopsw.kostaerpserver.dto.ocr.PythonOcrResponse;
import com.oopsw.kostaerpserver.service.Interface.BusinessDocumentVerificationService;
import com.oopsw.kostaerpserver.service.client.BusinessRegistrationClient;
import com.oopsw.kostaerpserver.service.client.PythonOcrClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BusinessDocumentVerificationServiceImpl implements
    BusinessDocumentVerificationService {

    private final PythonOcrClient pythonOcrClient;
    private final BusinessRegistrationClient businessRegistrationClient;

    @Override
    public BusinessVerificationResult verify(String expectedBusinessNumber,
        MultipartFile document) {
        String expected = normalize(expectedBusinessNumber);

        PythonOcrResponse ocrResponse;

        try {
            ocrResponse = pythonOcrClient.extractBusinessNumber(document);
        } catch (Exception exception) {
            return BusinessVerificationResult.needReview(
                null, "OCR 서버 요청에 실패했습니다."
            );
        }

        String extracted = normalizeOrNull(ocrResponse.bId());

        if (extracted == null) {
            return BusinessVerificationResult.needReview(
                null, "서류에서 사업자등록번호를 인식하지 못했습니다."
            );
        }

        if (!expected.equals(extracted)) {
            return BusinessVerificationResult.rejected(
                extracted, "입력한 사업자등록번호와 서류 번호가 일치하지 않습니다."
            );
        }

        try {
            if (!businessRegistrationClient.exists(extracted)) {
                return BusinessVerificationResult.rejected(
                    extracted, "등록되지 않은 사업자등록번호입니다."
                );
            }
        } catch (Exception exception) {
            return BusinessVerificationResult.needReview(
                extracted, "사업자등록번호 검증 서버 요청에 실패했습니다."
            );
        }

        return BusinessVerificationResult.approved(extracted);
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
}


