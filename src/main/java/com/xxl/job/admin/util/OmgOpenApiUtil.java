package com.xxl.job.admin.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxl.job.admin.api.OmgApi;
import com.xxl.job.admin.common.metadata.OmgProperties;
import com.xxl.job.admin.core.model.OmgResult;
import com.xxl.job.admin.core.model.OmgRobotRequest;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p> @date: 2021-06-01 11:10</p>
 *
 * @author 何嘉豪
 */
@Component
public class OmgOpenApiUtil {

    public static String API_SECRET;

    private OmgApi omgApi;

    @Resource
    private OmgProperties omgProperties;

    public static void initApiSecret(String apiSecret) {
        if (API_SECRET == null) {
            API_SECRET = apiSecret;
        } else {
            throw new IllegalStateException("api secret 已初始化");
        }
    }

    public static String signBody(Object body) throws Exception {
        Class<?> clazz = body.getClass();
        Set<String> fieldSet = Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).
                sorted().collect(Collectors.toCollection(LinkedHashSet::new));
        StringBuilder sb = new StringBuilder();
        for (String field : fieldSet) {
            if ("sign".equals(field)) {
                continue;
            }
            if ("log".equals(field)) {
                continue;
            }
            PropertyDescriptor descriptor = new PropertyDescriptor(field, clazz);
            Object invoke = descriptor.getReadMethod().invoke(body);
            if (invoke != null && StringUtils.hasLength(invoke.toString())) {
                sb.append(field).append("=").append(invoke.toString().trim()).append("&");
            }
        }
        sb.append("key=").append(API_SECRET);
        return DigestUtils.md5Hex(sb.toString()).toUpperCase();
    }

    @PostConstruct
    private void init() {
        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String apiSecret = omgProperties.getApiSecret();
        initApiSecret(apiSecret);
        ConnectionPool connectionPool = new ConnectionPool(50, 30, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(Duration.ofSeconds(30))
                .connectTimeout(Duration.ofMillis(500))
                .retryOnConnectionFailure(false)
                .connectionPool(connectionPool)
                .build();
        String baseUrl = omgProperties.getBaseUrl();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create(om)).build();
        omgApi = retrofit.create(OmgApi.class);
    }

    public Optional<OmgResult<Void>> sendMsg2WechatRobot(OmgRobotRequest robotRequest) {
        Call<OmgResult<Void>> omgResultCall = omgApi.sendText2Robot(robotRequest);
        try {
            return Optional.ofNullable(omgResultCall.execute().body());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
