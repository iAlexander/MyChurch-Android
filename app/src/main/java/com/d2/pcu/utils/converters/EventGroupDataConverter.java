package com.d2.pcu.utils.converters;

import androidx.room.TypeConverter;

import com.d2.pcu.data.model.calendar.Group;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class EventGroupDataConverter {

    @TypeConverter
    public String fromEventGroup(Group group) {
        return new Gson().toJson(group);
    }

    @TypeConverter
    public Group toEventGroup(String json) {
        return new Gson().fromJson(json, Group.class);
    }

}
