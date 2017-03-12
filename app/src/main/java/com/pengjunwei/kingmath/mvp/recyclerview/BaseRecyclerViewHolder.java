package com.pengjunwei.kingmath.mvp.recyclerview;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pengjunwei.kingmath.mvp.IViewParam;

/**
 * Created by WikiPeng on 2017/3/11 15:59.
 */
public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    protected Object     mData;
    protected IViewParam mViewParam;

    public BaseRecyclerViewHolder(View itemView, IViewParam viewParam) {
        super(itemView);
        this.mViewParam = viewParam;
    }

    @CallSuper
    public void onBindViewHolder(int position, Object data) {
        this.mData = data;
    }
}
