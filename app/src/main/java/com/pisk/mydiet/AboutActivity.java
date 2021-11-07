package com.pisk.mydiet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView title;
    final String USER_NAME = "user_name";

    final String SAVED_PROGRAM = "saved_program";
    int savedProg;
    SharedPreferences sPref;
    View hView;
    ImageView menuImage;
    TextView titleView;


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

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);

        hView =  navigationView.getHeaderView(0);
        menuImage = hView.findViewById(R.id.imageViewHead);

        savedProg = sPref.getInt(SAVED_PROGRAM, 0);

        titleView = findViewById(R.id.title1);

        if (savedProg == 1) {

            hView.setBackgroundResource(R.color.colorSuperFit);

            titleView.setBackgroundResource(R.drawable.custom_shape1);
            titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.superfit, 0);
            hView.setBackgroundResource(R.color.colorSuperFit);

        } else if (savedProg == 2) {

            hView.setBackgroundResource(R.color.colorFit);

            titleView.setBackgroundResource(R.drawable.custom_shape2);
            titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.fit, 0);
            hView.setBackgroundResource(R.color.colorFit);

        } else if (savedProg == 3) {

            hView.setBackgroundResource(R.color.colorBalance);

            titleView.setBackgroundResource(R.drawable.custom_shape3);
            titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.balance, 0);
            hView.setBackgroundResource(R.color.colorBalance);

        } else {

            hView.setBackgroundResource(R.color.colorStrong);

            titleView.setBackgroundResource(R.drawable.custom_shape4);
            titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.strong, 0);
            hView.setBackgroundResource(R.color.colorStrong);

        }

        titleView.setText( getResources().getString(R.string.app_name) );
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

            Intent sendIntent = MenuClick.share();
            startActivity(Intent.createChooser(sendIntent,"Поделиться"));


        } else if (id == R.id.nav_about) {


        } else if (id == R.id.nav_send) {
            try {
                Intent i = MenuClick.send();
                this.startActivity(i);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Ошибка. Установите приложение Telegram.", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_test) {

            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/hF9NMTY7ZvBNMAhP9"));
                startActivity(browserIntent);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Произошла ошибка.", Toast.LENGTH_LONG).show();
            }

            }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
