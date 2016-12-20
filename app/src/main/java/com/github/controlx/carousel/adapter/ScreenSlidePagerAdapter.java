package com.github.controlx.carousel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.github.controlx.carousel.fragment.ScreenSlidePageFragment;

/**
 * Created by Abhishek on 12/5/2016.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private int count;
    public ScreenSlidePagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        return ScreenSlidePageFragment.create(position);
    }

    @Override
    public int getCount() {
        return count;
    }
}