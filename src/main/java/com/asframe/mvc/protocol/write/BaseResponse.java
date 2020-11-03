package com.asframe.mvc.protocol.write;

import com.asframe.server.protocol.BaseProtocol;

/**-
 *
 * @author sodaChen
 */
public abstract class BaseResponse extends BaseProtocol implements IResponse {
    /**
     * 协议结果编码
     */
    protected short code;

    /**
     * 此次通讯对象的编码，告诉前端通讯时成功还是失败
     *
     * @param code
     */
    @Override
    public void setCode(short code) {
        this.code = code;
    }

    /**
     * 获取编码结果
     *
     * @return
     */
    @Override
    public short getCode() {
        return this.code;
    }
}
