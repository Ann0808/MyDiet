package com.pisk.mydiet;

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

        final String[] weeks = new String[4];

        for (int i = 0; i < weeks.length; i++) {
            weeks[i] = "Неделя " + (i+1);
        }

        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.day, weeks) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView mytextview=(TextView)view;

                if (programNumber == 1) {

                    mytextview.setBackgroundResource(R.drawable.custom_shape1);
                    hView.setBackgroundResource(R.color.colorSuperFit);

                } else if (programNumber == 2) {

                    mytextview.setBackgroundResource(R.drawable.custom_shape2);
                    hView.setBackgroundResource(R.color.colorFit);

                } else if (programNumber == 3) {

                    mytextview.setBackgroundResource(R.drawable.custom_shape3);
                    hView.setBackgroundResource(R.color.colorBalance);

                } else {

                    mytextview.setBackgroundResource(R.drawable.custom_shape4);
                    hView.setBackgroundResource(R.color.colorStrong);

                }

                title.setText("Список продуктов для программы " + getResources().getStringArray(R.array.programms)[programNumber - 1]);

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


        } else if (id == R.id.nav_slideshow) {

            Intent intent2 = new Intent(this, ProgramsActivity.class);
            startActivity(intent2);

        } else if (id == R.id.nav_manage) {
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
