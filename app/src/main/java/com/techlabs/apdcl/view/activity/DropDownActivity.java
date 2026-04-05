package com.techlabs.apdcl.view.activity;

import static com.techlabs.apdcl.Utils.Config.isTreeNode;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.Utils.custom.ErrorPdfLogger;
import com.techlabs.apdcl.adapters.CustomAdapter;
import com.techlabs.apdcl.adapters.SelectedFeedersAdapter;
import com.techlabs.apdcl.databinding.ActivityDropDownBinding;
import com.techlabs.apdcl.models.dashboard.DatabaseModel;
import com.techlabs.apdcl.models.dashboard.NetworkIDModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;
import com.techlabs.apdcl.view.fragment.DashboardFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DropDownActivity extends AppCompatActivity {
    private final List<String> spinDb = new ArrayList<>();
    private final List<String> projectNames = new ArrayList<>();
    private ActivityDropDownBinding binding;
    private List<String> region = new ArrayList<>();
    private List<String> zone = new ArrayList<>();
    private List<String> circle = new ArrayList<>();
    private List<String> division = new ArrayList<>();
    private List<String> sub = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> projectAdapter;
    private PrefManager prefManager;
    private SelectedFeedersAdapter rvAdapter;
    private String DbName = null;
    private boolean onBack = false;
    private Snackbar exitSnackbar;
    private boolean doubleBackToExitPressedOnce = false;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDropDownBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(DropDownActivity.this);
        binding.toolbar.setTitle("Network List");
        setSupportActionBar(binding.toolbar);
        binding.shimmerView.startShimmer();
        binding.loadNetworkBtn.setVisibility(View.GONE);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, new DashboardFragment())
                    .commit();
        }

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(DropDownActivity.this)) {
            new Handler(Looper.getMainLooper()).postDelayed(this::getDatabaseList, 3000);
        } else {
            final Dialog dialog = new Dialog(DropDownActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.no_internet_dialog);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(DropDownActivity.this.getDrawable(R.drawable.pop_background));
            LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
            Button RetryBtn = dialog.findViewById(R.id.btnDialog);
            lottieAnimationView.playAnimation();
            RetryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(DropDownActivity.this)) {
                        getDatabaseList();
                        dialog.dismiss();
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.fuse_color);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            getRefresh();
            getReset();
            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(DropDownActivity.this)) {
                getDatabaseList();
            } else {
                binding.swipeRefreshLayout.setRefreshing(false);
                showNoInternetDialog();
            }
        });

        adapter = new ArrayAdapter<>(this, R.layout.custom_spinner, spinDb);
        binding.databaseSpin.setAdapter(adapter);
        binding.databaseSpin.setDropDownBackgroundResource(android.R.color.white);

        projectAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner, projectNames);
        binding.projectSpin.setAdapter(projectAdapter);
        binding.projectSpin.setDropDownBackgroundResource(android.R.color.white);

        rvAdapter = new SelectedFeedersAdapter(this, new ArrayList<>());
        binding.rvLoaded.setLayoutManager(new LinearLayoutManager(this));
        binding.rvLoaded.setAdapter(rvAdapter);
        rvAdapter.setOnDataChangedListener(itemCount -> {
            binding.rvLoaded.setVisibility(itemCount > 0 ? View.VISIBLE : View.GONE);
            binding.loadNet.setVisibility(itemCount > 0 ? View.VISIBLE : View.GONE);
        });

        rvAdapter.setOnItemRemovedListener(item -> {
            isClear(item);
        });

        binding.loadNetworkBtn.setOnClickListener(v -> {
            if (!ResponseDataUtils.NetworkList.isEmpty()) {
                if (prefManager.getType().contains("Edit")) {
                    prefManager.setUserType("Analysis");
                } else {
                    prefManager.setUserType(prefManager.getType());
                }
                Intent intent = new Intent(DropDownActivity.this, MapActivity.class);
                intent.putExtra("NetworkId", ResponseDataUtils.NetworkList);
                intent.putExtra("Type", "Normal");
                intent.putExtra("Region", binding.regionSpin.getText().toString());
                intent.putExtra("Zone", binding.zoneSpin.getText().toString());
                intent.putExtra("Circle", binding.circleSpin.getText().toString());
                intent.putExtra("Division", binding.divisionSpin.getText().toString());
                intent.putExtra("Substation", binding.subStnSpin.getText().toString());
                intent.putExtra("selectedFeeder", binding.feederIdSpin.getText().toString());
                prefManager.setDBName(DbName);
                prefManager.setEditMode("Normal");
                startActivity(intent);
            } else {
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Please select any feeder!", Snackbar.LENGTH_LONG);
                snack.show();
            }
        });
        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {

                    @Override
                    public void handleOnBackPressed() {

                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main);

                        if (fragment != null && !fragment.isVisible()) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .show(fragment)
                                    .commit();

                            binding.loadNetworkBtn.setVisibility(View.GONE);
                            return;
                        }
                        isTreeNode = false;

                        if (doubleBackToExitPressedOnce) {
                            finish();
                            return;
                        }

                        doubleBackToExitPressedOnce = true;

                        new Handler(Looper.getMainLooper())
                                .postDelayed(() ->
                                                doubleBackToExitPressedOnce = false,
                                        2000
                                );
                    }
                });

        binding.projectSpin.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && binding.projectSpin.getAdapter() != null && binding.projectSpin.getAdapter().getCount() > 0) {
                binding.projectSpin.showDropDown();
            }
        });

        binding.projectSpin.setOnClickListener(v -> {
            if (binding.projectSpin.getAdapter() != null && binding.projectSpin.getAdapter().getCount() > 0) {
                binding.projectSpin.showDropDown();
            }
        });

        binding.databaseSpin.setOnItemClickListener((parent, view, position, id) -> {
            DbName = spinDb.get(position);
            prefManager.setDBName(DbName);
            binding.databaseSpin.setText(DbName, false);
            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(DropDownActivity.this)) {
                getRegions(DbName);
            } else {
                final Dialog dialog = new Dialog(DropDownActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.no_internet_dialog);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(DropDownActivity.this.getDrawable(R.drawable.pop_background));
                LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
                Button RetryBtn = dialog.findViewById(R.id.btnDialog);
                lottieAnimationView.playAnimation();
                RetryBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(DropDownActivity.this)) {
                            getRegions(DbName);
                            dialog.dismiss();
                        }
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
            }

            binding.regionSpin.setText("", false);
            binding.zoneSpin.setText("", false);
            binding.circleSpin.setText("", false);
            binding.divisionSpin.setText("", false);
            binding.subStnSpin.setText("", false);
            binding.feederIdSpin.setText("", false);
        });

        binding.databaseSpin.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && binding.databaseSpin.getAdapter() != null && binding.databaseSpin.getAdapter().getCount() > 0) {
                binding.databaseSpin.showDropDown();
            }
        });

        binding.databaseSpin.setOnClickListener(v -> {
            if (binding.databaseSpin.getAdapter() != null && binding.databaseSpin.getAdapter().getCount() > 0) {
                binding.databaseSpin.showDropDown();
            }
        });

        binding.feederIdSpin.setOnClickListener(v -> {
            binding.feederIdSpin.showDropDown();
        });


    }

    private void getDatabaseList() {
        binding.itemLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
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
        jsonObject.addProperty("DashBoardType", "Main");
        jsonObject.addProperty("CYMDBNET", "");
        jsonObject.addProperty("Mode", "Mobile");
        Call<DatabaseModel> call = apiInterface.DatabaseList("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<DatabaseModel>() {
            @Override
            public void onResponse(@NonNull Call<DatabaseModel> call, @NonNull Response<DatabaseModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    ErrorPdfLogger.logApiSuccess(DropDownActivity.this, "POST", "dashboard/", "HTTP " + response.code() + " : " + response.message());
                    binding.itemLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    binding.swipeRefreshLayout.setRefreshing(false);
                    List<String> databaseNames = response.body().getOutput().getDatabaseName();
                    spinDb.clear();
                    spinDb.addAll(databaseNames);
                    adapter.notifyDataSetChanged();
                } else if (response.code() == 401) {
                    ErrorPdfLogger.logApiError(DropDownActivity.this, "POST", "dashboard/", "HTTP " + response.code() + " : " + response.message());
                    binding.itemLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    prefManager.setIsUserLogin(false);
                    startActivity(new Intent(DropDownActivity.this, LoginActivity.class));
                    finish();
                } else {
                    ErrorPdfLogger.logApiError(DropDownActivity.this, "POST", "dashboard/", "HTTP " + response.code() + " : " + response.message());
                    binding.itemLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    Snackbar snack = Snackbar.make(binding.getRoot(), response.message() + " - " + response.code(), Snackbar.LENGTH_INDEFINITE);
                    snack.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DatabaseModel> call, @NonNull Throwable t) {
                ErrorPdfLogger.logApiFailure(DropDownActivity.this, "POST", "dashboard/", t);
                binding.itemLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                binding.swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(binding.getRoot(), getString(R.string.error_msg), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void getRegions(String selectedDB) {
        binding.itemLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
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
                    ErrorPdfLogger.logApiSuccess(DropDownActivity.this, "POST", "dashboard/", "HTTP " + response.code() + " : " + response.message());
                    binding.itemLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);

                    NetworkIDModel.Output output = response.body().getOutput();
                    if (output == null) {
                        Snackbar snack = Snackbar.make(binding.getRoot(), "Empty response from server", Snackbar.LENGTH_LONG);
                        snack.show();
                        return;
                    }

                    List<String> group5 = output.getGroup5() != null ? output.getGroup5().getGroup5() : null;
                    if (group5 != null && !group5.isEmpty()) {
                        region.clear();
                        region.addAll(group5);
                        setSpinner(binding.regionSpin, region, false);
                    } else {
                        region.clear();
                        binding.regionSpin.setText("", false);
                    }
                    binding.textReg.setVisibility(region.isEmpty() ? View.GONE : View.VISIBLE);
                    binding.regionSpin.setVisibility(region.isEmpty() ? View.GONE : View.VISIBLE);

                    List<String> group4 = output.getGroup4() != null ? output.getGroup4().getGroup4() : null;
                    if (group4 != null && !group4.isEmpty()) {
                        zone.clear();
                        zone.addAll(group4);
                        setSpinner(binding.zoneSpin, zone, false);
                    } else {
                        zone.clear();
                        binding.zoneSpin.setText("", false);
                    }
                    binding.textZone.setVisibility(zone.isEmpty() ? View.GONE : View.VISIBLE);
                    binding.zoneSpin.setVisibility(zone.isEmpty() ? View.GONE : View.VISIBLE);

                    List<String> group3 = output.getGroup3() != null ? output.getGroup3().getGroup3() : null;
                    if (group3 != null && !group3.isEmpty()) {
                        circle.clear();
                        circle.addAll(group3);
                        setSpinner(binding.circleSpin, circle, false);
                    } else {
                        circle.clear();
                        binding.circleSpin.setText("", false);
                    }
                    binding.textCircle.setVisibility(circle.isEmpty() ? View.GONE : View.VISIBLE);
                    binding.circleSpin.setVisibility(circle.isEmpty() ? View.GONE : View.VISIBLE);

                    List<String> group2 = output.getGroup2() != null ? output.getGroup2().getGroup2() : null;
                    if (group2 != null && !group2.isEmpty()) {
                        division.clear();
                        division.addAll(group2);
                        setSpinner(binding.divisionSpin, division, false);
                    } else {
                        division.clear();
                        binding.divisionSpin.setText("", false);
                    }
                    binding.textDev.setVisibility(division.isEmpty() ? View.GONE : View.VISIBLE);
                    binding.divisionSpin.setVisibility(division.isEmpty() ? View.GONE : View.VISIBLE);

                    List<String> group1 = output.getGroup1() != null ? output.getGroup1().getGroup1() : null;
                    if (group1 != null && !group1.isEmpty()) {
                        sub.clear();
                        sub.addAll(group1);
                        setSpinner(binding.subStnSpin, sub, false);
                    } else {
                        sub.clear();
                        binding.subStnSpin.setText("", false);
                    }
                    binding.textStn.setVisibility(sub.isEmpty() ? View.GONE : View.VISIBLE);
                    binding.subStnSpin.setVisibility(sub.isEmpty() ? View.GONE : View.VISIBLE);

                    List<String> networkIds = output.getNetworkName() != null ? output.getNetworkName().getNetworkId() : null;
                    if (networkIds != null && !networkIds.isEmpty()) {
                        setSpinner(binding.feederIdSpin, networkIds, true);
                    } else {
                        setSpinner(binding.feederIdSpin, new ArrayList<>(), true);
                    }

                } else if (response.code() == 401) {
                    ErrorPdfLogger.logApiError(DropDownActivity.this, "POST", "dashboard/", "HTTP " + response.code() + " : " + response.message());
                    binding.itemLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    prefManager.setIsUserLogin(false);
                    startActivity(new Intent(DropDownActivity.this, LoginActivity.class));
                    finish();
                } else {
                    ErrorPdfLogger.logApiError(DropDownActivity.this, "POST", "dashboard/", "HTTP " + response.code() + " : " + response.message());
                    binding.itemLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    Snackbar snack = Snackbar.make(binding.getRoot(), response.message() + " - " + response.code(), Snackbar.LENGTH_INDEFINITE);
                    snack.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NetworkIDModel> call, @NonNull Throwable t) {
                ErrorPdfLogger.logApiFailure(DropDownActivity.this, "POST", "dashboard/", t);
                binding.itemLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                Snackbar.make(binding.getRoot(), getString(R.string.error_msg), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void getGroup(String groupType, String groupValue) {
        binding.itemLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        jsonObject.addProperty("DashBoardType", "Database");
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
                    ErrorPdfLogger.logApiSuccess(DropDownActivity.this, "POST", "dashboard/", "HTTP " + response.code() + " : " + response.message());
                    binding.itemLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    NetworkIDModel.Output output = response.body().getOutput();
                    if (output == null) {
                        Snackbar snack = Snackbar.make(binding.getRoot(), "Empty response from server", Snackbar.LENGTH_LONG);
                        snack.show();
                        return;
                    }
                    if (response.body().getOutput().getGroup5().getGroup5() != null && !response.body().getOutput().getGroup5().getGroup5().isEmpty()) {
                        setSpinner(binding.regionSpin, region, false);
                    } else {
                        binding.regionSpin.setFocusable(false);
                    }
                    if (groupType.equals("Group5")) {
                        setSpinner(binding.zoneSpin, response.body().getOutput().getGroup4().getGroup4(), false);
                        setSpinner(binding.circleSpin, response.body().getOutput().getGroup3().getGroup3(), false);
                        setSpinner(binding.divisionSpin, response.body().getOutput().getGroup2().getGroup2(), false);
                        setSpinner(binding.subStnSpin, response.body().getOutput().getGroup1().getGroup1(), false);
                        setSpinner(binding.feederIdSpin, response.body().getOutput().getNetworkName().getNetworkId(), true);
                    }
                    if (groupType.equals("Group4")) {
                        setSpinner(binding.circleSpin, response.body().getOutput().getGroup3().getGroup3(), false);
                        setSpinner(binding.divisionSpin, response.body().getOutput().getGroup2().getGroup2(), false);
                        setSpinner(binding.subStnSpin, response.body().getOutput().getGroup1().getGroup1(), false);
                        setSpinner(binding.feederIdSpin, response.body().getOutput().getNetworkName().getNetworkId(), true);
                    }
                    if (groupType.equals("Group3")) {
                        List<String> group2List = output.getGroup2() != null ? output.getGroup2().getGroup2() : null;
                        if (group2List == null || group2List.isEmpty()) {
                            Set<String> uq = new HashSet<>();
                            if (output.getGroup2All() != null) {
                                for (NetworkIDModel.Group2All item : output.getGroup2All()) {
                                    if (item != null && groupValue.equalsIgnoreCase(item.getGroup3()) && item.getGroup2() != null) {
                                        uq.add(item.getGroup2());
                                    }
                                }
                            }
                            group2List = new ArrayList<>(uq);
                        }
                        setSpinner(binding.divisionSpin, group2List, false);
                        binding.textDev.setVisibility(group2List.isEmpty() ? View.GONE : View.VISIBLE);
                        binding.divisionSpin.setVisibility(group2List.isEmpty() ? View.GONE : View.VISIBLE);
                        setSpinner(binding.subStnSpin, response.body().getOutput().getGroup1().getGroup1(), false);
                        setSpinner(binding.feederIdSpin, response.body().getOutput().getNetworkName().getNetworkId(), true);
                    }
                    if (groupType.equals("Group2")) {
                        setSpinner(binding.subStnSpin, response.body().getOutput().getGroup1().getGroup1(), false);
                        setSpinner(binding.feederIdSpin, response.body().getOutput().getNetworkName().getNetworkId(), true);
                    }
                    if (groupType.equals("Group1")) {
                        setSpinner(binding.feederIdSpin, response.body().getOutput().getNetworkName().getNetworkId(), true);
                    }

                } else if (response.code() == 401) {
                    ErrorPdfLogger.logApiError(DropDownActivity.this, "POST", "dashboard/", "HTTP " + response.code() + " : " + response.message());
                    binding.itemLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    prefManager.setIsUserLogin(false);
                    startActivity(new Intent(DropDownActivity.this, LoginActivity.class));
                    finish();
                } else {
                    ErrorPdfLogger.logApiError(DropDownActivity.this, "POST", "dashboard/", "HTTP " + response.code() + " : " + response.message());
                    binding.itemLayout.setVisibility(View.VISIBLE);
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    Snackbar snack = Snackbar.make(binding.getRoot(), response.message() + " - " + response.code(), Snackbar.LENGTH_INDEFINITE);
                    snack.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NetworkIDModel> call, @NonNull Throwable t) {
                ErrorPdfLogger.logApiFailure(DropDownActivity.this, "POST", "dashboard/", t);
                binding.itemLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                Snackbar.make(binding.getRoot(), getString(R.string.error_msg), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    private void setSpinner(AutoCompleteTextView item, List<String> data, boolean netId) {
        if (data == null) {
            data = new ArrayList<>();
        }
        if (netId) {
            CustomAdapter adapter = new CustomAdapter(this, data);
            adapter.setFromDialog(false);
            item.setAdapter(adapter);
            item.setDropDownVerticalOffset(-item.getHeight() - 14);
            List<String> selectedItems = new ArrayList<>(ResponseDataUtils.NetworkList);
            item.setText("", false);

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
                hideKeyboard();
            });


            item.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().isEmpty()) {
                        selectedItems.clear();
                        ResponseDataUtils.NetworkList.clear();
                    }
                }
            });

        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner, data);
            item.setAdapter(adapter);
            item.setDropDownBackgroundResource(android.R.color.white);

            item.setOnItemClickListener((parent, view, position, id) -> {
                String group = adapter.getItem(position);
                item.setText(group, false);
                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(DropDownActivity.this)) {
                    if (item == binding.regionSpin) {
                        binding.zoneSpin.setText("", false);
                        binding.circleSpin.setText("", false);
                        binding.divisionSpin.setText("", false);
                        binding.subStnSpin.setText("", false);
                        binding.feederIdSpin.setText("", false);
                        getGroup("Group5", group);
                    } else if (item == binding.zoneSpin) {
                        binding.circleSpin.setText("", false);
                        binding.divisionSpin.setText("", false);
                        binding.subStnSpin.setText("", false);
                        binding.feederIdSpin.setText("", false);
                        getGroup("Group4", group);
                    } else if (item == binding.circleSpin) {
                        binding.divisionSpin.setText("", false);
                        binding.subStnSpin.setText("", false);
                        binding.feederIdSpin.setText("", false);
                        getGroup("Group3", group);
                    } else if (item == binding.divisionSpin) {
                        binding.subStnSpin.setText("", false);
                        binding.feederIdSpin.setText("", false);
                        getGroup("Group2", group);
                    } else if (item == binding.subStnSpin) {
                        binding.feederIdSpin.setText("", false);
                        getGroup("Group1", group);
                    }
                } else {
                    final Dialog dialog = new Dialog(DropDownActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.no_internet_dialog);
                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(DropDownActivity.this.getDrawable(R.drawable.pop_background));
                    LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
                    Button RetryBtn = dialog.findViewById(R.id.btnDialog);
                    lottieAnimationView.playAnimation();
                    RetryBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(DropDownActivity.this)) {
                                if (item == binding.regionSpin) {
                                    getGroup("Group5", group);
                                } else if (item == binding.zoneSpin) {
                                    getGroup("Group4", group);
                                } else if (item == binding.circleSpin) {
                                    getGroup("Group3", group);
                                } else if (item == binding.divisionSpin) {
                                    getGroup("Group2", group);
                                } else if (item == binding.subStnSpin) {
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

    private void getRefresh() {
        spinDb.clear();
        projectNames.clear();
        region.clear();
        zone.clear();
        circle.clear();
        division.clear();
        sub.clear();
        ResponseDataUtils.NetworkList.clear();

        binding.databaseSpin.setText("", false);
        binding.projectSpin.setText("", false);
        binding.regionSpin.setText("", false);
        binding.zoneSpin.setText("", false);
        binding.circleSpin.setText("", false);
        binding.divisionSpin.setText("", false);
        binding.subStnSpin.setText("", false);
        binding.feederIdSpin.setText("", false);

        rvAdapter.clear();

        DbName = null;
        prefManager.setDBName(null);

        adapter.notifyDataSetChanged();
        if (binding.feederIdSpin.getAdapter() instanceof CustomAdapter) {
            ((CustomAdapter) binding.feederIdSpin.getAdapter()).clearSelection();
        }
    }

    private void getReset() {
        binding.textReg.setVisibility(View.VISIBLE);
        binding.regionSpin.setVisibility(View.VISIBLE);
        binding.textZone.setVisibility(View.VISIBLE);
        binding.zoneSpin.setVisibility(View.VISIBLE);
        binding.textCircle.setVisibility(View.VISIBLE);
        binding.circleSpin.setVisibility(View.VISIBLE);
        binding.textDev.setVisibility(View.VISIBLE);
        binding.divisionSpin.setVisibility(View.VISIBLE);
        binding.textStn.setVisibility(View.VISIBLE);
        binding.subStnSpin.setVisibility(View.VISIBLE);
    }

    private void showNoInternetDialog() {
        final Dialog dialog = new Dialog(DropDownActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.no_internet_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(DropDownActivity.this.getDrawable(R.drawable.pop_background));
        LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
        Button RetryBtn = dialog.findViewById(R.id.btnDialog);
        lottieAnimationView.playAnimation();
        RetryBtn.setOnClickListener(view -> {
            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(DropDownActivity.this)) {
                getDatabaseList();
                dialog.dismiss();
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ResponseDataUtils.NetworkList.clear();
        rvAdapter.clear();
        if (binding.feederIdSpin.getAdapter() instanceof CustomAdapter) {
            CustomAdapter adapter = (CustomAdapter) binding.feederIdSpin.getAdapter();
            adapter.clearSelection();
            binding.feederIdSpin.setText("", false);
            binding.feederIdSpin.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void isClear(String removedItem) {
        ResponseDataUtils.NetworkList.remove(removedItem);
        List<String> currentItems = rvAdapter.getSelectedItems();
        CustomAdapter adapter = (CustomAdapter) binding.feederIdSpin.getAdapter();
        adapter.clearSelection();

        SpannableStringBuilder builder = new SpannableStringBuilder();
        Drawable cancelIcon = ContextCompat.getDrawable(binding.feederIdSpin.getContext(), R.drawable.close);
        if (cancelIcon != null) {
            int iconSize = (int) (16 * binding.feederIdSpin.getContext().getResources().getDisplayMetrics().density);
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

        binding.feederIdSpin.setText(builder, false);
        binding.feederIdSpin.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_toll_menu, menu);
        menu.getItem(0).setVisible(prefManager.getType().contains("Admin"));
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                prefManager.setIsUserLogin(false);
                startActivity(new Intent(DropDownActivity.this, LoginActivity.class));
                finish();
                break;

            case R.id.nav_dashboard:
                binding.loadNetworkBtn.setVisibility(View.GONE);
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main);
                if (fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .show(fragment)
                            .commit();
                }
                break;

            case R.id.nav_nsc:
                if (prefManager.getType().contains("Admin")) {
                    Intent intent = new Intent(DropDownActivity.this, NewConnection.class);
                    startActivity(intent);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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

    @SuppressLint({"GestureBackNavigation", "MissingSuperCall"})
    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main);

        if (fragment != null && !fragment.isVisible()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(fragment)
                    .commit();

            binding.loadNetworkBtn.setVisibility(View.GONE);
            return;
        }

        if (onBack) {
            if (exitSnackbar != null) exitSnackbar.dismiss();
            finish();
            return;
        }

        this.onBack = true;

        exitSnackbar = Snackbar.make(binding.getRoot(), "Press back again to exit", Snackbar.LENGTH_SHORT);
        exitSnackbar.show();

        new android.os.Handler(getMainLooper()).postDelayed(
                () -> onBack = false,
                2000
        );
    }

}
