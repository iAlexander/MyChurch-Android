package com.d2.pcu.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.d2.pcu.App;
import com.d2.pcu.BuildConfig;
import com.d2.pcu.data.model.profile.NotificationHistoryItem;
import com.d2.pcu.data.model.profile.UserProfile;
import com.d2.pcu.data.responses.BoolDataResponse;
import com.d2.pcu.data.responses.BoolResponse;
import com.d2.pcu.data.responses.calendar.CalendarResponse;
import com.d2.pcu.data.responses.calendar.CalendarUpdateResponse;
import com.d2.pcu.data.responses.calendar.EventResponse;
import com.d2.pcu.data.responses.diocese.DioceseResponse;
import com.d2.pcu.data.responses.map.BaseTempleResponse;
import com.d2.pcu.data.responses.map.TempleResponse;
import com.d2.pcu.data.responses.news.NewsResponse;
import com.d2.pcu.data.responses.pray.PrayResponse;
import com.d2.pcu.data.responses.profile.GetUserProfileResponse;
import com.d2.pcu.data.responses.profile.NotificationHistory;
import com.d2.pcu.data.responses.profile.PaymentUrl;
import com.d2.pcu.data.responses.profile.ProfileSignUpResponse;
import com.d2.pcu.data.responses.temples.ShortTemplesInfoResponse;
import com.d2.pcu.data.serializers.news.NewsDeserializer;
import com.d2.pcu.ui.error.HTTPCode;
import com.d2.pcu.ui.error.HTTPException;
import com.d2.pcu.ui.error.NoInternetConnection;
import com.d2.pcu.ui.error.OnHTTPMasterResult;
import com.d2.pcu.ui.error.OnHTTPResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetLoader implements DefaultLifecycleObserver {

    private Gson gson;
    private OkHttpClient httpClient;
    private Retrofit retrofit;

    private AppAPI api;

    private HandlerThread handlerThread;
    private Handler handler;
    private ConnectivityManager cm;

    public NetLoader() {
//        handlerThread = new HandlerThread("netLoader");
//        handlerThread.start();
//
//        handler = new Handler(handlerThread.getLooper());
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        gson = new GsonBuilder()
                .registerTypeAdapter(NewsResponse.class, new NewsDeserializer())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();


        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(chain -> chain.proceed(
                        chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Accept", "application/json")
                                .build()))

                .followRedirects(true)
                .followSslRedirects(true)
                .cache(null)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG || BuildConfig.isDebugEnabled) {
            HttpLoggingInterceptor interceptorLog = new HttpLoggingInterceptor();
            interceptorLog.level(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptorLog);
        }
        httpClient = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        api = retrofit.create(AppAPI.class);

    }

    private Handler getHandler() {


        if (handler == null) {
            handlerThread = new HandlerThread("netLoader");
            handlerThread.start();
            handler = new Handler(handlerThread.getLooper());
        }
        return handler;
    }

    private boolean isOnline() {
        cm = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        if (handlerThread != null){
            handlerThread.quit();
        }
        handler = null;
    }

    void getBaseTemplesInfo(final double lt, final double lg, final OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.getBaseTemplesInfo(lt, lg, 5000).enqueue(new Callback<BaseTempleResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseTempleResponse> call, @NonNull Response<BaseTempleResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<BaseTempleResponse> call, @NonNull Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void getTempleById(final int id, final OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.getTempleById(id).enqueue(new Callback<TempleResponse>() {
            @Override
            public void onResponse(Call<TempleResponse> call, Response<TempleResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<TempleResponse> call, Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void getCalendarInfo(final OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.getCalendarInfo().enqueue(new Callback<CalendarResponse>() {
            @Override
            public void onResponse(@NonNull Call<CalendarResponse> call, @NonNull Response<CalendarResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<CalendarResponse> call, Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void getEventInfo(final int id, final OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.getEventInfo(id).enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, @NonNull Throwable t) {
                result.onFail(t);
            }
        }));
    }


    void getCalendarUpdate(final OnHTTPResult result){
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.getLastCalendarUpdate().enqueue(new Callback<CalendarUpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<CalendarUpdateResponse> call, @NonNull Response<CalendarUpdateResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<CalendarUpdateResponse> call, @NonNull Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void getNews(final int length, final OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.getNews(length, "Date-desc").enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, @NonNull Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void getPrays(final OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.getPrays().enqueue(new Callback<PrayResponse>() {
            @Override
            public void onResponse(@NonNull Call<PrayResponse> call, @NonNull Response<PrayResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<PrayResponse> call, @NonNull Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void getShortTemplesInfo(final OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.getShortTemplesInfo().enqueue(new Callback<ShortTemplesInfoResponse>() {
            @Override
            public void onResponse(Call<ShortTemplesInfoResponse> call, Response<ShortTemplesInfoResponse> response) {
                int resCode = response.code();
                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<ShortTemplesInfoResponse> call, Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void getDioceses(final OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.getDioceses("all").enqueue(new Callback<DioceseResponse>() {
            @Override
            public void onResponse(Call<DioceseResponse> call, Response<DioceseResponse> response) {
                int resCode = response.code();
                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<DioceseResponse> call, Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void signIn(final String email, final String password, final OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.signIn(email, password).enqueue(new Callback<ProfileSignUpResponse>() {
            @Override
            public void onResponse(Call<ProfileSignUpResponse> call, Response<ProfileSignUpResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else if (resCode == 400 && !response.errorBody().toString().isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonObject>() {
                    }.getType();

                    JsonObject error = gson.fromJson(response.errorBody().charStream(), type);
                    StringBuilder stringBuilder = new StringBuilder();
                    JsonArray errors = error.getAsJsonArray("errors");
                    for (JsonElement object : errors) {
                        stringBuilder.append(object.getAsJsonObject().get("message")).append("\n");
                    }
                    onFailure(null, new HTTPException(stringBuilder.toString()));
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<ProfileSignUpResponse> call, Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void signUp(UserProfile userProfile, final OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.signUp(
                userProfile.getEmail(),
                userProfile.getPhone(),
                userProfile.getFirstName(),
                userProfile.getLastName(),
                userProfile.isAcceptAgreement(),
                userProfile.getMember(),
                userProfile.getChurch().getId(),
                userProfile.getDiocese().getId(),
                userProfile.getFirebaseToken(),
                getProfileDateTimeFormat(userProfile.getBirthday()),
                getProfileDateTimeFormat(userProfile.getAngelday())
//                userProfile
        ).enqueue(new Callback<ProfileSignUpResponse>() {
            @Override
            public void onResponse(Call<ProfileSignUpResponse> call, Response<ProfileSignUpResponse> response) {
                int resCode = response.code();


                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else if (resCode == 400 && !response.errorBody().toString().isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonObject>() {
                    }.getType();

                    JsonObject error = gson.fromJson(response.errorBody().charStream(), type);
                    StringBuilder stringBuilder = new StringBuilder();
                    JsonArray errors = error.getAsJsonArray("errors");
                    for (JsonElement object : errors) {
                        stringBuilder.append(object.getAsJsonObject().get("message")).append("\n");
                    }
                    onFailure(null, new HTTPException(stringBuilder.toString()));
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<ProfileSignUpResponse> call, Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void forgotPass(String email, OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.forgotPass(email).enqueue(new Callback<BoolResponse>() {
            @Override
            public void onResponse(Call<BoolResponse> call, Response<BoolResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else if (resCode == 400 && !response.errorBody().toString().isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonObject>() {
                    }.getType();

                    JsonObject error = gson.fromJson(response.errorBody().charStream(), type);
                    StringBuilder stringBuilder = new StringBuilder();
                    JsonArray errors = error.getAsJsonArray("errors");
                    for (JsonElement object : errors) {
                        stringBuilder.append(object.getAsJsonObject().get("message")).append("\n");
                    }
                    onFailure(null, new HTTPException(stringBuilder.toString()));
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<BoolResponse> call, Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void updatePushToken(String accessToken, String token, OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.updatePushToken("Bearer " + accessToken, token).enqueue(new Callback<BoolResponse>() {
            @Override
            public void onResponse(Call<BoolResponse> call, Response<BoolResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else if (resCode == 400 && !response.errorBody().toString().isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonObject>() {
                    }.getType();

                    JsonObject error = gson.fromJson(response.errorBody().charStream(), type);
                    StringBuilder stringBuilder = new StringBuilder();
                    JsonArray errors = error.getAsJsonArray("errors");
                    for (JsonElement object : errors) {
                        stringBuilder.append(object.getAsJsonObject().get("message")).append("\n");
                    }
                    onFailure(null, new HTTPException(stringBuilder.toString()));
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<BoolResponse> call, Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void getNotifications(final String accessToken, final OnHTTPMasterResult<BoolDataResponse<NotificationHistory>> result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.getNotificationHistory("Bearer " + accessToken).enqueue(new Callback<BoolDataResponse<NotificationHistory>>() {
            @Override
            public void onResponse(@NonNull Call<BoolDataResponse<NotificationHistory>> call, Response<BoolDataResponse<NotificationHistory>> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else if (resCode == 400 && !response.errorBody().toString().isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonObject>() {
                    }.getType();
                    JsonObject errorBody = gson.fromJson(response.errorBody().charStream(), type);
                    onFailure(null, new HTTPException(errorBody.toString()));
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<BoolDataResponse<NotificationHistory>> call, @NonNull Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void getNotificationCard(final int id, final String accessToken, final OnHTTPMasterResult<BoolDataResponse<NotificationHistoryItem>> result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.getNotificationCard("Bearer " + accessToken, id).enqueue(new Callback<BoolDataResponse<NotificationHistoryItem>>() {
            @Override
            public void onResponse(@NonNull Call<BoolDataResponse<NotificationHistoryItem>> call, Response<BoolDataResponse<NotificationHistoryItem>> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else if (resCode == 400 && !response.errorBody().toString().isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonObject>() {
                    }.getType();
                    JsonObject errorBody = gson.fromJson(response.errorBody().charStream(), type);
                    onFailure(null, new HTTPException(errorBody.toString()));
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<BoolDataResponse<NotificationHistoryItem>> call, @NonNull Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void getUserProfile(String accessToken, OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.getUserProfile(accessToken).enqueue(new Callback<GetUserProfileResponse>() {
            @Override
            public void onResponse(Call<GetUserProfileResponse> call, Response<GetUserProfileResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else if (resCode == 400 && !response.errorBody().toString().isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonObject>() {
                    }.getType();
                    JsonObject errorBody = gson.fromJson(response.errorBody().charStream(), type);
                    onFailure(null, new HTTPException(errorBody.toString()));
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<GetUserProfileResponse> call, Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void changeEmail(String email, String accessToken, OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.changeEmail(accessToken, email).enqueue(new Callback<BoolResponse>() {
            @Override
            public void onResponse(Call<BoolResponse> call, Response<BoolResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else if (resCode == 400 && !response.errorBody().toString().isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonObject>() {
                    }.getType();
                    JsonObject errorBody = gson.fromJson(response.errorBody().charStream(), type);
                    if (errorBody.getAsJsonObject().get("data") != null) {
                        onFailure(null, new HTTPException(errorBody.toString()));
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        JsonArray errors = errorBody.getAsJsonArray("errors");
                        for (JsonElement object : errors) {
                            stringBuilder.append(object.getAsJsonObject().get("message")).append("\n");
                        }
                        onFailure(null, new HTTPException(stringBuilder.toString()));
                    }

                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<BoolResponse> call, Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void changePassword(String oldPass, String newPass, String accessToken, OnHTTPResult result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.changePassword(accessToken, oldPass, newPass).enqueue(new Callback<BoolResponse>() {
            @Override
            public void onResponse(Call<BoolResponse> call, Response<BoolResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else if (resCode == 400 && !response.errorBody().toString().isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonObject>() {
                    }.getType();
                    JsonObject errorBody = gson.fromJson(response.errorBody().charStream(), type);
                    if (errorBody.getAsJsonObject().get("data").getAsJsonObject().get("accessToken") != null) {
                        onFailure(null, new HTTPException(errorBody.toString()));
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        JsonArray errors = errorBody.getAsJsonArray("errors");
                        for (JsonElement object : errors) {
                            stringBuilder.append(object.getAsJsonObject().get("message")).append("\n");
                        }
                        onFailure(null, new HTTPException(stringBuilder.toString()));
                    }
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<BoolResponse> call, Throwable t) {
                result.onFail(t);
            }
        }));
    }

    void getPayUrl(String action, String resultUrl, final OnHTTPMasterResult<PaymentUrl> result) {
        if (!isOnline()) {
            result.onFail(new NoInternetConnection("offline"));
            return;
        }
        getHandler().post(() -> api.getPaymentUrl(action, resultUrl).enqueue(new Callback<PaymentUrl>() {
            @Override
            public void onResponse(@NonNull Call<PaymentUrl> call, @NonNull Response<PaymentUrl> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<PaymentUrl> call, @NonNull Throwable t) {
                result.onFail(t);
            }
        }));
    }

    private String getProfileDateTimeFormat(Date date) {
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a", Locale.US);
            return simpleDateFormat.format(date);
        }

        return "";
    }

//    void downloadFileSync(String url, File destFile) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    OkHttpClient client = new OkHttpClient();
//
//                    Request request = new Request.Builder().url(url).build();
//                    okhttp3.Response response = client.newCall(request).execute();
//                    ResponseBody body = response.body();
//                    long contentLength = body.contentLength();
//                    BufferedSource source = body.source();
//
//                    BufferedSink sink = Okio.buffer(Okio.sink(destFile));
//                    Buffer sinkBuffer = sink.buffer();
//
//                    long totalBytesRead = 0;
//                    int bufferSize = 8 * 1024;
//                    for (long bytesRead; (bytesRead = source.read(sinkBuffer, bufferSize)) != -1; ) {
//                        sink.emit();
//                        totalBytesRead += bytesRead;
//                        int progress = (int) ((totalBytesRead * 100) / contentLength);
////            publishProgress(progress);
//                    }
//                    sink.flush();
//                    sink.close();
//                    source.close();
//                } catch (IOException e) {
//                    // TODO: 2019-12-20
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
}
