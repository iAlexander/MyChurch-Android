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

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentLiqWebBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.InfoDialogListener;
import com.d2.pcu.utils.Constants;

import java.util.HashMap;
import java.util.Map;

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
        viewModel.enableLoading();
        binding.setModel(viewModel);
        viewModel.shouldShowAsUnreadNotification().removeObservers(getViewLifecycleOwner());
        viewModel.shouldShowAsUnreadNotification().observe(getViewLifecycleOwner(), count ->
                binding.ivNotificationBell.setImageResource(count == 0 ? R.drawable.ic_notifications_none : R.drawable.ic_notifications_active));

        setupWebView();

        viewModel.getPaymentData().removeObservers(getViewLifecycleOwner());
        viewModel.getPaymentData().observe(getViewLifecycleOwner(), s -> {
            Map<String, String> headers = new HashMap<>();
            headers.put("Accept-Language", "uk");

            binding.webView.loadUrl(s, headers);
        });

        Bundle args = getArguments();
        if (args != null && args.getFloat("amount") > 0) {
            viewModel.getForm(getArguments().getFloat("amount"));
        } else {
            viewModel.getForm();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        infoDialogListener = (InfoDialogListener) context;
    }

    @Override
    public void onDetach() {
        binding.webView.clearHistory();
        binding.webView.loadUrl("");
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

            @Override
            public void onPageFinished(WebView view, String url) {
                viewModel.disableLoading();
                super.onPageFinished(view, url);
            }
        });
    }

    private boolean handleUrlResult(String url) {
        Timber.d("finished: %s", url);

        if (Constants.PAYMENT_COMPLETE.equals(url)) {
            viewModel.onCompletePayment(binding.getRoot());
        }
        return false;
    }


}
