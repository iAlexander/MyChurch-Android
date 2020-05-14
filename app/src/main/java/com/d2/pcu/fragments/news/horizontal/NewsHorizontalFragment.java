package com.d2.pcu.fragments.news.horizontal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.databinding.FragmentNewsHorizontalBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.fragments.news.NewsViewModel;

public class NewsHorizontalFragment extends BaseFragment {

    private FragmentNewsHorizontalBinding binding;
    private NewsViewModel viewModel;

    private NewsHorizontalAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewsHorizontalBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new NewsHorizontalAdapter();

        binding.newsHorizontalVp.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(NewsViewModel.class);
        setViewModelListeners(viewModel);

        viewModel.getNewsLiveData().observe(getViewLifecycleOwner(), newsItems -> {
            adapter.setNews(newsItems);

            binding.newsHorizontalVp.setCurrentItem(viewModel.getSelectedItem(), false);

//            binding.newsHorizontalVp.postDelayed(
//                    () -> binding.newsHorizontalVp.setCurrentItem(viewModel.getSelectedItem()),
//                    10
//            );
        });

        binding.newsHorizontalVp.setPageTransformer(new ZoomOutTransformer());

        binding.setModel(viewModel);
    }

}
