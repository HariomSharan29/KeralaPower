package com.techlabs.apdcl.adapters.analysis;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.techlabs.apdcl.view.fragment.NetworksFragment;
import com.techlabs.apdcl.view.fragment.ParametersFragment;
import com.techlabs.apdcl.view.fragment.ShortCircuitCalculation;
import com.techlabs.apdcl.view.fragment.ShortCircuitNetworks;
import com.techlabs.apdcl.view.fragment.ShortCircuitParameters;
import com.techlabs.apdcl.view.fragment.ShortCircuitRating;

import java.util.ArrayList;
import java.util.List;

public class ShortCircuitViewPagerAdapter extends FragmentStateAdapter {

    private final Context mainContext;
    private List<String> list = new ArrayList<>();
    private final String index;
    private final String nodeId;
    private ShortCircuitCalculation shortCircuitCalculation;
    private ShortCircuitNetworks shortCircuitNetworks;
    private ShortCircuitParameters shortCircuitParameters;
    private ShortCircuitRating shortCircuitRating;

    public ShortCircuitViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Context context, List<String> list, String index, String nodeId) {
        super(fragmentManager, lifecycle);
        this.mainContext = context;
        this.list = list;
        this.index = index;
        this.nodeId = nodeId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                shortCircuitCalculation = new ShortCircuitCalculation();
                Bundle bundle0 = new Bundle();
                bundle0.putStringArrayList("Network", (ArrayList<String>) list);
                bundle0.putString("Index", index);
                bundle0.putString("NodeId", nodeId);
                shortCircuitCalculation.setArguments(bundle0);
                return shortCircuitCalculation;

            case 1:
                shortCircuitParameters = new ShortCircuitParameters();
                Bundle bundle1 = new Bundle();
                bundle1.putStringArrayList("Network", (ArrayList<String>) list);
                bundle1.putString("Index", index);
                bundle1.putString("NodeId", nodeId);
                shortCircuitParameters.setArguments(bundle1);
                return shortCircuitParameters;

            case 2:
                shortCircuitNetworks = new ShortCircuitNetworks();
                Bundle bundle2 = new Bundle();
                bundle2.putStringArrayList("Network", (ArrayList<String>) list);
                bundle2.putString("Index", index);
                bundle2.putString("NodeId", nodeId);
                shortCircuitNetworks.setArguments(bundle2);
                return shortCircuitNetworks;

            case 3:
                shortCircuitRating = new ShortCircuitRating();
                Bundle bundle3 = new Bundle();
                bundle3.putStringArrayList("Network", (ArrayList<String>) list);
                bundle3.putString("Index", index);
                bundle3.putString("NodeId", nodeId);
                shortCircuitRating.setArguments(bundle3);
                return shortCircuitRating;

            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public ShortCircuitCalculation getCalculationFragment() {
        return shortCircuitCalculation;
    }
    public ShortCircuitNetworks getShortCircuitNetworks() {
        return shortCircuitNetworks;
    }
    public ShortCircuitParameters getShortCircuitParameter(){return shortCircuitParameters; }
    public ShortCircuitRating getShortCircuitRating(){return shortCircuitRating; }

}
