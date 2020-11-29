package com.pisk.mydiet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

public class PagerLovingActivity extends AppCompatActivity {

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
    String lightColor = "#FFFFFF";
    HashMap<Integer, Integer> idWithNumber;
    int positionNumber = 0;
    String nameCategory = "";
    //Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_loving);

        //pageCount = 4;

        Intent intent = getIntent();
        idWithNumber = (HashMap<Integer, Integer>)intent.getSerializableExtra("idWithNumber");
        lightColor = intent.getStringExtra("arg_light_color");
        color = intent.getStringExtra("arg_color");
        positionNumber = CommonFunctions.getKeyByValue(idWithNumber, intent.getIntExtra("arg_id",0));
        pageCount = intent.getIntExtra("pageCount",1);
        nameCategory = intent.getStringExtra("category_name");

        pager = (ViewPager) findViewById(R.id.pager);
        pagerTab = (PagerTabStrip) findViewById(R.id.pagerTabStrip);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("К списку рецептов");

        pagerTab.setBackgroundColor(Color.parseColor(color));

        pagerTab.setTabIndicatorColor(getResources().getColor(R.color.colorWhite));
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(positionNumber);

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

            int id = idWithNumber.get(position);
            return PageFragment.newInstance(id, lightColor);
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return "♡"+nameCategory+"♡";
            if (position + 1 == pageCount) {
                return "⟵" + nameCategory;
            }
            else  {
                return nameCategory +"⟶";
            }
////            else {
////                return "←     →";
////            }

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

    @Override
    public void onBackPressed() {
        this.finish();
    }

}
