package com.example.hao.downloadfileapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
* @作者 hao
* @创建日期 2018/1/19 18:02
* Description: 网络接口类
*/
public interface DownloadApi {
    @GET
    Call<ResponseBody> downloadFile(@Url String url);
}
