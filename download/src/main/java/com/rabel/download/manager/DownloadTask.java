package com.rabel.download.manager;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;


import com.rabel.download.connect.NetworkUtils;
import com.rabel.download.storage.StorageUtils;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DownloadTask extends AsyncTask<String, Integer, Long> {

    public final static int TIME_OUT = 30000;

    private final static int BUFFER_SIZE = 1024 * 8;

    private static final String TAG = DownloadTask.class.getSimpleName();

    private static final boolean DEBUG = true;

    public static final String TEMP_SUFFIX = ".download";

    private HttpURLConnection mConnection = null;

    private final File mFile;

    private final File mTempFile;

    private final String mUrlString;

    private String mFileName;

    private final String mPath;

    private final URL mURL;

    private final DownloadTaskListener mListener;

    private final Context mContext;

    private long mDownloadSize;

    private long mPreviousFileSize;

    private long mTotalSize;

    private long mDownloadPercent;

    private long mNetworkSpeed;

    private long mPreviousTime;

    private long mTotalTime;

    private Throwable mError = null;

    private boolean mInterrupt = false;

    private final DownloadManager mDownloadManager;

    private final class ProgressReportingRandomAccessFile extends RandomAccessFile {
        private int progress = 0;

        public ProgressReportingRandomAccessFile(File file, String mode) throws FileNotFoundException {
            super(file, mode);
        }

        @Override
        public void write(byte[] buffer, int offset, int count) throws IOException {
            super.write(buffer, offset, count);
            progress += count;
            // mTotalSize = progress;//__tq
            publishProgress(progress);
        }
    }

    public DownloadTask(Context context, DownloadManager dnMgr, String url, String path) throws MalformedURLException {
        this(context, dnMgr, url, path, null);
    }

    public DownloadTask(Context context, DownloadManager dnMgr, String url, String path, DownloadTaskListener listener)
            throws MalformedURLException {
        this(context, dnMgr, url, null, path, listener);
    }

    public DownloadTask(Context context, DownloadManager dnMgr, String url, String name, String path,
            DownloadTaskListener listener) throws MalformedURLException {
        super();
        this.mUrlString = url;
        this.mListener = listener;
        this.mURL = new URL(url);
        if (TextUtils.isEmpty(name)) {
            mFileName = new File(mURL.getFile()).getName();
        } else {
            mFileName = name;
        }
        this.mFile = new File(path, mFileName);
        this.mTempFile = new File(path, mFileName + TEMP_SUFFIX);
        this.mContext = context;
        mPath = path;
        mDownloadManager = dnMgr;
    }

    public Context getContext() {
        return this.mContext;
    }

    public String getUrl() {
        return mUrlString;
    }

    public String getPath() {
        return this.mPath;
    }

    public URL getURL() {
        return mURL;
    }

    public String getFilePath() {
        return mFile.getAbsolutePath();
    }

    public String getFileName() {
        return mFile.getName();
    }

    public boolean isInterrupt() {
        return mInterrupt;
    }

    public long getDownloadPercent() {
        return mDownloadPercent;
    }

    public long getDownloadSize() {
        return mDownloadSize + mPreviousFileSize;
    }

    public long getTotalSize() {
        return mTotalSize;
    }

    public long getDownloadSpeed() {
        return this.mNetworkSpeed;
    }

    public long getTotalTime() {
        return this.mTotalTime;
    }

    public DownloadTaskListener getListener() {
        return this.mListener;
    }

    @Override
    protected void onPreExecute() {
        mPreviousTime = System.currentTimeMillis();
        if (mListener != null)
            mListener.preDownload(this);
    }

    @Override
    protected Long doInBackground(String... params) {

        long id = Thread.currentThread().getId();
        mDownloadManager.mThreads.put(id, 1);
        long result = -1;
        try {
            result = download();
        } catch (NetworkErrorException e) {
            sendErrorMsg(2, e.getMessage());
            mError = e;
        } catch (FileAlreadyExistException e) {
            sendErrorMsg(3, e.getMessage());
            mError = e;
        } catch (NoMemoryException e) {
            sendErrorMsg(4, e.getMessage());
            mError = e;
        } catch (IOException e) {
            sendErrorMsg(5, e.getMessage());
            mError = e;
        } catch (SpecifiedUrlIsNotFoundException e) {
            sendErrorMsg(6, e.getMessage());
            mError = e;
        } catch (OtherHttpErrorException e) {
            sendErrorMsg(7, e.getMessage());
            mError = e;
        } finally {
            if (mConnection != null) {
                mConnection.disconnect();
                mConnection = null;
            }
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {

        if (progress.length > 1) {
            mTotalSize = progress[1];
            if (mTotalSize == -1) {
                if (mListener != null) {
                    mListener.errorDownload(this, mError);
                    return;
                }
            }
        }
        mTotalTime = System.currentTimeMillis() - mPreviousTime;
        mDownloadSize = progress[0];
        if (mTotalSize == 0) {
            mDownloadPercent = -1;
        } else {
            mDownloadPercent = (mDownloadSize + mPreviousFileSize) * 100 / mTotalSize;
        }
        mNetworkSpeed = mDownloadSize / mTotalTime;
        if (mListener != null)
            mListener.updateProcess(this);
    }

    @Override
    protected void onPostExecute(Long result) {

        if (result == -1 || mInterrupt || mError != null) {
            if (DEBUG && mError != null) {
                Log.v(TAG, "Download failed." + mError.getMessage() + ", url " + mUrlString);
            }
            if (mListener != null) {
                mListener.errorDownload(this, mError);
            }
            mDownloadManager.notifyCompleted();
            return;
        }
        // finish download
        mTempFile.renameTo(mFile);
        if (mListener != null) {
            mListener.finishDownload(this);
        }
//        FileUtils.setPermissions(mFile.getAbsolutePath(), FileUtils.S_IRUSR | FileUtils.S_IWUSR | FileUtils.S_IRGRP | FileUtils.S_IROTH, -1, -1) ;
        mDownloadManager.notifyCompleted();
    }

    @Override
    public void onCancelled() {
        super.onCancelled();
        mInterrupt = true;
    }

    private long download() throws NetworkErrorException, IOException, SpecifiedUrlIsNotFoundException,
            FileAlreadyExistException, NoMemoryException, OtherHttpErrorException {

        if (DEBUG) {
            Log.v(TAG, "totalSize: " + mTotalSize);
        }

        /*
         * check net work
         */
        if (!NetworkUtils.isNetworkAvailable(mContext)) {
            throw new NetworkErrorException("Network blocked.");
        }

        /*
         * check file length
         */
        String userAgent = NetworkUtils.getUserAgent(mContext);
        mConnection = (HttpURLConnection) mURL.openConnection();
        mConnection.setRequestMethod("GET");
        mConnection.setRequestProperty("User-Agent", userAgent);
        mConnection.setRequestProperty("Accept-Encoding", "identity");
        if (mTempFile.exists()) {
            mPreviousFileSize = mTempFile.length();
            mConnection.setRequestProperty("Range", "bytes=" + mPreviousFileSize + "-");
        }
        mConnection.connect();

        int responseCode = mConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            throw new SpecifiedUrlIsNotFoundException("Not found : " + mUrlString);
        } else if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_PARTIAL) {
            String responseCodeString = Integer.toString(responseCode);
            throw new OtherHttpErrorException("http error code : " + responseCodeString, responseCodeString);
        }

        boolean isRangeDownload = false;
        int length = mConnection.getContentLength();
        mTotalSize = mConnection.getContentLength();// __tq
        Log.d("Debug", "DownloadTask mTotalSize:" + mTotalSize);
        if (responseCode == HttpURLConnection.HTTP_PARTIAL) {
            length += mPreviousFileSize;
            isRangeDownload = true;
        }

        // for debug
        if (mFile.exists() && length == mFile.length()) {
            if (DEBUG) {
                Log.v(null, "Output file already exists. Skipping download.");
            }
            mListener.finishProcess(this);
            throw new FileAlreadyExistException("Output file already exists. Skipping download.");
        }

        /*
         * check memory
         */
        long storage = StorageUtils.getAvailableStorage();
        if (DEBUG) {
            Log.i(null, "storage:" + storage + " totalSize:" + length);
        }

        if (length - mPreviousFileSize > storage) {
            throw new NoMemoryException("SD card no memory.");
        }

        RandomAccessFile outputStream = new ProgressReportingRandomAccessFile(mTempFile, "rw");

        InputStream inputStream = mConnection.getInputStream();

        publishProgress(0, length);

        int bytesCopied = copy(inputStream, outputStream, isRangeDownload);

        if ((mPreviousFileSize + bytesCopied) != mTotalSize && mTotalSize != -1 && !mInterrupt) {
            throw new IOException("Download incomplete: " + bytesCopied + " != " + mTotalSize);
        }

        if (DEBUG) {
            Log.v(TAG, "Download completed successfully.");
        }

        return bytesCopied;

    }

    private int copy(InputStream input, RandomAccessFile output, boolean isRangeDownload) throws IOException,
            NetworkErrorException {

        if (input == null || output == null) {
            return -1;
        }

        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        if (DEBUG) {
            Log.v(TAG, "length " + output.length());
        }

        int count = 0, n = 0;
        long errorBlockTimePreviousTime = -1, expireTime = 0;

        try {

            if (isRangeDownload) {
                output.seek(output.length());
            }

            while (!mInterrupt) {
                n = in.read(buffer, 0, BUFFER_SIZE);
                if (n == -1) {
                    break;
                }
                output.write(buffer, 0, n);
                count += n;

                /*
                 * check network
                 */
                if (!NetworkUtils.isNetworkAvailable(mContext)) {
                    throw new NetworkErrorException("Network blocked.");
                }

                if (mNetworkSpeed == 0) {
                    if (errorBlockTimePreviousTime > 0) {
                        expireTime = System.currentTimeMillis() - errorBlockTimePreviousTime;
                        if (expireTime > TIME_OUT) {
                            sendErrorMsg(1, "connection time out.");
                            throw new ConnectTimeoutException("connection time out.");
                        }
                    } else {
                        errorBlockTimePreviousTime = System.currentTimeMillis();
                    }
                } else {
                    expireTime = 0;
                    errorBlockTimePreviousTime = -1;
                }
            }
        } finally {
            mConnection.disconnect();
            mConnection = null;
            output.close();
            in.close();
            input.close();
        }
        return count;
    }

    public void cancel() {
        this.cancel(true);
        mInterrupt = true;
    }

    private void sendErrorMsg(int status, String reason) {
        if (mListener != null) {
            mListener.onError(status, reason);
        }
    }

}
