package com.d2.pcu.utils.converters;

import androidx.room.TypeConverter;

import com.d2.pcu.data.model.calendar.Group;
import com.d2.pcu.data.model.calendar.IconImage;
import com.google.gson.Gson;

public class IconImageDataConverter {

    @TypeConverter
    public String fromEventGroup(IconImage iconImage) {
        return new Gson().toJson(iconImage);
    }

    @TypeConverter
    public IconImage toEventGroup(String json) {
        return new Gson().fromJson(json, IconImage.class);
    }

}
