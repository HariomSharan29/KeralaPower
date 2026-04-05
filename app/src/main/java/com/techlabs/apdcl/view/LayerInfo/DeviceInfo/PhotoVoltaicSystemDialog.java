package com.techlabs.apdcl.view.LayerInfo.DeviceInfo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.techlabs.apdcl.databinding.PhotovoltaicsystemdialogBinding;


public class PhotoVoltaicSystemDialog extends Dialog {

    private PhotovoltaicsystemdialogBinding binding;

    public PhotoVoltaicSystemDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = PhotovoltaicsystemdialogBinding.inflate(LayoutInflater.from(getContext()));
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
//        // Close dialog
//        binding.imgClose.setOnClickListener(v -> dismiss());
//
//        // Example: Get data
//        String section = binding.sectionIdEdt.getText().toString();
//
//        // Checkbox example
//        binding.aChkBox.setChecked(true);
//        binding.bChkBox.setChecked(false);
//        binding.cChkBox.setChecked(true);
//
//        // System Components checkbox
//        binding.aChksBox.setChecked(true);
//        binding.bChksBox.setChecked(false);
//        binding.cChksBox.setChecked(true);
//
//        // Spinner setup
//        String[] options = {"PV", "BESS", "Converter"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                getContext(),
//                android.R.layout.simple_spinner_dropdown_item,
//                options
//        );
//        binding.mySpinner.setAdapter(adapter);
//    }
}