package com.d2.pcu.fragments.cabinet.user_profile;

public interface OnEditProfileDataClickListener {

    void onEditEmailOrPasswordClick(ProfileMenuViewModel.ChangeDataType changeDataType);

    void onEditPasswordClick(ProfileMenuViewModel.ChangeDataType changeDataType);
}
