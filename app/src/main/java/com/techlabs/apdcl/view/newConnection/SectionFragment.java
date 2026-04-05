package com.techlabs.apdcl.view.newConnection;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
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
import androidx.lifecycle.Observer;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.Args;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.Utils.SectionCallBack;
import com.techlabs.apdcl.databinding.FragmentSection2Binding;
import com.techlabs.apdcl.models.EquipmentModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SectionFragment extends Fragment {

    private FragmentSection2Binding binding;
    private final String[] typeList = {"Cable", "Overhead", "UnbalanceOverhead"};
    private final String[] statusList = {"Connected", "Disconnected"};
    private String[] cableIdList;
    private PrefManager prefManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSection2Binding.inflate(inflater, container, false);
        prefManager = new PrefManager(getActivity());
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<String> typeAdapters = new ArrayAdapter<>(requireActivity(), R.layout.custom_spinner, typeList);
        binding.typeSpinnerBar.setAdapter(typeAdapters);

        ArrayAdapter<String> statusAdapters = new ArrayAdapter<>(requireActivity(), R.layout.custom_spinner, statusList);
        binding.statusSpinnersBar.setAdapter(statusAdapters);
        Bundle bundle = this.getArguments();
        assert bundle != null;

        /*if (bundle.getString("applicationId") != null) {
            binding.deviceNumbersEdt.setText("CA_" + bundle.getString("applicationId"));
        }*/

        binding.typeSpinnerBar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = binding.typeSpinnerBar.getSelectedItem().toString();
                Args.setNSCSelectedType(type);
                if (bundle.getString("applicationId") != null) {
                    switch (type) {
                        case "Cable":
                            binding.deviceNumbersEdt.setText("CA_" + bundle.getString("applicationId"));
                            break;
                        case "Overhead":
                            binding.deviceNumbersEdt.setText("OH_" + bundle.getString("applicationId"));
                            break;
                        case "UnbalanceOverhead":
                            binding.deviceNumbersEdt.setText("UO_" + bundle.getString("applicationId"));
                            break;
                    }
                }
                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(requireActivity())) {
                    getEquipment(binding.typeSpinnerBar.getSelectedItem().toString());
                } else {
                    Snackbar.make(binding.getRoot(), "No Internet Connection", Snackbar.LENGTH_LONG)
                            .setAction("Retry", v -> getEquipment(binding.typeSpinnerBar.getSelectedItem().toString())).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Args.getIsNSCSectionValidate().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                checkData();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        assert getView() != null;
        getView().requestLayout();
    }

    @SuppressLint("SetTextI18n")
    private void getEquipment(String eqType) {
        if (eqType.equals("Cable")){
            binding.idTv.setText("Cable Id");
        }else {
            binding.idTv.setText("Line Id");
        }
        Bundle bundle = this.getArguments();
        assert bundle != null;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("NetworkId", bundle.getString("Network"));
        jsonObject.addProperty("Type", "Voltage");
        jsonObject.addProperty("Subtype", eqType);
        jsonObject.addProperty("UserType", prefManager.getUserType());
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        jsonObject.addProperty("Voltage", bundle.getString("Voltage"));
        String AccessToken = prefManager.getAccessToken();
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<EquipmentModel> call = apiInterface.getEquipmentData("Bearer " + AccessToken, jsonObject);
        call.enqueue(new Callback<EquipmentModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<EquipmentModel> call, @NonNull Response<EquipmentModel> response) {
                if (response.code() == 200) {
                    try {
                        EquipmentModel equipmentModel = response.body();
                        if (equipmentModel != null && equipmentModel.getEquipmentId() != null && !equipmentModel.getEquipmentId().isEmpty()) {
                            cableIdList = equipmentModel.getEquipmentId().toArray(new String[0]);
                            ArrayAdapter<String> adapters = new ArrayAdapter<>(requireContext(), R.layout.custom_spinner, cableIdList);
                            binding.cableIdSpinnerBar.setAdapter(adapters);
                        } else {
                            cableIdList = new String[]{"DEFAULT"};
                            ArrayAdapter<String> adapters = new ArrayAdapter<>(requireContext(), R.layout.custom_spinner, cableIdList);
                            binding.cableIdSpinnerBar.setAdapter(adapters);
                        }
                    } catch (Exception e) {
                        Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
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
                    Ok.setOnClickListener(v -> {
                        getEquipment(eqType);
                    });
                    Toast toast = new Toast(requireContext());
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EquipmentModel> call, @NonNull Throwable t) {
                if (t.getMessage() != null && t.getMessage().contains("BEGIN_ARRAY")) {
                    Log.e("PARSE_ISSUE", "Model mismatch: Array aa raha hai");
                    cableIdList = new String[]{"DEFAULT"};
                    ArrayAdapter<String> adapters = new ArrayAdapter<>(requireContext(), R.layout.custom_spinner, cableIdList);
                    binding.cableIdSpinnerBar.setAdapter(adapters);
                    return;
                }

                @SuppressLint("InflateParams")
                View layout = LayoutInflater.from(requireContext()).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView description = layout.findViewById(R.id.descripTv);
                header.setText(requireContext().getString(R.string.error));
                description.setText(requireContext().getString(R.string.error_msg));
                Ok.setOnClickListener(v -> {
                    getEquipment(eqType);
                });
                Toast toast = new Toast(requireContext());
                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
    }

    private void checkData() {
        binding.deviceNumbersEdt.setError(null);
        boolean isCancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(binding.deviceNumbersEdt.getText().toString().trim())) {
            binding.deviceNumbersEdt.setError(Html.fromHtml("<font color='red'>Please Enter Device Number</font>"));
            focusView = binding.deviceNumbersEdt;
            isCancel = true;
        }

        if (isCancel) {
            focusView.requestFocus();
        } else {
            if (binding.typeSpinnerBar.getSelectedItem() != null && binding.deviceNumbersEdt.getText() != null && binding.cableIdSpinnerBar.getSelectedItem() != null) {
                Bundle bundle = new Bundle();
                if (binding.typeSpinnerBar.getSelectedItem().toString().equals("Cable")) {
                    bundle.putString("DeviceType", "1");
                } else if (binding.typeSpinnerBar.getSelectedItem().toString().equals("Overhead")) {
                    bundle.putString("DeviceType", "2");
                } else {
                    bundle.putString("DeviceType", "23");
                }

                bundle.putString("DeviceNumber", binding.deviceNumbersEdt.getText().toString().trim());

                if (binding.statusSpinnersBar.getSelectedItem().equals("Connected")){
                    bundle.putString("Status", "0");
                }else {
                    bundle.putString("Status", "1");
                }

                bundle.putString("CableID", binding.cableIdSpinnerBar.getSelectedItem().toString());
                Args.setNSCSectionParameter(bundle);
            }
        }
    }
}