package com.pisk.mydiet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class LovingRecipesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout layCategories;
    String lColor = "";

    View hView;
    DatabaseHelper dbHelper;
    SharedPreferences sPref;
    int savedProg;
    boolean mySex = true;
    int myWeight, myHeight, myAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loving_recipes);

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

        sPref = getSharedPreferences(getResources().getString(R.string.sharedPref),0);
        savedProg = sPref.getInt(CommonFunctions.SAVED_PROGRAM, 0);
        mySex = sPref.getBoolean(CommonFunctions.MY_SEX, true);
        myWeight = sPref.getInt(CommonFunctions.MY_WEIGHT, 0);
        myHeight = sPref.getInt(CommonFunctions.MY_HEIGHT, 0);
        myAge = sPref.getInt(CommonFunctions.MY_AGE, 0);
        dbHelper = new DatabaseHelper(getApplicationContext());
        ProgramInfo prInfo = CommonFunctions.getProgramInfoFromDatabase(dbHelper,savedProg);
        lColor = prInfo.lColor;
        hView =  navigationView.getHeaderView(0);
        hView.getBackground().setColorFilter(Color.parseColor(lColor),android.graphics.PorterDuff.Mode.SRC_IN);
        layCategories = findViewById(R.id.layCategories);


        for (RecipeCategory cat : RecipeCategory.values()) {
            //Log.d("dblogs", "name : " + RecipeCategory.valueOf(cat.name()) );
//            Log.d("dblogs", "name : " + cat.categoryName());
//            Log.d("dblogs", "number : " + cat.number());
            Button buttonCategory = new Button(this);
            int buttonProgramHeight = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    getResources().getDimension(R.dimen.button_height),
                    getResources().getDisplayMetrics()
            );

            buttonCategory.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, buttonProgramHeight));
            buttonCategory.setText(cat.categoryName());
            buttonCategory.setGravity(Gravity.CENTER);
            buttonCategory.setShadowLayer(5,1,1, R.color.colorPrimaryDark);
            buttonCategory.setTextColor(getResources().getColor(R.color.colorWhite));
            buttonCategory.setTextSize(TypedValue.COMPLEX_UNIT_SP,23);
            buttonCategory.setBackgroundResource(R.drawable.custom_shape);
            GradientDrawable drawable = (GradientDrawable) buttonCategory.getBackground();
            drawable.setColor(Color.parseColor(lColor));
            buttonCategory.setCompoundDrawablePadding(-100);
            buttonCategory.setPadding(20,0,100,0);
            Drawable myDrawable = getResources().getDrawable(cat.categoryImage());
            buttonCategory.setCompoundDrawablesWithIntrinsicBounds(myDrawable, null, null , null);

            try {
                layCategories.addView(buttonCategory);
            } catch (Exception ex) {
                Log.d("dblogs", ex.getMessage());
            }

            final String categoryName = cat.categoryName();
            final int categoryNumber = cat.number();
            final int categoryImage = cat.categoryImage();
            final Intent intent = new Intent(this, LovingRecipesListActivity.class);
            buttonCategory.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {


                    intent.putExtra("category_name",categoryName);
                    intent.putExtra("category_number",categoryNumber);
                    intent.putExtra("category_image",categoryImage);
                    intent.putExtra("color",lColor);
                    startActivity(intent);

                }
            });

        }

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
        else if (id == R.id.nav_change) {
            Intent intent2 = new Intent(this, SettingsActivity.class);
            intent2.putExtra(CommonFunctions.MY_SEX, mySex);
            intent2.putExtra(CommonFunctions.MY_WEIGHT, myWeight);
            intent2.putExtra(CommonFunctions.MY_HEIGHT, myHeight);
            intent2.putExtra(CommonFunctions.MY_AGE, myAge);
            intent2.putExtra(CommonFunctions.CHOOSE_NEW, true);
            startActivity(intent2);

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

        } else if (id == R.id.nav_share) {

            Intent sendIntent = MenuClick.share();
            startActivity(Intent.createChooser(sendIntent,"Поделиться"));

        } else if (id == R.id.nav_about) {

            Intent intent2 = new Intent(this, AboutActivity.class);
            startActivity(intent2);

        }
        else if (id == R.id.nav_date_start) {
            Intent intent2 = new Intent(this, DateStartActivity.class);
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