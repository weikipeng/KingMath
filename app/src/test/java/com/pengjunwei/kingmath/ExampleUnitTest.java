package com.pengjunwei.kingmath;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        String testData = "-89860315840105113021-460030991519485";
        if (testData.matches("\\S{13,}")) {
            System.out.println("testData ==> true");
        } else {
            System.out.println("testData ==> 不匹配");
        }
    }
}