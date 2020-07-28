package com.d2.pcu.fragments.cabinet.user_profile;

import android.view.View;

import androidx.lifecycle.LiveData;

import com.d2.pcu.App;
import com.d2.pcu.StartFragments;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.profile.UserProfile;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.login.LogoutOnClickListener;
import com.d2.pcu.utils.Constants;

public class ProfileMenuViewModel extends BaseViewModel {

    private OnEditProfileDataClickListener onEditProfileDataClickListener;
    private LogoutOnClickListener logoutOnClickListener;

    private Repository repository;

    private int selectedDefaultScreen;

    private LiveData<UserProfile> userProfileLiveData;

    public ProfileMenuViewModel() {
        repository = App.getInstance().getRepositoryInstance();
        selectedDefaultScreen = repository.getSelectedStartScreenId();

        repository.getUserProfile(repository.getCredentials(Constants.ACCESS_TOKEN));

        userProfileLiveData = repository.getTransport().getUserProfileChannel();
    }

    void setOnEditProfileDataClickListener(OnEditProfileDataClickListener onEditProfileDataClickListener) {
        this.onEditProfileDataClickListener = onEditProfileDataClickListener;
    }

    public void setLogoutOnClickListener(LogoutOnClickListener logoutOnClickListener) {
        this.logoutOnClickListener = logoutOnClickListener;
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

    public void onLogout(View view) {
        repository.getNotificationHistory();
        if (logoutOnClickListener != null) {
            logoutOnClickListener.onLogout();
        }
    }


    int[] collectFragments() {
        return new int[]{
                StartFragments.MAP,
                StartFragments.CALENDAR,
                StartFragments.NEWS,
                StartFragments.PRAY,
                StartFragments.PROFILE
        };
    }

    public enum ChangeDataType {
        EMAIL, PASSWORD
    }
}
