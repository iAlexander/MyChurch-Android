package com.d2.pcu.data.model;

import android.net.Uri;
import android.text.TextUtils;

import com.d2.pcu.BuildConfig;
import com.google.gson.annotations.SerializedName;

public class PcuFile {
    @SerializedName("id")
    private int id;
    @SerializedName("file")
    private ContentFile file;
    @SerializedName("title")
    private String title;

    private String originalName;

    private int size;

    private String type;

    private String extension;

    public PcuFile() {
        id = -1;
        file = new ContentFile();
        title = "";
        originalName = "";
        size = -1;
        type = "";
        extension = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContentFile getFile() {
        return file;
    }

    public void setFile(ContentFile file) {
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Uri getUrl() {
        if (TextUtils.isEmpty(file.name)) {
            return Uri.parse(file.name);
        }
        return new Uri.Builder().scheme("http")
                .authority(Uri.parse(BuildConfig.API_BASE_URL).getAuthority())
                .path(file.path).appendPath(file.name)
                .build();
    }

    public String getStringUrl() {
        if (TextUtils.isEmpty(file.name)) {
            return file.name;
        }
        return new Uri.Builder().scheme("http")
                .authority(Uri.parse(BuildConfig.API_BASE_URL).getAuthority())
                .path(file.path).appendPath(file.name)
                .build().toString();
    }


    protected class ContentFile {
        protected String name;
        protected String path;

        ContentFile() {
            name = "";
            path = "";
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
