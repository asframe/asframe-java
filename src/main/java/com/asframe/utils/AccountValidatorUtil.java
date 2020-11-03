package com.asframe.utils;
import java.util.regex.Pattern;

/**
 * 账号相关验证方法
 * @Auther: sodaChen
 * @Date: 2018/10/3
 * @Description:
 */
public class AccountValidatorUtil {
    /**
     * 正则表达式：验证用户名 账号必须是6-18位字母与数字组合
     */
    public static final String REGEX_USERNAME = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
    /**
     * 正则表达式：手机号登陆 （只进行数字判断  6-18）
     */
    public static final String REGEX_NUMBER = "^[0-9]{6,18}$";

    /**
     * 正则表达式：验证密码 密码必须包括字母和数字，6-20字符
     */
    public static final String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证QQ
     */
    public static final String REGEX_QQ = "[1-9][0-9]{4,10}";
    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }
    /**
     * 校验手机号登陆
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isNumber(String username) {
        return Pattern.matches(REGEX_NUMBER, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验邮箱或QQ
     */
    public static boolean isEmailOrQQ(String qqOremail) {
        if(Pattern.matches(REGEX_EMAIL, qqOremail) || Pattern.matches(REGEX_QQ, qqOremail)){
            return true;
        }
        return false;
    }
    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }
}
