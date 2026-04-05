package com.techlabs.apdcl.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.DataCallback;
import com.techlabs.apdcl.Utils.DeviceArgument;
import com.techlabs.apdcl.Utils.ExcelDownloadManager;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.custom.ErrorPdfLogger;
import com.techlabs.apdcl.adapters.report.AbnormalConditionAdapter;
import com.techlabs.apdcl.adapters.report.DetailedReportAdapter;
import com.techlabs.apdcl.adapters.report.FaultFlowDetailedAdapter;
import com.techlabs.apdcl.adapters.report.OverLoadTransformerAdapter;
import com.techlabs.apdcl.adapters.report.OverLoadedConstructorAdapter;
import com.techlabs.apdcl.adapters.report.OverLoadedLinesAndCablesAdapter;
import com.techlabs.apdcl.adapters.report.ShortCircuitDetailedAdapter;
import com.techlabs.apdcl.adapters.report.TransformerReportAdapter;
import com.techlabs.apdcl.databinding.ActivityReportsBinding;
import com.techlabs.apdcl.models.DashBoardModel;
import com.techlabs.apdcl.models.analysis.FaultFlowDetailedModel;
import com.techlabs.apdcl.models.analysis.ShortCircuitDetailedModel;
import com.techlabs.apdcl.models.report.AbnormalReport;
import com.techlabs.apdcl.models.report.DetailedReport;
import com.techlabs.apdcl.models.report.OverLoadConductorReport;
import com.techlabs.apdcl.models.report.OverLoadLineAndCablesReport;
import com.techlabs.apdcl.models.report.OverLoadTransformerReport;
import com.techlabs.apdcl.models.report.TransformerReport;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reports extends AppCompatActivity implements DeviceArgument, DataCallback {

    private ActivityReportsBinding binding;
    private final String[] loadFlowValue = {"Load Flow - Abnormal conditions", "Load Flow - Feeder loading", "Load Flow - Detailed", "Load Flow - Overloaded conductors", "Load Flow - Overloaded lines and cables", "Load Flow - Overloaded transformers", "Load Flow - Transformers"};
    private final String[] loadAllocation = {"Load Allocation Detail"};
    private String reportName = null;
    private AbnormalReport abnormalReport;
    private DetailedReport detailedReport;
    private OverLoadConductorReport overLoadConductorReport;
    private OverLoadLineAndCablesReport overLoadLineAndCablesReport;
    private OverLoadTransformerReport overLoadTransformerReport;
    private TransformerReport transformerReport;
    private ShortCircuitDetailedModel shortCircuitDetailedModel;
    private FaultFlowDetailedModel faultFlowDetailedModel;
    private TransformerReportAdapter transformerReportAdapter;
    private AbnormalConditionAdapter abnormalConditionAdapter;
    private DetailedReportAdapter detailedReportAdapter;
    private OverLoadedConstructorAdapter constructorAdapter;
    private OverLoadedLinesAndCablesAdapter overLoadedLinesAndCablesAdapter;
    private OverLoadTransformerAdapter overLoadTransformerAdapter;
    private ShortCircuitDetailedAdapter shortCircuitDetailedAdapter;
    private FaultFlowDetailedAdapter faultFlowDetailedAdapter;
    private PrefManager prefManager;
    private DashBoardModel dashBoardModel;
    private ExcelDownloadManager downloadManager;
    private String selectedFilterType = "Load Flow - Abnormal conditions";
    private static final int REQUEST_CODE_OPEN_DIRECTORY = 1;
    private String selectedReport = null;
    private static final String ROOT_FOLDER = "T-LABS-NetAnalysis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(Reports.this);
        String reportSelection = prefManager.getReportSelection();
        binding.toolbar.setTitle("Reports");
        setSupportActionBar(binding.toolbar);

        Intent intent = getIntent();
        if (intent.getStringExtra("Type") != null) {
            if (Objects.requireNonNull(intent.getStringExtra("Type")).contains("loadflow")) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Reports.this, R.layout.custom_spinner, loadFlowValue);
                binding.loadFlowValue.setAdapter(adapter);
            } else {
                List<String> spinnerItems = new ArrayList<>();
                if (reportSelection.equals("Short-Circuit - Detailed")) {
                    spinnerItems.add("Short-Circuit - Detailed");
                } else if (reportSelection.equals("Short-Circuit - Fault Flow Detailed")) {
                    spinnerItems.add("Short-Circuit - Fault Flow Detailed");
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Reports.this, R.layout.custom_spinner, spinnerItems);
                binding.loadFlowValue.setAdapter(adapter);
            }
            if(Objects.requireNonNull(intent.getStringExtra("Type")).contains("loadallocation")){
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Reports.this, R.layout.custom_spinner, loadAllocation);
                binding.loadFlowValue.setAdapter(adapter);
            }
        }

        binding.analysisReport.setHasFixedSize(true);
        binding.analysisReport.setNestedScrollingEnabled(true);
        binding.analysisReport.setLayoutManager(new LinearLayoutManager(Reports.this));

        binding.loadFlowValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedReport = adapterView.getItemAtPosition(i).toString();
                onFilterTypeSelected(selectedReport);
                binding.shimmerView.setVisibility(View.VISIBLE);
                binding.shimmerView.startShimmer();
                binding.analysisReport.setVisibility(View.GONE);

                if (selectedReport.contains("Load Flow - Abnormal conditions")) {
                    reportName = selectedReport;
                    getAbnormalConditionsReports(selectedReport);
                    binding.downloadReportBtn.setVisibility(View.VISIBLE);
                    binding.analysisReport.setVisibility(View.GONE);
                    binding.shimmerView.setVisibility(View.VISIBLE);
                    binding.shimmerView.startShimmer();
                }

                if (selectedReport.contains("Load Flow - Feeder loading")) {
                    reportName = selectedReport;
                    getFeederLoading(selectedReport);
                    binding.downloadReportBtn.setVisibility(View.GONE);
                    binding.analysisReport.setVisibility(View.GONE);
                    binding.shimmerView.setVisibility(View.VISIBLE);
                    binding.shimmerView.startShimmer();
                }

                if (selectedReport.contains("Load Flow - Detailed")) {
                    reportName = selectedReport;
                    getDetailedReports(selectedReport);
                    binding.downloadReportBtn.setVisibility(View.VISIBLE);
                    binding.analysisReport.setVisibility(View.GONE);
                    binding.shimmerView.setVisibility(View.VISIBLE);
                    binding.shimmerView.startShimmer();
                }

                if (selectedReport.contains("Load Flow - Overloaded conductors")) {
                    reportName = selectedReport;
                    getOverloadedConductorsReports(selectedReport);
                    binding.downloadReportBtn.setVisibility(View.VISIBLE);
                    binding.analysisReport.setVisibility(View.GONE);
                    binding.shimmerView.setVisibility(View.VISIBLE);
                    binding.shimmerView.startShimmer();
                }

                if (selectedReport.contains("Load Flow - Overloaded lines and cables")) {
                    reportName = selectedReport;
                    getOverLoadedlinesAndCablesReports(selectedReport);
                    binding.downloadReportBtn.setVisibility(View.VISIBLE);
                    binding.analysisReport.setVisibility(View.GONE);
                    binding.shimmerView.setVisibility(View.VISIBLE);
                    binding.shimmerView.startShimmer();
                }

                if (selectedReport.contains("Load Flow - Overloaded transformers")) {
                    reportName = selectedReport;
                    getOverLoadedTransformer(selectedReport);
                    binding.downloadReportBtn.setVisibility(View.VISIBLE);
                    binding.analysisReport.setVisibility(View.GONE);
                    binding.shimmerView.setVisibility(View.VISIBLE);
                    binding.shimmerView.startShimmer();
                }

                if (selectedReport.contains("Load Flow - Transformers")) {
                    reportName = selectedReport;
                    getTransformerReports(selectedReport);
                    binding.downloadReportBtn.setVisibility(View.VISIBLE);
                    binding.analysisReport.setVisibility(View.GONE);
                    binding.shimmerView.setVisibility(View.VISIBLE);
                    binding.shimmerView.startShimmer();
                }

                if (selectedReport.contains("Short-Circuit - Detailed")) {
                    reportName = selectedReport;
                    getShortCircuitDetailed(selectedReport);
                    binding.downloadReportBtn.setVisibility(View.VISIBLE);
                    binding.analysisReport.setVisibility(View.GONE);
                    binding.shimmerView.setVisibility(View.VISIBLE);
                    binding.shimmerView.startShimmer();
                }

                if (selectedReport.contains("Short-Circuit - Fault Flow Detailed")) {
                    reportName = selectedReport;
                    getFaultFlowDetailed(selectedReport);
                    binding.downloadReportBtn.setVisibility(View.VISIBLE);
                    binding.analysisReport.setVisibility(View.GONE);
                    binding.shimmerView.setVisibility(View.VISIBLE);
                    binding.shimmerView.startShimmer();
                }

                if (selectedReport.contains("Load Allocation Detail")) {
                    reportName = selectedReport;
                    getLoadAllocationDetailed(selectedReport);
                    binding.downloadReportBtn.setVisibility(View.VISIBLE);
                    binding.analysisReport.setVisibility(View.GONE);
                    binding.shimmerView.setVisibility(View.VISIBLE);
                    binding.shimmerView.startShimmer();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                if (newText.length() >= 3 || newText.isEmpty()) {
                    switch (selectedFilterType) {
                        case "Load Flow - Abnormal conditions":
                            AbnormalFilters(newText);
                            break;
                        case "Load Flow - Detailed":
                            DetailedFilters(newText);
                            break;
                        case "Load Flow - Overloaded conductors":
                            OverloadedConductorsFilters(newText);
                            break;
                        case "Load Flow - Overloaded lines and cables":
                            OverloadedLinesAndCablesFilters(newText);
                            break;
                        case "Load Flow - Overloaded transformers":
                            overloadedTransformersFilters(newText);
                            break;
                        case "Load Flow - Transformers":
                            TransformersFilters(newText);
                            break;
                        case "Short-Circuit - Detailed":
                            shortCircuitFilters(newText);
                            break;
                        case "Short-Circuit - Fault Flow Detailed":
                            faultFlowFilters(newText);
                            break;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*binding.downloadReportBtn.setOnClickListener(v -> {
            binding.downloadReportBtn.setVisibility(View.GONE);
            binding.downloadProgress.setVisibility(View.VISIBLE);
            try {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
                if (selectedReport != null) {
                    if (selectedReport.contains("Load Flow - Abnormal conditions")) {
                        if (abnormalReport != null && !abnormalReport.getOutput().getData().isEmpty()) {
                            GeneratePdf();
                        } else {
                            binding.downloadReportBtn.setVisibility(View.VISIBLE);
                            binding.downloadProgress.setVisibility(View.GONE);
                            Snackbar.make(binding.getRoot(), "No Found Load Flow - Abnormal conditions", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    if (selectedReport.contains("Load Flow - Detailed")) {
                        if (detailedReport != null && !detailedReport.getOutput().getData().isEmpty()) {
                            GeneratePdf();
                        } else {
                            binding.downloadReportBtn.setVisibility(View.VISIBLE);
                            binding.downloadProgress.setVisibility(View.GONE);
                            Snackbar.make(binding.getRoot(), "No Found Load Flow - Detailed", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    if (selectedReport.contains("Load Flow - Overloaded conductors")) {
                        if (overLoadConductorReport != null && !overLoadConductorReport.getOutput().getData().isEmpty()) {
                            GeneratePdf();
                        } else {
                            binding.downloadReportBtn.setVisibility(View.VISIBLE);
                            binding.downloadProgress.setVisibility(View.GONE);
                            Snackbar.make(binding.getRoot(), "No Found Load Flow - Overloaded conductors", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    if (selectedReport.contains("Load Flow - Overloaded lines and cables")) {
                        if (overLoadLineAndCablesReport != null && !overLoadLineAndCablesReport.getOutput().getData().isEmpty()) {
                            GeneratePdf();
                        } else {
                            binding.downloadReportBtn.setVisibility(View.VISIBLE);
                            binding.downloadProgress.setVisibility(View.GONE);
                            Snackbar.make(binding.getRoot(), "No Found Load Flow - Overloaded lines and cables", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    if (selectedReport.contains("Load Flow - Overloaded transformers")) {
                        if (overLoadTransformerReport != null && !overLoadTransformerReport.getOutput().getData().isEmpty()) {
                            GeneratePdf();
                        } else {
                            binding.downloadReportBtn.setVisibility(View.VISIBLE);
                            binding.downloadProgress.setVisibility(View.GONE);
                            Snackbar.make(binding.getRoot(), "No Found Load Flow - Overloaded transformers", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    if (selectedReport.contains("Load Flow - Transformers")) {
                        if (transformerReport != null && !transformerReport.getOutput().getData().isEmpty()) {
                            GeneratePdf();
                        } else {
                            binding.downloadReportBtn.setVisibility(View.VISIBLE);
                            binding.downloadProgress.setVisibility(View.GONE);
                            Snackbar.make(binding.getRoot(), "No Found Load Flow - Transformers", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    if (selectedReport.contains("Short-Circuit - Detailed")) {
                        if (shortCircuitDetailedModel != null && !shortCircuitDetailedModel.getOutput().getData().isEmpty()) {
                            GeneratePdf();
                        } else {
                            binding.downloadReportBtn.setVisibility(View.VISIBLE);
                            binding.downloadProgress.setVisibility(View.GONE);
                            Snackbar.make(binding.getRoot(), "No Found Short-Circuit - Detailed", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    if (selectedReport.contains("Short-Circuit - Fault Flow Detailed")) {
                        if (faultFlowDetailedModel != null && !faultFlowDetailedModel.getOutput().getData().isEmpty()) {
                            GeneratePdf();
                        } else {
                            binding.downloadReportBtn.setVisibility(View.VISIBLE);
                            binding.downloadProgress.setVisibility(View.GONE);
                            Snackbar.make(binding.getRoot(), "No Found Short-Circuit - Fault Flow Detailed", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    binding.downloadReportBtn.setVisibility(View.VISIBLE);
                    binding.downloadProgress.setVisibility(View.GONE);
                    Snackbar.make(binding.getRoot(), "No Found Analysis Report", Snackbar.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
            }
        });*/

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_pre:
                startActivity(new Intent(Reports.this, PdfViewerActivity.class));
                break;

            case R.id.nav_download:
                try {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
                    if (selectedReport != null) {
                        if (selectedReport.contains("Load Flow - Abnormal conditions")) {
                            if (abnormalReport != null && !abnormalReport.getOutput().getData().isEmpty()) {
                                GeneratePdf();
                            } else {
                                Snackbar.make(binding.getRoot(), "No Found Load Flow - Abnormal conditions", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        if (selectedReport.contains("Load Flow - Detailed")) {
                            if (detailedReport != null && !detailedReport.getOutput().getData().isEmpty()) {
                                GeneratePdf();
                            } else {
                                Snackbar.make(binding.getRoot(), "No Found Load Flow - Detailed", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        if (selectedReport.contains("Load Flow - Overloaded conductors")) {
                            if (overLoadConductorReport != null && !overLoadConductorReport.getOutput().getData().isEmpty()) {
                                GeneratePdf();
                            } else {
                                Snackbar.make(binding.getRoot(), "No Found Load Flow - Overloaded conductors", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        if (selectedReport.contains("Load Flow - Overloaded lines and cables")) {
                            if (overLoadLineAndCablesReport != null && !overLoadLineAndCablesReport.getOutput().getData().isEmpty()) {
                                GeneratePdf();
                            } else {
                                Snackbar.make(binding.getRoot(), "No Found Load Flow - Overloaded lines and cables", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        if (selectedReport.contains("Load Flow - Overloaded transformers")) {
                            if (overLoadTransformerReport != null && !overLoadTransformerReport.getOutput().getData().isEmpty()) {
                                GeneratePdf();
                            } else {
                                Snackbar.make(binding.getRoot(), "No Found Load Flow - Overloaded transformers", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        if (selectedReport.contains("Load Flow - Transformers")) {
                            if (transformerReport != null && !transformerReport.getOutput().getData().isEmpty()) {
                                GeneratePdf();
                            } else {
                                Snackbar.make(binding.getRoot(), "No Found Load Flow - Transformers", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        if (selectedReport.contains("Short-Circuit - Detailed")) {
                            if (shortCircuitDetailedModel != null && !shortCircuitDetailedModel.getOutput().getData().isEmpty()) {
                                GeneratePdf();
                            } else {
                                Snackbar.make(binding.getRoot(), "No Found Short-Circuit - Detailed", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        if (selectedReport.contains("Short-Circuit - Fault Flow Detailed")) {
                            if (faultFlowDetailedModel != null && !faultFlowDetailedModel.getOutput().getData().isEmpty()) {
                                GeneratePdf();
                            } else {
                                Snackbar.make(binding.getRoot(), "No Found Short-Circuit - Fault Flow Detailed", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Snackbar.make(binding.getRoot(), "No Found Analysis Report", Snackbar.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onFilterTypeSelected(String filterType) {
        selectedFilterType = filterType;
    }

    private void GeneratePdf() {
        try {
            JsonObject jsonObject = new JsonObject();
            Intent intent = getIntent();
            if (intent.getStringArrayListExtra("NetworkId") != null) {
                JsonArray jsonArray = new Gson().toJsonTree(intent.getStringArrayListExtra("NetworkId")).getAsJsonArray();
                jsonObject.add("NetworkId", jsonArray);
                jsonObject.addProperty("UserType", prefManager.getUserType());
                jsonObject.addProperty("Group5", "");
                jsonObject.addProperty("Group4", "");
                jsonObject.addProperty("Group3", "");
                jsonObject.addProperty("Group2", "");
                jsonObject.addProperty("Group1", "");
                jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
                jsonObject.addProperty("Project", prefManager.getProjectName());
                jsonObject.addProperty("DashBoardType", "Network");
                jsonObject.addProperty("Mode", "Mobile");
                String AccessToken = prefManager.getAccessToken();
                ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
                Call<DashBoardModel> call = apiInterface.getDashboardData("Bearer " + AccessToken, jsonObject);
                call.enqueue(new Callback<DashBoardModel>() {
                    @Override
                    public void onResponse(@NonNull Call<DashBoardModel> call, @NonNull Response<DashBoardModel> response) {
                        if (response.code() == 200) {
                            ErrorPdfLogger.logApiSuccess(Reports.this, "POST", "/dashboard/", response.message()
                                    + "UserType : " + prefManager.getUserType()
                                    + "Group5 : " + ""
                                    + "Group4 : " + ""
                                    + "Group3 : " + ""
                                    + "Group2 : " + ""
                                    + "Group1 : " + ""
                                    + "CYMDBNET : " + prefManager.getDBName()
                                    + "Project : " + prefManager.getProjectName()
                                    + "DashBoardType : " + "Network"
                                    + "Mode : " + "Moblie"
                                    + "AccessToken : " + prefManager.getAccessToken()
                            );
                            binding.downloadProgress.setVisibility(View.GONE);
                            binding.downloadReportBtn.setVisibility(View.VISIBLE);
                            dashBoardModel = response.body();
                            new generatePDf().execute();
                        } else {
                            ErrorPdfLogger.logApiError(Reports.this, "POST", "/dashboard/", response.message()
                                    + "UserType : " + prefManager.getUserType()
                                    + "Group5 : " + ""
                                    + "Group4 : " + ""
                                    + "Group3 : " + ""
                                    + "Group2 : " + ""
                                    + "Group1 : " + ""
                                    + "CYMDBNET : " + prefManager.getDBName()
                                    + "Project : " + prefManager.getProjectName()
                                    + "DashBoardType : " + "Network"
                                    + "Mode : " + "Moblie"
                                    + "AccessToken : " + prefManager.getAccessToken()
                            );
                            binding.downloadProgress.setVisibility(View.GONE);
                            binding.downloadReportBtn.setVisibility(View.GONE);
                            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG);
                            snack.show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DashBoardModel> call, @NonNull Throwable t) {
                        ErrorPdfLogger.logApiFailure(
                                Reports.this,
                                "POST",
                                "/dashboard/",
                                t
                        );
                        binding.downloadProgress.setVisibility(View.GONE);
                        binding.downloadReportBtn.setVisibility(View.GONE);
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                        snack.show();
                    }
                });
            }
        } catch (Exception e) {
            Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class generatePDf extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.downloadReportBtn.setVisibility(View.GONE);
            binding.downloadProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String status = "";
            try {
                String selectedReport = binding.loadFlowValue.getSelectedItem().toString();
                File documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

                File appDataDir = new File(documentsDir, "T-LABS-NetAnalysis");
                if (!appDataDir.exists()) appDataDir.mkdirs();

                File directory = new File(appDataDir, "AnalysisReport");
                if (!directory.exists()) directory.mkdirs();

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                /*if (selectedReport.contains("Load Flow - Abnormal conditions")) {
                    if (abnormalReport.getOutput().getData() != null) {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                        String fileName = "Load_Flow_Abnormal_conditions" + timeStamp + ".pdf";
                        File file = new File(directory, fileName);

                        PdfWriter writer = new PdfWriter(file);
                        PdfDocument pdfDocument = new PdfDocument(writer);
                        Document document = new Document(pdfDocument, new PageSize(PageSize.A3));

                        String headerHexColor = "#183883";
                        if (headerHexColor.startsWith("#")) {
                            headerHexColor = headerHexColor.substring(1);
                        }
                        int hColor = (int) Long.parseLong(headerHexColor, 16);
                        int a = (hColor >> 16) & 0xFF;
                        int b = (hColor >> 8) & 0xFF;
                        int c = hColor & 0xFF;
                        DeviceRgb headerColor = new DeviceRgb(a, b, c);

                        @SuppressLint("UseCompatLoadingForDrawables")
                        Drawable drawable = getDrawable(R.drawable.ttpl_logo);
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bitmapData = stream.toByteArray();
                        ImageData imageData = ImageDataFactory.create(bitmapData);
                        Image image = new Image(imageData);
                        image.setWidth(70f);

                        float[] columnWidth1 = {100, 660};
                        Table table1 = new Table(columnWidth1);
                        //Table1 _01
                        table1.addCell(new Cell(1, 1).add(image).setBorder(Border.NO_BORDER));
                        table1.addCell(new Cell().add(new Paragraph("Load Flow - Abnormal conditions Report").setUnderline().setFontSize(14f).setFontColor(Color.convertRgbToCmyk(headerColor)).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

                        float[] columnWidth2 = {100, 560, 100};
                        Table table2 = new Table(columnWidth2);

                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMM - yyyy, HH : mm : ss", Locale.getDefault());
                        String formattedDate = dateFormat.format(currentDate);

                        if (dashBoardModel.getOutput() != null) {
                            StringBuilder Substaion = new StringBuilder();
                            StringBuilder division = new StringBuilder();
                            StringBuilder feeder = new StringBuilder();

                            if (dashBoardModel.getOutput().getGroup2().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup2().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup2().get(i).getGroup2() != null) {
                                        if (!Substaion.toString().contains(dashBoardModel.getOutput().getGroup2().get(i).getGroup2())) {
                                            Substaion.append(",  ").append(dashBoardModel.getOutput().getGroup2().get(i).getGroup2());
                                        }
                                    } else {
                                        if (!Substaion.toString().contains("None")) {
                                            Substaion.append(",  ").append("None");
                                        }
                                    }

                                }
                            }

                            if (dashBoardModel.getOutput().getGroup1().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup1().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup1().get(i).getGroup1() != null) {
                                        if (!division.toString().contains(dashBoardModel.getOutput().getGroup1().get(i).getGroup1())) {
                                            division.append(",  ").append(dashBoardModel.getOutput().getGroup1().get(i).getGroup1());
                                        }
                                    } else {
                                        if (!division.toString().contains("None")) {
                                            division.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            if (dashBoardModel.getOutput().getNetworkName().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getNetworkName().size(); i++) {
                                    if (dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId() != null) {
                                        if (!feeder.toString().contains(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId())) {
                                            feeder.append(",  ").append(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId());
                                        }
                                    } else {
                                        if (!feeder.toString().contains("None")) {
                                            feeder.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            //Table2 _02
                            table2.addCell(new Cell().add(new Paragraph("User Name :- ").setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(prefManager.getUserName()).setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _04
                            table2.addCell(new Cell().add(new Paragraph("Date :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(formattedDate).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _05
                            table2.addCell(new Cell().add(new Paragraph("Substation Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(Substaion.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _06
                            table2.addCell(new Cell().add(new Paragraph("Division Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(division.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _07
                            table2.addCell(new Cell().add(new Paragraph("Feeder Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(feeder.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                        }

                        float[] columnWidth3 = {100, 100, 100, 100, 70, 70, 70, 40, 40, 40};
                        Table table3 = new Table(columnWidth3);

                        table3.addHeaderCell("Feeder Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Section Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Equipment Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Code").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Loading A\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Loading B\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Loading C\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("VA\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("VB\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("VC\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);

                        for (int i = 0; i < abnormalReport.getOutput().getData().size(); i++) {

                            if (abnormalReport.getOutput().getData().get(i).getFeederId() != null && !abnormalReport.getOutput().getData().get(i).getFeederId().equals("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getFeederIdColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getFeederIdColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getFeederIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getFeederId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getFeederId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getSectionId() != null && !abnormalReport.getOutput().getData().get(i).getSectionId().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getSectionIdColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getSectionIdColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getSectionIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getSectionId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getSectionId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getEqId() != null && !abnormalReport.getOutput().getData().get(i).getFeederId().equals("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getEqIdColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getEqIdColor().equals("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getEqIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getEqId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getEqId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getEqCode() != null && !abnormalReport.getOutput().getData().get(i).getEqCode().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getEqCodeColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getEqCodeColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getEqCodeColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getEqCode())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getEqCode())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getLoadinga() != null && !abnormalReport.getOutput().getData().get(i).getLoadinga().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getLOADINGAColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getLOADINGAColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getLOADINGAColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getLoadinga())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getLoadinga())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getLoadingb() != null && !abnormalReport.getOutput().getData().get(i).getLoadingb().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getLOADINGBColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getLOADINGBColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getLOADINGBColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getLoadingb())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getLoadingb())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getLoadingc() != null && !abnormalReport.getOutput().getData().get(i).getLoadingc().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getLOADINGCColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getLOADINGCColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getLOADINGCColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getLoadingc())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getLoadingc())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getVa() != null && !abnormalReport.getOutput().getData().get(i).getVa().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getVAColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getVAColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getVAColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getVa())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getVa())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getVb() != null && !abnormalReport.getOutput().getData().get(i).getVb().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getVBColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getVBColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getVBColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getVb())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getVb())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getVc() != null && !abnormalReport.getOutput().getData().get(i).getVc().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getVCColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getVCColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getVCColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getVc())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getVc())));
                                }
                            } else {
                                table2.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                        }

                        document.add(table1);
                        document.add(new Paragraph("\n"));
                        document.add(table2);
                        document.add(new Paragraph("\n"));
                        document.add(table3);
                        document.close();
                        status = "Download Report Successfully";
                    } else {
                        status = "No Report Found! Please try again later";
                    }
                }*/

                if (selectedReport.contains("Load Flow - Abnormal conditions")) {
                    if (abnormalReport.getOutput().getData() != null) {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                        String fileName = "Load_Flow_Abnormal_conditions" + timeStamp + ".pdf";
                        File file = new File(directory, fileName);

                        PdfWriter writer = new PdfWriter(file);
                        PdfDocument pdfDocument = new PdfDocument(writer);
                        Document document = new Document(pdfDocument, new PageSize(PageSize.A3));

                        String headerHexColor = "#183883";
                        if (headerHexColor.startsWith("#")) {
                            headerHexColor = headerHexColor.substring(1);
                        }
                        int hColor = (int) Long.parseLong(headerHexColor, 16);
                        int a = (hColor >> 16) & 0xFF;
                        int b = (hColor >> 8) & 0xFF;
                        int c = hColor & 0xFF;
                        DeviceRgb headerColor = new DeviceRgb(a, b, c);

                        @SuppressLint("UseCompatLoadingForDrawables")
                        Drawable drawable = getDrawable(R.drawable.ttpl_logo);
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bitmapData = stream.toByteArray();
                        ImageData imageData = ImageDataFactory.create(bitmapData);
                        Image image = new Image(imageData);
                        image.setWidth(70f);

                        float[] columnWidth1 = {100, 660};
                        Table table1 = new Table(columnWidth1);
                        table1.setMarginBottom(0f); // Remove default spacing after table1
                        table1.addCell(new Cell(1, 1).add(image).setBorder(Border.NO_BORDER));
                        table1.addCell(new Cell().add(new Paragraph("Load Flow - Abnormal conditions Report")
                                        .setUnderline()
                                        .setFontSize(14f)
                                        .setFontColor(Color.convertRgbToCmyk(headerColor))
                                        .setTextAlignment(TextAlignment.CENTER))
                                .setBorder(Border.NO_BORDER));

                        float[] columnWidth2 = {100, 560, 100};
                        Table table2 = new Table(columnWidth2);
                        table2.setMarginBottom(0f); // Remove default spacing after table2

                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMM - yyyy, HH : mm : ss", Locale.getDefault());
                        String formattedDate = dateFormat.format(currentDate);

                        if (dashBoardModel.getOutput() != null) {
                            StringBuilder Substaion = new StringBuilder();
                            StringBuilder division = new StringBuilder();
                            StringBuilder feeder = new StringBuilder();

                            if (dashBoardModel.getOutput().getGroup2().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup2().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup2().get(i).getGroup2() != null) {
                                        if (!Substaion.toString().contains(dashBoardModel.getOutput().getGroup2().get(i).getGroup2())) {
                                            Substaion.append(",  ").append(dashBoardModel.getOutput().getGroup2().get(i).getGroup2());
                                        }
                                    } else {
                                        if (!Substaion.toString().contains("None")) {
                                            Substaion.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            if (dashBoardModel.getOutput().getGroup1().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup1().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup1().get(i).getGroup1() != null) {
                                        if (!division.toString().contains(dashBoardModel.getOutput().getGroup1().get(i).getGroup1())) {
                                            division.append(",  ").append(dashBoardModel.getOutput().getGroup1().get(i).getGroup1());
                                        }
                                    } else {
                                        if (!division.toString().contains("None")) {
                                            division.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            if (dashBoardModel.getOutput().getGroup1().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getNetworkName().size(); i++) {
                                    if (dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId() != null) {
                                        if (!feeder.toString().contains(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId())) {
                                            feeder.append(",  ").append(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId());
                                        }
                                    } else {
                                        if (!feeder.toString().contains("None")) {
                                            feeder.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            // Table2: Minimize spacing within cells
                            table2.addCell(new Cell().add(new Paragraph("User Name :- ").setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(prefManager.getUserName()).setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));

                            table2.addCell(new Cell().add(new Paragraph("Date :- ").setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(formattedDate).setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));

                            table2.addCell(new Cell().add(new Paragraph("Substation Name :- ").setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(Substaion.toString()).setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));

                            table2.addCell(new Cell().add(new Paragraph("Division Name :- ").setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(division.toString()).setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));

                            table2.addCell(new Cell().add(new Paragraph("Feeder Name :- ").setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(feeder.toString()).setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setFixedLeading(10f).setMargin(0f)).setPadding(0f).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                        }

                        float[] columnWidth3 = {100, 100, 100, 100, 70, 70, 70, 40, 40, 40};
                        Table table3 = new Table(columnWidth3);

                        table3.addHeaderCell("Feeder Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Section Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Equipment Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Code").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Loading A\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Loading B\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Loading C\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("VA\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("VB\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("VC\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);

                        for (int i = 0; i < abnormalReport.getOutput().getData().size(); i++) {
                            if (abnormalReport.getOutput().getData().get(i).getFeederId() != null && !abnormalReport.getOutput().getData().get(i).getFeederId().equals("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getFeederIdColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getFeederIdColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getFeederIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getFeederId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getFeederId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getSectionId() != null && !abnormalReport.getOutput().getData().get(i).getSectionId().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getSectionIdColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getSectionIdColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getSectionIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getSectionId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getSectionId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getEqId() != null && !abnormalReport.getOutput().getData().get(i).getFeederId().equals("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getEqIdColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getEqIdColor().equals("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getEqIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getEqId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getEqId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getEqCode() != null && !abnormalReport.getOutput().getData().get(i).getEqCode().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getEqCodeColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getEqCodeColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getEqCodeColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getEqCode())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getEqCode())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getLoadinga() != null && !abnormalReport.getOutput().getData().get(i).getLoadinga().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getLOADINGAColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getLOADINGAColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getLOADINGAColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getLoadinga())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getLoadinga())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getLoadingb() != null && !abnormalReport.getOutput().getData().get(i).getLoadingb().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getLOADINGBColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getLOADINGBColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getLOADINGBColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getLoadingb())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getLoadingb())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getLoadingc() != null && !abnormalReport.getOutput().getData().get(i).getLoadingc().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getLOADINGCColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getLOADINGCColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getLOADINGCColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getLoadingc())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getLoadingc())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getVa() != null && !abnormalReport.getOutput().getData().get(i).getVa().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getVAColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getVAColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getVAColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getVa())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getVa())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getVb() != null && !abnormalReport.getOutput().getData().get(i).getVb().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getVBColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getVBColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getVBColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getVb())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getVb())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (abnormalReport.getOutput().getData().get(i).getVc() != null && !abnormalReport.getOutput().getData().get(i).getVc().contains("NULL")) {
                                if (!abnormalReport.getOutput().getData().get(i).getVCColor().isEmpty() && !abnormalReport.getOutput().getData().get(i).getVCColor().contains("NULL")) {
                                    String hexColor = abnormalReport.getOutput().getData().get(i).getVCColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getVc())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(abnormalReport.getOutput().getData().get(i).getVc())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }
                        }

                        document.add(table1);
                        document.add(new Paragraph("\n"));
                        document.add(table2);
                        document.add(new Paragraph("\n"));
                        document.add(table3);
                        document.close();
                        status = "Download Report Successfully";
                    } else {
                        status = "No Report Found! Please try again later";
                    }
                }

                if (selectedReport.contains("Load Flow - Detailed")) {
                    if (detailedReport.getOutput().getData() != null) {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                        String fileName = "Load_Flow_Detailed" + timeStamp + ".pdf";
                        File file = new File(directory, fileName);

                        PdfWriter writer = new PdfWriter(file);
                        PdfDocument pdfDocument = new PdfDocument(writer);
                        Document document = new Document(pdfDocument, new PageSize(PageSize.A4));

                        String headerHexColor = "#183883";
                        if (headerHexColor.startsWith("#")) {
                            headerHexColor = headerHexColor.substring(1);
                        }
                        int hColor = (int) Long.parseLong(headerHexColor, 16);
                        int a = (hColor >> 16) & 0xFF;
                        int b = (hColor >> 8) & 0xFF;
                        int c = hColor & 0xFF;
                        DeviceRgb headerColor = new DeviceRgb(a, b, c);

                        @SuppressLint("UseCompatLoadingForDrawables")
                        Drawable drawable = getDrawable(R.drawable.ttpl_logo);
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bitmapData = stream.toByteArray();
                        ImageData imageData = ImageDataFactory.create(bitmapData);
                        Image image = new Image(imageData);
                        image.setWidth(70f);

                        float[] columnWidth1 = {100, 660};
                        Table table1 = new Table(columnWidth1);
                        //Table1 _01
                        table1.addCell(new Cell(1, 1).add(image).setBorder(Border.NO_BORDER));
                        table1.addCell(new Cell().add(new Paragraph("Load Flow - Detailed Report").setUnderline().setFontSize(14f).setFontColor(Color.convertRgbToCmyk(headerColor)).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

                        float[] columnWidth2 = {100, 560, 100};
                        Table table2 = new Table(columnWidth2);

                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMM - yy, HH : mm : ss", Locale.getDefault());
                        String formattedDate = dateFormat.format(currentDate);

                        if (dashBoardModel.getOutput() != null) {
                            StringBuilder Substaion = new StringBuilder();
                            StringBuilder division = new StringBuilder();
                            StringBuilder feeder = new StringBuilder();

                            if (dashBoardModel.getOutput().getGroup2().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup2().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup2().get(i).getGroup2() != null) {
                                        if (!Substaion.toString().contains(dashBoardModel.getOutput().getGroup2().get(i).getGroup2())) {
                                            Substaion.append(",  ").append(dashBoardModel.getOutput().getGroup2().get(i).getGroup2());
                                        }
                                    } else {
                                        if (!Substaion.toString().contains("None")) {
                                            Substaion.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            if (dashBoardModel.getOutput().getGroup1().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup1().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup1().get(i).getGroup1() != null) {
                                        if (!division.toString().contains(dashBoardModel.getOutput().getGroup1().get(i).getGroup1())) {
                                            division.append(",  ").append(dashBoardModel.getOutput().getGroup1().get(i).getGroup1());
                                        }
                                    } else {
                                        if (!division.toString().contains("None")) {
                                            division.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            if (dashBoardModel.getOutput().getNetworkName().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getNetworkName().size(); i++) {
                                    if (dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId() != null) {
                                        if (!feeder.toString().contains(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId())) {
                                            feeder.append(",  ").append(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId());
                                        }
                                    } else {
                                        if (!feeder.toString().contains("None")) {
                                            feeder.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            //Table2 _02
                            table2.addCell(new Cell().add(new Paragraph("User Name  ").setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(prefManager.getUserName()).setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _04
                            table2.addCell(new Cell().add(new Paragraph("Date  ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(formattedDate).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _05
                            table2.addCell(new Cell().add(new Paragraph("Substation Name ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(Substaion.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _06
                            table2.addCell(new Cell().add(new Paragraph("Division Name  ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(division.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _07
                            table2.addCell(new Cell().add(new Paragraph("Feeder Name  ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(feeder.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                        }

                        float[] columnWidth3 = {100, 100, 100, 100, 80, 80, 80, 80};
                        Table table3 = new Table(columnWidth3);

                        table3.addHeaderCell("Feeder Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Section Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Equipment Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Code").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Loading A\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Thru Power A\n(kw)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Thru Power A\n(Kva)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("VA\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);

                        for (int i = 0; i < detailedReport.getOutput().getData().size(); i++) {

                            if (detailedReport.getOutput().getData().get(i).getFeederId() != null && !detailedReport.getOutput().getData().get(i).getFeederId().equals("NULL")) {
                                if (!detailedReport.getOutput().getData().get(i).getFeederIdColor().isEmpty() && !detailedReport.getOutput().getData().get(i).getFeederIdColor().contains("NULL")) {
                                    String hexColor = detailedReport.getOutput().getData().get(i).getFeederIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getFeederId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getFeederId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (detailedReport.getOutput().getData().get(i).getSectionId() != null && !detailedReport.getOutput().getData().get(i).getSectionId().contains("NULL")) {
                                if (!detailedReport.getOutput().getData().get(i).getSectionIdColor().isEmpty() && !detailedReport.getOutput().getData().get(i).getSectionIdColor().contains("NULL")) {
                                    String hexColor = detailedReport.getOutput().getData().get(i).getSectionIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getSectionId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getSectionId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (detailedReport.getOutput().getData().get(i).getEqId() != null && !detailedReport.getOutput().getData().get(i).getFeederId().equals("NULL")) {
                                if (!detailedReport.getOutput().getData().get(i).getEqIdColor().isEmpty() && !detailedReport.getOutput().getData().get(i).getEqIdColor().equals("NULL")) {
                                    String hexColor = detailedReport.getOutput().getData().get(i).getEqIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getEqId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getEqId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (detailedReport.getOutput().getData().get(i).getEqCode() != null && !detailedReport.getOutput().getData().get(i).getEqCode().contains("NULL")) {
                                if (!detailedReport.getOutput().getData().get(i).getEqCodeColor().isEmpty() && !detailedReport.getOutput().getData().get(i).getEqCodeColor().contains("NULL")) {
                                    String hexColor = detailedReport.getOutput().getData().get(i).getEqCodeColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getEqCode())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getEqCode())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (detailedReport.getOutput().getData().get(i).getLoadinga() != null && !detailedReport.getOutput().getData().get(i).getLoadinga().contains("NULL")) {
                                if (!detailedReport.getOutput().getData().get(i).getLOADINGAColor().isEmpty() && !detailedReport.getOutput().getData().get(i).getLOADINGAColor().contains("NULL")) {
                                    String hexColor = detailedReport.getOutput().getData().get(i).getLOADINGAColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getLoadinga())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getLoadinga())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (detailedReport.getOutput().getData().get(i).getKwa() != null && !detailedReport.getOutput().getData().get(i).getKwa().contains("NULL")) {
                                if (!detailedReport.getOutput().getData().get(i).getKWAColor().isEmpty() && !detailedReport.getOutput().getData().get(i).getKWAColor().contains("NULL")) {
                                    String hexColor = detailedReport.getOutput().getData().get(i).getKWAColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getKwa())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getKwa())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (detailedReport.getOutput().getData().get(i).getKvara() != null && !detailedReport.getOutput().getData().get(i).getKvara().contains("NULL")) {
                                if (!detailedReport.getOutput().getData().get(i).getKVARAColor().isEmpty() && !detailedReport.getOutput().getData().get(i).getKVARAColor().contains("NULL")) {
                                    String hexColor = detailedReport.getOutput().getData().get(i).getKVARAColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getKvara())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getKvara())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (detailedReport.getOutput().getData().get(i).getVa() != null && !detailedReport.getOutput().getData().get(i).getVa().contains("NULL")) {
                                if (!detailedReport.getOutput().getData().get(i).getVAColor().isEmpty() && !detailedReport.getOutput().getData().get(i).getVAColor().contains("NULL")) {
                                    String hexColor = detailedReport.getOutput().getData().get(i).getVAColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getVa())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(detailedReport.getOutput().getData().get(i).getVa())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                        }

                        document.add(table1);
                        document.add(new Paragraph("\n"));
                        document.add(table2);
                        document.add(new Paragraph("\n"));
                        document.add(table3);
                        document.close();
                        status = "Download Report Successfully";
                    } else {
                        status = "No Report Found! Please try again later";
                    }
                }

                if (selectedReport.contains("Load Flow - Overloaded conductors")) {
                    if (overLoadConductorReport.getOutput().getData() != null) {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                        String fileName = "Load_Flow_OverloadedConductors" + timeStamp + ".pdf";
                        File file = new File(directory, fileName);

                        PdfWriter writer = new PdfWriter(file);
                        PdfDocument pdfDocument = new PdfDocument(writer);
                        Document document = new Document(pdfDocument, new PageSize(PageSize.A4));

                        String headerHexColor = "#183883";
                        if (headerHexColor.startsWith("#")) {
                            headerHexColor = headerHexColor.substring(1);
                        }
                        int hColor = (int) Long.parseLong(headerHexColor, 16);
                        int a = (hColor >> 16) & 0xFF;
                        int b = (hColor >> 8) & 0xFF;
                        int c = hColor & 0xFF;
                        DeviceRgb headerColor = new DeviceRgb(a, b, c);

                        @SuppressLint("UseCompatLoadingForDrawables")
                        Drawable drawable = getDrawable(R.drawable.ttpl_logo);
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bitmapData = stream.toByteArray();
                        ImageData imageData = ImageDataFactory.create(bitmapData);
                        Image image = new Image(imageData);
                        image.setWidth(70f);

                        float[] columnWidth1 = {100, 660};
                        Table table1 = new Table(columnWidth1);
                        //Table1 _01
                        table1.addCell(new Cell(1, 1).add(image).setBorder(Border.NO_BORDER));
                        table1.addCell(new Cell().add(new Paragraph("Load Flow - Overloaded conductors Report").setUnderline().setFontSize(14f).setFontColor(Color.convertRgbToCmyk(headerColor)).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

                        float[] columnWidth2 = {100, 560, 100};
                        Table table2 = new Table(columnWidth2);

                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMM - yy, HH : mm : ss", Locale.getDefault());
                        String formattedDate = dateFormat.format(currentDate);

                        if (dashBoardModel.getOutput() != null) {
                            StringBuilder Substaion = new StringBuilder();
                            StringBuilder division = new StringBuilder();
                            StringBuilder feeder = new StringBuilder();

                            if (dashBoardModel.getOutput().getGroup2().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup2().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup2().get(i).getGroup2() != null) {
                                        if (!Substaion.toString().contains(dashBoardModel.getOutput().getGroup2().get(i).getGroup2())) {
                                            Substaion.append(",  ").append(dashBoardModel.getOutput().getGroup2().get(i).getGroup2());
                                        }
                                    } else {
                                        if (!Substaion.toString().contains("None")) {
                                            Substaion.append(",  ").append("None");
                                        }
                                    }

                                }
                            }

                            if (dashBoardModel.getOutput().getGroup1().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup1().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup1().get(i).getGroup1() != null) {
                                        if (!division.toString().contains(dashBoardModel.getOutput().getGroup1().get(i).getGroup1())) {
                                            division.append(",  ").append(dashBoardModel.getOutput().getGroup1().get(i).getGroup1());
                                        }
                                    } else {
                                        if (!division.toString().contains("None")) {
                                            division.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            if (dashBoardModel.getOutput().getNetworkName().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getNetworkName().size(); i++) {
                                    if (dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId() != null) {
                                        if (!feeder.toString().contains(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId())) {
                                            feeder.append(",  ").append(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId());
                                        }
                                    } else {
                                        if (!feeder.toString().contains("None")) {
                                            feeder.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            //Table2 _02
                            table2.addCell(new Cell().add(new Paragraph("User Name :- ").setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(prefManager.getUserName()).setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _04
                            table2.addCell(new Cell().add(new Paragraph("Date :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(formattedDate).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _05
                            table2.addCell(new Cell().add(new Paragraph("Substation Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(Substaion.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _06
                            table2.addCell(new Cell().add(new Paragraph("Division Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(division.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _07
                            table2.addCell(new Cell().add(new Paragraph("Feeder Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(feeder.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                        }

                        float[] columnWidth3 = {100, 100, 100, 100, 80, 80, 80};
                        Table table3 = new Table(columnWidth3);

                        table3.addHeaderCell("Feeder Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Protective Device Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Begin Section Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("End Section Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Conductor Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Capacity (A)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Loading (%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);

                        for (int i = 0; i < overLoadConductorReport.getOutput().getData().size(); i++) {

                            if (overLoadConductorReport.getOutput().getData().get(i).getFeederId() != null && !overLoadConductorReport.getOutput().getData().get(i).getFeederId().equals("NULL")) {
                                if (!overLoadConductorReport.getOutput().getData().get(i).getFeederIdColor().isEmpty() && !overLoadConductorReport.getOutput().getData().get(i).getFeederIdColor().contains("NULL")) {
                                    String hexColor = overLoadConductorReport.getOutput().getData().get(i).getFeederIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getFeederId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getFeederId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadConductorReport.getOutput().getData().get(i).getOlcProDevID() != null && !overLoadConductorReport.getOutput().getData().get(i).getOlcProDevID().contains("NULL")) {
                                if (!overLoadConductorReport.getOutput().getData().get(i).getOlcProDevIDColor().isEmpty() && !overLoadConductorReport.getOutput().getData().get(i).getOlcProDevIDColor().contains("NULL")) {
                                    String hexColor = overLoadConductorReport.getOutput().getData().get(i).getOlcProDevIDColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getOlcProDevID())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getOlcProDevID())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadConductorReport.getOutput().getData().get(i).getOlcBegOvlCond() != null && !overLoadConductorReport.getOutput().getData().get(i).getOlcBegOvlCond().equals("NULL")) {
                                if (!overLoadConductorReport.getOutput().getData().get(i).getOlcBegOvlCondColor().isEmpty() && !overLoadConductorReport.getOutput().getData().get(i).getOlcBegOvlCondColor().equals("NULL")) {
                                    String hexColor = overLoadConductorReport.getOutput().getData().get(i).getOlcBegOvlCondColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getOlcBegOvlCond())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getOlcBegOvlCond())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadConductorReport.getOutput().getData().get(i).getOlcEndOvlCond() != null && !overLoadConductorReport.getOutput().getData().get(i).getOlcEndOvlCond().contains("NULL")) {
                                if (!overLoadConductorReport.getOutput().getData().get(i).getOlcEndOvlCondColor().isEmpty() && !overLoadConductorReport.getOutput().getData().get(i).getOlcEndOvlCondColor().contains("NULL")) {
                                    String hexColor = overLoadConductorReport.getOutput().getData().get(i).getOlcEndOvlCondColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getOlcEndOvlCond())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getOlcEndOvlCond())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadConductorReport.getOutput().getData().get(i).getOlcConductID() != null && !overLoadConductorReport.getOutput().getData().get(i).getOlcConductID().contains("NULL")) {
                                if (!overLoadConductorReport.getOutput().getData().get(i).getOlcConductIDColor().isEmpty() && !overLoadConductorReport.getOutput().getData().get(i).getOlcConductIDColor().contains("NULL")) {
                                    String hexColor = overLoadConductorReport.getOutput().getData().get(i).getOlcConductIDColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getOlcConductID())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getOlcConductID())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadConductorReport.getOutput().getData().get(i).getOlcLoadCap() != null && !overLoadConductorReport.getOutput().getData().get(i).getOlcLoadCap().contains("NULL")) {
                                if (!overLoadConductorReport.getOutput().getData().get(i).getOlcLoadCapColor().isEmpty() && !overLoadConductorReport.getOutput().getData().get(i).getOlcLoadCapColor().contains("NULL")) {
                                    String hexColor = overLoadConductorReport.getOutput().getData().get(i).getOlcLoadCapColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getOlcLoadCap())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getOlcLoadCap())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadConductorReport.getOutput().getData().get(i).getOlcPercntOvl() != null && !overLoadConductorReport.getOutput().getData().get(i).getOlcPercntOvl().contains("NULL")) {
                                if (!overLoadConductorReport.getOutput().getData().get(i).getOlcPercntOvlColor().isEmpty() && !overLoadConductorReport.getOutput().getData().get(i).getOlcPercntOvlColor().contains("NULL")) {
                                    String hexColor = overLoadConductorReport.getOutput().getData().get(i).getOlcPercntOvlColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getOlcPercntOvl())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadConductorReport.getOutput().getData().get(i).getOlcPercntOvl())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }
                        }

                        document.add(table1);
                        document.add(new Paragraph("\n"));
                        document.add(table2);
                        document.add(new Paragraph("\n"));
                        document.add(table3);
                        document.close();
                        status = "Download Report Successfully";
                    } else {
                        status = "No Report Found! Please try again later";
                    }

                }

                if (selectedReport.contains("Load Flow - Overloaded lines and cables")) {
                    if (overLoadLineAndCablesReport.getOutput().getData() != null) {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                        String fileName = "Load_Flow_OverloadedLinesAndCables" + timeStamp + ".pdf";
                        File file = new File(directory, fileName);

                        PdfWriter writer = new PdfWriter(file);
                        PdfDocument pdfDocument = new PdfDocument(writer);
                        Document document = new Document(pdfDocument, new PageSize(PageSize.A4));

                        String headerHexColor = "#183883";
                        if (headerHexColor.startsWith("#")) {
                            headerHexColor = headerHexColor.substring(1);
                        }
                        int hColor = (int) Long.parseLong(headerHexColor, 16);
                        int a = (hColor >> 16) & 0xFF;
                        int b = (hColor >> 8) & 0xFF;
                        int c = hColor & 0xFF;
                        DeviceRgb headerColor = new DeviceRgb(a, b, c);

                        //Add Image
                        @SuppressLint("UseCompatLoadingForDrawables")
                        Drawable drawable = getDrawable(R.drawable.ttpl_logo);
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bitmapData = stream.toByteArray();
                        ImageData imageData = ImageDataFactory.create(bitmapData);
                        Image image = new Image(imageData);
                        image.setWidth(70f);

                        float[] columnWidth1 = {100, 660};
                        Table table1 = new Table(columnWidth1);
                        //Table1 _01
                        table1.addCell(new Cell(1, 1).add(image).setBorder(Border.NO_BORDER));
                        table1.addCell(new Cell().add(new Paragraph("Load Flow - Overloaded lines and cables Report").setUnderline().setFontSize(14f).setFontColor(Color.convertRgbToCmyk(headerColor)).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

                        float[] columnWidth2 = {100, 560, 100};
                        Table table2 = new Table(columnWidth2);

                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMM - yy, HH : mm : ss", Locale.getDefault());
                        String formattedDate = dateFormat.format(currentDate);

                        if (dashBoardModel.getOutput() != null) {
                            StringBuilder Substaion = new StringBuilder();
                            StringBuilder division = new StringBuilder();
                            StringBuilder feeder = new StringBuilder();

                            if (dashBoardModel.getOutput().getGroup2().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup2().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup2().get(i).getGroup2() != null) {
                                        if (!Substaion.toString().contains(dashBoardModel.getOutput().getGroup2().get(i).getGroup2())) {
                                            Substaion.append(",  ").append(dashBoardModel.getOutput().getGroup2().get(i).getGroup2());
                                        }
                                    } else {
                                        if (!Substaion.toString().contains("None")) {
                                            Substaion.append(",  ").append("None");
                                        }
                                    }

                                }
                            }

                            if (dashBoardModel.getOutput().getGroup1().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup1().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup1().get(i).getGroup1() != null) {
                                        if (!division.toString().contains(dashBoardModel.getOutput().getGroup1().get(i).getGroup1())) {
                                            division.append(",  ").append(dashBoardModel.getOutput().getGroup1().get(i).getGroup1());
                                        }
                                    } else {
                                        if (!division.toString().contains("None")) {
                                            division.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            if (dashBoardModel.getOutput().getNetworkName().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getNetworkName().size(); i++) {
                                    if (dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId() != null) {
                                        if (!feeder.toString().contains(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId())) {
                                            feeder.append(",  ").append(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId());
                                        }
                                    } else {
                                        if (!feeder.toString().contains("None")) {
                                            feeder.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            //Table2 _02
                            table2.addCell(new Cell().add(new Paragraph("User Name :- ").setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(prefManager.getUserName()).setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _04
                            table2.addCell(new Cell().add(new Paragraph("Date :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(formattedDate).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _05
                            table2.addCell(new Cell().add(new Paragraph("Substation Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(Substaion.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _06
                            table2.addCell(new Cell().add(new Paragraph("Division Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(division.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _07
                            table2.addCell(new Cell().add(new Paragraph("Feeder Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(feeder.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                        }

                        float[] columnWidth3 = {110, 110, 110, 90, 90, 90};
                        Table table3 = new Table(columnWidth3);

                        table3.addHeaderCell("Equipment No.").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("From Node").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("To Node").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("IBal (A)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Angel I (o)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Loading (%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);

                        for (int i = 0; i < overLoadLineAndCablesReport.getOutput().getData().size(); i++) {

                            if (overLoadLineAndCablesReport.getOutput().getData().get(i).getEqNo() != null && !overLoadLineAndCablesReport.getOutput().getData().get(i).getEqNo().equals("NULL")) {
                                if (!overLoadLineAndCablesReport.getOutput().getData().get(i).getEqNoColor().isEmpty() && !overLoadLineAndCablesReport.getOutput().getData().get(i).getEqNoColor().contains("NULL")) {
                                    String hexColor = overLoadLineAndCablesReport.getOutput().getData().get(i).getEqNoColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadLineAndCablesReport.getOutput().getData().get(i).getEqNo())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadLineAndCablesReport.getOutput().getData().get(i).getEqNo())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadLineAndCablesReport.getOutput().getData().get(i).getFromNodeId() != null && !overLoadLineAndCablesReport.getOutput().getData().get(i).getFromNodeId().contains("NULL")) {
                                if (!overLoadLineAndCablesReport.getOutput().getData().get(i).getFromNodeIdColor().isEmpty() && !overLoadLineAndCablesReport.getOutput().getData().get(i).getFromNodeIdColor().contains("NULL")) {
                                    String hexColor = overLoadLineAndCablesReport.getOutput().getData().get(i).getFromNodeIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadLineAndCablesReport.getOutput().getData().get(i).getFromNodeId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadLineAndCablesReport.getOutput().getData().get(i).getFromNodeId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadLineAndCablesReport.getOutput().getData().get(i).getToNodeId() != null && !overLoadLineAndCablesReport.getOutput().getData().get(i).getToNodeId().equals("NULL")) {
                                if (!overLoadLineAndCablesReport.getOutput().getData().get(i).getToNodeIdColor().isEmpty() && !overLoadLineAndCablesReport.getOutput().getData().get(i).getToNodeIdColor().equals("NULL")) {
                                    String hexColor = overLoadLineAndCablesReport.getOutput().getData().get(i).getToNodeIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadLineAndCablesReport.getOutput().getData().get(i).getToNodeId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadLineAndCablesReport.getOutput().getData().get(i).getToNodeId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadLineAndCablesReport.getOutput().getData().get(i).getIBal() != null && !overLoadLineAndCablesReport.getOutput().getData().get(i).getIBal().contains("NULL")) {
                                if (!overLoadLineAndCablesReport.getOutput().getData().get(i).getIBalColor().isEmpty() && !overLoadLineAndCablesReport.getOutput().getData().get(i).getIBalColor().contains("NULL")) {
                                    String hexColor = overLoadLineAndCablesReport.getOutput().getData().get(i).getIBalColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadLineAndCablesReport.getOutput().getData().get(i).getIBal())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadLineAndCablesReport.getOutput().getData().get(i).getIBal())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadLineAndCablesReport.getOutput().getData().get(i).getIAngle() != null && !overLoadLineAndCablesReport.getOutput().getData().get(i).getIAngle().contains("NULL")) {
                                if (!overLoadLineAndCablesReport.getOutput().getData().get(i).getIAngleColor().isEmpty() && !overLoadLineAndCablesReport.getOutput().getData().get(i).getIAngleColor().contains("NULL")) {
                                    String hexColor = overLoadLineAndCablesReport.getOutput().getData().get(i).getIAngleColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadLineAndCablesReport.getOutput().getData().get(i).getIAngle())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadLineAndCablesReport.getOutput().getData().get(i).getIAngle())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadLineAndCablesReport.getOutput().getData().get(i).getLoading() != null && !overLoadLineAndCablesReport.getOutput().getData().get(i).getLoading().contains("NULL")) {
                                if (!overLoadLineAndCablesReport.getOutput().getData().get(i).getLOADINGColor().isEmpty() && !overLoadLineAndCablesReport.getOutput().getData().get(i).getLOADINGColor().contains("NULL")) {
                                    String hexColor = overLoadLineAndCablesReport.getOutput().getData().get(i).getLOADINGColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadLineAndCablesReport.getOutput().getData().get(i).getLoading())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadLineAndCablesReport.getOutput().getData().get(i).getLoading())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                        }

                        document.add(table1);
                        document.add(new Paragraph("\n"));
                        document.add(table2);
                        document.add(new Paragraph("\n"));
                        document.add(table3);
                        document.close();
                        status = "Download Report Successfully";
                    } else {
                        status = "No Report Found! Please try again later";
                    }
                }

                if (selectedReport.contains("Load Flow - Overloaded transformers")) {
                    if (overLoadTransformerReport.getOutput().getData() != null) {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                        String fileName = "LoadFlow_OverloadedTransformers" + timeStamp + ".pdf";
                        File file = new File(directory, fileName);

                        PdfWriter writer = new PdfWriter(file);
                        PdfDocument pdfDocument = new PdfDocument(writer);
                        Document document = new Document(pdfDocument, new PageSize(PageSize.A4));

                        String headerHexColor = "#183883";
                        if (headerHexColor.startsWith("#")) {
                            headerHexColor = headerHexColor.substring(1);
                        }
                        int hColor = (int) Long.parseLong(headerHexColor, 16);
                        int a = (hColor >> 16) & 0xFF;
                        int b = (hColor >> 8) & 0xFF;
                        int c = hColor & 0xFF;
                        DeviceRgb headerColor = new DeviceRgb(a, b, c);

                        //Add Image
                        @SuppressLint("UseCompatLoadingForDrawables")
                        Drawable drawable = getDrawable(R.drawable.ttpl_logo);
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bitmapData = stream.toByteArray();
                        ImageData imageData = ImageDataFactory.create(bitmapData);
                        Image image = new Image(imageData);
                        image.setWidth(70f);

                        float[] columnWidth1 = {100, 660};
                        Table table1 = new Table(columnWidth1);
                        //Table1 _01
                        table1.addCell(new Cell(1, 1).add(image).setBorder(Border.NO_BORDER));
                        table1.addCell(new Cell().add(new Paragraph("Load Flow - Overloaded transformers Report").setUnderline().setFontSize(14f).setFontColor(Color.convertRgbToCmyk(headerColor)).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

                        float[] columnWidth2 = {100, 560, 100};
                        Table table2 = new Table(columnWidth2);

                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMM - yy, HH : mm : ss", Locale.getDefault());
                        String formattedDate = dateFormat.format(currentDate);

                        if (dashBoardModel.getOutput() != null) {
                            StringBuilder Substaion = new StringBuilder();
                            StringBuilder division = new StringBuilder();
                            StringBuilder feeder = new StringBuilder();

                            if (dashBoardModel.getOutput().getGroup2().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup2().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup2().get(i).getGroup2() != null) {
                                        if (!Substaion.toString().contains(dashBoardModel.getOutput().getGroup2().get(i).getGroup2())) {
                                            Substaion.append(",  ").append(dashBoardModel.getOutput().getGroup2().get(i).getGroup2());
                                        }
                                    } else {
                                        if (!Substaion.toString().contains("None")) {
                                            Substaion.append(",  ").append("None");
                                        }
                                    }

                                }
                            }

                            if (dashBoardModel.getOutput().getGroup1().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup1().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup1().get(i).getGroup1() != null) {
                                        if (!division.toString().contains(dashBoardModel.getOutput().getGroup1().get(i).getGroup1())) {
                                            division.append(",  ").append(dashBoardModel.getOutput().getGroup1().get(i).getGroup1());
                                        }
                                    } else {
                                        if (!division.toString().contains("None")) {
                                            division.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            if (dashBoardModel.getOutput().getNetworkName().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getNetworkName().size(); i++) {
                                    if (dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId() != null) {
                                        if (!feeder.toString().contains(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId())) {
                                            feeder.append(",  ").append(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId());
                                        }
                                    } else {
                                        if (!feeder.toString().contains("None")) {
                                            feeder.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            //Table2 _02
                            table2.addCell(new Cell().add(new Paragraph("User Name :- ").setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(prefManager.getUserName()).setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _04
                            table2.addCell(new Cell().add(new Paragraph("Date :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(formattedDate).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _05
                            table2.addCell(new Cell().add(new Paragraph("Substation Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(Substaion.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _06
                            table2.addCell(new Cell().add(new Paragraph("Division Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(division.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _07
                            table2.addCell(new Cell().add(new Paragraph("Feeder Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(feeder.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                        }

                        float[] columnWidth3 = {110, 110, 110, 140, 90};
                        Table table3 = new Table(columnWidth3);

                        table3.addHeaderCell("Equipment No.").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("From Node").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("To Node").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Total Thru Power (MVA)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Loading (%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(8).setTextAlignment(TextAlignment.LEFT);

                        for (int i = 0; i < overLoadTransformerReport.getOutput().getData().size(); i++) {

                            if (overLoadTransformerReport.getOutput().getData().get(i).getEqNo() != null && !overLoadTransformerReport.getOutput().getData().get(i).getEqNo().equals("NULL")) {
                                if (!overLoadTransformerReport.getOutput().getData().get(i).getEqNoColor().isEmpty() && !overLoadTransformerReport.getOutput().getData().get(i).getEqNoColor().contains("NULL")) {
                                    String hexColor = overLoadTransformerReport.getOutput().getData().get(i).getEqNoColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadTransformerReport.getOutput().getData().get(i).getEqNo())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadTransformerReport.getOutput().getData().get(i).getEqNo())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadTransformerReport.getOutput().getData().get(i).getFromNodeId() != null && !overLoadTransformerReport.getOutput().getData().get(i).getFromNodeId().contains("NULL")) {
                                if (!overLoadTransformerReport.getOutput().getData().get(i).getFromNodeIdColor().isEmpty() && !overLoadTransformerReport.getOutput().getData().get(i).getFromNodeIdColor().contains("NULL")) {
                                    String hexColor = overLoadTransformerReport.getOutput().getData().get(i).getFromNodeIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);
                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadTransformerReport.getOutput().getData().get(i).getFromNodeId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadTransformerReport.getOutput().getData().get(i).getFromNodeId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadTransformerReport.getOutput().getData().get(i).getToNodeId() != null && !overLoadTransformerReport.getOutput().getData().get(i).getToNodeId().equals("NULL")) {
                                if (!overLoadTransformerReport.getOutput().getData().get(i).getToNodeIdColor().isEmpty() && !overLoadTransformerReport.getOutput().getData().get(i).getToNodeIdColor().equals("NULL")) {
                                    String hexColor = overLoadTransformerReport.getOutput().getData().get(i).getToNodeIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadTransformerReport.getOutput().getData().get(i).getToNodeId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadTransformerReport.getOutput().getData().get(i).getToNodeId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadTransformerReport.getOutput().getData().get(i).getMvatot() != null && !overLoadTransformerReport.getOutput().getData().get(i).getMvatot().contains("NULL")) {
                                if (!overLoadTransformerReport.getOutput().getData().get(i).getMVATOTColor().isEmpty() && !overLoadTransformerReport.getOutput().getData().get(i).getMVATOTColor().contains("NULL")) {
                                    String hexColor = overLoadTransformerReport.getOutput().getData().get(i).getMVATOTColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadTransformerReport.getOutput().getData().get(i).getMvatot())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadTransformerReport.getOutput().getData().get(i).getMvatot())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (overLoadTransformerReport.getOutput().getData().get(i).getLoading() != null && !overLoadTransformerReport.getOutput().getData().get(i).getLoading().contains("NULL")) {
                                if (!overLoadTransformerReport.getOutput().getData().get(i).getLOADINGColor().isEmpty() && !overLoadTransformerReport.getOutput().getData().get(i).getLOADINGColor().contains("NULL")) {
                                    String hexColor = overLoadTransformerReport.getOutput().getData().get(i).getLOADINGColor();

                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }

                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadTransformerReport.getOutput().getData().get(i).getLoading())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(overLoadTransformerReport.getOutput().getData().get(i).getLoading())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                        }

                        document.add(table1);
                        document.add(new Paragraph("\n"));
                        document.add(table2);
                        document.add(new Paragraph("\n"));
                        document.add(table3);
                        document.close();
                        status = "Download Report Successfully";
                    } else {
                        status = "No Report Found! Please try again later";
                    }
                }

                if (selectedReport.contains("Load Flow - Transformers")) {
                    if (transformerReport.getOutput().getData() != null) {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                        String fileName = "Load_Flow_Transformers" + timeStamp + ".pdf";
                        File file = new File(directory, fileName);

                        PdfWriter writer = new PdfWriter(file);
                        PdfDocument pdfDocument = new PdfDocument(writer);
                        Document document = new Document(pdfDocument, new PageSize(PageSize.A0));

                        String headerHexColor = "#183883";
                        if (headerHexColor.startsWith("#")) {
                            headerHexColor = headerHexColor.substring(1);
                        }
                        int hColor = (int) Long.parseLong(headerHexColor, 16);
                        int a = (hColor >> 16) & 0xFF;
                        int b = (hColor >> 8) & 0xFF;
                        int c = hColor & 0xFF;
                        DeviceRgb headerColor = new DeviceRgb(a, b, c);

                        //Add Image
                        @SuppressLint("UseCompatLoadingForDrawables")
                        Drawable drawable = getDrawable(R.drawable.ttpl_logo);
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bitmapData = stream.toByteArray();
                        ImageData imageData = ImageDataFactory.create(bitmapData);
                        Image image = new Image(imageData);
                        image.setWidth(70f);

                        float[] columnWidth1 = {100, 660};
                        Table table1 = new Table(columnWidth1);
                        //Table1 _01
                        table1.addCell(new Cell(1, 1).add(image).setBorder(Border.NO_BORDER));
                        table1.addCell(new Cell().add(new Paragraph("Load Flow - Transformer Report").setUnderline().setFontSize(14f).setFontColor(Color.convertRgbToCmyk(headerColor)).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

                        float[] columnWidth2 = {100, 560, 100};
                        Table table2 = new Table(columnWidth2);

                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMM - yy, HH : mm : ss", Locale.getDefault());
                        String formattedDate = dateFormat.format(currentDate);

                        if (dashBoardModel.getOutput() != null) {
                            StringBuilder Substaion = new StringBuilder();
                            StringBuilder division = new StringBuilder();
                            StringBuilder feeder = new StringBuilder();

                            if (dashBoardModel.getOutput().getGroup2().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup2().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup2().get(i).getGroup2() != null) {
                                        if (!Substaion.toString().contains(dashBoardModel.getOutput().getGroup2().get(i).getGroup2())) {
                                            Substaion.append(",  ").append(dashBoardModel.getOutput().getGroup2().get(i).getGroup2());
                                        }
                                    } else {
                                        if (!Substaion.toString().contains("None")) {
                                            Substaion.append(",  ").append("None");
                                        }
                                    }

                                }
                            }

                            if (dashBoardModel.getOutput().getGroup1().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup1().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup1().get(i).getGroup1() != null) {
                                        if (!division.toString().contains(dashBoardModel.getOutput().getGroup1().get(i).getGroup1())) {
                                            division.append(",  ").append(dashBoardModel.getOutput().getGroup1().get(i).getGroup1());
                                        }
                                    } else {
                                        if (!division.toString().contains("None")) {
                                            division.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            if (dashBoardModel.getOutput().getNetworkName().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getNetworkName().size(); i++) {
                                    if (dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId() != null) {
                                        if (!feeder.toString().contains(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId())) {
                                            feeder.append(",  ").append(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId());
                                        }
                                    } else {
                                        if (!feeder.toString().contains("None")) {
                                            feeder.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            //Table2 _02
                            table2.addCell(new Cell().add(new Paragraph("User Name :- ").setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(prefManager.getUserName()).setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _04
                            table2.addCell(new Cell().add(new Paragraph("Date :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(formattedDate).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _05
                            table2.addCell(new Cell().add(new Paragraph("Substation Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(Substaion.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _06
                            table2.addCell(new Cell().add(new Paragraph("Division Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(division.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _07
                            table2.addCell(new Cell().add(new Paragraph("Feeder Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(feeder.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                        }

                        float[] columnWidth3 = {160, 160, 160, 160, 160, 100, 100, 100, 130, 130, 130, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
                        Table table3 = new Table(columnWidth3);

                        table3.addHeaderCell("Equipment No.").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("From Node").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("To Node").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Equipment Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Code").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Cap Nom\n(kva)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Prim Volt\n(kVLL)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Sec Volt\n(kVLL)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Total Thru Power\n(kW)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Total Thru Power\n(kVar)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Total Thru Power\n(kVA)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Pf avg\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("IBal\n(A)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Angel I\n(o)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Total Loss\n(kw)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Total Loss\n(kvar)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Buck\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Boost\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Vset\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Reg Section Id").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
                        table3.addHeaderCell("Loading\n(%)").setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(10).setTextAlignment(TextAlignment.LEFT);

                        for (int i = 0; i < transformerReport.getOutput().getData().size(); i++) {

                            if (transformerReport.getOutput().getData().get(i).getEqNo() != null && !transformerReport.getOutput().getData().get(i).getEqNo().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getEqNoColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getEqNoColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getEqNoColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getEqNo())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getEqNo())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getFromNodeId() != null && !transformerReport.getOutput().getData().get(i).getFromNodeId().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getFromNodeIdColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getFromNodeIdColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getFromNodeIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getFromNodeId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getFromNodeId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getToNodeId() != null && !transformerReport.getOutput().getData().get(i).getToNodeId().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getToNodeIdColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getToNodeIdColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getToNodeIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getToNodeId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getToNodeId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getEqId() != null && !transformerReport.getOutput().getData().get(i).getEqId().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getEqIdColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getEqIdColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getEqIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getEqId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getEqId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getEqCode() != null && !transformerReport.getOutput().getData().get(i).getEqCode().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getEqCodeColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getEqCodeColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getEqCodeColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getEqCode())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getEqCode())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getXfoKVANom() != null && !transformerReport.getOutput().getData().get(i).getXfoKVANom().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getXfoKVANomColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getXfoKVANomColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getXfoKVANomColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoKVANom())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoKVANom())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getXfoKVLL1() != null && !transformerReport.getOutput().getData().get(i).getXfoKVLL1().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getXfoKVLL1Color().isEmpty() && !transformerReport.getOutput().getData().get(i).getXfoKVLL1Color().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getXfoKVLL1Color();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoKVLL1())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoKVLL1())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getXfoKVLL2() != null && !transformerReport.getOutput().getData().get(i).getXfoKVLL2().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getXfoKVLL2Color().isEmpty() && !transformerReport.getOutput().getData().get(i).getXfoKVLL2Color().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getXfoKVLL2Color();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoKVLL2())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoKVLL2())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getKwtot() != null && !transformerReport.getOutput().getData().get(i).getKwtot().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getKWTOTColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getKWTOTColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getKWTOTColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getKwtot())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getKwtot())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getKvartot() != null && !transformerReport.getOutput().getData().get(i).getKvartot().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getKVARTOTColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getKVARTOTColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getKVARTOTColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getKvartot())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getKvartot())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getKvatot() != null && !transformerReport.getOutput().getData().get(i).getKvatot().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getKVATOTColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getKVATOTColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getKVATOTColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getKvatot())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getKvatot())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getPFavg() != null && !transformerReport.getOutput().getData().get(i).getPFavg().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getPFavgColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getPFavgColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getPFavgColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getPFavg())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getPFavg())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getIBal() != null && !transformerReport.getOutput().getData().get(i).getIBal().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getIBalColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getIBalColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getIBalColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getIBal())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getIBal())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getIAngle() != null && !transformerReport.getOutput().getData().get(i).getIAngle().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getIAngleColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getIAngleColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getIAngleColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getIAngle())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getIAngle())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getKWLossTot() != null && !transformerReport.getOutput().getData().get(i).getKWLossTot().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getKWLossTotColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getKWLossTotColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getKWLossTotColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getKWLossTot())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getKWLossTot())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getKVARLossTot() != null && !transformerReport.getOutput().getData().get(i).getKVARLossTot().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getKVARLossTotColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getKVARLossTotColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getKVARLossTotColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getKVARLossTot())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getKVARLossTot())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getXfoBuck() != null && !transformerReport.getOutput().getData().get(i).getXfoBuck().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getXfoBuckColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getXfoBuckColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getXfoBuckColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoBuck())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoBuck())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getXfoBoost() != null && !transformerReport.getOutput().getData().get(i).getXfoBoost().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getXfoBoostColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getXfoBoostColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getXfoBoostColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoBoost())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoBoost())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getXfoVset() != null && !transformerReport.getOutput().getData().get(i).getXfoVset().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getXfoVsetColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getXfoVsetColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getXfoVsetColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoVset())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoVset())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getXfoRegId() != null && !transformerReport.getOutput().getData().get(i).getXfoRegId().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getXfoRegIdColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getXfoRegIdColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getXfoRegIdColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoRegId())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getXfoRegId())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                            if (transformerReport.getOutput().getData().get(i).getLoading() != null && !transformerReport.getOutput().getData().get(i).getLoading().equals("NULL")) {
                                if (!transformerReport.getOutput().getData().get(i).getLOADINGColor().isEmpty() && !transformerReport.getOutput().getData().get(i).getLOADINGColor().contains("NULL")) {
                                    String hexColor = transformerReport.getOutput().getData().get(i).getLOADINGColor();
                                    if (hexColor.startsWith("#")) {
                                        hexColor = hexColor.substring(1);
                                    }
                                    int color = (int) Long.parseLong(hexColor, 16);
                                    int red = (color >> 16) & 0xFF;
                                    int green = (color >> 8) & 0xFF;
                                    int blue = color & 0xFF;
                                    DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                    table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getLoading())));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph(transformerReport.getOutput().getData().get(i).getLoading())));
                                }
                            } else {
                                table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).add(new Paragraph("")));
                            }

                        }

                        document.add(table1);
                        document.add(new Paragraph("\n"));
                        document.add(table2);
                        document.add(new Paragraph("\n"));
                        document.add(table3);
                        document.close();
                        status = "Download Report Successfully";
                    } else {
                        status = "No Report Found! Please try again later";
                    }
                }

                if (selectedReport.contains("Short-Circuit - Detailed")) {
                    if (shortCircuitDetailedModel.getOutput().getData() != null) {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                        String fileName = "Short-Circuit Detailed" + timeStamp + ".pdf";
                        File file = new File(directory, fileName);

                        PdfWriter writer = new PdfWriter(file);
                        PdfDocument pdfDocument = new PdfDocument(writer);
                        Document document = new Document(pdfDocument, new PageSize(PageSize.A0));

                        String headerHexColor = "#183883";
                        if (headerHexColor.startsWith("#")) {
                            headerHexColor = headerHexColor.substring(1);
                        }
                        int hColor = (int) Long.parseLong(headerHexColor, 16);
                        int a = (hColor >> 16) & 0xFF;
                        int b = (hColor >> 8) & 0xFF;
                        int c = hColor & 0xFF;
                        DeviceRgb headerColor = new DeviceRgb(a, b, c);

                        //Add Image
                        @SuppressLint("UseCompatLoadingForDrawables")
                        Drawable drawable = getDrawable(R.drawable.ttpl_logo);
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bitmapData = stream.toByteArray();
                        ImageData imageData = ImageDataFactory.create(bitmapData);
                        Image image = new Image(imageData);
                        image.setWidth(70f);

                        float[] columnWidth1 = {100, 660};
                        Table table1 = new Table(columnWidth1);
                        //Table1 _01
                        table1.addCell(new Cell(1, 1).add(image).setBorder(Border.NO_BORDER));
                        table1.addCell(new Cell().add(new Paragraph("Short-Circuit Detailed Report").setUnderline().setFontSize(14f).setFontColor(Color.convertRgbToCmyk(headerColor)).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

                        float[] columnWidth2 = {100, 560, 100};
                        Table table2 = new Table(columnWidth2);

                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMM - yy, HH : mm : ss", Locale.getDefault());
                        String formattedDate = dateFormat.format(currentDate);

                        if (dashBoardModel.getOutput() != null) {
                            StringBuilder Substaion = new StringBuilder();
                            StringBuilder division = new StringBuilder();
                            StringBuilder feeder = new StringBuilder();

                            if (dashBoardModel.getOutput().getGroup2().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup2().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup2().get(i).getGroup2() != null) {
                                        if (!Substaion.toString().contains(dashBoardModel.getOutput().getGroup2().get(i).getGroup2())) {
                                            Substaion.append(",  ").append(dashBoardModel.getOutput().getGroup2().get(i).getGroup2());
                                        }
                                    } else {
                                        if (!Substaion.toString().contains("None")) {
                                            Substaion.append(",  ").append("None");
                                        }
                                    }

                                }
                            }

                            if (dashBoardModel.getOutput().getGroup1().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup1().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup1().get(i).getGroup1() != null) {
                                        if (!division.toString().contains(dashBoardModel.getOutput().getGroup1().get(i).getGroup1())) {
                                            division.append(",  ").append(dashBoardModel.getOutput().getGroup1().get(i).getGroup1());
                                        }
                                    } else {
                                        if (!division.toString().contains("None")) {
                                            division.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            if (dashBoardModel.getOutput().getNetworkName().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getNetworkName().size(); i++) {
                                    if (dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId() != null) {
                                        if (!feeder.toString().contains(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId())) {
                                            feeder.append(",  ").append(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId());
                                        }
                                    } else {
                                        if (!feeder.toString().contains("None")) {
                                            feeder.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            //Table2 _02
                            table2.addCell(new Cell().add(new Paragraph("User Name :- ").setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(prefManager.getUserName()).setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _04
                            table2.addCell(new Cell().add(new Paragraph("Date :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(formattedDate).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _05
                            table2.addCell(new Cell().add(new Paragraph("Substation Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(Substaion.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _06
                            table2.addCell(new Cell().add(new Paragraph("Division Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(division.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _07
                            table2.addCell(new Cell().add(new Paragraph("Feeder Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(feeder.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                        }

                        float[] columnWidth3 = {160, 160, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 160};
                        Table table3 = new Table(columnWidth3);

                        if (shortCircuitDetailedModel.getOutput().getColumns1() != null && !shortCircuitDetailedModel.getOutput().getColumns1().isEmpty()) {
                            for (int i = 0; i < shortCircuitDetailedModel.getOutput().getColumns1().size(); i++) {
                                if (shortCircuitDetailedModel.getOutput().getColumns1().get(i) != null) {
                                    table3.addHeaderCell(shortCircuitDetailedModel.getOutput().getColumns1().get(i)).setBackgroundColor(headerColor).setFontColor(ColorConstants.WHITE).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.LEFT);
                                }
                            }
                        }

                        if (shortCircuitDetailedModel.getOutput().getData() != null && !shortCircuitDetailedModel.getOutput().getData().isEmpty()) {

                            for (int i = 0; i < shortCircuitDetailedModel.getOutput().getData().size(); i++) {

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getFeederId() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getFeederId().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getFeederIdColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getFeederIdColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getFeederIdColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getFeederId())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getFeederId())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getNodeId() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getNodeId().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getNodeIdColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getNodeIdColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getNodeIdColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getNodeId())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getNodeId())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getPhase() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getPhase().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getPhaseColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getPhaseColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getPhaseColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getPhase())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getPhase())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getKvln() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getKvln().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getKVLNColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getKVLNColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getKVLNColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getKvln())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getKvln())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLLamp() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLLamp().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLLamp())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLLamp())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmax() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmax().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmaxColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmaxColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmaxColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmax())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmax())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmaxZ() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmaxZ().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmaxZColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmaxZColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmaxZColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmaxZ())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmaxZ())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmin() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmin().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKminColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKminColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKminColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmin())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKmin())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKminZ() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKminZ().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKminZColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKminZColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKminZColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKminZ())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLLampKminZ())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLGamp() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLGamp().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLGamp())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLGamp())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKmax() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKmax().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKmaxColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKmaxColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKmaxColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKmax())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKmax())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKmin() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKmin().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKminColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKminColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKminColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKmin())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLGampKmin())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLamp() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLamp().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLampColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLampColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLampColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLamp())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLamp())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLampMin() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLampMin().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLampMinColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLampMinColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLampMinColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLampMin())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLampMin())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmax() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmax().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmaxColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmaxColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmaxColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmax())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmax())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmaxZ() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmaxZ().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmaxColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmaxColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmaxColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmaxZ())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmaxZ())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmin() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmin().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKminColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKminColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKminColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmin())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKmin())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKminZ() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKminZ().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKminZColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKminZColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKminZColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKminZ())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLLampKminZ())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMax() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMax().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMaxColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMaxColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMaxColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMax())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMax())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMin() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMin().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMinColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMinColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMinColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMin())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLGampMin())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmax() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmax().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmaxColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmaxColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmaxColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmax())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmax())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmaxZ() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmaxZ().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmaxZColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmaxZColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmaxZColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmaxZ())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmaxZ())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmin() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmin().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKminColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKminColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKminColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmin())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKmin())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKminZ() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKminZ().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKminZColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKminZColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKminZColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKminZ())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getLGampKminZ())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (shortCircuitDetailedModel.getOutput().getData().get(i).getDistance() != null && !shortCircuitDetailedModel.getOutput().getData().get(i).getDistance().equals("NULL")) {
                                    if (!shortCircuitDetailedModel.getOutput().getData().get(i).getDistanceColor().isEmpty() && !shortCircuitDetailedModel.getOutput().getData().get(i).getDistanceColor().contains("NULL")) {
                                        String hexColor = shortCircuitDetailedModel.getOutput().getData().get(i).getDistanceColor();
                                        if (hexColor.startsWith("#")) {
                                            hexColor = hexColor.substring(1);
                                        }
                                        int color = (int) Long.parseLong(hexColor, 16);
                                        int red = (color >> 16) & 0xFF;
                                        int green = (color >> 8) & 0xFF;
                                        int blue = color & 0xFF;
                                        DeviceRgb customColor = new DeviceRgb(red, green, blue);

                                        table3.addCell(new Cell().setBackgroundColor(customColor).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getDistance())));
                                    } else {
                                        table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph(shortCircuitDetailedModel.getOutput().getData().get(i).getDistance())));
                                    }
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).add(new Paragraph("")));
                                }

                            }
                        }

                        document.add(table1);
                        document.add(new Paragraph("\n"));
                        document.add(table2);
                        document.add(new Paragraph("\n"));
                        document.add(table3);
                        document.close();
                        status = "Download Report Successfully";
                    } else {
                        status = "No Report Found! Please try again later";
                    }
                }

                if (selectedReport.contains("Short-Circuit - Fault Flow Detailed")) {
                    if (faultFlowDetailedModel.getOutput().getData() != null) {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                        String fileName = "Short-Circuit - Fault Flow Detailed" + timeStamp + ".pdf";
                        File file = new File(directory, fileName);

                        PdfWriter writer = new PdfWriter(file);
                        PdfDocument pdfDocument = new PdfDocument(writer);
                        Document document = new Document(pdfDocument, new PageSize(PageSize.A0));

                        String headerHexColor = "#183883";
                        if (headerHexColor.startsWith("#")) {
                            headerHexColor = headerHexColor.substring(1);
                        }
                        int hColor = (int) Long.parseLong(headerHexColor, 16);
                        int a = (hColor >> 16) & 0xFF;
                        int b = (hColor >> 8) & 0xFF;
                        int c = hColor & 0xFF;
                        DeviceRgb headerColor = new DeviceRgb(a, b, c);

                        //Add Image
                        @SuppressLint("UseCompatLoadingForDrawables")
                        Drawable drawable = getDrawable(R.drawable.ttpl_logo);
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bitmapData = stream.toByteArray();
                        ImageData imageData = ImageDataFactory.create(bitmapData);
                        Image image = new Image(imageData);
                        image.setWidth(70f);

                        float[] columnWidth1 = {100, 660};
                        Table table1 = new Table(columnWidth1);
                        //Table1 _01
                        table1.addCell(new Cell(1, 1).add(image).setBorder(Border.NO_BORDER));
                        table1.addCell(new Cell().add(new Paragraph("Short-Circuit - Fault Flow Detailed Report").setUnderline().setFontSize(14f).setFontColor(Color.convertRgbToCmyk(headerColor)).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));

                        float[] columnWidth2 = {100, 560, 100};
                        Table table2 = new Table(columnWidth2);

                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMM - yy, HH : mm : ss", Locale.getDefault());
                        String formattedDate = dateFormat.format(currentDate);

                        if (dashBoardModel.getOutput() != null) {
                            StringBuilder Substaion = new StringBuilder();
                            StringBuilder division = new StringBuilder();
                            StringBuilder feeder = new StringBuilder();

                            if (dashBoardModel.getOutput().getGroup2().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup2().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup2().get(i).getGroup2() != null) {
                                        if (!Substaion.toString().contains(dashBoardModel.getOutput().getGroup2().get(i).getGroup2())) {
                                            Substaion.append(",  ").append(dashBoardModel.getOutput().getGroup2().get(i).getGroup2());
                                        }
                                    } else {
                                        if (!Substaion.toString().contains("None")) {
                                            Substaion.append(",  ").append("None");
                                        }
                                    }

                                }
                            }

                            if (dashBoardModel.getOutput().getGroup1().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getGroup1().size(); i++) {
                                    if (dashBoardModel.getOutput().getGroup1().get(i).getGroup1() != null) {
                                        if (!division.toString().contains(dashBoardModel.getOutput().getGroup1().get(i).getGroup1())) {
                                            division.append(",  ").append(dashBoardModel.getOutput().getGroup1().get(i).getGroup1());
                                        }
                                    } else {
                                        if (!division.toString().contains("None")) {
                                            division.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            if (dashBoardModel.getOutput().getNetworkName().size() >= 0) {
                                for (int i = 0; i < dashBoardModel.getOutput().getNetworkName().size(); i++) {
                                    if (dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId() != null) {
                                        if (!feeder.toString().contains(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId())) {
                                            feeder.append(",  ").append(dashBoardModel.getOutput().getNetworkName().get(i).getNetworkId());
                                        }
                                    } else {
                                        if (!feeder.toString().contains("None")) {
                                            feeder.append(",  ").append("None");
                                        }
                                    }
                                }
                            }

                            //Table2 _02
                            table2.addCell(new Cell().add(new Paragraph("User Name :- ").setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(prefManager.getUserName()).setFontSize(10f)).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _04
                            table2.addCell(new Cell().add(new Paragraph("Date :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(formattedDate).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _05
                            table2.addCell(new Cell().add(new Paragraph("Substation Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(Substaion.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _06
                            table2.addCell(new Cell().add(new Paragraph("Division Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(division.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

                            //Table1 _07
                            table2.addCell(new Cell().add(new Paragraph("Feeder Name :- ").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph(feeder.toString()).setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                            table2.addCell(new Cell().add(new Paragraph("").setFontSize(10f).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
                        }

                        float[] columnWidth3 = {160, 100, 100, 160, 100, 100, 100, 100, 100, 100, 100};
                        Table table3 = new Table(columnWidth3);

                        if (faultFlowDetailedModel.getOutput().getColumns1() != null && !faultFlowDetailedModel.getOutput().getColumns1().isEmpty()) {
                            for (int i = 0; i < faultFlowDetailedModel.getOutput().getColumns1().size() - 1; i++) {
                                if (faultFlowDetailedModel.getOutput().getColumns1().get(i) != null) {
                                    Cell headerCell = new Cell()
                                            .add(new Paragraph(faultFlowDetailedModel.getOutput().getColumns1().get(i))
                                                    .setFontSize(22)
                                                    .setPaddingLeft(5)
                                                    .setTextAlignment(TextAlignment.CENTER))
                                            .setBackgroundColor(headerColor)
                                            .setFontColor(ColorConstants.WHITE);
                                    table3.addHeaderCell(headerCell);
                                }
                            }
                        }

                        if (faultFlowDetailedModel.getOutput().getData() != null && !faultFlowDetailedModel.getOutput().getData().isEmpty()) {

                            for (int i = 0; i < faultFlowDetailedModel.getOutput().getData().size(); i++) {

                                if (faultFlowDetailedModel.getOutput().getData().get(i).getFaultedItem() != null && !faultFlowDetailedModel.getOutput().getData().get(i).getFaultedItem().equals("NULL")) {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(faultFlowDetailedModel.getOutput().getData().get(i).getFaultedItem()).setMargin(0)));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("")));
                                }

                                if (faultFlowDetailedModel.getOutput().getData().get(i).getFaultType() != null && !faultFlowDetailedModel.getOutput().getData().get(i).getFaultType().equals("NULL")) {

                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(faultFlowDetailedModel.getOutput().getData().get(i).getFaultType()).setMargin(0)));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("")));
                                }

                                if (faultFlowDetailedModel.getOutput().getData().get(i).getFaultPhase() != null && !faultFlowDetailedModel.getOutput().getData().get(i).getFaultPhase().equals("NULL")) {

                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(faultFlowDetailedModel.getOutput().getData().get(i).getFaultPhase()).setMargin(0)));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(20).setPaddingLeft(0).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("")));
                                }

                                if (faultFlowDetailedModel.getOutput().getData().get(i).getFeederId() != null && !faultFlowDetailedModel.getOutput().getData().get(i).getFeederId().equals("NULL")) {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(faultFlowDetailedModel.getOutput().getData().get(i).getFeederId()).setMargin(0)));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("")));
                                }

                                if (faultFlowDetailedModel.getOutput().getData().get(i).getSectionId() != null && !faultFlowDetailedModel.getOutput().getData().get(i).getSectionId().equals("NULL")) {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(faultFlowDetailedModel.getOutput().getData().get(i).getSectionId()).setMargin(0)));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setTextAlignment(TextAlignment.CENTER).setPaddingLeft(5).add(new Paragraph("")));
                                }

                                if (faultFlowDetailedModel.getOutput().getData().get(i).getEquipmentId() != null && !faultFlowDetailedModel.getOutput().getData().get(i).getEquipmentId().equals("NULL")) {

                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(faultFlowDetailedModel.getOutput().getData().get(i).getEquipmentId()).setMargin(0)));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("")));
                                }

                                if (faultFlowDetailedModel.getOutput().getData().get(i).getCode() != null && !faultFlowDetailedModel.getOutput().getData().get(i).getCode().equals("NULL")) {

                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(faultFlowDetailedModel.getOutput().getData().get(i).getCode()).setMargin(0)));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("")));
                                }

                                Double loadingAPercent = faultFlowDetailedModel.getOutput().getData().get(i).getLoadingAPercent();
                                if (loadingAPercent != null) {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(faultFlowDetailedModel.getOutput().getData().get(i).getLoadingAPercent())).setMargin(0)));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("")));
                                }

                                Double thruPowerAKw = faultFlowDetailedModel.getOutput().getData().get(i).getThruPowerAKw();
                                if (thruPowerAKw != null) {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(faultFlowDetailedModel.getOutput().getData().get(i).getThruPowerAKw())).setMargin(0)));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("")));
                                }

                                Double thruPowerAkvar = faultFlowDetailedModel.getOutput().getData().get(i).getThruPowerAkvar();
                                if (thruPowerAkvar!= null) {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(faultFlowDetailedModel.getOutput().getData().get(i).getThruPowerAkvar())).setMargin(0)));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("")));
                                }

                                Double vAPercent = faultFlowDetailedModel.getOutput().getData().get(i).getVaPercent();
                                if (vAPercent!= null) {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(faultFlowDetailedModel.getOutput().getData().get(i).getVaPercent())).setMargin(0)));
                                } else {
                                    table3.addCell(new Cell().setBackgroundColor(ColorConstants.WHITE).setFontColor(ColorConstants.BLACK).setFontSize(22).setPaddingLeft(5).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("")));
                                }

                            }
                        }

                        document.add(table1);
                        document.add(new Paragraph("\n"));
                        document.add(table2);
                        document.add(new Paragraph("\n"));
                        document.add(table3);
                        document.close();
                        status = "Download Report Successfully";
                    } else {
                        status = "No Report Found! Please try again later";
                    }
                }

            } catch (Exception e) {
                Log.d("Exception", e.getLocalizedMessage());
            }
            return status;
        }

        @Override
        protected void onPostExecute(String status) {
            super.onPostExecute(status);
            binding.downloadReportBtn.setVisibility(View.VISIBLE);
            binding.downloadProgress.setVisibility(View.GONE);

            if (status.equals("Download Report Successfully")) {
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), status, Snackbar.LENGTH_LONG);
                snack.setAction("View", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        openFolder();
                        Intent intent = new Intent(Reports.this, PdfViewerActivity.class);
                        startActivity(intent);
                    }
                });
                snack.show();
            } else {
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), status, Snackbar.LENGTH_SHORT);
                snack.show();
            }
        }

    }

    private void getAbnormalConditionsReports(String loadFlowArgument) {
        JsonObject jsonObject = new JsonObject();
        Intent intent = getIntent();
        if (intent.getStringArrayListExtra("NetworkId") != null && intent.getStringExtra("Type") != null) {
            JsonArray jsonArray = new Gson().toJsonTree(intent.getStringArrayListExtra("NetworkId")).getAsJsonArray();
            jsonObject.addProperty("Username", prefManager.getUserName());
            jsonObject.add("NetworkId", jsonArray);
            jsonObject.addProperty("ReportName", loadFlowArgument);
            jsonObject.addProperty("Type", intent.getStringExtra("Type"));
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<AbnormalReport> call = apiInterface.getAbnormalReport("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<AbnormalReport>() {
                @Override
                public void onResponse(@NonNull Call<AbnormalReport> call, @NonNull Response<AbnormalReport> response) {
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    binding.analysisReport.setVisibility(View.VISIBLE);
                    if (response.code() == 200) {
                        ErrorPdfLogger.logApiSuccess(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + loadFlowArgument
                                + "Type : " + intent.getStringExtra("Type")
                                + "ProjectName : " + prefManager.getProjectName()
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        try {
                            abnormalReport = response.body();
                            assert abnormalReport != null;
                            if (abnormalReport.getOutput().getData() != null) {
                                abnormalConditionAdapter = new AbnormalConditionAdapter(Reports.this, abnormalReport.getOutput().getData(), Reports.this, reportName);
                                binding.analysisReport.setAdapter(abnormalConditionAdapter);
                                synchronized (Reports.this) {
                                    Reports.this.notify();
                                }
                            }
                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(Reports.this,e);
                            Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                        }
                    } else {
                        ErrorPdfLogger.logApiError(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + loadFlowArgument
                                + "Type : " + intent.getStringExtra("Type")
                                + "ProjectName : " + prefManager.getProjectName()
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                        snack.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AbnormalReport> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(
                            Reports.this,
                            "POST",
                            "/report/",
                            t
                    );
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            });
        }

    }

    private void getFeederLoading(String selectedReport) {
        if (selectedReport.contains("Load Flow - Feeder loading")) {
            downloadManager = new ExcelDownloadManager(prefManager);
            JsonObject jsonObject = new JsonObject();
            Intent intent = getIntent();
            JsonArray jsonArray = new Gson().toJsonTree(intent.getStringArrayListExtra("NetworkId")).getAsJsonArray();
            jsonObject.addProperty("Username", prefManager.getUserName());
            jsonObject.add("NetworkId", jsonArray);
            jsonObject.addProperty("ReportName", selectedReport);
            jsonObject.addProperty("Type", intent.getStringExtra("Type"));
            downloadManager.downloadExcel(jsonObject, new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    binding.analysisReport.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        ErrorPdfLogger.logApiSuccess(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + selectedReport
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        try {
                            byte[] rawData = response.body().bytes();
                            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "AnalysisReport");
                            if (!dir.exists()) dir.mkdirs();

                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                            File pdfFile = new File(dir, "LoadFlow-FeederLoading" + timeStamp + ".pdf");

                            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(rawData);

                            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfFile));
                            com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
                            Document document = new Document(pdfDoc, com.itextpdf.kernel.geom.PageSize.A4.rotate());

                            Workbook workbook = WorkbookFactory.create(byteArrayInputStream);
                            Sheet sheet = workbook.getSheetAt(0);

                            Row headerRow = null;
                            int headerRowIndex = 0;
                            int numColumns = 0;

                            for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                                Row row = sheet.getRow(i);
                                if (row == null) continue;

                                boolean isEmpty = true;
                                for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                                    if (row.getCell(j) != null && !row.getCell(j).toString().trim().isEmpty()) {
                                        isEmpty = false;
                                        break;
                                    }
                                }

                                if (!isEmpty) {
                                    headerRow = row;
                                    headerRowIndex = i;
                                    numColumns = row.getPhysicalNumberOfCells();
                                    break;
                                }
                            }

                            float[] columnWidths = new float[numColumns];
                            int totalRows = sheet.getPhysicalNumberOfRows();

                            for (int i = 0; i < totalRows; i++) {
                                Row row = sheet.getRow(i);
                                if (row == null) continue;
                                for (int j = 0; j < numColumns; j++) {
                                    if (row.getCell(j) != null) {
                                        String cellData = row.getCell(j).toString();
                                        int length = cellData.length();
                                        if (length > columnWidths[j]) {
                                            columnWidths[j] = length;
                                        }
                                    }
                                }
                            }

                            float totalWidth = 0;
                            for (float w : columnWidths) totalWidth += w;
                            float[] normalizedWidths = new float[numColumns];
                            for (int i = 0; i < numColumns; i++) {
                                normalizedWidths[i] = columnWidths[i] / totalWidth;
                            }

                            Table table = new Table(UnitValue.createPercentArray(normalizedWidths)).useAllAvailableWidth();

                            for (int col = 0; col < numColumns; col++) {
                                String header = (headerRow.getCell(col) != null) ? headerRow.getCell(col).toString() : "";
                                Cell headerCell = new Cell()
                                        .add(new Paragraph(header).setFontSize(8).setMultipliedLeading(1.0f).setNeutralRole().simulateBold()).setTextAlignment(TextAlignment.CENTER);
//                                .setWidth(UnitValue.createPercentValue(100f / numColumns));
                                table.addHeaderCell(headerCell);
                            }

                            for (int i = headerRowIndex + 1; i < totalRows; i++) {
                                Row row = sheet.getRow(i);
                                if (row == null) continue;

                                boolean isEmptyRow = true;
                                for (int j = 0; j < numColumns; j++) {
                                    if (row.getCell(j) != null && !row.getCell(j).toString().trim().isEmpty()) {
                                        isEmptyRow = false;
                                        break;
                                    }
                                }
                                if (isEmptyRow) continue;

                                boolean isHighlightRow = (i == 4 || i == 5 || i == 12 || i == 13);
                                for (int j = 0; j < numColumns; j++) {
                                    String cellData = "";
                                    if (row.getCell(j) != null) {
                                        if (row.getCell(j).getCellType() == CellType.NUMERIC) {
                                            double numValue = row.getCell(j).getNumericCellValue();
                                            cellData = String.format("%.2f", numValue);
                                        } else {
                                            cellData = row.getCell(j).toString();
                                        }
                                    }

                                    Cell cell = new Cell()
                                            .add(new Paragraph(cellData).setFontSize(7).setMultipliedLeading(1.0f))
                                            .setTextAlignment(TextAlignment.LEFT);
                                    if (j == 0) {
                                        cell.simulateBold();
                                        cell.setFontColor(ColorConstants.BLACK);
                                    }
                                    if (isHighlightRow) {
                                        cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                                    }
                                    table.addCell(cell);
                                }
                            }

                            document.add(table);
                            document.close();
                            workbook.close();

                            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "download successfully!", Snackbar.LENGTH_LONG);
//                            snack.setAction("Locate", v -> openFolder());
                            snack.setAction("Locate", v -> {
                                Intent intent = new Intent(Reports.this, PdfViewerActivity.class);
                                startActivity(intent);
                            });
                            snack.show();

                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(Reports.this,e);
                            e.getLocalizedMessage();
                            Snackbar.make(findViewById(android.R.id.content), "PDF conversion failed!", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        ErrorPdfLogger.logApiError(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + selectedReport
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        System.out.println("Failed response: " + response.code());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(
                            Reports.this,
                            "POST",
                            "/report/",
                            t
                    );
                    System.out.println("API call failed: " + t.getMessage());
                }
            });
        }
    }

    private void getDetailedReports(String loadFlowArgument) {
        JsonObject jsonObject = new JsonObject();
        Intent intent = getIntent();
        if (intent.getStringArrayListExtra("NetworkId") != null && intent.getStringExtra("Type") != null) {
            JsonArray jsonArray = new Gson().toJsonTree(intent.getStringArrayListExtra("NetworkId")).getAsJsonArray();
            jsonObject.addProperty("Username", prefManager.getUserName());
            jsonObject.add("NetworkId", jsonArray);
            jsonObject.addProperty("ReportName", loadFlowArgument);
            jsonObject.addProperty("Type", intent.getStringExtra("Type"));
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<DetailedReport> call = apiInterface.getDetailedReport("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<DetailedReport>() {
                @Override
                public void onResponse(@NonNull Call<DetailedReport> call, @NonNull Response<DetailedReport> response) {
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    binding.analysisReport.setVisibility(View.VISIBLE);
                    if (response.code() == 200) {
                        ErrorPdfLogger.logApiSuccess(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + loadFlowArgument
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        try {
                            detailedReport = response.body();
                            assert detailedReport != null;
                            if (detailedReport.getOutput() != null) {
                                if (detailedReport.getOutput().getData() != null) {
                                    detailedReportAdapter = new DetailedReportAdapter(Reports.this, detailedReport.getOutput().getData(), Reports.this, reportName);
                                    binding.analysisReport.setAdapter(detailedReportAdapter);
                                    synchronized (Reports.this) {
                                        Reports.this.notify();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(Reports.this,e);
                            Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                        }
                    } else {
                        ErrorPdfLogger.logApiError(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + loadFlowArgument
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                        snack.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DetailedReport> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(
                            Reports.this,
                            "POST",
                            "/report/",
                            t
                    );
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            });
        }
    }

    private void getOverloadedConductorsReports(String loadFlowArgument) {
        JsonObject jsonObject = new JsonObject();
        Intent intent = getIntent();
        if (intent.getStringArrayListExtra("NetworkId") != null && intent.getStringExtra("Type") != null) {
            JsonArray jsonArray = new Gson().toJsonTree(intent.getStringArrayListExtra("NetworkId")).getAsJsonArray();
            jsonObject.addProperty("Username", prefManager.getUserName());
            jsonObject.add("NetworkId", jsonArray);
            jsonObject.addProperty("ReportName", loadFlowArgument);
            jsonObject.addProperty("Type", intent.getStringExtra("Type"));
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<OverLoadConductorReport> call = apiInterface.getOverLoadConductorReport("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<OverLoadConductorReport>() {
                @Override
                public void onResponse(@NonNull Call<OverLoadConductorReport> call, @NonNull Response<OverLoadConductorReport> response) {
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    binding.analysisReport.setVisibility(View.VISIBLE);
                    if (response.code() == 200) {
                        ErrorPdfLogger.logApiSuccess(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + loadFlowArgument
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        try {
                            overLoadConductorReport = response.body();
                            assert overLoadConductorReport != null;
                            if (overLoadConductorReport.getOutput().getData() != null) {
                                constructorAdapter = new OverLoadedConstructorAdapter(Reports.this, overLoadConductorReport.getOutput().getData(), Reports.this, reportName);
                                binding.analysisReport.setAdapter(constructorAdapter);
                                synchronized (Reports.this) {
                                    Reports.this.notify();
                                }
                            }
                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(Reports.this,e);
                            Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                        }
                    } else {
                        ErrorPdfLogger.logApiError(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + loadFlowArgument
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                        snack.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<OverLoadConductorReport> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(Reports.this, "POST", "/report/", t);
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            });
        }
    }

    private void getOverLoadedlinesAndCablesReports(String loadFlowArgument) {
        JsonObject jsonObject = new JsonObject();
        Intent intent = getIntent();
        if (intent.getStringArrayListExtra("NetworkId") != null && intent.getStringExtra("Type") != null) {
            JsonArray jsonArray = new Gson().toJsonTree(intent.getStringArrayListExtra("NetworkId")).getAsJsonArray();
            jsonObject.addProperty("Username", prefManager.getUserName());
            jsonObject.add("NetworkId", jsonArray);
            jsonObject.addProperty("ReportName", loadFlowArgument);
            jsonObject.addProperty("Type", intent.getStringExtra("Type"));
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<OverLoadLineAndCablesReport> call = apiInterface.getOverLoadLineAndCablesReport("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<OverLoadLineAndCablesReport>() {
                @Override
                public void onResponse(@NonNull Call<OverLoadLineAndCablesReport> call, @NonNull Response<OverLoadLineAndCablesReport> response) {
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    binding.analysisReport.setVisibility(View.VISIBLE);
                    if (response.code() == 200) {
                        ErrorPdfLogger.logApiSuccess(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + loadFlowArgument
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        try {
                            overLoadLineAndCablesReport = response.body();
                            assert overLoadLineAndCablesReport != null;
                            if (overLoadLineAndCablesReport.getOutput().getData() != null) {
                                overLoadedLinesAndCablesAdapter = new OverLoadedLinesAndCablesAdapter(Reports.this, overLoadLineAndCablesReport.getOutput().getData(), Reports.this, reportName);
                                binding.analysisReport.setAdapter(overLoadedLinesAndCablesAdapter);
                                synchronized (Reports.this) {
                                    Reports.this.notify();
                                }
                            }
                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(Reports.this,e);
                            Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                        }

                    } else {
                        ErrorPdfLogger.logApiError(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + loadFlowArgument
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                        snack.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<OverLoadLineAndCablesReport> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(Reports.this, "POST", "/report/", t);
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            });
        }
    }

    private void getOverLoadedTransformer(String loadFlowArgument) {
        JsonObject jsonObject = new JsonObject();
        Intent intent = getIntent();
        if (intent.getStringArrayListExtra("NetworkId") != null && intent.getStringExtra("Type") != null) {
            JsonArray jsonArray = new Gson().toJsonTree(intent.getStringArrayListExtra("NetworkId")).getAsJsonArray();
            jsonObject.addProperty("Username", prefManager.getUserName());
            jsonObject.add("NetworkId", jsonArray);
            jsonObject.addProperty("ReportName", loadFlowArgument);
            jsonObject.addProperty("Type", intent.getStringExtra("Type"));
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<OverLoadTransformerReport> call = apiInterface.getOverLoadTransformerReport("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<OverLoadTransformerReport>() {
                @Override
                public void onResponse(@NonNull Call<OverLoadTransformerReport> call, @NonNull Response<OverLoadTransformerReport> response) {
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    binding.analysisReport.setVisibility(View.VISIBLE);
                    if (response.code() == 200) {
                        ErrorPdfLogger.logApiSuccess(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + loadFlowArgument
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        try {
                            overLoadTransformerReport = response.body();
                            assert overLoadTransformerReport != null;
                            if (overLoadTransformerReport.getOutput().getData() != null) {
                                overLoadTransformerAdapter = new OverLoadTransformerAdapter(Reports.this, overLoadTransformerReport.getOutput().getData(), Reports.this, reportName);
                                binding.analysisReport.setAdapter(overLoadTransformerAdapter);
                                synchronized (Reports.this) {
                                    Reports.this.notify();
                                }
                            }
                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(Reports.this,e);
                            Log.d("Exception", e.getLocalizedMessage());
                        }
                    } else {
                        ErrorPdfLogger.logApiError(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + loadFlowArgument
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                        snack.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<OverLoadTransformerReport> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(Reports.this, "POST", "/report/", t);
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            });
        }
    }

    private void getTransformerReports(String loadFlowArgument) {
        JsonObject jsonObject = new JsonObject();
        Intent intent = getIntent();
        if (intent.getStringArrayListExtra("NetworkId") != null && intent.getStringExtra("Type") != null) {
            JsonArray jsonArray = new Gson().toJsonTree(intent.getStringArrayListExtra("NetworkId")).getAsJsonArray();
            jsonObject.addProperty("Username", prefManager.getUserName());
            jsonObject.add("NetworkId", jsonArray);
            jsonObject.addProperty("ReportName", loadFlowArgument);
            jsonObject.addProperty("Type", intent.getStringExtra("Type"));
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<TransformerReport> call = apiInterface.getTransformerReport("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<TransformerReport>() {
                @Override
                public void onResponse(@NonNull Call<TransformerReport> call, @NonNull Response<TransformerReport> response) {
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    binding.analysisReport.setVisibility(View.VISIBLE);
                    if (response.code() == 200) {
                        ErrorPdfLogger.logApiSuccess(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + loadFlowArgument
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        try {
                            transformerReport = response.body();
                            assert transformerReport != null;
                            if (transformerReport.getOutput() != null) {
                                if (transformerReport.getOutput().getData() != null) {
                                    transformerReportAdapter = new TransformerReportAdapter(Reports.this, transformerReport.getOutput().getData(), Reports.this, reportName);
                                    binding.analysisReport.setAdapter(transformerReportAdapter);
                                    synchronized (Reports.this) {
                                        Reports.this.notify();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(Reports.this,e);
                            Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                        }
                    } else {
                        ErrorPdfLogger.logApiError(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + loadFlowArgument
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                        snack.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TransformerReport> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(Reports.this, "POST", "/report/", t);
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            });
        }
    }

    private void getShortCircuitDetailed(String selectedReport) {
        JsonObject jsonObject = new JsonObject();
        Intent intent = getIntent();
        if (intent.getStringArrayListExtra("NetworkId") != null && intent.getStringExtra("Type") != null) {
            JsonArray jsonArray = new Gson().toJsonTree(intent.getStringArrayListExtra("NetworkId")).getAsJsonArray();
            jsonObject.addProperty("Username", prefManager.getUserName());
            jsonObject.add("NetworkId", jsonArray);
            jsonObject.addProperty("ReportName", selectedReport);
            jsonObject.addProperty("Type", intent.getStringExtra("Type"));
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<ShortCircuitDetailedModel> call = apiInterface.ShortCircuitDetailed("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<ShortCircuitDetailedModel>() {
                @Override
                public void onResponse(@NonNull Call<ShortCircuitDetailedModel> call, @NonNull Response<ShortCircuitDetailedModel> response) {
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    binding.analysisReport.setVisibility(View.VISIBLE);
                    if (response.code() == 200) {
                        ErrorPdfLogger.logApiSuccess(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + selectedReport
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        try {
                            shortCircuitDetailedModel = response.body();
                            assert shortCircuitDetailedModel != null;
                            if (shortCircuitDetailedModel.getOutput() != null) {
                                if (shortCircuitDetailedModel.getOutput().getData() != null && !shortCircuitDetailedModel.getOutput().getData().isEmpty()) {
                                    shortCircuitDetailedAdapter = new ShortCircuitDetailedAdapter(Reports.this, shortCircuitDetailedModel.getOutput().getData(), Reports.this, reportName);
                                    binding.analysisReport.setAdapter(shortCircuitDetailedAdapter);
                                    synchronized (Reports.this) {
                                        Reports.this.notify();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(Reports.this,e);
                            Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                        }
                    } else {
                        ErrorPdfLogger.logApiError(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + selectedReport
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                        snack.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ShortCircuitDetailedModel> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(Reports.this, "POST", "/report/", t);
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            });
        }
    }
    private void getFaultFlowDetailed(String selectedReport) {
        JsonObject jsonObject = new JsonObject();
        Intent intent = getIntent();
        if (intent.getStringArrayListExtra("NetworkId") != null && intent.getStringExtra("Type") != null) {
            JsonArray jsonArray = new Gson().toJsonTree(intent.getStringArrayListExtra("NetworkId")).getAsJsonArray();
            jsonObject.addProperty("Username", prefManager.getUserName());
            jsonObject.add("NetworkId", jsonArray);
            jsonObject.addProperty("ReportName", selectedReport);
            jsonObject.addProperty("Type", intent.getStringExtra("Type"));
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<FaultFlowDetailedModel> call = apiInterface.FaultFlowDetailed("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<FaultFlowDetailedModel>() {
                @Override
                public void onResponse(@NonNull Call<FaultFlowDetailedModel> call, @NonNull Response<FaultFlowDetailedModel> response) {
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    binding.analysisReport.setVisibility(View.VISIBLE);
                    if (response.code() == 200) {
                        ErrorPdfLogger.logApiSuccess(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + selectedReport
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        try {
                            faultFlowDetailedModel = response.body();
                            assert faultFlowDetailedModel != null;
                            if (faultFlowDetailedModel.getOutput() != null) {
                                if (faultFlowDetailedModel.getOutput().getData() != null && !faultFlowDetailedModel.getOutput().getData().isEmpty()) {
                                    faultFlowDetailedAdapter = new FaultFlowDetailedAdapter(Reports.this, faultFlowDetailedModel.getOutput().getData(), Reports.this, reportName);
                                    binding.analysisReport.setAdapter(faultFlowDetailedAdapter);
                                    synchronized (Reports.this) {
                                        Reports.this.notify();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(Reports.this,e);
                            Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                        }
                    } else {
                        ErrorPdfLogger.logApiError(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + selectedReport
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                        snack.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FaultFlowDetailedModel> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(Reports.this, "POST", "/report/", t);
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            });
        }
    }
    private void getLoadAllocationDetailed(String selectedReport) {
        if (selectedReport.contains("Load Allocation Detail")) {
            downloadManager = new ExcelDownloadManager(prefManager);
            JsonObject jsonObject = new JsonObject();
            Intent intent = getIntent();
            JsonArray jsonArray = new Gson().toJsonTree(intent.getStringArrayListExtra("NetworkId")).getAsJsonArray();
            jsonObject.addProperty("Username", prefManager.getUserName());
            jsonObject.add("NetworkId", jsonArray);
            jsonObject.addProperty("ReportName", selectedReport);
            jsonObject.addProperty("Type", intent.getStringExtra("Type"));

            downloadManager.downloadExcel(jsonObject, new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    binding.shimmerView.stopShimmer();
                    binding.shimmerView.setVisibility(View.GONE);
                    binding.analysisReport.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        ErrorPdfLogger.logApiSuccess(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + selectedReport
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        try {
                            byte[] rawData = response.body().bytes();
                            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "AnalysisReport");
                            if (!dir.exists()) dir.mkdirs();

                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                            File pdfFile = new File(dir, "LoadAllocationDetailed" + timeStamp + ".pdf");

                            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(rawData);

                            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfFile));
                            com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
                            Document document = new Document(pdfDoc, com.itextpdf.kernel.geom.PageSize.A4.rotate());

                            Workbook workbook = WorkbookFactory.create(byteArrayInputStream);
                            Sheet sheet = workbook.getSheetAt(0);

                            int totalRows = sheet.getPhysicalNumberOfRows();
                            Row headerRow = null;
                            int headerRowIndex = 0;

                            for (int i = 0; i < totalRows; i++) {
                                Row row = sheet.getRow(i);
                                if (row == null) continue;

                                boolean isEmpty = true;
                                for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                                    if (row.getCell(j) != null && !row.getCell(j).toString().trim().isEmpty()) {
                                        isEmpty = false;
                                        break;
                                    }
                                }

                                if (!isEmpty) {
                                    headerRow = row;
                                    headerRowIndex = i;
                                    break;
                                }
                            }

                            if (headerRow == null) {
                                Snackbar.make(findViewById(android.R.id.content), "No data found!", Snackbar.LENGTH_LONG).show();
                                return;
                            }

                            List<Integer> validColumns = new ArrayList<>();
                            int maxCols = headerRow.getPhysicalNumberOfCells();
                            for (int col = 0; col < maxCols; col++) {
                                boolean hasData = false;
                                for (int rowIndex = headerRowIndex; rowIndex < totalRows; rowIndex++) {
                                    Row r = sheet.getRow(rowIndex);
                                    if (r != null && r.getCell(col) != null && !r.getCell(col).toString().trim().isEmpty()) {
                                        hasData = true;
                                        break;
                                    }
                                }
                                if (hasData) {
                                    validColumns.add(col);
                                }
                            }

                            float[] columnWidths = new float[validColumns.size()];
                            for (int i = headerRowIndex; i < totalRows; i++) {
                                Row row = sheet.getRow(i);
                                if (row == null) continue;
                                for (int colIndex = 0; colIndex < validColumns.size(); colIndex++) {
                                    int actualCol = validColumns.get(colIndex);
                                    if (row.getCell(actualCol) != null) {
                                        String cellData = row.getCell(actualCol).toString();
                                        int length = cellData.length();
                                        if (length > columnWidths[colIndex]) {
                                            columnWidths[colIndex] = length;
                                        }
                                    }
                                }
                            }
                            float totalWidth = 0;
                            for (float w : columnWidths) totalWidth += w;
                            float[] normalizedWidths = new float[columnWidths.length];
                            for (int i = 0; i < columnWidths.length; i++) {
                                normalizedWidths[i] = columnWidths[i] / totalWidth;
                            }

                            Table table = new Table(UnitValue.createPercentArray(normalizedWidths)).useAllAvailableWidth();

                            for (int colIndex : validColumns) {
                                String header = (headerRow.getCell(colIndex) != null) ? headerRow.getCell(colIndex).toString() : "";
                                Cell headerCell = new Cell()
                                        .add(new Paragraph(header).setFontSize(8).setMultipliedLeading(1.0f)).simulateBold()
                                        .setTextAlignment(TextAlignment.CENTER);
                                table.addHeaderCell(headerCell);
                            }

                            for (int i = headerRowIndex + 1; i < totalRows; i++) {
                                Row row = sheet.getRow(i);
                                if (row == null) continue;

                                boolean isEmptyRow = true;
                                for (int colIndex : validColumns) {
                                    if (row.getCell(colIndex) != null && !row.getCell(colIndex).toString().trim().isEmpty()) {
                                        isEmptyRow = false;
                                        break;
                                    }
                                }
                                if (isEmptyRow) continue;

                                boolean isHighlightRow = (i == 4 || i == 5 || i == 12 || i == 13);

                                for (int colIndex : validColumns) {
                                    String cellData = "";
                                    if (row.getCell(colIndex) != null) {
                                        if (row.getCell(colIndex).getCellType() == CellType.NUMERIC) {
                                            double numValue = row.getCell(colIndex).getNumericCellValue();
                                            cellData = String.format("%.2f", numValue);
                                        } else {
                                            cellData = row.getCell(colIndex).toString();
                                        }
                                    }

                                    Cell cell = new Cell()
                                            .add(new Paragraph(cellData).setFontSize(7).setMultipliedLeading(1.0f))
                                            .setTextAlignment(TextAlignment.LEFT);
                                    if (colIndex == validColumns.get(0)) {
                                        cell.simulateBold();
                                        cell.setFontColor(ColorConstants.BLACK);
                                    }
                                    if (isHighlightRow) {
                                        cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                                    }
                                    table.addCell(cell);
                                }
                            }

                            document.add(table);
                            document.close();
                            workbook.close();

                            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Download successful!", Snackbar.LENGTH_LONG);
//                            snack.setAction("Locate", v -> openFolder());
                            snack.setAction("Locate", v -> {
                                Intent intent = new Intent(Reports.this, PdfViewerActivity.class);
                                startActivity(intent);
                            });
                            snack.show();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Snackbar.make(findViewById(android.R.id.content), "PDF conversion failed!", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        ErrorPdfLogger.logApiError(Reports.this, "POST", "/report/", response.message()
                                + "UserName : " + prefManager.getUserName()
                                + "NetworkId : " + jsonArray
                                + "ReportName : " + selectedReport
                                + "Type : " + intent.getStringExtra("Type")
                                + "AccessToken : " + prefManager.getAccessToken()
                        );
                        System.out.println("Failed response: " + response.code());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(Reports.this, "POST", "/report/", t);
                    System.out.println("API call failed: " + t.getMessage());
                }
            });
        }
    }

    @Override
    public void onXYCordinateSend(String deviceNo, String deviceType) {
        if (deviceNo != null && deviceType != null) {
            onDataReceived(deviceNo, deviceType);
        }
    }

    @Override
    public void onDataReceived(String deviceNo, String deviceType) {
        Intent intent = new Intent();
        intent.putExtra("DeviceNo", deviceNo);
        intent.putExtra("DeviceType", deviceType);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void AbnormalFilters(String text) {
        List<AbnormalReport.Output.Datum> filterdList = new ArrayList<>();
        for (AbnormalReport.Output.Datum item : abnormalReport.getOutput().getData()) {
            if (item.getSectionId().toLowerCase().contains(text.toLowerCase())) {
                filterdList.add(item);
            }
        }
        abnormalConditionAdapter.filterList(filterdList);
    }

    private void DetailedFilters(String text) {
        List<DetailedReport.Output.Datum> filterdList = new ArrayList<>();
        for (DetailedReport.Output.Datum item : detailedReport.getOutput().getData()) {
            if (item.getSectionId().toLowerCase().contains(text.toLowerCase())) {
                filterdList.add(item);
            }
        }
        detailedReportAdapter.filterList(filterdList);
    }

    private void OverloadedConductorsFilters(String text) {
        List<OverLoadConductorReport.Output.Datum> filterdList = new ArrayList<>();
        for (OverLoadConductorReport.Output.Datum item : overLoadConductorReport.getOutput().getData()) {
            if (item.getOlcProDevID().toLowerCase().contains(text.toLowerCase())) {
                filterdList.add(item);
            }
        }
        constructorAdapter.filterList(filterdList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_DIRECTORY && resultCode == RESULT_OK) {
            if (data != null) {
                Uri treeUri = data.getData();
                final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                assert treeUri != null;
                getContentResolver().takePersistableUriPermission(treeUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                openAnalysisReportFolder(treeUri);
            }
        }
    }

    private void openAnalysisReportFolder(Uri treeUri) {
        Uri analysisReportUri = Uri.withAppendedPath(treeUri, "AnalysisReport");
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, analysisReportUri);
        startActivityForResult(intent, REQUEST_CODE_OPEN_DIRECTORY);
    }

    private void OverloadedLinesAndCablesFilters(String text) {
        List<OverLoadLineAndCablesReport.Output.Datum> filterdList = new ArrayList<>();
        for (OverLoadLineAndCablesReport.Output.Datum item : overLoadLineAndCablesReport.getOutput().getData()) {
            if (item.getEqNo().toLowerCase().contains(text.toLowerCase())) {
                filterdList.add(item);
            }
        }
        overLoadedLinesAndCablesAdapter.filterList(filterdList);
    }

    private void overloadedTransformersFilters(String text) {
        try {
            List<OverLoadTransformerReport.Output.Datum> filterdList = new ArrayList<>();
            for (OverLoadTransformerReport.Output.Datum item : overLoadTransformerReport.getOutput().getData()) {
                if (item.getEqNo().toLowerCase().contains(text.toLowerCase())) {
                    filterdList.add(item);
                }
            }
            overLoadTransformerAdapter.filterList(filterdList);
        } catch (Exception e) {
            Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
        }
    }

    private void TransformersFilters(String text) {
        List<TransformerReport.Output.Datum> filterdList = new ArrayList<>();
        for (TransformerReport.Output.Datum item : transformerReport.getOutput().getData()) {
            if (item.getEqNo().toLowerCase().contains(text.toLowerCase())) {
                filterdList.add(item);
            }
        }
        transformerReportAdapter.filterList(filterdList);
    }

    private void shortCircuitFilters(String text) {
        List<ShortCircuitDetailedModel.Output.Datum> filterdCircuitList = new ArrayList<>();
        for (ShortCircuitDetailedModel.Output.Datum item : shortCircuitDetailedModel.getOutput().getData()) {
            if (item.getNodeId().toLowerCase().contains(text.toLowerCase())) {
                filterdCircuitList.add(item);
            }
        }
        shortCircuitDetailedAdapter.filterList(filterdCircuitList);
    }

    private void faultFlowFilters(String text) {
        List<FaultFlowDetailedModel.DataItem> filterdFaultFlowList = new ArrayList<>();
        for (FaultFlowDetailedModel.DataItem item : faultFlowDetailedModel.getOutput().getData()) {
            if (item.getSectionId().toLowerCase().contains(text.toLowerCase())) {
                filterdFaultFlowList.add(item);
            }
        }
        faultFlowDetailedAdapter.filterList(filterdFaultFlowList);
    }

    private void openFolder() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_CODE_OPEN_DIRECTORY);
    }

}