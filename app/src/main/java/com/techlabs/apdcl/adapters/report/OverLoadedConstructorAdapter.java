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
import com.techlabs.apdcl.databinding.OverloadedConstructorReportLayoutBinding;
import com.techlabs.apdcl.models.report.OverLoadConductorReport;

import java.util.List;

public class OverLoadedConstructorAdapter extends RecyclerView.Adapter<OverLoadedConstructorAdapter.ViewHolder> {

    private final Context mainContext;
    private final DeviceArgument deviceArgument;
    private List<OverLoadConductorReport.Output.Datum> data;
    private String reportName = "";

    public OverLoadedConstructorAdapter(Context mainContext, List<OverLoadConductorReport.Output.Datum> data, DeviceArgument deviceArgument, String reportName) {
        this.mainContext = mainContext;
        this.data = data;
        this.deviceArgument = deviceArgument;
        this.reportName = reportName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OverloadedConstructorReportLayoutBinding binding = OverloadedConstructorReportLayoutBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.isEmpty()) {
            holder.bindViews();
        } else {
            OverLoadConductorReport.Output.Datum item = data.get(position);
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
    public void filterList(List<OverLoadConductorReport.Output.Datum> list) {
        data = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        OverloadedConstructorReportLayoutBinding binding;

        public ViewHolder(@NonNull OverloadedConstructorReportLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(OverLoadConductorReport.Output.Datum item) {

            binding.contentLLayout.setVisibility(View.VISIBLE);
            binding.noDataTvLayout.setVisibility(View.GONE);

            if (item.getFeederId() != null && !item.getFeederId().isEmpty()) {
                binding.feederIdTv.setText(item.getFeederId());
                if (item.getFeederIdColor() != null && !item.getFeederIdColor().isEmpty() && !item.getFeederIdColor().equals("NULL")) {
                    binding.feederIdTv.setText(item.getFeederIdColor());
                }
            }

            if (item.getOlcProDevID() != null && !item.getOlcProDevID().isEmpty()) {
                binding.olcProDevIdTv.setText(item.getOlcProDevID());
                if (item.getOlcProDevIDColor() != null && !item.getOlcProDevIDColor().isEmpty() && !item.getOlcProDevIDColor().equals("NULL")) {
                    binding.olcProDevIdTv.setText(item.getOlcProDevIDColor());
                }
            }

            if (item.getOlcBegOvlCond() != null && !item.getOlcBegOvlCond().isEmpty()) {
                binding.olcBegOvlCondTv.setText(item.getOlcBegOvlCond());
                if (item.getOlcBegOvlCondColor() != null && !item.getOlcBegOvlCondColor().isEmpty() && !item.getOlcBegOvlCondColor().equals("NULL")) {
                    binding.olcBegOvlCondTv.setText(item.getOlcBegOvlCondColor());
                }
            }

            if (item.getOlcEndOvlCond() != null && !item.getOlcEndOvlCond().isEmpty()) {
                binding.olcEndOvlCondTv.setText(item.getOlcEndOvlCond());
                if (item.getOlcEndOvlCondColor() != null && !item.getOlcEndOvlCondColor().isEmpty() && !item.getOlcEndOvlCondColor().equals("NULL")) {
                    binding.olcEndOvlCondTv.setText(item.getOlcEndOvlCondColor());
                }
            }

            if (item.getOlcConductID() != null && !item.getOlcConductID().isEmpty()) {
                binding.olcConductIDTv.setText(item.getOlcConductID());
                if (item.getOlcConductIDColor() != null && !item.getOlcConductIDColor().isEmpty() && !item.getOlcConductIDColor().equals("NULL")) {
                    binding.olcConductIDTv.setText(item.getOlcConductIDColor());
                }
            }

            if (item.getOlcLoadCap() != null && !item.getOlcLoadCap().isEmpty()) {
                binding.olcLoadCapTv.setText(item.getOlcLoadCap());
                if (item.getOlcLoadCapColor() != null && !item.getOlcLoadCapColor().isEmpty() && !item.getOlcLoadCapColor().equals("NULL")) {
                    binding.olcLoadCapTv.setText(item.getOlcLoadCapColor());
                }
            }

            if (item.getOlcPercntOvl() != null && !item.getOlcPercntOvl().isEmpty()) {
                binding.olcPercentOvlTv.setText(item.getOlcPercntOvl());
                if (item.getOlcPercntOvlColor() != null && !item.getOlcPercntOvlColor().isEmpty() && !item.getOlcPercntOvlColor().equals("NULL")) {
                    binding.olcPercentOvlTv.setText(item.getOlcPercntOvlColor());
                }
            }

            binding.viewEquipmentBtn.setOnClickListener(view -> {
                if (deviceArgument != null) {
                    if (reportName.equals("Load Flow - Overloaded conductors")) {
                        deviceArgument.onXYCordinateSend(item.getOlcBegOvlCond(), "41");
                    }
                }
            });

        }

        public void bindViews() {
            binding.contentLLayout.setVisibility(View.GONE);
            binding.mainLayout.setBackgroundColor(Color.TRANSPARENT);
            binding.noDataTvLayout.setVisibility(View.VISIBLE);
        }
    }

}
