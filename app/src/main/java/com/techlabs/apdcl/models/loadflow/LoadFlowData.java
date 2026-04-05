package com.techlabs.apdcl.models.loadflow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class LoadFlowData {

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

        @SerializedName("overVoltage")
        @Expose
        private List<OverVoltage> overVoltage;
        @SerializedName("overVoltage_color")
        @Expose
        private String overVoltageColor;
        @SerializedName("overload_color")
        @Expose
        private String overloadColor;
        @SerializedName("overload")
        @Expose
        private List<Overload> overload;
        @SerializedName("undervoltage")
        @Expose
        private List<Undervoltage> undervoltage;
        @SerializedName("undervoltage_color")
        @Expose
        private String undervoltageColor;

        public List<OverVoltage> getOverVoltage() {
            return overVoltage;
        }

        public void setOverVoltage(List<OverVoltage> overVoltage) {
            this.overVoltage = overVoltage;
        }

        public String getOverVoltageColor() {
            return overVoltageColor;
        }

        public class OverVoltage {

            @SerializedName("ID")
            @Expose
            private String id;
            @SerializedName("ItemType")
            @Expose
            private Integer itemType;
            @SerializedName("Type")
            @Expose
            private Integer type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Integer getItemType() {
                return itemType;
            }

            public void setItemType(Integer itemType) {
                this.itemType = itemType;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

        }

        public void setOverVoltageColor(String overVoltageColor) {
            this.overVoltageColor = overVoltageColor;
        }

        public String getOverloadColor() {
            return overloadColor;
        }

        public void setOverloadColor(String overloadColor) {
            this.overloadColor = overloadColor;
        }

        public List<Overload> getOverload() {
            return overload;
        }

        public void setOverload(List<Overload> overload) {
            this.overload = overload;
        }

        public class Overload {

            @SerializedName("ID")
            @Expose
            private String id;
            @SerializedName("ItemType")
            @Expose
            private Integer itemType;
            @SerializedName("Type")
            @Expose
            private Integer type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Integer getItemType() {
                return itemType;
            }

            public void setItemType(Integer itemType) {
                this.itemType = itemType;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

        }

        public List<Undervoltage> getUndervoltage() {
            return undervoltage;
        }

        public void setUndervoltage(List<Undervoltage> undervoltage) {
            this.undervoltage = undervoltage;
        }

        public String getUndervoltageColor() {
            return undervoltageColor;
        }

        public class Undervoltage {

            @SerializedName("ID")
            @Expose
            private String id;
            @SerializedName("ItemType")
            @Expose
            private Integer itemType;
            @SerializedName("Type")
            @Expose
            private Integer type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Integer getItemType() {
                return itemType;
            }

            public void setItemType(Integer itemType) {
                this.itemType = itemType;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

        }

        public void setUndervoltageColor(String undervoltageColor) {
            this.undervoltageColor = undervoltageColor;
        }

    }

}

//package com.techlabs.apdcl.models.loadflow;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//import java.util.List;
//
//public class LoadFlowData {
//
//    @SerializedName("output")
//    @Expose
//    private Output output;
//
//    public Output getOutput() {
//        return output;
//    }
//
//    public void setOutput(Output output) {
//        this.output = output;
//    }
//
//    public class Output {
//
//        @SerializedName("overVoltage")
//        @Expose
//        private List<OverVoltage> overVoltage;
//        @SerializedName("overVoltage_color")
//        @Expose
//        private String overVoltageColor;
//        @SerializedName("overload")
//        @Expose
//        private List<Overload> overload;
//        @SerializedName("overload_color")
//        @Expose
//        private String overloadColor;
//        @SerializedName("undervoltage")
//        @Expose
//        private List<Undervoltage> undervoltage;
//        @SerializedName("undervoltage_color")
//        @Expose
//        private String undervoltageColor;
//
//        public List<OverVoltage> getOverVoltage() {
//            return overVoltage;
//        }
//
//        public void setOverVoltage(List<OverVoltage> overVoltage) {
//            this.overVoltage = overVoltage;
//        }
//
//        public String getOverVoltageColor() {
//            return overVoltageColor;
//        }
//
//        public void setOverVoltageColor(String overVoltageColor) {
//            this.overVoltageColor = overVoltageColor;
//        }
//
//        public List<Overload> getOverload() {
//            return overload;
//        }
//
//        public void setOverload(List<Overload> overload) {
//            this.overload = overload;
//        }
//
//        public String getOverloadColor() {
//            return overloadColor;
//        }
//
//        public void setOverloadColor(String overloadColor) {
//            this.overloadColor = overloadColor;
//        }
//
//        public List<Undervoltage> getUndervoltage() {
//            return undervoltage;
//        }
//
//        public void setUndervoltage(List<Undervoltage> undervoltage) {
//            this.undervoltage = undervoltage;
//        }
//
//        public String getUndervoltageColor() {
//            return undervoltageColor;
//        }

//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public void setUndervoltageColor(String undervoltageColor) {
//            this.undervoltageColor = undervoltageColor;
//        }
//
//        public class OverVoltage {
//
//            @SerializedName("ID")
//            @Expose
//            private String id;
//            @SerializedName("ItemType")
//            @Expose
//            private Integer itemType;
//            @SerializedName("Type")
//            @Expose
//            private Integer type;
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public Integer getItemType() {
//                return itemType;
//            }
//
//            public void setItemType(Integer itemType) {
//                this.itemType = itemType;
//            }
//
//            public Integer getType() {
//                return type;
//            }
//
//            public void setType(Integer type) {
//                this.type = type;
//            }
//
//        }
//
//        public class Overload {
//
//            @SerializedName("ID")
//            @Expose
//            private String id;
//            @SerializedName("ItemType")
//            @Expose
//            private Integer itemType;
//            @SerializedName("Type")
//            @Expose
//            private Integer type;
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public Integer getItemType() {
//                return itemType;
//            }
//
//            public void setItemType(Integer itemType) {
//                this.itemType = itemType;
//            }
//
//            public Integer getType() {
//                return type;
//            }
//
//            public void setType(Integer type) {
//                this.type = type;
//            }
//
//        }
//
//        public class Undervoltage {
//
//            @SerializedName("ID")
//            @Expose
//            private String id;
//            @SerializedName("ItemType")
//            @Expose
//            private Integer itemType;
//            @SerializedName("Type")
//            @Expose
//            private Integer type;
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public Integer getItemType() {
//                return itemType;
//            }
//
//            public void setItemType(Integer itemType) {
//                this.itemType = itemType;
//            }
//
//            public Integer getType() {
//                return type;
//            }
//
//            public void setType(Integer type) {
//                this.type = type;
//            }
//
//        }
//
//    }
//
//}
//
//
