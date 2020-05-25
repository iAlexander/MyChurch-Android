package com.d2.pcu.fragments.cabinet.donate.pay;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.databinding.FragmentLiqWebBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.InfoDialogListener;
import com.d2.pcu.utils.Constants;

import timber.log.Timber;

public class LiqWebFragment extends BaseFragment {

    private FragmentLiqWebBinding binding;
    private LiqWebViewModel viewModel;
    private InfoDialogListener infoDialogListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLiqWebBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LiqWebViewModel.class);
        viewModel.setInfoDialogListener(infoDialogListener);
        setViewModelListeners(viewModel);
        binding.setModel(viewModel);

        setupWebView();

        viewModel.getPaymentData().observe(getViewLifecycleOwner(), s -> {
            binding.webView.loadUrl(s);
        });

        viewModel.getForm();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        infoDialogListener = (InfoDialogListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        infoDialogListener = null;
    }

    private void setupWebView() {
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setSupportMultipleWindows(false);
        binding.webView.getSettings().setAllowContentAccess(true);

        CookieManager.getInstance().setAcceptThirdPartyCookies(binding.webView, true);
        binding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return handleUrlResult(url);

            }
        });
    }

    private boolean handleUrlResult(String url) {
        Timber.d("finished: %s", url);

        if(Constants.PAYMENT_COMPLETE.equals(url)){
            viewModel.onCompletePayment(binding.getRoot());
        }
        return false;
    }
}
