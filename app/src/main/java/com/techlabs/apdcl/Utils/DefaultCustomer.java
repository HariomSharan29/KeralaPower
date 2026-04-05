package com.techlabs.apdcl.Utils;

import com.google.gson.JsonObject;

public class DefaultCustomer {
    private final String phase;
    private final String phaseType;
    private final int randomNumber = (int) (Math.random() * 9000) + 1000;

    public DefaultCustomer(String phase, String phaseType) {
        this.phase = phase;
        this.phaseType = phaseType;
    }

    public void OneCustomer() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("CustomerNumber", randomNumber);
            jsonObject.addProperty("CustomerType", "COMMERCIAL");
            jsonObject.addProperty("Status", "0");
            jsonObject.add("Phase", buildPhaseObject());
            jsonObject.add("ActualKW", buildActualKwObject());
            jsonObject.add("ActualPF", buildActualPfObject());
            jsonObject.add("ConnectedKVA", buildConnectedKvaObject());
            jsonObject.add("CustomerCount", buildCustomerCountObject());
            ListDataManager.sendDefaultData(jsonObject);
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    private JsonObject buildPhaseObject() {
        JsonObject phaseObject = createZeroObject("0");
        if ("ThreePhase".equalsIgnoreCase(phaseType)) {
            phaseObject.addProperty("7", "7");
            return phaseObject;
        }

        switch (phase) {
            case "1":
                phaseObject.addProperty("1", "1");
                break;
            case "2":
                phaseObject.addProperty("2", "1");
                break;
            case "3":
                phaseObject.addProperty("3", "1");
                break;
            case "4":
                phaseObject.addProperty("1", "1");
                phaseObject.addProperty("2", "1");
                break;
            case "5":
                phaseObject.addProperty("1", "1");
                phaseObject.addProperty("3", "1");
                break;
            case "6":
                phaseObject.addProperty("2", "1");
                phaseObject.addProperty("3", "1");
                break;
            default:
                phaseObject.addProperty("1", "1");
                phaseObject.addProperty("2", "1");
                phaseObject.addProperty("3", "1");
                break;
        }
        return phaseObject;
    }

    private JsonObject buildActualKwObject() {
        if ("ThreePhase".equalsIgnoreCase(phaseType)) {
            return createThreePhaseObject("0.0");
        }
        JsonObject actualKwObject = createZeroObject("0");
        switch (phase) {
            case "1":
                actualKwObject.addProperty("1", "0.0");
                break;
            case "2":
                actualKwObject.addProperty("2", "0.0");
                break;
            case "3":
                actualKwObject.addProperty("3", "0.0");
                break;
            case "4":
                actualKwObject.addProperty("1", "0.0");
                actualKwObject.addProperty("2", "0.0");
                break;
            case "5":
                actualKwObject.addProperty("1", "0.0");
                actualKwObject.addProperty("3", "0.0");
                break;
            case "6":
                actualKwObject.addProperty("2", "0.0");
                actualKwObject.addProperty("3", "0.0");
                break;
            default:
                actualKwObject.addProperty("1", "0.0");
                actualKwObject.addProperty("2", "0.0");
                actualKwObject.addProperty("3", "0.0");
                break;
        }
        return actualKwObject;
    }

    private JsonObject buildActualPfObject() {
        if ("ThreePhase".equalsIgnoreCase(phaseType)) {
            return createThreePhaseObject("10");
        }
        JsonObject actualPfObject = createZeroObject("0");
        switch (phase) {
            case "1":
                actualPfObject.addProperty("1", "10");
                break;
            case "2":
                actualPfObject.addProperty("2", "10");
                break;
            case "3":
                actualPfObject.addProperty("3", "10");
                break;
            case "4":
                actualPfObject.addProperty("1", "10");
                actualPfObject.addProperty("2", "10");
                break;
            case "5":
                actualPfObject.addProperty("1", "10");
                actualPfObject.addProperty("3", "10");
                break;
            case "6":
                actualPfObject.addProperty("2", "10");
                actualPfObject.addProperty("3", "10");
                break;
            default:
                actualPfObject.addProperty("1", "10");
                actualPfObject.addProperty("2", "10");
                actualPfObject.addProperty("3", "10");
                break;
        }
        return actualPfObject;
    }

    private JsonObject buildConnectedKvaObject() {
        return buildActualKwObject();
    }

    private JsonObject buildCustomerCountObject() {
        return buildActualKwObject();
    }

    private JsonObject createThreePhaseObject(String value) {
        JsonObject jsonObject = createZeroObject("0");
        jsonObject.addProperty("7", value);
        return jsonObject;
    }

    private JsonObject createZeroObject(String defaultValue) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("1", defaultValue);
        jsonObject.addProperty("2", defaultValue);
        jsonObject.addProperty("3", defaultValue);
        jsonObject.addProperty("7", "0");
        return jsonObject;
    }
}
