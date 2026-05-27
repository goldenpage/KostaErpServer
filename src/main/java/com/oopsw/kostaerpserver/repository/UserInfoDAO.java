package com.oopsw.kostaerpserver.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoDAO {

    public String login();

    public String register();

    public boolean checkMemberByVO();

    public boolean setPw();

    public boolean getPhoneCheck();

    public boolean getBidCheck();

    public boolean checkPwFindUser();

}
