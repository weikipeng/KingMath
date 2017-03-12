package com.pengjunwei.kingmath.viewholder;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.model.FactorInfo;
import com.pengjunwei.kingmath.mvp.IViewParam;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerViewHolder;
import com.pengjunwei.kingmath.mvp.recyclerview.ILayoutProvider;
import com.pengjunwei.kingmath.sunpower.IViewParamSunPower;

/**
 * Created by WikiPeng on 2017/3/11 16:03.
 */
public class ViewHolderFactor extends BaseRecyclerViewHolder {
    protected TextView mTitle;
    protected TextView mValueText;
    protected EditText mValueEditText;
    protected TextView mUnit;


    public ViewHolderFactor(View itemView, IViewParam viewParam) {
        super(itemView, viewParam);
        initView();
        addEvent();
    }

    protected void addEvent() {
        mValueText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueText.setVisibility(View.GONE);
                mValueEditText.setVisibility(View.VISIBLE);
                mValueEditText.requestFocus();
            }
        });

        mValueEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mValueText.setVisibility(View.VISIBLE);
                    mValueEditText.setVisibility(View.GONE);
                }
            }
        });

        mValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mData == null) {
                    return;
                }
                if (!(mData instanceof FactorInfo)) {
                    return;
                }

                FactorInfo tData    = (FactorInfo) mData;
                String     newValue = mValueEditText.getText().toString();
                if (!TextUtils.isEmpty(newValue)) {
                    tData.value = newValue;
                    mValueText.setText(newValue);

                    if (mViewParam instanceof IViewParamSunPower) {
                        ((IViewParamSunPower) mViewParam).updateFactor(tData);
                    }
                }
            }
        });
    }

    private void initView() {
        mTitle = (TextView) itemView.findViewById(R.id.title);
        mValueText = (TextView) itemView.findViewById(R.id.valueText);
        mValueEditText = (EditText) itemView.findViewById(R.id.value);
        mUnit = (TextView) itemView.findViewById(R.id.unit);
    }

    @Override
    public void onBindViewHolder(int position, Object data) {
        super.onBindViewHolder(position, data);
        if (data == null) {
            return;
        }
        if (!(data instanceof FactorInfo)) {
            return;
        }

        FactorInfo tData = (FactorInfo) data;
        updateView(tData);
    }

    public void updateView(FactorInfo tData) {
        mTitle.setText(tData.name);
        mValueText.setText(String.valueOf(tData.value));
        mValueEditText.setText(String.valueOf(tData.value));
        mUnit.setText(tData.unitText);
    }

    public static class LayoutProvider implements ILayoutProvider {

        @Override
        public BaseRecyclerViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater
                , @NonNull ViewGroup parent, IViewParam viewParam) {
            return new ViewHolderFactor(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_factor, parent, false), viewParam);
        }
    }
}
