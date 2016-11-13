package com.example.kognsin.switchcompat;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.obswitchcompat.ObSwitchCompat;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    ObSwitchCompat obSwitchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(mFragmentAdapter);
        pager.setOffscreenPageLimit(mFragmentAdapter.getCount());
        pager.setCurrentItem(0);

        obSwitchCompat = (ObSwitchCompat) findViewById(R.id.obSwitchCompat);
        obSwitchCompat.setTrackHeight(getResources().getDimensionPixelSize(R.dimen.track_height));
        obSwitchCompat.setThumbHeight(getResources().getDimensionPixelSize(R.dimen.thumb_height));
        obSwitchCompat.setThumbWidth(getResources().getDimensionPixelSize(R.dimen.thumb_width));
        obSwitchCompat.setTrackStokeWidth(getResources().getDimensionPixelSize(R.dimen.stoke));
        obSwitchCompat.setTrackPadding(getResources().getDimensionPixelSize(R.dimen.stoke));
        obSwitchCompat.setTrackColor(Color.WHITE);
        obSwitchCompat.setupWithViewPager(pager);
    }

    public class FragmentAdapter extends FragmentPagerAdapter{

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BlankFragment.newInstance("Page "+ (position + 1));
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + (position + 1);
        }
    }

}
