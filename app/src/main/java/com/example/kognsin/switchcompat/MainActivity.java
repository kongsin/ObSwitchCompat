package com.example.kognsin.switchcompat;

import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.obswitchcompat.ObSwitchCompat;
import com.example.obswitchcompat.ObSwitchCompatIconAdapter;

import java.util.ArrayList;
import java.util.List;

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

        List<Integer> icons = new ArrayList<>();
        icons.add(android.R.drawable.ic_dialog_info);
        icons.add(android.R.drawable.ic_dialog_dialer);
        icons.add(android.R.drawable.ic_dialog_alert);
        icons.add(android.R.drawable.ic_dialog_email);
        icons.add(android.R.drawable.ic_dialog_map);
        IconAdapter iconAdapter = new IconAdapter(icons);

        obSwitchCompat = (ObSwitchCompat) findViewById(R.id.obSwitchCompat);
        obSwitchCompat.setTrackHeight(getResources().getDimensionPixelSize(R.dimen.track_height));
        obSwitchCompat.setThumbHeight(getResources().getDimensionPixelSize(R.dimen.thumb_height));
        obSwitchCompat.setThumbWidth(getResources().getDimensionPixelSize(R.dimen.thumb_width));
        obSwitchCompat.setThumbColor(android.R.color.transparent);
        obSwitchCompat.setThumbStokeWidth(0);
        obSwitchCompat.setTrackStokeWidth(0);
        obSwitchCompat.setTrackPadding(0);
        obSwitchCompat.setTrackColor(Color.WHITE);
        obSwitchCompat.setTrackStokeColor(Color.WHITE);

        obSwitchCompat.setTabIcon(iconAdapter);
        obSwitchCompat.setupWithViewPager(pager);
    }

    public class FragmentAdapter extends FragmentPagerAdapter{

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BlankFragment.newInstance("P."+ (position + 1));
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }

    public class IconAdapter implements ObSwitchCompatIconAdapter {

        private List<Integer> mIcons = new ArrayList<>();

        public IconAdapter(List<Integer> icons) {
            this.mIcons = icons;
        }

        @Override
        public int getTabIcon(int position) {
            return mIcons.get(position);
        }

        @Override
        public int getItemCount() {
            return mIcons.size();
        }
    }

}
