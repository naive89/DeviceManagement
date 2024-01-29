package com.grays2.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.grays2.system.domain.Role;
import com.grays2.system.domain.bo.RoleBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getList();

    Role getRoleByUsername(@Param("username") String username);

    Role getLastRole();

    void insertBo(@Param("bo") RoleBo bo);

    Role getRoleByAccount(@Param("account") String account);

    void updateBo(@Param("roleBo") RoleBo roleBo);

    void deleteBo(@Param("roleBo") RoleBo roleBo);

    Role getRoleByUserId(@Param("userId") Integer userId);

    Role getByAccess(@Param("access") int access);
}
