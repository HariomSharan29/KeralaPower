package com.techlabs.apdcl.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Topology {

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

        @SerializedName("disconnected")
        @Expose
        private List<String> disconnected;
        @SerializedName("interconnection")
        @Expose
        private List<String> interconnection;
        @SerializedName("isolated")
        @Expose
        private List<String> isolated;
        @SerializedName("loop")
        @Expose
        private List<String> loop;
        @SerializedName("sourcenode")
        @Expose
        private List<String> sourcenode;

        public List<String> getDisconnected() {
            return disconnected;
        }

        public void setDisconnected(List<String> disconnected) {
            this.disconnected = disconnected;
        }

        public List<String> getInterconnection() {
            return interconnection;
        }

        public void setInterconnection(List<String> interconnection) {
            this.interconnection = interconnection;
        }

        public List<String> getIsolated() {
            return isolated;
        }

        public void setIsolated(List<String> isolated) {
            this.isolated = isolated;
        }

        public List<String> getLoop() {
            return loop;
        }

        public void setLoop(List<String> loop) {
            this.loop = loop;
        }

        public List<String> getSourcenode() {
            return sourcenode;
        }

        public void setSourcenode(List<String> sourcenode) {
            this.sourcenode = sourcenode;
        }

    }

}




