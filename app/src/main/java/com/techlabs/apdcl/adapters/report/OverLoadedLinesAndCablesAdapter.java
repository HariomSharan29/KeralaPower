package com.techlabs.apdcl.adapters.report;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techlabs.apdcl.Utils.DeviceArgument;
import com.techlabs.apdcl.databinding.OverloadedLinesCablesLayoutBinding;
import com.techlabs.apdcl.models.report.OverLoadLineAndCablesReport;

import java.util.List;

public class OverLoadedLinesAndCablesAdapter extends RecyclerView.Adapter<OverLoadedLinesAndCablesAdapter.ViewHolder> {

    private final Context mainContext;
    private final DeviceArgument deviceArgument;
    private List<OverLoadLineAndCablesReport.Output.Datum> data;
    private String reportName = "";

    public OverLoadedLinesAndCablesAdapter(Context mainContext, List<OverLoadLineAndCablesReport.Output.Datum> data, DeviceArgument deviceArgument, String reportName) {
        this.mainContext = mainContext;
        this.data = data;
        this.deviceArgument = deviceArgument;
        this.reportName = reportName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OverloadedLinesCablesLayoutBinding binding = OverloadedLinesCablesLayoutBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.isEmpty()) {
            holder.bindViews();
        } else {
            OverLoadLineAndCablesReport.Output.Datum item = data.get(position);
            holder.bindView(item);
        }
    }

    @Override
    public int getItemCount() {
        if (data.isEmpty()) {
            return 1;
        } else {
            return data.size();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(List<OverLoadLineAndCablesReport.Output.Datum> list) {
        data = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final OverloadedLinesCablesLayoutBinding binding;

        public ViewHolder(@NonNull OverloadedLinesCablesLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(OverLoadLineAndCablesReport.Output.Datum item) {

            binding.constraintLayout.setVisibility(View.VISIBLE);
            binding.noDataTvLayout.setVisibility(View.GONE);

            if (item.getEqNo() != null && !item.getEqNo().isEmpty()) {
                binding.equipIdNoTv.setText(item.getEqNo());
                if (item.getEqNoColor() != null && !item.getEqNoColor().isEmpty() && !item.getEqNoColor().equals("NULL")) {
                    binding.equipIdNoTv.setBackgroundColor(Color.parseColor(item.getEqNoColor()));
                }
            }

            if (item.getFromNodeId() != null && !item.getFromNodeId().isEmpty()) {
                binding.fromNodeIdTv.setText(item.getFromNodeId());
                if (item.getFromNodeIdColor() != null && !item.getFromNodeIdColor().isEmpty() && !item.getFromNodeIdColor().equals("NULL")) {
                    binding.fromNodeIdTv.setBackgroundColor(Color.parseColor(item.getFromNodeIdColor()));
                }
            }

            if (item.getToNodeId() != null && !item.getToNodeId().isEmpty()) {
                binding.toNodeIdTv.setText(item.getToNodeId());
                if (item.getToNodeIdColor() != null && !item.getToNodeIdColor().isEmpty() && !item.getToNodeIdColor().equals("NULL")) {
                    binding.toNodeIdTv.setBackgroundColor(Color.parseColor(item.getToNodeIdColor()));
                }
            }

            if (item.getIBal() != null && !item.getIBal().isEmpty()) {
                binding.iBalTv.setText(item.getIBal());
                if (item.getIBalColor() != null && !item.getIBalColor().isEmpty() && !item.getIBalColor().equals("NULL")) {
                    binding.iBalTv.setBackgroundColor(Color.parseColor(item.getIBalColor()));
                }
            }

            if (item.getIAngle() != null && !item.getIAngle().isEmpty()) {
                binding.iAngleTv.setText(item.getIAngle());
                if (item.getIAngleColor() != null && !item.getIAngleColor().isEmpty() && !item.getIAngleColor().equals("NULL")) {
                    binding.iAngleTv.setBackgroundColor(Color.parseColor(item.getIAngleColor()));
                }
            }

            if (item.getLoading() != null && !item.getLoading().isEmpty()) {
                binding.loadingGTv.setText(item.getLoading());
                if (item.getLOADINGColor() != null && !item.getLOADINGColor().isEmpty() && !item.getLOADINGColor().equals("NULL")) {
                    binding.loadingGTv.setBackgroundColor(Color.parseColor(item.getLOADINGColor()));
                }
            }

            binding.viewEquipmentBtn.setOnClickListener(view -> {
                if (deviceArgument != null) {
                    if (reportName.equals("Load Flow - Overloaded lines and cables")) {
                        deviceArgument.onXYCordinateSend(item.getEqNo(), "41");
                    }
                }
            });

        }

        public void bindViews() {
            binding.mainLayout.setBackgroundColor(Color.TRANSPARENT);
            binding.constraintLayout.setVisibility(View.GONE);
            binding.noDataTvLayout.setVisibility(View.VISIBLE);
        }
    }

}
