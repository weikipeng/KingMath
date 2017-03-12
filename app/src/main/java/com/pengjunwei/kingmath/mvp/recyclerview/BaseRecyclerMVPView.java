package com.pengjunwei.kingmath.mvp.recyclerview;

import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.mvp.BaseMVPView;
import com.pengjunwei.kingmath.mvp.IMVPProvider;
import com.pengjunwei.kingmath.mvp.IPresenter;

/**
 * Created by WikiPeng on 2017/3/11 15:30.
 */
public class BaseRecyclerMVPView extends BaseMVPView implements IRecyclerView {
    protected RecyclerView mRecyclerView;
    protected int          mRecyclerViewId;

    public BaseRecyclerMVPView(IMVPProvider provider) {
        this(provider, R.id.recyclerView);
    }

    public BaseRecyclerMVPView(IMVPProvider provider, @IdRes int recyclerViewId) {
        this(provider, recyclerViewId, null);
    }

    public <T extends IPresenter> BaseRecyclerMVPView(IMVPProvider provider, T presenter) {
        this(provider, R.id.recyclerView, presenter);
    }

    public <T extends IPresenter> BaseRecyclerMVPView(IMVPProvider provider, int recyclerViewId, T presenter) {
        super(provider, presenter);
        this.mRecyclerViewId = recyclerViewId;
        initView();
        initData();
        addEvent();
    }


    protected void initView() {
        mRecyclerView = provider.findViewById(mRecyclerViewId);
    }

    protected void initData() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(provider.getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    protected void addEvent() {

    }


    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
