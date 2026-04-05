package com.techlabs.apdcl.models.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NetworkIDModel {
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
        private List<String> databaseName;
        @SerializedName("Networkinfo")
        @Expose
        private Networkinfo networkinfo;
        @SerializedName("Group5All")
        @Expose
        private List<Group5All> group5All;
        @SerializedName("Group4All")
        @Expose
        private List<Group4All> group4All;
        @SerializedName("Group3All")
        @Expose
        private List<Group3All> group3All;
        @SerializedName("Group2All")
        @Expose
        private List<Group2All> group2All;
        @SerializedName("Group1All")
        @Expose
        private List<Group1All> group1All;
        @SerializedName("NetworkNameAll")
        @Expose
        private List<NetworkNameAll> networkNameAll;
        @SerializedName("Group5")
        @Expose
        private Group5 group5;
        @SerializedName("Group4")
        @Expose
        private Group4 group4;
        @SerializedName("Group3")
        @Expose
        private Group3 group3;
        @SerializedName("Group2")
        @Expose
        private Group2 group2;
        @SerializedName("Group1")
        @Expose
        private Group1 group1;
        @SerializedName("NetworkName")
        @Expose
        private NetworkName networkName;
        @SerializedName("ConsumerCount")
        @Expose
        private List<ConsumerCount> consumerCount;
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
        @SerializedName("socket")
        @Expose
        private String socket;
        @SerializedName("DT_Count")
        @Expose
        private DTCount dTCount;

        public List<String> getDatabaseName() {
            return databaseName;
        }

        public void setDatabaseName(List<String> databaseName) {
            this.databaseName = databaseName;
        }

        public Networkinfo getNetworkinfo() {
            return networkinfo;
        }

        public void setNetworkinfo(Networkinfo networkinfo) {
            this.networkinfo = networkinfo;
        }

        public List<Group5All> getGroup5All() {
            return group5All;
        }

        public void setGroup5All(List<Group5All> group5All) {
            this.group5All = group5All;
        }

        public List<Group4All> getGroup4All() {
            return group4All;
        }

        public void setGroup4All(List<Group4All> group4All) {
            this.group4All = group4All;
        }

        public List<Group3All> getGroup3All() {
            return group3All;
        }

        public void setGroup3All(List<Group3All> group3All) {
            this.group3All = group3All;
        }

        public List<Group2All> getGroup2All() {
            return group2All;
        }

        public void setGroup2All(List<Group2All> group2All) {
            this.group2All = group2All;
        }

        public List<Group1All> getGroup1All() {
            return group1All;
        }

        public void setGroup1All(List<Group1All> group1All) {
            this.group1All = group1All;
        }

        public List<NetworkNameAll> getNetworkNameAll() {
            return networkNameAll;
        }

        public void setNetworkNameAll(List<NetworkNameAll> networkNameAll) {
            this.networkNameAll = networkNameAll;
        }

        public Group5 getGroup5() {
            return group5;
        }

        public void setGroup5(Group5 group5) {
            this.group5 = group5;
        }

        public Group4 getGroup4() {
            return group4;
        }

        public void setGroup4(Group4 group4) {
            this.group4 = group4;
        }

        public Group3 getGroup3() {
            return group3;
        }

        public void setGroup3(Group3 group3) {
            this.group3 = group3;
        }

        public Group2 getGroup2() {
            return group2;
        }

        public void setGroup2(Group2 group2) {
            this.group2 = group2;
        }

        public Group1 getGroup1() {
            return group1;
        }

        public void setGroup1(Group1 group1) {
            this.group1 = group1;
        }

        public NetworkName getNetworkName() {
            return networkName;
        }

        public void setNetworkName(NetworkName networkName) {
            this.networkName = networkName;
        }

        public List<ConsumerCount> getConsumerCount() {
            return consumerCount;
        }

        public void setConsumerCount(List<ConsumerCount> consumerCount) {
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

        public String getSocket() {
            return socket;
        }

        public void setSocket(String socket) {
            this.socket = socket;
        }

        public DTCount getDTCount() {
            return dTCount;
        }

        public void setDTCount(DTCount dTCount) {
            this.dTCount = dTCount;
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
    public class ConsumerCount {

        @SerializedName("ConsumerClassId")
        @Expose
        private String consumerClassId;
        @SerializedName("ConsumerClassId_count")
        @Expose
        private Integer consumerClassIdCount;

        public String getConsumerClassId() {
            return consumerClassId;
        }

        public void setConsumerClassId(String consumerClassId) {
            this.consumerClassId = consumerClassId;
        }

        public Integer getConsumerClassIdCount() {
            return consumerClassIdCount;
        }

        public void setConsumerClassIdCount(Integer consumerClassIdCount) {
            this.consumerClassIdCount = consumerClassIdCount;
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
        private Float actualKVA;
        @SerializedName("ConnectedKVA")
        @Expose
        private Float connectedKVA;

        public Float getActualKVA() {
            return actualKVA;
        }

        public void setActualKVA(Float actualKVA) {
            this.actualKVA = actualKVA;
        }

        public Float getConnectedKVA() {
            return connectedKVA;
        }

        public void setConnectedKVA(Float connectedKVA) {
            this.connectedKVA = connectedKVA;
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
    public class Group1 {

        @SerializedName("Group1")
        @Expose
        private List<String> group1;

        public List<String> getGroup1() {
            return group1;
        }

        public void setGroup1(List<String> group1) {
            this.group1 = group1;
        }

    }
    public class Group1All {

        @SerializedName("Group5")
        @Expose
        private String group5;
        @SerializedName("Group4")
        @Expose
        private String group4;
        @SerializedName("Group3")
        @Expose
        private String group3;
        @SerializedName("Group2")
        @Expose
        private String group2;
        @SerializedName("Group1")
        @Expose
        private String group1;

        public String getGroup5() {
            return group5;
        }

        public void setGroup5(String group5) {
            this.group5 = group5;
        }

        public String getGroup4() {
            return group4;
        }

        public void setGroup4(String group4) {
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

        @SerializedName("Group2")
        @Expose
        private List<String> group2;

        public List<String> getGroup2() {
            return group2;
        }

        public void setGroup2(List<String> group2) {
            this.group2 = group2;
        }

    }
    public class Group2All {

        @SerializedName("Group5")
        @Expose
        private String group5;
        @SerializedName("Group4")
        @Expose
        private String group4;
        @SerializedName("Group3")
        @Expose
        private String group3;
        @SerializedName("Group2")
        @Expose
        private String group2;

        public String getGroup5() {
            return group5;
        }

        public void setGroup5(String group5) {
            this.group5 = group5;
        }

        public String getGroup4() {
            return group4;
        }

        public void setGroup4(String group4) {
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

        @SerializedName("Group3")
        @Expose
        private List<String> group3;

        public List<String> getGroup3() {
            return group3;
        }

        public void setGroup3(List<String> group3) {
            this.group3 = group3;
        }

    }
    public class Group3All {

        @SerializedName("Group5")
        @Expose
        private String group5;
        @SerializedName("Group4")
        @Expose
        private String group4;
        @SerializedName("Group3")
        @Expose
        private String group3;

        public String getGroup5() {
            return group5;
        }

        public void setGroup5(String group5) {
            this.group5 = group5;
        }

        public String getGroup4() {
            return group4;
        }

        public void setGroup4(String group4) {
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

        @SerializedName("Group4")
        @Expose
        private List<String> group4;

        public List<String> getGroup4() {
            return group4;
        }

        public void setGroup4(List<String> group4) {
            this.group4 = group4;
        }

    }
    public class Group4All {

        @SerializedName("Group5")
        @Expose
        private String group5;
        @SerializedName("Group4")
        @Expose
        private String group4;

        public String getGroup5() {
            return group5;
        }

        public void setGroup5(String group5) {
            this.group5 = group5;
        }

        public String getGroup4() {
            return group4;
        }

        public void setGroup4(String group4) {
            this.group4 = group4;
        }

    }
    public class Group5 {

        @SerializedName("Group5")
        @Expose
        private List<String> group5;

        public List<String> getGroup5() {
            return group5;
        }

        public void setGroup5(List<String> group5) {
            this.group5 = group5;
        }

    }
    public class Group5All {

        @SerializedName("Group5")
        @Expose
        private String group5;

        public String getGroup5() {
            return group5;
        }

        public void setGroup5(String group5) {
            this.group5 = group5;
        }

    }
    public class NetworkName {

        @SerializedName("NetworkId")
        @Expose
        private List<String> networkId;

        public List<String> getNetworkId() {
            return networkId;
        }

        public void setNetworkId(List<String> networkId) {
            this.networkId = networkId;
        }

    }
    public class NetworkNameAll {

        @SerializedName("Group5")
        @Expose
        private String group5;
        @SerializedName("Group4")
        @Expose
        private String group4;
        @SerializedName("Group3")
        @Expose
        private String group3;
        @SerializedName("Group2")
        @Expose
        private String group2;
        @SerializedName("Group1")
        @Expose
        private String group1;
        @SerializedName("NetworkId")
        @Expose
        private String networkId;

        public String getGroup5() {
            return group5;
        }

        public void setGroup5(String group5) {
            this.group5 = group5;
        }

        public String getGroup4() {
            return group4;
        }

        public void setGroup4(String group4) {
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

        public String getNetworkId() {
            return networkId;
        }

        public void setNetworkId(String networkId) {
            this.networkId = networkId;
        }

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

}
