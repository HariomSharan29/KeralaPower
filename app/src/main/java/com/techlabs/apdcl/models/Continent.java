package com.techlabs.apdcl.models;

import java.util.ArrayList;

public class Continent {

    private String name;
    private ArrayList<DType> DeviceList = new ArrayList<>();

    public Continent(String name, ArrayList<DType> deviceList) {
        this.name = name;
        DeviceList = deviceList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DType> getDeviceList() {
        return DeviceList;
    }

    public void setDeviceList(ArrayList<DType> deviceList) {
        DeviceList = deviceList;
    }

}
