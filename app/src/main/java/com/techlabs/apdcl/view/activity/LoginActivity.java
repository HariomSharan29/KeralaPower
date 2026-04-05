package com.techlabs.apdcl.view.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.Utils.custom.ErrorPdfLogger;
import com.techlabs.apdcl.databinding.ActivityLoginBinding;
import com.techlabs.apdcl.models.LoginModel;
import com.techlabs.apdcl.models.ProjectModel;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private PrefManager prefManager;
    private ActivityLoginBinding binding;
    private boolean isPasswordVisible = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setStatusBarTransparent(this);
        prefManager = new PrefManager(LoginActivity.this);

        Glide.with(LoginActivity.this).load(R.raw.electric_tower).into(binding.imageView);

        binding.loginBtn.setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            view = getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            CheckDetails();
        });

        binding.imgVisible.setOnClickListener(v -> togglePasswordVisibility());

    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.passwordEdtTxt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.imgVisible.setImageResource(R.drawable.ic_visibility_off);
        } else {
            binding.passwordEdtTxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.imgVisible.setImageResource(R.drawable.ic_visibility);
        }
        isPasswordVisible = !isPasswordVisible;
        binding.passwordEdtTxt.setSelection(binding.passwordEdtTxt.getText().length());
    }

    private void CheckDetails() {
        binding.userNameEdtTxt.setError(null);
        binding.passwordEdtTxt.setError(null);
        boolean isCancel = false;
        View focusView = null;

        if (binding.passwordEdtTxt.getText().toString().trim().isEmpty()) {
            binding.passwordEdtTxt.setError("Please enter the password");
            focusView = binding.passwordEdtTxt;
            isCancel = true;
        }

        if (binding.userNameEdtTxt.getText().toString().trim().isEmpty()) {
            binding.userNameEdtTxt.setError("Please enter the username");
            focusView = binding.userNameEdtTxt;
            isCancel = true;
        } else if (binding.userNameEdtTxt.getText().toString().trim().length() < 3) {
            binding.userNameEdtTxt.setError("Please enter minimum three letter");
            focusView = binding.userNameEdtTxt;
            isCancel = true;
        }

        if (isCancel) {
            focusView.requestFocus();
        } else {
            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(LoginActivity.this)) {
                SendLoginData(binding.userNameEdtTxt.getText().toString().trim(), binding.passwordEdtTxt.getText().toString().trim());
            } else {
                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.no_internet_dialog);
                dialog.getWindow().setBackgroundDrawable(LoginActivity.this.getDrawable(R.drawable.pop_background));
                LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
                Button RetryBtn = dialog.findViewById(R.id.btnDialog);
                lottieAnimationView.playAnimation();
                RetryBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(LoginActivity.this)) {
                            SendLoginData(binding.userNameEdtTxt.getText().toString().trim(), binding.passwordEdtTxt.getText().toString().trim());
                            dialog.dismiss();
                        }
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
            }
        }
    }

    private void SendLoginData(String userName, String passWord) {
        binding.userNameEdtTxt.setVisibility(View.GONE);
        binding.passwordEdtTxt.setVisibility(View.GONE);
        binding.imgVisible.setVisibility(View.GONE);
        binding.loginBtn.setVisibility(View.GONE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", userName);
        jsonObject.addProperty("password", passWord);
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<LoginModel> call = apiInterface.getLogin(jsonObject);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                if (response.code() == 200) {
                    ErrorPdfLogger.logApiSuccess(LoginActivity.this, "POST", "AdminPanel/user/login/", "HTTP " + response.code() + " : " + response.message());
                    try {
                        LoginModel loginModel = response.body();
                        if (loginModel != null) {
                            if (loginModel.getUsername() != null && !loginModel.getUsername().equals("null") && !loginModel.getUsername().isEmpty()) {
                                prefManager.setUserName(loginModel.getUsername());
                                prefManager.setIsUserLogin(true);
                                prefManager.setType(loginModel.getUsertype());
                                prefManager.setUserType(loginModel.getUsertype());
                                prefManager.setAccessToken(loginModel.getAccess());
                                prefManager.setName(loginModel.getName());
                                prefManager.setEmail(loginModel.getEmail());
                                prefManager.setLastLogin(loginModel.getStartTime());

                                getProjectList(loginModel.getAccess(), loginModel.getUsername());
                                startActivity(new Intent(LoginActivity.this, SplashScreen.class));
                                finish();
                            } else {
                                binding.userNameEdtTxt.setVisibility(View.VISIBLE);
                                binding.passwordEdtTxt.setVisibility(View.VISIBLE);
                                binding.imgVisible.setVisibility(View.VISIBLE);
                                binding.loginBtn.setVisibility(View.VISIBLE);
                                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Incorrect username and password", Snackbar.LENGTH_SHORT);
                                snack.show();
                            }
                        }
                    } catch (Exception e) {
                        ErrorPdfLogger.logCrash(LoginActivity.this, e);
                        Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                    }
                } else {
                    ErrorPdfLogger.logApiError(LoginActivity.this, "POST", "AdminPanel/user/login/", "HTTP " + response.code() + " : " + response.message());
                    binding.userNameEdtTxt.setVisibility(View.VISIBLE);
                    binding.passwordEdtTxt.setVisibility(View.VISIBLE);
                    binding.imgVisible.setVisibility(View.VISIBLE);
                    binding.loginBtn.setVisibility(View.VISIBLE);
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                ErrorPdfLogger.logApiFailure(LoginActivity.this, "POST", "AdminPanel/user/login/", t);
                binding.userNameEdtTxt.setVisibility(View.VISIBLE);
                binding.passwordEdtTxt.setVisibility(View.VISIBLE);
                binding.imgVisible.setVisibility(View.VISIBLE);
                binding.loginBtn.setVisibility(View.VISIBLE);
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                snack.show();
            }
        });
    }
    private void getProjectList(String accessToken, String userId) {
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<ProjectModel> call = apiInterface.getProject("Bearer " + accessToken, userId);
        call.enqueue(new Callback<ProjectModel>() {
            @Override
            public void onResponse(@NonNull Call<ProjectModel> call, @NonNull Response<ProjectModel> response) {
                if (response.code() == 200 && response.body() != null) {
                    List<ProjectModel.ProjectName> projectList = response.body().getProjectName();
                    for (ProjectModel.ProjectName project : projectList) {
                        if ("Propose".equalsIgnoreCase(project.getServerType())) {
                            prefManager.setDBName(project.getDatabase());
                            prefManager.setProjectName(project.getProjectName());
                        }
                    }
                } else if (response.code() == 401) {
                    prefManager.setIsUserLogin(false);
                    startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Snackbar snack = Snackbar.make(binding.getRoot(), response.message() + " - " + response.code(), Snackbar.LENGTH_INDEFINITE);
                    snack.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProjectModel> call, @NonNull Throwable t) {
                getProjectList(accessToken, userId);
                Snackbar.make(binding.getRoot(), getString(R.string.error_msg), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public static void setStatusBarTransparent(AppCompatActivity activity) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        );

        window.setStatusBarColor(Color.parseColor("#183883"));
    }
}
