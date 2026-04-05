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
import com.techlabs.apdcl.databinding.ShuntreactorsnippetBinding;

import java.util.Objects;

public class ShuntReactorSnippet extends Dialog {

    private final Context mainContext;
    private ShuntreactorsnippetBinding binding;
    private JsonObject jsonObject;
    private String sectionID;
    private String DeviceNumber;
    private String EquipmentId;
    private String NetworkId;
    private String deviceType;

    public ShuntReactorSnippet(@NonNull Context context, String sectionID, String DeviceNumber, String EquipmentId, String NetworkId, String deviceType, JsonObject jsonObject) {
        super(context);
        this.mainContext = context;
        this.jsonObject = jsonObject;
        this.sectionID = sectionID;
        this.DeviceNumber = DeviceNumber;
        this.EquipmentId = EquipmentId;
        this.NetworkId = NetworkId;
        this.deviceType = deviceType;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ShuntreactorsnippetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        View root = Objects.requireNonNull(getWindow()).getDecorView().getRootView();
        root.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        root.setBackgroundResource(R.drawable.pop_background);

        binding.featuresTv.setText("Shunt Reactor");
        binding.cableTV.setText("Device No.");
        binding.lengthTV.setText("Equip ID");

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

        binding.imgClose.setOnClickListener(v -> dismiss());

        binding.moreBtn.setOnClickListener(v -> {
            jsonObject.addProperty("DeviceNumber", DeviceNumber);
            jsonObject.addProperty("DeviceType", deviceType);

            ShuntReactorDialog dialog = new ShuntReactorDialog(mainContext, jsonObject);
            dialog.show();
        });
    }
}
