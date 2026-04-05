package com.techlabs.apdcl.view.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.databinding.FragmentLoadFlowBoxBinding;
import com.techlabs.apdcl.models.loadflow.LoadFlowBoxData;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadFlowBox extends Fragment {
    private Context mContext;
    private FragmentLoadFlowBoxBinding binding;
    private String deviceNumber = null;
    private String type = "0";
    private List<String> list;
    private String equipId;
    private BottomSheetDialog dialog;
    private PrefManager prefManager;

    public LoadFlowBox(Context mContext, String deviceNumber, String type, List<String> list, String equipId) {
        this.mContext = mContext;
        this.deviceNumber = deviceNumber;
        this.type = type;
        this.list = list;
        this.equipId = equipId;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoadFlowBoxBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefManager = new PrefManager(mContext);

        if (!equipId.isEmpty()) {
            binding.eqipLayout.setVisibility(View.VISIBLE);
            binding.equipId.setText(equipId);
        } else {
            binding.eqipLayout.setVisibility(View.GONE);
        }

        binding.imgClose.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(LoadFlowBox.this);
            transaction.commit();
        });

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mContext)) {
            loadFlowBoxData();
        } else {
            final Dialog dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.no_internet_dialog);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getContext().getDrawable(R.drawable.pop_background));
            LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
            Button RetryBtn = dialog.findViewById(R.id.btnDialog);
            lottieAnimationView.playAnimation();
            RetryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mContext)) {
                        loadFlowBoxData();
                        dialog.dismiss();
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

    }

    private void loadFlowBoxData() {
        binding.shimmerView.startShimmer();
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.dataView.setVisibility(View.GONE);
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new Gson().toJsonTree(list).getAsJsonArray();
        jsonObject.addProperty("Username", prefManager.getUserName());
        jsonObject.add("NetworkId", jsonArray);
        jsonObject.addProperty("DeviceNumber", deviceNumber);
        jsonObject.addProperty("DeviceType", type);
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        jsonObject.addProperty("Type", "loadflow");
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<LoadFlowBoxData> call = apiInterface.getLoadFlowBoxData("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<LoadFlowBoxData>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(@NonNull Call<LoadFlowBoxData> call, @NonNull Response<LoadFlowBoxData> response) {
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                binding.dataView.setVisibility(View.VISIBLE);
                if (response.code() == 200) {
                    try {
                        LoadFlowBoxData loadFlowBoxData = response.body();
                        assert loadFlowBoxData != null;
                        if (loadFlowBoxData.getOutput() != null && !loadFlowBoxData.getOutput().isEmpty()) {
                            if (loadFlowBoxData.getOutput().get(0).getEqNo() != null && !loadFlowBoxData.getOutput().get(0).getEqNo().equals("null") && !loadFlowBoxData.getOutput().get(0).getEqNo().isEmpty()) {
                                binding.deviceNumber.setText(loadFlowBoxData.getOutput().get(0).getEqNo());
                            } else {
                                binding.deviceNumber.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getVBaseA() != null && !loadFlowBoxData.getOutput().get(0).getVBaseA().equals("null") && !loadFlowBoxData.getOutput().get(0).getVBaseA().isEmpty()) {
                                binding.vBaseA.setText(loadFlowBoxData.getOutput().get(0).getVBaseA());
                                String hexColorCode = loadFlowBoxData.getOutput().get(0).getVBaseAColor();
                                if (hexColorCode != null && !hexColorCode.contains("#fff")) {
                                    binding.vBaseA.setBackgroundColor(Color.parseColor(hexColorCode));
                                }
                            } else {
                                binding.vBaseA.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getVBaseB() != null && !loadFlowBoxData.getOutput().get(0).getVBaseB().equals("null") && !loadFlowBoxData.getOutput().get(0).getVBaseB().isEmpty()) {
                                binding.vBaseB.setText(loadFlowBoxData.getOutput().get(0).getVBaseB());
                                String hexColorCode = loadFlowBoxData.getOutput().get(0).getVBaseBColor();
                                if (hexColorCode != null && !hexColorCode.contains("#fff")) {
                                    binding.vBaseB.setBackgroundColor(Color.parseColor(hexColorCode));
                                }
                            } else {
                                binding.vBaseB.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getVBaseB() != null && !loadFlowBoxData.getOutput().get(0).getVBaseB().equals("null") && !loadFlowBoxData.getOutput().get(0).getVBaseC().isEmpty()) {
                                binding.vBaseC.setText(loadFlowBoxData.getOutput().get(0).getVBaseC());
                                String hexColorCode = loadFlowBoxData.getOutput().get(0).getVBaseCColor();
                                if (hexColorCode != null && !hexColorCode.contains("#fff")) {
                                    binding.vBaseC.setBackgroundColor(Color.parseColor(hexColorCode));
                                }
                            } else {
                                binding.vBaseC.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvlla() != null && !loadFlowBoxData.getOutput().get(0).getKvlla().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvlla().isEmpty()) {
                                binding.kvLiA.setText(loadFlowBoxData.getOutput().get(0).getKvlla());
                                String hexColorCode = loadFlowBoxData.getOutput().get(0).getkVLLAColor();
                                if (hexColorCode != null && !hexColorCode.contains("#fff")) {
                                    binding.kvLiA.setBackgroundColor(Color.parseColor(hexColorCode));
                                }
                            } else {
                                binding.kvLiA.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvllb() != null && !loadFlowBoxData.getOutput().get(0).getKvllb().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvllb().isEmpty()) {
                                binding.kvLiB.setText(loadFlowBoxData.getOutput().get(0).getKvllb());
                                String hexColorCode = loadFlowBoxData.getOutput().get(0).getkVLLAColor();
                                if (hexColorCode != null && !hexColorCode.contains("#fff")) {
                                    binding.kvLiB.setBackgroundColor(Color.parseColor(hexColorCode));
                                }
                            } else {
                                binding.kvLiB.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvllc() != null && !loadFlowBoxData.getOutput().get(0).getKvllc().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvllc().isEmpty()) {
                                binding.kvLiC.setText(loadFlowBoxData.getOutput().get(0).getKvllc());
                                if (loadFlowBoxData.getOutput().get(0).getkVLLCColor() != null && !loadFlowBoxData.getOutput().get(0).getkVLLCColor().contains("#fff")) {
                                    binding.kvLiC.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getkVLLCColor()));
                                }
                            } else {
                                binding.kvLiC.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvlna() != null && !loadFlowBoxData.getOutput().get(0).getKvlna().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvlna().isEmpty()) {
                                binding.kvLnA.setText(loadFlowBoxData.getOutput().get(0).getKvlna());
                                if (loadFlowBoxData.getOutput().get(0).getkVLNAColor() != null && !loadFlowBoxData.getOutput().get(0).getkVLNAColor().contains("#fff")) {
                                    binding.kvLnA.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getkVLNAColor()));
                                }
                            } else {
                                binding.kvLnA.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvlnb() != null && !loadFlowBoxData.getOutput().get(0).getKvlnb().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvlnb().isEmpty()) {
                                binding.kvLnB.setText(loadFlowBoxData.getOutput().get(0).getKvlnb());
                                if (loadFlowBoxData.getOutput().get(0).getkVLNBColor() != null && !loadFlowBoxData.getOutput().get(0).getkVLNBColor().contains("#fff")) {
                                    binding.kvLnB.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getkVLNBColor()));
                                }
                            } else {
                                binding.kvLnB.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvlnc() != null && !loadFlowBoxData.getOutput().get(0).getKvlnc().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvlnc().isEmpty()) {
                                binding.kvLnC.setText(loadFlowBoxData.getOutput().get(0).getKvlnc());
                                if (loadFlowBoxData.getOutput().get(0).getkVLNCColor() != null && !loadFlowBoxData.getOutput().get(0).getkVLNCColor().contains("#fff")) {
                                    binding.kvLnC.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getkVLNCColor()));
                                }
                            } else {
                                binding.kvLnC.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getIa() != null && loadFlowBoxData.getOutput().get(0).getKvlnc().equals("null") && !loadFlowBoxData.getOutput().get(0).getIa().isEmpty()) {
                                binding.iA.setText(loadFlowBoxData.getOutput().get(0).getIa());
                                if (loadFlowBoxData.getOutput().get(0).getIAColor() != null && !loadFlowBoxData.getOutput().get(0).getIAColor().contains("#fff")) {
                                    binding.iA.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getIAColor()));
                                }
                            } else {
                                binding.iA.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getIb() != null && !loadFlowBoxData.getOutput().get(0).getIb().equals("null") && !loadFlowBoxData.getOutput().get(0).getIb().isEmpty()) {
                                binding.iB.setText(loadFlowBoxData.getOutput().get(0).getIb());
                                if (loadFlowBoxData.getOutput().get(0).getIBColor() != null && !loadFlowBoxData.getOutput().get(0).getIBColor().contains("#fff")) {
                                    binding.iB.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getIBColor()));
                                }
                            } else {
                                binding.iB.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getIc() != null && !loadFlowBoxData.getOutput().get(0).getIc().equals("null") && !loadFlowBoxData.getOutput().get(0).getIc().isEmpty()) {
                                binding.iC.setText(loadFlowBoxData.getOutput().get(0).getIc());
                                if (loadFlowBoxData.getOutput().get(0).getICColor() != null && !loadFlowBoxData.getOutput().get(0).getICColor().contains("#fff")) {
                                    binding.iC.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getICColor()));
                                }
                            } else {
                                binding.iC.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvaa() != null && !loadFlowBoxData.getOutput().get(0).getKvaa().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvaa().isEmpty()) {
                                binding.kvaA.setText(loadFlowBoxData.getOutput().get(0).getKvaa());
                                if (loadFlowBoxData.getOutput().get(0).getKVAAColor() != null && !loadFlowBoxData.getOutput().get(0).getKVAAColor().contains("#fff")) {
                                    binding.kvaA.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getKVAAColor()));
                                }
                            } else {
                                binding.kvaA.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvab() != null && !loadFlowBoxData.getOutput().get(0).getKvab().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvab().isEmpty()) {
                                binding.kvaB.setText(loadFlowBoxData.getOutput().get(0).getKvab());
                                if (loadFlowBoxData.getOutput().get(0).getKVABColor() != null && !loadFlowBoxData.getOutput().get(0).getKVABColor().contains("#fff")) {
                                    binding.kvaB.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getKVABColor()));
                                }
                            } else {
                                binding.kvaB.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvac() != null && !loadFlowBoxData.getOutput().get(0).getKvac().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvac().isEmpty()) {
                                binding.kvaC.setText(loadFlowBoxData.getOutput().get(0).getKvac());
                                if (loadFlowBoxData.getOutput().get(0).getKVACColor() != null && !loadFlowBoxData.getOutput().get(0).getKVACColor().contains("#fff")) {
                                    binding.kvaC.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getKVACColor()));
                                }
                            } else {
                                binding.kvaC.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKwa() != null && !loadFlowBoxData.getOutput().get(0).getKwa().equals("null") && !loadFlowBoxData.getOutput().get(0).getKwa().isEmpty()) {
                                binding.kwA.setText(loadFlowBoxData.getOutput().get(0).getKwa());
                                if (loadFlowBoxData.getOutput().get(0).getKWAColor() != null && !loadFlowBoxData.getOutput().get(0).getKWAColor().contains("#fff")) {
                                    binding.kwA.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getKWAColor()));
                                }
                            } else {
                                binding.kwA.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKwb() != null && !loadFlowBoxData.getOutput().get(0).getKwb().equals("null") && !loadFlowBoxData.getOutput().get(0).getKwb().isEmpty()) {
                                binding.kwB.setText(loadFlowBoxData.getOutput().get(0).getKwb());
                                if (loadFlowBoxData.getOutput().get(0).getKWBColor() != null && !loadFlowBoxData.getOutput().get(0).getKWBColor().contains("#fff")) {
                                    binding.kwB.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getKWBColor()));
                                }
                            } else {
                                binding.kwB.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKwc() != null && !loadFlowBoxData.getOutput().get(0).getKwc().equals("null") && !loadFlowBoxData.getOutput().get(0).getKwc().isEmpty()) {
                                binding.kwC.setText(loadFlowBoxData.getOutput().get(0).getKwc());
                                if (loadFlowBoxData.getOutput().get(0).getKWCColor() != null && !loadFlowBoxData.getOutput().get(0).getKWCColor().contains("#fff")) {
                                    binding.kwC.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getKWCColor()));
                                }
                            } else {
                                binding.kwC.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvara() != null && !loadFlowBoxData.getOutput().get(0).getKvara().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvara().isEmpty()) {
                                binding.kvArA.setText(loadFlowBoxData.getOutput().get(0).getKvara());
                                if (loadFlowBoxData.getOutput().get(0).getKVARAColor() != null && !loadFlowBoxData.getOutput().get(0).getKVARAColor().contains("#fff")) {
                                    binding.kvArA.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getKVARAColor()));
                                }
                            } else {
                                binding.kvArA.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvarb() != null && !loadFlowBoxData.getOutput().get(0).getKvarb().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvarb().isEmpty()) {
                                binding.kvArB.setText(loadFlowBoxData.getOutput().get(0).getKvarb());
                                if (loadFlowBoxData.getOutput().get(0).getKVARBColor() != null && !loadFlowBoxData.getOutput().get(0).getKVARBColor().contains("#fff")) {
                                    binding.kvArB.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getKVARBColor()));
                                }
                            } else {
                                binding.kvArB.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvarc() != null && !loadFlowBoxData.getOutput().get(0).getKvarc().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvarc().isEmpty()) {
                                binding.kvArC.setText(loadFlowBoxData.getOutput().get(0).getKvarc());
                                if (loadFlowBoxData.getOutput().get(0).getKVARCColor() != null && !loadFlowBoxData.getOutput().get(0).getKVARCColor().contains("#fff")) {
                                    binding.kvArC.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getKVARCColor()));
                                }
                            } else {
                                binding.kvArC.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvatot() != null && !loadFlowBoxData.getOutput().get(0).getKvatot().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvatot().isEmpty()) {
                                binding.kvaTotal.setText(loadFlowBoxData.getOutput().get(0).getKvatot());
                                if (loadFlowBoxData.getOutput().get(0).getKVATOTColor() != null && !loadFlowBoxData.getOutput().get(0).getKVATOTColor().contains("#fff")) {
                                    binding.kvaTotal.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getKVATOTColor()));
                                }
                            } else {
                                binding.kvaTotal.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKwtot() != null && !loadFlowBoxData.getOutput().get(0).getKwtot().equals("null") && !loadFlowBoxData.getOutput().get(0).getKwtot().isEmpty()) {
                                binding.kwTotal.setText(loadFlowBoxData.getOutput().get(0).getKwtot());
                                if (loadFlowBoxData.getOutput().get(0).getKWTOTColor() != null && !loadFlowBoxData.getOutput().get(0).getKWTOTColor().contains("#fff")) {
                                    binding.kwTotal.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getKWTOTColor()));
                                }
                            } else {
                                binding.kwTotal.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getKvartot() != null && !loadFlowBoxData.getOutput().get(0).getKvartot().equals("null") && !loadFlowBoxData.getOutput().get(0).getKvartot().isEmpty()) {
                                binding.kvArTotal.setText(loadFlowBoxData.getOutput().get(0).getKvartot());
                                if (loadFlowBoxData.getOutput().get(0).getKVARTOTColor() != null && !loadFlowBoxData.getOutput().get(0).getKVARTOTColor().contains("#fff")) {
                                    binding.kvArTotal.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getKVARTOTColor()));
                                }
                            } else {
                                binding.kvArTotal.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getaDvabs() != null && !loadFlowBoxData.getOutput().get(0).getaDvabs().equalsIgnoreCase("null") && !loadFlowBoxData.getOutput().get(0).getaDvabs().isEmpty()) {
                                binding.vrData.setText(loadFlowBoxData.getOutput().get(0).getaDvabs());
                                if (loadFlowBoxData.getOutput().get(0).getaDvAbsColor() != null && !loadFlowBoxData.getOutput().get(0).getaDvAbsColor().contains("#fff")) {
                                    binding.vrData.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getaDvAbsColor()));
                                }
                            } else {
                                binding.vrData.setText("0.0");
                            }

                            if (loadFlowBoxData.getOutput().get(0).getLoading() != null && !loadFlowBoxData.getOutput().get(0).getLoading().equals("null") && !loadFlowBoxData.getOutput().get(0).getLoading().isEmpty()) {
                                binding.loadingData.setText(loadFlowBoxData.getOutput().get(0).getLoading());
                                if (loadFlowBoxData.getOutput().get(0).getLOADINGColor() != null && !loadFlowBoxData.getOutput().get(0).getLOADINGColor().contains("#fff")) {
                                    binding.loadingData.setBackgroundColor(Color.parseColor(loadFlowBoxData.getOutput().get(0).getLOADINGColor()));
                                }
                            } else {
                                binding.loadingData.setText("0.0");
                            }

                        } else {
                            Snackbar snack = Snackbar.make(requireActivity().findViewById(android.R.id.content), "No Result", Snackbar.LENGTH_LONG);
                            snack.show();
                        }
                    } catch (Exception e) {
                        Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                    }
                } else {
                    Snackbar snack = Snackbar.make(requireActivity().findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoadFlowBoxData> call, @NonNull Throwable t) {
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                binding.dataView.setVisibility(View.VISIBLE);
                Snackbar snack = Snackbar.make(requireActivity().findViewById(android.R.id.content), mContext.getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                snack.show();
            }
        });
    }

}