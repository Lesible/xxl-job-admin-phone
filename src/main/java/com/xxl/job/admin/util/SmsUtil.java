package com.xxl.job.admin.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.xxl.job.admin.api.SmsApi;
import com.xxl.job.admin.core.model.SmsRequest;
import com.xxl.job.admin.core.model.SmsResult;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * <p> @date: 2021-04-16 14:40</p>
 *
 * @author 何嘉豪
 */
@Component
@ConfigurationProperties("sms")
public class SmsUtil {

    private static final Logger log = LoggerFactory.getLogger(SmsUtil.class);
    private final static String IV_PARAMETER = "59463135";
    private static final String KEY = "ASBUE23s123576ds4ESG123Drge";
    private static final String ALGORITHM = "DES";
    private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";
    private String baseUrl;
    private String username;
    private String password;
    private SmsApi smsApi;

    private static String encrypt(String data) {
        if (data == null)
            return null;
        try {
            Key secretKey = generateKey();
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] bytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(bytes));
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    private static Key generateKey() throws Exception {
        DESKeySpec dks = new DESKeySpec(KEY.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(dks);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

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

    @PostConstruct
    public void initValue() {
        if (!StringUtils.hasLength(baseUrl)) {
            throw new IllegalArgumentException("短信平台地址不能为空");
        }
        String httpUrl = UriComponentsBuilder.fromHttpUrl(baseUrl).toUriString();
        ConnectionPool connectionPool = new ConnectionPool(100, 30, TimeUnit.SECONDS);
        // 配置 okhttpClient
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(Duration.ofSeconds(30))
                .connectTimeout(Duration.ofMillis(500))
                .retryOnConnectionFailure(false)
                .connectionPool(connectionPool)
//                .addInterceptor(interceptor)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(httpUrl)
                .client(okHttpClient).addConverterFactory(JacksonConverterFactory
                        .create(objectMapper)).build();
        smsApi = retrofit.create(SmsApi.class);
    }

    public SmsResult sendSms(SmsRequest smsRequest) {
        if (!StringUtils.hasText(smsRequest.getSign())) {
            smsRequest.setSign("星麦云商");
        }
        smsRequest.setUsername(username);
        smsRequest.setPassword(password);
        try {
            Call<SmsResult> smsResultCall = smsApi.sendSms(new SmsRequestWrapper(smsRequest));
            return smsResultCall.execute().body();
        } catch (IOException e) {
            log.error("发送短信失败", e);
            return null;
        }
    }

    public static class SmsRequestWrapper {

        private final String entity;

        public SmsRequestWrapper(SmsRequest smsRequest) {
            this.entity = encrypt(JsonUtil.jsonValue(smsRequest));
        }

        public String getEntity() {
            return entity;
        }

        @Override
        public String toString() {
            return "SmsRequestWrapper{" +
                    "entity='" + entity + '\'' +
                    '}';
        }
    }

}
