package com.grays2.system.service;



import com.grays2.common.result.R;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.bo.UserBo;
import com.grays2.system.domain.model.LoginTempInfo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.domain.vo.UserVo;

import java.util.List;
import java.util.Map;

public interface UserService {
    PageVo<UserVo> getList(PageBo pageBo);

    List<UserVo> getList();

    PageVo<UserVo> selectUserLists(String username, PageBo pageBo);

    byte[] getUserImage(String account);

    Map<String, Object> getInfo();

    Map<String, Object> getInfoData();

    R<String> ChangeStatus(UserBo bo);

    void deleteList(List<UserBo> userList);

    R<String> delete(UserBo userBo);

    R<String> insert(UserBo bo);

    R<String> updatePwd(UserBo bo);

    R<String> updateUserInfo(UserBo bo);

    PageVo<LoginTempInfo> getOnlineList(PageBo pageBo);
}
