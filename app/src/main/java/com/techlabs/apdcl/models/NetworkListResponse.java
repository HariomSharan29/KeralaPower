package com.techlabs.apdcl.models;

public class NetworkListResponse {

    String NetworkId;
    String Group1;
    String Group2;
    String Group3;


    public NetworkListResponse(String networkId, String group1, String group2, String group3) {
        NetworkId = networkId;
        Group1 = group1;
        Group2 = group2;
        Group3 = group3;
    }

    public String getNetworkId() {
        return NetworkId;
    }

    public void setNetworkId(String networkId) {
        NetworkId = networkId;
    }

    public String getGroup1() {
        return Group1;
    }

    public void setGroup1(String group1) {
        Group1 = group1;
    }

    public String getGroup2() {
        return Group2;
    }

    public void setGroup2(String group2) {
        Group2 = group2;
    }

    public String getGroup3() {
        return Group3;
    }

    public void setGroup3(String group3) {
        Group3 = group3;
    }

}
