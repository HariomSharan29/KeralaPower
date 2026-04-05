package com.techlabs.apdcl.view.LayerInfo.DeviceInfo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.adapters.Source.SourceAdapter;
import com.techlabs.apdcl.databinding.SourceBinding;

import java.util.Objects;

public class SourceDialog extends Dialog {

    private SourceBinding binding;
    private Context context;
    private FragmentManager fragmentManager;
    private Lifecycle lifecycle;
    private SourceAdapter adapter;
    private PrefManager prefManager;
    private String networkId;
    private String nodeId;
    private String nodeIdX;
    private String nodeIdY;
    private String latitude;
    private String longitude;

    public SourceDialog(@NonNull Context context, FragmentManager fragmentManager, Lifecycle lifecycle, String networkId, String nodeId, String nodeIdX, String nodeIdY, String latitude, String longitude) {
        super(context);
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.lifecycle = lifecycle;
        this.networkId = networkId;
        this.nodeId = nodeId;
        this.nodeIdX = nodeIdX;
        this.nodeIdY = nodeIdY;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @SuppressLint({"MissingInflatedId", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SourceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View MainLayoutBackGround = Objects.requireNonNull(getWindow()).getDecorView().getRootView();
        MainLayoutBackGround.setBackground(getContext().getDrawable(R.drawable.pop_layout_background));
        prefManager = new PrefManager(context);

        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.tablayout.addTab(binding.tablayout.newTab().setText("Network"));
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Source"));

        adapter = new SourceAdapter(fragmentManager, lifecycle, context, networkId, nodeId, nodeIdX, nodeIdY, latitude, longitude);
        binding.viewPager.setAdapter(adapter);

        binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tablayout.selectTab(binding.tablayout.getTabAt(position));
            }
        });

    }
}

