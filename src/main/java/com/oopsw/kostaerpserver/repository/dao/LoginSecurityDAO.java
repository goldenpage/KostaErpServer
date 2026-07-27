package com.oopsw.kostaerpserver.repository.dao;

import com.oopsw.kostaerpserver.vo.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginSecurityDAO {
    LoginUser findByBId(@Param("bId") String bId);
}
