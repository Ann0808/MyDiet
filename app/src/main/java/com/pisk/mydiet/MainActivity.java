package com.pisk.mydiet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.ShareActionProvider;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    SharedPreferences sPref;
    final String SAVED_PROGRAM = "saved_program";
    final String DATE_START = "date_start";

    private TextView titleView, tvProgressHorizontal;
    private ListView listView;
    Button b1, b2;
    ProgressBar pBar;
    int savedProg;
    String savedDate;
    int daysGone =0;

    private ShareActionProvider mShareActionProvider;

//    private AlarmManager manager;
//    private PendingIntent pendingIntent;

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
        navigationView.getMenu().getItem(0).setChecked(true);

//        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);


        titleView = (TextView) findViewById(R.id.title);
        listView = (ListView) findViewById(R.id.daylist2);
        pBar = findViewById(R.id.pb_horizontal);
        tvProgressHorizontal = findViewById(R.id.tv_progress_horizontal);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);


        Log.d("myLogs2", "jj: " + savedProg);



        // on resume

    }



    @Override
    protected void onResume() {

        super.onResume();

        final Intent intent = new Intent(this, SettingsActivity.class);

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
        savedProg = sPref.getInt(SAVED_PROGRAM, 0);
        savedDate = sPref.getString(DATE_START,"1/1/2019");
        //Log.d("myLogs2", "days left: " + savedDate);


        //Log.d("myLogs2", "days left: " + dayLefttoStart);

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
                if (progress > 100) {
                    progress = 100;
                }
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
                //Log.d("myLogs2",days[i]);
                localDate += (24 * 60 * 60 * 1000);
            }

            final ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(this, R.layout.day, days) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    TextView mytextview=(TextView)view;

                    Log.d("myLogs2", "text: " + mytextview.getText());

                    if (position < daysGone) {
                        mytextview.setText(mytextview.getText() +" (Пройден ✓)");
                    }

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

                    //Log.d("myLogs2","fgjhj: " + v.getText());

                    return view;
                }
            };



            listView.setDivider(null);
//        listView.setHeaderDividersEnabled(true);

            listView.setAdapter(adapter);

            listView.setSelection(daysGone); //scroll to current day


            //TextView v = (TextView) listView.getChildAt(0);
            //Log.d("myLogs2", "posittttt: " + v.getText());
//            for(int j=0; j<5; j++) {
//                TextView v = (TextView) getViewByPosition(j,listView);
//                //v.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.strong, 0);
//                v.setText("kkk");
//                Log.d("myLogs2","textview: " +v.getText());
//            }


            LinearLayout linearLayout = findViewById(R.id.linearLayout);
            if (progress == 100) {
                linearLayout.removeView(listView);
                TextView finished = findViewById(R.id.finished);
                finished.setVisibility(View.VISIBLE);
                LinearLayout buttonsLayout = findViewById(R.id.buttonsLayout);
                buttonsLayout.setVisibility(View.VISIBLE);

                if (savedProg == 1) {
                    b1.setBackgroundResource(R.drawable.custom_shape1);
                    b2.setBackgroundResource(R.drawable.custom_shape1);
                } else if (savedProg == 2) {
                    b1.setBackgroundResource(R.drawable.custom_shape2);
                    b2.setBackgroundResource(R.drawable.custom_shape2);
                } else if (savedProg == 3) {
                    b1.setBackgroundResource(R.drawable.custom_shape3);
                    b2.setBackgroundResource(R.drawable.custom_shape3);
                } else {
                    b1.setBackgroundResource(R.drawable.custom_shape4);
                    b2.setBackgroundResource(R.drawable.custom_shape4);
                }

            }
            final Intent intent4 = new Intent(getApplicationContext(), SettingsActivity.class);

            b1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {

//                    manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//                    int interval = 10000; // 10 seconds

                    //manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() , interval, pendingIntent);
                    //Toast.makeText(getApplicationContext(), "Alarm Set", Toast.LENGTH_SHORT).show();

                    //intent4.putExtra("arg_program_number", savedProg);

                    intent4.putExtra("start_again", true);
                    startActivity(intent4);

                }
            });

            b2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    intent4.putExtra("choose_new", true);
                    startActivity(intent4);

                }
            });



        }

        final Intent intent3 = new Intent(this, PagerActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

//                int newPos = position;
//                if (newPos !=0 ) {
//                    newPos++;
//                }
//                b.putInt("arg_day_number", newPos);
//                intent.putExtra("bund", b);
                intent3.putExtra("arg_day_number", (position + 1));
                intent3.putExtra("arg_program_number", savedProg);
                intent3.putExtra("arg_date", listView.getItemAtPosition(position).toString());
                //Log.d("myLogs2", "clicked date " + listView.getItemAtPosition(position).toString());
                startActivity(intent3);
            }
        });


    }

//    public View getViewByPosition(int pos, ListView listView) {
//        final int firstListItemPosition = listView.getFirstVisiblePosition();
//        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;
//
//        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
//            return listView.getAdapter().getView(pos, null, listView);
//        } else {
//            final int childIndex = pos - firstListItemPosition;
//            return listView.getChildAt(childIndex);
//        }
//    }

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

        } else if (id == R.id.nav_gallery) {
            Intent intent2 = new Intent(this, ProductsActivity.class);
            startActivity(intent2);


        } else if (id == R.id.nav_slideshow) {

            Intent intent2 = new Intent(this, ProgramsActivity.class);
            startActivity(intent2);

        } else if (id == R.id.nav_manage) {
            Intent intent2 = new Intent(this, MyPageActivity.class);
            startActivity(intent2);

        } else if (id == R.id.nav_share) {

            //Intent shareIntent = new Intent(Intent.ACTION_SEND);;

            // Now update the ShareActionProvider with the new share intent
            //mShareActionProvider.setShareIntent(shareIntent);

            ArrayList<Uri> uris = new ArrayList<>();
            Uri path = Uri.parse("android.resource://com.pisk.mydiet/" + R.drawable.food);
            Uri path2 = Uri.parse("android.resource://com.pisk.mydiet/" + R.drawable.paper);
            uris.add(path);
            uris.add(path2);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            //sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "Приложение nam");
 //           sendIntent.putExtra(Intent.EXTRA_STREAM, path);

            sendIntent.putExtra(Intent.EXTRA_TEXT, "lj;dhsgk ;sjhg "+"https://play.google.com/store/apps/details?id=se.feomedia.quizkampen.de.lite");
           // sendIntent.setType("image/*");

            sendIntent.setType("text/plain");
            //sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(sendIntent,"Поделиться"));



        } else if (id == R.id.nav_send) {

            Intent intent2 = new Intent(this, AboutActivity.class);
            startActivity(intent2);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
