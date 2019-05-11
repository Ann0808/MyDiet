package com.pisk.mydiet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

public class SettingsActivity extends AppCompatActivity {

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



    }


}
