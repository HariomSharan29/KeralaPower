package com.techlabs.apdcl.models.Line;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Unbalanced {

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
        @SerializedName("PhaseConductorIdA")
        @Expose
        private String phaseConductorIdA;
        @SerializedName("PhaseConductorIdB")
        @Expose
        private String phaseConductorIdB;
        @SerializedName("PhaseConductorIdC")
        @Expose
        private String phaseConductorIdC;
        @SerializedName("NeutralConductorId")
        @Expose
        private String neutralConductorId;
        @SerializedName("NeutralConductorId2")
        @Expose
        private String neutralConductorId2;
        @SerializedName("ConductorSpacingId")
        @Expose
        private String conductorSpacingId;
        @SerializedName("UserDefinedImpedances")
        @Expose
        private Integer userDefinedImpedances;
        @SerializedName("NominalRatingA")
        @Expose
        private Float nominalRatingA;
        @SerializedName("NominalRatingB")
        @Expose
        private Float nominalRatingB;
        @SerializedName("NominalRatingC")
        @Expose
        private Float nominalRatingC;
        @SerializedName("FirstRatingA")
        @Expose
        private Float firstRatingA;
        @SerializedName("FirstRatingB")
        @Expose
        private Float firstRatingB;
        @SerializedName("FirstRatingC")
        @Expose
        private Float firstRatingC;
        @SerializedName("SecondRatingA")
        @Expose
        private Float secondRatingA;
        @SerializedName("SecondRatingB")
        @Expose
        private Float secondRatingB;
        @SerializedName("SecondRatingC")
        @Expose
        private Float secondRatingC;
        @SerializedName("ThirdRatingA")
        @Expose
        private Float thirdRatingA;
        @SerializedName("ThirdRatingB")
        @Expose
        private Float thirdRatingB;
        @SerializedName("ThirdRatingC")
        @Expose
        private Float thirdRatingC;
        @SerializedName("FourthRatingA")
        @Expose
        private Float fourthRatingA;
        @SerializedName("FourthRatingB")
        @Expose
        private Float fourthRatingB;
        @SerializedName("FourthRatingC")
        @Expose
        private Float fourthRatingC;
        @SerializedName("SelfResistanceA")
        @Expose
        private Float selfResistanceA;
        @SerializedName("SelfResistanceB")
        @Expose
        private Float selfResistanceB;
        @SerializedName("SelfResistanceC")
        @Expose
        private Float selfResistanceC;
        @SerializedName("SelfReactanceA")
        @Expose
        private Float selfReactanceA;
        @SerializedName("SelfReactanceB")
        @Expose
        private Float selfReactanceB;
        @SerializedName("SelfReactanceC")
        @Expose
        private Float selfReactanceC;
        @SerializedName("ShuntSusceptanceA")
        @Expose
        private Float shuntSusceptanceA;
        @SerializedName("ShuntSusceptanceB")
        @Expose
        private Float shuntSusceptanceB;
        @SerializedName("ShuntSusceptanceC")
        @Expose
        private Float shuntSusceptanceC;
        @SerializedName("LockImpedance")
        @Expose
        private Integer lockImpedance;
        @SerializedName("Temperature")
        @Expose
        private Float temperature;
        @SerializedName("Frequency")
        @Expose
        private Object frequency;
        @SerializedName("Transposed")
        @Expose
        private Integer transposed;
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
        @SerializedName("ShuntConductanceA")
        @Expose
        private Float shuntConductanceA;
        @SerializedName("ShuntConductanceB")
        @Expose
        private Float shuntConductanceB;
        @SerializedName("ShuntConductanceC")
        @Expose
        private Float shuntConductanceC;
        @SerializedName("MutualResistanceAB")
        @Expose
        private Float mutualResistanceAB;
        @SerializedName("MutualResistanceBC")
        @Expose
        private Float mutualResistanceBC;
        @SerializedName("MutualResistanceCA")
        @Expose
        private Float mutualResistanceCA;
        @SerializedName("MutualReactanceAB")
        @Expose
        private Float mutualReactanceAB;
        @SerializedName("MutualReactanceBC")
        @Expose
        private Float mutualReactanceBC;
        @SerializedName("MutualReactanceCA")
        @Expose
        private Float mutualReactanceCA;
        @SerializedName("MutualShuntSusceptanceAB")
        @Expose
        private Float mutualShuntSusceptanceAB;
        @SerializedName("MutualShuntSusceptanceBC")
        @Expose
        private Float mutualShuntSusceptanceBC;
        @SerializedName("MutualShuntSusceptanceCA")
        @Expose
        private Float mutualShuntSusceptanceCA;
        @SerializedName("MutualShuntConductanceAB")
        @Expose
        private Float mutualShuntConductanceAB;
        @SerializedName("MutualShuntConductanceBC")
        @Expose
        private Float mutualShuntConductanceBC;
        @SerializedName("MutualShuntConductanceCA")
        @Expose
        private Float mutualShuntConductanceCA;
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
        @SerializedName("Status")
        @Expose
        private Integer status;

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

        public String getPhaseConductorIdA() {
            return phaseConductorIdA;
        }

        public void setPhaseConductorIdA(String phaseConductorIdA) {
            this.phaseConductorIdA = phaseConductorIdA;
        }

        public String getPhaseConductorIdB() {
            return phaseConductorIdB;
        }

        public void setPhaseConductorIdB(String phaseConductorIdB) {
            this.phaseConductorIdB = phaseConductorIdB;
        }

        public String getPhaseConductorIdC() {
            return phaseConductorIdC;
        }

        public void setPhaseConductorIdC(String phaseConductorIdC) {
            this.phaseConductorIdC = phaseConductorIdC;
        }

        public String getNeutralConductorId() {
            return neutralConductorId;
        }

        public void setNeutralConductorId(String neutralConductorId) {
            this.neutralConductorId = neutralConductorId;
        }

        public String getNeutralConductorId2() {
            return neutralConductorId2;
        }

        public void setNeutralConductorId2(String neutralConductorId2) {
            this.neutralConductorId2 = neutralConductorId2;
        }

        public String getConductorSpacingId() {
            return conductorSpacingId;
        }

        public void setConductorSpacingId(String conductorSpacingId) {
            this.conductorSpacingId = conductorSpacingId;
        }

        public Integer getUserDefinedImpedances() {
            return userDefinedImpedances;
        }

        public void setUserDefinedImpedances(Integer userDefinedImpedances) {
            this.userDefinedImpedances = userDefinedImpedances;
        }

        public Float getNominalRatingA() {
            return nominalRatingA;
        }

        public void setNominalRatingA(Float nominalRatingA) {
            this.nominalRatingA = nominalRatingA;
        }

        public Float getNominalRatingB() {
            return nominalRatingB;
        }

        public void setNominalRatingB(Float nominalRatingB) {
            this.nominalRatingB = nominalRatingB;
        }

        public Float getNominalRatingC() {
            return nominalRatingC;
        }

        public void setNominalRatingC(Float nominalRatingC) {
            this.nominalRatingC = nominalRatingC;
        }

        public Float getFirstRatingA() {
            return firstRatingA;
        }

        public void setFirstRatingA(Float firstRatingA) {
            this.firstRatingA = firstRatingA;
        }

        public Float getFirstRatingB() {
            return firstRatingB;
        }

        public void setFirstRatingB(Float firstRatingB) {
            this.firstRatingB = firstRatingB;
        }

        public Float getFirstRatingC() {
            return firstRatingC;
        }

        public void setFirstRatingC(Float firstRatingC) {
            this.firstRatingC = firstRatingC;
        }

        public Float getSecondRatingA() {
            return secondRatingA;
        }

        public void setSecondRatingA(Float secondRatingA) {
            this.secondRatingA = secondRatingA;
        }

        public Float getSecondRatingB() {
            return secondRatingB;
        }

        public void setSecondRatingB(Float secondRatingB) {
            this.secondRatingB = secondRatingB;
        }

        public Float getSecondRatingC() {
            return secondRatingC;
        }

        public void setSecondRatingC(Float secondRatingC) {
            this.secondRatingC = secondRatingC;
        }

        public Float getThirdRatingA() {
            return thirdRatingA;
        }

        public void setThirdRatingA(Float thirdRatingA) {
            this.thirdRatingA = thirdRatingA;
        }

        public Float getThirdRatingB() {
            return thirdRatingB;
        }

        public void setThirdRatingB(Float thirdRatingB) {
            this.thirdRatingB = thirdRatingB;
        }

        public Float getThirdRatingC() {
            return thirdRatingC;
        }

        public void setThirdRatingC(Float thirdRatingC) {
            this.thirdRatingC = thirdRatingC;
        }

        public Float getFourthRatingA() {
            return fourthRatingA;
        }

        public void setFourthRatingA(Float fourthRatingA) {
            this.fourthRatingA = fourthRatingA;
        }

        public Float getFourthRatingB() {
            return fourthRatingB;
        }

        public void setFourthRatingB(Float fourthRatingB) {
            this.fourthRatingB = fourthRatingB;
        }

        public Float getFourthRatingC() {
            return fourthRatingC;
        }

        public void setFourthRatingC(Float fourthRatingC) {
            this.fourthRatingC = fourthRatingC;
        }

        public Float getSelfResistanceA() {
            return selfResistanceA;
        }

        public void setSelfResistanceA(Float selfResistanceA) {
            this.selfResistanceA = selfResistanceA;
        }

        public Float getSelfResistanceB() {
            return selfResistanceB;
        }

        public void setSelfResistanceB(Float selfResistanceB) {
            this.selfResistanceB = selfResistanceB;
        }

        public Float getSelfResistanceC() {
            return selfResistanceC;
        }

        public void setSelfResistanceC(Float selfResistanceC) {
            this.selfResistanceC = selfResistanceC;
        }

        public Float getSelfReactanceA() {
            return selfReactanceA;
        }

        public void setSelfReactanceA(Float selfReactanceA) {
            this.selfReactanceA = selfReactanceA;
        }

        public Float getSelfReactanceB() {
            return selfReactanceB;
        }

        public void setSelfReactanceB(Float selfReactanceB) {
            this.selfReactanceB = selfReactanceB;
        }

        public Float getSelfReactanceC() {
            return selfReactanceC;
        }

        public void setSelfReactanceC(Float selfReactanceC) {
            this.selfReactanceC = selfReactanceC;
        }

        public Float getShuntSusceptanceA() {
            return shuntSusceptanceA;
        }

        public void setShuntSusceptanceA(Float shuntSusceptanceA) {
            this.shuntSusceptanceA = shuntSusceptanceA;
        }

        public Float getShuntSusceptanceB() {
            return shuntSusceptanceB;
        }

        public void setShuntSusceptanceB(Float shuntSusceptanceB) {
            this.shuntSusceptanceB = shuntSusceptanceB;
        }

        public Float getShuntSusceptanceC() {
            return shuntSusceptanceC;
        }

        public void setShuntSusceptanceC(Float shuntSusceptanceC) {
            this.shuntSusceptanceC = shuntSusceptanceC;
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

        public Integer getTransposed() {
            return transposed;
        }

        public void setTransposed(Integer transposed) {
            this.transposed = transposed;
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

        public Float getShuntConductanceA() {
            return shuntConductanceA;
        }

        public void setShuntConductanceA(Float shuntConductanceA) {
            this.shuntConductanceA = shuntConductanceA;
        }

        public Float getShuntConductanceB() {
            return shuntConductanceB;
        }

        public void setShuntConductanceB(Float shuntConductanceB) {
            this.shuntConductanceB = shuntConductanceB;
        }

        public Float getShuntConductanceC() {
            return shuntConductanceC;
        }

        public void setShuntConductanceC(Float shuntConductanceC) {
            this.shuntConductanceC = shuntConductanceC;
        }

        public Float getMutualResistanceAB() {
            return mutualResistanceAB;
        }

        public void setMutualResistanceAB(Float mutualResistanceAB) {
            this.mutualResistanceAB = mutualResistanceAB;
        }

        public Float getMutualResistanceBC() {
            return mutualResistanceBC;
        }

        public void setMutualResistanceBC(Float mutualResistanceBC) {
            this.mutualResistanceBC = mutualResistanceBC;
        }

        public Float getMutualResistanceCA() {
            return mutualResistanceCA;
        }

        public void setMutualResistanceCA(Float mutualResistanceCA) {
            this.mutualResistanceCA = mutualResistanceCA;
        }

        public Float getMutualReactanceAB() {
            return mutualReactanceAB;
        }

        public void setMutualReactanceAB(Float mutualReactanceAB) {
            this.mutualReactanceAB = mutualReactanceAB;
        }

        public Float getMutualReactanceBC() {
            return mutualReactanceBC;
        }

        public void setMutualReactanceBC(Float mutualReactanceBC) {
            this.mutualReactanceBC = mutualReactanceBC;
        }

        public Float getMutualReactanceCA() {
            return mutualReactanceCA;
        }

        public void setMutualReactanceCA(Float mutualReactanceCA) {
            this.mutualReactanceCA = mutualReactanceCA;
        }

        public Float getMutualShuntSusceptanceAB() {
            return mutualShuntSusceptanceAB;
        }

        public void setMutualShuntSusceptanceAB(Float mutualShuntSusceptanceAB) {
            this.mutualShuntSusceptanceAB = mutualShuntSusceptanceAB;
        }

        public Float getMutualShuntSusceptanceBC() {
            return mutualShuntSusceptanceBC;
        }

        public void setMutualShuntSusceptanceBC(Float mutualShuntSusceptanceBC) {
            this.mutualShuntSusceptanceBC = mutualShuntSusceptanceBC;
        }

        public Float getMutualShuntSusceptanceCA() {
            return mutualShuntSusceptanceCA;
        }

        public void setMutualShuntSusceptanceCA(Float mutualShuntSusceptanceCA) {
            this.mutualShuntSusceptanceCA = mutualShuntSusceptanceCA;
        }

        public Float getMutualShuntConductanceAB() {
            return mutualShuntConductanceAB;
        }

        public void setMutualShuntConductanceAB(Float mutualShuntConductanceAB) {
            this.mutualShuntConductanceAB = mutualShuntConductanceAB;
        }

        public Float getMutualShuntConductanceBC() {
            return mutualShuntConductanceBC;
        }

        public void setMutualShuntConductanceBC(Float mutualShuntConductanceBC) {
            this.mutualShuntConductanceBC = mutualShuntConductanceBC;
        }

        public Float getMutualShuntConductanceCA() {
            return mutualShuntConductanceCA;
        }

        public void setMutualShuntConductanceCA(Float mutualShuntConductanceCA) {
            this.mutualShuntConductanceCA = mutualShuntConductanceCA;
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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

    }

}