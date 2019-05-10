package com.pisk.mydiet;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SettingsActivity extends Activity {

//    ViewPager pager;
//    PagerTabStrip pagerTab;
//    PagerAdapter pagerAdapter;

    Button b1, b2, b3, b4;
    EditText weight;
    LinearLayout bLayout, lay1, lay2, lay3;
    Animation anim, anim2;
    SharedPreferences sPref;
    final String SAVED_PROGRAM = "saved_program";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        weight = findViewById(R.id.weight);
        bLayout = (LinearLayout) findViewById(R.id.biglayout);
        lay1 = (LinearLayout) findViewById(R.id.lay1);
        lay2 = (LinearLayout) findViewById(R.id.lay2);
        lay3 = (LinearLayout) findViewById(R.id.lay3);
        //lay2.setAlpha((float) 0.0);

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                sPref = getPreferences(MODE_PRIVATE);
                int savedText = sPref.getInt(SAVED_PROGRAM, 0);

                Toast.makeText(getApplicationContext(), "program" + savedText, Toast.LENGTH_SHORT).show();

                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha);
                anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha2);
                lay1.startAnimation(anim);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        bLayout.removeView(lay1);
                        lay2.startAnimation(anim2);
                        lay2.setVisibility(View.VISIBLE);
                    }
                }, 2000);

            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                sPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt(SAVED_PROGRAM,Integer.valueOf(weight.getText().toString()));
                ed.commit();

//                sPref = getPreferences(MODE_PRIVATE);
//                int savedText = sPref.getInt(SAVED_PROGRAM, 0);
//
//                Toast.makeText(getApplicationContext(), "Text saved" + savedText, Toast.LENGTH_SHORT).show();
                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha);
                anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha2);
                lay2.startAnimation(anim);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        bLayout.removeView(lay2);
                        lay3.startAnimation(anim2);
                        lay3.setVisibility(View.VISIBLE);
                    }
                }, 2000);

            }
        });

//        pager = (ViewPager) findViewById(R.id.pager);
        //pagerTab = (PagerTabStrip) findViewById(R.id.pagerTabStrip);

        //pagerTab.setBackgroundResource(R.color.colorSuperFit);
        //pagerTab.setTabIndicatorColor(getResources().getColor(R.color.colorWhite));

//        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
//
//        pager.setAdapter(pagerAdapter);


    }

//    private class MyPagerAdapter extends FragmentPagerAdapter {
//
//        public MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int pos) {
//
//            switch(pos) {
//
//                case 0: return FirstFragment.newInstance(pos);
//                case 1: return SecondFragment.newInstance(pos);
//                default: return ThirdFragment.newInstance(pos);
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//
//            CharSequence charSequence = "";
//
//            switch (position){
//
//                case 0: charSequence  = "1";
//                    break;
//                case 1: charSequence  = "2";
//                    break;
//                default: charSequence  = "3";
//                    break;
//            }
//
//            return charSequence;
//        }
//    }

}
