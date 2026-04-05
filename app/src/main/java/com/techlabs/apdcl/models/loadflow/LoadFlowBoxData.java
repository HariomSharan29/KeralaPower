package com.techlabs.apdcl.models.loadflow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadFlowBoxData {

    @SerializedName("output")
    @Expose
    private List<Output> output;

    public List<Output> getOutput() {
        return output;
    }

    public void setOutput(List<Output> output) {
        this.output = output;
    }

    public class Output {

        @SerializedName("EqNo")
        @Expose
        private String eqNo;
        @SerializedName("IA")
        @Expose
        private String ia;
        @SerializedName("IA_color")
        @Expose
        private String iAColor;
        @SerializedName("IB")
        @Expose
        private String ib;
        @SerializedName("IB_color")
        @Expose
        private String iBColor;
        @SerializedName("IC")
        @Expose
        private String ic;
        @SerializedName("IC_color")
        @Expose
        private String iCColor;
        @SerializedName("KVAA")
        @Expose
        private String kvaa;
        @SerializedName("KVAA_color")
        @Expose
        private String kVAAColor;
        @SerializedName("KVAB")
        @Expose
        private String kvab;
        @SerializedName("KVAB_color")
        @Expose
        private String kVABColor;
        @SerializedName("KVAC")
        @Expose
        private String kvac;
        @SerializedName("KVAC_color")
        @Expose
        private String kVACColor;
        @SerializedName("KVARA")
        @Expose
        private String kvara;
        @SerializedName("KVARA_color")
        @Expose
        private String kVARAColor;
        @SerializedName("KVARB")
        @Expose
        private String kvarb;
        @SerializedName("KVARB_color")
        @Expose
        private String kVARBColor;
        @SerializedName("KVARC")
        @Expose
        private String kvarc;
        @SerializedName("KVARC_color")
        @Expose
        private String kVARCColor;
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
        @SerializedName("KVLLA")
        @Expose
        private String kvlla;
        @SerializedName("KVLLB")
        @Expose
        private String kvllb;
        @SerializedName("KVLLC")
        @Expose
        private String kvllc;
        @SerializedName("KVLNA")
        @Expose
        private String kvlna;
        @SerializedName("KVLNB")
        @Expose
        private String kvlnb;
        @SerializedName("KVLNC")
        @Expose
        private String kvlnc;
        @SerializedName("KWA")
        @Expose
        private String kwa;
        @SerializedName("KWA_color")
        @Expose
        private String kWAColor;
        @SerializedName("KWB")
        @Expose
        private String kwb;
        @SerializedName("KWB_color")
        @Expose
        private String kWBColor;
        @SerializedName("KWC")
        @Expose
        private String kwc;
        @SerializedName("KWC_color")
        @Expose
        private String kWCColor;
        @SerializedName("KWTOT")
        @Expose
        private String kwtot;
        @SerializedName("KWTOT_color")
        @Expose
        private String kWTOTColor;
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
        @SerializedName("kVLLA_color")
        @Expose
        private String kVLLAColor;
        @SerializedName("kVLLB_color")
        @Expose
        private String kVLLBColor;
        @SerializedName("kVLLC_color")
        @Expose
        private String kVLLCColor;
        @SerializedName("kVLNA_color")
        @Expose
        private String kVLNAColor;
        @SerializedName("kVLNB_color")
        @Expose
        private String kVLNBColor;
        @SerializedName("kVLNC_color")
        @Expose
        private String kVLNCColor;
        @SerializedName("A")
        @Expose
        private String a;
        @SerializedName("I")
        @Expose
        private String i;
        @SerializedName("I_color")
        @Expose
        private String iColor;
        @SerializedName("KVA")
        @Expose
        private String kva;
        @SerializedName("KVAR")
        @Expose
        private String kvar;
        @SerializedName("KVAR_color")
        @Expose
        private String kVARColor;
        @SerializedName("KVA_color")
        @Expose
        private String kVAColor;
        @SerializedName("KVLL")
        @Expose
        private String kvll;
        @SerializedName("KVLL_color")
        @Expose
        private String kVLLColor;
        @SerializedName("KVLN")
        @Expose
        private String kvln;
        @SerializedName("KVLN_color")
        @Expose
        private String kVLNColor;
        @SerializedName("KW")
        @Expose
        private String kw;
        @SerializedName("KW_color")
        @Expose
        private String kWColor;
        @SerializedName("VBase")
        @Expose
        private String vBase;
        @SerializedName("VBase_color")
        @Expose
        private String vBaseColor;
        @SerializedName("aDvAbs")
        @Expose
        private String aDvabs;
        @SerializedName("LOADING")
        @Expose
        private String loading;

        @SerializedName("aDvAbs_color")
        @Expose
        private String aDvAbsColor;
        @SerializedName("LOADING_color")
        @Expose
        private String LOADINGColor;

        public String getEqNo() {
            return eqNo;
        }

        public void setEqNo(String eqNo) {
            this.eqNo = eqNo;
        }

        public String getIa() {
            return ia;
        }

        public void setIa(String ia) {
            this.ia = ia;
        }

        public String getIAColor() {
            return iAColor;
        }

        public void setIAColor(String iAColor) {
            this.iAColor = iAColor;
        }

        public String getIb() {
            return ib;
        }

        public void setIb(String ib) {
            this.ib = ib;
        }

        public String getIBColor() {
            return iBColor;
        }

        public void setIBColor(String iBColor) {
            this.iBColor = iBColor;
        }

        public String getIc() {
            return ic;
        }

        public void setIc(String ic) {
            this.ic = ic;
        }

        public String getICColor() {
            return iCColor;
        }

        public void setICColor(String iCColor) {
            this.iCColor = iCColor;
        }

        public String getKvaa() {
            return kvaa;
        }

        public void setKvaa(String kvaa) {
            this.kvaa = kvaa;
        }

        public String getKVAAColor() {
            return kVAAColor;
        }

        public void setKVAAColor(String kVAAColor) {
            this.kVAAColor = kVAAColor;
        }

        public String getKvab() {
            return kvab;
        }

        public void setKvab(String kvab) {
            this.kvab = kvab;
        }

        public String getKVABColor() {
            return kVABColor;
        }

        public void setKVABColor(String kVABColor) {
            this.kVABColor = kVABColor;
        }

        public String getKvac() {
            return kvac;
        }

        public void setKvac(String kvac) {
            this.kvac = kvac;
        }

        public String getKVACColor() {
            return kVACColor;
        }

        public void setKVACColor(String kVACColor) {
            this.kVACColor = kVACColor;
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

        public String getKvarb() {
            return kvarb;
        }

        public void setKvarb(String kvarb) {
            this.kvarb = kvarb;
        }

        public String getKVARBColor() {
            return kVARBColor;
        }

        public void setKVARBColor(String kVARBColor) {
            this.kVARBColor = kVARBColor;
        }

        public String getKvarc() {
            return kvarc;
        }

        public void setKvarc(String kvarc) {
            this.kvarc = kvarc;
        }

        public String getKVARCColor() {
            return kVARCColor;
        }

        public void setKVARCColor(String kVARCColor) {
            this.kVARCColor = kVARCColor;
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

        public String getKvlla() {
            return kvlla;
        }

        public void setKvlla(String kvlla) {
            this.kvlla = kvlla;
        }

        public String getKvllb() {
            return kvllb;
        }

        public void setKvllb(String kvllb) {
            this.kvllb = kvllb;
        }

        public String getKvllc() {
            return kvllc;
        }

        public void setKvllc(String kvllc) {
            this.kvllc = kvllc;
        }

        public String getKvlna() {
            return kvlna;
        }

        public void setKvlna(String kvlna) {
            this.kvlna = kvlna;
        }

        public String getKvlnb() {
            return kvlnb;
        }

        public void setKvlnb(String kvlnb) {
            this.kvlnb = kvlnb;
        }

        public String getKvlnc() {
            return kvlnc;
        }

        public void setKvlnc(String kvlnc) {
            this.kvlnc = kvlnc;
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

        public String getKwb() {
            return kwb;
        }

        public void setKwb(String kwb) {
            this.kwb = kwb;
        }

        public String getKWBColor() {
            return kWBColor;
        }

        public void setKWBColor(String kWBColor) {
            this.kWBColor = kWBColor;
        }

        public String getKwc() {
            return kwc;
        }

        public void setKwc(String kwc) {
            this.kwc = kwc;
        }

        public String getKWCColor() {
            return kWCColor;
        }

        public void setKWCColor(String kWCColor) {
            this.kWCColor = kWCColor;
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

        public String getkVLLAColor() {
            return kVLLAColor;
        }

        public void setkVLLAColor(String kVLLAColor) {
            this.kVLLAColor = kVLLAColor;
        }

        public String getkVLLBColor() {
            return kVLLBColor;
        }

        public void setkVLLBColor(String kVLLBColor) {
            this.kVLLBColor = kVLLBColor;
        }

        public String getkVLLCColor() {
            return kVLLCColor;
        }

        public void setkVLLCColor(String kVLLCColor) {
            this.kVLLCColor = kVLLCColor;
        }

        public String getkVLNAColor() {
            return kVLNAColor;
        }

        public void setkVLNAColor(String kVLNAColor) {
            this.kVLNAColor = kVLNAColor;
        }

        public String getkVLNBColor() {
            return kVLNBColor;
        }

        public void setkVLNBColor(String kVLNBColor) {
            this.kVLNBColor = kVLNBColor;
        }

        public String getkVLNCColor() {
            return kVLNCColor;
        }

        public void setkVLNCColor(String kVLNCColor) {
            this.kVLNCColor = kVLNCColor;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getI() {
            return i;
        }

        public void setI(String i) {
            this.i = i;
        }

        public String getIColor() {
            return iColor;
        }

        public void setIColor(String iColor) {
            this.iColor = iColor;
        }

        public String getKva() {
            return kva;
        }

        public void setKva(String kva) {
            this.kva = kva;
        }

        public String getKvar() {
            return kvar;
        }

        public void setKvar(String kvar) {
            this.kvar = kvar;
        }

        public String getKVARColor() {
            return kVARColor;
        }

        public void setKVARColor(String kVARColor) {
            this.kVARColor = kVARColor;
        }

        public String getKVAColor() {
            return kVAColor;
        }

        public void setKVAColor(String kVAColor) {
            this.kVAColor = kVAColor;
        }

        public String getKvll() {
            return kvll;
        }

        public void setKvll(String kvll) {
            this.kvll = kvll;
        }

        public String getKVLLColor() {
            return kVLLColor;
        }

        public void setKVLLColor(String kVLLColor) {
            this.kVLLColor = kVLLColor;
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

        public String getKw() {
            return kw;
        }

        public void setKw(String kw) {
            this.kw = kw;
        }

        public String getKWColor() {
            return kWColor;
        }

        public void setKWColor(String kWColor) {
            this.kWColor = kWColor;
        }

        public String getVBase() {
            return vBase;
        }

        public void setVBase(String vBase) {
            this.vBase = vBase;
        }

        public String getVBaseColor() {
            return vBaseColor;
        }

        public void setVBaseColor(String vBaseColor) {
            this.vBaseColor = vBaseColor;
        }

        public String getaDvabs() {
            return aDvabs;
        }

        public void setaDvabs(String aDvabs) {
            this.aDvabs = aDvabs;
        }

        public String getLoading() {
            return loading;
        }

        public void setLoading(String loading) {
            this.loading = loading;
        }

        public String getaDvAbsColor() {
            return aDvAbsColor;
        }

        public void setaDvAbsColor(String aDvAbsColor) {
            this.aDvAbsColor = aDvAbsColor;
        }

        public String getLOADINGColor() {
            return LOADINGColor;
        }

        public void setLOADINGColor(String LOADINGColor) {
            this.LOADINGColor = LOADINGColor;
        }

    }

}


