package com.techlabs.apdcl.view.newConnection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.Args;
import com.techlabs.apdcl.databinding.FragmentSpotloadBinding;

public class SpotloadFragment extends Fragment {

    private FragmentSpotloadBinding binding;
    private final String[] location = {"At ToNode", "At FromNode"};
    private final String[] status = {"Connected", "DisConnected"};
    private final String phaseType;
    private String phase;
    private final String applicationID;
    private AddCustomerInfoDialog addCustomerInfoDialog;
    private final Context mainContext;

    public SpotloadFragment(String phaseType, String phase, String applicationID, Context context) {
        this.phaseType = phaseType;
        this.phase = phase;
        this.applicationID = applicationID;
        this.mainContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSpotloadBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (applicationID != null) {
            binding.spNumberEdt.setText("SP_" + applicationID);
        }

        if (phaseType.equals("byPhase")) {
            binding.singlePhaseCheckedLayout.setVisibility(View.VISIBLE);
            binding.threePhaseCheckedLayout.setVisibility(View.GONE);
        } else {
            binding.singlePhaseCheckedLayout.setVisibility(View.GONE);
            binding.threePhaseCheckedLayout.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<String> statusAdapters = new ArrayAdapter<>(requireActivity(), R.layout.custom_spinner, status);
        binding.statusSpinnerBar.setAdapter(statusAdapters);

        ArrayAdapter<String> locationAdapters = new ArrayAdapter<>(requireActivity(), R.layout.custom_spinner, location);
        binding.locationSpinnerBar.setAdapter(locationAdapters);

        Args.getNSCIsSpotloadValidate().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                checkDetails();
            }
        });

        Args.getNSCIsPhaseValidate().observe(getViewLifecycleOwner(), s -> {
            if (s != null) {
                phase = s;
            }
        });

        binding.customDetails.setOnClickListener(v -> {
            addCustomerInfoDialog = new AddCustomerInfoDialog(requireActivity(), phaseType, phase,applicationID);
            addCustomerInfoDialog.setCancelable(false);
            addCustomerInfoDialog.show();
        });

    }

    private void checkDetails() {
        binding.spNumberEdt.setError(null);
        boolean isCancel = false;
        View focusView = null;

        if (phaseType.equals("byPhase")) {
            if (binding.ccAEdt.getText().toString().trim().isEmpty()) {
                binding.ccAEdt.setError(Html.fromHtml("<font color='red'>All field required!</font>"));
                focusView = binding.ccAEdt;
                isCancel = true;
            }

            if (binding.ccBEdt.getText().toString().trim().isEmpty()) {
                binding.ccBEdt.setError(Html.fromHtml("<font color='red'>All field required!</font>"));
                focusView = binding.ccBEdt;
                isCancel = true;
            }

            if (binding.ccCEdt.getText().toString().trim().isEmpty()) {
                binding.ccCEdt.setError(Html.fromHtml("<font color='red'>All field required!</font>"));
                focusView = binding.ccCEdt;
                isCancel = true;
            }

            if (binding.customerAEdt.getText().toString().trim().isEmpty()) {
                binding.customerAEdt.setError(Html.fromHtml("<font color='red'>Customer Can't be empty! Please correct</font>"));
                focusView = binding.customerAEdt;
                isCancel = true;
            }

            if (binding.customerBEdt.getText().toString().trim().isEmpty()) {
                binding.customerBEdt.setError(Html.fromHtml("<font color='red'>Customer Can't be empty! Please correct</font>"));
                focusView = binding.customerBEdt;
                isCancel = true;
            }

            if (binding.customerCEdt.getText().toString().trim().isEmpty()) {
                binding.customerCEdt.setError(Html.fromHtml("<font color='red'>Customer Can't be empty! Please correct</font>"));
                focusView = binding.customerCEdt;
                isCancel = true;
            }

        } else {

            if (binding.spConnectedCapacityEdt.getText().toString().isEmpty()) {
                binding.spConnectedCapacityEdt.setError(Html.fromHtml("<font color='red'>Connected Capacity Can't be empty! Please correct</font>"));
                focusView = binding.spConnectedCapacityEdt;
                isCancel = true;
            }

            if (binding.spCustomersEdt.getText().toString().isEmpty()) {
                binding.spCustomersEdt.setError(Html.fromHtml("<font color='red'>Customer Can't be empty! Please correct</font>"));
                focusView = binding.spCustomersEdt;
                isCancel = true;
            }
        }

        if (isCancel) {
            focusView.requestFocus();
        } else {
            if (binding.spNumberEdt.getText() != null && binding.locationSpinnerBar.getSelectedItem() != null && binding.statusSpinnerBar.getSelectedItem() != null ) {
                Bundle bundle = new Bundle();
                bundle.putString("DeviceNumber", binding.spNumberEdt.getText().toString().trim());
                bundle.putString("DeviceType", "20");
                bundle.putString("Status", binding.statusSpinnerBar.getSelectedItem().equals("Connected") ? "0" : "1");
                bundle.putString("PhaseType", phaseType.equals("ThreePhase") ? "ThreePhase" : "SinglePhase");
                bundle.putString("LoadValueType", "2");
                if (binding.locationSpinnerBar.getSelectedItem().equals("At ToNode")){
                    bundle.putString("Location", "2");
                }else {
                    bundle.putString("Location", "1");
                }
                bundle.putString("ID","DEFAULT");
                Args.setNSCSpotloadParameter(bundle);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getView().requestLayout();
    }

}
