package com.d2.pcu.utils;

import androidx.annotation.StringDef;

import com.d2.pcu.BuildConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {

    public static final boolean DONATE_ENABLED = BuildConfig.isDonatEnabled;
    public static final boolean AUDIO_ENABLED = BuildConfig.isAudioEnabled;
    public static final boolean MESSENGERS_ENABLED = BuildConfig.isMessengersEnabled;
    public static final boolean NOTIFICATION_ENABLED = BuildConfig.isNotificationEnabled;


//    public static final String BASE_URL = "http://test.cerkva.asp-win.d2.digital/";
//    public static final String BASE_URL = "http://ec2-3-133-104-185.us-east-2.compute.amazonaws.com:8081/";
//    public static final String BASE_URL = "http://10.103.41.17:8081/";

//    public static final String BASE_NEWS_URL = "http://test.cerkva.asp-win.d2.digital/";

//    http://test.cerkva.asp-win.d2.digital/church/list-geo

    public static final String PREFS = "pcu_app_prefs";
    public static final String STATE_ON_BOARDING = "onBoarding";
    public static final String STATE_AGREEMENT = "agreementApprove";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_TYPE = "userType";
    public static final String LAST_LOCATION = "last_location";
    public static final String PUSH_TOKEN = "pushToken";


    public static final String START_SCREEN_ID = "startScreen";

    public static final int TEMPLE_TYPE_CATHEDRAL = 1;
    public static final int TEMPLE_TYPE_CHURCH = 2;
    public static final String CATHEDRAL_CHURCH = "Кафедральний";

    //    ERRORS
    public static final String ERROR_FRAGMENT_TAG = "errorFragment";
    public static final int ERROR_TYPE_NO_CONNECTION = 1;
    public static final int ERROR_TYPE_SERVER_ERROR = 2;


    //PRAYS
    public static final String PRAY_MORNING = "РАНКОВІ";
    public static final String PRAY_EVENING = "ВЕЧІРНІ";

    public static final String PLAYBACK_CHANNEL_ID = "playback_channel";
    public static final int PLAYBACK_NOTIFICATION_ID = 1;
    public static final String MEDIA_SESSION_TAG = "audio_pcu";
    public static final String EXO_BUNDLE_KEY = "exo_bundle";

    public static final String SINGLE_TRACK="singleTrack";
    public static final String ITEMS_KEY = "playlist";
    public static final String EXO_POSITION = "exo_track";


    @StringDef({
            PaymentAction.SUBSCRIBE, PaymentAction.DONATE, PaymentAction.PAY, PaymentAction.AUTH
    })
    @Retention(RetentionPolicy.CLASS)
    public @interface PaymentAction {
        String SUBSCRIBE="subscribe";
        String DONATE = "paydonate";
        String PAY = "pay";
        String AUTH = "auth";
    }

    public static final String PAYMENT_COMPLETE = "http://pcu.com/success";


}
