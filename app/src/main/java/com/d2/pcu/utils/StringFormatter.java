package com.d2.pcu.utils;

import java.text.DecimalFormat;

public class StringFormatter {
    private static DecimalFormat doubleFormatter = new DecimalFormat("#.#");

    public static String formatDoubleToString(double value) {
        return doubleFormatter.format(value);
    }
}
