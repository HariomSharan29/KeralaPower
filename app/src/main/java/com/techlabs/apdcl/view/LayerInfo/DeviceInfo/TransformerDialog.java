package com.techlabs.apdcl.view.LayerInfo.DeviceInfo;

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
import com.techlabs.apdcl.Utils.custom.ErrorPdfLogger;
import com.techlabs.apdcl.databinding.DtinfoLayoutBinding;
import com.techlabs.apdcl.models.EquipmentModel;
import com.techlabs.apdcl.models.Line.Cable;
import com.techlabs.apdcl.models.Line.Overhead;
import com.techlabs.apdcl.models.Line.Unbalanced;
import com.techlabs.apdcl.models.del.UpdateDeviceModel;
import com.techlabs.apdcl.models.device.Transformer;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransformerDialog extends Dialog {

    private Context mainContext;
    private DtinfoLayoutBinding binding;
    private JsonObject jsonObject;
    private JsonObject requestObject = new JsonObject();
    private PrefManager prefManager;
    private String voltage;
    private String[] statusList = {"Connected", "Disconnected"};
    private String[] dtIdList;
    private String networkId;

    private String currentLineId;
    private String[] unBalanceIdList;

    private TransformerSnippet snippet;

    public TransformerDialog(@NonNull Context context, String networkId, String voltage, JsonObject jsonObject, TransformerSnippet snippet) {
        super(context);
        this.mainContext = context;
        this.jsonObject = jsonObject;
        this.voltage = voltage;
        this.networkId = networkId;
        this.snippet = snippet;
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DtinfoLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View MainLayoutBackGround = Objects.requireNonNull(getWindow()).getDecorView().getRootView();
        MainLayoutBackGround.setBackground(getContext().getDrawable(R.drawable.pop_layout_background));
        prefManager = new PrefManager(mainContext);

        binding.headerTitle.setText("Transformer");

        binding.btnLayout.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.cableBtn.setTextColor(getContext().getColor(R.color.white));

        binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
        binding.editTransformerLayout.setVisibility(View.VISIBLE);
        binding.meterLayout.setVisibility(View.GONE);
        binding.statusSpinnersBar.setThreshold(0);
        binding.statusSpinnersBar.setOnClickListener(v -> binding.statusSpinnersBar.showDropDown());
        binding.dtIDSpinner.setThreshold(0);
        binding.dtIDSpinner.setOnClickListener(v -> binding.dtIDSpinner.showDropDown());

        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
            getTransformerDetails();
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
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
                        getTransformerDetails();
                        if (prefManager.getUserType().contains("Edit")) {
                            getEquipment();
                        }
                        dialog.dismiss();
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        binding.transformerBtn.setOnClickListener(view -> {
            binding.transformerBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.transformerBtn.setTextColor(getContext().getColor(R.color.black));
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.white));
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
            binding.dtInfoLayout.setVisibility(View.VISIBLE);
            binding.cableInfoLayout.setVisibility(View.GONE);
            binding.overheadInfoLayout.setVisibility(View.GONE);
            binding.unbalanceInfoLayout.setVisibility(View.GONE);
            binding.nodeInfoLayout.setVisibility(View.GONE);
            binding.headerTitle.setText("Transformer");
        });

        binding.cableBtn.setOnClickListener(view -> {
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.black));
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
            binding.transformerBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.transformerBtn.setTextColor(getContext().getColor(R.color.white));
            binding.dtInfoLayout.setVisibility(View.GONE);
            binding.nodeInfoLayout.setVisibility(View.GONE);

            if (binding.cableBtn.getText().toString().equals("Cable")) {
                binding.cableInfoLayout.setVisibility(View.VISIBLE);
                binding.overheadInfoLayout.setVisibility(View.GONE);
                binding.unbalanceInfoLayout.setVisibility(View.GONE);
                binding.dtInfoLayout.setVisibility(View.GONE);
                binding.headerTitle.setText("Cable");
                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
                    if (requestObject != null) {
                        getCableInfo();
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
                            dialog.dismiss();
                        }
                    });
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            } else if (binding.cableBtn.getText().toString().equals("Balance")) {
                binding.overheadInfoLayout.setVisibility(View.VISIBLE);
                binding.cableInfoLayout.setVisibility(View.GONE);
                binding.unbalanceInfoLayout.setVisibility(View.GONE);
                binding.dtInfoLayout.setVisibility(View.GONE);
                binding.headerTitle.setText("Overhead Line Balanced");
                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
                    if (requestObject != null) {
                        getOverheadInfo();
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
                            getOverheadInfo();
                            dialog.dismiss();
                        }
                    });
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            } else {
                binding.unbalanceInfoLayout.setVisibility(View.VISIBLE);
                binding.overheadInfoLayout.setVisibility(View.VISIBLE);
                binding.cableInfoLayout.setVisibility(View.GONE);
                binding.dtInfoLayout.setVisibility(View.GONE);
                binding.headerTitle.setText("Overhead Line UnBalanced");

                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
                    if (requestObject != null) {
                        getUnBalanceInfo();
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
                            getUnBalanceInfo();
                            dialog.dismiss();
                        }
                    });
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        });

        if (prefManager.getUserType().contains("Edit")) {
            binding.editTransformerLayout.setVisibility(View.VISIBLE);
            binding.aChkBox.setClickable(true);
            binding.bChkBox.setClickable(true);
            binding.cChkBox.setClickable(true);
        } else {
            binding.editTransformerLayout.setVisibility(View.GONE);
            binding.aChkBox.setClickable(false);
            binding.bChkBox.setClickable(false);
            binding.cChkBox.setClickable(false);
        }

        binding.nodeBtn.setOnClickListener(view -> {
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.black));
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.white));
            binding.transformerBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.transformerBtn.setTextColor(getContext().getColor(R.color.white));
            binding.nodeInfoLayout.setVisibility(View.VISIBLE);
            binding.dtInfoLayout.setVisibility(View.GONE);
            binding.cableInfoLayout.setVisibility(View.GONE);
            binding.overheadInfoLayout.setVisibility(View.GONE);
            binding.unbalanceInfoLayout.setVisibility(View.GONE);
            binding.headerTitle.setText("Node");
            getNodeInfo();
        });
        binding.okbtns.setOnClickListener(v -> checkDetails());

        binding.canclebtns.setOnClickListener(v -> dismiss());
    }
    private void getNodeInfo() {
        binding.nodeInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();

        if (prefManager.getUserType() != null) {
            jsonObject.addProperty("UserType", prefManager.getUserType());
            jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<Transformer> call = apiInterface.getTransformerData("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<Transformer>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<Transformer> call, @NonNull Response<Transformer> response) {
                    binding.nodeInfoLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    if (response.code() == 200) {
                        try {
                            Transformer transformer = response.body();
                            if (transformer == null || transformer.getOutput() == null) return;

                            String fromNode = transformer.getOutput().getFromNodeId();
                            if (fromNode != null && !fromNode.isEmpty() && !fromNode.equals("null")) {
                                binding.idFromNodesTv.setText(fromNode);
                            } else {
                                binding.idFromNodesTv.setText("UNDEFINED");
                            }


                            String toNode = transformer.getOutput().getToNodeId();
                            if (toNode != null && !toNode.isEmpty() && !toNode.equals("null")) {
                                binding.idToNodeTv.setText(toNode);
                            } else {
                                binding.idToNodeTv.setText("UNDEFINED");
                            }

                            // Cord Type checkbox
                            boolean hasNodes = (fromNode != null && !fromNode.equals("null"))
                                    || (toNode != null && !toNode.equals("null"));
                            binding.corTypeChk.setChecked(hasNodes);

                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(mainContext, e);
                            e.printStackTrace();
                        }
                    } else {
                        showErrorToast(response.message() + " - " + response.code(),
                                mainContext.getString(R.string.error_msg));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Transformer> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(mainContext, "POST", "/LayermodelInfo/", t);
                    binding.nodeInfoLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    showErrorToast(mainContext.getString(R.string.error),
                            mainContext.getString(R.string.error_msg));
                }
            });
        }
    }

    private void getTransformerDetails() {
        binding.dtInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
        if (prefManager.getUserType() != null) {
            jsonObject.addProperty("UserType", prefManager.getUserType());
            jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<Transformer> call = apiInterface.getTransformerData("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<Transformer>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<Transformer> call, @NonNull Response<Transformer> response) {
                    binding.dtInfoLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    if (response.code() == 200) {
                        ErrorPdfLogger.logApiSuccess(mainContext, "POST", "/LayermodelInfo/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "CYMDBNET : " + prefManager.getDBName()
                                + "AccessToken : " + prefManager.getAccessToken()
                                + "jsonObject : " + jsonObject
                        );
                        try {
                            Transformer transformer = response.body();
                            assert transformer != null;
                            if (transformer.getOutput() != null) {
                                if (transformer.getOutput() != null && !transformer.getOutput().toString().isEmpty() && !transformer.getOutput().equals("null")) {

                                    if (transformer.getOutput().getDeviceTypeLine() != null) {
                                        if (transformer.getOutput().getDeviceTypeLine() == 1) {
                                            binding.cableBtn.setText("Cable");
                                        } else if (transformer.getOutput().getDeviceTypeLine() == 2) {
                                            binding.cableBtn.setText("Balance");
                                        } else if (transformer.getOutput().getDeviceTypeLine() == 17) {
                                            binding.cableBtn.setText("UnBalance");
                                        } else {
                                            binding.btnLayout.setWeightSum(binding.btnLayout.getWeightSum() - 1);
                                            binding.cableBtn.setVisibility(View.GONE);
                                        }
                                    } else {
                                        binding.btnLayout.setWeightSum(binding.btnLayout.getWeightSum() - 1);
                                        binding.cableBtn.setVisibility(View.GONE);
                                    }

                                    if (transformer.getOutput().getSectionId() != null && !transformer.getOutput().getSectionId().isEmpty() && transformer.getOutput().getDeviceTypeLine() != null && !transformer.getOutput().getDeviceTypeLine().toString().isEmpty() && prefManager.getUserType() != null) {
                                        requestObject.addProperty("DeviceNumber", transformer.getOutput().getLineDeviceNumber().toString());
                                        requestObject.addProperty("DeviceType", transformer.getOutput().getDeviceTypeLine().toString());
                                        requestObject.addProperty("UserType", prefManager.getUserType());
                                        requestObject.addProperty("CYMDBNET", prefManager.getDBName());
                                    }

                                    if (!transformer.getOutput().getSectionId().isEmpty() && !transformer.getOutput().getSectionId().equals("null") && transformer.getOutput().getSectionId() != null) {
                                        binding.sectionTv.setText(transformer.getOutput().getSectionId());
                                    } else {
                                        binding.sectionTv.setText("UNDEFINED");
                                    }

                                    if (!transformer.getOutput().getPhase().toString().isEmpty() && !transformer.getOutput().getPhase().toString().equals("null") && transformer.getOutput().getPhase() != null) {
                                        if (transformer.getOutput().getPhase() == 7) {
                                            binding.aChkBox.setChecked(true);
                                            binding.bChkBox.setChecked(true);
                                            binding.cChkBox.setChecked(true);
                                        } else if (transformer.getOutput().getPhase() == 1) {
                                            binding.aChkBox.setChecked(true);
                                            binding.bChkBox.setChecked(false);
                                            binding.cChkBox.setChecked(false);
                                        } else if (transformer.getOutput().getPhase() == 2) {
                                            binding.bChkBox.setChecked(true);
                                            binding.cChkBox.setChecked(false);
                                            binding.aChkBox.setChecked(false);
                                        } else if (transformer.getOutput().getPhase() == 3) {
                                            binding.cChkBox.setChecked(true);
                                            binding.aChkBox.setChecked(false);
                                            binding.bChkBox.setChecked(false);
                                        }
                                    }

                                    if (transformer.getOutput().getZoneId() != null) {
                                        binding.zoneTv.setText(transformer.getOutput().getZoneId().toString());
                                    } else {
                                        binding.zoneTv.setText("UNDEFINED");
                                    }

                                    if (transformer.getOutput().getEquipmentId() != null && !transformer.getOutput().getEquipmentId().isEmpty() && !transformer.getOutput().getEquipmentId().equals("null")) {
                                        currentLineId = transformer.getOutput().getEquipmentId();
                                        dtIdList = new String[]{currentLineId};
                                    } else {
                                        currentLineId = "Undefined";
                                        dtIdList = new String[]{"Undefined"};
                                    }
                                    ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, dtIdList);
                                    idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                                    binding.dtIDSpinner.setAdapter(idAdapter);
                                    binding.dtIDSpinner.setText(currentLineId, false);
                                    idAdapter.notifyDataSetChanged();

                                    if (!transformer.getOutput().getDeviceNumber().isEmpty() && !transformer.getOutput().getDeviceNumber().equals("null") && transformer.getOutput().getDeviceNumber() != null) {
                                        binding.spNumberTv.setText(transformer.getOutput().getDeviceNumber());
                                        binding.spNumberTv.setText(transformer.getOutput().getDeviceNumber());
                                    }

                                    ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, statusList);
                                    statusAdapter.setDropDownViewResource(R.layout.custom_spinner);
                                    binding.statusSpinnersBar.setAdapter(statusAdapter);
                                    if (transformer.getOutput().getStatus() != null) {
                                        String status = transformer.getOutput().getStatus() == 0 ? "Connected" : "Disconnected";
                                        binding.statusSpinnersBar.setText(status, false);
                                    } else {
                                        binding.statusSpinnersBar.setText("Connected", false);
                                    }

                                    if (transformer.getOutput().getReversible() != null && !transformer.getOutput().getReversible().toString().isEmpty() && !transformer.getOutput().getReversible().toString().equals("null")) {
                                        if (transformer.getOutput().getReversible() == 1) {
                                            binding.reversibleChk.setChecked(true);
                                        } else {
                                            binding.reversibleChk.setChecked(false);
                                        }
                                    } else {
                                        binding.reversibleChk.setChecked(false);
                                    }

                                    if (!transformer.getOutput().getLocation().toString().isEmpty() && !transformer.getOutput().getLocation().toString().equals("null") && transformer.getOutput().getLocation() != null) {
                                        if (transformer.getOutput().getLocation() == 1) {
                                            binding.spLengthTv.setText("At From Node");
                                        } else if (transformer.getOutput().getLocation() == 2) {
                                            binding.spLengthTv.setText("At To Node");
                                        } else {
                                            binding.spLengthTv.setText("At Middle Node");
                                        }
                                    }

                                    if (!transformer.getOutput().getFaultIndicator().toString().isEmpty() && !transformer.getOutput().getFaultIndicator().toString().equals("null") && transformer.getOutput().getFaultIndicator() != null) {
                                        if (transformer.getOutput().getFaultIndicator() == 1) {
                                            binding.dtFaultIndicatorTv.setText("Visual Fault Indicator");
                                        } else if (transformer.getOutput().getFaultIndicator() == 2) {
                                            binding.dtFaultIndicatorTv.setText("Remote Fault Indicator");
                                        } else {
                                            binding.dtFaultIndicatorTv.setText("No Fault Indicator");
                                        }
                                    }

                                    if (transformer.getOutput().getPrimaryTapSettingPercent() != null) {
                                        binding.dtPrimaryTapATv.setText(transformer.getOutput().getPrimaryTapSettingPercent().toString() + " " + "%");
                                    }

                                    if (transformer.getOutput().getPrimaryVoltageKVLL() != null) {
                                        binding.dtPrimaryTapBTv.setText(transformer.getOutput().getPrimaryVoltageKVLL().toString() + " " + "KVLL");
                                    }

                                    if (transformer.getOutput().getSecondaryTapSettingPercent() != null) {
                                        binding.dtSecondaryTapATv.setText(transformer.getOutput().getSecondaryTapSettingPercent().toString() + " " + "%");
                                    }

                                    if (!transformer.getOutput().getSecondaryVoltageKVLL().toString().isEmpty() && !transformer.getOutput().getSecondaryVoltageKVLL().toString().equals("null") && transformer.getOutput().getSecondaryVoltageKVLL() != null) {
                                        binding.dtSecondaryTapBTv.setText(transformer.getOutput().getSecondaryVoltageKVLL().toString() + " " + "KVLL");
                                    }

                                    if (transformer.getOutput().getDemandType() != null && transformer.getOutput().getIsTotalDemand() != null) {
                                        if (transformer.getOutput().getMeterIndex() != null && !transformer.getOutput().getMeterIndex().isEmpty()) {
                                            binding.meterIndex.setText(transformer.getOutput().getMeterIndex());
                                        }

                                        if (transformer.getOutput().getRefrenceTime() != null && !transformer.getOutput().getRefrenceTime().isEmpty()) {
                                            binding.refrenceDate.setText(transformer.getOutput().getRefrenceTime());
                                        }

                                        if (transformer.getOutput().getVal1A() != null) {
                                            binding.fuseAtNodeval1ATv.setText(transformer.getOutput().getVal1A().toString());
                                        }

                                        if (transformer.getOutput().getVal1B() != null) {   // ← was: getVal2A()
                                            binding.fuseAtNodeval2ATv.setText(transformer.getOutput().getVal1B().toString());
                                        }

                                        if (transformer.getOutput().getVal1C() != null) {   // ← was: getVal1A()
                                            binding.fuseAtNodeval3ATv.setText(transformer.getOutput().getVal1C().toString());

                                        }

                                        if (transformer.getOutput().getVal2A() != null) {   // ← was: getVal1A()
                                            binding.fuseAtNodeval1BTv.setText(transformer.getOutput().getVal2A().toString());
                                        }

                                        if (transformer.getOutput().getVal2B() != null) {   // ← was: getVal1A()
                                            binding.fuseAtNodeval2BTv.setText(transformer.getOutput().getVal2B().toString());
                                        }

                                        if (transformer.getOutput().getVal2C() != null) {   // ← was: getVal1A()
                                            binding.fuseAtNodeval3BTv.setText(transformer.getOutput().getVal2C().toString());
                                        }

                                    } else {
                                        binding.meterLayout.setVisibility(View.GONE);
                                    }

                                    if (!transformer.getOutput().getFromNodeId().isEmpty() && !transformer.getOutput().getFromNodeId().equals("null") && transformer.getOutput().getFromNodeId() != null) {
                                        binding.idFromNodesTv.setText(transformer.getOutput().getFromNodeId());
                                    }

                                    if (!transformer.getOutput().getToNodeId().isEmpty() && !transformer.getOutput().getToNodeId().equals("null") && transformer.getOutput().getToNodeId() != null) {
                                        binding.idToNodeTv.setText(transformer.getOutput().getToNodeId());
                                    }

                                    if (!transformer.getOutput().getFromNodeId().equals("null") && !transformer.getOutput().getToNodeId().equals("null")) {
                                        binding.corTypeChk.setChecked(true);
                                    } else {
                                        binding.corTypeChk.setChecked(false);
                                    }

                                    if (voltage != null) {
                                        binding.voltageTransformerTv.setText(voltage);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(mainContext,e);
                            e.printStackTrace();
                        }
                    } else {
                        ErrorPdfLogger.logApiError(mainContext, "POST", "/LayermodelInfo/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "CYMDBNET : " + prefManager.getDBName()
                                + "AccessToken : " + prefManager.getAccessToken()
                                + "jsonObject : " + jsonObject
                        );
                        @SuppressLint("InflateParams")
                        View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                        TextView Ok = layout.findViewById(R.id.okBtn);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
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
                public void onFailure(@NonNull Call<Transformer> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(mainContext, "POST", "/LayermodelInfo/", t);
                    binding.dtInfoLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    @SuppressLint("InflateParams")
                    View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
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
    }

    private void getEquipment() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("NetworkId", networkId);
        jsonObject.addProperty("Type", "Equipment");
        jsonObject.addProperty("Subtype", "Transformer");
        jsonObject.addProperty("UserType", prefManager.getUserType());
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<EquipmentModel> call = apiInterface.getEquipmentData("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<EquipmentModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<EquipmentModel> call, @NonNull Response<EquipmentModel> response) {
                if (response.code() == 200) {
                    ErrorPdfLogger.logApiError(mainContext, "POST", "/EquipmentInfo/", response.message()
                            + "NetworkId : " + networkId
                            + "Type : " + "Equipment"
                            + "Subtype : " + "Transformer"
                            + "UserType : " + prefManager.getUserType()
                            + "CYMDBNET : " + prefManager.getDBName()
                            + "AccessToken : " + prefManager.getAccessToken()
                            + "jsonObject : " + jsonObject
                    );
                    try {
                        EquipmentModel equipmentModel = response.body();
                        if (equipmentModel == null) {
                            return;
                        }
                        if (equipmentModel.getAllEquipmentId() != null
                                && equipmentModel.getAllEquipmentId().getEquipmentId() != null
                                && !equipmentModel.getAllEquipmentId().getEquipmentId().isEmpty()) {
                            dtIdList = equipmentModel.getAllEquipmentId().getEquipmentId().toArray(new String[0]);
                            if (currentLineId != null && !currentLineId.equals("Undefined") && !Arrays.asList(dtIdList).contains(currentLineId)) {
                                String[] tempList = new String[dtIdList.length + 1];
                                System.arraycopy(dtIdList, 0, tempList, 0, dtIdList.length);
                                tempList[dtIdList.length] = currentLineId;
                                dtIdList = tempList;
                            }
                        } else {
                            dtIdList = currentLineId != null ? new String[]{currentLineId} : new String[]{"Undefined"};
                        }
                        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, dtIdList);
                        idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                        binding.dtIDSpinner.setAdapter(idAdapter);
                        binding.dtIDSpinner.setText(currentLineId != null ? currentLineId : "Undefined", false);
                        idAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        ErrorPdfLogger.logCrash(mainContext, e);
                        dtIdList = currentLineId != null ? new String[]{currentLineId} : new String[]{"Undefined"};
                        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, dtIdList);
                        idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                        binding.dtIDSpinner.setAdapter(idAdapter);
                        binding.dtIDSpinner.setText(currentLineId != null ? currentLineId : "Undefined", false);
                        idAdapter.notifyDataSetChanged();
                    }
                } else {
                    ErrorPdfLogger.logApiError(mainContext, "POST", "/EquipmentInfo/", response.message()
                            + "NetworkId : " + networkId
                            + "Type : " + "Equipment"
                            + "Subtype : " + "Transformer"
                            + "UserType : " + prefManager.getUserType()
                            + "CYMDBNET : " + prefManager.getDBName()
                            + "AccessToken : " + prefManager.getAccessToken()
                            + "jsonObject : " + jsonObject
                    );
                    @SuppressLint("InflateParams")
                    View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView description = layout.findViewById(R.id.descripTv);
                    header.setText(response.message() + " - " + response.code());
                    description.setText(mainContext.getString(R.string.error_msg));
                    Ok.setOnClickListener(v -> getEquipment());
                    Toast toast = new Toast(mainContext);
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EquipmentModel> call, @NonNull Throwable t) {
                ErrorPdfLogger.logApiFailure(mainContext, "POST", "/EquipmentInfo/", t);
                dtIdList = currentLineId != null ? new String[]{currentLineId} : new String[]{"Undefined"};
                ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, dtIdList);
                idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                binding.dtIDSpinner.setAdapter(idAdapter);
                binding.dtIDSpinner.setText(currentLineId != null ? currentLineId : "Undefined", false);
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

    @SuppressLint("SetTextI18n")
    private void checkDetails() {
        Log.d("", "Check Detailed called");
        binding.spNumberTv.setError(null);
        binding.sectionTv.setError(null);
        binding.statusSpinnersBar.setError(null);
        binding.dtIDSpinner.setError(null);
        boolean isCancel = false;
        View focusView = null;

        if (binding.spNumberTv.getText().toString().trim().isEmpty()) {
            binding.spNumberTv.setError("Device Number cannot be empty!");
            focusView = binding.spNumberTv;
            isCancel = true;
        }

        if (binding.sectionTv.getText().toString().trim().isEmpty()) {
            binding.sectionTv.setError("Section ID cannot be empty!");
            focusView = binding.sectionTv;
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
        if (binding.dtIDSpinner.getText().toString().trim().isEmpty()) {
            id = "";
            binding.dtIDSpinner.setError("Please select an ID!");
            focusView = binding.dtIDSpinner;
            isCancel = true;
        } else {
            id = binding.dtIDSpinner.getText().toString().trim();
            ArrayAdapter<?> idAdapter = (ArrayAdapter<?>) binding.dtIDSpinner.getAdapter();
            boolean isValidId = false;
            for (int i = 0; i < idAdapter.getCount(); i++) {
                if (id.equals(idAdapter.getItem(i).toString())) {
                    isValidId = true;
                    break;
                }
            }
            if (!isValidId) {
                binding.dtIDSpinner.setError("Invalid ID selected!");
                focusView = binding.dtIDSpinner;
                isCancel = true;
            }
        }

        if (isCancel) {
            focusView.requestFocus();
        } else {
            String deviceNumber = binding.spNumberTv.getText().toString().trim();
            /*String status = binding.statusSpinnersBar.getSelectedItem() != null
                    ? binding.statusSpinnersBar.getSelectedItem().toString() : "Connected";
            String statusCode = status.equals("Connected") ? "0" : "1";
            String deviceType = "5";
            String id = binding.dtIDSpinner.getSelectedItem() != null
                    ? binding.dtIDSpinner.getSelectedItem().toString() :"";*/
            String statusCode = status.equals("Connected") ? "0" : "1";
            String deviceType = "5";
            String cymdbnet = prefManager.getDBName() != null ? prefManager.getDBName() : "";

            if (networkId == null) {
                showErrorToast("Error", "Network ID is missing.");
                return;
            }

            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mainContext)) {
                if (prefManager.getUserType() != null && prefManager.getUserType().contains("Edit")) {
                    updateDevice(networkId, deviceType, deviceNumber, id, statusCode, cymdbnet, snippet);
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
                            updateDevice(networkId, deviceType, deviceNumber, id, statusCode, cymdbnet, snippet);
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

    private void updateDevice(String networkId, String deviceType, String deviceNumber, String id, String status, String cymdbnet, TransformerSnippet snippet) {
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
                    ErrorPdfLogger.logApiSuccess(mainContext, "POST", "/DeviceUpdate/", response.message()
                            + "NetworkId : " + networkId
                            + "DeviceType : " + deviceType
                            + "DeviceNumber : " + deviceNumber
                            + "ID : " + id
                            + "Status : " + status
                            + "CYMDBNET : " + prefManager.getDBName()
                            + "AccessToken : " + prefManager.getAccessToken()
                            + "jsonObject : " + jsonObject
                    );
                    try {
                        UpdateDeviceModel updateDeviceModel = response.body();
                        assert updateDeviceModel != null;
                        String message = updateDeviceModel.getResults() != null && !updateDeviceModel.getResults().isEmpty()
                                ? updateDeviceModel.getResults().get(0).getMessage()
                                : "Device updated successfully";
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
                        snack.show();
                        dismiss();
                        if (snippet != null) {
                            snippet.dismiss();
                        }
                    } catch (Exception e) {
                        ErrorPdfLogger.logCrash(mainContext,e);
                        e.printStackTrace();
                        showErrorToast(response.message(), mainContext.getString(R.string.error_msg));
                    }
                } else {
                    ErrorPdfLogger.logApiError(mainContext, "POST", "/DeviceUpdate/", response.message()
                            + "NetworkId : " + networkId
                            + "DeviceType : " + deviceType
                            + "DeviceNumber : " + deviceNumber
                            + "ID : " + id
                            + "Status : " + status
                            + "CYMDBNET : " + prefManager.getDBName()
                            + "AccessToken : " + prefManager.getAccessToken()
                            + "jsonObject : " + jsonObject
                    );
                    showErrorToast(response.message() + " - " + response.code(), mainContext.getString(R.string.error_msg));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateDeviceModel> call, @NonNull Throwable t) {
                ErrorPdfLogger.logApiFailure(mainContext, "POST", "/DeviceUpdate/", t);
                showErrorToast(mainContext.getString(R.string.error), mainContext.getString(R.string.error_msg));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showErrorToast(String headerText, String descriptionText) {
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

    private void getCableInfo() {
        binding.cableInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<Cable> call = apiInterface.getCableData("Bearer " + prefManager.getAccessToken(), requestObject);
        call.enqueue(new Callback<Cable>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Cable> call, @NonNull Response<Cable> response) {
                binding.cableInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                if (response.code() == 200) {
                    ErrorPdfLogger.logApiSuccess(mainContext, "POST", "/LayermodelInfo/", response.message()
                            + "AccessToken : " + prefManager.getAccessToken()
                            + "requestObject : " + requestObject
                    );
                    try {
                        Cable cable = response.body();
                        assert cable != null;
                        if (cable.getOutput() != null) {
                            if (cable.getOutput().getSectionId() != null && !cable.getOutput().getSectionId().isEmpty()) {
                                binding.sectionTv.setText(cable.getOutput().getSectionId());
                            } else {
                                binding.sectionTv.setText("UNDEFINED");
                            }

                            if (cable.getOutput().getPhase() != null) {
                                if (cable.getOutput().getPhase() == 7) {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(true);
                                } else if (cable.getOutput().getPhase() == 1) {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(false);
                                    binding.cChkBox.setChecked(false);
                                } else if (cable.getOutput().getPhase() == 3) {
                                    binding.cChkBox.setChecked(true);
                                    binding.aChkBox.setChecked(false);
                                    binding.bChkBox.setChecked(false);
                                }
                            }

                            if (cable.getOutput().getZoneId() != null) {
                                binding.zoneTv.setText(cable.getOutput().getZoneId().toString());
                            } else {
                                binding.zoneTv.setText("UNDEFINED");
                            }

                            if (cable.getOutput().getDeviceType() != null) {
                                if (cable.getOutput().getDeviceType() == 1) {
                                    binding.typeTv.setText("Cable");
                                } else if (cable.getOutput().getDeviceType() == 2) {
                                    binding.typeTv.setText("OverHead");
                                } else if (cable.getOutput().getDeviceType() == 23) {
                                    binding.typeTv.setText("Unbalance");
                                }
                            }

                            if (cable.getOutput().getDeviceNumber() != null && !cable.getOutput().getDeviceNumber().isEmpty() && !cable.getOutput().getDeviceNumber().equals("null")) {
                                binding.cnumberTv.setText(cable.getOutput().getDeviceNumber());
                            } else {
                                binding.cnumberTv.setText("UNDEFINED");
                            }

                            if (cable.getOutput().getStatus() != null) {
                                if (cable.getOutput().getStatus() == 0) {
                                    binding.cstatusTv.setText("Connected");
                                } else if (cable.getOutput().getStatus() == 1) {
                                    binding.cstatusTv.setText("DisConnected");
                                } else {
                                    binding.cstatusTv.setText("By Passed");
                                }
                            }

                            if (cable.getOutput().getLength() != null) {
                                binding.clengthTv.setText(cable.getOutput().getLength().toString() + " " + "m");
                            }

                            if (!cable.getOutput().getCableId().isEmpty() && cable.getOutput().getCableId() != null) {
                                binding.ccableIdTv.setText(cable.getOutput().getCableId());
                            }

                            if (cable.getOutput().getNumberOfCableInParallel() != null) {
                                binding.cnbCablePhaseTv.setText(cable.getOutput().getNumberOfCableInParallel().toString() + "  " + "runs");
                            }

                            if (cable.getOutput().getOperatingTemperature() != null) {
                                binding.condTempTv.setText(cable.getOutput().getOperatingTemperature().toString() + " " + "°F");
                            }

                            if (cable.getOutput().getNominalRating() != null && !cable.getOutput().getNominalRating().toString().isEmpty()) {
                                binding.caNominalRating.setText(cable.getOutput().getNominalRating().toString() + " " + "A");
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
                                binding.conductorSizeTv.setText(cable.getOutput().getSizeMm2().toString() + " " + "mm²");
                            }

                            if (cable.getOutput().getInsulationType() != null && !cable.getOutput().getInsulationType().equals("null")) {
                                binding.insulationTypeTv.setText(cable.getOutput().getInsulationType());
                            }

                            if (cable.getOutput().getFROMNodeId() != null && !cable.getOutput().getFROMNodeId().equals("null")) {
                                binding.idFromNodesTv.setText(cable.getOutput().getFROMNodeId());
                            }

                            if (cable.getOutput().getTONodeId() != null && !cable.getOutput().getTONodeId().equals("null")) {
                                binding.idToNodeTv.setText(cable.getOutput().getTONodeId());
                            }

                            if (!cable.getOutput().getToNodeId().equals("null") || !cable.getOutput().getFromNodeId().equals("null")) {
                                binding.corTypeChk.setChecked(true);
                            } else {
                                binding.corTypeChk.setChecked(false);
                            }

                            if (voltage != null) {
                                binding.cableVoltage.setText(voltage);
                            }
                        }
                    } catch (Exception e) {
                        ErrorPdfLogger.logCrash(mainContext,e);
                        e.printStackTrace();
                    }
                } else {
                    ErrorPdfLogger.logApiError(mainContext, "POST", "/LayermodelInfo/", response.message()
                            + "AccessToken : " + prefManager.getAccessToken()
                            + "requestObject : " + requestObject
                    );
                    @SuppressLint("InflateParams")
                    View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
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
                ErrorPdfLogger.logApiFailure(mainContext, "POST", "/LayermodelInfo/", t);
                binding.dtInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                @SuppressLint("InflateParams")
                View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
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

    private void getOverheadInfo() {
        binding.overheadInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
//        binding.shimmerView.setVisibility(View.GONE);
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<Overhead> call = apiInterface.getOverheadData("Bearer " + prefManager.getAccessToken(), requestObject);
        call.enqueue(new Callback<Overhead>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Overhead> call, @NonNull Response<Overhead> response) {
                binding.overheadInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                if (response.code() == 200) {
                    ErrorPdfLogger.logApiSuccess(mainContext, "POST", "/LayermodelInfo/", response.message()
                            + "AccessToken : " + prefManager.getAccessToken()
                            + "requestObject : " + requestObject
                    );
                    try {
                        Overhead overhead = response.body();
                        assert overhead != null;
                        if (overhead.getOutput() != null) {
                            if (!overhead.getOutput().getSectionId().isEmpty() && !overhead.getOutput().getSectionId().equals("null") && overhead.getOutput().getSectionId() != null) {
                                binding.sectionTv.setText(overhead.getOutput().getSectionId());
                            } else {
                                binding.sectionTv.setText("UNDEFINED");
                            }

                            if (overhead.getOutput().getPhase() != null) {
                                if (overhead.getOutput().getPhase() == 7) {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(true);
                                } else if (overhead.getOutput().getPhase() == 1) {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(false);
                                    binding.cChkBox.setChecked(false);
                                } else if (overhead.getOutput().getPhase() == 2) {
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(false);
                                    binding.aChkBox.setChecked(false);
                                } else if (overhead.getOutput().getPhase() == 3) {
                                    binding.cChkBox.setChecked(true);
                                    binding.aChkBox.setChecked(false);
                                    binding.bChkBox.setChecked(false);
                                }
                            }

                            if (overhead.getOutput().getZoneId() != null) {
                                binding.zoneTv.setText(overhead.getOutput().getZoneId().toString());
                            } else {
                                binding.zoneTv.setText("UNDEFINED");
                            }

                            if (overhead.getOutput().getDeviceType() != null) {
                                if (overhead.getOutput().getDeviceType() == 1) {
                                    binding.overheadTyeTv.setText("Cable");
                                } else if (overhead.getOutput().getDeviceType() == 2) {
                                    binding.overheadTyeTv.setText("OverHead");
                                } else if (overhead.getOutput().getDeviceType() == 23) {
                                    binding.overheadTyeTv.setText("Unbalance");
                                }
                            } else {
                                binding.overheadTyeTv.setText("UNDEFINED");
                            }

                            if (!overhead.getOutput().getDeviceNumber().isEmpty() && !overhead.getOutput().getDeviceNumber().equals("null") && overhead.getOutput().getDeviceNumber() != null) {
                                binding.overheadNumberTv.setText(overhead.getOutput().getDeviceNumber());
                            } else {
                                binding.overheadNumberTv.setText("UNDEFINED");
                            }

                            if (overhead.getOutput().getStatus() != null) {
                                if (overhead.getOutput().getStatus() == 0) {
                                    binding.overheadStatusTv.setText("Connected");
                                } else if (overhead.getOutput().getStatus() == 1) {
                                    binding.overheadStatusTv.setText("DisConnected");
                                } else {
                                    binding.overheadStatusTv.setText("By Passed");
                                }
                            } else {
                                binding.overheadStatusTv.setText("UNDEFINED");
                            }

                            if (overhead.getOutput().getLength() != null) {
                                binding.overheadLengthTv.setText(overhead.getOutput().getLength().toString() + " " + "m");
                            } else {
                                binding.overheadLengthTv.setText("UNDEFINED");
                            }

                            if (!overhead.getOutput().getLineId().isEmpty() && !overhead.getOutput().getLineId().equals("null") && overhead.getOutput().getLineId() != null) {
                                binding.overheadLengthIdTv.setText(overhead.getOutput().getLineId());
                            } else {
                                binding.overheadLengthIdTv.setText("UNDEFINED");
                            }

                            if (overhead.getOutput().getNominalRating() != null && !overhead.getOutput().getNominalRating().toString().isEmpty()) {
                                binding.ohNominalRating.setText(overhead.getOutput().getNominalRating().toString() + " " + "A");
                            }

                            if (overhead.getOutput().getPositiveSequenceResistance() != null) {
                                binding.positiveSequenceFirstTv.setText(overhead.getOutput().getPositiveSequenceResistance().toString() + " " + "R + jXΩ/km");
                            } else {
                                binding.positiveSequenceFirstTv.setText("");
                            }

                            if (overhead.getOutput().getPositiveSequenceReactance() != null) {
                                binding.positiveSequenceSecondTv.setText(overhead.getOutput().getPositiveSequenceReactance().toString() + " " + "G + jBµS/km");
                            }

                            if (overhead.getOutput().getZeroSequenceResistance() != null) {
                                binding.zeroSequenceFirstTv.setText(overhead.getOutput().getZeroSequenceResistance().toString() + " " + "R + jXΩ/km");
                            }

                            if (overhead.getOutput().getZeroSequenceReactance() != null) {
                                binding.zeroSequenceSecondTv.setText(overhead.getOutput().getZeroSequenceReactance().toString() + " " + "G + jBµS/km");
                            }

                            if (!overhead.getOutput().getFromNodeId().isEmpty() && !overhead.getOutput().getFromNodeId().equals("null") && overhead.getOutput().getFromNodeId() != null) {
                                binding.idFromNodesTv.setText(overhead.getOutput().getFromNodeId());
                            }

                            if (!overhead.getOutput().getToNodeId().isEmpty() && !overhead.getOutput().getToNodeId().equals("null") && overhead.getOutput().getToNodeId() != null) {
                                binding.idToNodeTv.setText(overhead.getOutput().getToNodeId());
                            }

                            if (overhead.getOutput().getFromNodeId() != null || !overhead.getOutput().getFromNodeId().equals("null")) {
                                binding.corTypeChk.setChecked(true);
                            } else {
                                binding.corTypeChk.setChecked(false);
                            }

                            if (voltage != null) {
                                binding.overheadVoltage.setText(voltage);
                            }
                        }
                    } catch (Exception e) {
                        ErrorPdfLogger.logCrash(mainContext,e);
                        e.printStackTrace();
                    }
                } else {
                    ErrorPdfLogger.logApiError(mainContext, "POST", "/LayermodelInfo/", response.message()
                            + "AccessToken : " + prefManager.getAccessToken()
                            + "requestObject : " + requestObject
                    );
                    @SuppressLint("InflateParams")
                    View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
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
            public void onFailure(@NonNull Call<Overhead> call, @NonNull Throwable t) {
                ErrorPdfLogger.logApiFailure(mainContext, "POST", "/LayermodelInfo/", t);
                binding.dtInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                @SuppressLint("InflateParams")
                View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
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

    private void getUnBalanceInfo() {
        binding.unbalanceInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<Unbalanced> call = apiInterface.getUnbalancedData("Bearer " + prefManager.getAccessToken(), requestObject);
        call.enqueue(new Callback<Unbalanced>() {
            @Override
            public void onResponse(Call<Unbalanced> call, Response<Unbalanced> response) {
                binding.unbalanceInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                if (response.code() == 200) {
                    ErrorPdfLogger.logApiSuccess(mainContext, "POST", "/LayermodelInfo/", response.message()
                            + "AccessToken : " + prefManager.getAccessToken()
                            + "requestObject : " + requestObject
                    );
                    try {
                        Unbalanced unbalanced = response.body();
                        assert unbalanced != null;
                        if (unbalanced.getOutput() != null) {
                            if (!unbalanced.getOutput().getSectionId().isEmpty() && unbalanced.getOutput().getSectionId() != null) {
                                binding.sectionTv.setText(unbalanced.getOutput().getSectionId());
                            }
                            if (!unbalanced.getOutput().getPhase().toString().isEmpty() && !unbalanced.getOutput().getPhase().toString().equals("null") && unbalanced.getOutput().getPhase() != null) {
                                if (unbalanced.getOutput().getPhase() == 1) {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(false);
                                    binding.cChkBox.setChecked(false);
                                } else if (unbalanced.getOutput().getPhase() == 2) {
                                    binding.aChkBox.setChecked(false);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(false);
                                } else if (unbalanced.getOutput().getPhase() == 3) {
                                    binding.aChkBox.setChecked(false);
                                    binding.bChkBox.setChecked(false);
                                    binding.cChkBox.setChecked(true);
                                } else if (unbalanced.getOutput().getPhase() == 4) {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(false);
                                } else if (unbalanced.getOutput().getPhase() == 5) {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(false);
                                    binding.cChkBox.setChecked(true);
                                } else if (unbalanced.getOutput().getPhase() == 6) {
                                    binding.aChkBox.setChecked(false);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(true);
                                } else {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(true);
                                }
                            }

                            if (unbalanced.getOutput().getZoneId() != null && !unbalanced.getOutput().getZoneId().toString().isEmpty()) {
                                binding.zoneTv.setText(unbalanced.getOutput().getZoneId().toString());
                            }
                            if (!unbalanced.getOutput().getDeviceType().toString().isEmpty() && !unbalanced.getOutput().getDeviceType().toString().equals("null") && unbalanced.getOutput().getDeviceType() != null) {
                                if (unbalanced.getOutput().getDeviceType().toString().equals("1")) {
                                    binding.untyeTv.setText("Cable");
                                } else if (unbalanced.getOutput().getDeviceType().toString().equals("2")) {
                                    binding.untyeTv.setText("OverHead");
                                } else if (unbalanced.getOutput().getDeviceType().toString().equals("23")) {
                                    binding.untyeTv.setText("Unbalance");
                                }
                            }

                            if (!unbalanced.getOutput().getDeviceNumber().isEmpty() && !unbalanced.getOutput().getDeviceNumber().equals("null") && unbalanced.getOutput().getDeviceNumber() != null) {
                                binding.unnumberTv.setText(unbalanced.getOutput().getDeviceNumber());
                            }

                           /* ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, statusList);
                            statusAdapter.setDropDownViewResource(R.layout.custom_spinner);
                            binding.unstatusTv.setAdapter(statusAdapter);
                            if (unbalanced.getOutput().getStatus() != null) {
                                String status = unbalanced.getOutput().getStatus() == 0 ? "Connected" : "Disconnected";
                                binding.unstatusTv.setText(status, false);
                            } else {
                                binding.unstatusTv.setText("Connected", false);
                            }*/

                            if (!unbalanced.getOutput().getLength().toString().isEmpty() && !unbalanced.getOutput().getLength().toString().equals("null") && unbalanced.getOutput().getLength() != null) {
                                binding.unlengthTv.setText(unbalanced.getOutput().getLength().toString() + " " + "m");
                            }
                            if (!unbalanced.getOutput().getLineId().isEmpty() && !unbalanced.getOutput().getLineId().equals("null") && unbalanced.getOutput().getLineId() != null) {
                                binding.unbalanceLineIdTv.setText(unbalanced.getOutput().getLineId());
                            }

                            if (unbalanced.getOutput().getLineId() != null && !unbalanced.getOutput().getLineId().isEmpty() && !unbalanced.getOutput().getLineId().equals("null")) {
                                currentLineId = unbalanced.getOutput().getLineId();
                                unBalanceIdList = new String[]{currentLineId};
                            } else {
                                currentLineId = "Undefined";
                                unBalanceIdList = new String[]{"Undefined"};
                            }



                            if (!unbalanced.getOutput().getFromNodeId().isEmpty() && !unbalanced.getOutput().getFromNodeId().equals("null") && unbalanced.getOutput().getFromNodeId() != null) {
                                binding.idFromNodesTv.setText(unbalanced.getOutput().getFromNodeId());
                            }

                            if (!unbalanced.getOutput().getFromNodeX().toString().isEmpty() && !unbalanced.getOutput().getFromNodeX().toString().equals("null") && unbalanced.getOutput().getFromNodeX() != null) {
                                binding.xFromNodesTv.setText(unbalanced.getOutput().getFromNodeX().toString());
                            }

                            if (!unbalanced.getOutput().getFromNodeY().toString().isEmpty() && !unbalanced.getOutput().getFromNodeY().toString().equals("null") && unbalanced.getOutput().getFromNodeY() != null) {
                                binding.yFromNodesTv.setText(unbalanced.getOutput().getFromNodeY().toString());
                            }

                            if (!unbalanced.getOutput().getToNodeId().isEmpty() && !unbalanced.getOutput().getToNodeId().equals("null") && unbalanced.getOutput().getToNodeId() != null) {
                                binding.idToNodeTv.setText(unbalanced.getOutput().getToNodeId());
                            }

                            if (!unbalanced.getOutput().getToNodeX().toString().isEmpty() && !unbalanced.getOutput().getToNodeX().toString().equals("null") && unbalanced.getOutput().getToNodeX() != null) {
                                binding.xToNodeTv.setText(unbalanced.getOutput().getToNodeX().toString());
                            }

                            if (!unbalanced.getOutput().getToNodeY().toString().isEmpty() && !unbalanced.getOutput().getToNodeY().toString().equals("null") && unbalanced.getOutput().getToNodeY() != null) {
                                binding.yToNodesTv.setText(unbalanced.getOutput().getToNodeY().toString());
                            }

                            if (!unbalanced.getOutput().getToNodeId().equals("null") || !unbalanced.getOutput().getFromNodeId().equals("null")) {
                                binding.corTypeChk.setChecked(true);
                            } else {
                                binding.corTypeChk.setChecked(false);
                            }

                        }
                    } catch (Exception e) {
                        ErrorPdfLogger.logCrash(mainContext,e);
                        e.printStackTrace();

                    }
                } else {
                    ErrorPdfLogger.logApiError(mainContext, "POST", "/LayermodelInfo/", response.message()
                            + "AccessToken : " + prefManager.getAccessToken()
                            + "requestObject : " + requestObject
                    );
                    View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
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
            public void onFailure(Call<Unbalanced> call, Throwable t) {
                ErrorPdfLogger.logApiFailure(mainContext, "POST", "/LayermodelInfo/", t);
                binding.unbalanceInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
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

}

