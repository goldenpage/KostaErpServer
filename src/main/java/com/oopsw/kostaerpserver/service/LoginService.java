package com.oopsw.kostaerpserver.service;


import com.oopsw.kostaerpserver.vo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    boolean login(String bId, String pw);
    int register(User user);
    User checkMemberByVO(User user);
    int setPw(User user);
    int getPhoneCheck(@Param("phone") String phone);
    int getBidCheck(@Param("bId") String bId);
    int checkPwFindUser(User user);
}
