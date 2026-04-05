package com.techlabs.apdcl.DataBase.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.JsonObject;

import org.json.JSONObject;

@Entity(tableName = "ConsumerDetails")
public class CustomerData {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String phaseType;
    public int phase;
    public String customerNumber;
    public String customerType;
    public int status;
    public JsonObject phaseObject;
    public JsonObject actualKWObject;
    public JsonObject actualPfObject;
    public JsonObject connKVAObject;
    public JsonObject custNoObject;

    public CustomerData(String phaseType, int phase, String customerNumber, String customerType, int status, JsonObject phaseObject, JsonObject actualKWObject, JsonObject actualPfObject, JsonObject connKVAObject, JsonObject custNoObject) {
        this.phaseType = phaseType;
        this.phase = phase;
        this.customerNumber = customerNumber;
        this.customerType = customerType;
        this.status = status;
        this.phaseObject = phaseObject;
        this.actualKWObject = actualKWObject;
        this.actualPfObject = actualPfObject;
        this.connKVAObject = connKVAObject;
        this.custNoObject = custNoObject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhaseType() {
        return phaseType;
    }

    public void setPhaseType(String phaseType) {
        this.phaseType = phaseType;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public JsonObject getPhaseObject() {
        return phaseObject;
    }

    public void setPhaseObject(JsonObject phaseObject) {
        this.phaseObject = phaseObject;
    }

    public JsonObject getActualKWObject() {
        return actualKWObject;
    }

    public void setActualKWObject(JsonObject actualKWObject) {
        this.actualKWObject = actualKWObject;
    }

    public JsonObject getActualPfObject() {
        return actualPfObject;
    }

    public void setActualPfObject(JsonObject actualPfObject) {
        this.actualPfObject = actualPfObject;
    }

    public JsonObject getConnKVAObject() {
        return connKVAObject;
    }

    public void setConnKVAObject(JsonObject connKVAObject) {
        this.connKVAObject = connKVAObject;
    }

    public JsonObject getCustNoObject() {
        return custNoObject;
    }

    public void setCustNoObject(JsonObject custNoObject) {
        this.custNoObject = custNoObject;
    }
}
