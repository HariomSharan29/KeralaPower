package com.techlabs.apdcl.view.LayerInfo.DeviceInfo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.databinding.PhotovoltaicsystemdialogBinding;
import com.techlabs.apdcl.models.Line.Cable;
import com.techlabs.apdcl.models.Line.Overhead;
import com.techlabs.apdcl.models.Line.Unbalanced;
import com.techlabs.apdcl.models.device.PhotoVoltaic;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoVoltaicSystemDialog extends Dialog {

    private final Context mainContext;
    private final JsonObject jsonObject;
    private final JsonObject requestObject = new JsonObject();
    private PhotovoltaicsystemdialogBinding binding;
    private PrefManager prefManager;

    public PhotoVoltaicSystemDialog(@NonNull Context context, JsonObject jsonObject) {
        super(context);
        this.mainContext = context;
        this.jsonObject = jsonObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = PhotovoltaicsystemdialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View root = Objects.requireNonNull(getWindow()).getDecorView().getRootView();
        root.setBackground(getContext().getDrawable(R.drawable.pop_layout_background));
        prefManager = new PrefManager(mainContext);

        binding.btnLayout.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.reactorBtn.setText("Photovoltaic");
        binding.reactorBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
        binding.reactorBtn.setTextColor(getContext().getColor(R.color.black));
        binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.cableBtn.setTextColor(getContext().getColor(R.color.white));
        binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
        binding.headerTitle.setText("Photovoltaic");
        binding.photoVoltaicInfoLayout.setVisibility(View.VISIBLE);
        binding.cableInfoLayout.setVisibility(View.GONE);
        binding.overheadInfoLayout.setVisibility(View.GONE);
        binding.unbalanceInfoLayout.setVisibility(View.GONE);
        binding.nodeInfoLayout.setVisibility(View.GONE);
        binding.editReactorLayout.setVisibility(View.GONE);

        binding.imgClose.setOnClickListener(v -> dismiss());

        binding.reactorBtn.setOnClickListener(v -> {
            binding.reactorBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.reactorBtn.setTextColor(getContext().getColor(R.color.black));
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.white));
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
            binding.photoVoltaicInfoLayout.setVisibility(View.VISIBLE);
            binding.cableInfoLayout.setVisibility(View.GONE);
            binding.overheadInfoLayout.setVisibility(View.GONE);
            binding.unbalanceInfoLayout.setVisibility(View.GONE);
            binding.nodeInfoLayout.setVisibility(View.GONE);
        });

        binding.nodeBtn.setOnClickListener(v -> {
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.black));
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.white));
            binding.reactorBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.reactorBtn.setTextColor(getContext().getColor(R.color.white));
            binding.nodeInfoLayout.setVisibility(View.VISIBLE);
            binding.photoVoltaicInfoLayout.setVisibility(View.GONE);
            binding.cableInfoLayout.setVisibility(View.GONE);
            binding.overheadInfoLayout.setVisibility(View.GONE);
            binding.unbalanceInfoLayout.setVisibility(View.GONE);
        });

        binding.cableBtn.setOnClickListener(v -> {
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.black));
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
            binding.reactorBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.reactorBtn.setTextColor(getContext().getColor(R.color.white));
            binding.photoVoltaicInfoLayout.setVisibility(View.GONE);
            binding.nodeInfoLayout.setVisibility(View.GONE);

            if (binding.cableBtn.getText().toString().equals("Cable")) {
                binding.cableInfoLayout.setVisibility(View.VISIBLE);
                binding.overheadInfoLayout.setVisibility(View.GONE);
                binding.unbalanceInfoLayout.setVisibility(View.GONE);
                if (!requestObject.toString().contains("{}")) {
                    getCableInfo();
                }
            } else if (binding.cableBtn.getText().toString().equals("Balance")) {
                binding.overheadInfoLayout.setVisibility(View.VISIBLE);
                binding.cableInfoLayout.setVisibility(View.GONE);
                binding.unbalanceInfoLayout.setVisibility(View.GONE);
                if (!requestObject.toString().contains("{}")) {
                    getOverheadInfo();
                }
            } else if (binding.cableBtn.getText().toString().equals("UnBalance")) {
                binding.overheadInfoLayout.setVisibility(View.GONE);
                binding.cableInfoLayout.setVisibility(View.GONE);
                binding.unbalanceInfoLayout.setVisibility(View.VISIBLE);
                if (!requestObject.toString().contains("{}")) {
                    getUnBalanceInfo();
                }
            }
        });

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
            getPhotoVoltaicInfo();
        } else {
            showNoInternetDialog();
        }
    }

    private void getPhotoVoltaicInfo() {
        binding.photoVoltaicInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();

        if (prefManager.getUserType() == null) {
            binding.shimmerView.stopShimmer();
            binding.shimmerView.setVisibility(View.GONE);
            binding.photoVoltaicInfoLayout.setVisibility(View.VISIBLE);
            Toast.makeText(mainContext, "UserType not found", Toast.LENGTH_SHORT).show();
            return;
        }

        jsonObject.addProperty("UserType", prefManager.getUserType());
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());

        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<PhotoVoltaic> call = apiInterface.getPhotoVoltaicData("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<PhotoVoltaic>() {
            @Override
            public void onResponse(@NonNull Call<PhotoVoltaic> call, @NonNull Response<PhotoVoltaic> response) {
                binding.photoVoltaicInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);

                if (response.code() == 200 && response.body() != null && response.body().getOutput() != null) {
                    PhotoVoltaic.Output output = response.body().getOutput();

                    if (output.getDevicetypeLine() != null && output.getDevicetypeLine() == 1) {
                        binding.cableBtn.setText("Cable");
                    } else if (output.getDevicetypeLine() != null && output.getDevicetypeLine() == 2) {
                        binding.cableBtn.setText("Balance");
                    } else if (output.getDevicetypeLine() != null && output.getDevicetypeLine() == 17) {
                        binding.cableBtn.setText("UnBalance");
                    } else {
                        binding.cableBtn.setText("");
                    }

                    if (output.getDevicenumberLine() != null && output.getDevicetypeLine() != null) {
                        requestObject.addProperty("DeviceNumber", output.getDevicenumberLine());
                        requestObject.addProperty("DeviceType", String.valueOf(output.getDevicetypeLine()));
                        requestObject.addProperty("UserType", prefManager.getUserType());
                        requestObject.addProperty("CYMDBNET", prefManager.getDBName());
                    }

                    if (output.getSectionId() != null && !output.getSectionId().isEmpty() && !output.getSectionId().equals("null")) {
                        binding.sectionIdEdt.setText(output.getSectionId());
                    }

                    if (output.getAcdcconverterid() != null && !output.getAcdcconverterid().isEmpty() && !output.getAcdcconverterid().equals("null")) {
                        binding.shuntId.setText(output.getAcdcconverterid());
                    } else if (jsonObject.has("EquipmentId") && !jsonObject.get("EquipmentId").isJsonNull()) {
                        binding.shuntId.setText(jsonObject.get("EquipmentId").getAsString());
                    } else if (output.getConvDevicetype() != null) {
                        binding.shuntId.setText(String.valueOf(output.getConvDevicetype()));
                    } else {
                        binding.shuntId.setText("");
                    }

                    if (output.getDeviceNumber() != null && !output.getDeviceNumber().isEmpty() && !output.getDeviceNumber().equals("null")) {
                        binding.NumShunt.setText(output.getDeviceNumber());
                    }

                    if (output.getStatus() != null) {
                        if (output.getStatus() == 0) {
                            binding.shuntStatus.setText("Connected");
                        } else if (output.getStatus() == 1) {
                            binding.shuntStatus.setText("Disconnected");
                        } else {
                            binding.shuntStatus.setText(String.valueOf(output.getStatus()));
                        }
                    }

                    if (output.getLocation() != null) {
                        if (output.getLocation() == 1) {
                            binding.shuntLocation.setText("At From Node");
                        } else if (output.getLocation() == 2) {
                            binding.shuntLocation.setText("At To Node");
                        } else {
                            binding.shuntLocation.setText("At Middle Node");
                        }
                    }

                    if (output.getModel() != null && !output.getModel().isEmpty() && !output.getModel().equals("null")) {
                        binding.stageShunt.setText(output.getModel());
                    } else {
                        binding.stageShunt.setText("");
                    }

                    binding.aChkBox.setChecked(false);
                    binding.bChkBox.setChecked(false);
                    binding.cChkBox.setChecked(false);
                    if (output.getPhase() != null) {
                        if (output.getPhase() == 7) {
                            binding.aChkBox.setChecked(true);
                            binding.bChkBox.setChecked(true);
                            binding.cChkBox.setChecked(true);
                        } else if (output.getPhase() == 1) {
                            binding.aChkBox.setChecked(true);
                        } else if (output.getPhase() == 2) {
                            binding.bChkBox.setChecked(true);
                        } else if (output.getPhase() == 3) {
                            binding.cChkBox.setChecked(true);
                        }
                    }

                    binding.aChksBox.setChecked(true);
                    binding.bChksBox.setChecked(output.getUsedccapacitor() != null && output.getUsedccapacitor() == 1);
                    binding.cChksBox.setChecked(output.getConvDevicetype() != null);

                    ArrayList<String> spinnerItems = new ArrayList<>();
                    if (output.getAcdcconverterid() != null && !output.getAcdcconverterid().isEmpty() && !output.getAcdcconverterid().equals("null")) {
                        spinnerItems.add(output.getAcdcconverterid());
                    } else if (output.getInternalcouplingelement() != null && output.getInternalcouplingelement() == 1) {
                        spinnerItems.add("Internal Coupling Element");
                    } else if (output.getModelcontrol() != null) {
                        spinnerItems.add(String.valueOf(output.getModelcontrol()));
                    } else {
                        spinnerItems.add("Not Available");
                    }
                    android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(mainContext, android.R.layout.simple_spinner_item, spinnerItems);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.mySpinner.setAdapter(adapter);

                    if (output.getFromnodeid() != null && !output.getFromnodeid().isEmpty() && !output.getFromnodeid().equals("null")) {
                        binding.idFromNodesTv.setText(output.getFromnodeid());
                    }
                    if (output.getFromnodeX() != null) {
                        binding.xFromNodesTv.setText(String.valueOf(output.getFromnodeX()));
                    }
                    if (output.getFromnodeY() != null) {
                        binding.yFromNodesTv.setText(String.valueOf(output.getFromnodeY()));
                    }
                    if (output.getTonodeid() != null && !output.getTonodeid().isEmpty() && !output.getTonodeid().equals("null")) {
                        binding.idToNodeTv.setText(output.getTonodeid());
                    }
                    if (output.getTonodeX() != null) {
                        binding.xToNodeTv.setText(String.valueOf(output.getTonodeX()));
                    }
                    if (output.getTonodeY() != null) {
                        binding.yToNodesTv.setText(String.valueOf(output.getTonodeY()));
                    }

                    binding.corTypeChk.setChecked((output.getFromnodeid() != null && !output.getFromnodeid().equals("null"))
                            || (output.getTonodeid() != null && !output.getTonodeid().equals("null")));
                } else {
                    Toast.makeText(mainContext, "Unable to load photovoltaic data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhotoVoltaic> call, @NonNull Throwable t) {
                binding.photoVoltaicInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                Toast.makeText(mainContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCableInfo() {
        binding.cableInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<Cable> call = apiInterface.getCableData("Bearer " + prefManager.getAccessToken(), requestObject);
        call.enqueue(new Callback<Cable>() {
            @Override
            public void onResponse(@NonNull Call<Cable> call, @NonNull Response<Cable> response) {
                binding.cableInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                if (response.code() == 200 && response.body() != null && response.body().getOutput() != null) {
                    Cable.Output output = response.body().getOutput();

                    if (output.getSectionId() != null && !output.getSectionId().isEmpty()) {
                        binding.sectionIdEdt.setText(output.getSectionId());
                    }

                    if (output.getDeviceType() != null) {
                        if (output.getDeviceType() == 1) {
                            binding.typeTv.setText("Cable");
                        } else if (output.getDeviceType() == 2) {
                            binding.typeTv.setText("OverHead");
                        } else if (output.getDeviceType() == 23) {
                            binding.typeTv.setText("Unbalance");
                        }
                    }

                    if (output.getDeviceNumber() != null) {
                        binding.cnumberTv.setText(output.getDeviceNumber());
                    }

                    if (output.getStatus() != null) {
                        if (output.getStatus() == 0) {
                            binding.cstatusTv.setText("Connected");
                        } else if (output.getStatus() == 1) {
                            binding.cstatusTv.setText("DisConnected");
                        } else {
                            binding.cstatusTv.setText("By Passed");
                        }
                    }

                    if (output.getLength() != null) {
                        binding.clengthTv.setText(output.getLength() + " M");
                    }

                    if (output.getCableId() != null) {
                        binding.ccableIdTv.setText(output.getCableId());
                    }

                    if (output.getNumberOfCableInParallel() != null) {
                        binding.cnbCablePhaseTv.setText(output.getNumberOfCableInParallel() + " runs");
                    }

                    if (output.getOperatingTemperature() != null) {
                        binding.condTempTv.setText(output.getOperatingTemperature() + " °F");
                    }

                    if (output.getCableType() != null) {
                        if (output.getCableType() == 0) {
                            binding.cableTypeTv.setText("1C");
                        } else if (output.getCableType() == 1) {
                            binding.cableTypeTv.setText("3C");
                        } else {
                            binding.cableTypeTv.setText("3.5C");
                        }
                    }

                    if (output.getMaterialID() != null) {
                        binding.conductorMaterialTv.setText(String.valueOf(output.getMaterialID()));
                    } else {
                        binding.conductorMaterialTv.setText("Default");
                    }

                    if (output.getSizeMm2() != null) {
                        binding.conductorSizeTv.setText(output.getSizeMm2() + " mm²");
                    }

                    if (output.getInsulationType() != null && !output.getInsulationType().equals("null")) {
                        binding.insulationTypeTv.setText(output.getInsulationType());
                    }

                    if (output.getFROMNodeId() != null && !output.getFROMNodeId().equals("null")) {
                        binding.idFromNodesTv.setText(output.getFROMNodeId());
                    }

                    if (output.getTONodeId() != null && !output.getTONodeId().equals("null")) {
                        binding.idToNodeTv.setText(output.getTONodeId());
                    }

                    binding.corTypeChk.setChecked((output.getToNodeId() != null && !output.getToNodeId().equals("null"))
                            || (output.getFromNodeId() != null && !output.getFromNodeId().equals("null")));
                } else {
                    Toast.makeText(mainContext, "Unable to load cable data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cable> call, @NonNull Throwable t) {
                binding.cableInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                Toast.makeText(mainContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOverheadInfo() {
        binding.overheadInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<Overhead> call = apiInterface.getOverheadData("Bearer " + prefManager.getAccessToken(), requestObject);
        call.enqueue(new Callback<Overhead>() {
            @Override
            public void onResponse(@NonNull Call<Overhead> call, @NonNull Response<Overhead> response) {
                binding.overheadInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                if (response.code() == 200 && response.body() != null && response.body().getOutput() != null) {
                    Overhead.Output output = response.body().getOutput();

                    if (output.getDeviceType() != null) {
                        if (output.getDeviceType() == 1) {
                            binding.overheadTyeTv.setText("Cable");
                        } else if (output.getDeviceType() == 2) {
                            binding.overheadTyeTv.setText("OverHead");
                        } else if (output.getDeviceType() == 23) {
                            binding.overheadTyeTv.setText("Unbalance");
                        }
                    }

                    if (output.getDeviceNumber() != null) {
                        binding.overheadNumberTv.setText(output.getDeviceNumber());
                    }

                    if (output.getStatus() != null) {
                        if (output.getStatus() == 0) {
                            binding.overheadStatusTv.setText("Connected");
                        } else if (output.getStatus() == 1) {
                            binding.overheadStatusTv.setText("DisConnected");
                        } else {
                            binding.overheadStatusTv.setText("By Passed");
                        }
                    }

                    if (output.getLength() != null) {
                        binding.overheadLengthTv.setText(output.getLength() + " m");
                    }

                    if (output.getLineId() != null) {
                        binding.overheadLengthIdTv.setText(output.getLineId());
                    }

                    if (output.getPositiveSequenceResistance() != null) {
                        binding.positiveSequenceFirstTv.setText(output.getPositiveSequenceResistance() + " R + jXΩ/km");
                    }

                    if (output.getPositiveSequenceReactance() != null) {
                        binding.positiveSequenceSecondTv.setText(output.getPositiveSequenceReactance() + " G + jBµS/km");
                    }

                    if (output.getZeroSequenceResistance() != null) {
                        binding.zeroSequenceFirstTv.setText(output.getZeroSequenceResistance() + " R + jXΩ/km");
                    }

                    if (output.getZeroSequenceReactance() != null) {
                        binding.zeroSequenceSecondTv.setText(output.getZeroSequenceReactance() + " G + jBµS/km");
                    }

                    if (output.getFromNodeId() != null) {
                        binding.idFromNodesTv.setText(output.getFromNodeId());
                    }

                    if (output.getToNodeId() != null) {
                        binding.idToNodeTv.setText(output.getToNodeId());
                    }

                    binding.corTypeChk.setChecked((output.getFromNodeId() != null && !output.getFromNodeId().equals("null"))
                            || (output.getToNodeId() != null && !output.getToNodeId().equals("null")));
                } else {
                    Toast.makeText(mainContext, "Unable to load balance data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Overhead> call, @NonNull Throwable t) {
                binding.overheadInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                Toast.makeText(mainContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUnBalanceInfo() {
        binding.unbalanceInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<Unbalanced> call = apiInterface.getUnbalancedData("Bearer " + prefManager.getAccessToken(), requestObject);
        call.enqueue(new Callback<Unbalanced>() {
            @Override
            public void onResponse(@NonNull Call<Unbalanced> call, @NonNull Response<Unbalanced> response) {
                binding.unbalanceInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                if (response.code() == 200 && response.body() != null && response.body().getOutput() != null) {
                    Unbalanced.Output output = response.body().getOutput();

                    if (output.getDeviceType() != null) {
                        if (String.valueOf(output.getDeviceType()).equals("1")) {
                            binding.untyeTv.setText("Cable");
                        } else if (String.valueOf(output.getDeviceType()).equals("2")) {
                            binding.untyeTv.setText("OverHead");
                        } else if (String.valueOf(output.getDeviceType()).equals("23")) {
                            binding.untyeTv.setText("Unbalance");
                        }
                    }

                    if (output.getDeviceNumber() != null) {
                        binding.unnumberTv.setText(output.getDeviceNumber());
                    }

                    if (output.getStatus() != null) {
                        if (output.getStatus() == 0) {
                            binding.unstatusTv.setText("Connected");
                        } else if (output.getStatus() == 1) {
                            binding.unstatusTv.setText("DisConnected");
                        } else {
                            binding.unstatusTv.setText("By Passed");
                        }
                    }

                    if (output.getLength() != null) {
                        binding.unlengthTv.setText(output.getLength() + " m");
                    }

                    if (output.getLineId() != null) {
                        binding.unbalanceLineIdTv.setText(output.getLineId());
                    }

                    if (output.getFromNodeId() != null) {
                        binding.idFromNodesTv.setText(output.getFromNodeId());
                    }
                    if (output.getFromNodeX() != null) {
                        binding.xFromNodesTv.setText(String.valueOf(output.getFromNodeX()));
                    }
                    if (output.getFromNodeY() != null) {
                        binding.yFromNodesTv.setText(String.valueOf(output.getFromNodeY()));
                    }
                    if (output.getToNodeId() != null) {
                        binding.idToNodeTv.setText(output.getToNodeId());
                    }
                    if (output.getToNodeX() != null) {
                        binding.xToNodeTv.setText(String.valueOf(output.getToNodeX()));
                    }
                    if (output.getToNodeY() != null) {
                        binding.yToNodesTv.setText(String.valueOf(output.getToNodeY()));
                    }

                    binding.corTypeChk.setChecked((output.getToNodeId() != null && !output.getToNodeId().equals("null"))
                            || (output.getFromNodeId() != null && !output.getFromNodeId().equals("null")));
                } else {
                    Toast.makeText(mainContext, "Unable to load unbalance data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Unbalanced> call, @NonNull Throwable t) {
                binding.unbalanceInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                Toast.makeText(mainContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNoInternetDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.no_internet_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getContext().getDrawable(R.drawable.pop_background));
        LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
        Button retryBtn = dialog.findViewById(R.id.btnDialog);
        lottieAnimationView.playAnimation();
        retryBtn.setOnClickListener(view -> {
            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
                dialog.dismiss();
                getPhotoVoltaicInfo();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
}