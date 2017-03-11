package com.pengjunwei.kingmath.model;

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
public class SunPower {

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
    public int bankLoanCycle = 8;

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


    //----------------------------------------------------------------
    //----------------------------------------------------------------
    //----------------------------------------------------------------
//     * 月供利用房贷计算器：224.6元/月
// * 年贷款金额：224.6*12=2695.2元

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

    transient protected List<FactorInfo> factorInfoList;

    public List<FactorInfo> getFactorInfoList() {

        if (factorInfoList == null) {
            factorInfoList = new ArrayList<>();


            factorInfoList.add(new FactorInfo("每块多晶硅板面积:", String.valueOf(polysiliconPlateArea), "平方米"));
            factorInfoList.add(new FactorInfo("额定功率:", String.valueOf(ratedPower), "瓦"));
            factorInfoList.add(new FactorInfo("单价 成本价:", String.valueOf(unitPriceCost), "元/瓦"));
            factorInfoList.add(new FactorInfo("单价 市场价:", String.valueOf(unitPriceMarket), "元/瓦"));

            factorInfoList.add(new FactorInfo("年发电量系数因子:", String.valueOf(factorAnnualPowerGeneration), ""));

            factorInfoList.add(new FactorInfo("国家上网电价:", String.valueOf(countryElectricityGridPrice), "元/度"));
            factorInfoList.add(new FactorInfo("使用电价:", String.valueOf(countryElectricityUsePrice), "元/度"));
            factorInfoList.add(new FactorInfo("国家补贴:", String.valueOf(countryAllowance), "元/度"));
            factorInfoList.add(new FactorInfo("省补贴:", String.valueOf(provinceAllowance), "元/度"));
            factorInfoList.add(new FactorInfo("银行贷款利率:", String.valueOf(bankAnnualInterestRate), "%"));
            factorInfoList.add(new FactorInfo("银行贷款周期:", String.valueOf(bankLoanCycle), "年"));

            factorInfoList.add(new FactorInfo("自用比例:", String.valueOf(selfUsePercent * 100), "%"));
        }

        return factorInfoList;
    }

    transient protected List<ResultShowInfo> resultInfoList;

    public List<ResultShowInfo> getResultInfoList() {
        if (resultInfoList == null) {
            resultInfoList = new ArrayList<>();
        }else{
            resultInfoList.clear();
        }

        resultInfoList.add(new ResultShowInfo("装机容量:", String.valueOf(getInstalledCapacity()), "瓦"));
        resultInfoList.add(new ResultShowInfo("年发电量:", String.valueOf(getAnnualPowerGeneration()), "度"));
        resultInfoList.add(new ResultShowInfo("投资造价:", String.valueOf(getInvestmentCost()), "元"));
        resultInfoList.add(new ResultShowInfo("全额上网收益:", String.valueOf(getProfitsAllPush()), "元"));
        resultInfoList.add(new ResultShowInfo("自发自用余电上网（" +
                (selfUsePercent * 100) + "%自用）:", String.valueOf(getProfitsUseSelf()), "元"));

        return resultInfoList;
    }
}
