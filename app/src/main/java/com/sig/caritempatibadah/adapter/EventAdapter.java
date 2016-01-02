package com.sig.caritempatibadah.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sig.caritempatibadah.fragments.FragmentAddEvent_;
import com.sig.caritempatibadah.fragments.FragmentShowEvent_;

/**
 * Created by Rahmat Rasyidi Hakim on 12/11/2015.
 */
public class EventAdapter extends FragmentStatePagerAdapter {

    public EventAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentShowEvent_.builder().build();
            default:
                return FragmentAddEvent_.builder().build();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Lihat Event";
            default:
                return "Tambah Event";
        }
    }
}
