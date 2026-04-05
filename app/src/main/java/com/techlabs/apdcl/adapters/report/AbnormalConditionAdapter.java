package com.techlabs.apdcl.adapters.report;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techlabs.apdcl.Utils.DeviceArgument;
import com.techlabs.apdcl.databinding.AbnormalConditionReportLayoutBinding;
import com.techlabs.apdcl.models.report.AbnormalReport;

import java.util.List;

public class AbnormalConditionAdapter extends RecyclerView.Adapter<AbnormalConditionAdapter.ViewHolder> {

    private final Context mainContext;
    private final DeviceArgument deviceArgument;
    private List<AbnormalReport.Output.Datum> data;
    private String reportName = "";

    public AbnormalConditionAdapter(Context mainContext, List<AbnormalReport.Output.Datum> data, DeviceArgument deviceArgument, String reportName) {
        this.mainContext = mainContext;
        this.data = data;
        this.deviceArgument = deviceArgument;
        this.reportName = reportName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        AbnormalConditionReportLayoutBinding binding = AbnormalConditionReportLayoutBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AbnormalReport.Output.Datum item = data.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(List<AbnormalReport.Output.Datum> list) {
        data = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final AbnormalConditionReportLayoutBinding binding;

        public ViewHolder(@NonNull AbnormalConditionReportLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(AbnormalReport.Output.Datum item) {

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

            if (!item.getLOADINGBColor().equalsIgnoreCase("NULL") && !item.getLOADINGBColor().isEmpty()) {
                if (overLoadColors == null && item.getLOADINGBColor().equals("#FF0000")) {
                    overLoadColors = item.getLOADINGBColor();
                } else if (overVoltageColors == null && item.getLOADINGBColor().equals("#00FF00")) {
                    overVoltageColors = item.getLOADINGBColor();
                } else if (underVoltageColors == null && item.getLOADINGBColor().equals("#FFFF00")) {
                    underVoltageColors = item.getLOADINGBColor();
                }
            }

            if (!item.getLOADINGCColor().equalsIgnoreCase("NULL") && !item.getLOADINGCColor().isEmpty()) {
                if (overLoadColors == null && item.getLOADINGCColor().equals("#FF0000")) {
                    overLoadColors = item.getLOADINGCColor();
                } else if (overVoltageColors == null && item.getLOADINGCColor().equals("#00FF00")) {
                    overVoltageColors = item.getLOADINGCColor();
                } else if (underVoltageColors == null && item.getLOADINGCColor().equals("#FFFF00")) {
                    underVoltageColors = item.getLOADINGCColor();
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

            if (!item.getVBColor().equalsIgnoreCase("NULL") && !item.getVBColor().isEmpty()) {
                if (overLoadColors == null && item.getVBColor().equals("#FF0000")) {
                    overLoadColors = item.getVBColor();
                } else if (overVoltageColors == null && item.getVBColor().equals("#00FF00")) {
                    overVoltageColors = item.getVBColor();
                } else if (underVoltageColors == null && item.getVBColor().equals("#FFFF00")) {
                    underVoltageColors = item.getVBColor();
                }
            }

            if (!item.getVCColor().equalsIgnoreCase("NULL") && !item.getVCColor().isEmpty()) {
                if (overLoadColors == null && item.getVCColor().equals("#FF0000")) {
                    overLoadColors = item.getVCColor();
                } else if (overVoltageColors == null && item.getVCColor().equals("#00FF00")) {
                    overVoltageColors = item.getVCColor();
                } else if (underVoltageColors == null && item.getVCColor().equals("#FFFF00")) {
                    underVoltageColors = item.getVCColor();
                }
            }

            if (item.getEqCode() != null && !item.getEqCode().isEmpty()) {
                binding.abnormalContentLayout.codeTvc.setText(item.getEqCode());
                if (item.getEqCodeColor() != null && !item.getEqCodeColor().isEmpty() && !item.getEqCodeColor().equals("NULL")) {
                    binding.abnormalContentLayout.codeTvc.setBackgroundColor(Color.parseColor(item.getEqCodeColor()));
                }
            }

            if (item.getFeederId() != null && !item.getFeederId().isEmpty()) {
                binding.abnormalContentLayout.feederIdTv.setText(item.getFeederId());
                if (item.getFeederIdColor() != null && !item.getFeederIdColor().isEmpty() && !item.getFeederIdColor().equals("NULL")) {
                    binding.abnormalContentLayout.feederIdTv.setBackgroundColor(Color.parseColor(item.getFeederIdColor()));
                }
            }

            if (item.getSectionId() != null && !item.getSectionId().isEmpty()) {
                binding.abnormalContentLayout.sectionIdTv.setText(item.getSectionId());
                if (item.getSectionIdColor() != null && !item.getSectionIdColor().isEmpty() && !item.getSectionIdColor().equals("NULL")) {
                    binding.abnormalContentLayout.sectionIdTv.setBackgroundColor(Color.parseColor(item.getSectionIdColor()));
                }
            }

            if (item.getEqId() != null && !item.getEqId().isEmpty()) {
                binding.abnormalContentLayout.equipmentIdTv.setText(item.getEqId());
                if (item.getEqIdColor() != null && !item.getEqIdColor().isEmpty() && !item.getEqIdColor().equals("NULL")) {
                    binding.abnormalContentLayout.equipmentIdTv.setBackgroundColor(Color.parseColor(item.getEqIdColor()));
                }
            }

            if (item.getEqCode() != null && !item.getEqCode().isEmpty()) {
                binding.abnormalContentLayout.codeTv.setText(item.getEqCode());
                if (item.getEqCodeColor() != null && !item.getEqCodeColor().isEmpty() && !item.getEqCodeColor().equals("NULL")) {
                    binding.abnormalContentLayout.codeTv.setBackgroundColor(Color.parseColor(item.getEqCodeColor()));
                }
            }

            if (item.getLoadinga() != null && !item.getLoadinga().isEmpty()) {
                binding.abnormalContentLayout.loadingATv.setText(item.getLoadinga());
                if (item.getLOADINGAColor() != null && !item.getLOADINGAColor().isEmpty() && !item.getLOADINGAColor().equals("NULL")) {
                    binding.abnormalContentLayout.loadingATv.setBackgroundColor(Color.parseColor(item.getLOADINGAColor()));
                }
            }

            if (item.getLoadingb() != null && !item.getLoadingb().isEmpty()) {
                binding.abnormalContentLayout.loadingBTv.setText(item.getLoadingb());
                if (item.getLOADINGBColor() != null && !item.getLOADINGBColor().isEmpty() && !item.getLOADINGBColor().equals("NULL")) {
                    binding.abnormalContentLayout.loadingBTv.setBackgroundColor(Color.parseColor(item.getLOADINGBColor()));
                }
            }

            if (item.getLoadingc() != null && !item.getLoadingc().isEmpty()) {
                binding.abnormalContentLayout.loadingCTv.setText(item.getLoadingc());
                if (item.getLOADINGCColor() != null && !item.getLOADINGCColor().isEmpty() && !item.getLOADINGCColor().equals("NULL")) {
                    binding.abnormalContentLayout.loadingCTv.setBackgroundColor(Color.parseColor(item.getLOADINGCColor()));
                }
            }

            if (item.getVa() != null && !item.getVa().isEmpty()) {
                binding.abnormalContentLayout.vaTv.setText(item.getVa());
                if (item.getVAColor() != null && !item.getVAColor().isEmpty() && !item.getVAColor().equals("NULL")) {
                    binding.abnormalContentLayout.vaTv.setBackgroundColor(Color.parseColor(item.getVAColor()));
                }
            }

            if (item.getVb() != null && !item.getVb().isEmpty()) {
                binding.abnormalContentLayout.vBTv.setText(item.getVb());
                if (item.getVBColor() != null && !item.getVBColor().isEmpty() && !item.getVBColor().equals("NULL")) {
                    binding.abnormalContentLayout.vBTv.setBackgroundColor(Color.parseColor(item.getVBColor()));
                }
            }

            if (item.getVc() != null && !item.getVc().isEmpty()) {
                binding.abnormalContentLayout.vCTv.setText(item.getVc());
                if (item.getVCColor() != null && !item.getVCColor().isEmpty() && !item.getVCColor().equals("NULL")) {
                    binding.abnormalContentLayout.vCTv.setBackgroundColor(Color.parseColor(item.getVCColor()));
                }
            }

            binding.abnormalContentLayout.viewEquipmentBtn.setOnClickListener(view -> {
                if (deviceArgument != null) {
                    if (reportName.equals("Load Flow - Abnormal conditions")) {
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
    }

}
