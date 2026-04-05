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

public class SpotLoadSnippet extends Dialog {

    private Context mainContext;
    private SnipetLayoutBinding binding;
    private JsonObject jsonObject;
    private String DeviceNumber;
    private String NetworkId;
    private String ConnectKva;
    private String customer;
    private String DeviceType;

    public SpotLoadSnippet(@NonNull Context context, String DeviceNumber, String NetworkId, String ConnectKva, String Kwh, String DeviceType, JsonObject jsonObject) {
        super(context);
        this.mainContext = context;
        this.DeviceNumber = DeviceNumber;
        this.NetworkId = NetworkId;
        this.ConnectKva = ConnectKva;
        this.customer = Kwh;
        this.DeviceType = DeviceType;
        this.jsonObject = jsonObject;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SnipetLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View MainLayoutBackGround = Objects.requireNonNull(getWindow()).getDecorView().getRootView();
        MainLayoutBackGround.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MainLayoutBackGround.setBackgroundResource(R.drawable.pop_background);

        binding.featuresTv.setText("SpotLoad");
        binding.sectionTv.setText("Device No.");
        binding.cableTV.setText("Connected Kva    ");
        binding.lengthTV.setText("Customers");


        if (NetworkId != null && !NetworkId.isEmpty()) {
            binding.networkId.setText(NetworkId);
        } else {
            binding.networkId.setText("");
        }

        if (DeviceNumber != null && !DeviceNumber.isEmpty()) {
            binding.sectionId.setText(DeviceNumber);
        } else {
            binding.sectionId.setText("");
        }

        if (ConnectKva != null && !ConnectKva.isEmpty()) {
            binding.cableID.setText(String.format("%.2f", Float.parseFloat(ConnectKva)) + " " + "kVA");
        } else {
            binding.cableID.setText("");
        }

        if (customer != null && !customer.isEmpty()) {
            binding.lengthId.setText(customer);
        } else {
            binding.lengthId.setText("");
        }

        binding.imgClose.setOnClickListener(view -> {
            dismiss();
        });

        binding.moreBtn.setOnClickListener(view ->
        {
            jsonObject.addProperty("DeviceNumber", DeviceNumber);
            jsonObject.addProperty("DeviceType", DeviceType);
            SpotLoadDialog spotLoadDialog = new SpotLoadDialog(mainContext, jsonObject);
            spotLoadDialog.show();
        });


    }

}
