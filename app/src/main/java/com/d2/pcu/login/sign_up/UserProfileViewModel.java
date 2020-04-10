package com.d2.pcu.login.sign_up;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.diocese.Diocese;
import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.data.model.profile.UserProfile;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.login.SignInOnClickListener;
import com.d2.pcu.login.user_type.UserType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class UserProfileViewModel extends ViewModel {

    private Repository repository;
    private UserType userType;

    private UserProfile userProfile;

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnSaveClickListener onSaveClickListener;

    private LiveData<List<BaseTemple>> templesLiveData;
    private List<BaseTemple> searchedTemplesList;

    private LiveData<List<Diocese>> dioceseLiveData;

    private SignInOnClickListener signInOnClickListener;

    public UserProfileViewModel() {
        repository = App.getInstance().getRepositoryInstance();

        if (repository.getTransport().getBaseTemplesChannel().getValue() == null) {
            repository.getShortTemplesInfo();
        }

        repository.getDiocese();

        templesLiveData = repository.getTransport().getBaseTemplesChannel();
        dioceseLiveData = repository.getTransport().getDioceseChannel();

        searchedTemplesList = new LinkedList<>();

        userProfile = new UserProfile();
    }

    void setSignInOnClickListener(SignInOnClickListener signInOnClickListener) {
        this.signInOnClickListener = signInOnClickListener;
    }

    void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    void setOnSaveClickListener(OnSaveClickListener onSaveClickListener) {
        this.onSaveClickListener = onSaveClickListener;
    }

    UserProfile getUserProfile() {
        return userProfile;
    }

    UserType getUserType() {
        return userType;
    }

    void setUserType(UserType userType) {
        this.userType = userType;
    }

    List<BaseTemple> getTemplesListByName(String query) {
       if (templesLiveData.getValue() != null) {
           searchedTemplesList.clear();

           for (BaseTemple temple : templesLiveData.getValue()) {

               if (temple.getName().toLowerCase().startsWith(query.toLowerCase())) {
                   searchedTemplesList.add(temple);
               }
           }

           return searchedTemplesList;
       }

       return Collections.emptyList();
    }

    LiveData<List<BaseTemple>> getTemplesList() {
        return templesLiveData;
    }

    List<Diocese> getDioceseList() {
        if (dioceseLiveData.getValue() != null) {
            return dioceseLiveData.getValue();
        }

        return Collections.emptyList();
    }

    String[] getDioceseNames() {
        if (dioceseLiveData.getValue() != null) {
            List<Diocese> dioceseList = dioceseLiveData.getValue();

            String[] names = new String[dioceseList.size()];

            for (int i = 0; i < names.length; i++) {
                names[i] = dioceseList.get(i).getName();
            }

            return names;
        }

        return new String[0];
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }

    public void onSaveClick(View view) {
        if (onSaveClickListener != null) {
            onSaveClickListener.onSaveClick();

            if (valid) {
                userProfile.setFirebaseToken(App.getInstance().getFirebaseToken());
                repository.signUp(userProfile, () -> {
                    if (signInOnClickListener != null) {
                        signInOnClickListener.onSignedUp();
                    }
                });
            }
        }
    }

    boolean valid;

    public interface OnRequestResult {
        void onSuccess();
    }
}
