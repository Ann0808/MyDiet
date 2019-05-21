package com.pisk.mydiet;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DaysListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private TextView titleView;
    //Bundle b;
    int programNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_list);

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

        Intent intentTmp = getIntent();
        //b = intentTmp.getBundleExtra("bund");
        programNumber = intentTmp.getIntExtra("arg_program_number",0);

        listView = (ListView) findViewById(R.id.daylist2);

        titleView = (TextView) findViewById(R.id.title);

        ColorDrawable sage = null;

        if (programNumber == 1) {
            sage = new ColorDrawable(this.getResources().getColor(R.color.colorSuperFit));
            titleView.setBackgroundResource(R.drawable.custom_shape1);
            titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.superfit, 0);
        } else if (programNumber == 2) {
            sage = new ColorDrawable(this.getResources().getColor(R.color.colorFit));
            titleView.setBackgroundResource(R.drawable.custom_shape2);
            titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.fit, 0);
        } else if (programNumber == 3) {
            sage = new ColorDrawable(this.getResources().getColor(R.color.colorBalance));
            titleView.setBackgroundResource(R.drawable.custom_shape3);
            titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.balance, 0);
        } else {
            sage = new ColorDrawable(this.getResources().getColor(R.color.colorStrong));
            titleView.setBackgroundResource(R.drawable.custom_shape4);
            titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.strong, 0);
        }

        titleView.setText(getResources().getStringArray(R.array.programms)[programNumber - 1]);

        final String[] days = new String[28];

        for (int i = 0; i < days.length; i++) {
            days[i] = "День " + (i+1);
        }

        final ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this, R.layout.day, days) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView mytextview=(TextView)view;

                if (programNumber == 1) {

                    mytextview.setBackgroundResource(R.drawable.dcustom_shape1);

                } else if (programNumber == 2) {

                    mytextview.setBackgroundResource(R.drawable.dcustom_shape2);

                } else if (programNumber == 3) {

                    mytextview.setBackgroundResource(R.drawable.dcustom_shape3);

                } else {

                    mytextview.setBackgroundResource(R.drawable.dcustom_shape4);

                }

                return view;
            }
        };

//        listView.setDivider(sage);
        listView.setDivider(null);
//        listView.setHeaderDividersEnabled(true);
        listView.setAdapter(adapter);

//        for (int i = 0; i < days.length; i++) {
//            TextView view = (TextView) adapter.getView(i,null,null);
//            Log.d("design", "text is: " + view.getText());
//            view.setText("hh");
//        }



        final Intent intent = new Intent(this, PagerActivity.class);

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
                intent.putExtra("arg_day_number", (position + 1));
                intent.putExtra("arg_program_number", programNumber);
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
            Intent intent2 = new Intent(this, ProductsActivity.class);
            startActivity(intent2);

        } else if (id == R.id.nav_slideshow) {

            Intent intent2 = new Intent(this, ProgramsActivity.class);
            startActivity(intent2);

        } else if (id == R.id.nav_manage) {
            Intent intent2 = new Intent(this, MyPageActivity.class);
            startActivity(intent2);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
