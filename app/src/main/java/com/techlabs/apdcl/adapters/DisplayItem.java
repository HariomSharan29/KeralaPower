package com.techlabs.apdcl.adapters;

import com.techlabs.apdcl.models.device.SpotLoad.Output.CustomerData;

public class DisplayItem {
    private final CustomerData customerData;
    private final int phase;

    public DisplayItem(CustomerData customerData, int phase) {
        this.customerData = customerData;
        this.phase = phase;
    }

    public CustomerData getCustomerData() {
        return customerData;
    }

    public int getPhase() {
        return phase;
    }
}
