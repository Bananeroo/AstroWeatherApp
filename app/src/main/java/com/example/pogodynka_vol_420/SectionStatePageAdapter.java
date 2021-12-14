package com.example.pogodynka_vol_420;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionStatePageAdapter extends PagerAdapter {

    private  final List<Fragment> mFragmentList=new ArrayList<>();
    private  final List<String>mFragmentTitleList=new ArrayList<>();

    private void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;    }
}
