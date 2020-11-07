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
import android.widget.CompoundButton;
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

import static com.pisk.mydiet.CommonFunctions.MY_SEX;

public class MyPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    SharedPreferences sPref;

    int savedProg;
    String lColor = "";
    //EditText viewName;
    LinearLayout  bigLayout, lay2;
    Button editWeightButton, saveWeightButton, editHeightButton, saveHeightButton, editAgeButton, saveAgeButton, buttonSave;
    EditText myWeight, myHeight, myAge;
    RadioGroup radioSex;

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
        savedProg = sPref.getInt(CommonFunctions.SAVED_PROGRAM, 0);
        int userWeight = sPref.getInt(CommonFunctions.MY_WEIGHT, 0);
        int userHeight = sPref.getInt(CommonFunctions.MY_HEIGHT, 0);
        int userAge = sPref.getInt(CommonFunctions.MY_AGE, 0);
        boolean mySex = sPref.getBoolean(MY_SEX, true);

        if (mySex == false) {
            RadioButton b = (RadioButton) findViewById(R.id.radio_male);
            b.setChecked(true);
        } else {
            RadioButton b = (RadioButton) findViewById(R.id.radio_female);
            b.setChecked(true);
        }

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        ProgramInfo prInfo = CommonFunctions.getProgramInfoFromDatabase(dbHelper,savedProg);
        lColor = prInfo.lColor;
        hView.setBackgroundColor(Color.parseColor(prInfo.lColor));

        bigLayout = findViewById(R.id.bigLayout);
        lay2 = findViewById(R.id.lay2);

        editWeightButton = findViewById(R.id.editWeightButton);
        saveWeightButton = findViewById(R.id.saveWeightButton);
        editHeightButton = findViewById(R.id.editHeightButton);
        saveHeightButton = findViewById(R.id.saveHeightButton);
        editAgeButton = findViewById(R.id.editAgeButton);
        saveAgeButton = findViewById(R.id.saveAgeButton);
        myWeight = findViewById(R.id.myWeight);
        myHeight = findViewById(R.id.myHeight);
        myAge = findViewById(R.id.myAge);
        radioSex = findViewById(R.id.radioGroupSex);
        buttonSave = findViewById(R.id.buttonSave);

        GradientDrawable drawable = (GradientDrawable) buttonSave.getBackground();
        drawable.setColor(Color.parseColor(lColor));

        radioSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                boolean isWoman;

                switch(checkedId){
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
                Toast.makeText(getApplicationContext(),"Данные о поле обновлены",Toast.LENGTH_SHORT).show();

            }
        });

        myWeight.setText(Integer.toString(userWeight));
        myHeight.setText(Integer.toString(userHeight));
        myAge.setText(Integer.toString(userAge));

        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myWeight.setClickable(false);
                myWeight.setCursorVisible(false);
                myWeight.setFocusable(false);
                myWeight.setFocusableInTouchMode(false);
                myWeight.setSelection(myWeight.getText().length());
                myWeight.setBackgroundResource(0);
                editWeightButton.setVisibility(View.VISIBLE);

                myHeight.setClickable(false);
                myHeight.setCursorVisible(false);
                myHeight.setFocusable(false);
                myHeight.setFocusableInTouchMode(false);
                myHeight.setSelection(myHeight.getText().length());
                myHeight.setBackgroundResource(0);
                editHeightButton.setVisibility(View.VISIBLE);

                myAge.setClickable(false);
                myAge.setCursorVisible(false);
                myAge.setFocusable(false);
                myAge.setFocusableInTouchMode(false);
                myAge.setSelection(myAge.getText().length());
                myAge.setBackgroundResource(0);
                editAgeButton.setVisibility(View.VISIBLE);

                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt(CommonFunctions.MY_HEIGHT, Integer.parseInt(myHeight.getText().toString()));
                ed.putInt(CommonFunctions.MY_WEIGHT, Integer.parseInt(myWeight.getText().toString()));
                ed.putInt(CommonFunctions.MY_AGE, Integer.parseInt(myAge.getText().toString()));
                ed.commit();

                buttonSave.setVisibility(View.INVISIBLE);

                Toast.makeText(getApplicationContext(),"Данные обновлены",Toast.LENGTH_SHORT).show();

            }
        });

        editHeightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myHeight.setClickable(true);
                myHeight.setCursorVisible(true);
                myHeight.setFocusable(true);
                myHeight.setFocusableInTouchMode(true);
                myHeight.requestFocus();
                myHeight.setSelection(myHeight.getText().length());
                myHeight.setBackgroundColor(Color.parseColor(lColor));
                editHeightButton.setVisibility(View.INVISIBLE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) editHeightButton.getLayoutParams();
                params.width = 0;
                editHeightButton.setLayoutParams(params);
                saveHeightButton.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.VISIBLE);
            }
        });

        saveHeightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myHeight.setClickable(false);
                myHeight.setCursorVisible(false);
                myHeight.setFocusable(false);
                myHeight.setFocusableInTouchMode(false);
                myHeight.setSelection(myHeight.getText().length());
                myHeight.setBackgroundResource(0);
                saveHeightButton.setVisibility(View.INVISIBLE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) editHeightButton.getLayoutParams();
                final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (24 * scale + 0.5f);
                params.width = pixels;
                editHeightButton.setLayoutParams(params);
                editHeightButton.setVisibility(View.VISIBLE);

                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt(CommonFunctions.MY_HEIGHT, Integer.parseInt(myHeight.getText().toString()));
                ed.commit();

                Toast.makeText(getApplicationContext(),"Данные о росте обновлены",Toast.LENGTH_SHORT).show();
            }
        });

        editWeightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myWeight.setClickable(true);
                myWeight.setCursorVisible(true);
                myWeight.setFocusable(true);
                myWeight.setFocusableInTouchMode(true);
                myWeight.requestFocus();
                myWeight.setSelection(myWeight.getText().length());
                myWeight.setBackgroundColor(Color.parseColor(lColor));
                editWeightButton.setVisibility(View.INVISIBLE);
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) editWeightButton.getLayoutParams();
//                params.width = 0;
//                editWeightButton.setLayoutParams(params);
//                saveWeightButton.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.VISIBLE);
            }
        });

        saveWeightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myWeight.setClickable(false);
                myWeight.setCursorVisible(false);
                myWeight.setFocusable(false);
                myWeight.setFocusableInTouchMode(false);
                myWeight.setSelection(myWeight.getText().length());
                myWeight.setBackgroundResource(0);
                saveWeightButton.setVisibility(View.INVISIBLE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) editWeightButton.getLayoutParams();
                final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (24 * scale + 0.5f);
                params.width = pixels;
                editWeightButton.setLayoutParams(params);
                editWeightButton.setVisibility(View.VISIBLE);

                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt(CommonFunctions.MY_WEIGHT, Integer.parseInt(myWeight.getText().toString()));
                ed.commit();

                Toast.makeText(getApplicationContext(),"Данные о весе обновлены",Toast.LENGTH_SHORT).show();
            }
        });

        editAgeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myAge.setClickable(true);
                myAge.setCursorVisible(true);
                myAge.setFocusable(true);
                myAge.setFocusableInTouchMode(true);
                myAge.requestFocus();
                myAge.setSelection(myAge.getText().length());
                myAge.setBackgroundColor(Color.parseColor(lColor));
                editAgeButton.setVisibility(View.INVISIBLE);
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) editAgeButton.getLayoutParams();
//                params.width = 0;
//                editAgeButton.setLayoutParams(params);
//                saveAgeButton.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.VISIBLE);
            }
        });

        saveAgeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myAge.setClickable(false);
                myAge.setCursorVisible(false);
                myAge.setFocusable(false);
                myAge.setFocusableInTouchMode(false);
                myAge.setSelection(myAge.getText().length());
                myAge.setBackgroundResource(0);
                saveAgeButton.setVisibility(View.INVISIBLE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) editAgeButton.getLayoutParams();
                final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (24 * scale + 0.5f);
                params.width = pixels;
                editAgeButton.setLayoutParams(params);
                editAgeButton.setVisibility(View.VISIBLE);

                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt(CommonFunctions.MY_AGE, Integer.parseInt(myAge.getText().toString()));
                ed.commit();

                Toast.makeText(getApplicationContext(),"Данные о возрасте обновлены",Toast.LENGTH_SHORT).show();
            }
        });


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
        else if (id == R.id.nav_date_start) {
            Intent intent2 = new Intent(this, DateStartActivity.class);
            startActivity(intent2);

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
