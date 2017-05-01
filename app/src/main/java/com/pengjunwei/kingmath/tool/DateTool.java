package com.pengjunwei.kingmath.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wikipeng on 2017/4/22.
 */

public final class DateTool {
    public static final String PATTERN_YEAR_MONTH_DAY_2 = "yyyyMMdd";
    private DateTool(){

    }

    public static String getYMD() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_YEAR_MONTH_DAY_2, Locale.getDefault());
        return simpleDateFormat.format(new Date());
    }
}
