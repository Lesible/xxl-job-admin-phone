package com.xxl.job.admin.common.metadata;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p> @date: 2021-06-01 11:13</p>
 *
 * @author 何嘉豪
 */
@Component
@ConfigurationProperties(prefix = "omg")
public class OmgProperties {

    private String baseUrl;

    private String apiSecret;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

}
