package com.asframe.platform.wechat;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shizq
 * @version 1.0 微信用户信息
 * @date 2020-09-11
 * @remark https://developers.weixin.qq.com/miniprogram/dev/api/open-api/user-info/wx.getUserInfo.html
 */
public class WechatUserInfoBean {
    @JSONField(name = "city")
    private String city;
    @JSONField(name = "country")
    private String country;
    @JSONField(name = "province")
    private String province;
    @JSONField(name = "openId")
    private String openId;
    @JSONField(name = "gender")
    private Integer gender;
    @JSONField(name = "nickName")
    private String nickName;
    @JSONField(name = "avatarUrl")
    private String avatarUrl;
    @JSONField(name = "unionId")
    private String unionId;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    @Override
    public String toString() {
        return "WechatUserInfoBean{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", openId='" + openId + '\'' +
                ", gender=" + gender +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", unionId='" + unionId + '\'' +
                '}';
    }
}

