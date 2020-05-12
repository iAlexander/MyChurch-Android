package com.d2.pcu.fragments.map;

import android.os.Parcel;
import android.text.TextUtils;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

public class TempleSuggestion implements SearchSuggestion {

    private String templeName;
    private String location;
    private int id;


    public TempleSuggestion(String templeName, String location, int id) {
        this.templeName = templeName;
        this.id = id;
        this.location = location;
    }

    public TempleSuggestion(Parcel source) {
        this.templeName = source.readString();
        this.id = source.readInt();
        this.location = source.readString();
    }

    @Override
    public String getBody() {
        StringBuilder stringBuilder = new StringBuilder();
        if(!TextUtils.isEmpty(templeName)) stringBuilder.append(templeName);
        if(!TextUtils.isEmpty(templeName) && !TextUtils.isEmpty(location)) stringBuilder.append(", ");
        if(!TextUtils.isEmpty(location)) stringBuilder.append(location);
        return stringBuilder.toString();
    }

    public int getId() {
        return id;
    }

    public static final Creator<TempleSuggestion> CREATOR = new Creator<TempleSuggestion>() {
        @Override
        public TempleSuggestion createFromParcel(Parcel source) {
            return new TempleSuggestion(source);
        }

        @Override
        public TempleSuggestion[] newArray(int size) {
            return new TempleSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(templeName);
        dest.writeString(location);
        dest.writeInt(id);
    }
}
