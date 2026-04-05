package com.techlabs.apdcl.view.LayerInfo.DeviceInfo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.adapters.CustAdapter.CustomersAdapter;
import com.techlabs.apdcl.databinding.CustmbtnLayoutBinding;
import com.techlabs.apdcl.models.device.SpotLoad;

import java.util.List;
import java.util.Objects;

public class CustomerDetailsDialog extends Dialog {
    private CustmbtnLayoutBinding binding;
    private Context mainContext;
    private String deviceNumber;
    private PrefManager prefManager;
    private CustomersAdapter customersAdapter;

    private String[] formatList = {"kVA & PF", "kW & kvar", "kW & PF"};
    private String[] loadList = {"DEFAULT"};

    private List<SpotLoad.Output.CustomerData> customerData;

    public CustomerDetailsDialog(@NonNull Context context, String deviceNumber,List<SpotLoad.Output.CustomerData> customerData) {
        super(context);
        this.mainContext = context;
        this.deviceNumber = deviceNumber;
        this.customerData = customerData;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CustmbtnLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View MainLayoutBackGround = Objects.requireNonNull(getWindow()).getDecorView().getRootView();
        MainLayoutBackGround.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MainLayoutBackGround.setBackgroundResource(R.drawable.pop_background);
        prefManager = new PrefManager(mainContext);

        ArrayAdapter<String> loadAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, loadList);
        binding.loadModelSpinner.setAdapter(loadAdapter);

        ArrayAdapter<String> formatAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, formatList);
        binding.formatSpinner.setAdapter(formatAdapter);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mainContext));
        if (customerData != null && !customerData.isEmpty()) {
            customersAdapter = new CustomersAdapter(mainContext, customerData);
            binding.recyclerView.setAdapter(customersAdapter);
        } else {
            Toast.makeText(mainContext, "No customer data to display", Toast.LENGTH_SHORT).show();
        }

        binding.imgClose.setOnClickListener(v ->
                dismiss()
        );

    }

}


