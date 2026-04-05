package com.techlabs.apdcl.view.newConnection;

import static com.google.firebase.remoteconfig.FirebaseRemoteConfig.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.DataBase.Room.AppDatabase;
import com.techlabs.apdcl.DataBase.Room.CustomerData;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.ListDataManager;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.databinding.AddCustomerLayoutBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

public class AddCustomerInfoDialog extends Dialog {

    private final String phaseValue;
    private final String phaseType;
    private final String applicationID;
    private final List<View> viewList = new ArrayList<>();
    private final Context mainContext;
    private AddCustomerLayoutBinding binding;
    private LayoutInflater layoutInflater;
    private int a = 1;
    private PrefManager prefManager;
    private AppDatabase appDatabase;

    public AddCustomerInfoDialog(@NonNull Context context, String phaseType, String phase, String applicationID) {
        super(context);
        this.mainContext = context;
        this.phaseType = phaseType;
        this.phaseValue = phase;
        this.applicationID = applicationID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddCustomerLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        layoutInflater = getLayoutInflater();
        prefManager = new PrefManager(mainContext);
        appDatabase = AppDatabase.getInstance(mainContext);

        @SuppressLint("InflateParams")
        View child = layoutInflater.inflate(R.layout.add_header_title_layout, null);
        binding.dynamicLayout.addView(child);

        final int random = new Random().nextInt(61) + 20;
        ListDataManager.clearData();
        ListDataManager.clearDefaultData();

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                final List<CustomerData> list = appDatabase.customerDataDao().getUniqueCustomers();
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (binding == null) {
                        return;
                    }
                    if (list.isEmpty()) {
                        AddDefaultCustomer(random);
                    } else {
                        updateUi(list);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Database Error for applicationID: " + applicationID, e);
            }
        });

        binding.okbtn.setOnClickListener(v -> Executors.newSingleThreadExecutor().execute(() -> {
            appDatabase.customerDataDao().deleteAll();
            new Handler(Looper.getMainLooper()).post(this::checkDetails);
        }));

        binding.add.setOnClickListener(v -> addDynamicViews(random, a++));
        binding.remove.setOnClickListener(v -> removeDynamicViews());
        binding.canclebtn.setOnClickListener(v -> dismiss());
    }

    private void AddDefaultCustomer(int random) {
        View rowView;
        if ("ThreePhase".equalsIgnoreCase(phaseType)) {
            rowView = layoutInflater.inflate(R.layout.three_phase_layout, null);
        } else if ("1".equals(phaseValue)) {
            rowView = layoutInflater.inflate(R.layout.by_phase_a_layout, null);
        } else if ("2".equals(phaseValue)) {
            rowView = layoutInflater.inflate(R.layout.by_phase_b_layout, null);
        } else if ("3".equals(phaseValue)) {
            rowView = layoutInflater.inflate(R.layout.by_phase_c_layout, null);
        } else if ("4".equals(phaseValue)) {
            rowView = layoutInflater.inflate(R.layout.by_phase_ab_layout, null);
        } else if ("5".equals(phaseValue)) {
            rowView = layoutInflater.inflate(R.layout.by_phase_ac_layout, null);
        } else if ("6".equals(phaseValue)) {
            rowView = layoutInflater.inflate(R.layout.by_phase_bc_layout, null);
        } else {
            rowView = layoutInflater.inflate(R.layout.by_phase_abc_layout, null);
        }

        binding.dynamicLayout.addView(rowView);
        viewList.add(rowView);

        EditText customerNumber = rowView.findViewById(R.id.customerNumber);
        customerNumber.setText(random + "-" + a);

        Spinner spinner = rowView.findViewById(R.id.cust_type_spinnerBar);
        if (prefManager.getConsumerClasses() != null && !prefManager.getConsumerClasses().isEmpty()) {
            ArrayAdapter<String> locationAdapters = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, prefManager.getConsumerClasses());
            spinner.setAdapter(locationAdapters);
        }
    }

    private void updateUi(List<CustomerData> list) {
        for (int i = 0; i < list.size(); i++) {
            CustomerData customerData = list.get(i);
            View rowView;

            if ("ThreePhase".equalsIgnoreCase(customerData.getPhaseType())) {
                rowView = layoutInflater.inflate(R.layout.three_phase_layout, null);
            } else if ("1".equals(phaseValue)) {
                rowView = layoutInflater.inflate(R.layout.by_phase_a_layout, null);
            } else if ("2".equals(phaseValue)) {
                rowView = layoutInflater.inflate(R.layout.by_phase_b_layout, null);
            } else if ("3".equals(phaseValue)) {
                rowView = layoutInflater.inflate(R.layout.by_phase_c_layout, null);
            } else if ("4".equals(phaseValue)) {
                rowView = layoutInflater.inflate(R.layout.by_phase_ab_layout, null);
            } else if ("5".equals(phaseValue)) {
                rowView = layoutInflater.inflate(R.layout.by_phase_ac_layout, null);
            } else if ("6".equals(phaseValue)) {
                rowView = layoutInflater.inflate(R.layout.by_phase_bc_layout, null);
            } else {
                rowView = layoutInflater.inflate(R.layout.by_phase_abc_layout, null);
            }

            binding.dynamicLayout.addView(rowView);
            viewList.add(rowView);

            EditText customerNumber = rowView.findViewById(R.id.customerNumber);
            customerNumber.setText(customerData.getCustomerNumber());

            Spinner spinner = rowView.findViewById(R.id.cust_type_spinnerBar);
            if (prefManager.getConsumerClasses() != null && !prefManager.getConsumerClasses().isEmpty()) {
                ArrayAdapter<String> locationAdapters = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, prefManager.getConsumerClasses());
                spinner.setAdapter(locationAdapters);
                if (customerData.getCustomerType() != null && !customerData.getCustomerType().isEmpty()) {
                    int spinnerPosition = locationAdapters.getPosition(customerData.getCustomerType());
                    if (spinnerPosition != -1) {
                        spinner.setSelection(spinnerPosition);
                    }
                }
            }

            Spinner statusSpinner = rowView.findViewById(R.id.statusSpinnerBar);
            String targetStatus = customerData.getStatus() == 0 ? "Connected" : "DisConnected";
            for (int j = 0; j < statusSpinner.getCount(); j++) {
                if (statusSpinner.getItemAtPosition(j).toString().equalsIgnoreCase(targetStatus)) {
                    statusSpinner.setSelection(j);
                    break;
                }
            }

            JsonObject kwObject = customerData.getActualKWObject();
            JsonObject pfObject = customerData.getActualPfObject();
            JsonObject kVaObject = customerData.getConnKVAObject();
            JsonObject custObject = customerData.getCustNoObject();

            if ("ThreePhase".equalsIgnoreCase(customerData.getPhaseType())) {
                ((TextView) rowView.findViewById(R.id.Phase)).setText("ABC");
                ((EditText) rowView.findViewById(R.id.actualKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kwObject, "7")));
                ((EditText) rowView.findViewById(R.id.actualPf)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(pfObject, "7")));
                ((EditText) rowView.findViewById(R.id.connectedKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kVaObject, "7")));
                ((EditText) rowView.findViewById(R.id.custCount)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(custObject, "7")));
            } else if ("1".equals(phaseValue)) {
                ((TextView) rowView.findViewById(R.id.Phase)).setText("A");
                ((EditText) rowView.findViewById(R.id.actualAKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kwObject, "1")));
                ((EditText) rowView.findViewById(R.id.actualAPf)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(pfObject, "1")));
                ((EditText) rowView.findViewById(R.id.connectedAKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kVaObject, "1")));
                ((EditText) rowView.findViewById(R.id.customerACount)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(custObject, "1")));
            } else if ("2".equals(phaseValue)) {
                ((TextView) rowView.findViewById(R.id.bPhase)).setText("B");
                ((EditText) rowView.findViewById(R.id.actualBKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kwObject, "2")));
                ((EditText) rowView.findViewById(R.id.actualBPf)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(pfObject, "2")));
                ((EditText) rowView.findViewById(R.id.connectedBKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kVaObject, "2")));
                ((EditText) rowView.findViewById(R.id.customerBCount)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(custObject, "2")));
            } else if ("3".equals(phaseValue)) {
                ((TextView) rowView.findViewById(R.id.cPhase)).setText("C");
                ((EditText) rowView.findViewById(R.id.actualCKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kwObject, "3")));
                ((EditText) rowView.findViewById(R.id.actualCPf)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(pfObject, "3")));
                ((EditText) rowView.findViewById(R.id.connectedCKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kVaObject, "3")));
                ((EditText) rowView.findViewById(R.id.customerCCount)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(custObject, "3")));
            } else if ("4".equals(phaseValue)) {
                ((TextView) rowView.findViewById(R.id.aPhase)).setText("A");
                ((TextView) rowView.findViewById(R.id.bPhase)).setText("B");
                ((EditText) rowView.findViewById(R.id.actualAKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kwObject, "1")));
                ((EditText) rowView.findViewById(R.id.actualBKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kwObject, "2")));
                ((EditText) rowView.findViewById(R.id.actualAPf)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(pfObject, "1")));
                ((EditText) rowView.findViewById(R.id.actualBPf)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(pfObject, "2")));
                ((EditText) rowView.findViewById(R.id.connectedAKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kVaObject, "1")));
                ((EditText) rowView.findViewById(R.id.connectedBKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kVaObject, "2")));
                ((EditText) rowView.findViewById(R.id.customerACount)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(custObject, "1")));
                ((EditText) rowView.findViewById(R.id.customerBCount)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(custObject, "2")));
            } else if ("5".equals(phaseValue)) {
                ((TextView) rowView.findViewById(R.id.aPhase)).setText("A");
                ((TextView) rowView.findViewById(R.id.bPhase)).setText("C");
                ((EditText) rowView.findViewById(R.id.actualAKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kwObject, "1")));
                ((EditText) rowView.findViewById(R.id.actualCKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kwObject, "3")));
                ((EditText) rowView.findViewById(R.id.actualAPf)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(pfObject, "1")));
                ((EditText) rowView.findViewById(R.id.actualCPf)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(pfObject, "3")));
                ((EditText) rowView.findViewById(R.id.connectedAKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kVaObject, "1")));
                ((EditText) rowView.findViewById(R.id.connectedCKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kVaObject, "3")));
                ((EditText) rowView.findViewById(R.id.customerACount)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(custObject, "1")));
                ((EditText) rowView.findViewById(R.id.customerCCount)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(custObject, "3")));
            } else if ("6".equals(phaseValue)) {
                ((TextView) rowView.findViewById(R.id.bPhase)).setText("B");
                ((TextView) rowView.findViewById(R.id.cPhase)).setText("C");
                ((EditText) rowView.findViewById(R.id.actualBKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kwObject, "2")));
                ((EditText) rowView.findViewById(R.id.actualCKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kwObject, "3")));
                ((EditText) rowView.findViewById(R.id.actualBPf)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(pfObject, "2")));
                ((EditText) rowView.findViewById(R.id.actualCPf)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(pfObject, "3")));
                ((EditText) rowView.findViewById(R.id.connectedBKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kVaObject, "2")));
                ((EditText) rowView.findViewById(R.id.connectedCKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kVaObject, "3")));
                ((EditText) rowView.findViewById(R.id.customerBCount)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(custObject, "2")));
                ((EditText) rowView.findViewById(R.id.customerCCount)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(custObject, "3")));
            } else {
                ((TextView) rowView.findViewById(R.id.aPhase)).setText("A");
                ((TextView) rowView.findViewById(R.id.bPhase)).setText("B");
                ((TextView) rowView.findViewById(R.id.cPhase)).setText("C");
                ((EditText) rowView.findViewById(R.id.actualAKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kwObject, "1")));
                ((EditText) rowView.findViewById(R.id.actualBKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kwObject, "2")));
                ((EditText) rowView.findViewById(R.id.actualCKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kwObject, "3")));
                ((EditText) rowView.findViewById(R.id.actualAPf)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(pfObject, "1")));
                ((EditText) rowView.findViewById(R.id.actualBPf)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(pfObject, "2")));
                ((EditText) rowView.findViewById(R.id.actualCPf)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(pfObject, "3")));
                ((EditText) rowView.findViewById(R.id.connectedAKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kVaObject, "1")));
                ((EditText) rowView.findViewById(R.id.connectedBKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kVaObject, "2")));
                ((EditText) rowView.findViewById(R.id.connectedCKva)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(kVaObject, "3")));
                ((EditText) rowView.findViewById(R.id.customerACount)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(custObject, "1")));
                ((EditText) rowView.findViewById(R.id.customerBCount)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(custObject, "2")));
                ((EditText) rowView.findViewById(R.id.customerCCount)).setText(String.valueOf(ResponseDataUtils.getSafeDouble(custObject, "3")));
            }
        }
    }

    private void checkDetails() {
        try {
            List<CustomerData> customerList = new ArrayList<>();

            for (int i = 0; i < viewList.size(); i++) {
                View rowView = viewList.get(i);
                JsonObject jsonObject = new JsonObject();

                EditText customerNumberEdt = rowView.findViewById(R.id.customerNumber);
                Spinner customerTypeSpinner = rowView.findViewById(R.id.cust_type_spinnerBar);
                Spinner statusSpinner = rowView.findViewById(R.id.statusSpinnerBar);

                String customerNumber = customerNumberEdt.getText().toString().trim();
                String customerType = customerTypeSpinner.getSelectedItem().toString().trim();
                int status = statusSpinner.getSelectedItem().equals("Connected") ? 0 : 1;

                jsonObject.addProperty("CustomerNumber", customerNumber);
                jsonObject.addProperty("CustomerType", customerType);
                jsonObject.addProperty("Status", String.valueOf(status));

                JsonObject phaseObject = new JsonObject();
                JsonObject actualKwObject = new JsonObject();
                JsonObject actualPfObject = new JsonObject();
                JsonObject connectedKvaObject = new JsonObject();
                JsonObject customerCountObject = new JsonObject();

                if ("ThreePhase".equalsIgnoreCase(phaseType)) {
                    phaseObject.addProperty("1", "0");
                    phaseObject.addProperty("2", "0");
                    phaseObject.addProperty("3", "0");
                    phaseObject.addProperty("7", "7");

                    actualKwObject.addProperty("1", "0");
                    actualKwObject.addProperty("2", "0");
                    actualKwObject.addProperty("3", "0");
                    actualKwObject.addProperty("7", ((EditText) rowView.findViewById(R.id.actualKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualKva)).getText().toString().trim());

                    actualPfObject.addProperty("1", "0");
                    actualPfObject.addProperty("2", "0");
                    actualPfObject.addProperty("3", "0");
                    actualPfObject.addProperty("7", ((EditText) rowView.findViewById(R.id.actualPf)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualPf)).getText().toString().trim());

                    connectedKvaObject.addProperty("1", "0");
                    connectedKvaObject.addProperty("2", "0");
                    connectedKvaObject.addProperty("3", "0");
                    connectedKvaObject.addProperty("7", ((EditText) rowView.findViewById(R.id.connectedKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.connectedKva)).getText().toString().trim());

                    customerCountObject.addProperty("1", "0");
                    customerCountObject.addProperty("2", "0");
                    customerCountObject.addProperty("3", "0");
                    customerCountObject.addProperty("7", ((EditText) rowView.findViewById(R.id.custCount)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.custCount)).getText().toString().trim());
                } else if ("1".equals(phaseValue)) {
                    phaseObject.addProperty("1", "1");
                    phaseObject.addProperty("2", "0");
                    phaseObject.addProperty("3", "0");
                    phaseObject.addProperty("7", "0");

                    actualKwObject.addProperty("1", ((EditText) rowView.findViewById(R.id.actualAKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualAKva)).getText().toString().trim());
                    actualKwObject.addProperty("2", "0");
                    actualKwObject.addProperty("3", "0");
                    actualKwObject.addProperty("7", "0");

                    actualPfObject.addProperty("1", ((EditText) rowView.findViewById(R.id.actualAPf)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualAPf)).getText().toString().trim());
                    actualPfObject.addProperty("2", "0");
                    actualPfObject.addProperty("3", "0");
                    actualPfObject.addProperty("7", "0");

                    connectedKvaObject.addProperty("1", ((EditText) rowView.findViewById(R.id.connectedAKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.connectedAKva)).getText().toString().trim());
                    connectedKvaObject.addProperty("2", "0");
                    connectedKvaObject.addProperty("3", "0");
                    connectedKvaObject.addProperty("7", "0");

                    customerCountObject.addProperty("1", ((EditText) rowView.findViewById(R.id.customerACount)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.customerACount)).getText().toString().trim());
                    customerCountObject.addProperty("2", "0");
                    customerCountObject.addProperty("3", "0");
                    customerCountObject.addProperty("7", "0");
                } else if ("2".equals(phaseValue)) {
                    phaseObject.addProperty("1", "0");
                    phaseObject.addProperty("2", "1");
                    phaseObject.addProperty("3", "0");
                    phaseObject.addProperty("7", "0");

                    actualKwObject.addProperty("1", "0");
                    actualKwObject.addProperty("2", ((EditText) rowView.findViewById(R.id.actualBKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualBKva)).getText().toString().trim());
                    actualKwObject.addProperty("3", "0");
                    actualKwObject.addProperty("7", "0");

                    actualPfObject.addProperty("1", "0");
                    actualPfObject.addProperty("2", ((EditText) rowView.findViewById(R.id.actualBPf)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualBPf)).getText().toString().trim());
                    actualPfObject.addProperty("3", "0");
                    actualPfObject.addProperty("7", "0");

                    connectedKvaObject.addProperty("1", "0");
                    connectedKvaObject.addProperty("2", ((EditText) rowView.findViewById(R.id.connectedBKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.connectedBKva)).getText().toString().trim());
                    connectedKvaObject.addProperty("3", "0");
                    connectedKvaObject.addProperty("7", "0");

                    customerCountObject.addProperty("1", "0");
                    customerCountObject.addProperty("2", ((EditText) rowView.findViewById(R.id.customerBCount)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.customerBCount)).getText().toString().trim());
                    customerCountObject.addProperty("3", "0");
                    customerCountObject.addProperty("7", "0");
                } else if ("3".equals(phaseValue)) {
                    phaseObject.addProperty("1", "0");
                    phaseObject.addProperty("2", "0");
                    phaseObject.addProperty("3", "1");
                    phaseObject.addProperty("7", "0");

                    actualKwObject.addProperty("1", "0");
                    actualKwObject.addProperty("2", "0");
                    actualKwObject.addProperty("3", ((EditText) rowView.findViewById(R.id.actualCKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualCKva)).getText().toString().trim());
                    actualKwObject.addProperty("7", "0");

                    actualPfObject.addProperty("1", "0");
                    actualPfObject.addProperty("2", "0");
                    actualPfObject.addProperty("3", ((EditText) rowView.findViewById(R.id.actualCPf)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualCPf)).getText().toString().trim());
                    actualPfObject.addProperty("7", "0");

                    connectedKvaObject.addProperty("1", "0");
                    connectedKvaObject.addProperty("2", "0");
                    connectedKvaObject.addProperty("3", ((EditText) rowView.findViewById(R.id.connectedCKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.connectedCKva)).getText().toString().trim());
                    connectedKvaObject.addProperty("7", "0");

                    customerCountObject.addProperty("1", "0");
                    customerCountObject.addProperty("2", "0");
                    customerCountObject.addProperty("3", ((EditText) rowView.findViewById(R.id.customerCCount)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.customerCCount)).getText().toString().trim());
                    customerCountObject.addProperty("7", "0");
                } else if ("4".equals(phaseValue)) {
                    phaseObject.addProperty("1", "1");
                    phaseObject.addProperty("2", "1");
                    phaseObject.addProperty("3", "0");
                    phaseObject.addProperty("7", "0");

                    actualKwObject.addProperty("1", ((EditText) rowView.findViewById(R.id.actualAKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualAKva)).getText().toString().trim());
                    actualKwObject.addProperty("2", ((EditText) rowView.findViewById(R.id.actualBKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualBKva)).getText().toString().trim());
                    actualKwObject.addProperty("3", "0");
                    actualKwObject.addProperty("7", "0");

                    actualPfObject.addProperty("1", ((EditText) rowView.findViewById(R.id.actualAPf)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualAPf)).getText().toString().trim());
                    actualPfObject.addProperty("2", ((EditText) rowView.findViewById(R.id.actualBPf)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualBPf)).getText().toString().trim());
                    actualPfObject.addProperty("3", "0");
                    actualPfObject.addProperty("7", "0");

                    connectedKvaObject.addProperty("1", ((EditText) rowView.findViewById(R.id.connectedAKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.connectedAKva)).getText().toString().trim());
                    connectedKvaObject.addProperty("2", ((EditText) rowView.findViewById(R.id.connectedBKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.connectedBKva)).getText().toString().trim());
                    connectedKvaObject.addProperty("3", "0");
                    connectedKvaObject.addProperty("7", "0");

                    customerCountObject.addProperty("1", ((EditText) rowView.findViewById(R.id.customerACount)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.customerACount)).getText().toString().trim());
                    customerCountObject.addProperty("2", ((EditText) rowView.findViewById(R.id.customerBCount)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.customerBCount)).getText().toString().trim());
                    customerCountObject.addProperty("3", "0");
                    customerCountObject.addProperty("7", "0");
                } else if ("5".equals(phaseValue)) {
                    phaseObject.addProperty("1", "1");
                    phaseObject.addProperty("2", "0");
                    phaseObject.addProperty("3", "1");
                    phaseObject.addProperty("7", "0");

                    actualKwObject.addProperty("1", ((EditText) rowView.findViewById(R.id.actualAKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualAKva)).getText().toString().trim());
                    actualKwObject.addProperty("2", "0");
                    actualKwObject.addProperty("3", ((EditText) rowView.findViewById(R.id.actualCKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualCKva)).getText().toString().trim());
                    actualKwObject.addProperty("7", "0");

                    actualPfObject.addProperty("1", ((EditText) rowView.findViewById(R.id.actualAPf)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualAPf)).getText().toString().trim());
                    actualPfObject.addProperty("2", "0");
                    actualPfObject.addProperty("3", ((EditText) rowView.findViewById(R.id.actualCPf)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualCPf)).getText().toString().trim());
                    actualPfObject.addProperty("7", "0");

                    connectedKvaObject.addProperty("1", ((EditText) rowView.findViewById(R.id.connectedAKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.connectedAKva)).getText().toString().trim());
                    connectedKvaObject.addProperty("2", "0");
                    connectedKvaObject.addProperty("3", ((EditText) rowView.findViewById(R.id.connectedCKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.connectedCKva)).getText().toString().trim());
                    connectedKvaObject.addProperty("7", "0");

                    customerCountObject.addProperty("1", ((EditText) rowView.findViewById(R.id.customerACount)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.customerACount)).getText().toString().trim());
                    customerCountObject.addProperty("2", "0");
                    customerCountObject.addProperty("3", ((EditText) rowView.findViewById(R.id.customerCCount)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.customerCCount)).getText().toString().trim());
                    customerCountObject.addProperty("7", "0");
                } else if ("6".equals(phaseValue)) {
                    phaseObject.addProperty("1", "0");
                    phaseObject.addProperty("2", "1");
                    phaseObject.addProperty("3", "1");
                    phaseObject.addProperty("7", "0");

                    actualKwObject.addProperty("1", "0");
                    actualKwObject.addProperty("2", ((EditText) rowView.findViewById(R.id.actualBKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualBKva)).getText().toString().trim());
                    actualKwObject.addProperty("3", ((EditText) rowView.findViewById(R.id.actualCKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualCKva)).getText().toString().trim());
                    actualKwObject.addProperty("7", "0");

                    actualPfObject.addProperty("1", "0");
                    actualPfObject.addProperty("2", ((EditText) rowView.findViewById(R.id.actualBPf)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualBPf)).getText().toString().trim());
                    actualPfObject.addProperty("3", ((EditText) rowView.findViewById(R.id.actualCPf)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualCPf)).getText().toString().trim());
                    actualPfObject.addProperty("7", "0");

                    connectedKvaObject.addProperty("1", "0");
                    connectedKvaObject.addProperty("2", ((EditText) rowView.findViewById(R.id.connectedBKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.connectedBKva)).getText().toString().trim());
                    connectedKvaObject.addProperty("3", ((EditText) rowView.findViewById(R.id.connectedCKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.connectedCKva)).getText().toString().trim());
                    connectedKvaObject.addProperty("7", "0");

                    customerCountObject.addProperty("1", "0");
                    customerCountObject.addProperty("2", ((EditText) rowView.findViewById(R.id.customerBCount)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.customerBCount)).getText().toString().trim());
                    customerCountObject.addProperty("3", ((EditText) rowView.findViewById(R.id.customerCCount)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.customerCCount)).getText().toString().trim());
                    customerCountObject.addProperty("7", "0");
                } else {
                    phaseObject.addProperty("1", "1");
                    phaseObject.addProperty("2", "1");
                    phaseObject.addProperty("3", "1");
                    phaseObject.addProperty("7", "0");

                    actualKwObject.addProperty("1", ((EditText) rowView.findViewById(R.id.actualAKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualAKva)).getText().toString().trim());
                    actualKwObject.addProperty("2", ((EditText) rowView.findViewById(R.id.actualBKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualBKva)).getText().toString().trim());
                    actualKwObject.addProperty("3", ((EditText) rowView.findViewById(R.id.actualCKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualCKva)).getText().toString().trim());
                    actualKwObject.addProperty("7", "0");

                    actualPfObject.addProperty("1", ((EditText) rowView.findViewById(R.id.actualAPf)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualAPf)).getText().toString().trim());
                    actualPfObject.addProperty("2", ((EditText) rowView.findViewById(R.id.actualBPf)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualBPf)).getText().toString().trim());
                    actualPfObject.addProperty("3", ((EditText) rowView.findViewById(R.id.actualCPf)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.actualCPf)).getText().toString().trim());
                    actualPfObject.addProperty("7", "0");

                    connectedKvaObject.addProperty("1", ((EditText) rowView.findViewById(R.id.connectedAKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.connectedAKva)).getText().toString().trim());
                    connectedKvaObject.addProperty("2", ((EditText) rowView.findViewById(R.id.connectedBKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.connectedBKva)).getText().toString().trim());
                    connectedKvaObject.addProperty("3", ((EditText) rowView.findViewById(R.id.connectedCKva)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.connectedCKva)).getText().toString().trim());
                    connectedKvaObject.addProperty("7", "0");

                    customerCountObject.addProperty("1", ((EditText) rowView.findViewById(R.id.customerACount)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.customerACount)).getText().toString().trim());
                    customerCountObject.addProperty("2", ((EditText) rowView.findViewById(R.id.customerBCount)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.customerBCount)).getText().toString().trim());
                    customerCountObject.addProperty("3", ((EditText) rowView.findViewById(R.id.customerCCount)).getText().toString().trim().isEmpty() ? "0.0" : ((EditText) rowView.findViewById(R.id.customerCCount)).getText().toString().trim());
                    customerCountObject.addProperty("7", "0");
                }

                jsonObject.add("Phase", phaseObject);
                jsonObject.add("ActualKW", actualKwObject);
                jsonObject.add("ActualPF", actualPfObject);
                jsonObject.add("ConnectedKVA", connectedKvaObject);
                jsonObject.add("CustomerCount", customerCountObject);

                ListDataManager.sendData(jsonObject);
                customerList.add(new CustomerData(phaseType, Integer.parseInt(phaseValue), customerNumber, customerType, status, phaseObject, actualKwObject, actualPfObject, connectedKvaObject, customerCountObject));
            }

            if (!customerList.isEmpty()) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    try {
                        appDatabase.customerDataDao().insertAll(customerList);
                        dismiss();
                    } catch (Exception e) {
                        Log.e("DatabaseError", "Bulk insert failed", e);
                        dismiss();
                    }
                });
            } else {
                dismiss();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in checkDetails", e);
            dismiss();
        }
    }

    private void removeDynamicViews() {
        try {
            if (viewList.size() > 1) {
                View lastView = viewList.get(viewList.size() - 1);
                binding.dynamicLayout.removeView(lastView);
                viewList.remove(viewList.size() - 1);
            } else {
                Snackbar.make(binding.getRoot(), "The load must have at least one customer.", Snackbar.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error removing customer view", e);
        }
    }

    private void addDynamicViews(int random, int a) {
        try {
            View rowView;
            if ("ThreePhase".equalsIgnoreCase(phaseType)) {
                rowView = layoutInflater.inflate(R.layout.three_phase_layout, null);
            } else if ("1".equals(phaseValue)) {
                rowView = layoutInflater.inflate(R.layout.by_phase_a_layout, null);
            } else if ("2".equals(phaseValue)) {
                rowView = layoutInflater.inflate(R.layout.by_phase_b_layout, null);
            } else if ("3".equals(phaseValue)) {
                rowView = layoutInflater.inflate(R.layout.by_phase_c_layout, null);
            } else if ("4".equals(phaseValue)) {
                rowView = layoutInflater.inflate(R.layout.by_phase_ab_layout, null);
            } else if ("5".equals(phaseValue)) {
                rowView = layoutInflater.inflate(R.layout.by_phase_ac_layout, null);
            } else if ("6".equals(phaseValue)) {
                rowView = layoutInflater.inflate(R.layout.by_phase_bc_layout, null);
            } else {
                rowView = layoutInflater.inflate(R.layout.by_phase_abc_layout, null);
            }

            binding.dynamicLayout.addView(rowView);
            viewList.add(rowView);

            EditText customerNumber = rowView.findViewById(R.id.customerNumber);
            customerNumber.setText(random + "-" + ++a);

            Spinner spinner = rowView.findViewById(R.id.cust_type_spinnerBar);
            if (prefManager.getConsumerClasses() != null && !prefManager.getConsumerClasses().isEmpty()) {
                ArrayAdapter<String> locationAdapters = new ArrayAdapter<>(mainContext, R.layout.custom_spinner, prefManager.getConsumerClasses());
                spinner.setAdapter(locationAdapters);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error adding dynamic customer view", e);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        binding = null;
    }
}
