package com.pengjunwei.kingmath.viewholder;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.model.FactorInfo;
import com.pengjunwei.kingmath.model.ResultShowInfo;
import com.pengjunwei.kingmath.mvp.IPresenter;
import com.pengjunwei.kingmath.mvp.IViewParam;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerAdapter;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerMVPView;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerPresenter;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerViewHolder;
import com.pengjunwei.kingmath.mvp.recyclerview.ILayoutProvider;
import com.pengjunwei.kingmath.mvp.recyclerview.IRecyclerPresenter;
import com.pengjunwei.kingmath.sunpower.IViewParamSunPower;
import com.pengjunwei.kingmath.sunpower.SunPowerAdapter;

import java.util.List;

/**
 * Created by WikiPeng on 2017/3/12 16:59.
 */
public class ViewHolderItem3 extends BaseRecyclerViewHolder {
    protected TextView   mTitle;
    protected IPresenter mRecyclerPresenter;

    protected View     mBottomLine;
    protected View     mBottomLayout;
    protected TextView mValueText;
    protected TextView mUnit;


    public ViewHolderItem3(View itemView, IViewParam viewParam) {
        super(itemView, viewParam);
        initView();

        addEvent();
    }

    protected void addEvent() {
        if (!(mViewParam instanceof IViewParamSunPower)) {
            return;
        }
        if (!((IViewParamSunPower) mViewParam).isEditable()) {
            return;
        }
    }

    private void initView() {
        mTitle = (TextView) itemView.findViewById(R.id.name);

        mBottomLine = itemView.findViewById(R.id.bottomCutLine);
        mBottomLayout = itemView.findViewById(R.id.bottomLayout);
        mValueText = (TextView) itemView.findViewById(R.id.valueText);
        mUnit = (TextView) itemView.findViewById(R.id.unit);
        mRecyclerPresenter = new BaseRecyclerPresenter(itemView);
        mRecyclerPresenter.setMVPView(
                new BaseRecyclerMVPView(mRecyclerPresenter.getProvider()
                        , R.id.nestRecyclerView));

        BaseRecyclerAdapter mAdapter = new SunPowerAdapter(mViewParam);
        mAdapter.getTypeProvider().register(FactorInfo.class, ViewHolderFactor.class
                , new ViewHolderFactor.LayoutProvider());
        ((IRecyclerPresenter) mRecyclerPresenter).setAdapter(mAdapter);
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
        updateView(tData);
    }

    public void updateView(Data tData) {
        mTitle.setText(tData.name);
        ((IRecyclerPresenter) mRecyclerPresenter).setRecyclerViewData(tData.resultShowInfoList);

        if (tData.value == null || TextUtils.isEmpty(String.valueOf(tData.value))) {
            mBottomLine.setVisibility(View.GONE);
            mBottomLayout.setVisibility(View.GONE);
            return;
        }

        mBottomLine.setVisibility(View.VISIBLE);
        mBottomLayout.setVisibility(View.VISIBLE);
        mUnit.setText(tData.unit);
        mValueText.setText(String.valueOf(tData.value));
    }

    public static class LayoutProvider implements ILayoutProvider {

        @Override
        public BaseRecyclerViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater
                , @NonNull ViewGroup parent, IViewParam viewParam) {
            return new ViewHolderItem3(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_3, parent, false), viewParam);
        }
    }

    public static class Data {
        public String               name;
        public List<ResultShowInfo> resultShowInfoList;
        public Object               value;
        public String               unit;

    }
}
