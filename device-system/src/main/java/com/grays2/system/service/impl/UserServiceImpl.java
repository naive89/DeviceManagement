package com.grays2.system.service.impl;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.TokenSign;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.grays2.common.constant.RedisConstant;
import com.grays2.common.constant.SystemConstant;
import com.grays2.common.redis.RedisUtils;
import com.grays2.common.result.R;
import com.grays2.common.utils.GzipUtils;
import com.grays2.common.utils.StringUtils;
import com.grays2.system.domain.*;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.bo.UserBo;
import com.grays2.system.domain.model.LoginTempInfo;
import com.grays2.system.domain.vo.LoginVo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.domain.vo.UserVo;
import com.grays2.system.mapper.*;
import com.grays2.system.others.utils.CheckUtils;
import com.grays2.system.service.DictService;
import com.grays2.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final CommonMapper commonMapper;

    private final RoleMapper roleMapper;

    private final LoginMapper loginMapper;

    private final UserRoleMapper userRoleMapper;

    private final DictService dictService;

    private final GzipUtils gzipUtils;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, CommonMapper commonMapper, RoleMapper roleMapper, LoginMapper loginMapper, UserRoleMapper userRoleMapper,  DictService dictService, GzipUtils gzipUtils) {
        this.userMapper = userMapper;
        this.commonMapper = commonMapper;
        this.roleMapper = roleMapper;
        this.loginMapper = loginMapper;
        this.userRoleMapper = userRoleMapper;
        this.dictService = dictService;
        this.gzipUtils = gzipUtils;
    }

    @Override
    public PageVo<UserVo> getList(PageBo pageBo) {
        List<UserVo> list = this.getList();
        return new PageVo<>(pageBo, list);
    }

    @Override
    public List<UserVo> getList() {
        List<UserVo> userListVo = userMapper.getUserListVo();
        userListVo = userListVo.stream().peek(o -> o.setImage("")).collect(Collectors.toList());
        return userListVo;
    }

    /**
     * 模糊查询用户列表
     * 查询账号列、姓名列
     */
    @Override
    public PageVo<UserVo> selectUserLists(String username, PageBo pageBo) {
        List<UserVo> userVoList = userMapper.selectUserLists(username);
        return new PageVo<>(pageBo, userVoList);
    }

    @Override
    public byte[] getUserImage(String account) {
        int imageId = userMapper.getUserByAccount(account).getImageId();
        String image = commonMapper.getImage(imageId);
        return gzipUtils.compress(image);
    }

    @Override
    public Map<String, Object> getInfo() {
        String account = (String) StpUtil.getLoginId();
        User user = userMapper.getUserByAccount(account);
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        Role role = roleMapper.getRoleByAccount(account);
        map.put("roleName", role.getRoleName());
        map.put("roleId", role.getRoleId());
        LoginVo loginVo = loginMapper.getLastByAccount(account);
        map.put("time", loginVo.getTime().toString().replace("T", " "));
        map.put("ip", loginVo.getIp());
        byte[] image = getUserImage(account);
        map.put("image", image);
        return map;
    }

    @Override
    public Map<String, Object> getInfoData() {
        String account = (String) StpUtil.getLoginId();
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.getUserByAccount(account);
        map.put("user", user);
        byte[] image_byte = getUserImage(account);
        map.put("image", image_byte);
        return map;
    }

    /**
     * 修改账号状态
     */
    @Override
    public R<String> ChangeStatus(UserBo bo) {
        User user = userMapper.selectById(bo.getUserId());
        int status = user.getStatus();
        if (status == 3) {
            return R.warn("超级管理员无法被禁止");
        }
        status = (status + 1) % 2;
        user.setStatus(status);
        userMapper.updateById(user);
        if (status == SystemConstant.USER_STATUS_NORMAL) {
            return R.ok("激活成功");
        }
        if (status == SystemConstant.USER_STATUS_DISABLE) {
            return R.ok("禁用成功");
        }
        return R.warn("修改账号状态出现错误");
    }

    @Override
    public void deleteList(List<UserBo> userList) {
        userMapper.deleteList(userList);
    }

    @Override
    public R<String> delete(UserBo userBo) {
        int userId = userBo.getUserId();
        //删除用户表的关联表
        //1.用户权限表
        LambdaQueryWrapper<UserRole> lqwUserRole = new LambdaQueryWrapper<>();
        lqwUserRole.eq(UserRole::getUserId, userId);
        userRoleMapper.delete(lqwUserRole);
        //删除用户表信息
        LambdaQueryWrapper<User> lqwUser = new LambdaQueryWrapper<>();
        lqwUser.eq(User::getUserId, userId);
        userMapper.delete(lqwUser);
        return R.ok("删除成功");
    }

    /**
     * 新增用户
     */
    @Override
    public R<String> insert(UserBo bo) {
        if (checkUserExist(bo.getUsername())) return R.fail("用户名已存在");
        String check = CheckUtils.checkUserBo(bo);
        //检查结果不为空
        if (StringUtils.isNotEmpty(check)) return R.fail(check);
        //校验没问题
        User user = new User();
        user.setUsername(user.getUsername());
        user.setName(user.getName());
        user.setMobile(user.getMobile());
        user.setRemark(user.getRemark());
        user.setEmail(user.getEmail());
        user.setStatus(SystemConstant.USER_STATUS_NORMAL);
        //用户输入的密码
        String password = bo.getPassword();
        //生成随机加密盐值
        String salt = IdUtil.simpleUUID();
        password = SaSecureUtil.sha256(password + salt);
        user.setSalt(salt);
        user.setPassword(password);
        user.setCreateTime(LocalDateTime.now());
        //给用户生成一张照片
        user.setImageId(SystemConstant.image_Id);
        //生成账号
        String db_user = RedisConstant.db_user;
        int userId = dictService.getId(db_user);
        String account = String.valueOf(userId + SystemConstant.billion - 1);
        user.setAccount(account);
        user.setUserId(userId);
        user.setUsername(bo.getUsername());
        user.setName(bo.getName());
        user.setEmail(bo.getEmail());
        user.setMobile(bo.getMobile());
        //添加用户信息
        userMapper.insert(user);
        dictService.updateDBId(db_user);
        UserRole userRole = new UserRole();
        int id = dictService.getId(RedisConstant.db_user_role);
        userRole.setId(id);
        int role_id = dictService.getId(RedisConstant.db_role) - 1;
        userRole.setUserId(userId);
        userRole.setRoleId(role_id);
        userRoleMapper.insert(userRole);
        return R.ok("添加账号成功,账号为:" + account);
    }

    @Override
    public R<String> updatePwd(UserBo bo) {
        if (bo.getOldPassword().equals(bo.getPassword())) return R.warn("新密码不可和旧密码一致");
        UserVo vo = userMapper.getUserByUsernameVo(bo.getUsername());
        String salt = vo.getSalt();
        String pwd = SaSecureUtil.sha256(bo.getOldPassword() + salt);
        if (!pwd.equals(vo.getPassword())) return R.fail("修改密码失败，旧密码不正确");
        userMapper.updatePwd(vo.getUsername(), pwd);
        return R.ok("修改密码成功");
    }

    @Override
    public R<String> updateUserInfo(UserBo bo) {
        UserBo bo1 = userMapper.getUserByUserIdBo(bo.getUserId());
        if (bo.equals(bo1)) return R.warn("用户信息修改前后一致");
        if (StringUtils.isNotEmpty(userMapper.getUserByUsernameVo(bo.getUsername()))) {
            return R.fail("用户名已存在");
        }
        userMapper.updateBoByUserId(bo);
        return R.ok("修改成功");
    }

    @Override
    public PageVo<LoginTempInfo> getOnlineList(PageBo pageBo) {
        StpLogic stpLogic = SaManager.getStpLogic("login");
        List<String> tokens = stpLogic.searchTokenValue("", -1, -1, true);
        // 保存所有用户id及对应的不同平台下的临时有效时长
        List<LoginTempInfo> list = new ArrayList<>();
        tokens.forEach(o -> {
            //真实token
            String token = o.substring(o.lastIndexOf(":") + 1);
            //token过期时间
            long loginTimeout = stpLogic.getTokenActivityTimeoutByToken(token);
            if (loginTimeout == -2) {
                // 当前 token 所代表的会话已经临时过期了, 直接跳过
                return;
            }
            //从token获取登录的账号
            String account = (String) stpLogic.getLoginIdByToken(token);
            String username = userMapper.getUserByAccount(account).getUsername();
            // 根据登录id和token, 获取对应的登录类型
            String mobile = null;
            SaSession session = stpLogic.getSessionByLoginId(account, false);
            if (session == null) {
                mobile = "";
            } else {
                // 遍历解析
                List<TokenSign> tokenSignList = session.tokenSignListCopy();
                for (TokenSign tokenSign : tokenSignList) {
                    if (tokenSign.getValue().equals(token)) {
                        mobile = tokenSign.getDevice();
                        break;
                    }
                }
            }
            // 每个用户id可以多次登录, 也可以在不同平台登录
            LoginTempInfo tempInfo = new LoginTempInfo(account, username, token, mobile, loginTimeout);
            // 同一个用户如果多端登录需要同时记录不同平台的时限
            list.add(tempInfo);
        });
        return new PageVo<>(pageBo, list);
    }

    /**
     * 判断用户名是否存在
     */
    public boolean checkUserExist(String username) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>().eq(User::getUsername, username);
        return userMapper.exists(lqw);
    }
}
