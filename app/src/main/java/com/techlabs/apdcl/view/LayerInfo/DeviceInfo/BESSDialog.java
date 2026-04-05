package com.techlabs.apdcl.view.LayerInfo.DeviceInfo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.techlabs.apdcl.databinding.BessDialogBinding;

public class BESSDialog extends Dialog {

    private BessDialogBinding binding;

    public BESSDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = BessDialogBinding.inflate(LayoutInflater.from(getContext()));
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
//        // Example: Get value
//        String sectionId = binding.sectionIdEdt.getText().toString();
//
//        // Example: Set value
//        binding.ActiveGeneration.setText("0");
//
//        // Checkbox example
//        binding.aChkBox.setChecked(true);
//        binding.bChkBox.setChecked(false);
//        binding.cChkBox.setChecked(false);
//    }
}