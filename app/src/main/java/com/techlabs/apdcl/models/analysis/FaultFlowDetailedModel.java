package com.techlabs.apdcl.models.analysis;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FaultFlowDetailedModel {

    @SerializedName("output")
    private Output output;

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public static class Output {

        @SerializedName("columns")
        private List<String> columns;

        @SerializedName("columns1")
        private List<String> columns1;

        @SerializedName("data")
        private List<DataItem> data;

        public List<String> getColumns() {
            return columns;
        }

        public void setColumns(List<String> columns) {
            this.columns = columns;
        }

        public List<String> getColumns1() {
            return columns1;
        }

        public void setColumns1(List<String> columns1) {
            this.columns1 = columns1;
        }

        public List<DataItem> getData() {
            return data;
        }

        public void setData(List<DataItem> data) {
            this.data = data;
        }
    }

    public static class DataItem {

        @SerializedName("Faulted Item")
        private String faultedItem;

        @SerializedName("Fault Type")
        private String faultType;

        @SerializedName("Fault Phase")
        private String faultPhase;

        @SerializedName("Feeder Id")
        private String feederId;

        @SerializedName("Section Id")
        private String sectionId;

        @SerializedName("Equipment Id")
        private String equipmentId;

        @SerializedName("Code")
        private String code;

        @SerializedName("Loading A(%)")
        private double loadingAPercent;

        @SerializedName("Thru Power A(kW)")
        private double thruPowerAKw;

        @SerializedName("Thru Power A(kvar)")
        private double thruPowerAkvar;

        @SerializedName("VA(%)")
        private double vaPercent;

        @SerializedName("Equipment No")
        private String equipmentNo;

        public String getFaultedItem() {
            return faultedItem;
        }

        public void setFaultedItem(String faultedItem) {
            this.faultedItem = faultedItem;
        }

        public String getFaultType() {
            return faultType;
        }

        public void setFaultType(String faultType) {
            this.faultType = faultType;
        }

        public String getFaultPhase() {
            return faultPhase;
        }

        public void setFaultPhase(String faultPhase) {
            this.faultPhase = faultPhase;
        }

        public String getFeederId() {
            return feederId;
        }

        public void setFeederId(String feederId) {
            this.feederId = feederId;
        }

        public String getSectionId() {
            return sectionId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(String equipmentId) {
            this.equipmentId = equipmentId;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public double getLoadingAPercent() {
            return loadingAPercent;
        }

        public void setLoadingAPercent(double loadingAPercent) {
            this.loadingAPercent = loadingAPercent;
        }

        public double getThruPowerAKw() {
            return thruPowerAKw;
        }

        public void setThruPowerAKw(double thruPowerAKw) {
            this.thruPowerAKw = thruPowerAKw;
        }

        public double getThruPowerAkvar() {
            return thruPowerAkvar;
        }

        public void setThruPowerAkvar(double thruPowerAkvar) {
            this.thruPowerAkvar = thruPowerAkvar;
        }

        public double getVaPercent() {
            return vaPercent;
        }

        public void setVaPercent(double vaPercent) {
            this.vaPercent = vaPercent;
        }

        public String getEquipmentNo() {
            return equipmentNo;
        }

        public void setEquipmentNo(String equipmentNo) {
            this.equipmentNo = equipmentNo;
        }
    }
}
