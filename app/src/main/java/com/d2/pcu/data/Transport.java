package com.d2.pcu.data;

import androidx.lifecycle.MutableLiveData;

import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.data.responses.calendar.CalendarResponse;
import com.d2.pcu.data.responses.more.MoreResponse;
import com.d2.pcu.data.responses.news.NewsResponse;

import java.util.List;

public class Transport {

    private MutableLiveData<List<BaseTemple>> baseTemplesChannel = new MutableLiveData<>();

    private MutableLiveData<List<Temple>> templesChannel = new MutableLiveData<>();

    private MutableLiveData<Temple> templeChannel = new MutableLiveData<>();

    private MutableLiveData<CalendarResponse> calendarChannel = new MutableLiveData<>();

    private MutableLiveData<NewsResponse> newsChannel = new MutableLiveData<>();

    private MutableLiveData<MoreResponse> moreChannel = new MutableLiveData<>();

    public MutableLiveData<List<BaseTemple>> getBaseTemplesChannel() {
        return baseTemplesChannel;
    }

    public MutableLiveData<List<Temple>> getTemplesChannel() {
        return templesChannel;
    }

    public MutableLiveData<Temple> getTempleChannel() {
        return templeChannel;
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
