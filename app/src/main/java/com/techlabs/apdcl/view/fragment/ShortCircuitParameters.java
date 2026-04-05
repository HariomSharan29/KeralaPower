package com.techlabs.apdcl.view.fragment;

import static com.techlabs.apdcl.Utils.Config.scDeviceNumber;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ShortCircuitArgument;
import com.techlabs.apdcl.databinding.FragmentShortCircuitParametersBinding;

import java.util.ArrayList;
import java.util.List;

public class ShortCircuitParameters extends Fragment {

    private FragmentShortCircuitParametersBinding binding;
    private String[] calculation = {"Nominal Voltage", "Operating Voltage", "Load Flow Solution"};
    private PrefManager prefManager;
    private ShortCircuitArgument shortCircuitArgument;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            shortCircuitArgument = (ShortCircuitArgument) getActivity();
            binding = FragmentShortCircuitParametersBinding.inflate(inflater, container, false);
            return binding.getRoot();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity() + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefManager = new PrefManager(getActivity());

        ArrayAdapter<String> calculationAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner, calculation);
        binding.calculationSpinner.setAdapter(calculationAdapter);

        /*binding.okBtn.setOnClickListener(v -> {
            SendShortCircuitArguments();
        });

        binding.cancelBtn.setOnClickListener(v -> {
            JsonObject jsonObject = new JsonObject();
            List<String> list = new ArrayList<>();
            jsonObject.addProperty("ShortCircuit", false);
            if (shortCircuitArgument != null) {
                shortCircuitArgument.onShortCircuitArgReceived(jsonObject, list);
            }
        });*/
    }

    public void SendShortCircuitArguments() {
        JsonObject jsonObject = new JsonObject();
        Bundle bundle = this.getArguments();
        assert bundle != null;
        if (bundle.get("Network") != null) {
            jsonObject.addProperty("Username", prefManager.getUserName());
            jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            List<String> list = new ArrayList<String>((ArrayList<String>) bundle.get("Network"));
            JsonArray jsonArray = new Gson().toJsonTree(list).getAsJsonArray();
            jsonObject.add("NetworkId", jsonArray);
            jsonObject.addProperty("Calculate", "FF");
            jsonObject.addProperty("LocationType", "53");

            if (scDeviceNumber != null) {
                jsonObject.addProperty("DeviceNumber", scDeviceNumber);
            }

            jsonObject.addProperty("FaultPosition", "50.0");
            jsonObject.addProperty("FaultType", "LLL");
            jsonObject.addProperty("FaultPhase", "A");

            if (binding.calculationSpinner.getSelectedItem().toString().contains("Nominal Voltage")) {
                jsonObject.addProperty("Method", "0");
            } else if (binding.calculationSpinner.getSelectedItem().toString().contains("Operating Voltage")) {
                jsonObject.addProperty("Method", "1");
            } else {
                jsonObject.addProperty("Method", "2");
            }

            if (binding.transformerAtNominalTap.isChecked()) {
                jsonObject.addProperty("NominalTap", "1");
            } else {
                jsonObject.addProperty("NominalTap", "0");
            }

            if (binding.adjustmentImpedence.isChecked()) {
                jsonObject.addProperty("AImpedence", "1");
            } else {
                jsonObject.addProperty("AImpedence", "0");
            }

            if (binding.synchronousGenerater.isChecked()) {
                jsonObject.addProperty("SyncGenerator", "1");
            } else {
                jsonObject.addProperty("SyncGenerator", "0");
            }

            if (binding.weCs.isChecked()) {
                jsonObject.addProperty("WECS", "1");
            } else {
                jsonObject.addProperty("WECS", "0");
            }

            if (binding.photoVoltaic.isChecked()) {
                jsonObject.addProperty("Photovoltaic", "1");
            } else {
                jsonObject.addProperty("Photovoltaic", "0");
            }

            jsonObject.addProperty("LimitCategories", "1");
            jsonObject.addProperty("ConductorDB", "95.0");
            jsonObject.addProperty("CableDB", "95.0");
            jsonObject.addProperty("FuseDB", "95.0");
            jsonObject.addProperty("RecloserDB", "95.0");
            jsonObject.addProperty("LVCBDB", "95.0");
            jsonObject.addProperty("BreakerDB", "95.0");
            jsonObject.addProperty("SectionalizerDB", "95.0");
            jsonObject.addProperty("NetworkProtectorDB", "95.0");
            jsonObject.addProperty("ShuntCapacitorDB", "95.0");
            jsonObject.addProperty("overvoltage", "95.0");

            if (shortCircuitArgument != null) {
                shortCircuitArgument.onShortCircuitArgReceived(jsonObject, (ArrayList<String>) bundle.get("Network"));
            }
        }
    }
}