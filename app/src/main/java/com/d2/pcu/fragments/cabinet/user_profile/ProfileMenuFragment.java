package com.d2.pcu.fragments.cabinet.user_profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentUserProfileMenuBinding;
import com.d2.pcu.fragments.BaseFragment;

public class ProfileMenuFragment extends BaseFragment {

    private FragmentUserProfileMenuBinding binding;
    private ProfileMenuViewModel viewModel;

    private OnEditProfileDataClickListener onEditProfileDataClickListener;

    private String selectedName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserProfileMenuBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ProfileMenuViewModel.class);
        setViewModelListeners(viewModel);
        viewModel.setOnEditProfileDataClickListener(onEditProfileDataClickListener);

        binding.setModel(viewModel);
        setSelectorData();

        getDefaultSelectedFragmentName();
        binding.defaultScreenSelector.setDialogSelectedText(selectedName);

        viewModel.getUserProfileLiveData().observe(
                getViewLifecycleOwner(),
                userProfile -> {
                    String fullName = userProfile.getFirstName() + " " + userProfile.getLastName();
                    binding.nameField.setText(fullName);
//                    binding.tvMember.setText(userProfile.getMember());
                }
        );
    }

    private void setSelectorData() {
        binding.defaultScreenSelector.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.intro_start_screen)).
                    setItems(R.array.menu_names, (dialog, which) -> {
                                binding.defaultScreenSelector.setDialogSelectedText(
                                        getResources().getStringArray(R.array.menu_names)[which]);
                                viewModel.setSelectedDefaultScreen(viewModel.collectFragments()[which]);
                            }
                    );
            builder.create().show();
        });
    }

    private void getDefaultSelectedFragmentName() {
        String[] names = getResources().getStringArray(R.array.menu_names);

        int defaultSelectedFragment = viewModel.getSelectedDefaultScreen();
        if (defaultSelectedFragment != R.id.resource_unset)
            selectedName = names[defaultSelectedFragment];
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onEditProfileDataClickListener = (OnEditProfileDataClickListener) context;
    }

    @Override
    public void onDetach() {
        onEditProfileDataClickListener = null;
        super.onDetach();
    }
}
