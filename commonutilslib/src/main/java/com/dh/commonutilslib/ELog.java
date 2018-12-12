package com.dh.commonutilslib;


import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

/**
 * 日志打印类
 * created by huanghui
 */
public class ELog {
    public static boolean DEBUG = false;

    private static final String LOG_TAG = "com.dh.commonutilslib";

    private static final int LOG_LEVEL = 1;

    // Log.v
    public static void v(String tag, String msg) {
        if (LOG_LEVEL <= 1 && DEBUG) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL <= 1 && DEBUG) {
            android.util.Log.v(tag, msg, tr);
        }
    }

    public static void v(String msg) {
        v(LOG_TAG, msg);
    }

    // Log.d
    public static void d(String tag, String msg) {
        if (LOG_LEVEL <= 2 && DEBUG) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL <= 2 && DEBUG) {
            android.util.Log.d(tag, msg, tr);
        }
    }

    public static void d(String msg) {
        d(LOG_TAG, msg);
    }

    // Log.i
    public static void i(String tag, String msg) {
        if (LOG_LEVEL <= 3 && DEBUG) {
            android.util.Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL <= 3 && DEBUG) {
            android.util.Log.i(tag, msg, tr);
        }
    }

    public static void i(String msg) {
        i(LOG_TAG, msg);
    }

    // Log.w
    public static void w(String tag, String msg) {
        if (LOG_LEVEL <= 4 && DEBUG) {
            android.util.Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL <= 4 && DEBUG) {
            android.util.Log.w(tag, msg, tr);
        }
    }

    public static void w(String msg) {
        w(LOG_TAG, msg);
    }

    /**
     * 打印长字符串
     *
     * @param html
     */
    public static void printLongString(String html) {
        ByteArrayOutputStream output = null;
        int length = 0; // 每一次读取的长度
        char[] buffer = new char[2048]; // 设缓冲最大值为2048字符
        // 字符串转为字符流
        BufferedReader br = new BufferedReader(new StringReader(html));
        try {
            while ((length = br.read(buffer)) != -1) { // 若读到的不是末尾
                e("html", new String(buffer, 0, length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showLargeLog(String logContent, int showLength, String tag) {
        if (logContent.length() > showLength) {
            String show = logContent.substring(0, showLength);
            e(tag, show);
            /*剩余的字符串如果大于规定显示的长度，截取剩余字符串进行递归，否则打印结果*/
            if ((logContent.length() - showLength) > showLength) {
                String partLog = logContent.substring(showLength, logContent.length());
                showLargeLog(partLog, showLength, tag);
            } else {
                String printLog = logContent.substring(showLength, logContent.length());
                e(tag, printLog);
            }
        } else {
            e(tag, logContent);
        }
    }

    public static void logStackTrace() {
        StackTraceElement st[] = Thread.currentThread().getStackTrace();
        int length = st.length;
        for (int i = 0; i < length; i++) {
            ELog.d("调用栈" + i + ":" + st[i]);
        }
    }

    // Log.e
    public static void e(String tag, String msg) {
        if (LOG_LEVEL <= 5 && DEBUG) {
            android.util.Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL <= 5 && DEBUG) {
            android.util.Log.e(tag, msg, tr);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            e(LOG_TAG, msg);
        }
    }

    public static void systemOut(String msg) {
        if (DEBUG) {
            System.out.println(LOG_TAG + "..." + msg);
        }
    }

    public static void t(String msg, Throwable tr) {
        e(LOG_TAG, msg, tr);
    }

    public static void f(Context context, String msg, String filename, boolean append) {
        writeToFile(context, msg, filename, append);
    }

    public static void f(Context context, String msg, String filename) {
        writeToFile(context, msg, filename, false);
    }

    public static void f(Context context, String msg) {
        writeToFile(context, msg, "app.log", true);
    }

    public static void writeToFile(Context context, String msg, String filename, boolean append) {
        try {
            BufferedWriter bos = new BufferedWriter(new FileWriter(
                    FileUtil.getLogPath(context) + "/" + filename, append));
            bos.write(msg + "\n");
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
