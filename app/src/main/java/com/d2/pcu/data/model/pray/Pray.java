package com.d2.pcu.data.model.pray;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;

import androidx.annotation.DrawableRes;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.d2.pcu.BuildConfig;
import com.d2.pcu.R;
import com.d2.pcu.data.db.MasterDbModel;

@Entity
public class Pray extends MasterDbModel {

    @PrimaryKey
    private int id;

    private String title;

    private String text;

    private String type;

    private String internalPath;

    private boolean downloaded;

    @Ignore
    private PlayFile file;

    public Pray() {
        this.id = -1;
        this.title = "";
        this.text = "";
        this.type = "";
        this.downloaded = false;
        this.file = new PlayFile();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    public String getInternalPath() {
        return internalPath;
    }

    public void setInternalPath(String internalPath) {
        this.internalPath = internalPath;
    }

    public PlayFile getFile() {
        return file;
    }

    public void setFile(PlayFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Pray{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", internalPath='" + internalPath + '\'' +
                ", downloaded=" + downloaded +
                '}';
    }

    public class PlayFile {
        private String name;
        private String path;

        protected PlayFile() {
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

    public static MediaDescriptionCompat getMediaDescription(Context context, Pray pray) {
        Bundle extras = new Bundle();
        Bitmap bitmap = getBitmap(context, R.drawable.ic_news_active);
        extras.putParcelable(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, bitmap);
        extras.putParcelable(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, bitmap);
        return new MediaDescriptionCompat.Builder()
                .setMediaId(pray.file.name)
                .setIconBitmap(bitmap)
                .setTitle(pray.title)
//                .setDescription(pray.text)
                .setExtras(extras)
                .build();
    }

    public static Bitmap getBitmap(Context context, @DrawableRes int bitmapResource) {
        return ((BitmapDrawable) context.getResources().getDrawable(bitmapResource)).getBitmap();
    }

    public Uri getUrl() {
//        return Uri.parse("https://storage.googleapis.com/automotive-media/Jazz_In_Paris.mp3");
        if(id==2){
            return Uri.parse("https://storage.googleapis.com/automotive-media/Jazz_In_Paris.mp3");
        }
        return new Uri.Builder().scheme("http")
                .authority(Uri.parse(BuildConfig.API_BASE_URL).getAuthority())
                .path(file.path).appendPath(file.name)
                .build();
    }
}
