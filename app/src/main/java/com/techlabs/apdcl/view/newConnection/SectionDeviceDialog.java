package com.techlabs.apdcl.view.newConnection;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.DataBase.Room.AppDatabase;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.AddDevice;
import com.techlabs.apdcl.Utils.Args;
import com.techlabs.apdcl.Utils.DefaultCustomer;
import com.techlabs.apdcl.Utils.ListDataManager;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.Utils.custom.UTM;
import com.techlabs.apdcl.adapters.DynamicFragmentAdapter;
import com.techlabs.apdcl.databinding.SectionDialogLayoutBinding;
import com.techlabs.apdcl.models.PhaseStatus;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SectionDeviceDialog extends Dialog {

    private SectionDialogLayoutBinding binding;
    private final Context mainContext;
    private List<GeoPoint> newSectionGeoPointList = new ArrayList<>();
    private final String networkId;
    private AddDevice addDevice;
    private final String nodeId;
    private PrefManager prefManager;
    private DynamicFragmentAdapter adapter;
    private String phase;
    private int tabPosition;
    private final JsonObject sectionData = new JsonObject();
    private final JsonObject sectionObject1 = new JsonObject();
    private final JsonObject transformerObject = new JsonObject();
    private final JsonObject spotLoadObject = new JsonObject();
    private String sectionID;
    private final String applicationId;
    private final JsonArray devicesArrays = new JsonArray();
    private final String nodeIdX;
    private final String nodeIdY;
    private final String equipVoltage;
    private boolean isThreePhase;
    private boolean isByPhase;

    public SectionDeviceDialog(@NonNull Context context, List<GeoPoint> newSectionGeoPointList, String networkId, String nodeId, String applicationId, String nodeIdX, String nodeIdY, String equipVoltage) {
        super(context);
        this.mainContext = context;
        this.newSectionGeoPointList = newSectionGeoPointList;
        this.networkId = networkId;
        this.nodeId = nodeId;
        this.applicationId = applicationId;
        this.nodeIdX = nodeIdX;
        this.nodeIdY = nodeIdY;
        this.equipVoltage = equipVoltage;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "NotifyDataSetChanged", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SectionDialogLayoutBinding.inflate(LayoutInflater.from(getContext()));
        setContentView(binding.getRoot());
        Objects.requireNonNull(getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View MainLayoutBackGround = getWindow().getDecorView().getRootView();
        MainLayoutBackGround.setBackground(getContext().getDrawable(R.drawable.pop_background));

        addDevice = (AddDevice) mainContext;
        prefManager = new PrefManager(mainContext);
        Args.getNSCSelectedType().observe((LifecycleOwner) mainContext, new Observer<String>() {
            @Override
            public void onChanged(String selectedType) {
                if (binding != null) {
                    switch (selectedType) {
                        case "Cable":
                            binding.sectionIdEdt.setText("CA_" + applicationId);
                            break;
                        case "Overhead":
                            binding.sectionIdEdt.setText("OH_" + applicationId);
                            break;
                        case "UnbalanceOverhead":
                            binding.sectionIdEdt.setText("UO_" + applicationId);
                            break;
                        default:
                            binding.sectionIdEdt.setText("SEC_" + applicationId);
                            break;
                    }
                }
            }
        });
        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(mainContext)) {
            getPhaseStatus("ConnectedPhase", prefManager.getUserType(), nodeIdX, nodeIdY, prefManager.getDBName());
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
                        getPhaseStatus("ConnectedPhase", prefManager.getUserType(), nodeIdX, nodeIdY, prefManager.getDBName());
                        dialog.dismiss();
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

//        binding.sectionIdEdt.setText("CA_" + applicationId);

        binding.okbtn.setOnClickListener(v -> {
            for (int i = 0; i < adapter.getItemCount(); i++) {
                if (Objects.equals(adapter.getPageTitle(i), "Section")) {
                    Args.setNSCSectionValidate(true);
                } else if (Objects.equals(adapter.getPageTitle(i), "Transformer")) {
                    Args.setNSCIsTransformerValidate(true);
                } else if (Objects.equals(adapter.getPageTitle(i), "SpotLoad By Phase")) {
                    Args.setNSCIsSpotloadValidate(true);
                } else if (Objects.equals(adapter.getPageTitle(i), "SpotLoad Three Phase")) {
                    Args.setNSCIsSpotloadValidate(true);
                }
            }
            if (binding != null) {
                checkDetails();
            }
        });

        binding.canclebtn.setOnClickListener(v -> {
            if (addDevice != null) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("isCancel", true);
                addDevice.addDevice(jsonObject);
                dismiss();
            }
            dismiss();
            new Thread(() -> {
                try {
                    AppDatabase database = AppDatabase.getInstance(mainContext);
                    database.customerDataDao().deleteAll();
                } catch (Exception e) {
                    Log.e("SpotLoadDatabase", "Error deleting all CustomerData entries: " + e.getMessage(), e);
                }
            }).start();
        });

        binding.viewPager.setVisibility(View.VISIBLE);
        binding.tablayout.setVisibility(View.VISIBLE);

        ViewPager2 viewPager = binding.viewPager;
        TabLayout tabLayout = binding.tablayout;

        SectionFragment sectionNSCFragment = new SectionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Network", networkId);
        bundle.putString("applicationId", applicationId);
        bundle.putString("Voltage", equipVoltage);
        sectionNSCFragment.setArguments(bundle);
        adapter = new DynamicFragmentAdapter((FragmentActivity) mainContext);
        adapter.addFragment(sectionNSCFragment, "Section");
        viewPager.requestLayout();
        adapter.notifyDataSetChanged();

        binding.viewPager.setAdapter(adapter);
        Args.setNSCSectionValidate(true);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(adapter.getPageTitle(position))).attach();

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(adapter.getPageTitle(position));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    tabPosition = tab.getPosition();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }).attach();

        binding.addDevice.setOnClickListener(v -> {
            if (binding.phaseA.isChecked() && binding.phaseB.isChecked() && binding.phaseC.isChecked()) {
                phase = "7";
            } else if (binding.phaseB.isChecked() && binding.phaseC.isChecked()) {
                phase = "6";
            } else if (binding.phaseA.isChecked() && binding.phaseC.isChecked()) {
                phase = "5";
            } else if (binding.phaseA.isChecked() && binding.phaseB.isChecked()) {
                phase = "4";
            } else if (binding.phaseC.isChecked()) {
                phase = "3";
            } else if (binding.phaseB.isChecked()) {
                phase = "2";
            } else {
                phase = "1";
            }

            PopupMenu popupMenu = new PopupMenu(mainContext, v);
            popupMenu.getMenuInflater().inflate(R.menu.add_device_menu, popupMenu.getMenu());
            popupMenu.setForceShowIcon(true);

            if (!adapter.getTitleList().contains("Section") && adapter.getItemCount() == 1) {
                popupMenu.getMenu().getItem(0).setEnabled(false);
                popupMenu.getMenu().getItem(1).setEnabled(false);
            } else {
                popupMenu.getMenu().getItem(0).setEnabled(true);
                popupMenu.getMenu().getItem(1).setEnabled(true);
            }

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.transformer_menu:
                            if (adapter.getTitleList().contains("Section")) {
                                if (adapter.getItemCount() < 2 && !Objects.equals(adapter.getPageTitle(tabPosition), "Transformer")) {
                                    menuItem.setChecked(!menuItem.isChecked());
                                    TransformerFragment transformerNSCFragment = new TransformerFragment(networkId, applicationId, equipVoltage != null ? equipVoltage : "");
                                    adapter.addFragment(transformerNSCFragment, "Transformer");
                                    binding.viewPager.setAdapter(adapter);
                                    viewPager.requestLayout();
                                    adapter.notifyDataSetChanged();
                                    Args.setNSCIsTransformerValidate(true);

                                }
                            } else {
                                if (adapter.getItemCount() < 2) {
                                    menuItem.setChecked(!menuItem.isChecked());
                                    TransformerFragment transformerNSCFragment = new TransformerFragment(networkId, applicationId, equipVoltage != null ? equipVoltage : "");
                                    adapter.addFragment(transformerNSCFragment, "Transformer");
                                    binding.viewPager.setAdapter(adapter);
                                    viewPager.requestLayout();
                                    adapter.notifyDataSetChanged();
                                    Args.setNSCIsTransformerValidate(true);
                                }
                            }
                            return true;

                        case R.id.byPhase:
                            if (adapter.getTitleList().contains("Section")) {
                                if (adapter.getItemCount() < 2 && !Objects.equals(adapter.getPageTitle(tabPosition), "SpotLoad By Phase")) {
                                    menuItem.setChecked(!menuItem.isChecked());
                                    adapter.addFragment(new SpotloadFragment("byPhase", phase, applicationId, mainContext), "SpotLoad By Phase");
                                    binding.viewPager.setAdapter(adapter);
                                    viewPager.requestLayout();
                                    adapter.notifyDataSetChanged();
                                    Args.setNSCIsSpotloadValidate(true);
                                    Args.setPhaseValidate(phase);

                                    if (!adapter.getTitleList().contains("SpotLoad Three Phase")) {
                                        binding.phaseA.setEnabled(true);
                                        binding.phaseA.setFocusable(true);
                                        binding.phaseA.setClickable(true);

                                        binding.phaseB.setEnabled(true);
                                        binding.phaseB.setFocusable(true);
                                        binding.phaseB.setClickable(true);

                                        binding.phaseC.setEnabled(true);
                                        binding.phaseC.setFocusable(true);
                                        binding.phaseC.setClickable(true);
                                    }

                                }
                            } else {
                                if (adapter.getItemCount() < 2) {
                                    menuItem.setChecked(!menuItem.isChecked());
                                    adapter.addFragment(new SpotloadFragment("byPhase", phase, applicationId, mainContext), "SpotLoad By Phase");
                                    binding.viewPager.setAdapter(adapter);
                                    viewPager.requestLayout();
                                    adapter.notifyDataSetChanged();
                                    Args.setNSCIsSpotloadValidate(true);
                                    Args.setPhaseValidate(phase);

                                    if (!adapter.getTitleList().contains("SpotLoad Three Phase")) {
                                        binding.phaseA.setEnabled(true);
                                        binding.phaseA.setFocusable(true);
                                        binding.phaseA.setClickable(true);

                                        binding.phaseB.setEnabled(true);
                                        binding.phaseB.setFocusable(true);
                                        binding.phaseB.setClickable(true);

                                        binding.phaseC.setEnabled(true);
                                        binding.phaseC.setFocusable(true);
                                        binding.phaseC.setClickable(true);
                                    }
                                }
                            }

                            return true;

                        case R.id.threePhase:
                            if (adapter.getTitleList().contains("Section")) {
                                if (!isByPhase) {
                                    if (adapter.getItemCount() < 2 && !Objects.equals(adapter.getPageTitle(tabPosition), "SpotLoad")) {
                                        menuItem.setChecked(!menuItem.isChecked());
                                        adapter.addFragment(new SpotloadFragment("ThreePhase", phase, applicationId, mainContext), "SpotLoad Three Phase");
                                        binding.viewPager.setAdapter(adapter);
                                        viewPager.requestLayout();
                                        adapter.notifyDataSetChanged();
                                        Args.setNSCIsSpotloadValidate(true);
                                        Args.setPhaseValidate(phase);

                                        binding.phaseA.setChecked(true);
                                        binding.phaseA.setEnabled(false);
                                        binding.phaseA.setFocusable(false);
                                        binding.phaseA.setClickable(false);

                                        binding.phaseB.setChecked(true);
                                        binding.phaseB.setEnabled(false);
                                        binding.phaseB.setFocusable(false);
                                        binding.phaseB.setClickable(false);

                                        binding.phaseC.setChecked(true);
                                        binding.phaseC.setEnabled(false);
                                        binding.phaseC.setFocusable(false);
                                        binding.phaseC.setClickable(false);

                                    }
                                } else {
                                    Snackbar.make(binding.getRoot(), "Devices Install By Phase", Snackbar.LENGTH_SHORT).show();
                                }
                            } else {
                                if (!isByPhase) {
                                    if (adapter.getItemCount() < 2) {
                                        menuItem.setChecked(!menuItem.isChecked());
                                        adapter.addFragment(new SpotloadFragment("ThreePhase", phase, applicationId, mainContext), "SpotLoad Three Phase");
                                        binding.viewPager.setAdapter(adapter);
                                        viewPager.requestLayout();
                                        adapter.notifyDataSetChanged();
                                        Args.setNSCIsSpotloadValidate(true);
                                        Args.setPhaseValidate(phase);

                                        binding.phaseA.setChecked(true);
                                        binding.phaseA.setEnabled(false);
                                        binding.phaseA.setFocusable(false);
                                        binding.phaseA.setClickable(false);

                                        binding.phaseB.setChecked(true);
                                        binding.phaseB.setEnabled(false);
                                        binding.phaseB.setFocusable(false);
                                        binding.phaseB.setClickable(false);

                                        binding.phaseC.setChecked(true);
                                        binding.phaseC.setEnabled(false);
                                        binding.phaseC.setFocusable(false);
                                        binding.phaseC.setClickable(false);
                                    }
                                } else {
                                    Snackbar.make(binding.getRoot(), "Devices Install By Phase", Snackbar.LENGTH_SHORT).show();
                                }
                            }

                            return true;

                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        });

        binding.removeDevice.setOnClickListener(v -> {
            if (adapter.getItemCount() == 0) {
                binding.removeDevice.setEnabled(false);
                Snackbar.make(binding.getRoot(), "No Devices Added !", Snackbar.LENGTH_SHORT).show();
                return;
            } else {
                binding.removeDevice.setEnabled(true);
            }

            int position = tabPosition;
            boolean moreThanOneTab = adapter.getItemCount() > 1;
            boolean hasSectionTab = adapter.hasFragmentWithTitle("Section");
            boolean currentIsSection =
                    "Section".contentEquals(Objects.requireNonNull(adapter.getPageTitle(position)));

            if (hasSectionTab && moreThanOneTab && currentIsSection) {
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    if (!"Section".contentEquals(Objects.requireNonNull(adapter.getPageTitle(i)))) {
                        position = i;
                        break;
                    }
                }
            }

            CharSequence csTitle = adapter.getPageTitle(position);
            String title = csTitle != null ? csTitle.toString() : "";

            adapter.removeFragment(position);

            if (adapter.getItemCount() == 0) {
                binding.viewPager.setAdapter(null);
            } else {
                binding.viewPager.setCurrentItem(0, false);
            }
            resetValidationFlagsForTitle(title);
            if ("Section".equals(title)) {
                binding.sectionIdEdt.setText("SEC_" + applicationId);
                Args.setNSCSelectedType("");
            }

            /* if (adapter.getItemCount() > 1 && !adapter.getPageTitle(tabPosition).equals("Section")) {
                adapter.removeFragment(tabPosition);

                if (adapter.getPageTitle(tabPosition).equals("Transformer")) {
                    Args.setIsTransformerValidate(false);
                }

                if (adapter.getPageTitle(tabPosition).equals("SpotLoad By Phase")) {
                    Args.setIsSpotloadValidate(false);
                    binding.phaseA.setEnabled(true);
                    binding.phaseA.setFocusable(true);
                    binding.phaseA.setClickable(true);

                    binding.phaseB.setEnabled(true);
                    binding.phaseB.setFocusable(true);
                    binding.phaseB.setClickable(true);

                    binding.phaseC.setEnabled(true);
                    binding.phaseC.setFocusable(true);
                    binding.phaseC.setClickable(true);
                }

                if (adapter.getPageTitle(tabPosition).equals("SpotLoad Three Phase")) {
                    Args.setIsSpotloadValidate(false);
                    binding.phaseA.setEnabled(true);
                    binding.phaseA.setFocusable(true);
                    binding.phaseA.setClickable(true);

                    binding.phaseB.setEnabled(true);
                    binding.phaseB.setFocusable(true);
                    binding.phaseB.setClickable(true);

                    binding.phaseC.setEnabled(true);
                    binding.phaseC.setFocusable(true);
                    binding.phaseC.setClickable(true);
                }

            } else {
                Snackbar.make(binding.getRoot(), "Section is required", Snackbar.LENGTH_LONG).show();
            }*/

        });
    }

    private void resetValidationFlagsForTitle(String title) {
        switch (title) {
            case "Transformer":
                Args.setIsTransformerValidate(false);
                break;

            case "SpotLoad By Phase":
                Args.setIsSpotloadValidate(false);
                binding.phaseA.setEnabled(true);
                binding.phaseA.setFocusable(true);
                binding.phaseA.setClickable(true);

                binding.phaseB.setEnabled(true);
                binding.phaseB.setFocusable(true);
                binding.phaseB.setClickable(true);

                binding.phaseC.setEnabled(true);
                binding.phaseC.setFocusable(true);
                binding.phaseC.setClickable(true);
                break;

            case "SpotLoad Three Phase":
                Args.setIsSpotloadValidate(false);
                binding.phaseA.setEnabled(true);
                binding.phaseA.setFocusable(true);
                binding.phaseA.setClickable(true);

                binding.phaseB.setEnabled(true);
                binding.phaseB.setFocusable(true);
                binding.phaseB.setClickable(true);

                binding.phaseC.setEnabled(true);
                binding.phaseC.setFocusable(true);
                binding.phaseC.setClickable(true);
                break;

            case "Section":
                Args.setSectionValidate(false);
                break;
        }
    }

    private void getPhaseStatus(String connectedPhase, String userType, String nodeIdX, String nodeIdY, String dbName) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Type", connectedPhase);
        jsonObject.addProperty("UserType", userType);
        jsonObject.addProperty("NodeX", nodeIdX);
        jsonObject.addProperty("NodeY", nodeIdY);
        jsonObject.addProperty("CYMDBNET", dbName);
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<PhaseStatus> call = apiInterface.getPhaseStatus("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<PhaseStatus>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<PhaseStatus> call, @NonNull Response<PhaseStatus> response) {
                if (response.code() == 200) {
                    PhaseStatus phaseStatus = response.body();
                    assert phaseStatus != null;
                    if (phaseStatus.getPhase() != null) {
                        switch (phaseStatus.getPhase()) {
                            case "1":
                                binding.phaseA.setChecked(true);
                                binding.phaseB.setChecked(false);
                                binding.phaseC.setChecked(false);
                                isByPhase = true;
                                break;
                            case "2":
                                binding.phaseA.setChecked(false);
                                binding.phaseB.setChecked(true);
                                binding.phaseC.setChecked(false);
                                isByPhase = true;
                                break;
                            case "3":
                                binding.phaseA.setChecked(false);
                                binding.phaseB.setChecked(false);
                                binding.phaseC.setChecked(true);
                                isByPhase = true;
                                break;
                            case "4":
                                binding.phaseA.setChecked(true);
                                binding.phaseB.setChecked(true);
                                binding.phaseC.setChecked(false);
                                isByPhase = true;
                                break;
                            case "5":
                                binding.phaseA.setChecked(true);
                                binding.phaseB.setChecked(false);
                                binding.phaseC.setChecked(true);
                                isByPhase = true;
                                break;
                            case "6":
                                binding.phaseA.setChecked(false);
                                binding.phaseB.setChecked(true);
                                binding.phaseC.setChecked(true);
                                isByPhase = true;
                                break;
                            default:
                                binding.phaseA.setChecked(true);
                                binding.phaseB.setChecked(true);
                                binding.phaseC.setChecked(true);
                                isThreePhase = true;
                                break;
                        }
                    }
                } else {
                    @SuppressLint("InflateParams")
                    View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView description = layout.findViewById(R.id.descripTv);
                    header.setText(response.message() + " - " + response.code());
                    description.setText(mainContext.getString(R.string.error_msg));
                    Ok.setOnClickListener(v -> getPhaseStatus(connectedPhase, userType, nodeIdX, nodeIdY, dbName));
                    Toast toast = new Toast(mainContext);
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhaseStatus> call, @NonNull Throwable t) {
                @SuppressLint("InflateParams")
                View layout = LayoutInflater.from(mainContext).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView description = layout.findViewById(R.id.descripTv);
                header.setText(mainContext.getString(R.string.error));
                description.setText(mainContext.getString(R.string.error_msg));
                Ok.setOnClickListener(v -> getPhaseStatus(connectedPhase, userType, nodeIdX, nodeIdY, dbName));
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
        new Thread(() -> {
            try {
                AppDatabase database = AppDatabase.getInstance(mainContext);
                database.customerDataDao().deleteAll();
            } catch (Exception e) {
                Log.e("SpotLoadDatabase", "Error deleting all CustomerData entries: " + e.getMessage(), e);
            }
        }).start();
        super.dismiss();
        binding = null;
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (addDevice != null) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("isCancel", true);
            addDevice.addDevice(jsonObject);
            dismiss();
        }
        dismiss();
    }

    private void checkDetails() {
        binding.sectionIdEdt.setError(null);
        binding.zoneTv.setError(null);
        boolean isCancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(binding.zoneTv.getText().toString().trim())) {
            binding.zoneTv.setError(Html.fromHtml("<font color='red'>Please enter zone!</font>"));
            focusView = binding.zoneTv;
            isCancel = true;
        }

        if (TextUtils.isEmpty(binding.sectionIdEdt.getText().toString().trim())) {
            binding.sectionIdEdt.setError(Html.fromHtml("<font color='red'>Please enter number</font>"));
            focusView = binding.sectionIdEdt;
            isCancel = true;
        }

        if (isCancel) {
            focusView.requestFocus();
        } else {
            getPhase();
        }
    }

    private void getPhase() {
        try {
            if (binding.phaseA.isChecked() && binding.phaseB.isChecked() && binding.phaseC.isChecked()) {
                phase = "7";
            } else if (binding.phaseB.isChecked() && binding.phaseC.isChecked()) {
                phase = "6";
            } else if (binding.phaseA.isChecked() && binding.phaseC.isChecked()) {
                phase = "5";
            } else if (binding.phaseA.isChecked() && binding.phaseB.isChecked()) {
                phase = "4";
            } else if (binding.phaseC.isChecked()) {
                phase = "3";
            } else if (binding.phaseB.isChecked()) {
                phase = "2";
            } else {
                phase = "1";
            }

            sectionID = binding.sectionIdEdt.getText().toString();

            for (int i = 0; i < adapter.getItemCount(); i++) {
                if (Objects.equals(adapter.getPageTitle(i), "Section")) {
                    getSectionData();

                } else if (Objects.equals(adapter.getPageTitle(i), "Transformer")) {
                    getTransformerData();

                } else if (Objects.equals(adapter.getPageTitle(i), "SpotLoad By Phase")) {
                    getSpotLoadData();

                } else if (Objects.equals(adapter.getPageTitle(i), "SpotLoad Three Phase")) {
                    getSpotLoadData();
                }
            }

        } catch (Exception e) {
            Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
        }
    }

    private void getSectionData() {
        Args.getNSCSectionParameter().observe((LifecycleOwner) mainContext, bundle -> {
            if (bundle != null && bundle.getString("DeviceNumber") != null && bundle.getString("DeviceType") != null && nodeId != null && bundle.getString("CableID") != null && sectionObject1.isEmpty()) {
                try {
                    if (!sectionData.isEmpty()) {
                        sectionObject1.addProperty("DeviceNumber", bundle.getString("DeviceNumber"));
                        sectionObject1.addProperty("DeviceType", bundle.getString("DeviceType"));
                        sectionObject1.addProperty("SectionID", bundle.getString("DeviceNumber"));
                        sectionObject1.addProperty("Location", "0");
                        sectionObject1.addProperty("Status", bundle.getString("Status"));
                        sectionObject1.addProperty("EquipmentID", bundle.getString("CableID"));
                        sectionData.addProperty("DeviceNumber", bundle.getString("DeviceNumber"));
                        sectionData.addProperty("DeviceType", bundle.getString("DeviceType"));
                        devicesArrays.add(sectionObject1);
                        sendData();
                    } else {
                        getSectionDeviceData();
                    }
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                }
            } else {
                if (binding != null) {
                    Snackbar.make(binding.getRoot(), "All filed are required!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getTransformerData() {
        Args.getNSCTransformerParameter().observe((LifecycleOwner) mainContext, bundle -> {
            if (bundle != null && bundle.getString("DeviceNumber") != null && bundle.getString("DeviceType") != null && bundle.getString("Location") != null && bundle.getString("Status") != null && bundle.getString("EquipmentID") != null && transformerObject.isEmpty()) {
                try {
                    if (!sectionData.isEmpty()) {
                        transformerObject.addProperty("DeviceNumber", bundle.getString("DeviceNumber"));
                        transformerObject.addProperty("DeviceType", bundle.getString("DeviceType"));
                        if (sectionObject1.isEmpty()) {
                            transformerObject.addProperty("Location", "0");
                        } else {
                            transformerObject.addProperty("Location", bundle.getString("Location"));
                        }
                        transformerObject.addProperty("Status", bundle.getString("Status"));
                        transformerObject.addProperty("EquipmentID", bundle.getString("EquipmentID"));
                        devicesArrays.add(transformerObject);
                        sendData();
                    } else {
                        getSectionDeviceData();
                    }
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                }
            } else {
                if (binding != null) {
                    Snackbar.make(binding.getRoot(), "All filed are required!", Snackbar.LENGTH_LONG).show();
                }
            }

        });
    }

    private void getSpotLoadData() {
        Args.getNSCSpotloadParameter().observe((LifecycleOwner) mainContext, new Observer<Bundle>() {
            @Override
            public void onChanged(Bundle bundle) {
                if (bundle != null && bundle.getString("DeviceNumber") != null && bundle.getString("DeviceType") != null && bundle.getString("Location") != null && bundle.getString("PhaseType") != null && bundle.getString("Status") != null && spotLoadObject.isEmpty()) {
                    try {
                        if (!sectionData.isEmpty()) {
                            spotLoadObject.addProperty("DeviceNumber", bundle.getString("DeviceNumber"));
                            spotLoadObject.addProperty("DeviceType", bundle.getString("DeviceType"));
                            if (sectionObject1.isEmpty()) {
                                spotLoadObject.addProperty("Location", "0");
                            } else {
                                spotLoadObject.addProperty("Location", bundle.getString("Location"));
                            }
                            spotLoadObject.addProperty("PhaseType", bundle.getString("PhaseType"));
                            spotLoadObject.addProperty("LoadValueType", bundle.getString("LoadValueType", "2"));
                            JsonArray jsonArray1 = new JsonArray();
                            if (!ListDataManager.getData().isEmpty()) {
                                for (int i = 0; i < ListDataManager.getData().size(); i++) {
                                    jsonArray1.add(ListDataManager.getData().get(i));
                                }
                            } else {
                                DefaultCustomer defaultCustomer = new DefaultCustomer(phase, bundle.getString("PhaseType"));
                                defaultCustomer.OneCustomer();

                                jsonArray1.add(ListDataManager.getDefaultData().get(0));

                            }
                            spotLoadObject.add("CustomerData", jsonArray1);
                            devicesArrays.add(spotLoadObject);
                            sendData();
                            ListDataManager.clearData();
                            ListDataManager.clearDefaultData();

                        } else {
                            getSectionDeviceData();
                        }
                    } catch (Exception e) {
                        Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                    }
                } else {
                    if(binding !=null){
                        Snackbar.make(binding.getRoot(), "All filed are required!", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void getSectionDeviceData() {
        if (phase != null && networkId != null && nodeId != null && applicationId != null) {
            sectionData.addProperty("Username", prefManager.getUserName());
            sectionData.addProperty("SectionId", sectionID);
            sectionData.addProperty("Phase", phase);
            sectionData.addProperty("NetworkId", networkId);
            sectionData.addProperty("FromNodeID", nodeId);
            sectionData.addProperty("ApplicationID", applicationId);
            sectionData.addProperty("CYMDBNET", prefManager.getDBName());

            JsonArray latArray = new JsonArray();
            JsonArray lonArray = new JsonArray();

            if (newSectionGeoPointList != null) {
                for (GeoPoint point : newSectionGeoPointList) {
                    UTM utmPoint = UTM.latLonToUTM(point.getLatitude(), point.getLongitude());
                    latArray.add(utmPoint.easting);
                    lonArray.add(utmPoint.northing);
                }
            }

            sectionData.add("X", latArray);
            sectionData.add("Y", lonArray);

            for (int i = 0; i < adapter.getItemCount(); i++) {
                String title = (String) adapter.getPageTitle(i);

                if (title == null) continue;

                switch (title) {
                    case "Section":
                        getSectionData();
                        break;
                    case "Transformer":
                        getTransformerData();
                        break;

                    case "SpotLoad By Phase":
                        getSpotLoadData();
                        break;

                    case "SpotLoad Three Phase":
                        getSpotLoadData();
                        break;

                    default:
                        Log.w("TabHandler", "Unhandled tab title: " + title);
                        break;
                }
            }
        } else {
            Snackbar.make(binding.getRoot(), "Some Required Field is Empty!", Snackbar.LENGTH_LONG).show();
        }
    }

    private void sendData() {
        if (adapter.getItemCount() == devicesArrays.size()) {
            JsonObject jsonObject = new JsonObject();
            JsonObject jsonObject1 = new JsonObject();
            jsonObject1.add("Section", sectionData);
            jsonObject1.add("Device", devicesArrays);
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(jsonObject1);
            jsonObject.add("Data", jsonArray);
            if (addDevice != null) {
                addDevice.addDevice(jsonObject);
                dismiss();
            }
        } else {
            if (binding != null) {
                Snackbar.make(binding.getRoot(), "All filed are required!", Snackbar.LENGTH_LONG).show();
            }
            getPhase();
        }
    }
}
