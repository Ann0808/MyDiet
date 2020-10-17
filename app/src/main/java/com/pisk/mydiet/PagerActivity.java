package com.pisk.mydiet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

public class PagerActivity extends AppCompatActivity {

    static final String TAG = "myLogs";
    int pageCount;
    //Bundle b;
    int programNumber;
    int dayNumber;
    String date;
    ViewPager pager;
    PagerTabStrip pagerTab;
    PagerAdapter pagerAdapter;
    String color;
    String lightColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        Intent intentTmp = getIntent();
        programNumber = intentTmp.getIntExtra("arg_program_number",0);
        dayNumber = intentTmp.getIntExtra("arg_day_number",0);
        pageCount = intentTmp.getIntExtra("arg_page_count",0);

        color = intentTmp.getStringExtra("arg_color");
        lightColor = intentTmp.getStringExtra("arg_light_color");

        date = intentTmp.getStringExtra("arg_date");

        pager = (ViewPager) findViewById(R.id.pager);
        pagerTab = (PagerTabStrip) findViewById(R.id.pagerTabStrip);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("К списку дней");

        pagerTab.setBackgroundColor(Color.parseColor(color));

        pagerTab.setTabIndicatorColor(getResources().getColor(R.color.colorWhite));
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

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
            return PageFragment.newInstance((position +1), dayNumber, programNumber,date,lightColor);
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Map<Integer, CharSequence> hashMap = CommonFunctions.getTitlesMap(pageCount);
            return hashMap.get(position);
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
