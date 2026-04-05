package com.techlabs.apdcl.Utils;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static String sectionId = null;
    public static boolean isLoadFlow = false;
    public static boolean isShortCircuit = false;
    public static boolean isLoadAllocation = false;
    public static boolean isTreeNode = false;
    public static List<String> networkIdList = new ArrayList<>();
    public static String scDeviceNumber = null;
    public static boolean isSystemVoltageColor = false;
    public static boolean isPhaseColor = false;
    public static boolean isLayerColor = false;

    public static MutableLiveData<Boolean> isSectionValidate = new MutableLiveData<>();
    public static MutableLiveData<Bundle> sectionParameter = new MutableLiveData<>();
    public static MutableLiveData<Boolean> isTransformerValidate = new MutableLiveData<>();
    public static MutableLiveData<Bundle> transformerParameter = new MutableLiveData<>();
    public static MutableLiveData<Boolean> isSpotloadValidate = new MutableLiveData<>();
    public static MutableLiveData<Bundle> spotloadParameter = new MutableLiveData<>();

    public static LiveData<Boolean> getIsSectionValidate() {
        return isSectionValidate;
    }

    public static void setSectionValidate(boolean value) {
        isSectionValidate.setValue(value);
    }

    public static LiveData<Bundle> getSectionParameter() {
        return sectionParameter;
    }

    public static void setSectionParameter(Bundle bundle) {
        sectionParameter.setValue(bundle);
    }

    public static LiveData<Boolean> getIsTransformerValidate() {
        return isTransformerValidate;
    }

    public static void setIsTransformerValidate(boolean value) {
        isTransformerValidate.setValue(value);
    }

    public static LiveData<Bundle> getTransformerParameter() {
        return transformerParameter;
    }

    public static void setTransformerParameter(Bundle bundle) {
        transformerParameter.setValue(bundle);
    }

    public static LiveData<Boolean> getIsSpotloadValidate() {
        return isSpotloadValidate;
    }

    public static void setIsSpotloadValidate(boolean value) {
        isTransformerValidate.setValue(value);
    }

    public static LiveData<Bundle> getSpotloadParameter() {
        return spotloadParameter;
    }

    public static void setSpotloadParameter(Bundle bundle) {
        transformerParameter.setValue(bundle);
    }

}
