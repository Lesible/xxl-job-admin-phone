package com.xxl.job.admin.core.model;

/**
 * <p> @date: 2021-04-16 16:12</p>
 *
 * @author 何嘉豪
 */
public class SmsResult {
    @Override
    public String toString() {
        return "SmsResult{" +
                "success=" + success +
                ", num=" + num +
                ", id='" + id + '\'' +
                ", smsUsername='" + smsUsername + '\'' +
                ", platform='" + platform + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    private boolean success;

    private Integer num;

    private String id;

    private String smsUsername;

    private String platform;

    private String code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmsUsername() {
        return smsUsername;
    }

    public void setSmsUsername(String smsUsername) {
        this.smsUsername = smsUsername;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
