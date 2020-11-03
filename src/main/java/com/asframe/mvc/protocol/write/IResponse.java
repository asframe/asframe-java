package com.asframe.mvc.protocol.write;

import com.asframe.mvc.protocol.IHttpProtocol;

/**
 * 协议的基础接口，定义了请求和发送的基础结构
 *
 * @author sodaChen
 * @date 2018-10-1
 */
public interface IResponse extends IHttpProtocol {
    /**
     * 此次通讯对象的编码，告诉前端通讯时成功还是失败
     *
     * @param code
     */
    void setCode(short code);

    /**
     * 获取编码结果
     *
     * @return
     */
    short getCode();
}
