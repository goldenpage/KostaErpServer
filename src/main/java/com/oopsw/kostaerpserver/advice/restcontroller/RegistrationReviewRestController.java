package com.oopsw.kostaerpserver.advice.restcontroller;


import com.oopsw.kostaerpserver.auth.ErpAdminUserDetails;
import com.oopsw.kostaerpserver.dto.manager.RegistrationDocument;
import com.oopsw.kostaerpserver.dto.manager.RegistrationRejectRequest;
import com.oopsw.kostaerpserver.dto.manager.RegistrationReviewResponse;
import com.oopsw.kostaerpserver.repository.entity.admin.ReviewStatus;
import com.oopsw.kostaerpserver.service.entity.ocr.RegistrationReviewService;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manager/registration-reviews")
@RequiredArgsConstructor
public class RegistrationReviewRestController {
    private final RegistrationReviewService reviewService;

    @GetMapping
    public Page<RegistrationReviewResponse> getReviews(
        @RequestParam(defaultValue = "PENDING") ReviewStatus status,
        Pageable pageable
    ) {
        return reviewService.getReviews(status, pageable);
    }

    @GetMapping("/{reviewId}")
    public RegistrationReviewResponse getReview(@PathVariable int reviewId) {
        return reviewService.getReview(reviewId);
    }

    @PostMapping("/{reviewId}/approve")
    public ResponseEntity<Void> approve(
        @PathVariable int reviewId,
        @AuthenticationPrincipal ErpAdminUserDetails admin
    ) {
        reviewService.approve(reviewId, admin.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}/reject")
    public ResponseEntity<Void> reject(
        @PathVariable int reviewId,
        @RequestBody RegistrationRejectRequest request,
        @AuthenticationPrincipal ErpAdminUserDetails admin
    ) {
        reviewService.reject(reviewId, admin.getUsername(), request.reason());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{reviewId}/document")
    public ResponseEntity<Resource> getDocument(
        @PathVariable int reviewId
    ) {
        RegistrationDocument document = reviewService.getDocument(reviewId);
        Resource resource = document.resource();

        return ResponseEntity.ok()
            .contentType(document.mediaType())
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.inline()
                    .filename(resource.getFilename(), StandardCharsets.UTF_8)
                    .build()
                    .toString()
            )
            .body(resource);
    }
}
