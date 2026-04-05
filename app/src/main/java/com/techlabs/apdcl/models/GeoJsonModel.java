package com.techlabs.apdcl.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoJsonModel {

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

        @SerializedName("cables_data2")
        @Expose
        private CablesData2 cablesData2;
        @SerializedName("oh_data2")
        @Expose
        private OhData2 ohData2;
        @SerializedName("ohunbal_data2")
        @Expose
        private OhunbalData2 ohunbalData2;
        @SerializedName("cb_data2")
        @Expose
        private CbData2 cbData2;
        @SerializedName("sect_data2")
        @Expose
        private SectData2 sectData2;
        @SerializedName("dt_data2")
        @Expose
        private DtData2 dtData2;
        @SerializedName("fuse_data2")
        @Expose
        private FuseData2 fuseData2;
        @SerializedName("switch_data2")
        @Expose
        private SwitchData2 switchData2;
        @SerializedName("shunt_capacitor2")
        @Expose
        private ShuntCapacitor2 shuntCapacitor2;
        @SerializedName("spotload2")
        @Expose
        private Spotload2 spotload2;
        @SerializedName("source_data2")
        @Expose
        private SourceData2 sourceData2;

        public CablesData2 getCablesData2() {
            return cablesData2;
        }

        public void setCablesData2(CablesData2 cablesData2) {
            this.cablesData2 = cablesData2;
        }

        public OhData2 getOhData2() {
            return ohData2;
        }

        public void setOhData2(OhData2 ohData2) {
            this.ohData2 = ohData2;
        }

        public OhunbalData2 getOhunbalData2() {
            return ohunbalData2;
        }

        public void setOhunbalData2(OhunbalData2 ohunbalData2) {
            this.ohunbalData2 = ohunbalData2;
        }

        public CbData2 getCbData2() {
            return cbData2;
        }

        public void setCbData2(CbData2 cbData2) {
            this.cbData2 = cbData2;
        }

        public SectData2 getSectData2() {
            return sectData2;
        }

        public void setSectData2(SectData2 sectData2) {
            this.sectData2 = sectData2;
        }

        public DtData2 getDtData2() {
            return dtData2;
        }

        public void setDtData2(DtData2 dtData2) {
            this.dtData2 = dtData2;
        }

        public FuseData2 getFuseData2() {
            return fuseData2;
        }

        public void setFuseData2(FuseData2 fuseData2) {
            this.fuseData2 = fuseData2;
        }

        public SwitchData2 getSwitchData2() {
            return switchData2;
        }

        public void setSwitchData2(SwitchData2 switchData2) {
            this.switchData2 = switchData2;
        }

        public ShuntCapacitor2 getShuntCapacitor2() {
            return shuntCapacitor2;
        }

        public void setShuntCapacitor2(ShuntCapacitor2 shuntCapacitor2) {
            this.shuntCapacitor2 = shuntCapacitor2;
        }

        public Spotload2 getSpotload2() {
            return spotload2;
        }

        public void setSpotload2(Spotload2 spotload2) {
            this.spotload2 = spotload2;
        }

        public SourceData2 getSourceData2() {
            return sourceData2;
        }

        public void setSourceData2(SourceData2 sourceData2) {
            this.sourceData2 = sourceData2;
        }

        public class CablesData2 {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("features")
            @Expose
            private List<Feature> features;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Feature> getFeatures() {
                return features;
            }

            public void setFeatures(List<Feature> features) {
                this.features = features;
            }

            public class Feature {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("geometry")
                @Expose
                private Geometry geometry;
                @SerializedName("properties")
                @Expose
                private Properties properties;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Geometry getGeometry() {
                    return geometry;
                }

                public void setGeometry(Geometry geometry) {
                    this.geometry = geometry;
                }

                public Properties getProperties() {
                    return properties;
                }

                public void setProperties(Properties properties) {
                    this.properties = properties;
                }

                public class Geometry {

                    @SerializedName("type")
                    @Expose
                    private String type;
                    @SerializedName("coordinates")
                    @Expose
                    private List<List<Float>> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<List<Float>> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<List<Float>> coordinates) {
                        this.coordinates = coordinates;
                    }

                }

                public class Properties {

                    @SerializedName("SectionId")
                    @Expose
                    private String sectionId;
                    @SerializedName("NetworkId")
                    @Expose
                    private String networkId;
                    @SerializedName("DeviceNumber")
                    @Expose
                    private String deviceNumber;
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
                    @SerializedName("DeviceType")
                    @Expose
                    private Integer deviceType;
                    @SerializedName("Phase")
                    @Expose
                    private Integer phase;
                    @SerializedName("CableId")
                    @Expose
                    private String cableId;
                    @SerializedName("Length")
                    @Expose
                    private Float length;

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

                    public String getDeviceNumber() {
                        return deviceNumber;
                    }

                    public void setDeviceNumber(String deviceNumber) {
                        this.deviceNumber = deviceNumber;
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

                    public Integer getDeviceType() {
                        return deviceType;
                    }

                    public void setDeviceType(Integer deviceType) {
                        this.deviceType = deviceType;
                    }

                    public Integer getPhase() {
                        return phase;
                    }

                    public void setPhase(Integer phase) {
                        this.phase = phase;
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

                }

            }

        }

        public class OhData2 {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("features")
            @Expose
            private List<Feature__1> features;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Feature__1> getFeatures() {
                return features;
            }

            public void setFeatures(List<Feature__1> features) {
                this.features = features;
            }

            public class Feature__1 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("geometry")
                @Expose
                private Geometry__1 geometry;
                @SerializedName("properties")
                @Expose
                private Properties__1 properties;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Geometry__1 getGeometry() {
                    return geometry;
                }

                public void setGeometry(Geometry__1 geometry) {
                    this.geometry = geometry;
                }

                public Properties__1 getProperties() {
                    return properties;
                }

                public void setProperties(Properties__1 properties) {
                    this.properties = properties;
                }

                public class Geometry__1 {

                    @SerializedName("type")
                    @Expose
                    private String type;
                    @SerializedName("coordinates")
                    @Expose
                    private List<List<Float>> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<List<Float>> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<List<Float>> coordinates) {
                        this.coordinates = coordinates;
                    }

                }

                public class Properties__1 {

                    @SerializedName("SectionId")
                    @Expose
                    private String sectionId;
                    @SerializedName("NetworkId")
                    @Expose
                    private String networkId;
                    @SerializedName("DeviceNumber")
                    @Expose
                    private String deviceNumber;
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
                    @SerializedName("DeviceType")
                    @Expose
                    private Integer deviceType;
                    @SerializedName("Phase")
                    @Expose
                    private Integer phase;
                    @SerializedName("LineId")
                    @Expose
                    private String lineId;
                    @SerializedName("Length")
                    @Expose
                    private Float length;

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

                    public String getDeviceNumber() {
                        return deviceNumber;
                    }

                    public void setDeviceNumber(String deviceNumber) {
                        this.deviceNumber = deviceNumber;
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

                    public Integer getDeviceType() {
                        return deviceType;
                    }

                    public void setDeviceType(Integer deviceType) {
                        this.deviceType = deviceType;
                    }

                    public Integer getPhase() {
                        return phase;
                    }

                    public void setPhase(Integer phase) {
                        this.phase = phase;
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

                }

            }

        }

        public class OhunbalData2 {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("features")
            @Expose
            private List<Feature__2> features;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Feature__2> getFeatures() {
                return features;
            }

            public void setFeatures(List<Feature__2> features) {
                this.features = features;
            }

            public class Feature__2 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("geometry")
                @Expose
                private Geometry__2 geometry;
                @SerializedName("properties")
                @Expose
                private Properties__2 properties;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Geometry__2 getGeometry() {
                    return geometry;
                }

                public void setGeometry(Geometry__2 geometry) {
                    this.geometry = geometry;
                }

                public Properties__2 getProperties() {
                    return properties;
                }

                public void setProperties(Properties__2 properties) {
                    this.properties = properties;
                }

                public class Geometry__2 {

                    @SerializedName("type")
                    @Expose
                    private String type;
                    @SerializedName("coordinates")
                    @Expose
                    private List<List<Float>> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<List<Float>> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<List<Float>> coordinates) {
                        this.coordinates = coordinates;
                    }

                }

                public class Properties__2 {

                    @SerializedName("SectionId")
                    @Expose
                    private String sectionId;
                    @SerializedName("NetworkId")
                    @Expose
                    private String networkId;
                    @SerializedName("DeviceNumber")
                    @Expose
                    private String deviceNumber;
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
                    @SerializedName("DeviceType")
                    @Expose
                    private Integer deviceType;
                    @SerializedName("Phase")
                    @Expose
                    private Integer phase;
                    @SerializedName("LineId")
                    @Expose
                    private String lineId;
                    @SerializedName("Length")
                    @Expose
                    private Float length;

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

                    public String getDeviceNumber() {
                        return deviceNumber;
                    }

                    public void setDeviceNumber(String deviceNumber) {
                        this.deviceNumber = deviceNumber;
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

                    public Integer getDeviceType() {
                        return deviceType;
                    }

                    public void setDeviceType(Integer deviceType) {
                        this.deviceType = deviceType;
                    }

                    public Integer getPhase() {
                        return phase;
                    }

                    public void setPhase(Integer phase) {
                        this.phase = phase;
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

                }

            }

        }

        public class CbData2 {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("features")
            @Expose
            private List<Feature__3> features;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Feature__3> getFeatures() {
                return features;
            }

            public void setFeatures(List<Feature__3> features) {
                this.features = features;
            }

            public class Feature__3 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("geometry")
                @Expose
                private Geometry__3 geometry;
                @SerializedName("properties")
                @Expose
                private Properties__3 properties;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Geometry__3 getGeometry() {
                    return geometry;
                }

                public void setGeometry(Geometry__3 geometry) {
                    this.geometry = geometry;
                }

                public Properties__3 getProperties() {
                    return properties;
                }

                public void setProperties(Properties__3 properties) {
                    this.properties = properties;
                }

                public class Geometry__3 {

                    @SerializedName("type")
                    @Expose
                    private String type;
                    @SerializedName("coordinates")
                    @Expose
                    private List<Float> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<Float> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<Float> coordinates) {
                        this.coordinates = coordinates;
                    }

                }

                public class Properties__3 {

                    @SerializedName("SectionId")
                    @Expose
                    private String sectionId;
                    @SerializedName("NetworkId")
                    @Expose
                    private String networkId;
                    @SerializedName("DeviceNumber")
                    @Expose
                    private String deviceNumber;
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
                    @SerializedName("DeviceType")
                    @Expose
                    private Integer deviceType;
                    @SerializedName("EquipmentId")
                    @Expose
                    private String equipmentId;
                    @SerializedName("Phase")
                    @Expose
                    private Integer phase;
                    @SerializedName("RatedCurrent")
                    @Expose
                    private Float ratedCurrent;
                    @SerializedName("RatedVoltage")
                    @Expose
                    private Float ratedVoltage;

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

                    public String getDeviceNumber() {
                        return deviceNumber;
                    }

                    public void setDeviceNumber(String deviceNumber) {
                        this.deviceNumber = deviceNumber;
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

                    public Integer getDeviceType() {
                        return deviceType;
                    }

                    public void setDeviceType(Integer deviceType) {
                        this.deviceType = deviceType;
                    }

                    public String getEquipmentId() {
                        return equipmentId;
                    }

                    public void setEquipmentId(String equipmentId) {
                        this.equipmentId = equipmentId;
                    }

                    public Integer getPhase() {
                        return phase;
                    }

                    public void setPhase(Integer phase) {
                        this.phase = phase;
                    }

                    public Float getRatedCurrent() {
                        return ratedCurrent;
                    }

                    public void setRatedCurrent(Float ratedCurrent) {
                        this.ratedCurrent = ratedCurrent;
                    }

                    public Float getRatedVoltage() {
                        return ratedVoltage;
                    }

                    public void setRatedVoltage(Float ratedVoltage) {
                        this.ratedVoltage = ratedVoltage;
                    }

                }

            }

        }

        public class SectData2 {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("features")
            @Expose
            private List<Feature__4> features;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Feature__4> getFeatures() {
                return features;
            }

            public void setFeatures(List<Feature__4> features) {
                this.features = features;
            }

            public class Feature__4 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("geometry")
                @Expose
                private Geometry__4 geometry;
                @SerializedName("properties")
                @Expose
                private Properties__4 properties;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Geometry__4 getGeometry() {
                    return geometry;
                }

                public void setGeometry(Geometry__4 geometry) {
                    this.geometry = geometry;
                }

                public Properties__4 getProperties() {
                    return properties;
                }

                public void setProperties(Properties__4 properties) {
                    this.properties = properties;
                }

                public class Geometry__4 {

                    @SerializedName("type")
                    @Expose
                    private String type;
                    @SerializedName("coordinates")
                    @Expose
                    private List<Float> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<Float> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<Float> coordinates) {
                        this.coordinates = coordinates;
                    }

                }

                public class Properties__4 {

                    @SerializedName("SectionId")
                    @Expose
                    private String sectionId;
                    @SerializedName("NetworkId")
                    @Expose
                    private String networkId;
                    @SerializedName("DeviceNumber")
                    @Expose
                    private String deviceNumber;
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
                    @SerializedName("DeviceType")
                    @Expose
                    private Integer deviceType;
                    @SerializedName("EquipmentId")
                    @Expose
                    private String equipmentId;
                    @SerializedName("Phase")
                    @Expose
                    private Integer phase;
                    @SerializedName("RatedCurrent")
                    @Expose
                    private Float ratedCurrent;
                    @SerializedName("RatedVoltage")
                    @Expose
                    private Float ratedVoltage;

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

                    public String getDeviceNumber() {
                        return deviceNumber;
                    }

                    public void setDeviceNumber(String deviceNumber) {
                        this.deviceNumber = deviceNumber;
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

                    public Integer getDeviceType() {
                        return deviceType;
                    }

                    public void setDeviceType(Integer deviceType) {
                        this.deviceType = deviceType;
                    }

                    public String getEquipmentId() {
                        return equipmentId;
                    }

                    public void setEquipmentId(String equipmentId) {
                        this.equipmentId = equipmentId;
                    }

                    public Integer getPhase() {
                        return phase;
                    }

                    public void setPhase(Integer phase) {
                        this.phase = phase;
                    }

                    public Float getRatedCurrent() {
                        return ratedCurrent;
                    }

                    public void setRatedCurrent(Float ratedCurrent) {
                        this.ratedCurrent = ratedCurrent;
                    }

                    public Float getRatedVoltage() {
                        return ratedVoltage;
                    }

                    public void setRatedVoltage(Float ratedVoltage) {
                        this.ratedVoltage = ratedVoltage;
                    }

                }

            }

        }

        public class DtData2 {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("features")
            @Expose
            private List<Feature__5> features;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Feature__5> getFeatures() {
                return features;
            }

            public void setFeatures(List<Feature__5> features) {
                this.features = features;
            }

            public class Feature__5 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("geometry")
                @Expose
                private Geometry__5 geometry;
                @SerializedName("properties")
                @Expose
                private Properties__5 properties;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Geometry__5 getGeometry() {
                    return geometry;
                }

                public void setGeometry(Geometry__5 geometry) {
                    this.geometry = geometry;
                }

                public Properties__5 getProperties() {
                    return properties;
                }

                public void setProperties(Properties__5 properties) {
                    this.properties = properties;
                }

                public class Geometry__5 {

                    @SerializedName("type")
                    @Expose
                    private String type;
                    @SerializedName("coordinates")
                    @Expose
                    private List<Float> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<Float> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<Float> coordinates) {
                        this.coordinates = coordinates;
                    }

                }

                public class Properties__5 {

                    @SerializedName("SectionId")
                    @Expose
                    private String sectionId;
                    @SerializedName("NetworkId")
                    @Expose
                    private String networkId;
                    @SerializedName("DeviceNumber")
                    @Expose
                    private String deviceNumber;
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
                    @SerializedName("FromNode_X_l")
                    @Expose
                    private Float fromNodeXL;
                    @SerializedName("FromNode_Y_l")
                    @Expose
                    private Float fromNodeYL;
                    @SerializedName("ToNode_X_l")
                    @Expose
                    private Float toNodeXL;
                    @SerializedName("ToNode_Y_l")
                    @Expose
                    private Float toNodeYL;
                    @SerializedName("DeviceType")
                    @Expose
                    private Integer deviceType;
                    @SerializedName("EquipmentId")
                    @Expose
                    private String equipmentId;
                    @SerializedName("Phase")
                    @Expose
                    private Integer phase;
                    @SerializedName("NominalRatingKVA")
                    @Expose
                    private Float nominalRatingKVA;
                    @SerializedName("PrimaryVoltageKVLL")
                    @Expose
                    private Float primaryVoltageKVLL;
                    @SerializedName("SecondaryVoltageKVLL")
                    @Expose
                    private Float secondaryVoltageKVLL;

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

                    public String getDeviceNumber() {
                        return deviceNumber;
                    }

                    public void setDeviceNumber(String deviceNumber) {
                        this.deviceNumber = deviceNumber;
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

                    public Float getFromNodeXL() {
                        return fromNodeXL;
                    }

                    public void setFromNodeXL(Float fromNodeXL) {
                        this.fromNodeXL = fromNodeXL;
                    }

                    public Float getFromNodeYL() {
                        return fromNodeYL;
                    }

                    public void setFromNodeYL(Float fromNodeYL) {
                        this.fromNodeYL = fromNodeYL;
                    }

                    public Float getToNodeXL() {
                        return toNodeXL;
                    }

                    public void setToNodeXL(Float toNodeXL) {
                        this.toNodeXL = toNodeXL;
                    }

                    public Float getToNodeYL() {
                        return toNodeYL;
                    }

                    public void setToNodeYL(Float toNodeYL) {
                        this.toNodeYL = toNodeYL;
                    }

                    public Integer getDeviceType() {
                        return deviceType;
                    }

                    public void setDeviceType(Integer deviceType) {
                        this.deviceType = deviceType;
                    }

                    public String getEquipmentId() {
                        return equipmentId;
                    }

                    public void setEquipmentId(String equipmentId) {
                        this.equipmentId = equipmentId;
                    }

                    public Integer getPhase() {
                        return phase;
                    }

                    public void setPhase(Integer phase) {
                        this.phase = phase;
                    }

                    public Float getNominalRatingKVA() {
                        return nominalRatingKVA;
                    }

                    public void setNominalRatingKVA(Float nominalRatingKVA) {
                        this.nominalRatingKVA = nominalRatingKVA;
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

                }

            }

        }

        public class FuseData2 {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("features")
            @Expose
            private List<Feature__6> features;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Feature__6> getFeatures() {
                return features;
            }

            public void setFeatures(List<Feature__6> features) {
                this.features = features;
            }

            public class Feature__6 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("geometry")
                @Expose
                private Geometry__6 geometry;
                @SerializedName("properties")
                @Expose
                private Properties__6 properties;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Geometry__6 getGeometry() {
                    return geometry;
                }

                public void setGeometry(Geometry__6 geometry) {
                    this.geometry = geometry;
                }

                public Properties__6 getProperties() {
                    return properties;
                }

                public void setProperties(Properties__6 properties) {
                    this.properties = properties;
                }

                public class Geometry__6 {

                    @SerializedName("type")
                    @Expose
                    private String type;
                    @SerializedName("coordinates")
                    @Expose
                    private List<Float> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<Float> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<Float> coordinates) {
                        this.coordinates = coordinates;
                    }

                }

                public class Properties__6 {

                    @SerializedName("SectionId")
                    @Expose
                    private String sectionId;
                    @SerializedName("NetworkId")
                    @Expose
                    private String networkId;
                    @SerializedName("DeviceNumber")
                    @Expose
                    private String deviceNumber;
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
                    @SerializedName("DeviceType")
                    @Expose
                    private Integer deviceType;
                    @SerializedName("EquipmentId")
                    @Expose
                    private String equipmentId;
                    @SerializedName("Phase")
                    @Expose
                    private Integer phase;
                    @SerializedName("RatedCurrent")
                    @Expose
                    private Float ratedCurrent;
                    @SerializedName("RatedVoltage")
                    @Expose
                    private Float ratedVoltage;

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

                    public String getDeviceNumber() {
                        return deviceNumber;
                    }

                    public void setDeviceNumber(String deviceNumber) {
                        this.deviceNumber = deviceNumber;
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

                    public Integer getDeviceType() {
                        return deviceType;
                    }

                    public void setDeviceType(Integer deviceType) {
                        this.deviceType = deviceType;
                    }

                    public String getEquipmentId() {
                        return equipmentId;
                    }

                    public void setEquipmentId(String equipmentId) {
                        this.equipmentId = equipmentId;
                    }

                    public Integer getPhase() {
                        return phase;
                    }

                    public void setPhase(Integer phase) {
                        this.phase = phase;
                    }

                    public Float getRatedCurrent() {
                        return ratedCurrent;
                    }

                    public void setRatedCurrent(Float ratedCurrent) {
                        this.ratedCurrent = ratedCurrent;
                    }

                    public Float getRatedVoltage() {
                        return ratedVoltage;
                    }

                    public void setRatedVoltage(Float ratedVoltage) {
                        this.ratedVoltage = ratedVoltage;
                    }

                }

            }

        }

        public class ShuntCapacitor2 {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("features")
            @Expose
            private List<Feature__8> features;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Feature__8> getFeatures() {
                return features;
            }

            public void setFeatures(List<Feature__8> features) {
                this.features = features;
            }

            public class Feature__8 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("geometry")
                @Expose
                private Geometry__8 geometry;
                @SerializedName("properties")
                @Expose
                private Properties__8 properties;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Geometry__8 getGeometry() {
                    return geometry;
                }

                public void setGeometry(Geometry__8 geometry) {
                    this.geometry = geometry;
                }

                public Properties__8 getProperties() {
                    return properties;
                }

                public void setProperties(Properties__8 properties) {
                    this.properties = properties;
                }

                public class Geometry__8 {

                    @SerializedName("type")
                    @Expose
                    private String type;
                    @SerializedName("coordinates")
                    @Expose
                    private List<Float> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<Float> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<Float> coordinates) {
                        this.coordinates = coordinates;
                    }

                }

                public class Properties__8 {

                    @SerializedName("DeviceNumber")
                    @Expose
                    private String deviceNumber;
                    @SerializedName("DeviceType")
                    @Expose
                    private Integer deviceType;
                    @SerializedName("NetworkId")
                    @Expose
                    private String networkId;
                    @SerializedName("SectionId")
                    @Expose
                    private String sectionId;
                    @SerializedName("Lattitude")
                    @Expose
                    private Float lattitude;
                    @SerializedName("Longitude")
                    @Expose
                    private Float longitude;
                    @SerializedName("Phase")
                    @Expose
                    private Integer phase;
                    @SerializedName("EquipmentId")
                    @Expose
                    private String equipmentId;
                    @SerializedName("KVAR")
                    @Expose
                    private Float kvar;

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

                    public String getNetworkId() {
                        return networkId;
                    }

                    public void setNetworkId(String networkId) {
                        this.networkId = networkId;
                    }

                    public String getSectionId() {
                        return sectionId;
                    }

                    public void setSectionId(String sectionId) {
                        this.sectionId = sectionId;
                    }

                    public Float getLattitude() {
                        return lattitude;
                    }

                    public void setLattitude(Float lattitude) {
                        this.lattitude = lattitude;
                    }

                    public Float getLongitude() {
                        return longitude;
                    }

                    public void setLongitude(Float longitude) {
                        this.longitude = longitude;
                    }

                    public Integer getPhase() {
                        return phase;
                    }

                    public void setPhase(Integer phase) {
                        this.phase = phase;
                    }

                    public String getEquipmentId() {
                        return equipmentId;
                    }

                    public void setEquipmentId(String equipmentId) {
                        this.equipmentId = equipmentId;
                    }

                    public Float getKvar() {
                        return kvar;
                    }

                    public void setKvar(Float kvar) {
                        this.kvar = kvar;
                    }

                }

            }

        }

        public class SourceData2 {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("features")
            @Expose
            private List<Feature__10> features;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Feature__10> getFeatures() {
                return features;
            }

            public void setFeatures(List<Feature__10> features) {
                this.features = features;
            }

            public class Feature__10 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("geometry")
                @Expose
                private Geometry__10 geometry;
                @SerializedName("properties")
                @Expose
                private Properties__10 properties;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Geometry__10 getGeometry() {
                    return geometry;
                }

                public void setGeometry(Geometry__10 geometry) {
                    this.geometry = geometry;
                }

                public Properties__10 getProperties() {
                    return properties;
                }

                public void setProperties(Properties__10 properties) {
                    this.properties = properties;
                }

                public class Geometry__10 {

                    @SerializedName("type")
                    @Expose
                    private String type;
                    @SerializedName("coordinates")
                    @Expose
                    private List<Float> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<Float> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<Float> coordinates) {
                        this.coordinates = coordinates;
                    }

                }

                public class Properties__10 {

                    @SerializedName("NetworkId")
                    @Expose
                    private String networkId;
                    @SerializedName("NodeId")
                    @Expose
                    private String nodeId;

                    public String getNetworkId() {
                        return networkId;
                    }

                    public void setNetworkId(String networkId) {
                        this.networkId = networkId;
                    }

                    public String getNodeId() {
                        return nodeId;
                    }

                    public void setNodeId(String nodeId) {
                        this.nodeId = nodeId;
                    }

                }

            }

        }

        public class Spotload2 {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("features")
            @Expose
            private List<Feature__9> features;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Feature__9> getFeatures() {
                return features;
            }

            public void setFeatures(List<Feature__9> features) {
                this.features = features;
            }

            public class Feature__9 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("geometry")
                @Expose
                private Geometry__9 geometry;
                @SerializedName("properties")
                @Expose
                private Properties__9 properties;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Geometry__9 getGeometry() {
                    return geometry;
                }

                public void setGeometry(Geometry__9 geometry) {
                    this.geometry = geometry;
                }

                public Properties__9 getProperties() {
                    return properties;
                }

                public void setProperties(Properties__9 properties) {
                    this.properties = properties;
                }

                public class Geometry__9 {

                    @SerializedName("type")
                    @Expose
                    private String type;
                    @SerializedName("coordinates")
                    @Expose
                    private List<Float> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<Float> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<Float> coordinates) {
                        this.coordinates = coordinates;
                    }

                }

                public class Properties__9 {

                    @SerializedName("SectionId")
                    @Expose
                    private String sectionId;
                    @SerializedName("NetworkId")
                    @Expose
                    private String networkId;
                    @SerializedName("DeviceNumber")
                    @Expose
                    private String deviceNumber;
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
                    @SerializedName("DeviceType")
                    @Expose
                    private Integer deviceType;
                    @SerializedName("ConnectedKVA")
                    @Expose
                    private Float connectedKVA;
                    @SerializedName("KWHUsage")
                    @Expose
                    private Float kWHUsage;
                    @SerializedName("Customers")
                    @Expose
                    private Integer customers;

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

                    public String getDeviceNumber() {
                        return deviceNumber;
                    }

                    public void setDeviceNumber(String deviceNumber) {
                        this.deviceNumber = deviceNumber;
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

                    public Integer getDeviceType() {
                        return deviceType;
                    }

                    public void setDeviceType(Integer deviceType) {
                        this.deviceType = deviceType;
                    }

                    public Float getConnectedKVA() {
                        return connectedKVA;
                    }

                    public void setConnectedKVA(Float connectedKVA) {
                        this.connectedKVA = connectedKVA;
                    }

                    public Float getKWHUsage() {
                        return kWHUsage;
                    }

                    public void setKWHUsage(Float kWHUsage) {
                        this.kWHUsage = kWHUsage;
                    }

                    public Integer getCustomers() {
                        return customers;
                    }

                    public void setCustomers(Integer customers) {
                        this.customers = customers;
                    }

                }

            }

        }

        public class SwitchData2 {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("features")
            @Expose
            private List<Feature__7> features;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Feature__7> getFeatures() {
                return features;
            }

            public void setFeatures(List<Feature__7> features) {
                this.features = features;
            }

            public class Feature__7 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("geometry")
                @Expose
                private Geometry__7 geometry;
                @SerializedName("properties")
                @Expose
                private Properties__7 properties;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Geometry__7 getGeometry() {
                    return geometry;
                }

                public void setGeometry(Geometry__7 geometry) {
                    this.geometry = geometry;
                }

                public Properties__7 getProperties() {
                    return properties;
                }

                public void setProperties(Properties__7 properties) {
                    this.properties = properties;
                }

                public class Geometry__7 {

                    @SerializedName("type")
                    @Expose
                    private String type;
                    @SerializedName("coordinates")
                    @Expose
                    private List<Float> coordinates;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public List<Float> getCoordinates() {
                        return coordinates;
                    }

                    public void setCoordinates(List<Float> coordinates) {
                        this.coordinates = coordinates;
                    }

                }

                public class Properties__7 {

                    @SerializedName("SectionId")
                    @Expose
                    private String sectionId;
                    @SerializedName("NetworkId")
                    @Expose
                    private String networkId;
                    @SerializedName("DeviceNumber")
                    @Expose
                    private String deviceNumber;
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
                    @SerializedName("DeviceType")
                    @Expose
                    private Integer deviceType;
                    @SerializedName("EquipmentId")
                    @Expose
                    private String equipmentId;
                    @SerializedName("Phase")
                    @Expose
                    private Integer phase;
                    @SerializedName("RatedCurrent")
                    @Expose
                    private Float ratedCurrent;
                    @SerializedName("RatedVoltage")
                    @Expose
                    private Float ratedVoltage;

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

                    public String getDeviceNumber() {
                        return deviceNumber;
                    }

                    public void setDeviceNumber(String deviceNumber) {
                        this.deviceNumber = deviceNumber;
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

                    public Integer getDeviceType() {
                        return deviceType;
                    }

                    public void setDeviceType(Integer deviceType) {
                        this.deviceType = deviceType;
                    }

                    public String getEquipmentId() {
                        return equipmentId;
                    }

                    public void setEquipmentId(String equipmentId) {
                        this.equipmentId = equipmentId;
                    }

                    public Integer getPhase() {
                        return phase;
                    }

                    public void setPhase(Integer phase) {
                        this.phase = phase;
                    }

                    public Float getRatedCurrent() {
                        return ratedCurrent;
                    }

                    public void setRatedCurrent(Float ratedCurrent) {
                        this.ratedCurrent = ratedCurrent;
                    }

                    public Float getRatedVoltage() {
                        return ratedVoltage;
                    }

                    public void setRatedVoltage(Float ratedVoltage) {
                        this.ratedVoltage = ratedVoltage;
                    }

                }

            }

        }

    }

}





