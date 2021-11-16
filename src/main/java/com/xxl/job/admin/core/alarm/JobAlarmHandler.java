package com.xxl.job.admin.core.alarm;

import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class JobAlarmHandler implements ApplicationContextAware, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(JobAlarmHandler.class);

    private ApplicationContext applicationContext;
    private List<JobAlarm> jobAlarmList;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, JobAlarm> serviceBeanMap = applicationContext.getBeansOfType(JobAlarm.class);
        if (serviceBeanMap.size() > 0) {
            jobAlarmList = new ArrayList<>(serviceBeanMap.values());
        }
    }


    public boolean alarm(@NonNull XxlJobInfo info, XxlJobLog jobLog) {
        boolean result = false;
        if (jobAlarmList != null && jobAlarmList.size() > 0) {
            result = true;  // success means all-success
            for (JobAlarm alarm : jobAlarmList) {
                boolean resultItem = false;
                try {
                    resultItem = alarm.doAlarm(info, jobLog);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                if (!resultItem) {
                    result = false;
                }
            }
        }
        return result;
    }

    public void alarm(XxlJobInfo xxlJobInfo, String msg) {
        if (jobAlarmList != null && jobAlarmList.size() > 0) {
            for (JobAlarm jobAlarm : jobAlarmList) {
                if (Objects.equals(jobAlarm.getAlarmType(), xxlJobInfo.getAlarmType())) {
                    jobAlarm.doAlarm(xxlJobInfo, msg);
                }
            }
        }
    }

}
