package com.d2.pcu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    public static String getDayAndMonth(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM", Locale.getDefault());

        return simpleDateFormat.format(date);
    }

    public static String getDate(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        return simpleDateFormat.format(date);
    }

    public static String getTime(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        return simpleDateFormat.format(date);
    }
}
