package com.pengjunwei.kingmath.mvp.recyclerview;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by WikiPeng on 2017/3/11 17:52.
 */
public interface ILayoutProvider {
    BaseRecyclerViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent);
}
