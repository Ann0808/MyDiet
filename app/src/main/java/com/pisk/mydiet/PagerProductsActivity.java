package com.pisk.mydiet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class PagerProductsActivity extends AppCompatActivity {

    static final String TAG = "myLogs";
    static final int PAGE_COUNT = 5;
    //Bundle b;
    int programNumber;
    int week = 1;
    ViewPager pager;
    PagerTabStrip pagerTab;
    PagerAdapter pagerAdapter;
    SharedPreferences sPref;
    final String SAVED_PROGRAM = "saved_program";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        Intent intentTmp = getIntent();
        //b = intentTmp.getBundleExtra("bund");

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
        programNumber = sPref.getInt(SAVED_PROGRAM, 0);
        week = intentTmp.getIntExtra("arg_week_number",1);


        pager = (ViewPager) findViewById(R.id.pager);
        pagerTab = (PagerTabStrip) findViewById(R.id.pagerTabStrip);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("К списку недель");

        if(programNumber ==1) {
            pagerTab.setBackgroundResource(R.color.colorSuperFit);
        } else if(programNumber ==2) {
            pagerTab.setBackgroundResource(R.color.colorFit);
        } else if(programNumber ==3) {
            pagerTab.setBackgroundResource(R.color.colorBalance);
        } else {
            pagerTab.setBackgroundResource(R.color.colorStrong);
        }

        //for time
        //pagerTab.setBackgroundResource(R.color.colorPrimaryDark);

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
            return PageProductsFragment.newInstance((position +1), programNumber,week);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            CharSequence charSequence = "";

            switch (position){

                case 0: charSequence  = "Овощи/Фрукты ⟶";
                    break;
                case 1: charSequence  = "Рыба/Мясо ⟶";
                    break;
                case 2: charSequence  = "Молочные пр-ты ⟶";
                    break;
                case 3: charSequence  = "Бакалея ⟶";
                    break;
                default: charSequence  = "⟵ Разное";
                    break;
            }

            return charSequence;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
