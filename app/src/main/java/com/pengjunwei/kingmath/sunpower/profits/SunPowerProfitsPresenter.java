package com.pengjunwei.kingmath.sunpower.profits;

import android.app.Activity;
import android.content.Intent;

import com.pengjunwei.kingmath.MainActivity;
import com.pengjunwei.kingmath.model.FactorInfo;
import com.pengjunwei.kingmath.model.ResultShowInfo;
import com.pengjunwei.kingmath.model.SunPower;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerPresenter;
import com.pengjunwei.kingmath.mvp.recyclerview.IRecyclerView;
import com.pengjunwei.kingmath.sunpower.SunPowerAdapter;
import com.pengjunwei.kingmath.sunpower.SunPowerDao;
import com.pengjunwei.kingmath.tool.FOpenLog;
import com.pengjunwei.kingmath.viewholder.ViewHolderFactor;
import com.pengjunwei.kingmath.viewholder.ViewHolderItem3;
import com.pengjunwei.kingmath.viewholder.ViewHolderResult;

/**
 * Created by WikiPeng on 2017/3/11 18:34.
 */
public class SunPowerProfitsPresenter extends BaseRecyclerPresenter implements ISunPowerProfitsPresenter {

    protected SunPowerDao mSunPowerDao;
    protected SunPower    mSunPower;

    public SunPowerProfitsPresenter(Activity activity) {
        super(activity);

        initData();
    }

    protected void initData() {
        mSunPowerDao = new SunPowerDao(provider.getActivity());
        mAdapter = new SunPowerAdapter(this);

        mAdapter.getTypeProvider().register(ResultShowInfo.class, ViewHolderResult.class
                , new ViewHolderFactor.LayoutProvider());
        mAdapter.getTypeProvider().register(ViewHolderItem3.Data.class, ViewHolderItem3.class
                , new ViewHolderItem3.LayoutProvider());

        mvpView = new SunPowerProfitsView(provider, this);
        ((IRecyclerView) mvpView).setAdapter(mAdapter);
    }

    @Override
    public void refresh(boolean isForce) {
        super.refresh(isForce);
        if (mSunPower != null) {
            mAdapter.clear();
            mAdapter.add(mSunPower.getResultInfoList());
            mAdapter.notifyDataSetChanged();
        }

        ((ISunPowerProfitsView) mvpView).updateView(mSunPower);
    }

    @Override
    public void setIntent(Intent intent) {
        if (intent != null) {
            mSunPower = intent.getParcelableExtra(MainActivity.EXTRA_DATA);
        }
    }

    @Override
    public void updateFactor(FactorInfo data) {

    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public void updateSelfUsePercent(float progress) {
        mSunPower.selfUsePercent = progress / 100f;
        refresh(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        mSunPowerDao.save(mSunPower);
    }

    @Override
    public void finish() {
        super.finish();
        FOpenLog.e("kingMathFocus debug SunPowerProfitsPresenter finish ====>");
        Intent data = new Intent();
        data.putExtra(MainActivity.EXTRA_DATA, mSunPower);
        provider.getActivity().setResult(Activity.RESULT_OK,data);
    }
}
