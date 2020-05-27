package com.d2.pcu.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;

public class StringFormatter {
    private static DecimalFormat doubleFormatter = new DecimalFormat("#.#");

    public static String formatDoubleToString(double value) {
        return doubleFormatter.format(value);
    }

    public static int toInt(String str) {
        if (TextUtils.isEmpty(str) || !str.matches(".*\\d.*")) {
            return 0;
        } else {
            return Integer.decode(str);
        }
    }
}
