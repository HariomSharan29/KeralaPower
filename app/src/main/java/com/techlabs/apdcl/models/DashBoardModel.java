package com.techlabs.apdcl.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashBoardModel {

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

        @SerializedName("DatabaseName")
        @Expose
        private String DatabaseName;
        @SerializedName("Networkinfo")
        @Expose
        private Networkinfo networkinfo;
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
        @SerializedName("NetworkName")
        @Expose
        private List<NetworkName> networkName;
        @SerializedName("ConsumerCount")
        @Expose
        private List<Object> consumerCount;
        @SerializedName("CustomerCountall")
        @Expose
        private CustomerCountall customerCountall;
        @SerializedName("CustomeraLoad")
        @Expose
        private CustomeraLoad customeraLoad;
        @SerializedName("CableLen")
        @Expose
        private CableLen cableLen;
        @SerializedName("overheadlen")
        @Expose
        private Overheadlen overheadlen;
        @SerializedName("overheadunballen")
        @Expose
        private Overheadunballen overheadunballen;
        @SerializedName("DT_Count")
        @Expose
        private DTCount dTCount;

        public String getDatabaseName() {
            return DatabaseName;
        }

        public void setDatabaseName(String databaseName) {
            this.DatabaseName = databaseName;
        }

        public Networkinfo getNetworkinfo() {
            return networkinfo;
        }

        public void setNetworkinfo(Networkinfo networkinfo) {
            this.networkinfo = networkinfo;
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

        public List<NetworkName> getNetworkName() {
            return networkName;
        }

        public void setNetworkName(List<NetworkName> networkName) {
            this.networkName = networkName;
        }

        public List<Object> getConsumerCount() {
            return consumerCount;
        }

        public void setConsumerCount(List<Object> consumerCount) {
            this.consumerCount = consumerCount;
        }

        public CustomerCountall getCustomerCountall() {
            return customerCountall;
        }

        public void setCustomerCountall(CustomerCountall customerCountall) {
            this.customerCountall = customerCountall;
        }

        public CustomeraLoad getCustomeraLoad() {
            return customeraLoad;
        }

        public void setCustomeraLoad(CustomeraLoad customeraLoad) {
            this.customeraLoad = customeraLoad;
        }

        public CableLen getCableLen() {
            return cableLen;
        }

        public void setCableLen(CableLen cableLen) {
            this.cableLen = cableLen;
        }

        public Overheadlen getOverheadlen() {
            return overheadlen;
        }

        public void setOverheadlen(Overheadlen overheadlen) {
            this.overheadlen = overheadlen;
        }

        public Overheadunballen getOverheadunballen() {
            return overheadunballen;
        }

        public void setOverheadunballen(Overheadunballen overheadunballen) {
            this.overheadunballen = overheadunballen;
        }

        public DTCount getDTCount() {
            return dTCount;
        }

        public void setDTCount(DTCount dTCount) {
            this.dTCount = dTCount;
        }

        public class Networkinfo {

            @SerializedName("Group5_Count")
            @Expose
            private Integer group5Count;
            @SerializedName("Group4_Count")
            @Expose
            private Integer group4Count;
            @SerializedName("Group3_Count")
            @Expose
            private Integer group3Count;
            @SerializedName("Group2_Count")
            @Expose
            private Integer group2Count;
            @SerializedName("Group1_Count")
            @Expose
            private Integer group1Count;
            @SerializedName("NetworkId_Count")
            @Expose
            private Integer networkIdCount;

            public Integer getGroup5Count() {
                return group5Count;
            }

            public void setGroup5Count(Integer group5Count) {
                this.group5Count = group5Count;
            }

            public Integer getGroup4Count() {
                return group4Count;
            }

            public void setGroup4Count(Integer group4Count) {
                this.group4Count = group4Count;
            }

            public Integer getGroup3Count() {
                return group3Count;
            }

            public void setGroup3Count(Integer group3Count) {
                this.group3Count = group3Count;
            }

            public Integer getGroup2Count() {
                return group2Count;
            }

            public void setGroup2Count(Integer group2Count) {
                this.group2Count = group2Count;
            }

            public Integer getGroup1Count() {
                return group1Count;
            }

            public void setGroup1Count(Integer group1Count) {
                this.group1Count = group1Count;
            }

            public Integer getNetworkIdCount() {
                return networkIdCount;
            }

            public void setNetworkIdCount(Integer networkIdCount) {
                this.networkIdCount = networkIdCount;
            }

        }

        public class Group5 {

            @SerializedName("Group5")
            @Expose
            private Object group5;

            public Object getGroup5() {
                return group5;
            }

            public void setGroup5(Object group5) {
                this.group5 = group5;
            }

        }

        public class Group4 {

            @SerializedName("Group4")
            @Expose
            private Object group4;

            public Object getGroup4() {
                return group4;
            }

            public void setGroup4(Object group4) {
                this.group4 = group4;
            }

        }

        public class Group3 {

            @SerializedName("Group3")
            @Expose
            private Object group3;

            public Object getGroup3() {
                return group3;
            }

            public void setGroup3(Object group3) {
                this.group3 = group3;
            }

        }

        public class Group2 {

            @SerializedName("Group2")
            @Expose
            private String group2;

            public String getGroup2() {
                return group2;
            }

            public void setGroup2(String group2) {
                this.group2 = group2;
            }

        }

        public class Group1 {

            @SerializedName("Group1")
            @Expose
            private String group1;

            public String getGroup1() {
                return group1;
            }

            public void setGroup1(String group1) {
                this.group1 = group1;
            }

        }

        public class NetworkName {

            @SerializedName("NetworkId")
            @Expose
            private String networkId;

            public String getNetworkId() {
                return networkId;
            }

            public void setNetworkId(String networkId) {
                this.networkId = networkId;
            }

        }

        public class CustomerCountall {

            @SerializedName("Consumer_count")
            @Expose
            private Integer consumerCount;

            public Integer getConsumerCount() {
                return consumerCount;
            }

            public void setConsumerCount(Integer consumerCount) {
                this.consumerCount = consumerCount;
            }

        }

        public class CustomeraLoad {

            @SerializedName("ActualKVA")
            @Expose
            private Object actualKVA;
            @SerializedName("ConnectedKVA")
            @Expose
            private Object connectedKVA;

            public Object getActualKVA() {
                return actualKVA;
            }

            public void setActualKVA(Object actualKVA) {
                this.actualKVA = actualKVA;
            }

            public Object getConnectedKVA() {
                return connectedKVA;
            }

            public void setConnectedKVA(Object connectedKVA) {
                this.connectedKVA = connectedKVA;
            }

        }

        public class CableLen {

            @SerializedName("Length")
            @Expose
            private Float length;

            public Float getLength() {
                return length;
            }

            public void setLength(Float length) {
                this.length = length;
            }

        }

        public class Overheadlen {

            @SerializedName("Length")
            @Expose
            private Float length;

            public Float getLength() {
                return length;
            }

            public void setLength(Float length) {
                this.length = length;
            }

        }

        public class Overheadunballen {

            @SerializedName("Length")
            @Expose
            private Float length;

            public Float getLength() {
                return length;
            }

            public void setLength(Float length) {
                this.length = length;
            }

        }

        public class DTCount {

            @SerializedName("DT_count")
            @Expose
            private Integer dTCount;

            public Integer getDTCount() {
                return dTCount;
            }

            public void setDTCount(Integer dTCount) {
                this.dTCount = dTCount;
            }

        }

    }
}






