package com.techlabs.apdcl.models.device;

import com.google.gson.annotations.SerializedName;

public class PhotoVoltaic {

    @SerializedName("output")
    private Output output;

    public Output getOutput() {
        return output;
    }

    public static class Output {

        @SerializedName("DeviceNumber")
        private String deviceNumber;

        @SerializedName("Networkid")
        private String networkId;

        @SerializedName("Phase")
        private Integer phase;

        @SerializedName("Status")
        private Integer status;

        @SerializedName("Ctconnection")
        private Integer ctconnection;

        @SerializedName("Useactivepowercontrols")
        private Integer useactivepowercontrols;

        @SerializedName("Symbolsize")
        private Double symbolsize;

        @SerializedName("Devicetype")
        private Integer devicetype;

        @SerializedName("SectionId")
        private String sectionId;

        @SerializedName("Location")
        private Integer location;

        @SerializedName("ConvDevicetype")
        private Integer convDevicetype;

        @SerializedName("Acdcconverterid")
        private String acdcconverterid;

        @SerializedName("Model")
        private String model;

        @SerializedName("Internalcouplingelement")
        private Integer internalcouplingelement;

        @SerializedName("Modelcontrol")
        private Integer modelcontrol;

        @SerializedName("Usedccapacitor")
        private Integer usedccapacitor;

        @SerializedName("Fromnodeid")
        private String fromnodeid;

        @SerializedName("FromnodeX")
        private Double fromnodeX;

        @SerializedName("FromnodeY")
        private Double fromnodeY;

        @SerializedName("Tonodeid")
        private String tonodeid;

        @SerializedName("TonodeX")
        private Double tonodeX;

        @SerializedName("TonodeY")
        private Double tonodeY;

        @SerializedName("DevicetypeLine")
        private Integer devicetypeLine;

        @SerializedName("DevicenumberLine")
        private String devicenumberLine;

        public String getDeviceNumber() {
            return deviceNumber;
        }

        public String getNetworkId() {
            return networkId;
        }

        public Integer getPhase() {
            return phase;
        }

        public Integer getStatus() {
            return status;
        }

        public Integer getCtconnection() {
            return ctconnection;
        }

        public Integer getUseactivepowercontrols() {
            return useactivepowercontrols;
        }

        public Double getSymbolsize() {
            return symbolsize;
        }

        public Integer getDevicetype() {
            return devicetype;
        }

        public String getSectionId() {
            return sectionId;
        }

        public Integer getLocation() {
            return location;
        }

        public Integer getConvDevicetype() {
            return convDevicetype;
        }

        public String getAcdcconverterid() {
            return acdcconverterid;
        }

        public String getModel() {
            return model;
        }

        public Integer getInternalcouplingelement() {
            return internalcouplingelement;
        }

        public Integer getModelcontrol() {
            return modelcontrol;
        }

        public Integer getUsedccapacitor() {
            return usedccapacitor;
        }

        public String getFromnodeid() {
            return fromnodeid;
        }

        public Double getFromnodeX() {
            return fromnodeX;
        }

        public Double getFromnodeY() {
            return fromnodeY;
        }

        public String getTonodeid() {
            return tonodeid;
        }

        public Double getTonodeX() {
            return tonodeX;
        }

        public Double getTonodeY() {
            return tonodeY;
        }

        public Integer getDevicetypeLine() {
            return devicetypeLine;
        }

        public String getDevicenumberLine() {
            return devicenumberLine;
        }
    }
}
