package com.pengjunwei.kingmath.mvp.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.pengjunwei.kingmath.mvp.IViewParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by WikiPeng on 2017/3/11 15:58.
 */
public class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    protected IViewTypeProvider mTypeProvider;
    protected List<Object>      mDataList;
    protected IViewParam        mViewParam;


    public BaseRecyclerAdapter() {
        this(null);
    }

    public BaseRecyclerAdapter(IViewParam viewParam) {
        mDataList = new ArrayList<>();
        this.mViewParam = viewParam;
    }

    public void setTypeProvider(IViewTypeProvider provider) {
        mTypeProvider = provider;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mTypeProvider.getViewHolder(parent, viewType, mViewParam);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if (position >= 0 && position < mDataList.size()) {
            holder.onBindViewHolder(position, mDataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getTypeProvider().getType(mDataList.get(position).getClass());
    }

    public void clear() {
        mDataList.clear();
    }

    public void add(Collection dataList) {
        mDataList.addAll(dataList);
    }

    public IViewTypeProvider getTypeProvider() {
        if (mTypeProvider == null) {
            mTypeProvider = ViewTypeProvider.getInstance();
        }
        return mTypeProvider;
    }
}
