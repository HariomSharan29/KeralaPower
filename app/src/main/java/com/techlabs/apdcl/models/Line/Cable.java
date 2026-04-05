package com.techlabs.apdcl.models.Line;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cable {

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
        @SerializedName("NetworkId")
        @Expose
        private String networkId;
        @SerializedName("CableId")
        @Expose
        private String cableId;
        @SerializedName("Length")
        @Expose
        private Float length;
        @SerializedName("NumberOfCableInParallel")
        @Expose
        private Integer numberOfCableInParallel;
        @SerializedName("CTConnection")
        @Expose
        private Integer cTConnection;
        @SerializedName("X")
        @Expose
        private Object x;
        @SerializedName("Y")
        @Expose
        private Object y;
        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("NominalRating")
        @Expose
        private Float nominalRating;
        @SerializedName("FirstRating")
        @Expose
        private Float firstRating;
        @SerializedName("SecondRating")
        @Expose
        private Float secondRating;
        @SerializedName("ThirdRating")
        @Expose
        private Float thirdRating;
        @SerializedName("FourthRating")
        @Expose
        private Float fourthRating;
        @SerializedName("HarmonicModel")
        @Expose
        private Integer harmonicModel;
        @SerializedName("TCCRepositoryID")
        @Expose
        private Object tCCRepositoryID;
        @SerializedName("OperatingTemperature")
        @Expose
        private Float operatingTemperature;
        @SerializedName("Neutral1Type")
        @Expose
        private Integer neutral1Type;
        @SerializedName("Neutral2Type")
        @Expose
        private Integer neutral2Type;
        @SerializedName("Neutral3Type")
        @Expose
        private Integer neutral3Type;
        @SerializedName("Neutral1ID")
        @Expose
        private Object neutral1ID;
        @SerializedName("Neutral2ID")
        @Expose
        private Object neutral2ID;
        @SerializedName("Neutral3ID")
        @Expose
        private Object neutral3ID;
        @SerializedName("AmpacityDeratingFactor")
        @Expose
        private Float ampacityDeratingFactor;
        @SerializedName("FlowConstraintActive")
        @Expose
        private Integer flowConstraintActive;
        @SerializedName("FlowConstraintUnit")
        @Expose
        private Integer flowConstraintUnit;
        @SerializedName("MaximumFlow")
        @Expose
        private Float maximumFlow;
        @SerializedName("DeviceType")
        @Expose
        private Integer deviceType;
        @SerializedName("SectionId")
        @Expose
        private String sectionId;
        @SerializedName("Location")
        @Expose
        private Integer location;
        @SerializedName("DeviceStage")
        @Expose
        private Object deviceStage;
        @SerializedName("Flags")
        @Expose
        private Integer flags;
        @SerializedName("ComponentMask")
        @Expose
        private Integer componentMask;
        @SerializedName("InitFromEquipFlags")
        @Expose
        private Integer initFromEquipFlags;
        @SerializedName("FromNodeId")
        @Expose
        private String fromNodeId;
        @SerializedName("FromNodeConnectorIndex")
        @Expose
        private Integer fromNodeConnectorIndex;
        @SerializedName("ToNodeId")
        @Expose
        private String toNodeId;
        @SerializedName("ToNodeConnectorIndex")
        @Expose
        private Integer toNodeConnectorIndex;
        @SerializedName("Phase")
        @Expose
        private Integer phase;
        @SerializedName("ZoneId")
        @Expose
        private Object zoneId;
        @SerializedName("StructureId")
        @Expose
        private Object structureId;
        @SerializedName("BreakPointIndex")
        @Expose
        private Integer breakPointIndex;
        @SerializedName("BreakPointLocation")
        @Expose
        private Integer breakPointLocation;
        @SerializedName("FROM_NodeId")
        @Expose
        private String fROMNodeId;
        @SerializedName("FROM_ComponentMask")
        @Expose
        private Integer fROMComponentMask;
        @SerializedName("InsulationType")
        @Expose
        private String insulationType;
        @SerializedName("FROM_NodeId_X")
        @Expose
        private Double fROMNodeIdX;
        @SerializedName("FROM_NodeId_Y")
        @Expose
        private Double fROMNodeIdY;
        @SerializedName("FROM_ZoneId")
        @Expose
        private Object fROMZoneId;
        @SerializedName("FROM_UserDefinedBaseVoltage")
        @Expose
        private Float fROMUserDefinedBaseVoltage;
        @SerializedName("FROM_RatedVoltage")
        @Expose
        private Object fROMRatedVoltage;
        @SerializedName("FROM_RatedCurrent")
        @Expose
        private Object fROMRatedCurrent;
        @SerializedName("FROM_ANSISymCurrent")
        @Expose
        private Object fROMANSISymCurrent;
        @SerializedName("FROM_ANSIAsymCurrent")
        @Expose
        private Object fROMANSIAsymCurrent;
        @SerializedName("FROM_PeakCurrent")
        @Expose
        private Object fROMPeakCurrent;
        @SerializedName("FROM_Standard")
        @Expose
        private Object fROMStandard;
        @SerializedName("FROM_TestCircuitPowerFactor")
        @Expose
        private Object fROMTestCircuitPowerFactor;
        @SerializedName("Installation")
        @Expose
        private Integer installation;
        @SerializedName("TO_NodeId")
        @Expose
        private String tONodeId;
        @SerializedName("TO_ComponentMask")
        @Expose
        private Integer tOComponentMask;
        @SerializedName("TO_NodeId_X")
        @Expose
        private Double tONodeIdX;
        @SerializedName("TO_NodeId_Y")
        @Expose
        private Float tONodeIdY;
        @SerializedName("TO_ZoneId")
        @Expose
        private Object tOZoneId;
        @SerializedName("TO_UserDefinedBaseVoltage")
        @Expose
        private Float tOUserDefinedBaseVoltage;
        @SerializedName("TO_RatedVoltage")
        @Expose
        private Object tORatedVoltage;
        @SerializedName("TO_RatedCurrent")
        @Expose
        private Object tORatedCurrent;
        @SerializedName("TO_ANSISymCurrent")
        @Expose
        private Object tOANSISymCurrent;
        @SerializedName("TO_ANSIAsymCurrent")
        @Expose
        private Object tOANSIAsymCurrent;
        @SerializedName("TO_PeakCurrent")
        @Expose
        private Object tOPeakCurrent;
        @SerializedName("TO_Standard")
        @Expose
        private Object tOStandard;
        @SerializedName("TO_TestCircuitPowerFactor")
        @Expose
        private Object tOTestCircuitPowerFactor;
        @SerializedName("TO_Installation")
        @Expose
        private Integer tOInstallation;
        @SerializedName("EquipmentId")
        @Expose
        private String equipmentId;
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
        @SerializedName("WithstandRating")
        @Expose
        private Float withstandRating;
        @SerializedName("ZeroSequenceShuntSusceptance")
        @Expose
        private Float zeroSequenceShuntSusceptance;
        @SerializedName("LevelKV")
        @Expose
        private Float levelKV;
        @SerializedName("Manufacturer")
        @Expose
        private String manufacturer;
        @SerializedName("Standard")
        @Expose
        private String standard;
        @SerializedName("CableType")
        @Expose
        private Integer cableType;
        @SerializedName("NumberOfGroundingConductors")
        @Expose
        private Integer numberOfGroundingConductors;
        @SerializedName("ConcentricNeutralBeforeSheath")
        @Expose
        private Integer concentricNeutralBeforeSheath;
        @SerializedName("UserDefinedImpedances")
        @Expose
        private Integer userDefinedImpedances;
        @SerializedName("Frequency")
        @Expose
        private Float frequency;
        @SerializedName("Temperature")
        @Expose
        private Float temperature;
        @SerializedName("ImpedancesNote")
        @Expose
        private String impedancesNote;
        @SerializedName("Favorite")
        @Expose
        private Integer favorite;
        @SerializedName("ModifiedByUser")
        @Expose
        private String modifiedByUser;
        @SerializedName("LastChange")
        @Expose
        private Integer lastChange;
        @SerializedName("Comments")
        @Expose
        private String comments;
        @SerializedName("PosSeqShuntConductance")
        @Expose
        private Float posSeqShuntConductance;
        @SerializedName("ZeroSequenceShuntConductance")
        @Expose
        private Float zeroSequenceShuntConductance;
        @SerializedName("LockImpedance")
        @Expose
        private Integer lockImpedance;
        @SerializedName("CableConcentricNeutralLocation")
        @Expose
        private Object cableConcentricNeutralLocation;
        @SerializedName("MaterialID")
        @Expose
        private Object materialID;
        @SerializedName("LayerPosition")
        @Expose
        private Object layerPosition;
        @SerializedName("Thickness")
        @Expose
        private Object thickness;
        @SerializedName("ConcentricNeutralsType")
        @Expose
        private Object concentricNeutralsType;
        @SerializedName("NumberOfWires")
        @Expose
        private Object numberOfWires;
        @SerializedName("StrapWidth")
        @Expose
        private Object strapWidth;
        @SerializedName("LayLength")
        @Expose
        private Object layLength;
        @SerializedName("CableConductorLocation")
        @Expose
        private Integer cableConductorLocation;
        @SerializedName("CableSize")
        @Expose
        private Integer cableSize;
        @SerializedName("Size_mm2")
        @Expose
        private Float sizeMm2;
        @SerializedName("Diameter")
        @Expose
        private Float diameter;
        @SerializedName("ConstructionType")
        @Expose
        private Integer constructionType;
        @SerializedName("NumberOfStrands")
        @Expose
        private Integer numberOfStrands;
        @SerializedName("CableSheathLocation")
        @Expose
        private Object cableSheathLocation;
        @SerializedName("SheathType")
        @Expose
        private Object sheathType;
        @SerializedName("TapeThickness")
        @Expose
        private Object tapeThickness;
        @SerializedName("NumberOfTapes")
        @Expose
        private Object numberOfTapes;
        @SerializedName("TapeWidth")
        @Expose
        private Object tapeWidth;
        @SerializedName("OverlapRatio")
        @Expose
        private Object overlapRatio;

        public String getDeviceNumber() {
            return deviceNumber;
        }

        public void setDeviceNumber(String deviceNumber) {
            this.deviceNumber = deviceNumber;
        }

        public String getNetworkId() {
            return networkId;
        }

        public void setNetworkId(String networkId) {
            this.networkId = networkId;
        }

        public String getCableId() {
            return cableId;
        }

        public void setCableId(String cableId) {
            this.cableId = cableId;
        }

        public Float getLength() {
            return length;
        }

        public void setLength(Float length) {
            this.length = length;
        }

        public Integer getNumberOfCableInParallel() {
            return numberOfCableInParallel;
        }

        public void setNumberOfCableInParallel(Integer numberOfCableInParallel) {
            this.numberOfCableInParallel = numberOfCableInParallel;
        }

        public Integer getCTConnection() {
            return cTConnection;
        }

        public void setCTConnection(Integer cTConnection) {
            this.cTConnection = cTConnection;
        }

        public Object getX() {
            return x;
        }

        public void setX(Object x) {
            this.x = x;
        }

        public Object getY() {
            return y;
        }

        public void setY(Object y) {
            this.y = y;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Float getNominalRating() {
            return nominalRating;
        }

        public void setNominalRating(Float nominalRating) {
            this.nominalRating = nominalRating;
        }

        public Float getFirstRating() {
            return firstRating;
        }

        public void setFirstRating(Float firstRating) {
            this.firstRating = firstRating;
        }

        public Float getSecondRating() {
            return secondRating;
        }

        public void setSecondRating(Float secondRating) {
            this.secondRating = secondRating;
        }

        public Float getThirdRating() {
            return thirdRating;
        }

        public void setThirdRating(Float thirdRating) {
            this.thirdRating = thirdRating;
        }

        public Float getFourthRating() {
            return fourthRating;
        }

        public void setFourthRating(Float fourthRating) {
            this.fourthRating = fourthRating;
        }

        public Integer getHarmonicModel() {
            return harmonicModel;
        }

        public void setHarmonicModel(Integer harmonicModel) {
            this.harmonicModel = harmonicModel;
        }

        public Object getTCCRepositoryID() {
            return tCCRepositoryID;
        }

        public void setTCCRepositoryID(Object tCCRepositoryID) {
            this.tCCRepositoryID = tCCRepositoryID;
        }

        public Float getOperatingTemperature() {
            return operatingTemperature;
        }

        public void setOperatingTemperature(Float operatingTemperature) {
            this.operatingTemperature = operatingTemperature;
        }

        public Integer getNeutral1Type() {
            return neutral1Type;
        }

        public void setNeutral1Type(Integer neutral1Type) {
            this.neutral1Type = neutral1Type;
        }

        public Integer getNeutral2Type() {
            return neutral2Type;
        }

        public void setNeutral2Type(Integer neutral2Type) {
            this.neutral2Type = neutral2Type;
        }

        public Integer getNeutral3Type() {
            return neutral3Type;
        }

        public void setNeutral3Type(Integer neutral3Type) {
            this.neutral3Type = neutral3Type;
        }

        public Object getNeutral1ID() {
            return neutral1ID;
        }

        public void setNeutral1ID(Object neutral1ID) {
            this.neutral1ID = neutral1ID;
        }

        public Object getNeutral2ID() {
            return neutral2ID;
        }

        public void setNeutral2ID(Object neutral2ID) {
            this.neutral2ID = neutral2ID;
        }

        public Object getNeutral3ID() {
            return neutral3ID;
        }

        public void setNeutral3ID(Object neutral3ID) {
            this.neutral3ID = neutral3ID;
        }

        public Float getAmpacityDeratingFactor() {
            return ampacityDeratingFactor;
        }

        public void setAmpacityDeratingFactor(Float ampacityDeratingFactor) {
            this.ampacityDeratingFactor = ampacityDeratingFactor;
        }

        public Integer getFlowConstraintActive() {
            return flowConstraintActive;
        }

        public void setFlowConstraintActive(Integer flowConstraintActive) {
            this.flowConstraintActive = flowConstraintActive;
        }

        public Integer getFlowConstraintUnit() {
            return flowConstraintUnit;
        }

        public void setFlowConstraintUnit(Integer flowConstraintUnit) {
            this.flowConstraintUnit = flowConstraintUnit;
        }

        public Float getMaximumFlow() {
            return maximumFlow;
        }

        public void setMaximumFlow(Float maximumFlow) {
            this.maximumFlow = maximumFlow;
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

        public Integer getLocation() {
            return location;
        }

        public void setLocation(Integer location) {
            this.location = location;
        }

        public Object getDeviceStage() {
            return deviceStage;
        }

        public void setDeviceStage(Object deviceStage) {
            this.deviceStage = deviceStage;
        }

        public Integer getFlags() {
            return flags;
        }

        public void setFlags(Integer flags) {
            this.flags = flags;
        }

        public Integer getComponentMask() {
            return componentMask;
        }

        public void setComponentMask(Integer componentMask) {
            this.componentMask = componentMask;
        }

        public Integer getInitFromEquipFlags() {
            return initFromEquipFlags;
        }

        public void setInitFromEquipFlags(Integer initFromEquipFlags) {
            this.initFromEquipFlags = initFromEquipFlags;
        }

        public String getFromNodeId() {
            return fromNodeId;
        }

        public void setFromNodeId(String fromNodeId) {
            this.fromNodeId = fromNodeId;
        }

        public Integer getFromNodeConnectorIndex() {
            return fromNodeConnectorIndex;
        }

        public void setFromNodeConnectorIndex(Integer fromNodeConnectorIndex) {
            this.fromNodeConnectorIndex = fromNodeConnectorIndex;
        }

        public String getToNodeId() {
            return toNodeId;
        }

        public void setToNodeId(String toNodeId) {
            this.toNodeId = toNodeId;
        }

        public Integer getToNodeConnectorIndex() {
            return toNodeConnectorIndex;
        }

        public void setToNodeConnectorIndex(Integer toNodeConnectorIndex) {
            this.toNodeConnectorIndex = toNodeConnectorIndex;
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

        public Object getStructureId() {
            return structureId;
        }

        public void setStructureId(Object structureId) {
            this.structureId = structureId;
        }

        public Integer getBreakPointIndex() {
            return breakPointIndex;
        }

        public void setBreakPointIndex(Integer breakPointIndex) {
            this.breakPointIndex = breakPointIndex;
        }

        public Integer getBreakPointLocation() {
            return breakPointLocation;
        }

        public void setBreakPointLocation(Integer breakPointLocation) {
            this.breakPointLocation = breakPointLocation;
        }

        public String getFROMNodeId() {
            return fROMNodeId;
        }

        public void setFROMNodeId(String fROMNodeId) {
            this.fROMNodeId = fROMNodeId;
        }

        public String getInsulationType() {
            return insulationType;
        }

        public void setInsulationType(String insulationType) {
            this.fROMNodeId = insulationType;
        }

        public Integer getFROMComponentMask() {
            return fROMComponentMask;
        }

        public void setFROMComponentMask(Integer fROMComponentMask) {
            this.fROMComponentMask = fROMComponentMask;
        }

        public Double getFROMNodeIdX() {
            return fROMNodeIdX;
        }

        public void setFROMNodeIdX(Double fROMNodeIdX) {
            this.fROMNodeIdX = Double.valueOf(fROMNodeIdX);
        }

        public Double getFROMNodeIdY() {
            return fROMNodeIdY;
        }

        public void setFROMNodeIdY(Double fROMNodeIdY) {
            this.fROMNodeIdY = fROMNodeIdY;
        }

        public Object getFROMZoneId() {
            return fROMZoneId;
        }

        public void setFROMZoneId(Object fROMZoneId) {
            this.fROMZoneId = fROMZoneId;
        }

        public Float getFROMUserDefinedBaseVoltage() {
            return fROMUserDefinedBaseVoltage;
        }

        public void setFROMUserDefinedBaseVoltage(Float fROMUserDefinedBaseVoltage) {
            this.fROMUserDefinedBaseVoltage = fROMUserDefinedBaseVoltage;
        }

        public Object getFROMRatedVoltage() {
            return fROMRatedVoltage;
        }

        public void setFROMRatedVoltage(Object fROMRatedVoltage) {
            this.fROMRatedVoltage = fROMRatedVoltage;
        }

        public Object getFROMRatedCurrent() {
            return fROMRatedCurrent;
        }

        public void setFROMRatedCurrent(Object fROMRatedCurrent) {
            this.fROMRatedCurrent = fROMRatedCurrent;
        }

        public Object getFROMANSISymCurrent() {
            return fROMANSISymCurrent;
        }

        public void setFROMANSISymCurrent(Object fROMANSISymCurrent) {
            this.fROMANSISymCurrent = fROMANSISymCurrent;
        }

        public Object getFROMANSIAsymCurrent() {
            return fROMANSIAsymCurrent;
        }

        public void setFROMANSIAsymCurrent(Object fROMANSIAsymCurrent) {
            this.fROMANSIAsymCurrent = fROMANSIAsymCurrent;
        }

        public Object getFROMPeakCurrent() {
            return fROMPeakCurrent;
        }

        public void setFROMPeakCurrent(Object fROMPeakCurrent) {
            this.fROMPeakCurrent = fROMPeakCurrent;
        }

        public Object getFROMStandard() {
            return fROMStandard;
        }

        public void setFROMStandard(Object fROMStandard) {
            this.fROMStandard = fROMStandard;
        }

        public Object getFROMTestCircuitPowerFactor() {
            return fROMTestCircuitPowerFactor;
        }

        public void setFROMTestCircuitPowerFactor(Object fROMTestCircuitPowerFactor) {
            this.fROMTestCircuitPowerFactor = fROMTestCircuitPowerFactor;
        }

        public Integer getInstallation() {
            return installation;
        }

        public void setInstallation(Integer installation) {
            this.installation = installation;
        }

        public String getTONodeId() {
            return tONodeId;
        }

        public void setTONodeId(String tONodeId) {
            this.tONodeId = tONodeId;
        }

        public Integer getTOComponentMask() {
            return tOComponentMask;
        }

        public void setTOComponentMask(Integer tOComponentMask) {
            this.tOComponentMask = tOComponentMask;
        }

        public Double getTONodeIdX() {
            return tONodeIdX;
        }

        public void setTONodeIdX(Double tONodeIdX) {
            this.tONodeIdX = tONodeIdX;
        }

        public Float getTONodeIdY() {
            return tONodeIdY;
        }

        public void setTONodeIdY(Float tONodeIdY) {
            this.tONodeIdY = tONodeIdY;
        }

        public Object getTOZoneId() {
            return tOZoneId;
        }

        public void setTOZoneId(Object tOZoneId) {
            this.tOZoneId = tOZoneId;
        }

        public Float getTOUserDefinedBaseVoltage() {
            return tOUserDefinedBaseVoltage;
        }

        public void setTOUserDefinedBaseVoltage(Float tOUserDefinedBaseVoltage) {
            this.tOUserDefinedBaseVoltage = tOUserDefinedBaseVoltage;
        }

        public Object getTORatedVoltage() {
            return tORatedVoltage;
        }

        public void setTORatedVoltage(Object tORatedVoltage) {
            this.tORatedVoltage = tORatedVoltage;
        }

        public Object getTORatedCurrent() {
            return tORatedCurrent;
        }

        public void setTORatedCurrent(Object tORatedCurrent) {
            this.tORatedCurrent = tORatedCurrent;
        }

        public Object getTOANSISymCurrent() {
            return tOANSISymCurrent;
        }

        public void setTOANSISymCurrent(Object tOANSISymCurrent) {
            this.tOANSISymCurrent = tOANSISymCurrent;
        }

        public Object getTOANSIAsymCurrent() {
            return tOANSIAsymCurrent;
        }

        public void setTOANSIAsymCurrent(Object tOANSIAsymCurrent) {
            this.tOANSIAsymCurrent = tOANSIAsymCurrent;
        }

        public Object getTOPeakCurrent() {
            return tOPeakCurrent;
        }

        public void setTOPeakCurrent(Object tOPeakCurrent) {
            this.tOPeakCurrent = tOPeakCurrent;
        }

        public Object getTOStandard() {
            return tOStandard;
        }

        public void setTOStandard(Object tOStandard) {
            this.tOStandard = tOStandard;
        }

        public Object getTOTestCircuitPowerFactor() {
            return tOTestCircuitPowerFactor;
        }

        public void setTOTestCircuitPowerFactor(Object tOTestCircuitPowerFactor) {
            this.tOTestCircuitPowerFactor = tOTestCircuitPowerFactor;
        }

        public Integer getTOInstallation() {
            return tOInstallation;
        }

        public void setTOInstallation(Integer tOInstallation) {
            this.tOInstallation = tOInstallation;
        }

        public String getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(String equipmentId) {
            this.equipmentId = equipmentId;
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

        public Float getWithstandRating() {
            return withstandRating;
        }

        public void setWithstandRating(Float withstandRating) {
            this.withstandRating = withstandRating;
        }

        public Float getZeroSequenceShuntSusceptance() {
            return zeroSequenceShuntSusceptance;
        }

        public void setZeroSequenceShuntSusceptance(Float zeroSequenceShuntSusceptance) {
            this.zeroSequenceShuntSusceptance = zeroSequenceShuntSusceptance;
        }

        public Float getLevelKV() {
            return levelKV;
        }

        public void setLevelKV(Float levelKV) {
            this.levelKV = levelKV;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public Integer getCableType() {
            return cableType;
        }

        public void setCableType(Integer cableType) {
            this.cableType = cableType;
        }

        public Integer getNumberOfGroundingConductors() {
            return numberOfGroundingConductors;
        }

        public void setNumberOfGroundingConductors(Integer numberOfGroundingConductors) {
            this.numberOfGroundingConductors = numberOfGroundingConductors;
        }

        public Integer getConcentricNeutralBeforeSheath() {
            return concentricNeutralBeforeSheath;
        }

        public void setConcentricNeutralBeforeSheath(Integer concentricNeutralBeforeSheath) {
            this.concentricNeutralBeforeSheath = concentricNeutralBeforeSheath;
        }

        public Integer getUserDefinedImpedances() {
            return userDefinedImpedances;
        }

        public void setUserDefinedImpedances(Integer userDefinedImpedances) {
            this.userDefinedImpedances = userDefinedImpedances;
        }

        public Float getFrequency() {
            return frequency;
        }

        public void setFrequency(Float frequency) {
            this.frequency = frequency;
        }

        public Float getTemperature() {
            return temperature;
        }

        public void setTemperature(Float temperature) {
            this.temperature = temperature;
        }

        public String getImpedancesNote() {
            return impedancesNote;
        }

        public void setImpedancesNote(String impedancesNote) {
            this.impedancesNote = impedancesNote;
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

        public Integer getLockImpedance() {
            return lockImpedance;
        }

        public void setLockImpedance(Integer lockImpedance) {
            this.lockImpedance = lockImpedance;
        }

        public Object getCableConcentricNeutralLocation() {
            return cableConcentricNeutralLocation;
        }

        public void setCableConcentricNeutralLocation(Object cableConcentricNeutralLocation) {
            this.cableConcentricNeutralLocation = cableConcentricNeutralLocation;
        }

        public Object getMaterialID() {
            return materialID;
        }

        public void setMaterialID(Object materialID) {
            this.materialID = materialID;
        }

        public Object getLayerPosition() {
            return layerPosition;
        }

        public void setLayerPosition(Object layerPosition) {
            this.layerPosition = layerPosition;
        }

        public Object getThickness() {
            return thickness;
        }

        public void setThickness(Object thickness) {
            this.thickness = thickness;
        }

        public Object getConcentricNeutralsType() {
            return concentricNeutralsType;
        }

        public void setConcentricNeutralsType(Object concentricNeutralsType) {
            this.concentricNeutralsType = concentricNeutralsType;
        }

        public Object getNumberOfWires() {
            return numberOfWires;
        }

        public void setNumberOfWires(Object numberOfWires) {
            this.numberOfWires = numberOfWires;
        }

        public Object getStrapWidth() {
            return strapWidth;
        }

        public void setStrapWidth(Object strapWidth) {
            this.strapWidth = strapWidth;
        }

        public Object getLayLength() {
            return layLength;
        }

        public void setLayLength(Object layLength) {
            this.layLength = layLength;
        }

        public Integer getCableConductorLocation() {
            return cableConductorLocation;
        }

        public void setCableConductorLocation(Integer cableConductorLocation) {
            this.cableConductorLocation = cableConductorLocation;
        }

        public Integer getCableSize() {
            return cableSize;
        }

        public void setCableSize(Integer cableSize) {
            this.cableSize = cableSize;
        }

        public Float getSizeMm2() {
            return sizeMm2;
        }

        public void setSizeMm2(Float sizeMm2) {
            this.sizeMm2 = sizeMm2;
        }

        public Float getDiameter() {
            return diameter;
        }

        public void setDiameter(Float diameter) {
            this.diameter = diameter;
        }

        public Integer getConstructionType() {
            return constructionType;
        }

        public void setConstructionType(Integer constructionType) {
            this.constructionType = constructionType;
        }

        public Integer getNumberOfStrands() {
            return numberOfStrands;
        }

        public void setNumberOfStrands(Integer numberOfStrands) {
            this.numberOfStrands = numberOfStrands;
        }

        public Object getCableSheathLocation() {
            return cableSheathLocation;
        }

        public void setCableSheathLocation(Object cableSheathLocation) {
            this.cableSheathLocation = cableSheathLocation;
        }

        public Object getSheathType() {
            return sheathType;
        }

        public void setSheathType(Object sheathType) {
            this.sheathType = sheathType;
        }

        public Object getTapeThickness() {
            return tapeThickness;
        }

        public void setTapeThickness(Object tapeThickness) {
            this.tapeThickness = tapeThickness;
        }

        public Object getNumberOfTapes() {
            return numberOfTapes;
        }

        public void setNumberOfTapes(Object numberOfTapes) {
            this.numberOfTapes = numberOfTapes;
        }

        public Object getTapeWidth() {
            return tapeWidth;
        }

        public void setTapeWidth(Object tapeWidth) {
            this.tapeWidth = tapeWidth;
        }

        public Object getOverlapRatio() {
            return overlapRatio;
        }

        public void setOverlapRatio(Object overlapRatio) {
            this.overlapRatio = overlapRatio;
        }

    }

}
