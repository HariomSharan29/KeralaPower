package com.techlabs.apdcl.adapters;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.techlabs.apdcl.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> implements Filterable {

    private List<String> allItems;
    private List<String> filteredItems;
    private List<String> selectedItems;
    private Context context;
    private OnItemSelectedListener listener;
    private boolean isFromDialog = false;
    private int currentDialogSelection = 0;
    private String searchQuery = "";

    public interface OnItemSelectedListener {
        void onItemSelected(String item, boolean isSelected);
    }

    public void setFromDialog(boolean fromDialog) {
        this.isFromDialog = fromDialog;
        currentDialogSelection = 0;
    }

    public CustomAdapter(Context context, List<String> items) {
        super(context, 0, items);
        this.context = context;
        this.allItems = new ArrayList<>(items);
        this.filteredItems = new ArrayList<>(items);
        this.selectedItems = new ArrayList<>();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return filteredItems.size();
    }

    @Override
    public String getItem(int position) {
        return filteredItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_spinner_checkbox, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.cBox);
        TextView textView = convertView.findViewById(R.id.netId);

        String item = getItem(position);
        if (!searchQuery.isEmpty() && item.toLowerCase().contains(searchQuery.toLowerCase())) {
            textView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_light));
        } else {
            textView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        }
        textView.setText(item);

        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(selectedItems.contains(item));

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isFromDialog) {
                if (isChecked) {
                    if (!selectedItems.contains(item)) {
                        if (currentDialogSelection >= 2) {
                            checkBox.setChecked(false);
                            Toast.makeText(context, "You can only select 2 feeders in dialog", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        currentDialogSelection++;
                        selectedItems.add(item);
                    }
                } else {
                    if (selectedItems.contains(item)) {
                        selectedItems.remove(item);
                        if (currentDialogSelection > 0) currentDialogSelection--;
                    }
                }
            } else {
                if (isChecked) {
                    if (selectedItems.size() >= 3) {
                        checkBox.setChecked(false);
                        Toast.makeText(context, "You can only select 3 feeders", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    selectedItems.add(item);
                } else {
                    selectedItems.remove(item);
                }
            }

            notifyDataSetChanged();
            if (listener != null) {
                listener.onItemSelected(item, isChecked);
            }
        });

        convertView.setOnClickListener(v -> checkBox.performClick());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
    public void setSelected(String item, boolean isSelected) {
        if (isSelected) {
            if (!selectedItems.contains(item)) selectedItems.add(item);
        } else {
            selectedItems.remove(item);
        }
        notifyDataSetChanged();
    }

    public void clearSelection() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<String> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(allItems);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (String item : allItems) {
                        if (item.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItems.clear();
                filteredItems.addAll((List<String>) results.values);
                searchQuery = constraint != null ? constraint.toString().trim() : "";
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return (String) resultValue;
            }
        };
    }
}
