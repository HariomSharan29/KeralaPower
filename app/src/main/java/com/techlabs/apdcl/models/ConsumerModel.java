package com.techlabs.apdcl.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConsumerModel {
    @SerializedName("ConsumerClassID")
    @Expose
    private List<String> consumerClassID;

    public List<String> getConsumerClassID() {
        return consumerClassID;
    }

    public void setConsumerClassID(List<String> consumerClassID) {
        this.consumerClassID = consumerClassID;
    }
}
