package com.techlabs.apdcl.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SelectedFeedersModel {

@SerializedName("result")
@Expose
private Result result;
    @SerializedName("status")
    @Expose
    private Integer status;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public class Result {

        @SerializedName("Group6")
        @Expose
        private List<Group6> group6;
        @SerializedName("Group5")
        @Expose
        private List<Group5> group5;
        @SerializedName("Group4")
        @Expose
        private List<Group4> group4;
        @SerializedName("Group3")
        @Expose
        private List<Group3> group3;
        @SerializedName("Group2")
        @Expose
        private List<Group2> group2;
        @SerializedName("Group1")
        @Expose
        private List<Group1> group1;
        @SerializedName("NetworkId")
        @Expose
        private List<NetworkId> networkId;
        @SerializedName("MeterDeviceNumber")
        @Expose
        private List<MeterDeviceNumber> meterDeviceNumber;

        public List<Group6> getGroup6() {
            return group6;
        }

        public void setGroup6(List<Group6> group6) {
            this.group6 = group6;
        }

        public List<Group5> getGroup5() {
            return group5;
        }

        public void setGroup5(List<Group5> group5) {
            this.group5 = group5;
        }

        public List<Group4> getGroup4() {
            return group4;
        }


        public void setGroup4(List<Group4> group4) {
            this.group4 = group4;
        }

        public List<Group3> getGroup3() {
            return group3;
        }

        public void setGroup3(List<Group3> group3) {
            this.group3 = group3;
        }

        public List<Group2> getGroup2() {
            return group2;
        }

        public void setGroup2(List<Group2> group2) {
            this.group2 = group2;
        }

        public List<Group1> getGroup1() {
            return group1;
        }

        public void setGroup1(List<Group1> group1) {
            this.group1 = group1;
        }

        public List<NetworkId> getNetworkId() {
            return networkId;
        }

        public void setNetworkId(List<NetworkId> networkId) {
            this.networkId = networkId;
        }

        public List<MeterDeviceNumber> getMeterDeviceNumber() {
            return meterDeviceNumber;
        }

        public void setMeterDeviceNumber(List<MeterDeviceNumber> meterDeviceNumber) {
            this.meterDeviceNumber = meterDeviceNumber;
        }

    }

    public class Group1 {

        @SerializedName("Group6")
        @Expose
        private Object group6;
        @SerializedName("Group5")
        @Expose
        private Object group5;
        @SerializedName("Group4")
        @Expose
        private Object group4;
        @SerializedName("Group3")
        @Expose
        private String group3;
        @SerializedName("Group2")
        @Expose
        private String group2;
        @SerializedName("Group1")
        @Expose
        private String group1;

        public Object getGroup6() {
            return group6;
        }

        public void setGroup6(Object group6) {
            this.group6 = group6;
        }

        public Object getGroup5() {
            return group5;
        }

        public void setGroup5(Object group5) {
            this.group5 = group5;
        }

        public Object getGroup4(Object group4) {
            return group4;
        }

        public void setGroup4(Object group4) {
            this.group4 = group4;
        }

        public String getGroup3() {
            return group3;
        }

        public void setGroup3(String group3) {
            this.group3 = group3;
        }

        public String getGroup2() {
            return group2;
        }

        public void setGroup2(String group2) {
            this.group2 = group2;
        }

        public String getGroup1() {
            return group1;
        }

        public void setGroup1(String group1) {
            this.group1 = group1;
        }

    }
    public class Group2 {

        @SerializedName("Group6")
        @Expose
        private Object group6;
        @SerializedName("Group5")
        @Expose
        private Object group5;
        @SerializedName("Group4")
        @Expose
        private Object group4;
        @SerializedName("Group3")
        @Expose
        private String group3;
        @SerializedName("Group2")
        @Expose
        private String group2;

        public Object getGroup6() {
            return group6;
        }

        public void setGroup6(Object group6) {
            this.group6 = group6;
        }

        public Object getGroup5() {
            return group5;
        }

        public void setGroup5(Object group5) {
            this.group5 = group5;
        }

        public Object getGroup4(Object group4) {
            return group4;
        }

        public void setGroup4(Object group4) {
            this.group4 = group4;
        }

        public String getGroup3() {
            return group3;
        }

        public void setGroup3(String group3) {
            this.group3 = group3;
        }

        public String getGroup2() {
            return group2;
        }

        public void setGroup2(String group2) {
            this.group2 = group2;
        }

    }
    public class Group3 {

        @SerializedName("Group6")
        @Expose
        private Object group6;
        @SerializedName("Group5")
        @Expose
        private Object group5;
        @SerializedName("Group4")
        @Expose
        private Object group4;
        @SerializedName("Group3")
        @Expose
        private String group3;

        public Object getGroup6() {
            return group6;
        }

        public void setGroup6(Object group6) {
            this.group6 = group6;
        }

        public Object getGroup5() {
            return group5;
        }

        public void setGroup5(Object group5) {
            this.group5 = group5;
        }

        public Object getGroup4(Object group4) {
            return group4;
        }

        public void setGroup4(Object group4) {
            this.group4 = group4;
        }

        public String getGroup3() {
            return group3;
        }

        public void setGroup3(String group3) {
            this.group3 = group3;
        }

    }
    public class Group4 {

        @SerializedName("Group6")
        @Expose
        private Object group6;
        @SerializedName("Group5")
        @Expose
        private Object group5;
        @SerializedName("Group4")
        @Expose
        private Object group4;

        public Object getGroup6() {
            return group6;
        }

        public void setGroup6(Object group6) {
            this.group6 = group6;
        }

        public Object getGroup5() {
            return group5;
        }

        public void setGroup5(Object group5) {
            this.group5 = group5;
        }

        public Object getGroup4() {
            return group4;
        }

        public void setGroup4(Object group4) {
            this.group4 = group4;
        }

    }

    public class Group5 {

        @SerializedName("Group6")
        @Expose
        private Object group6;
        @SerializedName("Group5")
        @Expose
        private Object group5;

        public Object getGroup6() {
            return group6;
        }

        public void setGroup6(Object group6) {
            this.group6 = group6;
        }

        public Object getGroup5() {
            return group5;
        }

        public void setGroup5(Object group5) {
            this.group5 = group5;
        }

    }

    public class Group6 {

        @SerializedName("Group6")
        @Expose
        private Object group6;

        public Object getGroup6() {
            return group6;
        }

        public void setGroup6(Object group6) {
            this.group6 = group6;
        }

    }

    public class NetworkId {

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
        private Object group4;
        @SerializedName("Group5")
        @Expose
        private Object group5;
        @SerializedName("Group6")
        @Expose
        private Object group6;
        @SerializedName("NetworkId")
        @Expose
        private String networkId;

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

        public void setGroup4(Object group4) {
            this.group4 = group4;
        }

        public Object getGroup5() {
            return group5;
        }

        public void setGroup5(Object group5) {
            this.group5 = group5;
        }

        public Object getGroup6() {
            return group6;
        }

        public void setGroup6(Object group6) {
            this.group6 = group6;
        }

        public String getNetworkId() {
            return networkId;
        }

        public void setNetworkId(String networkId) {
            this.networkId = networkId;
        }

    }

    public class MeterDeviceNumber {

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
        private Object group4;
        @SerializedName("Group5")
        @Expose
        private Object group5;
        @SerializedName("Group6")
        @Expose
        private Object group6;
        @SerializedName("MeterDeviceNumber")
        @Expose
        private Object meterDeviceNumber;

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

        public void setGroup4(Object group4) {
            this.group4 = group4;
        }

        public Object getGroup5() {
            return group5;
        }

        public void setGroup5(Object group5) {
            this.group5 = group5;
        }

        public Object getGroup6() {
            return group6;
        }

        public void setGroup6(Object group6) {
            this.group6 = group6;
        }

        public Object getMeterDeviceNumber() {
            return meterDeviceNumber;
        }

        public void setMeterDeviceNumber(Object meterDeviceNumber) {
            this.meterDeviceNumber = meterDeviceNumber;
        }

    }

}