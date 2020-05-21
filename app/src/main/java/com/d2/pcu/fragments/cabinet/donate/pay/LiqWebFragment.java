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

import timber.log.Timber;

public class LiqWebFragment extends BaseFragment {

    private FragmentLiqWebBinding binding;
    private LiqWebViewModel viewModel;


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
        setViewModelListeners(viewModel);
        binding.setModel(viewModel);

        setupWebView();

        viewModel.getPaymentData().observe(getViewLifecycleOwner(), s -> {
            Timber.d("response: %s", s);
            binding.webView.loadUrl(s);
//            binding.webView.loadData(s, "text/html; charset=UTF-8", null);
        });

        viewModel.getForm();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        this.listener = (OnDonatesClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        this.listener = null;
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

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Timber.d("finished: %s", url);
//                hideProgress();
//                if (url.contains("success")) {
//                    presenter.logComplete(url.endsWith("/success"));
//                }
            }
        });
    }

    private boolean handleUrlResult(String url) {
        Timber.d("finished: %s", url);

//        if (!TextUtils.isEmpty(url)) {
//            if (url.toLowerCase().startsWith(AuthService.HandLink.INVOICE)) {
//                Uri uri = Uri.parse(url);
//                Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(it);
//                return true;
//            }
//
//            switch (url) {
//                case AuthService.HandLink.PAYMENT_FAILED:
//                    presenter.subscribe();
//                    return true;
//                case AuthService.HandLink.PAYMENT_SUCCESS:
//                    presenter.goVacancy();
//                    return true;
//                case AuthService.HandLink.PAYMENT_SERVICES:
//                case AuthService.HandLink.PAYMENT_SERVICES_MOBILE:
//                case AuthService.HandLink.PAYMENT_SERVICES_BUSINESS:
//                    presenter.onSuccess();
//                    return true;
//            }
//        }
        return false;
    }
}
