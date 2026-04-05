package com.techlabs.apdcl.models.nsc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDeviceModel {

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

        @SerializedName("Status")
        @Expose
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

}
