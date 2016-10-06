package com.example.kognsin.switchcompat;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.obswitchcompat.ObSwitchCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    ObSwitchCompat obSwitchCompat;
    List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments = new ArrayList<>();
        fragments.add(new BlankFragment().setPage("Page 1"));
        fragments.add(new BlankFragment().setPage("Page 2"));
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));
        obSwitchCompat = (ObSwitchCompat) findViewById(R.id.obSwitchCompat);
        obSwitchCompat.setTitle(new String[]{"TAB1", "TAB2"});
        obSwitchCompat.setTrackColor(Color.GREEN);
        obSwitchCompat.setupWithViewPager(pager);
    }

    public class FragmentAdapter extends FragmentPagerAdapter{

        List<Fragment> fragments;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
