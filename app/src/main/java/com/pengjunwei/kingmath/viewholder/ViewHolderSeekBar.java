package com.pengjunwei.kingmath.viewholder;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.mvp.IViewParam;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerViewHolder;
import com.pengjunwei.kingmath.mvp.recyclerview.ILayoutProvider;
import com.xw.repo.BubbleSeekBar;

/**
 * Created by WikiPeng on 2017/3/18 11:46.
 */
public class ViewHolderSeekBar extends BaseRecyclerViewHolder {

    protected TextView mSelfProfit;

    protected BubbleSeekBar mSeekBar;

    protected TextView mSeekBarPercent;

    protected int mOldProgress;

    public ViewHolderSeekBar(View itemView, IViewParam viewParam) {
        super(itemView, viewParam);
        initView();
    }

    private void initView() {

    }

    @Override
    public void onBindViewHolder(int position, Object data) {
        super.onBindViewHolder(position, data);
        if (data == null) {
            return;
        }
        if (!(data instanceof Data)) {
            return;
        }

        Data tData = (Data) data;
    }

    public static class LayoutProvider implements ILayoutProvider {

        @Override
        public BaseRecyclerViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater
                , @NonNull ViewGroup parent, IViewParam viewParam) {
            return new ViewHolderSeekBar(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seekbar, parent, false), viewParam);
        }
    }

    public static class Data {
        public String name;
        public int    percent;

    }
}
