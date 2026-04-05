package com.techlabs.apdcl.models.device;

import java.util.*;

import com.google.gson.annotations.*;

public class SpotLoad {

    @SerializedName("output")
    List<Output> output;
    public void setOutput(List<Output> output) {
        this.output = output;
    }
    public List<Output> getOutput() {
        return output;
    }

    public class Output {

        @SerializedName("NetworkId")
        String networkId;

        @SerializedName("DeviceNumber")
        String deviceNumber;

        @SerializedName("DeviceType")
        Integer deviceType;

        @SerializedName("SectionId")
        String sectionId;

        @SerializedName("Location")
        Integer location;

        @SerializedName("Phase")
        Integer phase;

        @SerializedName("LoadValue1")
        Float loadValue1;

        @SerializedName("LoadValue2")
        Float loadValue2;

        @SerializedName("KWHUsage")
        Integer kWHUsage;

        @SerializedName("ConnectedKVA")
        Float connectedKVA;

        @SerializedName("NumberOfCustomer")
        Float numberOfCustomer;

        @SerializedName("DeviceTypeLine")
        Integer deviceTypeLine;

        @SerializedName("LineDeviceNumber")
        String lineDeviceNumber;

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

        @SerializedName("CustomerData")
        List<CustomerData> customerData;

        public void setNetworkId(String NetworkId) {
            this.networkId = NetworkId;
        }
        public String getNetworkId() {
            return networkId;
        }

        public void setDeviceNumber(String DeviceNumber) {
            this.deviceNumber = DeviceNumber;
        }
        public String getDeviceNumber() {
            return deviceNumber;
        }

        public void setDeviceType(int DeviceType) {
            this.deviceType = DeviceType;
        }
        public int getDeviceType() {
            return deviceType;
        }

        public void setSectionId(String SectionId) {
            this.sectionId = SectionId;
        }
        public String getSectionId() {
            return sectionId;
        }

        public void setLocation(Integer Location) {
            this.location = Location;
        }
        public Integer getLocation() {
            return location;
        }

        public void setPhase(Integer phase) {
            this.phase = phase;
        }
        public Integer getPhase() {
            return phase;
        }

        public void setLoadValue1(Float LoadValue1) {
            this.loadValue1 = LoadValue1;
        }
        public Float getLoadValue1() {
            return loadValue1;
        }

        public void setLoadValue2(Float LoadValue2) {
            this.loadValue2 = LoadValue2;
        }
        public Float getLoadValue2() {
            return loadValue2;
        }

        public void setKWHUsage(Integer KWHUsage) {
            this.kWHUsage = KWHUsage;
        }
        public Integer getKWHUsage() {
            return kWHUsage;
        }

        public void setConnectedKVA(Float ConnectedKVA) {
            this.connectedKVA = ConnectedKVA;
        }
        public Float getConnectedKVA() {
            return connectedKVA;
        }

        public void setNumberOfCustomer(Float NumberOfCustomer) {
            this.numberOfCustomer = NumberOfCustomer;
        }
        public Float getNumberOfCustomer() {
            return numberOfCustomer;
        }

        public void setDeviceTypeLine(Integer DeviceTypeLine) {
            this.deviceTypeLine = DeviceTypeLine;
        }
        public Integer getDeviceTypeLine() {
            return deviceTypeLine;
        }

        public void setLineDeviceNumber(String LineDeviceNumber) {
            this.lineDeviceNumber = LineDeviceNumber;
        }
        public String getLineDeviceNumber() {
            return lineDeviceNumber;
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
        public void setCustomerData(List<CustomerData> CustomerData) {
            this.customerData = CustomerData;
        }
        public List<CustomerData> getCustomerData() {
            return customerData;
        }

        public class CustomerData {

            @SerializedName("CustomerNumber")
            String customerNumber;

            @SerializedName("ConsumerClassId")
            String consumerClassId;

            @SerializedName("LockDuringLoadAllocation")
            Integer lockDuringLoadAllocation;

            @SerializedName("Status")
            Integer status;

            @SerializedName("LoadYear")
            String loadYear;

            @SerializedName("NormalPriority")
            Integer normalPriority;

            @SerializedName("EmergencyPriority")
            Integer emergencyPriority;

            @SerializedName("Phase")
            Phase phase;

            @SerializedName("ActualKW")
             ActualKW actualKW;
            @SerializedName("PowerFactor")
            PowerFactor powerFactor;

            @SerializedName("ConnectedKVA")
            ConnectedKVA connectedKVA;

            @SerializedName("KWH")
            KWH kwh;

            @SerializedName("CustomerCount")
            CustomerCount customerCount;


            public void setCustomerNumber(String CustomerNumber) {
                this.customerNumber = CustomerNumber;
            }
            public String getCustomerNumber() {
                return customerNumber;
            }

            public void setConsumerClassId(String ConsumerClassId) {
                this.consumerClassId = ConsumerClassId;
            }
            public String getConsumerClassId() {
                return consumerClassId;
            }

            public void setLockDuringLoadAllocation(Integer LockDuringLoadAllocation) {
                this.lockDuringLoadAllocation = LockDuringLoadAllocation;
            }
            public Integer getLockDuringLoadAllocation() {
                return lockDuringLoadAllocation;
            }

            public void setStatus(Integer Status) {
                this.status = Status;
            }
            public Integer getStatus() {
                return status;
            }

            public void setLoadYear(String LoadYear) {
                this.loadYear = LoadYear;
            }
            public String getLoadYear() {
                return loadYear;
            }

            public void setNormalPriority(Integer NormalPriority) {
                this.normalPriority = NormalPriority;
            }
            public Integer getNormalPriority() {
                return normalPriority;
            }

            public void setEmergencyPriority(Integer EmergencyPriority) {
                this.emergencyPriority = EmergencyPriority;
            }
            public Integer getEmergencyPriority() {
                return emergencyPriority;
            }

            public void setPhase(Phase Phase) {
                this.phase = Phase;
            }
            public Phase getPhase() {
                return phase;
            }

            public void setActualKW(ActualKW actualKW) {
                this.actualKW = actualKW;
            }

            public ActualKW getActualKW() {
               return actualKW;
            }

            public void setPowerFactor(PowerFactor PowerFactor) {
                this.powerFactor = PowerFactor;
            }
            public PowerFactor getPowerFactor() {
                return powerFactor;
            }

            public void setConnectedKVA(ConnectedKVA ConnectedKVA) {
                this.connectedKVA = ConnectedKVA;
            }
            public ConnectedKVA getConnectedKVA() {
                return connectedKVA;
            }

            public void setKWH(KWH KWH) {
                this.kwh = KWH;
            }
            public KWH getKWH() {
                return kwh;
            }

            public void setCustomerCount(CustomerCount CustomerCount) {
                this.customerCount = CustomerCount;
            }
            public CustomerCount getCustomerCount() {
                return customerCount;
            }

            public class CustomerCount {

                @SerializedName("1")
                @Expose
                private Float _1;
                @SerializedName("2")
                @Expose
                private Float _2;
                @SerializedName("3")
                @Expose
                private Float _3;
                @SerializedName("7")
                @Expose
                private Float _7;

                public Float get1() {
                    return _1;
                }

                public void set1(Float _1) {
                    this._1 = _1;
                }

                public Float get2() {
                    return _2;
                }

                public void set2(Float _2) {
                    this._2 = _2;
                }

                public Float get3() {
                    return _3;
                }

                public void set3(Float _3) {
                    this._3 = _3;
                }

                public Float get7() {
                    return _7;
                }

                public void set7(Float _7) {
                    this._7 = _7;
                }

            }

            public class ConnectedKVA {

                @SerializedName("1")
                @Expose
                private Float _1;
                @SerializedName("2")
                @Expose
                private Float _2;
                @SerializedName("3")
                @Expose
                private Float _3;
                @SerializedName("7")
                @Expose
                private Float _7;

                public Float get1() {
                    return _1;
                }

                public void set1(Float _1) {
                    this._1 = _1;
                }

                public Float get2() {
                    return _2;
                }

                public void set2(Float _2) {
                    this._2 = _2;
                }

                public Float get3() {
                    return _3;
                }

                public void set3(Float _3) {
                    this._3 = _3;
                }

                public Float get7() {
                    return _7;
                }

                public void set7(Float _7) {
                    this._7 = _7;
                }
            }
            public class PowerFactor {

                @SerializedName("1")
                @Expose
                private Float _1;
                @SerializedName("2")
                @Expose
                private Float _2;
                @SerializedName("3")
                @Expose
                private Float _3;
                @SerializedName("7")
                @Expose
                private Float _7;

                public Float get1() {
                    return _1;
                }

                public void set1(Float _1) {
                    this._1 = _1;
                }

                public Float get2() {
                    return _2;
                }

                public void set2(Float _2) {
                    this._2 = _2;
                }

                public Float get3() {
                    return _3;
                }

                public void set3(Float _3) {
                    this._3 = _3;
                }

                public Float get7() {
                    return _7;
                }

                public void set7(Float _7) {
                    this._7 = _7;
                }


            }
            public class KWH {

                @SerializedName("1")
                @Expose
                private Float _1;
                @SerializedName("2")
                @Expose
                private Float _2;
                @SerializedName("3")
                @Expose
                private Float _3;
                @SerializedName("7")
                @Expose
                private Float _7;

                public Float get1() {
                    return _1;
                }

                public void set1(Float _1) {
                    this._1 = _1;
                }

                public Float get2() {
                    return _2;
                }

                public void set2(Float _2) {
                    this._2 = _2;
                }

                public Float get3() {
                    return _3;
                }

                public void set3(Float _3) {
                    this._3 = _3;
                }

                public Float get7() {
                    return _7;
                }

                public void set7(Float _7) {
                    this._7 = _7;
                }
            }

            public class ActualKW {

                @SerializedName("1")
                @Expose
                private Float _1;
                @SerializedName("2")
                @Expose
                private Float _2;
                @SerializedName("3")
                @Expose
                private Float _3;
                @SerializedName("7")
                @Expose
                private Float _7;

                public Float get1() {
                    return _1;
                }

                public void set1(Float _1) {
                    this._1 = _1;
                }

                public Float get2() {
                    return _2;
                }

                public void set2(Float _2) {
                    this._2 = _2;
                }

                public Float get3() {
                    return _3;
                }

                public void set3(Float _3) {
                    this._3 = _3;
                }

                public Float get7() {
                    return _7;
                }

                public void set7(Float _7) {
                    this._7 = _7;
                }

            }

        }

        public class Phase {
            @SerializedName("1")
            @Expose
            private Integer _1;
            @SerializedName("2")
            @Expose
            private Integer _2;
            @SerializedName("3")
            @Expose
            private Integer _3;
            @SerializedName("7")
            @Expose
            private Integer _7;

            public Integer get1() {
                return _1;
            }

            public void set1(Integer _1) {
                this._1 = _1;
            }

            public Integer get2() {
                return _2;
            }

            public void set2(Integer _2) {
                this._2 = _2;
            }

            public Integer get3() {
                return _3;
            }

            public void set3(Integer _3) {
                this._3 = _3;
            }

            public Integer get7() {
                return _7;
            }

            public void set7(Integer _7) {
                this._7 = _7;
            }

        }

    }

}

