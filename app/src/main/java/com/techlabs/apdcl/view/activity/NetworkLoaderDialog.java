package com.techlabs.apdcl.view.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.adapters.CustomAdapter;
import com.techlabs.apdcl.adapters.SelectedFeedersAdapter;
import com.techlabs.apdcl.databinding.NetworkLoaderBinding;

import com.techlabs.apdcl.models.dashboard.NetworkIDModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkLoaderDialog extends Dialog {
    private List<String> region = new ArrayList<>();
    private List<String> zone = new ArrayList<>();
    private List<String> circle = new ArrayList<>();
    private List<String> division = new ArrayList<>();
    private List<String> sub = new ArrayList<>();
    private PrefManager prefManager;
    private SelectedFeedersAdapter rvAdapter;

    private NetworkLoaderBinding binding;
    private OnNetworkSelectedListener listener;
    private String selectedRegion;
    private String selectedZone;
    private String selectedCircle;
    private String selectedDivision;
    private String selectedSubstation;
    private String selectedFeeder;


    public void setOnNetworkSelectedListener(OnNetworkSelectedListener listener) {
        this.listener = listener;
    }
    public interface OnNetworkReloadListener {
        void onReloadRequested();
    }

    private OnNetworkReloadListener reloadListener;

    public void setOnNetworkReloadListener(OnNetworkReloadListener listener) {
        this.reloadListener = listener;
    }



    public NetworkLoaderDialog(@NonNull Context context, String region, String zone, String circle, String division, String substation, String feeder,SelectedFeedersAdapter adapter) {
        super(context);
        this.selectedRegion = region;
        this.selectedZone = zone;
        this.selectedCircle = circle;
        this.selectedDivision = division;
        this.selectedSubstation = substation;
        this.selectedFeeder = feeder;
        this.rvAdapter = adapter;
    }
    public interface OnNetworkSelectedListener {
        void onNetworkSelected(List<String> newFeeders);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = NetworkLoaderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(getContext());
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
            windowParams.copyFrom(window.getAttributes());
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            int margin = (int) (6 * getContext().getResources().getDisplayMetrics().density);
            windowParams.width = getContext().getResources().getDisplayMetrics().widthPixels - (margin * 2);
            windowParams.gravity = Gravity.TOP;
            int statusBarHeight = 0;
            @SuppressLint("InternalInsetResource")
            int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = getContext().getResources().getDimensionPixelSize(resourceId);
            }
            windowParams.y = statusBarHeight;
            window.setAttributes(windowParams);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        setCanceledOnTouchOutside(false);
        binding.imgClose.setOnClickListener(view -> dismiss());

        binding.database.setText(prefManager.getDBName());
        binding.database.setEnabled(false);
        binding.rvLoaded.setAdapter(rvAdapter);
        binding.rvLoaded.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvLoaded.setVisibility(selectedFeeder.isEmpty() ? View.GONE : View.VISIBLE);
        binding.loadNet.setVisibility(selectedFeeder.isEmpty() ? View.GONE : View.VISIBLE);



        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
            getRegions(prefManager.getDBName());
            if (selectedRegion != null && !selectedRegion.isEmpty()) {
                binding.region.setText(selectedRegion, false);
                getGroup("Group5", selectedRegion);
            }
            if (selectedZone != null && !selectedZone.isEmpty()) {
                binding.zone.setText(selectedZone, false);
                getGroup("Group4", selectedZone);
            }
            if (selectedCircle != null && !selectedCircle.isEmpty()) {
                binding.circle.setText(selectedCircle, false);
                getGroup("Group3", selectedCircle);
            }
            if (selectedDivision != null && !selectedDivision.isEmpty()) {
                binding.division.setText(selectedDivision, false);
                getGroup("Group2", selectedDivision);
            }
            if (selectedSubstation != null && !selectedSubstation.isEmpty()) {
                binding.substn.setText(selectedSubstation, false);
                getGroup("Group1", selectedSubstation);
            }
        }

        rvAdapter.setOnDataChangedListener(itemCount -> {
            binding.rvLoaded.setVisibility(itemCount > 0 ? View.VISIBLE : View.GONE);
            binding.loadNet.setVisibility(itemCount > 0 ? View.VISIBLE : View.GONE);
        });

        rvAdapter.setOnItemRemovedListener(this::isClear);


        binding.loadNetworkBtn.setOnClickListener(v -> {
            if (!ResponseDataUtils.NetworkList.isEmpty()) {
                if (listener != null) {
                    listener.onNetworkSelected(new ArrayList<>(ResponseDataUtils.NetworkList));
                }
                dismiss();
            } else {
                Snackbar.make(binding.getRoot(), "Please select any feeder!", Snackbar.LENGTH_LONG).show();
            }
        });


    }

    private void getRegions(String selectedDB) {
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("NetworkId", new JsonArray());
        jsonObject.addProperty("UserType", prefManager.getUserType());
        jsonObject.addProperty("Project", prefManager.getProjectName());
        jsonObject.addProperty("Group5", "");
        jsonObject.addProperty("Group4", "");
        jsonObject.addProperty("Group3", "");
        jsonObject.addProperty("Group2", "");
        jsonObject.addProperty("Group1", "");
        jsonObject.addProperty("DashBoardType", "Database");
        jsonObject.addProperty("CYMDBNET", selectedDB);
        jsonObject.addProperty("Mode", "Mobile");
        Call<NetworkIDModel> call = apiInterface.NetworkList("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<NetworkIDModel>() {
            @Override
            public void onResponse(@NonNull Call<NetworkIDModel> call, @NonNull Response<NetworkIDModel> response) {
                if (response.code() == 200 && response.body() != null) {

                    if (response.body().getOutput().getGroup5().getGroup5() != null && !response.body().getOutput().getGroup5().getGroup5().isEmpty()) {
                        region.clear();
                        region.addAll(response.body().getOutput().getGroup5().getGroup5());
                        setSpinner(binding.region, region, false);
                    } else {
                        binding.region.setFocusable(false);
                    }
//                    binding.region.setVisibility(region.isEmpty() ? View.GONE : View.VISIBLE);

                    if (response.body().getOutput().getGroup4().getGroup4() != null && !response.body().getOutput().getGroup4().getGroup4().isEmpty()) {
                        zone.clear();
                        zone.addAll(response.body().getOutput().getGroup4().getGroup4());
                        setSpinner(binding.zone, zone, false);
                    } else {
                        binding.zone.setFocusable(false);
                    }
//                    binding.zone.setVisibility(zone.isEmpty() ? View.GONE : View.VISIBLE);

                    if (response.body().getOutput().getGroup3().getGroup3() != null && !response.body().getOutput().getGroup3().getGroup3().isEmpty()) {
                        circle.clear();
                        circle.addAll(response.body().getOutput().getGroup3().getGroup3());
                        setSpinner(binding.circle, circle, false);
                    } else {
                        binding.circle.setFocusable(false);
                    }
//                    binding.circle.setVisibility(circle.isEmpty() ? View.GONE : View.VISIBLE);

                    if (response.body().getOutput().getGroup2().getGroup2() != null && !response.body().getOutput().getGroup2().getGroup2().isEmpty()) {
                        division.clear();
                        division.addAll(response.body().getOutput().getGroup2().getGroup2());
                        setSpinner(binding.division, division, false);
                    } else {
                        binding.division.setFocusable(false);
                    }
//                    binding.division.setVisibility(division.isEmpty() ? View.GONE : View.VISIBLE);

                    if (response.body().getOutput().getGroup1().getGroup1() != null && !response.body().getOutput().getGroup1().getGroup1().isEmpty()) {
                        sub.clear();
                        sub.addAll(response.body().getOutput().getGroup1().getGroup1());
                        setSpinner(binding.substn, sub, false);
                    } else {
                        binding.substn.setFocusable(false);
                    }
//                    binding.substn.setVisibility(sub.isEmpty() ? View.GONE : View.VISIBLE);

                    if (response.body().getOutput().getNetworkName().getNetworkId() != null && !response.body().getOutput().getNetworkName().getNetworkId().isEmpty()) {
                        setSpinner(binding.feederID, response.body().getOutput().getNetworkName().getNetworkId(), true);
                    } else {
                        binding.feederID.setFocusable(false);
                    }

                }  else {
                    Snackbar snack = Snackbar.make(binding.getRoot(), response.message() + " - " + response.code(), Snackbar.LENGTH_INDEFINITE);
                    snack.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NetworkIDModel> call, @NonNull Throwable t) {
                Snackbar snack = Snackbar.make(binding.getRoot(),  "Please try again" , Snackbar.LENGTH_INDEFINITE);
                snack.show();
            }
        });
    }

    private void getGroup(String groupType, String groupValue) {
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        jsonObject.addProperty("DashBoardType", "Group");
        jsonObject.addProperty("Group1", groupType.equals("Group1") ? groupValue : "");
        jsonObject.addProperty("Group2", groupType.equals("Group2") ? groupValue : "");
        jsonObject.addProperty("Group3", groupType.equals("Group3") ? groupValue : "");
        jsonObject.addProperty("Group4", groupType.equals("Group4") ? groupValue : "");
        jsonObject.addProperty("Group5", groupType.equals("Group5") ? groupValue : "");
        jsonObject.add("NetworkId", new JsonArray());
        jsonObject.addProperty("Project", prefManager.getProjectName());
        jsonObject.addProperty("UserType", prefManager.getUserType());
        jsonObject.addProperty("Mode", "Mobile");
        Call<NetworkIDModel> call = apiInterface.NetworkList("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<NetworkIDModel>() {
            @Override
            public void onResponse(@NonNull Call<NetworkIDModel> call, @NonNull Response<NetworkIDModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getOutput().getGroup5().getGroup5() != null && !response.body().getOutput().getGroup5().getGroup5().isEmpty()) {
                        setSpinner(binding.region, region, false);
                    } else {
                        binding.region.setFocusable(false);
                    }
                    if (groupType.equals("Group5")) {
                        setSpinner(binding.zone, response.body().getOutput().getGroup4().getGroup4(), false);
                        setSpinner(binding.circle, response.body().getOutput().getGroup3().getGroup3(), false);
                        setSpinner(binding.division, response.body().getOutput().getGroup2().getGroup2(), false);
                        setSpinner(binding.substn, response.body().getOutput().getGroup1().getGroup1(), false);
                        setSpinner(binding.feederID, response.body().getOutput().getNetworkName().getNetworkId(), true);
                    }
                    if (groupType.equals("Group4")) {
                        setSpinner(binding.circle, response.body().getOutput().getGroup3().getGroup3(), false);
                        setSpinner(binding.division, response.body().getOutput().getGroup2().getGroup2(), false);
                        setSpinner(binding.substn, response.body().getOutput().getGroup1().getGroup1(), false);
                        setSpinner(binding.feederID, response.body().getOutput().getNetworkName().getNetworkId(), true);
                    }
                    if (groupType.equals("Group3")) {
                        setSpinner(binding.division, response.body().getOutput().getGroup2().getGroup2(), false);
                        setSpinner(binding.substn, response.body().getOutput().getGroup1().getGroup1(), false);
                        setSpinner(binding.feederID, response.body().getOutput().getNetworkName().getNetworkId(), true);
                    }
                    if (groupType.equals("Group2")) {
                        setSpinner(binding.substn, response.body().getOutput().getGroup1().getGroup1(), false);
                        setSpinner(binding.feederID, response.body().getOutput().getNetworkName().getNetworkId(), true);
                    }
                    if (groupType.equals("Group1")) {
                        setSpinner(binding.feederID, response.body().getOutput().getNetworkName().getNetworkId(), true);
                    }

                } else {
                    Snackbar snack = Snackbar.make(binding.getRoot(), response.message() + " - " + response.code(), Snackbar.LENGTH_INDEFINITE);
                    snack.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NetworkIDModel> call, @NonNull Throwable t) {
                Snackbar snack = Snackbar.make(binding.getRoot(),  "Please try again" , Snackbar.LENGTH_INDEFINITE);
                snack.show();            }
        });
    }

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    private void setSpinner(AutoCompleteTextView item, List<String> data, boolean netId) {
        if (netId) {
            CustomAdapter adapter = new CustomAdapter(this.getContext(), data);
            adapter.setFromDialog(true);
            item.setAdapter(adapter);
            item.setDropDownVerticalOffset(-item.getHeight() - 14);
            List<String> selectedItems = new ArrayList<>(rvAdapter.getSelectedItems());
            for (String feeder : selectedItems) {
                adapter.setSelected(feeder, true);
            }

            adapter.setOnItemSelectedListener((selectedItem, isSelected) -> {
                if (isSelected) {
                    if (!selectedItems.contains(selectedItem)) {
                        selectedItems.add(selectedItem);
                        rvAdapter.addFeeder(selectedItem);
                        ResponseDataUtils.NetworkList.add(selectedItem);
                    }
                } else {
                    selectedItems.remove(selectedItem);
                    rvAdapter.removeFeeder(selectedItem);
                    ResponseDataUtils.NetworkList.remove(selectedItem);
                }
                iconCancel(item, selectedItems, adapter);

            });

        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.custom_spinner, data);
            item.setAdapter(adapter);
            item.setDropDownBackgroundResource(android.R.color.white);
            item.setDropDownHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            item.getDropDownBackground().setColorFilter(ContextCompat.getColor(getContext(), android.R.color.white), PorterDuff.Mode.SRC_ATOP);

            item.setOnItemClickListener((parent, view, position, id) -> {
                String group = adapter.getItem(position);
                item.setText(group, false);
                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(NetworkLoaderDialog.this.getContext())) {
                    if (item == binding.region) {
                        binding.zone.setText("", false);
                        binding.circle.setText("", false);
                        binding.division.setText("", false);
                        binding.substn.setText("", false);
                        binding.feederID.setText("", false);
                        getGroup("Group5", group);
                    } else if (item == binding.zone) {
                        binding.circle.setText("", false);
                        binding.division.setText("", false);
                        binding.substn.setText("", false);
                        binding.feederID.setText("", false);
                        getGroup("Group4", group);
                    } else if (item == binding.circle) {
                        binding.division.setText("", false);
                        binding.substn.setText("", false);
                        binding.feederID.setText("", false);
                        getGroup("Group3", group);
                    } else if (item == binding.division) {
                        binding.substn.setText("", false);
                        binding.feederID.setText("", false);
                        getGroup("Group2", group);
                    } else if (item == binding.substn) {
                        binding.feederID.setText("", false);
                        getGroup("Group1", group);
                    }
                } else {
                    final Dialog dialog = new Dialog(NetworkLoaderDialog.this.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.no_internet_dialog);
                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(NetworkLoaderDialog.this.getContext().getDrawable(R.drawable.pop_background));
                    LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
                    Button RetryBtn = dialog.findViewById(R.id.btnDialog);
                    lottieAnimationView.playAnimation();
                    RetryBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(NetworkLoaderDialog.this.getContext())) {
                                if (item == binding.region) {
                                    getGroup("Group5", group);
                                } else if (item == binding.zone) {
                                    getGroup("Group4", group);
                                } else if (item == binding.circle) {
                                    getGroup("Group3", group);
                                } else if (item == binding.division) {
                                    getGroup("Group2", group);
                                } else if (item == binding.substn) {
                                    getGroup("Group1", group);
                                }
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.show();
                }

            });
        }

        item.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                item.setBackgroundResource(R.drawable.list_spin);
                item.post(() -> {
                    if (item.getAdapter() != null && item.getAdapter().getCount() > 0) {
                        item.showDropDown();
                    }
                });
            } else {
                item.setBackgroundResource(R.drawable.tag_spinner_bg);
            }
        });
        item.setOnClickListener(v -> {
            if (item.getAdapter() != null && item.getAdapter().getCount() > 0) {
                item.showDropDown();
            }
        });

    }

    private void iconCancel(AutoCompleteTextView item, List<String> selectedItems, CustomAdapter adapter) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        Drawable cancelIcon = ContextCompat.getDrawable(item.getContext(), R.drawable.close);
        if (cancelIcon != null) {
            int iconSize = (int) (16 * item.getContext().getResources().getDisplayMetrics().density);
            cancelIcon.setBounds(0, 0, iconSize, iconSize);

            for (int i = 0; i < selectedItems.size(); i++) {
                String text = selectedItems.get(i) + " ";
                builder.append(text);
                ImageSpan imageSpan = new ImageSpan(cancelIcon, ImageSpan.ALIGN_CENTER);
                int start = builder.length();
                builder.append(" ");
                int end = builder.length();
                builder.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                String currentItem = selectedItems.get(i);
                builder.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        if (selectedItems.contains(currentItem)) {
                            selectedItems.remove(currentItem);
                            rvAdapter.removeFeeder(currentItem);
                            ResponseDataUtils.NetworkList.remove(currentItem);
                            iconCancel(item, selectedItems, adapter);
                            adapter.clearSelection();
                        }
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                    }
                }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (i < selectedItems.size() - 1) {
                    builder.append(", ");
                }
            }
        }

        item.setText(builder, false);
        item.setMovementMethod(LinkMovementMethod.getInstance());
    }
    private void isClear(String removedItem) {
        ResponseDataUtils.NetworkList.remove(removedItem);
        List<String> currentItems = rvAdapter.getSelectedItems();
        CustomAdapter adapter = (CustomAdapter) binding.feederID.getAdapter();
        if (adapter != null) {
            adapter.clearSelection();
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        Drawable cancelIcon = ContextCompat.getDrawable(binding.feederID.getContext(), R.drawable.close);
        if (cancelIcon != null) {
            int iconSize = (int) (16 * binding.feederID.getContext().getResources().getDisplayMetrics().density);
            cancelIcon.setBounds(0, 0, iconSize, iconSize);

            for (int i = 0; i < currentItems.size(); i++) {
                String text = currentItems.get(i) + " ";
                builder.append(text);
                int start = builder.length();
                builder.append(" ");
                int end = builder.length();
                builder.setSpan(new ImageSpan(cancelIcon, ImageSpan.ALIGN_CENTER), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                String currentItem = currentItems.get(i);
                builder.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        rvAdapter.removeFeeder(currentItem);
                        ResponseDataUtils.NetworkList.remove(currentItem);
                        isClear(currentItem);
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        ds.setUnderlineText(false);
                    }
                }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                if (i < currentItems.size() - 1) {
                    builder.append(", ");
                }
            }
        }

        binding.feederID.setText(builder, false);
        binding.feederID.setMovementMethod(LinkMovementMethod.getInstance());
        if (reloadListener != null) {
            reloadListener.onReloadRequested();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        List<String> currentSelected = rvAdapter.getSelectedItems();
        if (currentSelected != null && !currentSelected.isEmpty()) {
            String restoredText = String.join(", ", currentSelected);
            binding.feederID.setText(restoredText);

        } else if (selectedFeeder != null && !selectedFeeder.isEmpty()) {
            binding.feederID.setText(selectedFeeder);
            String[] feeders = selectedFeeder.split(",\\s*");
            for (String f : feeders) {
                rvAdapter.addFeeder(f.trim());
                if (!ResponseDataUtils.NetworkList.contains(f.trim())) {
                    ResponseDataUtils.NetworkList.add(f.trim());
                }
            }
            binding.rvLoaded.setVisibility(View.VISIBLE);
            binding.loadNet.setVisibility(View.VISIBLE);
        } else {
            binding.feederID.setText("");
            binding.rvLoaded.setVisibility(View.GONE);
            binding.loadNet.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
/*        List<String> currentSelected = rvAdapter.getSelectedItems();
        ResponseDataUtils.NetworkList.clear();
        ResponseDataUtils.NetworkList.addAll(currentSelected);*/
    }

}
