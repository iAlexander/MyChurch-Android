package com.d2.pcu.fragments.cabinet.user_profile.change_data;

import android.view.View;

import com.d2.pcu.App;
import com.d2.pcu.R;
import com.d2.pcu.data.Repository;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.fragments.cabinet.user_profile.ProfileMenuViewModel;
import com.d2.pcu.listeners.InfoDialogListener;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.login.sign_up.UserProfileViewModel;
import com.d2.pcu.utils.Constants;

public class ChangeProfileEnteringDataViewModel extends BaseViewModel {

    private Repository repository;

    private ProfileMenuViewModel.ChangeDataType changeDataType;

    private String oldValue;
    private String newValue;

    private OnBackButtonClickListener onBackButtonClickListener;
    private InfoDialogListener infoDialogListener;

    public ChangeProfileEnteringDataViewModel() {
        repository = App.getInstance().getRepositoryInstance();
    }

    void setChangeDataType(ProfileMenuViewModel.ChangeDataType changeDataType) {
        this.changeDataType = changeDataType;
    }

    void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    void setInfoDialogListener(InfoDialogListener infoDialogListener) {
        this.infoDialogListener = infoDialogListener;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public void onBackPressed(View view) {
        oldValue = "";
        newValue = "";
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }

    public void apply(View view) {
        if (changeDataType == ProfileMenuViewModel.ChangeDataType.EMAIL) {
            repository.changeEmail(newValue, repository.getCredentials(Constants.ACCESS_TOKEN),
                    new UserProfileViewModel.OnRequestResult() {
                        @Override
                        public void onSuccess() {
                            oldValue = "";
                            newValue = "";
                            if (infoDialogListener != null) {
                                infoDialogListener.showInfoDialog(R.string.email_was_change, R.string.understand_text);
                                if (onBackButtonClickListener != null) {
                                    onBackButtonClickListener.onBackButtonPressed();
                                }
                            }
                        }
                    });
        } else {
            repository.changePassword(oldValue, newValue, repository.getCredentials(Constants.ACCESS_TOKEN),
                    new UserProfileViewModel.OnRequestResult() {
                        @Override
                        public void onSuccess() {
                            oldValue = "";
                            newValue = "";
                            if (infoDialogListener != null) {
                                infoDialogListener.showInfoDialog(R.string.pass_was_change, R.string.understand_text);
                                if (onBackButtonClickListener != null) {
                                    onBackButtonClickListener.onBackButtonPressed();
                                }
                            }
                        }
                    });
        }
    }
}
