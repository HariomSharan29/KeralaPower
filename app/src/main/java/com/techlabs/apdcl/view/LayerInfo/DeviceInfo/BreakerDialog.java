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
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.Utils.custom.ErrorPdfLogger;
import com.techlabs.apdcl.databinding.BreakerMoreinfoLayoutBinding;
import com.techlabs.apdcl.models.Line.Cable;
import com.techlabs.apdcl.models.Line.Overhead;
import com.techlabs.apdcl.models.Line.Unbalanced;
import com.techlabs.apdcl.models.device.Breaker;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreakerDialog extends Dialog {

    private Context mainContext;
    private BreakerMoreinfoLayoutBinding binding;
    private JsonObject jsonObject;
    private JsonObject requestObject = new JsonObject();
    private PrefManager prefManager;
    private String voltage;
    private String[] unBalanceIdList;
    private String currentLineId;

    public BreakerDialog(@NonNull Context context, String voltage, JsonObject jsonObject) {
        super(context);
        this.mainContext = context;
        this.jsonObject = jsonObject;
        this.voltage = voltage;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = BreakerMoreinfoLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View MainLayoutBackGround = Objects.requireNonNull(getWindow()).getDecorView().getRootView();
        MainLayoutBackGround.setBackground(getContext().getDrawable(R.drawable.pop_layout_background));
        prefManager = new PrefManager(mainContext);

        binding.btnLayout.setBackground(mainContext.getDrawable(R.drawable.background_layout));
        binding.cableBtn.setBackground(mainContext.getDrawable(R.drawable.background_layout));
        binding.cableBtn.setTextColor(getContext().getColor(R.color.white));
        binding.nodeBtn.setBackground(mainContext.getDrawable(R.drawable.background_layout));
        binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
        binding.breakerInfoLayout.setVisibility(View.VISIBLE);
        binding.editBreakerLayout.setVisibility(View.GONE);
        binding.headerTitle.setText("Breaker");
        binding.shimmerView.startShimmer();

        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
            getBreakerInfo();
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
                    getBreakerInfo();
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        binding.breakerBtn.setOnClickListener(view -> {
            binding.breakerBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.breakerBtn.setTextColor(getContext().getColor(R.color.black));
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.white));
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
            binding.breakerInfoLayout.setVisibility(View.VISIBLE);
            binding.cableInfoLayout.setVisibility(View.GONE);
            binding.overheadInfoLayout.setVisibility(View.GONE);
            binding.unbalanceInfoLayout.setVisibility(View.GONE);
            binding.nodeInfoLayout.setVisibility(View.GONE);
            binding.headerTitle.setText("Breaker");
        });

        binding.cableBtn.setOnClickListener(view -> {
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.black));
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
            binding.breakerBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.breakerBtn.setTextColor(getContext().getColor(R.color.white));
            binding.breakerInfoLayout.setVisibility(View.GONE);
            binding.nodeInfoLayout.setVisibility(View.GONE);
            if (binding.cableBtn.getText().toString().equals("Cable")) {
                binding.headerTitle.setText("Cable");
                binding.cableInfoLayout.setVisibility(View.VISIBLE);
                binding.overheadInfoLayout.setVisibility(View.GONE);
                binding.unbalanceInfoLayout.setVisibility(View.GONE);
                binding.unbalanceInfoLayout.setVisibility(View.GONE);
                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
                    if (!requestObject.toString().contains("{}")) {
                        getCableInfo();
                    } else {
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Not Data", Snackbar.LENGTH_LONG);
                        snack.show();
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
                                dismiss();
                                getCableInfo();
                            }
                        }
                    });
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            } else if (binding.cableBtn.getText().toString().equals("Balance")) {
                binding.headerTitle.setText("Overhead Line Balanced");
                binding.overheadInfoLayout.setVisibility(View.VISIBLE);
                binding.cableInfoLayout.setVisibility(View.GONE);
                binding.unbalanceInfoLayout.setVisibility(View.GONE);
                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
                    if (!requestObject.toString().contains("{}")) {
                        getOverheadInfo();
                    } else {
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Not Data", Snackbar.LENGTH_LONG);
                        snack.show();
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
                                getOverheadInfo();
                            }
                        }
                    });
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            } else {
                binding.headerTitle.setText("Overhead Line UnBalance");
                binding.unbalanceInfoLayout.setVisibility(View.VISIBLE);
                binding.overheadInfoLayout.setVisibility(View.GONE);
                binding.cableInfoLayout.setVisibility(View.GONE);
                binding.unbalanceInfoLayout.setVisibility(View.GONE);
                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
                    if (!requestObject.toString().contains("{}")) {
                        getUnBalanceInfo();
                    } else {
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Not Data", Snackbar.LENGTH_LONG);
                        snack.show();
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
            binding.breakerBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.breakerBtn.setTextColor(getContext().getColor(R.color.white));
            binding.nodeInfoLayout.setVisibility(View.VISIBLE);
            binding.breakerInfoLayout.setVisibility(View.GONE);
            binding.cableInfoLayout.setVisibility(View.GONE);
            binding.overheadInfoLayout.setVisibility(View.GONE);
            binding.unbalanceInfoLayout.setVisibility(View.GONE);
            binding.headerTitle.setText("Node");
        });

    }

    private void getBreakerInfo() {
        binding.breakerInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
        if (prefManager.getUserType() != null) {
            jsonObject.addProperty("UserType", prefManager.getUserType());
            jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<Breaker> call = apiInterface.getBreakerData("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<Breaker>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<Breaker> call, @NonNull Response<Breaker> response) {
                    binding.breakerInfoLayout.setVisibility(View.VISIBLE);
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
                            Breaker breaker = response.body();
                            assert breaker != null;
                            if (breaker.getOutput() != null) {
                                if (breaker.getOutput().getDeviceTypeLine() != null) {
                                    binding.btnLayout.setWeightSum(3);
                                    binding.cableBtn.setVisibility(View.VISIBLE);
                                    if (breaker.getOutput().getDeviceTypeLine().equals("1")) {
                                        binding.cableBtn.setText("Cable");
                                    } else if (breaker.getOutput().getDeviceTypeLine().equals("2")) {
                                        binding.cableBtn.setText("Balance");
                                    } else if (breaker.getOutput().getDeviceTypeLine().equals("17")) {
                                        binding.cableBtn.setText("UnBalance");
                                    } else {
                                        binding.cableBtn.setText("");
                                    }
                                } else {
                                    binding.btnLayout.setWeightSum(2);
                                    binding.cableBtn.setVisibility(View.GONE);
                                }

                                if (prefManager.getUserType() != null && breaker.getOutput().getDeviceTypeLine() != null && !breaker.getOutput().getDeviceNumber().equals("null")) {
                                    requestObject.addProperty("DeviceNumber", breaker.getOutput().getLineDeviceNumber());
                                    requestObject.addProperty("DeviceType", breaker.getOutput().getDeviceTypeLine().toString());
                                    requestObject.addProperty("UserType", prefManager.getUserType());
                                    requestObject.addProperty("CYMDBNET", prefManager.getDBName());
                                }

                                if (breaker.getOutput().getSectionId() != null && !breaker.getOutput().getSectionId().isEmpty() && !breaker.getOutput().getSectionId().equals("null")) {
                                    binding.sectionTv.setText(breaker.getOutput().getSectionId());
                                }

                                if (breaker.getOutput().getPhase() != null) {
                                    if (breaker.getOutput().getPhase().equals("7")) {
                                        binding.aChkBox.setChecked(true);
                                        binding.bChkBox.setChecked(true);
                                        binding.cChkBox.setChecked(true);
                                    } else if (breaker.getOutput().getPhase().equals("1")) {
                                        binding.aChkBox.setChecked(true);
                                        binding.bChkBox.setChecked(false);
                                        binding.cChkBox.setChecked(false);
                                    } else if (breaker.getOutput().getPhase().equals("2")) {
                                        binding.bChkBox.setChecked(true);
                                        binding.cChkBox.setChecked(false);
                                        binding.aChkBox.setChecked(false);
                                    } else if (breaker.getOutput().getPhase().equals("3")) {
                                        binding.cChkBox.setChecked(true);
                                        binding.aChkBox.setChecked(false);
                                        binding.bChkBox.setChecked(false);
                                    }
                                }

                                if (breaker.getOutput().getZoneId() != null) {
                                    binding.zoneTv.setText(breaker.getOutput().getZoneId().toString());
                                } else {
                                    binding.zoneTv.setText("UNDEFINED");
                                }

                                if (breaker.getOutput().getEquipmentId() != null && !breaker.getOutput().getEquipmentId().isEmpty() && !breaker.getOutput().getEquipmentId().equals("null")) {
                                    binding.tyeTv.setText(breaker.getOutput().getEquipmentId());
                                } else {
                                    binding.tyeTv.setText("UNDEFINED");
                                }

                                if (breaker.getOutput().getDeviceNumber() != null && !breaker.getOutput().getDeviceNumber().isEmpty() && !breaker.getOutput().getDeviceNumber().equals("null")) {
                                    binding.numberTv.setText(breaker.getOutput().getDeviceNumber());
                                } else {
                                    binding.numberTv.setText("UNDEFINED");
                                }

                                if (breaker.getOutput().getModel() != null && !breaker.getOutput().getModel().isEmpty()) {
                                    binding.breakerType.setText(breaker.getOutput().getModel());
                                } else {
                                    binding.breakerType.setText("UNDEFINED");
                                }

                                if (breaker.getOutput().getRatedCurrent() != null && !breaker.getOutput().getRatedCurrent().toString().isEmpty()) {
                                    binding.ratedCurrent.setText(breaker.getOutput().getRatedCurrent().toString() + "  " + "A");
                                }

                                if (breaker.getOutput().getInterruptingRating() != null && !breaker.getOutput().getInterruptingRating().toString().isEmpty()) {
                                    binding.breakerIntK.setText(breaker.getOutput().getInterruptingRating().toString() + "  " + "KA");
                                }

                                if (breaker.getOutput().getStatus() != null) {
                                    if (breaker.getOutput().getStatus().equals("0")) {
                                        binding.statusTv.setText("Connected");
                                    } else if (breaker.getOutput().getStatus().equals("1")) {
                                        binding.statusTv.setText("DisConnected");
                                    } else {
                                        binding.statusTv.setText("By Passed");
                                    }
                                } else {
                                    binding.statusTv.setText("UNDEFINED");
                                }

                                if (breaker.getOutput().getReversible() != null) {
                                    if (breaker.getOutput().getReversible().equals("1")) {
                                        binding.reversibleChk.setChecked(true);
                                    }
                                } else {
                                    binding.reversibleChk.setChecked(false);
                                }

                                if (breaker.getOutput().getLocation() != null) {
                                    if (breaker.getOutput().getLocation().equals("1")) {
                                        binding.locationBrTv.setText("At From Node");
                                    } else if (breaker.getOutput().getLocation().equals("2")) {
                                        binding.locationBrTv.setText("At To Node");
                                    }
                                } else {
                                    binding.locationBrTv.setText("UNDEFINED");
                                }

                                if (breaker.getOutput().getClosedPhase() != null) {
                                    if (breaker.getOutput().getClosedPhase().equals("7")) {
                                        binding.stateTv.setText("Close");
                                    } else if (breaker.getOutput().getClosedPhase().equals("0")) {
                                        binding.stateTv.setText("Open");
                                    }
                                } else {
                                    binding.stateTv.setText("UNDEFINED");
                                }

                                if (breaker.getOutput().getDisconnectedPhase() != null) {
                                    if (breaker.getOutput().getDisconnectedPhase().equals("0") || breaker.getOutput().getDisconnectedPhase().equals("1")) {
                                        binding.connectedChk.setChecked(true);
                                    }
                                } else {
                                    binding.connectedChk.setChecked(false);
                                }

                                if (breaker.getOutput().getRemoteControlled() != null) {
                                    if (breaker.getOutput().getRemoteControlled().equals("1")) {
                                        binding.remotelyControlled.setChecked(true);
                                    } else {
                                        binding.remotelyControlled.setChecked(false);
                                    }
                                }

                                if (breaker.getOutput().getAutomated() != null) {
                                    if (breaker.getOutput().getAutomated().equals("1")) {
                                        binding.automated.setChecked(true);
                                    } else {
                                        binding.automated.setChecked(false);
                                    }
                                }

                                if (breaker.getOutput().getDemandType() != null && breaker.getOutput().getIsTotalDemand() != null) {

                                    if (breaker.getOutput().getMeterIndex() != null && !breaker.getOutput().getMeterIndex().toString().isEmpty()) {
                                        binding.meterIndex.setText(breaker.getOutput().getMeterIndex().toString());
                                    }

                                    if (breaker.getOutput().getReferenceTime() != null && !breaker.getOutput().getReferenceTime().toString().isEmpty()) {
                                        binding.referenceTime.setText(breaker.getOutput().getReferenceTime().toString());
                                    }

                                    if (breaker.getOutput().getDemandType().equals("0") && breaker.getOutput().getIsTotalDemand().equals("0")) {
                                        binding.kwKvarTv.setText("KVA-PF");
                                        if (breaker.getOutput().getVal1A() != null) {
                                            binding.aKwTv.setText(breaker.getOutput().getVal1A().toString() + "KVA");
                                        } else {
                                            binding.aKwTv.setText("0.0");
                                        }
                                        if (breaker.getOutput().getVal2A() != null) {
                                            binding.aKvarTv.setText(breaker.getOutput().getVal2A().toString() + "PF%");
                                        } else {
                                            binding.aKvarTv.setText("0.0");
                                        }

                                        if (breaker.getOutput().getVal1B() != null) {
                                            binding.bKwTv.setText(breaker.getOutput().getVal1B().toString() + "KVA");
                                        } else {
                                            binding.bKwTv.setText("0.0");
                                        }
                                        if (breaker.getOutput().getVal2B() != null) {
                                            binding.bKvarTv.setText(breaker.getOutput().getVal2B().toString() + "PF%");
                                        } else {
                                            binding.bKvarTv.setText("0.0");
                                        }

                                        if (breaker.getOutput().getVal1C() != null) {
                                            binding.cKwTv.setText(breaker.getOutput().getVal1C().toString() + "KVA");
                                        } else {
                                            binding.cKwTv.setText("0.0");
                                        }
                                        if (breaker.getOutput().getVal2C() != null) {
                                            binding.cKvarTv.setText(breaker.getOutput().getVal2C().toString() + "PF%");
                                        } else {
                                            binding.cKvarTv.setText("0.0");
                                        }

                                    } else if (breaker.getOutput().getDemandType().equals("2") && breaker.getOutput().getIsTotalDemand().equals("0")) {
                                        binding.kwKvarTv.setText("KW-PF");
                                        if (breaker.getOutput().getVal1A() != null) {
                                            binding.aKwTv.setText(breaker.getOutput().getVal1A().toString() + "  " + "KW");
                                        } else {
                                            binding.aKwTv.setText("0.0");
                                        }
                                        if (breaker.getOutput().getVal2A() != null) {
                                            binding.aKvarTv.setText(breaker.getOutput().getVal2A().toString() + "  " + "PF%");
                                        } else {
                                            binding.aKvarTv.setText("0.0");
                                        }

                                        if (breaker.getOutput().getVal1B() != null) {
                                            binding.bKwTv.setText(breaker.getOutput().getVal1B() + "  " + "KW");
                                        } else {
                                            binding.bKwTv.setText("0.0");
                                        }
                                        if (breaker.getOutput().getVal2B() != null) {
                                            binding.bKvarTv.setText(breaker.getOutput().getVal2B().toString() + "  " + "PF%");
                                        } else {
                                            binding.bKvarTv.setText("0.0");
                                        }

                                        if (breaker.getOutput().getVal1C() != null) {
                                            binding.cKwTv.setText(breaker.getOutput().getVal1C() + "  " + "KW");
                                        } else {
                                            binding.cKwTv.setText("0.0");
                                        }
                                        if (breaker.getOutput().getVal2C() != null) {
                                            binding.cKvarTv.setText(breaker.getOutput().getVal2C().toString() + "  " + "PF%");
                                        } else {
                                            binding.cKvarTv.setText("0.0");
                                        }

                                    } else if (breaker.getOutput().getDemandType().equals("3") && breaker.getOutput().getIsTotalDemand().equals("0")) {
                                        binding.kwKvarTv.setText("KW-Kvar");
                                        if (breaker.getOutput().getVal1A() != null) {
                                            binding.aKwTv.setText(breaker.getOutput().getVal1A().toString() + "  " + "KW");
                                        } else {
                                            binding.aKwTv.setText("0.0");
                                        }

                                        if (breaker.getOutput().getVal2A() != null) {
                                            binding.aKvarTv.setText(breaker.getOutput().getVal2A().toString() + "  " + "Kvar");
                                        } else {
                                            binding.aKvarTv.setText("0.0");
                                        }

                                        if (breaker.getOutput().getVal1B() != null) {
                                            binding.bKwTv.setText(breaker.getOutput().getVal1B() + "  " + "KW");
                                        } else {
                                            binding.bKwTv.setText("0.0");
                                        }
                                        if (breaker.getOutput().getVal2B() != null) {
                                            binding.bKvarTv.setText(breaker.getOutput().getVal2B().toString() + "  " + "Kvar");
                                        } else {
                                            binding.bKvarTv.setText("0.0");
                                        }

                                        if (breaker.getOutput().getVal1C() != null) {
                                            binding.cKwTv.setText(breaker.getOutput().getVal1C() + "  " + "KW");
                                        } else {
                                            binding.cKwTv.setText("0.0");
                                        }
                                        if (breaker.getOutput().getVal2C() != null) {
                                            binding.cKvarTv.setText(breaker.getOutput().getVal2C().toString() + "  " + "Kvar");
                                        } else {
                                            binding.cKvarTv.setText("0.0");
                                        }
                                    }
                                } else {
                                    binding.meterLayout.setVisibility(View.GONE);
                                    binding.kwKvarTv.setText("KW-Kvar");
                                    if (breaker.getOutput().getVal1A() != null) {
                                        binding.aKwTv.setText(breaker.getOutput().getVal1A().toString() + "  " + "KW");
                                    } else {
                                        binding.aKwTv.setText("0.0");
                                    }
                                    if (breaker.getOutput().getVal2A() != null) {
                                        binding.aKvarTv.setText(breaker.getOutput().getVal2A() + "  " + "Kvar");
                                    } else {
                                        binding.aKvarTv.setText("0.0");
                                    }

                                    if (breaker.getOutput().getVal1B() != null) {
                                        binding.bKwTv.setText(breaker.getOutput().getVal1B().toString() + "  " + "KW");
                                    } else {
                                        binding.bKwTv.setText("0.0");
                                    }
                                    if (breaker.getOutput().getVal2B() != null) {
                                        binding.bKvarTv.setText(breaker.getOutput().getVal2B() + "  " + "Kvar");
                                    } else {
                                        binding.bKvarTv.setText("0.0");
                                    }

                                    if (breaker.getOutput().getVal1C() != null) {
                                        binding.cKwTv.setText(breaker.getOutput().getVal1C() + "  " + "KW");
                                    } else {
                                        binding.cKwTv.setText("0.0");
                                    }

                                    if (breaker.getOutput().getVal2C() != null) {
                                        binding.cKvarTv.setText(breaker.getOutput().getVal2C().toString() + "  " + "Kvar");
                                    } else {
                                        binding.cKvarTv.setText("0.0");
                                    }
                                }

                                if (breaker.getOutput().getFromNodeId() != null && !breaker.getOutput().getFromNodeId().isEmpty() && !breaker.getOutput().getFromNodeId().equals("null")) {
                                    binding.idFromNodesTv.setText(breaker.getOutput().getFromNodeId());
                                } else {
                                    binding.idFromNodesTv.setText("");
                                }

                                if (breaker.getOutput().getToNodeId() != null && !breaker.getOutput().getToNodeId().isEmpty() && !breaker.getOutput().getToNodeId().equals("null")) {
                                    binding.idToNodeTv.setText(breaker.getOutput().getToNodeId());
                                } else {
                                    binding.idToNodeTv.setText("");
                                }

                                if (breaker.getOutput().getToNodeId() != null || breaker.getOutput().getFromNodeId() != null) {
                                    binding.corTypeChk.setChecked(true);
                                } else {
                                    binding.corTypeChk.setChecked(false);
                                }

                                if (voltage != null) {
                                    binding.voltageTv.setText(voltage);
                                }
                            }
                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(mainContext,e);
                            Log.d("exception", e.toString());
                        }
                    } else {
                        ErrorPdfLogger.logApiError(mainContext, "POST", "/LayermodelInfo/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "CYMDBNET : " + prefManager.getDBName()
                                + "AccessToken : " + prefManager.getAccessToken()
                                + "jsonObject : " + jsonObject
                        );
                        @SuppressLint("InflateParams") View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
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
                public void onFailure(@NonNull Call<Breaker> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(mainContext, "POST", "/LayermodelInfo/", t);
                    binding.breakerInfoLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    @SuppressLint("InflateParams") View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
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
        } else {
            binding.shimmerView.stopShimmer();
            binding.shimmerView.setVisibility(View.GONE);

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
                                    binding.cableTypeTv.setText("Cable");
                                } else if (cable.getOutput().getDeviceType() == 2) {
                                    binding.cableTypeTv.setText("OverHead");
                                } else if (cable.getOutput().getDeviceType() == 23) {
                                    binding.cableTypeTv.setText("Unbalance");
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

                            binding.corTypeChk.setChecked(!cable.getOutput().getToNodeId().equals("null") || !cable.getOutput().getFromNodeId().equals("null"));

                            if (voltage != null) {
                                binding.cableVoltage.setText(voltage);
                            }

                        }
                    } catch (Exception e) {
                        ErrorPdfLogger.logCrash(mainContext,e);
                        Log.d("exception", e.toString());
                    }
                } else {
                    ErrorPdfLogger.logApiError(mainContext, "POST", "/LayermodelInfo/", response.message()
                            + "AccessToken : " + prefManager.getAccessToken()
                            + "requestObject : " + requestObject
                    );
                    @SuppressLint("InflateParams") View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
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
                binding.cableInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                @SuppressLint("InflateParams") View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
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
                                binding.xToNodeTv.setText(overhead.getOutput().getToNodeId());
                            }

                            binding.corTypeChk.setChecked(!overhead.getOutput().getFromNodeId().equals("null") || !overhead.getOutput().getToNodeId().equals("null"));

                            if (voltage != null) {
                                binding.overheadVoltage.setText(voltage);
                            }

                        }
                    } catch (Exception e) {
                        ErrorPdfLogger.logCrash(mainContext,e);
                        Log.d("exception", e.toString());
                    }
                } else {
                    ErrorPdfLogger.logApiError(mainContext, "POST", "/LayermodelInfo/", response.message()
                            + "AccessToken : " + prefManager.getAccessToken()
                            + "requestObject : " + requestObject
                    );
                    @SuppressLint("InflateParams") View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
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
                binding.overheadInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                @SuppressLint("InflateParams") View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
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
                            + "requestObject : " + requestObject);
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

    @Override
    public void dismiss() {
        super.dismiss();
        binding = null;
    }
}

