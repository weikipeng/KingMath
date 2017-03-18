package com.pengjunwei.kingmath.mvp.recyclerview;

import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;

import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.mvp.IMVPProvider;
import com.pengjunwei.kingmath.mvp.IPresenter;

/**
 * Created by WikiPeng on 2017/3/11 15:30.
 */
public class BaseRecyclerGridMVPView extends BaseRecyclerMVPView implements IRecyclerView {

    public BaseRecyclerGridMVPView(IMVPProvider provider) {
        this(provider, R.id.recyclerView);
    }

    public BaseRecyclerGridMVPView(IMVPProvider provider, @IdRes int recyclerViewId) {
        this(provider, recyclerViewId, null);
    }

    public <T extends IPresenter> BaseRecyclerGridMVPView(IMVPProvider provider, T presenter) {
        this(provider, R.id.recyclerView, presenter);
    }

    public <T extends IPresenter> BaseRecyclerGridMVPView(IMVPProvider provider, int recyclerViewId, T presenter) {
        super(provider, recyclerViewId, presenter);
    }

    protected void initData() {
        GridLayoutManager layoutManager =
                new GridLayoutManager(provider.getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
