package com.d2.pcu.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.d2.pcu.R;
import com.d2.pcu.StartFragments;
import com.d2.pcu.data.db.MasterDbModel;
import com.d2.pcu.data.db.OnDbResult;
import com.d2.pcu.data.db.OnDbResultState;
import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.data.model.news.NewsList;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.data.model.pray.PraysList;
import com.d2.pcu.data.model.profile.NotificationHistoryItem;
import com.d2.pcu.data.model.profile.UserProfile;
import com.d2.pcu.data.model.profile.UserState;
import com.d2.pcu.data.responses.BoolDataResponse;
import com.d2.pcu.data.responses.BoolResponse;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.d2.pcu.data.responses.calendar.CalendarResponse;
import com.d2.pcu.data.model.UpdateResponse;
import com.d2.pcu.data.responses.calendar.EventResponse;
import com.d2.pcu.data.responses.diocese.DioceseResponse;
import com.d2.pcu.data.responses.map.TempleResponse;
import com.d2.pcu.data.responses.news.NewsResponse;
import com.d2.pcu.data.responses.pray.PrayResponse;
import com.d2.pcu.data.responses.profile.GetUserProfileResponse;
import com.d2.pcu.data.responses.profile.NotificationHistory;
import com.d2.pcu.data.responses.profile.PaymentUrl;
import com.d2.pcu.data.responses.profile.ProfileSignUpResponse;
import com.d2.pcu.data.responses.temples.ShortTemplesInfoResponse;
import com.d2.pcu.login.OnLoginError;
import com.d2.pcu.login.sign_up.UserProfileViewModel;
import com.d2.pcu.login.user_type.UserType;
import com.d2.pcu.ui.error.HTTPException;
import com.d2.pcu.ui.error.OnError;
import com.d2.pcu.ui.error.OnHTTPMasterResult;
import com.d2.pcu.ui.error.OnHTTPResult;
import com.d2.pcu.utils.Constants;
import com.d2.pcu.utils.Locator;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class Repository implements LifecycleObserver, LifecycleOwner {

    private static final String TAG = Repository.class.getSimpleName();

    private LifecycleRegistry lifecycleRegistry;
    private Gson gson;
    private NetLoader netLoader;
    private DbLoader dbLoader;
    private Transport channels;
    private SharedPreferences sharedPreferences;

    private OnError onError;

    public void setOnErrorListener(OnError onError) {
        this.onError = onError;
    }

    private OnLoginError onLoginErrorListener;

    public void setOnLoginErrorListener(OnLoginError onLoginErrorListener) {
        this.onLoginErrorListener = onLoginErrorListener;
    }

    public void removeOnLoginErrorListener() {
        this.onLoginErrorListener = null;
    }


    public Repository(Context context) {
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.setCurrentState(Lifecycle.State.INITIALIZED);
        channels = new Transport();

        netLoader = new NetLoader();
        dbLoader = new DbLoader();

        sharedPreferences = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        getLifecycle().addObserver(netLoader);
        getLifecycle().addObserver(dbLoader);

        Log.i(TAG, "Created");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    public Transport getTransport() {
        return channels;
    }

    public boolean getOnBoardingState() {
        return sharedPreferences.getBoolean(Constants.STATE_ON_BOARDING, false);
    }

    public void setOnBoardingState(boolean state) {
        sharedPreferences.edit().putBoolean(Constants.STATE_ON_BOARDING, state).apply();
    }

    public void setSelectedStartScreenId(@StartFragments int id) {
        sharedPreferences.edit().putInt(Constants.START_SCREEN_ID, id).apply();
    }

    public int getSelectedStartScreenId() {
        return sharedPreferences.getInt(Constants.START_SCREEN_ID, R.id.resource_unset);
    }

    public void saveAgreementApprove(boolean isApply) {
        sharedPreferences.edit().putBoolean(Constants.STATE_AGREEMENT, isApply).apply();
    }

    public boolean getAgreementState() {
        return sharedPreferences.getBoolean(Constants.STATE_AGREEMENT, false);
    }

    public void saveUserType(UserType userType) {
        sharedPreferences.edit().putString(Constants.USER_TYPE, userType.toString()).apply();
    }

    public UserType getUserType() {
        return UserType.fromString(sharedPreferences.getString(Constants.USER_TYPE, ""));
    }

    private void saveCredentials(String credential, String credentialType) {
        sharedPreferences.edit().putString(credentialType, credential).apply();
    }

    public String getCredentials(String credential) {
        return sharedPreferences.getString(credential, "");
    }

    public void removeCredentials() {
        sharedPreferences.edit().putString(Constants.USER_EMAIL, "").apply();
    }

    public void setAuthState(UserState userState) {
        sharedPreferences.edit().putString("isAuth", userState.toString()).apply();
    }

    public UserState getAuthState() {
        return UserState.fromString(sharedPreferences.getString("isAuth", ""));
    }

    public void setLastLocation(LatLng location) {
        if (location == null) {
            sharedPreferences.edit().remove(Constants.LAST_LOCATION).apply();
        } else {
            sharedPreferences.edit().putString(Constants.LAST_LOCATION, getGson().toJson(location)).apply();
        }
    }

    public LatLng getLastLocation() {
        LatLng latLng = Locator.DEFAULT_KYIV;
        String json = sharedPreferences.getString(Constants.LAST_LOCATION, "");
        if (!TextUtils.isEmpty(json)) {
            try {
                latLng = getGson().fromJson(json, LatLng.class);
            } catch (Exception e) {
                Timber.e(e, "parse json from sp: %s", e.getMessage());
            }
        }
        return latLng;
    }

    public void getShortTemplesInfo() {
        netLoader.getShortTemplesInfo(new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                channels.getBaseTemplesChannel().postValue(((ShortTemplesInfoResponse) response).getData().getList());
            }

            @Override
            public void onFail(Throwable ex) {
                Timber.e(ex, "getShortTemplesInfo: %s", ex.getMessage());
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void getShortTemplesInfo(LatLng latLng) {
        netLoader.getShortTemplesInfo(new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                List<BaseTemple> temples = ((ShortTemplesInfoResponse) response).getData().getList();

                for (BaseTemple temple : temples) {
                    temple.setDistance(SphericalUtil.computeDistanceBetween(latLng, temple.getLatLng()) / 1000);
                }

                Collections.sort(
                        temples,
                        (o1, o2) -> Double.compare(o1.getDistance(), o2.getDistance())
                );

                channels.getBaseTemplesChannel().postValue(temples);
            }

            @Override
            public void onFail(Throwable ex) {
                Timber.e(ex, "getShortTemplesInfo: %s", ex.getMessage());
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void getTempleById(int id) {
        netLoader.getTempleById(id, new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                channels.getTempleChannel().setValue(((TempleResponse) response).getTemple());
            }

            @Override
            public void onFail(Throwable ex) {
                Timber.d(ex, "getTempleById: %s", ex.getMessage());
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public LiveData<List<CalendarItem>> getCalendarItemsLiveData(){
        return dbLoader.getCalendarItems();
    }

    public void checkCalendarUpdate(){

        netLoader.getCalendarUpdate(new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                String updateToken = "";
                if (response != null) {
                    updateToken = ((UpdateResponse) response)
                            .getLastUpdate()
                            .getHash();
                }
                String lastToken = sharedPreferences.getString(Constants.CALENDAR_UPDATE_TOKEN,"");
                if(!Objects.equals(lastToken, updateToken)){
                    getCalendar(updateToken);
                }
            }

            @Override
            public void onFail(Throwable ex) {
                Timber.d(ex, "getCalendarUpdate: %s", ex.getMessage());
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void getCalendar(String updateToken) {


        netLoader.getCalendarInfo(new OnHTTPResult() {

            @Override
            public void onSuccess(OnMasterResponse response) {
                List<CalendarItem> items = new ArrayList<>();

                if (response != null) {
                    items = ((CalendarResponse) response).
                            getCalendarDataWrapper().getCalendarItems();
                }
                if (CollectionUtils.isEmpty(items)) {
                    channels.getCalendarChannel().postValue(new ArrayList<>());
                } else {
                    dbLoader.saveCalendarItems(items, isSuccess -> {

                    });
                }

                sharedPreferences.edit().putString(Constants.CALENDAR_UPDATE_TOKEN, updateToken).apply();
            }

            @Override
            public void onFail(Throwable ex) {
                Timber.d(ex, "getCalendar: %s", ex.getMessage());
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void getEventInfo(final int id) {
        netLoader.getEventInfo(id, new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                channels.getEventChannel().postValue(((EventResponse) response).getEvent());
            }

            @Override
            public void onFail(Throwable ex) {
                Timber.d(ex, "getEventInfo: %s", ex.getMessage());
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void getNews(final int length) {
        netLoader.getNews(length, new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                List<NewsItem> items = ((NewsResponse) response).getNews();

                dbLoader.updateAndSaveNewsToDb(items, isSuccess -> {
                    if (isSuccess) {
                        dbLoader.getNews(dbModel ->
                                channels.getNewsChannel().postValue(((NewsList) dbModel).getItems()));
                    }
                });
            }

            @Override
            public void onFail(Throwable ex) {
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void updateNewsItemAsRead(NewsItem newsItem) {
        dbLoader.updateReadNewsItem(newsItem, isSuccess -> {
            // TODO: 2020-01-13
        });
    }

    public void updateNotificationItemAsRead(NotificationHistoryItem item) {
        dbLoader.updateReadNotificationItem(item, isSuccess -> {
            // TODO: 2020-01-13
        });
    }

    public void checkPrayUpdate(){

        netLoader.getPrayUpdate(new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                String updateToken = "";
                if (response != null) {
                    updateToken = ((UpdateResponse) response)
                            .getLastUpdate()
                            .getHash();
                }
                String lastToken = sharedPreferences.getString(Constants.PRAY_UPDATE_TOKEN,"");
                if(!Objects.equals(lastToken, updateToken)){
                    getPrays(updateToken);
                }
            }

            @Override
            public void onFail(Throwable ex) {
                Timber.d(ex, "getPrayUpdate: %s", ex.getMessage());
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void getPrays(String updateToken) {
        netLoader.getPrays(new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {

                List<Pray> items = new ArrayList<>();

                if (response != null) {
                    items = ((PrayResponse) response).getPrayDataWrapper().getPrays();
                }
                if (CollectionUtils.isEmpty(items)) {
                    channels.getMorningServerPraysChannel().postValue(new ArrayList<>());
                    channels.getEveningServerPraysChannel().postValue(new ArrayList<>());
                } else {
                    dbLoader.savePrays(items);
                }

                if(!Constants.PRAY_FORCE_UPDATE.equals(updateToken)){
                    sharedPreferences.edit().putString(Constants.PRAY_UPDATE_TOKEN, updateToken).apply();
                }
            }

            @Override
            public void onFail(Throwable ex) {
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public LiveData<List<Pray>> getPraysLiveData(String type){
        return dbLoader.getPrays(type);
    }

    public void getDiocese() {
        netLoader.getDioceses(new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                channels.getDioceseChannel().postValue(((DioceseResponse) response).getDioceseDataWrapper().getDioceseList());
            }

            @Override
            public void onFail(Throwable ex) {
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void signIn(String email, String password, UserProfileViewModel.OnRequestResult onRequestResult) {
        netLoader.signIn(email, password, new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                boolean ok = ((ProfileSignUpResponse) response).isOk();
                String accessToken = ((ProfileSignUpResponse) response).getProfileAccessTokenWrapperResponse().getAccessToken();

                if (accessToken == null || accessToken.isEmpty()) {
                    if (ok) {
                        saveCredentials(email, Constants.USER_EMAIL);

                        // FIXME: 5/28/20 send pushToken
                        FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener(task -> {
                                    if (!task.isSuccessful()) {
                                        Timber.w(task.getException(), "getInstanceId failed");
                                    } else if (task.getResult() != null) {
                                        // Get new Instance ID token
                                        String firebaseToken = task.getResult().getToken();
                                        updatePushToken(firebaseToken);
                                        Timber.d("Firebase Message Token: %s", firebaseToken);
                                    }
                                });
                    }
                } else {
                    saveCredentials(email, Constants.USER_EMAIL);
                    saveCredentials(accessToken, Constants.ACCESS_TOKEN);
                }

                onRequestResult.onSuccess();
            }

            @Override
            public void onFail(Throwable ex) {
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onLoginErrorListener.onError(ex.getMessage());
                    } else {
                        onLoginErrorListener.onError("Перевірте підключення до інтернету");
                    }
                }
            }
        });
    }

    public void signUp(UserProfile userProfile, UserProfileViewModel.OnRequestResult onRequestResult) {
        final String email = userProfile.getEmail();

        netLoader.signUp(userProfile, new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                boolean ok = ((ProfileSignUpResponse) response).isOk();
                String accessToken = ((ProfileSignUpResponse) response).getProfileAccessTokenWrapperResponse().getAccessToken();

                if (accessToken == null || accessToken.isEmpty()) {
                    if (ok) {
                        saveCredentials(email, Constants.USER_EMAIL);
                    }
                } else {
                    saveCredentials(email, Constants.USER_EMAIL);
                    saveCredentials(accessToken, Constants.ACCESS_TOKEN);
                }

                onRequestResult.onSuccess();
            }

            @Override
            public void onFail(Throwable ex) {
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onLoginErrorListener.onError(ex.getMessage());
                    } else {
                        onLoginErrorListener.onError("Перевірте підключення до інтернету");
                    }
                }
            }
        });
    }

    public void forgotPass(String email, UserProfileViewModel.OnRequestResult requestResult) {

        netLoader.forgotPass(email, new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                boolean ok = ((BoolResponse) response).isOk();

                if (ok) {
                    requestResult.onSuccess();
                }
            }

            @Override
            public void onFail(Throwable ex) {
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onLoginErrorListener.onError(ex.getMessage());
                    } else {
                        onLoginErrorListener.onError("Перевірте підключення до інтернету");
                    }
                }
            }
        });
    }

    public void updatePushToken(String token) {
        String auth = getCredentials(Constants.ACCESS_TOKEN);
        if (TextUtils.isEmpty(auth)) return;
        netLoader.updatePushToken(auth, token, new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                boolean ok = ((BoolResponse) response).isOk();

                if (ok) saveCredentials(token, Constants.PUSH_TOKEN);
            }

            @Override
            public void onFail(Throwable ex) {
                try {
                    Gson gson = new Gson();

                    JsonObject errorBody = gson.fromJson(ex.getMessage(), JsonObject.class);
                    String newToken = errorBody.getAsJsonObject().get("data").getAsJsonObject().get("accessToken").getAsString();

                    if (newToken != null && !newToken.isEmpty()) {
                        saveCredentials(newToken, Constants.ACCESS_TOKEN);
                        updatePushToken(token);
                    }

                } catch (JsonSyntaxException ignore) {
                }
            }
        });
    }

    public LiveData<List<NotificationHistoryItem>> getNotificationLiveData(){
        return dbLoader.getNotifications();
    }

    public void getNotificationHistory() {
        netLoader.getNotifications(getCredentials(Constants.ACCESS_TOKEN), new OnHTTPMasterResult<BoolDataResponse<NotificationHistory>>() {
            @Override
            public void onSuccess(BoolDataResponse<NotificationHistory> response) {
                List<NotificationHistoryItem> items = new ArrayList<>();
                if (response.getData() != null) {
                    items = response.getData().getNotificationList();
                }
                if (CollectionUtils.isEmpty(items)) {
                    channels.getNotificationChannel().postValue(new ArrayList<>());
                } else {
                    dbLoader.updateAndSaveNotificationToDb(items, isSuccess -> {
//                        if (isSuccess) {
//                            dbLoader.getNotifications(dbModel ->
//                                    channels.getNotificationChannel()
//                                            .postValue(((NotificationList) dbModel).getItems()));
//                        }
                    });
                }
            }

            @Override
            public void onFail(Throwable ex) {
                try {
                    Gson gson = new Gson();

                    JsonObject errorBody = gson.fromJson(ex.getMessage(), JsonObject.class);
                    String newToken = errorBody.getAsJsonObject().get("data").getAsJsonObject().get("accessToken").getAsString();

                    if (newToken != null && !newToken.isEmpty()) {
                        saveCredentials(newToken, Constants.ACCESS_TOKEN);
                        getNotificationHistory();
                        return;
                    }

                } catch (JsonSyntaxException ignore) {
                }
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void getNotificationHistory(int id) {
        netLoader.getNotificationCard(id, getCredentials(Constants.ACCESS_TOKEN), new OnHTTPMasterResult<BoolDataResponse<NotificationHistoryItem>>() {
            @Override
            public void onSuccess(BoolDataResponse<NotificationHistoryItem> response) {
                NotificationHistoryItem item = response.getData();

                if (item == null) {
                    channels.getNotificationCardChannel().postValue(null);
                } else {
                    dbLoader.updateAndSaveNotificationToDb(item, isSuccess -> {
                        if (isSuccess) {
                            dbLoader.getNotification(id, dbModel ->
                                    channels.getNotificationCardChannel()
                                            .postValue((NotificationHistoryItem) dbModel));
                        }
                    });
                }
            }

            @Override
            public void onFail(Throwable ex) {
                try {
                    Gson gson = new Gson();

                    JsonObject errorBody = gson.fromJson(ex.getMessage(), JsonObject.class);
                    String newToken = errorBody.getAsJsonObject().get("data").getAsJsonObject().get("accessToken").getAsString();

                    if (newToken != null && !newToken.isEmpty()) {
                        saveCredentials(newToken, Constants.ACCESS_TOKEN);
                        getNotificationHistory(id);
                        return;
                    }

                } catch (JsonSyntaxException ignore) {
                }
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public LiveData<Integer> getUnreadNotificationCount(){
        return dbLoader.getUnreadNotificationCount();
    }

    public void saveNotificationToDb(NotificationHistoryItem item) {
        dbLoader.saveNotification(item);
    }

    public void getUserProfile(String accessToken) {

        String authHeader = "Bearer " + accessToken;
        netLoader.getUserProfile(authHeader, new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                boolean ok = ((GetUserProfileResponse) response).isOk();
                UserProfile up = ((GetUserProfileResponse) response).getUserProfile();
                if (up != null && up.getChurch() != null) {
                    setLastLocation(((GetUserProfileResponse) response).getUserProfile().getChurch().getLatLng());
                }
                getTransport().getUserProfileChannel().postValue(((GetUserProfileResponse) response).getUserProfile());
            }

            @Override
            public void onFail(Throwable ex) {

                try {
                    Gson gson = new Gson();

                    JsonObject errorBody = gson.fromJson(ex.getMessage(), JsonObject.class);
                    String newToken = errorBody.getAsJsonObject().get("data").getAsJsonObject().get("accessToken").getAsString();

                    if (newToken != null && !newToken.isEmpty()) {
                        getUserProfile(newToken);
                        saveCredentials(newToken, Constants.ACCESS_TOKEN);
                        return;
                    }

                } catch (JsonSyntaxException ignore) {
                }

                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void changeEmail(String newEmail, String accessToken, UserProfileViewModel.OnRequestResult onRequestResult) {
        String authHeader = "Bearer " + accessToken;
        netLoader.changeEmail(newEmail, authHeader, new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                boolean ok = ((BoolResponse) response).isOk();

                if (ok) {
                    onRequestResult.onSuccess();
                }

            }

            @Override
            public void onFail(Throwable ex) {
                try {
                    Gson gson = new Gson();

                    JsonObject errorBody = gson.fromJson(ex.getMessage(), JsonObject.class);
                    String newToken = errorBody.getAsJsonObject().get("data").getAsJsonObject().get("accessToken").getAsString();

                    if (newToken != null && !newToken.isEmpty()) {
                        saveCredentials(newToken, Constants.ACCESS_TOKEN);
                        changeEmail(newEmail, newToken, onRequestResult);
                        return;
                    }

                } catch (JsonSyntaxException ignore) {
                }

                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void changePassword(String oldPass, String newPass, String accessToken, UserProfileViewModel.OnRequestResult onRequestResult) {
        String authHeader = "Bearer " + accessToken;
        netLoader.changePassword(oldPass, newPass, authHeader, new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                boolean ok = ((BoolResponse) response).isOk();

                if (ok) {
                    onRequestResult.onSuccess();
                }
            }

            @Override
            public void onFail(Throwable ex) {
                try {
                    Gson gson = new Gson();

                    JsonObject errorBody = gson.fromJson(ex.getMessage(), JsonObject.class);

                    String newToken = errorBody.getAsJsonObject().get("data").getAsJsonObject().get("accessToken").getAsString();

                    if (newToken != null && !newToken.isEmpty()) {
                        saveCredentials(newToken, Constants.ACCESS_TOKEN);
                        changePassword(oldPass, newPass, newToken, onRequestResult);
                        return;
                    }

                } catch (JsonSyntaxException ignore) {
                }

                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void getCheckOut(String action, String resultUrl) {

        netLoader.getPayUrl(action, resultUrl, new OnHTTPMasterResult<PaymentUrl>() {
            @Override
            public void onSuccess(PaymentUrl response) {
                channels.getPaymentChannel().postValue(response.getData().getUrl());
            }

            @Override
            public void onFail(Throwable ex) {
                if (onError != null) {
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    //    public void downloadFile(String url, File fileDest) {
//        netLoader.downloadFileSync(url, fileDest);
//    }
}
