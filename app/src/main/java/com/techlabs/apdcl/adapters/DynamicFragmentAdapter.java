package com.techlabs.apdcl.adapters;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class DynamicFragmentAdapter extends FragmentStateAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public DynamicFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public @Nullable CharSequence getPageTitle(int position) {
        return (position < 0 || position >= fragmentTitleList.size())
                ? null
                : fragmentTitleList.get(position);
    }

    public boolean hasFragmentWithTitle(@NonNull String title) {
        return fragmentTitleList.contains(title);
    }

    public @NonNull List<String> getTitleList() {
        return fragmentTitleList;
    }

    public @Nullable Fragment getFragmentAt(int position) {
        return (position >= 0 && position < fragmentList.size())
                ? fragmentList.get(position)
                : null;
    }

    @SuppressLint("NotifyDataSetChanged")
    public boolean addFragment(@NonNull Fragment fragment, @NonNull String title) {

        boolean hasSection = hasFragmentWithTitle("Section");
        boolean addingSection = "Section".equals(title);

        if (!addingSection && !hasSection && getItemCount() > 0) {
            return false;
        }

        if (hasFragmentWithTitle(title)) {
            return false;
        }

        fragmentList.add(fragment);
        fragmentTitleList.add(title);

        notifyItemInserted(fragmentList.size() - 1);
        return true;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeFragment(int position) {
        if (position < 0 || position >= fragmentList.size()) return;
        fragmentList.remove(position);
        fragmentTitleList.remove(position);
        notifyItemRemoved(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearFragments() {
        fragmentList.clear();
        fragmentTitleList.clear();
        notifyDataSetChanged();
    }

}