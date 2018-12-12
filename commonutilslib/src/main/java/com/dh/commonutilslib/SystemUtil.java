package com.dh.commonutilslib;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

public class SystemUtil {

    /**
     * 获取当前版本
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
            return verName;
        } catch (Exception e) {
            return "0.0.0";
        }

    }

    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            return versionCode;
        } catch (Exception e) {
            return versionCode;
        }

    }


    /**
     * 获得设备ID，如果设备ID为空，则获得UUID
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        if (null == context) {
            return saveDeviceId(context);
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null == telephonyManager) {
            return saveDeviceId(context);
        }
        String deviceId = telephonyManager.getDeviceId();
        if (deviceId != null && !TextUtils.isEmpty(deviceId.trim())) {
            return deviceId;
        } else {
            return saveDeviceId(context);
        }
    }

    private static String saveDeviceId(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String deviceId = sp.getString("deviceId", "");
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = UUID.randomUUID().toString();
            sp.edit().putString("deviceId", deviceId).commit();
            return deviceId;
        }
        return deviceId;
    }

    public static boolean isHighVersion() {
        return Build.VERSION.SDK_INT >= 14;
    }

    /**
     * 是android8.0及以上系统
     *
     * @return
     */
    public static boolean isAndroid8() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    public static boolean isAndroid7() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static boolean isAndroid6() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * @return 判断sdcard是否ok
     */
    public static boolean isSdcardAvailable() {
        String externalStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(externalStorageState);

    }


    /**
     * 获取本地的ip地址
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return "";
    }


    /**
     * 判断是否是android4.4.2系统
     *
     * @return
     */
    public static boolean isAndroid4_4_2() {
        return Build.VERSION.RELEASE.equals("4.4.2");
    }

    public static String getAndroidSystemVersion() {
        return Build.VERSION.RELEASE;
    }


    /**
     * 调起拨号盘
     *
     * @param context
     * @param phone
     */
    public static void dialPhone(Context context, String phone) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
    }

    /**
     * 跳转到系统浏览器
     *
     * @param context
     * @param url
     */
    public static void jumpSystemBrowser(Context context, String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showToast(context, "没有安装浏览器");
        }
    }


    /**
     * 获取手机uuid
     *
     * @param context
     * @return
     */
    public static String getMyUUID(Context context) {
        String deviceId = SharedPreferencesUtil.getInstance().getString(Constants.KEY_DEVICE_ID);
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        //md5加密
        uniqueId = MD5.encryptData(uniqueId.getBytes());
        SharedPreferencesUtil.getInstance().putString(Constants.KEY_DEVICE_ID, uniqueId);
        return uniqueId;
    }


    /**
     * 获取当前音乐音量的的百分比
     *
     * @param context
     * @return
     */
    public static int getVolumePercent(Context context) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 3
        int current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        ELog.e("tag", "音乐音量值：" + max + "-" + current);
        int percent = current * 100 / max;
        return percent;
//        int max = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);// 1
//        int current = am.getStreamVolume(AudioManager.STREAM_SYSTEM);
//        ELog.d("service", "系统音量值：" + max + "-" + current);
//        max = am.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);// 0
//        current = am.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
//        ELog.e("service", "通话音量值：" + max + "-" + current);
//        max = am.getStreamMaxVolume(AudioManager.STREAM_RING);// 2
//        current = am.getStreamVolume(AudioManager.STREAM_RING);
//        ELog.e("service", "系统铃声值：" + max + "-" + current);
//
//        max = am.getStreamMaxVolume(AudioManager.STREAM_ALARM);// 4
//        current = am.getStreamVolume(AudioManager.STREAM_ALARM);
//        ELog.e("service", "闹铃音量值：" + max + "-" + current);
//
//        max = am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);// 5
//        current = am.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
//        ELog.e("service", "提示声音音量值：" + max + "-" + current);

    }


    /**
     * op 的值是 0 ~ 47，其中0代表粗略定位权限，1代表精确定位权限，24代表悬浮窗权限。（具体可以看看android源码在android.app下就有个AppOpsManager类）
     * 返回 0 就代表有权限，1代表没有权限，-1函数出错啦
     *
     * @param context
     * @param op
     * @return
     */
    public static int checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            Object object = context.getSystemService("appops");
            Class c = object.getClass();
            try {
                Class[] cArg = new Class[3];
                cArg[0] = int.class;
                cArg[1] = int.class;
                cArg[2] = String.class;
                Method lMethod = c.getDeclaredMethod("checkOp", cArg);
                return (Integer) lMethod.invoke(object, op, Binder.getCallingUid(), context.getPackageName());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * android5.0以下的机型
     *
     * @return
     */
    public static boolean isBelowAndroid5() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        return true;
    }

    /**
     * 跳转到应用市场
     *
     * @param context
     * @param packageName
     */
    public static void jumpMarket(Context context, String packageName) {
        try {
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            ToastUtils.showToast(context, "没有找到应用市场");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static boolean isScreenOn(Context context) {
        //            KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
//            ELog.d("dh", "km != null :" + (km != null));
//            ELog.d("dh", "km.inKeyguardRestrictedInputMode():" + (km.inKeyguardRestrictedInputMode()));
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//            boolean isLocked = false;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
//                isLocked = !powerManager.isInteractive();
//            } else {
        //noinspection deprecation
//            isLocked = !powerManager.isScreenOn();
//            }
//            ELog.d("dh", "isLocked:" + isLocked);
        return powerManager.isScreenOn();
    }

    /**
     * 获取进程号对应的进程名
     * getProcessName(android.os.Process.myPid())
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 启动qq
     *
     * @param context
     * @param qq
     */
    public static void startQQ(Context context, String qq) {
        try {
            //第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
            String url = String.format("mqqwpa://im/chat?chat_type=wpa&uin=%1$s", qq);//uin是发送过去的qq号码
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToast(context, "请检查是否安装QQ");
        }
    }

}
