package com.xxl.job.admin.api;

import com.xxl.job.admin.core.model.SmsResult;
import com.xxl.job.admin.util.SmsUtil;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * <p> @date: 2021-05-15 16:41</p>
 *
 * @author 何嘉豪
 */
public interface SmsApi {

    @POST("backstage/send/sengSindleTSMS")
    Call<SmsResult> sendSms(@Body SmsUtil.SmsRequestWrapper smsRequestWrapper);


}
