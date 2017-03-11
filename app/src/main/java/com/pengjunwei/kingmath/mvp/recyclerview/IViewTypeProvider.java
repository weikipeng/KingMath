package com.pengjunwei.kingmath.mvp.recyclerview;

import android.view.ViewGroup;

/**
 * Created by WikiPeng on 2017/3/11 16:11.
 */
public interface IViewTypeProvider {
    int getType(Class<?> typeClass);

    BaseRecyclerViewHolder getViewHolder(ViewGroup parent, int viewType);

    void register(Class dataClass, Class viewHolderClass);
}
