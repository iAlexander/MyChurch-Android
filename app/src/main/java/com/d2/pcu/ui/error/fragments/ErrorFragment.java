package com.d2.pcu.ui.error.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentErrorBinding;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ErrorFragment extends BottomSheetDialogFragment {

    private static final String ERROR_TYPE = "errorType";

    private FragmentErrorBinding binding;

    private OnLoadingStateChangedListener onLoadingStateChangedListener;

    private int errorType;

    public static ErrorFragment newInstance(int errorType) {
        Bundle arg = new Bundle();
        arg.putInt(ERROR_TYPE, errorType);

        ErrorFragment errorFragment = new ErrorFragment();
        errorFragment.setArguments(arg);

        return errorFragment;
    }

    public ErrorFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            errorType = getArguments().getInt(ERROR_TYPE);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_error, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (errorType) {
            case Constants.ERROR_TYPE_NO_CONNECTION : {
                binding.errorImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_no_connection));
                binding.errorTitleTv.setText(getActivity().getResources().getString(R.string.error_no_connection_title));
                binding.errorDescriptionTv.setText(getActivity().getResources().getString(R.string.error_no_connection_description));
                break;
            }
            case Constants.ERROR_TYPE_SERVER_ERROR : {
                binding.errorImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_server_error));
                binding.errorTitleTv.setText(getActivity().getResources().getString(R.string.error_server_error_title));
                binding.errorDescriptionTv.setText(getActivity().getResources().getString(R.string.error_server_error_description));
                break;
            }
        }

        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(false);
        }
        binding.errorApplyBtn.setOnClickListener(btnView -> ErrorFragment.this.dismiss());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onLoadingStateChangedListener = (OnLoadingStateChangedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onLoadingStateChangedListener = null;
    }
}
