package com.techlabs.apdcl.models.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class OverLoadConductorReport {

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

            @SerializedName("FeederId")
            @Expose
            private String feederId;
            @SerializedName("FeederId_color")
            @Expose
            private String feederIdColor;
            @SerializedName("olcBegOvlCond")
            @Expose
            private String olcBegOvlCond;
            @SerializedName("olcBegOvlCond_color")
            @Expose
            private String olcBegOvlCondColor;
            @SerializedName("olcConductID")
            @Expose
            private String olcConductID;
            @SerializedName("olcConductID_color")
            @Expose
            private String olcConductIDColor;
            @SerializedName("olcEndOvlCond")
            @Expose
            private String olcEndOvlCond;
            @SerializedName("olcEndOvlCond_color")
            @Expose
            private String olcEndOvlCondColor;
            @SerializedName("olcLoadCap")
            @Expose
            private String olcLoadCap;
            @SerializedName("olcLoadCap_color")
            @Expose
            private String olcLoadCapColor;
            @SerializedName("olcPercntOvl")
            @Expose
            private String olcPercntOvl;
            @SerializedName("olcPercntOvl_color")
            @Expose
            private String olcPercntOvlColor;
            @SerializedName("olcProDevID")
            @Expose
            private String olcProDevID;
            @SerializedName("olcProDevID_color")
            @Expose
            private String olcProDevIDColor;

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

            public String getOlcBegOvlCond() {
                return olcBegOvlCond;
            }

            public void setOlcBegOvlCond(String olcBegOvlCond) {
                this.olcBegOvlCond = olcBegOvlCond;
            }

            public String getOlcBegOvlCondColor() {
                return olcBegOvlCondColor;
            }

            public void setOlcBegOvlCondColor(String olcBegOvlCondColor) {
                this.olcBegOvlCondColor = olcBegOvlCondColor;
            }

            public String getOlcConductID() {
                return olcConductID;
            }

            public void setOlcConductID(String olcConductID) {
                this.olcConductID = olcConductID;
            }

            public String getOlcConductIDColor() {
                return olcConductIDColor;
            }

            public void setOlcConductIDColor(String olcConductIDColor) {
                this.olcConductIDColor = olcConductIDColor;
            }

            public String getOlcEndOvlCond() {
                return olcEndOvlCond;
            }

            public void setOlcEndOvlCond(String olcEndOvlCond) {
                this.olcEndOvlCond = olcEndOvlCond;
            }

            public String getOlcEndOvlCondColor() {
                return olcEndOvlCondColor;
            }

            public void setOlcEndOvlCondColor(String olcEndOvlCondColor) {
                this.olcEndOvlCondColor = olcEndOvlCondColor;
            }

            public String getOlcLoadCap() {
                return olcLoadCap;
            }

            public void setOlcLoadCap(String olcLoadCap) {
                this.olcLoadCap = olcLoadCap;
            }

            public String getOlcLoadCapColor() {
                return olcLoadCapColor;
            }

            public void setOlcLoadCapColor(String olcLoadCapColor) {
                this.olcLoadCapColor = olcLoadCapColor;
            }

            public String getOlcPercntOvl() {
                return olcPercntOvl;
            }

            public void setOlcPercntOvl(String olcPercntOvl) {
                this.olcPercntOvl = olcPercntOvl;
            }

            public String getOlcPercntOvlColor() {
                return olcPercntOvlColor;
            }

            public void setOlcPercntOvlColor(String olcPercntOvlColor) {
                this.olcPercntOvlColor = olcPercntOvlColor;
            }

            public String getOlcProDevID() {
                return olcProDevID;
            }

            public void setOlcProDevID(String olcProDevID) {
                this.olcProDevID = olcProDevID;
            }

            public String getOlcProDevIDColor() {
                return olcProDevIDColor;
            }

            public void setOlcProDevIDColor(String olcProDevIDColor) {
                this.olcProDevIDColor = olcProDevIDColor;
            }

        }

    }

}
