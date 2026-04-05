package com.techlabs.apdcl.models.analysis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShortCircuitDetailedModel {

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
        @SerializedName("columns1")
        @Expose
        private List<String> columns1;
        @SerializedName("data")
        @Expose
        private List<Datum> data;

        public List<String> getColumns() {
            return columns;
        }

        public void setColumns(List<String> columns) {
            this.columns = columns;
        }

        public List<String> getColumns1() {
            return columns1;
        }

        public void setColumns1(List<String> columns1) {
            this.columns1 = columns1;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public class Datum {

            @SerializedName("Distance")
            @Expose
            private String distance;
            @SerializedName("Distance_color")
            @Expose
            private String distanceColor;
            @SerializedName("FeederId")
            @Expose
            private String feederId;
            @SerializedName("FeederId_color")
            @Expose
            private String feederIdColor;
            @SerializedName("KVLN")
            @Expose
            private String kvln;
            @SerializedName("KVLN_color")
            @Expose
            private String kVLNColor;
            @SerializedName("LGampKmax")
            @Expose
            private String lGampKmax;
            @SerializedName("LGampKmaxZ")
            @Expose
            private String lGampKmaxZ;
            @SerializedName("LGampKmaxZ_color")
            @Expose
            private String lGampKmaxZColor;
            @SerializedName("LGampKmax_color")
            @Expose
            private String lGampKmaxColor;
            @SerializedName("LGampKmin")
            @Expose
            private String lGampKmin;
            @SerializedName("LGampKminZ")
            @Expose
            private String lGampKminZ;
            @SerializedName("LGampKminZ_color")
            @Expose
            private String lGampKminZColor;
            @SerializedName("LGampKmin_color")
            @Expose
            private String lGampKminColor;
            @SerializedName("LGampMax")
            @Expose
            private String lGampMax;
            @SerializedName("LGampMax_color")
            @Expose
            private String lGampMaxColor;
            @SerializedName("LGampMin")
            @Expose
            private String lGampMin;
            @SerializedName("LGampMin_color")
            @Expose
            private String lGampMinColor;
            @SerializedName("LLGamp")
            @Expose
            private String lLGamp;
            @SerializedName("LLGampKmax")
            @Expose
            private String lLGampKmax;
            @SerializedName("LLGampKmax_color")
            @Expose
            private String lLGampKmaxColor;
            @SerializedName("LLGampKmin")
            @Expose
            private String lLGampKmin;
            @SerializedName("LLGampKmin_color")
            @Expose
            private String lLGampKminColor;
            @SerializedName("LLGamp_color")
            @Expose
            private String lLGampColor;
            @SerializedName("LLLamp")
            @Expose
            private String lLLamp;
            @SerializedName("LLLampKmax")
            @Expose
            private String lLLampKmax;
            @SerializedName("LLLampKmaxZ")
            @Expose
            private String lLLampKmaxZ;
            @SerializedName("LLLampKmaxZ_color")
            @Expose
            private String lLLampKmaxZColor;
            @SerializedName("LLLampKmax_color")
            @Expose
            private String lLLampKmaxColor;
            @SerializedName("LLLampKmin")
            @Expose
            private String lLLampKmin;
            @SerializedName("LLLampKminZ")
            @Expose
            private String lLLampKminZ;
            @SerializedName("LLLampKminZ_color")
            @Expose
            private String lLLampKminZColor;
            @SerializedName("LLLampKmin_color")
            @Expose
            private String lLLampKminColor;
            @SerializedName("LLLamp_color")
            @Expose
            private String lLLampColor;
            @SerializedName("LLamp")
            @Expose
            private String lLamp;
            @SerializedName("LLampKmax")
            @Expose
            private String lLampKmax;
            @SerializedName("LLampKmaxZ")
            @Expose
            private String lLampKmaxZ;
            @SerializedName("LLampKmaxZ_color")
            @Expose
            private String lLampKmaxZColor;
            @SerializedName("LLampKmax_color")
            @Expose
            private String lLampKmaxColor;
            @SerializedName("LLampKmin")
            @Expose
            private String lLampKmin;
            @SerializedName("LLampKminZ")
            @Expose
            private String lLampKminZ;
            @SerializedName("LLampKminZ_color")
            @Expose
            private String lLampKminZColor;
            @SerializedName("LLampKmin_color")
            @Expose
            private String lLampKminColor;
            @SerializedName("LLampMin")
            @Expose
            private String lLampMin;
            @SerializedName("LLampMin_color")
            @Expose
            private String lLampMinColor;
            @SerializedName("LLamp_color")
            @Expose
            private String lLampColor;
            @SerializedName("NodeId")
            @Expose
            private String nodeId;
            @SerializedName("NodeId_color")
            @Expose
            private String nodeIdColor;
            @SerializedName("Phase")
            @Expose
            private String phase;
            @SerializedName("Phase_color")
            @Expose
            private String phaseColor;

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getDistanceColor() {
                return distanceColor;
            }

            public void setDistanceColor(String distanceColor) {
                this.distanceColor = distanceColor;
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

            public String getKvln() {
                return kvln;
            }

            public void setKvln(String kvln) {
                this.kvln = kvln;
            }

            public String getKVLNColor() {
                return kVLNColor;
            }

            public void setKVLNColor(String kVLNColor) {
                this.kVLNColor = kVLNColor;
            }

            public String getLGampKmax() {
                return lGampKmax;
            }

            public void setLGampKmax(String lGampKmax) {
                this.lGampKmax = lGampKmax;
            }

            public String getLGampKmaxZ() {
                return lGampKmaxZ;
            }

            public void setLGampKmaxZ(String lGampKmaxZ) {
                this.lGampKmaxZ = lGampKmaxZ;
            }

            public String getLGampKmaxZColor() {
                return lGampKmaxZColor;
            }

            public void setLGampKmaxZColor(String lGampKmaxZColor) {
                this.lGampKmaxZColor = lGampKmaxZColor;
            }

            public String getLGampKmaxColor() {
                return lGampKmaxColor;
            }

            public void setLGampKmaxColor(String lGampKmaxColor) {
                this.lGampKmaxColor = lGampKmaxColor;
            }

            public String getLGampKmin() {
                return lGampKmin;
            }

            public void setLGampKmin(String lGampKmin) {
                this.lGampKmin = lGampKmin;
            }

            public String getLGampKminZ() {
                return lGampKminZ;
            }

            public void setLGampKminZ(String lGampKminZ) {
                this.lGampKminZ = lGampKminZ;
            }

            public String getLGampKminZColor() {
                return lGampKminZColor;
            }

            public void setLGampKminZColor(String lGampKminZColor) {
                this.lGampKminZColor = lGampKminZColor;
            }

            public String getLGampKminColor() {
                return lGampKminColor;
            }

            public void setLGampKminColor(String lGampKminColor) {
                this.lGampKminColor = lGampKminColor;
            }

            public String getLGampMax() {
                return lGampMax;
            }

            public void setLGampMax(String lGampMax) {
                this.lGampMax = lGampMax;
            }

            public String getLGampMaxColor() {
                return lGampMaxColor;
            }

            public void setLGampMaxColor(String lGampMaxColor) {
                this.lGampMaxColor = lGampMaxColor;
            }

            public String getLGampMin() {
                return lGampMin;
            }

            public void setLGampMin(String lGampMin) {
                this.lGampMin = lGampMin;
            }

            public String getLGampMinColor() {
                return lGampMinColor;
            }

            public void setLGampMinColor(String lGampMinColor) {
                this.lGampMinColor = lGampMinColor;
            }

            public String getLLGamp() {
                return lLGamp;
            }

            public void setLLGamp(String lLGamp) {
                this.lLGamp = lLGamp;
            }

            public String getLLGampKmax() {
                return lLGampKmax;
            }

            public void setLLGampKmax(String lLGampKmax) {
                this.lLGampKmax = lLGampKmax;
            }

            public String getLLGampKmaxColor() {
                return lLGampKmaxColor;
            }

            public void setLLGampKmaxColor(String lLGampKmaxColor) {
                this.lLGampKmaxColor = lLGampKmaxColor;
            }

            public String getLLGampKmin() {
                return lLGampKmin;
            }

            public void setLLGampKmin(String lLGampKmin) {
                this.lLGampKmin = lLGampKmin;
            }

            public String getLLGampKminColor() {
                return lLGampKminColor;
            }

            public void setLLGampKminColor(String lLGampKminColor) {
                this.lLGampKminColor = lLGampKminColor;
            }

            public String getLLGampColor() {
                return lLGampColor;
            }

            public void setLLGampColor(String lLGampColor) {
                this.lLGampColor = lLGampColor;
            }

            public String getLLLamp() {
                return lLLamp;
            }

            public void setLLLamp(String lLLamp) {
                this.lLLamp = lLLamp;
            }

            public String getLLLampKmax() {
                return lLLampKmax;
            }

            public void setLLLampKmax(String lLLampKmax) {
                this.lLLampKmax = lLLampKmax;
            }

            public String getLLLampKmaxZ() {
                return lLLampKmaxZ;
            }

            public void setLLLampKmaxZ(String lLLampKmaxZ) {
                this.lLLampKmaxZ = lLLampKmaxZ;
            }

            public String getLLLampKmaxZColor() {
                return lLLampKmaxZColor;
            }

            public void setLLLampKmaxZColor(String lLLampKmaxZColor) {
                this.lLLampKmaxZColor = lLLampKmaxZColor;
            }

            public String getLLLampKmaxColor() {
                return lLLampKmaxColor;
            }

            public void setLLLampKmaxColor(String lLLampKmaxColor) {
                this.lLLampKmaxColor = lLLampKmaxColor;
            }

            public String getLLLampKmin() {
                return lLLampKmin;
            }

            public void setLLLampKmin(String lLLampKmin) {
                this.lLLampKmin = lLLampKmin;
            }

            public String getLLLampKminZ() {
                return lLLampKminZ;
            }

            public void setLLLampKminZ(String lLLampKminZ) {
                this.lLLampKminZ = lLLampKminZ;
            }

            public String getLLLampKminZColor() {
                return lLLampKminZColor;
            }

            public void setLLLampKminZColor(String lLLampKminZColor) {
                this.lLLampKminZColor = lLLampKminZColor;
            }

            public String getLLLampKminColor() {
                return lLLampKminColor;
            }

            public void setLLLampKminColor(String lLLampKminColor) {
                this.lLLampKminColor = lLLampKminColor;
            }

            public String getLLLampColor() {
                return lLLampColor;
            }

            public void setLLLampColor(String lLLampColor) {
                this.lLLampColor = lLLampColor;
            }

            public String getLLamp() {
                return lLamp;
            }

            public void setLLamp(String lLamp) {
                this.lLamp = lLamp;
            }

            public String getLLampKmax() {
                return lLampKmax;
            }

            public void setLLampKmax(String lLampKmax) {
                this.lLampKmax = lLampKmax;
            }

            public String getLLampKmaxZ() {
                return lLampKmaxZ;
            }

            public void setLLampKmaxZ(String lLampKmaxZ) {
                this.lLampKmaxZ = lLampKmaxZ;
            }

            public String getLLampKmaxZColor() {
                return lLampKmaxZColor;
            }

            public void setLLampKmaxZColor(String lLampKmaxZColor) {
                this.lLampKmaxZColor = lLampKmaxZColor;
            }

            public String getLLampKmaxColor() {
                return lLampKmaxColor;
            }

            public void setLLampKmaxColor(String lLampKmaxColor) {
                this.lLampKmaxColor = lLampKmaxColor;
            }

            public String getLLampKmin() {
                return lLampKmin;
            }

            public void setLLampKmin(String lLampKmin) {
                this.lLampKmin = lLampKmin;
            }

            public String getLLampKminZ() {
                return lLampKminZ;
            }

            public void setLLampKminZ(String lLampKminZ) {
                this.lLampKminZ = lLampKminZ;
            }

            public String getLLampKminZColor() {
                return lLampKminZColor;
            }

            public void setLLampKminZColor(String lLampKminZColor) {
                this.lLampKminZColor = lLampKminZColor;
            }

            public String getLLampKminColor() {
                return lLampKminColor;
            }

            public void setLLampKminColor(String lLampKminColor) {
                this.lLampKminColor = lLampKminColor;
            }

            public String getLLampMin() {
                return lLampMin;
            }

            public void setLLampMin(String lLampMin) {
                this.lLampMin = lLampMin;
            }

            public String getLLampMinColor() {
                return lLampMinColor;
            }

            public void setLLampMinColor(String lLampMinColor) {
                this.lLampMinColor = lLampMinColor;
            }

            public String getLLampColor() {
                return lLampColor;
            }

            public void setLLampColor(String lLampColor) {
                this.lLampColor = lLampColor;
            }

            public String getNodeId() {
                return nodeId;
            }

            public void setNodeId(String nodeId) {
                this.nodeId = nodeId;
            }

            public String getNodeIdColor() {
                return nodeIdColor;
            }

            public void setNodeIdColor(String nodeIdColor) {
                this.nodeIdColor = nodeIdColor;
            }

            public String getPhase() {
                return phase;
            }

            public void setPhase(String phase) {
                this.phase = phase;
            }

            public String getPhaseColor() {
                return phaseColor;
            }

            public void setPhaseColor(String phaseColor) {
                this.phaseColor = phaseColor;
            }

        }

    }

}
