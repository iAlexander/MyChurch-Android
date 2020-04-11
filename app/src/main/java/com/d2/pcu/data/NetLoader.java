package com.d2.pcu.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.d2.pcu.BuildConfig;
import com.d2.pcu.data.model.profile.UserProfile;
import com.d2.pcu.data.responses.BoolResponse;
import com.d2.pcu.data.responses.calendar.CalendarResponse;
import com.d2.pcu.data.responses.calendar.EventResponse;
import com.d2.pcu.data.responses.diocese.DioceseResponse;
import com.d2.pcu.data.responses.map.BaseTempleResponse;
import com.d2.pcu.data.responses.map.TempleResponse;
import com.d2.pcu.data.responses.news.NewsResponse;
import com.d2.pcu.data.responses.pray.PrayResponse;
import com.d2.pcu.data.responses.profile.GetUserProfileResponse;
import com.d2.pcu.data.responses.profile.ProfileSignUpResponse;
import com.d2.pcu.data.responses.temples.ShortTemplesInfoResponse;
import com.d2.pcu.data.serializers.news.NewsDeserializer;
import com.d2.pcu.ui.error.HTTPCode;
import com.d2.pcu.ui.error.HTTPException;
import com.d2.pcu.ui.error.OnHTTPResult;
import com.d2.pcu.utils.Constants;
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
import timber.log.Timber;

public class NetLoader implements DefaultLifecycleObserver {

    private static final String TAG = NetLoader.class.getSimpleName();

    private Gson gson;
    private OkHttpClient httpClient;
    private Retrofit retrofit;

    private AppAPI api;

    private HandlerThread handlerThread;
    private Handler handler;

    public NetLoader() {
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
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptorLog = new HttpLoggingInterceptor();
            interceptorLog.level(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptorLog);
        }
        httpClient = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        api = retrofit.create(AppAPI.class);

        handlerThread = new HandlerThread("netLoader");
        handlerThread.start();

        handler = new Handler(handlerThread.getLooper());

        Log.i(TAG, "Created");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        handlerThread.quit();
    }

    void getBaseTemplesInfo(final double lt, final double lg, final OnHTTPResult result) {
        handler.post(() -> api.getBaseTemplesInfo(lt, lg, 5000).enqueue(new Callback<BaseTempleResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseTempleResponse> call, @NonNull Response<BaseTempleResponse> response) {
                int resCode = response.code();
                Timber.d("getBaseTemplesInfo -> onResponseCode: %s", resCode);

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
        handler.post(() -> api.getTempleById(id).enqueue(new Callback<TempleResponse>() {
            @Override
            public void onResponse(Call<TempleResponse> call, Response<TempleResponse> response) {
                int resCode = response.code();

                Timber.d("getTempleById -> onResponseCode: %s", resCode);

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<TempleResponse> call, Throwable t) {
                Log.i(TAG, "getTempleById -> onFailure");

                result.onFail(t);
            }
        }));
    }

    void getCalendarInfo(final OnHTTPResult result) {
        handler.post(() -> api.getCalendarInfo().enqueue(new Callback<CalendarResponse>() {
            @Override
            public void onResponse(@NonNull Call<CalendarResponse> call, @NonNull Response<CalendarResponse> response) {
                int resCode = response.code();

                Log.i(TAG, "getCalendarInfo -> onResponse: " + resCode);

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<CalendarResponse> call, Throwable t) {
                Log.i(TAG, "getCalendarInfo -> onFailure: " + t.getMessage());
                result.onFail(t);
            }
        }));
    }

    void getEventInfo(final int id, final OnHTTPResult result) {
        handler.post(() -> api.getEventInfo(id).enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                int resCode = response.code();

                Log.i(TAG, "getEventInfo -> onResponse: " + resCode);

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "getEventInfo -> onFailure: " + t.getMessage());
                result.onFail(t);
            }
        }));
    }

    void getNews(final int length, final OnHTTPResult result) {
        handler.post(() -> api.getNews(length, "Date-desc").enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                int resCode = response.code();

                Log.i(TAG, "getEventInfo -> onResponse: " + resCode);

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "getEventInfo -> onFailure: " + t.getMessage());
                result.onFail(t);
            }
        }));
    }

    void getPrays(final OnHTTPResult result) {
        handler.post(() -> api.getPrays().enqueue(new Callback<PrayResponse>() {
            @Override
            public void onResponse(@NonNull Call<PrayResponse> call, @NonNull Response<PrayResponse> response) {
                int resCode = response.code();

                Log.i(TAG, "getPrays -> onResponse: " + resCode);

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<PrayResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "getPrays -> onFailure: " + t.getMessage());
                result.onFail(t);
            }
        }));
    }

    void getShortTemplesInfo(final OnHTTPResult result) {
        handler.post(() -> api.getShortTemplesInfo().enqueue(new Callback<ShortTemplesInfoResponse>() {
            @Override
            public void onResponse(Call<ShortTemplesInfoResponse> call, Response<ShortTemplesInfoResponse> response) {
                int resCode = response.code();

                Log.i(TAG, "getShortTemplesInfo -> onResponse: " + resCode);

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<ShortTemplesInfoResponse> call, Throwable t) {
                Log.i(TAG, "getShortTemplesInfo -> onFailure: " + t.getMessage());
                result.onFail(t);
            }
        }));
    }

    void getDioceses(final OnHTTPResult result) {
        handler.post(() -> api.getDioceses("all").enqueue(new Callback<DioceseResponse>() {
            @Override
            public void onResponse(Call<DioceseResponse> call, Response<DioceseResponse> response) {
                int resCode = response.code();

                Log.i(TAG, "getDioceses -> onResponse: " + resCode);

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<DioceseResponse> call, Throwable t) {
                Log.i(TAG, "getDioceses -> onFailure: " + t.getMessage());
                result.onFail(t);
            }
        }));
    }

    void signIn(final String email, final String password, final OnHTTPResult result) {
        handler.post(() -> api.signIn(email, password).enqueue(new Callback<ProfileSignUpResponse>() {
            @Override
            public void onResponse(Call<ProfileSignUpResponse> call, Response<ProfileSignUpResponse> response) {
                int resCode = response.code();

                Log.i(TAG, "signUp -> onResponse: " + resCode);

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
                Log.i(TAG, "signUp -> onFailure: " + t.getMessage());
                result.onFail(t);
            }
        }));
    }

    void signUp(UserProfile userProfile, final OnHTTPResult result) {
        handler.post(() -> api.signUp(
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

                Log.i(TAG, "signUp -> onResponse: " + resCode);

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
                Log.i(TAG, "signUp -> onFailure: " + t.getMessage());
                result.onFail(t);
            }
        }));
    }

    void forgotPass(String email, OnHTTPResult result) {
        handler.post(() -> api.forgotPass(email).enqueue(new Callback<BoolResponse>() {
            @Override
            public void onResponse(Call<BoolResponse> call, Response<BoolResponse> response) {
                int resCode = response.code();

                Log.i(TAG, "forgotPass -> onResponse: " + resCode);

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
                Log.i(TAG, "forgotPass -> onFailure: " + t.getMessage());
                result.onFail(t);
            }
        }));
    }

    void getUserProfile(String accessToken, OnHTTPResult result) {
        handler.post(() -> api.getUserProfile(accessToken).enqueue(new Callback<GetUserProfileResponse>() {
            @Override
            public void onResponse(Call<GetUserProfileResponse> call, Response<GetUserProfileResponse> response) {
                int resCode = response.code();

                Log.i(TAG, "getUserProfile -> onResponse: " + resCode);

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
                Log.i(TAG, "getUserProfile -> onFailure: " + t.getMessage());
                result.onFail(t);
            }
        }));
    }

    void changeEmail(String email, String accessToken, OnHTTPResult result) {
        handler.post(() -> api.changeEmail(accessToken, email).enqueue(new Callback<BoolResponse>() {
            @Override
            public void onResponse(Call<BoolResponse> call, Response<BoolResponse> response) {
                int resCode = response.code();

                Log.i(TAG, "changeEmail -> onResponse: " + resCode);

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
                Log.i(TAG, "forgotPass -> onFailure: " + t.getMessage());
                result.onFail(t);
            }
        }));
    }

    void changePassword(String oldPass, String newPass, String accessToken, OnHTTPResult result) {
        handler.post(() -> api.changePassword(accessToken, oldPass, newPass).enqueue(new Callback<BoolResponse>() {
            @Override
            public void onResponse(Call<BoolResponse> call, Response<BoolResponse> response) {
                int resCode = response.code();

                Log.i(TAG, "changeEmail -> onResponse: " + resCode);

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
                Log.i(TAG, "forgotPass -> onFailure: " + t.getMessage());
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
