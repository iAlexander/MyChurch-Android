package com.d2.pcu.fragments.cabinet.user_profile;

import android.content.res.TypedArray;
import android.view.View;

import androidx.lifecycle.LiveData;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.profile.UserProfile;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnNotificationClickListener;
import com.d2.pcu.utils.Constants;

public class ProfileMenuViewModel extends BaseViewModel {

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnEditProfileDataClickListener onEditProfileDataClickListener;
    private OnNotificationClickListener onNotificationClickListener;

    private Repository repository;

    private int selectedDefaultScreen;

    private LiveData<UserProfile> userProfileLiveData;

    public ProfileMenuViewModel() {
        repository = App.getInstance().getRepositoryInstance();
        selectedDefaultScreen = repository.getSelectedStartScreenId();

        repository.getUserProfile(repository.getCredentials(Constants.ACCESS_TOKEN));

        userProfileLiveData = repository.getTransport().getUserProfileChannel();
    }

    void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    void setOnEditProfileDataClickListener(OnEditProfileDataClickListener onEditProfileDataClickListener) {
        this.onEditProfileDataClickListener = onEditProfileDataClickListener;
    }

    void setOnNotificationClickListener(OnNotificationClickListener onNotificationClickListener) {
        this.onNotificationClickListener = onNotificationClickListener;
    }

    public LiveData<UserProfile> getUserProfileLiveData() {
        return userProfileLiveData;
    }

    void setSelectedDefaultScreen(int selectedDefaultScreen) {
        this.selectedDefaultScreen = selectedDefaultScreen;
        repository.setSelectedStartScreenId(selectedDefaultScreen);
    }

    int getSelectedDefaultScreen() {
        return selectedDefaultScreen;
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }

    public void onEditEmail(View view, ChangeDataType changeDataType) {
        if (onEditProfileDataClickListener != null) {
            onEditProfileDataClickListener.onEditEmailOrPasswordClick(changeDataType);
        }
    }

    public void onEditPasswordClick(View view, ChangeDataType changeDataType) {
        if (onEditProfileDataClickListener != null) {
            onEditProfileDataClickListener.onEditEmailOrPasswordClick(changeDataType);
        }
    }

    int[] collectFragments(TypedArray fragmentsIds) {
        int[] fragmentId = new int[fragmentsIds.length()];

        for (int i = 0; i < fragmentsIds.length(); i++) {
            int id = fragmentsIds.getResourceId(i, -1);
            if (id != -1) {
                fragmentId[i] = (id);
            }
        }
        return fragmentId;
    }

    public void onNotificationClick(View view) {
        if (onNotificationClickListener != null) {
            onNotificationClickListener.onNotificationClick();
        }
    }

    public enum ChangeDataType {
        EMAIL, PASSWORD
    }
}
