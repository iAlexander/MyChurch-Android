package com.d2.pcu.login.sign_up;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.data.model.profile.UserProfile;
import com.d2.pcu.databinding.FragmentUserProfileBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.login.OnLoginError;
import com.d2.pcu.login.SignInOnClickListener;
import com.d2.pcu.login.sign_up.adapters_and_viewholders.TemplesDialogAdapter;
import com.d2.pcu.login.user_type.UserType;
import com.d2.pcu.utils.Constants;

import java.util.Calendar;
import java.util.Date;

public class UserProfileFragment extends BaseFragment {

    private FragmentUserProfileBinding binding;
    private UserProfileViewModel viewModel;

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnLoginError onLoginError;
    private SignInOnClickListener signInOnClickListener;

    private TemplesDialogAdapter templesAdapter;

    private AlertDialog churchDialog;

    private UserType userType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            userType = UserType.fromString(getArguments().getString(Constants.USER_TYPE));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.userProfileTazoimenstvoCv.findViewById(
                getResources().getIdentifier("year", "id", "android")
        ).setVisibility(View.GONE);

        binding.userProfileBirthdayCv.setMaxDate(new Date().getTime());

        templesAdapter = new TemplesDialogAdapter(shortTemple -> {
            binding.churchSelectorD.setDialogSelectedText(shortTemple.getName());
            viewModel.getUserProfile().setChurch(shortTemple);
            churchDialog.dismiss();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(UserProfileViewModel.class);
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);
        viewModel.setUserType(userType);
        viewModel.setSignInOnClickListener(signInOnClickListener);

        binding.setModel(viewModel);

        switch (viewModel.getUserType()) {
            case BELIEVER: {
                setBelieverProfile();
                break;
            }
            case CLERGY: {
                setClergyProfile();
                break;
            }
            case BISHOP: {
                setBishopProfile();
                break;
            }
        }

        binding.churchSelectorD.setOnClickListener(view -> {
            churchDialog.show();
        });

        churchDialog = assembleTemplesSearchDialog();

        viewModel.getTemplesList().observe(getViewLifecycleOwner(), shortTemples -> {
            templesAdapter.setTemples(shortTemples);
        });
    }

    private void setBelieverProfile() {
        changeViewVisibility(false,
                binding.sanTitleTvTop,
                binding.sanSelectorD,
                binding.tazoimenstvoTitleTv,
                binding.userProfileTazoimenstvoCv,
                binding.eparhiaTitleTv,
                binding.eparhiaSelectorD
        );

        changeViewVisibility(true,
                binding.statusTitleDuhovenstovTv,
                binding.statusSelectorViryaninD
        );

        binding.nameTitleTv.setText(getString(R.string.name));

        binding.statusSelectorViryaninD.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.
                    setTitle(getString(R.string.status)).
                    setItems(
                            R.array.believer_benefice,
                            (dialog, which) -> {
                                binding.
                                        statusSelectorViryaninD.
                                        setDialogSelectedText(
                                                getResources().
                                                        getStringArray(R.array.believer_benefice)[which]);

                                if (which == 0) {
                                    viewModel.getUserProfile().setMember("Parishioner");
                                } else {
                                    viewModel.getUserProfile().setMember("Mpc");
                                }
                            }
                    );
            builder.create().show();
        });

        binding.eparhiaBelieverSelectorD.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.
                    setTitle(getString(R.string.diocese)).
                    setItems(
                            viewModel.getDioceseNames(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    binding.eparhiaBelieverSelectorD.setDialogSelectedText(
                                            viewModel.getDioceseNames()[which]
                                    );

                                    viewModel.getUserProfile().setDiocese(
                                            viewModel.getDioceseList().get(which)
                                    );
                                }
                            }
                    );
            builder.create().show();
        });

        viewModel.setOnSaveClickListener(new OnSaveClickListener() {
            @Override
            public void onSaveClick() {
                UserProfile userProfile = viewModel.getUserProfile();

                userProfile.setFirstName(binding.userNameEt.getText().toString());
                userProfile.setLastName(binding.userSurnameEt.getText().toString());

                Calendar calendar = Calendar.getInstance();
                int day = binding.userProfileBirthdayCv.getDayOfMonth();
                int month = binding.userProfileBirthdayCv.getMonth();
                int year = binding.userProfileBirthdayCv.getYear();
                calendar.set(year, month, day);
                userProfile.setBirthday(calendar.getTime());

                userProfile.setAngelday(null);

                userProfile.setEmail(binding.userEmailEt.getText().toString());

                String phone = "380" + binding.userPhoneEt.getText().toString();
                userProfile.setPhone(phone);

                checkUserProfileValidity();
            }
        });
    }

    private void setClergyProfile() {
        changeViewVisibility(false,
                binding.sanTitleTvTop,
                binding.sanSelectorD,
                binding.eparhiaBelieverSelectorD,
                binding.eparhiaBelieverTitleTv
        );

        changeViewVisibility(true,
                binding.sanTitleDuhovenstovTv,
                binding.sanSelectorDuhovenstovD
        );

        binding.nameTitleTv.setText(getString(R.string.name));

        binding.sanSelectorDuhovenstovD.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.
                    setTitle(getString(R.string.benefice)).
                    setItems(
                            R.array.priest_state,
                            (dialog, which) -> {
                                binding.
                                        sanSelectorDuhovenstovD.
                                        setDialogSelectedText(
                                                getResources().
                                                        getStringArray(R.array.priest_state)[which]);

                                viewModel.getUserProfile().setMember("Priest");
                            }
                    );
            builder.create().show();
        });

        binding.eparhiaSelectorD.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.
                    setTitle(getString(R.string.diocese)).
                    setItems(
                            viewModel.getDioceseNames(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    binding.eparhiaSelectorD.setDialogSelectedText(
                                            viewModel.getDioceseNames()[which]
                                    );

                                    viewModel.getUserProfile().setDiocese(
                                            viewModel.getDioceseList().get(which)
                                    );
                                }
                            }
                    );
            builder.create().show();
        });

        viewModel.setOnSaveClickListener(new OnSaveClickListener() {
            @Override
            public void onSaveClick() {
                UserProfile userProfile = viewModel.getUserProfile();

                userProfile.setFirstName(binding.userNameEt.getText().toString());
                userProfile.setLastName(binding.userSurnameEt.getText().toString());

                Calendar calendar = Calendar.getInstance();
                int day = binding.userProfileBirthdayCv.getDayOfMonth();
                int month = binding.userProfileBirthdayCv.getMonth();
                int year = binding.userProfileBirthdayCv.getYear();
                calendar.set(year, month, day);
                userProfile.setBirthday(calendar.getTime());

                Calendar calendarAngel = Calendar.getInstance();
                int dayA = binding.userProfileBirthdayCv.getDayOfMonth();
                int monthA = binding.userProfileBirthdayCv.getMonth();
                int yearA = binding.userProfileBirthdayCv.getYear();
                calendar.set(yearA, monthA, dayA);
                userProfile.setAngelday(calendarAngel.getTime());

                userProfile.setEmail(binding.userEmailEt.getText().toString());

                String phone = "380" + binding.userPhoneEt.getText().toString();
                userProfile.setPhone(phone);

                checkUserProfileValidity();
            }
        });
    }

    private void setBishopProfile() {
        changeViewVisibility(false,
                binding.eparhiaBelieverSelectorD,
                binding.eparhiaBelieverTitleTv
        );

        binding.eparhiaSelectorD.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.
                    setTitle(getString(R.string.diocese)).
                    setItems(
                            viewModel.getDioceseNames(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    binding.eparhiaSelectorD.setDialogSelectedText(
                                            viewModel.getDioceseNames()[which]
                                    );

                                    viewModel.getUserProfile().setDiocese(
                                            viewModel.getDioceseList().get(which)
                                    );
                                }
                            }
                    );
            builder.create().show();
        });

        binding.sanSelectorD.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.
                    setTitle(getString(R.string.benefice)).
                    setItems(
                            R.array.bishop_benefice,
                            (dialog, which) -> {
                                binding.
                                        sanSelectorD.
                                        setDialogSelectedText(
                                                getResources().
                                                        getStringArray(R.array.bishop_benefice)[which]);

                                viewModel.getUserProfile().setMember("PriestPro");
                            }
                    );
            builder.create().show();
        });

        viewModel.setOnSaveClickListener(new OnSaveClickListener() {
            @Override
            public void onSaveClick() {
                UserProfile userProfile = viewModel.getUserProfile();

                userProfile.setFirstName(binding.userNameEt.getText().toString());
                userProfile.setLastName(binding.userSurnameEt.getText().toString());

                Calendar calendar = Calendar.getInstance();
                int day = binding.userProfileBirthdayCv.getDayOfMonth();
                int month = binding.userProfileBirthdayCv.getMonth();
                int year = binding.userProfileBirthdayCv.getYear();
                calendar.set(year, month, day);
                userProfile.setBirthday(calendar.getTime());

                Calendar calendarAngel = Calendar.getInstance();
                int dayA = binding.userProfileBirthdayCv.getDayOfMonth();
                int monthA = binding.userProfileBirthdayCv.getMonth();
                int yearA = binding.userProfileBirthdayCv.getYear();
                calendar.set(yearA, monthA, dayA);
                userProfile.setAngelday(calendarAngel.getTime());

                userProfile.setEmail(binding.userEmailEt.getText().toString());

                String phone = "380" + binding.userPhoneEt.getText().toString();
                userProfile.setPhone(phone);

                checkUserProfileValidity();
            }
        });
    }

    private void changeViewVisibility(boolean visible, View... views) {
        for (View v : views) {
            v.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    private AlertDialog assembleTemplesSearchDialog() {
        assert (getContext() != null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_temple_search, null);

        final SearchView searchView = dialogView.findViewById(R.id.serch_v);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 3) {
                    templesAdapter.setTemples(viewModel.getTemplesListByName(newText));
                }

                if (newText.length() == 0) {
                    if (viewModel.getTemplesList().getValue() != null) {
                        templesAdapter.setTemples(viewModel.getTemplesList().getValue());
                    }
                }
                return false;
            }
        });

        RecyclerView recyclerView = dialogView.findViewById(R.id.serch_rv);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.TOP;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        recyclerView.setAdapter(templesAdapter);

        return dialog;
    }

    private void checkUserProfileValidity() {
        if (onLoginError != null) {
            ifBlock:
            {
                UserProfile userProfile = viewModel.getUserProfile();
                if (userProfile.getFirstName().isEmpty()) {
                    onLoginError.onError(formatter(R.string.name));
                    viewModel.valid = false;
                    break ifBlock;
                }
                if (userProfile.getLastName().isEmpty()) {
                    onLoginError.onError(formatter(R.string.surname));
                    viewModel.valid = false;
                    break ifBlock;
                }
                if (userProfile.getPhone().isEmpty()) {
                    onLoginError.onError(formatter(R.string.phone_number));
                    viewModel.valid = false;
                    break ifBlock;
                }
                if (userProfile.getEmail().isEmpty()) {
                    onLoginError.onError(formatter(R.string.email));
                    viewModel.valid = false;
                    break ifBlock;
                }
                if (userProfile.getMember().isEmpty()) {
                    String error = formatter(R.string.status);
                    String fullError = error.concat("або \"" + getRes(R.string.benefice) + "\"");
                    onLoginError.onError(fullError);
                    viewModel.valid = false;
                    break ifBlock;
                }
                if (userProfile.getChurch().getId() == -1) {
                    onLoginError.onError(getRes(R.string.temple_name));
                    viewModel.valid = false;
                    break ifBlock;
                }
                if (userProfile.getDiocese().getId() == -1) {
                    onLoginError.onError(getRes(R.string.diocese));
                    viewModel.valid = false;
                    break ifBlock;
                }

               viewModel.valid = true;
            }
        }
    }

    private String formatter(int resId) {
        return getResources().getString(R.string.field_error_text, getRes(resId));
    }

    private String getRes(int resId) {
        return getString(resId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBackButtonClickListener = (OnBackButtonClickListener) context;
        onLoginError = (OnLoginError) context;
        signInOnClickListener = (SignInOnClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackButtonClickListener = null;
        onLoginError = null;
        signInOnClickListener = null;
    }
}
