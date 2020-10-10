package com.pisk.mydiet;

import android.content.Intent;
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

public class PagerActivity extends AppCompatActivity {

    static final String TAG = "myLogs";
    static final int PAGE_COUNT = 5;
    //Bundle b;
    int programNumber;
    int dayNumber;
    String date;
    ViewPager pager;
    PagerTabStrip pagerTab;
    PagerAdapter pagerAdapter;
    //TextView viewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        Intent intentTmp = getIntent();
        //b = intentTmp.getBundleExtra("bund");
        programNumber = intentTmp.getIntExtra("arg_program_number",0);
        dayNumber = intentTmp.getIntExtra("arg_day_number",0);

        date = intentTmp.getStringExtra("arg_date");

        pager = (ViewPager) findViewById(R.id.pager);
        pagerTab = (PagerTabStrip) findViewById(R.id.pagerTabStrip);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("К списку дней");

        //viewDate = findViewById(R.id.date);
        //ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        //params.height = ViewPager.LayoutParams.WRAP_CONTENT;
        //viewDate.setText("'l");
//        if (date != null) {
//
//           // viewDate.setLayoutParams(params);
//            //viewDate.setText("ll");
//        }

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
            return PageFragment.newInstance((position +1), dayNumber, programNumber,date);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            CharSequence charSequence = "";

            switch (position){

                case 0: charSequence  = "Завтрак ⟶";
                    break;
                case 1: charSequence  = "Перекус ⟶";
                    break;
                case 2: charSequence  = "Обед ⟶";
                    break;
                case 3: charSequence  = "Полдник ⟶";
                    break;
                default: charSequence  = "⟵ Ужин";
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
