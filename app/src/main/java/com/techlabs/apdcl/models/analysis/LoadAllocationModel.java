package com.techlabs.apdcl.models.analysis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LoadAllocationModel {

    @SerializedName("output")
    @Expose
    private Output output;

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    /*public class Output {

        @SerializedName("overVoltage")
        @Expose
        private List<OverVoltage> overVoltage;
        @SerializedName("overVoltage_color")
        @Expose
        private String overVoltageColor;
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

    }*/
    public class Output {

        @SerializedName("overload")
        @Expose
        private List<Object> overload;
        @SerializedName("overload_color")
        @Expose
        private String overloadColor;
        @SerializedName("undervoltage")
        @Expose
        private List<Object> undervoltage;
        @SerializedName("undervoltage_color")
        @Expose
        private String undervoltageColor;
        @SerializedName("overVoltage")
        @Expose
        private List<Object> overVoltage;
        @SerializedName("overVoltage_color")
        @Expose
        private String overVoltageColor;
        @SerializedName("Message")
        @Expose
        private List<Message> message;
        @SerializedName("Status")
        @Expose
        private String status;

        public List<Object> getOverload() {
            return overload;
        }

        public void setOverload(List<Object> overload) {
            this.overload = overload;
        }

        public String getOverloadColor() {
            return overloadColor;
        }

        public void setOverloadColor(String overloadColor) {
            this.overloadColor = overloadColor;
        }

        public List<Object> getUndervoltage() {
            return undervoltage;
        }

        public void setUndervoltage(List<Object> undervoltage) {
            this.undervoltage = undervoltage;
        }

        public String getUndervoltageColor() {
            return undervoltageColor;
        }

        public void setUndervoltageColor(String undervoltageColor) {
            this.undervoltageColor = undervoltageColor;
        }

        public List<Object> getOverVoltage() {
            return overVoltage;
        }

        public void setOverVoltage(List<Object> overVoltage) {
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

    }
    public class Message {

        @SerializedName("Severity")
        @Expose
        private String severity;
        @SerializedName("Messages")
        @Expose
        private String messages;
        @SerializedName("code")
        @Expose
        private String code;

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public String getMessages() {
            return messages;
        }

        public void setMessages(String messages) {
            this.messages = messages;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

    }

}
