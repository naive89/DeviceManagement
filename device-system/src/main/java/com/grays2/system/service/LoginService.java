package com.grays2.system.service;


import com.grays2.common.result.R;
import com.grays2.system.domain.Login;
import com.grays2.system.domain.bo.LoginBo;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.bo.UserBo;
import com.grays2.system.domain.model.LoginTempInfo;
import com.grays2.system.domain.vo.LoginVo;
import com.grays2.system.domain.vo.PageVo;

import java.util.List;

public interface LoginService {
    R<Object> doLogin(LoginBo bo);

    R<String> doRegister(UserBo bo);

    List<LoginVo> getListIndex();


    PageVo<LoginVo> getList(PageBo pageBo);
}
