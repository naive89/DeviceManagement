package com.grays2.system.service.impl;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.grays2.common.constant.ActivationConstant;
import com.grays2.common.constant.RedisConstant;
import com.grays2.common.constant.SystemConstant;
import com.grays2.common.redis.RedisUtils;
import com.grays2.common.result.R;
import com.grays2.common.utils.IPUtils;
import com.grays2.common.utils.StringUtils;
import com.grays2.system.domain.Login;
import com.grays2.system.domain.User;
import com.grays2.system.domain.bo.LoginBo;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.bo.UserBo;
import com.grays2.system.domain.model.LoginTempInfo;
import com.grays2.system.domain.vo.LoginVo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.mapper.LoginMapper;
import com.grays2.system.mapper.UserMapper;
import com.grays2.system.service.LoginService;
import com.grays2.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 登录方法
     */
    @Override
    public R<Object> doLogin(LoginBo bo) {
        String account = bo.getAccount();
        if (StringUtils.isEmpty(account)) return R.warn("账号不能为空");
        String pwd = bo.getPassword();
        if (StringUtils.isEmpty(pwd)) return R.warn("密码不能为空");
        String ip = IPUtils.getIp(request);
        if (redisUtils.noHasKey(RedisConstant.code + ":" + ip)) return R.warn("验证码已经过期");
        User user = userMapper.getUserByAccount(account);
        if (StringUtils.isEmpty(user)) {
            return R.fail("账号不存在");
        }
        if (SystemConstant.USER_STATUS_DISABLE == user.getStatus()) {
            return R.fail("账号已被禁用，请联系管理员解封");
        }
        int errorNum = 0;
        String key = SystemConstant.userStatus + ":" + account;
        if (redisUtils.hasKey(key)) {
            errorNum = (int) redisUtils.get(key);
        }
        if (errorNum >= SystemConstant.passwordErrorNum) return R.fail("此账号因密码错误次数太对，已经被暂时禁用。");
        // 验证密码是否正确
        String sha256 = SaSecureUtil.sha256(pwd + user.getSalt());//（用户输入的密码+盐值）加密
        String password = user.getPassword();//真正的密码（加密的）
        if (!sha256.equals(password)) {
            long time = SystemConstant.passwordErrorNum_Time;
            redisUtils.set(key, errorNum + 1, time);
            return R.fail("密码错误");
        }
        checkLoginIsFirst(user.getUserId(), user.getAccount());

        Login login = new Login();
        //账号密码验证成功，添加登录记录
        login.setUsername(user.getUsername());
        login.setAccount(account);
        login.setTime(LocalDateTime.now());
        login.setIp(ip);
        String innerIp = IPUtils.innerIp(ip);
        login.setInnerIp(innerIp);
        loginMapper.insert(login);
        if (errorNum > 0) redisUtils.del(key);
        SaLoginModel saLoginModel = new SaLoginModel().setDevice("");
        StpUtil.login(account, saLoginModel);
        String token = StpUtil.getTokenValue();
        String tokenName = SaManager.getConfig().getTokenName();
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("tokenName", tokenName);
        map.put("username", user.getUsername());
        return R.ok("登录成功", map);
    }

    /**
     * 检查用户是否为今日第一次登录
     */
    @Async
    public void checkLoginIsFirst(Integer userId, String account) {
        LambdaQueryWrapper<Login> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Login::getAccount, account);
        String time = LocalDateTimeUtil.formatNormal(LocalDateTime.now()).replace(":", "-").split(" ")[0];
        lqw.like(Login::getTime, time);
    }


    @Override
    public R<String> doRegister(UserBo bo) {
        return userService.insert(bo);
    }
    @Override
    public List<LoginVo> getListIndex() {
        String account = (String) StpUtil.getLoginId();
        return loginMapper.getListByAccount(account);
    }

    @Override
    public PageVo<LoginVo> getList(PageBo pageBo) {
        List<LoginVo> list = this.loginMapper.getList();
        return new PageVo<>(pageBo,list);
    }

}
