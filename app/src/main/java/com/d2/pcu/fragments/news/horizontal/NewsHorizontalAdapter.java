package com.d2.pcu.fragments.news.horizontal;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.databinding.NewsItemHorizontalViewBinding;

import java.util.ArrayList;
import java.util.List;

public class NewsHorizontalAdapter extends RecyclerView.Adapter<NewsHorizontalViewHolder> {

    private List<NewsItem> news;

    NewsHorizontalAdapter() {
        news = new ArrayList<>();
    }

    void setNews(List<NewsItem> news) {
        this.news.clear();
        this.news.addAll(news);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        NewsItemHorizontalViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.news_item_horizontal_view, parent, false);

        return new NewsHorizontalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHorizontalViewHolder holder, int position) {
        holder.bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news == null ? 0 : news.size();
    }
}
