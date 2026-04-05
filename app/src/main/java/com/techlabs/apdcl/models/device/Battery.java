package com.techlabs.apdcl.models.device;

import com.google.gson.annotations.SerializedName;

public class Battery {

    @SerializedName("output")
    private Output output;

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public static class Output {

        @SerializedName("DeviceNumber")
        private String deviceNumber;

        @SerializedName("Networkid")
        private String networkId;

        @SerializedName("EquipmentId")
        private String equipmentId;

        @SerializedName("phase")
        private Integer phase;

        @SerializedName("Phase")
        private Integer phaseValue;

        @SerializedName("connectionstatus")
        private Integer connectionStatus;

        @SerializedName("connectionconfiguration")
        private Integer connectionConfiguration;

        @SerializedName("ctconnection")
        private Integer ctConnection;

        @SerializedName("useactivepowercontrols")
        private Integer useActivePowerControls;

        @SerializedName("usereactivepowercontrols")
        private Integer useReactivePowerControls;

        @SerializedName("symbolsize")
        private Double symbolSize;

        @SerializedName("maximumsoc")
        private Double maximumSoc;

        @SerializedName("minimumsoc")
        private Double minimumSoc;

        @SerializedName("initialsoc")
        private Double initialSoc;

        @SerializedName("DeviceType")
        private Integer deviceType;

        @SerializedName("SectionId")
        private String sectionId;

        @SerializedName("Location")
        private Integer location;

        @SerializedName("DeviceStage")
        private Object deviceStage;

        @SerializedName("FromNodeId")
        private String fromNodeId;

        @SerializedName("ToNodeId")
        private String toNodeId;

        @SerializedName("DeviceTypeLine")
        private Integer deviceTypeLine;

        @SerializedName("LineDeviceNumber")
        private String lineDeviceNumber;

        public String getDeviceNumber() {
            return deviceNumber;
        }

        public String getNetworkId() {
            return networkId;
        }

        public String getEquipmentId() {
            return equipmentId;
        }

        public Integer getPhase() {
            return phase != null ? phase : phaseValue;
        }

        public Integer getConnectionStatus() {
            return connectionStatus;
        }

        public Integer getConnectionConfiguration() {
            return connectionConfiguration;
        }

        public Integer getCtConnection() {
            return ctConnection;
        }

        public Integer getUseActivePowerControls() {
            return useActivePowerControls;
        }

        public Integer getUseReactivePowerControls() {
            return useReactivePowerControls;
        }

        public Double getSymbolSize() {
            return symbolSize;
        }

        public Double getMaximumSoc() {
            return maximumSoc;
        }

        public Double getMinimumSoc() {
            return minimumSoc;
        }

        public Double getInitialSoc() {
            return initialSoc;
        }

        public Integer getDeviceType() {
            return deviceType;
        }

        public String getSectionId() {
            return sectionId;
        }

        public Integer getLocation() {
            return location;
        }

        public Object getDeviceStage() {
            return deviceStage;
        }

        public String getFromNodeId() {
            return fromNodeId;
        }

        public String getToNodeId() {
            return toNodeId;
        }

        public Integer getDeviceTypeLine() {
            return deviceTypeLine;
        }

        public String getLineDeviceNumber() {
            return lineDeviceNumber;
        }
    }
}
