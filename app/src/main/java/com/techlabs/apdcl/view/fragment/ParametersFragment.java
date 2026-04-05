package com.techlabs.apdcl.view.fragment;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.LoadFlowArgument;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.databinding.FragmentParametersBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ParametersFragment extends Fragment {

    private FragmentParametersBinding binding;
    private HashMap<String, String> CalculationMethodMap = new HashMap<String, String>();
    private LoadFlowArgument mListener;
    private TimePickerDialog mTimePicker;
    private String hours = null;
    private String minutes = null;
    private PrefManager prefManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            mListener = (LoadFlowArgument) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity() + " must implement BottomSheetListener");
        }
        binding = FragmentParametersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefManager = new PrefManager(getActivity());

        CalculationMethodMap.put("Voltage Drop -Unbalanced", "VoltageDropUnbalanced");
        CalculationMethodMap.put("Voltage Drop -Balanced", "VoltageDropBalanced");
        CalculationMethodMap.put("Fast Decoupled -Balanced", "FastDecoupled");
        CalculationMethodMap.put("Gauss Seidel -Balanced", "GaussSeidel");
        CalculationMethodMap.put("Newton Raphson -Balanced", "NewtonRaphson");
        CalculationMethodMap.put("Newton Raphson -Unbalanced", "NewtonRaphsonUnbalanced");

        ArrayList<String> spinnerData = new ArrayList<>(CalculationMethodMap.keySet());

        ArrayAdapter<String> calculationAdapters = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner, spinnerData);
        binding.calculationMethod.setAdapter(calculationAdapters);
        binding.calculationMethod.setSelection(5);

        binding.timeEdt.setOnClickListener(view1 -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    hours = String.valueOf(selectedHour);
                    minutes = String.valueOf(selectedMinute);
                    binding.timeEdt.setText(selectedHour + ":" + selectedMinute);
                }
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

        /*binding.okBtn.setOnClickListener(view1 -> {
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
        assert bundle != null;
        if (bundle.get("Network") != null) {
            List<String> list = new ArrayList<String>((ArrayList<String>) bundle.get("Network"));
            JsonArray jsonArray = new Gson().toJsonTree(list).getAsJsonArray();
            jsonObject.add("NetworkId", jsonArray);
            dashBoardJsonObject.add("NetworkId", jsonArray);
            dashBoardJsonObject.addProperty("UserType", prefManager.getUserType());
            jsonObject.addProperty("Username", prefManager.getUserName());
            jsonObject.addProperty("UserType", prefManager.getUserType());
            jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            if (CalculationMethodMap.get(binding.calculationMethod.getSelectedItem()) != null) {
                jsonObject.addProperty("method", CalculationMethodMap.get(binding.calculationMethod.getSelectedItem()));
            } else {
                jsonObject.addProperty("method", "VoltageDropUnbalanced");
            }

            if (binding.toleRanceEdt.getText().toString() != null && !binding.toleRanceEdt.getText().toString().isEmpty()) {
                jsonObject.addProperty("Tolerance", binding.toleRanceEdt.getText().toString());
            } else {
                jsonObject.addProperty("Tolerance", "0.01");
            }

            jsonObject.addProperty("Iterations", "60");
            jsonObject.addProperty("AmbientTemperature", "77");

            if (hours != null) {
                jsonObject.addProperty("hour", hours);
            } else {
                jsonObject.addProperty("hour", "12");
            }

            if (minutes != null) {
                jsonObject.addProperty("minute", minutes);
            } else {
                jsonObject.addProperty("minute", "15");
            }

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
            Snackbar snack = Snackbar.make(getActivity().findViewById(android.R.id.content), "Please Select Network!", Snackbar.LENGTH_LONG);
            snack.show();
        }
    }
        public boolean hasValidData() {
        if (binding == null) return false;

        if (binding.calculationMethod.getSelectedItem() == null ||
                binding.calculationMethod.getSelectedItem().toString().trim().isEmpty()) {
            return false;
        }

        if (binding.toleRanceEdt.getText() == null ||
                binding.toleRanceEdt.getText().toString().trim().isEmpty()) {
            return false;
        }

        if (binding.timeEdt.getText() == null ||
                binding.timeEdt.getText().toString().trim().isEmpty()) {
            return false;
        }

        if (binding.itrationsEdt.getText() == null ||
                binding.itrationsEdt.getText().toString().trim().isEmpty()) {
            return false;
        }

        return true;
    }

}