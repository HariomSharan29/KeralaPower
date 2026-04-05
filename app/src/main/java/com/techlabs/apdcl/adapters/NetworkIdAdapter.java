package com.techlabs.apdcl.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techlabs.apdcl.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkIdAdapter extends RecyclerView.Adapter<NetworkIdAdapter.ViewHolder> {

    private List<String> list;
    private List<Boolean> checkedStates = new ArrayList<>();
    private final OnNetworkSelectionChangedListener selectionChangedListener;

    public NetworkIdAdapter(List<String> list,OnNetworkSelectionChangedListener selectionChangedListener) {
        this.list = list;
        this.selectionChangedListener = selectionChangedListener;
        this.checkedStates = listTrue(list.size());
    }

    @NonNull
    @Override
    public NetworkIdAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.network_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NetworkIdAdapter.ViewHolder holder, int position) {
        holder.NetworkId.setText(list.get(position));
        holder.NetworkId.setChecked(checkedStates.get(position));

        holder.NetworkId.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkedStates.set(position, isChecked);
            if (selectionChangedListener != null) {
                selectionChangedListener.onSelectionChanged(isAnyCheckboxChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public List<String> getSelectedNetworks() {
        List<String> selectedNetworks = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (checkedStates.get(i)) {
                selectedNetworks.add(list.get(i));
            }
        }
        return selectedNetworks;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox NetworkId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NetworkId = itemView.findViewById(R.id.networkID);
        }
    }
    public boolean isAnyCheckboxChecked() {
        for (boolean isChecked : checkedStates) {
            if (isChecked) {
                return true;
            }
        }
        return false;
    }
    private List<Boolean> listTrue(int size) {return new ArrayList<>(Collections.nCopies(size, true));}

    public interface OnNetworkSelectionChangedListener {void onSelectionChanged(boolean isCheckBoxSelected);}

}
