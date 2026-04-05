package com.techlabs.apdcl.view.LayerInfo.DeviceInfo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.databinding.WindDeviceSnippetBinding;

import java.util.Objects;

public class WindSnippet extends Dialog {

    private final Context mainContext;
    private final JsonObject jsonObject;
    private final String sectionID;
    private final String deviceNumber;
    private final String equipmentId;
    private final String networkId;
    private final String deviceType;
    private WindDeviceSnippetBinding binding;

    public WindSnippet(@NonNull Context context, String sectionID, String deviceNumber, String equipmentId, String networkId, String deviceType, JsonObject jsonObject) {
        super(context);
        this.mainContext = context;
        this.jsonObject = jsonObject;
        this.sectionID = sectionID;
        this.deviceNumber = deviceNumber;
        this.equipmentId = equipmentId;
        this.networkId = networkId;
        this.deviceType = deviceType;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = WindDeviceSnippetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        View root = Objects.requireNonNull(getWindow()).getDecorView().getRootView();
        root.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        root.setBackgroundResource(R.drawable.pop_background);

        binding.featuresTv.setText("Wind");
        binding.cableTV.setText("Device No.");
        binding.lengthTV.setText("Equip ID");
        binding.networkId.setText(networkId != null ? networkId : "");
        binding.sectionId.setText(sectionID != null ? sectionID : "");
        binding.cableID.setText(deviceNumber != null ? deviceNumber : "");
        binding.lengthId.setText(equipmentId != null ? equipmentId : "");

        binding.imgClose.setOnClickListener(v -> dismiss());
        binding.moreBtn.setOnClickListener(v -> {
            jsonObject.addProperty("DeviceNumber", deviceNumber);
            jsonObject.addProperty("DeviceType", deviceType);
            WindDeviceInfoDialog dialog = new WindDeviceInfoDialog(mainContext, jsonObject);
            dialog.show();
        });
    }
}
