package com.oopsw.kostaerpserver.service.Interface;

import com.oopsw.kostaerpserver.dto.ocr.DocumentReviewResponse;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentReviewService {

    DocumentReviewResponse review(String expectedBid, MultipartFile multipartFile);
}
