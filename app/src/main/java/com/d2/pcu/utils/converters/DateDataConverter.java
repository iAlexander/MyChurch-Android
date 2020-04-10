package com.d2.pcu.utils.converters;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateDataConverter {

    @TypeConverter
    public long fromDate(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public Date toDate(long time) {
        return new Date(time);
    }

    @TypeConverter
    public Date toDate(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());

        return dateFormat.parse(date);
    }

}
