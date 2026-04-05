package com.techlabs.apdcl.models.nsc;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

public class NewConnectionModel {

    @SerializedName("output")
    @Expose
    private List<Output> output;
    @SerializedName("Database")
    @Expose
    private String database;

    public List<Output> getOutput() {
        return output;
    }

    public void setOutput(List<Output> output) {
        this.output = output;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public class Output {

        @SerializedName("Sno")
        @Expose
        private Integer sno;
        @SerializedName("ApplicationID")
        @Expose
        private String applicationID;
        @SerializedName("BPNO")
        @Expose
        private String bpno;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("DTGISID")
        @Expose
        private String dtgisid;
        @SerializedName("PoleNo")
        @Expose
        private String poleNo;
        @SerializedName("ContractDemand")
        @Expose
        private String contractDemand;
        @SerializedName("NearestConsumerNumber")
        @Expose
        private String nearestConsumerNumber;
        @SerializedName("SanctionedLoad")
        @Expose
        private String sanctionedLoad;
        @SerializedName("SanctionedPhase")
        @Expose
        private String sanctionedPhase;
        @SerializedName("SanctionedCategory")
        @Expose
        private String sanctionedCategory;
        @SerializedName("ServiceCableLength")
        @Expose
        private String serviceCableLength;
        @SerializedName("Circle")
        @Expose
        private String circle;
        @SerializedName("Division")
        @Expose
        private String division;
        @SerializedName("Subdivision")
        @Expose
        private String subdivision;
        @SerializedName("Section")
        @Expose
        private String section;
        @SerializedName("SubstationID")
        @Expose
        private String substationID;
        @SerializedName("SubstationName")
        @Expose
        private String substationName;
        @SerializedName("Feederid")
        @Expose
        private String feederid;
        @SerializedName("OperationType")
        @Expose
        private String operationType;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("Feasibility")
        @Expose
        private String feasibility;
        @SerializedName("Before_Percentage_VR")
        @Expose
        private String beforePercentageVR;
        @SerializedName("After_Percentage_VR")
        @Expose
        private String afterPercentageVR;
        @SerializedName("Before_DTLoading")
        @Expose
        private String beforeDTLoading;
        @SerializedName("After_DTLoading")
        @Expose
        private String afterDTLoading;
        @SerializedName("Recomnd_Phase")
        @Expose
        private String recomndPhase;
        @SerializedName("Remark")
        @Expose
        private String remark;

        public Integer getSno() {
            return sno;
        }

        public void setSno(Integer sno) {
            this.sno = sno;
        }

        public String getApplicationID() {
            return applicationID;
        }

        public void setApplicationID(String applicationID) {
            this.applicationID = applicationID;
        }

        public String getBpno() {
            return bpno;
        }

        public void setBpno(String bpno) {
            this.bpno = bpno;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDtgisid() {
            return dtgisid;
        }

        public void setDtgisid(String dtgisid) {
            this.dtgisid = dtgisid;
        }

        public String getPoleNo() {
            return poleNo;
        }

        public void setPoleNo(String poleNo) {
            this.poleNo = poleNo;
        }

        public String getContractDemand() {
            return contractDemand;
        }

        public void setContractDemand(String contractDemand) {
            this.contractDemand = contractDemand;
        }

        public String getNearestConsumerNumber() {
            return nearestConsumerNumber;
        }

        public void setNearestConsumerNumber(String nearestConsumerNumber) {
            this.nearestConsumerNumber = nearestConsumerNumber;
        }

        public String getSanctionedLoad() {
            return sanctionedLoad;
        }

        public void setSanctionedLoad(String sanctionedLoad) {
            this.sanctionedLoad = sanctionedLoad;
        }

        public String getSanctionedPhase() {
            return sanctionedPhase;
        }

        public void setSanctionedPhase(String sanctionedPhase) {
            this.sanctionedPhase = sanctionedPhase;
        }

        public String getSanctionedCategory() {
            return sanctionedCategory;
        }

        public void setSanctionedCategory(String sanctionedCategory) {
            this.sanctionedCategory = sanctionedCategory;
        }

        public String getServiceCableLength() {
            return serviceCableLength;
        }

        public void setServiceCableLength(String serviceCableLength) {
            this.serviceCableLength = serviceCableLength;
        }

        public String getCircle() {
            return circle;
        }

        public void setCircle(String circle) {
            this.circle = circle;
        }

        public String getDivision() {
            return division;
        }

        public void setDivision(String division) {
            this.division = division;
        }

        public String getSubdivision() {
            return subdivision;
        }

        public void setSubdivision(String subdivision) {
            this.subdivision = subdivision;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getSubstationID() {
            return substationID;
        }

        public void setSubstationID(String substationID) {
            this.substationID = substationID;
        }

        public String getSubstationName() {
            return substationName;
        }

        public void setSubstationName(String substationName) {
            this.substationName = substationName;
        }

        public String getFeederid() {
            return feederid;
        }

        public void setFeederid(String feederid) {
            this.feederid = feederid;
        }

        public String getOperationType() {
            return operationType;
        }

        public void setOperationType(String operationType) {
            this.operationType = operationType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getFeasibility() {
            return feasibility;
        }

        public void setFeasibility(String feasibility) {
            this.feasibility = feasibility;
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

        public String getBeforeDTLoading() {
            return beforeDTLoading;
        }

        public void setBeforeDTLoading(String beforeDTLoading) {
            this.beforeDTLoading = beforeDTLoading;
        }

        public String getAfterDTLoading() {
            return afterDTLoading;
        }

        public void setAfterDTLoading(String afterDTLoading) {
            this.afterDTLoading = afterDTLoading;
        }

        public String getRecomndPhase() {
            return recomndPhase;
        }

        public void setRecomndPhase(String recomndPhase) {
            this.recomndPhase = recomndPhase;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

    }

}

