package com.techlabs.apdcl.view.activity;

import static com.techlabs.apdcl.Utils.Config.isTreeNode;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.Utils.custom.ErrorPdfLogger;
import com.techlabs.apdcl.adapters.NewConnectionAdapter;
import com.techlabs.apdcl.databinding.ActivityNewConnectionBinding;
import com.techlabs.apdcl.models.nsc.NewConnectionModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewConnection extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ActivityNewConnectionBinding binding;
    private NewConnectionAdapter newConnectionAdapter;
    private PrefManager prefManager;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewConnectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(NewConnection.this);
        setSupportActionBar(binding.toolbar);
        binding.refreshLayout.setOnRefreshListener(this);
        binding.refreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.blue));

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(NewConnection.this)) {
            getNewConnection();
        } else {
            final Dialog dialog = new Dialog(NewConnection.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.no_internet_dialog);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(NewConnection.this.getDrawable(R.drawable.pop_background));
            LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
            Button RetryBtn = dialog.findViewById(R.id.btnDialog);
            lottieAnimationView.playAnimation();
            RetryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(NewConnection.this)) {
                        getNewConnection();
                        dialog.dismiss();
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        binding.connectionFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newConnectionAdapter != null && newText != null) {
                    newConnectionAdapter.filter(newText);
                }
                return true;
            }
        });

    }

    private void getNewConnection() {
        binding.shimmerViewContainer.startShimmer();
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserType", prefManager.getType());
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        jsonObject.addProperty("DatabaseType", "NewConnection");
        Call<NewConnectionModel> call = apiInterface.getNewConnectionData("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<NewConnectionModel>() {
            @Override
            public void onResponse(@NonNull Call<NewConnectionModel> call, @NonNull Response<NewConnectionModel> response) {
                if (response.code() == 200) {
                    ErrorPdfLogger.logNewConnection(NewConnection.this, "POST", "MSEDCL_NC/networklist/", "New Connection data");
                    binding.shimmerViewContainer.stopShimmer();
                    binding.shimmerViewContainer.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.refreshLayout.setRefreshing(false);
                    NewConnectionModel newConnectionModel = response.body();
                    assert newConnectionModel != null;
                    if (newConnectionModel.getOutput() != null && !newConnectionModel.getOutput().isEmpty()) {
//                        prefManager.setDBName(newConnectionModel.getDatabase());
//                        prefManager.setProjectName("MSEDCL");
                        List<NewConnectionModel.Output> filteredList = new ArrayList<>();
                        for (NewConnectionModel.Output item : newConnectionModel.getOutput()) {
                            if (item.getSanctionedLoad() != null) {
                                try {
                                    int sanc = Integer.parseInt(item.getSanctionedLoad().trim());
                                    if (sanc > 5 && sanc < 10) {
                                        filteredList.add(item);
                                    }
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (!filteredList.isEmpty()) {
                            newConnectionAdapter = new NewConnectionAdapter(NewConnection.this, filteredList);
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(NewConnection.this));
                            binding.recyclerView.setHasFixedSize(true);
                            binding.recyclerView.setAdapter(newConnectionAdapter);
                            binding.recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            binding.recyclerView.setVisibility(View.GONE);
                        }
                    }
                } else if (response.code() == 401) {
                    prefManager.setIsUserLogin(false);
                    startActivity(new Intent(NewConnection.this, LoginActivity.class));
                    finish();
                } else {
                    ErrorPdfLogger.logNewConnection(NewConnection.this, "POST", "MSEDCL_NC/networklist/", "HTTP " + response.code() + " : " + response.message());
                    binding.shimmerViewContainer.stopShimmer();
                    binding.shimmerViewContainer.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewConnectionModel> call, @NonNull Throwable t) {
                ErrorPdfLogger.logNewConnection(NewConnection.this, "POST", "MSEDCL_NC/networklist/", Log.getStackTraceString(t));
                binding.shimmerViewContainer.stopShimmer();
                binding.shimmerViewContainer.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                snack.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_toll_menu, menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(2).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_logout) {
            prefManager.setIsUserLogin(false);
            startActivity(new Intent(NewConnection.this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getNewConnection();
                Snackbar.make(binding.getRoot(), "List Update", Snackbar.LENGTH_LONG).show();
            }
        }, 3000);
    }

}