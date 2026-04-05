package com.techlabs.apdcl.models.Line;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Overhead {

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
        @SerializedName("LineId")
        @Expose
        private String lineId;
        @SerializedName("Length")
        @Expose
        private Float length;
        @SerializedName("ComponentMask")
        @Expose
        private Object componentMask;
        @SerializedName("PhaseConductorId")
        @Expose
        private String phaseConductorId;
        @SerializedName("NeutralConductorId")
        @Expose
        private String neutralConductorId;
        @SerializedName("ConductorSpacingId")
        @Expose
        private String conductorSpacingId;
        @SerializedName("NominalRating")
        @Expose
        private Object nominalRating;
        @SerializedName("FirstRating")
        @Expose
        private Object firstRating;
        @SerializedName("SecondRating")
        @Expose
        private Object secondRating;
        @SerializedName("ThirdRating")
        @Expose
        private Object thirdRating;
        @SerializedName("FourthRating")
        @Expose
        private Object fourthRating;
        @SerializedName("UserDefinedImpedances")
        @Expose
        private Integer userDefinedImpedances;
        @SerializedName("PositiveSequenceResistance")
        @Expose
        private Float positiveSequenceResistance;
        @SerializedName("PositiveSequenceReactance")
        @Expose
        private Float positiveSequenceReactance;
        @SerializedName("ZeroSequenceResistance")
        @Expose
        private Float zeroSequenceResistance;
        @SerializedName("ZeroSequenceReactance")
        @Expose
        private Float zeroSequenceReactance;
        @SerializedName("PosSeqShuntSusceptance")
        @Expose
        private Float posSeqShuntSusceptance;
        @SerializedName("ZeroSequenceShuntSusceptance")
        @Expose
        private Float zeroSequenceShuntSusceptance;
        @SerializedName("LockImpedance")
        @Expose
        private Integer lockImpedance;
        @SerializedName("Temperature")
        @Expose
        private Float temperature;
        @SerializedName("Frequency")
        @Expose
        private Object frequency;
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
        @SerializedName("PosSeqShuntConductance")
        @Expose
        private Float posSeqShuntConductance;
        @SerializedName("ZeroSequenceShuntConductance")
        @Expose
        private Float zeroSequenceShuntConductance;
        @SerializedName("Diameter")
        @Expose
        private Object diameter;
        @SerializedName("GMR")
        @Expose
        private Object gmr;
        @SerializedName("R25")
        @Expose
        private Object r25;
        @SerializedName("R50")
        @Expose
        private Object r50;
        @SerializedName("WithstandRating")
        @Expose
        private Object withstandRating;
        @SerializedName("CodeWord")
        @Expose
        private Object codeWord;
        @SerializedName("Size_mm2")
        @Expose
        private Object sizeMm2;
        @SerializedName("ConstructionType")
        @Expose
        private Object constructionType;
        @SerializedName("FirstResistanceDC")
        @Expose
        private Object firstResistanceDC;
        @SerializedName("SecondResistanceDC")
        @Expose
        private Object secondResistanceDC;
        @SerializedName("MaterialId")
        @Expose
        private Object materialId;
        @SerializedName("AWGSize")
        @Expose
        private Object aWGSize;
        @SerializedName("SizeUnit")
        @Expose
        private Object sizeUnit;
        @SerializedName("OutsideArea")
        @Expose
        private Object outsideArea;
        @SerializedName("NumberOfStrands")
        @Expose
        private Object numberOfStrands;
        @SerializedName("TemperatureAC1")
        @Expose
        private Object temperatureAC1;
        @SerializedName("TemperatureAC2")
        @Expose
        private Object temperatureAC2;
        @SerializedName("TemperatureDC1")
        @Expose
        private Object temperatureDC1;
        @SerializedName("TemperatureDC2")
        @Expose
        private Object temperatureDC2;
        @SerializedName("Phase")
        @Expose
        private Integer phase;
        @SerializedName("ZoneId")
        @Expose
        private Object zoneId;
        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("FromNodeId")
        @Expose
        private String fromNodeId;
        @SerializedName("ToNodeId")
        @Expose
        private String toNodeId;
        @SerializedName("FromNode_X")
        @Expose
        private Double fromNodeX;
        @SerializedName("FromNode_Y")
        @Expose
        private Double fromNodeY;
        @SerializedName("ToNode_X")
        @Expose
        private Double toNodeX;
        @SerializedName("ToNode_Y")
        @Expose
        private Double toNodeY;

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

        public String getLineId() {
            return lineId;
        }

        public void setLineId(String lineId) {
            this.lineId = lineId;
        }

        public Float getLength() {
            return length;
        }

        public void setLength(Float length) {
            this.length = length;
        }

        public Object getComponentMask() {
            return componentMask;
        }

        public void setComponentMask(Object componentMask) {
            this.componentMask = componentMask;
        }

        public String getPhaseConductorId() {
            return phaseConductorId;
        }

        public void setPhaseConductorId(String phaseConductorId) {
            this.phaseConductorId = phaseConductorId;
        }

        public String getNeutralConductorId() {
            return neutralConductorId;
        }

        public void setNeutralConductorId(String neutralConductorId) {
            this.neutralConductorId = neutralConductorId;
        }

        public String getConductorSpacingId() {
            return conductorSpacingId;
        }

        public void setConductorSpacingId(String conductorSpacingId) {
            this.conductorSpacingId = conductorSpacingId;
        }

        public Object getNominalRating() {
            return nominalRating;
        }

        public void setNominalRating(Object nominalRating) {
            this.nominalRating = nominalRating;
        }

        public Object getFirstRating() {
            return firstRating;
        }

        public void setFirstRating(Object firstRating) {
            this.firstRating = firstRating;
        }

        public Object getSecondRating() {
            return secondRating;
        }

        public void setSecondRating(Object secondRating) {
            this.secondRating = secondRating;
        }

        public Object getThirdRating() {
            return thirdRating;
        }

        public void setThirdRating(Object thirdRating) {
            this.thirdRating = thirdRating;
        }

        public Object getFourthRating() {
            return fourthRating;
        }

        public void setFourthRating(Object fourthRating) {
            this.fourthRating = fourthRating;
        }

        public Integer getUserDefinedImpedances() {
            return userDefinedImpedances;
        }

        public void setUserDefinedImpedances(Integer userDefinedImpedances) {
            this.userDefinedImpedances = userDefinedImpedances;
        }

        public Float getPositiveSequenceResistance() {
            return positiveSequenceResistance;
        }

        public void setPositiveSequenceResistance(Float positiveSequenceResistance) {
            this.positiveSequenceResistance = positiveSequenceResistance;
        }

        public Float getPositiveSequenceReactance() {
            return positiveSequenceReactance;
        }

        public void setPositiveSequenceReactance(Float positiveSequenceReactance) {
            this.positiveSequenceReactance = positiveSequenceReactance;
        }

        public Float getZeroSequenceResistance() {
            return zeroSequenceResistance;
        }

        public void setZeroSequenceResistance(Float zeroSequenceResistance) {
            this.zeroSequenceResistance = zeroSequenceResistance;
        }

        public Float getZeroSequenceReactance() {
            return zeroSequenceReactance;
        }

        public void setZeroSequenceReactance(Float zeroSequenceReactance) {
            this.zeroSequenceReactance = zeroSequenceReactance;
        }

        public Float getPosSeqShuntSusceptance() {
            return posSeqShuntSusceptance;
        }

        public void setPosSeqShuntSusceptance(Float posSeqShuntSusceptance) {
            this.posSeqShuntSusceptance = posSeqShuntSusceptance;
        }

        public Float getZeroSequenceShuntSusceptance() {
            return zeroSequenceShuntSusceptance;
        }

        public void setZeroSequenceShuntSusceptance(Float zeroSequenceShuntSusceptance) {
            this.zeroSequenceShuntSusceptance = zeroSequenceShuntSusceptance;
        }

        public Integer getLockImpedance() {
            return lockImpedance;
        }

        public void setLockImpedance(Integer lockImpedance) {
            this.lockImpedance = lockImpedance;
        }

        public Float getTemperature() {
            return temperature;
        }

        public void setTemperature(Float temperature) {
            this.temperature = temperature;
        }

        public Object getFrequency() {
            return frequency;
        }

        public void setFrequency(Object frequency) {
            this.frequency = frequency;
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

        public Float getPosSeqShuntConductance() {
            return posSeqShuntConductance;
        }

        public void setPosSeqShuntConductance(Float posSeqShuntConductance) {
            this.posSeqShuntConductance = posSeqShuntConductance;
        }

        public Float getZeroSequenceShuntConductance() {
            return zeroSequenceShuntConductance;
        }

        public void setZeroSequenceShuntConductance(Float zeroSequenceShuntConductance) {
            this.zeroSequenceShuntConductance = zeroSequenceShuntConductance;
        }

        public Object getDiameter() {
            return diameter;
        }

        public void setDiameter(Object diameter) {
            this.diameter = diameter;
        }

        public Object getGmr() {
            return gmr;
        }

        public void setGmr(Object gmr) {
            this.gmr = gmr;
        }

        public Object getR25() {
            return r25;
        }

        public void setR25(Object r25) {
            this.r25 = r25;
        }

        public Object getR50() {
            return r50;
        }

        public void setR50(Object r50) {
            this.r50 = r50;
        }

        public Object getWithstandRating() {
            return withstandRating;
        }

        public void setWithstandRating(Object withstandRating) {
            this.withstandRating = withstandRating;
        }

        public Object getCodeWord() {
            return codeWord;
        }

        public void setCodeWord(Object codeWord) {
            this.codeWord = codeWord;
        }

        public Object getSizeMm2() {
            return sizeMm2;
        }

        public void setSizeMm2(Object sizeMm2) {
            this.sizeMm2 = sizeMm2;
        }

        public Object getConstructionType() {
            return constructionType;
        }

        public void setConstructionType(Object constructionType) {
            this.constructionType = constructionType;
        }

        public Object getFirstResistanceDC() {
            return firstResistanceDC;
        }

        public void setFirstResistanceDC(Object firstResistanceDC) {
            this.firstResistanceDC = firstResistanceDC;
        }

        public Object getSecondResistanceDC() {
            return secondResistanceDC;
        }

        public void setSecondResistanceDC(Object secondResistanceDC) {
            this.secondResistanceDC = secondResistanceDC;
        }

        public Object getMaterialId() {
            return materialId;
        }

        public void setMaterialId(Object materialId) {
            this.materialId = materialId;
        }

        public Object getAWGSize() {
            return aWGSize;
        }

        public void setAWGSize(Object aWGSize) {
            this.aWGSize = aWGSize;
        }

        public Object getSizeUnit() {
            return sizeUnit;
        }

        public void setSizeUnit(Object sizeUnit) {
            this.sizeUnit = sizeUnit;
        }

        public Object getOutsideArea() {
            return outsideArea;
        }

        public void setOutsideArea(Object outsideArea) {
            this.outsideArea = outsideArea;
        }

        public Object getNumberOfStrands() {
            return numberOfStrands;
        }

        public void setNumberOfStrands(Object numberOfStrands) {
            this.numberOfStrands = numberOfStrands;
        }

        public Object getTemperatureAC1() {
            return temperatureAC1;
        }

        public void setTemperatureAC1(Object temperatureAC1) {
            this.temperatureAC1 = temperatureAC1;
        }

        public Object getTemperatureAC2() {
            return temperatureAC2;
        }

        public void setTemperatureAC2(Object temperatureAC2) {
            this.temperatureAC2 = temperatureAC2;
        }

        public Object getTemperatureDC1() {
            return temperatureDC1;
        }

        public void setTemperatureDC1(Object temperatureDC1) {
            this.temperatureDC1 = temperatureDC1;
        }

        public Object getTemperatureDC2() {
            return temperatureDC2;
        }

        public void setTemperatureDC2(Object temperatureDC2) {
            this.temperatureDC2 = temperatureDC2;
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

        public Double getFromNodeX() {
            return fromNodeX;
        }

        public void setFromNodeX(Double fromNodeX) {
            this.fromNodeX = fromNodeX;
        }

        public Double getFromNodeY() {
            return fromNodeY;
        }

        public void setFromNodeY(Double fromNodeY) {
            this.fromNodeY = fromNodeY;
        }

        public Double getToNodeX() {
            return toNodeX;
        }

        public void setToNodeX(Double toNodeX) {
            this.toNodeX = toNodeX;
        }

        public Double getToNodeY() {
            return toNodeY;
        }

        public void setToNodeY(Double toNodeY) {
            this.toNodeY = toNodeY;
        }

    }

}



