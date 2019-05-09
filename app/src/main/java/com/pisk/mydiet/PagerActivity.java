package com.pisk.mydiet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

public class PagerActivity extends FragmentActivity {

    static final String TAG = "myLogs";
    static final int PAGE_COUNT = 5;
    //Bundle b;
    int programNumber;
    int dayNumber;
    ViewPager pager;
    PagerTabStrip pagerTab;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        Intent intentTmp = getIntent();
        //b = intentTmp.getBundleExtra("bund");
        programNumber = intentTmp.getIntExtra("arg_program_number",0);
        dayNumber = intentTmp.getIntExtra("arg_day_number",0);

        pager = (ViewPager) findViewById(R.id.pager);
        pagerTab = (PagerTabStrip) findViewById(R.id.pagerTabStrip);

        if(programNumber ==1) {
            pagerTab.setBackgroundResource(R.color.colorSuperFit);
        } else if(programNumber ==2) {
            pagerTab.setBackgroundResource(R.color.colorFit);
        } else if(programNumber ==3) {
            pagerTab.setBackgroundResource(R.color.colorBalance);
        } else {
            pagerTab.setBackgroundResource(R.color.colorStrong);
        }

        pagerTab.setTabIndicatorColor(getResources().getColor(R.color.colorWhite));
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, position = " + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int newPos = position;
            if (newPos !=0 ) {
                newPos++;
            }
            return PageFragment.newInstance((position +1), dayNumber, programNumber);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            CharSequence charSequence = "";

            switch (position){

                case 0: charSequence  = "Завтрак";
                    break;
                case 1: charSequence  = "Перекус";
                    break;
                case 2: charSequence  = "Обед";
                    break;
                case 3: charSequence  = "Полдник";
                    break;
                default: charSequence  = "Ужин";
                    break;
            }

            return charSequence;
        }


    }

}
