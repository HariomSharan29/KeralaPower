package com.techlabs.apdcl.adapters.report;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techlabs.apdcl.Utils.DeviceArgument;
import com.techlabs.apdcl.databinding.ShortCircuitDetailedLayoutBinding;
import com.techlabs.apdcl.models.analysis.ShortCircuitDetailedModel;

import java.util.List;

public class ShortCircuitDetailedAdapter extends RecyclerView.Adapter<ShortCircuitDetailedAdapter.ViewHolder> {

    private final Context mainContext;
    private final DeviceArgument deviceArgument;
    private List<ShortCircuitDetailedModel.Output.Datum> data;
    private String reportName = "";

    public ShortCircuitDetailedAdapter(Context mainContext, List<ShortCircuitDetailedModel.Output.Datum> data, DeviceArgument deviceArgument, String reportName) {
        this.mainContext = mainContext;
        this.data = data;
        this.deviceArgument = deviceArgument;
        this.reportName = reportName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ShortCircuitDetailedLayoutBinding binding = ShortCircuitDetailedLayoutBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShortCircuitDetailedModel.Output.Datum item = data.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(List<ShortCircuitDetailedModel.Output.Datum> list) {
        data = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ShortCircuitDetailedLayoutBinding binding;

        public ViewHolder(@NonNull ShortCircuitDetailedLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(ShortCircuitDetailedModel.Output.Datum item) {

            if (item.getFeederId() != null && !item.getFeederId().isEmpty()) {
                if (item.getFeederIdColor() != null && !item.getFeederIdColor().equals("NULL")) {
                    binding.shortCircuitTitleLayout.feederID.setBackgroundColor(Color.parseColor(item.getFeederIdColor()));
                    binding.shortCircuitTitleLayout.feederID.setText(item.getFeederId());
                } else {
                    binding.shortCircuitTitleLayout.feederID.setText(item.getFeederId());
                }
            }

            if (item.getNodeId() != null && !item.getNodeId().isEmpty()) {
                if (item.getNodeIdColor() != null && !item.getNodeIdColor().equals("NULL")) {
                    binding.shortCircuitTitleLayout.nodeID.setBackgroundColor(Color.parseColor(item.getNodeIdColor()));
                    binding.shortCircuitTitleLayout.nodeID.setText(item.getNodeId());
                } else {
                    binding.shortCircuitTitleLayout.nodeID.setText(item.getNodeId());
                }
            }

            if (item.getPhase() != null && !item.getPhase().isEmpty()) {
                if (item.getPhaseColor() != null && !item.getPhaseColor().equals("NULL")) {
                    binding.shortCircuitTitleLayout.phase.setBackgroundColor(Color.parseColor(item.getPhaseColor()));
                    binding.shortCircuitTitleLayout.phase.setText(item.getPhase());
                } else {
                    binding.shortCircuitTitleLayout.phase.setText(item.getPhase());
                }
            }

            if (item.getKvln() != null && !item.getKvln().isEmpty()) {
                if (item.getKVLNColor() != null && !item.getKVLNColor().equals("NULL")) {
                    binding.shortCircuitTitleLayout.kvln.setBackgroundColor(Color.parseColor(item.getKVLNColor()));
                    binding.shortCircuitTitleLayout.kvln.setText(item.getKvln());
                } else {
                    binding.shortCircuitTitleLayout.kvln.setText(item.getKvln());
                }
            }

            if (item.getLLLamp() != null && !item.getLLLamp().isEmpty()) {
                if (item.getLLLampColor() != null && !item.getLLLampColor().equals("NULL")) {
                    binding.shortCircuitTitleLayout.lllA.setBackgroundColor(Color.parseColor(item.getLLLampColor()));
                    binding.shortCircuitTitleLayout.lllA.setText(item.getLLLamp());
                } else {
                    binding.shortCircuitTitleLayout.lllA.setText(item.getLLLamp());
                }
            }

            if (item.getLLLampKmax() != null && !item.getLLLampKmax().isEmpty()) {
                if (item.getLLLampKmaxColor() != null && !item.getLLLampKmaxColor().equals("NULL")) {
                    binding.shortCircuitTitleLayout.lllKmaxA.setBackgroundColor(Color.parseColor(item.getLLLampKmaxColor()));
                    binding.shortCircuitTitleLayout.lllKmaxA.setText(item.getLLLampKmax());
                } else {
                    binding.shortCircuitTitleLayout.lllKmaxA.setText(item.getLLLampKmax());
                }
            }

            if (item.getLLLampKmaxZ() != null && !item.getLLLampKmaxZ().isEmpty()) {
                if (item.getLLLampKmaxZColor() != null && !item.getLLLampKmaxZColor().equals("NULL")) {
                    binding.shortCircuitTitleLayout.llKmaxZA.setBackgroundColor(Color.parseColor(item.getLLLampKmaxZColor()));
                    binding.shortCircuitTitleLayout.llKmaxZA.setText(item.getLLLampKmaxZ());
                } else {
                    binding.shortCircuitTitleLayout.llKmaxZA.setText(item.getLLLampKmaxZ());
                }
            }

            if (item.getLLLampKmin() != null && !item.getLLLampKmin().isEmpty()) {
                if (item.getLLLampKminColor() != null && !item.getLLLampKminColor().equals("NULL")) {
                    binding.shortCircuitTitleLayout.lllKMinA.setBackgroundColor(Color.parseColor(item.getLLLampKminColor()));
                    binding.shortCircuitTitleLayout.lllKMinA.setText(item.getLLLampKmin());
                } else {
                    binding.shortCircuitTitleLayout.lllKMinA.setText(item.getLLLampKmin());
                }
            }

            //Content
            if (item.getFeederId() != null && !item.getFeederId().isEmpty()) {
                if (item.getFeederIdColor() != null && !item.getFeederIdColor().equals("NULL")) {
                    binding.cellContentLayout.feederID.setBackgroundColor(Color.parseColor(item.getFeederIdColor()));
                    binding.cellContentLayout.feederID.setText(item.getFeederId());
                } else {
                    binding.cellContentLayout.feederID.setText(item.getFeederId());
                }
            }

            if (item.getNodeId() != null && !item.getNodeId().isEmpty()) {
                if (item.getNodeIdColor() != null && !item.getNodeIdColor().equals("NULL")) {
                    binding.cellContentLayout.nodeID.setBackgroundColor(Color.parseColor(item.getNodeIdColor()));
                    binding.cellContentLayout.nodeID.setText(item.getNodeId());
                } else {
                    binding.cellContentLayout.nodeID.setText(item.getNodeId());
                }
            }

            if (item.getPhase() != null && !item.getPhase().isEmpty()) {
                if (item.getPhaseColor() != null && !item.getPhaseColor().equals("NULL")) {
                    binding.cellContentLayout.phase.setBackgroundColor(Color.parseColor(item.getPhaseColor()));
                    binding.cellContentLayout.phase.setText(item.getPhase());
                } else {
                    binding.cellContentLayout.phase.setText(item.getPhase());
                }
            }

            if (item.getKvln() != null && !item.getKvln().isEmpty()) {
                if (item.getKVLNColor() != null && !item.getKVLNColor().equals("NULL")) {
                    binding.cellContentLayout.kvln.setBackgroundColor(Color.parseColor(item.getKVLNColor()));
                    binding.cellContentLayout.kvln.setText(item.getKvln());
                } else {
                    binding.cellContentLayout.kvln.setText(item.getKvln());
                }
            }

            if (item.getLLLamp() != null && !item.getLLLamp().isEmpty()) {
                if (item.getLLLampColor() != null && !item.getLLLampColor().equals("NULL")) {
                    binding.cellContentLayout.lllA.setBackgroundColor(Color.parseColor(item.getLLLampColor()));
                    binding.cellContentLayout.lllA.setText(item.getLLLamp());
                } else {
                    binding.cellContentLayout.lllA.setText(item.getLLLamp());
                }
            }

            if (item.getLLLampKmax() != null && !item.getLLLampKmax().isEmpty()) {
                if (item.getLLLampKmaxColor() != null && !item.getLLLampKmaxColor().equals("NULL")) {
                    binding.cellContentLayout.lllKmaxA.setBackgroundColor(Color.parseColor(item.getLLLampKmaxColor()));
                    binding.cellContentLayout.lllKmaxA.setText(item.getLLLampKmax());
                } else {
                    binding.cellContentLayout.lllKmaxA.setText(item.getLLLampKmax());
                }
            }

            if (item.getLLLampKmaxZ() != null && !item.getLLLampKmaxZ().isEmpty()) {
                if (item.getLLLampKmaxZColor() != null && !item.getLLLampKmaxZColor().equals("NULL")) {
                    binding.cellContentLayout.lllKmaxZA.setBackgroundColor(Color.parseColor(item.getLLLampKmaxZColor()));
                    binding.cellContentLayout.lllKmaxZA.setText(item.getLLLampKmaxZ());
                } else {
                    binding.cellContentLayout.lllKmaxZA.setText(item.getLLLampKmaxZ());
                }
            }

            if (item.getLLLampKmin() != null && !item.getLLLampKmin().isEmpty()) {
                if (item.getLLLampKminColor() != null && !item.getLLLampKminColor().equals("NULL")) {
                    binding.cellContentLayout.lllKminA.setBackgroundColor(Color.parseColor(item.getLLLampKminColor()));
                    binding.cellContentLayout.lllKminA.setText(item.getLLLampKmin());
                } else {
                    binding.cellContentLayout.lllKminA.setText(item.getLLLampKmin());
                }
            }

            if (item.getLLLampKminZ() != null && !item.getLLLampKminZ().isEmpty()) {
                if (item.getLLLampKminZColor() != null && !item.getLLLampKminZColor().equals("NULL")) {
                    binding.cellContentLayout.lllKminZA.setBackgroundColor(Color.parseColor(item.getLLLampKminZColor()));
                    binding.cellContentLayout.lllKminZA.setText(item.getLLLampKminZ());
                } else {
                    binding.cellContentLayout.lllKminZA.setText(item.getLLLampKminZ());
                }
            }

            if (item.getLLGamp() != null && !item.getLLGamp().isEmpty()) {
                if (item.getLLGampColor() != null && !item.getLLGampColor().equals("NULL")) {
                    binding.cellContentLayout.llgA.setBackgroundColor(Color.parseColor(item.getLLGampColor()));
                    binding.cellContentLayout.llgA.setText(item.getLLGamp());
                } else {
                    binding.cellContentLayout.llgA.setText(item.getLLGamp());
                }
            }

            if (item.getLLGampKmax() != null && !item.getLLGampKmax().isEmpty()) {
                if (item.getLLGampKmaxColor() != null && !item.getLLGampKmaxColor().equals("NULL")) {
                    binding.cellContentLayout.llgKmaxA.setBackgroundColor(Color.parseColor(item.getLLGampKmaxColor()));
                    binding.cellContentLayout.llgKmaxA.setText(item.getLLGampKmax());
                } else {
                    binding.cellContentLayout.llgKmaxA.setText(item.getLLGampKmax());
                }
            }

            if (item.getLLGampKmin() != null && !item.getLLGampKmin().isEmpty()) {
                if (item.getLLGampKminColor() != null && !item.getLLGampKminColor().equals("NULL")) {
                    binding.cellContentLayout.llgKminA.setBackgroundColor(Color.parseColor(item.getLLGampKminColor()));
                    binding.cellContentLayout.llgKminA.setText(item.getLLGampKmin());
                } else {
                    binding.cellContentLayout.llgKminA.setText(item.getLLGampKmin());
                }
            }

            if (item.getLLLampKminZ() != null && !item.getLLLampKminZ().isEmpty()) {
                if (item.getLLLampKminZColor() != null && !item.getLLLampKminZColor().equals("NULL")) {
                    binding.cellContentLayout.lllKminZA.setBackgroundColor(Color.parseColor(item.getLLLampKminZColor()));
                    binding.cellContentLayout.lllKminZA.setText(item.getLLLampKminZ());
                } else {
                    binding.cellContentLayout.lllKminZA.setText(item.getLLLampKminZ());
                }
            }

            if (item.getLLGamp() != null && !item.getLLGamp().isEmpty()) {
                if (item.getLLGampColor() != null && !item.getLLGampColor().equals("NULL")) {
                    binding.cellContentLayout.llgA.setBackgroundColor(Color.parseColor(item.getLLGampColor()));
                    binding.cellContentLayout.llgA.setText(item.getLLGamp());
                } else {
                    binding.cellContentLayout.llgA.setText(item.getLLGamp());
                }
            }

            if (item.getLLGampKmax() != null && !item.getLLGampKmax().isEmpty()) {
                if (item.getLLGampKmaxColor() != null && !item.getLLGampKmaxColor().equals("NULL")) {
                    binding.cellContentLayout.llgKmaxA.setBackgroundColor(Color.parseColor(item.getLLGampKmaxColor()));
                    binding.cellContentLayout.llgKmaxA.setText(item.getLLGampKmax());
                } else {
                    binding.cellContentLayout.llgKmaxA.setText(item.getLLGampKmax());
                }
            }

            if (item.getLLGampKmin() != null && !item.getLLGampKmin().isEmpty()) {
                if (item.getLLGampKminColor() != null && !item.getLLGampKminColor().equals("NULL")) {
                    binding.cellContentLayout.llgKminA.setBackgroundColor(Color.parseColor(item.getLLGampKminColor()));
                    binding.cellContentLayout.llgKminA.setText(item.getLLGampKmin());
                } else {
                    binding.cellContentLayout.llgKminA.setText(item.getLLGampKmin());
                }
            }

            if (item.getLLamp() != null && !item.getLLamp().isEmpty()) {
                if (item.getLLampColor() != null && !item.getLLampColor().equals("NULL")) {
                    binding.cellContentLayout.llA.setBackgroundColor(Color.parseColor(item.getLLampColor()));
                    binding.cellContentLayout.llA.setText(item.getLLamp());
                } else {
                    binding.cellContentLayout.llA.setText(item.getLLamp());
                }
            }

            if (item.getLLampMin() != null && !item.getLLampMin().isEmpty()) {
                if (item.getLLampMinColor() != null && !item.getLLampMinColor().equals("NULL")) {
                    binding.cellContentLayout.llMinA.setBackgroundColor(Color.parseColor(item.getLLampMinColor()));
                    binding.cellContentLayout.llMinA.setText(item.getLLampMin());
                } else {
                    binding.cellContentLayout.llMinA.setText(item.getLLampMin());
                }
            }

            if (item.getLLampKmax() != null && !item.getLLampKmax().isEmpty()) {
                if (item.getLLampKmaxColor() != null && !item.getLLampKmaxColor().equals("NULL")) {
                    binding.cellContentLayout.llKmaxA.setBackgroundColor(Color.parseColor(item.getLLampKmaxColor()));
                    binding.cellContentLayout.llKmaxA.setText(item.getLLampKmax());
                } else {
                    binding.cellContentLayout.llKmaxA.setText(item.getLLampKmax());
                }
            }

            if (item.getLLampKmax() != null && !item.getLLampKmax().isEmpty()) {
                if (item.getLLampKmaxColor() != null && !item.getLLampKmaxColor().equals("NULL")) {
                    binding.cellContentLayout.llKmaxZA.setBackgroundColor(Color.parseColor(item.getLLampKmaxColor()));
                    binding.cellContentLayout.llKmaxZA.setText(item.getLLampKmax());
                } else {
                    binding.cellContentLayout.llKmaxZA.setText(item.getLLampKmax());
                }
            }

            if (item.getLLampKmin() != null && !item.getLLampKmin().isEmpty()) {
                if (item.getLLampKminColor() != null && !item.getLLampKminColor().equals("NULL")) {
                    binding.cellContentLayout.llKMinA.setBackgroundColor(Color.parseColor(item.getLLampKminColor()));
                    binding.cellContentLayout.llKMinA.setText(item.getLLampKmin());
                } else {
                    binding.cellContentLayout.llKMinA.setText(item.getLLampKmin());
                }
            }

            if (item.getLLampKminZ() != null && !item.getLLampKminZ().isEmpty()) {
                if (item.getLLampKminZColor() != null && !item.getLLampKminZColor().equals("NULL")) {
                    binding.cellContentLayout.llKMinZA.setBackgroundColor(Color.parseColor(item.getLLampKminZColor()));
                    binding.cellContentLayout.llKMinZA.setText(item.getLLampKminZ());
                } else {
                    binding.cellContentLayout.llKMinZA.setText(item.getLLampKminZ());
                }
            }

            if (item.getLGampMax() != null && !item.getLGampMax().isEmpty()) {
                if (item.getLGampMaxColor() != null && !item.getLGampMaxColor().equals("NULL")) {
                    binding.cellContentLayout.lgMaxA.setBackgroundColor(Color.parseColor(item.getLGampMaxColor()));
                    binding.cellContentLayout.lgMaxA.setText(item.getLGampMax());
                } else {
                    binding.cellContentLayout.lgMaxA.setText(item.getLGampMax());
                }
            }

            if (item.getLGampMin() != null && !item.getLGampMin().isEmpty()) {
                if (item.getLGampMinColor() != null && !item.getLGampMinColor().equals("NULL")) {
                    binding.cellContentLayout.lgMinA.setBackgroundColor(Color.parseColor(item.getLGampMinColor()));
                    binding.cellContentLayout.lgMinA.setText(item.getLGampMin());
                } else {
                    binding.cellContentLayout.lgMinA.setText(item.getLGampMin());
                }
            }

            if (item.getLGampKmax() != null && !item.getLGampKmax().isEmpty()) {
                if (item.getLGampKmaxColor() != null && !item.getLGampKmaxColor().equals("NULL")) {
                    binding.cellContentLayout.lgKmaxA.setBackgroundColor(Color.parseColor(item.getLGampKmaxColor()));
                    binding.cellContentLayout.lgKmaxA.setText(item.getLGampKmax());
                } else {
                    binding.cellContentLayout.lgKmaxA.setText(item.getLGampKmax());
                }
            }

            if (item.getLGampKmaxZ() != null && !item.getLGampKmaxZ().isEmpty()) {
                if (item.getLGampKmaxZColor() != null && !item.getLGampKmaxZColor().equals("NULL")) {
                    binding.cellContentLayout.lgKmaxZa.setBackgroundColor(Color.parseColor(item.getLGampKmaxZColor()));
                    binding.cellContentLayout.lgKmaxZa.setText(item.getLGampKmaxZ());
                } else {
                    binding.cellContentLayout.lgKmaxZa.setText(item.getLGampKmaxZ());
                }
            }

            if (item.getLGampKmin() != null && !item.getLGampKmin().isEmpty()) {
                if (item.getLGampKminColor() != null && !item.getLGampKminColor().equals("NULL")) {
                    binding.cellContentLayout.lgKminA.setBackgroundColor(Color.parseColor(item.getLGampKminColor()));
                    binding.cellContentLayout.lgKminA.setText(item.getLGampKmin());
                } else {
                    binding.cellContentLayout.lgKminA.setText(item.getLGampKmin());
                }
            }

            if (item.getLGampKminZ() != null && !item.getLGampKminZ().isEmpty()) {
                if (item.getLGampKminColor() != null && !item.getLGampKminColor().equals("NULL")) {
                    binding.cellContentLayout.lgKminZA.setBackgroundColor(Color.parseColor(item.getLGampKminColor()));
                    binding.cellContentLayout.lgKminZA.setText(item.getLGampKminZ());
                } else {
                    binding.cellContentLayout.lgKminZA.setText(item.getLGampKminZ());
                }
            }

            if (item.getDistance() != null && !item.getDistance().isEmpty()) {
                if (item.getDistanceColor() != null && !item.getDistanceColor().equals("NULL")) {
                    binding.cellContentLayout.totalDistance.setBackgroundColor(Color.parseColor(item.getDistanceColor()));
                    binding.cellContentLayout.totalDistance.setText(item.getDistance());
                } else {
                    binding.cellContentLayout.totalDistance.setText(item.getDistance());
                }
            }

            binding.foldingCell.setOnClickListener(view -> {
                binding.foldingCell.initialize(1000, Color.BLUE, 3);
                binding.foldingCell.initialize(30, 1000, Color.DKGRAY, 3);
                binding.foldingCell.toggle(false);
            });

        }
    }

}
