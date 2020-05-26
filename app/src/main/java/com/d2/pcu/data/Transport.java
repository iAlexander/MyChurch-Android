package com.d2.pcu.data;

import androidx.lifecycle.MutableLiveData;

import com.d2.pcu.data.model.diocese.Diocese;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.data.model.calendar.Event;
import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.data.model.profile.NotificationHistoryItem;
import com.d2.pcu.data.model.profile.UserProfile;
import com.d2.pcu.data.model.profile.UserState;
import com.d2.pcu.data.responses.more.MoreResponse;
import com.d2.pcu.utils.exo.PlayItem;
import com.d2.pcu.utils.livedata_utils.SingleEvent;
import com.d2.pcu.utils.livedata_utils.SingleLiveEvent;

import java.util.List;

public class Transport {
    // ----------------------------------

    private SingleLiveEvent<UserState> stateSingleEvent = new SingleLiveEvent<>();

    private MutableLiveData<List<Diocese>> dioceseChannel = new MutableLiveData<>();

    private MutableLiveData<UserProfile> userProfileChannel = new MutableLiveData<>();

    public MutableLiveData<List<Diocese>> getDioceseChannel() {
        return dioceseChannel;
    }

    public SingleLiveEvent<UserState> getStateSingleEvent() {
        return stateSingleEvent;
    }

    public MutableLiveData<UserProfile> getUserProfileChannel() {
        return userProfileChannel;
    }


    // ----------------------------------
    private MutableLiveData<List<BaseTemple>> baseTemplesChannel = new MutableLiveData<>();

    private MutableLiveData<List<Temple>> templesChannel = new MutableLiveData<>();

    private SingleLiveEvent<Temple> templeChannel = new SingleLiveEvent<>();

    private MutableLiveData<List<CalendarItem>> calendarChannel = new MutableLiveData<>();

    private MutableLiveData<Event> eventChannel = new MutableLiveData<>();

    private MutableLiveData<List<NewsItem>> newsChannel = new MutableLiveData<>();

    private MutableLiveData<List<NotificationHistoryItem>> notificationChannel = new MutableLiveData<>();

    private MutableLiveData<NotificationHistoryItem> notificationCardChannel = new MutableLiveData<>();

    private MutableLiveData<MoreResponse> moreChannel = new MutableLiveData<>();

    private MutableLiveData<List<Pray>> morningServerPraysChannel = new MutableLiveData<>();

    private MutableLiveData<List<Pray>> eveningServerPraysChannel = new MutableLiveData<>();

    private MutableLiveData<List<Pray>> morningDBPraysChannel = new MutableLiveData<>();

    private MutableLiveData<List<Pray>> eveningDBPraysChannel = new MutableLiveData<>();

    private SingleLiveEvent<PlayItem> playItemEvent = new SingleLiveEvent<>();

    public MutableLiveData<List<BaseTemple>> getBaseTemplesChannel() {
        return baseTemplesChannel;
    }

    public MutableLiveData<List<Temple>> getTemplesChannel() {
        return templesChannel;
    }

    public MutableLiveData<Temple> getTempleChannel() {
        return templeChannel;
    }

    public MutableLiveData<List<CalendarItem>> getCalendarChannel() {
        return calendarChannel;
    }

    public MutableLiveData<Event> getEventChannel() {
        return eventChannel;
    }

    public MutableLiveData<MoreResponse> getMoreChannel() {
        return moreChannel;
    }

    public MutableLiveData<List<NewsItem>> getNewsChannel() {
        return newsChannel;
    }

    public MutableLiveData<List<NotificationHistoryItem>> getNotificationChannel() {
        return notificationChannel;
    }

    public MutableLiveData<NotificationHistoryItem> getNotificationCardChannel() {
        return notificationCardChannel;
    }

    public MutableLiveData<List<Pray>> getMorningServerPraysChannel() {
        return morningServerPraysChannel;
    }

    public MutableLiveData<List<Pray>> getEveningServerPraysChannel() {
        return eveningServerPraysChannel;
    }

    public MutableLiveData<List<Pray>> getMorningDBPraysChannel() {
        return morningDBPraysChannel;
    }

    public MutableLiveData<List<Pray>> getEveningDBPraysChannel() {
        return eveningDBPraysChannel;
    }

    public SingleLiveEvent<PlayItem> getPlayItemEvent() {
        return playItemEvent;
    }

    private MutableLiveData<String> paymentChannel = new MutableLiveData<>();

    public MutableLiveData<String> getPaymentChannel() {
        return paymentChannel;
    }
}
