package com.techlabs.apdcl.models.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AbnormalReport {

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

            @SerializedName("EqCode")
            @Expose
            private String eqCode;
            @SerializedName("EqCode_color")
            @Expose
            private String eqCodeColor;
            @SerializedName("EqId")
            @Expose
            private String eqId;
            @SerializedName("EqId_color")
            @Expose
            private String eqIdColor;
            @SerializedName("FeederId")
            @Expose
            private String feederId;
            @SerializedName("FeederId_color")
            @Expose
            private String feederIdColor;
            @SerializedName("LOADINGA")
            @Expose
            private String loadinga;
            @SerializedName("LOADINGA_color")
            @Expose
            private String lOADINGAColor;
            @SerializedName("LOADINGB")
            @Expose
            private String loadingb;
            @SerializedName("LOADINGB_color")
            @Expose
            private String lOADINGBColor;
            @SerializedName("LOADINGC")
            @Expose
            private String loadingc;
            @SerializedName("LOADINGC_color")
            @Expose
            private String lOADINGCColor;
            @SerializedName("SectionId")
            @Expose
            private String sectionId;
            @SerializedName("SectionId_color")
            @Expose
            private String sectionIdColor;
            @SerializedName("VA")
            @Expose
            private String va;
            @SerializedName("VA_color")
            @Expose
            private String vAColor;
            @SerializedName("VB")
            @Expose
            private String vb;
            @SerializedName("VB_color")
            @Expose
            private String vBColor;
            @SerializedName("VC")
            @Expose
            private String vc;
            @SerializedName("VC_color")
            @Expose
            private String vCColor;

            @SerializedName("EqNo")
            @Expose
            private String eqNo;
            @SerializedName("EqNo_color")
            @Expose
            private String eqNo_color;

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

            public String getEqId() {
                return eqId;
            }

            public void setEqId(String eqId) {
                this.eqId = eqId;
            }

            public String getEqIdColor() {
                return eqIdColor;
            }

            public void setEqIdColor(String eqIdColor) {
                this.eqIdColor = eqIdColor;
            }

            public String getFeederId() {
                return feederId;
            }

            public void setFeederId(String feederId) {
                this.feederId = feederId;
            }

            public String getFeederIdColor() {
                return feederIdColor;
            }

            public void setFeederIdColor(String feederIdColor) {
                this.feederIdColor = feederIdColor;
            }

            public String getLoadinga() {
                return loadinga;
            }

            public void setLoadinga(String loadinga) {
                this.loadinga = loadinga;
            }

            public String getLOADINGAColor() {
                return lOADINGAColor;
            }

            public void setLOADINGAColor(String lOADINGAColor) {
                this.lOADINGAColor = lOADINGAColor;
            }

            public String getLoadingb() {
                return loadingb;
            }

            public void setLoadingb(String loadingb) {
                this.loadingb = loadingb;
            }

            public String getLOADINGBColor() {
                return lOADINGBColor;
            }

            public void setLOADINGBColor(String lOADINGBColor) {
                this.lOADINGBColor = lOADINGBColor;
            }

            public String getLoadingc() {
                return loadingc;
            }

            public void setLoadingc(String loadingc) {
                this.loadingc = loadingc;
            }

            public String getLOADINGCColor() {
                return lOADINGCColor;
            }

            public void setLOADINGCColor(String lOADINGCColor) {
                this.lOADINGCColor = lOADINGCColor;
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

            public String getVa() {
                return va;
            }

            public void setVa(String va) {
                this.va = va;
            }

            public String getVAColor() {
                return vAColor;
            }

            public void setVAColor(String vAColor) {
                this.vAColor = vAColor;
            }

            public String getVb() {
                return vb;
            }

            public void setVb(String vb) {
                this.vb = vb;
            }

            public String getVBColor() {
                return vBColor;
            }

            public void setVBColor(String vBColor) {
                this.vBColor = vBColor;
            }

            public String getVc() {
                return vc;
            }

            public void setVc(String vc) {
                this.vc = vc;
            }

            public String getVCColor() {
                return vCColor;
            }

            public void setVCColor(String vCColor) {
                this.vCColor = vCColor;
            }







            public String getEqNo() {
                return eqNo;
            }

            public void setEqNo(String eqNo) {
                this.eqNo = eqNo;
            }

            public String getEqNo_color() {
                return eqNo_color;
            }

            public void setEqNo_color(String vCColor) {
                this.eqNo_color = eqNo_color;
            }

        }

    }
}