package com.d2.pcu.fragments.map;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

public class TempleSuggestion implements SearchSuggestion {

    private String templeName;
    private int id;


    public TempleSuggestion(String templeName, int id) {
        this.templeName = templeName;
        this.id = id;
    }

    public TempleSuggestion(Parcel source) {
        this.templeName = source.readString();
        this.id = source.readInt();
    }

    @Override
    public String getBody() {
        return templeName;
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
        dest.writeInt(id);
    }
}
