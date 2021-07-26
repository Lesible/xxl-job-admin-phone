package com.xxl.job.admin;

import com.xxl.job.admin.core.model.SmsRequest;
import com.xxl.job.admin.core.model.SmsResult;
import com.xxl.job.admin.util.SmsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * <p> @date: 2021-07-26 16:59</p>
 *
 * @author lesible
 */
@SpringBootTest
public class ApplicationTests {

    @Resource
    private SmsUtil smsUtil;

    @Test
    public void sendMsg() throws Exception {
        String phone = "17788501480";
        String content = "小主你好,这是 xxlJob 发给你的短信请查收";
        SmsResult smsResult = smsUtil.sendSms(SmsRequest.builder(phone, content).build());
        if (!smsResult.isSuccess()) {
            throw new Exception("发送失败咯");
        }
    }

}
