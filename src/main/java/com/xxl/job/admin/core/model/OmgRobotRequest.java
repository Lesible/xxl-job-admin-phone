package com.xxl.job.admin.core.model;

import com.xxl.job.admin.util.OmgOpenApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * <p> @date: 2021-06-01 11:04</p>
 *
 * @author 何嘉豪
 */
public class OmgRobotRequest {

    /**
     * 文本信息
     */
    private final String text;
    /**
     * &#064;手机号对应的人
     */
    private final List<String> mentionedMobileList;
    /**
     * 微信机器人地址
     */
    private final String wxChatBotUrl;
    /**
     * 签名
     */
    private final String sign;
    /**
     * 时间
     */
    private final Long time;

    private OmgRobotRequest(Builder builder) {
        this.text = builder.text;
        this.mentionedMobileList = builder.mentionedMobileList;
        this.wxChatBotUrl = builder.wxChatBotUrl;
        this.sign = builder.sign;
        this.time = builder.time;
    }

    public static Builder builder(String text, String wxChatBotUrl) {
        return new Builder(text, wxChatBotUrl);
    }

    public String getText() {
        return text;
    }

    public List<String> getMentionedMobileList() {
        return mentionedMobileList;
    }

    public String getWxChatBotUrl() {
        return wxChatBotUrl;
    }

    public String getSign() {
        return sign;
    }

    public Long getTime() {
        return time;
    }

    public static class Builder {

        private static final Logger log = LoggerFactory.getLogger(Builder.class);
        private String text;
        private Boolean isAtAll;
        private List<String> mentionedMobileList;
        private String wxChatBotUrl;
        private String sign;
        private Long time;

        public Builder(String text, String wxChatBotUrl) {
            this.text = text;
            this.wxChatBotUrl = wxChatBotUrl;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Boolean isIsAtAll() {
            return isAtAll;
        }

        public void setIsAtAll(Boolean atAll) {
            isAtAll = atAll;
        }

        public List<String> getMentionedMobileList() {
            return mentionedMobileList;
        }

        public void setMentionedMobileList(List<String> mentionedMobileList) {
            this.mentionedMobileList = mentionedMobileList;
        }

        public String getWxChatBotUrl() {
            return wxChatBotUrl;
        }

        public void setWxChatBotUrl(String wxChatBotUrl) {
            this.wxChatBotUrl = wxChatBotUrl;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public Builder isAtAll(Boolean isAtAll) {
            this.isAtAll = isAtAll;
            return this;
        }

        public Builder wxChatBotUrl(String wxChatBotUrl) {
            this.wxChatBotUrl = wxChatBotUrl;
            return this;
        }

        public Builder mentionedMobileList(String... mentionedMobiles) {
            this.mentionedMobileList = Arrays.asList(mentionedMobiles);
            return this;
        }

        public OmgRobotRequest build() {
            if (!StringUtils.hasLength(text)) {
                throw new IllegalArgumentException("信息内容不能为空");
            }
            if (!StringUtils.hasLength(wxChatBotUrl)) {
                throw new IllegalArgumentException("微信群机器人地址不能为空");
            }
            this.time = System.currentTimeMillis();
            try {
                this.sign = OmgOpenApiUtil.signBody(this);
            } catch (Exception e) {
                log.error("签名失败", e);
            }
            return new OmgRobotRequest(this);
        }
    }

}
