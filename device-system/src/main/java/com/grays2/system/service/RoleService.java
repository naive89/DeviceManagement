package com.grays2.system.service;



import com.grays2.common.result.R;
import com.grays2.system.domain.Role;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.bo.RoleBo;
import com.grays2.system.domain.vo.PageVo;

import java.util.List;

public interface RoleService {

    PageVo<Role> getList(PageBo pageBo);

    List<Role> getRoleKindList();

    void createRoleRedis();

    R<PageVo<Role>> insert(RoleBo roleBo, PageBo pageBo);

    PageVo<Role> getListSearch(RoleBo roleBo, PageBo pageBo);

    PageVo<Role> update(RoleBo roleBo, PageBo pageBo);

    PageVo<Role> delete(RoleBo roleBo, PageBo pageBo);
}
