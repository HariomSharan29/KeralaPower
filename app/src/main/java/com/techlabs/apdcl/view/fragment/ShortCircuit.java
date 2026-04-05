package com.techlabs.apdcl.view.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.ShortCircuitArgument;
import com.techlabs.apdcl.adapters.analysis.ShortCircuitViewPagerAdapter;
import com.techlabs.apdcl.databinding.ShortCircuitLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class ShortCircuit extends BottomSheetDialogFragment {
    private Context context;
    private ShortCircuitLayoutBinding binding;
    private BottomSheetDialog dialog;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private ShortCircuitViewPagerAdapter adapter;
    private ShortCircuitCalculation calculationFragment;
    private ShortCircuitNetworks networkFragment;
    private ShortCircuitParameters shortCircuitParameters;
    private ShortCircuitRating shortCircuitRating;
    private ShortCircuitArgument shortCircuitArgument;

    public ShortCircuit(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ShortCircuitLayoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            shortCircuitArgument = (ShortCircuitArgument) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement LoadFlowArgument");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        CoordinatorLayout layout = dialog.findViewById(R.id.bottomSheetLayout);
        assert layout != null;
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        Bundle bundle = this.getArguments();

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HALF_EXPANDED + 5) {

                } else if (newState == BottomSheetBehavior.PEEK_HEIGHT_AUTO) {

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        binding.tabLayout.addTab(binding.tabLayout.newTab().setCustomView(createTabView("Calculation")));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setCustomView(createTabView("Parameters")));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setCustomView(createTabView("Network")));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setCustomView(createTabView("ShortCircuitRating")));
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        if (bundle != null) {
            List<String> list = new ArrayList<String>((ArrayList<String>) bundle.get("Network"));
            adapter = new ShortCircuitViewPagerAdapter(fragmentManager, getLifecycle(), context, list, bundle.getString("Index"), bundle.getString("NodeId"));
            binding.viewPager.setAdapter(adapter);
            binding.viewPager.setOffscreenPageLimit(2);
            binding.viewPager.post(() -> adapter.notifyDataSetChanged());
        }


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
                binding.execution.setVisibility(View.VISIBLE);
                /*if (position == 1 || position == 3) {
                    binding.execution.setVisibility(View.GONE);
                } else {
                    binding.execution.setVisibility(View.VISIBLE);
                }*/
            }
        });

        binding.runBtn.setOnClickListener(v -> {
            calculationFragment = adapter.getCalculationFragment();
            networkFragment = adapter.getShortCircuitNetworks();
            shortCircuitParameters = adapter.getShortCircuitParameter();
            shortCircuitRating = adapter.getShortCircuitRating();
            if (networkFragment == null || !networkFragment.isValid()) {
                Snackbar.make(v, "Please select the feeder!", Snackbar.LENGTH_LONG).show();
                return;
            }
            int onTab = binding.tabLayout.getSelectedTabPosition();
            switch (onTab){
                case 0:
                    if (calculationFragment != null && calculationFragment.isValid()) {
                        calculationFragment.SendShortCircuitArguments();
                    }
                    break;
                case 1:
                    if(shortCircuitParameters !=null){
                        shortCircuitParameters.SendShortCircuitArguments();
                    }
                case 2:
                    if (networkFragment != null && networkFragment.isValid()) {
                        networkFragment.SendShortCircuitArguments();
                    }
                case 3:
                    if(shortCircuitRating !=null){
                        shortCircuitRating.SendShortCircuitArguments();
                    }
                    break;
            }
        });

        binding.cancelBtn.setOnClickListener(v -> {
            JsonObject jsonObject = new JsonObject();
            List<String> list = new ArrayList<>();
            jsonObject.addProperty("ShortCircuit", false);
            if (shortCircuitArgument != null) {
                shortCircuitArgument.onShortCircuitArgReceived(jsonObject, list);
            }
        });

    }

    private View createTabView(String tabText) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        TextView textView = view.findViewById(R.id.tabText);
        textView.setText(tabText);
        return view;
    }
}
