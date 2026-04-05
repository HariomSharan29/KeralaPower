package com.techlabs.apdcl.view.LayerInfo.LineInfo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.databinding.CableInfoDialogLayoutBinding;
import com.techlabs.apdcl.models.EquipmentModel;
import com.techlabs.apdcl.models.Line.Cable;
import com.techlabs.apdcl.models.del.UpdateDeviceModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CableMoreInfoDialog extends Dialog {
    private Context mainContext;
    private JsonObject jsonObject;
    private CableInfoDialogLayoutBinding binding;
    private PrefManager prefManager;
    private String[] statusList = {"Connected", "Disconnected"};
    private String[] typeList = {"Cable", "Overhead", "UnbalanceOverhead"};
    private String phase;
    private String networkId;
    private String voltage;
    private String[] cableIdList;
    private String currentLineId;
    private CableSnippet cableSnippet;

    public CableMoreInfoDialog(@NonNull Context context, String networkId, String voltage, JsonObject jsonObject, CableSnippet cableSnippet) {
        super(context);
        this.mainContext = context;
        this.jsonObject = jsonObject;
        this.networkId = networkId;
        this.voltage = voltage;
        this.cableSnippet = cableSnippet;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CableInfoDialogLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View MainLayoutBackGround = Objects.requireNonNull(getWindow()).getDecorView().getRootView();
        MainLayoutBackGround.setBackground(getContext().getDrawable(R.drawable.pop_background));

        prefManager = new PrefManager(mainContext);

        binding.btnLayout.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
        binding.cableInfoLayout.setVisibility(View.VISIBLE);
        binding.overheadInfoLayout.setVisibility(View.GONE);
        binding.headerTitle.setText("Cable");

        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
            getCableInfo();
            if (prefManager.getUserType().contains("Edit")) {
                getEquipment();
            }
        } else {
            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.no_internet_dialog);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getContext().getDrawable(R.drawable.pop_background));
            LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
            Button RetryBtn = dialog.findViewById(R.id.btnDialog);
            lottieAnimationView.playAnimation();
            RetryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getCableInfo();
                    if (prefManager.getUserType().contains("Edit")) {
                        getEquipment();
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        binding.cableBtn.setOnClickListener(view -> {
            binding.nodeInfoLayout.setVisibility(View.GONE);
            binding.cableInfoLayout.setVisibility(View.VISIBLE);
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.black));
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
            binding.headerTitle.setText("Cable");
        });

        binding.nodeBtn.setOnClickListener(view -> {
            binding.cableInfoLayout.setVisibility(View.GONE);
            binding.nodeInfoLayout.setVisibility(View.VISIBLE);
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.white));
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.black));
            binding.headerTitle.setText("Node");
        });

        if (prefManager.getUserType().contains("Edit")) {
            binding.editBtnLayout.setVisibility(View.VISIBLE);
            binding.aChkBox.setClickable(true);
            binding.bChkBox.setClickable(true);
            binding.cChkBox.setClickable(true);
        } else {
            binding.editBtnLayout.setVisibility(View.GONE);
            binding.aChkBox.setClickable(false);
            binding.bChkBox.setClickable(false);
            binding.cChkBox.setClickable(false);
        }

        binding.okbtns.setOnClickListener(v -> {
            checkDetails();
        });

        binding.canclebtns.setOnClickListener(v -> dismiss());

        binding.statusSpinnersBar.setThreshold(0);
        binding.statusSpinnersBar.setOnClickListener(v -> binding.statusSpinnersBar.showDropDown());
        binding.cableIdSpinnerBar.setThreshold(0);
        binding.cableIdSpinnerBar.setOnClickListener(v -> binding.cableIdSpinnerBar.showDropDown());
    }

    private void getCableInfo() {
        binding.cableInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
        jsonObject.addProperty("UserType", prefManager.getUserType());
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<Cable> call = apiInterface.getCableData("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<Cable>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Cable> call, @NonNull Response<Cable> response) {
                binding.cableInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                if (response.code() == 200) {
                    try {
                        Cable cable = response.body();
                        assert cable != null;
                        if (cable.getOutput() != null) {
                            if (cable.getOutput().getSectionId() != null && !cable.getOutput().getSectionId().isEmpty()) {
                                binding.sectionIdEdt.setText(cable.getOutput().getSectionId());
                            } else {
                                binding.sectionIdEdt.setText("UNDEFINED");
                            }

                            if (cable.getOutput().getPhase() != null) {
                                if (cable.getOutput().getPhase() == 1) {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(false);
                                    binding.cChkBox.setChecked(false);
                                } else if (cable.getOutput().getPhase() == 2) {
                                    binding.aChkBox.setChecked(false);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(false);
                                } else if (cable.getOutput().getPhase() == 3) {
                                    binding.aChkBox.setChecked(false);
                                    binding.bChkBox.setChecked(false);
                                    binding.cChkBox.setChecked(true);
                                } else if (cable.getOutput().getPhase() == 4) {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(false);
                                } else if (cable.getOutput().getPhase() == 5) {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(false);
                                    binding.cChkBox.setChecked(true);
                                } else if (cable.getOutput().getPhase() == 6) {
                                    binding.aChkBox.setChecked(false);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(true);
                                } else {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(true);
                                }
                            }

                            if (cable.getOutput().getZoneId() != null) {
                                binding.zoneTv.setText(cable.getOutput().getZoneId().toString());
                            } else {
                                binding.zoneTv.setText("UNDEFINED");
                            }

                            ArrayAdapter<String> typeAdapters = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, typeList);
                            binding.typeSpinnertv.setAdapter(typeAdapters);

                            if (cable.getOutput().getDeviceNumber() != null && !cable.getOutput().getDeviceNumber().isEmpty() && !cable.getOutput().getDeviceNumber().equals("null")) {
                                binding.deviceNumbersEdt.setText(cable.getOutput().getDeviceNumber());
                            } else {
                                binding.deviceNumbersEdt.setText("UNDEFINED");
                            }

                            ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, statusList);
                            statusAdapter.setDropDownViewResource(R.layout.custom_spinner);
                            binding.statusSpinnersBar.setAdapter(statusAdapter);
                            if (cable.getOutput().getStatus() != null) {
                                String status = cable.getOutput().getStatus() == 0 ? "Connected" : "Disconnected";
                                binding.statusSpinnersBar.setText(status, false);
                            } else {
                                binding.statusSpinnersBar.setText("Connected", false);
                            }


                            if (cable.getOutput().getLength() != null) {
                                binding.lengthTv.setText(cable.getOutput().getLength().toString() + " " + "M");
                            }
                            if (voltage != null) {
                                binding.voltageTv.setText(voltage);
                            }

                            if (cable.getOutput().getCableId() != null && !cable.getOutput().getCableId().isEmpty() && !cable.getOutput().getCableId().equals("null")) {
                                currentLineId = cable.getOutput().getCableId();
                                cableIdList = new String[]{currentLineId};
                            } else {
                                currentLineId = "Undefined";
                                cableIdList = new String[]{"Undefined"};
                            }
                            ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, cableIdList);
                            idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                            binding.cableIdSpinnerBar.setAdapter(idAdapter);
                            binding.cableIdSpinnerBar.setText(currentLineId, false);
                            idAdapter.notifyDataSetChanged();

                            if (cable.getOutput().getNumberOfCableInParallel() != null) {
                                binding.nbCablePhaseTv.setText(cable.getOutput().getNumberOfCableInParallel().toString() + " " + "runs");
                            }

                            if (cable.getOutput().getOperatingTemperature() != null) {
                                binding.condTempTv.setText(cable.getOutput().getOperatingTemperature().toString() + " " + "°C");
                            }

                            if (cable.getOutput().getNominalRating() != null && !cable.getOutput().getNominalRating().toString().isEmpty()) {
                                binding.nominalRating.setText(cable.getOutput().getNominalRating().toString() + " " + "A");
                            }

                            if (cable.getOutput().getCableType() != null) {
                                if (cable.getOutput().getCableType() == 0) {
                                    binding.cableTypeTv.setText("1C");
                                } else if (cable.getOutput().getCableType() == 1) {
                                    binding.cableTypeTv.setText("3C");
                                } else {
                                    binding.cableTypeTv.setText("3.5C");
                                }
                            }

                            if (cable.getOutput().getMaterialID() != null) {
                                binding.conductorMaterialTv.setText(cable.getOutput().getMaterialID().toString());
                            } else {
                                binding.conductorMaterialTv.setText("Default");
                            }

                            if (cable.getOutput().getSizeMm2() != null) {
                                binding.conductorSizeTv.setText(cable.getOutput().getSizeMm2().toString() + " " + "AWG");
                            }

                            if (cable.getOutput().getInsulationType() != null && !cable.getOutput().getInsulationType().equals("null")) {
                                binding.insulationTypeTv.setText(cable.getOutput().getInsulationType());
                            }

                            if (cable.getOutput().getFROMNodeId() != null && !cable.getOutput().getFROMNodeId().equals("null")) {
                                binding.idFromNodesTv.setText(cable.getOutput().getFROMNodeId());
                            }

                            if (cable.getOutput().getFROMNodeIdX() != null) {
                                binding.xFromNodesTv.setText(cable.getOutput().getFROMNodeIdX().toString());
                            }

                            if (cable.getOutput().getFROMNodeIdY() != null) {
                                binding.yFromNodesTv.setText(cable.getOutput().getFROMNodeIdY().toString());
                            }

                            if (cable.getOutput().getTONodeId() != null && !cable.getOutput().getTONodeId().equals("null")) {
                                binding.idToNodeTv.setText(cable.getOutput().getTONodeId());
                            }

                            if (cable.getOutput().getTONodeIdX() != null) {
                                binding.xToNodeTv.setText(cable.getOutput().getTONodeIdX().toString());
                            }

                            if (cable.getOutput().getTONodeIdY() != null) {
                                binding.yToNodesTv.setText(cable.getOutput().getTONodeIdY().toString());
                            }

                            if (!cable.getOutput().getToNodeId().equals("null") || !cable.getOutput().getFromNodeId().equals("null")) {
                                binding.corTypeChk.setChecked(true);
                            } else {
                                binding.corTypeChk.setChecked(false);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    @SuppressLint("InflateParams")
                    View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView description = layout.findViewById(R.id.descripTv);
                    header.setText(response.message() + " - " + response.code());
                    description.setText(mainContext.getString(R.string.error_msg));
                    Toast toast = new Toast(mainContext);
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cable> call, @NonNull Throwable t) {
                binding.cableInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                @SuppressLint("InflateParams")
                View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView description = layout.findViewById(R.id.descripTv);
                header.setText(mainContext.getString(R.string.error));
                description.setText(mainContext.getString(R.string.error_msg));
                Toast toast = new Toast(mainContext);
                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkDetails() {
        binding.deviceNumbersEdt.setError(null);
        binding.sectionIdEdt.setError(null);
        binding.cableIdSpinnerBar.setError(null);
        binding.statusSpinnersBar.setError(null);
        boolean isCancel = false;
        View focusView = null;

        if (binding.deviceNumbersEdt.getText().toString().trim().isEmpty()) {
            binding.deviceNumbersEdt.setError("Not Required Field Empty!");
            focusView = binding.deviceNumbersEdt;
            isCancel = true;
        }

        if (binding.sectionIdEdt.getText().toString().trim().isEmpty()) {
            binding.sectionIdEdt.setError("Not Required Field Empty!");
            focusView = binding.sectionIdEdt;
            isCancel = true;
        }

        String status = "";
        if (binding.statusSpinnersBar.getText().toString().trim().isEmpty()) {
            binding.statusSpinnersBar.setError("Please select a status!");
            focusView = binding.statusSpinnersBar;
            isCancel = true;
        } else {
            status = binding.statusSpinnersBar.getText().toString().trim();
            ArrayAdapter<?> statusAdapter = (ArrayAdapter<?>) binding.statusSpinnersBar.getAdapter();
            boolean isValidStatus = false;
            for (int i = 0; i < statusAdapter.getCount(); i++) {
                if (status.equals(statusAdapter.getItem(i).toString())) {
                    isValidStatus = true;
                    break;
                }
            }
            if (!isValidStatus) {
                binding.statusSpinnersBar.setError("Invalid status selected!");
                focusView = binding.statusSpinnersBar;
                isCancel = true;
            }
        }

        String id;
        if (binding.cableIdSpinnerBar.getText().toString().trim().isEmpty()) {
            id = "";
            binding.cableIdSpinnerBar.setError("Please select an ID!");
            focusView = binding.cableIdSpinnerBar;
            isCancel = true;
        } else {
            id = binding.cableIdSpinnerBar.getText().toString().trim();
            ArrayAdapter<?> idAdapter = (ArrayAdapter<?>) binding.cableIdSpinnerBar.getAdapter();
            boolean isValidId = false;
            for (int i = 0; i < idAdapter.getCount(); i++) {
                if (id.equals(idAdapter.getItem(i).toString())) {
                    isValidId = true;
                    break;
                }
            }
            if (!isValidId) {
                binding.cableIdSpinnerBar.setError("Invalid ID selected!");
                focusView = binding.cableIdSpinnerBar;
                isCancel = true;
            }
        }

        if (isCancel) {
            focusView.requestFocus();
        } else {
            if (binding.aChkBox.isChecked()) {
                phase = "1";
            }

            if (binding.bChkBox.isChecked()) {
                phase = "2";
            }

            if (binding.cChkBox.isChecked()) {
                phase = "3";
            }

            if (binding.aChkBox.isChecked() && binding.bChkBox.isChecked()) {
                phase = "4";
            }

            if (binding.aChkBox.isChecked() && binding.cChkBox.isChecked()) {
                phase = "5";
            }

            if (binding.bChkBox.isChecked() && binding.cChkBox.isChecked()) {
                phase = "6";
            }

            if (binding.aChkBox.isChecked() && binding.bChkBox.isChecked() && binding.cChkBox.isChecked()) {
                phase = "7";
            }
            String deviceNumber = binding.deviceNumbersEdt.getText().toString().trim();
            String statusCode = status.equals("Connected") ? "0" : "1";
            String deviceType = "1";
            String cymdbnet = prefManager.getDBName() != null ? prefManager.getDBName() : "";

            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mainContext)) {
                if (prefManager.getUserType() != null && prefManager.getUserType().contains("Edit")) {
                    updateDevice(networkId, deviceType, deviceNumber, id, statusCode, cymdbnet, cableSnippet);
                }
            } else {
                final Dialog dialog = new Dialog(mainContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.no_internet_dialog);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(mainContext.getDrawable(R.drawable.pop_background));
                LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
                Button retryBtn = dialog.findViewById(R.id.btnDialog);
                lottieAnimationView.playAnimation();
                retryBtn.setOnClickListener(view -> {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mainContext)) {
                        if (prefManager.getUserType() != null && prefManager.getUserType().contains("Edit")) {
                            updateDevice(networkId, deviceType, deviceNumber, id, statusCode, cymdbnet, cableSnippet);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void updateDevice(String networkId, String deviceType, String deviceNumber, String id, String status, String cymdbnet, CableSnippet cableSnippet) {
        JsonObject deviceObject = new JsonObject();
        deviceObject.addProperty("NetworkId", networkId);
        deviceObject.addProperty("DeviceType", deviceType);
        deviceObject.addProperty("DeviceNumber", deviceNumber);
        deviceObject.addProperty("ID", id);
        deviceObject.addProperty("Status", status);
        deviceObject.addProperty("CYMDBNET", cymdbnet);
        JsonArray dataArray = new JsonArray();
        dataArray.add(deviceObject);
        JsonObject obj = new JsonObject();
        obj.add("Data", dataArray);
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<UpdateDeviceModel> call = apiInterface.deviceUpdate("Bearer " + prefManager.getAccessToken(), obj);
        call.enqueue(new Callback<UpdateDeviceModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<UpdateDeviceModel> call, @NonNull Response<UpdateDeviceModel> response) {
                if (response.code() == 200) {
                    try {
                        UpdateDeviceModel updateDeviceModel = response.body();
                        assert updateDeviceModel != null;
                        String message = updateDeviceModel.getResults() != null && !updateDeviceModel.getResults().isEmpty()
                                ? updateDeviceModel.getResults().get(0).getMessage()
                                : "Device updated successfully";
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
                        snack.show();
                        dismiss();
                        if (cableSnippet != null) {
                            cableSnippet.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showErrorToast(response.message(), mainContext.getString(R.string.error_msg));
                    }
                } else {
                    showErrorToast(response.message() + " - " + response.code(), mainContext.getString(R.string.error_msg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateDeviceModel> call, @NonNull Throwable t) {
                showErrorToast(mainContext.getString(R.string.error), mainContext.getString(R.string.error_msg));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showErrorToast(String headerText, String descriptionText) {
        @SuppressLint("InflateParams")
        View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
        TextView okBtn = layout.findViewById(R.id.okBtn);
        TextView header = layout.findViewById(R.id.headerTv);
        TextView description = layout.findViewById(R.id.descripTv);
        header.setText(headerText);
        description.setText(descriptionText);
        okBtn.setOnClickListener(v -> {
        });
        Toast toast = new Toast(mainContext);
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    private void getEquipment() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("NetworkId", networkId);
        jsonObject.addProperty("Type", "Equipment");
        jsonObject.addProperty("Subtype", "Cable");
        jsonObject.addProperty("UserType", prefManager.getUserType());
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<EquipmentModel> call = apiInterface.getEquipmentData("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<EquipmentModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<EquipmentModel> call, @NonNull Response<EquipmentModel> response) {
                if (response.code() == 200) {
                    try {
                        EquipmentModel equipmentModel = response.body();
                        if (equipmentModel == null) {
                            Log.e("EQUIPMENT_RESPONSE", "Body is null");
                            return;
                        }
                        if (equipmentModel.getAllEquipmentId() != null
                                && equipmentModel.getAllEquipmentId().getEquipmentId() != null
                                && !equipmentModel.getAllEquipmentId().getEquipmentId().isEmpty()) {
                            cableIdList = equipmentModel.getAllEquipmentId().getEquipmentId().toArray(new String[0]);
                            if (currentLineId != null && !currentLineId.equals("Undefined") && !Arrays.asList(cableIdList).contains(currentLineId)) {
                                String[] tempList = new String[cableIdList.length + 1];
                                System.arraycopy(cableIdList, 0, tempList, 0, cableIdList.length);
                                tempList[cableIdList.length] = currentLineId;
                                cableIdList = tempList;
                            }
                        } else {
                            cableIdList = currentLineId != null ? new String[]{currentLineId} : new String[]{"Undefined"};
                        }
                        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, cableIdList);
                        idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                        binding.cableIdSpinnerBar.setAdapter(idAdapter);
                        binding.cableIdSpinnerBar.setText(currentLineId != null ? currentLineId : "Undefined", false);
                        idAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        cableIdList = currentLineId != null ? new String[]{currentLineId} : new String[]{"Undefined"};
                        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, cableIdList);
                        idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                        binding.cableIdSpinnerBar.setAdapter(idAdapter);
                        binding.cableIdSpinnerBar.setText(currentLineId != null ? currentLineId : "Undefined", false);
                        idAdapter.notifyDataSetChanged();
                    }
                } else {
                    @SuppressLint("InflateParams")
                    View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView description = layout.findViewById(R.id.descripTv);
                    header.setText(response.message() + " - " + response.code());
                    description.setText(mainContext.getString(R.string.error_msg));
                    Ok.setOnClickListener(v -> {
                        getEquipment();
                    });
                    Toast toast = new Toast(mainContext);
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EquipmentModel> call, @NonNull Throwable t) {
                cableIdList = currentLineId != null ? new String[]{currentLineId} : new String[]{"Undefined"};
                ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, cableIdList);
                idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                binding.cableIdSpinnerBar.setAdapter(idAdapter);
                binding.cableIdSpinnerBar.setText(currentLineId != null ? currentLineId : "Undefined", false);
                idAdapter.notifyDataSetChanged();
//                @SuppressLint("InflateParams")
//                View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
//                TextView Ok = layout.findViewById(R.id.okBtn);
//                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//                TextView header = layout.findViewById(R.id.headerTv);
//                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//                TextView description = layout.findViewById(R.id.descripTv);
//                header.setText(mainContext.getString(R.string.error));
//                description.setText(mainContext.getString(R.string.error_msg));
//                Ok.setOnClickListener(v -> {
//                    getEquipment();
//                });
//                Toast toast = new Toast(mainContext);
//                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
//                toast.setDuration(Toast.LENGTH_LONG);
//                toast.setView(layout);
//                toast.show();
            }
        });
    }

}

