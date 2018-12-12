package com.dh.commonutilslib.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonRecyclerAdapter<T> extends MultiItemTypeAdapter<T> {
	protected Context mContext;
	protected int mLayoutId;
	protected List<T> mDatas;
	protected LayoutInflater mInflater;

	public CommonRecyclerAdapter(final Context context, final int layoutId, List<T> datas) {
		super(context, datas);
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mLayoutId = layoutId;
		mDatas = datas;

		addItemViewDelegate(new ItemViewDelegate<T>() {
			@Override
			public int getItemViewLayoutId() {
				return layoutId;
			}

			@Override
			public boolean isForViewType(T item, int position) {
				return true;
			}

			@Override
			public void convert(RecyclerViewHolder holder, T t, int position) {
				CommonRecyclerAdapter.this.convert(holder, t, position);
			}
		});
	}

	protected abstract void convert(RecyclerViewHolder holder, T t, int position);

	public void addData(int position, T value) {
		mDatas.add(position, value);
		notifyItemInserted(position);
	}

	public void removeData(int position) {
		mDatas.remove(position);
		notifyItemRemoved(position);
	}
}
