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
import com.techlabs.apdcl.databinding.SnipetLayoutBinding;

import java.util.Objects;

public class ShuntCapacitorSnippet extends Dialog {

    private Context mainContext;
    private SnipetLayoutBinding binding;
    private JsonObject jsonObject;
    private String sectionID;
    private String DeviceNumber;
    private String EquipmentId;
    private String NetworkId;
    private String deviceType;

    public ShuntCapacitorSnippet(@NonNull Context context, String sectionID, String DeviceNumber, String EquipmentId, String NetworkId, String deviceType, JsonObject jsonObject) {
        super(context);
        this.mainContext = context;
        this.jsonObject = jsonObject;
        this.sectionID = DeviceNumber;
        this.DeviceNumber = DeviceNumber;
        this.EquipmentId = EquipmentId;
        this.NetworkId = NetworkId;
        this.deviceType = deviceType;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SnipetLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View MainLayoutBackGround = Objects.requireNonNull(getWindow()).getDecorView().getRootView();
        MainLayoutBackGround.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MainLayoutBackGround.setBackgroundResource(R.drawable.pop_background);

        binding.featuresTv.setText("ShuntCapacitor");

        binding.cableTV.setText("Device No.");
        binding.lengthTV.setText("Eqip ID");

        if (NetworkId != null && !NetworkId.isEmpty()) {
            binding.networkId.setText(NetworkId);
        } else {
            binding.networkId.setText("");
        }

        if (sectionID != null && !sectionID.isEmpty()) {
            binding.sectionId.setText(sectionID);
        } else {
            binding.sectionId.setText("");
        }

        if (DeviceNumber != null && !DeviceNumber.isEmpty()) {
            binding.cableID.setText(DeviceNumber);
        } else {
            binding.cableID.setText("");
        }

        if (EquipmentId != null && !EquipmentId.isEmpty()) {
            binding.lengthId.setText(EquipmentId);
        } else {
            binding.lengthId.setText("");
        }

        binding.imgClose.setOnClickListener(view -> {
            dismiss();
        });

        binding.imgClose.setOnClickListener(view -> {
            dismiss();
        });

        binding.moreBtn.setOnClickListener(view -> {
            jsonObject.addProperty("DeviceNumber", DeviceNumber);
            jsonObject.addProperty("DeviceType", deviceType);
            ShuntCapacitorDialog shuntCapacitorDialog = new ShuntCapacitorDialog(mainContext, jsonObject);
            shuntCapacitorDialog.show();
        });
    }

}
