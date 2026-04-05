package com.techlabs.apdcl.Utils.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHelper extends RecyclerView {

    private int maxHeightPx = Integer.MAX_VALUE;

    public ItemViewHelper(@NonNull Context context) {
        super(context);
    }

    public ItemViewHelper(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemViewHelper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setMaxHeightPx(int maxHeightPx) {
        this.maxHeightPx = maxHeightPx;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int limitedHeightSpec = MeasureSpec.makeMeasureSpec(maxHeightPx, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, limitedHeightSpec);
    }
}
