package com.techlabs.apdcl.models.dashboard;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardReqBody {
    @SerializedName("NetworkId")
    List<String> NetworkId;

    @SerializedName("UserType")
    String UserType;

    @SerializedName("Project")
    String Project;

    @SerializedName("Group5")
    List<String> Group5;

    @SerializedName("Group4")
    List<String> Group4;

    @SerializedName("Group3")
    List<String> Group3;

    @SerializedName("Group2")
    List<String> Group2;

    @SerializedName("Group1")
    List<String> Group1;

    @SerializedName("DashBoardType")
    String DashBoardType;

    @SerializedName("CYMDBNET")
    String CYMDBNET;


    public void setNetworkId(List<String> NetworkId) {
        this.NetworkId = NetworkId;
    }
    public List<String> getNetworkId() {
        return NetworkId;
    }

    public void setUserType(String UserType) {
        this.UserType = UserType;
    }
    public String getUserType() {
        return UserType;
    }

    public void setProject(String Project) {
        this.Project = Project;
    }
    public String getProject() {
        return Project;
    }

    public void setGroup5(List<String> Group5) {
        this.Group5 = Group5;
    }
    public List<String> getGroup5() {
        return Group5;
    }

    public void setGroup4(List<String> Group4) {
        this.Group4 = Group4;
    }
    public List<String> getGroup4() {
        return Group4;
    }

    public void setGroup3(List<String> Group3) {
        this.Group3 = Group3;
    }
    public List<String> getGroup3() {
        return Group3;
    }

    public void setGroup2(List<String> Group2) {
        this.Group2 = Group2;
    }
    public List<String> getGroup2() {
        return Group2;
    }

    public void setGroup1(List<String> Group1) {
        this.Group1 = Group1;
    }
    public List<String> getGroup1() {
        return Group1;
    }

    public void setDashBoardType(String DashBoardType) {
        this.DashBoardType = DashBoardType;
    }
    public String getDashBoardType() {
        return DashBoardType;
    }

    public void setCYMDBNET(String CYMDBNET) {
        this.CYMDBNET = CYMDBNET;
    }
    public String getCYMDBNET() {
        return CYMDBNET;
    }

}
