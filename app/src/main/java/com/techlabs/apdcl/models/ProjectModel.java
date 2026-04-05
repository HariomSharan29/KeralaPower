package com.techlabs.apdcl.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectModel {
    @SerializedName("PROJECT_NAME")
    @Expose
    private List<ProjectName> projectName;

    public List<ProjectName> getProjectName() {
        return projectName;
    }

    public void setProjectName(List<ProjectName> projectName) {
        this.projectName = projectName;
    }

    public class ProjectName {

        @SerializedName("PROJECT_NAME")
        @Expose
        private String projectName;
        @SerializedName("DATABASE")
        @Expose
        private String database;
        @SerializedName("SERVER_TYPE")
        @Expose
        private String serverType;
        @SerializedName("PROJECT_PROJECTION")
        @Expose
        private String projectProjection;
        @SerializedName("SQL_PROJECT_ZONE")
        @Expose
        private String sqlProjectZone;
        @SerializedName("SQL_PROJECT_LONGITUTE")
        @Expose
        private String sqlProjectLongitute;
        @SerializedName("SQL_PROJECT_LATITUDE")
        @Expose
        private String sqlProjectLatitude;

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public String getServerType() {
            return serverType;
        }

        public void setServerType(String serverType) {
            this.serverType = serverType;
        }

        public String getProjectProjection() {
            return projectProjection;
        }

        public void setProjectProjection(String projectProjection) {
            this.projectProjection = projectProjection;
        }

        public String getSqlProjectZone() {
            return sqlProjectZone;
        }

        public void setSqlProjectZone(String sqlProjectZone) {
            this.sqlProjectZone = sqlProjectZone;
        }

        public String getSqlProjectLongitute() {
            return sqlProjectLongitute;
        }

        public void setSqlProjectLongitute(String sqlProjectLongitute) {
            this.sqlProjectLongitute = sqlProjectLongitute;
        }

        public String getSqlProjectLatitude() {
            return sqlProjectLatitude;
        }

        public void setSqlProjectLatitude(String sqlProjectLatitude) {
            this.sqlProjectLatitude = sqlProjectLatitude;
        }

    }
}
