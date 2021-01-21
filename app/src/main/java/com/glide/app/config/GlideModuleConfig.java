package com.glide.app.config;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.target.ViewTarget;
import com.glide.app.R;

@GlideModule
public class GlideModuleConfig extends AppGlideModule {

    int diskSize = 1024 * 1024 * 300;  //磁盘大小限制300M
    int smallDiskSize = 1024 * 1024 * 50; //磁盘外部存储有限制时，用内部存储用这个值
    int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存
    String imageCacheFile = "image_cache";

    public static final String IMAGE_DIR_NAME = "Images";
    public static final String ATTACHMENT_DIR_NAME = "Attachments";


    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //关于内存缓存：一般情况下，开发者是不需要自己去指定它们的大小的，因为Glide已经帮我们做好了。
        // 默认的内存缓存和BitmapPool的大小由MemorySizeCalculator根据当前设备的屏幕大小和可用内存计算得到。
        // 同时Glide还支持动态的缓存大小调整，在存在大量图片的Activity/Fragment中，
        // 开发者可以通过setMemoryCategory方法来提高Glide的内存缓存大小，从而加快图片的加载速度。
        // 自定义内存和图片池大小
        //builder.setMemoryCache(new LruResourceCache(memorySize));
        //builder.setBitmapPool(new LruBitmapPool(memorySize));

        // 定义图片缓存大小和位置，依然是外部存储
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, IMAGE_DIR_NAME, diskSize));

        ViewTarget.setTagId(R.id.glide_tag_id);

        //更改配置
    }


    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
        //替换组件：基于原生HttpURLConnection的HTTP通讯组件替换成OkHttp3组件
        //也可以替换成其他HTTP组件
        //prepend将OkHttpFetcher放在HttpUrlFetcher前面，列表中取第0个执行，
        // 即OkHttpFetcher
        //glide.getRegistry().prepend(GlideUrl.class, InputStream.class, new OkHttpGlideUrlLoader.Factory());
        // 替换组件交给三方库 okhttp3-integration
    }

    /**
     * 清单解析的开启
     * <p>
     * 这里不开启，避免添加相同的modules两次
     *
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return true;
    }
}
