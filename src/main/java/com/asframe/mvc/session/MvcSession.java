/**
 * @BaseMvcSession.java
 * @author sodaChen  E-mail:asframe@qq.com
 * @version 1.0
 * <br>Copyright (C)
 * <br>This program is protected by copyright laws.
 * <br>Program Name:KeepArea
 * <br>Date:2019/11/24
 */
package com.asframe.mvc.session;

import com.asframe.mvc.protocol.read.BaseHttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现基础绑定modelmap的基础功能
 *
 * @author sodaChen
 * @version 1.0
 * <br>Date:2020/10/7
 */
public class MvcSession implements IMvcSession {


    private static Logger logger = LogManager.getLogger(MvcSession.class);
    protected HttpServletRequest httpRequest;


    /**
     * token和账号id绑定
     */
    private static Map<String, String> tokenMap = new ConcurrentHashMap<>();
    /**
     * uid和token绑定
     */
    private static Map<String, String> uuidMap = new ConcurrentHashMap<>();

    public MvcSession() {

    }

    /**
     * 先临时用静态方法去调用获取uuid
     *
     * @param token
     * @return
     */
    public static String getSessionUUid(String token) {
//        String uuid = redisUtil.get(TokenRedisUtil.getCacheTokenUuid(token)) + "";
//        logger.info(token + "从redis获取uuid:" + uuid);
//        if (StringUtils.isNotBlank(uuid) && !"null".equals(uuid)) {// 证明该token依旧在有效期内
//            if (StringUtils.isBlank(tokenMap.get(token))) {//该token在有效期，但是服务重启导致map刷新，获取不到uuid
//                tokenMap.put(token, uuid);
//                setSessionUUid(token, uuid);
//            }
//        } else {
//            return null;
//        }
//        TokenRedisUtil.renewTokenRedis(token);
        return "";
    }

    public static void setSessionUUid(String token, String uuid) {
        //先删除掉原来的旧token
        if (uuidMap.containsKey(uuid)) {
            String oldToken = uuidMap.remove(uuid);
            if (oldToken != null) {
                tokenMap.remove(oldToken);
            }
        }
        logger.info(token + " uuid:" + uuid + "放到本地缓存中");
        tokenMap.put(token, uuid);
        //反向绑定
        uuidMap.put(uuid, token);
    }


    @Override
    public HttpSession getHttpSession() {
        return this.httpRequest.getSession();
    }

    /**
     * 获取token
     *
     * @return
     */
    @Override
    public String getToken() {
        return getHttpRequest().getParameter("token");

    }

    @Override
    public String getToken(BaseHttpRequest baseRequest) {
        if (getHttpRequest().getParameter("token") == null) {
            return baseRequest.getToken();
        }
        return getHttpRequest().getParameter("token");

    }

    /**
     * 返回账号的唯一id
     *
     * @return
     */
    @Override
    public String getUuid(String token) {
        return getSessionUUid(token);
//        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
//        String uuid = redisUtil.get(SmsInfoUtil.REDIS_TOKEN + CACHETOKEN_UUID + token) + "";
//        if (StringUtils.isNotBlank(uuid) && !"null".equals(uuid)) {// 证明该token依旧在有效期内
//            if (StringUtils.isBlank(tokenMap.get(token))) {//该token在有效期，但是服务重启导致map刷新，获取不到uuid
//                tokenMap.put(token, uuid);
//                setUuid(token, uuid);
//            }
//        }
//        return tokenMap.get(token);
    }

    @Override
    public String getUuid() {
        String token = this.getToken();
        if (token == null) {
            return "";
        }
        return getSessionUUid(token);
//        return tokenMap.get(token);
    }

    @Override
    public String getUuid(BaseHttpRequest baseRequest) {
        String token = this.getToken(baseRequest);
        if (token == null) {
            return "";
        }
        return getSessionUUid(token);
//        return tokenMap.get(token);
    }

    @Override
    public void setUuid(String token, String uuid) {
        setSessionUUid(token, uuid);
//        //先删除掉原来的旧token
//        if(uuidMap.containsKey(uuid))
//        {
//            String oldToken = uuidMap.remove(uuid);
//            if(oldToken != null)
//            {
//                tokenMap.remove(oldToken);
//            }
//        }
//        tokenMap.put(token,uuid);
//        //反向绑定
//        uuidMap.put(uuid,token);
    }

    @Override
    public void loginOut() {

    }

    public static void removeToken(String token) {
        logger.info("--------------removeToken----" + token);
        String uuid = tokenMap.get(token);
        if (StringUtils.isNotBlank(uuid)) {
            tokenMap.remove(token);
            tokenMap.remove(uuid);
//            redisUtil.del(TokenRedisUtil.getRedisToken(TokenRedisUtil.getCacheTokenKey(token)));
//            redisUtil.del(TokenRedisUtil.getRedisToken(token));
//            redisUtil.del(TokenRedisUtil.getCacheTokenUuid(token));
//            redisUtil.del(TokenRedisUtil.getCacheTokenKey(token));
        }
    }

    /**
     * 暂时是封装这个，其实是更加复杂的数据模型才对
     *
     * @param httpRequest
     */
    @Override
    public void init(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    /**
     * @return
     */
    @Override
    public HttpServletRequest getHttpRequest() {
        return this.httpRequest;
    }
}
