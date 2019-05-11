package com.pisk.mydiet;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    Button b1, b2, b3, b4;
    NumberPicker weight, growth;
    EditText name;
    LinearLayout lay1, lay2, lay3;
    RelativeLayout bLayout;
    ScrollView scrollView2;
    Animation anim, anim2;
    SharedPreferences sPref;
    TextView hello3;
    final String SAVED_PROGRAM = "saved_program";
    final String USER_NAME = "user_name";
    final String USER_WEIGHT = "user_weight";
    final String USER_GROWTH = "user_growth";
    final String USER_GOAL = "user_goal";
    String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);

        //b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        //b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);

        weight = findViewById(R.id.weight);
        growth = findViewById(R.id.growth);
        name = findViewById(R.id.userName);

        //setDividerColor(weight,R.color.colorAccent);

        weight.setMaxValue(170);
        weight.setMinValue(40);
        weight.setValue(60);

        growth.setMaxValue(210);
        growth.setMinValue(120);
        growth.setValue(160);

        bLayout = findViewById(R.id.biglayout);

        lay1 = findViewById(R.id.lay1);
        lay2 = findViewById(R.id.lay2);
        lay3 = findViewById(R.id.lay3);

        hello3 = findViewById(R.id.hello3);

        scrollView2 = findViewById(R.id.scroll2);

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                //Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                String usernameLocal = name.getText().toString();
                if (usernameLocal.length() == 0) usernameLocal = "Пользователь";
                //sPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(USER_NAME,usernameLocal);
                ed.commit();

                //sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
                userName = sPref.getString(USER_NAME, "Пользователь");
                hello3.setText(userName + ", введите Ваши данные: ");
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
                        scrollView2.setVisibility(View.VISIBLE);
                    }
                }, 1000);

            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                sPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt(USER_WEIGHT,weight.getValue());
                ed.putInt(USER_GROWTH,growth.getValue());

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
                        bLayout.removeView(scrollView2);
                        lay3.startAnimation(anim2);
                        lay3.setVisibility(View.VISIBLE);
                    }
                }, 1000);

            }
        });



    }


}
