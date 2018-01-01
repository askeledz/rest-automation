package com.rest.autotests.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Andrej Skeledzija 2017
 */
public class DateHelper {

    public static String getChangedDateString(int field, int amount, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Calendar calendar = Calendar.getInstance();
        //add one month
        calendar.add(field, amount);
        return sdf.format(calendar.getTime());
    }
}
