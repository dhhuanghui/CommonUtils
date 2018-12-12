package com.dh.commonutilslib;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;


public final class ToastUtils {

//    private static Toast toast;

//    public static void showToast(Context context, String text) {
//        if (toast != null) {
//            toast.cancel();
//        }
//        toast = new Toast(context);
//        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
//        TextView tvText = (TextView) view.findViewById(R.id.tv_toast);
//        tvText.setText(text);
//        // toast.setBackground();
//        toast.setView(view);
//        toast.setGravity(Gravity.TOP, 0, 0);
//        if (text.length() < 10) {
//            toast.setDuration(Toast.LENGTH_SHORT);
//        } else {
//            toast.setDuration(Toast.LENGTH_LONG);
//        }
//        toast.show();
//    }

    private ToastUtils() {
    }

//    public static void showToast(Context context, @StringRes int resId) {
//        showToast(BaseApplication.getInstance(), BaseApplication.getInstance().getString(resId));
//    }

    public static void showToast(Context context, @StringRes int resId) {
        Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG).show();
//        showToast(BaseApplication.getInstance(), BaseApplication.getInstance().getString(resId));
    }

    public static void showToast(Context context, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
//        showToast(BaseApplication.getInstance(), msg);
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

//    public static void realese(){
//        if (toast!=null) {
//            toast.cancel();
//            toast=null;
//        }
//    }
}
