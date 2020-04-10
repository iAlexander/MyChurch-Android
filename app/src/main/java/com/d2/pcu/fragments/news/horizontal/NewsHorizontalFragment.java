package com.d2.pcu.fragments.news.horizontal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentNewsHorizontalBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.fragments.news.NewsViewModel;
import com.d2.pcu.listeners.OnBackButtonClickListener;

public class NewsHorizontalFragment extends BaseFragment {

    private FragmentNewsHorizontalBinding binding;
    private NewsViewModel viewModel;

    private NewsHorizontalAdapter adapter;

    private OnBackButtonClickListener onBackButtonClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_horizontal, container, false);

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
        viewModel = ViewModelProviders.of(getActivity()).get(NewsViewModel.class);
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBackButtonClickListener = (OnBackButtonClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackButtonClickListener = null;
    }
}
