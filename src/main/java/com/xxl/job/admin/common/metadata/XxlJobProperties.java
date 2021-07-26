package com.xxl.job.admin.common.metadata;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p> @date: 2021-07-26 14:04</p>
 *
 * @author lesible
 */
@Component
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    private final TriggerPool triggerPool = new TriggerPool();
    private String accessToken = "''";
    private String i18n = "zh_CN";
    private Integer logRetentionDays = 30;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }

    public Integer getLogRetentionDays() {
        return logRetentionDays;
    }

    public void setLogRetentionDays(Integer logRetentionDays) {
        this.logRetentionDays = logRetentionDays;
    }

    public TriggerPool getTriggerPool() {
        return triggerPool;
    }

    public static class TriggerPool {

        private Integer fastMax = 100;
        private Integer slowMax = 200;

        public Integer getFastMax() {
            return fastMax;
        }

        public void setFastMax(Integer fastMax) {
            this.fastMax = fastMax;
        }

        public Integer getSlowMax() {
            return slowMax;
        }

        public void setSlowMax(Integer slowMax) {
            this.slowMax = slowMax;
        }
    }
}

