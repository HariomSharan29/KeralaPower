package com.techlabs.apdcl.view.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.databinding.LoadflowLayoutstatusBinding;
import com.techlabs.apdcl.models.nsc.NewConnectionModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;
import com.techlabs.apdcl.view.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadFlowStatus extends BottomSheetDialogFragment {
    private LoadflowLayoutstatusBinding binding;
    private Context mContext;
    private PrefManager prefManager;
    private List<String> networkID = new ArrayList<>();

    public LoadFlowStatus(Context mContext, ArrayList<String> networkId) {
        this.mContext = mContext;
        this.networkID = networkId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LoadflowLayoutstatusBinding.inflate(inflater, container, false);
        prefManager = new PrefManager(mContext);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mContext)) {
            getStatus();
        } else {
            Snackbar.make(binding.getRoot(), "No Internet Connection", Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    public void dismiss() {
        super.dismiss();
        binding = null;
    }

    private void getStatus() {
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserType", prefManager.getType());
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        jsonObject.addProperty("DatabaseType", "NewConnection");
        Call<NewConnectionModel> call = apiInterface.getNewConnectionData("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<NewConnectionModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<NewConnectionModel> call, @NonNull Response<NewConnectionModel> response) {
                if (response.code() == 200) {
                    NewConnectionModel newConnectionModel = response.body();
                    assert newConnectionModel != null;
                    if (newConnectionModel.getOutput() != null && !newConnectionModel.getOutput().isEmpty()) {
                        for (int i = 0; i < newConnectionModel.getOutput().size(); i++) {
                            if (newConnectionModel.getOutput().get(i).getFeederid().contains(networkID.get(0))) {

                                if (newConnectionModel.getOutput().get(i).getApplicationID() != null && !newConnectionModel.getOutput().get(i).getApplicationID().toString().isEmpty()) {
                                    binding.applicationID.setText(newConnectionModel.getOutput().get(i).getApplicationID().toString());
                                }

                                if (newConnectionModel.getOutput().get(i).getDtgisid() != null && !newConnectionModel.getOutput().get(i).getDtgisid().isEmpty()) {
                                    binding.dtId.setText(newConnectionModel.getOutput().get(i).getDtgisid());
                                }

                                if (newConnectionModel.getOutput().get(i).getBeforePercentageVR() != null && !newConnectionModel.getOutput().get(i).getBeforePercentageVR().toString().isEmpty()) {
                                    binding.beforeVrPercentage.setText(newConnectionModel.getOutput().get(i).getBeforePercentageVR().toString());
                                }

                                if (newConnectionModel.getOutput().get(i).getAfterPercentageVR() != null && !newConnectionModel.getOutput().get(i).getAfterPercentageVR().toString().isEmpty()) {
                                    binding.afterVrPercentage.setText(newConnectionModel.getOutput().get(i).getAfterPercentageVR().toString());
                                }

                                if (newConnectionModel.getOutput().get(i).getBeforeDTLoading() != null && !newConnectionModel.getOutput().get(i).getBeforeDTLoading().toString().isEmpty()) {
                                    binding.beforeDtLoading.setText(newConnectionModel.getOutput().get(i).getBeforeDTLoading().toString());
                                }

                                if (newConnectionModel.getOutput().get(i).getAfterDTLoading() != null && !newConnectionModel.getOutput().get(i).getAfterDTLoading().toString().isEmpty()) {
                                    binding.afterDtLoading.setText(newConnectionModel.getOutput().get(i).getAfterDTLoading().toString());
                                }

                                if (newConnectionModel.getOutput().get(i).getFeasibility() != null && !newConnectionModel.getOutput().get(i).getFeasibility().isEmpty()) {
                                    binding.fesibility.setText(newConnectionModel.getOutput().get(i).getFeasibility());
                                }

                            }
                        }
                    }

                } else if (response.code() == 401) {
                    prefManager.setIsUserLogin(false);
                    startActivity(new Intent(mContext, LoginActivity.class));
                    getActivity().finish();
                } else {
                    @SuppressLint("InflateParams")
                    View layout = LayoutInflater.from(getActivity()).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView description = layout.findViewById(R.id.descripTv);
                    header.setText(response.message() + " - " + response.code());
                    description.setText(getString(R.string.error_msg));
                    Ok.setOnClickListener(v -> {
                        getStatus();
                    });
                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewConnectionModel> call, @NonNull Throwable t) {
                @SuppressLint("InflateParams") View layout = LayoutInflater.from(getActivity()).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView description = layout.findViewById(R.id.descripTv);
                header.setText(getString(R.string.error));
                description.setText(getString(R.string.error_msg));
                Ok.setOnClickListener(v -> {
                    getStatus();
                });
                Toast toast = new Toast(getActivity());
                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
    }
}


