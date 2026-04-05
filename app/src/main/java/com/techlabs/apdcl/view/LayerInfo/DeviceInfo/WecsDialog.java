package com.techlabs.apdcl.view.LayerInfo.DeviceInfo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.techlabs.apdcl.databinding.WecsdialogBinding;

public class WecsDialog extends Dialog {

    private WecsdialogBinding binding;

    public WecsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = WecsdialogBinding.inflate(LayoutInflater.from(getContext()));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.getRoot());

        // Full width dialog
        if (getWindow() != null) {
            getWindow().setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
        }

//        initUI();
    }

//    private void initUI() {
//
//        // Close button
//        binding.imgClose.setOnClickListener(v -> dismiss());
//
//        // Example: Get values
//        String sectionId = binding.sectionIdEdt.getText().toString();
//
//        // Phase checkbox
//        binding.aChkBox.setChecked(true);
//        binding.bChkBox.setChecked(false);
//        binding.cChkBox.setChecked(true);
//
//        // Settings phase (AB, BC, CA)
//        binding.aChksBox.setChecked(true);
//        binding.bChksBox.setChecked(false);
//        binding.cChksBox.setChecked(true);
//
//        // Generator values set
//        binding.shuntLosses.setText("100");
//        binding.activeGeneration.setText("50");
//        binding.powerFactor.setText("95");
//
//        // Override checkbox
//        binding.aChksBox2.setChecked(true); // rename issue explained below
//        binding.bChksBox2.setChecked(false);
//    }
}