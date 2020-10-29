package com.pisk.mydiet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
//import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.net.Uri;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
//import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    SharedPreferences sPref;

    View hView;
    ImageView menuImage;
    private TextView titleView, tvProgressHorizontal;
    private ListView listView;
    Button b1, b2;
    ProgressBar pBar;
    int savedProg, myWeight, myHeight, myAge;

    String savedDate;
    int daysGone =0;
    int countDays = 0;
    boolean hintDays = false, mySex = true;
    String lLightColor = "";
    String lColor = "";
    int pageCount = 5;

    boolean fromSettings = false;

    DatabaseHelper dbHelper;


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

        hView =  navigationView.getHeaderView(0);
        menuImage = hView.findViewById(R.id.imageViewHead);

        //menuImage.setImageResource(R.drawable.balance);
        titleView = (TextView) findViewById(R.id.title);
        listView = (ListView) findViewById(R.id.daylist2);
        pBar = findViewById(R.id.pb_horizontal);
        tvProgressHorizontal = findViewById(R.id.tv_progress_horizontal);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);

        Intent intentTmp = getIntent();
        fromSettings = intentTmp.getBooleanExtra("from_settings",false);

    }


    @SuppressLint("ResourceType")
    @Override
    protected void onResume() {

        super.onResume();

        final Intent intent = new Intent(this, SettingsActivity.class);

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
        savedProg = sPref.getInt(CommonFunctions.SAVED_PROGRAM, 0);
        savedDate = sPref.getString(CommonFunctions.DATE_START,"");
        hintDays = sPref.getBoolean(CommonFunctions.HINT_DAYS,false);

        mySex = sPref.getBoolean(CommonFunctions.MY_SEX, true);
        myWeight = sPref.getInt(CommonFunctions.MY_WEIGHT, 0);
        myHeight = sPref.getInt(CommonFunctions.MY_HEIGHT, 0);
        myAge = sPref.getInt(CommonFunctions.MY_AGE, 0);


        if (savedProg == 0) {  //change to ==0
            //intent.putExtra("arg_program_number", 0);
            startActivity(intent);

        } else {

            if (!hintDays) {

                LayoutInflater inflater = this.getLayoutInflater();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(inflater.inflate(R.layout.dialog_days, null));
                builder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putBoolean(CommonFunctions.HINT_DAYS,true);
                        ed.commit();

                    }
                });

                final AlertDialog alert = builder.create();

                alert.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                });

                alert.show();

            }

            dbHelper = new DatabaseHelper(getApplicationContext());
            ProgramInfo prInfo = CommonFunctions.getProgramInfoFromDatabase(dbHelper,savedProg);

            String lName = prInfo.lName;
            String lImage = prInfo.lImage;
            lColor = prInfo.lColor;
            final String lColorDay = prInfo.lColorDay;
            lLightColor = prInfo.lLightColor;
            countDays = prInfo.countDays;
            pageCount = prInfo.countMeal;

            pBar.getProgressDrawable().setColorFilter(Color.parseColor(lColorDay), android.graphics.PorterDuff.Mode.SRC_IN);
            titleView.setText(lName);
            titleView.setBackgroundResource(R.drawable.custom_shape);
            GradientDrawable drawable = (GradientDrawable) titleView.getBackground();
            drawable.setColor(Color.parseColor(lColor));
            titleView.setTypeface(titleView.getTypeface(), Typeface.BOLD);
            Drawable limg = CommonFunctions.decodeDrawable(getApplicationContext(),lImage);
            Bitmap bitmap = ((BitmapDrawable) limg).getBitmap();
            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 170, 300, true));
            titleView.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
            hView.getBackground().setColorFilter(Color.parseColor(lColor),android.graphics.PorterDuff.Mode.SRC_IN);

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

            pBar.setProgress(progress);
            tvProgressHorizontal.setText(progress + "%");

            final String[] days = new String[countDays];

            daysGone = -dayLefttoStart;
            Log.d("myLogs2", "days gone: " + daysGone);

            long localDate = millis;
            for (int i = 0; i < days.length; i++) {

                Date dateStart = new Date(localDate);
                DateFormat df = new SimpleDateFormat("dd.MM.yy");
                String firstDate = df.format(dateStart);
                days[i] = "День " + (i+1) + " (" + firstDate + ")";
                localDate += (24 * 60 * 60 * 1000);
            }

            final ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(this, R.layout.day, days) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    TextView mytextview=(TextView)view;

                    if (position < daysGone) {
                        mytextview.setText(mytextview.getText() +"\n(Пройден ✓)");
                    }

                    mytextview.setBackgroundResource(R.drawable.custom_shape);

                    if (lLightColor != "") {
                        GradientDrawable drawable = (GradientDrawable) mytextview.getBackground();
                        drawable.setColor(Color.parseColor(lColorDay));
                    }

                    return view;
                }
            };



            listView.setDivider(null);
            listView.setAdapter(adapter);
            listView.setSelection(daysGone); //scroll to current day

            LinearLayout linearLayout = findViewById(R.id.linearLayout);
            if (progress == 100) {
                linearLayout.removeView(listView);
                TextView finished = findViewById(R.id.finished);
                finished.setVisibility(View.VISIBLE);
                LinearLayout buttonsLayout = findViewById(R.id.buttonsLayout);
                buttonsLayout.setVisibility(View.VISIBLE);
                b1.setBackgroundResource(R.drawable.custom_shape);
                b2.setBackgroundResource(R.drawable.custom_shape);
                GradientDrawable draw = (GradientDrawable) b1.getBackground();
                draw.setColor(Color.parseColor(lColor));
                draw = (GradientDrawable) b2.getBackground();
                draw.setColor(Color.parseColor(lColor));

            }
            final Intent intent4 = new Intent(getApplicationContext(), SettingsActivity.class);

            b1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {

                    intent4.putExtra(CommonFunctions.START_AGAIN, true);
                    startActivity(intent4);

                }
            });

            b2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    intent4.putExtra(CommonFunctions.CHOOSE_NEW, true);
                    startActivity(intent4);

                }
            });



        }

        final Intent intent3 = new Intent(this, PagerActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

                intent3.putExtra("arg_day_number", (position + 1));
                intent3.putExtra("arg_program_number", savedProg);
                intent3.putExtra("arg_page_count", pageCount);
                intent3.putExtra("arg_color", lColor);
                intent3.putExtra("arg_light_color", lLightColor);
                intent3.putExtra("arg_date", listView.getItemAtPosition(position).toString());
                startActivity(intent3);
            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if ((drawer.isDrawerOpen(GravityCompat.START))||(fromSettings)) {
            drawer.closeDrawer(GravityCompat.START);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
            System.exit(0);
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


        }
//        else if (id == R.id.nav_slideshow) {
//
//            Intent intent2 = new Intent(this, ProgramsActivity.class);
//            startActivity(intent2);
//
//        }
        else if (id == R.id.nav_change) {
            Intent intent2 = new Intent(this, SettingsActivity.class);
            intent2.putExtra(CommonFunctions.MY_SEX, mySex);
            intent2.putExtra(CommonFunctions.MY_WEIGHT, myWeight);
            intent2.putExtra(CommonFunctions.MY_HEIGHT, myHeight);
            intent2.putExtra(CommonFunctions.MY_AGE, myAge);
            intent2.putExtra(CommonFunctions.CHOOSE_NEW, true);
            startActivity(intent2);

        }
        else if (id == R.id.nav_manage) {
            Intent intent2 = new Intent(this, MyPageActivity.class);
            startActivity(intent2);

        } else if (id == R.id.nav_share) {

            Intent sendIntent = MenuClick.share();
            startActivity(Intent.createChooser(sendIntent,"Поделиться"));

        } else if (id == R.id.nav_about) {

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