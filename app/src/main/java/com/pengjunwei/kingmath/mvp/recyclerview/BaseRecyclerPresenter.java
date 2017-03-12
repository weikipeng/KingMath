package com.pengjunwei.kingmath.mvp.recyclerview;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;

import com.pengjunwei.kingmath.mvp.BasePresenter;

import java.util.List;

/**
 * Created by WikiPeng on 2017/3/11 15:34.
 */
public class BaseRecyclerPresenter extends BasePresenter implements IRecyclerPresenter {
    protected BaseRecyclerAdapter mAdapter;

    protected int mRecyclerViewId;

    public BaseRecyclerPresenter(Activity activity) {
        super(activity);
    }

    public BaseRecyclerPresenter(View view) {
        super(view);
    }

    public BaseRecyclerPresenter(View view, @IdRes int recyclerViewId) {
        super(view);
        mRecyclerViewId = recyclerViewId;
    }

    @Override
    public void setRecyclerViewData(List data) {
        mAdapter.clear();
        mAdapter.add(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(BaseRecyclerAdapter adapter) {
        mAdapter = adapter;
        ((IRecyclerView) mvpView).setAdapter(mAdapter);
    }
}
