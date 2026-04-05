package com.techlabs.apdcl.view.LayerInfo.LineInfo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
import com.techlabs.apdcl.databinding.UnbalanceinfoLayoutBinding;
import com.techlabs.apdcl.models.EquipmentModel;
import com.techlabs.apdcl.models.Line.Unbalanced;
import com.techlabs.apdcl.models.del.UpdateDeviceModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnbalanceMoreInfoDialog extends Dialog {

    private UnbalanceinfoLayoutBinding binding;
    private Context mainContext;
    private JsonObject jsonObject;
    private PrefManager prefManager;
    private String[] unBalanceIdList;
    private String currentLineId;
    private String networkId;
    private String[] statusList = {"Connected", "Disconnected"};

    public UnbalanceMoreInfoDialog(@NonNull Context context, String networkId, JsonObject jsonObject) {
        super(context);
        this.mainContext = context;
        this.jsonObject = jsonObject;
        this.networkId = networkId;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UnbalanceinfoLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View MainLayoutBackGround = Objects.requireNonNull(getWindow()).getDecorView().getRootView();
        MainLayoutBackGround.setBackground(getContext().getDrawable(R.drawable.pop_layout_background));

        prefManager = new PrefManager(mainContext);

        binding.btnLayout.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.nodesTvBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.nodesTvBtn.setTextColor(getContext().getColor(R.color.white));

        binding.unbalanceInfoLayout.setVisibility(View.VISIBLE);
        binding.editUnbalnceLayout.setVisibility(View.VISIBLE);
        binding.headerTitle.setText("Overhead Line UnBalance");

        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
            getUnbalancedInfo();
            getEquipment();
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
                    getUnbalancedInfo();
                    getEquipment();
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        binding.unbalanceTvBtn.setOnClickListener(view -> {
            binding.nodeInfoLayout.setVisibility(View.GONE);
            binding.unbalanceInfoLayout.setVisibility(View.VISIBLE);
            binding.unbalanceTvBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.unbalanceTvBtn.setTextColor(getContext().getColor(R.color.black));
            binding.nodesTvBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.nodesTvBtn.setTextColor(getContext().getColor(R.color.white));
            binding.headerTitle.setText("unbalance");
        });

        binding.nodesTvBtn.setOnClickListener(view -> {
            binding.unbalanceInfoLayout.setVisibility(View.GONE);
            binding.nodeInfoLayout.setVisibility(View.VISIBLE);
            binding.unbalanceTvBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.unbalanceTvBtn.setTextColor(getContext().getColor(R.color.white));
            binding.nodesTvBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.nodesTvBtn.setTextColor(getContext().getColor(R.color.black));
            binding.headerTitle.setText("Node");
        });

        binding.statusTv.setThreshold(0);
        binding.statusTv.setOnClickListener(v -> binding.statusTv.showDropDown());
        binding.unbalanceLineIdTv.setThreshold(0);
        binding.unbalanceLineIdTv.setOnClickListener(v -> binding.unbalanceLineIdTv.showDropDown());

        binding.okbtns.setOnClickListener(view -> checkDetails());

        binding.canclebtns.setOnClickListener(v -> dismiss());
    }

    private void getUnbalancedInfo() {
        binding.unbalanceInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.startShimmer();
        binding.shimmerView.setVisibility(View.VISIBLE);
        if (prefManager.getUserType() != null) {
            jsonObject.addProperty("UserType", prefManager.getUserType());
            jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<Unbalanced> call = apiInterface.getUnbalancedData("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<Unbalanced>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<Unbalanced> call, @NonNull Response<Unbalanced> response) {
                    binding.unbalanceInfoLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    if (response.code() == 200) {
                        try {
                            Unbalanced unbalanced = response.body();
                            assert unbalanced != null;
                            if (unbalanced.getOutput() != null) {
                                if (!unbalanced.getOutput().getSectionId().isEmpty() && unbalanced.getOutput().getSectionId() != null) {
                                    binding.sectionTv.setText(unbalanced.getOutput().getSectionId());
                                }

                                if (!unbalanced.getOutput().getPhase().toString().isEmpty() && !unbalanced.getOutput().getPhase().toString().equals("null") && unbalanced.getOutput().getPhase() != null) {
                                    if (unbalanced.getOutput().getPhase() == 1) {
                                        binding.aPhase.setChecked(true);
                                        binding.bPhase.setChecked(false);
                                        binding.cPhase.setChecked(false);
                                    } else if (unbalanced.getOutput().getPhase() == 2) {
                                        binding.aPhase.setChecked(false);
                                        binding.bPhase.setChecked(true);
                                        binding.cPhase.setChecked(false);
                                    } else if (unbalanced.getOutput().getPhase() == 3) {
                                        binding.aPhase.setChecked(false);
                                        binding.bPhase.setChecked(false);
                                        binding.cPhase.setChecked(true);
                                    } else if (unbalanced.getOutput().getPhase() == 4) {
                                        binding.aPhase.setChecked(true);
                                        binding.bPhase.setChecked(true);
                                        binding.cPhase.setChecked(false);
                                    } else if (unbalanced.getOutput().getPhase() == 5) {
                                        binding.aPhase.setChecked(true);
                                        binding.bPhase.setChecked(false);
                                        binding.cPhase.setChecked(true);
                                    } else if (unbalanced.getOutput().getPhase() == 6) {
                                        binding.aPhase.setChecked(false);
                                        binding.bPhase.setChecked(true);
                                        binding.cPhase.setChecked(true);
                                    } else {
                                        binding.aPhase.setChecked(true);
                                        binding.bPhase.setChecked(true);
                                        binding.cPhase.setChecked(true);
                                    }
                                }

                                if (unbalanced.getOutput().getZoneId() != null && !unbalanced.getOutput().getZoneId().toString().isEmpty()) {
                                    binding.zoneTv.setText(unbalanced.getOutput().getZoneId().toString());
                                }

                                if (!unbalanced.getOutput().getDeviceType().toString().isEmpty() && !unbalanced.getOutput().getDeviceType().toString().equals("null") && unbalanced.getOutput().getDeviceType() != null) {
                                    if (unbalanced.getOutput().getDeviceType().toString().equals("1")) {
                                        binding.deviceTypeTv.setText("Cable");
                                    } else if (unbalanced.getOutput().getDeviceType().toString().equals("2")) {
                                        binding.deviceTypeTv.setText("OverHead");
                                    } else if (unbalanced.getOutput().getDeviceType().toString().equals("23")) {
                                        binding.deviceTypeTv.setText("Unbalance");
                                    }
                                }

                                if (!unbalanced.getOutput().getDeviceNumber().isEmpty() && !unbalanced.getOutput().getDeviceNumber().equals("null") && unbalanced.getOutput().getDeviceNumber() != null) {
                                    binding.deviceNumberTv.setText(unbalanced.getOutput().getDeviceNumber());
                                }

                                /*if (!unbalanced.getOutput().getStatus().toString().isEmpty() && !unbalanced.getOutput().getStatus().toString().equals("null") && unbalanced.getOutput().getStatus() != null) {
                                    if (unbalanced.getOutput().getStatus().toString().equals("0")) {
                                        binding.statusTv.setText("Connected");
                                    } else if (unbalanced.getOutput().getStatus().toString().equals("1")) {
                                        binding.statusTv.setText("DisConnected");
                                    } else {
                                        binding.statusTv.setText("By Passed");
                                    }
                                }*/

                                /*ArrayAdapter<String> statusAdapters = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, statusList);
                                statusAdapters.setDropDownViewResource(R.layout.custom_spinner);
                                binding.statusTv.setAdapter(statusAdapters);
                                if (!unbalanced.getOutput().getStatus().toString().isEmpty() && !unbalanced.getOutput().getStatus().toString().equals("null") && unbalanced.getOutput().getStatus() != null) {
                                    binding.statusTv.setSelection(unbalanced.getOutput().getStatus() == 0 ? 0 : 1);
                                }*/

                                ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, statusList);
                                statusAdapter.setDropDownViewResource(R.layout.custom_spinner);
                                binding.statusTv.setAdapter(statusAdapter);
                                if (unbalanced.getOutput().getStatus() != null) {
                                    String status = unbalanced.getOutput().getStatus() == 0 ? "Connected" : "Disconnected";
                                    binding.statusTv.setText(status, false);
                                } else {
                                    binding.statusTv.setText("Connected", false);
                                }

                                if (!unbalanced.getOutput().getLength().toString().isEmpty() && !unbalanced.getOutput().getLength().toString().equals("null") && unbalanced.getOutput().getLength() != null) {
                                    binding.lengthTv.setText(unbalanced.getOutput().getLength().toString() + " " + "m");
                                }

                                if (!unbalanced.getOutput().getLineId().isEmpty() && !unbalanced.getOutput().getLineId().equals("null") && unbalanced.getOutput().getLineId() != null) {
                                    binding.unbalanceLineIdTv.setText(unbalanced.getOutput().getLineId());
                                }

                                /*if (!unbalanced.getOutput().getLineId().isEmpty() && !unbalanced.getOutput().getLineId().equals("null") && unbalanced.getOutput().getLineId() != null) {
                                    currentLineId = unbalanced.getOutput().getLineId();
                                    unBalanceIdList = new String[]{currentLineId};
                                    ArrayAdapter<String> adapters = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, unBalanceIdList);
                                    adapters.setDropDownViewResource(R.layout.custom_spinner);
                                    binding.unbalanceLineIdTv.setAdapter(adapters);
                                    int position = adapters.getPosition(currentLineId);
                                    if (position >= 0) {
                                        binding.unbalanceLineIdTv.setSelection(position);
                                    }
                                    adapters.notifyDataSetChanged();
                                } else {
                                    currentLineId = "Undefined";
                                    unBalanceIdList = new String[]{"Undefined"};
                                    ArrayAdapter<String> adapters = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, unBalanceIdList);
                                    adapters.setDropDownViewResource(R.layout.custom_spinner);
                                    binding.unbalanceLineIdTv.setAdapter(adapters);
                                    binding.unbalanceLineIdTv.setSelection(0);
                                }*/
                                if (unbalanced.getOutput().getLineId() != null && !unbalanced.getOutput().getLineId().isEmpty() && !unbalanced.getOutput().getLineId().equals("null")) {
                                    currentLineId = unbalanced.getOutput().getLineId();
                                    unBalanceIdList = new String[]{currentLineId};
                                } else {
                                    currentLineId = "Undefined";
                                    unBalanceIdList = new String[]{"Undefined"};
                                }
                                ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, unBalanceIdList);
                                idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                                binding.unbalanceLineIdTv.setAdapter(idAdapter);
                                binding.unbalanceLineIdTv.setText(currentLineId, false);
                                idAdapter.notifyDataSetChanged();

                                //Comment
//                                    if (!jsonObject.getString("PositiveSequenceResistance").isEmpty() && !jsonObject.getString("PositiveSequenceResistance").equals("null") && jsonObject.getString("PositiveSequenceResistance") != null){
//                                        OverHeadPositiveFirstTv.setText(jsonObject.getString("PositiveSequenceResistance")+" "+"R + jXΩ/km");
//                                    }else {
//                                        OverHeadPositiveFirstTv.setText("Not Available");
//                                    }
//
//                                    if (!jsonObject.getString("PositiveSequenceReactance").isEmpty() && !jsonObject.getString("PositiveSequenceReactance").equals("null") && jsonObject.getString("PositiveSequenceReactance") != null){
//                                        OverHeadPositiveSecondTv.setText(jsonObject.getString("PositiveSequenceReactance")+" "+"G + jBµS/km");
//                                    }else {
//                                        OverHeadPositiveSecondTv.setText("Not Available");
//                                    }
//
//                                    if (!jsonObject.getString("ZeroSequenceResistance").isEmpty() && !jsonObject.getString("ZeroSequenceResistance").equals("null") && jsonObject.getString("ZeroSequenceResistance") != null){
//                                        OverHeadZeroFirstTv.setText(jsonObject.getString("ZeroSequenceResistance")+" "+"R + jXΩ/km");
//                                    }else {
//                                        OverHeadZeroFirstTv.setText("Not Available");
//                                    }
//
//                                    if (!jsonObject.getString("ZeroSequenceReactance").isEmpty() && !jsonObject.getString("ZeroSequenceReactance").equals("null") && jsonObject.getString("ZeroSequenceReactance") != null){
//                                        OverHeadZeroSecondTv.setText(jsonObject.getString("ZeroSequenceReactance")+" "+"G + jBµS/km");
//                                    }else {
//                                        OverHeadZeroSecondTv.setText("Not Available");
//                                    }

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
                public void onFailure(@NonNull Call<Unbalanced> call, @NonNull Throwable t) {
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
        } else {
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "UserType null", Snackbar.LENGTH_INDEFINITE);
            snack.show();
        }

    }

    private void getEquipment() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("NetworkId", networkId);
        jsonObject.addProperty("Type", "Equipment");
        jsonObject.addProperty("Subtype", "UnBalance");
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
                        assert equipmentModel != null;
                        if (!equipmentModel.getAllEquipmentId().getEquipmentId().isEmpty()) {
                            unBalanceIdList = equipmentModel.getAllEquipmentId().getEquipmentId().toArray(new String[0]);
                            if (currentLineId != null && !currentLineId.equals("Undefined") && !Arrays.asList(unBalanceIdList).contains(currentLineId)) {
                                String[] tempList = new String[unBalanceIdList.length + 1];
                                System.arraycopy(unBalanceIdList, 0, tempList, 0, unBalanceIdList.length);
                                tempList[unBalanceIdList.length] = currentLineId;
                                unBalanceIdList = tempList;
                            }
                        } else {
                            unBalanceIdList = currentLineId != null ? new String[]{currentLineId} : new String[]{"Undefined"};
                        }
                        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, unBalanceIdList);
                        idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                        binding.unbalanceLineIdTv.setAdapter(idAdapter);
                        binding.unbalanceLineIdTv.setText(currentLineId != null ? currentLineId : "Undefined", false);
                        idAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (currentLineId != null) {
                        unBalanceIdList = new String[]{currentLineId};
                        ArrayAdapter<String> adapters = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, unBalanceIdList);
                        adapters.setDropDownViewResource(R.layout.custom_spinner);
                        binding.unbalanceLineIdTv.setAdapter(adapters);
                        binding.unbalanceLineIdTv.setSelection(0);
                        adapters.notifyDataSetChanged();
                    }
//                    @SuppressLint("InflateParams")
//                    View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
//                    TextView Ok = layout.findViewById(R.id.okBtn);
//                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//                    TextView header = layout.findViewById(R.id.headerTv);
//                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//                    TextView description = layout.findViewById(R.id.descripTv);
//                    header.setText(response.message() + " - " + response.code());
//                    description.setText(mainContext.getString(R.string.error_msg));
//                    Ok.setOnClickListener(v -> getEquipment());
//                    Toast toast = new Toast(mainContext);
//                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
//                    toast.setDuration(Toast.LENGTH_LONG);
//                    toast.setView(layout);
//                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EquipmentModel> call, @NonNull Throwable t) {
                if (currentLineId != null) {
                    unBalanceIdList = new String[]{currentLineId};
                    ArrayAdapter<String> adapters = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, unBalanceIdList);
                    adapters.setDropDownViewResource(R.layout.custom_spinner);
                    binding.unbalanceLineIdTv.setAdapter(adapters);
                    binding.unbalanceLineIdTv.setSelection(0);
                    adapters.notifyDataSetChanged();
                }
                @SuppressLint("InflateParams")
                View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView description = layout.findViewById(R.id.descripTv);
                header.setText(mainContext.getString(R.string.error));
                description.setText(mainContext.getString(R.string.error_msg));
                Ok.setOnClickListener(v -> getEquipment());
                Toast toast = new Toast(mainContext);
                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void checkDetails() {
        binding.deviceNumberTv.setError(null);
        binding.sectionTv.setError(null);
        binding.statusTv.setError(null);
        binding.unbalanceLineIdTv.setError(null);
        boolean isCancel = false;
        View focusView = null;

        if (binding.deviceNumberTv.getText().toString().trim().isEmpty()) {
            binding.deviceNumberTv.setError("Device Number cannot be empty!");
            focusView = binding.deviceNumberTv;
            isCancel = true;
        }

        if (binding.sectionTv.getText().toString().trim().isEmpty()) {
            binding.sectionTv.setError("Section ID cannot be empty!");
            focusView = binding.sectionTv;
            isCancel = true;
        }

        String status = "";
        if (binding.statusTv.getText().toString().trim().isEmpty()) {
            binding.statusTv.setError("Please select a status!");
            focusView = binding.statusTv;
            isCancel = true;
        } else {
            status = binding.statusTv.getText().toString().trim();
            ArrayAdapter<?> statusAdapter = (ArrayAdapter<?>) binding.statusTv.getAdapter();
            boolean isValidStatus = false;
            for (int i = 0; i < statusAdapter.getCount(); i++) {
                if (status.equals(statusAdapter.getItem(i).toString())) {
                    isValidStatus = true;
                    break;
                }
            }
            if (!isValidStatus) {
                binding.statusTv.setError("Invalid status selected!");
                focusView = binding.statusTv;
                isCancel = true;
            }
        }

        String id;
        if (binding.unbalanceLineIdTv.getText().toString().trim().isEmpty()) {
            id = "";
            binding.unbalanceLineIdTv.setError("Please select an ID!");
            focusView = binding.unbalanceLineIdTv;
            isCancel = true;
        } else {
            id = binding.unbalanceLineIdTv.getText().toString().trim();
            ArrayAdapter<?> idAdapter = (ArrayAdapter<?>) binding.unbalanceLineIdTv.getAdapter();
            boolean isValidId = false;
            for (int i = 0; i < idAdapter.getCount(); i++) {
                if (id.equals(idAdapter.getItem(i).toString())) {
                    isValidId = true;
                    break;
                }
            }
            if (!isValidId) {
                binding.unbalanceLineIdTv.setError("Invalid ID selected!");
                focusView = binding.unbalanceLineIdTv;
                isCancel = true;
            }
        }

        if (isCancel) {
            focusView.requestFocus();
        } else {
            String deviceNumber = binding.deviceNumberTv.getText().toString().trim();
            String statusCode = status.equals("Connected") ? "0" : "1";
            String deviceType = "2";
            String cymdbnet = prefManager.getDBName() != null ? prefManager.getDBName() : "";

            if (networkId == null) {
                showErrorToast("Error", "Network ID is missing.");
                return;
            }

            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mainContext)) {
                if (prefManager.getUserType() != null && prefManager.getUserType().contains("Edit")) {
                    updateDevice(networkId, deviceType, deviceNumber, id, statusCode, cymdbnet);
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
                            updateDevice(networkId, deviceType, deviceNumber, id, statusCode, cymdbnet);
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

    private void updateDevice(String networkId, String deviceType, String deviceNumber, String id, String status, String cymdbnet) {
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
}


