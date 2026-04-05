package com.techlabs.apdcl.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerModel {

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

        @SerializedName("DeviceNumber")
        @Expose
        private String deviceNumber;
        @SerializedName("DeviceType")
        @Expose
        private Integer deviceType;
        @SerializedName("NetworkId")
        @Expose
        private String networkId;
        @SerializedName("CustomerNumber")
        @Expose
        private String customerNumber;
        @SerializedName("ConsumerClassId")
        @Expose
        private String consumerClassId;
        @SerializedName("NumberOfCustomer")
        @Expose
        private Double numberOfCustomer;
        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("LockDuringLoadAllocation")
        @Expose
        private Integer lockDuringLoadAllocation;
        @SerializedName("LoadYear")
        @Expose
        private Integer loadYear;
        @SerializedName("LoadModelId")
        @Expose
        private Integer loadModelId;
        @SerializedName("NormalPriority")
        @Expose
        private Integer normalPriority;
        @SerializedName("EmergencyPriority")
        @Expose
        private Integer emergencyPriority;
        @SerializedName("LoadValueType")
        @Expose
        private Integer loadValueType;
        @SerializedName("Phase")
        @Expose
        private Integer phase;
        @SerializedName("CenterTapPercent")
        @Expose
        private Double centerTapPercent;
        @SerializedName("CenterTapPercent2")
        @Expose
        private Object centerTapPercent2;
        @SerializedName("LoadValue1")
        @Expose
        private Double loadValue1;
        @SerializedName("LoadValue2")
        @Expose
        private Double loadValue2;
        @SerializedName("connectedKVA")
        @Expose
        private Float connectedKVA;
        @SerializedName("KWHUsage")
        @Expose
        private Double kWHUsage;
        @SerializedName("LoadValue1N1")
        @Expose
        private Object loadValue1N1;
        @SerializedName("LoadValue1N2")
        @Expose
        private Object loadValue1N2;
        @SerializedName("LoadValue2N1")
        @Expose
        private Object loadValue2N1;
        @SerializedName("LoadValue2N2")
        @Expose
        private Object loadValue2N2;

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

        public String getNetworkId() {
            return networkId;
        }

        public void setNetworkId(String networkId) {
            this.networkId = networkId;
        }

        public String getCustomerNumber() {
            return customerNumber;
        }

        public void setCustomerNumber(String customerNumber) {
            this.customerNumber = customerNumber;
        }

        public String getConsumerClassId() {
            return consumerClassId;
        }

        public void setConsumerClassId(String consumerClassId) {
            this.consumerClassId = consumerClassId;
        }

        public Double getNumberOfCustomer() {
            return numberOfCustomer;
        }

        public void setNumberOfCustomer(Double numberOfCustomer) {
            this.numberOfCustomer = numberOfCustomer;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getLockDuringLoadAllocation() {
            return lockDuringLoadAllocation;
        }

        public void setLockDuringLoadAllocation(Integer lockDuringLoadAllocation) {
            this.lockDuringLoadAllocation = lockDuringLoadAllocation;
        }

        public Integer getLoadYear() {
            return loadYear;
        }

        public void setLoadYear(Integer loadYear) {
            this.loadYear = loadYear;
        }

        public Integer getLoadModelId() {
            return loadModelId;
        }

        public void setLoadModelId(Integer loadModelId) {
            this.loadModelId = loadModelId;
        }

        public Integer getNormalPriority() {
            return normalPriority;
        }

        public void setNormalPriority(Integer normalPriority) {
            this.normalPriority = normalPriority;
        }

        public Integer getEmergencyPriority() {
            return emergencyPriority;
        }

        public void setEmergencyPriority(Integer emergencyPriority) {
            this.emergencyPriority = emergencyPriority;
        }

        public Integer getLoadValueType() {
            return loadValueType;
        }

        public void setLoadValueType(Integer loadValueType) {
            this.loadValueType = loadValueType;
        }

        public Integer getPhase() {
            return phase;
        }

        public void setPhase(Integer phase) {
            this.phase = phase;
        }

        public Double getCenterTapPercent() {
            return centerTapPercent;
        }

        public void setCenterTapPercent(Double centerTapPercent) {
            this.centerTapPercent = centerTapPercent;
        }

        public Object getCenterTapPercent2() {
            return centerTapPercent2;
        }

        public void setCenterTapPercent2(Object centerTapPercent2) {
            this.centerTapPercent2 = centerTapPercent2;
        }

        public Double getLoadValue1() {
            return loadValue1;
        }

        public void setLoadValue1(Double loadValue1) {
            this.loadValue1 = loadValue1;
        }

        public Double getLoadValue2() {
            return loadValue2;
        }

        public void setLoadValue2(Double loadValue2) {
            this.loadValue2 = loadValue2;
        }

        public Float getConnectedKVA() {
            return connectedKVA;
        }

        public void setConnectedKVA(Float connectedKVA) {
            this.connectedKVA = connectedKVA;
        }

        public Double getKWHUsage() {
            return kWHUsage;
        }

        public void setKWHUsage(Double kWHUsage) {
            this.kWHUsage = kWHUsage;
        }

        public Object getLoadValue1N1() {
            return loadValue1N1;
        }

        public void setLoadValue1N1(Object loadValue1N1) {
            this.loadValue1N1 = loadValue1N1;
        }

        public Object getLoadValue1N2() {
            return loadValue1N2;
        }

        public void setLoadValue1N2(Object loadValue1N2) {
            this.loadValue1N2 = loadValue1N2;
        }

        public Object getLoadValue2N1() {
            return loadValue2N1;
        }

        public void setLoadValue2N1(Object loadValue2N1) {
            this.loadValue2N1 = loadValue2N1;
        }

        public Object getLoadValue2N2() {
            return loadValue2N2;
        }

        public void setLoadValue2N2(Object loadValue2N2) {
            this.loadValue2N2 = loadValue2N2;
        }

    }

}

