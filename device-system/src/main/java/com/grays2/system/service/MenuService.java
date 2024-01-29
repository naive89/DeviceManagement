package com.grays2.system.service;



import com.grays2.common.result.R;
import com.grays2.system.domain.Menu;
import com.grays2.system.domain.bo.MenuBo;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.vo.PageVo;

import java.util.List;

public interface MenuService {
    List<Menu> getAsideMenus();

    List<Menu> listParent();

    PageVo<Menu> getList(PageBo pageBo);

    List<Menu> getList();

    PageVo<Menu> getMenusByName(MenuBo menuBo, PageBo pageBo);

    void createMenuParentRedis();

    void createMenuRedis();

    R<PageVo<Menu>> update(Menu menu, PageBo pageBo);

    R<PageVo<Menu>> insert(MenuBo menuBo, PageBo pageBo);

    R<PageVo<Menu>> delete(MenuBo menuBo, PageBo pageBo);

    List<Integer> getMenuListByRole(Integer access);

    R<String> changeMenuAccessByRole(List<Integer> newPower, List<Integer> oldPower, Integer access);
}
