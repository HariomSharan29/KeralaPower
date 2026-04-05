package com.techlabs.apdcl.models.del;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateDeviceModel {

    @SerializedName("results")
    @Expose
    private List<Result> results;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public class Result {

        @SerializedName("DeviceNumber")
        @Expose
        private String deviceNumber;
        @SerializedName("message")
        @Expose
        private String message;

        public String getDeviceNumber() {
            return deviceNumber;
        }

        public void setDeviceNumber(String deviceNumber) {
            this.deviceNumber = deviceNumber;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

}