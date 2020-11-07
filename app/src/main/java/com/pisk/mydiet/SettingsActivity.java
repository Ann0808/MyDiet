package com.pisk.mydiet;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AppCompatActivity;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.pisk.mydiet.CommonFunctions.DATE_START;
import static com.pisk.mydiet.CommonFunctions.MY_AGE;
import static com.pisk.mydiet.CommonFunctions.MY_HEIGHT;
import static com.pisk.mydiet.CommonFunctions.MY_SEX;
import static com.pisk.mydiet.CommonFunctions.MY_WEIGHT;

public class SettingsActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener {

    Button buttonNextSex, buttonNextBodyParams, buttonNextGoal, buttonNextActivity, buttonFinish;

    //EditText name;
    LinearLayout laySex, layBodyParams, layChooseProgram, layCalendar;
    RelativeLayout bLayout, scrollBodyParams, scrollGoal, scrollActivity;
    ScrollView  scrollChooseProgram, scrollCalendar;
    Animation anim, anim2;
    SharedPreferences sPref;
    TextView textViewChooseProgram, recomendation;
    private RadioGroup radioGroupGoal, radioGroupActive, radioGroupSex;
    CalendarView picker;
    EditText growth, weight, age;

    int myWeight, myHeight, myAge;
    boolean mySex, chooseNew = false, startAgain = false;


    String currentDate = "";
    boolean isWoman = true;
    int heightValue = 0;
    int weightValue = 0;
    int ageValue = 0;
    double goalCoef = 0;
    double activityCoef = 0;

    private boolean firstPage = false;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    LayoutInflater inflaterForAlert;
    AlertDialog.Builder builderAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        firstPage = true;

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);

        buttonNextSex = findViewById(R.id.buttonNextSex);
        buttonNextBodyParams = findViewById(R.id.buttonNextBodyParams);
        buttonNextGoal = findViewById(R.id.buttonNextGoal);
        buttonNextActivity = findViewById(R.id.buttonNextActivity);
        buttonFinish = findViewById(R.id.buttonFinish);
        //name = findViewById(R.id.userName);


        bLayout = findViewById(R.id.biglayout);

        laySex = findViewById(R.id.laySex);
        layBodyParams = findViewById(R.id.layBodyParams);
        //layGoalAndActivity = findViewById(R.id.layGoalAndActivity);
        //layActivity = findViewById(R.id.layActivity);
        layChooseProgram = findViewById(R.id.layChooseProgram);
        layCalendar = findViewById(R.id.layCalendar);

        textViewChooseProgram = findViewById(R.id.textViewChooseProgram);
        recomendation = findViewById(R.id.recomendation);

        scrollBodyParams = findViewById(R.id.scrollBodyParams);
        scrollGoal = findViewById(R.id.scrollGoal);
        scrollActivity = findViewById(R.id.scrollActivity);
        scrollChooseProgram = findViewById(R.id.scrollChooseProgram);
        scrollCalendar = findViewById(R.id.scrollCalendar);

        radioGroupGoal = findViewById(R.id.radioGroupGoal);
        radioGroupActive = findViewById(R.id.radioGroupActive);
        radioGroupSex = findViewById(R.id.radioGroupSex);

        growth = findViewById(R.id.growth);
        growth.requestFocus();
        weight = findViewById(R.id.weight);
        age = findViewById(R.id.Age);


        picker = findViewById(R.id.datePicker);
        picker.setMinDate(System.currentTimeMillis() -1000);
        picker.setDate(System.currentTimeMillis() -1000);

        inflaterForAlert = this.getLayoutInflater();
        builderAlert = new AlertDialog.Builder(this);

        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

        // заменить это потом на получение по апи ++
        dbHelper = new DatabaseHelper(getApplicationContext());
        //dbHelper.create_db();
        db = dbHelper.open();
        String table = "programs as R ";
        String columns[] = { "R.id as id, R.name as name", "R.image as image", "R.color as color", "R.lightColor as lightColor", "R.description as description"};
        cursor = db.query(table, columns, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nameColIndex = cursor.getColumnIndex(dbHelper.PrNAME);
                int imageColIndex = cursor.getColumnIndex(dbHelper.PrIMAGE);
                int colorColIndex = cursor.getColumnIndex(dbHelper.PrCOLOR);
                int idColIndex = cursor.getColumnIndex(dbHelper.Prid);
                int idLightColor = cursor.getColumnIndex(dbHelper.PrlightColor);
                int idDescription = cursor.getColumnIndex(dbHelper.PrDescription);

                do {
                    String lName = cursor.getString(nameColIndex);
                    final String lShortDescr = cursor.getString(idDescription);
                    String lImage = cursor.getString(imageColIndex);
                    final String lColor = cursor.getString(colorColIndex);
                    final int lProgramNumber = cursor.getInt(idColIndex);
                    final String  lLightColor = cursor.getString(idLightColor);

                    Button buttonProgram = new Button(this);
                    int buttonProgramHeight = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            getResources().getDimension(R.dimen.button_height),
                            getResources().getDisplayMetrics()
                    );
                    buttonProgram.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, buttonProgramHeight));
                    buttonProgram.setText(lName);
                    buttonProgram.setBackgroundResource(R.drawable.custom_shape);
                    GradientDrawable drawable = (GradientDrawable) buttonProgram.getBackground();
                    drawable.setColor(Color.parseColor(lColor));
                    buttonProgram.setTypeface(buttonProgram.getTypeface(), Typeface.BOLD);

                    Drawable limg = CommonFunctions.decodeDrawable(getApplicationContext(),lImage);
                    limg.setBounds(0, 0, 150, 240);
                    buttonProgram.setCompoundDrawables(null, null, limg, null);


                    buttonProgram.setShadowLayer(5,1,1, R.color.colorPrimaryDark);
                    buttonProgram.setTextColor(getResources().getColor(R.color.colorWhite));
                    buttonProgram.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);

                    layChooseProgram.addView(buttonProgram);

                    buttonProgram.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.setBackgroundResource(R.drawable.custom_shape);
                            v.startAnimation(buttonClick);
                            //final GradientDrawable drawable = (GradientDrawable) v.getBackground();
                            //drawable.setColor(Color.parseColor(lLightColor));
                            //test comm
                            View contentAlert =  inflaterForAlert.inflate(R.layout.dialog_program_info, null);
                            builderAlert.setView(contentAlert);
                            TextView tvProgramInfo = (TextView) contentAlert.findViewById(R.id.programInfoView);
                            tvProgramInfo.setText(lShortDescr);
                            builderAlert.setNegativeButton("Посмотреть другие", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //drawable.setColor(Color.parseColor(lColor));
                                    dialog.cancel();

                                }
                            });
                            builderAlert.setPositiveButton("Выбрать эту программу", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    setSAVED_PROGRAM(lProgramNumber);
                                    makeAnimationButton();
                                    //dialog.cancel();

                                }
                            });


                            final AlertDialog alert = builderAlert.create();

                            alert.setOnShowListener( new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface arg0) {
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                }
                            });

                            alert.show();

                            //testcomm
                        }});

                }while (cursor.moveToNext());
            }
        }
        // заменить это потом на получение по апи --

        //go from main
        Intent intentTmp = getIntent();

        chooseNew = intentTmp.getBooleanExtra(CommonFunctions.CHOOSE_NEW, false);
        startAgain = intentTmp.getBooleanExtra(CommonFunctions.START_AGAIN, false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = simpleDateFormat.format(System.currentTimeMillis() -1000);

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(DATE_START,currentDate);
        ed.commit();

       // if (chooseNew) {

            mySex = sPref.getBoolean(MY_SEX, true);
            myWeight = sPref.getInt(MY_WEIGHT, 0);
            myHeight = sPref.getInt(MY_HEIGHT, 0);
            myAge = sPref.getInt(MY_AGE, 0);
            if (mySex == false) {
                RadioButton b = (RadioButton) findViewById(R.id.radio_male);
                RadioGroup radioGroupSex =  findViewById(R.id.radioGroupSex);
                b.setChecked(true);
                onCheckedChanged(radioGroupSex, R.id.radio_male);
            }

            if (myWeight > 0) {
                weight.setText(Integer.toString(myWeight));
            }

            if (myHeight > 0) {
                growth.setText(Integer.toString(myHeight));
            }

            if (myAge > 0) {
                age.setText(Integer.toString(myAge));
            }

       // }

        if (startAgain) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    bLayout.removeView(laySex);
                    bLayout.removeView(scrollGoal);
                    bLayout.removeView(scrollChooseProgram);
                    scrollCalendar.setVisibility(View.VISIBLE);
                    layCalendar.setVisibility(View.VISIBLE);

                }
            }, 0);
        }
        //go from main
        radioGroupSex.setOnCheckedChangeListener(this);
        radioGroupGoal.setOnCheckedChangeListener(this);
        radioGroupActive.setOnCheckedChangeListener(this);



        picker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                month++;
                String strMonth = month>9 ? String.valueOf(month) : "0" + month;
                String day = dayOfMonth>9 ? String.valueOf(dayOfMonth) : "0" + dayOfMonth;
                currentDate = day + "/" + strMonth + "/" + year;
            }
        });


        buttonNextSex.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                firstPage = false;

                v.setBackgroundResource(R.drawable.custom_shape_light);
                int selectedId = radioGroupSex.getCheckedRadioButtonId();

                switch(selectedId){
                    case R.id.radio_female:
                        isWoman = true;
                        break;
                    case R.id.radio_male:
                        isWoman = false;
                        break;
                    default: isWoman = true;
                        break;
                }

                SharedPreferences.Editor ed = sPref.edit();
                ed.putBoolean(MY_SEX,isWoman);
                ed.commit();

                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha);
                anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha2);
                laySex.startAnimation(anim);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        bLayout.removeView(laySex);
//                        layBodyParams.startAnimation(anim2);

                        scrollBodyParams.startAnimation(anim2);
                        scrollBodyParams.setVisibility(View.VISIBLE);
                        layBodyParams.setVisibility(View.VISIBLE);
                    }
                }, 1000);

            }
        });

        buttonNextBodyParams.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                if ((growth.getText().length() == 0 || weight.getText().length() == 0 || age.getText().length() == 0) ||
                        (Integer.valueOf(weight.getText().toString()) == 0 || Integer.valueOf(growth.getText().toString()) == 0 || Integer.valueOf(age.getText().toString()) == 0)){

                    builderAlert.setView(inflaterForAlert.inflate(R.layout.dialog_enter_body_params, null));
                    builderAlert.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });
                    final AlertDialog alert = builderAlert.create();

                    alert.setOnShowListener( new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0) {
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        }
                    });

                    alert.show();

                } else {

                    weightValue = Integer.valueOf(weight.getText().toString());
                    heightValue = Integer.valueOf(growth.getText().toString());
                    ageValue = Integer.valueOf(age.getText().toString());

                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putInt(MY_WEIGHT,weightValue);
                    ed.putInt(MY_HEIGHT,heightValue);
                    ed.putInt(MY_AGE,ageValue);
                    ed.commit();

                    v.setBackgroundResource(R.drawable.custom_shape_light);


                    anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha);
                    anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha2);
                    //layBodyParams.startAnimation(anim);
                    scrollBodyParams.startAnimation(anim);


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            bLayout.removeView(scrollBodyParams);
                            //layGoalAndActivity.startAnimation(anim2);
                            scrollGoal.startAnimation(anim2);
                            scrollGoal.setVisibility(View.VISIBLE);
                            //layGoalAndActivity.setVisibility(View.VISIBLE);
                            //scrollGoalAndActivity.setVisibility(View.VISIBLE);
                        }
                    }, 1000);
                }

            }
        });

        buttonNextGoal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                v.setBackgroundResource(R.drawable.custom_shape_light);

                int selectedId = radioGroupGoal.getCheckedRadioButtonId();


                switch(selectedId){
                    case R.id.radio_GoalSlim:
                        goalCoef = 0.7;
                        break;
                    case R.id.radio_GoalBalance:
                        goalCoef = 1;
                        break;
                    default: goalCoef = 1.3;
                        break;
                }


                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha);
                anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha2);
                //layGoalAndActivity.startAnimation(anim);
                scrollGoal.startAnimation(anim);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        bLayout.removeView(scrollGoal);
                        scrollActivity.startAnimation(anim2);
                        scrollActivity.setVisibility(View.VISIBLE);
//                        layChooseProgram.startAnimation(anim2);
//                        layChooseProgram.setVisibility(View.VISIBLE);
//                        scrollChooseProgram.setVisibility(View.VISIBLE);
                    }
                }, 1000);

            }
        });

        buttonNextActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                v.setBackgroundResource(R.drawable.custom_shape_light);

                //points = 0;

                int selectedId = radioGroupActive.getCheckedRadioButtonId();



                switch(selectedId){
                    case R.id.radio_lazyActive:
                        activityCoef = 1.2;
                        break;
                    case R.id.radio_balanseActive:
                        activityCoef = 1.4;
                        break;
                    default: activityCoef = 1.6;
                        break;
                }

                int recommendedNorm = CommonFunctions.countDayNorm(isWoman,weightValue, heightValue,goalCoef, activityCoef, ageValue);

                recomendation.setText("(Ваша дневная норма каллорий " + recommendedNorm + ")");

                anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha);
                anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myalpha2);
                //layGoalAndActivity.startAnimation(anim);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        bLayout.removeView(scrollActivity);
                        layChooseProgram.startAnimation(anim2);
                        layChooseProgram.setVisibility(View.VISIBLE);
                        scrollChooseProgram.setVisibility(View.VISIBLE);
                    }
                }, 1000);

            }
        });


        buttonFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                v.setBackgroundResource(R.drawable.custom_shape_light);
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

                    if (millis > System.currentTimeMillis()) {

                        startAlert(millis,true);
                    }

                    millis = millis - 24*60*60*1000;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (millis > System.currentTimeMillis()) {
                    startAlert(millis,false);

                }
                final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(CommonFunctions.FROM_SETTINGS, true);
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
        layChooseProgram.startAnimation(anim);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                bLayout.removeView(scrollChooseProgram);
                layCalendar.startAnimation(anim2);
                scrollCalendar.setVisibility(View.VISIBLE);
                layCalendar.setVisibility(View.VISIBLE);

            }
        }, 1000);
    }

    public void setSAVED_PROGRAM(int prog) {
        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref), 0);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(CommonFunctions.SAVED_PROGRAM,prog);

        ed.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        for(int i=0; i< group.getChildCount();i++) {
            RadioButton rb = (RadioButton) group.getChildAt(i);
            rb.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        RadioButton rb = findViewById(checkedId);
        rb.setTextColor(getResources().getColor(R.color.colorBalance));
    }

    @Override
    public void onBackPressed() {

        if (!firstPage) {

            final Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        }
        else if (chooseNew || startAgain) {
            final Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(CommonFunctions.FROM_SETTINGS, true);
            startActivity(intent);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
            System.exit(0);
        }

    }


}
