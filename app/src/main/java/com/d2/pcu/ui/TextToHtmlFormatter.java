package com.d2.pcu.ui;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

public class TextToHtmlFormatter {

    public static Spanned getFormattedHtmlText(String source) {

        if (source != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(source, Html.FROM_HTML_OPTION_USE_CSS_COLORS);
            } else {
                return Html.fromHtml(source);
            }
        } else {
            return Html.fromHtml("");
        }


    }
}
