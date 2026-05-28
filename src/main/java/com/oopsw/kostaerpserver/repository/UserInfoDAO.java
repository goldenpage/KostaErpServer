package com.oopsw.kostaerpserver.repository;

import com.oopsw.kostaerpserver.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoDAO {

    User login(User user);

    int register(User user);

    User checkMemberByVO(User user);

    int setPw(User user);

    int getPhoneCheck(@Param("phone") String phone);

    int getBidCheck(@Param("bId") String bId);

    int checkPwFindUser(User user);
}