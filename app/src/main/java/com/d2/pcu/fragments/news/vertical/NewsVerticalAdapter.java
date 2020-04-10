package com.d2.pcu.fragments.news.vertical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.databinding.NewsItemVerticalViewBinding;

import java.util.ArrayList;
import java.util.List;

public class NewsVerticalAdapter extends RecyclerView.Adapter<NewsVerticalItemViewHolder> {

    private OnNewsItemClickListener onNewsItemClickListener;
    private List<NewsItem> news;

    private Context context;

    NewsVerticalAdapter(Context context, OnNewsItemClickListener onNewsItemClickListener) {
        this.onNewsItemClickListener = onNewsItemClickListener;
        news = new ArrayList<>();

        this.context = context;
        setHasStableIds(true);
    }

    void setNews(List<NewsItem> news) {
        this.news.clear();
        this.news.addAll(news);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsVerticalItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        NewsItemVerticalViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.news_item_vertical_view, parent, false);

        return new NewsVerticalItemViewHolder(context, binding, onNewsItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsVerticalItemViewHolder holder, int position) {
        holder.bind(news.get(position), position);
    }

    @Override
    public int getItemCount() {
        return news == null ? 0 : news.size();
    }

    @Override
    public long getItemId(int position) {
        return news.get(position).getId();
    }
}
