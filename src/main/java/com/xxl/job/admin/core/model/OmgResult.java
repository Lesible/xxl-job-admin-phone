package com.xxl.job.admin.core.model;

/**
 * <p> @date: 2021-06-01 11:01</p>
 *
 * @author 何嘉豪
 */
public class OmgResult<T> {

    private Boolean success;

    private Integer code;

    private String message;

    private T data;

    @Override
    public String toString() {
        return "OmgResult{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
