package com.techlabs.apdcl.view.fragment;

import static com.techlabs.apdcl.Utils.Config.scDeviceNumber;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ShortCircuitArgument;
import com.techlabs.apdcl.databinding.FragmentShortCircuitRatingBinding;

import java.util.ArrayList;
import java.util.List;

public class ShortCircuitRating extends Fragment {

    private FragmentShortCircuitRatingBinding binding;
    private String[] limitCategories = {"Critical", "Marginal", "Level3", "Level4", "Level5"};
    private PrefManager prefManager;
    private ShortCircuitArgument shortCircuitArgument;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            shortCircuitArgument = (ShortCircuitArgument) getActivity();
            binding = FragmentShortCircuitRatingBinding.inflate(inflater, container, false);
            return binding.getRoot();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity() + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefManager = new PrefManager(getActivity());

        ArrayAdapter<String> limitCategoryAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner, limitCategories);
        binding.limitCategorySpinner.setAdapter(limitCategoryAdapter);

        binding.limitCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = limitCategories[position];
                resetEdits();

                switch (selectedCategory) {
                    case "Critical":
                        enableViews(
                                R.color.blue,
                                binding.condCri, binding.cableCri, binding.fuseCri, binding.recloCri,
                                binding.lvbcCri, binding.breCri, binding.secCri, binding.capReaCri,
                                binding.nodeCri, binding.voltCri
                        );
                        break;
                    case "Marginal":
                        enableViews(
                                R.color.blue,
                                binding.condMar, binding.cableMar, binding.fuseMar, binding.recloMar,
                                binding.lvbcMar, binding.breMar, binding.secMar, binding.capReaMar,
                                binding.nodeMar, binding.voltMar
                        );
                        break;

                    case "Level3":
                        enableViews(
                                R.color.blue,
                                binding.condLev3, binding.cableLev3, binding.fuseLev3, binding.recloLev3,
                                binding.lvbcLev3, binding.breLev3, binding.secLev3, binding.capReaLev3,
                                binding.nodeLev3, binding.voltLev3
                        );
                        break;

                    case "Level4":
                        enableViews(
                                R.color.blue,
                                binding.condLev4, binding.cableLev4, binding.fuseLev4, binding.recloLev4,
                                binding.lvbcLev4, binding.breLev4, binding.secLev4, binding.capReaLev4,
                                binding.nodeLev4, binding.voltLev4
                        );
                        break;

                    case "Level5":
                        enableViews(
                                R.color.blue,
                                binding.condLev5, binding.cableLev5, binding.fuseLev5, binding.recloLev5,
                                binding.lvbcLev5, binding.breLev5, binding.secLev5, binding.capReaLev5,
                                binding.nodeLev5, binding.voltLev5
                        );
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        /*binding.okBtn.setOnClickListener(v -> {
            SendShortCircuitArguments();
        });*/

        binding.applyLimitCategory.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                resetEdits();
            }
        });

        /*binding.cancelBtn.setOnClickListener(v -> {
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

            String selectedCategory = binding.limitCategorySpinner.getSelectedItem().toString();
            collectValues(jsonObject, selectedCategory);

            jsonObject.addProperty("Calculate", "FF");
            jsonObject.addProperty("LocationType", "53");

            if (scDeviceNumber != null) {
                jsonObject.addProperty("DeviceNumber", scDeviceNumber);
            }

            jsonObject.addProperty("FaultPosition", "50.0");
            jsonObject.addProperty("FaultType", "LLL");
            jsonObject.addProperty("FaultPhase", "A");
            jsonObject.addProperty("Method", "1");
            jsonObject.addProperty("NominalTap", "0");
            jsonObject.addProperty("AImpedence", "1");
            jsonObject.addProperty("SyncGenerator", "1");
            jsonObject.addProperty("WECS", "1");
            jsonObject.addProperty("Photovoltaic", "1");

            if (binding.applyLimitCategory.isChecked()) {
                jsonObject.addProperty("LimitCategories", "1");
            } else {
                jsonObject.addProperty("LimitCategories", "0");
            }

            Log.d("ShortCircuit", "Collected values: " + jsonObject.toString());

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

    private void enableViews(int colorResId, EditText... edit) {
        int color = getResources().getColor(colorResId, null);
        for (EditText text : edit) {
            text.setTextColor(color);
            text.setFocusableInTouchMode(true);
            text.setFocusable(true);
            text.setClickable(true);
            text.setEnabled(true);
        }
    }

    private void reset(int color, EditText... edit) {
        for (EditText text : edit) {
            text.setTextColor(color);
            text.setFocusable(false);
            text.setFocusableInTouchMode(false);
            text.setClickable(false);
            text.setEnabled(false);
        }
    }

    private void resetEdits() {
        int defaultColor = getResources().getColor(R.color.black, null);
        reset(
                defaultColor,
                binding.condCri, binding.cableCri, binding.fuseCri, binding.recloCri,
                binding.lvbcCri, binding.breCri, binding.secCri, binding.capReaCri,
                binding.nodeCri, binding.voltCri,
                binding.condMar, binding.cableMar, binding.fuseMar, binding.recloMar,
                binding.lvbcMar, binding.breMar, binding.secMar, binding.capReaMar,
                binding.nodeMar, binding.voltMar,
                binding.condLev3, binding.cableLev3, binding.fuseLev3, binding.recloLev3,
                binding.lvbcLev3, binding.breLev3, binding.secLev3, binding.capReaLev3,
                binding.nodeLev3, binding.voltLev3,
                binding.condLev4, binding.cableLev4, binding.fuseLev4, binding.recloLev4,
                binding.lvbcLev4, binding.breLev4, binding.secLev4, binding.capReaLev4,
                binding.nodeLev4, binding.voltLev4,
                binding.condLev5, binding.cableLev5, binding.fuseLev5, binding.recloLev5,
                binding.lvbcLev5, binding.breLev5, binding.secLev5, binding.capReaLev5,
                binding.nodeLev5, binding.voltLev5
        );
    }

    private void collectValues(JsonObject jsonObject, String category) {
        boolean isEdited = false;
        switch (category) {
            case "Critical":
                jsonObject.addProperty("ConductorDB", binding.condCri.getText().toString().isEmpty() ? "95.0" : binding.condCri.getText().toString());
                jsonObject.addProperty("CableDB", binding.cableCri.getText().toString().isEmpty() ? "95.0" : binding.cableCri.getText().toString());
                jsonObject.addProperty("FuseDB", binding.fuseCri.getText().toString().isEmpty() ? "95.0" : binding.fuseCri.getText().toString());
                jsonObject.addProperty("RecloserDB", binding.recloCri.getText().toString().isEmpty() ? "95.0" : binding.recloCri.getText().toString());
                jsonObject.addProperty("LVCBDB", binding.lvbcCri.getText().toString().isEmpty() ? "95.0" : binding.lvbcCri.getText().toString());
                jsonObject.addProperty("BreakerDB", binding.breCri.getText().toString().isEmpty() ? "95.0" : binding.breCri.getText().toString());
                jsonObject.addProperty("SectionalizerDB", binding.secCri.getText().toString().isEmpty() ? "95.0" : binding.secCri.getText().toString());
                jsonObject.addProperty("NetworkProtectorDB", binding.capReaCri.getText().toString().isEmpty() ? "95.0" : binding.capReaCri.getText().toString());
                jsonObject.addProperty("ShuntCapacitorDB", binding.nodeCri.getText().toString().isEmpty() ? "95.0" : binding.nodeCri.getText().toString());
                jsonObject.addProperty("overvoltage", binding.voltCri.getText().toString().isEmpty() ? "95.0" : binding.voltCri.getText().toString());
                break;

            case "Marginal":
                jsonObject.addProperty("ConductorDB", binding.condMar.getText().toString().isEmpty() ? "95.0" : binding.condMar.getText().toString());
                jsonObject.addProperty("CableDB", binding.cableMar.getText().toString().isEmpty() ? "95.0" : binding.cableMar.getText().toString());
                jsonObject.addProperty("FuseDB", binding.fuseMar.getText().toString().isEmpty() ? "95.0" : binding.fuseMar.getText().toString());
                jsonObject.addProperty("RecloserDB", binding.recloMar.getText().toString().isEmpty() ? "95.0" : binding.recloMar.getText().toString());
                jsonObject.addProperty("LVCBDB", binding.lvbcMar.getText().toString().isEmpty() ? "95.0" : binding.lvbcMar.getText().toString());
                jsonObject.addProperty("BreakerDB", binding.breMar.getText().toString().isEmpty() ? "95.0" : binding.breMar.getText().toString());
                jsonObject.addProperty("SectionalizerDB", binding.secMar.getText().toString().isEmpty() ? "95.0" : binding.secMar.getText().toString());
                jsonObject.addProperty("NetworkProtectorDB", binding.capReaMar.getText().toString().isEmpty() ? "95.0" : binding.capReaMar.getText().toString());
                jsonObject.addProperty("ShuntCapacitorDB", binding.nodeMar.getText().toString().isEmpty() ? "95.0" : binding.nodeMar.getText().toString());
                jsonObject.addProperty("overvoltage", binding.voltMar.getText().toString().isEmpty() ? "95.0" : binding.voltMar.getText().toString());
                break;

            case "Level3":
                jsonObject.addProperty("ConductorDB", binding.condLev3.getText().toString().isEmpty() ? "95.0" : binding.condLev3.getText().toString());
                jsonObject.addProperty("CableDB", binding.cableLev3.getText().toString().isEmpty() ? "95.0" : binding.cableLev3.getText().toString());
                jsonObject.addProperty("FuseDB", binding.fuseLev3.getText().toString().isEmpty() ? "95.0" : binding.fuseLev3.getText().toString());
                jsonObject.addProperty("RecloserDB", binding.recloLev3.getText().toString().isEmpty() ? "95.0" : binding.recloLev3.getText().toString());
                jsonObject.addProperty("LVCBDB", binding.lvbcLev3.getText().toString().isEmpty() ? "95.0" : binding.lvbcLev3.getText().toString());
                jsonObject.addProperty("BreakerDB", binding.breLev3.getText().toString().isEmpty() ? "95.0" : binding.breLev3.getText().toString());
                jsonObject.addProperty("SectionalizerDB", binding.secLev3.getText().toString().isEmpty() ? "95.0" : binding.secLev3.getText().toString());
                jsonObject.addProperty("NetworkProtectorDB", binding.capReaLev3.getText().toString().isEmpty() ? "95.0" : binding.capReaLev3.getText().toString());
                jsonObject.addProperty("ShuntCapacitorDB", binding.nodeLev3.getText().toString().isEmpty() ? "95.0" : binding.nodeLev3.getText().toString());
                jsonObject.addProperty("overvoltage", binding.voltLev3.getText().toString().isEmpty() ? "95.0" : binding.voltLev3.getText().toString());
                break;

            case "Level4":
                jsonObject.addProperty("ConductorDB", binding.condLev4.getText().toString().isEmpty() ? "95.0" : binding.condLev4.getText().toString());
                jsonObject.addProperty("CableDB", binding.cableLev4.getText().toString().isEmpty() ? "95.0" : binding.cableLev4.getText().toString());
                jsonObject.addProperty("FuseDB", binding.fuseLev4.getText().toString().isEmpty() ? "95.0" : binding.fuseLev4.getText().toString());
                jsonObject.addProperty("RecloserDB", binding.recloLev4.getText().toString().isEmpty() ? "95.0" : binding.recloLev4.getText().toString());
                jsonObject.addProperty("LVCBDB", binding.lvbcLev4.getText().toString().isEmpty() ? "95.0" : binding.lvbcLev4.getText().toString());
                jsonObject.addProperty("BreakerDB", binding.breLev4.getText().toString().isEmpty() ? "95.0" : binding.breLev4.getText().toString());
                jsonObject.addProperty("SectionalizerDB", binding.secLev4.getText().toString().isEmpty() ? "95.0" : binding.secLev4.getText().toString());
                jsonObject.addProperty("NetworkProtectorDB", binding.capReaLev4.getText().toString().isEmpty() ? "95.0" : binding.capReaLev4.getText().toString());
                jsonObject.addProperty("ShuntCapacitorDB", binding.nodeLev4.getText().toString().isEmpty() ? "95.0" : binding.nodeLev4.getText().toString());
                jsonObject.addProperty("overvoltage", binding.voltLev4.getText().toString().isEmpty() ? "95.0" : binding.voltLev4.getText().toString());
                break;

            case "Level5":
                jsonObject.addProperty("ConductorDB", binding.condLev5.getText().toString().isEmpty() ? "95.0" : binding.condLev5.getText().toString());
                jsonObject.addProperty("CableDB", binding.cableLev5.getText().toString().isEmpty() ? "95.0" : binding.cableLev5.getText().toString());
                jsonObject.addProperty("FuseDB", binding.fuseLev5.getText().toString().isEmpty() ? "95.0" : binding.fuseLev5.getText().toString());
                jsonObject.addProperty("RecloserDB", binding.recloLev5.getText().toString().isEmpty() ? "95.0" : binding.recloLev5.getText().toString());
                jsonObject.addProperty("LVCBDB", binding.lvbcLev5.getText().toString().isEmpty() ? "95.0" : binding.lvbcLev5.getText().toString());
                jsonObject.addProperty("BreakerDB", binding.breLev5.getText().toString().isEmpty() ? "95.0" : binding.breLev5.getText().toString());
                jsonObject.addProperty("SectionalizerDB", binding.secLev5.getText().toString().isEmpty() ? "95.0" : binding.secLev5.getText().toString());
                jsonObject.addProperty("NetworkProtectorDB", binding.capReaLev5.getText().toString().isEmpty() ? "95.0" : binding.capReaLev5.getText().toString());
                jsonObject.addProperty("ShuntCapacitorDB", binding.nodeLev5.getText().toString().isEmpty() ? "95.0" : binding.nodeLev5.getText().toString());
                jsonObject.addProperty("overvoltage", binding.voltLev5.getText().toString().isEmpty() ? "95.0" : binding.voltLev5.getText().toString());
                break;
        }
        if (!isEdited) {
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
        }
    }
}