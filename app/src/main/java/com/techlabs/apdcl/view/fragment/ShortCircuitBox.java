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
import com.techlabs.apdcl.databinding.FragmentShortCircuitBoxBinding;
import com.techlabs.apdcl.models.analysis.ShortCircuitBoxModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShortCircuitBox extends Fragment {

    private FragmentShortCircuitBoxBinding binding;
    private PrefManager prefManager;
    private BottomSheetDialog dialog;
    private Context mainContext;
    private List<String> list;
    private String deviceNumber;
    private String deviceType;

    public ShortCircuitBox(Context mainContext, List<String> list, String deviceNumber, String deviceType) {
        this.mainContext = mainContext;
        this.list = list;
        this.deviceNumber = deviceNumber;
        this.deviceType = deviceType;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShortCircuitBoxBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefManager = new PrefManager(mainContext);

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mainContext)) {
            getShortCircuitBox();
        } else {
            final Dialog dialog = new Dialog(mainContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.no_internet_dialog);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(requireContext().getDrawable(R.drawable.pop_background));
            LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
            Button RetryBtn = dialog.findViewById(R.id.btnDialog);
            lottieAnimationView.playAnimation();
            RetryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mainContext)) {
                        getShortCircuitBox();
                        dialog.dismiss();
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }
        binding.imgClose.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(ShortCircuitBox.this);
            transaction.commit();
        });

    }

    private void getShortCircuitBox() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new Gson().toJsonTree(list).getAsJsonArray();
        jsonObject.addProperty("Username", prefManager.getUserName());
        jsonObject.add("NetworkId", jsonArray);
        jsonObject.addProperty("DeviceNumber", deviceNumber);
        jsonObject.addProperty("DeviceType", deviceType);
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<ShortCircuitBoxModel> call = apiInterface.ShortCircuitBox("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<ShortCircuitBoxModel>() {
            @Override
            public void onResponse(@NonNull Call<ShortCircuitBoxModel> call, @NonNull Response<ShortCircuitBoxModel> response) {
                if (response.code() == 200) {
                    try {
                        ShortCircuitBoxModel shortCircuitBoxModel = response.body();
                        assert shortCircuitBoxModel != null;
                        if (shortCircuitBoxModel.getOutput().get(0).getEqNo() != null && !shortCircuitBoxModel.getOutput().get(0).getEqNo().isEmpty()) {
                            binding.deviceNumber.setText(shortCircuitBoxModel.getOutput().get(0).getEqNo());
                        }

                        if (shortCircuitBoxModel.getOutput().get(0).getLLLampKmax() != null && !shortCircuitBoxModel.getOutput().get(0).getLLLampKmax().equals("null") && !shortCircuitBoxModel.getOutput().get(0).getLLLampKmax().isEmpty()) {
                            if (shortCircuitBoxModel.getOutput().get(0).getLLLampKmaxColor() != null && !shortCircuitBoxModel.getOutput().get(0).getLLLampKmaxColor().equals("null") && !shortCircuitBoxModel.getOutput().get(0).getLLLampKmaxColor().contains("#fff")) {
                                binding.lll.setBackgroundColor(Color.parseColor(shortCircuitBoxModel.getOutput().get(0).getLLLampKmaxColor()));
                                binding.lll.setText(shortCircuitBoxModel.getOutput().get(0).getLLLampKmax());
                            } else {
                                binding.lll.setText(shortCircuitBoxModel.getOutput().get(0).getLLLampKmax());
                            }
                        }

                        if (shortCircuitBoxModel.getOutput().get(0).getLLGampKmax() != null && !shortCircuitBoxModel.getOutput().get(0).getLLGampKmax().isEmpty()) {
                            if (shortCircuitBoxModel.getOutput().get(0).getLLampKmaxColor() != null && !shortCircuitBoxModel.getOutput().get(0).getLLampKmaxColor().equals("null") && !shortCircuitBoxModel.getOutput().get(0).getLLampKmaxColor().contains("#fff")) {
                                binding.llg.setBackgroundColor(Color.parseColor(shortCircuitBoxModel.getOutput().get(0).getLLampKmaxColor()));
                                binding.llg.setText(shortCircuitBoxModel.getOutput().get(0).getLLGampKmax());
                            } else {
                                binding.llg.setText(shortCircuitBoxModel.getOutput().get(0).getLLGampKmax());
                            }
                        }

                        if (shortCircuitBoxModel.getOutput().get(0).getLLampKmax() != null && !shortCircuitBoxModel.getOutput().get(0).getLLampKmax().isEmpty()) {
                            if (shortCircuitBoxModel.getOutput().get(0).getLLampKmaxColor() != null && !shortCircuitBoxModel.getOutput().get(0).getLLampKmaxColor().equals("null") && !shortCircuitBoxModel.getOutput().get(0).getLLampKmaxColor().contains("#fff")) {
                                binding.ll.setBackgroundColor(Color.parseColor(shortCircuitBoxModel.getOutput().get(0).getLLampKmaxColor()));
                                binding.ll.setText(shortCircuitBoxModel.getOutput().get(0).getLLampKmax());
                            } else {
                                binding.ll.setText(shortCircuitBoxModel.getOutput().get(0).getLLampKmax());
                            }
                        }

                        if (shortCircuitBoxModel.getOutput().get(0).getLGampKmax() != null && !shortCircuitBoxModel.getOutput().get(0).getLGampKmax().isEmpty()) {
                            if (shortCircuitBoxModel.getOutput().get(0).getLGampKmaxColor() != null && !shortCircuitBoxModel.getOutput().get(0).getLGampKmaxColor().equals("null") && !shortCircuitBoxModel.getOutput().get(0).getLGampKmaxColor().contains("#fff")) {
                                binding.lg.setBackgroundColor(Color.parseColor(shortCircuitBoxModel.getOutput().get(0).getLGampKmaxColor()));
                                binding.lg.setText(shortCircuitBoxModel.getOutput().get(0).getLGampKmax());
                            } else {
                                binding.lg.setText(shortCircuitBoxModel.getOutput().get(0).getLGampKmax());
                            }
                        }

                        if (shortCircuitBoxModel.getOutput().get(0).getLGampKminZ() != null && !shortCircuitBoxModel.getOutput().get(0).getLGampKminZ().isEmpty()) {
                            if (shortCircuitBoxModel.getOutput().get(0).getLGampKminZColor() != null && !shortCircuitBoxModel.getOutput().get(0).getLGampKminZColor().equals("null") && !shortCircuitBoxModel.getOutput().get(0).getLGampKminZColor().contains("#fff")) {
                                binding.lgMin.setBackgroundColor(Color.parseColor(shortCircuitBoxModel.getOutput().get(0).getLGampKminZColor()));
                                binding.lgMin.setText(shortCircuitBoxModel.getOutput().get(0).getLGampKminZ());
                            } else {
                                binding.lgMin.setText(shortCircuitBoxModel.getOutput().get(0).getLGampKminZ());
                            }
                        }
                    } catch (Exception e) {
                        Log.d("Exception", e.getLocalizedMessage());;
                    }
                } else {
                    Snackbar snack = Snackbar.make(getActivity().findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ShortCircuitBoxModel> call, @NonNull Throwable t) {
                Snackbar snack = Snackbar.make(getActivity().findViewById(android.R.id.content), mainContext.getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                snack.show();
            }
        });
    }
}