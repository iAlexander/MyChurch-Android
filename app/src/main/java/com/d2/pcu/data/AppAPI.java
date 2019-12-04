package com.d2.pcu.data;

import com.d2.pcu.data.responses.calendar.CalendarResponse;
import com.d2.pcu.data.responses.calendar.EventResponse;
import com.d2.pcu.data.responses.map.BaseTempleResponse;
import com.d2.pcu.data.responses.map.TemplesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppAPI {

    @GET("temple/list")
    Call<BaseTempleResponse> getBaseTemplesInfo(
            @Query("lt") double lt,
            @Query("lg") double lg,
            @Query("radius") int radius
    );

    @GET("temple")
    Call<TemplesResponse> getTempleById(
            @Query("id") int id
    );

    @GET("calendar")
    Call<CalendarResponse> getCalendarInfo();

    @GET("calendar/{id}")
    Call<EventResponse> getEventInfo(
            @Query("id") int id
    );
}
