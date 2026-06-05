package com.oopsw.kostaerpserver.service.Interface;


import com.oopsw.kostaerpserver.dto.RegisterRequest;
import com.oopsw.kostaerpserver.vo.User;
import org.apache.coyote.BadRequestException;


public interface LoginService {
    User login(String bId, String pw) throws BadRequestException ;
    int register(RegisterRequest request);
    User checkMemberByVO(String bId, String name,String pw);
   int setPw( String pw, String bId, String name, String phone);
    int getPhoneCheck( String phone);
    int getBidCheck( String bId);
    int checkPwFindUser(String bId, String name, String phone);

}
