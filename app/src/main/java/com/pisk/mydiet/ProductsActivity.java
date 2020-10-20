package com.pisk.mydiet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    int programNumber;
    final String SAVED_PROGRAM = "saved_program";
    SharedPreferences sPref;
    TextView title;
    String lLightColor = "";
    String lColor = "";
    String lName = "";
    int countDays = 0;

    View hView;
    ImageView menuImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
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
        navigationView.getMenu().getItem(1).setChecked(true);

        hView =  navigationView.getHeaderView(0);
        menuImage = hView.findViewById(R.id.imageViewHead);

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
        programNumber = sPref.getInt(SAVED_PROGRAM, 0);

        listView = (ListView) findViewById(R.id.listViewWeeks);
        title = findViewById(R.id.title1);

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        ProgramInfo prInfo = CommonFunctions.getProgramInfoFromDatabase(dbHelper,programNumber);

        lName = prInfo.lName;
        String lImage = prInfo.lImage;
        lColor = prInfo.lColor;
        final String lColorDay = prInfo.lColorDay;
        lLightColor = prInfo.lLightColor;
        countDays = prInfo.countDays;

        int countWeeks = (countDays%7 > 0) ? countDays/7 + 1 : countDays/7;

        final String[] weeks = new String[countWeeks];

        for (int i = 0; i < weeks.length; i++) {
            weeks[i] = "Неделя " + (i+1);
        }

        hView.getBackground().setColorFilter(Color.parseColor(lColor),android.graphics.PorterDuff.Mode.SRC_IN);

        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.day, weeks) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView mytextview=(TextView)view;

                mytextview.setBackgroundResource(R.drawable.custom_shape);
                if (lColor != "") {
                    GradientDrawable drawable = (GradientDrawable) mytextview.getBackground();
                    drawable.setColor(Color.parseColor(lColor));
                }

                title.setText("Список продуктов для программы " + lName);

                return view;
            }
        };

        listView.setDivider(null);
//        listView.setHeaderDividersEnabled(true);
        listView.setAdapter(adapter);

        //final Intent intent = new Intent(this, ProductsItemActivity.class);
        final Intent intent = new Intent(this, PagerProductsActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

                intent.putExtra("arg_week_number", (position + 1));
                intent.putExtra("arg_color", lColor);
                intent.putExtra("arg_light_color", lLightColor);
                //intent.putExtra("arg_program_number", programNumber);
                startActivity(intent);
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


        }
//        else if (id == R.id.nav_slideshow) {
//
//            Intent intent2 = new Intent(this, ProgramsActivity.class);
//            startActivity(intent2);
//
//        }
        else if (id == R.id.nav_manage) {
            Intent intent2 = new Intent(this, MyPageActivity.class);
            startActivity(intent2);

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
