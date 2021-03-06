package com.xxl.job.admin.core.alarm;

import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import org.springframework.lang.NonNull;

/**
 * @author xuxueli 2020-01-19
 */
public interface JobAlarm {

    Integer getAlarmType();

    boolean doAlarm(@NonNull XxlJobInfo info, XxlJobLog jobLog);

    void doAlarm(XxlJobInfo xxlJobInfo, String msg);
}
