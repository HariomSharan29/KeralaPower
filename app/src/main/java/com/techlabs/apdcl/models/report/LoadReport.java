package com.techlabs.apdcl.models.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadReport {

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

        @SerializedName("columns")
        @Expose
        private List<String> columns;
        @SerializedName("data")
        @Expose
        private List<Datum> data;

        public List<String> getColumns() {
            return columns;
        }

        public void setColumns(List<String> columns) {
            this.columns = columns;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public class Datum {

            @SerializedName("AdKVADist")
            @Expose
            private String adKVADist;
            @SerializedName("AdKVADistA")
            @Expose
            private String adKVADistA;
            @SerializedName("AdKVADistA_color")
            @Expose
            private String adKVADistAColor;
            @SerializedName("AdKVADistB")
            @Expose
            private String adKVADistB;
            @SerializedName("AdKVADistB_color")
            @Expose
            private String adKVADistBColor;
            @SerializedName("AdKVADistC")
            @Expose
            private String adKVADistC;
            @SerializedName("AdKVADistC_color")
            @Expose
            private String adKVADistCColor;
            @SerializedName("AdKVADist_color")
            @Expose
            private String adKVADistColor;
            @SerializedName("AdKVASpot")
            @Expose
            private String adKVASpot;
            @SerializedName("AdKVASpotA")
            @Expose
            private String adKVASpotA;
            @SerializedName("AdKVASpotA_color")
            @Expose
            private String adKVASpotAColor;
            @SerializedName("AdKVASpotB")
            @Expose
            private String adKVASpotB;
            @SerializedName("AdKVASpotB_color")
            @Expose
            private String adKVASpotBColor;
            @SerializedName("AdKVASpotC")
            @Expose
            private String adKVASpotC;
            @SerializedName("AdKVASpotC_color")
            @Expose
            private String adKVASpotCColor;
            @SerializedName("AdKVASpot_color")
            @Expose
            private String adKVASpotColor;
            @SerializedName("AdPFDist")
            @Expose
            private String adPFDist;
            @SerializedName("AdPFDistA")
            @Expose
            private String adPFDistA;
            @SerializedName("AdPFDistA_color")
            @Expose
            private String adPFDistAColor;
            @SerializedName("AdPFDistB")
            @Expose
            private String adPFDistB;
            @SerializedName("AdPFDistB_color")
            @Expose
            private String adPFDistBColor;
            @SerializedName("AdPFDistC")
            @Expose
            private String adPFDistC;
            @SerializedName("AdPFDistC_color")
            @Expose
            private String adPFDistCColor;
            @SerializedName("AdPFDist_color")
            @Expose
            private String adPFDistColor;
            @SerializedName("AdPFSpot")
            @Expose
            private String adPFSpot;
            @SerializedName("AdPFSpotA")
            @Expose
            private String adPFSpotA;
            @SerializedName("AdPFSpotA_color")
            @Expose
            private String adPFSpotAColor;
            @SerializedName("AdPFSpotB")
            @Expose
            private String adPFSpotB;
            @SerializedName("AdPFSpotB_color")
            @Expose
            private String adPFSpotBColor;
            @SerializedName("AdPFSpotC")
            @Expose
            private String adPFSpotC;
            @SerializedName("AdPFSpotC_color")
            @Expose
            private String adPFSpotCColor;
            @SerializedName("AdPFSpot_color")
            @Expose
            private String adPFSpotColor;
            @SerializedName("EqCode")
            @Expose
            private String eqCode;
            @SerializedName("EqCode_color")
            @Expose
            private String eqCodeColor;
            @SerializedName("EqNo")
            @Expose
            private String eqNo;
            @SerializedName("EqNo_color")
            @Expose
            private String eqNoColor;
            @SerializedName("NetworkId")
            @Expose
            private String networkId;
            @SerializedName("NetworkId_color")
            @Expose
            private String networkIdColor;
            @SerializedName("SectionId")
            @Expose
            private String sectionId;
            @SerializedName("SectionId_color")
            @Expose
            private String sectionIdColor;
            @SerializedName("VBaseA")
            @Expose
            private String vBaseA;
            @SerializedName("VBaseA_color")
            @Expose
            private String vBaseAColor;
            @SerializedName("VBaseB")
            @Expose
            private String vBaseB;
            @SerializedName("VBaseB_color")
            @Expose
            private String vBaseBColor;
            @SerializedName("VBaseC")
            @Expose
            private String vBaseC;
            @SerializedName("VBaseC_color")
            @Expose
            private String vBaseCColor;

            public String getAdKVADist() {
                return adKVADist;
            }

            public void setAdKVADist(String adKVADist) {
                this.adKVADist = adKVADist;
            }

            public String getAdKVADistA() {
                return adKVADistA;
            }

            public void setAdKVADistA(String adKVADistA) {
                this.adKVADistA = adKVADistA;
            }

            public String getAdKVADistAColor() {
                return adKVADistAColor;
            }

            public void setAdKVADistAColor(String adKVADistAColor) {
                this.adKVADistAColor = adKVADistAColor;
            }

            public String getAdKVADistB() {
                return adKVADistB;
            }

            public void setAdKVADistB(String adKVADistB) {
                this.adKVADistB = adKVADistB;
            }

            public String getAdKVADistBColor() {
                return adKVADistBColor;
            }

            public void setAdKVADistBColor(String adKVADistBColor) {
                this.adKVADistBColor = adKVADistBColor;
            }

            public String getAdKVADistC() {
                return adKVADistC;
            }

            public void setAdKVADistC(String adKVADistC) {
                this.adKVADistC = adKVADistC;
            }

            public String getAdKVADistCColor() {
                return adKVADistCColor;
            }

            public void setAdKVADistCColor(String adKVADistCColor) {
                this.adKVADistCColor = adKVADistCColor;
            }

            public String getAdKVADistColor() {
                return adKVADistColor;
            }

            public void setAdKVADistColor(String adKVADistColor) {
                this.adKVADistColor = adKVADistColor;
            }

            public String getAdKVASpot() {
                return adKVASpot;
            }

            public void setAdKVASpot(String adKVASpot) {
                this.adKVASpot = adKVASpot;
            }

            public String getAdKVASpotA() {
                return adKVASpotA;
            }

            public void setAdKVASpotA(String adKVASpotA) {
                this.adKVASpotA = adKVASpotA;
            }

            public String getAdKVASpotAColor() {
                return adKVASpotAColor;
            }

            public void setAdKVASpotAColor(String adKVASpotAColor) {
                this.adKVASpotAColor = adKVASpotAColor;
            }

            public String getAdKVASpotB() {
                return adKVASpotB;
            }

            public void setAdKVASpotB(String adKVASpotB) {
                this.adKVASpotB = adKVASpotB;
            }

            public String getAdKVASpotBColor() {
                return adKVASpotBColor;
            }

            public void setAdKVASpotBColor(String adKVASpotBColor) {
                this.adKVASpotBColor = adKVASpotBColor;
            }

            public String getAdKVASpotC() {
                return adKVASpotC;
            }

            public void setAdKVASpotC(String adKVASpotC) {
                this.adKVASpotC = adKVASpotC;
            }

            public String getAdKVASpotCColor() {
                return adKVASpotCColor;
            }

            public void setAdKVASpotCColor(String adKVASpotCColor) {
                this.adKVASpotCColor = adKVASpotCColor;
            }

            public String getAdKVASpotColor() {
                return adKVASpotColor;
            }

            public void setAdKVASpotColor(String adKVASpotColor) {
                this.adKVASpotColor = adKVASpotColor;
            }

            public String getAdPFDist() {
                return adPFDist;
            }

            public void setAdPFDist(String adPFDist) {
                this.adPFDist = adPFDist;
            }

            public String getAdPFDistA() {
                return adPFDistA;
            }

            public void setAdPFDistA(String adPFDistA) {
                this.adPFDistA = adPFDistA;
            }

            public String getAdPFDistAColor() {
                return adPFDistAColor;
            }

            public void setAdPFDistAColor(String adPFDistAColor) {
                this.adPFDistAColor = adPFDistAColor;
            }

            public String getAdPFDistB() {
                return adPFDistB;
            }

            public void setAdPFDistB(String adPFDistB) {
                this.adPFDistB = adPFDistB;
            }

            public String getAdPFDistBColor() {
                return adPFDistBColor;
            }

            public void setAdPFDistBColor(String adPFDistBColor) {
                this.adPFDistBColor = adPFDistBColor;
            }

            public String getAdPFDistC() {
                return adPFDistC;
            }

            public void setAdPFDistC(String adPFDistC) {
                this.adPFDistC = adPFDistC;
            }

            public String getAdPFDistCColor() {
                return adPFDistCColor;
            }

            public void setAdPFDistCColor(String adPFDistCColor) {
                this.adPFDistCColor = adPFDistCColor;
            }

            public String getAdPFDistColor() {
                return adPFDistColor;
            }

            public void setAdPFDistColor(String adPFDistColor) {
                this.adPFDistColor = adPFDistColor;
            }

            public String getAdPFSpot() {
                return adPFSpot;
            }

            public void setAdPFSpot(String adPFSpot) {
                this.adPFSpot = adPFSpot;
            }

            public String getAdPFSpotA() {
                return adPFSpotA;
            }

            public void setAdPFSpotA(String adPFSpotA) {
                this.adPFSpotA = adPFSpotA;
            }

            public String getAdPFSpotAColor() {
                return adPFSpotAColor;
            }

            public void setAdPFSpotAColor(String adPFSpotAColor) {
                this.adPFSpotAColor = adPFSpotAColor;
            }

            public String getAdPFSpotB() {
                return adPFSpotB;
            }

            public void setAdPFSpotB(String adPFSpotB) {
                this.adPFSpotB = adPFSpotB;
            }

            public String getAdPFSpotBColor() {
                return adPFSpotBColor;
            }

            public void setAdPFSpotBColor(String adPFSpotBColor) {
                this.adPFSpotBColor = adPFSpotBColor;
            }

            public String getAdPFSpotC() {
                return adPFSpotC;
            }

            public void setAdPFSpotC(String adPFSpotC) {
                this.adPFSpotC = adPFSpotC;
            }

            public String getAdPFSpotCColor() {
                return adPFSpotCColor;
            }

            public void setAdPFSpotCColor(String adPFSpotCColor) {
                this.adPFSpotCColor = adPFSpotCColor;
            }

            public String getAdPFSpotColor() {
                return adPFSpotColor;
            }

            public void setAdPFSpotColor(String adPFSpotColor) {
                this.adPFSpotColor = adPFSpotColor;
            }

            public String getEqCode() {
                return eqCode;
            }

            public void setEqCode(String eqCode) {
                this.eqCode = eqCode;
            }

            public String getEqCodeColor() {
                return eqCodeColor;
            }

            public void setEqCodeColor(String eqCodeColor) {
                this.eqCodeColor = eqCodeColor;
            }

            public String getEqNo() {
                return eqNo;
            }

            public void setEqNo(String eqNo) {
                this.eqNo = eqNo;
            }

            public String getEqNoColor() {
                return eqNoColor;
            }

            public void setEqNoColor(String eqNoColor) {
                this.eqNoColor = eqNoColor;
            }

            public String getNetworkId() {
                return networkId;
            }

            public void setNetworkId(String networkId) {
                this.networkId = networkId;
            }

            public String getNetworkIdColor() {
                return networkIdColor;
            }

            public void setNetworkIdColor(String networkIdColor) {
                this.networkIdColor = networkIdColor;
            }

            public String getSectionId() {
                return sectionId;
            }

            public void setSectionId(String sectionId) {
                this.sectionId = sectionId;
            }

            public String getSectionIdColor() {
                return sectionIdColor;
            }

            public void setSectionIdColor(String sectionIdColor) {
                this.sectionIdColor = sectionIdColor;
            }

            public String getVBaseA() {
                return vBaseA;
            }

            public void setVBaseA(String vBaseA) {
                this.vBaseA = vBaseA;
            }

            public String getVBaseAColor() {
                return vBaseAColor;
            }

            public void setVBaseAColor(String vBaseAColor) {
                this.vBaseAColor = vBaseAColor;
            }

            public String getVBaseB() {
                return vBaseB;
            }

            public void setVBaseB(String vBaseB) {
                this.vBaseB = vBaseB;
            }

            public String getVBaseBColor() {
                return vBaseBColor;
            }

            public void setVBaseBColor(String vBaseBColor) {
                this.vBaseBColor = vBaseBColor;
            }

            public String getVBaseC() {
                return vBaseC;
            }

            public void setVBaseC(String vBaseC) {
                this.vBaseC = vBaseC;
            }

            public String getVBaseCColor() {
                return vBaseCColor;
            }

            public void setVBaseCColor(String vBaseCColor) {
                this.vBaseCColor = vBaseCColor;
            }

        }

    }
}


