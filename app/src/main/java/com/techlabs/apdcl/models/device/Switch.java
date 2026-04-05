package com.techlabs.apdcl.models.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Switch {

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
        private Integer deviceType;
        @SerializedName("SectionId")
        @Expose
        private String sectionId;
        @SerializedName("NetworkId")
        @Expose
        private String networkId;
        @SerializedName("Location")
        @Expose
        private Integer location;
        @SerializedName("EquipmentId")
        @Expose
        private String equipmentId;
        @SerializedName("ClosedPhase")
        @Expose
        private Integer closedPhase;
        @SerializedName("ComponentMask")
        @Expose
        private Object componentMask;
        @SerializedName("RatedCurrent")
        @Expose
        private Object ratedCurrent;
        @SerializedName("FirstRatedCurrent")
        @Expose
        private Object firstRatedCurrent;
        @SerializedName("SecondRatedCurrent")
        @Expose
        private Object secondRatedCurrent;
        @SerializedName("ThirdRatedCurrent")
        @Expose
        private Object thirdRatedCurrent;
        @SerializedName("FourthRatedCurrent")
        @Expose
        private Object fourthRatedCurrent;
        @SerializedName("RatedVoltage")
        @Expose
        private Object ratedVoltage;
        @SerializedName("Reversible")
        @Expose
        private Object reversible;
        @SerializedName("SinglePhaseLocking")
        @Expose
        private Object singlePhaseLocking;
        @SerializedName("RemoteControlled")
        @Expose
        private Integer remoteControlled;
        @SerializedName("Automated")
        @Expose
        private Integer automated;
        @SerializedName("Favorite")
        @Expose
        private Object favorite;
        @SerializedName("ModifiedByUser")
        @Expose
        private Object modifiedByUser;
        @SerializedName("Flags")
        @Expose
        private Object flags;
        @SerializedName("LastChange")
        @Expose
        private Object lastChange;
        @SerializedName("Comments")
        @Expose
        private Object comments;
        @SerializedName("Phase")
        @Expose
        private Integer phase;
        @SerializedName("ZoneId")
        @Expose
        private Object zoneId;
        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("IsTotalDemand")
        @Expose
        private Object isTotalDemand;
        @SerializedName("DemandType")
        @Expose
        private Object demandType;
        @SerializedName("Val1Total")
        @Expose
        private Object val1Total;
        @SerializedName("Val2Total")
        @Expose
        private Object val2Total;
        @SerializedName("Val1A")
        @Expose
        private Object val1A;
        @SerializedName("Val2A")
        @Expose
        private Object val2A;
        @SerializedName("Val1B")
        @Expose
        private Object val1B;
        @SerializedName("Val2B")
        @Expose
        private Object val2B;
        @SerializedName("Val1C")
        @Expose
        private Object val1C;
        @SerializedName("Val2C")
        @Expose
        private Object val2C;
        @SerializedName("DisconnectedPhase")
        @Expose
        private Object disconnectedPhase;
        @SerializedName("FromNodeId")
        @Expose
        private String fromNodeId;
        @SerializedName("FromNode_X")
        @Expose
        private Float fromNodeX;
        @SerializedName("FromNode_Y")
        @Expose
        private Float fromNodeY;
        @SerializedName("ToNodeId")
        @Expose
        private String toNodeId;
        @SerializedName("ToNode_X")
        @Expose
        private Float toNodeX;
        @SerializedName("ToNode_Y")
        @Expose
        private Float toNodeY;
        @SerializedName("DeviceTypeLine")
        @Expose
        private Integer deviceTypeLine;
        @SerializedName("LineDeviceNumber")
        @Expose
        private String lineDeviceNumber;

        public String getDeviceNumber() {
            return deviceNumber;
        }

        public void setDeviceNumber(String deviceNumber) {
            this.deviceNumber = deviceNumber;
        }

        public Integer getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Integer deviceType) {
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

        public Integer getLocation() {
            return location;
        }

        public void setLocation(Integer location) {
            this.location = location;
        }

        public String getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(String equipmentId) {
            this.equipmentId = equipmentId;
        }

        public Integer getClosedPhase() {
            return closedPhase;
        }

        public void setClosedPhase(Integer closedPhase) {
            this.closedPhase = closedPhase;
        }

        public Object getComponentMask() {
            return componentMask;
        }

        public void setComponentMask(Object componentMask) {
            this.componentMask = componentMask;
        }

        public Object getRatedCurrent() {
            return ratedCurrent;
        }

        public void setRatedCurrent(Object ratedCurrent) {
            this.ratedCurrent = ratedCurrent;
        }

        public Object getFirstRatedCurrent() {
            return firstRatedCurrent;
        }

        public void setFirstRatedCurrent(Object firstRatedCurrent) {
            this.firstRatedCurrent = firstRatedCurrent;
        }

        public Object getSecondRatedCurrent() {
            return secondRatedCurrent;
        }

        public void setSecondRatedCurrent(Object secondRatedCurrent) {
            this.secondRatedCurrent = secondRatedCurrent;
        }

        public Object getThirdRatedCurrent() {
            return thirdRatedCurrent;
        }

        public void setThirdRatedCurrent(Object thirdRatedCurrent) {
            this.thirdRatedCurrent = thirdRatedCurrent;
        }

        public Object getFourthRatedCurrent() {
            return fourthRatedCurrent;
        }

        public void setFourthRatedCurrent(Object fourthRatedCurrent) {
            this.fourthRatedCurrent = fourthRatedCurrent;
        }

        public Object getRatedVoltage() {
            return ratedVoltage;
        }

        public void setRatedVoltage(Object ratedVoltage) {
            this.ratedVoltage = ratedVoltage;
        }

        public Object getReversible() {
            return reversible;
        }

        public void setReversible(Object reversible) {
            this.reversible = reversible;
        }

        public Object getSinglePhaseLocking() {
            return singlePhaseLocking;
        }

        public void setSinglePhaseLocking(Object singlePhaseLocking) {
            this.singlePhaseLocking = singlePhaseLocking;
        }

        public Integer getRemoteControlled() {
            return remoteControlled;
        }

        public void setRemoteControlled(Integer remoteControlled) {
            this.remoteControlled = remoteControlled;
        }

        public Integer getAutomated() {
            return automated;
        }

        public void setAutomated(Integer automated) {
            this.automated = automated;
        }

        public Object getFavorite() {
            return favorite;
        }

        public void setFavorite(Object favorite) {
            this.favorite = favorite;
        }

        public Object getModifiedByUser() {
            return modifiedByUser;
        }

        public void setModifiedByUser(Object modifiedByUser) {
            this.modifiedByUser = modifiedByUser;
        }

        public Object getFlags() {
            return flags;
        }

        public void setFlags(Object flags) {
            this.flags = flags;
        }

        public Object getLastChange() {
            return lastChange;
        }

        public void setLastChange(Object lastChange) {
            this.lastChange = lastChange;
        }

        public Object getComments() {
            return comments;
        }

        public void setComments(Object comments) {
            this.comments = comments;
        }

        public Integer getPhase() {
            return phase;
        }

        public void setPhase(Integer phase) {
            this.phase = phase;
        }

        public Object getZoneId() {
            return zoneId;
        }

        public void setZoneId(Object zoneId) {
            this.zoneId = zoneId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Object getIsTotalDemand() {
            return isTotalDemand;
        }

        public void setIsTotalDemand(Object isTotalDemand) {
            this.isTotalDemand = isTotalDemand;
        }

        public Object getDemandType() {
            return demandType;
        }

        public void setDemandType(Object demandType) {
            this.demandType = demandType;
        }

        public Object getVal1Total() {
            return val1Total;
        }

        public void setVal1Total(Object val1Total) {
            this.val1Total = val1Total;
        }

        public Object getVal2Total() {
            return val2Total;
        }

        public void setVal2Total(Object val2Total) {
            this.val2Total = val2Total;
        }

        public Object getVal1A() {
            return val1A;
        }

        public void setVal1A(Object val1A) {
            this.val1A = val1A;
        }

        public Object getVal2A() {
            return val2A;
        }

        public void setVal2A(Object val2A) {
            this.val2A = val2A;
        }

        public Object getVal1B() {
            return val1B;
        }

        public void setVal1B(Object val1B) {
            this.val1B = val1B;
        }

        public Object getVal2B() {
            return val2B;
        }

        public void setVal2B(Object val2B) {
            this.val2B = val2B;
        }

        public Object getVal1C() {
            return val1C;
        }

        public void setVal1C(Object val1C) {
            this.val1C = val1C;
        }

        public Object getVal2C() {
            return val2C;
        }

        public void setVal2C(Object val2C) {
            this.val2C = val2C;
        }

        public Object getDisconnectedPhase() {
            return disconnectedPhase;
        }

        public void setDisconnectedPhase(Object disconnectedPhase) {
            this.disconnectedPhase = disconnectedPhase;
        }

        public String getFromNodeId() {
            return fromNodeId;
        }

        public void setFromNodeId(String fromNodeId) {
            this.fromNodeId = fromNodeId;
        }

        public Float getFromNodeX() {
            return fromNodeX;
        }

        public void setFromNodeX(Float fromNodeX) {
            this.fromNodeX = fromNodeX;
        }

        public Float getFromNodeY() {
            return fromNodeY;
        }

        public void setFromNodeY(Float fromNodeY) {
            this.fromNodeY = fromNodeY;
        }

        public String getToNodeId() {
            return toNodeId;
        }

        public void setToNodeId(String toNodeId) {
            this.toNodeId = toNodeId;
        }

        public Float getToNodeX() {
            return toNodeX;
        }

        public void setToNodeX(Float toNodeX) {
            this.toNodeX = toNodeX;
        }

        public Float getToNodeY() {
            return toNodeY;
        }

        public void setToNodeY(Float toNodeY) {
            this.toNodeY = toNodeY;
        }

        public Integer getDeviceTypeLine() {
            return deviceTypeLine;
        }

        public void setDeviceTypeLine(Integer deviceTypeLine) {
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
