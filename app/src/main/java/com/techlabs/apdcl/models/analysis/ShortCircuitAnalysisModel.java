package com.techlabs.apdcl.models.analysis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShortCircuitAnalysisModel {
    @SerializedName("ID")
    @Expose
    private Id id;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public class Id {

        @SerializedName("Id")
        @Expose
        private List<String> id;

        public List<String> getId() {
            return id;
        }

        public void setId(List<String> id) {
            this.id = id;
        }

    }

}
