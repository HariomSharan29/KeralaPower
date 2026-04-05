package com.techlabs.apdcl.models.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransformerReport {

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
            @SerializedName("IAngle")
            @Expose
            private String iAngle;
            @SerializedName("IAngle_color")
            @Expose
            private String iAngleColor;
            @SerializedName("IBal")
            @Expose
            private String iBal;
            @SerializedName("IBal_color")
            @Expose
            private String iBalColor;
            @SerializedName("KVARLossTot")
            @Expose
            private String kVARLossTot;
            @SerializedName("KVARLossTot_color")
            @Expose
            private String kVARLossTotColor;
            @SerializedName("KVARTOT")
            @Expose
            private String kvartot;
            @SerializedName("KVARTOT_color")
            @Expose
            private String kVARTOTColor;
            @SerializedName("KVATOT")
            @Expose
            private String kvatot;
            @SerializedName("KVATOT_color")
            @Expose
            private String kVATOTColor;
            @SerializedName("KWLossTot")
            @Expose
            private String kWLossTot;
            @SerializedName("KWLossTot_color")
            @Expose
            private String kWLossTotColor;
            @SerializedName("KWTOT")
            @Expose
            private String kwtot;
            @SerializedName("KWTOT_color")
            @Expose
            private String kWTOTColor;
            @SerializedName("LOADING")
            @Expose
            private String loading;
            @SerializedName("LOADING_color")
            @Expose
            private String lOADINGColor;
            @SerializedName("PFavg")
            @Expose
            private String pFavg;
            @SerializedName("PFavg_color")
            @Expose
            private String pFavgColor;
            @SerializedName("ToNodeId")
            @Expose
            private String toNodeId;
            @SerializedName("ToNodeId_color")
            @Expose
            private String toNodeIdColor;
            @SerializedName("XfoBoost")
            @Expose
            private String xfoBoost;
            @SerializedName("XfoBoost_color")
            @Expose
            private String xfoBoostColor;
            @SerializedName("XfoBuck")
            @Expose
            private String xfoBuck;
            @SerializedName("XfoBuck_color")
            @Expose
            private String xfoBuckColor;
            @SerializedName("XfoKVANom")
            @Expose
            private String xfoKVANom;
            @SerializedName("XfoKVANom_color")
            @Expose
            private String xfoKVANomColor;
            @SerializedName("XfoKVLL1")
            @Expose
            private String xfoKVLL1;
            @SerializedName("XfoKVLL1_color")
            @Expose
            private String xfoKVLL1Color;
            @SerializedName("XfoKVLL2")
            @Expose
            private String xfoKVLL2;
            @SerializedName("XfoKVLL2_color")
            @Expose
            private String xfoKVLL2Color;
            @SerializedName("XfoRegId")
            @Expose
            private String xfoRegId;
            @SerializedName("XfoRegId_color")
            @Expose
            private String xfoRegIdColor;
            @SerializedName("XfoVset")
            @Expose
            private String xfoVset;
            @SerializedName("XfoVset_color")
            @Expose
            private String xfoVsetColor;

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

            public String getIAngle() {
                return iAngle;
            }

            public void setIAngle(String iAngle) {
                this.iAngle = iAngle;
            }

            public String getIAngleColor() {
                return iAngleColor;
            }

            public void setIAngleColor(String iAngleColor) {
                this.iAngleColor = iAngleColor;
            }

            public String getIBal() {
                return iBal;
            }

            public void setIBal(String iBal) {
                this.iBal = iBal;
            }

            public String getIBalColor() {
                return iBalColor;
            }

            public void setIBalColor(String iBalColor) {
                this.iBalColor = iBalColor;
            }

            public String getKVARLossTot() {
                return kVARLossTot;
            }

            public void setKVARLossTot(String kVARLossTot) {
                this.kVARLossTot = kVARLossTot;
            }

            public String getKVARLossTotColor() {
                return kVARLossTotColor;
            }

            public void setKVARLossTotColor(String kVARLossTotColor) {
                this.kVARLossTotColor = kVARLossTotColor;
            }

            public String getKvartot() {
                return kvartot;
            }

            public void setKvartot(String kvartot) {
                this.kvartot = kvartot;
            }

            public String getKVARTOTColor() {
                return kVARTOTColor;
            }

            public void setKVARTOTColor(String kVARTOTColor) {
                this.kVARTOTColor = kVARTOTColor;
            }

            public String getKvatot() {
                return kvatot;
            }

            public void setKvatot(String kvatot) {
                this.kvatot = kvatot;
            }

            public String getKVATOTColor() {
                return kVATOTColor;
            }

            public void setKVATOTColor(String kVATOTColor) {
                this.kVATOTColor = kVATOTColor;
            }

            public String getKWLossTot() {
                return kWLossTot;
            }

            public void setKWLossTot(String kWLossTot) {
                this.kWLossTot = kWLossTot;
            }

            public String getKWLossTotColor() {
                return kWLossTotColor;
            }

            public void setKWLossTotColor(String kWLossTotColor) {
                this.kWLossTotColor = kWLossTotColor;
            }

            public String getKwtot() {
                return kwtot;
            }

            public void setKwtot(String kwtot) {
                this.kwtot = kwtot;
            }

            public String getKWTOTColor() {
                return kWTOTColor;
            }

            public void setKWTOTColor(String kWTOTColor) {
                this.kWTOTColor = kWTOTColor;
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

            public String getPFavg() {
                return pFavg;
            }

            public void setPFavg(String pFavg) {
                this.pFavg = pFavg;
            }

            public String getPFavgColor() {
                return pFavgColor;
            }

            public void setPFavgColor(String pFavgColor) {
                this.pFavgColor = pFavgColor;
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

            public String getXfoBoost() {
                return xfoBoost;
            }

            public void setXfoBoost(String xfoBoost) {
                this.xfoBoost = xfoBoost;
            }

            public String getXfoBoostColor() {
                return xfoBoostColor;
            }

            public void setXfoBoostColor(String xfoBoostColor) {
                this.xfoBoostColor = xfoBoostColor;
            }

            public String getXfoBuck() {
                return xfoBuck;
            }

            public void setXfoBuck(String xfoBuck) {
                this.xfoBuck = xfoBuck;
            }

            public String getXfoBuckColor() {
                return xfoBuckColor;
            }

            public void setXfoBuckColor(String xfoBuckColor) {
                this.xfoBuckColor = xfoBuckColor;
            }

            public String getXfoKVANom() {
                return xfoKVANom;
            }

            public void setXfoKVANom(String xfoKVANom) {
                this.xfoKVANom = xfoKVANom;
            }

            public String getXfoKVANomColor() {
                return xfoKVANomColor;
            }

            public void setXfoKVANomColor(String xfoKVANomColor) {
                this.xfoKVANomColor = xfoKVANomColor;
            }

            public String getXfoKVLL1() {
                return xfoKVLL1;
            }

            public void setXfoKVLL1(String xfoKVLL1) {
                this.xfoKVLL1 = xfoKVLL1;
            }

            public String getXfoKVLL1Color() {
                return xfoKVLL1Color;
            }

            public void setXfoKVLL1Color(String xfoKVLL1Color) {
                this.xfoKVLL1Color = xfoKVLL1Color;
            }

            public String getXfoKVLL2() {
                return xfoKVLL2;
            }

            public void setXfoKVLL2(String xfoKVLL2) {
                this.xfoKVLL2 = xfoKVLL2;
            }

            public String getXfoKVLL2Color() {
                return xfoKVLL2Color;
            }

            public void setXfoKVLL2Color(String xfoKVLL2Color) {
                this.xfoKVLL2Color = xfoKVLL2Color;
            }

            public String getXfoRegId() {
                return xfoRegId;
            }

            public void setXfoRegId(String xfoRegId) {
                this.xfoRegId = xfoRegId;
            }

            public String getXfoRegIdColor() {
                return xfoRegIdColor;
            }

            public void setXfoRegIdColor(String xfoRegIdColor) {
                this.xfoRegIdColor = xfoRegIdColor;
            }

            public String getXfoVset() {
                return xfoVset;
            }

            public void setXfoVset(String xfoVset) {
                this.xfoVset = xfoVset;
            }

            public String getXfoVsetColor() {
                return xfoVsetColor;
            }

            public void setXfoVsetColor(String xfoVsetColor) {
                this.xfoVsetColor = xfoVsetColor;
            }

        }

    }

}
