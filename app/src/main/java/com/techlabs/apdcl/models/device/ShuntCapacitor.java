package com.techlabs.apdcl.models.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class ShuntCapacitor {

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
        @SerializedName("EquipmentId")
        @Expose
        private String equipmentId;
        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("ConnectionConfiguration")
        @Expose
        private Integer connectionConfiguration;
        @SerializedName("KVARA")
        @Expose
        private Float kvara;
        @SerializedName("KVARB")
        @Expose
        private Float kvarb;
        @SerializedName("KVARC")
        @Expose
        private Float kvarc;
        @SerializedName("KVLN")
        @Expose
        private Float kvln;
        @SerializedName("CapacitorControlType")
        @Expose
        private Integer capacitorControlType;
        @SerializedName("OnValueA")
        @Expose
        private Object onValueA;
        @SerializedName("OnValueB")
        @Expose
        private Object onValueB;
        @SerializedName("OnValueC")
        @Expose
        private Object onValueC;
        @SerializedName("OffValueA")
        @Expose
        private Object offValueA;
        @SerializedName("OffValueB")
        @Expose
        private Object offValueB;
        @SerializedName("OffValueC")
        @Expose
        private Object offValueC;
        @SerializedName("SwitchingMode")
        @Expose
        private Object switchingMode;
        @SerializedName("InitiallyClosedPhase")
        @Expose
        private Object initiallyClosedPhase;
        @SerializedName("CurrentClosedPhase")
        @Expose
        private Object currentClosedPhase;
        @SerializedName("ControlledPhase")
        @Expose
        private Object controlledPhase;
        @SerializedName("SensorLocation")
        @Expose
        private Object sensorLocation;
        @SerializedName("ControlledNodeId")
        @Expose
        private Object controlledNodeId;
        @SerializedName("SymbolSize")
        @Expose
        private Float symbolSize;
        @SerializedName("LossesA")
        @Expose
        private Float lossesA;
        @SerializedName("LossesB")
        @Expose
        private Float lossesB;
        @SerializedName("LossesC")
        @Expose
        private Float lossesC;
        @SerializedName("ByPhase")
        @Expose
        private Integer byPhase;
        @SerializedName("SwitchedKVARA")
        @Expose
        private Float switchedKVARA;
        @SerializedName("SwitchedKVARB")
        @Expose
        private Float switchedKVARB;
        @SerializedName("SwitchedKVARC")
        @Expose
        private Float switchedKVARC;
        @SerializedName("SwitchedLossesA")
        @Expose
        private Float switchedLossesA;
        @SerializedName("SwitchedLossesB")
        @Expose
        private Float switchedLossesB;
        @SerializedName("SwitchedLossesC")
        @Expose
        private Float switchedLossesC;
        @SerializedName("VoltageOverride")
        @Expose
        private Integer voltageOverride;
        @SerializedName("VoltageOverrideOn")
        @Expose
        private Float voltageOverrideOn;
        @SerializedName("VoltageOverrideOff")
        @Expose
        private Float voltageOverrideOff;
        @SerializedName("VoltageOverrideDeadband")
        @Expose
        private Float voltageOverrideDeadband;
        @SerializedName("CTConnection")
        @Expose
        private Integer cTConnection;
        @SerializedName("InterruptingRating")
        @Expose
        private Float interruptingRating;
        @SerializedName("PythonDeviceScriptID")
        @Expose
        private Object pythonDeviceScriptID;
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
        private String zoneId;
        @SerializedName("StructureId")
        @Expose
        private Object structureId;
        @SerializedName("BreakPointIndex")
        @Expose
        private Integer breakPointIndex;
        @SerializedName("BreakPointLocation")
        @Expose
        private Integer breakPointLocation;
        @SerializedName("FROMX")
        @Expose
        private Float fromx;
        @SerializedName("FROMY")
        @Expose
        private Float fromy;
        @SerializedName("TOX")
        @Expose
        private Float tox;
        @SerializedName("TOY")
        @Expose
        private Float toy;
        @SerializedName("RatedKVAR")
        @Expose
        private Float ratedKVAR;
        @SerializedName("RatedVoltageKVLL")
        @Expose
        private Float ratedVoltageKVLL;
        @SerializedName("CostForFixedBank")
        @Expose
        private Float costForFixedBank;
        @SerializedName("CostForSwitchedBank")
        @Expose
        private Float costForSwitchedBank;
        @SerializedName("LossesKW")
        @Expose
        private Float lossesKW;
        @SerializedName("PhaseType")
        @Expose
        private Integer phaseType;
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

        public String getNetworkId() {
            return networkId;
        }

        public void setNetworkId(String networkId) {
            this.networkId = networkId;
        }

        public String getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(String equipmentId) {
            this.equipmentId = equipmentId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getConnectionConfiguration() {
            return connectionConfiguration;
        }

        public void setConnectionConfiguration(Integer connectionConfiguration) {
            this.connectionConfiguration = connectionConfiguration;
        }

        public Float getKvara() {
            return kvara;
        }

        public void setKvara(Float kvara) {
            this.kvara = kvara;
        }

        public Float getKvarb() {
            return kvarb;
        }

        public void setKvarb(Float kvarb) {
            this.kvarb = kvarb;
        }

        public Float getKvarc() {
            return kvarc;
        }

        public void setKvarc(Float kvarc) {
            this.kvarc = kvarc;
        }

        public Float getKvln() {
            return kvln;
        }

        public void setKvln(Float kvln) {
            this.kvln = kvln;
        }

        public Integer getCapacitorControlType() {
            return capacitorControlType;
        }

        public void setCapacitorControlType(Integer capacitorControlType) {
            this.capacitorControlType = capacitorControlType;
        }

        public Object getOnValueA() {
            return onValueA;
        }

        public void setOnValueA(Object onValueA) {
            this.onValueA = onValueA;
        }

        public Object getOnValueB() {
            return onValueB;
        }

        public void setOnValueB(Object onValueB) {
            this.onValueB = onValueB;
        }

        public Object getOnValueC() {
            return onValueC;
        }

        public void setOnValueC(Object onValueC) {
            this.onValueC = onValueC;
        }

        public Object getOffValueA() {
            return offValueA;
        }

        public void setOffValueA(Object offValueA) {
            this.offValueA = offValueA;
        }

        public Object getOffValueB() {
            return offValueB;
        }

        public void setOffValueB(Object offValueB) {
            this.offValueB = offValueB;
        }

        public Object getOffValueC() {
            return offValueC;
        }

        public void setOffValueC(Object offValueC) {
            this.offValueC = offValueC;
        }

        public Object getSwitchingMode() {
            return switchingMode;
        }

        public void setSwitchingMode(Object switchingMode) {
            this.switchingMode = switchingMode;
        }

        public Object getInitiallyClosedPhase() {
            return initiallyClosedPhase;
        }

        public void setInitiallyClosedPhase(Object initiallyClosedPhase) {
            this.initiallyClosedPhase = initiallyClosedPhase;
        }

        public Object getCurrentClosedPhase() {
            return currentClosedPhase;
        }

        public void setCurrentClosedPhase(Object currentClosedPhase) {
            this.currentClosedPhase = currentClosedPhase;
        }

        public Object getControlledPhase() {
            return controlledPhase;
        }

        public void setControlledPhase(Object controlledPhase) {
            this.controlledPhase = controlledPhase;
        }

        public Object getSensorLocation() {
            return sensorLocation;
        }

        public void setSensorLocation(Object sensorLocation) {
            this.sensorLocation = sensorLocation;
        }

        public Object getControlledNodeId() {
            return controlledNodeId;
        }

        public void setControlledNodeId(Object controlledNodeId) {
            this.controlledNodeId = controlledNodeId;
        }

        public Float getSymbolSize() {
            return symbolSize;
        }

        public void setSymbolSize(Float symbolSize) {
            this.symbolSize = symbolSize;
        }

        public Float getLossesA() {
            return lossesA;
        }

        public void setLossesA(Float lossesA) {
            this.lossesA = lossesA;
        }

        public Float getLossesB() {
            return lossesB;
        }

        public void setLossesB(Float lossesB) {
            this.lossesB = lossesB;
        }

        public Float getLossesC() {
            return lossesC;
        }

        public void setLossesC(Float lossesC) {
            this.lossesC = lossesC;
        }

        public Integer getByPhase() {
            return byPhase;
        }

        public void setByPhase(Integer byPhase) {
            this.byPhase = byPhase;
        }

        public Float getSwitchedKVARA() {
            return switchedKVARA;
        }

        public void setSwitchedKVARA(Float switchedKVARA) {
            this.switchedKVARA = switchedKVARA;
        }

        public Float getSwitchedKVARB() {
            return switchedKVARB;
        }

        public void setSwitchedKVARB(Float switchedKVARB) {
            this.switchedKVARB = switchedKVARB;
        }

        public Float getSwitchedKVARC() {
            return switchedKVARC;
        }

        public void setSwitchedKVARC(Float switchedKVARC) {
            this.switchedKVARC = switchedKVARC;
        }

        public Float getSwitchedLossesA() {
            return switchedLossesA;
        }

        public void setSwitchedLossesA(Float switchedLossesA) {
            this.switchedLossesA = switchedLossesA;
        }

        public Float getSwitchedLossesB() {
            return switchedLossesB;
        }

        public void setSwitchedLossesB(Float switchedLossesB) {
            this.switchedLossesB = switchedLossesB;
        }

        public Float getSwitchedLossesC() {
            return switchedLossesC;
        }

        public void setSwitchedLossesC(Float switchedLossesC) {
            this.switchedLossesC = switchedLossesC;
        }

        public Integer getVoltageOverride() {
            return voltageOverride;
        }

        public void setVoltageOverride(Integer voltageOverride) {
            this.voltageOverride = voltageOverride;
        }

        public Float getVoltageOverrideOn() {
            return voltageOverrideOn;
        }

        public void setVoltageOverrideOn(Float voltageOverrideOn) {
            this.voltageOverrideOn = voltageOverrideOn;
        }

        public Float getVoltageOverrideOff() {
            return voltageOverrideOff;
        }

        public void setVoltageOverrideOff(Float voltageOverrideOff) {
            this.voltageOverrideOff = voltageOverrideOff;
        }

        public Float getVoltageOverrideDeadband() {
            return voltageOverrideDeadband;
        }

        public void setVoltageOverrideDeadband(Float voltageOverrideDeadband) {
            this.voltageOverrideDeadband = voltageOverrideDeadband;
        }

        public Integer getCTConnection() {
            return cTConnection;
        }

        public void setCTConnection(Integer cTConnection) {
            this.cTConnection = cTConnection;
        }

        public Float getInterruptingRating() {
            return interruptingRating;
        }

        public void setInterruptingRating(Float interruptingRating) {
            this.interruptingRating = interruptingRating;
        }

        public Object getPythonDeviceScriptID() {
            return pythonDeviceScriptID;
        }

        public void setPythonDeviceScriptID(Object pythonDeviceScriptID) {
            this.pythonDeviceScriptID = pythonDeviceScriptID;
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

        public String getZoneId() {
            return zoneId;
        }

        public void setZoneId(String zoneId) {
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

        public Float getFromx() {
            return fromx;
        }

        public void setFromx(Float fromx) {
            this.fromx = fromx;
        }

        public Float getFromy() {
            return fromy;
        }

        public void setFromy(Float fromy) {
            this.fromy = fromy;
        }

        public Float getTox() {
            return tox;
        }

        public void setTox(Float tox) {
            this.tox = tox;
        }

        public Float getToy() {
            return toy;
        }

        public void setToy(Float toy) {
            this.toy = toy;
        }

        public Float getRatedKVAR() {
            return ratedKVAR;
        }

        public void setRatedKVAR(Float ratedKVAR) {
            this.ratedKVAR = ratedKVAR;
        }

        public Float getRatedVoltageKVLL() {
            return ratedVoltageKVLL;
        }

        public void setRatedVoltageKVLL(Float ratedVoltageKVLL) {
            this.ratedVoltageKVLL = ratedVoltageKVLL;
        }

        public Float getCostForFixedBank() {
            return costForFixedBank;
        }

        public void setCostForFixedBank(Float costForFixedBank) {
            this.costForFixedBank = costForFixedBank;
        }

        public Float getCostForSwitchedBank() {
            return costForSwitchedBank;
        }

        public void setCostForSwitchedBank(Float costForSwitchedBank) {
            this.costForSwitchedBank = costForSwitchedBank;
        }

        public Float getLossesKW() {
            return lossesKW;
        }

        public void setLossesKW(Float lossesKW) {
            this.lossesKW = lossesKW;
        }

        public Integer getPhaseType() {
            return phaseType;
        }

        public void setPhaseType(Integer phaseType) {
            this.phaseType = phaseType;
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

/*package com.techlabs.apdcl.models.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShuntCapacitor {

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
        @SerializedName("EquipmentId")
        @Expose
        private String equipmentId;
        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("ConnectionConfiguration")
        @Expose
        private Integer connectionConfiguration;
        @SerializedName("KVARA")
        @Expose
        private Float kvara;
        @SerializedName("KVARB")
        @Expose
        private Float kvarb;
        @SerializedName("KVARC")
        @Expose
        private Float kvarc;
        @SerializedName("KVLN")
        @Expose
        private Float kvln;
        @SerializedName("CapacitorControlType")
        @Expose
        private Integer capacitorControlType;
        @SerializedName("OnValueA")
        @Expose
        private Object onValueA;
        @SerializedName("OnValueB")
        @Expose
        private Object onValueB;
        @SerializedName("OnValueC")
        @Expose
        private Object onValueC;
        @SerializedName("OffValueA")
        @Expose
        private Object offValueA;
        @SerializedName("OffValueB")
        @Expose
        private Object offValueB;
        @SerializedName("OffValueC")
        @Expose
        private Object offValueC;
        @SerializedName("SwitchingMode")
        @Expose
        private Object switchingMode;
        @SerializedName("InitiallyClosedPhase")
        @Expose
        private Object initiallyClosedPhase;
        @SerializedName("CurrentClosedPhase")
        @Expose
        private Object currentClosedPhase;
        @SerializedName("ControlledPhase")
        @Expose
        private Object controlledPhase;
        @SerializedName("SensorLocation")
        @Expose
        private Object sensorLocation;
        @SerializedName("ControlledNodeId")
        @Expose
        private Object controlledNodeId;
        @SerializedName("SymbolSize")
        @Expose
        private Float symbolSize;
        @SerializedName("LossesA")
        @Expose
        private Float lossesA;
        @SerializedName("LossesB")
        @Expose
        private Float lossesB;
        @SerializedName("LossesC")
        @Expose
        private Float lossesC;
        @SerializedName("ByPhase")
        @Expose
        private Integer byPhase;
        @SerializedName("SwitchedKVARA")
        @Expose
        private Float switchedKVARA;
        @SerializedName("SwitchedKVARB")
        @Expose
        private Float switchedKVARB;
        @SerializedName("SwitchedKVARC")
        @Expose
        private Float switchedKVARC;
        @SerializedName("SwitchedLossesA")
        @Expose
        private Float switchedLossesA;
        @SerializedName("SwitchedLossesB")
        @Expose
        private Float switchedLossesB;
        @SerializedName("SwitchedLossesC")
        @Expose
        private Float switchedLossesC;
        @SerializedName("VoltageOverride")
        @Expose
        private Integer voltageOverride;
        @SerializedName("VoltageOverrideOn")
        @Expose
        private Float voltageOverrideOn;
        @SerializedName("VoltageOverrideOff")
        @Expose
        private Float voltageOverrideOff;
        @SerializedName("VoltageOverrideDeadband")
        @Expose
        private Float voltageOverrideDeadband;
        @SerializedName("CTConnection")
        @Expose
        private Integer cTConnection;
        @SerializedName("InterruptingRating")
        @Expose
        private Float interruptingRating;
        @SerializedName("PythonDeviceScriptID")
        @Expose
        private Object pythonDeviceScriptID;
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
        @SerializedName("FROMX")
        @Expose
        private Double fromx;
        @SerializedName("FROMY")
        @Expose
        private Double fromy;
        @SerializedName("TOX")
        @Expose
        private Double tox;
        @SerializedName("TOY")
        @Expose
        private Double toy;
        @SerializedName("RatedKVAR")
        @Expose
        private Float ratedKVAR;
        @SerializedName("RatedVoltageKVLL")
        @Expose
        private Float ratedVoltageKVLL;
        @SerializedName("CostForFixedBank")
        @Expose
        private Float costForFixedBank;
        @SerializedName("CostForSwitchedBank")
        @Expose
        private Float costForSwitchedBank;
        @SerializedName("LossesKW")
        @Expose
        private Float lossesKW;
        @SerializedName("PhaseType")
        @Expose
        private Integer phaseType;
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
        @SerializedName("DeviceTypeLine")
        @Expose
        private Integer deviceTypeLine;

        @SerializedName("LineDeviceNumber")
        @Expose
        private Integer lineDeviceNumber;

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

        public String getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(String equipmentId) {
            this.equipmentId = equipmentId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getConnectionConfiguration() {
            return connectionConfiguration;
        }

        public void setConnectionConfiguration(Integer connectionConfiguration) {
            this.connectionConfiguration = connectionConfiguration;
        }

        public Float getKvara() {
            return kvara;
        }

        public void setKvara(Float kvara) {
            this.kvara = kvara;
        }

        public Float getKvarb() {
            return kvarb;
        }

        public void setKvarb(Float kvarb) {
            this.kvarb = kvarb;
        }

        public Float getKvarc() {
            return kvarc;
        }

        public void setKvarc(Float kvarc) {
            this.kvarc = kvarc;
        }

        public Float getKvln() {
            return kvln;
        }

        public void setKvln(Float kvln) {
            this.kvln = kvln;
        }

        public Integer getCapacitorControlType() {
            return capacitorControlType;
        }

        public void setCapacitorControlType(Integer capacitorControlType) {
            this.capacitorControlType = capacitorControlType;
        }

        public Object getOnValueA() {
            return onValueA;
        }

        public void setOnValueA(Object onValueA) {
            this.onValueA = onValueA;
        }

        public Object getOnValueB() {
            return onValueB;
        }

        public void setOnValueB(Object onValueB) {
            this.onValueB = onValueB;
        }

        public Object getOnValueC() {
            return onValueC;
        }

        public void setOnValueC(Object onValueC) {
            this.onValueC = onValueC;
        }

        public Object getOffValueA() {
            return offValueA;
        }

        public void setOffValueA(Object offValueA) {
            this.offValueA = offValueA;
        }

        public Object getOffValueB() {
            return offValueB;
        }

        public void setOffValueB(Object offValueB) {
            this.offValueB = offValueB;
        }

        public Object getOffValueC() {
            return offValueC;
        }

        public void setOffValueC(Object offValueC) {
            this.offValueC = offValueC;
        }

        public Object getSwitchingMode() {
            return switchingMode;
        }

        public void setSwitchingMode(Object switchingMode) {
            this.switchingMode = switchingMode;
        }

        public Object getInitiallyClosedPhase() {
            return initiallyClosedPhase;
        }

        public void setInitiallyClosedPhase(Object initiallyClosedPhase) {
            this.initiallyClosedPhase = initiallyClosedPhase;
        }

        public Object getCurrentClosedPhase() {
            return currentClosedPhase;
        }

        public void setCurrentClosedPhase(Object currentClosedPhase) {
            this.currentClosedPhase = currentClosedPhase;
        }

        public Object getControlledPhase() {
            return controlledPhase;
        }

        public void setControlledPhase(Object controlledPhase) {
            this.controlledPhase = controlledPhase;
        }

        public Object getSensorLocation() {
            return sensorLocation;
        }

        public void setSensorLocation(Object sensorLocation) {
            this.sensorLocation = sensorLocation;
        }

        public Object getControlledNodeId() {
            return controlledNodeId;
        }

        public void setControlledNodeId(Object controlledNodeId) {
            this.controlledNodeId = controlledNodeId;
        }

        public Float getSymbolSize() {
            return symbolSize;
        }

        public void setSymbolSize(Float symbolSize) {
            this.symbolSize = symbolSize;
        }

        public Float getLossesA() {
            return lossesA;
        }

        public void setLossesA(Float lossesA) {
            this.lossesA = lossesA;
        }

        public Float getLossesB() {
            return lossesB;
        }

        public void setLossesB(Float lossesB) {
            this.lossesB = lossesB;
        }

        public Float getLossesC() {
            return lossesC;
        }

        public void setLossesC(Float lossesC) {
            this.lossesC = lossesC;
        }

        public Integer getByPhase() {
            return byPhase;
        }

        public void setByPhase(Integer byPhase) {
            this.byPhase = byPhase;
        }

        public Float getSwitchedKVARA() {
            return switchedKVARA;
        }

        public void setSwitchedKVARA(Float switchedKVARA) {
            this.switchedKVARA = switchedKVARA;
        }

        public Float getSwitchedKVARB() {
            return switchedKVARB;
        }

        public void setSwitchedKVARB(Float switchedKVARB) {
            this.switchedKVARB = switchedKVARB;
        }

        public Float getSwitchedKVARC() {
            return switchedKVARC;
        }

        public void setSwitchedKVARC(Float switchedKVARC) {
            this.switchedKVARC = switchedKVARC;
        }

        public Float getSwitchedLossesA() {
            return switchedLossesA;
        }

        public void setSwitchedLossesA(Float switchedLossesA) {
            this.switchedLossesA = switchedLossesA;
        }

        public Float getSwitchedLossesB() {
            return switchedLossesB;
        }

        public void setSwitchedLossesB(Float switchedLossesB) {
            this.switchedLossesB = switchedLossesB;
        }

        public Float getSwitchedLossesC() {
            return switchedLossesC;
        }

        public void setSwitchedLossesC(Float switchedLossesC) {
            this.switchedLossesC = switchedLossesC;
        }

        public Integer getVoltageOverride() {
            return voltageOverride;
        }

        public void setVoltageOverride(Integer voltageOverride) {
            this.voltageOverride = voltageOverride;
        }

        public Float getVoltageOverrideOn() {
            return voltageOverrideOn;
        }

        public void setVoltageOverrideOn(Float voltageOverrideOn) {
            this.voltageOverrideOn = voltageOverrideOn;
        }

        public Float getVoltageOverrideOff() {
            return voltageOverrideOff;
        }

        public void setVoltageOverrideOff(Float voltageOverrideOff) {
            this.voltageOverrideOff = voltageOverrideOff;
        }

        public Float getVoltageOverrideDeadband() {
            return voltageOverrideDeadband;
        }

        public void setVoltageOverrideDeadband(Float voltageOverrideDeadband) {
            this.voltageOverrideDeadband = voltageOverrideDeadband;
        }

        public Integer getCTConnection() {
            return cTConnection;
        }

        public void setCTConnection(Integer cTConnection) {
            this.cTConnection = cTConnection;
        }

        public Float getInterruptingRating() {
            return interruptingRating;
        }

        public void setInterruptingRating(Float interruptingRating) {
            this.interruptingRating = interruptingRating;
        }

        public Object getPythonDeviceScriptID() {
            return pythonDeviceScriptID;
        }

        public void setPythonDeviceScriptID(Object pythonDeviceScriptID) {
            this.pythonDeviceScriptID = pythonDeviceScriptID;
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

        public Double getFromx() {
            return fromx;
        }

        public void setFromx(Double fromx) {
            this.fromx = fromx;
        }

        public Double getFromy() {
            return fromy;
        }

        public void setFromy(Double fromy) {
            this.fromy = fromy;
        }

        public Double getTox() {
            return tox;
        }

        public void setTox(Double tox) {
            this.tox = tox;
        }

        public Double getToy() {
            return toy;
        }

        public void setToy(Double toy) {
            this.toy = toy;
        }

        public Float getRatedKVAR() {
            return ratedKVAR;
        }

        public void setRatedKVAR(Float ratedKVAR) {
            this.ratedKVAR = ratedKVAR;
        }

        public Float getRatedVoltageKVLL() {
            return ratedVoltageKVLL;
        }

        public void setRatedVoltageKVLL(Float ratedVoltageKVLL) {
            this.ratedVoltageKVLL = ratedVoltageKVLL;
        }

        public Float getCostForFixedBank() {
            return costForFixedBank;
        }

        public void setCostForFixedBank(Float costForFixedBank) {
            this.costForFixedBank = costForFixedBank;
        }

        public Float getCostForSwitchedBank() {
            return costForSwitchedBank;
        }

        public void setCostForSwitchedBank(Float costForSwitchedBank) {
            this.costForSwitchedBank = costForSwitchedBank;
        }

        public Float getLossesKW() {
            return lossesKW;
        }

        public void setLossesKW(Float lossesKW) {
            this.lossesKW = lossesKW;
        }

        public Integer getPhaseType() {
            return phaseType;
        }

        public void setPhaseType(Integer phaseType) {
            this.phaseType = phaseType;
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

        public Integer getDeviceTypeLine() {
            return deviceTypeLine;
        }

        public void setDeviceTypeLine(Integer deviceTypeLine) {
            this.deviceTypeLine = deviceTypeLine;
        }

        public Integer getLineDeviceNumber() {
            return lineDeviceNumber;
        }

        public void setLineDeviceNumber(Integer lineDeviceNumber) {
            this.lineDeviceNumber = deviceTypeLine;
        }

    }

}*/


