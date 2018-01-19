package com.example.hao.downloadfileapp.download;

/**
* @作者 hao
* @创建日期 2018/1/19 18:04
* Description:
*/
public interface ProgressListener {
    /**
     * @param progress     已经下载或上传字节数
     * @param total        总字节数
     * @param done         是否完成
     */
    void onProgress(long progress, long total, boolean done);
}
