package com.xxl.job.admin.core.alarm.impl;

import com.xxl.job.admin.core.alarm.JobAlarm;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import org.springframework.stereotype.Component;

/**
 * <p> @date: 2021-07-26 14:59</p>
 *
 * @author lesible
 */
@Component
public class SmsJobAlarm implements JobAlarm {

    @Override
    public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog) {
        boolean alarmResult = true;
        String phoneNum = info.getPhoneNum();
        return false;
    }
}
