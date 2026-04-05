package com.techlabs.apdcl.models.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;


public class Transformer {

    @SerializedName("output")
    @Expose
    private Output output;

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    @Generated("jsonschema2pojo")
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
        @SerializedName("ComponentMask")
        @Expose
        private Integer componentMask;
        @SerializedName("PhaseType")
        @Expose
        private Integer phaseType;
        @SerializedName("WindingType")
        @Expose
        private Integer windingType;
        @SerializedName("NominalRatingKVA")
        @Expose
        private Float nominalRatingKVA;
        @SerializedName("FirstLoadingLimitKVA")
        @Expose
        private Float firstLoadingLimitKVA;
        @SerializedName("SecondLoadingLimitKVA")
        @Expose
        private Float secondLoadingLimitKVA;
        @SerializedName("ThirdLoadingLimitKVA")
        @Expose
        private Float thirdLoadingLimitKVA;
        @SerializedName("FourthLoadingLimitKVA")
        @Expose
        private Float fourthLoadingLimitKVA;
        @SerializedName("VoltageUnit")
        @Expose
        private Integer voltageUnit;
        @SerializedName("PrimaryVoltageKVLL")
        @Expose
        private Float primaryVoltageKVLL;
        @SerializedName("SecondaryVoltageKVLL")
        @Expose
        private Float secondaryVoltageKVLL;
        @SerializedName("PosSeqImpedancePercent")
        @Expose
        private Float posSeqImpedancePercent;
        @SerializedName("ZeroSeqImpedancePercent")
        @Expose
        private Float zeroSeqImpedancePercent;
        @SerializedName("ZeroSeqImpedancePrimSecPercent")
        @Expose
        private Float zeroSeqImpedancePrimSecPercent;
        @SerializedName("ZeroSeqImpedancePrimMagPercent")
        @Expose
        private Float zeroSeqImpedancePrimMagPercent;
        @SerializedName("ZeroSeqImpedanceSecMagPercent")
        @Expose
        private Float zeroSeqImpedanceSecMagPercent;
        @SerializedName("XRRatio")
        @Expose
        private Float xRRatio;
        @SerializedName("TransformerConnection")
        @Expose
        private Integer transformerConnection;
        @SerializedName("PrimGroundingResistanceOhms")
        @Expose
        private Float primGroundingResistanceOhms;
        @SerializedName("PrimGroundingReactanceOhms")
        @Expose
        private Float primGroundingReactanceOhms;
        @SerializedName("SecGroundingResistanceOhms")
        @Expose
        private Float secGroundingResistanceOhms;
        @SerializedName("SecGroundingReactanceOhms")
        @Expose
        private Float secGroundingReactanceOhms;
        @SerializedName("NoLoadLossesKW")
        @Expose
        private Float noLoadLossesKW;
        @SerializedName("Reversible")
        @Expose
        private Integer reversible;
        @SerializedName("XR0Ratio")
        @Expose
        private Float xR0Ratio;
        @SerializedName("XR0PrimSecRatio")
        @Expose
        private Float xR0PrimSecRatio;
        @SerializedName("XR0PrimMagRatio")
        @Expose
        private Float xR0PrimMagRatio;
        @SerializedName("XR0SecMagRatio")
        @Expose
        private Float xR0SecMagRatio;
        @SerializedName("MagnetizingCurrent")
        @Expose
        private Float magnetizingCurrent;
        @SerializedName("PhaseShift")
        @Expose
        private Integer phaseShift;
        @SerializedName("Favorite")
        @Expose
        private Integer favorite;
        @SerializedName("ModifiedByUser")
        @Expose
        private String modifiedByUser;
        @SerializedName("Flags")
        @Expose
        private Integer flags;
        @SerializedName("LastChange")
        @Expose
        private Integer lastChange;
        @SerializedName("Comments")
        @Expose
        private String comments;
        @SerializedName("InsulationType")
        @Expose
        private Integer insulationType;
        @SerializedName("Phase")
        @Expose
        private Integer phase;
        @SerializedName("ZoneId")
        @Expose
        private Object zoneId;
        @SerializedName("FromNodeId")
        @Expose
        private String fromNodeId;
        @SerializedName("ToNodeId")
        @Expose
        private String toNodeId;
        @SerializedName("FromNode_X")
        @Expose
        private Float fromNodeX;
        @SerializedName("FromNode_Y")
        @Expose
        private Float fromNodeY;
        @SerializedName("ToNode_X")
        @Expose
        private Float toNodeX;
        @SerializedName("ToNode_Y")
        @Expose
        private Float toNodeY;
        @SerializedName("Length")
        @Expose
        private Float length;
        @SerializedName("CaDeviceNumber")
        @Expose
        private String caDeviceNumber;
        @SerializedName("CableId")
        @Expose
        private String cableId;
        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("CableType")
        @Expose
        private Integer cableType;
        @SerializedName("PrimaryTapSettingPercent")
        @Expose
        private Float primaryTapSettingPercent;
        @SerializedName("SecondaryTapSettingPercent")
        @Expose
        private Float secondaryTapSettingPercent;
        @SerializedName("FaultIndicator")
        @Expose
        private Integer faultIndicator;
        @SerializedName("dtStatus")
        @Expose
        private Integer dtStatus;
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
        @SerializedName("DeviceTypeLine")
        @Expose
        private Integer deviceTypeLine;
        @SerializedName("LineDeviceNumber")
        @Expose
        private String lineDeviceNumber;

        @SerializedName("MeterIndex")
        @Expose
        private String meterIndex;

        @SerializedName("ReferenceTime")
        @Expose
        private String refrenceTime;

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

        public Integer getComponentMask() {
            return componentMask;
        }

        public void setComponentMask(Integer componentMask) {
            this.componentMask = componentMask;
        }

        public Integer getPhaseType() {
            return phaseType;
        }

        public void setPhaseType(Integer phaseType) {
            this.phaseType = phaseType;
        }

        public Integer getWindingType() {
            return windingType;
        }

        public void setWindingType(Integer windingType) {
            this.windingType = windingType;
        }

        public Float getNominalRatingKVA() {
            return nominalRatingKVA;
        }

        public void setNominalRatingKVA(Float nominalRatingKVA) {
            this.nominalRatingKVA = nominalRatingKVA;
        }

        public Float getFirstLoadingLimitKVA() {
            return firstLoadingLimitKVA;
        }

        public void setFirstLoadingLimitKVA(Float firstLoadingLimitKVA) {
            this.firstLoadingLimitKVA = firstLoadingLimitKVA;
        }

        public Float getSecondLoadingLimitKVA() {
            return secondLoadingLimitKVA;
        }

        public void setSecondLoadingLimitKVA(Float secondLoadingLimitKVA) {
            this.secondLoadingLimitKVA = secondLoadingLimitKVA;
        }

        public Float getThirdLoadingLimitKVA() {
            return thirdLoadingLimitKVA;
        }

        public void setThirdLoadingLimitKVA(Float thirdLoadingLimitKVA) {
            this.thirdLoadingLimitKVA = thirdLoadingLimitKVA;
        }

        public Float getFourthLoadingLimitKVA() {
            return fourthLoadingLimitKVA;
        }

        public void setFourthLoadingLimitKVA(Float fourthLoadingLimitKVA) {
            this.fourthLoadingLimitKVA = fourthLoadingLimitKVA;
        }

        public Integer getVoltageUnit() {
            return voltageUnit;
        }

        public void setVoltageUnit(Integer voltageUnit) {
            this.voltageUnit = voltageUnit;
        }

        public Float getPrimaryVoltageKVLL() {
            return primaryVoltageKVLL;
        }

        public void setPrimaryVoltageKVLL(Float primaryVoltageKVLL) {
            this.primaryVoltageKVLL = primaryVoltageKVLL;
        }

        public Float getSecondaryVoltageKVLL() {
            return secondaryVoltageKVLL;
        }

        public void setSecondaryVoltageKVLL(Float secondaryVoltageKVLL) {
            this.secondaryVoltageKVLL = secondaryVoltageKVLL;
        }

        public Float getPosSeqImpedancePercent() {
            return posSeqImpedancePercent;
        }

        public void setPosSeqImpedancePercent(Float posSeqImpedancePercent) {
            this.posSeqImpedancePercent = posSeqImpedancePercent;
        }

        public Float getZeroSeqImpedancePercent() {
            return zeroSeqImpedancePercent;
        }

        public void setZeroSeqImpedancePercent(Float zeroSeqImpedancePercent) {
            this.zeroSeqImpedancePercent = zeroSeqImpedancePercent;
        }

        public Float getZeroSeqImpedancePrimSecPercent() {
            return zeroSeqImpedancePrimSecPercent;
        }

        public void setZeroSeqImpedancePrimSecPercent(Float zeroSeqImpedancePrimSecPercent) {
            this.zeroSeqImpedancePrimSecPercent = zeroSeqImpedancePrimSecPercent;
        }

        public Float getZeroSeqImpedancePrimMagPercent() {
            return zeroSeqImpedancePrimMagPercent;
        }

        public void setZeroSeqImpedancePrimMagPercent(Float zeroSeqImpedancePrimMagPercent) {
            this.zeroSeqImpedancePrimMagPercent = zeroSeqImpedancePrimMagPercent;
        }

        public Float getZeroSeqImpedanceSecMagPercent() {
            return zeroSeqImpedanceSecMagPercent;
        }

        public void setZeroSeqImpedanceSecMagPercent(Float zeroSeqImpedanceSecMagPercent) {
            this.zeroSeqImpedanceSecMagPercent = zeroSeqImpedanceSecMagPercent;
        }

        public Float getXRRatio() {
            return xRRatio;
        }

        public void setXRRatio(Float xRRatio) {
            this.xRRatio = xRRatio;
        }

        public Integer getTransformerConnection() {
            return transformerConnection;
        }

        public void setTransformerConnection(Integer transformerConnection) {
            this.transformerConnection = transformerConnection;
        }

        public Float getPrimGroundingResistanceOhms() {
            return primGroundingResistanceOhms;
        }

        public void setPrimGroundingResistanceOhms(Float primGroundingResistanceOhms) {
            this.primGroundingResistanceOhms = primGroundingResistanceOhms;
        }

        public Float getPrimGroundingReactanceOhms() {
            return primGroundingReactanceOhms;
        }

        public void setPrimGroundingReactanceOhms(Float primGroundingReactanceOhms) {
            this.primGroundingReactanceOhms = primGroundingReactanceOhms;
        }

        public Float getSecGroundingResistanceOhms() {
            return secGroundingResistanceOhms;
        }

        public void setSecGroundingResistanceOhms(Float secGroundingResistanceOhms) {
            this.secGroundingResistanceOhms = secGroundingResistanceOhms;
        }

        public Float getSecGroundingReactanceOhms() {
            return secGroundingReactanceOhms;
        }

        public void setSecGroundingReactanceOhms(Float secGroundingReactanceOhms) {
            this.secGroundingReactanceOhms = secGroundingReactanceOhms;
        }

        public Float getNoLoadLossesKW() {
            return noLoadLossesKW;
        }

        public void setNoLoadLossesKW(Float noLoadLossesKW) {
            this.noLoadLossesKW = noLoadLossesKW;
        }

        public Integer getReversible() {
            return reversible;
        }

        public void setReversible(Integer reversible) {
            this.reversible = reversible;
        }

        public Float getXR0Ratio() {
            return xR0Ratio;
        }

        public void setXR0Ratio(Float xR0Ratio) {
            this.xR0Ratio = xR0Ratio;
        }

        public Float getXR0PrimSecRatio() {
            return xR0PrimSecRatio;
        }

        public void setXR0PrimSecRatio(Float xR0PrimSecRatio) {
            this.xR0PrimSecRatio = xR0PrimSecRatio;
        }

        public Float getXR0PrimMagRatio() {
            return xR0PrimMagRatio;
        }

        public void setXR0PrimMagRatio(Float xR0PrimMagRatio) {
            this.xR0PrimMagRatio = xR0PrimMagRatio;
        }

        public Float getXR0SecMagRatio() {
            return xR0SecMagRatio;
        }

        public void setXR0SecMagRatio(Float xR0SecMagRatio) {
            this.xR0SecMagRatio = xR0SecMagRatio;
        }

        public Float getMagnetizingCurrent() {
            return magnetizingCurrent;
        }

        public void setMagnetizingCurrent(Float magnetizingCurrent) {
            this.magnetizingCurrent = magnetizingCurrent;
        }

        public Integer getPhaseShift() {
            return phaseShift;
        }

        public void setPhaseShift(Integer phaseShift) {
            this.phaseShift = phaseShift;
        }

        public Integer getFavorite() {
            return favorite;
        }

        public void setFavorite(Integer favorite) {
            this.favorite = favorite;
        }

        public String getModifiedByUser() {
            return modifiedByUser;
        }

        public void setModifiedByUser(String modifiedByUser) {
            this.modifiedByUser = modifiedByUser;
        }

        public Integer getFlags() {
            return flags;
        }

        public void setFlags(Integer flags) {
            this.flags = flags;
        }

        public Integer getLastChange() {
            return lastChange;
        }

        public void setLastChange(Integer lastChange) {
            this.lastChange = lastChange;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public Integer getInsulationType() {
            return insulationType;
        }

        public void setInsulationType(Integer insulationType) {
            this.insulationType = insulationType;
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

        public String getFromNodeId() {
            return fromNodeId;
        }

        public void setFromNodeId(String fromNodeId) {
            this.fromNodeId = fromNodeId;
        }

        public String getToNodeId() {
            return toNodeId;
        }

        public void setToNodeId(String toNodeId) {
            this.toNodeId = toNodeId;
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

        public Float getLength() {
            return length;
        }

        public void setLength(Float length) {
            this.length = length;
        }

        public String getCaDeviceNumber() {
            return caDeviceNumber;
        }

        public void setCaDeviceNumber(String caDeviceNumber) {
            this.caDeviceNumber = caDeviceNumber;
        }

        public String getCableId() {
            return cableId;
        }

        public void setCableId(String cableId) {
            this.cableId = cableId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getCableType() {
            return cableType;
        }

        public void setCableType(Integer cableType) {
            this.cableType = cableType;
        }

        public Float getPrimaryTapSettingPercent() {
            return primaryTapSettingPercent;
        }

        public void setPrimaryTapSettingPercent(Float primaryTapSettingPercent) {
            this.primaryTapSettingPercent = primaryTapSettingPercent;
        }

        public Float getSecondaryTapSettingPercent() {
            return secondaryTapSettingPercent;
        }

        public void setSecondaryTapSettingPercent(Float secondaryTapSettingPercent) {
            this.secondaryTapSettingPercent = secondaryTapSettingPercent;
        }

        public Integer getFaultIndicator() {
            return faultIndicator;
        }

        public void setFaultIndicator(Integer faultIndicator) {
            this.faultIndicator = faultIndicator;
        }

        public Integer getDtStatus() {
            return dtStatus;
        }

        public void setDtStatus(Integer dtStatus) {
            this.dtStatus = dtStatus;
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

        public void setMeterIndex(String meterIndex) {
            this.meterIndex = meterIndex;
        }

        public String getMeterIndex() {
            return meterIndex;
        }

        public void setRefrenceTime(String refrenceTime) {
            this.refrenceTime = refrenceTime;
        }

        public String getRefrenceTime() {
            return refrenceTime;
        }

    }

}



