package com.example.kognsin.switchcompat;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.obswitchcompat.ObSwitchCompat;
import com.example.obswitchcompat.ObSwitchCompatIconAdapter;
import com.example.obswitchcompat.ObSwitchCompatTab;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    ObSwitchCompat obSwitchCompat;
    private IconAdapter iconAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.ic_assignment_ind_black_24dp);
        icons.add(R.drawable.ic_av_timer_black_24dp);
        icons.add(R.drawable.ic_cast_black_24dp);
        icons.add(R.drawable.ic_cloud_download_black_24dp);
        icons.add(R.drawable.ic_mail_outline_black_24dp);
        iconAdapter = new IconAdapter(icons);

        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), iconAdapter);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(mFragmentAdapter);
        pager.setOffscreenPageLimit(mFragmentAdapter.getCount());
        pager.setCurrentItem(0);

        obSwitchCompat = (ObSwitchCompat) findViewById(R.id.obSwitchCompat);
        obSwitchCompat.setTrackHeight((int) getResources().getDimension(R.dimen.track_height));
        obSwitchCompat.setThumbHeight((int) getResources().getDimension(R.dimen.thumb_height));
        obSwitchCompat.setThumbWidth((int) getResources().getDimension(R.dimen.thumb_width));
        obSwitchCompat.setThumbColor(Color.TRANSPARENT);
        obSwitchCompat.setThumbStokeWidth((int) getResources().getDimension(R.dimen.stoke));
        obSwitchCompat.setThumbStokeColor(Color.LTGRAY);
        obSwitchCompat.setTrackStokeWidth(0);
        obSwitchCompat.setTrackPadding(0);
        obSwitchCompat.setTrackColor(Color.TRANSPARENT);
        obSwitchCompat.setTrackStokeColor(Color.WHITE);
        obSwitchCompat.setThumbTextColor(Color.GRAY);
        int tabPadding = (int) getResources().getDimension(R.dimen.tracktab_space);
        int tabPaddingTop = (int) getResources().getDimension(R.dimen.tracktab_spaceTop);
        obSwitchCompat.setTabPadding(new int[]{tabPadding, tabPaddingTop, tabPadding, tabPaddingTop});

        obSwitchCompat.setTabIcon(iconAdapter, ObSwitchCompatTab.ImagePosition.LEFT);
        obSwitchCompat.setupWithViewPager(pager);
    }

    public class FragmentAdapter extends FragmentPagerAdapter{


        private final IconAdapter mIconAdapter;

        public FragmentAdapter(FragmentManager fm, IconAdapter adapter) {
            super(fm);
            mIconAdapter = iconAdapter;
        }

        @Override
        public Fragment getItem(int position) {
            return BlankFragment.newInstance("P."+ (position + 1), iconAdapter.getTabIcon(position));
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "P." + (position + 1);
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
