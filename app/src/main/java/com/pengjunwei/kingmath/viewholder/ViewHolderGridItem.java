package com.pengjunwei.kingmath.viewholder;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.model.FactorInfo;
import com.pengjunwei.kingmath.model.ResultShowInfo;
import com.pengjunwei.kingmath.mvp.IViewParam;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerViewHolder;
import com.pengjunwei.kingmath.mvp.recyclerview.ILayoutProvider;

/**
 * Created by WikiPeng on 2017/3/11 16:03.
 */
public class ViewHolderGridItem extends BaseRecyclerViewHolder {
    protected TextView mValueText;
    protected TextView mUnit;


    public ViewHolderGridItem(View itemView, IViewParam viewParam) {
        super(itemView, viewParam);
        initView();
    }

    private void initView() {
        mValueText = (TextView) itemView.findViewById(R.id.valueText);
        mUnit = (TextView) itemView.findViewById(R.id.unit);
    }

    @Override
    public void onBindViewHolder(int position, Object data) {
        super.onBindViewHolder(position, data);
        if (data == null) {
            return;
        }
        if (!(data instanceof ResultShowInfo)) {
            return;
        }

        ResultShowInfo tData = (ResultShowInfo) data;
        updateView(tData);
    }

    public void updateView(ResultShowInfo tData) {
        mValueText.setText(String.valueOf(tData.value));
        mUnit.setText(tData.unitText);
    }

    public static class LayoutProvider implements ILayoutProvider {

        @Override
        public BaseRecyclerViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater
                , @NonNull ViewGroup parent, IViewParam viewParam) {
            return new ViewHolderGridItem(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_result_show, parent, false), viewParam);
        }
    }

    public static class Data extends ResultShowInfo {

        public Data(String fieldName, String name, Object value, String unitText) {
            super(fieldName, name, value, unitText);
        }
    }

}
