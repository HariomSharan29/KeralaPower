package com.techlabs.apdcl.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.databinding.NewConnetionItemLayoutBinding;
import com.techlabs.apdcl.models.nsc.NewConnectionModel;
import com.techlabs.apdcl.view.activity.MapActivity;

import java.util.ArrayList;
import java.util.List;

public class NewConnectionAdapter extends RecyclerView.Adapter<NewConnectionAdapter.ViewHolder> {

    private Context mainContext;
    private List<NewConnectionModel.Output> list;
    private List<NewConnectionModel.Output> updateList;
    private PrefManager prefManager;

    public NewConnectionAdapter(Context mainContext, List<NewConnectionModel.Output> list) {
        this.mainContext = mainContext;
        this.list = new ArrayList<>(list);
        this.updateList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewConnetionItemLayoutBinding binding = NewConnetionItemLayoutBinding.inflate(layoutInflater, parent, false);
        prefManager = new PrefManager(mainContext);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewConnectionModel.Output item = list.get(position);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String text) {
        list.clear();
        if (text.isEmpty()) {
            list.addAll(updateList);
        } else {
            text = text.toLowerCase();
            for (NewConnectionModel.Output item : updateList) {
                if (item.getApplicationID().contains(text)) {
                    list.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private NewConnetionItemLayoutBinding binding;

        public ViewHolder(@NonNull NewConnetionItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(NewConnectionModel.Output item) {

            try {

                if (item.getFeederid() != null && !item.getFeederid().isEmpty()) {
                    binding.newConnectionTitleLayout.feederIdTv.setText(item.getFeederid());
                } else {
                    binding.newConnectionTitleLayout.feederIdTv.setText("");
                }

                if (item.getNearestConsumerNumber() != null && !item.getNearestConsumerNumber().isEmpty()) {
                    binding.newConnectionTitleLayout.nearstConsumerNoTv.setText(item.getNearestConsumerNumber());
                } else {
                    binding.newConnectionTitleLayout.nearstConsumerNoTv.setText("");
                }

                if (item.getApplicationID() != null && !item.getApplicationID().isEmpty()) {
                    binding.newConnectionTitleLayout.applicationID.setText(item.getApplicationID().toString());
                } else {
                    binding.newConnectionTitleLayout.applicationID.setText("");
                }

                if (item.getBeforePercentageVR() != null && !item.getBeforePercentageVR().toString().isEmpty()) {
                    binding.newConnectionTitleLayout.beforeVr.setText(item.getBeforePercentageVR().toString());
                } else {
                    binding.newConnectionTitleLayout.beforeVr.setText("");
                }

                if (item.getAfterPercentageVR() != null && !item.getAfterPercentageVR().toString().isEmpty()) {
                    binding.newConnectionTitleLayout.afterVr.setText(item.getAfterPercentageVR().toString());
                } else {
                    binding.newConnectionTitleLayout.afterVr.setText("");
                }

                if (item.getBeforeDTLoading() != null && !item.getBeforeDTLoading().toString().isEmpty()) {
                    binding.newConnectionTitleLayout.beforeDtLoading.setText(item.getBeforeDTLoading().toString());
                } else {
                    binding.newConnectionTitleLayout.beforeDtLoading.setText("");
                }

                if (item.getAfterDTLoading() != null && !item.getAfterDTLoading().toString().isEmpty()) {
                    binding.newConnectionTitleLayout.afterDtLoading.setText(item.getAfterDTLoading().toString());
                } else {
                    binding.newConnectionTitleLayout.afterDtLoading.setText("");
                }

                if (item.getRecomndPhase() != null && !item.getRecomndPhase().toString().isEmpty()) {
                    if (item.getRecomndPhase() == "7") {
                        binding.newConnectionTitleLayout.recomndPhase.setText("ABC");
                    } else if (item.getRecomndPhase() == "3") {
                        binding.newConnectionTitleLayout.recomndPhase.setText("C");
                    } else if (item.getRecomndPhase() == "1") {
                        binding.newConnectionTitleLayout.recomndPhase.setText("A");
                    }
                } else {
                    binding.newConnectionTitleLayout.nearstConsumerNoTv.setText("");
                }

                if (item.getRemark() != null && !item.getRemark().isEmpty()) {
                    binding.newConnectionTitleLayout.remark.setText(item.getRemark());
                } else {
                    binding.newConnectionTitleLayout.remark.setText("");
                }

                if (item.getFeederid() != null && !item.getFeederid().isEmpty()) {
                    binding.newConnectionContentLayout.feederIdTv.setText(item.getFeederid());
                } else {
                    binding.newConnectionContentLayout.feederIdTv.setText("");
                }

                if (item.getNearestConsumerNumber() != null && !item.getNearestConsumerNumber().isEmpty()) {
                    binding.newConnectionContentLayout.nearstConsumerNoTv.setText(item.getNearestConsumerNumber());
                } else {
                    binding.newConnectionContentLayout.nearstConsumerNoTv.setText("");
                }

                if (item.getApplicationID() != null && !item.getApplicationID().toString().isEmpty()) {
                    binding.newConnectionContentLayout.applicationID.setText(item.getApplicationID().toString());
                } else {
                    binding.newConnectionContentLayout.applicationID.setText("");
                }

                if (item.getBeforePercentageVR() != null && !item.getBeforePercentageVR().toString().isEmpty()) {
                    binding.newConnectionContentLayout.beforeVr.setText(item.getBeforePercentageVR().toString());
                } else {
                    binding.newConnectionContentLayout.beforeVr.setText("");
                }

                if (item.getAfterPercentageVR() != null && !item.getAfterPercentageVR().toString().isEmpty()) {
                    binding.newConnectionContentLayout.afterVr.setText(item.getAfterPercentageVR().toString());
                } else {
                    binding.newConnectionContentLayout.afterVr.setText("");
                }

                if (item.getBeforeDTLoading() != null && !item.getBeforeDTLoading().toString().isEmpty()) {
                    binding.newConnectionContentLayout.beforeDtLoading.setText(item.getBeforeDTLoading().toString());
                } else {
                    binding.newConnectionContentLayout.beforeDtLoading.setText("");
                }

                if (item.getAfterDTLoading() != null && !item.getAfterDTLoading().toString().isEmpty()) {
                    binding.newConnectionContentLayout.afterDtLoading.setText(item.getAfterDTLoading().toString());
                } else {
                    binding.newConnectionContentLayout.afterDtLoading.setText("");
                }

                if (item.getRecomndPhase() != null && !item.getRecomndPhase().toString().isEmpty()) {
                    if (item.getRecomndPhase() == "7") {
                        binding.newConnectionContentLayout.recomndPhase.setText("ABC");
                    } else if (item.getRecomndPhase() == "3") {
                        binding.newConnectionContentLayout.recomndPhase.setText("C");
                    } else if (item.getRecomndPhase() == "1") {
                        binding.newConnectionContentLayout.recomndPhase.setText("A");
                    }
                } else {
                    binding.newConnectionContentLayout.nearstConsumerNoTv.setText("");
                }

                if (item.getRemark() != null && !item.getRemark().isEmpty()) {
                    binding.newConnectionContentLayout.remark.setText(item.getRemark());
                } else {
                    binding.newConnectionContentLayout.remark.setText("");
                }

                if (item.getStatus() != null && !item.getStatus().isEmpty()) {
                    binding.newConnectionContentLayout.status.setText(item.getStatus());
                } else {
                    binding.newConnectionContentLayout.status.setText("");
                }
                if(item.getBpno() != null && !item.getBpno().toString().isEmpty()){
                    binding.newConnectionContentLayout.bpNo.setText(item.getBpno().toString());
                }else {
                    binding.newConnectionContentLayout.bpNo.setText("");
                }
                if(item.getDate() != null && !item.getDate().isEmpty()){
                    binding.newConnectionContentLayout.date.setText(item.getDate());
                }else {
                    binding.newConnectionContentLayout.date.setText("");
                }
                if(item.getOperationType() !=null && !item.getOperationType().toString().isEmpty()){
                    binding.newConnectionContentLayout.operationType.setText(item.getOperationType().toString());
                }else {binding.newConnectionContentLayout.operationType.setText("");}

                if(item.getDtgisid() != null && !item.getDtgisid().isEmpty()){
                    binding.newConnectionContentLayout.dtSwtiched.setText(item.getDtgisid());
                }else{binding.newConnectionContentLayout.dtSwtiched.setText("");}

                if(item.getPoleNo() != null && !item.getPoleNo().toString().isEmpty()){
                    binding.newConnectionContentLayout.poleNo.setText(item.getPoleNo().toString());
                }else{binding.newConnectionContentLayout.poleNo.setText("");}

                if(item.getContractDemand() !=null && !item.getContractDemand().toString().isEmpty()){
                    binding.newConnectionContentLayout.contractDem.setText(item.getContractDemand().toString());
                }else{binding.newConnectionContentLayout.contractDem.setText("");}

                if(item.getSanctionedLoad() !=null && !item.getSanctionedLoad().toString().isEmpty()){
                    binding.newConnectionContentLayout.sancLoad.setText(item.getSanctionedLoad().toString());
                }else{binding.newConnectionContentLayout.sancLoad.setText("");}

                if(item.getSanctionedPhase() !=null && !item.getSanctionedPhase().toString().isEmpty()){
                    binding.newConnectionContentLayout.sancPhase.setText(item.getSanctionedPhase().toString());
                }else{binding.newConnectionContentLayout.sancPhase.setText("");}

                if(item.getSanctionedCategory() !=null && !item.getSanctionedCategory().toString().isEmpty()){
                    binding.newConnectionContentLayout.sancCategory.setText(item.getSanctionedCategory().toString());
                }else{binding.newConnectionContentLayout.sancCategory.setText("");}

                if(item.getServiceCableLength() !=null && !item.getServiceCableLength().toString().isEmpty()){
                    binding.newConnectionContentLayout.serCableLength.setText(item.getServiceCableLength().toString());
                }else{binding.newConnectionContentLayout.serCableLength.setText("");}

                if(item.getCircle() !=null && !item.getCircle().toString().isEmpty()){
                    binding.newConnectionContentLayout.circle.setText(item.getCircle().toString());
                }else{binding.newConnectionContentLayout.circle.setText("");}

                if(item.getDivision() !=null && !item.getDivision().toString().isEmpty()){
                    binding.newConnectionContentLayout.division.setText(item.getDivision().toString());
                }else{binding.newConnectionContentLayout.division.setText("");}

                if(item.getSubdivision() !=null && !item.getSubdivision().toString().isEmpty()){
                    binding.newConnectionContentLayout.subDivision.setText(item.getSubdivision().toString());
                }else{binding.newConnectionContentLayout.subDivision.setText("");}

                if(item.getSection() !=null && !item.getSection().toString().isEmpty()){
                    binding.newConnectionContentLayout.section.setText(item.getSection().toString());
                }else{binding.newConnectionContentLayout.section.setText("");}

                if(item.getSubstationID() !=null && !item.getSubstationID().toString().isEmpty()){
                    binding.newConnectionContentLayout.subStnID.setText(item.getSubstationID().toString());
                }else{binding.newConnectionContentLayout.subStnID.setText("");}

                if(item.getSubstationName() !=null && !item.getSubstationName().toString().isEmpty()){
                    binding.newConnectionContentLayout.subStnName.setText(item.getSubstationName().toString());
                }else{binding.newConnectionContentLayout.subStnName.setText("");}

                if (item.getFeasibility() != null && !item.getFeasibility().isEmpty()) {
                    if (item.getFeasibility().equals("YES")) {
                        binding.newConnectionContentLayout.feasibilityImg.setImageDrawable(ContextCompat.getDrawable(mainContext, R.drawable.ic_check_circle));
                        ;
                    } else {
                        binding.newConnectionContentLayout.feasibilityImg.setImageDrawable(ContextCompat.getDrawable(mainContext, R.drawable.ic_circular_uncheck));
                    }
                } else {
                    binding.newConnectionContentLayout.feasibilityImg.setImageDrawable(ContextCompat.getDrawable(mainContext, R.drawable.ic_circular_uncheck));

                }

                View.OnClickListener onClickListener = v -> openMapActivity(item);
                binding.newConnectionTitleLayout.selectTitleBtn.setOnClickListener(onClickListener);
                binding.newConnectionContentLayout.selectBtn.setOnClickListener(onClickListener);

            binding.foldingCell.setOnClickListener(view -> {
                binding.foldingCell.initialize(100, Color.BLUE, 3);
                binding.foldingCell.initialize(30, 100, Color.BLUE, 3);
                binding.foldingCell.toggle(false);
            });

            } catch (Exception e) {
                e.getLocalizedMessage();
            }

        }

        private void openMapActivity(NewConnectionModel.Output item) {
            Intent intent = new Intent(mainContext, MapActivity.class);
            prefManager.setUserType("Edit");
            ArrayList<String> list1 = new ArrayList<>();
            list1.add(item.getFeederid());
            intent.putStringArrayListExtra("NetworkId", list1);
            intent.putExtra("NearstConsumerNo", item.getNearestConsumerNumber());
            intent.putExtra("DeviceType", "20");
            intent.putExtra("ApplicationID", item.getApplicationID());
            intent.putExtra("ConnectedCapacity", item.getSanctionedLoad());
            intent.putExtra("phase", item.getRecomndPhase());
            intent.putExtra("CableLength", item.getServiceCableLength());
            intent.putExtra("Type", "NSC");
            mainContext.startActivity(intent);
        }
    }

}
