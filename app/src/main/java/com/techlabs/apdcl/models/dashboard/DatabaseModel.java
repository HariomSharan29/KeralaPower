package com.techlabs.apdcl.models.dashboard;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class DatabaseModel {

   @SerializedName("output")
   Output output;


    public void setOutput(Output output) {
        this.output = output;
    }
    public Output getOutput() {
        return output;
    }

    public class Output {

        @SerializedName("DatabaseName")
        List<String> DatabaseName;

        @SerializedName("Networkinfo")
        Networkinfo Networkinfo;

        @SerializedName("Group5All")
        List<Group5All> Group5All;

        @SerializedName("Group4All")
        List<Group4All> Group4All;

        @SerializedName("Group3All")
        List<Group3All> Group3All;

        @SerializedName("Group2All")
        List<Group2All> Group2All;

        @SerializedName("Group1All")
        List<Group1All> Group1All;

        @SerializedName("NetworkNameAll")
        List<NetworkNameAll> NetworkNameAll;

        @SerializedName("Group5")
        Group5 Group5;

        @SerializedName("Group4")
        Group4 Group4;

        @SerializedName("Group3")
        Group3 Group3;

        @SerializedName("Group2")
        Group2 Group2;

        @SerializedName("Group1")
        Group1 Group1;

        @SerializedName("NetworkName")
        NetworkName NetworkName;

        @SerializedName("ConsumerCount")
        List<ConsumerCount> ConsumerCount;

        @SerializedName("CustomerCountall")
        CustomerCountall CustomerCountall;

        @SerializedName("CustomeraLoad")
        CustomeraLoad CustomeraLoad;

        @SerializedName("CableLen")
        CableLen CableLen;

        @SerializedName("overheadlen")
        Overheadlen overheadlen;

        @SerializedName("overheadunballen")
        Overheadunballen overheadunballen;

        @SerializedName("socket")
        String socket;

        @SerializedName("DT_Count")
        DTCount DTCount;


        public void setDatabaseName(List<String> DatabaseName) {
            this.DatabaseName = DatabaseName;
        }
        public List<String> getDatabaseName() {
            return DatabaseName;
        }

        public void setNetworkinfo(Networkinfo Networkinfo) {
            this.Networkinfo = Networkinfo;
        }
        public Networkinfo getNetworkinfo() {
            return Networkinfo;
        }

        public void setGroup5All(List<Group5All> Group5All) {
            this.Group5All = Group5All;
        }
        public List<Group5All> getGroup5All() {
            return Group5All;
        }

        public void setGroup4All(List<Group4All> Group4All) {
            this.Group4All = Group4All;
        }
        public List<Group4All> getGroup4All() {
            return Group4All;
        }

        public void setGroup3All(List<Group3All> Group3All) {
            this.Group3All = Group3All;
        }
        public List<Group3All> getGroup3All() {
            return Group3All;
        }

        public void setGroup2All(List<Group2All> Group2All) {
            this.Group2All = Group2All;
        }
        public List<Group2All> getGroup2All() {
            return Group2All;
        }

        public void setGroup1All(List<Group1All> Group1All) {
            this.Group1All = Group1All;
        }
        public List<Group1All> getGroup1All() {
            return Group1All;
        }

        public void setNetworkNameAll(List<NetworkNameAll> NetworkNameAll) {
            this.NetworkNameAll = NetworkNameAll;
        }
        public List<NetworkNameAll> getNetworkNameAll() {
            return NetworkNameAll;
        }

        public void setGroup5(Group5 Group5) {
            this.Group5 = Group5;
        }
        public Group5 getGroup5() {
            return Group5;
        }

        public void setGroup4(Group4 Group4) {
            this.Group4 = Group4;
        }
        public Group4 getGroup4() {
            return Group4;
        }

        public void setGroup3(Group3 Group3) {
            this.Group3 = Group3;
        }
        public Group3 getGroup3() {
            return Group3;
        }

        public void setGroup2(Group2 Group2) {
            this.Group2 = Group2;
        }
        public Group2 getGroup2() {
            return Group2;
        }

        public void setGroup1(Group1 Group1) {
            this.Group1 = Group1;
        }
        public Group1 getGroup1() {
            return Group1;
        }

        public void setNetworkName(NetworkName NetworkName) {
            this.NetworkName = NetworkName;
        }
        public NetworkName getNetworkName() {
            return NetworkName;
        }

        public void setConsumerCount(List<ConsumerCount> ConsumerCount) {
            this.ConsumerCount = ConsumerCount;
        }
        public List<ConsumerCount> getConsumerCount() {
            return ConsumerCount;
        }

        public void setCustomerCountall(CustomerCountall CustomerCountall) {
            this.CustomerCountall = CustomerCountall;
        }
        public CustomerCountall getCustomerCountall() {
            return CustomerCountall;
        }

        public void setCustomeraLoad(CustomeraLoad CustomeraLoad) {
            this.CustomeraLoad = CustomeraLoad;
        }
        public CustomeraLoad getCustomeraLoad() {
            return CustomeraLoad;
        }

        public void setCableLen(CableLen CableLen) {
            this.CableLen = CableLen;
        }
        public CableLen getCableLen() {
            return CableLen;
        }

        public void setOverheadlen(Overheadlen overheadlen) {
            this.overheadlen = overheadlen;
        }
        public Overheadlen getOverheadlen() {
            return overheadlen;
        }

        public void setOverheadunballen(Overheadunballen overheadunballen) {
            this.overheadunballen = overheadunballen;
        }
        public Overheadunballen getOverheadunballen() {
            return overheadunballen;
        }

        public void setSocket(String socket) {
            this.socket = socket;
        }
        public String getSocket() {
            return socket;
        }

        public void setDTCount(DTCount DTCount) {
            this.DTCount = DTCount;
        }
        public DTCount getDTCount() {
            return DTCount;
        }

    }
    public class Networkinfo {

        @SerializedName("Group5_Count")
        int Group5Count;

        @SerializedName("Group4_Count")
        int Group4Count;

        @SerializedName("Group3_Count")
        int Group3Count;

        @SerializedName("Group2_Count")
        int Group2Count;

        @SerializedName("Group1_Count")
        int Group1Count;

        @SerializedName("NetworkId_Count")
        int NetworkIdCount;

        @SerializedName("VoltageWise")
        List<VoltageWise> VoltageWise;


        public void setGroup5Count(int Group5Count) {
            this.Group5Count = Group5Count;
        }
        public int getGroup5Count() {
            return Group5Count;
        }

        public void setGroup4Count(int Group4Count) {
            this.Group4Count = Group4Count;
        }
        public int getGroup4Count() {
            return Group4Count;
        }

        public void setGroup3Count(int Group3Count) {
            this.Group3Count = Group3Count;
        }
        public int getGroup3Count() {
            return Group3Count;
        }

        public void setGroup2Count(int Group2Count) {
            this.Group2Count = Group2Count;
        }
        public int getGroup2Count() {
            return Group2Count;
        }

        public void setGroup1Count(int Group1Count) {
            this.Group1Count = Group1Count;
        }
        public int getGroup1Count() {
            return Group1Count;
        }

        public void setNetworkIdCount(int NetworkIdCount) {
            this.NetworkIdCount = NetworkIdCount;
        }
        public int getNetworkIdCount() {
            return NetworkIdCount;
        }

        public void setVoltageWise(List<VoltageWise> VoltageWise) {
            this.VoltageWise = VoltageWise;
        }
        public List<VoltageWise> getVoltageWise() {
            return VoltageWise;
        }

    }

    public class VoltageWise {

        @SerializedName("Group2")
        String Group2;

        @SerializedName("NetworkType_0_Count")
        Integer NetworkType0Count;

        @SerializedName("NetworkType_1_Count")
        Integer NetworkType1Count;

        public void setGroup2(String Group2) {
            this.Group2 = Group2;
        }
        public String getGroup2() {
            return Group2;
        }

        public void setNetworkType0Count(Integer NetworkType0Count) {
            this.NetworkType0Count = NetworkType0Count;
        }
        public Integer getNetworkType0Count() {
            return NetworkType0Count;
        }

        public void setNetworkType1Count(Integer NetworkType1Count) {
            this.NetworkType1Count = NetworkType1Count;
        }
        public Integer getNetworkType1Count() {
            return NetworkType1Count;
        }

    }

    public class NetworkName {

        @SerializedName("NetworkId")
        List<String> NetworkId;


        public void setNetworkId(List<String> NetworkId) {
            this.NetworkId = NetworkId;
        }
        public List<String> getNetworkId() {
            return NetworkId;
        }

    }
    public class NetworkNameAll {

        @SerializedName("Group5")
        String Group5;

        @SerializedName("Group4")
        String Group4;

        @SerializedName("Group3")
        String Group3;

        @SerializedName("Group2")
        String Group2;

        @SerializedName("Group1")
        String Group1;

        @SerializedName("NetworkId")
        String NetworkId;


        public void setGroup5(String Group5) {
            this.Group5 = Group5;
        }
        public String getGroup5() {
            return Group5;
        }

        public void setGroup4(String Group4) {
            this.Group4 = Group4;
        }
        public String getGroup4() {
            return Group4;
        }

        public void setGroup3(String Group3) {
            this.Group3 = Group3;
        }
        public String getGroup3() {
            return Group3;
        }

        public void setGroup2(String Group2) {
            this.Group2 = Group2;
        }
        public String getGroup2() {
            return Group2;
        }

        public void setGroup1(String Group1) {
            this.Group1 = Group1;
        }
        public String getGroup1() {
            return Group1;
        }

        public void setNetworkId(String NetworkId) {
            this.NetworkId = NetworkId;
        }
        public String getNetworkId() {
            return NetworkId;
        }

    }

    public class Group1 {

        @SerializedName("Group1")
        List<String> Group1;


        public void setGroup1(List<String> Group1) {
            this.Group1 = Group1;
        }
        public List<String> getGroup1() {
            return Group1;
        }

    }
    public class Group1All {

        @SerializedName("Group5")
        String Group5;

        @SerializedName("Group4")
        String Group4;

        @SerializedName("Group3")
        String Group3;

        @SerializedName("Group2")
        String Group2;

        @SerializedName("Group1")
        String Group1;


        public void setGroup5(String Group5) {
            this.Group5 = Group5;
        }
        public String getGroup5() {
            return Group5;
        }

        public void setGroup4(String Group4) {
            this.Group4 = Group4;
        }
        public String getGroup4() {
            return Group4;
        }

        public void setGroup3(String Group3) {
            this.Group3 = Group3;
        }
        public String getGroup3() {
            return Group3;
        }

        public void setGroup2(String Group2) {
            this.Group2 = Group2;
        }
        public String getGroup2() {
            return Group2;
        }

        public void setGroup1(String Group1) {
            this.Group1 = Group1;
        }
        public String getGroup1() {
            return Group1;
        }

    }

    public class Group2 {

        @SerializedName("Group2")
        List<String> Group2;


        public void setGroup2(List<String> Group2) {
            this.Group2 = Group2;
        }
        public List<String> getGroup2() {
            return Group2;
        }

    }
    public class Group2All {

        @SerializedName("Group5")
        String Group5;

        @SerializedName("Group4")
        String Group4;

        @SerializedName("Group3")
        String Group3;

        @SerializedName("Group2")
        String Group2;


        public void setGroup5(String Group5) {
            this.Group5 = Group5;
        }
        public String getGroup5() {
            return Group5;
        }

        public void setGroup4(String Group4) {
            this.Group4 = Group4;
        }
        public String getGroup4() {
            return Group4;
        }

        public void setGroup3(String Group3) {
            this.Group3 = Group3;
        }
        public String getGroup3() {
            return Group3;
        }

        public void setGroup2(String Group2) {
            this.Group2 = Group2;
        }
        public String getGroup2() {
            return Group2;
        }

    }

    public class Group3 {

        @SerializedName("Group3")
        List<String> Group3;


        public void setGroup3(List<String> Group3) {
            this.Group3 = Group3;
        }
        public List<String> getGroup3() {
            return Group3;
        }

    }
    public class Group3All {

        @SerializedName("Group5")
        String Group5;

        @SerializedName("Group4")
        String Group4;

        @SerializedName("Group3")
        String Group3;


        public void setGroup5(String Group5) {
            this.Group5 = Group5;
        }
        public String getGroup5() {
            return Group5;
        }

        public void setGroup4(String Group4) {
            this.Group4 = Group4;
        }
        public String getGroup4() {
            return Group4;
        }

        public void setGroup3(String Group3) {
            this.Group3 = Group3;
        }
        public String getGroup3() {
            return Group3;
        }

    }

    public class Group4 {

        @SerializedName("Group4")
        List<String> Group4;


        public void setGroup4(List<String> Group4) {
            this.Group4 = Group4;
        }
        public List<String> getGroup4() {
            return Group4;
        }

    }
    public class Group4All {

        @SerializedName("Group5")
        String Group5;

        @SerializedName("Group4")
        String Group4;


        public void setGroup5(String Group5) {
            this.Group5 = Group5;
        }
        public String getGroup5() {
            return Group5;
        }

        public void setGroup4(String Group4) {
            this.Group4 = Group4;
        }
        public String getGroup4() {
            return Group4;
        }

    }

    public class Group5 {

        @SerializedName("Group5")
        List<String> Group5;


        public void setGroup5(List<String> Group5) {
            this.Group5 = Group5;
        }
        public List<String> getGroup5() {
            return Group5;
        }

    }
    public class Group5All {

        @SerializedName("Group5")
        String Group5;


        public void setGroup5(String Group5) {
            this.Group5 = Group5;
        }
        public String getGroup5() {
            return Group5;
        }

    }
    public class CableLen {

        @SerializedName("Length")
        double Length;


        public void setLength(double Length) {
            this.Length = Length;
        }
        public double getLength() {
            return Length;
        }

    }
    public class ConsumerCount {

        @SerializedName("ConsumerClassId")
        String ConsumerClassId;

        @SerializedName("ConsumerClassId_count")
        int ConsumerClassIdCount;


        public void setConsumerClassId(String ConsumerClassId) {
            this.ConsumerClassId = ConsumerClassId;
        }
        public String getConsumerClassId() {
            return ConsumerClassId;
        }

        public void setConsumerClassIdCount(int ConsumerClassIdCount) {
            this.ConsumerClassIdCount = ConsumerClassIdCount;
        }
        public int getConsumerClassIdCount() {
            return ConsumerClassIdCount;
        }

    }
    public class CustomeraLoad {

        @SerializedName("ActualKVA")
        double ActualKVA;

        @SerializedName("ConnectedKVA")
        double ConnectedKVA;


        public void setActualKVA(double ActualKVA) {
            this.ActualKVA = ActualKVA;
        }
        public double getActualKVA() {
            return ActualKVA;
        }

        public void setConnectedKVA(double ConnectedKVA) {
            this.ConnectedKVA = ConnectedKVA;
        }
        public double getConnectedKVA() {
            return ConnectedKVA;
        }

    }
    public class CustomerCountall {

        @SerializedName("Consumer_count")
        int ConsumerCount;


        public void setConsumerCount(int ConsumerCount) {
            this.ConsumerCount = ConsumerCount;
        }
        public int getConsumerCount() {
            return ConsumerCount;
        }

    }
    public class DTCount {

        @SerializedName("DT_count")
        int DTCount;


        public void setDTCount(int DTCount) {
            this.DTCount = DTCount;
        }
        public int getDTCount() {
            return DTCount;
        }

    }
    public class Overheadlen {

        @SerializedName("Length")
        double Length;


        public void setLength(double Length) {
            this.Length = Length;
        }
        public double getLength() {
            return Length;
        }

    }
    public class Overheadunballen {

        @SerializedName("Length")
        Double   Length;


        public void setLength(Double   Length) {
            this.Length = Length;
        }
        public Double   getLength() {
            return Length;
        }

    }

}
