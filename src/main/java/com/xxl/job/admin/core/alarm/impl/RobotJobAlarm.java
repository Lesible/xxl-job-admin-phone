package com.xxl.job.admin.core.alarm.impl;

import com.xxl.job.admin.core.alarm.JobAlarm;
import com.xxl.job.admin.core.model.OmgResult;
import com.xxl.job.admin.core.model.OmgRobotRequest;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.util.OmgOpenApiUtil;
import com.xxl.job.core.context.XxlJobContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <p>  </p>
 * <p> create 2021-09-26 17:34 by lesible </p>
 *
 * @author 何嘉豪
 */
@Component
public class RobotJobAlarm implements JobAlarm {
    private static final Logger log = LoggerFactory.getLogger(SmsJobAlarm.class);

    @Resource
    private OmgOpenApiUtil omgOpenApiUtil;

    @Override
    public Integer getAlarmType() {
        return 0;
    }

    @Override
    public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog) {
        // 不是机器人类型 直接返回
        if (info.getAlarmType() != 0) {
            return true;
        }
        // log 不为空,并且是执行结果是失败的,进入告警
        if (jobLog != null && XxlJobContext.HANDLE_COCE_SUCCESS != jobLog.getHandleCode()) {
            // 获取手机号,如果手机号不为空,则发送短信
            int triggerCode = jobLog.getTriggerCode();
            String robotUrl = info.getAlarmAddress();
            if (StringUtils.hasText(robotUrl)) {
                // 如果有多个,全部都失败才认为通知失败
                boolean allFail = true;
                String errorType;
                String errorMsg;
                if (triggerCode == 500) {
                    errorType = "调度失败";
                    errorMsg = jobLog.getTriggerMsg().replaceAll("<br>", "\n");
                } else {
                    errorType = "执行失败";
                    errorMsg = jobLog.getHandleMsg();
                }
                for (String robotAddress : robotUrl.split(",")) {
                    Optional<OmgResult<Void>> omgResult = omgOpenApiUtil.sendMsg2WechatRobot(OmgRobotRequest.builder(
                            String.format("任务名:[%s]%s%n错误信息:%n%s%n", info.getJobDesc(), errorType, errorMsg)
                            , robotAddress).build());
                    if (omgResult.isPresent() && omgResult.get().getSuccess() != null && omgResult.get().getSuccess()) {
                        allFail = false;
                    } else {
                        log.error("发送到机器人错误");
                    }
                }
                if (allFail) {
                    log.error("发送到机器人错误");
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void doAlarm(XxlJobInfo xxlJobInfo, String msg) {
        String robotUrl = xxlJobInfo.getAlarmAddress();
        if (StringUtils.hasText(robotUrl)) {
            for (String robotAddress : robotUrl.split(",")) {
                omgOpenApiUtil.sendMsg2WechatRobot(OmgRobotRequest
                        .builder(xxlJobInfo.getJobDesc() + ":" + msg, robotAddress).build());
            }
        }
    }
}
