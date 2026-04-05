package com.techlabs.apdcl.adapters.Source;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.techlabs.apdcl.view.fragment.SourceFragment;
import com.techlabs.apdcl.view.fragment.SourceNetworkFragment;

public class SourceAdapter extends FragmentStateAdapter {
    private final Context context;
    private final String networkId;
    private final String nodeId;
    private final String nodeIdX;
    private final String nodeIdY;
    private final String latitude;
    private final String longitude;

    public SourceAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Context context, String networkId, String nodeId, String nodeIdX, String nodeIdY, String latitude, String longitude) {
        super(fragmentManager, lifecycle);
        this.context = context;
        this.networkId = networkId;
        this.nodeId = nodeId;
        this.nodeIdX = nodeIdX;
        this.nodeIdY = nodeIdY;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                SourceNetworkFragment sourceNetworkFragment = new SourceNetworkFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("NodeId", nodeId);
                sourceNetworkFragment.setArguments(bundle1);
                return sourceNetworkFragment;

            case 1:
                SourceFragment sourceFragment = new SourceFragment();
                Bundle bundle = new Bundle();
                bundle.putString("NodeId", nodeId);
                bundle.putString("NodeIdX", nodeIdX);
                bundle.putString("NodeIdY", nodeIdY);
                bundle.putString("Latitude", latitude);
                bundle.putString("Longitude", longitude);
                sourceFragment.setArguments(bundle);
                return sourceFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}