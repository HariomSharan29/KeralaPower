package com.techlabs.apdcl.models.device;

import com.google.gson.annotations.SerializedName;

public class Wind {

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

        @SerializedName("NetworkId")
        private String networkId;

        @SerializedName("EquipmentId")
        private String equipmentId;

        @SerializedName("SymbolSize")
        private Double symbolSize;

        @SerializedName("ConstantWindSpeed")
        private Integer constantWindSpeed;

        @SerializedName("ForceT0")
        private Integer forceT0;

        @SerializedName("WindModelId")
        private String windModelId;

        @SerializedName("Status")
        private Integer status;

        @SerializedName("ConnectionConfiguration")
        private Integer connectionConfiguration;

        @SerializedName("Phase")
        private Integer phase;

        @SerializedName("CTConnection")
        private Integer ctConnection;

        @SerializedName("UseActivePowerControls")
        private Integer useActivePowerControls;

        @SerializedName("UseReactivePowerControls")
        private Integer useReactivePowerControls;

        @SerializedName("DeviceType")
        private Integer deviceType;

        @SerializedName("SectionId")
        private String sectionId;

        @SerializedName("Location")
        private Integer location;

        @SerializedName("FromNodeId")
        private String fromNodeId;

        @SerializedName("ToNodeId")
        private String toNodeId;

        @SerializedName("DeviceStage")
        private Object deviceStage;

        @SerializedName("DeviceTypeLine")
        private Integer deviceTypeLine;

        @SerializedName("LineDeviceNumber")
        private String lineDeviceNumber;

        @SerializedName("RatedPower_KW")
        private Double ratedPowerKw;

        @SerializedName("GeneratorPowerFactor_Percent")
        private Double generatorPowerFactorPercent;

        public String getDeviceNumber() { return deviceNumber; }
        public String getNetworkId() { return networkId; }
        public String getEquipmentId() { return equipmentId; }
        public Double getSymbolSize() { return symbolSize; }
        public Integer getConstantWindSpeed() { return constantWindSpeed; }
        public Integer getForceT0() { return forceT0; }
        public String getWindModelId() { return windModelId; }
        public Integer getStatus() { return status; }
        public Integer getConnectionConfiguration() { return connectionConfiguration; }
        public Integer getPhase() { return phase; }
        public Integer getCtConnection() { return ctConnection; }
        public Integer getUseActivePowerControls() { return useActivePowerControls; }
        public Integer getUseReactivePowerControls() { return useReactivePowerControls; }
        public Integer getDeviceType() { return deviceType; }
        public String getSectionId() { return sectionId; }
        public Integer getLocation() { return location; }
        public String getFromNodeId() { return fromNodeId; }
        public String getToNodeId() { return toNodeId; }
        public Object getDeviceStage() { return deviceStage; }
        public Integer getDeviceTypeLine() { return deviceTypeLine; }
        public String getLineDeviceNumber() { return lineDeviceNumber; }
        public Double getRatedPowerKw() { return ratedPowerKw; }
        public Double getGeneratorPowerFactorPercent() { return generatorPowerFactorPercent; }
    }
}
