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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.databinding.SpotloadinfoLayoutBinding;
import com.techlabs.apdcl.models.Line.Cable;
import com.techlabs.apdcl.models.Line.Overhead;
import com.techlabs.apdcl.models.Line.Unbalanced;
import com.techlabs.apdcl.models.device.SpotLoad;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpotLoadDialog extends Dialog {

    private SpotloadinfoLayoutBinding binding;
    private Context mainContext;
    private JsonObject jsonObject;
    private JsonObject requestObject = new JsonObject();
    private PrefManager prefManager;
    private List<SpotLoad.Output.CustomerData> customerData;
    private String[] unBalanceIdList;
    private String currentLineId;

    public SpotLoadDialog(@NonNull Context context, JsonObject jsonObject) {
        super(context);
        this.mainContext = context;
        this.jsonObject = jsonObject;
    }

    @SuppressLint({"ResourceType", "SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SpotloadinfoLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View MainLayoutBackGround = Objects.requireNonNull(getWindow()).getDecorView().getRootView();
        MainLayoutBackGround.setBackground(getContext().getDrawable(R.drawable.pop_layout_background));
        prefManager = new PrefManager(mainContext);

        binding.btnLayout.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.cableBtn.setTextColor(getContext().getColor(R.color.white));
        binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
        binding.spLoadModelTv.setText("DEFAULT");
        binding.editSpotloadLayout.setVisibility(View.GONE);

        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
            getSpotLoadInfo();
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
                        getSpotLoadInfo();
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        binding.SpotLoadBtn.setOnClickListener(view -> {
            binding.SpotLoadBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.SpotLoadBtn.setTextColor(getContext().getColor(R.color.black));
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.white));
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
            binding.spotLoadInfoLayout.setVisibility(View.VISIBLE);
            binding.cableInfoLayout.setVisibility(View.GONE);
            binding.overheadInfoLayout.setVisibility(View.GONE);
            binding.unbalanceInfoLayout.setVisibility(View.GONE);
            binding.nodeInfoLayout.setVisibility(View.GONE);
            binding.headerTitle.setText("SpotLoad");
        });

        binding.cableBtn.setOnClickListener(view -> {
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.black));
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
            binding.SpotLoadBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.SpotLoadBtn.setTextColor(getContext().getColor(R.color.white));
            binding.spotLoadInfoLayout.setVisibility(View.GONE);
            binding.nodeInfoLayout.setVisibility(View.GONE);
            if (binding.cableBtn.getText().toString().equals("Cable")) {
                binding.cableInfoLayout.setVisibility(View.VISIBLE);
                binding.overheadInfoLayout.setVisibility(View.GONE);
                binding.unbalanceInfoLayout.setVisibility(View.GONE);
                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
                    if (!requestObject.toString().contains("{}")) {
                        getCableInfo();
                    } else {
                        Toast.makeText(mainContext, "No Data", Toast.LENGTH_SHORT).show();
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
                                if (!requestObject.toString().contains("{}")) {
                                    getCableInfo();
                                } else {
                                    Toast.makeText(mainContext, "No Data", Toast.LENGTH_SHORT).show();
                                }
                            }
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
                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
                    if (!requestObject.toString().contains("{}")) {
                        getOverheadInfo();
                    } else {
                        Toast.makeText(mainContext, "No Data", Toast.LENGTH_SHORT).show();
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
                                if (!requestObject.toString().contains("{}")) {
                                    getOverheadInfo();
                                } else {
                                    Toast.makeText(mainContext, "No Data", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            } else {
                binding.unbalanceInfoLayout.setVisibility(View.VISIBLE);
                binding.overheadInfoLayout.setVisibility(View.GONE);
                binding.cableInfoLayout.setVisibility(View.GONE);
                binding.unbalanceInfoLayout.setVisibility(View.GONE);
                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
                    getUnBalanceInfo();
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
                                getUnBalanceInfo();
                            }
                        }
                    });
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        });

        binding.nodeBtn.setOnClickListener(view -> {
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.black));
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.white));
            binding.SpotLoadBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.SpotLoadBtn.setTextColor(getContext().getColor(R.color.white));
            binding.nodeInfoLayout.setVisibility(View.VISIBLE);
            binding.spotLoadInfoLayout.setVisibility(View.GONE);
            binding.cableInfoLayout.setVisibility(View.GONE);
            binding.overheadInfoLayout.setVisibility(View.GONE);
            binding.unbalanceInfoLayout.setVisibility(View.GONE);
            binding.headerTitle.setText("Node");
        });

        binding.customDetails.setOnClickListener(v -> {
            if (!binding.spNumberTv.getText().toString().trim().isEmpty()) {
                if (customerData != null && !customerData.isEmpty()) {
                    CustomerDetailsDialog custombtnDialog = new CustomerDetailsDialog(mainContext, binding.spNumberTv.getText().toString().trim(), customerData);
                    custombtnDialog.show();
                }
            }
        });

    }

    private void getSpotLoadInfo() {
        binding.spotLoadInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
        if (prefManager.getUserType() != null) {
            jsonObject.addProperty("UserType", prefManager.getUserType());
            jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<SpotLoad> call = apiInterface.getSpotLoadData("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<SpotLoad>() {
                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                @Override
                public void onResponse(@NonNull Call<SpotLoad> call, @NonNull Response<SpotLoad> response) {
                    binding.spotLoadInfoLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    if (response.code() == 200) {
                        try {
                            SpotLoad spotLoad = response.body();
                            assert spotLoad != null;
                            if (spotLoad.getOutput() != null) {
                                customerData = spotLoad.getOutput().get(0).getCustomerData();
                                binding.btnLayout.setWeightSum(3);
                                binding.cableBtn.setVisibility(View.VISIBLE);
                                if (spotLoad.getOutput() != null && !spotLoad.getOutput().isEmpty()) {
                                    int deviceTypeLine = spotLoad.getOutput().get(0).getDeviceTypeLine();
                                    if (deviceTypeLine == 1) {
                                        binding.cableBtn.setText("Cable");
                                    } else if (deviceTypeLine == 2) {
                                        binding.cableBtn.setText("Balance");
                                    } else if (deviceTypeLine == 17) {
                                        binding.cableBtn.setText("UnBalance");
                                    } else {
                                        binding.btnLayout.setWeightSum(2);
                                        binding.cableBtn.setVisibility(View.GONE);
                                    }
                                } else {
                                    binding.btnLayout.setWeightSum(2);
                                    binding.cableBtn.setVisibility(View.GONE);
                                }

                                if (!spotLoad.getOutput().get(0).getSectionId().equals("null") && prefManager.getUserType() != null) {
                                    requestObject.addProperty("DeviceNumber", spotLoad.getOutput().get(0).getLineDeviceNumber());
                                    requestObject.addProperty("DeviceType", spotLoad.getOutput().get(0).getDeviceTypeLine().toString());
                                    requestObject.addProperty("UserType", prefManager.getUserType());
                                    requestObject.addProperty("CYMDBNET", prefManager.getDBName());

                                }

                                if (!spotLoad.getOutput().get(0).getSectionId().isEmpty() && !spotLoad.getOutput().get(0).getSectionId().equals("null") && spotLoad.getOutput().get(0).getSectionId() != null) {
                                    binding.sectionIdTv.setText(spotLoad.getOutput().get(0).getSectionId());
                                }

                                if (spotLoad.getOutput().get(0).getCustomerData().get(0).getPhase().get7() != null) {
                                    if (spotLoad.getOutput().get(0).getCustomerData().get(0).getPhase().get7() == 0) {

                                        binding.singlePhaseCheckedLayout.setVisibility(View.VISIBLE);
                                        binding.threePhaseCheckedLayout.setVisibility(View.GONE);

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getPhase().get1() == 1) {
                                            binding.aChkBox.setChecked(true);
                                            binding.aChkBox.setClickable(true);
                                        } else {
                                            binding.aChkBox.setChecked(false);
                                        }

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getPhase().get2() == 1) {
                                            binding.bChkBox.setChecked(true);
                                            binding.bChkBox.setClickable(true);
                                        } else {
                                            binding.bChkBox.setChecked(false);
                                        }

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getPhase().get3() == 1) {
                                            binding.cChkBox.setChecked(true);
                                            binding.cChkBox.setClickable(true);
                                        } else {
                                            binding.cChkBox.setChecked(false);
                                        }
                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getPhase().get7() == 1) {
                                            binding.aChkBox.setChecked(true);
                                            binding.bChkBox.setChecked(true);
                                            binding.cChkBox.setChecked(true);
                                            binding.aChkBox.setClickable(true);
                                            binding.bChkBox.setClickable(true);
                                            binding.cChkBox.setClickable(true);
                                        }

                                        float apA = 0.0f, apB = 0.0f, apC = 0.0f;
                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getActualKW().get1() != null) {
                                            binding.apATv.setText(String.format("%.2f", apA = Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getActualKW().get1().toString())));
                                        }

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getActualKW().get2() != null) {
                                            binding.apBTv.setText(String.format("%.2f", apB = Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getActualKW().get2().toString())));
                                        }

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getActualKW().get3() != null) {
                                            binding.apCTv.setText(String.format("%.2f", apC = Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getActualKW().get3().toString())));
                                        }
                                        float totalAP = apA + apB + apC;
                                        binding.apTotalTv.setText(String.format("%.2f", totalAP));


                                        float pfA = 0.0f, pfB = 0.0f, pfC = 0.0f;
                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getPowerFactor().get1() != null) {
                                            binding.pfATv.setText(String.format("%.2f", pfA = Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getPowerFactor().get1().toString())));
                                        }

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getPowerFactor().get2() != null) {
                                            binding.pfBTv.setText(String.format("%.2f", pfB = Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getPowerFactor().get2().toString())));
                                        }

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getPowerFactor().get3() != null) {
                                            binding.pfCTv.setText(String.format("%.2f", pfC = Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getPowerFactor().get3().toString())));
                                        }
                                        float totalPF = pfA + pfB + pfC;
                                        binding.pfTotalTv.setText(String.format("%.2f", totalPF));

                                        float kwhA = 0.0f, kwhB = 0.0f, kwhC = 0.0f;
                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getKWH().get1() != null) {
                                            binding.ConsATv.setText(String.format("%.2f", kwhA = Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getKWH().get1().toString())));
                                        }
                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getKWH().get2() != null) {
                                            binding.ConsBTv.setText(String.format("%.2f", kwhB = Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getKWH().get2().toString())));
                                        }
                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getKWH().get3() != null) {
                                            binding.ConsCTv.setText(String.format("%.2f", kwhC = Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getKWH().get1().toString())));
                                        }
                                        float totalKWH = kwhA + kwhB + kwhC;
                                        binding.ConsTotalTv.setText(String.format("%.2f kWh", totalKWH));

                                        //Capacity
                                        float kvaA = 0.0f, kvaB = 0.0f, kvaC = 0.0f;
                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getConnectedKVA().get1() != null) {
                                            binding.ccATv.setText(String.format("%.2f", kvaA = Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getConnectedKVA().get1().toString())));
                                        }

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getConnectedKVA().get2() != null) {
                                            binding.ccBTv.setText(String.format("%.2f", kvaB = Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getConnectedKVA().get2().toString())));
                                        }

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getConnectedKVA().get3() != null) {
                                            binding.ccCTv.setText(String.format("%.2f", kvaC = Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getConnectedKVA().get3().toString())));
                                        }
                                        float totalKVA = kvaA + kvaB + kvaC;
                                        binding.ccTotalTv.setText(String.format("%.2f kVA", totalKVA));


                                    } else {

                                        binding.singlePhaseCheckedLayout.setVisibility(View.GONE);
                                        binding.threePhaseCheckedLayout.setVisibility(View.VISIBLE);

                                        binding.aChkBox.setChecked(true);
                                        binding.aChkBox.setClickable(false);
                                        binding.bChkBox.setChecked(true);
                                        binding.bChkBox.setClickable(false);
                                        binding.cChkBox.setChecked(true);
                                        binding.cChkBox.setClickable(false);

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getActualKW().get7() != null) {
                                            binding.spApparentPowerTv.setText(String.format("%.2f", Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getActualKW().get7().toString())) + " " + "kVA");
                                        }

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getPowerFactor().get7() != null) {
                                            binding.spPowerFactorTv.setText(String.format("%.2f", Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getPowerFactor().get7().toString())) + " " + "%");
                                        }

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getKWH().get7() != null) {
                                            binding.spConsumptionTv.setText(spotLoad.getOutput().get(0).getCustomerData().get(0).getKWH().get7().toString() + " " + "kWh");
                                        }

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getConnectedKVA().get7() != null) {
                                            binding.spConnectedCapacityTv.setText(String.format("%.2f", Float.parseFloat(spotLoad.getOutput().get(0).getCustomerData().get(0).getConnectedKVA().get7().toString())) + " " + "kVA");
                                        }

                                        if (spotLoad.getOutput().get(0).getCustomerData().get(0).getCustomerCount().get7() != null) {
                                            binding.spCustomersTv.setText(spotLoad.getOutput().get(0).getCustomerData().get(0).getCustomerCount().get7().toString());
                                        }

                                    }
                                }

                                if (spotLoad.getOutput().get(0).getDeviceNumber() != null) {
                                    binding.spNumberTv.setText(spotLoad.getOutput().get(0).getDeviceNumber());
                                }

                                if (spotLoad.getOutput().get(0).getCustomerData() != null) {
                                    binding.customerNum.setText(spotLoad.getOutput().get(0).getNumberOfCustomer().toString());
                                }

                                if (spotLoad.getOutput().get(0).getLocation() != null) {
                                    if (spotLoad.getOutput().get(0).getLocation() == 1) {
                                        binding.locationTv.setText("At From Node");
                                    } else if (spotLoad.getOutput().get(0).getLocation() == 2) {
                                        binding.locationTv.setText("At To Node");
                                    }
                                }

                                if (spotLoad.getOutput().get(0).getCustomerData().get(0).getConsumerClassId() != null) {
                                    binding.spCustomerTypeTv.setText(spotLoad.getOutput().get(0).getCustomerData().get(0).getConsumerClassId());
                                }
                                if (spotLoad.getOutput().get(0).getCustomerData().get(0).getLoadYear() != null) {
                                    binding.spYearTv.setText(spotLoad.getOutput().get(0).getCustomerData().get(0).getLoadYear());
                                }
                                //Node Data
                                if (spotLoad.getOutput().get(0).getFromNodeId() != null && !spotLoad.getOutput().get(0).getFromNodeId().isEmpty() && !spotLoad.getOutput().get(0).getFromNodeId().equals("null")) {
                                    binding.idFromNodesTv.setText(spotLoad.getOutput().get(0).getFromNodeId());
                                }

                                if (spotLoad.getOutput().get(0).getFromNodeX() != null) {
                                    binding.xFromNodesTv.setText(spotLoad.getOutput().get(0).getFromNodeX().toString());
                                }

                                if (spotLoad.getOutput().get(0).getFromNodeY() != null) {
                                    binding.yFromNodesTv.setText(spotLoad.getOutput().get(0).getFromNodeY().toString());
                                }

                                if (spotLoad.getOutput().get(0).getToNodeId() != null && !spotLoad.getOutput().get(0).getToNodeId().isEmpty() && !spotLoad.getOutput().get(0).getToNodeId().equals("null")) {
                                    binding.idToNodeTv.setText(spotLoad.getOutput().get(0).getToNodeId());
                                }

                                if (spotLoad.getOutput().get(0).getToNodeX() != null) {
                                    binding.xToNodeTv.setText(spotLoad.getOutput().get(0).getToNodeX().toString());
                                }

                                if (spotLoad.getOutput().get(0).getToNodeY() != null) {
                                    binding.yToNodesTv.setText(spotLoad.getOutput().get(0).getToNodeY().toString());
                                }

                                if (!spotLoad.getOutput().get(0).getToNodeId().equals("null") || !spotLoad.getOutput().get(0).getFromNodeId().equals("null")) {
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
                public void onFailure(@NonNull Call<SpotLoad> call, @NonNull Throwable t) {

                    binding.spotLoadInfoLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    Log.e("API_FAILURE", "Request failed: " + t.getMessage(), t);
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
                    try {
                        Cable cable = response.body();
                        assert cable != null;
                        if (cable.getOutput() != null) {
                            if (cable.getOutput() != null) {
                                if (cable.getOutput().getSectionId() != null && !cable.getOutput().getSectionId().isEmpty()) {
                                    binding.sectionIdTv.setText(cable.getOutput().getSectionId());
                                } else {
                                    binding.sectionIdTv.setText("UNDEFINED");
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
                                    binding.clengthTv.setText(cable.getOutput().getLength().toString() + " " + "M");
                                }

                                if (cable.getOutput().getCableId() != null) {
                                    binding.ccableIdTv.setText(cable.getOutput().getCableId());
                                }

                                if (cable.getOutput().getNumberOfCableInParallel() != null) {
                                    binding.cnbCablePhaseTv.setText(cable.getOutput().getNumberOfCableInParallel().toString() + " " + "runs");
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

                                   /* if (cable.getOutput().getFROMNodeId() != null && !cable.getOutput().getFROMNodeId().equals("null")) {
                                        FromIdTv.setText(cable.getOutput().getFROMNodeId());
                                    }

                                    if (cable.getOutput().getFROMNodeIdX() != null && !cable.getOutput().getFROMNodeIdX().equals("null")) {
                                        FromXTv.setText(cable.getOutput().getFROMNodeIdX().toString());
                                    }

                                    if (cable.getOutput().getFROMNodeIdY() != null && !cable.getOutput().getFROMNodeIdY().equals("null")) {
                                        FromYTv.setText(cable.getOutput().getFROMNodeIdY().toString());
                                    }

                                    if (cable.getOutput().getTONodeId() != null && !cable.getOutput().getTONodeId().equals("null")) {
                                        ToIdTv.setText(cable.getOutput().getTONodeId());
                                    }

                                    if (cable.getOutput().getTONodeIdX() != null && !cable.getOutput().getTONodeIdX().equals("null")) {
                                        ToXTv.setText(cable.getOutput().getTONodeIdX().toString());
                                    }

                                    if (cable.getOutput().getTONodeIdY() != null && !cable.getOutput().getTONodeIdY().equals("null")) {
                                        ToYTv.setText(cable.getOutput().getTONodeIdY().toString());
                                    }

                                    if (cable.getOutput().getToNodeId() != null || cable.getOutput().getFromNodeId() != null) {
                                        xyChk.setChecked(true);
                                    } else {
                                        xyChk.setChecked(false);
                                    }*/

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
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
                binding.cableInfoLayout.setVisibility(View.VISIBLE);
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
                    try {
                        Overhead overhead = response.body();
                        assert overhead != null;
                        if (overhead.getOutput() != null) {
                            if (!overhead.getOutput().getSectionId().isEmpty() && !overhead.getOutput().getSectionId().equals("null") && overhead.getOutput().getSectionId() != null) {
                                binding.sectionIdTv.setText(overhead.getOutput().getSectionId());
                            } else {
                                binding.sectionIdTv.setText("UNDEFINED");
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

                            if (overhead.getOutput().getLineId() != null) {
                                binding.overheadLengthIdTv.setText(overhead.getOutput().getLineId().toString() + " " + "m");
                            } else {
                                binding.overheadLengthIdTv.setText("UNDEFINED");
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

                                /*if (!overhead.getOutput().getFromNodeId().isEmpty() && !overhead.getOutput().getFromNodeId().equals("null") && overhead.getOutput().getFromNodeId() != null) {
                                    FromIdTv.setText(overhead.getOutput().getFromNodeId());
                                }

                                if (overhead.getOutput().getFromNodeX() != null) {
                                    FromXTv.setText(overhead.getOutput().getFromNodeX().toString());
                                }

                                if (overhead.getOutput().getFromNodeY() != null) {
                                    FromYTv.setText(overhead.getOutput().getFromNodeY().toString());
                                }

                                if (!overhead.getOutput().getToNodeId().isEmpty() && !overhead.getOutput().getToNodeId().equals("null") && overhead.getOutput().getToNodeId() != null) {
                                    ToIdTv.setText(overhead.getOutput().getToNodeId());
                                }

                                if (overhead.getOutput().getToNodeX() != null) {
                                    ToXTv.setText(overhead.getOutput().getToNodeX().toString());
                                }

                                if (overhead.getOutput().getToNodeY() != null) {
                                    ToYTv.setText(overhead.getOutput().getToNodeY().toString());
                                }

                                if (!overhead.getOutput().getFromNodeId().equals("null") || !overhead.getOutput().getFromNodeId().equals("null")) {
                                    xyChk.setChecked(true);
                                } else {
                                    xyChk.setChecked(false);
                                }*/
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
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
                binding.overheadInfoLayout.setVisibility(View.VISIBLE);
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
                    try {
                        Unbalanced unbalanced = response.body();
                        assert unbalanced != null;
                        if (unbalanced.getOutput() != null) {
                            if (!unbalanced.getOutput().getSectionId().isEmpty() && unbalanced.getOutput().getSectionId() != null) {
                                binding.sectionIdTv.setText(unbalanced.getOutput().getSectionId());
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

                            /*ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, statusList);
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
                        e.printStackTrace();

                    }
                } else {
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


