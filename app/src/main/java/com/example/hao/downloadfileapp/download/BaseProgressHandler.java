package com.example.hao.downloadfileapp.download;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
* @作者 hao
* @创建日期 2018/1/19 16:52
* Description: 下载进度handler基类
*/
public abstract class BaseProgressHandler {

    protected abstract void sendMessage(ProgressModel progressBean);

    protected abstract void handleMessage(Message message);

    protected abstract void onProgress(long progress, long total, boolean done);

    protected static class ResponseHandler extends Handler {

        private BaseProgressHandler mProgressHandler;
        public ResponseHandler(BaseProgressHandler mProgressHandler, Looper looper) {
            super(looper);
            this.mProgressHandler = mProgressHandler;
        }

        @Override
        public void handleMessage(Message msg) {
            mProgressHandler.handleMessage(msg);
        }
    }

}
