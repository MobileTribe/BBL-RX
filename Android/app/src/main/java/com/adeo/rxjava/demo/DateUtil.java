package com.adeo.rxjava.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by paul-hubert on 19/12/2016.
 */

public class DateUtil {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss:SSS", Locale.FRANCE);

    public static String getDate() {
        return DATE_FORMAT.format(new Date());
    }
}
