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
import com.techlabs.apdcl.databinding.DetailedReportLayoutBinding;
import com.techlabs.apdcl.models.report.DetailedReport;

import java.util.List;

public class DetailedReportAdapter extends RecyclerView.Adapter<DetailedReportAdapter.ViewHolder> {

    private final Context mainContext;
    private final DeviceArgument deviceArgument;
    private List<DetailedReport.Output.Datum> data;
    private String reportName = "";

    public DetailedReportAdapter(Context mainContext, List<DetailedReport.Output.Datum> detailReportList, DeviceArgument deviceArgument, String reportName) {
        this.mainContext = mainContext;
        this.data = detailReportList;
        this.deviceArgument = deviceArgument;
        this.reportName = reportName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        DetailedReportLayoutBinding binding = DetailedReportLayoutBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.isEmpty()) {
            holder.bindViews();
        } else {
            DetailedReport.Output.Datum item = data.get(position);
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
    public void filterList(List<DetailedReport.Output.Datum> list) {
        data = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final DetailedReportLayoutBinding binding;

        public ViewHolder(@NonNull DetailedReportLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(DetailedReport.Output.Datum item) {

            binding.constraintLayout.setVisibility(View.VISIBLE);
            binding.noDataTvLayout.setVisibility(View.GONE);

            String overLoadColors = null;
            String overVoltageColors = null;
            String underVoltageColors = null;

            if (!item.getFeederIdColor().equalsIgnoreCase("NULL") && !item.getFeederIdColor().isEmpty()) {
                if (item.getFeederIdColor().equals("#FF0000")) {
                    overLoadColors = item.getFeederIdColor();
                } else if (item.getFeederIdColor().equals("#00FF00")) {
                    overVoltageColors = item.getFeederIdColor();
                } else if (item.getFeederIdColor().equals("#FFFF00")) {
                    underVoltageColors = item.getFeederIdColor();
                }
            }

            if (!item.getSectionIdColor().equalsIgnoreCase("NULL") && !item.getSectionIdColor().isEmpty()) {
                if (overLoadColors == null && item.getSectionIdColor().equals("#FF0000")) {
                    overLoadColors = item.getSectionIdColor();
                } else if (overVoltageColors == null && item.getSectionIdColor().equals("#00FF00")) {
                    overVoltageColors = item.getSectionIdColor();
                } else if (underVoltageColors == null && item.getSectionIdColor().equals("#FFFF00")) {
                    underVoltageColors = item.getSectionIdColor();
                }
            }

            if (!item.getEqIdColor().equalsIgnoreCase("NULL") && !item.getEqIdColor().isEmpty()) {
                if (overLoadColors == null && item.getEqIdColor().equals("#FF0000")) {
                    overLoadColors = item.getEqIdColor();
                } else if (overVoltageColors == null && item.getEqIdColor().equals("#00FF00")) {
                    overVoltageColors = item.getEqIdColor();
                } else if (underVoltageColors == null && item.getEqIdColor().equals("#FFFF00")) {
                    underVoltageColors = item.getEqIdColor();
                }
            }

            if (!item.getEqCodeColor().equalsIgnoreCase("NULL") && !item.getEqCodeColor().isEmpty()) {
                if (overLoadColors == null && item.getEqCodeColor().equals("#FF0000")) {
                    overLoadColors = item.getEqCodeColor();
                } else if (overVoltageColors == null && item.getEqCodeColor().equals("#00FF00")) {
                    overVoltageColors = item.getEqCodeColor();
                } else if (underVoltageColors == null && item.getEqCodeColor().equals("#FFFF00")) {
                    underVoltageColors = item.getEqCodeColor();
                }
            }

            if (!item.getLOADINGAColor().equalsIgnoreCase("NULL") && !item.getLOADINGAColor().isEmpty()) {
                if (overLoadColors == null && item.getLOADINGAColor().equals("#FF0000")) {
                    overLoadColors = item.getLOADINGAColor();
                } else if (overVoltageColors == null && item.getLOADINGAColor().equals("#00FF00")) {
                    overVoltageColors = item.getLOADINGAColor();
                } else if (underVoltageColors == null && item.getLOADINGAColor().equals("#FFFF00")) {
                    underVoltageColors = item.getLOADINGAColor();
                }
            }

            if (!item.getKWAColor().equalsIgnoreCase("NULL") && !item.getKWAColor().isEmpty()) {
                if (overLoadColors == null && item.getKWAColor().equals("#FF0000")) {
                    overLoadColors = item.getKWAColor();
                } else if (overVoltageColors == null && item.getKWAColor().equals("#00FF00")) {
                    overVoltageColors = item.getKWAColor();
                } else if (underVoltageColors == null && item.getKWAColor().equals("#FFFF00")) {
                    underVoltageColors = item.getKWAColor();
                }
            }

            if (!item.getKVARAColor().equalsIgnoreCase("NULL") && !item.getKVARAColor().isEmpty()) {
                if (overLoadColors == null && item.getKVARAColor().equals("#FF0000")) {
                    overLoadColors = item.getKVARAColor();
                } else if (overVoltageColors == null && item.getKVARAColor().equals("#00FF00")) {
                    overVoltageColors = item.getKVARAColor();
                } else if (underVoltageColors == null && item.getKVARAColor().equals("#FFFF00")) {
                    underVoltageColors = item.getKVARAColor();
                }
            }

            if (!item.getVAColor().equalsIgnoreCase("NULL") && !item.getVAColor().isEmpty()) {
                if (overLoadColors == null && item.getVAColor().equals("#FF0000")) {
                    overLoadColors = item.getVAColor();
                } else if (overVoltageColors == null && item.getVAColor().equals("#00FF00")) {
                    overVoltageColors = item.getVAColor();
                } else if (underVoltageColors == null && item.getVAColor().equals("#FFFF00")) {
                    underVoltageColors = item.getVAColor();
                }
            }

            if (overLoadColors != null) {
                binding.overloadColorTv.setBackgroundColor(Color.parseColor(overLoadColors));
            }

            if (overVoltageColors != null) {
                binding.overVoltageColorTv.setBackgroundColor(Color.parseColor(overVoltageColors));
            }

            if (underVoltageColors != null) {
                binding.underVoltageColorTv.setBackgroundColor(Color.parseColor(underVoltageColors));
            }

            if (item.getEqCode() != null && !item.getEqCode().isEmpty()) {
                binding.codeTv.setText(item.getEqCode());
            }

            if (item.getFeederId() != null && !item.getFeederId().isEmpty()) {
                binding.feederIdTv.setText(item.getFeederId());
                if (item.getFeederIdColor() != null && !item.getFeederIdColor().isEmpty() && !item.getFeederIdColor().equals("NULL")) {
                    binding.feederIdTv.setBackgroundColor(Color.parseColor(item.getFeederIdColor()));
                }
            }

            if (item.getSectionId() != null && !item.getSectionId().isEmpty()) {
                binding.sectionIdTv.setText(item.getSectionId());
                if (item.getSectionIdColor() != null && !item.getSectionIdColor().isEmpty() && !item.getSectionIdColor().equals("NULL")) {
                    binding.sectionIdTv.setBackgroundColor(Color.parseColor(item.getSectionIdColor()));
                }
            }

            if (item.getEqId() != null && !item.getEqId().isEmpty()) {
                binding.equipmentIdTv.setText(item.getEqId());
                if (item.getEqIdColor() != null && !item.getEqIdColor().isEmpty() && !item.getEqIdColor().equals("NULL")) {
                    binding.equipmentIdTv.setBackgroundColor(Color.parseColor(item.getEqIdColor()));
                }
            }

            if (item.getLoadinga() != null && !item.getLoadinga().isEmpty()) {
                binding.loadingATv.setText(item.getLoadinga());
                if (item.getLOADINGAColor() != null && !item.getLOADINGAColor().isEmpty() && !item.getLOADINGAColor().equals("NULL")) {
                    binding.loadingATv.setBackgroundColor(Color.parseColor(item.getLOADINGAColor()));
                }
            }

            if (item.getKwa() != null && !item.getKwa().isEmpty()) {
                binding.kwaTv.setText(item.getEqCode());
                if (item.getKWAColor() != null && !item.getKWAColor().isEmpty() && !item.getKWAColor().equals("NULL")) {
                    binding.kwaTv.setBackgroundColor(Color.parseColor(item.getKWAColor()));
                }
            }

            if (item.getKvara() != null && !item.getKvara().isEmpty()) {
                binding.kvaraTv.setText(item.getKvara());
                if (item.getKVARAColor() != null && !item.getKVARAColor().isEmpty() && !item.getKVARAColor().equals("NULL")) {
                    binding.kvaraTv.setBackgroundColor(Color.parseColor(item.getKVARAColor()));
                }
            }

            if (item.getVa() != null && !item.getVa().isEmpty()) {
                binding.vaTv.setText(item.getVa());
                if (item.getVAColor() != null && !item.getVAColor().isEmpty() && !item.getVAColor().equals("NULL")) {
                    binding.vaTv.setBackgroundColor(Color.parseColor(item.getVAColor()));
                }
            }

            binding.viewEquipmentBtn.setOnClickListener(view -> {
                if (deviceArgument != null) {
                    if (reportName.equals("Load Flow - Detailed")) {
                        if (item.getEqCode().contains("Two-Winding Transformer")) {
                            deviceArgument.onXYCordinateSend(item.getEqNo(), "5");
                        } else if (item.getEqCode().contains("Spot Load")) {
                            deviceArgument.onXYCordinateSend(item.getEqNo(), "20");
                        } else if (item.getEqCode().contains("Breaker")) {
                            deviceArgument.onXYCordinateSend(item.getEqNo(), "8");
                        } else if (item.getEqCode().contains("Cable")) {
                            deviceArgument.onXYCordinateSend(item.getEqNo(), "1");
                        } else if (item.getEqCode().contains("Switch")) {
                            deviceArgument.onXYCordinateSend(item.getEqNo(), "13");
                        } else if (item.getEqCode().contains("Fuse")) {
                            deviceArgument.onXYCordinateSend(item.getEqNo(), "14");
                        } else if (item.getEqCode().contains("Overhead Line")) {
                            deviceArgument.onXYCordinateSend(item.getEqNo(), "2");
                        } else if (item.getEqCode().contains("ShuntCapacitor")) {
                            deviceArgument.onXYCordinateSend(item.getEqNo(), "17");
                        } else {
                            deviceArgument.onXYCordinateSend(item.getEqNo(), "23");
                        }
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
