package com.rabel.download.manager;

public interface DownloadTaskListener {

    public void updateProcess(DownloadTask task);

    public void finishProcess(DownloadTask task);

    public void finishDownload(DownloadTask task);

    public void preDownload(DownloadTask task);

    public void queuedTask(DownloadTask task);

    public void pausedDownload(DownloadTask task);

    public void resumedDownload(DownloadTask task);

    public void deletedDownload(DownloadTask task);

    public void errorDownload(DownloadTask task, Throwable error);

    void onError(int status, String reason);
}
