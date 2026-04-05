package com.techlabs.apdcl.view.fragment;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.Utils.custom.VoltageDonutView;
import com.techlabs.apdcl.databinding.FragmentDashboardBinding;
import com.techlabs.apdcl.models.ProjectModel;
import com.techlabs.apdcl.models.dashboard.DatabaseModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;
import com.techlabs.apdcl.view.activity.LoginActivity;
import com.techlabs.apdcl.view.activity.Profile;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashboardFragment extends Fragment {

    public FragmentDashboardBinding binding;
    private PrefManager prefManager;

    public DashboardFragment() {}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefManager = new PrefManager(requireContext());
        binding.toolbar.setTitle("Dashboard");
        binding.toolbar.inflateMenu(R.menu.dashboard_menu);
        binding.shimmerView.startShimmer();

        MenuItem profileItem = binding.toolbar.getMenu().findItem(R.id.menu_profile);
        MenuItem featuresItem = binding.toolbar.getMenu().findItem(R.id.menu_features);
        featuresItem.getIcon().setTint(getResources().getColor(R.color.white));

        if (profileItem != null) {
            profileItem.setActionView(R.layout.profile);
            View profileView = profileItem.getActionView();

            assert profileView != null;
            TextView userLetter = profileView.findViewById(R.id.userProfile);
            String name = prefManager.getName();
            userLetter.setText(name != null && !name.isEmpty() ? name.substring(0, 1).toUpperCase() : "U");
            profileView.setOnClickListener(v -> {
                startActivity(new Intent(requireActivity(), Profile.class));
            });

        } else {
            Log.e("DashboardFragment", "menu_profile item NOT FOUND");
        }
        binding.swipeRefresh.setColorSchemeResources(R.color.blue, R.color.fuse_color);
        binding.toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (item.getItemId() == R.id.menu_features) {

                Fragment fragment = requireActivity()
                        .getSupportFragmentManager()
                        .findFragmentById(R.id.main);

                if (fragment != null) {
                    requireActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .hide(fragment)
                            .commit();
                }

                requireActivity().findViewById(R.id.loadNetworkBtn)
                        .setVisibility(View.VISIBLE);

                return true;
            }

            if (id == R.id.menu_profile) {
                startActivity(new Intent(requireActivity(), Profile.class));
                return true;
            }
            if (id == R.id.menu_profile) {
                return true;
            }
            return false;
        });
        binding.swipeRefresh.setOnRefreshListener(this::getDashboardData);

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(requireActivity())) {
            getProject();
        } else {
            Snackbar.make(requireView(), requireContext().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", v -> {
                        getProject();
                    }).show();
        }
    }

    private void getProject() {
        binding.dashboardLayout.setVisibility(View.GONE);
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<ProjectModel> call = apiInterface.getProject("Bearer " + prefManager.getAccessToken(), prefManager.getUserName());
        call.enqueue(new Callback<ProjectModel>() {
            @Override
            public void onResponse(@NonNull Call<ProjectModel> call, @NonNull Response<ProjectModel> response) {
                if (response.code() == 200) {
                    ProjectModel projectModel = response.body();
                    assert projectModel != null;
                    List<ProjectModel.ProjectName> projectList = projectModel.getProjectName();
                    for (ProjectModel.ProjectName project : projectList) {
                        if ("Survey".equalsIgnoreCase(project.getServerType())) {
                            prefManager.setDatabaseSurvey(project.getDatabase());
                        } else if ("Production".equalsIgnoreCase(project.getServerType())) {
                            prefManager.setProjectName(project.getProjectName());
                        }
                    }
                    getDashboardData();
                } else if (response.code() == 401) {
                    prefManager.setIsUserLogin(false);
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    Snackbar.make(binding.getRoot(), response.code() + " - " + getString(R.string.error_msg), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProjectModel> call, @NonNull Throwable t) {
                Snackbar.make(binding.getRoot(), getString(R.string.error_msg), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void getDashboardData() {
        binding.dashboardLayout.setVisibility(View.GONE);
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

        Call<DatabaseModel> call = apiInterface.DatabaseList(
                "Bearer " + prefManager.getAccessToken(),
                jsonObject
        );

        call.enqueue(new Callback<DatabaseModel>() {
            @Override
            public void onResponse(@NonNull Call<DatabaseModel> call, @NonNull Response<DatabaseModel> response) {
                binding.swipeRefresh.setRefreshing(false);
                binding.dashboardLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);

                if (response.code() == 200 && response.body() != null) {
                    DatabaseModel.Output data = response.body().getOutput();

                    valueAnimator(binding.txtActualKva, data.getCustomeraLoad() != null ? data.getCustomeraLoad().getActualKVA() : 0.0, 1000);
                    valueAnimator(binding.txtConnectedKva, data.getCustomeraLoad() != null ? data.getCustomeraLoad().getConnectedKVA() : 0.0, 1000);
                    valueAnimator(binding.txtCustomer, data.getCustomerCountall() != null ? data.getCustomerCountall().getConsumerCount() : 0, 1000);
                    valueAnimator(binding.txtSubstation, data.getNetworkinfo() != null ? data.getNetworkinfo().getGroup1Count() : 0, 1000);
                    valueAnimator(binding.txtFeeder, data.getNetworkinfo() != null ? data.getNetworkinfo().getNetworkIdCount() : 0, 1000);
                    valueAnimator(binding.txtDtCount, data.getDTCount() != null ? data.getDTCount().getDTCount() : 0, 1000);

                    double cableLength = data.getCableLen() != null ? data.getCableLen().getLength() : 0.0;
                    double overheadLength = data.getOverheadlen() != null ? data.getOverheadlen().getLength() : 0.0;
                    double totalLength = cableLength + overheadLength;

                    valueAnimator(binding.txtCableLength, cableLength, 1000);
                    valueAnimator(binding.txtOverHeadLength, overheadLength, 1000);
                    valueAnimator(binding.txtTotalLength, totalLength, 1000);

                    updatePoleChart(data.getNetworkinfo() != null ? data.getNetworkinfo().getVoltageWise() : null);

                } else if (response.code() == 401) {
                    prefManager.setIsUserLogin(false);
                    startActivity(new Intent(requireActivity(), LoginActivity.class));
                    requireActivity().finish();
                } else {
                    Snackbar.make(binding.getRoot(), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DatabaseModel> call, @NonNull Throwable t) {
                binding.dashboardLayout.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
                Snackbar.make(binding.getRoot(), "Something went wrong. Try again.", Snackbar.LENGTH_LONG).show();
            }
        });
    }
    private void valueAnimator(final TextView textView, double targetValue, int duration) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, (float) targetValue);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            if (textView.getId() == R.id.txtCableLength || textView.getId() == R.id.txtOverHeadLength
                    || textView.getId() == R.id.txtTotalLength) {
                textView.setText(String.format("%.2f km", animatedValue));
            } else {
                textView.setText(String.valueOf(Math.round(animatedValue)));
            }
        });
        animator.start();
    }
    private void updatePoleChart(List<DatabaseModel.VoltageWise> voltageWise) {

        binding.donutContainer.removeAllViews();
        if (voltageWise == null || voltageWise.isEmpty()) return;
        DatabaseModel.VoltageWise v132_33 = null;
        DatabaseModel.VoltageWise v33 = null;
        DatabaseModel.VoltageWise v33_11 = null;
        DatabaseModel.VoltageWise v11 = null;

        for (DatabaseModel.VoltageWise item : voltageWise) {

            if (item == null || item.getGroup2() == null) continue;

            String group = item.getGroup2().trim();
            String groupNormalized = group.replace(" ", "").toUpperCase();

            if (groupNormalized.contains("132")) {
                v132_33 = item;
            } else if (groupNormalized.equals("33") || groupNormalized.equals("33KV")) {
                v33 = item;
            } else if (groupNormalized.contains("33/11")) {
                v33_11 = item;
            } else if (groupNormalized.contains("11")) {
                v11 = item;
            }
        }

        List<DatabaseModel.VoltageWise> ordered = new java.util.ArrayList<>();
        if (v132_33 != null) ordered.add(v132_33);
        if (v33 != null) ordered.add(v33);
        if (v33_11 != null) ordered.add(v33_11);
        if (v11 != null) ordered.add(v11);

        for (DatabaseModel.VoltageWise item : voltageWise) {
            if (item == null || item.getGroup2() == null) continue;
            if (!ordered.contains(item)) {
                ordered.add(item);
            }
        }

        for (DatabaseModel.VoltageWise item : ordered) {

            if (item == null || item.getGroup2() == null) continue;

            String group = item.getGroup2().trim();
            String groupNormalized = group.replace(" ", "").toUpperCase();

            int feeder = item.getNetworkType0Count() != null
                    ? item.getNetworkType0Count() : 0;

            int pss = item.getNetworkType1Count() != null
                    ? item.getNetworkType1Count() : 0;

            int feederColor = 0;
            int pssColor = 0;

            if (groupNormalized.contains("11")) {
                feederColor = R.color.feeder_default;
                pssColor = R.color.pss;
            }

            if (groupNormalized.contains("33")) {
                feederColor = R.color.feeder_default;
                pssColor = R.color.pss;
            }

            if (groupNormalized.contains("132")) {
                feederColor = R.color.feeder_default;
                pssColor = R.color.pss;
            }

            addDonut(group, feeder, pss,
                    feederColor == 0 ? R.color.feeder_11 : feederColor,
                    pssColor == 0 ? R.color.pss_default : pssColor);
        }

    }
    @SuppressLint("SetTextI18n")
    private void addDonut(String title, int feeder, int pss, int feederColor, int pssColor) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.voltage_donut_item, binding.donutContainer, false);

        TextView txtVoltage = view.findViewById(R.id.txtVoltage);
        VoltageDonutView donut = view.findViewById(R.id.donut);
        LinearLayout legendContainer = view.findViewById(R.id.legendContainer);

        donut.setData(feeder, pss, feederColor, pssColor);

        int bgColorRes;
        if (title.startsWith("132/33")) {
            bgColorRes = R.color.pss_132;

        } else if (title.startsWith("33/11")) {
            bgColorRes = R.color.pss_33;

        } else if (title.startsWith("33")) {
            bgColorRes = R.color.feeder_33;

        } else {
            bgColorRes = R.color.feeder_default;
        }
        int resolvedBgColor = getResources().getColor(bgColorRes);

        String displayTitle = title;
        String normalized = displayTitle.replace(" ", "").toUpperCase();
        if (!normalized.contains("KV")) {
            displayTitle = displayTitle + " kV";
        }

        txtVoltage.setText(displayTitle);
        txtVoltage.setTypeface(null, android.graphics.Typeface.BOLD);
        txtVoltage.setTextColor(Color.BLACK);
        txtVoltage.setPadding(18, 8, 18, 8);

        txtVoltage.setBackgroundResource(R.drawable.bg_voltage_label);

        android.graphics.drawable.GradientDrawable drawable =
                (android.graphics.drawable.GradientDrawable) txtVoltage.getBackground().mutate();

        drawable.setColor(Color.argb(40,
                Color.red(resolvedBgColor),
                Color.green(resolvedBgColor),
                Color.blue(resolvedBgColor)
        ));

        drawable.setStroke((int) (getResources().getDisplayMetrics().density), Color.argb(180,
                Color.red(resolvedBgColor),
                Color.green(resolvedBgColor),
                Color.blue(resolvedBgColor)
        ));

        legendContainer.removeAllViews();

        if (feeder > 0) {
            TextView tv = new TextView(getContext());
            tv.setText("● Feeder");
            tv.setTextSize(12f);
            tv.setTypeface(null, android.graphics.Typeface.BOLD);
            tv.setTextColor(getResources().getColor(feederColor));
            tv.setPadding(0, 4, 16, 4);
            legendContainer.addView(tv);
        }

        if (pss > 0) {
            TextView tv = new TextView(getContext());
            tv.setText("● PSS");
            tv.setTextSize(12f);
            tv.setTypeface(null, android.graphics.Typeface.BOLD);
            tv.setTextColor(getResources().getColor(pssColor));
            tv.setPadding(0, 4, 16, 4);
            legendContainer.addView(tv);
        }

        binding.donutContainer.addView(view);
    }

}
