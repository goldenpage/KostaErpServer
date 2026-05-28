package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.UserInfoDAO;
import com.oopsw.kostaerpserver.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    UserInfoDAO userInfoDAO;

    @Override
    public boolean login(String bId, String pw) {
        return userInfoDAO.login(bId, pw);
    }

    @Override
    public int register(User user) {
        if (user.getMarketingDate() == null) {

        }
        return userInfoDAO.register(user);
    }

    @Override
    public User checkMemberByVO(User user) {
        return userInfoDAO.checkMemberByVO(user);
    }

    @Override
    public int setPw(User user) {
        return userInfoDAO.setPw(user);
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
    public int checkPwFindUser(User user) {
        return userInfoDAO.checkPwFindUser(user);
    }
}
