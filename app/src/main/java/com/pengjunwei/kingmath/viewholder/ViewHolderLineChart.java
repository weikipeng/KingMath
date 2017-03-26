package com.pengjunwei.kingmath.viewholder;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.pengjunwei.kingmath.R;
import com.pengjunwei.kingmath.model.SunPower;
import com.pengjunwei.kingmath.mvp.IViewParam;
import com.pengjunwei.kingmath.mvp.recyclerview.BaseRecyclerViewHolder;
import com.pengjunwei.kingmath.mvp.recyclerview.ILayoutProvider;

/**
 * Created by WikiPeng on 2017/3/26 11:10.
 */
public class ViewHolderLineChart extends BaseRecyclerViewHolder {

    protected LineChart mLineChart;
    protected int colorText;

    public ViewHolderLineChart(View itemView, IViewParam viewParam) {
        super(itemView, viewParam);
        mLineChart = (LineChart) itemView.findViewById(R.id.chart);
        colorText = itemView.getResources().getColor(R.color.colorText);
    }

    @Override
    public void onBindViewHolder(int position, Object data) {
        super.onBindViewHolder(position, data);
        if (!(data instanceof Data)) {
            return;
        }
        SunPower sunPower = ((Data) data).sunPowerData;
        if (sunPower == null) {
            return;
        }

        LineData lineData = sunPower.getLineChartData();
        if (lineData == null) {
            return;
        }

        mLineChart.setData(lineData);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(colorText);
        xAxis.setDrawAxisLine(true);


        YAxis leftAxis = mLineChart.getAxisLeft();
//        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setTextColor(colorText);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        leftAxis.setAxisMaximum(lineData.getYMax() + 100);
//        leftAxis.setAxisMinimum(lineData.getYMin() - 100);

        mLineChart.getAxisRight().setEnabled(false);
//        YAxis rightAxis = mLineChart.getAxisRight();
////        rightAxis.setTypeface(mTf);
//        rightAxis.setLabelCount(5, false);
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mLineChart.getDescription().setEnabled(false);
        // get the legend (only possible after setting data)
        Legend l = mLineChart.getLegend();
        l.setEnabled(false);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        mLineChart.animateX(750);
    }

    public static class LayoutProvider implements ILayoutProvider {

        @Override
        public BaseRecyclerViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater
                , @NonNull ViewGroup parent, IViewParam viewParam) {
            return new ViewHolderLineChart(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_line_chart, parent, false), viewParam);
        }
    }

    public static class Data {
        public SunPower sunPowerData;

        public Data(SunPower sunPower) {
            this.sunPowerData = sunPower;
        }
    }
}
