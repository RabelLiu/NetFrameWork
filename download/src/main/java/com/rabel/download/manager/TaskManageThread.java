package com.rabel.download.manager;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

public class TaskManageThread<T extends AsyncTask<Params, ?, ?>, Params> extends Thread {
    private static final String TAG = TaskManageThread.class.getSimpleName();

    private volatile boolean mFinishRequested = false;

    private TaskManageCallback<T, Params> mTaskManageCallback = null;

    private TaskQueue<T> mTaskQueue;

    public interface TaskManageCallback<T, Params> {
        Params[] getInputParameter(T task);
    }

    public void setTaskManageCallback(TaskManageCallback<T, Params> callback) {
        mTaskManageCallback = callback;
    }

    public TaskManageThread(TaskQueue<T> reqTask) {
        mTaskQueue = reqTask;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void run() {
        try {
            while (!mFinishRequested) {
                T task = mTaskQueue.getTask();
                if (task != null) {
                    Params[] p = null;
                    if (mTaskManageCallback != null) {
                        p = mTaskManageCallback.getInputParameter(task);
                    }
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, p);
                    Log.d(TAG, "task is execute");
                }
            }
        } catch (Exception e) {
        } finally {
//            Log.d(TAG, "task manage thread is finished");
            mTaskQueue.removeAll();
        }
    }

    public void requestFinish() {
        mFinishRequested = true;
        interrupt();
    }
}
