package com.techlabs.apdcl.models.zoom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZoomToLayer {

    @SerializedName("output")
    @Expose
    private List<Output> output;

    public List<Output> getOutput() {
        return output;
    }

    public void setOutput(List<Output> output) {
        this.output = output;
    }

    public class Output {

        @SerializedName(value = "X", alternate = {"x"})
        @Expose
        private Double x;
        @SerializedName(value = "Y", alternate = {"y"})
        @Expose
        private Double y;
        @SerializedName(value = "DeviceNumber", alternate = {"devicenumber"})
        @Expose
        private String deviceNumber;
        @SerializedName(value = "DeviceType", alternate = {"devicetype"})
        @Expose
        private Integer deviceType;

        public Double getX() {
            return x;
        }

        public void setX(Double x) {
            this.x = x;
        }

        public Double getY() {
            return y;
        }

        public void setY(Double y) {
            this.y = y;
        }

        public String getDeviceNumber() {
            return deviceNumber;
        }

        public void setDeviceNumber(String deviceNumber) {
            this.deviceNumber = deviceNumber;
        }

        public Integer getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Integer deviceType) {
            this.deviceType = deviceType;
        }

    }

}



