package com.pisk.mydiet;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class LovingRecipesListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String lColor = "";
    int categoryNumber;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    LinearLayout layRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loving_recipes_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("К списку категорий");

        Intent intentTmp = getIntent();

        String nameCategory = intentTmp.getStringExtra("category_name");
        int categoryImage = intentTmp.getIntExtra("category_image",0);
        categoryNumber = intentTmp.getIntExtra("category_number",0);
        TextView titleView = (TextView) findViewById(R.id.titleCategory);
        titleView.setText(nameCategory);
        layRecipes = findViewById(R.id.layRecipes);

        try {
            Drawable myDrawable = getResources().getDrawable(categoryImage);
            titleView.setCompoundDrawablesWithIntrinsicBounds(myDrawable, null, null, null);
            titleView.setCompoundDrawablePadding(-100);
            titleView.setPadding(20,0,100,0);
        } catch (Exception ex) {

        }

        dbHelper = new DatabaseHelper(getApplicationContext());

        dbHelper.create_db();
        db = dbHelper.open();


        String table = "loving_recipes as R";

        String columns[] = { "R.name as name", "R.id as id"};
        String WHERE = dbHelper.CATEGORY_RECIPE + "='" + categoryNumber + "'";

        try {
            cursor = db.query(table, columns, WHERE, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    int nameColIndex = cursor.getColumnIndex(dbHelper.NAME);
                    int idColIndex = cursor.getColumnIndex(dbHelper.Prid);

                    do {

                        final String image;
                        String name;
                        int id;

                        name = cursor.getString(nameColIndex);
                        id = cursor.getInt(idColIndex);



                        final Button buttonCategory = new Button(this);
                        int buttonProgramHeight = (int) TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                getResources().getDimension(R.dimen.button_height),
                                getResources().getDisplayMetrics()
                        );

                        buttonCategory.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, buttonProgramHeight));
                        buttonCategory.setText(name);

                        buttonCategory.setGravity(Gravity.CENTER);
                        buttonCategory.setShadowLayer(5,1,1, R.color.colorPrimaryDark);
                        buttonCategory.setTextColor(getResources().getColor(R.color.colorWhite));
                        buttonCategory.setTextSize(TypedValue.COMPLEX_UNIT_SP,23);
                        buttonCategory.setBackgroundResource(R.drawable.custom_shape);


                        try {
                            layRecipes.addView(buttonCategory);
                        } catch (Exception ex) {
                            Log.d("dblogs", ex.getMessage());
                        }

                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception ex) {
            Log.d("dblogs",ex.getMessage());
        }

        dbHelper.close();

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
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        return true;
    }
}