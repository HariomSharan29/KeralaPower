package com.techlabs.apdcl.adapters.report;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.DeviceArgument;
import com.techlabs.apdcl.databinding.CellBinding;
import com.techlabs.apdcl.models.report.TransformerReport;

import java.util.List;

public class TransformerReportAdapter extends RecyclerView.Adapter<TransformerReportAdapter.ViewHolder> {

    private final Context mainContext;
    private final DeviceArgument deviceArgument;
    private List<TransformerReport.Output.Datum> data;
    private String reportName = "";

    public TransformerReportAdapter(Context mainContext, List<TransformerReport.Output.Datum> analysisReportList, DeviceArgument deviceArgument, String reportName) {
        this.mainContext = mainContext;
        this.data = analysisReportList;
        this.deviceArgument = deviceArgument;
        this.reportName = reportName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CellBinding cellBinding = CellBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(cellBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransformerReport.Output.Datum item = data.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(List<TransformerReport.Output.Datum> list) {
        data = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CellBinding binding;

        public ViewHolder(@NonNull CellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(TransformerReport.Output.Datum item) {

            String overLoadColors = null;
            String overVoltageColors = null;
            String underVoltageColors = null;

            if (item.getEqCodeColor() != null && !item.getEqCodeColor().equals("NULL") && !item.getEqCodeColor().isEmpty()) {
                if (item.getEqCodeColor().equals("#FF0000")) {
                    overLoadColors = item.getEqCodeColor();
                } else if (item.getEqCodeColor().equals("#00FF00")) {
                    overVoltageColors = item.getEqCodeColor();
                } else if (item.getEqCodeColor().equals("#FFFF00")) {
                    underVoltageColors = item.getEqCodeColor();
                }
            }

            if (item.getEqIdColor() != null && !item.getEqIdColor().equals("NULL") && !item.getEqIdColor().isEmpty()) {
                if (item.getEqIdColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getEqIdColor();
                } else if (item.getEqIdColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getEqIdColor();
                } else if (item.getEqIdColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getEqIdColor();
                }
            }

            if (item.getEqNoColor() != null && !item.getEqNoColor().equals("NULL") && !item.getEqNoColor().isEmpty()) {
                if (item.getEqNoColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getEqNoColor();
                } else if (item.getEqNoColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getEqNoColor();
                } else if (item.getEqNoColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getEqNoColor();
                }
            }

            if (item.getFromNodeIdColor() != null && !item.getFromNodeIdColor().equals("NULL") && !item.getFromNodeIdColor().isEmpty()) {
                if (item.getFromNodeIdColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getFromNodeIdColor();
                } else if (item.getFromNodeIdColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getFromNodeIdColor();
                } else if (item.getFromNodeIdColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getFromNodeIdColor();
                }
            }

            if (item.getIAngleColor() != null && !item.getIAngleColor().equals("NULL") && !item.getIAngleColor().isEmpty()) {
                if (item.getIAngleColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getIAngleColor();
                } else if (item.getIAngleColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getIAngleColor();
                } else if (item.getIAngleColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getIAngleColor();
                }
            }

            if (item.getIBalColor() != null && !item.getIBalColor().equals("NULL") && !item.getIBalColor().isEmpty()) {
                if (item.getIBalColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getIBalColor();
                } else if (item.getIBalColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getIBalColor();
                } else if (item.getIBalColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getIBalColor();
                }
            }

            if (item.getKVARLossTot() != null && !item.getKVARLossTot().equals("NULL") && !item.getKVARLossTot().isEmpty()) {
                if (item.getKVARLossTot().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getKVARLossTot();
                } else if (item.getKVARLossTot().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getKVARLossTot();
                } else if (item.getKVARLossTot().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getKVARLossTot();
                }
            }

            if (item.getKVARTOTColor() != null && !item.getKVARTOTColor().equals("NULL") && !item.getKVARTOTColor().isEmpty()) {
                if (item.getKVARTOTColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getKVARTOTColor();
                } else if (item.getKVARTOTColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getKVARTOTColor();
                } else if (item.getKVARTOTColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getKVARTOTColor();
                }
            }

            if (item.getKVATOTColor() != null && !item.getKVATOTColor().equals("NULL") && !item.getKVATOTColor().isEmpty()) {
                if (item.getKVATOTColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getKVATOTColor();
                } else if (item.getKVATOTColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getKVATOTColor();
                } else if (item.getKVATOTColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getKVATOTColor();
                }
            }

            if (item.getKWLossTotColor() != null && !item.getKWLossTotColor().equals("NULL") && !item.getKWLossTotColor().isEmpty()) {
                if (item.getKWLossTotColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getKWLossTotColor();
                } else if (item.getKWLossTotColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getKWLossTotColor();
                } else if (item.getKWLossTotColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getKWLossTotColor();
                }
            }

            if (item.getKWTOTColor() != null && !item.getKWTOTColor().equals("NULL") && !item.getKWTOTColor().isEmpty()) {
                if (item.getKWTOTColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getKWTOTColor();
                } else if (item.getKWTOTColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getKWTOTColor();
                } else if (item.getKWTOTColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getKWTOTColor();
                }
            }

            if (item.getLOADINGColor() != null && !item.getLOADINGColor().equals("NULL") && !item.getLOADINGColor().isEmpty()) {
                if (item.getLOADINGColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getLOADINGColor();
                } else if (item.getLOADINGColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getLOADINGColor();
                } else if (item.getLOADINGColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getLOADINGColor();
                }
            }

            if (item.getPFavgColor() != null && !item.getPFavgColor().equals("NULL") && !item.getPFavgColor().isEmpty()) {
                if (item.getPFavgColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getPFavgColor();
                } else if (item.getPFavgColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getPFavgColor();
                } else if (item.getPFavgColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getPFavgColor();
                }
            }

            if (item.getToNodeIdColor() != null && !item.getToNodeIdColor().equals("NULL") && !item.getToNodeIdColor().isEmpty()) {
                if (item.getToNodeIdColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getToNodeIdColor();
                } else if (item.getToNodeIdColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getToNodeIdColor();
                } else if (item.getToNodeIdColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getToNodeIdColor();
                }
            }

            if (item.getXfoBoostColor() != null && !item.getXfoBoostColor().equals("NULL") && !item.getXfoBoostColor().isEmpty()) {
                if (item.getXfoBoostColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getXfoBoostColor();
                } else if (item.getXfoBoostColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getXfoBoostColor();
                } else if (item.getXfoBoostColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getXfoBoostColor();
                }
            }

            if (item.getXfoBuckColor() != null && !item.getXfoBuckColor().equals("NULL") && !item.getXfoBuckColor().isEmpty()) {
                if (item.getXfoBuckColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getXfoBuckColor();
                } else if (item.getXfoBuckColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getXfoBuckColor();
                } else if (item.getXfoBuckColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getXfoBuckColor();
                }
            }

            if (item.getXfoKVANomColor() != null && !item.getXfoKVANomColor().equals("NULL") && !item.getXfoKVANomColor().isEmpty()) {
                if (item.getXfoKVANomColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getXfoKVANomColor();
                } else if (item.getXfoKVANomColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getXfoKVANomColor();
                } else if (item.getXfoKVANomColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getXfoKVANomColor();
                }
            }

            if (item.getXfoKVLL1Color() != null && !item.getXfoKVLL1Color().equals("NULL") && !item.getXfoKVLL1Color().isEmpty()) {
                if (item.getXfoKVLL1Color().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getXfoKVLL1Color();
                } else if (item.getXfoKVLL1Color().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getXfoKVLL1Color();
                } else if (item.getXfoKVLL1Color().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getXfoKVLL1Color();
                }
            }

            if (item.getXfoKVLL2Color() != null && !item.getXfoKVLL2Color().equals("NULL") && !item.getXfoKVLL2Color().isEmpty()) {
                if (item.getXfoKVLL2Color().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getXfoKVLL2Color();
                } else if (item.getXfoKVLL2Color().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getXfoKVLL2Color();
                } else if (item.getXfoKVLL2Color().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getXfoKVLL2Color();
                }
            }

            if (item.getXfoRegIdColor() != null && !item.getXfoRegIdColor().equals("NULL") && !item.getXfoRegIdColor().isEmpty()) {
                if (item.getXfoRegIdColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getXfoRegIdColor();
                } else if (item.getXfoRegIdColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getXfoRegIdColor();
                } else if (item.getXfoRegIdColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getXfoRegIdColor();
                }
            }

            if (item.getXfoVsetColor() != null && !item.getXfoVsetColor().equals("NULL") && !item.getXfoVsetColor().isEmpty()) {
                if (item.getXfoVsetColor().equals("#FF0000") && overLoadColors == null) {
                    overLoadColors = item.getXfoVsetColor();
                } else if (item.getXfoVsetColor().equals("#00FF00") && overVoltageColors == null) {
                    overVoltageColors = item.getXfoVsetColor();
                } else if (item.getXfoVsetColor().equals("#FFFF00") && underVoltageColors == null) {
                    underVoltageColors = item.getXfoVsetColor();
                }
            }

            if (overLoadColors != null) {
                binding.cellTitleLayout.overloadColorTv.setBackgroundColor(Color.parseColor(overLoadColors));
            } else {
                binding.cellTitleLayout.overloadColorTv.setBackgroundColor(mainContext.getColor(R.color.blue));
            }

            if (overVoltageColors != null) {
                binding.cellTitleLayout.overVoltageColorTv.setBackgroundColor(Color.parseColor(overVoltageColors));
            } else {
                binding.cellTitleLayout.overVoltageColorTv.setBackgroundColor(mainContext.getColor(R.color.blue));
            }

            if (underVoltageColors != null) {
                binding.cellTitleLayout.underVoltageColorTv.setBackgroundColor(Color.parseColor(underVoltageColors));
            } else {
                binding.cellTitleLayout.underVoltageColorTv.setBackgroundColor(mainContext.getColor(R.color.blue));
            }

            if (item.getEqNo() != null && !item.getEqNo().isEmpty() && !item.getEqNo().equals("null")) {
                binding.cellTitleLayout.equipmentNoTv.setText(item.getEqNo());
                if (item.getEqNoColor() != null && !item.getEqNoColor().isEmpty() && !item.getEqNoColor().equals("NULL")) {
                    binding.cellTitleLayout.equipmentNoTv.setBackgroundColor(Color.parseColor(item.getEqNoColor()));
                }
            }

            if (item.getEqId() != null && !item.getEqId().isEmpty() && !item.getEqId().equals("null")) {
                binding.cellTitleLayout.equipmentIdTv.setText(item.getEqId());
                if (item.getEqIdColor() != null && !item.getEqIdColor().isEmpty() && !item.getEqIdColor().equals("NULL")) {
                    binding.cellTitleLayout.equipmentIdTv.setBackgroundColor(Color.parseColor(item.getEqIdColor()));
                }
            }

            if (item.getEqCode() != null && !item.getEqCode().isEmpty() && !item.getEqCode().equals("null")) {
                binding.cellTitleLayout.codeTv.setText(item.getEqCode());
            }

            if (item.getFromNodeId() != null && !item.getFromNodeId().isEmpty() && !item.getFromNodeId().equals("null")) {
                binding.cellTitleLayout.fromNodeTv.setText(item.getFromNodeId());
                if (item.getFromNodeIdColor() != null && !item.getFromNodeIdColor().isEmpty() && !item.getFromNodeIdColor().equals("NULL")) {
                    binding.cellTitleLayout.fromNodeTv.setBackgroundColor(Color.parseColor(item.getFromNodeIdColor()));
                }
            }

            if (item.getToNodeId() != null && !item.getToNodeId().isEmpty() && !item.getToNodeId().equals("null")) {
                binding.cellTitleLayout.toNodeTv.setText(item.getToNodeId());
                if (item.getToNodeIdColor() != null && !item.getToNodeIdColor().isEmpty() && !item.getToNodeIdColor().equals("NULL")) {
                    binding.cellTitleLayout.toNodeTv.setBackgroundColor(Color.parseColor(item.getToNodeIdColor()));
                }
            }

            if (!item.getEqCode().isEmpty() && item.getEqCode() != null && !item.getEqCode().equals("null")) {
                binding.cellContentLayout.codeTvc.setText(item.getEqCode());
            }

            if (!item.getEqId().isEmpty() && item.getEqId() != null && !item.getEqId().equals("null")) {
                binding.cellContentLayout.eqpIdTvc.setText(item.getEqId());
                if (item.getEqIdColor() != null && !item.getEqIdColor().isEmpty() && !item.getEqIdColor().equals("NULL")) {
                    binding.cellContentLayout.eqpIdTvc.setBackgroundColor(Color.parseColor(item.getEqIdColor()));
                }
            }

            if (!item.getEqNo().isEmpty() && item.getEqNo() != null && !item.getEqNo().equals("null")) {
                binding.cellContentLayout.eqpNoTvc.setText(item.getEqNo());
                if (item.getEqNoColor() != null && !item.getEqNoColor().isEmpty() && !item.getEqNoColor().equals("NULL")) {
                    binding.cellContentLayout.eqpNoTvc.setBackgroundColor(Color.parseColor(item.getEqNoColor()));
                }
            }

            if (item.getFromNodeId() != null && !item.getFromNodeId().isEmpty() && !item.getFromNodeId().equals("null")) {
                binding.cellContentLayout.fromNodeTvc.setText(item.getFromNodeId());
                if (item.getFromNodeIdColor() != null && !item.getFromNodeIdColor().isEmpty() && !item.getFromNodeIdColor().equals("NULL")) {
                    binding.cellContentLayout.fromNodeTvc.setBackgroundColor(Color.parseColor(item.getFromNodeIdColor()));
                }
            }

            if (item.getToNodeId() != null && !item.getToNodeId().isEmpty() && !item.getToNodeId().equals("null")) {
                binding.cellContentLayout.toNodeTvc.setText(item.getToNodeId());
                if (item.getToNodeIdColor() != null && !item.getToNodeIdColor().isEmpty() && !item.getToNodeIdColor().equals("NULL")) {
                    binding.cellContentLayout.toNodeTvc.setBackgroundColor(Color.parseColor(item.getToNodeIdColor()));
                }
            }

            if (item.getXfoKVANom() != null && !item.getXfoKVANom().isEmpty() && !item.getXfoKVANom().equals("null")) {
                binding.cellContentLayout.capNomKvaTvc.setText(item.getXfoKVANom());
                if (item.getXfoKVANomColor() != null && !item.getXfoKVANomColor().isEmpty() && !item.getXfoKVANomColor().equals("NULL")) {
                    binding.cellContentLayout.capNomKvaTvc.setBackgroundColor(Color.parseColor(item.getXfoKVANomColor()));
                }
            }

            if (item.getXfoKVLL1() != null && !item.getXfoKVLL1().isEmpty() && !item.getXfoKVLL1().equals("null")) {
                binding.cellContentLayout.primVoltKvlTvc.setText(item.getXfoKVLL1());
                if (item.getXfoKVLL1Color() != null && !item.getXfoKVLL1Color().isEmpty() && !item.getXfoKVLL1Color().equals("NULL")) {
                    binding.cellContentLayout.primVoltKvlTvc.setBackgroundColor(Color.parseColor(item.getXfoKVLL1Color()));
                }
            }

            if (item.getXfoKVLL2() != null && !item.getXfoKVLL2().isEmpty() && !item.getXfoKVLL2().equals("null")) {
                binding.cellContentLayout.secVoltKvlTvc.setText(item.getXfoKVLL2());
                if (item.getXfoKVLL2Color() != null && !item.getXfoKVLL2Color().isEmpty() && !item.getXfoKVLL2Color().equals("NULL")) {
                    binding.cellContentLayout.secVoltKvlTvc.setBackgroundColor(Color.parseColor(item.getXfoKVLL2Color()));
                }
            }

            if (item.getKwtot() != null && !item.getKwtot().isEmpty() && !item.getKwtot().equals("null")) {
                binding.cellContentLayout.totalPowerKwTvc.setText(item.getKwtot());
                if (item.getKWTOTColor() != null && !item.getKWTOTColor().isEmpty() && !item.getKWTOTColor().equals("NULL")) {
                    binding.cellContentLayout.totalPowerKwTvc.setBackgroundColor(Color.parseColor(item.getKWTOTColor()));
                }
            }

            if (item.getKvartot() != null && !item.getKvartot().isEmpty() && !item.getKvartot().equals("null")) {
                binding.cellContentLayout.totalPowerKvarTvc.setText(item.getKvartot());
                if (item.getKVARTOTColor() != null && !item.getKVARTOTColor().isEmpty() && !item.getKVARTOTColor().equals("NULL")) {
                    binding.cellContentLayout.totalPowerKvarTvc.setBackgroundColor(Color.parseColor(item.getKVARTOTColor()));
                }
            }

            if (item.getKvatot() != null && !item.getKvatot().isEmpty() && !item.getKvatot().equals("null")) {
                binding.cellContentLayout.totalPowerKvaTvc.setText(item.getKvatot());
                if (item.getKVATOTColor() != null && !item.getKVATOTColor().isEmpty() && !item.getKVATOTColor().equals("NULL")) {
                    binding.cellContentLayout.totalPowerKvaTvc.setBackgroundColor(Color.parseColor(item.getKVATOTColor()));
                }
            }

            if (item.getPFavg() != null && !item.getPFavg().isEmpty() && !item.getPFavg().equals("null")) {
                binding.cellContentLayout.pfAvgTvc.setText(item.getPFavg());
                if (item.getPFavgColor() != null && !item.getPFavgColor().isEmpty() && !item.getPFavgColor().equals("NULL")) {
                    binding.cellContentLayout.pfAvgTvc.setBackgroundColor(Color.parseColor(item.getPFavgColor()));
                }
            }

            if (item.getIBal() != null && !item.getIBal().isEmpty() && !item.getIBal().equals("null")) {
                binding.cellContentLayout.iBalTvc.setText(item.getIBal());
                if (item.getIBalColor() != null && !item.getIBalColor().isEmpty() && !item.getIBalColor().equals("NULL")) {
                    binding.cellContentLayout.iBalTvc.setBackgroundColor(Color.parseColor(item.getIBalColor()));
                }
            }

            if (item.getIAngle() != null && !item.getIAngle().isEmpty() && !item.getIAngle().equals("null")) {
                binding.cellContentLayout.angelITvc.setText(item.getIAngle());
                if (item.getIAngleColor() != null && !item.getIAngleColor().isEmpty() && !item.getIAngleColor().equals("NULL")) {
                    binding.cellContentLayout.angelITvc.setBackgroundColor(Color.parseColor(item.getIAngleColor()));
                }
            }

            if (item.getKWLossTot() != null && !item.getKWLossTot().isEmpty() && !item.getKWLossTot().equals("null")) {
                binding.cellContentLayout.totalLossKwTvc.setText(item.getKWLossTot());
                if (item.getKWLossTotColor() != null && !item.getKWLossTotColor().isEmpty() && !item.getKWLossTotColor().equals("NULL")) {
                    binding.cellContentLayout.totalLossKwTvc.setBackgroundColor(Color.parseColor(item.getKWLossTotColor()));
                }
            }

            if (item.getKVARLossTot() != null && !item.getKVARLossTot().isEmpty() && !item.getKVARLossTot().equals("null")) {
                binding.cellContentLayout.totalLossKvarTvc.setText(item.getKVARLossTot());
                if (item.getKVARLossTotColor() != null && !item.getKVARLossTotColor().isEmpty() && !item.getKVARLossTotColor().equals("NULL")) {
                    binding.cellContentLayout.totalLossKvarTvc.setBackgroundColor(Color.parseColor(item.getKVARLossTotColor()));
                }
            }

            if (item.getXfoBuck() != null && !item.getXfoBuck().isEmpty() && !item.getXfoBuck().equals("null")) {
                binding.cellContentLayout.buckTvc.setText(item.getXfoBuck());
                if (item.getXfoBuckColor() != null && !item.getXfoBuckColor().isEmpty() && !item.getXfoBuckColor().equals("NULL")) {
                    binding.cellContentLayout.buckTvc.setBackgroundColor(Color.parseColor(item.getXfoBuckColor()));
                }
            }

            if (item.getXfoBoost() != null && !item.getXfoBoost().isEmpty() && !item.getXfoBoost().equals("null")) {
                binding.cellContentLayout.boostTvc.setText(item.getXfoBoost());
                if (item.getXfoBoostColor() != null && !item.getXfoBoostColor().isEmpty() && !item.getXfoBoostColor().equals("NULL")) {
                    binding.cellContentLayout.boostTvc.setBackgroundColor(Color.parseColor(item.getXfoBoostColor()));
                }
            }

            if (item.getXfoVset() != null && !item.getXfoVset().isEmpty() && !item.getXfoVset().equals("null")) {
                binding.cellContentLayout.vSetTvc.setText(item.getXfoVset());
                if (item.getXfoVsetColor() != null && !item.getXfoVsetColor().isEmpty() && !item.getXfoVsetColor().equals("NULL")) {
                    binding.cellContentLayout.vSetTvc.setBackgroundColor(Color.parseColor(item.getXfoVsetColor()));
                }
            }

            if (item.getXfoRegId() != null && !item.getXfoRegId().isEmpty() && !item.getXfoRegId().equals("null")) {
                binding.cellContentLayout.regSectionIdTvc.setText(item.getXfoRegId());
                if (item.getXfoRegIdColor() != null && !item.getXfoRegIdColor().isEmpty() && !item.getXfoRegIdColor().equals("NULL")) {
                    binding.cellContentLayout.regSectionIdTvc.setBackgroundColor(Color.parseColor(item.getXfoRegIdColor()));
                }
            }

            if (item.getLoading() != null && !item.getLoading().isEmpty() && !item.getLoading().equals("null")) {
                binding.cellContentLayout.loadingTvc.setText(item.getLoading());
                if (item.getLOADINGColor() != null && !item.getLOADINGColor().isEmpty() && !item.getLOADINGColor().equals("NULL")) {
                    binding.cellContentLayout.loadingTvc.setBackgroundColor(Color.parseColor(item.getLOADINGColor()));
                }
            }

            binding.foldingCell.setOnClickListener(view -> {
                binding.foldingCell.initialize(1000, Color.BLUE, 3);
                binding.foldingCell.initialize(30, 1000, Color.DKGRAY, 3);
                binding.foldingCell.toggle(false);
            });

            binding.cellContentLayout.viewEquipmentBtn.setOnClickListener(view -> {
                if (deviceArgument != null) {
                    if (reportName.equals("Load Flow - Transformers")) {
                        deviceArgument.onXYCordinateSend(item.getEqNo(), "5");
                    }
                }
            });

        }

    }

}
