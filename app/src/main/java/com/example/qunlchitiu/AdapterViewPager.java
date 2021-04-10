package com.example.qunlchitiu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class AdapterViewPager extends FragmentStatePagerAdapter {
    //Contructor
    public AdapterViewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //tạo 3 sự kiện onclick chuyển Fragment
        switch (position){
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentDetails();
            case 2:
                return new FragmentSearch();
            default:
                return new FragmentHome();
        }
    }

    @Override
    public int getCount() {
        return 3; //return về 3 vì mn có 3 item
    }
}
