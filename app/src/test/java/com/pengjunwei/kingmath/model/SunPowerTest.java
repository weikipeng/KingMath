package com.pengjunwei.kingmath.model;

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

    //
//    @After
//    public void tearDown() throws Exception {
//
//    }
//
//    @Test
//    public void setNumber() throws Exception {
//
//    }
//
//    @Test
//    public void getInstalledCapacity() throws Exception {
//
//    }
//
//    @Test
//    public void getAnnualPowerGeneration() throws Exception {
//
//    }
//
//    @Test
//    public void getInvestmentCost() throws Exception {
//
//    }
//
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