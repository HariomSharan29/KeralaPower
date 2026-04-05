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
import com.techlabs.apdcl.models.Line.Overhead;
import com.techlabs.apdcl.models.del.UpdateDeviceModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OverHeadMoreInfoDialog extends Dialog {

    private CableInfoDialogLayoutBinding binding;
    private Context mainContext;
    private JsonObject jsonObject;
    private PrefManager prefManager;
    private String[] overHeadIdList;
    private String currentLineId;
    private String networkId;
    private String[] statusList = {"Connected", "Disconnected"};
    private String phase;
    private OverheadSnippet snippet;

    public OverHeadMoreInfoDialog(@NonNull Context context, String networkId, JsonObject jsonObject, OverheadSnippet snippet) {
        super(context);
        this.mainContext = context;
        this.jsonObject = jsonObject;
        this.networkId = networkId;
        this.snippet = snippet;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CableInfoDialogLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View MainLayoutBackGround = Objects.requireNonNull(getWindow()).getDecorView().getRootView();
        MainLayoutBackGround.setBackground(getContext().getDrawable(R.drawable.pop_layout_background));
        prefManager = new PrefManager(mainContext);

        binding.cableInfoLayout.setVisibility(View.GONE);
        binding.overheadInfoLayout.setVisibility(View.VISIBLE);
        binding.btnLayout.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.cableBtn.setText("OverHead");
        binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
        binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
        binding.headerTitle.setText("Overhead Line Balanced");
        binding.editBtnLayout.setVisibility(View.GONE);

        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.overheadStatus.setThreshold(0);
        binding.overheadStatus.setOnClickListener(v -> binding.overheadStatus.showDropDown());
        binding.overHeadLineId.setThreshold(0);
        binding.overHeadLineId.setOnClickListener(v -> binding.overHeadLineId.showDropDown());

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
            getOverheadInfo();
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
                        getOverheadInfo();
                        if (prefManager.getUserType().contains("Edit")) {
                            getEquipment();
                        }
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        binding.cableBtn.setOnClickListener(view -> {
            binding.nodeInfoLayout.setVisibility(View.GONE);
            binding.overheadInfoLayout.setVisibility(View.VISIBLE);
            binding.cableBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
            binding.cableBtn.setTextColor(getContext().getColor(R.color.black));
            binding.nodeBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            binding.nodeBtn.setTextColor(getContext().getColor(R.color.white));
            binding.headerTitle.setText("Overhead Line Balanced");
        });

        binding.nodeBtn.setOnClickListener(view -> {
            binding.overheadInfoLayout.setVisibility(View.GONE);
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

        binding.okbtns.setOnClickListener(view -> checkDetails());

        binding.canclebtns.setOnClickListener(v -> dismiss());

    }

    private void getOverheadInfo() {
        binding.overheadInfoLayout.setVisibility(View.GONE);
        binding.shimmerView.startShimmer();
        binding.shimmerView.setVisibility(View.VISIBLE);
        jsonObject.addProperty("UserType", prefManager.getUserType());
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<Overhead> call = apiInterface.getOverheadData("Bearer " + prefManager.getAccessToken(), jsonObject);
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
                            if (overhead.getOutput().getSectionId() != null && !overhead.getOutput().getSectionId().isEmpty() && !overhead.getOutput().getSectionId().equals("null")) {
                                binding.sectionIdEdt.setText(overhead.getOutput().getSectionId());
                            } else {
                                binding.sectionIdEdt.setText("UNDEFINED");
                            }

                            if (overhead.getOutput().getPhase() != null) {
                                if (overhead.getOutput().getPhase() == 1) {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(false);
                                    binding.cChkBox.setChecked(false);
                                } else if (overhead.getOutput().getPhase() == 2) {
                                    binding.aChkBox.setChecked(false);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(false);
                                } else if (overhead.getOutput().getPhase() == 3) {
                                    binding.aChkBox.setChecked(false);
                                    binding.bChkBox.setChecked(false);
                                    binding.cChkBox.setChecked(true);
                                } else if (overhead.getOutput().getPhase() == 4) {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(false);
                                } else if (overhead.getOutput().getPhase() == 5) {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(false);
                                    binding.cChkBox.setChecked(true);
                                } else if (overhead.getOutput().getPhase() == 6) {
                                    binding.aChkBox.setChecked(false);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(true);
                                } else {
                                    binding.aChkBox.setChecked(true);
                                    binding.bChkBox.setChecked(true);
                                    binding.cChkBox.setChecked(true);
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

                            if (overhead.getOutput().getDeviceNumber() != null && !overhead.getOutput().getDeviceNumber().isEmpty() && !overhead.getOutput().getDeviceNumber().equals("null")) {
                                binding.overheadNumberTv.setText(overhead.getOutput().getDeviceNumber());
                            } else {
                                binding.overheadNumberTv.setText("UNDEFINED");
                            }

                                ArrayAdapter<String> statusAdapters = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, statusList);
                                statusAdapters.setDropDownViewResource(R.layout.custom_spinner);
                                binding.overheadStatus.setAdapter(statusAdapters);
                                if (overhead.getOutput().getStatus() != null) {
                                    binding.overheadStatus.setSelection(overhead.getOutput().getStatus() == 0 ? 0 : 1);
                                }

                                if (overhead.getOutput().getLength() != null) {
                                    binding.overheadLengthTv.setText(String.format("%.2f m", overhead.getOutput().getLength()));
                                } else {
                                    binding.overheadLengthTv.setText("UNDEFINED");
                                }

                            ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, statusList);
                            statusAdapter.setDropDownViewResource(R.layout.custom_spinner);
                            binding.overheadStatus.setAdapter(statusAdapter);
                            if (overhead.getOutput().getStatus() != null) {
                                String status = overhead.getOutput().getStatus() == 0 ? "Connected" : "Disconnected";
                                binding.overheadStatus.setText(status, false);
                            } else {
                                binding.overheadStatus.setText("Connected", false);
                            }

                                /*if (overhead.getOutput().getLineId() != null && !overhead.getOutput().getLineId().isEmpty() && !overhead.getOutput().getLineId().equals("null")) {
                                    currentLineId = overhead.getOutput().getLineId();
                                    overHeadIdList = new String[]{currentLineId};
                                    ArrayAdapter<String> adapters = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, overHeadIdList);
                                    adapters.setDropDownViewResource(R.layout.custom_spinner);
                                    binding.overHeadLineId.setAdapter(adapters);
                                    int position = adapters.getPosition(currentLineId);
                                    if (position >= 0) {
                                        binding.overHeadLineId.setSelection(position);
                                    }
                                    adapters.notifyDataSetChanged();
                                } else {
                                    currentLineId = "Undefined";
                                    overHeadIdList = new String[]{"Undefined"};
                                    ArrayAdapter<String> adapters = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, overHeadIdList);
                                    adapters.setDropDownViewResource(R.layout.custom_spinner);
                                    binding.overHeadLineId.setAdapter(adapters);
                                    binding.overHeadLineId.setSelection(0);
                                }*/

                            if (overhead.getOutput().getLineId() != null && !overhead.getOutput().getLineId().isEmpty() && !overhead.getOutput().getLineId().equals("null")) {
                                currentLineId = overhead.getOutput().getLineId();
                                overHeadIdList = new String[]{currentLineId};
                            } else {
                                currentLineId = "Undefined";
                                overHeadIdList = new String[]{"Undefined"};
                            }
                            ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, overHeadIdList);
                            idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                            binding.overHeadLineId.setAdapter(idAdapter);
                            binding.overHeadLineId.setText(currentLineId, false);
                            idAdapter.notifyDataSetChanged();

                            if (overhead.getOutput().getNominalRating() != null && !overhead.getOutput().getNominalRating().toString().isEmpty() && !overhead.getOutput().getNominalRating().equals("null")) {
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

                            if (overhead.getOutput().getFromNodeId() != null && !overhead.getOutput().getFromNodeId().isEmpty() && !overhead.getOutput().getFromNodeId().equals("null")) {
                                binding.idFromNodesTv.setText(overhead.getOutput().getFromNodeId());
                            }

                            if (overhead.getOutput().getFromNodeX() != null) {
                                binding.xFromNodesTv.setText(overhead.getOutput().getFromNodeX().toString());
                            }

                            if (overhead.getOutput().getFromNodeY() != null) {
                                binding.yFromNodesTv.setText(overhead.getOutput().getFromNodeY().toString());
                            }

                            if (overhead.getOutput().getToNodeId() != null && !overhead.getOutput().getToNodeId().isEmpty() && !overhead.getOutput().getToNodeId().equals("null")) {
                                binding.idToNodeTv.setText(overhead.getOutput().getToNodeId());
                            }

                            if (overhead.getOutput().getToNodeX() != null) {
                                binding.xToNodeTv.setText(overhead.getOutput().getToNodeX().toString());
                            }

                            if (overhead.getOutput().getToNodeY() != null) {
                                binding.yToNodesTv.setText(overhead.getOutput().getToNodeY().toString());
                            }

                            if (overhead.getOutput().getFromNodeId() != null || overhead.getOutput().getFromNodeId() != null) {
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
            public void onFailure(@NonNull Call<Overhead> call, @NonNull Throwable t) {
                binding.cableInfoLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
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

    private void getEquipment() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("NetworkId", networkId);
        jsonObject.addProperty("Type", "Equipment");
        jsonObject.addProperty("Subtype", "Overhead");
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
                            return;
                        }
                        if (equipmentModel.getAllEquipmentId() != null
                                && equipmentModel.getAllEquipmentId().getEquipmentId() != null
                                && !equipmentModel.getAllEquipmentId().getEquipmentId().isEmpty()) {
                            overHeadIdList = equipmentModel.getAllEquipmentId().getEquipmentId().toArray(new String[0]);
                            if (currentLineId != null && !currentLineId.equals("Undefined") && !Arrays.asList(overHeadIdList).contains(currentLineId)) {
                                String[] tempList = new String[overHeadIdList.length + 1];
                                System.arraycopy(overHeadIdList, 0, tempList, 0, overHeadIdList.length);
                                tempList[overHeadIdList.length] = currentLineId;
                                overHeadIdList = tempList;
                            }
                        } else {
                            overHeadIdList = currentLineId != null ? new String[]{currentLineId} : new String[]{"Undefined"};
                        }
                        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, overHeadIdList);
                        idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                        binding.overHeadLineId.setAdapter(idAdapter);
                        binding.overHeadLineId.setText(currentLineId != null ? currentLineId : "Undefined", false);
                        idAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        overHeadIdList = currentLineId != null ? new String[]{currentLineId} : new String[]{"Undefined"};
                        ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, overHeadIdList);
                        idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                        binding.overHeadLineId.setAdapter(idAdapter);
                        binding.overHeadLineId.setText(currentLineId != null ? currentLineId : "Undefined", false);
                        idAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (currentLineId != null) {
                        overHeadIdList = new String[]{currentLineId};
                        ArrayAdapter<String> adapters = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, overHeadIdList);
                        adapters.setDropDownViewResource(R.layout.custom_spinner);
                        binding.overHeadLineId.setAdapter(adapters);
                        binding.overHeadLineId.setText(currentLineId, false);
                        adapters.notifyDataSetChanged();
                    }
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
                Log.e("OVERHEAD_FAILURE", "onFailure: " + t.getMessage(), t);
                // Gson parsing error - silently fallback karo currentLineId se
                overHeadIdList = currentLineId != null ? new String[]{currentLineId} : new String[]{"Undefined"};
                ArrayAdapter<String> idAdapter = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, overHeadIdList);
                idAdapter.setDropDownViewResource(R.layout.custom_spinner);
                binding.overHeadLineId.setAdapter(idAdapter);
                binding.overHeadLineId.setText(currentLineId != null ? currentLineId : "Undefined", false);
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
//                Ok.setOnClickListener(v -> getEquipment());
//                Toast toast = new Toast(mainContext);
//                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
//                toast.setDuration(Toast.LENGTH_LONG);
//                toast.setView(layout);
//                toast.show();
            }
        });
    }

    private void checkDetails() {
        binding.overheadNumberTv.setError(null);
        binding.sectionIdEdt.setError(null);
        binding.overheadStatus.setError(null);
        binding.overHeadLineId.setError(null);
        boolean isCancel = false;
        View focusView = null;

        if (binding.overheadNumberTv.getText().toString().trim().isEmpty()) {
            binding.overheadNumberTv.setError("Device Number cannot be empty!");
            focusView = binding.overheadNumberTv;
            isCancel = true;
        }

        if (binding.sectionIdEdt.getText().toString().trim().isEmpty()) {
            binding.sectionIdEdt.setError("Section ID cannot be empty!");
            focusView = binding.sectionIdEdt;
            isCancel = true;
        }

        String status = "";
        if (binding.overheadStatus.getText().toString().trim().isEmpty()) {
            binding.overheadStatus.setError("Please select a status!");
            focusView = binding.overheadStatus;
            isCancel = true;
        } else {
            status = binding.overheadStatus.getText().toString().trim();
            ArrayAdapter<?> statusAdapter = (ArrayAdapter<?>) binding.overheadStatus.getAdapter();
            boolean isValidStatus = false;
            for (int i = 0; i < statusAdapter.getCount(); i++) {
                if (status.equals(statusAdapter.getItem(i).toString())) {
                    isValidStatus = true;
                    break;
                }
            }
            if (!isValidStatus) {
                binding.overheadStatus.setError("Invalid status selected!");
                focusView = binding.overheadStatus;
                isCancel = true;
            }
        }

        String id;
        if (binding.overHeadLineId.getText().toString().trim().isEmpty()) {
            id = "";
            binding.overHeadLineId.setError("Please select an ID!");
            focusView = binding.overHeadLineId;
            isCancel = true;
        } else {
            id = binding.overHeadLineId.getText().toString().trim();
            ArrayAdapter<?> idAdapter = (ArrayAdapter<?>) binding.overHeadLineId.getAdapter();
            boolean isValidId = false;
            for (int i = 0; i < idAdapter.getCount(); i++) {
                if (id.equals(idAdapter.getItem(i).toString())) {
                    isValidId = true;
                    break;
                }
            }
            if (!isValidId) {
                binding.overHeadLineId.setError("Invalid ID selected!");
                focusView = binding.overHeadLineId;
                isCancel = true;
            }
        }

        if (isCancel) {
            focusView.requestFocus();
        } else {
            String deviceNumber = binding.overheadNumberTv.getText().toString().trim();
            String statusCode = status.equals("Connected") ? "0" : "1";
            String deviceType = "2";
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

    private void updateDevice(String networkId, String deviceType, String deviceNumber, String id, String status, String cymdbnet, OverheadSnippet snippet) {
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
                        if (snippet != null) {
                            snippet.dismiss();
                        }
                        dismiss();
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
