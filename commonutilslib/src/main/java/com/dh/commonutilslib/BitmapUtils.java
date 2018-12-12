package com.dh.commonutilslib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.View;


import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/4/21.
 */
public class BitmapUtils {
    public static final String HEAD_SAVE_DIRECTORY = "avator_data";
    public static final String HEAD_SAVE_FILENAME = "avator";
    public static final String TEMP_DIRECTORY = "temp_pic";
    public static final String TEMP_FILENAME = "temp_";
    public static final int RESULT_PHOTOCAMERA = 21;
    public static final int RESULT_PHOTOALBUM = 22;
    public static final int REQUESTCODE_TO_CLIP = 20;
    public static final String IMAGE_UNSPECIFIED = "image/*";
    public final static int IMAGE_MAX_WIDTH = 720;// 最大宽度
    public final static int IMAGE_MAX_HEIGHT = 900;// 最大高度
    public final static int IMAGE_MAX_SIZE = 90;// 图片最大100k，这里设置的100，实际上图片比100k大，所以如果要把图片压缩到100k，则要改成更小

    public static Bitmap rotatePicture(Bitmap source, int rotateDegree) {
        Bitmap newBp = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        newBp = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return newBp;
    }

    /**
     * 图片翻转
     *
     * @param bitmap
     * @return
     */
    public static Bitmap rotatePicture2(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
//        Canvas cv = new Canvas(bitmap);
        Matrix m = new Matrix();
        m.postScale(1, -1);   //镜像垂直翻转
        m.postRotate(180);
        Bitmap new2 = Bitmap.createBitmap(bitmap, 0, 0, w, h, m, true);
        return new2;
//        cv.drawBitmap(new2, new Rect(0, 0, new2.getWidth(), new2.getHeight()), new Rect(0, 0, ww, wh), null);
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 压缩图片到指定大小
     *
     * @param file      图片文件
     * @param imageData 图片字节数组
     * @param path      图片路径
     * @param width     图片宽度
     * @param height    图片高度
     * @param maxSize   图片最大值
     * @return
     */
    public static Bitmap revitionImage(File file, byte[] imageData, String path, int width, int height, int maxSize) {
        Bitmap bitmap = null;
        Bitmap bitmap1 = null;
        String filePath = "";
        if (path != null && path.length() > 0) {
            filePath = path;
        }
        if (file != null && file.exists()) {
            filePath = file.getPath();
        }
        try {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                Bitmap bitmap2 = BitmapFactory.decodeFile(filePath, opts);
//                System.out.println("opts1:" + opts.outWidth + "," + opts.outHeight);
                if (bitmap2 != null) {
                    bitmap2.recycle();
                    bitmap2 = null;
                }
                // 计算图片缩放比例
                opts.inSampleSize = calculateInSampleSize(opts, width, height);
                // opts.inSampleSize = computeSampleSize(opts, 200,
                // width*height);
                opts.inJustDecodeBounds = false;
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
                bitmap1 = BitmapFactory.decodeFile(filePath, opts);
//                System.out.println("opts2:" + opts.outWidth + "," + opts.outHeight);
                if (bitmap1 != null)
                    bitmap = revitionImageSize(bitmap1, maxSize);
            }
            return bitmap;
        } catch (OutOfMemoryError oom) {
            oom.printStackTrace();
        } finally {
            if (bitmap1 != null && !bitmap1.isRecycled()) {
                bitmap1.recycle();
                bitmap1 = null;
            }
        }
        return null;
    }

    /**
     * 将图片压缩到指定大小
     *
     * @param originalBitmap
     * @return
     */
    public static Bitmap revitionImageSize(Bitmap originalBitmap, int maxSize) {
        if (originalBitmap == null) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 100;
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            while (baos.toByteArray().length / 1024 > maxSize) {
                baos.reset();
                if (options <= 0)
                    break;
                originalBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                options -= 10;
            }
            // 再次压缩尺寸
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
            return bitmap;
        } catch (OutOfMemoryError oom) {
        } finally {
            if (originalBitmap != null && !originalBitmap.isRecycled()) {
                originalBitmap.recycle();
                originalBitmap = null;
            }
        }
        return null;
    }

    public static Bitmap getCompressBitmap(Bitmap bitmap, int maxSize) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
//            ELog.d("Bimp_ImageSize", "before:" + baos.toByteArray().length / 1024 + "K");
        while (baos.toByteArray().length / 1024 > maxSize) {
            baos.reset();
            if (options <= 0)
                break;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 5;
        }
//            ELog.d("Bimp_ImageSize", "after:" + baos.toByteArray().length / 1024 + "K");
        // 再次压缩尺寸
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }


    /**
     * 压缩图片
     *
     * @param originalBitmap
     * @param maxSize        压缩大小
     * @param b              无用参数
     * @return
     */
    public static byte[] revitionImageSize(Bitmap originalBitmap, int maxSize, boolean b) {
        if (originalBitmap == null) {
            return null;
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 100;
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
//            ELog.d("Bimp_ImageSize", "before:" + baos.toByteArray().length / 1024 + "K");
            while (baos.toByteArray().length / 1024 > maxSize) {
                baos.reset();
                if (options <= 0)
                    break;
                originalBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                options -= 10;
            }
//            ELog.d("Bimp_ImageSize", "after:" + baos.toByteArray().length / 1024 + "K");
//            // 再次压缩尺寸
//            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
//            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
            return baos.toByteArray();
        } catch (OutOfMemoryError oom) {
        } finally {
            if (originalBitmap != null && !originalBitmap.isRecycled()) {
                originalBitmap.recycle();
                originalBitmap = null;
            }
        }
        return null;
    }

    /**
     * 根据BitmapFactory.Options 计算图片压缩比率
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);// 1440/900=2
            final int widthRatio = Math.round((float) width / (float) reqWidth);// 850/720=1
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            // 只能是2的次幂
            if (inSampleSize > 2 && inSampleSize < 6.5)
                inSampleSize = 4;
            if (inSampleSize >= 6.5 && inSampleSize < 8)
                inSampleSize = 8;
        }
        return inSampleSize > 0 ? inSampleSize : 1;
    }


    /**
     * 缓存头像文件到本地，同时对图片进行压缩
     *
     * @param mHeadpath
     */
    public static synchronized String cacheAvatorToDisk(Context context, String mHeadpath, Bitmap bitmap) {
        String diskCacheDirectory = FileUtil.getCachePath(context) + File.separator + BitmapUtils.HEAD_SAVE_DIRECTORY;
        // // 把头像文件保存到本地
        int dotIndex = mHeadpath.lastIndexOf(".");
        // 图片文件后缀
        String str = mHeadpath.substring(dotIndex, mHeadpath.length());
        // 缩小图片，防止内存溢出
        // BitmapFactory.Options opts = new BitmapFactory.Options();
        // opts.inJustDecodeBounds = false;
        // opts.inSampleSize = 2;// 缩小一倍
        // Bitmap bitmap = BitmapFactory.decodeFile(mHeadpath, opts);
        File imageFolder = new File(diskCacheDirectory + File.separator);
        if (!imageFolder.exists()) {
            imageFolder.mkdir();
        }
        File newImageFile = new File(diskCacheDirectory + File.separator + BitmapUtils.HEAD_SAVE_FILENAME + "_" + System.currentTimeMillis() + str);
        if (newImageFile.exists()) {
            newImageFile.delete();
        }
        String headPath = newImageFile.getPath();
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(newImageFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            bos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap = null;
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return headPath;
    }

    public static File cachePicToTempDirectory(Context context, String path, int index) {
        String diskCacheDirectory = FileUtil.getCachePath(context) + File.separator + BitmapUtils.TEMP_DIRECTORY;
        File imageFolder = new File(diskCacheDirectory + File.separator);
        if (!imageFolder.exists()) {
            imageFolder.mkdir();
        }
        int dotIndex = path.lastIndexOf(".");
        // 图片文件后缀
        String str = path.substring(dotIndex, path.length());
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        File newImageFile = new File(diskCacheDirectory + File.separator + BitmapUtils.TEMP_FILENAME + index + str);
//        String headPath = newImageFile.getPath();
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(newImageFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            bos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return newImageFile;
    }

    public static File cachePicToTempDirectory(Context context, File file, int index) {
        return cachePicToTempDirectory(context, file.getPath(), index);
    }

    /**
     * 压缩图片
     *
     * @param headPath
     * @return
     */
    public static Bitmap compressBitmapWithPath(String headPath) {
        int degree = readPictureDegree(headPath);
        Bitmap bitmap = revitionImage(null, null, headPath, BitmapUtils.IMAGE_MAX_WIDTH,
                BitmapUtils.IMAGE_MAX_HEIGHT, BitmapUtils.IMAGE_MAX_SIZE);
        Bitmap bmResult = null;
        if (degree != 0) {
            bmResult = BitmapUtils.rotatePicture(bitmap, degree);
        }
        if (bmResult == null) {
            return bitmap;
        } else {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
            return bmResult;
        }
    }

    /**
     * 照相
     */
    public static String fromCamera(Activity context, int requestCode, String fileProvider) {
        String headPath = "";
        if (SystemUtil.isSdcardAvailable()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 指定路径
            String headDir = FileUtil.getCachePath(context) + "/image/";
            File dirFile = new File(headDir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            headPath = headDir + System.currentTimeMillis() + ".jpg";
            // 对两个文件夹进行判断是否存在，不存在就要创建
            File file = new File(headPath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 启动相机
            Uri imageUri = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(context, fileProvider, file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                imageUri = Uri.fromFile(file);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            context.startActivityForResult(intent, requestCode);
        } else {
            ToastUtils.showToast(context, "SD卡不可用");
        }
        return headPath;
    }

    public static String fromCameraWithFragment(Fragment context, int requestCode, String fileProvider) {
        String headPath = "";
        if (SystemUtil.isSdcardAvailable()) {
            //android8.0
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 指定路径
            String headDir = FileUtil.getCachePath(context.getActivity()) + "/image/";
            File dirFile = new File(headDir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            headPath = headDir + System.currentTimeMillis() + ".jpg";
            // 对两个文件夹进行判断是否存在，不存在就要创建
            File file = new File(headPath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 启动相机
            Uri imageUri = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(context.getActivity(), fileProvider, file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                imageUri = Uri.fromFile(file);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            context.startActivityForResult(intent, requestCode);
        } else {
            ToastUtils.showToast(context.getActivity(), "SD卡不可用");
        }
        return headPath;
    }


//    public static String fromCamera(Fragment fragment, String fileProvider, int requestCode) {
//        String headPath = "";
//        if (Utils.isSdcardAvailable()) {
//            //android8.0
////            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            // 指定路径
//            String headDir = FileUtil.getCachePath(fragment.getActivity()) + "/image/";
//            File dirFile = new File(headDir);
//            if (!dirFile.exists()) {
//                dirFile.mkdirs();
//            }
//            headPath = headDir + System.currentTimeMillis() + ".jpg";
//            // 对两个文件夹进行判断是否存在，不存在就要创建
//            File file = new File(headPath);
//            if (!file.exists()) {
//                try {
//                    file.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            // 启动相机
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //判读版本是否在7.0以上
//                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
//                Uri apkUri = FileProvider.getUriForFile(fragment.getActivity(), fileProvider, file);
//                //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, apkUri);
//            } else {
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//            }
//            fragment.startActivityForResult(intent, requestCode);
//        } else {
//            ToastUtils.showToast(R.string.s_sdcard_unavalible);
//        }
//        return headPath;
//    }

    /**
     * 从图库选择
     */
    public static void fromPhoto(Activity context, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, BitmapUtils.IMAGE_UNSPECIFIED);
        context.startActivityForResult(intent, requestCode);
    }

    public static Bitmap decodeBitmapFromResources(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 根据传入的图片资源文件来解析图片
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap scaleBitmap(Resources res, int redId, int reqWidth, int reqHeight) {
        Bitmap bitmap = decodeSampledBitmapFromResource(res, redId, reqWidth, reqHeight);
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) reqWidth) / width;
        float scaleHeight = ((float) reqHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbm;

    }


    public static Bitmap decodeBitmapFromAssets(Context context, String sname) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;// 允许可清除
        options.inInputShareable = true;// 以上options的两个属性必须联合使用才会有效果
        InputStream is = null;
        try {
            is = context.getAssets().open(sname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 圆角图片
     *
     * @param resource
     * @return
     */
    public static Bitmap RoundCornerBitmap(Bitmap resource, int radius) {
        int width = resource.getWidth();
        int height = resource.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(resource, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        float right = width;
        float bottom = height;
        canvas.drawRoundRect(new RectF(0, 0, right, bottom), radius, radius, paint);
        return bitmap;
    }

    /**
     * 对bitmap图片高斯模糊处理
     *
     * @param bkg
     * @param view
     * @return
     */
    public static Bitmap blur(Bitmap bkg, View view) {
        if (bkg == null || view == null) {
            return null;
        }
        int width = (int) (((float) bkg.getWidth()) / 20.0f);
        int height = (int) (((float) bkg.getHeight()) / 20.0f);
        if (width <= 0) {
            width = 1;
        }
        if (height <= 0) {
            height = 1;
        }
        Bitmap overlay = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(((float) (-view.getLeft())) / 30.0f, ((float) (-view.getTop())) / 30.0f);
        canvas.scale(1.0f / 20.0f, 1.0f / 20.0f);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0.0f, 0.0f, paint);
        return FastBlur.doBlur(overlay, (int) 2.0f, true);
    }


    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i,
                    j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                // F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
    }

    /**
     * 得到bitmap的大小
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

    /**
     * 保存图片为JPEG
     *
     * @param bitmap
     * @param path
     */
    public static void saveJPGE_After(Context context, Bitmap bitmap, String path, int quality) {
        File file = new File(path);
        makeDir(file);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
                out.flush();
                out.close();
            }
            updateResources(context, file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeDir(File file) {
        File tempPath = new File(file.getParent());
        if (!tempPath.exists()) {
            tempPath.mkdirs();
        }
    }

    public static void updateResources(Context context, String path) {
        new SingleMediaScanner(context, path, null);
    }


    public static Bitmap createBitmap(Bitmap sourceBitmap, int color) {
        Bitmap newBitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(color);
        Paint paint = new Paint();
        int left = newBitmap.getWidth() / 2 - sourceBitmap.getWidth() / 2;
        if (left < 0) {
            left = 0;
        }
        int top = newBitmap.getHeight() / 2 - sourceBitmap.getHeight() / 2;
        if (top < 0) {
            top = 0;
        }
        canvas.drawBitmap(sourceBitmap, left, top, paint); //将原图使用给定的画笔画到画布上
        return newBitmap;
    }


    /**
     * bitmap中的透明色用白色替换
     *
     * @param bitmap
     * @return
     */
    public static Bitmap changeColor(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] colorArray = new int[w * h];
        int n = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int color = getMixtureWhite(bitmap.getPixel(j, i));
                colorArray[n++] = color;
            }
        }
        return Bitmap.createBitmap(colorArray, w, h, Bitmap.Config.ARGB_8888);
    }

    /**
     * 获取和白色混合颜色
     *
     * @return
     */
    private static int getMixtureWhite(int color) {
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.rgb(getSingleMixtureWhite(red, alpha), getSingleMixtureWhite

                        (green, alpha),
                getSingleMixtureWhite(blue, alpha));
    }

    /**
     * 获取单色的混合值
     *
     * @param color
     * @param alpha
     * @return
     */
    private static int getSingleMixtureWhite(int color, int alpha) {
        int newColor = color * alpha / 255 + 255 - alpha;
        return newColor > 255 ? 255 : newColor;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int newWidth, int newHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, newWidth, newHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        Bitmap newBitmap = compressImage(bitmap, 500);
        if (bitmap != null) {
            bitmap.recycle();
        }
        return newBitmap;
    }

    /**
     * 质量压缩
     *
     * @param image
     * @param maxSize
     */
    public static Bitmap compressImage(Bitmap image, int maxSize) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 80;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        while (os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }

        Bitmap bitmap = null;
        byte[] b = os.toByteArray();
        if (b.length != 0) {
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return bitmap;
    }


    public static Bitmap getRoundedCornerBitmap(Context context, Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),

                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

//        final int color = Color.RED;

        Paint paint = new Paint();

        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        canvas.drawARGB(0, 0, 0, 0);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawRect(new RectF(0, roundPx, bitmap.getWidth(), bitmap.getHeight()), paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;

    }
}
