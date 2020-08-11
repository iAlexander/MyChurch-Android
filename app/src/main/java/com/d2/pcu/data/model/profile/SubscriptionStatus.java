package com.d2.pcu.data.model.profile;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        SubscriptionStatus.NOT_SUBSCRIBED,
        SubscriptionStatus.SUBSCRIBED,
        SubscriptionStatus.UN_CLEAR
})
@Retention(RetentionPolicy.CLASS)
public @interface SubscriptionStatus {
    String NOT_SUBSCRIBED = "NotSubscribed";
    String SUBSCRIBED = "Subscribed";
    String UN_CLEAR = "UnClear";
}