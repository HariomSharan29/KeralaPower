package com.techlabs.apdcl.view.fragment;

import static com.techlabs.apdcl.Utils.Config.scDeviceNumber;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ShortCircuitArgument;
import com.techlabs.apdcl.adapters.NetworkIdAdapter;
import com.techlabs.apdcl.databinding.FragmentShortCircuitNetworksBinding;

import java.util.ArrayList;
import java.util.List;

public class ShortCircuitNetworks extends Fragment implements NetworkIdAdapter.OnNetworkSelectionChangedListener {

    private FragmentShortCircuitNetworksBinding binding;
    private PrefManager prefManager;
    private ShortCircuitArgument shortCircuitArgument;
    private NetworkIdAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            shortCircuitArgument = (ShortCircuitArgument) getActivity();
            binding = FragmentShortCircuitNetworksBinding.inflate(inflater, container, false);
            return binding.getRoot();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity() + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefManager = new PrefManager(getActivity());

        Bundle bundle = this.getArguments();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (bundle != null) {
            List<String> list = new ArrayList<String>((ArrayList<String>) bundle.get("Network"));
            adapter = new NetworkIdAdapter(list, this);
            binding.recyclerView.setAdapter(adapter);
        }

       /* binding.okBtn.setOnClickListener(v -> {
            SendShortCircuitArguments();
        });

        binding.cancelBtn.setOnClickListener(v -> {
            JsonObject jsonObject = new JsonObject();
            List<String> list = new ArrayList<>();
            jsonObject.addProperty("ShortCircuit", false);
            if (shortCircuitArgument != null) {
                shortCircuitArgument.onShortCircuitArgReceived(jsonObject, list);
            }
        });*/
        updateVisible();

    }

    private void updateVisible() {
        binding.alertCircuit.setVisibility(adapter.isAnyCheckboxChecked() ? View.GONE : View.VISIBLE);
        binding.alertCircuit.requestFocus();
    }

    public void SendShortCircuitArguments() {
        JsonObject jsonObject = new JsonObject();
        List<String> selectedNetwork = adapter.getSelectedNetworks();

        if (!selectedNetwork.isEmpty()) {
            jsonObject.addProperty("Username", prefManager.getUserName());
            jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            JsonArray jsonArray = new Gson().toJsonTree(selectedNetwork).getAsJsonArray();
            jsonObject.add("NetworkId", jsonArray);
            jsonObject.addProperty("Calculate", "SC");
            jsonObject.addProperty("LocationType", "10");

            if (scDeviceNumber != null) {
                jsonObject.addProperty("DeviceNumber", scDeviceNumber);
            }

            jsonObject.addProperty("FaultPosition", "50.0");
            jsonObject.addProperty("FaultType", "LLL");
            jsonObject.addProperty("FaultPhase", "A");

            jsonObject.addProperty("Method", "1");
            jsonObject.addProperty("NominalTap", "0");
            jsonObject.addProperty("AImpedence", "1");
            jsonObject.addProperty("SyncGenerator", "1");
            jsonObject.addProperty("WECS", "1");
            jsonObject.addProperty("Photovoltaic", "1");
            jsonObject.addProperty("LimitCategories", "1");
            jsonObject.addProperty("ConductorDB", "95.0");
            jsonObject.addProperty("CableDB", "95.0");
            jsonObject.addProperty("FuseDB", "95.0");
            jsonObject.addProperty("RecloserDB", "95.0");
            jsonObject.addProperty("LVCBDB", "95.0");
            jsonObject.addProperty("BreakerDB", "95.0");
            jsonObject.addProperty("SectionalizerDB", "95.0");
            jsonObject.addProperty("NetworkProtectorDB", "95.0");
            jsonObject.addProperty("ShuntCapacitorDB", "95.0");
            jsonObject.addProperty("overvoltage", "95.0");

            if (shortCircuitArgument != null) {
                shortCircuitArgument.onShortCircuitArgReceived(jsonObject, selectedNetwork);
            }
        }
    }

    public boolean isValid() {
        return adapter != null && !adapter.getSelectedNetworks().isEmpty();
    }

    @Override
    public void onSelectionChanged(boolean isAnySelected) {
        updateVisible();
    }
}