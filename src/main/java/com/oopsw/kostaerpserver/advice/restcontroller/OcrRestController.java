package com.oopsw.kostaerpserver.advice.restcontroller;


import com.oopsw.kostaerpserver.dto.ocr.OcrResponse;
import com.oopsw.kostaerpserver.service.OcrServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ocr")
@RequiredArgsConstructor
public class OcrRestController {
    private final OcrServiceImpl ocrService;

    @PostMapping
    public ResponseEntity<OcrResponse> getOcrCheck(@RequestParam("file")
        MultipartFile multipartFile) {

        return ResponseEntity.ok(ocrService.requestOcr(multipartFile));
    }
}
