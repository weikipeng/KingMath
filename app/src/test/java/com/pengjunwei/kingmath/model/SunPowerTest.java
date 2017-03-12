package com.pengjunwei.kingmath.model;

import org.apache.poi.ss.formula.functions.FinanceLib;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by WikiPeng on 2017/3/10 16:50.
 */
public class SunPowerTest {
    private SunPower sunPower;

    @Before
    public void setUp() throws Exception {
        sunPower = new SunPower();
        sunPower.setNumber(10);
    }

    @Test
    public void getPmt() {
        double result = sunPower.pmt();
        System.out.println("getPmt ===>" + result);

        double pmt2 = FinanceLib.pmt(0.0539 / 12d, 10*12, -20800, 0, false);
        System.out.println("getPmt ===>" + pmt2);

        System.out.println("            ");
    }

    @Test
    public void getProfitsAllPush() throws Exception {
        float result = sunPower.getProfitsAllPush();
        System.out.println("getProfitsAllPush ===>" + result);
    }

    @Test
    public void getProfitsUseSelf() throws Exception {
        float result = sunPower.getProfitsUseSelf();
        System.out.println("getProfitsAllPush ===>" + result);
    }

}