package com.pisk.mydiet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    SharedPreferences sPref;
    final String SAVED_PROGRAM = "saved_program";
    final String DATE_START = "date_start";

    private TextView titleView, tvProgressHorizontal;
    private ListView listView;
    ProgressBar pBar;
    int savedProg;
    String savedDate;
    int daysGone =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        titleView = (TextView) findViewById(R.id.title);
        listView = (ListView) findViewById(R.id.daylist2);
        pBar = findViewById(R.id.pb_horizontal);
        tvProgressHorizontal = findViewById(R.id.tv_progress_horizontal);

        Log.d("myLogs2", "jj: " + savedProg);



    }

    @Override
    protected void onResume() {
        super.onResume();

        final Intent intent = new Intent(this, SettingsActivity.class);
        final Intent intent2 = new Intent(this, ProgramsActivity.class);

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
        savedProg = sPref.getInt(SAVED_PROGRAM, 0);
        savedDate = sPref.getString(DATE_START,"1/1/2019");
        //Log.d("myLogs2", "days left: " + savedDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = sdf.parse(savedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();
        long today = System.currentTimeMillis();
        int dayLefttoStart = (int) Math.ceil((double)(millis - today)/ (24 * 60 * 60 * 1000));
        int progress = 0;

        if (dayLefttoStart < 0) {
            progress = -(int)(((double)dayLefttoStart*100)/(double)28) ;
        }

        Log.d("myLogs2", "days left: " + dayLefttoStart);

        if (savedProg == 0) {  //change to ==0
            //intent.putExtra("arg_program_number", 0);
            startActivity(intent);

        } else {
            if (savedProg == 1) {
                pBar.getProgressDrawable().setColorFilter(
                        getResources().getColor(R.color.colorSuperFitDay),
                        android.graphics.PorterDuff.Mode.SRC_IN);
                titleView.setBackgroundResource(R.drawable.custom_shape1);
                titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.superfit, 0);
            } else if (savedProg == 2) {
                pBar.getProgressDrawable().setColorFilter(
                        getResources().getColor(R.color.colorFitDay),
                        android.graphics.PorterDuff.Mode.SRC_IN);
                titleView.setBackgroundResource(R.drawable.custom_shape2);
                titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.fit, 0);
            } else if (savedProg == 3) {
                pBar.getProgressDrawable().setColorFilter(
                        getResources().getColor(R.color.colorBalanceDay),
                        android.graphics.PorterDuff.Mode.SRC_IN);
                titleView.setBackgroundResource(R.drawable.custom_shape3);
                titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.balance, 0);
            } else {
                pBar.getProgressDrawable().setColorFilter(
                        getResources().getColor(R.color.colorStrongDay),
                        android.graphics.PorterDuff.Mode.SRC_IN);
                titleView.setBackgroundResource(R.drawable.custom_shape4);
                titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.strong, 0);
            }

            titleView.setText(getResources().getStringArray(R.array.programms)[savedProg - 1]);
            pBar.setProgress(progress);
            tvProgressHorizontal.setText(progress + "%");

            final String[] days = new String[28];

            daysGone = -dayLefttoStart;
            Log.d("myLogs2", "days gone: " + daysGone);

            long localDate = millis;
            for (int i = 0; i < days.length; i++) {

                Date dateStart = new Date(localDate);
                DateFormat df = new SimpleDateFormat("dd.MM.yy");
                String firstDate = df.format(dateStart);
                days[i] = "День " + (i+1) + " (" + firstDate + ")";
                Log.d("myLogs2",days[i]);
                localDate += (24 * 60 * 60 * 1000);
            }

            final ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(this, R.layout.day, days) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    TextView mytextview=(TextView)view;

                    if (savedProg == 1) {

                        mytextview.setBackgroundResource(R.drawable.dcustom_shape1);


                    } else if (savedProg == 2) {

                        mytextview.setBackgroundResource(R.drawable.dcustom_shape2);

                    } else if (savedProg == 3) {

                        mytextview.setBackgroundResource(R.drawable.dcustom_shape3);

                    } else {

                        mytextview.setBackgroundResource(R.drawable.dcustom_shape4);

                    }

                    //Object obj = listView.getItemAtPosition();
                   // if (position < daysGone) {
                   //     mytextview.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.strong, 0);
                   // }

                    Log.d("myLogs2","position: " +position);

                    return view;
                }
            };

            listView.setDivider(null);
//        listView.setHeaderDividersEnabled(true);
            listView.setAdapter(adapter);

            for(int j=0; j<daysGone; j++) {
                TextView v = (TextView) getViewByPosition(j,listView);
                v.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.strong, 0);

            }


        }
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
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
