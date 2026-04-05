package com.techlabs.apdcl.models.contingency;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContingencyNetworkModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    @SerializedName("sxst_filename")
    @Expose
    private String sxstFilename;

    // 🔹 Getter & Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSxstFilename() {
        return sxstFilename;
    }

    public void setSxstFilename(String sxstFilename) {
        this.sxstFilename = sxstFilename;
    }
}

