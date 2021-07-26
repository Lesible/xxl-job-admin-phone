package com.xxl.job.admin.core.alarm.impl;

import com.xxl.job.admin.core.alarm.JobAlarm;
import com.xxl.job.admin.core.model.SmsRequest;
import com.xxl.job.admin.core.model.SmsResult;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.util.SmsUtil;
import com.xxl.job.core.context.XxlJobContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p> @date: 2021-07-26 14:59</p>
 *
 * @author lesible
 */
@Component
public class SmsJobAlarm implements JobAlarm {

    private static final Logger log = LoggerFactory.getLogger(SmsJobAlarm.class);

    @Resource
    private SmsUtil smsUtil;

    @Override
    public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog) {
        // log 不为空,并且是执行结果是失败的,进入告警
        if (jobLog != null && XxlJobContext.HANDLE_COCE_SUCCESS != jobLog.getHandleCode()) {
            // 获取手机号,如果手机号不为空,则发送短信
            String phoneNum = info.getPhoneNum();
            if (StringUtils.hasText(phoneNum)) {
                // 如果有多个,全部都失败才认为通知失败
                boolean allFail = true;
                for (String phone : phoneNum.split(",")) {
                    SmsResult smsResult = smsUtil.sendSms(SmsRequest.builder(phoneNum, jobLog.getHandleMsg()).build());
                    if (smsResult != null && smsResult.isSuccess()) {
                        allFail = false;
                    } else {
                        log.error("发送短信给 {} 失败", phone);
                    }
                }
                if (allFail) {
                    log.error("发送短信失败");
                    return false;
                }
            }
        }
        return true;
    }
}
