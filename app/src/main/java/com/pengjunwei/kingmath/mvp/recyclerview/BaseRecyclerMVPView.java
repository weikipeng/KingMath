package com.pengjunwei.kingmath.mvp.recyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.mvp.BaseMVPView;
import com.pengjunwei.kingmath.mvp.IMVPProvider;

/**
 * Created by WikiPeng on 2017/3/11 15:30.
 */
public class BaseRecyclerMVPView extends BaseMVPView implements IRecyclerView {
    protected RecyclerView mRecyclerView;

    public BaseRecyclerMVPView(IMVPProvider provider) {
        super(provider);
        initView();
        initData();
        addEvent();
    }


    protected void initView() {
        mRecyclerView = provider.findViewById(R.id.recyclerView);
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
