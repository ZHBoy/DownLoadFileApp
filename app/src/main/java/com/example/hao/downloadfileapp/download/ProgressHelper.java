package com.example.hao.downloadfileapp.download;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
* @作者 hao
* @创建日期 2018/1/19 18:04
* Description:
*/
public class ProgressHelper {

    private static ProgressModel progressModel = new ProgressModel();
    private static BaseProgressHandler mProgressHandler;

    public static OkHttpClient.Builder addProgress(OkHttpClient.Builder builder){

        if (builder == null){
            builder = new OkHttpClient.Builder();
        }

        final ProgressListener progressListener = new ProgressListener() {
            //该方法在子线程中运行
            @Override
            public void onProgress(long progress, long total, boolean done) {
                if (mProgressHandler == null){
                    return;
                }

                progressModel.setCurrentBytes(progress);
                progressModel.setTotalBytes(total);
                progressModel.setFinish(done);
                mProgressHandler.sendMessage(progressModel);

            }
        };

        //添加拦截器，自定义ResponseBody，添加下载进度
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(
                        new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();

            }
        });

        return builder;
    }

    public static void setProgressHandler(BaseProgressHandler progressHandler){
        mProgressHandler = progressHandler;
    }
}
