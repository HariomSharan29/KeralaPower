package com.techlabs.apdcl.view.LayerInfo.DeviceInfo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.annotation.NonNull;

import com.techlabs.apdcl.databinding.PhotovoltaicsystemdialogBinding;


public class PhotoVoltaicSnippet extends Dialog {

    private PhotovoltaicsystemdialogBinding binding;
    private Context context;

    public PhotoVoltaicSnippet(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = PhotovoltaicsystemdialogBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());

//        initViews();
    }

//    private void initViews() {
//
//        // Close button
//        binding.imgClose.setOnClickListener(v -> dismiss());
//
//        // Example data set (replace with API data)
//        binding.networkId.setText("12345");
//        binding.sectionId.setText("A1");
//        binding.cableID.setText("PV-001");
//        binding.lengthId.setText("50");
//
//        // Button click
//        binding.moreBtn.setOnClickListener(v -> {
//            // Open detail dialog or activity
//        });
//
//        binding.okBtn.setOnClickListener(v -> dismiss());
//    }
}