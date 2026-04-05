package com.techlabs.apdcl.Utils.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.pdf.PdfRenderer;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PdfPageAdapter extends RecyclerView.Adapter<PdfPageAdapter.PageHolder> {

    private final PdfRenderer renderer;
    private final Context context;

    public PdfPageAdapter(Context ctx, PdfRenderer r) {
        context = ctx;
        renderer = r;
    }

    @NonNull
    @Override
    public PageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PdfPageView view = new PdfPageView(context);
        return new PageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageHolder holder, int position) {
        PdfRenderer.Page page = renderer.openPage(position);
        holder.view.setPage(page);
        page.close();
    }

    @Override
    public int getItemCount() {
        return renderer.getPageCount();
    }

    static class PageHolder extends RecyclerView.ViewHolder {
        PdfPageView view;
        PageHolder(@NonNull PdfPageView v) {
            super(v);
            view = v;
        }
    }
}

