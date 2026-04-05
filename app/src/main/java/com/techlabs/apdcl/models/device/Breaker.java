package com.techlabs.apdcl.models.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Breaker {

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

        @SerializedName("DeviceNumber")
        @Expose
        private String deviceNumber;
        @SerializedName("DeviceType")
        @Expose
        private String deviceType;
        @SerializedName("SectionId")
        @Expose
        private String sectionId;
        @SerializedName("NetworkId")
        @Expose
        private String networkId;
        @SerializedName("Location")
        @Expose
        private String location;
        @SerializedName("EquipmentId")
        @Expose
        private String equipmentId;
        @SerializedName("ClosedPhase")
        @Expose
        private String closedPhase;
        @SerializedName("ComponentMask")
        @Expose
        private String componentMask;
        @SerializedName("RatedCurrent")
        @Expose
        private String ratedCurrent;
        @SerializedName("FirstRatedCurrent")
        @Expose
        private String firstRatedCurrent;
        @SerializedName("SecondRatedCurrent")
        @Expose
        private String secondRatedCurrent;
        @SerializedName("ThirdRatedCurrent")
        @Expose
        private String thirdRatedCurrent;
        @SerializedName("FourthRatedCurrent")
        @Expose
        private String fourthRatedCurrent;
        @SerializedName("RatedVoltage")
        @Expose
        private String ratedVoltage;
        @SerializedName("Reversible")
        @Expose
        private String reversible;
        @SerializedName("SinglePhaseLocking")
        @Expose
        private String singlePhaseLocking;
        @SerializedName("SinglePhaseTripping")
        @Expose
        private String singlePhaseTripping;
        @SerializedName("InterruptingRating")
        @Expose
        private String interruptingRating;
        @SerializedName("RemoteControlled")
        @Expose
        private String remoteControlled;
        @SerializedName("MeterIndex")
        @Expose
        private String meterIndex;
        @SerializedName("ReferenceTime")
        @Expose
        private String referenceTime;
        @SerializedName("Automated")
        @Expose
        private String automated;
        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("Manufacturer")
        @Expose
        private String manufacturer;
        @SerializedName("Model")
        @Expose
        private String model;
        @SerializedName("ANSIMaxRatedVoltage")
        @Expose
        private String aNSIMaxRatedVoltage;
        @SerializedName("ANSIRatedRangeKFactor")
        @Expose
        private String aNSIRatedRangeKFactor;
        @SerializedName("ANSIMaxSymetricalRMS")
        @Expose
        private String aNSIMaxSymetricalRMS;
        @SerializedName("ANSIClosingLatchingRMS")
        @Expose
        private String aNSIClosingLatchingRMS;
        @SerializedName("ANSIClosingLatchingCrest")
        @Expose
        private String aNSIClosingLatchingCrest;
        @SerializedName("IECMakingCurrent")
        @Expose
        private String iECMakingCurrent;
        @SerializedName("InterruptingTime")
        @Expose
        private String interruptingTime;
        @SerializedName("Favorite")
        @Expose
        private String favorite;
        @SerializedName("ModifiedByUser")
        @Expose
        private String modifiedByUser;
        @SerializedName("Flags")
        @Expose
        private String flags;
        @SerializedName("LastChange")
        @Expose
        private String lastChange;
        @SerializedName("Comments")
        @Expose
        private String comments;
        @SerializedName("Phase")
        @Expose
        private String phase;
        @SerializedName("ZoneId")
        @Expose
        private String zoneId;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("IsTotalDemand")
        @Expose
        private String isTotalDemand;
        @SerializedName("DemandType")
        @Expose
        private String demandType;
        @SerializedName("Val1Total")
        @Expose
        private String val1Total;
        @SerializedName("Val2Total")
        @Expose
        private String val2Total;
        @SerializedName("Val1A")
        @Expose
        private String val1A;
        @SerializedName("Val2A")
        @Expose
        private String val2A;
        @SerializedName("Val1B")
        @Expose
        private String val1B;
        @SerializedName("Val2B")
        @Expose
        private String val2B;
        @SerializedName("Val1C")
        @Expose
        private String val1C;
        @SerializedName("Val2C")
        @Expose
        private String val2C;
        @SerializedName("DisconnectedPhase")
        @Expose
        private String disconnectedPhase;
        @SerializedName("FromNodeId")
        @Expose
        private String fromNodeId;
        @SerializedName("FromNode_X")
        @Expose
        private String fromNodeX;
        @SerializedName("FromNode_Y")
        @Expose
        private String fromNodeY;
        @SerializedName("ToNodeId")
        @Expose
        private String toNodeId;
        @SerializedName("ToNode_X")
        @Expose
        private String toNodeX;
        @SerializedName("ToNode_Y")
        @Expose
        private String toNodeY;
        @SerializedName("DeviceTypeLine")
        @Expose
        private String deviceTypeLine;
        @SerializedName("LineDeviceNumber")
        @Expose
        private String lineDeviceNumber;

        public String getDeviceNumber() {
            return deviceNumber;
        }

        public void setDeviceNumber(String deviceNumber) {
            this.deviceNumber = deviceNumber;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getSectionId() {
            return sectionId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getNetworkId() {
            return networkId;
        }

        public void setNetworkId(String networkId) {
            this.networkId = networkId;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(String equipmentId) {
            this.equipmentId = equipmentId;
        }

        public String getClosedPhase() {
            return closedPhase;
        }

        public void setClosedPhase(String closedPhase) {
            this.closedPhase = closedPhase;
        }

        public String getComponentMask() {
            return componentMask;
        }

        public void setComponentMask(String componentMask) {
            this.componentMask = componentMask;
        }

        public String getRatedCurrent() {
            return ratedCurrent;
        }

        public void setRatedCurrent(String ratedCurrent) {
            this.ratedCurrent = ratedCurrent;
        }

        public String getFirstRatedCurrent() {
            return firstRatedCurrent;
        }

        public void setFirstRatedCurrent(String firstRatedCurrent) {
            this.firstRatedCurrent = firstRatedCurrent;
        }

        public String getSecondRatedCurrent() {
            return secondRatedCurrent;
        }

        public void setSecondRatedCurrent(String secondRatedCurrent) {
            this.secondRatedCurrent = secondRatedCurrent;
        }

        public String getThirdRatedCurrent() {
            return thirdRatedCurrent;
        }

        public void setThirdRatedCurrent(String thirdRatedCurrent) {
            this.thirdRatedCurrent = thirdRatedCurrent;
        }

        public String getFourthRatedCurrent() {
            return fourthRatedCurrent;
        }

        public void setFourthRatedCurrent(String fourthRatedCurrent) {
            this.fourthRatedCurrent = fourthRatedCurrent;
        }

        public String getRatedVoltage() {
            return ratedVoltage;
        }

        public void setRatedVoltage(String ratedVoltage) {
            this.ratedVoltage = ratedVoltage;
        }

        public String getReversible() {
            return reversible;
        }

        public void setReversible(String reversible) {
            this.reversible = reversible;
        }

        public String getSinglePhaseLocking() {
            return singlePhaseLocking;
        }

        public void setSinglePhaseLocking(String singlePhaseLocking) {
            this.singlePhaseLocking = singlePhaseLocking;
        }

        public String getSinglePhaseTripping() {
            return singlePhaseTripping;
        }

        public void setSinglePhaseTripping(String singlePhaseTripping) {
            this.singlePhaseTripping = singlePhaseTripping;
        }

        public String getInterruptingRating() {
            return interruptingRating;
        }

        public void setInterruptingRating(String interruptingRating) {
            this.interruptingRating = interruptingRating;
        }

        public String getRemoteControlled() {
            return remoteControlled;
        }

        public void setRemoteControlled(String remoteControlled) {
            this.remoteControlled = remoteControlled;
        }

        public String getMeterIndex() {
            return meterIndex;
        }

        public void setMeterIndex(String meterIndex) {
            this.meterIndex = meterIndex;
        }

        public String getReferenceTime() {
            return referenceTime;
        }

        public void setReferenceTime(String referenceTime) {
            this.referenceTime = referenceTime;
        }

        public String getAutomated() {
            return automated;
        }

        public void setAutomated(String automated) {
            this.automated = automated;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getANSIMaxRatedVoltage() {
            return aNSIMaxRatedVoltage;
        }

        public void setANSIMaxRatedVoltage(String aNSIMaxRatedVoltage) {
            this.aNSIMaxRatedVoltage = aNSIMaxRatedVoltage;
        }

        public String getANSIRatedRangeKFactor() {
            return aNSIRatedRangeKFactor;
        }

        public void setANSIRatedRangeKFactor(String aNSIRatedRangeKFactor) {
            this.aNSIRatedRangeKFactor = aNSIRatedRangeKFactor;
        }

        public String getANSIMaxSymetricalRMS() {
            return aNSIMaxSymetricalRMS;
        }

        public void setANSIMaxSymetricalRMS(String aNSIMaxSymetricalRMS) {
            this.aNSIMaxSymetricalRMS = aNSIMaxSymetricalRMS;
        }

        public String getANSIClosingLatchingRMS() {
            return aNSIClosingLatchingRMS;
        }

        public void setANSIClosingLatchingRMS(String aNSIClosingLatchingRMS) {
            this.aNSIClosingLatchingRMS = aNSIClosingLatchingRMS;
        }

        public String getANSIClosingLatchingCrest() {
            return aNSIClosingLatchingCrest;
        }

        public void setANSIClosingLatchingCrest(String aNSIClosingLatchingCrest) {
            this.aNSIClosingLatchingCrest = aNSIClosingLatchingCrest;
        }

        public String getIECMakingCurrent() {
            return iECMakingCurrent;
        }

        public void setIECMakingCurrent(String iECMakingCurrent) {
            this.iECMakingCurrent = iECMakingCurrent;
        }

        public String getInterruptingTime() {
            return interruptingTime;
        }

        public void setInterruptingTime(String interruptingTime) {
            this.interruptingTime = interruptingTime;
        }

        public String getFavorite() {
            return favorite;
        }

        public void setFavorite(String favorite) {
            this.favorite = favorite;
        }

        public String getModifiedByUser() {
            return modifiedByUser;
        }

        public void setModifiedByUser(String modifiedByUser) {
            this.modifiedByUser = modifiedByUser;
        }

        public String getFlags() {
            return flags;
        }

        public void setFlags(String flags) {
            this.flags = flags;
        }

        public String getLastChange() {
            return lastChange;
        }

        public void setLastChange(String lastChange) {
            this.lastChange = lastChange;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }

        public Object getZoneId() {
            return zoneId;
        }

        public void setZoneId(String zoneId) {
            this.zoneId = zoneId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsTotalDemand() {
            return isTotalDemand;
        }

        public void setIsTotalDemand(String isTotalDemand) {
            this.isTotalDemand = isTotalDemand;
        }

        public String getDemandType() {
            return demandType;
        }

        public void setDemandType(String demandType) {
            this.demandType = demandType;
        }

        public Object getVal1Total() {
            return val1Total;
        }

        public void setVal1Total(String val1Total) {
            this.val1Total = val1Total;
        }

        public String getVal2Total() {
            return val2Total;
        }

        public void setVal2Total(String val2Total) {
            this.val2Total = val2Total;
        }

        public String getVal1A() {
            return val1A;
        }

        public void setVal1A(String val1A) {
            this.val1A = val1A;
        }

        public String getVal2A() {
            return val2A;
        }

        public void setVal2A(String val2A) {
            this.val2A = val2A;
        }

        public String getVal1B() {
            return val1B;
        }

        public void setVal1B(String val1B) {
            this.val1B = val1B;
        }

        public String getVal2B() {
            return val2B;
        }

        public void setVal2B(String val2B) {
            this.val2B = val2B;
        }

        public String getVal1C() {
            return val1C;
        }

        public void setVal1C(String val1C) {
            this.val1C = val1C;
        }

        public Object getVal2C() {
            return val2C;
        }

        public void setVal2C(String val2C) {
            this.val2C = val2C;
        }

        public String getDisconnectedPhase() {
            return disconnectedPhase;
        }

        public void setDisconnectedPhase(String disconnectedPhase) {
            this.disconnectedPhase = disconnectedPhase;
        }

        public String getFromNodeId() {
            return fromNodeId;
        }

        public void setFromNodeId(String fromNodeId) {
            this.fromNodeId = fromNodeId;
        }

        public String getFromNodeX() {
            return fromNodeX;
        }

        public void setFromNodeX(String fromNodeX) {
            this.fromNodeX = fromNodeX;
        }

        public String getFromNodeY() {
            return fromNodeY;
        }

        public void setFromNodeY(String fromNodeY) {
            this.fromNodeY = fromNodeY;
        }

        public String getToNodeId() {
            return toNodeId;
        }

        public void setToNodeId(String toNodeId) {
            this.toNodeId = toNodeId;
        }

        public String getToNodeX() {
            return toNodeX;
        }

        public void setToNodeX(String toNodeX) {
            this.toNodeX = toNodeX;
        }

        public String getToNodeY() {
            return toNodeY;
        }

        public void setToNodeY(String toNodeY) {
            this.toNodeY = toNodeY;
        }

        public String getDeviceTypeLine() {
            return deviceTypeLine;
        }

        public void setDeviceTypeLine(String deviceTypeLine) {
            this.deviceTypeLine = deviceTypeLine;
        }

        public String getLineDeviceNumber() {
            return lineDeviceNumber;
        }

        public void setLineDeviceNumber(String lineDeviceNumber) {
            this.lineDeviceNumber = lineDeviceNumber;
        }

    }

}
