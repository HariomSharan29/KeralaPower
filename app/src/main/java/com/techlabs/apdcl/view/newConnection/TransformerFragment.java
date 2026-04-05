package com.techlabs.apdcl.view.newConnection;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.Args;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.Utils.TransformerCallback;
import com.techlabs.apdcl.databinding.FragmentTransformerBinding;
import com.techlabs.apdcl.models.EquipmentModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransformerFragment extends Fragment {

    private FragmentTransformerBinding binding;
    private final String[] location = {"At FromNode", "At ToNode", "At Middle"};
    private final String[] status = {"Connected", "DisConnected"};
    private final String networkId;
    private final String applicationId;
    private final String voltage;
    private PrefManager prefManager;
    private String[] equipArray;

    public TransformerFragment(String networkId, String applicationId, String voltage) {
        this.networkId = networkId;
        this.applicationId = applicationId;
        this.voltage = voltage;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransformerBinding.inflate(inflater, container, false);
        prefManager = new PrefManager(getActivity());
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (applicationId != null) {
            binding.deviceNumberEdt.setText("DT_" + applicationId);
        }
        ArrayAdapter<String> statusAdapters = new ArrayAdapter<>(requireActivity(), R.layout.custom_spinner, status);
        binding.statusSpinnerBar.setAdapter(statusAdapters);
        binding.phaseShiftSpinnerBar.setAdapter(statusAdapters);

        ArrayAdapter<String> locationAdapters = new ArrayAdapter<>(requireActivity(), R.layout.custom_spinner, location);
        binding.locationSpinnerBar.setAdapter(locationAdapters);

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(requireActivity())) {
            getTransformerEquipment(networkId, "Transformer");
        } else {
            Snackbar.make(binding.getRoot(), "No Internet Connection", Snackbar.LENGTH_LONG).show();
        }

        Args.getNSCIsTransformerValidate().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                checkDetails();
            }
        });

    }

    private void getTransformerEquipment(String networkId, String transformer) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("NetworkId", networkId);
        jsonObject.addProperty("Type", "Voltage");
        jsonObject.addProperty("Subtype", transformer);
        jsonObject.addProperty("UserType", prefManager.getUserType());
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        jsonObject.addProperty("Voltage", voltage.trim());
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<EquipmentModel> call = apiInterface.getEquipmentSurveyData("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<EquipmentModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<EquipmentModel> call, @NonNull Response<EquipmentModel> response) {
                if (response.code() == 200) {
                    EquipmentModel equipmentModel = response.body();
                    if (equipmentModel != null && equipmentModel.getEquipmentId() != null && !equipmentModel.getEquipmentId().isEmpty()) {
                        equipArray = equipmentModel.getEquipmentId().toArray(new String[0]);
                        ArrayAdapter<String> adapters = new ArrayAdapter<>(requireContext(), R.layout.custom_spinner, equipArray);
                        binding.equipIdSpinnerBar.setAdapter(adapters);
                    } else {
                        equipArray = new String[]{"DEFAULT"};
                        ArrayAdapter<String> adapters = new ArrayAdapter<>(requireContext(), R.layout.custom_spinner, equipArray);
                        binding.equipIdSpinnerBar.setAdapter(adapters);
                    }
                } else {
                    @SuppressLint("InflateParams")
                    View layout = LayoutInflater.from(requireContext()).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView description = layout.findViewById(R.id.descripTv);
                    header.setText(response.message() + " - " + response.code());
                    description.setText(requireContext().getString(R.string.error_msg));
                    Ok.setOnClickListener(v -> getTransformerEquipment(networkId, transformer));
                    Toast toast = new Toast(requireContext());
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EquipmentModel> call, @NonNull Throwable t) {
                @SuppressLint("InflateParams")
                View layout = LayoutInflater.from(requireContext()).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView description = layout.findViewById(R.id.descripTv);
                header.setText(requireContext().getString(R.string.error));
                description.setText(requireContext().getString(R.string.error_msg));
                Ok.setOnClickListener(v -> getTransformerEquipment(networkId, transformer));
                Toast toast = new Toast(requireContext());
                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        requireView().requestLayout();
    }

    private void checkDetails() {
        if (binding != null && binding.equipIdSpinnerBar.getSelectedItem() != null && binding.deviceNumberEdt.getText() != null && binding.statusSpinnerBar.getSelectedItem() != null && binding.locationSpinnerBar.getSelectedItem() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("DeviceNumber", binding.deviceNumberEdt.getText().toString());
            bundle.putString("DeviceType", "5");
            if (binding.locationSpinnerBar.getSelectedItem().equals("At ToNode")) {
                bundle.putString("Location", "2");
            } else if (binding.locationSpinnerBar.getSelectedItem().equals("At FromNode")) {
                bundle.putString("Location", "1");
            } else {
                bundle.putString("Location", "0");
            }

            if (binding.statusSpinnerBar.getSelectedItem().equals("Connected")) {
                bundle.putString("Status", "0");
            } else {
                bundle.putString("Status", "1");
            }

            if (binding.phaseShiftSpinnerBar.getSelectedItem().equals("Connected")) {
                bundle.putString("phaseShift", "11");
            } else {
                bundle.putString("phaseShift", "12");
            }


            if (!binding.primaryVoltageEdt.getText().toString().isBlank()) {
                bundle.putString("Voltage_Primary", binding.primaryVoltageEdt.getText().toString().trim());
            } else {
                bundle.putString("Voltage_Primary", "");
            }

            if (!binding.secondaryVoltageEdt.getText().toString().isBlank()) {
                bundle.putString("Voltage_Secondary", binding.secondaryVoltageEdt.getText().toString().trim());
            } else {
                bundle.putString("Voltage_Secondary", "");
            }

            bundle.putString("EquipmentID", binding.equipIdSpinnerBar.getSelectedItem().toString());

            Args.setNSCTransformerParameter(bundle);
        }
    }
}

