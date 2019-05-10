package com.pisk.mydiet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends Activity {

    SharedPreferences sPref;
    final String SAVED_PROGRAM = "saved_program";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    protected void onResume() {
        super.onResume();

        final Intent intent = new Intent(this, SettingsActivity.class);
        final Intent intent2 = new Intent(this, ProgramsActivity.class);

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
        int savedProg = sPref.getInt(SAVED_PROGRAM, 0);
        Log.d("myLogs2", "hh: " + savedProg);

        if (savedProg < 100) {  //change to ==0
            //intent.putExtra("arg_program_number", 0);
            startActivity(intent);


        } else {
            startActivity(intent2);
        }
    }

}
