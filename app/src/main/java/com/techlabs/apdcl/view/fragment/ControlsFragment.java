package com.techlabs.apdcl.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.LoadFlowArgument;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.databinding.FragmentControlsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ControlsFragment extends Fragment {

    private FragmentControlsBinding binding;
    private final String[] TransformerTabOperation = {"Normal Tap Operation", "Infinite Taps", "Lock Taps at their specified positions", "Disable Tap Changer"};
    private final String[] RegulatorTabOperation = {"Normal Tap Operation", "Normal Tap Operation - Lowest Tap In Range",
            "Normal Tap Operation - Highest Tap In Range", "Infinite Taps",
            "Lock Taps At Their Specified Positions", "Disable Tap Changer"};
    private LoadFlowArgument mListener;
    private PrefManager prefManager;

    public ControlsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            mListener = (LoadFlowArgument) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity() + " must implement BottomSheetListener");
        }
        binding = FragmentControlsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefManager = new PrefManager(getActivity());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), R.layout.custom_spinner, TransformerTabOperation);
        binding.transformerTabOperation.setAdapter(adapter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(requireActivity(), R.layout.custom_spinner, RegulatorTabOperation);
        binding.regularTabOperation.setAdapter(adapter1);

       /* binding.okBtn.setOnClickListener(view1 -> {
            Bundle bundle = this.getArguments();
            sendJsonObjectToActivity();

        });

        binding.cancelBtn.setOnClickListener(view1 -> {
            JsonObject jsonObject = new JsonObject();
            JsonObject dashBoardJsonObject = new JsonObject();
            List<String> list = new ArrayList<>();
            jsonObject.addProperty("isLoadFlow", false);
            if (mListener != null) {
                mListener.onJsonObjectReceived(jsonObject, dashBoardJsonObject, list);
            }
        });*/

    }

    public void sendJsonObjectToActivity() {
        JsonObject jsonObject = new JsonObject();
        JsonObject dashBoardJsonObject = new JsonObject();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.get("Network") != null) {
                List<String> list = new ArrayList<String>((ArrayList<String>) Objects.requireNonNull(bundle.get("Network")));
                JsonArray jsonArray = new Gson().toJsonTree(list).getAsJsonArray();
                jsonObject.add("NetworkId", jsonArray);
                dashBoardJsonObject.add("NetworkId", jsonArray);
                dashBoardJsonObject.addProperty("UserType", prefManager.getUserType());
                jsonObject.addProperty("Username", prefManager.getUserName());
                jsonObject.addProperty("UserType", prefManager.getUserType());
                jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
                jsonObject.addProperty("method", "VoltageDropUnbalanced");
                jsonObject.addProperty("Tolerance", "0.01");
                jsonObject.addProperty("Iterations", "60");
                jsonObject.addProperty("AmbientTemperature", "77");
                jsonObject.addProperty("hour", "12");
                jsonObject.addProperty("minute", "15");
                jsonObject.addProperty("TransformerTapOperationMode", "Normal");
                jsonObject.addProperty("EquipmentRatings", "0");
                jsonObject.addProperty("ProtectiveDeviceRatings", "0");
                jsonObject.addProperty("GroundingTransformerDB_FlagLevel1", "100");
                jsonObject.addProperty("GroundingTransformerDB_FlagLevel2", "100");
                jsonObject.addProperty("GroundingTransformerDB_FlagLevel3", "100");
                jsonObject.addProperty("GroundingTransformerDB_FlagLevel4", "100");
                jsonObject.addProperty("GroundingTransformerDB_FlagLevel5", "100");
                jsonObject.addProperty("SubstationDB_FlagLevel1", "100");
                jsonObject.addProperty("SubstationDB_FlagLevel2", "100");
                jsonObject.addProperty("SubstationDB_FlagLevel3", "100");
                jsonObject.addProperty("SubstationDB_FlagLevel4", "100");
                jsonObject.addProperty("SubstationDB_FlagLevel5", "100");
                jsonObject.addProperty("MiscellaneousDB_FlagLevel1", "100");
                jsonObject.addProperty("MiscellaneousDB_FlagLevel2", "100");
                jsonObject.addProperty("MiscellaneousDB_FlagLevel3", "100");
                jsonObject.addProperty("MiscellaneousDB_FlagLevel4", "100");
                jsonObject.addProperty("MiscellaneousDB_FlagLevel5", "100");
                jsonObject.addProperty("MicroTurbineDB_FlagLevel1", "100");
                jsonObject.addProperty("MicroTurbineDB_FlagLevel2", "100");
                jsonObject.addProperty("MicroTurbineDB_FlagLevel3", "100");
                jsonObject.addProperty("MicroTurbineDB_FlagLevel4", "100");
                jsonObject.addProperty("MicroTurbineDB_FlagLevel5", "100");
                jsonObject.addProperty("TransformerDB_FlagLevel1", "100");
                jsonObject.addProperty("TransformerDB_FlagLevel2", "100");
                jsonObject.addProperty("TransformerDB_FlagLevel3", "100");
                jsonObject.addProperty("TransformerDB_FlagLevel4", "100");
                jsonObject.addProperty("TransformerDB_FlagLevel5", "100");
                jsonObject.addProperty("PhotovoltaicDB_FlagLevel1", "100");
                jsonObject.addProperty("PhotovoltaicDB_FlagLevel2", "100");
                jsonObject.addProperty("PhotovoltaicDB_FlagLevel3", "100");
                jsonObject.addProperty("PhotovoltaicDB_FlagLevel4", "100");
                jsonObject.addProperty("PhotovoltaicDB_FlagLevel5", "100");
                jsonObject.addProperty("RegulatorDB_FlagLevel1", "100");
                jsonObject.addProperty("RegulatorDB_FlagLevel2", "100");
                jsonObject.addProperty("RegulatorDB_FlagLevel3", "100");
                jsonObject.addProperty("RegulatorDB_FlagLevel4", "100");
                jsonObject.addProperty("RegulatorDB_FlagLevel5", "100");
                jsonObject.addProperty("SofcDB_FlagLevel1", "100");
                jsonObject.addProperty("SofcDB_FlagLevel2", "100");
                jsonObject.addProperty("SofcDB_FlagLevel3", "100");
                jsonObject.addProperty("SofcDB_FlagLevel4", "100");
                jsonObject.addProperty("SofcDB_FlagLevel5", "100");
                jsonObject.addProperty("SwitchDB_FlagLevel1", "100");
                jsonObject.addProperty("SwitchDB_FlagLevel2", "100");
                jsonObject.addProperty("SwitchDB_FlagLevel3", "100");
                jsonObject.addProperty("SwitchDB_FlagLevel4", "100");
                jsonObject.addProperty("SwitchDB_FlagLevel5", "100");
                jsonObject.addProperty("AutoTransformerDB_FlagLevel1", "100");
                jsonObject.addProperty("AutoTransformerDB_FlagLevel2", "100");
                jsonObject.addProperty("AutoTransformerDB_FlagLevel3", "100");
                jsonObject.addProperty("AutoTransformerDB_FlagLevel4", "100");
                jsonObject.addProperty("AutoTransformerDB_FlagLevel5", "100");
                jsonObject.addProperty("SectionalizerDB_FlagLevel1", "100");
                jsonObject.addProperty("SectionalizerDB_FlagLevel2", "100");
                jsonObject.addProperty("SectionalizerDB_FlagLevel3", "100");
                jsonObject.addProperty("SectionalizerDB_FlagLevel4", "100");
                jsonObject.addProperty("SectionalizerDB_FlagLevel5", "100");
                jsonObject.addProperty("FuseDB_FlagLevel1", "100");
                jsonObject.addProperty("FuseDB_FlagLevel2", "100");
                jsonObject.addProperty("FuseDB_FlagLevel3", "100");
                jsonObject.addProperty("FuseDB_FlagLevel4", "100");
                jsonObject.addProperty("FuseDB_FlagLevel5", "100");
                jsonObject.addProperty("RecloserDB_FlagLevel1", "100");
                jsonObject.addProperty("RecloserDB_FlagLevel2", "100");
                jsonObject.addProperty("RecloserDB_FlagLevel3", "100");
                jsonObject.addProperty("RecloserDB_FlagLevel4", "100");
                jsonObject.addProperty("RecloserDB_FlagLevel5", "100");
                jsonObject.addProperty("BreakerDB_FlagLevel1", "100");
                jsonObject.addProperty("BreakerDB_FlagLevel2", "100");
                jsonObject.addProperty("BreakerDB_FlagLevel3", "100");
                jsonObject.addProperty("BreakerDB_FlagLevel4", "100");
                jsonObject.addProperty("BreakerDB_FlagLevel5", "100");
                jsonObject.addProperty("NetworkProtectorDB_FlagLevel1", "100");
                jsonObject.addProperty("NetworkProtectorDB_FlagLevel2", "100");
                jsonObject.addProperty("NetworkProtectorDB_FlagLevel3", "100");
                jsonObject.addProperty("NetworkProtectorDB_FlagLevel4", "100");
                jsonObject.addProperty("NetworkProtectorDB_FlagLevel5", "100");
                jsonObject.addProperty("LVCBDB_FlagLevel1", "100");
                jsonObject.addProperty("LVCBDB_FlagLevel2", "100");
                jsonObject.addProperty("LVCBDB_FlagLevel3", "100");
                jsonObject.addProperty("LVCBDB_FlagLevel4", "100");
                jsonObject.addProperty("LVCBDB_FlagLevel5", "100");
                jsonObject.addProperty("SynchronousGeneratorDB_FlagLevel1", "100");
                jsonObject.addProperty("SynchronousGeneratorDB_FlagLevel2", "100");
                jsonObject.addProperty("SynchronousGeneratorDB_FlagLevel3", "100");
                jsonObject.addProperty("SynchronousGeneratorDB_FlagLevel4", "100");
                jsonObject.addProperty("SynchronousGeneratorDB_FlagLevel5", "100");
                jsonObject.addProperty("BuswayDB_FlagLevel1", "100");
                jsonObject.addProperty("BuswayDB_FlagLevel2", "100");
                jsonObject.addProperty("BuswayDB_FlagLevel3", "100");
                jsonObject.addProperty("BuswayDB_FlagLevel4", "100");
                jsonObject.addProperty("BuswayDB_FlagLevel5", "100");
                jsonObject.addProperty("InductionGeneratorDB_FlagLevel1", "100");
                jsonObject.addProperty("InductionGeneratorDB_FlagLevel2", "100");
                jsonObject.addProperty("InductionGeneratorDB_FlagLevel3", "100");
                jsonObject.addProperty("InductionGeneratorDB_FlagLevel4", "100");
                jsonObject.addProperty("InductionGeneratorDB_FlagLevel5", "100");
                jsonObject.addProperty("SeriesCapacitorDB_FlagLevel1", "100");
                jsonObject.addProperty("SeriesCapacitorDB_FlagLevel2", "100");
                jsonObject.addProperty("SeriesCapacitorDB_FlagLevel3", "100");
                jsonObject.addProperty("SeriesCapacitorDB_FlagLevel4", "100");
                jsonObject.addProperty("SeriesCapacitorDB_FlagLevel5", "100");
                jsonObject.addProperty("BESSDB_FlagLevel1", "100");
                jsonObject.addProperty("BESSDB_FlagLevel2", "100");
                jsonObject.addProperty("BESSDB_FlagLevel3", "100");
                jsonObject.addProperty("BESSDB_FlagLevel4", "100");
                jsonObject.addProperty("BESSDB_FlagLevel5", "100");
                jsonObject.addProperty("PhaseShifterTransformerDB_FlagLevel1", "100");
                jsonObject.addProperty("PhaseShifterTransformerDB_FlagLevel2", "100");
                jsonObject.addProperty("PhaseShifterTransformerDB_FlagLevel3", "100");
                jsonObject.addProperty("PhaseShifterTransformerDB_FlagLevel4", "100");
                jsonObject.addProperty("PhaseShifterTransformerDB_FlagLevel5", "100");
                jsonObject.addProperty("ElectronicConverterGeneratorDB_FlagLevel1", "100");
                jsonObject.addProperty("ElectronicConverterGeneratorDB_FlagLevel2", "100");
                jsonObject.addProperty("ElectronicConverterGeneratorDB_FlagLevel3", "100");
                jsonObject.addProperty("ElectronicConverterGeneratorDB_FlagLevel4", "100");
                jsonObject.addProperty("ElectronicConverterGeneratorDB_FlagLevel5", "100");
                jsonObject.addProperty("InductionMotorDB_FlagLevel1", "100");
                jsonObject.addProperty("InductionMotorDB_FlagLevel2", "100");
                jsonObject.addProperty("InductionMotorDB_FlagLevel3", "100");
                jsonObject.addProperty("InductionMotorDB_FlagLevel4", "100");
                jsonObject.addProperty("InductionMotorDB_FlagLevel5", "100");
                jsonObject.addProperty("ShuntCapacitorDB_FlagLevel1", "100");
                jsonObject.addProperty("ShuntCapacitorDB_FlagLevel2", "100");
                jsonObject.addProperty("ShuntCapacitorDB_FlagLevel3", "100");
                jsonObject.addProperty("ShuntCapacitorDB_FlagLevel4", "100");
                jsonObject.addProperty("ShuntCapacitorDB_FlagLevel5", "100");
                jsonObject.addProperty("SynchronousMotorDB_FlagLevel1", "100");
                jsonObject.addProperty("SynchronousMotorDB_FlagLevel2", "100");
                jsonObject.addProperty("SynchronousMotorDB_FlagLevel3", "100");
                jsonObject.addProperty("SynchronousMotorDB_FlagLevel4", "100");
                jsonObject.addProperty("SynchronousMotorDB_FlagLevel5", "100");
                jsonObject.addProperty("ConductorDB_FlagLevel1", "100");
                jsonObject.addProperty("ConductorDB_FlagLevel2", "100");
                jsonObject.addProperty("ConductorDB_FlagLevel3", "100");
                jsonObject.addProperty("ConductorDB_FlagLevel4", "100");
                jsonObject.addProperty("ConductorDB_FlagLevel5", "100");
                jsonObject.addProperty("CableDB_FlagLevel1", "100");
                jsonObject.addProperty("CableDB_FlagLevel2", "100");
                jsonObject.addProperty("CableDB_FlagLevel3", "100");
                jsonObject.addProperty("CableDB_FlagLevel4", "100");
                jsonObject.addProperty("CableDB_FlagLevel5", "100");
                jsonObject.addProperty("WecsDB_FlagLevel1", "100");
                jsonObject.addProperty("WecsDB_FlagLevel2", "100");
                jsonObject.addProperty("WecsDB_FlagLevel3", "100");
                jsonObject.addProperty("WecsDB_FlagLevel4", "100");
                jsonObject.addProperty("WecsDB_FlagLevel5", "100");
                if (mListener != null) {
                    mListener.onJsonObjectReceived(jsonObject, dashBoardJsonObject, list);
                }
            } else {
                Toast.makeText(getActivity(), "Please Select Network", Toast.LENGTH_SHORT).show();
            }
        }

    }
    public boolean hasValidData() {
        if (binding == null || binding.transformerTabOperation == null || binding.regularTabOperation == null) {
            return false;
        }
        String transformerSelection = binding.transformerTabOperation.getSelectedItem().toString();
        String regulatorSelection = binding.regularTabOperation.getSelectedItem().toString();
        return !transformerSelection.isEmpty() && !regulatorSelection.isEmpty();
    }

}