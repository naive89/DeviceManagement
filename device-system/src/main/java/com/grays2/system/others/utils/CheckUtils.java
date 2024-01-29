package com.grays2.system.others.utils;


import cn.hutool.core.util.StrUtil;

import com.grays2.common.utils.StringUtils;
import com.grays2.system.domain.User;
import com.grays2.system.domain.bo.UserBo;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


@Service
public class CheckUtils {
    //定义特殊字符
    public static final String SPECIAL_CHAR = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    public static String checkUser(User user) {
        String error;
        String username = user.getUsername();
        //检查用户名
        error = checkUsername(username);
        if (StringUtils.isNotEmpty(error))
            return error;
        //检查密码
        String pwd = user.getPassword();
        error = checkPwd(pwd);
        if (StringUtils.isNotEmpty(error))
            return error;
        //校验手机号
        error = isMobile(user.getMobile());
        if (StringUtils.isNotEmpty(error)) {
            return error;
        }
        //校验邮箱
        error = isEmail(user.getEmail());
        if (StringUtils.isNotEmpty(error)) {
            return error;
        }
        error = isChineseName(user.getName());
        if (StringUtils.isNotEmpty(error)) {
            return error;
        }
        return "" ;
    }

    public static String checkUserBo(UserBo user) {
        String error;
        //检查用户名
        error = checkUsername(user.getUsername());
        if (StringUtils.isNotEmpty(error))
            return error;
        //检查密码
        error = checkPwd(user.getPassword());
        if (StringUtils.isNotEmpty(error))
            return error;
        //校验手机号
        error = isMobile(user.getMobile());
        if (StringUtils.isNotEmpty(error)) {
            return error;
        }
        //校验邮箱
        error = isEmail(user.getEmail());
        if (StringUtils.isNotEmpty(error)) {
            return error;
        }
        error = isChineseName(user.getName());
        if (StringUtils.isNotEmpty(error)) {
            return error;
        }
        return "" ;
    }

    /**
     * 中文名字正则
     * true 格式正确
     */
    public static String isChineseName(String name) {
        if (StringUtils.isEmpty(name)) return "姓名不能为空" ;
        return !name.matches("^([\\u4e00-\\u9fa5]{1,20}|[a-zA-Z.\\s]{1,20})$") ? "姓名不支持中英文混合，且长度在20个字符以内" : null;
    }

    /**
     * 手机号码正则 验证手机号码格式
     * true 格式正确
     */
    public static String isMobile(String mobile) {
        if (mobile.length() != 11 || StringUtils.isEmpty(mobile)) return "手机号码长度需要为11位" ;
        return !Pattern.compile("^((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[56])|(17[0-8])|(18[0-9])|(19[1589]))\\d{8}$", Pattern.CASE_INSENSITIVE).matcher(mobile).matches() ? "手机号码格式不正确" : null;
    }

    /**
     * 邮箱正则 验证邮箱格式
     * true 格式正确
     */
    public static String isEmail(String email) {
        if (StringUtils.isEmpty(email)) return "邮箱不能为空" ;
        return !Pattern.compile("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", Pattern.CASE_INSENSITIVE).matcher(email).matches() ? "邮箱格式不正确" : null;
    }


    /**
     * 检查密码
     */
    private static String checkPwd(String pwd) {
        if (!checkPasswordLength(pwd, "8", null))
            return "密码必须大于8位" ;
        if (!checkContainCase(pwd)
                || !checkContainDigit(pwd)
                || !checkContainSpecialChar(pwd)
                || !checkContainLowerCase(pwd)
                || !checkContainUpperCase(pwd))
            return "密码必须包含数字、大小写字母、特殊符号" ;
        return "" ;
    }


    /**
     * 检测密码中字符长度
     */
    public static boolean checkPasswordLength(String password, String minNum, String maxNum) {
        boolean flag = false;
        if (StrUtil.isBlank(maxNum)) {
            minNum = StrUtil.isBlank(minNum) ? "0" : minNum;
            if (password.length() >= Integer.parseInt(minNum)) {
                flag = true;
            }
        } else {
            minNum = StrUtil.isBlank(minNum) ? "0" : minNum;
            if (password.length() >= Integer.parseInt(minNum) &&
                    password.length() <= Integer.parseInt(maxNum)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测密码中是否包含数字
     */
    public static boolean checkContainDigit(String password) {
        char[] chPass = password.toCharArray();
        for (char pass : chPass) {
            if (Character.isDigit(pass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测密码中是否包含字母（不区分大小写）
     */
    public static boolean checkContainCase(String password) {
        char[] chPass = password.toCharArray();
        for (char pass : chPass) {
            if (isLetter(pass)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检测密码中是否包含小写字母
     */
    public static boolean checkContainLowerCase(String password) {
        char[] chPass = password.toCharArray();
        for (char pass : chPass) {
            if (isLowerCase(pass)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检测密码中是否包含大写字母
     */
    public static boolean checkContainUpperCase(String password) {
        char[] chPass = password.toCharArray();
        for (char pass : chPass) {
            if (isUpperCase(pass)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检测密码中是否包含特殊符号
     */
    public static boolean checkContainSpecialChar(String password) {
        char[] chPass = password.toCharArray();
        for (char pass : chPass) {
            if (SPECIAL_CHAR.indexOf(pass) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查用户名
     */

    private static String checkUsername(String username) {
        if (!length(username)) {
            return "用户名的长度应为8到20个字符" ;
        }
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if (!isLetter(c) && !isnumber(c)) {
                return "用户名必须有数字或字母组成" ;
            }
            if (i == 0 && !isLowerCase(c)) {
                return "用户名需要以小写字母开头" ;
            }
        }
        return "" ;
    }

    /**
     * 数字校验
     */
    private static boolean isnumber(char s) {
        return s >= '0' && s <= '9';
    }

    /**
     * 字母校验
     */
    private static boolean isLetter(char s) {
        return isLowerCase(s) || isUpperCase(s);
    }

    /**
     * 大写字母校验
     */
    private static boolean isUpperCase(char s) {
        return s >= 'A' && s <= 'Z';
    }

    /**
     * 小写字母校验
     */
    private static boolean isLowerCase(char s) {
        //加单引号 'a' 代表字母  不用转成数字
        return s >= 'a' && s <= 'z';
    }

    /**
     * 长度校验
     */
    private static boolean length(String s) {
        return s.length() >= 8 && s.length() <= 20;
    }
}
