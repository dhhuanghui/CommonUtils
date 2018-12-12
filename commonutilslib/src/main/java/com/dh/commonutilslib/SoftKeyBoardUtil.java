package com.dh.commonutilslib;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘工具
 */
public class SoftKeyBoardUtil {

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyBroad(Context context, EditText editText) {
        InputMethodManager mgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 隐藏软键盘，不需要用EditText
     */
    public static void hideSoftKeyBroadNotEditText(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * edittext获取焦点
     *
     * @param editText
     */
    public static void requestEditTextFocus(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param editText
     */
    public static void showSoftKeyBroad(Context context, EditText editText) {
        InputMethodManager mgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // only will trigger it if no physical keyboard is open
        mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 显示软键盘，不需要EditText
     *
     * @param context
     */
    public static void showSoftKeyBroadNotET(Context context) {
        InputMethodManager imm = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
