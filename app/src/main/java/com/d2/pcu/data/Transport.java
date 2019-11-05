package com.d2.pcu.data;

import androidx.lifecycle.MutableLiveData;

import com.d2.pcu.data.responses.calendar.CalendarResponse;
import com.d2.pcu.data.responses.map.TempleResponse;
import com.d2.pcu.data.responses.more.MoreResponse;
import com.d2.pcu.data.responses.news.NewsResponse;

public class Transport {

    private MutableLiveData<TempleResponse> templesChannel = new MutableLiveData<>();

    private MutableLiveData<CalendarResponse> calendarChannel = new MutableLiveData<>();

    private MutableLiveData<NewsResponse> newsChannel = new MutableLiveData<>();

    private MutableLiveData<MoreResponse> moreChannel = new MutableLiveData<>();

    public MutableLiveData<TempleResponse> getTemplesChannel() {
        return templesChannel;
    }

    public MutableLiveData<CalendarResponse> getCalendarChannel() {
        return calendarChannel;
    }

    public MutableLiveData<MoreResponse> getMoreChannel() {
        return moreChannel;
    }

    public MutableLiveData<NewsResponse> getNewsChannel() {
        return newsChannel;
    }
}
