package com.d2.pcu.data.model;

import com.google.gson.annotations.SerializedName;

public class PcuFileWrap {
    @SerializedName("file")
    private PcuFile file = new PcuFile();

    public PcuFile getFile() {
        return file;
    }

    public void setFile(PcuFile file) {
        this.file = file;
    }
}
