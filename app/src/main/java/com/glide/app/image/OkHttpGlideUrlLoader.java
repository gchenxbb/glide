package com.glide.app.image;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.glide.app.interceptor.ProgressIntercepor;

import java.io.InputStream;

import okhttp3.OkHttpClient;


public class OkHttpGlideUrlLoader implements ModelLoader<GlideUrl, InputStream> {
    public OkHttpGlideUrlLoader(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    private OkHttpClient okHttpClient;

    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private OkHttpClient client;

        public Factory() {

        }

        public Factory(OkHttpClient client) {
            this.client = client;
        }

        private synchronized OkHttpClient getOkHttpCLient() {
            if (client == null) {
                client = new OkHttpClient.Builder()
                        .addInterceptor(new ProgressIntercepor())
                        .build();
            }
            return client;
        }

        @NonNull
        @Override
        public ModelLoader<GlideUrl, InputStream> build(@NonNull MultiModelLoaderFactory multiFactory) {
            return new OkHttpGlideUrlLoader(getOkHttpCLient());
        }

        @Override
        public void teardown() {
            // Do nothing.
        }
    }


    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl url, int width, int height, @NonNull Options options) {
        return new LoadData<>(url, new OkHttpFetcher(okHttpClient, url));
    }

    @Override
    public boolean handles(@NonNull GlideUrl url) {
        return true;
    }
}
