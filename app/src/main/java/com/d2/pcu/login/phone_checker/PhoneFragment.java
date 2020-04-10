package com.d2.pcu.login.phone_checker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.d2.pcu.R;
import com.d2.pcu.databinding.CheckPhoneFragmentBinding;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;

public class PhoneFragment extends Fragment {

    private static final String TAG = PhoneFragment.class.getSimpleName();

    private PhoneViewModel viewModel;
    private CheckPhoneFragmentBinding binding;

    private PrefixEditText editor;
    private AppCompatButton bApply;

    private OnLoadingStateChangedListener onLoadingStateChangeListener;
    private OnBackButtonClickListener onBackButtonClickListener;

    public static PhoneFragment newInstance() {
        return new PhoneFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.check_phone_fragment, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editor = view.findViewById(R.id.edPhone);
        bApply = view.findViewById(R.id.bApply);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(PhoneViewModel.class);
        viewModel.setListener((OnPhoneSent) getActivity());
        viewModel.setOnLoadingStateChangeListener(onLoadingStateChangeListener);
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);

        viewModel.getPhoneChannel().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                bApply.setEnabled(s.length() == 9);
            }
        });

        binding.setModel(viewModel);
        binding.edPhone.post(new Runnable() {
            @Override
            public void run() {
                binding.edPhone.requestFocus();
            }
        });

        editor.addTextChangedListener(viewModel);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onLoadingStateChangeListener = (OnLoadingStateChangedListener) context;
        onBackButtonClickListener = (OnBackButtonClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onLoadingStateChangeListener = null;
        onBackButtonClickListener = null;
    }
}
