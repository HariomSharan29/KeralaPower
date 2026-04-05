package com.techlabs.apdcl.models.analysis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LoadFlowEdtModel {

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
        private List<Item> overload;

        @SerializedName("overload_color")
        @Expose
        private String overloadColor;

        @SerializedName("undervoltage")
        @Expose
        private List<Item> undervoltage;

        @SerializedName("undervoltage_color")
        @Expose
        private String undervoltageColor;

        @SerializedName("overVoltage")
        @Expose
        private List<Item> overVoltage;

        @SerializedName("overVoltage_color")
        @Expose
        private String overVoltageColor;

        @SerializedName("DTGISID")
        @Expose
        private String dtgisid;

        @SerializedName("NodeID")
        @Expose
        private String nodeId;

        @SerializedName("VR")
        @Expose
        private String vr;

        @SerializedName("DTLoading")
        @Expose
        private String dtLoading;

        @SerializedName("Message")
        @Expose
        private List<Object> message;

        @SerializedName("Status")
        @Expose
        private String status;

        public List<Item> getOverload() {
            return overload;
        }

        public void setOverload(List<Item> overload) {
            this.overload = overload;
        }

        public String getOverloadColor() {
            return overloadColor;
        }

        public void setOverloadColor(String overloadColor) {
            this.overloadColor = overloadColor;
        }

        public List<Item> getUndervoltage() {
            return undervoltage;
        }

        public void setUndervoltage(List<Item> undervoltage) {
            this.undervoltage = undervoltage;
        }

        public String getUndervoltageColor() {
            return undervoltageColor;
        }

        public void setUndervoltageColor(String undervoltageColor) {
            this.undervoltageColor = undervoltageColor;
        }

        public List<Item> getOverVoltage() {
            return overVoltage;
        }

        public void setOverVoltage(List<Item> overVoltage) {
            this.overVoltage = overVoltage;
        }

        public String getOverVoltageColor() {
            return overVoltageColor;
        }

        public void setOverVoltageColor(String overVoltageColor) {
            this.overVoltageColor = overVoltageColor;
        }

        public String getDtgisid() {
            return dtgisid;
        }

        public void setDtgisid(String dtgisid) {
            this.dtgisid = dtgisid;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getVr() {
            return vr;
        }

        public void setVr(String vr) {
            this.vr = vr;
        }

        public String getDtLoading() {
            return dtLoading;
        }

        public void setDtLoading(String dtLoading) {
            this.dtLoading = dtLoading;
        }

        public List<Object> getMessage() {
            return message;
        }

        public void setMessage(List<Object> message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class Item {
        @SerializedName("Type")
        @Expose
        private int type;

        @SerializedName("ID")
        @Expose
        private String id;

        @SerializedName("ItemType")
        @Expose
        private int itemType;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }
    }

}

/*    public class Output {

        @SerializedName("overload")
        @Expose
        private String overload;

        @SerializedName("overload_color")
        @Expose
        private String overloadColor;

        @SerializedName("undervoltage")
        @Expose
        private String undervoltage;

        @SerializedName("undervoltage_color")
        @Expose
        private String undervoltageColor;

        @SerializedName("overVoltage_color")
        @Expose
        private String overVoltageColor;

        @SerializedName("DTGISID")
        @Expose
        private String dtgisid;

        @SerializedName("NodeID")
        @Expose
        private String nodeId;

        @SerializedName("VR")
        @Expose
        private String vr;

        @SerializedName("DTLoading")
        @Expose
        private String dtLoading;

        @SerializedName("Message")
        @Expose
        private Message message;

        @SerializedName("Status")
        @Expose
        private String status;

        @SerializedName("Before_Percentage_VR")
        @Expose
        private String beforePercentageVR;

        @SerializedName("After_Percentage_VR")
        @Expose
        private String afterPercentageVR;

        @SerializedName("After_DTLoading")
        @Expose
        private String afterDTLoading;

        @SerializedName("Feasibility")
        @Expose
        private String feasibility;


        public String getOverload() {
            return overload;
        }

        public void setOverload(String overload) {
            this.overload = overload;
        }

        public String getOverloadColor() {
            return overloadColor;
        }

        public void setOverloadColor(String overloadColor) {
            this.overloadColor = overloadColor;
        }

        public String getUndervoltage() {
            return undervoltage;
        }

        public void setUndervoltage(String undervoltage) {
            this.undervoltage = undervoltage;
        }

        public String getUndervoltageColor() {
            return undervoltageColor;
        }

        public void setUndervoltageColor(String undervoltageColor) {
            this.undervoltageColor = undervoltageColor;
        }

        public String getOverVoltageColor() {
            return overVoltageColor;
        }

        public void setOverVoltageColor(String overVoltageColor) {
            this.overVoltageColor = overVoltageColor;
        }

        public String getDtgisid() {
            return dtgisid;
        }

        public void setDtgisid(String dtgisid) {
            this.dtgisid = dtgisid;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getVr() {
            return vr;
        }

        public void setVr(String vr) {
            this.vr = vr;
        }

        public String getDtLoading() {
            return dtLoading;
        }

        public void setDtLoading(String dtLoading) {
            this.dtLoading = dtLoading;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBeforePercentageVR() {
            return beforePercentageVR;
        }

        public void setBeforePercentageVR(String beforePercentageVR) {
            this.beforePercentageVR = beforePercentageVR;
        }

        public String getAfterPercentageVR() {
            return afterPercentageVR;
        }

        public void setAfterPercentageVR(String afterPercentageVR) {
            this.afterPercentageVR = afterPercentageVR;
        }

        public String getAfterDTLoading() {
            return afterDTLoading;
        }

        public void setAfterDTLoading(String afterDTLoading) {
            this.afterDTLoading = afterDTLoading;
        }

        public String getFeasibility() {
            return feasibility;
        }

        public void setFeasibility(String feasibility) {
            this.feasibility = feasibility;
        }
    }

    public class Message {

        @SerializedName("Category")
        @Expose
        private String category;

        @SerializedName("Code")
        @Expose
        private int code;

        @SerializedName("Text")
        @Expose
        private String text;

        @SerializedName("Severity")
        @Expose
        private String severity;

        @SerializedName("Section")
        @Expose
        private String section;

        @SerializedName("TwoWindingTransformer")
        @Expose
        private String twoWindingTransformer;

        @SerializedName("Network")
        @Expose
        private String network;


        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
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

        public String getTwoWindingTransformer() {
            return twoWindingTransformer;
        }

        public void setTwoWindingTransformer(String twoWindingTransformer) {
            this.twoWindingTransformer = twoWindingTransformer;
        }

        public String getNetwork() {
            return network;
        }

        public void setNetwork(String network) {
            this.network = network;
        }
    }
}*/
