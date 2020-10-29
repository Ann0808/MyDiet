package com.pisk.mydiet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.util.Log;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    SharedPreferences sPref;

    int savedProg;
    String currentDate = "";
    String lColor = "";
    //EditText viewName;
    LinearLayout nameLayout, bigLayout, dateLay, lay2;
    Button bSave;
    CalendarView mCalendarView;
    TextView date;
    Button edit;

    View hView;
    ImageView menuImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(3).setChecked(true);

        hView =  navigationView.getHeaderView(0);
        menuImage = hView.findViewById(R.id.imageViewHead);

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
        //userName = "";
        savedProg = sPref.getInt(CommonFunctions.SAVED_PROGRAM, 0);
        currentDate = sPref.getString(CommonFunctions.DATE_START, "");

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        ProgramInfo prInfo = CommonFunctions.getProgramInfoFromDatabase(dbHelper,savedProg);
        lColor = prInfo.lColor;


        bigLayout = findViewById(R.id.bigLayout);
        bSave = findViewById(R.id.bSave);
        edit = findViewById(R.id.edit);
        mCalendarView = findViewById(R.id.datePicker);
        date = findViewById(R.id.date);
        lay2 = findViewById(R.id.lay2);

        //GradientDrawable draw = (GradientDrawable) mCalendarView.getBackground();
        mCalendarView.setBackgroundColor(Color.parseColor(lColor));

        final String savedDate = sPref.getString(CommonFunctions.DATE_START, "");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateD = null;
        try {
            dateD = sdf.parse(savedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = dateD.getTime();

        mCalendarView.setDate (millis, true, true);
        date.setText(savedDate);


        RadioButton b;
        int colorBack;
        int colorButton;


        //program.setText(getResources().getStringArray(R.array.programms)[savedProg - 1]);

        //edit.setBackgroundResource(colorButton);
       // mCalendarView.setBackgroundResource(colorButton);


        //viewName.setText(userName);

        //nameLayout = findViewById(R.id.nameLayout);
        dateLay = findViewById(R.id.datePickerL);
        dateLay.removeView(mCalendarView);
        //radioL.removeView(radioGroup);

        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                savedProg = sPref.getInt(CommonFunctions.SAVED_PROGRAM, 0);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) date.getLayoutParams();
                params.height = 1;
                date.setLayoutParams(params);

                //programL.removeView(program);

                LinearLayout.LayoutParams paramsB = (LinearLayout.LayoutParams) v.getLayoutParams();
                paramsB.height = 1;
                v.setLayoutParams(paramsB);
                dateLay.setVisibility(View.VISIBLE);
                dateLay.addView(mCalendarView);
                //radioL.addView(radioGroup);

                bSave.setVisibility(View.VISIBLE);
                v.setVisibility(View.INVISIBLE);
                //radioGroup.setVisibility(View.VISIBLE);
                //program.setVisibility(View.INVISIBLE);

//                viewName.setClickable(true);
//                viewName.setCursorVisible(true);
//                viewName.setFocusable(true);
//                viewName.setFocusableInTouchMode(true);
//                viewName.requestFocus();
//                viewName.setSelection(viewName.getText().length());
//                for (int i = 0; i< radioGroup.getChildCount();i++) {
//                    radioGroup.getChildAt(i).setClickable(true);
//                }

//                int color = R.color.colorWhite;
//                if (savedProg == 1) {
//                    color = R.color.colorSuperFit;
//                } else if (savedProg == 2) {
//                    color = R.color.colorFit;
//                } else if (savedProg == 3) {
//                    color = R.color.colorBalance;
//                } else {
//                    color = R.color.colorStrong;
//                }
                //viewName.setBackgroundResource(color);

                try {
                    Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
                    f.setAccessible(true);
                    //f.set(viewName, R.drawable.cursor);
                } catch (Exception ignored) {
                }



            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) date.getLayoutParams();
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                date.setLayoutParams(params);

                LinearLayout.LayoutParams paramsB = (LinearLayout.LayoutParams) edit.getLayoutParams();
                final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                paramsB.height = (int) (80 * scale + 0.5f);
                edit.setLayoutParams(paramsB);

                dateLay.removeView(mCalendarView);
//                programL.addView(program);
//                program.setVisibility(View.VISIBLE);
//                radioL.removeView(radioGroup);

                //int selectedId1 = radioGroup.getCheckedRadioButtonId();
                int numbProg;

                int colorBack;
                int colorButton;

               // program.setText(getResources().getStringArray(R.array.programms)[numbProg - 1]);

                //bigLayout.setBackgroundResource(colorBack);
                //b1.setBackgroundResource(colorButton);
                //edit.setBackgroundResource(colorButton);
                //mCalendarView.setBackgroundResource(colorButton);

                v.setVisibility(View.INVISIBLE);

                edit.setVisibility(View.VISIBLE);

//                viewName.setClickable(false);
//                viewName.setCursorVisible(false);
//                viewName.setFocusable(false);
//                viewName.setFocusableInTouchMode(false);
//                viewName.setBackgroundResource(0);

                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
               // im.hideSoftInputFromWindow(viewName.getWindowToken(), 0);

                SharedPreferences.Editor ed = sPref.edit();
                //ed.putString(USER_NAME,viewName.getText().toString());
//                ed.putInt(CommonFunctions.SAVED_PROGRAM, numbProg);
//                ed.putString(CommonFunctions.DATE_START,currentDate);
//                ed.commit();

                date.setText(currentDate);

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
                    Log.d("myLogs2", "not ok");
                }
                if (millis > System.currentTimeMillis()) {
                    startAlert(millis,false);
                }

                Toast.makeText(getApplicationContext(),"Данные обновлены",Toast.LENGTH_SHORT).show();


            }
        });

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                month++;
                String strMonth = month>9 ? String.valueOf(month) : "0" + month;
                String day = dayOfMonth>9 ? String.valueOf(dayOfMonth) : "0" + dayOfMonth;
                currentDate = day + "/" + strMonth + "/" + year;
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



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {

            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);

        } else if (id == R.id.nav_gallery) {
            Intent intent2 = new Intent(this, ProductsActivity.class);
            startActivity(intent2);

        }
//        else if (id == R.id.nav_slideshow) {
//
//            Intent intent2 = new Intent(this, ProgramsActivity.class);
//            startActivity(intent2);
//
//        }
        else if (id == R.id.nav_change) {

            boolean mySex = sPref.getBoolean(CommonFunctions.MY_SEX, true);
            int myWeight = sPref.getInt(CommonFunctions.MY_WEIGHT, 0);
            int myHeight = sPref.getInt(CommonFunctions.MY_HEIGHT, 0);
            int myAge = sPref.getInt(CommonFunctions.MY_AGE, 0);

            Intent intent2 = new Intent(this, SettingsActivity.class);
            intent2.putExtra(CommonFunctions.MY_SEX, mySex);
            intent2.putExtra(CommonFunctions.MY_WEIGHT, myWeight);
            intent2.putExtra(CommonFunctions.MY_HEIGHT, myHeight);
            intent2.putExtra(CommonFunctions.MY_AGE, myAge);
            intent2.putExtra(CommonFunctions.CHOOSE_NEW, true);
            startActivity(intent2);


        }
        else if (id == R.id.nav_manage) {


        }
        else if (id == R.id.nav_share) {

            Intent sendIntent = MenuClick.share();
            startActivity(Intent.createChooser(sendIntent,"Поделиться"));

        }
        else if (id == R.id.nav_about) {

            Intent intent2 = new Intent(this, AboutActivity.class);
            startActivity(intent2);

        } else if (id == R.id.nav_send) {
            try {
                Intent i = MenuClick.send();
                this.startActivity(i);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Ошибка. Установите приложение Telegram.", Toast.LENGTH_LONG).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
