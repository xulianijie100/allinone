package com.beeboxes.ot.net;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiService {

    /**
     * 上传人脸图片
     */
    @POST("IOT/GetValidationInfo")
    Call<ResponseBody> getValidationInfo(@Body RequestBody body);

}
