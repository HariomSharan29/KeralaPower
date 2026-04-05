package com.techlabs.apdcl.adapters.analysis;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.techlabs.apdcl.view.fragment.ControlsFragment;
import com.techlabs.apdcl.view.fragment.NetworksFragment;
import com.techlabs.apdcl.view.fragment.ParametersFragment;

import java.util.ArrayList;
import java.util.List;

public class LoadFlowViewPagerAdapter extends FragmentStateAdapter {

    private Context context;
    private List<String> list = new ArrayList<>();
    private ParametersFragment parametersFragment;
    private NetworksFragment networksFragment;
    private ControlsFragment controlsFragment;

    public LoadFlowViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Context context, List<String> list) {
        super(fragmentManager, lifecycle);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                parametersFragment = new ParametersFragment();
                Bundle bundle0 = new Bundle();
                bundle0.putStringArrayList("Network", (ArrayList<String>) list);
                parametersFragment.setArguments(bundle0);
                return parametersFragment;
            case 1:
                networksFragment = new NetworksFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putStringArrayList("Network", (ArrayList<String>) list);
                networksFragment.setArguments(bundle1);
                return networksFragment;
            case 2:
                controlsFragment = new ControlsFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putStringArrayList("Network", (ArrayList<String>) list);
                controlsFragment.setArguments(bundle2);
                return controlsFragment;
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public NetworksFragment getNetworksFragment() {
        return networksFragment;
    }
    public ParametersFragment getParametersFragment() {
        return parametersFragment;
    }
    public ControlsFragment getControlsFragment() {
        return controlsFragment;
    }
}
