package com.techlabs.apdcl.view.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.databinding.FragmentSourceNetworkBinding;
import com.techlabs.apdcl.models.device.DashboardModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourceNetworkFragment extends Fragment {
    private FragmentSourceNetworkBinding binding;
    private String[] networkTypeList = {"UNDEFINED"};
    private PrefManager prefManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSourceNetworkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View MainLayoutBackGround = getActivity().getWindow().getDecorView().getRootView();
        MainLayoutBackGround.setBackground(getContext().getDrawable(R.drawable.pop_layout_background));
        prefManager = new PrefManager(getActivity());
        Bundle bundle = this.getArguments();

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getActivity())) {
            if (bundle.getString("NodeId") != null) {
                getNetworkInfo(bundle.getString("NodeId"));
            }
        } else {
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.no_internet_dialog);
            dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.pop_background));
            LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
            Button RetryBtn = dialog.findViewById(R.id.btnDialog);
            lottieAnimationView.playAnimation();
            RetryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getActivity())) {
                        getNetworkInfo(bundle.getString("NodeId"));
                        dialog.dismiss();
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        if (bundle.getString("NetworkId") != null) {
            binding.networkName.setText(bundle.getString("NetworkId"));
        }

        ArrayAdapter<String> networkTypeAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner, networkTypeList);
        binding.networkTypeSpinner.setAdapter(networkTypeAdapter);

    }

    private void getNetworkInfo(String nodeId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("DeviceNumber", nodeId);
        jsonObject.addProperty("DeviceType", "43");
        jsonObject.addProperty("UserType", prefManager.getUserType());
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<DashboardModel> call = apiInterface.getSourceData("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<DashboardModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<DashboardModel> call, @NonNull Response<DashboardModel> response) {
                if (response.code() == 200){
                    DashboardModel source = response.body();
                    assert source != null;
                    if (source.getOutput().getNetworkId() != null && !source.getOutput().getNetworkId().isBlank()){
                        binding.networkName.setText(source.getOutput().getNetworkId());
                    }else {
                        binding.networkName.setText(getString(R.string.undefined));
                    }

                    if (source.getOutput().getNetworkType() != null && !source.getOutput().getNetworkType().isBlank()){
                        if (source.getOutput().getNetworkType().equals("0")){
                            binding.networkTypeSpinner.getItemAtPosition(0);
                        }else {
                            binding.networkTypeSpinner.getItemAtPosition(1);
                        }
                    }else {
                        binding.networkTypeSpinner.getItemAtPosition(2);
                    }

                    if (source.getOutput().getGroup1() != null && !source.getOutput().getGroup1().isBlank()){
                        binding.areaSpinner.setText(source.getOutput().getGroup1());
                    }else {
                        binding.areaSpinner.setText("");
                    }

                    if (source.getOutput().getGroup2() != null && !source.getOutput().getGroup2().isBlank()){
                        binding.voltageLevelSpinner.setText(source.getOutput().getGroup2());
                    }else {
                        binding.voltageLevelSpinner.setText("");
                    }

                    if (source.getOutput().getGroup3() != null && !source.getOutput().getGroup3().isBlank()){
                        binding.regionSpinner.setText(source.getOutput().getGroup3());
                    }else {
                        binding.regionSpinner.setText("");
                    }

                }else {
                    @SuppressLint("InflateParams")
                    View layout = LayoutInflater.from(getActivity()).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
                    header.setText(response.message() + " - " + response.code());
                    description.setText(getString(R.string.error_msg));
                    Ok.setOnClickListener(v -> {
                        getNetworkInfo(nodeId);
                    });
                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DashboardModel> call, @NonNull Throwable t) {
                View layout = LayoutInflater.from(getActivity()).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView description = layout.findViewById(R.id.descripTv);
                header.setText(getActivity().getString(R.string.error));
                description.setText(getString(R.string.error_msg));
                Ok.setOnClickListener(v -> {
                    getNetworkInfo(nodeId);
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