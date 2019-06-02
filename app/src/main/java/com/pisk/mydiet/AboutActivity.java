package com.pisk.mydiet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.TextView;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView title;
    final String USER_NAME = "user_name";

    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
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
        navigationView.getMenu().getItem(4).setChecked(true);

        title = findViewById(R.id.title1);
        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
        String userName = sPref.getString(USER_NAME, "Пользователь");
        title.setText(userName + "! \n Добро пожаловать в " + getResources().getString(R.string.app_name) + "!");
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

            //Intent shareIntent = new Intent(Intent.ACTION_SEND);;

            // Now update the ShareActionProvider with the new share intent
            //mShareActionProvider.setShareIntent(shareIntent);

//            ArrayList<Uri> uris = new ArrayList<>();
//            Uri path = Uri.parse("android.resource://com.pisk.mydiet/" + R.drawable.food);
//            Uri path2 = Uri.parse("android.resource://com.pisk.mydiet/" + R.drawable.paper);
//            uris.add(path);
//            uris.add(path2);
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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
