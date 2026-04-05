package com.techlabs.apdcl.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.databinding.ActivityProfileBinding;
import com.techlabs.apdcl.models.Logout;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Profile extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(this);

        // ✅ Single call — clean
        loadProfileData();

        binding.btnLogout.setOnClickListener(v -> {
            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(this)) {
                logout();
            } else {
                Snackbar.make(binding.getRoot(), getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", v1 -> {
                            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(Profile.this)) {
                                logout();
                            }
                        }).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadProfileData() {
        try {
            // ✅ Name — from loginModel.getName()
            String name = prefManager.getName();
            binding.profileName.setText((name != null && !name.isBlank()) ? name : "");

            // ✅ UserType | ID — from loginModel.getUsertype() + loginModel.getUsername()
            String userType = prefManager.getUserType();
            String userName = prefManager.getUserName();
            if (userType != null && !userType.isBlank() && userName != null && !userName.isBlank()) {
                binding.empProfileIdTv.setText(userType + " | ID: " + userName);
            } else if (userName != null && !userName.isBlank()) {
                binding.empProfileIdTv.setText("ID: " + userName);
            } else {
                binding.empProfileIdTv.setText("");
            }

            // ✅ Email — from loginModel.getEmail()
            String email = prefManager.getEmail();
            binding.emailTv.setText((email != null && !email.isBlank()) ? email : "");

            // ❌ Mobile — API me nahi hai
            binding.mobileNoTv.setText("");

            // ❌ Place of Work — API me nahi hai
            binding.placeWorkTv.setText("");

            // ❌ Region, Zone, Circle — API me nahi hai
            binding.regionDivisionCircleTv.setText("");

            // ✅ Last Login — from loginModel.getStartTime()
            String lastLogin = prefManager.getLastLogin();
            if (lastLogin != null && !lastLogin.isBlank()) {
                binding.tvLastLogin.setText(ResponseDataUtils.formatDateTime(lastLogin));
            } else {
                binding.tvLastLogin.setText("");
            }

            // ❌ Last Logout — API me nahi hai
            binding.tvLastLogout.setText("");

        } catch (Exception e) {
            Log.d("ProfileException", Objects.requireNonNull(e.getLocalizedMessage()));
        }
    }

    private void logout() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", prefManager.getAccessToken());
        Retrofit retrofit = RetrofitClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Logout> call = apiInterface.logout(jsonObject);
        call.enqueue(new Callback<Logout>() {
            @Override
            public void onResponse(@NonNull Call<Logout> call, @NonNull Response<Logout> response) {
                if (response.code() == 200) {
                    Logout logout = response.body();
                    assert logout != null;
                    if (logout.getMessage().equalsIgnoreCase("Logout successful")) {
                        clearPrefAndGoToLogin();
                    }
                } else if (response.code() == 401) {
                    prefManager.setIsUserLogin(false);
                    startActivity(new Intent(Profile.this, LoginActivity.class));
                    finish();
                } else {
                    showErrorToast(response.message() + " - " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Logout> call, @NonNull Throwable t) {
                showErrorToast(getString(R.string.error));
            }
        });
    }

    private void clearPrefAndGoToLogin() {
        prefManager.setIsUserLogin(false);
        prefManager.setUserType(null);
        prefManager.setAccessToken(null);
        prefManager.setUserName(null);
        prefManager.setType(null);
        prefManager.setName(null);
        prefManager.setDesignation(null);
        prefManager.setEmail(null);
        prefManager.setMobile(null);
        prefManager.setDate_Joined(null);
        prefManager.setPlace_Of_Work(null);
        prefManager.setRegion(null);
        prefManager.setZone(null);
        prefManager.setCircle(null);
        prefManager.setLastLogin(null);
        prefManager.setLastLogout(null);
        startActivity(new Intent(Profile.this, LoginActivity.class));
        finish();
    }

    @SuppressLint("InflateParams")
    private void showErrorToast(String headerMessage) {
        View layout = LayoutInflater.from(Profile.this).inflate(R.layout.toast_layout, null);
        TextView ok = layout.findViewById(R.id.okBtn);
        TextView header = layout.findViewById(R.id.headerTv);
        TextView description = layout.findViewById(R.id.descripTv);
        header.setText(headerMessage);
        description.setText(getString(R.string.error_msg));
        ok.setOnClickListener(v -> logout());
        Toast toast = new Toast(Profile.this);
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}