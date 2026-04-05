package com.techlabs.apdcl.view.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.treelib.TreeNode;
import com.example.treelib.TreeView;
import com.example.treelib.helper.base.BaseNodeViewBinder;
import com.example.treelib.helper.base.BaseNodeViewFactory;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.LoadAllocationArgument;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.databinding.FragmentLoadAllocationBinding;
import com.techlabs.apdcl.models.SelectedFeedersModel;
import com.techlabs.apdcl.models.analysis.AnalysisInformationModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadAllocation extends BottomSheetDialogFragment {

    private FragmentLoadAllocationBinding binding;
    private Context mainContext;
    private BottomSheetDialog dialog;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private PrefManager prefManager;
    private List<String> networksList = new ArrayList<>();
    private String networks[];
    private List<String> metersList = new ArrayList<>();
    private String[] meters;
    private LoadAllocationArgument loadAllocationArgument;

    public LoadAllocation(Context context) {this.mainContext = context;}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            loadAllocationArgument = (LoadAllocationArgument) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity() + " must implement BottomSheetListener");
        }
        binding = FragmentLoadAllocationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefManager = new PrefManager(mainContext);

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mainContext)) {
            getSelectedFeeder();
        } else {
            final Dialog dialog = new Dialog(mainContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.no_internet_dialog);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(mainContext.getDrawable(R.drawable.pop_background));
            LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
            Button RetryBtn = dialog.findViewById(R.id.btnDialog);
            lottieAnimationView.playAnimation();
            RetryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mainContext)) {
                        getSelectedFeeder();
                        dialog.dismiss();
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        CoordinatorLayout layout = dialog.findViewById(R.id.bottomSheetLayout);
        assert layout != null;
//        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HALF_EXPANDED + 5) {

                } else if (newState == BottomSheetBehavior.PEEK_HEIGHT_AUTO) {

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mainContext)) {
            getDownStreamInformation();
        } else {
            final Dialog dialog = new Dialog(mainContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.no_internet_dialog);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(mainContext.getDrawable(R.drawable.pop_background));
            LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
            Button RetryBtn = dialog.findViewById(R.id.btnDialog);
            lottieAnimationView.playAnimation();
            RetryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mainContext)) {
                        getDownStreamInformation();
                        dialog.dismiss();
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        binding.okBtn.setOnClickListener(v -> {
            SendLoadAllocationArguments();
        });

        binding.cancelBtn.setOnClickListener(v -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("LoadAllocation", false);
            if (loadAllocationArgument != null) {
                loadAllocationArgument.onLoadAllocationArgReceived(jsonObject);
            }
            dismiss();
        });

        ArrayAdapter<CharSequence> allocationMethod = ArrayAdapter.createFromResource(
                requireContext(), R.array.allocation_method, R.layout.item_spinner_compact);
        allocationMethod.setDropDownViewResource(R.layout.item_spinner_dropdown_compact);
        binding.allocationMethod.setAdapter(allocationMethod);

        ArrayAdapter<CharSequence> actualLoad = ArrayAdapter.createFromResource(
                requireContext(), R.array.actual_load, R.layout.item_spinner_compact);
        actualLoad.setDropDownViewResource(R.layout.item_spinner_dropdown_compact);
        binding.actualLoad.setAdapter(actualLoad);

        ArrayAdapter<CharSequence> type = ArrayAdapter.createFromResource(
                requireContext(), R.array.type, R.layout.item_spinner_compact);
        type.setDropDownViewResource(R.layout.item_spinner_dropdown_compact);
        binding.type.setAdapter(type);

        ArrayAdapter<CharSequence> loadFlowParameter = ArrayAdapter.createFromResource(
                requireContext(), R.array.defaults, R.layout.item_spinner_compact);
        loadFlowParameter.setDropDownViewResource(R.layout.item_spinner_dropdown_compact);
        binding.loadFlowParameter.setAdapter(loadFlowParameter);

    }

    private void SendLoadAllocationArguments() {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new Gson().toJsonTree(networksList).getAsJsonArray();
        jsonObject.addProperty("Username", prefManager.getUserName());
        jsonObject.add("NetworkId", jsonArray);
        jsonObject.addProperty("Method", "0");
        jsonObject.addProperty("LoadValueType", "0");
        jsonObject.addProperty("IsTotalDemand", "0");
        jsonObject.addProperty("DemandAValue1", "0");
        jsonObject.addProperty("DemandAValue2", "0");
        jsonObject.addProperty("DemandBValue1", "0");
        jsonObject.addProperty("DemandBValue2", "0");
        jsonObject.addProperty("DemandCValue1", "0");
        jsonObject.addProperty("DemandCValue2", "0");
        jsonObject.addProperty("DemandTotalValue1", "0");
        jsonObject.addProperty("DemandTotalValue2", "0");
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());

        if (loadAllocationArgument != null) {
            loadAllocationArgument.onLoadAllocationArgReceived(jsonObject);
        }
        dismiss();
    }

    private void getDownStreamInformation() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        JsonObject jsonObject = new JsonObject();
        JsonArray netArr = new Gson().toJsonTree(bundle.get("Network")).getAsJsonArray();
        if (netArr.size() == 1) {
            jsonObject.addProperty("NetworkId", netArr.get(0).getAsString());
        } else {
            jsonObject.add("NetworkId", netArr);
        }
        jsonObject.addProperty("Username", prefManager.getUserName());
        jsonObject.addProperty("AnalysisType", "LoadAllocation");
        jsonObject.addProperty("InforamtionName", "DownstreamInformation");
        jsonObject.addProperty("InformationType", "ConnectedKVA");
        jsonObject.addProperty("NodeId", "TOPO_HEADNODE_ID_000005");
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<AnalysisInformationModel> call = apiInterface.AnalysisInformatio("Bearer " + prefManager.getAccessToken(),jsonObject);
        call.enqueue(new Callback<AnalysisInformationModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<AnalysisInformationModel> call, @NonNull Response<AnalysisInformationModel> response) {
                if (response.code() == 200) {
                    AnalysisInformationModel analysisInformationModel = response.body();

                    assert analysisInformationModel != null;
                    if (analysisInformationModel.getOutput().getA() != null) {
                        binding.dSA.setText(analysisInformationModel.getOutput().getA());
                    }

                    if (analysisInformationModel.getOutput().getB() != null) {
                        binding.dSB.setText(analysisInformationModel.getOutput().getB());
                    }

                    if (analysisInformationModel.getOutput().getC() != null) {
                        binding.dSC.setText(analysisInformationModel.getOutput().getC());
                    }

                    if (analysisInformationModel.getOutput().getTotal() != null) {
                        binding.dSTotal.setText(analysisInformationModel.getOutput().getTotal());
                    }

                } else {
                    @SuppressLint("InflateParams") View layout = LayoutInflater.from(getActivity()).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
                    header.setText(response.message() + " - " + response.code());
                    description.setText(getString(R.string.error_msg));
                    Ok.setOnClickListener(v -> {
                        getDownStreamInformation();
                    });
                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AnalysisInformationModel> call, @NonNull Throwable t) {
                @SuppressLint("InflateParams") View layout = LayoutInflater.from(getActivity()).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
                header.setText(requireActivity().getString(R.string.error));
                description.setText(getString(R.string.error_msg));
                Ok.setOnClickListener(v -> {
                    getDownStreamInformation();
                });
                Toast toast = new Toast(getActivity());
                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
    }

    private void getSelectedFeeder() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        if (bundle.get("Network") != null) {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new Gson().toJsonTree(bundle.get("Network")).getAsJsonArray();
            jsonObject.add("NetworkId", jsonArray);
            jsonObject.addProperty("UserType", prefManager.getUserType());
            jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            String AccessToken = prefManager.getAccessToken();
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<SelectedFeedersModel> call = apiInterface.getSelectedFeeder("Bearer " + AccessToken, jsonObject);
            call.enqueue(new Callback<SelectedFeedersModel>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<SelectedFeedersModel> call, @NonNull Response<SelectedFeedersModel> response) {
                    if (response.code() == 200) {
                        SelectedFeedersModel selectedFeedersModel = response.body();
                        assert selectedFeedersModel != null;
                        /*if (selectedFeedersModel.getResult() != null) {
                            List<SelectedFeedersModel.NetworkId> networkIdList = selectedFeedersModel.getResult().getNetworkId();
                            if (networkIdList != null && !networkIdList.isEmpty()) {
                                for (SelectedFeedersModel.NetworkId network : networkIdList) {
                                    if (network.getNetworkId() != null) {
                                        networksList.add(network.getNetworkId());
                                    }
                                }
                            }
                            List<SelectedFeedersModel.MeterDeviceNumber> meterList = selectedFeedersModel.getResult().getMeterDeviceNumber();
                            if (meterList != null && !meterList.isEmpty()) {
                                for (SelectedFeedersModel.MeterDeviceNumber meter : meterList) {
                                    if (meter.getMeterDeviceNumber() != null) {
                                        metersList.add(String.valueOf(meter.getMeterDeviceNumber()));

                                    }
                                }
                            }
                        }*/
                        if (selectedFeedersModel.getResult() != null) {
                            List<String> group3List = new ArrayList<>();
                            List<String> group2List = new ArrayList<>();
                            List<String> group1List = new ArrayList<>();
                            List<String> networkIdList = new ArrayList<>();

                            if (selectedFeedersModel.getResult().getGroup3() != null && !selectedFeedersModel.getResult().getGroup3().isEmpty()) {
                                for (SelectedFeedersModel.Group3 group3 : selectedFeedersModel.getResult().getGroup3()) {
                                    group3List.add(group3.getGroup3());
                                }
                            }
                            if (selectedFeedersModel.getResult().getGroup2() != null && !selectedFeedersModel.getResult().getGroup2().isEmpty()) {
                                for (SelectedFeedersModel.Group2 group2 : selectedFeedersModel.getResult().getGroup2()) {
                                    group2List.add(group2.getGroup2());
                                }
                            }
                            if (selectedFeedersModel.getResult().getGroup1() != null && !selectedFeedersModel.getResult().getGroup1().isEmpty()) {
                                for (SelectedFeedersModel.Group1 group1 : selectedFeedersModel.getResult().getGroup1()) {
                                    group1List.add(group1.getGroup1());
                                }
                            }
                            if (selectedFeedersModel.getResult().getNetworkId() != null && !selectedFeedersModel.getResult().getNetworkId().isEmpty()) {
                                for (SelectedFeedersModel.NetworkId network : selectedFeedersModel.getResult().getNetworkId()) {
                                    networkIdList.add(network.getNetworkId());
                                    networksList.add(network.getNetworkId());
                                }
                            }
                            List<SelectedFeedersModel.MeterDeviceNumber> meterList = selectedFeedersModel.getResult().getMeterDeviceNumber();
                            if (meterList != null && !meterList.isEmpty()) {
                                for (SelectedFeedersModel.MeterDeviceNumber meter : meterList) {
                                    if (meter.getMeterDeviceNumber() != null) {
                                        metersList.add(String.valueOf(meter.getMeterDeviceNumber()));

                                    }
                                }
                            }
                            if (!metersList.isEmpty()) {
                                meters = new String[]{Arrays.toString(metersList.toArray())};
                            } else {
                                meters = new String[]{"Default"};
                            }
                            ArrayAdapter<String> adapters = new ArrayAdapter<>(mainContext, R.layout.item_spinner_compact, meters);
                            adapters.setDropDownViewResource(R.layout.item_spinner_dropdown_compact);
                            binding.meterSpinner.setAdapter(adapters);

                            TreeNode root = TreeNode.root();
                            TreeNode currentParent = root;

                            if (!group3List.isEmpty()) {
                                TreeNode group3Node = new TreeNode(group3List.get(0), 0);
                                currentParent.addChild(group3Node);
                                currentParent = group3Node;
                            }
                            if (!group2List.isEmpty()) {
                                TreeNode group2Node = new TreeNode(group2List.get(0), 1);
                                currentParent.addChild(group2Node);
                                currentParent = group2Node;
                            }
                            if (!group1List.isEmpty()) {
                                TreeNode group1Node = new TreeNode(group1List.get(0), 2);
                                currentParent.addChild(group1Node);
                                currentParent = group1Node;
                            }
                            if (!networkIdList.isEmpty()) {
                                /*TreeNode networkNode = new TreeNode(networkIdList.get(0), 3);
                                currentParent.addChild(networkNode);*/
                                for (String networkId : networkIdList) {
                                    TreeNode networkNode = new TreeNode(networkId, 3);
                                    currentParent.addChild(networkNode);
                                }
                            }

                            BaseNodeViewFactory factory = new BaseNodeViewFactory() {
                                @Override
                                public int getNodeLayoutId(int level) {return R.layout.item_first_level;}

                                @Override
                                public int getViewType(TreeNode node) {return node.getLevel();}

                                @Override
                                public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
                                    return new BaseNodeViewBinder(view) {
                                        @Override
                                        public int getToggleTriggerViewId() {return R.id.arrow_img;}

                                        @Override
                                        public void bindView(TreeNode treeNode) {
                                            TextView textView = itemView.findViewById(R.id.node_name_view);
                                            CheckBox checkBox = itemView.findViewById(R.id.checkBox);
                                            ImageView ig = itemView.findViewById(R.id.arrow_img);
                                            if (textView != null) {
                                                textView.setText(treeNode.getValue().toString());
                                            }
                                            int padding = treeNode.getLevel() * 30;
                                            itemView.setPadding(padding, itemView.getPaddingTop(), itemView.getPaddingRight(), itemView.getPaddingBottom());
                                            if (ig != null) {
                                                ig.setRotation(treeNode.isExpanded() ? 90f : 0f);
                                            }
                                            if (treeNode.getLevel() == 3) {
                                                checkBox.setVisibility(View.VISIBLE);
                                                ig.setVisibility(View.GONE);
                                                checkBox.setChecked(networksList.contains(treeNode.getValue().toString())); // Initial state
                                                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                                    String networkId = treeNode.getValue().toString();
                                                    if (isChecked && !networksList.contains(networkId)) {
                                                        networksList.add(networkId);
                                                    } else if (!isChecked && networksList.contains(networkId)) {
                                                        networksList.remove(networkId);
                                                    }
                                                });
                                            } else {
                                                checkBox.setVisibility(View.GONE);
                                                ig.setVisibility(View.VISIBLE);
                                            }
                                        }

                                        @Override
                                        public void onNodeToggled(TreeNode treeNode, boolean expanded) {
                                            ImageView arrow = itemView.findViewById(R.id.arrow_img);
                                            if (arrow != null) {
                                                arrow.animate()
                                                        .rotation(expanded ? 90f : 0f)
                                                        .setDuration(200)
                                                        .start();
                                            }
                                        }
                                    };
                                }
                            };

                            TreeView treeView = new TreeView(root, mainContext, factory);
                            FrameLayout treeViewContainer = binding.getRoot().findViewById(R.id.treeViewContainer);
                            treeViewContainer.removeAllViews();
                            treeViewContainer.addView(treeView.getView());
                            treeView.expandAll();
                        }
                    } else {
                        @SuppressLint("InflateParams")
                        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.toast_layout, null);
                        TextView Ok = layout.findViewById(R.id.okBtn);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
                        header.setText(response.message() + " - " + response.code());
                        description.setText(getString(R.string.error_msg));
                        Ok.setOnClickListener(v -> {
                            getSelectedFeeder();
                        });
                        Toast toast = new Toast(getActivity());
                        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SelectedFeedersModel> call, @NonNull Throwable t) {
                    @SuppressLint("InflateParams")
                    View layout = LayoutInflater.from(getActivity()).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView description = layout.findViewById(R.id.descripTv);
                    header.setText(requireActivity().getString(R.string.error));
                    description.setText(getString(R.string.error_msg));
                    Ok.setOnClickListener(v -> {
                        getSelectedFeeder();
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

    @Override
    public void onStart() {
        super.onStart();
        Window w = getDialog().getWindow();
        if (w != null) {
            w.setStatusBarColor(
                    requireActivity().getWindow().getStatusBarColor()
            );
            w.getDecorView().setSystemUiVisibility(0);
        }
    }

}