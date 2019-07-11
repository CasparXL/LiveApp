package com.jlkf.text.textapp.util.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.jlkf.text.textapp.R;

import java.io.File;
import java.security.MessageDigest;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;


/**
 * create by libo
 * create on 2018/12/26
 * description Glide图片加载工具类
 */
public class ImageLoaderManager {

    /**
     * 默认加载
     *
     * @param context   上下文
     * @param url       路径
     * @param imageView 控件
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        if (context != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    /**
     * 默认加载
     *
     * @param context   上下文
     * @param url       路径
     * @param imageView 控件
     */
    public static void loadImage(Context context, Object url, ImageView imageView) {
        if (context != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    @SuppressLint("CheckResult")
    public static void loadVideoScreenshot(final Context context, String uri, ImageView imageView, long frameTimeMicros) {
        RequestOptions requestOptions = RequestOptions.frameOf(frameTimeMicros);
        requestOptions.set(FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST);
        requestOptions.transform(new BitmapTransformation() {
            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                return toTransform;
            }

            @Override
            public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
                try {
                    messageDigest.update((context.getPackageName() + "RotateTransform").getBytes("utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Glide.with(context).load(uri).apply(requestOptions).into(imageView);
    }

    /**
     * 默认加载方式
     *
     * @param context   上下文
     * @param url       路径
     * @param imageView 控件
     */
    public static void loadImage(Context context, int url, ImageView imageView) {
        if (context != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    /**
     * 加载圆形图片
     *
     * @param context   上下文
     * @param url       链接
     * @param imageView 控件
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        if (context != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CircleCrop());

            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    /**
     * 加载圆形图片
     *
     * @param context   上下文
     * @param url       链接
     * @param imageView 控件
     */
    public static void loadCircleImage(Context context, int url, ImageView imageView) {
        if (context != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CircleCrop());

            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    /**
     * 加载圆角图片
     *
     * @param context   上下文
     * @param url
     * @param imageView
     * @param radius    圆角大小
     */
    public static void loadRoundImage(Context context, String url, ImageView imageView, int radius) {
        if (context != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transforms(new CenterCrop(), new RoundedCorners(MeasureUtil.dip2px(context, radius)));

            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    /**
     * 加载圆角图片
     *
     * @param context   上下文
     * @param url
     * @param imageView
     * @param radius    圆角大小
     */
    public static void loadRoundImage(Context context, int url, ImageView imageView, int radius) {
        if (context != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transforms(new CenterCrop(), new RoundedCorners(MeasureUtil.dip2px(context, radius)));

            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    /**
     * 部分切弧度
     *
     * @param context   上下文
     * @param url       路径
     * @param imageView 图片控件
     * @param radius    弧度
     * @param how       方向 ①:切上边部分 左上角和右上角
     *                  ②:切下边部分 左下角和右下角
     *                  三:切左边部分 左上角和左下角
     *                  ④:切右边部分 右上角和右下角
     *                  五:切左上角和右下角
     *                  ⑥:切左下角和右上角
     */
    public static void loadRoundImage2(Context context, String url, ImageView imageView, int radius, int how) {
        if (context != null) {
            CornerTransform transformation = new CornerTransform(context, MeasureUtil.dip2px(context, radius));
            //只是绘制左上角和右上角圆角
            switch (how) {
                case 1:
                    //切上边部分 左上角和右上角
                    transformation.setExceptCorner(false, false, true, true);
                    break;
                case 2:
                    //切下边部分 左下角和右下角
                    transformation.setExceptCorner(true, true, false, false);
                    break;
                case 3:
                    //切左边部分 左上角和左下角
                    transformation.setExceptCorner(false, true, false, true);
                    break;
                case 4:
                    //切右边部分 右上角和右下角
                    transformation.setExceptCorner(true, false, true, true);
                    break;
                case 5:
                    //切左上角和右下角
                    transformation.setExceptCorner(false, true, true, false);
                    break;
                case 6:
                    //切左下角和右上角
                    transformation.setExceptCorner(true, false, false, true);
                    break;
            }
            RequestOptions ro = new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).transform(transformation).skipMemoryCache(true);
            Glide.with(context)
                    .load(url)
                    .apply(ro)
                    .into(imageView);
        }
    }

    /**
     * 加载图片指定大小
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public static void loadSizeImage(Context context, String url, ImageView imageView, int width, int height) {
        if (context != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .override(width, height)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    /**
     * 加载本地图片文件
     *
     * @param context
     * @param file
     * @param imageView
     */
    public static void loadFileImage(Context context, File file, ImageView imageView) {
        if (context != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop();

            Glide.with(context)
                    .load(file)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    /**
     * 加载高斯模糊
     *
     * @param context
     * @param url
     * @param imageView
     * @param radius    模糊级数 最大25
     */
    public static void loadBlurImage(Context context, String url, ImageView imageView, int radius) {
        if (context != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .override(300)
                    .transforms(new BlurTransformation(radius));

            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    /**
     * 加载gif图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadGifImage(Context context, String url, ImageView imageView) {
        if (context != null) {
            Glide.with(context)
                    .load(url)
                    .into(imageView);
        }
    }

    /**
     * 加载gif图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadGifImage(Context context, Object url, ImageView imageView) {
        if (context != null) {
            Glide.with(context).asGif()
                    .load(url)
                    .into(imageView);
        }
    }

}
