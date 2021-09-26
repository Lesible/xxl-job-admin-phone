package com.xxl.job.admin.api;

import com.xxl.job.admin.core.model.OmgResult;
import com.xxl.job.admin.core.model.OmgRobotRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * <p> @date: 2021-06-01 11:03</p>
 *
 * @author 何嘉豪
 */
public interface OmgApi {

    @POST("wxChatBot/sendTextMessage")
    Call<OmgResult<Void>> sendText2Robot(@Body OmgRobotRequest request);
}
