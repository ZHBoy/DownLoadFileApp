package com.example.hao.downloadfileapp.download;

/**
* @作者 hao
* @创建日期 2018/1/19 16:53
* Description: 进度传递对象
*/
public class ProgressModel {

    private long currentBytes;//当前已下载
    private long totalBytes;//总大小
    private boolean finish;//完成标志

    public long getCurrentBytes() {
        return currentBytes;
    }

    public void setCurrentBytes(long currentBytes) {
        this.currentBytes = currentBytes;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}
