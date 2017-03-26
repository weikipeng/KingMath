package com.pengjunwei.kingmath.sunpower.profits;

import android.widget.TextView;

import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.model.SunPower;
import com.pengjunwei.kingmath.mvp.IMVPProvider;
import com.pengjunwei.kingmath.mvp.IPresenter;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerMVPView;
import com.xw.repo.BubbleSeekBar;

/**
 * Created by WikiPeng on 2017/3/11 18:34.
 */
public class SunPowerProfitsView extends BaseRecyclerMVPView implements ISunPowerProfitsView {

//    protected TextView mSelfProfit;

//    protected BubbleSeekBar mSeekBar;

//    protected TextView mSeekBarPercent;

//    protected int mOldProgress;

    public SunPowerProfitsView(IMVPProvider provider) {
        super(provider);
    }

    public <T extends IPresenter> SunPowerProfitsView(IMVPProvider provider, T presenter) {
        super(provider, presenter);
    }

    @Override
    protected void initView() {
        super.initView();
//        mSelfProfit = provider.findViewById(R.id.selfUseProfits);
//        mSeekBarPercent = provider.findViewById(R.id.seekBarPercent);
//        mSeekBar = provider.findViewById(R.id.seekBar);
//        mSeekBar.setShowText(true);
    }

    @Override
    protected void initData() {
        super.initData();
//        mSeekBar.setProgressRange(1, 100, 70f);
//        mSeekBar.setSectionCount(20);
    }

    @Override
    protected void addEvent() {
        super.addEvent();
//        mSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
//            @Override
//            public void onProgressChanged(int progress) {
//                if (mOldProgress == progress) {
//                    return;
//                }
//                mOldProgress = progress;
//                mSeekBarPercent.setText(progress + "%");
//                ((ISunPowerProfitsPresenter) presenter).updateSelfUsePercent(progress);
//            }
//        });
    }

    @Override
    public void updateView(SunPower data) {
//        mSelfProfit.setText(String.valueOf(data.getProfitsUseSelf()));
//        mSeekBarPercent.setText(data.selfUsePercent * 100 + "%");
//        mSeekBar.setProgress(data.selfUsePercent * 100);
    }
}
