package com.techlabs.apdcl.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NetworkResponse {

    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("Group3")
        @Expose
        private List<Group3> group3;
        @SerializedName("Group2")
        @Expose
        private List<Group2> group2;
        @SerializedName("Group1")
        @Expose
        private List<Group1> group1;
        @SerializedName("NetworkId")
        @Expose
        private List<NetworkId> networkId;

        public List<Group3> getGroup3() {
            return group3;
        }

        public void setGroup3(List<Group3> group3) {
            this.group3 = group3;
        }

        public List<Group2> getGroup2() {
            return group2;
        }

        public void setGroup2(List<Group2> group2) {
            this.group2 = group2;
        }

        public List<Group1> getGroup1() {
            return group1;
        }

        public void setGroup1(List<Group1> group1) {
            this.group1 = group1;
        }

        public List<NetworkId> getNetworkId() {
            return networkId;
        }

        public void setNetworkId(List<NetworkId> networkId) {
            this.networkId = networkId;
        }

        public class Group3 {

            @SerializedName("Group3")
            @Expose
            private String group3;

            public String getGroup3() {
                return group3;
            }

            public void setGroup3(String group3) {
                this.group3 = group3;
            }

        }

        public class Group2 {

            @SerializedName("Group3")
            @Expose
            private String group3;
            @SerializedName("Group2")
            @Expose
            private String group2;

            public String getGroup3() {
                return group3;
            }

            public void setGroup3(String group3) {
                this.group3 = group3;
            }

            public String getGroup2() {
                return group2;
            }

            public void setGroup2(String group2) {
                this.group2 = group2;
            }

        }

        public class Group1 {

            @SerializedName("Group3")
            @Expose
            private String group3;
            @SerializedName("Group2")
            @Expose
            private String group2;
            @SerializedName("Group1")
            @Expose
            private String group1;

            public String getGroup3() {
                return group3;
            }

            public void setGroup3(String group3) {
                this.group3 = group3;
            }

            public String getGroup2() {
                return group2;
            }

            public void setGroup2(String group2) {
                this.group2 = group2;
            }

            public String getGroup1() {
                return group1;
            }

            public void setGroup1(String group1) {
                this.group1 = group1;
            }

        }

        public class NetworkId {

            @SerializedName("Group1")
            @Expose
            private String group1;
            @SerializedName("Group2")
            @Expose
            private String group2;
            @SerializedName("Group3")
            @Expose
            private String group3;
            @SerializedName("NetworkId")
            @Expose
            private String networkId;

            public String getGroup1() {
                return group1;
            }

            public void setGroup1(String group1) {
                this.group1 = group1;
            }

            public String getGroup2() {
                return group2;
            }

            public void setGroup2(String group2) {
                this.group2 = group2;
            }

            public String getGroup3() {
                return group3;
            }

            public void setGroup3(String group3) {
                this.group3 = group3;
            }

            public String getNetworkId() {
                return networkId;
            }

            public void setNetworkId(String networkId) {
                this.networkId = networkId;
            }

        }

    }

}

//import java.util.List;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//public class NetworkResponse {
//
//    @SerializedName("result")
//    @Expose
//    private List<Result> result;
//
//    public List<Result> getResult() {
//        return result;
//    }
//
//    public void setResult(List<Result> result) {
//        this.result = result;
//    }
//
//    public class Result {
//
//        @SerializedName("NetworkId")
//        @Expose
//        private String networkId;
//        @SerializedName("Group1")
//        @Expose
//        private String group1;
//        @SerializedName("Group2")
//        @Expose
//        private String group2;
//        @SerializedName("Group3")
//        @Expose
//        private String group3;
//
//        public String getNetworkId() {
//            return networkId;
//        }
//
//        public void setNetworkId(String networkId) {
//            this.networkId = networkId;
//        }
//
//        public String getGroup1() {
//            return group1;
//        }
//
//        public void setGroup1(String group1) {
//            this.group1 = group1;
//        }
//
//        public String getGroup2() {
//            return group2;
//        }
//
//        public void setGroup2(String group2) {
//            this.group2 = group2;
//        }
//
//        public String getGroup3() {
//            return group3;
//        }
//
//        public void setGroup3(String group3) {
//            this.group3 = group3;
//        }
//
//    }
//
//}

