package com.oopsw.kostaerpserver.service.Interface;

import com.oopsw.kostaerpserver.dto.ocr.BusinessVerificationResult;
import org.springframework.web.multipart.MultipartFile;

public interface BusinessDocumentVerificationService {

    BusinessVerificationResult verify(
        String expectedBusinessNumber,
        String representativeName,
        String companyName,
        MultipartFile document
    );
}
