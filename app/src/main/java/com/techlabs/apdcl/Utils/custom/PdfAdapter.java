package com.techlabs.apdcl.Utils.custom;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techlabs.apdcl.R;

import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.ViewHolder> {

    private Context context;
    private List<Uri> pdfUris;
    private OnPdfClickListener listener;

    public interface OnPdfClickListener {
        void onPdfClick(Uri uri);
    }

    public PdfAdapter(Context context, List<Uri> pdfUris, OnPdfClickListener listener) {
        this.context = context;
        this.pdfUris = pdfUris;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pdf, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri uri = pdfUris.get(position);
        holder.name.setText(getFileName(uri));
        holder.date.setText(getFileDateTime(uri));
        holder.eye.setOnClickListener(v -> listener.onPdfClick(uri));
    }

    @Override
    public int getItemCount() {
        return pdfUris.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView date;
        ImageView eye;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pdfName);
            date = itemView.findViewById(R.id.pdfDate);
            eye = itemView.findViewById(R.id.viewPdf);
        }
    }
    private String getFileName(Uri uri) {
        String docId = DocumentsContract.getDocumentId(uri);
        String[] parts = docId.split("/");
        String name = parts[parts.length - 1];
        if (name.length() > 23) {
            name = name.substring(0, 30);
        }
        return name;
    }
    private String getFileDateTime(Uri uri) {
        try (android.database.Cursor cursor = context.getContentResolver().query(
                uri,
                new String[]{
                        DocumentsContract.Document.COLUMN_LAST_MODIFIED
                },
                null, null, null
        )) {
            if (cursor != null && cursor.moveToFirst()) {
                long modified = cursor.getLong(0);

                java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("dd MMM yyyy | hh:mm a");

                return sdf.format(new java.util.Date(modified));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Unknown Date";
    }

}
