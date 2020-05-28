package com.d2.pcu.fragments.notification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.profile.NotificationHistoryItem;
import com.d2.pcu.fragments.BaseViewModel;

import java.util.Collections;
import java.util.List;

public class NotificationViewModel extends BaseViewModel {

    private final Repository repository;
    private final LiveData<List<NotificationHistoryItem>> notificationLiveData;
    private final MutableLiveData<NotificationHistoryItem> itemLiveData;
    private int selectedItemId;

    public NotificationViewModel() {
        repository = App.getInstance().getRepositoryInstance();

        notificationLiveData = Transformations.switchMap(
                repository.getNotificationLiveData(), input -> {

                    Collections.sort(
                            input,
                            (o1, o2) -> Long.compare(o2.getCreatedAt().getTime(), o1.getCreatedAt().getTime())
                    );

                    return new MutableLiveData<>(input);
                }
        );

        itemLiveData = repository.getTransport().getNotificationCardChannel();

        getData();

    }

    public void setSelectedId(int selectedId){
        this.selectedItemId = selectedId;
        getItemData();
    }

    public void setSelectedItemId(int selectedItem) {
//        this.selectedItem = selectedItem;
        if (notificationLiveData.getValue() != null) {
            NotificationHistoryItem item = notificationLiveData.getValue().get(selectedItem);
            this.selectedItemId = item.getId();
            itemLiveData.postValue(item);
            repository.updateNotificationItemAsRead(item);
            getItemData();
        }
    }

    public LiveData<List<NotificationHistoryItem>> getNotificationLiveData() {
        return notificationLiveData;
    }

    public LiveData<NotificationHistoryItem> getNotificationItemLiveData() {
       return itemLiveData;
    }

    public void getData() {
        repository.getNotificationHistory();
    }

    public void getItemData(){
        repository.getNotificationHistory(selectedItemId);
    }

}
