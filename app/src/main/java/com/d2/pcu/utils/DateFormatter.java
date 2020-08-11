package com.d2.pcu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    private static final Locale locale = new Locale("uk","UA");
    private static final SimpleDateFormat paymentDateFormat = new SimpleDateFormat("dd MMM yyyy", locale);

    public static String getDayAndMonth(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM", locale);

        return simpleDateFormat.format(date);
    }

    public static String getDate(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", locale);

        return simpleDateFormat.format(date);
    }

    public static String getTime(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", locale);

        return simpleDateFormat.format(date);
    }

    public static String getDatePayment(Date date) {
        if (date == null) {
            return "";
        }

        return paymentDateFormat.format(date);
    }

}
