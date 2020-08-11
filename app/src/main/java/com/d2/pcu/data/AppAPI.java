package com.d2.pcu.data;

import com.d2.pcu.data.model.profile.NotificationHistoryItem;
import com.d2.pcu.data.model.profile.UserProfile;
import com.d2.pcu.data.responses.BoolDataResponse;
import com.d2.pcu.data.responses.BoolResponse;
import com.d2.pcu.data.responses.calendar.CalendarResponse;
import com.d2.pcu.data.model.UpdateResponse;
import com.d2.pcu.data.responses.calendar.EventResponse;
import com.d2.pcu.data.responses.diocese.DioceseResponse;
import com.d2.pcu.data.responses.map.BaseTempleResponse;
import com.d2.pcu.data.responses.map.TempleResponse;
import com.d2.pcu.data.responses.news.NewsResponse;
import com.d2.pcu.data.responses.pray.PrayResponse;
import com.d2.pcu.data.responses.profile.GetUserProfileResponse;
import com.d2.pcu.data.responses.profile.History;
import com.d2.pcu.data.responses.profile.NotificationHistory;
import com.d2.pcu.data.model.profile.PaymentHistoryItem;
import com.d2.pcu.data.responses.profile.PaymentUrl;
import com.d2.pcu.data.responses.profile.ProfileSignUpResponse;
import com.d2.pcu.data.responses.temples.ShortTemplesInfoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppAPI {

    @GET("temple")
    Call<BaseTempleResponse> getBaseTemplesInfo(
            @Query("lt") double lt,
            @Query("lg") double lg,
            @Query("radius") int radius
    );

    @GET("/church/card/{id}")
    Call<TempleResponse> getTempleById(
            @Path("id") int id
    );

    @GET("gala?n=all")
    Call<CalendarResponse> getCalendarInfo();

    @GET("gala/last-update")
    Call<UpdateResponse> getCalendarLastUpdate();

    @GET("calendar/{id}")
    Call<EventResponse> getEventInfo(
            @Query("id") int id
    );

    @GET("/church/prayer?n=all")
    Call<PrayResponse> getPrays();

    @GET("church/prayer/last-update")
    Call<UpdateResponse> getPrayerLastUpdate();

    @GET("/pages/news")
    Call<NewsResponse> getNews(
            @Query("n") int length,
            @Query("sort") String sortType
    );

    @GET("church/list-geo")
    Call<ShortTemplesInfoResponse> getShortTemplesInfo();

    @GET("church/diocese/")
    Call<DioceseResponse> getDioceses(
            @Query("n") String all
    );

    @POST("register")
    Call<ProfileSignUpResponse> signUp(
            @Body UserProfile userProfile
    );

    @FormUrlEncoded
    @POST("/register")
    Call<ProfileSignUpResponse> signUp(
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("acceptAgreement") boolean acceptAgreement,
            @Field("member") String member,
            @Field("church") int church,
            @Field("diocese") int diocese,
            @Field("firebaseToken") String firebaseToken,
            @Field("birthday") String birthday,
            @Field("angelday") String angelday
            );

    @FormUrlEncoded
    @POST("/account/login")
    Call<ProfileSignUpResponse> signIn (
            @Field("login") String login,
            @Field("password") String password
    );

    @POST("/account/logout")
    Call<BoolResponse> logout(@Header("Authorization") String accessToken);

    @FormUrlEncoded
    @POST("/account/forgot-password")
    Call<BoolResponse> forgotPass(@Field("login") String email);

    @FormUrlEncoded
    @POST("/account/token-update")
    Call<BoolResponse> updatePushToken(@Header("Authorization") String accessToken, @Field("firebaseToken") String token);

    @GET("/account/profile")
    Call<GetUserProfileResponse> getUserProfile(@Header("Authorization") String accessToken);

    @FormUrlEncoded
    @POST("/profile/email-change")
    Call<BoolResponse> changeEmail(
            @Header("Authorization") String accessToken,
            @Field("newEmail") String email
    );

    @FormUrlEncoded
    @POST("/profile/change-password")
    Call<BoolResponse> changePassword(
            @Header("Authorization") String accessToken,
            @Field("oldPassword") String oldPass,
            @Field("newPassword") String newPass
    );

    @GET("/notification/history")
    Call<BoolDataResponse<NotificationHistory>> getNotificationHistory(@Header("Authorization") String accessToken);

    @GET("/notification/card/{id}")
    Call<BoolDataResponse<NotificationHistoryItem>> getNotificationCard(@Header("Authorization") String accessToken, @Path("id") int id);

    @GET("/api/pay/generate-liqpay-url")
    Call<PaymentUrl> getPaymentUrl(@Query("actionType") String action, @Query("resultUrl") String resultUrl, @Query("amount") float amount, @Header("Authorization") String accessToken);

    @GET("/api/pay/unsubscribe-liqpay")
    Call<BoolResponse> getUnsubscribe(@Header("Authorization") String accessToken);

    @GET("/api/pay/history-liqpay?n=all")
    Call<BoolDataResponse<History<PaymentHistoryItem>>> getPaymentHistory(@Header("Authorization") String accessToken);

}
