package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.UserInfoDAO;
import com.oopsw.kostaerpserver.service.Interface.LoginService;
import com.oopsw.kostaerpserver.vo.User;
import java.time.LocalDateTime;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserInfoDAO userInfoDAO;

    @Override
    public User login(String bId, String pw) throws BadRequestException {
        if (bId == null || bId.isBlank()) {
            throw new BadRequestException("사업자 ID는 필수입니다.");
        }
        return userInfoDAO.login(bId, pw);
    }


    @Override
    public int register(User user, boolean marketingAgree) {
        LocalDateTime now = LocalDateTime.now();
        user.setAgreementDate(now);
        user.setSignDate(now);
        user.setMarketingDate(marketingAgree ? now : null);

        return userInfoDAO.register(user);
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
}
