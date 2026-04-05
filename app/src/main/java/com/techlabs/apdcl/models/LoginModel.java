package com.techlabs.apdcl.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class LoginModel {

    @SerializedName("refresh")
    String refresh;

    @SerializedName("access")
    String access;

    @SerializedName("msg")
    String msg;

    @SerializedName("status")
    int status;

    @SerializedName("usertype")
    String usertype;

    @SerializedName("email")
    String email;

    @SerializedName("Name")
    String Name;

    @SerializedName("Username")
    String Username;

    @SerializedName("permissions")
    List<Permissions> permissions;

    @SerializedName("start_time")
    String start_time;


    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
    public String getRefresh() {
        return refresh;
    }

    public void setAccess(String access) {
        this.access = access;
    }
    public String getAccess() {
        return access;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
    public String getUsertype() {
        return usertype;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    public String getName() {
        return Name;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }
    public String getUsername() {
        return Username;
    }

    public void setPermissions(List<Permissions> permissions) {
        this.permissions = permissions;
    }
    public List<Permissions> getPermissions() {
        return permissions;
    }
    public String getStartTime() {
        return start_time;
    }

    public void setStartTime(String start_time) {
        this.start_time = start_time;
    }

    public class Permissions {

        @SerializedName("codename")
        String codename;


        public void setCodename(String codename) {
            this.codename = codename;
        }
        public String getCodename() {
            return codename;
        }

    }

}
