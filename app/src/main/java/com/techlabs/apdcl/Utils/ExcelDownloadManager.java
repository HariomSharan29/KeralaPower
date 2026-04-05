package com.techlabs.apdcl.Utils;

import com.google.gson.JsonObject;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ExcelDownloadManager {

    private ApiInterface apiService;
    private PrefManager prefManager;

    public ExcelDownloadManager(PrefManager prefManager) {
        this.prefManager = prefManager;
        apiService = RetrofitClient.getClient().create(ApiInterface.class);
    }

    public void downloadExcel(JsonObject jsonObject, Callback<ResponseBody> callback) {
        String token = prefManager.getAccessToken();
        Call<ResponseBody> call = apiService.downloadExcel("Bearer " + token, jsonObject);
        call.enqueue(callback);
    }
}
