package com.dh.commonutilslib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.dh.commonutilslib.enums.DrawablePosition;
import com.dh.commonutilslib.interfaces.OnItemUpdateListener;

/**
 * Created by Administrator on 2015/7/16.
 */
public class UIViewUtil {
    /**
     * 设置View的显示和隐藏
     *
     * @param view
     * @param v
     */
    public static void setViewVisible(View view, int v) {
        if (view.getVisibility() != v) {
            view.setVisibility(v);
        }
    }

    public static void setViewSelect(View view, boolean b) {
        if (view.isSelected() != b) {
            view.setSelected(b);
        }
    }

    public static void setViewChecked(CheckBox view, boolean b) {
        if (view.isChecked() != b) {
            view.setChecked(b);
        }
    }

    public static void setViewEnable(View view, boolean b) {
        if (view.isEnabled() != b) {
            view.setEnabled(b);
        }
    }

    public static void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null && view.getVisibility() != View.GONE) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    public static void invisible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null && view.getVisibility() != View.INVISIBLE) {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public static void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null && view.getVisibility() != View.VISIBLE) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    public static void goneNoNetLayout(View noNetLayout) {
        if (noNetLayout != null && noNetLayout.getVisibility() == View.VISIBLE) {
            noNetLayout.setVisibility(View.GONE);
        }
    }

    public static void setTextViewDrawable(Context context, TextView textView, int resId, DrawablePosition drawablePosition) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (drawablePosition) {
            case LEFT:
                textView.setCompoundDrawables(drawable, null, null, null);
                break;
            case TOP:
                textView.setCompoundDrawables(null, drawable, null, null);
                break;
            case RIGHT:
                textView.setCompoundDrawables(null, null, drawable, null);
                break;
            case BOTTOM:
                textView.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }

    public static boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    public static boolean isGone(View view) {
        return view.getVisibility() == View.GONE;
    }

    /**
     * 更新listView或者gridView的Item
     *
     * @param viewGroup
     * @param currentPosition
     * @param oldPosition     上一次选中的位置，假如等于-1，则不使用
     * @param listener
     */
    public static void updateGridView(ViewGroup viewGroup, int currentPosition, int oldPosition, OnItemUpdateListener listener) {
        if (viewGroup instanceof GridView || viewGroup instanceof ListView) {
            AbsListView absListView = ((AbsListView) viewGroup);
            int firstVisiblePos = absListView.getFirstVisiblePosition();
            int lastVisiblePos = absListView.getLastVisiblePosition();
            if (oldPosition != -1 && oldPosition >= firstVisiblePos
                    && oldPosition <= lastVisiblePos && oldPosition != currentPosition) {
                View viewOld = absListView.getChildAt(oldPosition - firstVisiblePos);
                if (viewOld != null && viewOld.getTag() != null) {
                    if (listener != null) {
                        listener.onUpdateOld(viewOld, oldPosition);
                    }
                }
            }
            if (currentPosition < firstVisiblePos || currentPosition > lastVisiblePos) return;
            View view = absListView.getChildAt(currentPosition - firstVisiblePos);
            if (view != null && view.getTag() != null) {
                if (listener != null) {
                    listener.onUpdateCurrent(view, currentPosition);
                }
            }
        }
    }

//    /**
//     * 初始化更多Item的popupWindow
//     *
//     * @param context
//     * @param strings
//     * @param windowDrawable
//     * @param listener
//     */
//    public static MoreItemPopupWindow initMoreItemPopupWindow(Context context, String[] strings,
//                                                              Drawable windowDrawable, AdapterView.OnItemClickListener listener) {
//        MoreItemPopupWindow moreItemPopupWindow = new MoreItemPopupWindow(context);
//        final List<Map<String, String>> moreList = moreItemPopupWindow.initStringTitleData(strings);
//        moreItemPopupWindow.initPopupWindow(moreList, windowDrawable);
//        moreItemPopupWindow.setOnItemClickListener(listener);
//        return moreItemPopupWindow;
//    }

//    public static void setRightViewParmas(HeaderView headerView, View.OnClickListener listenter) {
//        TextView tvRight = (TextView) headerView.findViewById(R.id.tv_right);
//        headerView.setRightSingleVisible();
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvRight.getLayoutParams();
//        layoutParams.width = Util.dip2Px(25);
//        layoutParams.height = Util.dip2Px(25);
//        tvRight.setLayoutParams(layoutParams);
//        tvRight.setPadding(0, 0, 0, 0);
//        tvRight.setText("?");
//        tvRight.setBackgroundResource(R.drawable.shape_circle);
//        tvRight.setOnClickListener(listenter);
//    }

    public static boolean imageViewReused(ImageView imageView, String imageUri) {
        String path = (String) imageView.getTag();
        if (path != null && path.equals(imageUri)) {
            return true;
        }
        return false;
    }
}
