//package com.d2.pcu.fragments.cabinet.donate.pay;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.CookieManager;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.databinding.DataBindingUtil;
//
//import org.jetbrains.annotations.NotNull;
//
//import ua.rabota.employer.BuildConfig;
//import ua.rabota.employer.R;
//import ua.rabota.employer.RuaApp;
//import ua.rabota.employer.api.AuthService;
//import ua.rabota.employer.databinding.FragmentWebPayBinding;
//import ua.rabota.employer.ui.Fragments;
//import ua.rabota.employer.ui.base.BaseFragment;
//
//public class WebPayFragment extends BaseFragment implements WebPayContract.View {
//    private FragmentWebPayBinding layout;
//    private WebPayPresenter presenter;
//
//    public static WebPayFragment newInstance() {
//        return new WebPayFragment();
//    }
//
//    @Override
//    public void onAttach(@NotNull Context context) {
//        super.onAttach(context);
//        presenter = new WebPayPresenter();
//        RuaApp.get(context).getAppComponent().inject(presenter);
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        layout = DataBindingUtil.inflate(inflater, R.layout.fragment_web_pay, container, false);
//        return attachToBaseView(inflater, container, layout.getRoot());
//    }
//
//    @Override
//    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        presenter.attach(this);
//
//        initViews();
//        initClickListeners();
//        presenter.subscribe();
//    }
//
//    @Override
//    public void onPause() {
//        layout.webView.stopLoading();
//        presenter.unsubscribe();
//        super.onPause();
//    }
//
//    @Override
//    public void onDestroyView() {
//        layout.webView.setWebViewClient(null);
//        super.onDestroyView();
//    }
//
//    private void initViews() {
//        setupWebView();
//    }
//
//    private void initClickListeners() {
//        layout.ivBack.setOnClickListener(v -> getParentActivity().onBackClicked());
//        if (BuildConfig.DEBUG) {
//            layout.tvTitle.setOnClickListener(v -> presenter.openSuccessDebug());
//        }
//    }
//
//    @Override
//    public void showError(String message) {
//        hideProgress();
//        if (!TextUtils.isEmpty(message)) showToast(message);
//    }
//
//    @Override
//    public void showError(int text) {
//        showError(getString(text));
//    }
//
//    @Override
//    public void openLink(String url) {
//        layout.webView.loadUrl(url);
//    }
//
//    @Override
//    public void onComplete() {
//        getParentActivity().onBackPressed();
//    }
//
//    @Override
//    public void onGoVacancy() {
//        getParentActivity().clearFragmentsBackStackNavigate(Fragments.VACANCIES);
//    }
//
//    private void setupWebView() {
//        layout.webView.getSettings().setJavaScriptEnabled(true);
//        layout.webView.getSettings().setDomStorageEnabled(true);
//        layout.webView.getSettings().setSupportMultipleWindows(false);
//        layout.webView.getSettings().setAllowContentAccess(true);
//
//        CookieManager.getInstance().setAcceptThirdPartyCookies(layout.webView, true);
//        layout.webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return handleUrlResult(url);
//
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                hideProgress();
//                if (url.contains("success")) {
//                    presenter.logComplete(url.endsWith("/success"));
//                }
//            }
//        });
//    }
//
//    private boolean handleUrlResult(String url) {
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
//        return false;
//    }
//}