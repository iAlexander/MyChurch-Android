package com.d2.pcu.fragments.news.horizontal;

import android.text.method.LinkMovementMethod;

import androidx.recyclerview.widget.RecyclerView;

import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.databinding.NewsItemHorizontalViewBinding;
import com.d2.pcu.ui.TextToHtmlFormatter;

public class NewsHorizontalViewHolder extends RecyclerView.ViewHolder {

    private NewsItemHorizontalViewBinding binding;

    NewsHorizontalViewHolder(NewsItemHorizontalViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(NewsItem newsItem) {
        binding.setNewsItem(newsItem);

        binding.newsItemTitleTv.setText(TextToHtmlFormatter.getFormattedHtmlText(newsItem.getTitle()));
        binding.newsItemTitleTv.setMovementMethod(LinkMovementMethod.getInstance());

        binding.newsItemMainTv.setText(TextToHtmlFormatter.getFormattedHtmlText(newsItem.getText()));
        binding.newsItemMainTv.setMovementMethod(LinkMovementMethod.getInstance());

        binding.newsItemNoticeMainTv.setText(TextToHtmlFormatter.getFormattedHtmlText(newsItem.getNotice()));
        binding.newsItemNoticeMainTv.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
