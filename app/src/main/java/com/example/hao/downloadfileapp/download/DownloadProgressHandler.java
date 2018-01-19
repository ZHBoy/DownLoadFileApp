package com.example.hao.downloadfileapp.download;


import android.os.Looper;
import android.os.Message;

/**
* @作者 hao
* @创建日期 2018/1/19 16:51
* Description: 下载的Handler
*/
public abstract class DownloadProgressHandler extends BaseProgressHandler {

    private static final int DOWNLOAD_PROGRESS = 1;
    protected ResponseHandler mHandler = new ResponseHandler(this, Looper.getMainLooper());

    @Override
    protected void sendMessage(ProgressModel progressBean) {
        mHandler.obtainMessage(DOWNLOAD_PROGRESS,progressBean).sendToTarget();

    }

    @Override
    protected void handleMessage(Message message){
        switch (message.what){
            case DOWNLOAD_PROGRESS:
                ProgressModel progressBean = (ProgressModel)message.obj;
                onProgress(progressBean.getCurrentBytes(),progressBean.getTotalBytes(),progressBean.isFinish());

        }
    }


}
