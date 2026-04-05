package com.techlabs.apdcl.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefManager {
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private  final String ISFIRSTTIMEUSER = "isFirstTimeUser";
    private  final String IsUserLogin = "isUserLogin";
    private  final String UserName = "UserName";
    private  final String UserType = "userType";
    private  final String AccessToken = "accessToken";
    private  final String type = "type";
    private  final String editMode = "editMode";
    private final String PREF_DATABASE_NAME = "DatabaseName";
    private final String ProjectName = "ProjectName";
    private final String Report = "report_selection";
    private static final String Profile_Name = "Name";
    private final String Database_Survey = "DATABASE_SURVEY";
    private final String Designation = "Designation";
    private final String Email = "Email";
    private final String Mobile = "Mobile";
    private final String Date_Joined = "Date_Joined";
    private final String Place_Of_Work = "Place_Of_Work";
    private final String Region = "Region";
    private final String Zone = "Zone";
    private final String Circle = "Circle";
    private final String lastLogin = "lastLogin";
    private final String lastLogout = "lastLogout";

    public PrefManager(Context context) {
        this.context = context;
        this.pref = PreferenceManager.getDefaultSharedPreferences(this.context);
        this.editor = this.pref.edit();
    }

    public void setIsFirstTimeUser(Boolean z) {
        this.editor.putBoolean(ISFIRSTTIMEUSER, z);
        this.editor.commit();
    }

    public Boolean getIsFirstTimeUser() {
        return this.pref.getBoolean(ISFIRSTTIMEUSER, false);
    }

    public void setIsUserLogin(Boolean b) {
        this.editor.putBoolean(IsUserLogin, b);
        this.editor.commit();
    }

    public Boolean getIsUserLogin() {
        return this.pref.getBoolean(IsUserLogin, false);
    }

    public void setUserName(String b) {
        this.editor.putString(UserName, b);
        this.editor.commit();
    }

    public String getUserName() {
        return this.pref.getString(UserName, "");
    }

    public void setUserType(String b) {
        this.editor.putString(UserType, b);
        this.editor.commit();
    }

    public String getUserType() {
        return this.pref.getString(UserType, "");
    }

    public void setAccessToken(String b) {
        this.editor.putString(AccessToken, b);
        this.editor.commit();
    }

    public String getAccessToken() {
        return this.pref.getString(AccessToken, "");
    }

    public void clearSharedPreferences(Context context){
                editor.clear();
                editor.apply();
    }

    public void setType(String b) {
        this.editor.putString(type, b);
        this.editor.commit();
    }

    public String getType() {
        return this.pref.getString(type, "");
    }

    public void setEditMode(String b) {
        this.editor.putString(editMode, b);
        this.editor.commit();
    }

    public String getEditMode() {
        return this.pref.getString(editMode, "");
    }

    public void setDBName(String databaseName) {
        editor.putString(PREF_DATABASE_NAME, databaseName);
        editor.apply();
    }

    public String getDBName() {
        return pref.getString(PREF_DATABASE_NAME, "");
    }



    public void setProjectName(String projectName) {
        editor.putString(ProjectName, projectName);
        editor.apply();
    }

    public String getProjectName() {
        return pref.getString(ProjectName, "");
    }

    public void setReportSelection(String value) {
        editor.putString(Report, value);
        editor.apply();
    }

    public String getReportSelection() {
        return pref.getString(Report, "");
    }

    public void setName(String name) {
        pref.edit().putString(Profile_Name, name).apply();
    }
    public String getName() {
        return pref.getString(Profile_Name, "");
    }

    public String getDesignation() {
        return this.pref.getString(Designation, "");
    }

    public void setDesignation(String b) {
        this.editor.putString(Designation, b);
        this.editor.commit();
    }

    public String getEmail() {
        return this.pref.getString(Email, "");
    }

    public void setEmail(String b) {
        this.editor.putString(Email, b);
        this.editor.commit();
    }



    public String getMobile() {
        return this.pref.getString(Mobile, "");
    }

    public void setMobile(String b) {
        this.editor.putString(Mobile, b);
        this.editor.commit();
    }

    public String getDate_Joined() {
        return this.pref.getString(Date_Joined, "");
    }

    public void setDate_Joined(String b) {
        this.editor.putString(Date_Joined, b);
        this.editor.commit();
    }

    public String getPlace_Of_Work() {
        return this.pref.getString(Place_Of_Work, "");
    }

    public void setPlace_Of_Work(String b) {
        this.editor.putString(Place_Of_Work, b);
        this.editor.commit();
    }

    public String getRegion() {
        return this.pref.getString(Region, "");
    }

    public void setRegion(String b) {
        this.editor.putString(Region, b);
        this.editor.commit();
    }

    public String getZone() {
        return this.pref.getString(Zone, "");
    }

    public void setZone(String b) {
        this.editor.putString(Zone, b);
        this.editor.commit();
    }

    public String getCircle() {
        return this.pref.getString(Circle, "");
    }

    public void setCircle(String b) {
        this.editor.putString(Circle, b);
        this.editor.commit();
    }

    public String getLastLogin() {
        return this.pref.getString(lastLogin, "");
    }

    public void setLastLogin(String b) {
        this.editor.putString(lastLogin, b);
        this.editor.commit();

    }

    public String getLastLogout() {
        return this.pref.getString(lastLogout, "");
    }

    public void setLastLogout(String b) {
        this.editor.putString(lastLogout, b);
        this.editor.commit();
    }

    public List<String> getConsumerClasses() {
        String json = pref.getString("KEY_CONSUMER_CLASSES", null);
        if (json == null) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveConsumerClasses(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("KEY_CONSUMER_CLASSES", json);
        editor.apply();
    }

    public String getDatabaseSurvey() {
        return pref.getString(Database_Survey, null);
    }

    public void setDatabaseSurvey(String databaseSurvey) {
        editor.putString(Database_Survey, databaseSurvey);
        editor.apply();
    }



}
