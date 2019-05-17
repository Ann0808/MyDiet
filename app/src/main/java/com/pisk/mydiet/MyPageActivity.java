package com.pisk.mydiet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MyPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String SAVED_PROGRAM = "saved_program";
    final String DATE_START = "date_start";
    final String USER_NAME = "user_name";

    SharedPreferences sPref;

    int savedProg;
    String savedDate;
    String userName;
    EditText viewName;
    LinearLayout nameLayout, bigLayout;
    Spinner spinner;
    ImageView edit;
    Button b1;

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

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
        userName = sPref.getString(USER_NAME, "Пользователь");
        savedProg = sPref.getInt(SAVED_PROGRAM, 0);

        bigLayout = findViewById(R.id.bigLayout);
        b1 = findViewById(R.id.b1);
        spinner = findViewById(R.id.spinner);

        int colorBack;
        int colorButton;
        if (savedProg == 1) {
            colorBack = R.color.colorSuperFitLight;
            colorButton = R.drawable.custom_shape1;
        } else if (savedProg == 2) {
            colorBack = R.color.colorFitLight;
            colorButton = R.drawable.custom_shape2;
        } else if (savedProg == 3) {
            colorBack = R.color.colorBalanceLight;
            colorButton = R.drawable.custom_shape3;
        } else {
            colorBack = R.color.colorStrongLight;
            colorButton = R.drawable.custom_shape4;
        }

        bigLayout.setBackgroundResource(colorBack);
        b1.setBackgroundResource(colorButton);

        viewName = findViewById(R.id.userName);
        viewName.setText(userName);

        //editName = findViewById(R.id.editName);
        edit = findViewById(R.id.imageView3);
        nameLayout = findViewById(R.id.nameLayout);




        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) v.getLayoutParams();
                //params.width = 1;
                //v.setLayoutParams(params);
                v.setVisibility(View.INVISIBLE);

                //LinearLayout.LayoutParams paramsB = (LinearLayout.LayoutParams) b1.getLayoutParams();
                //paramsB.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                b1.setVisibility(View.VISIBLE);
                //b1.setLayoutParams(paramsB);

                viewName.setClickable(true);
                viewName.setCursorVisible(true);
                viewName.setFocusable(true);
                viewName.setFocusableInTouchMode(true);
                viewName.requestFocus();
                viewName.setSelection(viewName.getText().length());

                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                im.showSoftInput(viewName, 0);
                int color = R.color.colorWhite;
                if (savedProg == 1) {
                    color = R.color.colorSuperFit;
                } else if (savedProg == 2) {
                    color = R.color.colorFit;
                } else if (savedProg == 3) {
                    color = R.color.colorBalance;
                } else {
                    color = R.color.colorStrong;
                }
                viewName.setBackgroundResource(color);

                try {
                    // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
                    Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
                    f.setAccessible(true);
                    f.set(viewName, R.drawable.cursor);
                } catch (Exception ignored) {
                }



            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) v.getLayoutParams();
                //params.width = 1;
                //v.setLayoutParams(params);
                v.setVisibility(View.INVISIBLE);

                //LinearLayout.LayoutParams paramsV = (LinearLayout.LayoutParams) edit.getLayoutParams();
                //paramsV.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                edit.setVisibility(View.VISIBLE);
                //edit.setLayoutParams(paramsV);

                viewName.setClickable(false);
                viewName.setCursorVisible(false);
                viewName.setFocusable(false);
                viewName.setFocusableInTouchMode(false);
                viewName.setBackgroundResource(0);

               // viewName.requestFocus();

                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(viewName.getWindowToken(), 0);

                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(USER_NAME,viewName.getText().toString());
                ed.commit();


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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
