package com.grays2.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.grays2.system.domain.Menu;
import com.grays2.system.domain.bo.MenuBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenuByAccess(@Param("access") int access);

    List<Menu> getMenuList();

    Menu getMenuByName(@Param("name") String name);

    int getLastId();

    void insertMenuBo(@Param("menu") MenuBo menu);

    List<Menu> getMenuListByParentId(@Param("id") int id);

    void updateMenuList(@Param("list") List<Menu> menus);

    List<Menu> getMenuLists(@Param("list") List<Integer> insertList);
}
