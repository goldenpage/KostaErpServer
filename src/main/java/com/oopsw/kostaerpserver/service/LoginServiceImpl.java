package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.auth.service.AuthService;
import com.oopsw.kostaerpserver.dto.auth.RegisterRequest;
import com.oopsw.kostaerpserver.repository.dao.UserInfoDAO;
import com.oopsw.kostaerpserver.service.Interface.LoginService;
import com.oopsw.kostaerpserver.vo.User;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthService authService;
    private final UserInfoDAO userInfoDAO;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User login(String bId, String pw) throws BadRequestException {
        if (bId == null || bId.isBlank()) {
            throw new BadRequestException("사업자 ID는 필수입니다.");
        }
        if (pw == null || pw.isBlank()) {
            throw new BadRequestException("비밀번호는 필수입니다.");
        }
        User user = userInfoDAO.login(bId, pw);
        if (user == null) {
            throw new BadRequestException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    @Transactional
    @Override
    public int register(RegisterRequest request) {
        LocalDateTime now = LocalDateTime.now();

        if (request != null) {

        }
        String encode = passwordEncoder.encode(request.getPw());
        User user = User.builder()
            .bId(request.getBId())
            .pw(encode)
            .phone(request.getPhone())
            .name(request.getName())
            .email(request.getEmail())
            .storeName(request.getStoreName())
            .storeType(request.getStoreType())
            .storeCategory(request.getStoreCategory())
            .signDate(now)
            .agreementDate(now)
            .marketingDate(request.isMarketingAgree() ? now : null)
            .build();
        int result = userInfoDAO.register(user);
        if (result == 1) {
            authService.createUserAccount(user.getBId(), user.getPw(), user.getName(), user.getEmail());
        }
        return result;
    }

    @Override
    public User checkMemberByVO(String bId, String name, String pw) {
        return userInfoDAO.checkMemberByVO(bId, name, pw);
    }


    @Override
    public int setPw(String pw, String bId, String name, String phone) {
        return userInfoDAO.setPw(pw, bId, name, phone);
    }

    @Override
    public int getPhoneCheck(String phone) {
        return userInfoDAO.getPhoneCheck(phone);
    }

    @Override
    public int getBidCheck(String bId) {
        return userInfoDAO.getBidCheck(bId);
    }


    @Override
    public int checkPwFindUser(String bId, String name, String phone) {
        return userInfoDAO.checkPwFindUser(bId, name, phone);
    }

    public String getNameById(String bId) {
        return userInfoDAO.getNameById(bId);
    }
}
