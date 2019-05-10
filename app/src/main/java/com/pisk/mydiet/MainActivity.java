package com.pisk.mydiet;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayAdapter<String> mAdapter;
    private ListView listView;
    private int countProgramms = 4;
    static final String TAG = "myLogs";

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


 //      TextView textView = (TextView) findViewById(R.id.textview);
//        textView.measure(0, 0);
//       Log.d(TAG, "width is: " + textView.getMeasuredWidth());
//        textView.getLayoutParams().height = (int) (textView.getMeasuredWidth()*0.3);

        listView = (ListView) findViewById(R.id.listView);

        Resources res = getResources();
        final String[] programms = res.getStringArray(R.array.programms);

        final ListViewItem[] items = new ListViewItem[countProgramms];

        for (int i = 0; i < items.length; i++) {
            if (i == 0) {
                items[i] = new ListViewItem(programms[i], CustomAdapter.TYPE_SuperFit);
            } else if (i == 1) {
                items[i] = new ListViewItem(programms[i], CustomAdapter.TYPE_Fit);
            } else if (i == 2) {
                items[i] = new ListViewItem(programms[i], CustomAdapter.TYPE_Balance);
            } else {
                items[i] = new ListViewItem(programms[i], CustomAdapter.TYPE_Strong);
            }
        }

        CustomAdapter customAdapter = new CustomAdapter(this, R.id.textview, items);

//        Display display = getWindowManager().getDefaultDisplay();
//        DisplayMetrics outMetrics = new DisplayMetrics ();
 //       display.getMetrics(outMetrics);

//        float density  = getResources().getDisplayMetrics().density;
        //float dpHeight = outMetrics.heightPixels / density;
//        float dpWidth  = outMetrics.widthPixels / density;


//        View convertView = customAdapter.getView(3,null, this.listView);
//        TextView textView = convertView.findViewById(R.id.textview);
//        textView.measure(0, 0);
//        Log.d(TAG, "height is: " + textView.getMeasuredHeight());
//        Log.d(TAG, "width is: " + dpWidth);
//        textView.getLayoutParams().height = (int) (dpWidth*2);


        listView.setDivider(getResources().getDrawable(android.R.color.transparent));
        listView.setAdapter(customAdapter);


        final Intent intent = new Intent(this, DaysListActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

                Log.d(TAG, "position is: " + position);

//                int newPos = position;
//                Bundle b = new Bundle();
//                if (newPos !=0 ) {
//                    newPos++;
//                }
                //b.putInt("arg_program_number", newPos);
                //intent.putExtra("bund", b);
                intent.putExtra("arg_program_number", (position + 1));
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
        getMenuInflater().inflate(R.menu.main, menu);
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
