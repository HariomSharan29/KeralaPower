
package com.techlabs.apdcl.models.nsc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LayerDataUpdateModel {

    @SerializedName("CYMSECTION")
    @Expose
    private String cymsection;
    @SerializedName("cymeLine")
    @Expose
    private String cymeLine;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getCymsection() {
        return cymsection;
    }

    public void setCymsection(String cymsection) {
        this.cymsection = cymsection;
    }

    public String getCymeLine() {
        return cymeLine;
    }

    public void setCymeLine(String cymeLine) {
        this.cymeLine = cymeLine;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}