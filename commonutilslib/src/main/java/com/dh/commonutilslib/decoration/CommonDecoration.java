package com.dh.commonutilslib.decoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dh on 2017/4/10.
 * layoutManager为GridLayoutManager时，item的上下左右间距
 */
public class CommonDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "CommonDecoration";
    private int leftSpace;
    private int midSpace;
    private int rightSpace;
    private boolean isNeedCalculateTop = false;
    private int topSpace;
    private int midTopSpace;
    private int bottomSpace;

    public CommonDecoration(int leftSpace, int midSpace, int rightSpace) {
        this.leftSpace = leftSpace;
        this.midSpace = midSpace;
        this.rightSpace = rightSpace;
    }

    public CommonDecoration(int leftSpace, int midSpace, int rightSpace, int topSpace, int midTopSpace, int bottomSpace) {
        isNeedCalculateTop = true;
        this.leftSpace = leftSpace;
        this.midSpace = midSpace;
        this.rightSpace = rightSpace;
        this.topSpace = topSpace;
        this.midTopSpace = midTopSpace;
        this.bottomSpace = bottomSpace;
    }

    public CommonDecoration(int sameSpace) {
        this.leftSpace = sameSpace;
        this.midSpace = sameSpace;
        this.rightSpace = sameSpace;
    }

    public CommonDecoration(int sameHorizontalSpace, int sameVerticalSpace) {
        isNeedCalculateTop = true;
        this.leftSpace = sameHorizontalSpace;
        this.midSpace = sameHorizontalSpace;
        this.rightSpace = sameHorizontalSpace;
        this.topSpace = sameVerticalSpace;
        this.midTopSpace = sameVerticalSpace;
        this.bottomSpace = sameVerticalSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            int totalSpace = leftSpace + rightSpace + midSpace * (spanCount - 1);
            int itemNeedSpace = totalSpace / spanCount;
            if (parent.getChildAdapterPosition(view) % spanCount == 0) {
                //最左一条
                outRect.left = leftSpace;
                outRect.right = itemNeedSpace - leftSpace;
//                ELog.d(TAG, "0 outrect:" + outRect.toString());
            } else if (parent.getChildAdapterPosition(view) % spanCount == spanCount - 1) {
                //最右一条
                outRect.left = itemNeedSpace - rightSpace;
                outRect.right = rightSpace;
//                ELog.d(TAG, "spanCount - 1 outrect:" + outRect.toString());
            } else {
                outRect.left = itemNeedSpace / 2;
                outRect.right = itemNeedSpace / 2;
//                ELog.d(TAG, "other outrect:" + outRect.toString());
            }
            if (!isNeedCalculateTop)
                return;
            //计算出有几行
            int rows = parent.getAdapter().getItemCount() / spanCount + 1;
            int totalVerticalSpace = topSpace + midTopSpace * (rows - 1) + bottomSpace;
            int rowNeedVerticalSpace = totalVerticalSpace / rows;
            //每一个item处于哪一行
            if (parent.getChildAdapterPosition(view) / spanCount == 0) {
//                    //第一行
                outRect.top = topSpace;
                outRect.bottom = rowNeedVerticalSpace - topSpace;
            } else if (parent.getChildAdapterPosition(view) / spanCount == rows - 1) {
                //最后一行
                outRect.top = topSpace;
                if (topSpace != 0 && bottomSpace != 0) {
                    outRect.bottom = rowNeedVerticalSpace - topSpace - bottomSpace;
                } else {
                    outRect.bottom = rowNeedVerticalSpace - topSpace;
                }
            } else {
                outRect.top = topSpace;
                outRect.bottom = rowNeedVerticalSpace - topSpace;
            }


        }
    }

}
