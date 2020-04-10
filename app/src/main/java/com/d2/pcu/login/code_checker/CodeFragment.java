package com.d2.pcu.login.code_checker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.d2.pcu.R;
import com.d2.pcu.databinding.CheckCodeFragmentBinding;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.login.phone_checker.OnPhoneSent;
import com.google.android.material.textfield.TextInputLayout;

public class CodeFragment extends Fragment //implements MySMSBroadcastReceiver.SMSReceiveListener
{

    private static final String TAG = "CodeFragment";

    private CodeViewModel viewModel;
    private CheckCodeFragmentBinding binding;

    private AppCompatEditText edCode;
    private TextInputLayout tlCode;
    private AppCompatButton bApply;

    private String phone = "";

    private OnLoadingStateChangedListener onLoadingStateChangeListener;
    private OnBackButtonClickListener onBackButtonClickListener;

//    private MySMSBroadcastReceiver mySMSBroadcastReceiver;

    public static CodeFragment newInstance() {
        return new CodeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            phone = getArguments().getString("phone");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.check_code_fragment, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edCode = view.findViewById(R.id.edCode);
        tlCode = view.findViewById(R.id.ilTextInput);
        bApply = view.findViewById(R.id.bApply);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(CodeViewModel.class);
        viewModel.setListener( (OnCodeSent) getActivity() );
        viewModel.setOnLoadingStateChangeListener(onLoadingStateChangeListener);
        viewModel.disableLoading();
        viewModel.setPhone(phone);
        viewModel.setPhoneListener( (OnPhoneSent) getActivity() );
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);

        binding.setModel(viewModel);

        viewModel.getCodeChannel().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                bApply.setEnabled(s.length() == 4);
                tlCode.setError(null);
            }
        });

        if ( ((OnErrorChannelCallback) getActivity()) != null ) {
            MutableLiveData<Boolean> errorChannel = ((OnErrorChannelCallback) getActivity()).getErrorChannel();
            errorChannel.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean error) {
                    if (error) {
//                        tlCode.setError(getString(R.string.login_invalid_code));
                    }
                }
            });
        }

        viewModel.isBtnEnable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.invalidateAll();
            }
        });

        edCode.addTextChangedListener(viewModel);


        //SMS
/*        mySMSBroadcastReceiver = new MySMSBroadcastReceiver();
        mySMSBroadcastReceiver.setSmsReceiver(this);

        startSmsListener();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);

        getActivity().getApplicationContext().registerReceiver(mySMSBroadcastReceiver, intentFilter);
        new AppSignatureHelper(getActivity().getApplicationContext()).getAppSignatures();
    }

    private void startSmsListener() {
        SmsRetrieverClient smsClient = SmsRetriever.getClient(getContext());
        Task<Void> task = smsClient.startSmsRetriever();

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: SmsRetrieverClient");
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // TODO: 2019-12-02
            }
        });
    }

    @Override
    public void onSmsReceived(String smsCode) {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mySMSBroadcastReceiver);
        binding.edCode.setText(smsCode);
    }

    @Override
    public void onSmsTimeOut() {
        //
*/    }

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
