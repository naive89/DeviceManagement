package com.grays2.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.grays2.system.domain.User;
import com.grays2.system.domain.bo.UserBo;
import com.grays2.system.domain.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    UserVo getUserByUsernameVo(@Param("username") String username);

    UserBo getUserByUserIdBo(@Param("userId") int userId);

    List<UserVo> getUserListVo();

    List<User> getUserList();

    List<UserVo> selectUserLists(@Param("username") String username);

    void deleteList(@Param("userList") List<UserBo> userList);

    void updatePwd(@Param("username") String username, @Param("password") String password);

    void updateImage(@Param("username") String username, @Param("id") int id);

    void updateBoByUserId(@Param("bo") UserBo bo);

    int getLastId();

    User getUserByAccount(@Param("account") String account);

    void insertUserList(@Param("list") List<User> list);
}
