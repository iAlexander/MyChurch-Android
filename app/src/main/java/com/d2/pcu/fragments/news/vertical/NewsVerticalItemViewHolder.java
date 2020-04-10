package com.d2.pcu.fragments.news.vertical;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.R;
import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.databinding.NewsItemVerticalViewBinding;

public class NewsVerticalItemViewHolder extends RecyclerView.ViewHolder {

    private NewsItemVerticalViewBinding binding;
    private NewsItem newsItem;
    private OnNewsItemClickListener onNewsItemClickListener;

    private int position;

    private Context context;

    NewsVerticalItemViewHolder(Context context, NewsItemVerticalViewBinding binding,
                               OnNewsItemClickListener onNewsItemClickListener) {
        super(binding.getRoot());
        this.binding = binding;
        this.binding.setHolder(this);
        this.onNewsItemClickListener = onNewsItemClickListener;

        this.context = context;
    }


    public void bind(NewsItem newsItem, int position) {
        this.newsItem = newsItem;
        binding.setNewsItem(this.newsItem);
        this.position = position;

        if (newsItem.isRead()) {
            binding.newsItemTitleTv.setTextAppearance(context, R.style.TitleAppTextBlack);
        } else {
            binding.newsItemTitleTv.setTextAppearance(context, R.style.TitleAppTextBlack_Bold);
        }
    }

    public void onNewsClick(View view) {
        if (onNewsItemClickListener != null) {
            onNewsItemClickListener.onNewsItemClick(position);
        }
    }
}
