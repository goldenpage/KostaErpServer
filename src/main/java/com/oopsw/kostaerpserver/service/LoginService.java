package com.oopsw.kostaerpserver.service;


import com.oopsw.kostaerpserver.vo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    User login(String bId, String pw);
    int register(User user, boolean marketingAgree);
    User checkMemberByVO(String bId, String name,String pw);
   int setPw( String pw, String bId, String name, String phone);
    int getPhoneCheck( String phone);
    int getBidCheck(@Param("bId") String bId);
    int checkPwFindUser(String bId, String name, String phone);
}
