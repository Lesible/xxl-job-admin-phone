package com.xxl.job.admin.core.conf;

import com.xxl.job.admin.common.metadata.XxlJobProperties;
import com.xxl.job.admin.core.alarm.JobAlarmHandler;
import com.xxl.job.admin.core.scheduler.XxlJobScheduler;
import com.xxl.job.admin.dao.*;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * xxl-job config
 *
 * @author xuxueli 2017-04-28
 */

@Component
public class XxlJobAdminConfig implements InitializingBean, DisposableBean {

    private static XxlJobAdminConfig adminConfig = null;
    private XxlJobScheduler xxlJobScheduler;


    // ---------------------- XxlJobScheduler ----------------------
    // conf
    @Resource
    private XxlJobProperties xxlJobProperties;
    @Resource
    private XxlJobLogDao xxlJobLogDao;
    @Resource
    private XxlJobInfoDao xxlJobInfoDao;


    // ---------------------- XxlJobScheduler ----------------------
    @Resource
    private XxlJobRegistryDao xxlJobRegistryDao;
    // dao, service
    @Resource
    private XxlJobGroupDao xxlJobGroupDao;
    @Resource
    private XxlJobLogReportDao xxlJobLogReportDao;
    @Resource
    private DataSource dataSource;
    @Resource
    private JobAlarmHandler jobAlarmHandler;

    public static XxlJobAdminConfig getAdminConfig() {
        return adminConfig;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        adminConfig = this;

        xxlJobScheduler = new XxlJobScheduler();
        xxlJobScheduler.init();
    }

    @Override
    public void destroy() throws Exception {
        xxlJobScheduler.destroy();
    }

    public String getI18n() {
        String i18n = xxlJobProperties.getI18n();
        if (!Arrays.asList("zh_CN", "zh_TC", "en").contains(i18n)) {
            return "zh_CN";
        }
        return i18n;
    }

    public String getAccessToken() {
        return xxlJobProperties.getAccessToken();
    }

    public int getTriggerPoolFastMax() {
        Integer triggerPoolFastMax = xxlJobProperties.getTriggerPool().getFastMax();
        if (triggerPoolFastMax < 200) {
            return 200;
        }
        return triggerPoolFastMax;
    }

    public int getTriggerPoolSlowMax() {
        Integer triggerPoolSlowMax = xxlJobProperties.getTriggerPool().getSlowMax();
        if (triggerPoolSlowMax < 100) {
            return 100;
        }
        return triggerPoolSlowMax;
    }

    public int getLogRetentionDays() {
        int logRetentionDays = xxlJobProperties.getLogRetentionDays();
        if (logRetentionDays < 7) {
            return -1;  // Limit greater than or equal to 7, otherwise close
        }
        return logRetentionDays;
    }

    public XxlJobLogDao getXxlJobLogDao() {
        return xxlJobLogDao;
    }

    public XxlJobInfoDao getXxlJobInfoDao() {
        return xxlJobInfoDao;
    }

    public XxlJobRegistryDao getXxlJobRegistryDao() {
        return xxlJobRegistryDao;
    }

    public XxlJobGroupDao getXxlJobGroupDao() {
        return xxlJobGroupDao;
    }

    public XxlJobLogReportDao getXxlJobLogReportDao() {
        return xxlJobLogReportDao;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public JobAlarmHandler getJobAlarmHandler() {
        return jobAlarmHandler;
    }

}
