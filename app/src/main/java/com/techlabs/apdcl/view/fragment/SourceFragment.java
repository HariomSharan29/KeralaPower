package com.techlabs.apdcl.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.techlabs.apdcl.R;
import com.techlabs.apdcl.databinding.FragmentSourceBinding;


public class SourceFragment extends Fragment {
    private FragmentSourceBinding binding;
    private String[] zoneList = {"UNDEFINED"};
    private String[] SourceList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSourceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View MainLayoutBackGround = getActivity().getWindow().getDecorView().getRootView();
        MainLayoutBackGround.setBackground(getContext().getDrawable(R.drawable.pop_layout_background));
        Bundle bundle = this.getArguments();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner, zoneList);
        binding.zoneSpiner.setAdapter(adapter1);

        if (bundle.getString("NetworkId") != null) {
            SourceList = new String[]{bundle.getString("NetworkId")};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner, SourceList);
            binding.sourceTypeSpinner.setAdapter(adapter);
        }
        if (bundle.getString("NodeId") != null) {
            binding.nodeId.setText(bundle.getString("NodeId"));
        }
        if (bundle.getString("NodeIdX") != null) {
            binding.nodeIdX.setText(bundle.getString("NodeIdX"));
        }
        if (bundle.getString("NodeIdY") != null) {
            binding.nodeIdY.setText(bundle.getString("NodeIdY"));
        }
        if (bundle.getString("Latitude") != null) {
            binding.latitude.setText(bundle.getString("Latitude"));
        }
        if (bundle.getString("Longitude") != null) {
            binding.longitude.setText(bundle.getString("Longitude"));
        }
    }
}