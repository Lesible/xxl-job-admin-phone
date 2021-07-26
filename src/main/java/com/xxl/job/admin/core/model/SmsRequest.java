package com.xxl.job.admin.core.model;

/**
 * <p> @date: 2021-04-16 15:11</p>
 *
 * @author 何嘉豪
 */
public class SmsRequest {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCompanyNum() {
        return companyNum;
    }

    public void setCompanyNum(Long companyNum) {
        this.companyNum = companyNum;
    }

    public Integer getBsType() {
        return bsType;
    }

    public void setBsType(Integer bsType) {
        this.bsType = bsType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSa() {
        return sa;
    }

    public void setSa(String sa) {
        this.sa = sa;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 公司号
     */
    private Long companyNum;

    /**
     * 业务类型
     */
    private Integer bsType;

    /**
     * 类型 0 营销类 1 通知类
     */
    private Integer type;

    /**
     * 手机号
     */
    private String phoneNum;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 短信签名
     */
    private String sign;

    /**
     * 公司额外码
     */
    private String sa;

    /**
     * 平台类型 0 星麦
     */
    private Integer platformType;

    /**
     * 事务 id
     */
    private String transactionId;

}
