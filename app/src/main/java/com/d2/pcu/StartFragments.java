package com.d2.pcu;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        StartFragments.MAP, StartFragments.CALENDAR, StartFragments.NEWS,
        StartFragments.PRAY, StartFragments.PROFILE
})
@Retention(RetentionPolicy.CLASS)
public @interface StartFragments {
    int MAP = 0;
    int CALENDAR = 1;
    int NEWS = 2;
    int PRAY = 3;
    int PROFILE = 4;
}
