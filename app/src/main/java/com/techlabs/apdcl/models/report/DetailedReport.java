package com.techlabs.apdcl.models.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailedReport {

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
            @SerializedName("KVARA")
            @Expose
            private String kvara;
            @SerializedName("KVARA_color")
            @Expose
            private String kVARAColor;
            @SerializedName("KWA")
            @Expose
            private String kwa;
            @SerializedName("KWA_color")
            @Expose
            private String kWAColor;
            @SerializedName("LOADINGA")
            @Expose
            private String loadinga;
            @SerializedName("LOADINGA_color")
            @Expose
            private String lOADINGAColor;
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

            public String getKvara() {
                return kvara;
            }

            public void setKvara(String kvara) {
                this.kvara = kvara;
            }

            public String getKVARAColor() {
                return kVARAColor;
            }

            public void setKVARAColor(String kVARAColor) {
                this.kVARAColor = kVARAColor;
            }

            public String getKwa() {
                return kwa;
            }

            public void setKwa(String kwa) {
                this.kwa = kwa;
            }

            public String getKWAColor() {
                return kWAColor;
            }

            public void setKWAColor(String kWAColor) {
                this.kWAColor = kWAColor;
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

            public String getEqNo() {
                return eqNo;
            }

            public void setEqNo(String eqNo) {
                this.eqNo = eqNo;
            }

            public String getEqNo_color() {
                return eqNo_color;
            }

            public void setEqNo_color(String eqNo_color) {
                this.eqNo_color = eqNo_color;
            }

        }

    }
}



