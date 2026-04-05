package com.techlabs.apdcl.models;

import com.google.gson.annotations.SerializedName;

public class ShuntReactorModel {

    @SerializedName("output")
    private Output output;

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public static class Output {

        @SerializedName("SectionId")
        private String sectionId;

        @SerializedName("DeviceNumber")
        private String deviceNumber;

        @SerializedName("EquipmentId")
        private String equipmentId;

        @SerializedName("NetworkId")
        private String networkId;

        @SerializedName("Phase")
        private Integer phase;

        @SerializedName("Status")
        private Integer status;

        @SerializedName("Location")
        private Integer location;

        @SerializedName("ZoneId")
        private Integer zoneId;

        @SerializedName("DeviceType")
        private Integer deviceType;

        @SerializedName("FromNodeId")
        private String fromNodeId;

        @SerializedName("ToNodeId")
        private String toNodeId;

        @SerializedName("RatedKVAR")
        private Double ratedKVAR;

        @SerializedName("KVLN")
        private Double kvln;

        @SerializedName("InterruptingRating_x")
        private Double interruptingRatingX;

        @SerializedName("LossesKW")
        private Double lossesKW;

        @SerializedName("KVARA")
        private Double kvarA;

        @SerializedName("KVARB")
        private Double kvarB;

        @SerializedName("KVARC")
        private Double kvarC;

        @SerializedName("LossesA")
        private Double lossesA;

        @SerializedName("LossesB")
        private Double lossesB;

        @SerializedName("LossesC")
        private Double lossesC;

        @SerializedName("DeviceStage")
        private Integer deviceStage;

        @SerializedName("ByPhase")
        private Integer byPhase;

        @SerializedName("DeviceTypeLine")
        private Integer deviceTypeLine;

        @SerializedName("LineDeviceNumber")
        private String lineDeviceNumber;

        // Getters and Setters

        public String getSectionId() { return sectionId; }
        public void setSectionId(String sectionId) { this.sectionId = sectionId; }

        public String getDeviceNumber() { return deviceNumber; }
        public void setDeviceNumber(String deviceNumber) { this.deviceNumber = deviceNumber; }

        public String getEquipmentId() { return equipmentId; }
        public void setEquipmentId(String equipmentId) { this.equipmentId = equipmentId; }

        public String getNetworkId() { return networkId; }
        public void setNetworkId(String networkId) { this.networkId = networkId; }

        public Integer getPhase() { return phase; }
        public void setPhase(Integer phase) { this.phase = phase; }

        public Integer getStatus() { return status; }
        public void setStatus(Integer status) { this.status = status; }

        public Integer getLocation() { return location; }
        public void setLocation(Integer location) { this.location = location; }

        public Integer getZoneId() { return zoneId; }
        public void setZoneId(Integer zoneId) { this.zoneId = zoneId; }

        public Integer getDeviceType() { return deviceType; }
        public void setDeviceType(Integer deviceType) { this.deviceType = deviceType; }

        public String getFromNodeId() { return fromNodeId; }
        public void setFromNodeId(String fromNodeId) { this.fromNodeId = fromNodeId; }

        public String getToNodeId() { return toNodeId; }
        public void setToNodeId(String toNodeId) { this.toNodeId = toNodeId; }

        public Double getRatedKVAR() { return ratedKVAR; }
        public void setRatedKVAR(Double ratedKVAR) { this.ratedKVAR = ratedKVAR; }

        public Double getKvln() { return kvln; }
        public void setKvln(Double kvln) { this.kvln = kvln; }

        public Double getInterruptingRatingX() { return interruptingRatingX; }
        public void setInterruptingRatingX(Double interruptingRatingX) { this.interruptingRatingX = interruptingRatingX; }

        public Double getLossesKW() { return lossesKW; }
        public void setLossesKW(Double lossesKW) { this.lossesKW = lossesKW; }

        public Double getKvarA() { return kvarA; }
        public void setKvarA(Double kvarA) { this.kvarA = kvarA; }

        public Double getKvarB() { return kvarB; }
        public void setKvarB(Double kvarB) { this.kvarB = kvarB; }

        public Double getKvarC() { return kvarC; }
        public void setKvarC(Double kvarC) { this.kvarC = kvarC; }

        public Double getLossesA() { return lossesA; }
        public void setLossesA(Double lossesA) { this.lossesA = lossesA; }

        public Double getLossesB() { return lossesB; }
        public void setLossesB(Double lossesB) { this.lossesB = lossesB; }

        public Double getLossesC() { return lossesC; }
        public void setLossesC(Double lossesC) { this.lossesC = lossesC; }

        public Integer getDeviceStage() { return deviceStage; }
        public void setDeviceStage(Integer deviceStage) { this.deviceStage = deviceStage; }

        public Integer getByPhase() { return byPhase; }
        public void setByPhase(Integer byPhase) { this.byPhase = byPhase; }

        public Integer getDeviceTypeLine() { return deviceTypeLine; }
        public void setDeviceTypeLine(Integer deviceTypeLine) { this.deviceTypeLine = deviceTypeLine; }

        public String getLineDeviceNumber() { return lineDeviceNumber; }
        public void setLineDeviceNumber(String lineDeviceNumber) { this.lineDeviceNumber = lineDeviceNumber; }
    }
}
