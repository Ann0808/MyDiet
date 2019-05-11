package com.pisk.mydiet;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

//    ViewPager pager;
//    PagerTabStrip pagerTab;
//    PagerAdapter pagerAdapter;

    Button b1, b2, b3, b4;
    NumberPicker weight;
    LinearLayout lay1, lay2, lay3;
    RelativeLayout bLayout;
    Animation anim, anim2;
    SharedPreferences sPref;
    final String SAVED_PROGRAM = "saved_program";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        weight = findViewById(R.id.weight);

        weight.setMaxValue(170);
        weight.setMinValue(30);

        bLayout = findViewById(R.id.biglayout);

        lay1 = findViewById(R.id.lay1);
        lay2 = findViewById(R.id.lay2);
        lay3 = findViewById(R.id.lay3);
        //lay2.setAlpha((float) 0.0);

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                //Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();

                sPref = getPreferences(MODE_PRIVATE);
                int savedText = sPref.getInt(SAVED_PROGRAM, 0);

                //Toast.makeText(getApplicationContext(), "program" + savedText, Toast.LENGTH_SHORT).show();

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
                }, 1000);

            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                sPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt(SAVED_PROGRAM,weight.getValue());
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
                }, 1000);

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

//    @Override public void onLayoutChange (View v,
//                                          int left, int top, int right, int bottom,
//                                          int oldLeft, int oldTop, int oldRight, int oldBottom) {
//
//        if (left == right || top == bottom || (
//                left == oldLeft && top == oldTop &&
//                        right == oldRight && bottom == oldBottom))
//            return;
//
//        switch (v.getId()) {
//
//            case R.id.biglayout:
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    v.setBackground(
//                            scaleKeepingAspect(
//                                    getResources(),
//                                    R.drawable.apple,
//                                    v.getWidth(),
//                                    v.getHeight()));
//                } else {
//                    v.setBackgroundDrawable(
//                            scaleKeepingAspect(
//                                    getResources(),
//                                    R.drawable.apple,
//                                    v.getWidth(),
//                                    v.getHeight()));
//                }
//                break;
//
//        }
//
//    }

//    private BitmapDrawable scaleKeepingAspect(Resources res, int id, int dstWidth, int dstHeight) {
//
//        Bitmap b = (new BitmapFactory()).decodeResource(res, id);
//        float scaleX = (float) dstWidth / b.getWidth();
//        float scaleY = (float) dstHeight / b.getHeight();
//        float scale = scaleX < scaleY ? scaleX : scaleY;
//        int sclWidth = Math.round(scale * b.getWidth());
//        int sclHeight = Math.round(scale * b.getHeight());
//
//        b = Bitmap.createScaledBitmap(b, sclWidth, sclHeight, false);
//        int[] pixels = new int[sclWidth * sclHeight];
//        b.getPixels(pixels, 0, sclWidth, 0, 0, sclWidth, sclHeight);
//        b = Bitmap.createBitmap(dstWidth, dstHeight, b.getConfig());
//        b.setPixels(pixels, 0, sclWidth, (dstWidth - sclWidth) / 2, (dstHeight - sclHeight) / 2, sclWidth, sclHeight);
//        return new BitmapDrawable(res, Bitmap.createBitmap(b));
//
//    }


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
