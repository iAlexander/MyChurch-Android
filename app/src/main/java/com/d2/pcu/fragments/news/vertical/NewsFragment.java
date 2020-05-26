package com.d2.pcu.fragments.news.vertical;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentNewsBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.fragments.news.NewsViewModel;
import com.d2.pcu.listeners.OnAdditionalFuncNewsListener;

public class NewsFragment extends BaseFragment {

    private FragmentNewsBinding binding;
    private NewsViewModel viewModel;
    private NewsVerticalAdapter adapter;

    private OnAdditionalFuncNewsListener onAdditionalFuncNewsListener;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(NewsViewModel.class);
        setViewModelListeners(viewModel);
        binding.setModel(viewModel);
        viewModel.shouldShowAsUnreadNotification().observe(getViewLifecycleOwner(), count -> {
            binding.ivNotificationBell.setImageResource(count == 0 ? R.drawable.ic_notifications_none : R.drawable.ic_notifications_active);
        });
        viewModel.enableLoading();

        adapter = new NewsVerticalAdapter(
                getContext(),

                (position) -> {
                    viewModel.setSelectedItem(position);

                    if (onAdditionalFuncNewsListener != null) {
                        onAdditionalFuncNewsListener.openHorizontalFragment();
                    }
                });

        binding.list.setAdapter(adapter);

        binding.switeRefreshL.setOnRefreshListener(viewModel::loadNews);

        viewModel.getNewsLiveData().observe(getViewLifecycleOwner(), (news) -> {
            binding.switeRefreshL.setRefreshing(false);
            adapter.setNews(news);
            viewModel.disableLoading();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onAdditionalFuncNewsListener = (OnAdditionalFuncNewsListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onAdditionalFuncNewsListener = null;
    }
}
