package com.techlabs.apdcl.models.analysis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AnalysisInformationModel {

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

        @SerializedName("A")
        @Expose
        private String a;
        @SerializedName("B")
        @Expose
        private String b;
        @SerializedName("C")
        @Expose
        private String c;
        @SerializedName("Total")
        @Expose
        private String total;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

    }

}
