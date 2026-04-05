package com.techlabs.apdcl.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techlabs.apdcl.R;

import java.util.ArrayList;
import java.util.List;

public class SelectedFeedersAdapter extends RecyclerView.Adapter<SelectedFeedersAdapter.ViewHolder> {
    private List<String> selectedItems;
    private Context context;
    private OnItemRemovedListener onItemRemovedListener;
    private OnDataChangedListener onDataChangedListener;
    public interface OnItemRemovedListener {
        void onItemRemoved(String item);
    }
    public interface OnDataChangedListener {
        void onDataChanged(int itemCount);
    }

    public SelectedFeedersAdapter(Context context, List<String> initialItems) {
        this.context = context;
        this.selectedItems = new ArrayList<>(initialItems);
    }

    public void setOnItemRemovedListener(OnItemRemovedListener listener) {
        this.onItemRemovedListener = listener;
    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_spinner_checkbox, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = selectedItems.get(position);
        holder.textView.setText(item);
        holder.checkBox.setChecked(true);

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                selectedItems.remove(item);
                notifyDataSetChanged();
                if (onItemRemovedListener != null) {
                    onItemRemovedListener.onItemRemoved(item);
                }
                if (onDataChangedListener != null) {
                    onDataChangedListener.onDataChanged(getItemCount());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedItems.size();
    }

    public void addFeeder(String feederId) {
        if (!selectedItems.contains(feederId)) {
            selectedItems.add(feederId);
            notifyItemInserted(selectedItems.size() - 1);
            if (onDataChangedListener != null) {
                onDataChangedListener.onDataChanged(getItemCount());
            }
        }
    }
    public List<String> getSelectedItems() {
        return new ArrayList<>(selectedItems);
    }

    public void removeFeeder(String feederId) {
        int position = selectedItems.indexOf(feederId);
        if (position != -1) {
            selectedItems.remove(position);
            notifyItemRemoved(position);
            if (onDataChangedListener != null) {
                onDataChangedListener.onDataChanged(getItemCount());
            }
        }
    }

        public void clear() {
        selectedItems.clear();
        notifyDataSetChanged();
        if (onDataChangedListener != null) {
            onDataChangedListener.onDataChanged(getItemCount());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cBox);
            textView = itemView.findViewById(R.id.netId);
        }
    }
}