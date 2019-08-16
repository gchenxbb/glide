package com.glide.app;



import com.glide.app.interceptor.ProgressIntercepor;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by root on 18-8-8.
 */

public class ProgressResponseBody extends ResponseBody {
    private static final String TAG = "ProgressResponseBody";
    private BufferedSource bufferedSource;
    private ResponseBody responseBody;
    private ProgressListener progressListener;

    public ProgressResponseBody(String url, ResponseBody responseBody) {
        this.responseBody = responseBody;
        progressListener = ProgressIntercepor.LISTENER_MAP.get(url);
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(new ProgressSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private class ProgressSource extends ForwardingSource {

        long totalBytesRead = 0;
        int currentProgress;

        public ProgressSource(Source delegate) {
            super(delegate);
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            long bytesRead = super.read(sink, byteCount);
            //计算下载比例
            long fullLength = responseBody.contentLength();
            if (bytesRead == -1) {
                totalBytesRead = fullLength;
            } else {
                totalBytesRead += bytesRead;
            }
            int progress = (int) (100f * totalBytesRead / fullLength);
            //CLogUtils.d(TAG, "download progress is " + progress);
            if (progressListener != null && progress != currentProgress) {
                progressListener.onProgress(progress);
            }
            if (progressListener != null && totalBytesRead == fullLength) {
                progressListener = null;
            }
            currentProgress = progress;
            return bytesRead;

        }
    }

}
