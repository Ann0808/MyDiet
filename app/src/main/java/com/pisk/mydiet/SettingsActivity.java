package com.pisk.mydiet;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {

    Button b1, b2, b3, b4, finish;
            //superFit, fit, balance, strong, finish;
//    NumberPicker weight, growth;
    EditText name;
    LinearLayout lay1, lay2, lay3, lay4;
    RelativeLayout bLayout;
    ScrollView scrollView2, scrollView3, scrollView4;
    Animation anim, anim2;
    SharedPreferences sPref;
    TextView hello4, recommend;
    private RadioGroup radioGroup1, radioGroup2;
    private RadioButton radioSelected1, radioSelected2;
    Calendar dateAndTime=Calendar.getInstance();
    DatePickerDialog.OnDateSetListener d;
    TextView currentDateTime;
    CalendarView picker;


    final String SAVED_PROGRAM = "saved_program";
    final String USER_NAME = "user_name";
//    final String USER_WEIGHT = "user_weight";
//    final String USER_GROWTH = "user_growth";
    final String USER_GOAL = "user_goal";
    final String DATE_START = "date_start";

    String[] programms;

    String userName = "";
    String currentDate = "";
    int points = 2;
    int programRecommended = 0;

    int intentProgramNumber = 0;
    private boolean firstPage = false;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        firstPage = true;

        //dayNumber = intentTmp.getIntExtra("arg_day_number",0);

        programms = getResources().getStringArray(R.array.programms);

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);

        //b1 = (Button) findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        //b3 = (Button) findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        finish = findViewById(R.id.buttonFinish);
        name = findViewById(R.id.userName);


        bLayout = findViewById(R.id.biglayout);

        lay1 = findViewById(R.id.lay1);
        lay2 = findViewById(R.id.lay2);
        lay3 = findViewById(R.id.lay3);
        lay4 = findViewById(R.id.lay4);

        hello4 = findViewById(R.id.hello4);
        recommend = findViewById(R.id.rec1);

        scrollView2 = findViewById(R.id.scroll2);
        scrollView3 = findViewById(R.id.scroll3);
        scrollView4 = findViewById(R.id.scroll4);

        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);

        picker = findViewById(R.id.datePicker);
        picker.setMinDate(System.currentTimeMillis() -1000);
        picker.setDate(System.currentTimeMillis() -1000);

        dbHelper = new DatabaseHelper(getApplicationContext());
        dbHelper.create_db();
        db = dbHelper.open();
        String table = "programs as R ";
        String columns[] = { "R.id as id, R.name as name", "R.image as image",
                             "R.color as color", "R.lightColor as lightColor",  "R.dayColor as dayColor"};
        cursor = db.query(table, columns, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nameColIndex = cursor.getColumnIndex(dbHelper.PrNAME);
                int imageColIndex = cursor.getColumnIndex(dbHelper.PrIMAGE);
                int colorColIndex = cursor.getColumnIndex(dbHelper.PrCOLOR);
                int lightColorColIndex = cursor.getColumnIndex(dbHelper.PrlightColor);
                int dayColorColIndex = cursor.getColumnIndex(dbHelper.PrdayColor);
                int idColIndex = cursor.getColumnIndex(dbHelper.Prid);

                do {
                    String lName = cursor.getString(nameColIndex);
                    String lImage = cursor.getString(imageColIndex);
                    String lColor = cursor.getString(colorColIndex);
                    String lLightColor = cursor.getString(lightColorColIndex);
                    String lDayColor = cursor.getString(dayColorColIndex);
                    final int lProgramNumber = cursor.getInt(idColIndex);

                    Button buttonProgram = new Button(this);
                    //float buttonProgramHeight = getResources().getDimension(R.dimen.button_height);
                    int buttonProgramHeight = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            getResources().getDimension(R.dimen.button_height),
                            getResources().getDisplayMetrics()
                    );
                    buttonProgram.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, buttonProgramHeight));
                    buttonProgram.setText(lName);
                    //buttonProgram.setId(lProgramNumber);
                    buttonProgram.setBackgroundResource(R.drawable.custom_shape);

                    Drawable limg = decodeDrawable(getApplicationContext(),lImage);
                   // Drawable limg = getApplicationContext().getResources().getDrawable(R.drawable.strong);
                    limg.setBounds(0, 0, 150, 240);
                    buttonProgram.setCompoundDrawables(null, null, limg, null);

                    GradientDrawable drawable = (GradientDrawable) buttonProgram.getBackground();
                    drawable.setColor(Color.parseColor(lColor));
                    buttonProgram.setTypeface(buttonProgram.getTypeface(), Typeface.BOLD);

                    buttonProgram.setShadowLayer(5,1,1, R.color.colorPrimaryDark);
                    buttonProgram.setTextColor(getResources().getColor(R.color.colorWhite));
                    buttonProgram.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);

                    lay3.addView(buttonProgram);

                    buttonProgram.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          makeAnimationButton();
                          setSAVED_PROGRAM(lProgramNumber);
                        }});

                }while (cursor.moveToNext());
            }
        }


        //go from main
        Intent intentTmp = getIntent();

        boolean chooseNew = intentTmp.getBooleanExtra("choose_new", false);
        boolean startAgain = intentTmp.getBooleanExtra("start_again", false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = simpleDateFormat.format(System.currentTimeMillis() -1000);

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(DATE_START,currentDate);
        ed.commit();

        if (chooseNew) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    bLayout.removeView(lay1);
                    lay2.setVisibility(View.VISIBLE);
                    scrollView2.setVisibility(View.VISIBLE);

                }
            }, 0);
        }

        if (startAgain) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    bLayout.removeView(lay1);
                    bLayout.removeView(scrollView2);
                    bLayout.removeView(scrollView3);
                    scrollView4.setVisibility(View.VISIBLE);
                    lay4.setVisibility(View.VISIBLE);

                }
            }, 0);
        }
        //go from main

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

               for(int i=0; i< group.getChildCount();i++) {
                   RadioButton rb = (RadioButton) group.getChildAt(i);
                   rb.setTextColor(getResources().getColor(R.color.colorWhite));
               }
               RadioButton rb = findViewById(checkedId);
               rb.setTextColor(getResources().getColor(R.color.colorBalance));
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                for(int i=0; i< group.getChildCount();i++) {
                    RadioButton rb = (RadioButton) group.getChildAt(i);
                    rb.setTextColor(getResources().getColor(R.color.colorWhite));
                }
                RadioButton rb = findViewById(checkedId);
                rb.setTextColor(getResources().getColor(R.color.colorBalance));
            }
        });



        picker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                month++;
                String strMonth = month>9 ? String.valueOf(month) : "0" + month;
                String day = dayOfMonth>9 ? String.valueOf(dayOfMonth) : "0" + dayOfMonth;
                currentDate = day + "/" + strMonth + "/" + year;
                Log.d("myLogs2", "currentDate: " + currentDate);
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                firstPage = false;

                v.setBackgroundResource(R.drawable.dcustom_shape3);
                //Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                String usernameLocal = name.getText().toString();
                if (usernameLocal.length() == 0) usernameLocal = "Пользователь";
                //sPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(USER_NAME,usernameLocal);
                ed.commit();


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

                v.setBackgroundResource(R.drawable.dcustom_shape3);

                points = 0;

                int selectedId1 = radioGroup1.getCheckedRadioButtonId();
                int selectedId2 = radioGroup2.getCheckedRadioButtonId();


                switch(selectedId1){
                    case R.id.radio_slim:
                        points+=1;
                        break;
                    case R.id.radio_balance:
                        points+=2;
                        break;
                    default: points+=3;
                        break;
                }

                switch(selectedId2){
                    case R.id.radio_1:
                        points+=1;
                        break;
                    case R.id.radio_2:
                        points+=2;
                        break;
                    default: points+=3;
                        break;
                }

                if (points == 2) {
                    programRecommended = 0;
                } else if (points == 3) {
                    programRecommended = 1;
                } else if (points == 4) {
                    programRecommended = 2;
                } else {
                    programRecommended = 3;
                }

                String textRecomendation = "(Мы рекомендуем Вам" + "\n" + programms[programRecommended] + ")";
                recommend.setText(textRecomendation);


                sPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);

                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha);
                anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha2);
                lay2.startAnimation(anim);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        bLayout.removeView(scrollView2);
                        lay3.startAnimation(anim2);
                        lay3.setVisibility(View.VISIBLE);
                        scrollView3.setVisibility(View.VISIBLE);
                    }
                }, 1000);

            }
        });


        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                v.setBackgroundResource(R.drawable.dcustom_shape3);
                sPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(DATE_START,currentDate);
                ed.commit();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date;
                long millis = System.currentTimeMillis();
                try {
                    date = sdf.parse(currentDate + " 11:00:00");

                    millis = date.getTime();
                    //
//                    Date dateNotification = new Date(millis);
//                    DateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
//                    String firstDate = df.format(dateNotification);
//                    Log.d("myLogs2", "date of today's notification is: " + firstDate);
                    //
                    if (millis > System.currentTimeMillis()) {

                        startAlert(millis,true);
                    }

                    millis = millis - 24*60*60*1000;
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.d("myLogs2", "not ok");
                }
                if (millis > System.currentTimeMillis()) {
                    startAlert(millis,false);
                    
                }
                final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("from_settings", true);
                startActivity(intent);

            }
        });


    }

    public void startAlert(long time, boolean today) {

        Intent intent;

        if (today) {
            intent = new Intent(this, MyBroadcastReceiverToday.class);
        } else {
            intent = new Intent(this, MyBroadcastReceiver.class);
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public void makeAnimationButton() {
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha);
        anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha2);
        lay3.startAnimation(anim);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                bLayout.removeView(scrollView3);
                lay4.startAnimation(anim2);
                scrollView4.setVisibility(View.VISIBLE);
                lay4.setVisibility(View.VISIBLE);

            }
        }, 1000);
    }

    public void setSAVED_PROGRAM(int prog) {
        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(SAVED_PROGRAM,prog);

        ed.commit();
    }

    @Override
    public void onBackPressed() {

        Log.d("FP", "this" + firstPage);

        if (!firstPage) {

            final Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
            System.exit(0);
        }

    }

    public static Drawable decodeDrawable(Context context, String base64) {
        Drawable ret = null;//from w ww.  ja  va  2  s  .  c  o  m
        if (!base64.equals("")) {
            ByteArrayInputStream bais = new ByteArrayInputStream(
                    Base64.decode(base64.getBytes(), Base64.DEFAULT));
            ret = Drawable.createFromResourceStream(context.getResources(),
                    null, bais, null, null);
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }


}
