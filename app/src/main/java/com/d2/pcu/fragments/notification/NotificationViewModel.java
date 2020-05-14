package com.d2.pcu.fragments.notification;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.data.model.profile.NotificationHistoryItem;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.listeners.OnBackButtonClickListener;

import java.util.Collections;
import java.util.List;

public class NotificationViewModel extends BaseViewModel {

    private final Repository repository;
    private final LiveData<List<NotificationHistoryItem>> notificationLiveData;
    private OnBackButtonClickListener onBackButtonClickListener;

    public NotificationViewModel() {
        repository = App.getInstance().getRepositoryInstance();

        notificationLiveData = Transformations.switchMap(
                repository.getTransport().getNotificationChannel(), input -> {

                    Collections.sort(
                            input,
                            (o1, o2) -> Long.compare(o2.getCreatedAt().getTime(), o1.getCreatedAt().getTime())
                    );

                    return new MutableLiveData<>(input);
                }
        );
    }

    public void setSelectedItem(int selectedItem) {
//        this.selectedItem = selectedItem;
        if (notificationLiveData.getValue() != null) {
            NotificationHistoryItem item = notificationLiveData.getValue().get(selectedItem);
            item.setRead(true);
            repository.updateNotificationItemAsRead(item);
        }
    }

    public void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    public LiveData<List<NotificationHistoryItem>> getNotificationLiveData() {
        return notificationLiveData;
    }

    public void getData(){
        repository.getNotificationHistory();
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }
}
