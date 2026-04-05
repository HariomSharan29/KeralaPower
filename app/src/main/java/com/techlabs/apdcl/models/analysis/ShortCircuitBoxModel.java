package com.techlabs.apdcl.models.analysis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShortCircuitBoxModel {

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

        @SerializedName("EqNo")
        @Expose
        private String eqNo;
        @SerializedName("LGampKmax")
        @Expose
        private String lGampKmax;
        @SerializedName("LGampKmax_color")
        @Expose
        private String lGampKmaxColor;
        @SerializedName("LGampKminZ")
        @Expose
        private String lGampKminZ;
        @SerializedName("LGampKminZ_color")
        @Expose
        private String lGampKminZColor;
        @SerializedName("LLGampKmax")
        @Expose
        private String lLGampKmax;
        @SerializedName("LLGampKmax_color")
        @Expose
        private String lLGampKmaxColor;
        @SerializedName("LLLampKmax")
        @Expose
        private String lLLampKmax;
        @SerializedName("LLLampKmax_color")
        @Expose
        private String lLLampKmaxColor;
        @SerializedName("LLampKmax")
        @Expose
        private String lLampKmax;
        @SerializedName("LLampKmax_color")
        @Expose
        private String lLampKmaxColor;

        public String getEqNo() {
            return eqNo;
        }

        public void setEqNo(String eqNo) {
            this.eqNo = eqNo;
        }

        public String getLGampKmax() {
            return lGampKmax;
        }

        public void setLGampKmax(String lGampKmax) {
            this.lGampKmax = lGampKmax;
        }

        public String getLGampKmaxColor() {
            return lGampKmaxColor;
        }

        public void setLGampKmaxColor(String lGampKmaxColor) {
            this.lGampKmaxColor = lGampKmaxColor;
        }

        public String getLGampKminZ() {
            return lGampKminZ;
        }

        public void setLGampKminZ(String lGampKminZ) {
            this.lGampKminZ = lGampKminZ;
        }

        public String getLGampKminZColor() {
            return lGampKminZColor;
        }

        public void setLGampKminZColor(String lGampKminZColor) {
            this.lGampKminZColor = lGampKminZColor;
        }

        public String getLLGampKmax() {
            return lLGampKmax;
        }

        public void setLLGampKmax(String lLGampKmax) {
            this.lLGampKmax = lLGampKmax;
        }

        public String getLLGampKmaxColor() {
            return lLGampKmaxColor;
        }

        public void setLLGampKmaxColor(String lLGampKmaxColor) {
            this.lLGampKmaxColor = lLGampKmaxColor;
        }

        public String getLLLampKmax() {
            return lLLampKmax;
        }

        public void setLLLampKmax(String lLLampKmax) {
            this.lLLampKmax = lLLampKmax;
        }

        public String getLLLampKmaxColor() {
            return lLLampKmaxColor;
        }

        public void setLLLampKmaxColor(String lLLampKmaxColor) {
            this.lLLampKmaxColor = lLLampKmaxColor;
        }

        public String getLLampKmax() {
            return lLampKmax;
        }

        public void setLLampKmax(String lLampKmax) {
            this.lLampKmax = lLampKmax;
        }

        public String getLLampKmaxColor() {
            return lLampKmaxColor;
        }

        public void setLLampKmaxColor(String lLampKmaxColor) {
            this.lLampKmaxColor = lLampKmaxColor;
        }

    }

}