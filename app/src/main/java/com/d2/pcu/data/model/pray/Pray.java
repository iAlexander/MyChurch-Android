package com.d2.pcu.data.model.pray;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.d2.pcu.data.db.MasterDbModel;
import com.d2.pcu.data.model.PcuFile;

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
    private File file;

    public Pray() {
        this.id = -1;
        this.title = "";
        this.text = "";
        this.type = "";
        this.downloaded = false;
        this.file = new File();
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
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

    public class File {
        private String name;
        private String path;

        protected File() {
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
