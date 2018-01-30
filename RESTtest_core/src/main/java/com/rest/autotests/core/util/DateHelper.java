package com.rest.autotests.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Andrej Skeledzija 2017
 */
public class DateHelper {

    //Logger
    private static final Logger logger = LogManager.getLogger(DateHelper.class);

    public static String getChangedDateString(int field, int amount, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Calendar calendar = Calendar.getInstance();
        //add one month
        calendar.add(field, amount);
        return sdf.format(calendar.getTime());
    }
}
