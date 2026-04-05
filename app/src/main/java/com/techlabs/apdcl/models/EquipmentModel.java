package com.techlabs.apdcl.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EquipmentModel {

    @SerializedName("AllEquipmentId")
    @Expose
    private AllEquipmentId allEquipmentId;
    @SerializedName("EquipmentId")
    @Expose
    private List<String> equipmentId;

    public AllEquipmentId getAllEquipmentId() {
        return allEquipmentId;
    }

    public void setAllEquipmentId(AllEquipmentId allEquipmentId) {
        this.allEquipmentId = allEquipmentId;
    }

    public List<String> getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(List<String> equipmentId) {
        this.equipmentId = equipmentId;
    }

    public class AllEquipmentId {

        @SerializedName("EquipmentId")
        @Expose
        private List<String> equipmentId;

        public List<String> getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(List<String> equipmentId) {
            this.equipmentId = equipmentId;
        }

    }

}


