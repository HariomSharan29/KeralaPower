package com.techlabs.apdcl.models.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OverLoadTransformerReport {

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

            @SerializedName("EqNo")
            @Expose
            private String eqNo;
            @SerializedName("EqNo_color")
            @Expose
            private String eqNoColor;
            @SerializedName("FromNodeId")
            @Expose
            private String fromNodeId;
            @SerializedName("FromNodeId_color")
            @Expose
            private String fromNodeIdColor;
            @SerializedName("LOADING")
            @Expose
            private String loading;
            @SerializedName("LOADING_color")
            @Expose
            private String lOADINGColor;
            @SerializedName("MVATOT")
            @Expose
            private String mvatot;
            @SerializedName("MVATOT_color")
            @Expose
            private String mVATOTColor;
            @SerializedName("ToNodeId")
            @Expose
            private String toNodeId;
            @SerializedName("ToNodeId_color")
            @Expose
            private String toNodeIdColor;

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

            public String getFromNodeId() {
                return fromNodeId;
            }

            public void setFromNodeId(String fromNodeId) {
                this.fromNodeId = fromNodeId;
            }

            public String getFromNodeIdColor() {
                return fromNodeIdColor;
            }

            public void setFromNodeIdColor(String fromNodeIdColor) {
                this.fromNodeIdColor = fromNodeIdColor;
            }

            public String getLoading() {
                return loading;
            }

            public void setLoading(String loading) {
                this.loading = loading;
            }

            public String getLOADINGColor() {
                return lOADINGColor;
            }

            public void setLOADINGColor(String lOADINGColor) {
                this.lOADINGColor = lOADINGColor;
            }

            public String getMvatot() {
                return mvatot;
            }

            public void setMvatot(String mvatot) {
                this.mvatot = mvatot;
            }

            public String getMVATOTColor() {
                return mVATOTColor;
            }

            public void setMVATOTColor(String mVATOTColor) {
                this.mVATOTColor = mVATOTColor;
            }

            public String getToNodeId() {
                return toNodeId;
            }

            public void setToNodeId(String toNodeId) {
                this.toNodeId = toNodeId;
            }

            public String getToNodeIdColor() {
                return toNodeIdColor;
            }

            public void setToNodeIdColor(String toNodeIdColor) {
                this.toNodeIdColor = toNodeIdColor;
            }

        }

    }

}