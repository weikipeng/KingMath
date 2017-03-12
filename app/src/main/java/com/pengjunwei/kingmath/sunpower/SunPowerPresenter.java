package com.pengjunwei.kingmath.sunpower;

import android.app.Activity;
import android.content.Intent;

import com.pengjunwei.kingmath.MainActivity;
import com.pengjunwei.kingmath.SunPowerProfitsActivity;
import com.pengjunwei.kingmath.model.FactorInfo;
import com.pengjunwei.kingmath.model.SunPower;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerPresenter;
import com.pengjunwei.kingmath.mvp.recyclerview.IRecyclerView;
import com.pengjunwei.kingmath.viewholder.ViewHolderFactor;

import java.lang.reflect.Field;

/**
 * Created by WikiPeng on 2017/3/11 15:44.
 */
public class SunPowerPresenter extends BaseRecyclerPresenter implements ISunPowerPresenter {

    //----------------------------------------------------------------
    //--------------------------------注释--------------------------------
    //----------------------------------------------------------------
    protected SunPower mSunPower;

    public SunPowerPresenter(Activity activity) {
        super(activity);
        mvpView = new SunPowerView(provider, this);

        initData();
    }


    protected void initData() {

        mAdapter = new SunPowerAdapter(this);
        mAdapter.getTypeProvider().register(FactorInfo.class, ViewHolderFactor.class
                , new ViewHolderFactor.LayoutProvider());
        ((IRecyclerView) mvpView).setAdapter(mAdapter);
        mSunPower = new SunPower();

//        //--
//
//        TextView textView = new TextView(this);
//        textView.setLayoutParams(new LinearLayout
//                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        textView.setTextColor(getResources().getColor(R.color.colorText));
//
//        StringBuilder stringBuilder = new StringBuilder();
//
//        List<FactorInfo> factorInfoList = mSunPower.getFactorInfoList();
//
//        if (factorInfoList != null && factorInfoList.size() > 0) {
//            for (FactorInfo info : factorInfoList) {
//                stringBuilder.append(info.name)
//                        .append(info.value).append(info.unitText).append("\n");
//            }
//        }
//
//        textView.setText(stringBuilder.toString());
//        mTabLayout.addView(textView);
    }


    @Override
    public void refresh(boolean isForce) {
        super.refresh(isForce);
        mAdapter.clear();
        mAdapter.add(mSunPower.getFactorInfoList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void calculateSunPower(int number) {
        mSunPower.setNumber(number);
        Intent intent = new Intent(provider.getActivity(), SunPowerProfitsActivity.class);
        intent.putExtra(MainActivity.EXTRA_DATA, mSunPower);
        provider.getActivity().startActivity(intent);
    }

    @Override
    public void updateFactor(FactorInfo data) {
        if (data == null) {
            return;
        }

        changeFieldValue(mSunPower, SunPower.class, data.name, data.value);
    }

    protected void changeFieldValue(Object object, Class clazz, String name, Object value) {
        Field declaredField = null;
        try {
            declaredField = clazz.getDeclaredField(name);
            boolean accessible = declaredField.isAccessible();
            declaredField.setAccessible(true);

            declaredField.set(object, value);
            declaredField.setAccessible(accessible);
        } catch (NoSuchFieldException
                | SecurityException
                | IllegalArgumentException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
