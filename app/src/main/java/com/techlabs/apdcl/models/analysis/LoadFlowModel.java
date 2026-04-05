package com.techlabs.apdcl.models.analysis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoadFlowModel {

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

        @SerializedName("overload")
        @Expose
        private List<Overload> overload;
        @SerializedName("overload_color")
        @Expose
        private String overloadColor;
        @SerializedName("undervoltage")
        @Expose
        private List<Undervoltage> undervoltage;
        @SerializedName("undervoltage_color")
        @Expose
        private String undervoltageColor;
        @SerializedName("overVoltage")
        @Expose
        private List<OverVoltage> overVoltage;
        @SerializedName("overVoltage_color")
        @Expose
        private String overVoltageColor;
        @SerializedName("Message")
        @Expose
        private List<Message> message;
        @SerializedName("Status")
        @Expose
        private String status;

        public List<Overload> getOverload() {
            return overload;
        }

        public void setOverload(List<Overload> overload) {
            this.overload = overload;
        }

        public String getOverloadColor() {
            return overloadColor;
        }

        public void setOverloadColor(String overloadColor) {
            this.overloadColor = overloadColor;
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

        public void setUndervoltageColor(String undervoltageColor) {
            this.undervoltageColor = undervoltageColor;
        }

        public List<OverVoltage> getOverVoltage() {
            return overVoltage;
        }

        public void setOverVoltage(List<OverVoltage> overVoltage) {
            this.overVoltage = overVoltage;
        }

        public String getOverVoltageColor() {
            return overVoltageColor;
        }

        public void setOverVoltageColor(String overVoltageColor) {
            this.overVoltageColor = overVoltageColor;
        }

        public List<Message> getMessage() {
            return message;
        }

        public void setMessage(List<Message> message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public class Overload {

            @SerializedName("Type")
            @Expose
            private Integer type;
            @SerializedName("ID")
            @Expose
            private String id;
            @SerializedName("ItemType")
            @Expose
            private Integer itemType;

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

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

        }

        public class Undervoltage {

            @SerializedName("Type")
            @Expose
            private Integer type;
            @SerializedName("ID")
            @Expose
            private String id;
            @SerializedName("ItemType")
            @Expose
            private Integer itemType;

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

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

        }

        public class OverVoltage {

            @SerializedName("Type")
            @Expose
            private Integer type;
            @SerializedName("ID")
            @Expose
            private String id;
            @SerializedName("ItemType")
            @Expose
            private Integer itemType;

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

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

        }

        public class Message {

            @SerializedName("Category")
            @Expose
            private String category;
            @SerializedName("Code")
            @Expose
            private Integer code;
            @SerializedName("Text")
            @Expose
            private String text;
            @SerializedName("Severity")
            @Expose
            private String severity;
            @SerializedName("Section")
            @Expose
            private String section;
            @SerializedName("BESS")
            @Expose
            private String bess;
            @SerializedName("Network")
            @Expose
            private String network;

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public Integer getCode() {
                return code;
            }

            public void setCode(Integer code) {
                this.code = code;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getSeverity() {
                return severity;
            }

            public void setSeverity(String severity) {
                this.severity = severity;
            }

            public String getSection() {
                return section;
            }

            public void setSection(String section) {
                this.section = section;
            }

            public String getBess() {
                return bess;
            }

            public void setBess(String bess) {
                this.bess = bess;
            }

            public String getNetwork() {
                return network;
            }

            public void setNetwork(String network) {
                this.network = network;
            }

        }

    }

}







