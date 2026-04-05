package com.techlabs.apdcl.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.Config;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ShortCircuitArgument;
import com.techlabs.apdcl.databinding.FragmentShortCircuitCalculationBinding;
import com.techlabs.apdcl.models.analysis.ShortCircuitAnalysisModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShortCircuitCalculation extends Fragment {

    private FragmentShortCircuitCalculationBinding binding;
    private final String[] calculation = {"Short Circuit Level At Buses and Nodes", "Fault Flow Current and Voltage"};
    private final String[] faultLocation = {"Node", "Cable", "Line"};
    private final String[] faultType = {"LLL", "LLLG", "LL", "LLG", "Lg", "All Types"};
    private final String[] faultPhase = {"ABC"};
    private PrefManager prefManager;
    private String[] locationSection;
    private ShortCircuitArgument shortCircuitArgument;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            shortCircuitArgument = (ShortCircuitArgument) getActivity();
            binding = FragmentShortCircuitCalculationBinding.inflate(inflater, container, false);
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

        Bundle bundle = this.getArguments();
        assert bundle != null;
        if (bundle.getString("Index") != null) {
            binding.calculationSpinner.setSelection(Integer.parseInt(bundle.getString("Index")));
        }

        ArrayAdapter<String> faultLocationAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner, faultLocation);
        binding.locationSpinner.setAdapter(faultLocationAdapter);

        ArrayAdapter<String> faultTypeAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner, faultType);
        binding.faultTypeSpinner.setAdapter(faultTypeAdapter);

        ArrayAdapter<String> faultPhaseAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner, faultPhase);
        binding.phaseSpinner.setAdapter(faultPhaseAdapter);

        binding.calculationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItems = binding.calculationSpinner.getSelectedItem().toString();
                String reportValue = "";

                if (selectedItems.contains("Short Circuit Level At Buses and Nodes")) {
                    setSpinnersEnabled(false);
                    reportValue = "Short-Circuit - Detailed";
                } else if (selectedItems.contains("Fault Flow Current and Voltage")) {
                    setSpinnersEnabled(true);
                    reportValue = "Short-Circuit - Fault Flow Detailed";
                }

                PrefManager pref = new PrefManager(getActivity());
                pref.setReportSelection(reportValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getCalculationData(binding.locationSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

/*        binding.okBtn.setOnClickListener(v -> {
            if (!binding.nbCablePhaseEdt.getText().toString().isEmpty()) {
                SendShortCircuitArguments();
            } else {
                Snackbar snack = Snackbar.make(getActivity().findViewById(android.R.id.content), "The field can't be empty! please correct...", Snackbar.LENGTH_LONG);
                snack.show();
            }
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
            if (prefManager.getUserName() != null) {
                jsonObject.addProperty("Username", prefManager.getUserName());
            }

            if (prefManager.getDBName() != null) {
                jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            }

            if (bundle.get("Network") != null) {
                List<String> list = new ArrayList<String>((ArrayList<String>) bundle.get("Network"));
                JsonArray jsonArray = new Gson().toJsonTree(list).getAsJsonArray();
                jsonObject.add("NetworkId", jsonArray);
            }

            if (binding.calculationSpinner.getSelectedItem().toString().contains("Fault Flow Current and Voltage")) {
                jsonObject.addProperty("Calculate", "FF");
            } else {
                jsonObject.addProperty("Calculate", "SC");
            }

            if (binding.locationSpinner.getSelectedItem().toString().contains("Node")) {
                jsonObject.addProperty("LocationType", "53");
            } else if (binding.locationSpinner.getSelectedItem().toString().contains("Cable")) {
                jsonObject.addProperty("LocationType", "10");
            } else {
                jsonObject.addProperty("LocationType", "11");
            }

            if (binding.typeSpinner.getSelectedItem() != null && !binding.typeSpinner.getSelectedItem().toString().trim().isEmpty()) {
                jsonObject.addProperty("DeviceNumber", binding.typeSpinner.getSelectedItem().toString());
            }

            if (!binding.nbCablePhaseEdt.getText().toString().isEmpty()) {
                jsonObject.addProperty("FaultPosition", binding.nbCablePhaseEdt.getText().toString().trim());
            }

            if (binding.faultTypeSpinner.getSelectedItem() != null && !binding.faultTypeSpinner.getSelectedItem().toString().trim().isEmpty()) {
                jsonObject.addProperty("FaultType", binding.faultTypeSpinner.getSelectedItem().toString());
            }

            if (binding.phaseSpinner.getSelectedItem() != null && !binding.phaseSpinner.getSelectedItem().toString().trim().isEmpty()) {
                jsonObject.addProperty("FaultPhase", binding.phaseSpinner.getSelectedItem().toString().trim());
            }

            jsonObject.addProperty("Method", "1");
            jsonObject.addProperty("NominalTap", "0");
            jsonObject.addProperty("AImpedence", "1");
            jsonObject.addProperty("SyncGenerator", "1");
            jsonObject.addProperty("WECS", "1");
            jsonObject.addProperty("Photovoltaic", "1");
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

    private void setSpinnersEnabled(boolean enabled) {
        binding.locationSpinner.setEnabled(enabled);
        binding.locationSpinner.setAlpha(enabled ? 1.0f : 0.5f);

        binding.phaseSpinner.setEnabled(enabled);
        binding.phaseSpinner.setAlpha(enabled ? 1.0f : 0.5f);

        binding.typeSpinner.setEnabled(enabled);
        binding.typeSpinner.setAlpha(enabled ? 1.0f : 0.5f);

        binding.faultTypeSpinner.setEnabled(enabled);
        binding.faultTypeSpinner.setAlpha(enabled ? 1.0f : 0.5f);

        binding.nbCablePhaseEdt.setEnabled(enabled);
        binding.nbCablePhaseEdt.setFocusable(enabled);
        binding.nbCablePhaseEdt.setFocusableInTouchMode(enabled);
        binding.nbCablePhaseEdt.setClickable(enabled);
    }

    /*
        private void getCalculationData(String string) {
            Bundle bundle = this.getArguments();
            if (string != null) {
                assert bundle != null;
                if (bundle.get("Network") != null) {
                    JsonObject jsonObject = new JsonObject();
                    JsonArray jsonArray = new Gson().toJsonTree(bundle.get("Network")).getAsJsonArray();
                    jsonObject.add("NetworkList", jsonArray);
                    jsonObject.addProperty("Subtype", string);
                    jsonObject.addProperty("Type", "ID");
                    jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
                    String AccessToken = prefManager.getAccessToken();
                    ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
                    Call<ShortCircuitAnalysisModel> call = apiInterface.getShortCircuitCalData("Bearer " + AccessToken, jsonObject);
                    call.enqueue(new Callback<ShortCircuitAnalysisModel>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(@NonNull Call<ShortCircuitAnalysisModel> call, @NonNull Response<ShortCircuitAnalysisModel> response) {
                            if (response.code() == 200) {
                                try {
                                    ShortCircuitAnalysisModel model = response.body();
                                    assert model != null;
                                    if (!model.getOutput().getDeviceNumber().isEmpty()) {
                                        if (bundle.getString("NodeId") != null) {
                                            model.getOutput().getDeviceNumber().add(0, bundle.getString("NodeId"));
                                            locationSection = model.getOutput().getDeviceNumber().toArray(new String[0]);
                                            ArrayAdapter<String> adapters = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner, locationSection);
                                            binding.typeSpinner.setAdapter(adapters);
                                        } else {
                                            locationSection = model.getOutput().getDeviceNumber().toArray(new String[0]);
                                            ArrayAdapter<String> adapters = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner, locationSection);
                                            binding.typeSpinner.setAdapter(adapters);
                                        }
                                        Config.scDeviceNumber = binding.typeSpinner.getSelectedItem().toString().trim();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                @SuppressLint("InflateParams")
                                View layout = LayoutInflater.from(getActivity()).inflate(R.layout.toast_layout, null);
                                TextView Ok = layout.findViewById(R.id.okBtn);
                                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                                TextView header = layout.findViewById(R.id.headerTv);
                                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                                TextView description = layout.findViewById(R.id.descripTv);
                                header.setText(response.message() + " - " + response.code());
                                description.setText(getString(R.string.error_msg));
                                Ok.setOnClickListener(v -> {
                                    getCalculationData(string);
                                });
                                Toast toast = new Toast(getActivity());
                                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ShortCircuitAnalysisModel> call, @NonNull Throwable t) {
                            @SuppressLint("InflateParams")
                            View layout = LayoutInflater.from(getActivity()).inflate(R.layout.toast_layout, null);
                            TextView Ok = layout.findViewById(R.id.okBtn);
                            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                            TextView header = layout.findViewById(R.id.headerTv);
                            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                            TextView description = layout.findViewById(R.id.descripTv);
                            header.setText(getActivity().getString(R.string.error));
                            description.setText(getString(R.string.error_msg));
                            Ok.setOnClickListener(v -> {
                                getCalculationData(string);
                            });
                            Toast toast = new Toast(getActivity());
                            toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                        }
                    });
                }
            }
        }
    */
    private void getCalculationData(String string) {
        Bundle bundle = this.getArguments();
        if (string != null) {
            assert bundle != null;
            if (bundle.get("Network") != null) {
                JsonObject jsonObject = new JsonObject();
                JsonArray jsonArray = new Gson().toJsonTree(bundle.get("Network")).getAsJsonArray();
                jsonObject.add("NetworkId", jsonArray);
                jsonObject.addProperty("Type", "ID");
                jsonObject.addProperty("Subtype", string);
                jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
                String AccessToken = prefManager.getAccessToken();
                ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
                Call<ShortCircuitAnalysisModel> call = apiInterface.getShortCircuitCalData("Bearer " + AccessToken, jsonObject);
                call.enqueue(new Callback<ShortCircuitAnalysisModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<ShortCircuitAnalysisModel> call, @NonNull Response<ShortCircuitAnalysisModel> response) {
                        if (response.code() == 200) {
                            try {
                                ShortCircuitAnalysisModel model = response.body();
                                assert model != null;
                                List<String> deviceNumbers = model.getId().getId();
                                if (!deviceNumbers.isEmpty()) {
                                    if (bundle.getString("NodeId") != null) {
                                        deviceNumbers.add(0, bundle.getString("NodeId"));
                                        locationSection = deviceNumbers.toArray(new String[0]);
                                        ArrayAdapter<String> adapters = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner, locationSection);
                                        binding.typeSpinner.setAdapter(adapters);
                                    } else {
                                        locationSection = deviceNumbers.toArray(new String[0]);
                                        ArrayAdapter<String> adapters = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner, locationSection);
                                        binding.typeSpinner.setAdapter(adapters);
                                    }
                                    Config.scDeviceNumber = binding.typeSpinner.getSelectedItem().toString().trim();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            @SuppressLint("InflateParams")
                            View layout = LayoutInflater.from(getActivity()).inflate(R.layout.toast_layout, null);
                            TextView Ok = layout.findViewById(R.id.okBtn);
                            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                            TextView header = layout.findViewById(R.id.headerTv);
                            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                            TextView description = layout.findViewById(R.id.descripTv);
                            header.setText(response.message() + " - " + response.code());
                            description.setText(getString(R.string.error_msg));
                            Ok.setOnClickListener(v -> {
                                getCalculationData(string);
                            });
                            Toast toast = new Toast(getActivity());
                            toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ShortCircuitAnalysisModel> call, @NonNull Throwable t) {
                        @SuppressLint("InflateParams")
                        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.toast_layout, null);
                        TextView Ok = layout.findViewById(R.id.okBtn);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                        TextView header = layout.findViewById(R.id.headerTv);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                        TextView description = layout.findViewById(R.id.descripTv);
                        header.setText(getActivity().getString(R.string.error));
                        description.setText(getString(R.string.error_msg));
                        Ok.setOnClickListener(v -> {
                            getCalculationData(string);
                        });
                        Toast toast = new Toast(getActivity());
                        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                });
            }
        }
    }

    public boolean isValid() {
        return binding != null && !binding.nbCablePhaseEdt.getText().toString().isEmpty();
    }

}