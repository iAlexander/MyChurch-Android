package com.d2.pcu.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUpdater<T> {

    public List<T> updateList(List<T> oldItems, List<T> newItems) {

        if (oldItems != null && !oldItems.isEmpty()) {

            List<T> diffItems = new ArrayList<>(newItems);
            diffItems.removeAll(oldItems);

            if (!diffItems.isEmpty()) {
//                int end = oldItems.size() - diffItems.size();
//                oldItems = oldItems.subList(0, end);
                oldItems.addAll(diffItems);
            }
            return oldItems;

        } else {
            return newItems;
        }
    }

}
