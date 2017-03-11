package com.pengjunwei.kingmath.viewholder;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.model.FactorInfo;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerViewHolder;
import com.pengjunwei.kingmath.mvp.recyclerview.ILayoutProvider;

/**
 * Created by WikiPeng on 2017/3/11 16:03.
 */
public class ViewHolderFactor extends BaseRecyclerViewHolder {
    protected TextView mTitle;
    protected TextView mValueText;
    protected EditText mValueEditText;
    protected TextView mUnit;


    public ViewHolderFactor(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        mTitle = (TextView) itemView.findViewById(R.id.title);
        mValueText = (TextView) itemView.findViewById(R.id.valueText);
        mValueEditText = (EditText) itemView.findViewById(R.id.value);
        mUnit = (TextView) itemView.findViewById(R.id.unit);
    }

    @Override
    public void onBindViewHolder(int position, Object data) {
        if (data == null) {
            return;
        }
        if (!(data instanceof FactorInfo)) {
            return;
        }

        FactorInfo tData = (FactorInfo) data;
        mTitle.setText(tData.name);
        mValueText.setText(tData.value);
        mValueEditText.setText(tData.value);
        mUnit.setText(tData.unitText);
    }

    public static class LayoutProvider implements ILayoutProvider {

        @Override
        public BaseRecyclerViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            return new ViewHolderFactor(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_factor, parent, false));
        }
    }
}
