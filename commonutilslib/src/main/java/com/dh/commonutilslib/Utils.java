package com.dh.commonutilslib;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by THINK on 2017/9/10.
 */

/**
 * Created by Administrator on 2015/3/24.
 */
public class Utils {


    public static SpannableStringBuilder setForegroundSpan(String content, int color, int start, int end) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return builder;
    }

    public static SpannableStringBuilder setFirstLineIndentation2Word(String content) {
        SpannableStringBuilder span = new SpannableStringBuilder("缩进" + content);
        span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return span;
    }

    public static SpannableStringBuilder setBackgroundSpan(String content, int color, int start, int end) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new BackgroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static SpannableStringBuilder setForegroundSpanAndSize(String content, int color, int size, int start, int end) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(size), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return builder;
    }

    /**
     * 找到viewPager当前的fragment
     * 基于FragmentPagerAdapter的实现是有效的，但对于
     * 　　FragmentStatePagerAdapter就无效了
     *
     * @param fragmentActivity
     * @param viewPagerCurrentItem
     */
    public static Fragment findFragmentByTag(FragmentActivity fragmentActivity, int pagerId, int viewPagerCurrentItem) {
        return fragmentActivity.getSupportFragmentManager().findFragmentByTag(
                "android:switcher:" + pagerId + ":" + viewPagerCurrentItem);
    }

    public static Fragment findFragmentByTag(FragmentManager fragmentManager, int pagerId, int viewPagerCurrentItem) {
        return fragmentManager.findFragmentByTag(
                "android:switcher:" + pagerId + ":" + viewPagerCurrentItem);
    }

    /**
     * 检测是否安装了指定应用
     *
     * @param context
     * @param packName
     * @return
     */
    public static boolean isInstalledApp(Context context, String packName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packageInfoList.size(); i++) {
            if (packageInfoList.get(i).packageName.equalsIgnoreCase(packName))
                return true;
        }
        return false;
    }

    /**
     * 获取随机数
     *
     * @param maxNum
     * @param minNum
     * @return
     */
    public static int getRandomNum(int maxNum, int minNum) {
        return (int) (Math.random() * (maxNum - minNum + 1) + minNum);
    }

    public static String formatSize(long size) {
        if (size < 1024) {
            return size + "B";
        } else {
            long s = size / 1024l;
            if (s < 1024) {
                return s + "K";
            } else {
                s = size / 1024 / 1024;
                if (s < 1024) {
                    return s + "M";
                } else {
                    s = size / 1024 / 1024 / 1024;
                    return s + "G";
                }
            }
        }
    }


    /**
     * 格式化容量单位
     *
     * @param size size
     * @return size
     */
    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "G";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "T";
    }

    /**
     * 转化评论的时间
     *
     * @param time 17-05-05 10:30:10
     * @return
     */
    public static String formatCommentTime(String time) {
        String formatTime = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
        try {
            Date date = sdf.parse(time);
            long timeL = date.getTime();
            long delTime = System.currentTimeMillis() - timeL;
            long oneDayTime = 24 * 60 * 60 * 1000l;
            long t = delTime / oneDayTime;
            if (t == 0) {
                //不到一天
                //t：几个小时
                t = delTime / (60 * 60 * 1000l);
                if (t == 0) {
                    //不到一小时
                    //t:几分钟
                    t = delTime / (60 * 1000l);
                    if (t == 0) {
                        //不到一分钟
                        formatTime = "刚刚";
                    } else {
                        formatTime = t + "分钟前";
                    }
                } else {
                    formatTime = t + "小时前";
                }
            } else {
                formatTime = t + "天前";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatTime;
    }

    public static int getHeightFromWidth(Context context, int width, int height) {
        if (width == 0) {
            return 0;
        }
        return (int) (((float) ScreenUtils.getScreenWidth(context)) / (((float) width) / ((float) height)));
    }

    // 获取AppKey
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
            if (TextUtils.isEmpty(apiKey)) {
                apiKey = String.valueOf(metaData.getInt(metaKey));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return apiKey;
    }

    public static String getTopActivityInfo(Context context) {
        ActivityManager manager = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            List<ActivityManager.RunningAppProcessInfo> pis = manager.getRunningAppProcesses();
//            ActivityManager.RunningAppProcessInfo topAppProcess = pis.get(0);
//            if (topAppProcess != null && topAppProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                ELog.i("processName:" + topAppProcess.processName);
//            }
//        } else {
        //getRunningTasks() is deprecated since API Level 21 (Android 5.0)
        List localList = manager.getRunningTasks(1);
        if (localList.size() > 0) {
            ActivityManager.RunningTaskInfo localRunningTaskInfo = (ActivityManager.RunningTaskInfo) localList.get(0);
            ELog.i("topActivity:" + localRunningTaskInfo.topActivity.getClassName());
            return localRunningTaskInfo.topActivity.getClassName();
        }
        return null;
//        }
    }

    /**
     * 判断当前应用程序处于前台还是后台
     */
    public static boolean isAppBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;

    }


    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String testPost(String name, String password, String email) {
        //模拟器使用10.0.2.2代替localhost
        HttpPost httpRequst = new HttpPost("http://10.0.2.2:8080/fresh/register");//创建HttpPost对象
//        HttpPost httpRequst = new HttpPost("http://localhost:8080/fresh/register");//创建HttpPost对象
        String result = "";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("email", email));

        try {
            httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
            httpRequst.addHeader("Content-Type", "text/html");    //这行很重要
            httpRequst.addHeader("charset", HTTP.UTF_8);         //这行很重要
            ELog.d("code--:" + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);//取出应答字符串
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            result = e.getMessage().toString();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            result = e.getMessage().toString();
        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage().toString();
        }
        return result;
    }

//    public static String post(String amount, String bankId) {
//        HttpPost httpRequst = new HttpPost(Cons.url.HOST + Cons.url.SUBMIT);//创建HttpPost对象
//        String result = "";
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("amount", amount));
//        params.add(new BasicNameValuePair("bankId", bankId));
//
//        try {
//            httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
//            ELog.d("code--:" + httpResponse.getStatusLine().getStatusCode());
//            if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                HttpEntity httpEntity = httpResponse.getEntity();
//                result = EntityUtils.toString(httpEntity);//取出应答字符串
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            result = e.getMessage().toString();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//            result = e.getMessage().toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            result = e.getMessage().toString();
//        }
//        return result;
//    }

    public static byte[] get(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
        // 请求超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
        // 读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 40000);
        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] retBuf = null;
        if (response != null && response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {// HttpStatus.SC_OK
            try {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                InputStream inStream = response.getEntity().getContent();
                byte[] buffer = new byte[2048];
                int readedByte = -1;
                while ((readedByte = inStream.read(buffer)) != -1) {
                    output.write(buffer, 0, readedByte);
                }
                inStream.close();
                retBuf = output.toByteArray();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (response == null) {
                ELog.d("response failed!response is null,url=" + url);
            } else {
                ELog.d("response failed!status code=" + response.getStatusLine().getStatusCode() + ",url=" + url);
            }
        }
        client.getConnectionManager().shutdown();
        return retBuf;
    }

    public static Drawable getResourcesDrawable(Context context, String name) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(name, "drawable", context.getPackageName());
        try {
            return ResourcesCompat.getDrawable(res, resourceId, null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 转化距离
     *
     * @param distance 距离为公里
     * @return
     */
    public static String getDistance(double distance) {
        String d = "0m";
        if (distance > 1) {
            d = FloatUtil.formatDouble2String2bits(distance) + "km";
        } else {
            double dd = distance * 1000;
            if (dd > 1) {
                d = String.valueOf(Math.round(dd)) + "m";
            }
        }
        return d;
    }

    public static String formatVideoDuration(long duration) {
        if (duration < 60) {
            return String.format("00:%02d", duration);
        } else if (duration < 60 * 60) {
            long minute = duration / 60;
            long seconds = duration % 60;
            return String.format("%02d:%02d", minute, seconds);
        } else {
            //5*3600=18000
            long hour = duration / (60 * 60);
            long minute = (duration - hour * 60 * 60) / 60;
            long seconds = (duration - hour * 60 * 60) % 60;
            return String.format("%02d:%02d:%02d", hour, minute, seconds);
        }
    }



//    public static void doStartApplicationWithPackageName(Context context, String packagename) {
//
//        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
//        PackageInfo packageinfo = null;
//        try {
//            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);
//
//            if (packageinfo == null) {
//                return;
//            }
//
//            // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
//            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
//            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//            resolveIntent.setPackage(packageinfo.packageName);
//
//            // 通过getPackageManager()的queryIntentActivities方法遍历
//            List<ResolveInfo> resolveinfoList = context.getPackageManager()
//                    .queryIntentActivities(resolveIntent, 0);
//
//            ResolveInfo resolveinfo = resolveinfoList.iterator().next();
//            if (resolveinfo != null) {
//                // packagename = 参数packname
//                String packageName = resolveinfo.activityInfo.packageName;
//                // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
//                String className = resolveinfo.activityInfo.name;
//                // LAUNCHER Intent
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//
//                // 设置ComponentName参数1:packagename参数2:MainActivity路径
//                ComponentName cn = new ComponentName(packageName, className);
//
//                intent.setComponent(cn);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            String toast = "";
//            if (packagename.contains("meipaimv")) {
//                toast = String.format(context.getResources().getString(R.string.s_noapp), context.getResources().getString(R.string.s_app_mp));
//            } else if (packagename.contains("vue")) {
//                toast = String.format(context.getResources().getString(R.string.s_noapp), context.getResources().getString(R.string.s_app_vue));
//
//            } else if (packagename.contains("aweme")) {
//                toast = String.format(context.getResources().getString(R.string.s_noapp), context.getResources().getString(R.string.s_app_xy));
//
//            } else if (packagename.contains("snowcamera")) {
//                toast = String.format(context.getResources().getString(R.string.s_noapp), context.getResources().getString(R.string.s_app_bkj));
//
//            }
//            ToastUtils.showToast(toast);
//            jumpMarket(context, packagename);
//        }
//    }

    /**
     * 根据屏幕宽度与密度计算GridView显示的列数， 最少为三列，并获取Item宽度
     */
    public static int getImageItemWidth(Activity activity) {
        int screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
        int densityDpi = activity.getResources().getDisplayMetrics().densityDpi;
        int cols = screenWidth / densityDpi;
        cols = cols < 3 ? 3 : cols;
        int columnSpace = (int) (2 * activity.getResources().getDisplayMetrics().density);
        return (screenWidth - columnSpace * (cols - 1)) / cols;
    }


    public static boolean checkSpecialChar(String str) {
        // 清除掉所有特殊字符
        ELog.d("tag", "str:" + str);
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
//         m.replaceAll("").trim();
    }



    public static <T> ArrayList<T> parseJsonArr(JSONArray jsonArr, Class classX) throws JSONException {
        //遍历JsonArray对象
        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(jsonArr.toString()).getAsJsonArray();
        Gson gson = new Gson();
        ArrayList<T> list = new ArrayList<>();
        //加强for循环遍历JsonArray
        for (JsonElement user : jsonArray) {
            //使用GSON，直接转成Bean对象
            T holidayItem = (T) gson.fromJson(user, classX);
            list.add(holidayItem);
        }
        return list;
    }


    /**
     * 获取文本高度
     *
     * @param text
     * @param paint
     * @return
     */
    public static float getFontHeight(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        //这样得出的高度会比真实的文本高度高
//        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
//        int height = fm.descent - fm.ascent
        return rect.height();
    }

    /**
     * 获取文本宽度
     *
     * @param text
     * @param paint
     * @return
     */
    public static float getTextWidth(String text, Paint paint) {
        return paint.measureText(text);
    }


    /**
     * 往剪贴板中放入文本
     *
     * @param context
     * @param content
     */
    public static void putTextIntoClipboard(Context context, String content) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        ClipData clipData = ClipData.newPlainText("", content);
        //添加ClipData对象到剪切板中
        clipboardManager.setPrimaryClip(clipData);

    }

    /**
     * 从剪贴板中获取文本
     *
     * @param context
     * @return
     */
    public static String getTextFromClipboard(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //判断剪切版时候有内容
        if (!clipboardManager.hasPrimaryClip())
            return null;
        ClipData clipData = clipboardManager.getPrimaryClip();
        //获取 ClipDescription
        ClipDescription clipDescription = clipboardManager.getPrimaryClipDescription();
        //获取 lable
//        String lable = clipDescription.getLabel().toString();
        //获取 text
        String text = clipData.getItemAt(0).getText().toString();
        return text;
    }


}

