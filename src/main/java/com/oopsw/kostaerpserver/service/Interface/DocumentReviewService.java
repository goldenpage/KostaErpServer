package com.oopsw.kostaerpserver.service.Interface;

import com.oopsw.kostaerpserver.dto.ocr.DocumentReviewResponse;
import com.oopsw.kostaerpserver.service.OcrServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface DocumentReviewService {

    public DocumentReviewResponse review(MultipartFile multipartFile);


}
