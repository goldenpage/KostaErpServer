package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.dto.auth.RegisterRequest;
import com.oopsw.kostaerpserver.dto.auth.RegistrationResponse;
import com.oopsw.kostaerpserver.dto.ocr.BusinessVerificationResult;
import com.oopsw.kostaerpserver.repository.dao.UserInfoDAO;
import com.oopsw.kostaerpserver.service.Interface.BusinessDocumentVerificationService;
import com.oopsw.kostaerpserver.service.Interface.LoginService;
import com.oopsw.kostaerpserver.service.Interface.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserInfoDAO userInfoDAO;
    private final LoginService loginService;
    private final BusinessDocumentVerificationService verificationService;

    @Override
    public RegistrationResponse register(
        RegisterRequest request,
        MultipartFile document
    ) {
        String normalizedBid = validateNewRegistration(request, document);
        request.setBId(normalizedBid);

        BusinessVerificationResult result =
            verificationService.verify(normalizedBid, document);

        return switch (result.status()) {
            case APPROVED -> {
                loginService.register(request);

                yield new RegistrationResponse(
                    "APPROVED",
                    "회원가입이 완료되었습니다."
                );
            }

            case REJECTED -> new RegistrationResponse(
                "REJECTED",
                result.message()
            );

            case NEED_REVIEW -> new RegistrationResponse(
                "RETRY",
                result.message()
            );
        };
    }

    private String validateNewRegistration(
        RegisterRequest request,
        MultipartFile document
    ) {
        if (request == null) {
            throw new IllegalArgumentException("회원가입 정보는 필수입니다.");
        }

        if (request.getBId() == null || request.getBId().isBlank()) {
            throw new IllegalArgumentException("사업자등록번호는 필수입니다.");
        }

        if (request.getPw() == null || request.getPw().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }

        if (document == null || document.isEmpty()) {
            throw new IllegalArgumentException("사업자등록증 파일은 필수입니다.");
        }

        String normalizedBid = request.getBId().replaceAll("\\D", "");

        if (normalizedBid.length() != 10) {
            throw new IllegalArgumentException(
                "사업자등록번호 형식이 올바르지 않습니다."
            );
        }

        if (userInfoDAO.getBidCheck(normalizedBid) > 0) {
            throw new IllegalArgumentException(
                "이미 가입된 사업자등록번호입니다."
            );
        }

        return normalizedBid;
    }
}