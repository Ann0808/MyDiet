package com.pisk.mydiet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ProgramsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayAdapter<String> mAdapter;
    private ListView listView;
    private int countProgramms = 4;
    static final String TAG = "myLogs";

    final String SAVED_PROGRAM = "saved_program";
    SharedPreferences sPref;
    int programNumber;

    View hView;
    ImageView menuImage;

    final String HINT_PROGRAMS = "hint_programs";
    boolean hintPrograms = false;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_programs);

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
        navigationView.getMenu().getItem(2).setChecked(true);

        hView =  navigationView.getHeaderView(0);
        menuImage = hView.findViewById(R.id.imageViewHead);

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);

        hintPrograms = sPref.getBoolean(HINT_PROGRAMS,false);

        if (!hintPrograms) {

            LayoutInflater inflater = this.getLayoutInflater();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(inflater.inflate(R.layout.dialog_programs, null));
            builder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putBoolean(HINT_PROGRAMS,true);
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
        dbHelper.create_db();
        db = dbHelper.open();
        String table = "programs as R ";
        String columns[] = { "R.id as id, R.name as name", "R.image as image", "R.color as color", "R.lightColor as lightColor"};
        cursor = db.query(table, columns, null, null, null, null, null);

        LinearLayout layPrograms = findViewById(R.id.layPrograms);

        final Intent intent = new Intent(this, DaysListActivity.class);
        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nameColIndex = cursor.getColumnIndex(dbHelper.PrNAME);
                int imageColIndex = cursor.getColumnIndex(dbHelper.PrIMAGE);
                int colorColIndex = cursor.getColumnIndex(dbHelper.PrCOLOR);
                int idColIndex = cursor.getColumnIndex(dbHelper.Prid);
                int idLightColor = cursor.getColumnIndex(dbHelper.PrlightColor);

                do {
                    String lName = cursor.getString(nameColIndex);
                    String lImage = cursor.getString(imageColIndex);
                    String lColor = cursor.getString(colorColIndex);
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

                    layPrograms.addView(buttonProgram);


                    buttonProgram.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(buttonClick);
                            intent.putExtra("arg_program_number", lProgramNumber);
                            startActivity(intent);
                        }});

                }while (cursor.moveToNext());
            }
        }

        programNumber = sPref.getInt(SAVED_PROGRAM, 0);
        ProgramInfo prInfo = CommonFunctions.getProgramInfoFromDatabase(dbHelper,programNumber);
        String lColor = prInfo.lColor;
        hView.getBackground().setColorFilter(Color.parseColor(lColor),android.graphics.PorterDuff.Mode.SRC_IN);

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
//
//        }
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
