package com.techlabs.apdcl.adapters.CustAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.techlabs.apdcl.adapters.DisplayItem;
import com.techlabs.apdcl.databinding.CustomersLayoutBinding;
import com.techlabs.apdcl.databinding.ChildRowLayoutBinding;
import com.techlabs.apdcl.models.device.SpotLoad.Output.CustomerData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.ParentViewHolder> {

    private Context mainContext;
    private List<CustomerData> customerDataList;
    private Map<String, CustomerCommonData> customerCommonDataMap;
    private Map<String, List<DisplayItem>> customerChildMap;

    private static class CustomerCommonData {
        String customerNumber;
        String consumerClassId;
        String status;
        String loadYear;

        CustomerCommonData(CustomerData customer) {
            this.customerNumber = customer.getCustomerNumber() != null && !customer.getCustomerNumber().isEmpty()
                    ? customer.getCustomerNumber() : "";
            this.consumerClassId = customer.getConsumerClassId() != null && !customer.getConsumerClassId().isEmpty()
                    ? customer.getConsumerClassId() : "";
            this.status = customer.getStatus() != null
                    ? (customer.getStatus() == 0 ? "Connected" : customer.getStatus() == 1 ? "Disconnected" : "Unknown")
                    : "N/A";
            this.loadYear = customer.getLoadYear() != null ? customer.getLoadYear() : "";
        }
    }

    public CustomersAdapter(Context mainContext, List<CustomerData> customerDataList) {
        this.mainContext = mainContext;
        this.customerDataList = customerDataList;
        this.customerCommonDataMap = new HashMap<>();
        this.customerChildMap = new HashMap<>();

        for (CustomerData customer : customerDataList) {
            if (customer != null) {
                String customerKey = customer.getCustomerNumber() != null ? customer.getCustomerNumber() : String.valueOf(System.currentTimeMillis());
                customerCommonDataMap.putIfAbsent(customerKey, new CustomerCommonData(customer));

                List<DisplayItem> childItems = new ArrayList<>();
                if (customer.getPhase() != null) {
                    boolean isPhaseA = customer.getPhase().get1() != null && customer.getPhase().get1() != 0;
                    boolean isPhaseB = customer.getPhase().get2() != null && customer.getPhase().get2() != 0;
                    boolean isPhaseC = customer.getPhase().get3() != null && customer.getPhase().get3() != 0;
                    boolean isPhaseABC = customer.getPhase().get7() != null && customer.getPhase().get7() != 0;

                    if (isPhaseABC) {
                        childItems.add(new DisplayItem(customer, 7));
                    } else {
                        if (isPhaseA) childItems.add(new DisplayItem(customer, 1));
                        if (isPhaseB) childItems.add(new DisplayItem(customer, 2));
                        if (isPhaseC) childItems.add(new DisplayItem(customer, 3));
                    }
                } else {
                    childItems.add(new DisplayItem(customer, -1));
                }
                customerChildMap.put(customerKey, childItems);
            }
        }
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CustomersLayoutBinding binding = CustomersLayoutBinding.inflate(layoutInflater, parent, false);
        return new ParentViewHolder(binding, customerCommonDataMap, customerChildMap);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {
        CustomerData customer = customerDataList.get(position);
        holder.bindView(customer);
    }

    @Override
    public int getItemCount() {
        return customerDataList.size();
    }

    public static class ParentViewHolder extends RecyclerView.ViewHolder {
        private CustomersLayoutBinding binding;
        private Map<String, CustomerCommonData> customerCommonDataMap;
        private Map<String, List<DisplayItem>> customerChildMap;

        public ParentViewHolder(@NonNull CustomersLayoutBinding binding,
                                Map<String, CustomerCommonData> customerCommonDataMap,
                                Map<String, List<DisplayItem>> customerChildMap) {
            super(binding.getRoot());
            this.binding = binding;
            this.customerCommonDataMap = customerCommonDataMap;
            this.customerChildMap = customerChildMap;
        }

        @SuppressLint("SetTextI18n")
        public void bindView(CustomerData customer) {
            String customerKey = customer.getCustomerNumber() != null ? customer.getCustomerNumber() : String.valueOf(System.currentTimeMillis());
            CustomerCommonData commonData = customerCommonDataMap.get(customerKey);

            if (commonData != null) {
                binding.customerNumber.setText(commonData.customerNumber);
                binding.CType.setText(commonData.consumerClassId);
                binding.status.setText(commonData.status);
                binding.year.setText(commonData.loadYear);
            }

            binding.childTable.removeAllViews();

            List<DisplayItem> childItems = customerChildMap.get(customerKey);
            if (childItems != null) {
                for (DisplayItem displayItem : childItems) {
                    ChildRowLayoutBinding childBinding = ChildRowLayoutBinding.inflate(
                            LayoutInflater.from(binding.getRoot().getContext()),
                            binding.childTable,
                            false
                    );
                    bindChildView(childBinding, displayItem);
                    binding.childTable.addView(childBinding.getRoot());
                }
            }
        }

        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        private void bindChildView(ChildRowLayoutBinding binding, DisplayItem displayItem) {
            CustomerData item = displayItem.getCustomerData();
            int phaseValue = displayItem.getPhase();
            binding.aKva.setVisibility(View.GONE);
            binding.actualPF.setVisibility(View.GONE);
            binding.connectedKva.setVisibility(View.GONE);
            binding.kWh.setVisibility(View.GONE);
            binding.Cust.setVisibility(View.GONE);

            switch (phaseValue) {
                case 1:
                    binding.phase.setText("A");
                    if (item.getActualKW() != null && item.getActualKW().get1() != null) {
                        binding.aKva.setText(String.format("%.2f", item.getActualKW().get1()));
                        binding.aKva.setVisibility(View.VISIBLE);
                    }
                    if (item.getPowerFactor() != null && item.getPowerFactor().get1() != null) {
                        binding.actualPF.setText(String.format("%.2f", item.getPowerFactor().get1()));
                        binding.actualPF.setVisibility(View.VISIBLE);
                    }
                    if (item.getConnectedKVA() != null && item.getConnectedKVA().get1() != null) {
                        binding.connectedKva.setText(String.format("%.2f", item.getConnectedKVA().get1()));
                        binding.connectedKva.setVisibility(View.VISIBLE);
                    }
                    if (item.getKWH() != null && item.getKWH().get1() != null) {
                        binding.kWh.setText(String.format("%.2f", item.getKWH().get1()));
                        binding.kWh.setVisibility(View.VISIBLE);
                    }
                    if (item.getCustomerCount() != null && item.getCustomerCount().get1() != null) {
                        binding.Cust.setText(String.format("%.2f", item.getCustomerCount().get1()));
                        binding.Cust.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    binding.phase.setText("B");
                    if (item.getActualKW() != null && item.getActualKW().get2() != null) {
                        binding.aKva.setText(String.format("%.2f", item.getActualKW().get2()));
                        binding.aKva.setVisibility(View.VISIBLE);
                    }
                    if (item.getPowerFactor() != null && item.getPowerFactor().get2() != null) {
                        binding.actualPF.setText(String.format("%.2f", item.getPowerFactor().get2()));
                        binding.actualPF.setVisibility(View.VISIBLE);
                    }
                    if (item.getConnectedKVA() != null && item.getConnectedKVA().get2() != null) {
                        binding.connectedKva.setText(String.format("%.2f", item.getConnectedKVA().get2()));
                        binding.connectedKva.setVisibility(View.VISIBLE);
                    }
                    if (item.getKWH() != null && item.getKWH().get2() != null) {
                        binding.kWh.setText(String.format("%.2f", item.getKWH().get2()));
                        binding.kWh.setVisibility(View.VISIBLE);
                    }
                    if (item.getCustomerCount() != null && item.getCustomerCount().get2() != null) {
                        binding.Cust.setText(String.format("%.2f", item.getCustomerCount().get2()));
                        binding.Cust.setVisibility(View.VISIBLE);
                    }
                    break;
                case 3:
                    binding.phase.setText("C");
                    if (item.getActualKW() != null && item.getActualKW().get3() != null) {
                        binding.aKva.setText(String.format("%.2f", item.getActualKW().get3()));
                        binding.aKva.setVisibility(View.VISIBLE);
                    }
                    if (item.getPowerFactor() != null && item.getPowerFactor().get3() != null) {
                        binding.actualPF.setText(String.format("%.2f", item.getPowerFactor().get3()));
                        binding.actualPF.setVisibility(View.VISIBLE);
                    }
                    if (item.getConnectedKVA() != null && item.getConnectedKVA().get3() != null) {
                        binding.connectedKva.setText(String.format("%.2f", item.getConnectedKVA().get3()));
                        binding.connectedKva.setVisibility(View.VISIBLE);
                    }
                    if (item.getKWH() != null && item.getKWH().get3() != null) {
                        binding.kWh.setText(String.format("%.2f", item.getKWH().get3()));
                        binding.kWh.setVisibility(View.VISIBLE);
                    }
                    if (item.getCustomerCount() != null && item.getCustomerCount().get3() != null) {
                        binding.Cust.setText(String.format("%.2f", item.getCustomerCount().get3()));
                        binding.Cust.setVisibility(View.VISIBLE);
                    }
                    break;
                case 7:
                    binding.phase.setText("ABC");
                    if (item.getActualKW() != null && item.getActualKW().get7() != null) {
                        binding.aKva.setText(String.format("%.2f", item.getActualKW().get7()));
                        binding.aKva.setVisibility(View.VISIBLE);
                    }
                    if (item.getPowerFactor() != null && item.getPowerFactor().get7() != null) {
                        binding.actualPF.setText(String.format("%.2f", item.getPowerFactor().get7()));
                        binding.actualPF.setVisibility(View.VISIBLE);
                    }
                    if (item.getConnectedKVA() != null && item.getConnectedKVA().get7() != null) {
                        binding.connectedKva.setText(String.format("%.2f", item.getConnectedKVA().get7()));
                        binding.connectedKva.setVisibility(View.VISIBLE);
                    }
                    if (item.getKWH() != null && item.getKWH().get7() != null) {
                        binding.kWh.setText(String.format("%.2f", item.getKWH().get7()));
                        binding.kWh.setVisibility(View.VISIBLE);
                    }
                    if (item.getCustomerCount() != null && item.getCustomerCount().get7() != null) {
                        binding.Cust.setText(String.format("%.2f", item.getCustomerCount().get7()));
                        binding.Cust.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    binding.phase.setText("N/A");
                    break;
            }
        }
    }
}