package com.techlabs.apdcl.models.trace;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tracing {

    @SerializedName("output")
    @Expose
    private List<String> output;

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(List<String> output) {
        this.output = output;
    }

}
