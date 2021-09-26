package com.xxl.job.admin;

import com.xxl.job.admin.core.model.OmgResult;
import com.xxl.job.admin.core.model.OmgRobotRequest;
import com.xxl.job.admin.core.model.SmsRequest;
import com.xxl.job.admin.core.model.SmsResult;
import com.xxl.job.admin.util.OmgOpenApiUtil;
import com.xxl.job.admin.util.SmsUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <p> @date: 2021-07-26 16:59</p>
 *
 * @author lesible
 */
@SpringBootTest
public class ApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(ApplicationTests.class);

    @Resource
    private SmsUtil smsUtil;

    @Resource
    private OmgOpenApiUtil omgOpenApiUtil;

    @Test
    public void sendMsg() throws Exception {
        String phone = "17788501480";
        String content = "小主你好,这是 xxlJob 发给你的短信请查收";
        SmsResult smsResult = smsUtil.sendSms(SmsRequest.builder(phone, content).build());
        log.info("smsResult: {}", smsResult);
        if (!smsResult.isSuccess()) {
            throw new Exception("发送失败咯");
        }
    }

    @Test
    public void sendRobotUrl() throws Exception {
        String wxRobotUrl = "https://open.feishu.cn/open-apis/bot/v2/hook/d1d884f9-a021-4e1e-ae08-7cfb26698be6";
        String msg = "错了错了错了 我又双叒叕报错了 ððð";
        OmgRobotRequest build = OmgRobotRequest.builder(msg, wxRobotUrl).build();
        Optional<OmgResult<Void>> voidOmgResult = omgOpenApiUtil.sendMsg2WechatRobot(build);
        log.info("voidOmgResult: {}", voidOmgResult);
    }

}
