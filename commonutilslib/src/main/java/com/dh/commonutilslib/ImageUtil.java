package com.dh.commonutilslib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by THINK on 2017/4/5.
 * load SD卡资源：load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
 * load assets资源：load("file:///android_asset/f003.gif")
 * load raw资源：load("Android.resource://com.frank.glide/raw/raw_1")或load("android.resource://com.frank.glide/raw/"+R.raw.raw_1)
 * load drawable资源：load("android.resource://com.frank.glide/drawable/news")或load("android.resource://com.frank.glide/drawable/"+R.drawable.news)
 * load ContentProvider资源：load("content://media/external/images/media/139469")
 * load http资源：load("http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg")
 * load https资源：load("https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp")
 * 当然，load不限于String类型，还可以：
 * load(Uri uri)，load(File file)，load(Integer resourceId)，load(URL url)，load(byte[] model)，load(T model)，loadFromMediaStore(Uri uri)。
 * load的资源也可以是本地视频，如果想要load网络视频或更高级的操作可以使用VideoView等其它控件完成。
 * 而且可以使用自己的ModelLoader进行资源加载：
 * using(ModelLoader<A, T> modelLoader, Class<T> dataClass)，using(final StreamModelLoader<T> modelLoader)，using(StreamByteArrayLoader modelLoader)，using(final FileDescriptorModelLoader<T> modelLoader)。
 * 返回GenericRequestBuilder实例。
 */

public class ImageUtil {
    public static final String PREFIX_LOCAL = "file:///";

    public static void displayImage(Fragment fragment, String url, ImageView imageView) {
        Glide.with(fragment).load(url).into(imageView);
        //让Glide既缓存全尺寸又缓存其他尺寸,下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
        //.diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    public static void displayImage(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(resId).into(imageView);
    }

    public static void displayImageSkipMemoryCache(Fragment fragment, String url, ImageView imageView) {
        Glide.with(fragment).load(url).skipMemoryCache(true).into(imageView);
        //让Glide既缓存全尺寸又缓存其他尺寸,下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
        //.diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    public static void displayImageWithCorner(Fragment fragment, String url, ImageView imageView, int radius) {
//        Glide.with(mFragment).load(url)
//                .bitmapTransform(new GlideRoundTransform(mFragment.getActivity(), 10)).into(imageView);
        Glide.with(fragment).load(url)
                .bitmapTransform(new RoundedCornersTransformation(fragment.getActivity(), radius, 0, RoundedCornersTransformation.CornerType.ALL))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)//既缓存全尺寸的也缓存其他尺寸
                .into(imageView);
    }

    public static void displayImageWithCornerTop(Context context, int resId, ImageView imageView, int radius) {
        Glide.with(context).load(resId)
                .bitmapTransform(new RoundedCornersTransformation(context, radius, 0, RoundedCornersTransformation.CornerType.TOP))
                .into(imageView);
    }

    public static void displayImageWithCornerSkipMemoryCache(Fragment fragment, String url, ImageView imageView, int radius) {
        Glide.with(fragment).load(url)
                .bitmapTransform(new RoundedCornersTransformation(fragment.getActivity(), radius, 0, RoundedCornersTransformation.CornerType.ALL))
                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)//既缓存全尺寸的也缓存其他尺寸
                .into(imageView);
    }

    public static void displayImageWithCorner(Context context, String url, ImageView imageView, int radius) {
        Glide.with(context).load(url)
                .bitmapTransform(new RoundedCornersTransformation(context, radius, 0, RoundedCornersTransformation.CornerType.ALL))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void displayImageWithCornerSkipMemoryCache(Context context, String url, ImageView imageView, int radius) {
        Glide.with(context).load(url)
                .bitmapTransform(new RoundedCornersTransformation(context, radius, 0, RoundedCornersTransformation.CornerType.ALL))
                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void displayImageWithCorner(Activity activity, String url, ImageView imageView, int radius) {
        Glide.with(activity).load(url)
                .bitmapTransform(new RoundedCornersTransformation(activity, radius, 0, RoundedCornersTransformation.CornerType.ALL))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    //Glide保存图片
    public static void savePicture(Activity activity, String url) {
        Glide.with(activity).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {

            }
        });
    }

    public static void displayImageWithCornerTopSkipMemoryCache(Activity activity, String url, ImageView imageView, int radius) {
        MultiTransformation multi = new MultiTransformation(
                new BlurTransformation(activity),
                new RoundedCornersTransformation(activity, 128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
        Glide.with(activity).load(url)
                .bitmapTransform(multi)
                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void displayImageWithCorner(Fragment fragment, String url, ImageView imageView) {
        displayImageWithCorner(fragment, url, imageView, ScreenUtils.dpToPxInt(fragment.getContext(), 5));
    }

    public static void displayImageWithCorner(Context context, String url, ImageView imageView) {
        displayImageWithCorner(context, url, imageView, ScreenUtils.dpToPxInt(context, 5));
    }

    public static void displayImageWithCorner(Activity activity, String url, ImageView imageView) {
        displayImageWithCorner(activity, url, imageView, ScreenUtils.dpToPxInt(activity, 5));
    }

    /**
     * 注意点：当在xml布局中设置centerCrop属性时，再用glide加载圆角图片，
     * 这时centerCrop是没有效果的，圆角也会无效，解决方法：
     * 在.bitmapTransform中加入new CenterCrop(activity)和呢我RoundedCornersTransformation两个即可
     *
     * @param activity
     * @param url
     * @param imageView
     */
    public static void displayImageWithCornerCenterCrop(Activity activity, String url, ImageView imageView, int radius) {
        Glide.with(activity).load(url)
                .bitmapTransform(new CenterCrop(activity),
                        new RoundedCornersTransformation(activity, radius, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void displayImageWithCornerCenterCrop(Fragment fragment, String url, ImageView imageView, int radius) {
        Glide.with(fragment).load(url)
                .bitmapTransform(new CenterCrop(fragment.getActivity()),
                        new RoundedCornersTransformation(fragment.getActivity(), radius, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void displayImageCircleCenterCrop(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url)
                .bitmapTransform(new CenterCrop(activity), new CropCircleTransformation(activity))
                .into(imageView);
    }
    public static void displayImageCircleCenterCrop(Activity activity, String url, ImageView imageView, int defaultResId) {
        Glide.with(activity).load(url).placeholder(defaultResId).error(defaultResId)
                .bitmapTransform(new CenterCrop(activity), new CropCircleTransformation(activity))
                .into(imageView);
    }

    public static void displayLocalImageCircleCenterCrop(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(new File(url))
                .bitmapTransform(new CenterCrop(activity), new CropCircleTransformation(activity))
                .into(imageView);
    }

//    /**
//     * 显示头像专用
//     *
//     * @param activity
//     * @param url
//     * @param imageView
//     */
//    public static void displayImageHeadCircleCenterCrop(Context activity, String url, ImageView imageView) {
//        Glide.with(activity).load(url).placeholder(R.drawable).error(R.drawable.icon_logo)
//                .bitmapTransform(new CenterCrop(activity), new CropCircleTransformation(activity))
//                .into(imageView);
//    }


    public static void displayImageMerchantHeadCircleCenterCrop(Activity activity, String url, int placeholder, ImageView imageView) {
        Glide.with(activity).load(url).placeholder(placeholder).error(placeholder)
                .into(imageView);
    }

//    public static void displayWithErrorImage(Activity activity, String url, ImageView imageView) {
//        Glide.with(activity).load(url).error(R.drawable.icon_logo).placeholder(R.drawable.icon_logo).dontAnimate().into(imageView);
//    }
//
//    public static void displayWithErrorImage(Context activity, String url, ImageView imageView) {
//        Glide.with(activity).load(url).error(R.drawable.icon_logo).placeholder(R.drawable.icon_logo).dontAnimate().into(imageView);
//    }

    public static void displayImageCircleCropSkipMemoryCache(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url)
                .bitmapTransform(new CenterCrop(activity), new CropCircleTransformation(activity))
                .skipMemoryCache(true)
                .into(imageView);
    }

//    public static void displayHeadIconCircleCenterCrop(Activity activity, String url, ImageView imageView) {
//        Glide.with(activity).load(url).placeholder(R.mipmap.icon_un_login_icon)
//                .bitmapTransform(new CenterCrop(activity), new CropCircleTransformation(activity))
//                .into(imageView);
//    }

//    public static void displayHeadIconCircleCropSkipMemoryCache(Activity activity, String url, ImageView imageView) {
//        Glide.with(activity).load(url).placeholder(R.mipmap.icon_un_login_icon)
//                .bitmapTransform(new CenterCrop(activity), new CropCircleTransformation(activity))
//                .skipMemoryCache(true)
//                .into(imageView);
//    }

    public static void displayImage(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).into(imageView);
    }

    public static void displayImage(Activity activity, String url, ImageView imageView, float thumbnail) {
        Glide.with(activity).load(url).thumbnail(thumbnail).into(imageView);
    }

    public static void displayLocalImage(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(new File(url)).into(imageView);
    }

    public static void displayLocalImageCorner(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(new File(url))
                .bitmapTransform(new CenterCrop(activity), new RoundedCornersTransformation(activity, 10, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void displayLocalImageCorner(Activity activity, String url, ImageView imageView, int radius) {
        Glide.with(activity).load(new File(url))
                .bitmapTransform(new CenterCrop(activity), new RoundedCornersTransformation(activity, radius, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void displayLocalImageCenterCrop(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(new File(url))
                .bitmapTransform(new CenterCrop(activity))
                .into(imageView);
    }

    public static void displayLocalImageCenterCrop(Context context, String url, ImageView imageView) {
        Glide.with(context).load(new File(url))
                .bitmapTransform(new CenterCrop(context))
                .into(imageView);
    }

    public static void displayLocalImageCenterCrop(Context context, String url, ImageView imageView, int defaultImgId, int errorImgId) {
        Glide.with(context).load(new File(url))
                .placeholder(defaultImgId)
                .error(errorImgId)
                .bitmapTransform(new CenterCrop(context))
                .into(imageView);
    }

    public static void displayLocalImageCorner(Context activity, String url, ImageView imageView) {
        Glide.with(activity).load(new File(url))
                .bitmapTransform(new CenterCrop(activity), new RoundedCornersTransformation(activity, 10, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void displayImageSkipMemoryCache(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).skipMemoryCache(true).into(imageView);
    }

    public static void displayImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    //    private SimpleTarget target = new SimpleTarget<Bitmap>() {
//        @Override
//        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
//            //这里我们拿到回掉回来的bitmap，可以加载到我们想使用到的地方
//
//        }
//    };
    public static void loadImage(Context context, String url, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context).load(url).asBitmap().into(simpleTarget);
    }

    public static void loadImage(Fragment context, String url, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context).load(url).asBitmap().into(simpleTarget);
    }

    public static void loadImageDontAnimate(Context context, String url, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context).load(url).asBitmap().dontAnimate().into(simpleTarget);
    }

    public static void loadImage(Activity context, String url, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context).load(url).asBitmap().into(simpleTarget);
    }

    public static void loadImageWithListener(Context context, String url, RequestListener<String, Bitmap> listener, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context).load(url).asBitmap().listener(listener).into(simpleTarget);
    }

    public static void loadImageWithListenerIntoImageView(Activity context, String url, RequestListener listener,
                                                          ImageView imageView) {
        Glide.with(context).load(url).listener(listener).into(imageView);
    }

    public static void loadImageSkipMemoryCache(Activity context, String url, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context).load(url).asBitmap().skipMemoryCache(true).into(simpleTarget);
    }

    public static void loadFileReadPicture(Activity activity, String path, SimpleTarget<Bitmap> target) {
        Glide.with(activity).load("file://" + path).asBitmap().skipMemoryCache(true).into(target);
    }
//    // 清除图片磁盘缓存，调用Glide自带方法
//    public static boolean clearCacheDiskSelf() {
//        try {
//            if (Looper.myLooper() == Looper.getMainLooper()) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Glide.get(BaseApplication.getInstance()).clearDiskCache();
//                    }
//                }).start();
//            } else {
//                Glide.get(BaseApplication.getInstance()).clearDiskCache();
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // 清除Glide内存缓存
//    public static boolean clearCacheMemory() {
//        try {
//            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
//                Glide.get(BaseApplication.getInstance()).clearMemory();
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
