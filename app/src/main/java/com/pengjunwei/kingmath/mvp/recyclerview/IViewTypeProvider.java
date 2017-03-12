package com.pengjunwei.kingmath.mvp.recyclerview;

import android.view.ViewGroup;

import com.pengjunwei.kingmath.mvp.IViewParam;

/**
 * Created by WikiPeng on 2017/3/11 16:11.
 */
public interface IViewTypeProvider {
    int getType(Class<?> typeClass);

    BaseRecyclerViewHolder getViewHolder(ViewGroup parent, int viewType, IViewParam viewParam);

    void register(Class dataClass, Class viewHolderClass, ILayoutProvider layoutProvider);
}
