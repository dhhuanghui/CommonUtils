package com.dh.commonutilslib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Created by THINK on 2017/3/29.
 */

public class FileUtil {
    private static final String INDIVIDUAL_DIR_NAME = "video-cache";

    /**
     * 日志目录
     *
     * @param c
     * @return
     */
    public static String getLogPath(Context c) {
        return getAbslutePath(getCachePath(c) + "/" + "logs");
    }

    private static String getAbslutePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取缓存路径
     *
     * @param c
     * @return
     */
    public static String getCachePath(Context c) {
        String dirPath = c.getFilesDir().getAbsolutePath();
        String storageState = Environment.getExternalStorageState();
        boolean canWrite = Environment.getExternalStorageDirectory().canWrite();
        if (Environment.MEDIA_MOUNTED.equals(storageState) && canWrite) {
            dirPath = Environment.getExternalStorageDirectory().getPath() + dirPath;
        }
        dirPath += "/cache";
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String getCacheDir(Context context) {
        return FileUtil.createRootPath(context) + "/cache";
    }

    public static String getMemoryCachePath(Context c) {
        return c.getFilesDir().getAbsolutePath() + "/cache";
    }

    /**
     * 删除data/data/com.wtmbuy.wtmbuylocalmarker/files/cache中的图片文件
     *
     * @param file
     */
    public static void deleteCacheImageFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                if (childFiles[i].isDirectory()) {
                    continue;
                }
                delete(childFiles[i]);
            }
        }
    }

    public static void delete(String path) {
        delete(new File(path));
    }

    public static void delete(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
        }
    }


    /**
     * 递归创建文件夹
     *
     * @param file
     * @return 创建失败返回""
     */
    public static String createFile(File file) {
        try {
            if (file.getParentFile().exists()) {
                ELog.i("----- 创建文件" + file.getAbsolutePath());
                file.createNewFile();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                file.createNewFile();
                ELog.i("----- 创建文件1" + file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 递归创建文件夹
     *
     * @param dirPath
     * @return 创建失败返回""
     */
    public static String createDir(String dirPath) {
        try {
            File file = new File(dirPath);
            if (file.getParentFile().exists()) {
                ELog.i("----- 创建文件夹" + file.getAbsolutePath());
                file.mkdir();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                ELog.i("----- 创建文件夹2" + file.getAbsolutePath());
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dirPath;
    }

    /**
     * 创建根缓存目录
     *
     * @return
     */
    public static String createRootPath(Context context) {
        String cacheRootPath = "";
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/cache
            File externalCacheDir = context.getExternalCacheDir();
            if (externalCacheDir == null) {
                cacheRootPath = context.getCacheDir().getPath();
            } else {
                cacheRootPath = externalCacheDir.getPath();
            }
        } else {
            // /data/data/<application package>/cache
            cacheRootPath = context.getCacheDir().getPath();
        }
        return cacheRootPath;
    }

    public static boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 文件拷贝
     *
     * @param src  源文件
     * @param desc 目的文件
     */
    public static void fileChannelCopy(File src, File desc) {
        //createFile(src);
        createFile(desc);
        FileInputStream fi = null;
        FileOutputStream fo = null;
        try {
            fi = new FileInputStream(src);
            fo = new FileOutputStream(desc);
            FileChannel in = fi.getChannel();//得到对应的文件通道
            FileChannel out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fo != null) fo.close();
                if (fi != null) fi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void fileRename(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        String parentPath = file.getParent();
        String renameTo = parentPath + File.separator + "moolink_temp.mp4";
        file.renameTo(new File(renameTo));
    }


    /**
     * 删除指定文件，如果是文件夹，则递归删除
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static boolean deleteFileOrDirectory(File file) throws IOException {
        try {
            if (file != null && file.isFile()) {
                return file.delete();
            }
            if (file != null && file.isDirectory()) {
                File[] childFiles = file.listFiles();
                // 删除空文件夹
                if (childFiles == null || childFiles.length == 0) {
                    return file.delete();
                }
                // 递归删除文件夹下的子文件
                for (int i = 0; i < childFiles.length; i++) {
                    deleteFileOrDirectory(childFiles[i]);
                }
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void writeFile(String filePathAndName, String fileContent) {
        try {
            OutputStream outstream = new FileOutputStream(filePathAndName);
            OutputStreamWriter out = new OutputStreamWriter(outstream);
            out.write(fileContent);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将内容写入文件
     *
     * @param filePath eg:/mnt/sdcard/demo.txt
     * @param content  内容
     * @param isAppend 是否追加
     */
    public static void writeFile(String filePath, String content, boolean isAppend) {
        ELog.i("save:" + filePath);
        try {
            FileOutputStream fout = new FileOutputStream(filePath, isAppend);
            byte[] bytes = content.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件夹大小
     *
     * @return
     * @throws Exception
     */
    public static long getFolderSize(String dir) throws Exception {
        File file = new File(dir);
        if (!file.exists()) {
            return 0;
        }
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i].getAbsolutePath());
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileLen 单位B
     * @return
     */
    public static String formatFileSizeToString(long fileLen) {
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString = "";
        if (fileLen < 1024) {
            fileSizeString = df.format((double) fileLen) + "B";
        } else if (fileLen < 1048576) {
            fileSizeString = df.format((double) fileLen / 1024) + "K";
        } else if (fileLen < 1073741824) {
            fileSizeString = df.format((double) fileLen / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileLen / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static String getCharset(String fileName) {
        BufferedInputStream bis = null;
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            bis = new BufferedInputStream(new FileInputStream(fileName));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.mark(0);
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80 - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return charset;
    }

    public static String getCharset1(String fileName) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
        int p = (bin.read() << 8) + bin.read();

        String code;
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        return code;
    }

    public static String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        //读入文件
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file.getAbsolutePath());
            byte[] buffer = new byte[1024];//1K
            //读大于1K的文件
            int length = 0;
            while (-1 != (length = fis.read(buffer))) {
                sb.append(new String(buffer, 0, length));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 图片保存到SD卡,图片不带后缀
     *
     * @param bitmap
     * @return
     */
    public static String saveToSdCardWithoutSuffix(Context context, Bitmap bitmap) {
        String imageUrl = FileUtil.getCachePath(context) + "/image/" + System.currentTimeMillis() + "-";
        File file = new File(imageUrl);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    /**
     * Bitmap转File保存
     *
     * @param context
     * @param bitmap
     * @return
     */
    public static File saveBitmapFile(Activity context, Bitmap bitmap) {
        if (bitmap == null) {
            Log.e("++++++bitmap++++", "bitmapNull");
            return null;
        }
        // 指定路径
        String headDir = FileUtil.getCachePath(context) + "/image/";
        File dirFile = new File(headDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        String headPath = headDir + System.currentTimeMillis() + ".png";
        // 对两个文件夹进行判断是否存在，不存在就要创建
        File file = new File(headPath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File getIndividualCacheDirectory(Context context) {
        File cacheDir = getCacheDirectory(context, true);
        return new File(cacheDir, INDIVIDUAL_DIR_NAME);
    }

    public static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens
            externalStorageState = "";
        }
        if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            ELog.w("Can't define system cache directory! '" + cacheDirPath + "%s' will be used.");
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                ELog.w("Unable to create external cache directory");
                return null;
            }
        }
        return appCacheDir;
    }

    public static final String POSTFIX = ".JPEG";
    public static final String APP_NAME = "moolink";
    public static final String CAMERA_PATH = "/" + APP_NAME + "/CameraImage/";
    public static final String CROP_PATH = "/" + APP_NAME + "/CropImage/";

    public static File createCropFile(Context context) {
        return createMediaFile(context, CROP_PATH);
    }

    private static File createMediaFile(Context context, String parentPath) {
        String state = Environment.getExternalStorageState();
        File rootDir = state.equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory() : context.getCacheDir();

        File folderDir = new File(rootDir.getAbsolutePath() + parentPath);
        if (!folderDir.exists() && folderDir.mkdirs()) {

        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String fileName = APP_NAME + "_" + timeStamp + "";
        File tmpFile = new File(folderDir, fileName + POSTFIX);
        return tmpFile;
    }


    /**
     * 拷贝数据库文件到本地查看
     *
     * @param context
     * @param dbName
     */
    public static void copyDBFileToLocal(Context context, String dbName) {
        //找到文件的路径  /data/data/包名/databases/数据库名称
        File dbFile = new File(Environment.getDataDirectory().getAbsolutePath() + "/data/" + context.getPackageName() + "/databases/" + dbName);
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            String dbFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/copy.db";
            File file = new File(dbFilePath);
            if (file.exists()) {
                file.delete();
            }
            //文件复制到sd卡中
            fis = new FileInputStream(dbFile);
            fos = new FileOutputStream(dbFilePath);
            int len = 0;
            byte[] buffer = new byte[2048];
            while (-1 != (len = fis.read(buffer))) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭数据流
            try {
                if (fos != null) fos.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
