package com.grays2.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.grays2.system.domain.Login;
import com.grays2.system.domain.vo.LoginVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoginMapper extends BaseMapper<Login> {
    void insertLogin(@Param("login") Login login);

    /**
     * 搜索某用户最后一次登录的信息
     */
    LoginVo getLastByUsername(@Param("username") String username);

    List<LoginVo> getList();

    List<LoginVo> getListByAccount(String account);

    LoginVo getLastByAccount(@Param("account") String account);
}
