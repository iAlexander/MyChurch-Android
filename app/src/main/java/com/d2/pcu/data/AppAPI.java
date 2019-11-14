package com.d2.pcu.data;

import com.d2.pcu.data.responses.map.BaseTempleResponse;
import com.d2.pcu.data.responses.map.TempleResponse;

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
    Call<TempleResponse> getTempleByName(
            @Query("name") String name
    );
}
