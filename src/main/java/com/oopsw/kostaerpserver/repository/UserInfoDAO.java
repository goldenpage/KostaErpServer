package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoDAO {

    User login(String bId, String pw);
    int register(User user);
    User checkMemberByVO(String bId, String name, String pw);
    int setPw( String pw, String bId, String name, String phone);
    int getPhoneCheck(String phone);
    int getBidCheck(@Param("bId") String bId);
    int checkPwFindUser(String bId, String name, String phone);
}