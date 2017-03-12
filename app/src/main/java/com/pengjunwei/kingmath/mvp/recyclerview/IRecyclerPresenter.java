package com.pengjunwei.kingmath.mvp.recyclerview;

import java.util.List;

/**
 * Created by WikiPeng on 2017/3/11 15:34.
 */
public interface IRecyclerPresenter {
    void setRecyclerViewData(List data);

    void setAdapter(BaseRecyclerAdapter adapter);
}
