package com.techlabs.apdcl.adapters.report;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techlabs.apdcl.Utils.DeviceArgument;
import com.techlabs.apdcl.databinding.FaultFlowDetailedLayoutBinding;
import com.techlabs.apdcl.models.analysis.FaultFlowDetailedModel;

import java.util.List;

public class FaultFlowDetailedAdapter extends RecyclerView.Adapter<FaultFlowDetailedAdapter.ViewHolder> {

    private final Context mainContext;
    private List<FaultFlowDetailedModel.DataItem> data;
    private final DeviceArgument deviceArgument;
    private String reportName = "";

    public FaultFlowDetailedAdapter(Context mainContext, List<FaultFlowDetailedModel.DataItem> data, DeviceArgument deviceArgument, String reportName) {
        this.mainContext = mainContext;
        this.data = data;
        this.deviceArgument = deviceArgument;
        this.reportName = reportName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FaultFlowDetailedLayoutBinding binding = FaultFlowDetailedLayoutBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FaultFlowDetailedModel.DataItem item = data.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final FaultFlowDetailedLayoutBinding binding;

        public ViewHolder(@NonNull FaultFlowDetailedLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("DefaultLocale")
        public void bindView(FaultFlowDetailedModel.DataItem item) {

            //Content
            if (item.getFeederId() != null && !item.getFeederId().isEmpty()) {
                binding.cellContentLayout.feederID.setText(item.getFeederId());
            }
            if (item.getSectionId() != null && !item.getSectionId().isEmpty()) {
                binding.cellContentLayout.sectionID.setText(item.getSectionId());
            }
            if (item.getFaultedItem() != null && !item.getFaultedItem().isEmpty()) {
                binding.cellContentLayout.faultedId.setText(item.getFaultedItem());
            }
            if(item.getFaultPhase() != null && !item.getFaultPhase().isEmpty()){
                binding.cellContentLayout.phase.setText(item.getFaultPhase());
            }
            if(item.getFaultType() != null && !item.getFaultType().isEmpty()){
                binding.cellContentLayout.type.setText(item.getFaultType());
            }
            if(item.getEquipmentId() != null && !item.getEquipmentId().isEmpty()){
                binding.cellContentLayout.equipID.setText(item.getEquipmentId());
            }
            binding.cellContentLayout.loading.setText(String.format("%.2f", item.getLoadingAPercent()));
            binding.cellContentLayout.thruPow.setText(String.format("%.2f", item.getThruPowerAKw()));
            binding.cellContentLayout.powerKvar.setText(String.format("%.2f", item.getThruPowerAkvar()));
            binding.cellContentLayout.va.setText(String.format("%.2f", item.getVaPercent()));

            if(item.getCode() != null && !item.getCode().isEmpty()){
                binding.cellContentLayout.codeTvc.setText(item.getCode());
            }
            if(item.getEquipmentNo() != null && !item.getEquipmentNo().isEmpty()){
                binding.cellContentLayout.eqpNum.setText(item.getEquipmentNo());
            }

        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void filterList(List<FaultFlowDetailedModel.DataItem> list) {
        data = list;
        notifyDataSetChanged();
    }

}
