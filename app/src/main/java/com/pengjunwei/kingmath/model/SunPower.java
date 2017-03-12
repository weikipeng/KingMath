package com.pengjunwei.kingmath.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.pengjunwei.kingmath.viewholder.ViewHolderItem3;

import org.apache.poi.ss.formula.functions.FinanceLib;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 光伏计算
 * <p>
 * 每块多晶硅板面积1.6平方，额定功率260瓦
 * 装机容量（瓦）=额定功率*多晶硅板的数量
 * 年发电量（度）=装机容量*1.123
 * 单价：成本价8元/瓦，市场价10元/瓦
 * 投资造价=装机容量*单价
 * <p>
 * 国家上网电价0.4153元/度，使用电价0.538元/度
 * 补贴：0.42元/度（国家）+0.1元/度（省）
 * 恒信银行贷款：年利率5.39%，周期10年
 * 邮政银行：年利率5.39%，周期8年
 * <p>
 * 例如：李某家屋顶可装10块板
 * 装机容量：260*10=2600瓦
 * 年发电量：2600*1.123=2919.8度
 * 投资造价：2600*8=20800元
 * 月供利用房贷计算器：224.6元/月
 * 年贷款金额：224.6*12=2695.2元
 * 全额上网收益：2919.8*（0.42+0.1+0.4153）=2730.89元
 * 自发自用余电上网（70%自用）：2043.86*（0.538+0.42+0.1）+875.94*（0.42+0.1+0.4153）=3039.3元
 * <p>
 * Created by WikiPeng on 2017/3/10 15:51.
 */
public class SunPower implements Parcelable {

    /**
     * 每块多晶硅板面积
     * 1.6平方米
     * 额定功率 260瓦
     */
    public float polysiliconPlateArea = 1.6f;

    /**
     * 额定功率 260瓦
     */
    public int ratedPower = 260;

//    单价：成本价8元/瓦，市场价10元/瓦

    /**
     * 单价 成本价
     */
    public float unitPriceCost   = 8f;
    /**
     * 单价 市场价
     */
    public float unitPriceMarket = 10f;

    /**
     * 年发电量系数因子
     */
    public float factorAnnualPowerGeneration = 1.123f;

    //----------------------------------------------------------------
    //--------------------------------注释--------------------------------
    //----------------------------------------------------------------

    /**
     * 国家上网电价 0.4153元/度
     */
    public float countryElectricityGridPrice = 0.4153f;

    /**
     * 使用电价 0.538元/度
     */
    public float countryElectricityUsePrice = 0.538f;

    /**
     * 国家补贴
     * 补贴：0.42元/度（国家）+0.1元/度（省）
     */
    public float countryAllowance = 0.42f;

    /**
     * 省补贴
     * 补贴：0.42元/度（国家）+0.1元/度（省）
     */
    public float provinceAllowance = 0.1f;

    /**
     * 恒信银行贷款：年利率5.39%，周期10年
     * 邮政银行：年利率5.39%，周期8年
     */
    public float bankAnnualInterestRate = 5.39f;

    /**
     * 银行贷款周期
     */
    public int bankLoanCycle = 10;

    /**
     * 自用比例
     */
    public float selfUsePercent = 0.70f;


    /**
     * 多晶硅板的数量
     * 装机板数量
     */
    public int number;

    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * 装机容量（瓦）=额定功率*多晶硅板的数量
     */
    public float getInstalledCapacity() {
//        装机容量（瓦）=额定功率*多晶硅板的数量
        return ratedPower * number;
    }

    /**
     * 年发电量（度）=装机容量*1.123
     */
    public float getAnnualPowerGeneration() {
        float result            = 0;
        float installedCapacity = getInstalledCapacity();
        result = installedCapacity * factorAnnualPowerGeneration;
        return result;
    }

    /**
     * 投资造价=装机容量*单价
     */
    public float getInvestmentCost() {
        return getInstalledCapacity() * unitPriceCost;
    }

    public float getProfitPercent() {
        return 0;
    }

    //----------------------------------------------------------------
    //----------------------------------------------------------------
    //----------------------------------------------------------------
//     * 月供利用房贷计算器：224.6元/月
// * 年贷款金额：224.6*12=2695.2元
    public double pmt() {
//        return FinanceLib.pmt(0.00740260861, 180, -984698, 0, false);
        double result = FinanceLib.pmt(
                bankAnnualInterestRate / 1200d //利率
                , bankLoanCycle * 12 //周期
                , -((double) getInvestmentCost())
                , 0 //future value
                , false//at the beginning of the period (or at the end)
        );

        BigDecimal b = new BigDecimal(result);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //----------------------------------------------------------------
    //--------------------------------全额上网收益--------------------------------
    //----------------------------------------------------------------
    public float getProfitsAllPush() {
        float result = 0;
        //年发电量
        float annualPowerGeneration = getAnnualPowerGeneration();

        result = annualPowerGeneration * (countryAllowance + provinceAllowance + countryElectricityGridPrice);

        return result;
    }

    //----------------------------------------------------------------
    //--------------------------------自发自用余电上网--------------------------------
    //----------------------------------------------------------------
    public float getProfitsUseSelf() {
        float result = 0;
        //年发电量
        float annualPowerGeneration = getAnnualPowerGeneration();

        float selfUse = annualPowerGeneration * selfUsePercent;

        System.out.println("getProfitsUseSelf selfUse:" + selfUse +
                " remain:" + (annualPowerGeneration - selfUse));

        double aim = 2043.86 * (0.538 + 0.42 + 0.1) + 875.94 * (0.42 + 0.1 + 0.4153);
        System.out.println("aim:" + aim);

        result = selfUse * (countryElectricityUsePrice + countryAllowance + provinceAllowance)
                + (annualPowerGeneration - selfUse) * (countryAllowance + provinceAllowance + countryElectricityGridPrice)
        ;

        return result;
    }

    transient protected List<Object> factorInfoList;

    public List<Object> getFactorInfoList() {

        if (factorInfoList == null) {
            factorInfoList = new ArrayList<>();
        }

        factorInfoList.add(new FactorInfo("polysiliconPlateArea", "每块多晶硅板面积:", polysiliconPlateArea, "平方米"));

        factorInfoList.add(new FactorInfo("ratedPower", "额定功率:", ratedPower, "瓦"));
        factorInfoList.add(new FactorInfo("unitPriceCost", "单价", unitPriceCost, "元/瓦"));

        factorInfoList.add(new FactorInfo("factorAnnualPowerGeneration", "年发电量系数因子:", factorAnnualPowerGeneration, ""));

        factorInfoList.add(new FactorInfo("countryElectricityGridPrice", "国家上网电价:", countryElectricityGridPrice, "元/度"));
        factorInfoList.add(new FactorInfo("countryElectricityUsePrice", "使用电价:", countryElectricityUsePrice, "元/度"));
        factorInfoList.add(new FactorInfo("countryAllowance", "国家补贴:", countryAllowance, "元/度"));
        factorInfoList.add(new FactorInfo("provinceAllowance", "省补贴:", provinceAllowance, "元/度"));
        factorInfoList.add(new FactorInfo("bankAnnualInterestRate", "银行贷款利率:", bankAnnualInterestRate, "%"));
        factorInfoList.add(new FactorInfo("bankLoanCycle", "银行贷款周期:", bankLoanCycle, "年"));

        factorInfoList.add(new FactorInfo("selfUsePercent", "自用比例:", selfUsePercent * 100, "%"));

        return factorInfoList;
    }

    transient protected List<Object> resultInfoList;

    public List<Object> getResultInfoList() {
        if (resultInfoList == null) {
            resultInfoList = new ArrayList<>();
        } else {
            resultInfoList.clear();
        }
        resultInfoList.add(new ResultShowInfo("", "数量:", number, "块"));
        resultInfoList.add(new ResultShowInfo("", "装机容量:", getInstalledCapacity() / 1000f, "千瓦"));
        resultInfoList.add(new ResultShowInfo("", "投资造价:", getInvestmentCost(), "元"));
        resultInfoList.add(new ResultShowInfo("", "年发电量:", getAnnualPowerGeneration(), "度"));

//        resultInfoList.add(new ResultShowInfo("", "全额上网收益:", getProfitsAllPush(), "元"));


        //----------------------------------------------------------------
        //--------------------------------注释--------------------------------
        //----------------------------------------------------------------
        ViewHolderItem3.Data data = new ViewHolderItem3.Data();
        data.resultShowInfoList = new ArrayList<>();
        data.name = "全额上网收益";
        data.resultShowInfoList.add(new ResultShowInfo("", "国家补贴:", countryAllowance, "元/度"));
        data.resultShowInfoList.add(new ResultShowInfo("", "省补贴:", provinceAllowance, "元/度"));
        data.resultShowInfoList.add(new ResultShowInfo("", "脱硫电价:", countryElectricityGridPrice, "元/度"));

        resultInfoList.add(data);


        //----------------------------------------------------------------
        //--------------------------------银行贷款--------------------------------
        //----------------------------------------------------------------
        double               pmtMonth = pmt();
        ViewHolderItem3.Data pmtData  = new ViewHolderItem3.Data();
        pmtData.resultShowInfoList = new ArrayList<>();
        pmtData.name = "银行贷款" + bankLoanCycle + "年";
        pmtData.resultShowInfoList.add(new ResultShowInfo("", "", pmtMonth, "元/月"));
        pmtData.resultShowInfoList.add(new ResultShowInfo("", "", pmtMonth * 12, "元/年"));
        resultInfoList.add(pmtData);


//        resultInfoList.add(new ResultShowInfo("", "月供:", pmtMonth, "元/月"));
//        resultInfoList.add(new ResultShowInfo("", "年贷款金额:", pmtMonth * 12, "元/年"));

        //     * 月供利用房贷计算器：224.6元/月
// * 年贷款金额：224.6*12=2695.2元


        resultInfoList.add(new ResultShowInfo("", "自发自用余电上网（" +
                (selfUsePercent * 100) + "%自用）:", getProfitsUseSelf(), "元"));

        return resultInfoList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.polysiliconPlateArea);
        dest.writeInt(this.ratedPower);
        dest.writeFloat(this.unitPriceCost);
        dest.writeFloat(this.unitPriceMarket);
        dest.writeFloat(this.factorAnnualPowerGeneration);
        dest.writeFloat(this.countryElectricityGridPrice);
        dest.writeFloat(this.countryElectricityUsePrice);
        dest.writeFloat(this.countryAllowance);
        dest.writeFloat(this.provinceAllowance);
        dest.writeFloat(this.bankAnnualInterestRate);
        dest.writeInt(this.bankLoanCycle);
        dest.writeFloat(this.selfUsePercent);
        dest.writeInt(this.number);
    }

    public SunPower() {
    }

    protected SunPower(Parcel in) {
        this.polysiliconPlateArea = in.readFloat();
        this.ratedPower = in.readInt();
        this.unitPriceCost = in.readFloat();
        this.unitPriceMarket = in.readFloat();
        this.factorAnnualPowerGeneration = in.readFloat();
        this.countryElectricityGridPrice = in.readFloat();
        this.countryElectricityUsePrice = in.readFloat();
        this.countryAllowance = in.readFloat();
        this.provinceAllowance = in.readFloat();
        this.bankAnnualInterestRate = in.readFloat();
        this.bankLoanCycle = in.readInt();
        this.selfUsePercent = in.readFloat();
        this.number = in.readInt();
    }

    public static final Parcelable.Creator<SunPower> CREATOR = new Parcelable.Creator<SunPower>() {
        @Override
        public SunPower createFromParcel(Parcel source) {
            return new SunPower(source);
        }

        @Override
        public SunPower[] newArray(int size) {
            return new SunPower[size];
        }
    };
}
