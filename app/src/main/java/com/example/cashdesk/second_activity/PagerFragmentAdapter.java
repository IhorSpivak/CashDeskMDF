package com.example.cashdesk.second_activity;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.cashdesk.helper.UpdatableFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerFragmentAdapter extends UpdatableFragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public PagerFragmentAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public OrdersListFragment getItem(int position) {
        if(position<0||position>=mFragmentList.size())
            return null;
        return (OrdersListFragment) mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void setFragments(List<Fragment> fragments, List<String> titles) {
        mFragmentList.clear();
        mFragmentTitleList.clear();
        mFragmentList.addAll(fragments);
        mFragmentTitleList.addAll(titles);
    }



    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
