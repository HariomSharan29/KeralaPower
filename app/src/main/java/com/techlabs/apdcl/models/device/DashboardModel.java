package com.techlabs.apdcl.models.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardModel {

    @SerializedName("output")
    @Expose
    private Output output;

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public class Output {

        @SerializedName("DeviceNumber")
        @Expose
        private String deviceNumber;
        @SerializedName("NetworkId")
        @Expose
        private String networkId;
        @SerializedName("NodeId")
        @Expose
        private String nodeId;
        @SerializedName("EquipmentId")
        @Expose
        private String equipmentId;
        @SerializedName("NetworkType")
        @Expose
        private String networkType;
        @SerializedName("Group1")
        @Expose
        private String group1;
        @SerializedName("Group2")
        @Expose
        private String group2;
        @SerializedName("Group3")
        @Expose
        private String group3;
        @SerializedName("Group4")
        @Expose
        private String group4;
        @SerializedName("Group5")
        @Expose
        private String group5;
        @SerializedName("Group6")
        @Expose
        private String group6;
        @SerializedName("AmbientTemperature")
        @Expose
        private String ambientTemperature;
        @SerializedName("NominalCapacityMVA")
        @Expose
        private String nominalCapacityMVA;
        @SerializedName("NominalKVLL")
        @Expose
        private String nominalKVLL;

        public String getDeviceNumber() {
            return deviceNumber;
        }

        public void setDeviceNumber(String deviceNumber) {
            this.deviceNumber = deviceNumber;
        }

        public String getNetworkId() {
            return networkId;
        }

        public void setNetworkId(String networkId) {
            this.networkId = networkId;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(String equipmentId) {
            this.equipmentId = equipmentId;
        }

        public String getNetworkType() {
            return networkType;
        }

        public void setNetworkType(String networkType) {
            this.networkType = networkType;
        }

        public String getGroup1() {
            return group1;
        }

        public void setGroup1(String group1) {
            this.group1 = group1;
        }

        public String getGroup2() {
            return group2;
        }

        public void setGroup2(String group2) {
            this.group2 = group2;
        }

        public String getGroup3() {
            return group3;
        }

        public void setGroup3(String group3) {
            this.group3 = group3;
        }

        public Object getGroup4() {
            return group4;
        }

        public void setGroup4(String group4) {
            this.group4 = group4;
        }

        public Object getGroup5() {
            return group5;
        }

        public void setGroup5(String group5) {
            this.group5 = group5;
        }

        public Object getGroup6() {
            return group6;
        }

        public void setGroup6(String group6) {
            this.group6 = group6;
        }

        public String getAmbientTemperature() {
            return ambientTemperature;
        }

        public void setAmbientTemperature(String ambientTemperature) {
            this.ambientTemperature = ambientTemperature;
        }

        public Object getNominalCapacityMVA() {
            return nominalCapacityMVA;
        }

        public void setNominalCapacityMVA(String nominalCapacityMVA) {
            this.nominalCapacityMVA = nominalCapacityMVA;
        }

        public String getNominalKVLL() {
            return nominalKVLL;
        }

        public void setNominalKVLL(String nominalKVLL) {
            this.nominalKVLL = nominalKVLL;
        }
    }
}