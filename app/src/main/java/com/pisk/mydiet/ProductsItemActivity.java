package com.pisk.mydiet;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ProductsItemActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int weekNumber;
    int programNumber;

    Map<String,String> productCount;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_item);
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
        programNumber = intentTmp.getIntExtra("arg_program_number",1);
        weekNumber = intentTmp.getIntExtra("arg_week_number",1);

        TextView title = findViewById(R.id.title5);
        title.setText("Список продуктов " + "\n"+ "на " +weekNumber+"-ю неделю");

        productCount = new HashMap<String, String>();;

        dbHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        dbHelper.create_db();
        db = dbHelper.open();

        String[] columns = new String[]{dbHelper.PRODUCT,dbHelper.COUNT};
        String WHERE = dbHelper.PROGRAM_NUMBER_PR + "='" + programNumber + "' AND " +
                dbHelper.WEEK + "='" + weekNumber + "'";
        cursor = db.query(dbHelper.TABLE_PRODUCTS, columns, WHERE, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                // определяем номера столбцов по имени в выборке
                int productColIndex = cursor.getColumnIndex(dbHelper.PRODUCT);
                int countColIndex = cursor.getColumnIndex(dbHelper.COUNT);

                do {
                    productCount.put(cursor.getString(productColIndex),cursor.getString(countColIndex));

                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {


        }

        dbHelper.close();

//        int ROWS = productCount.size();
//
//        int COLUMNS = 2;

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        int i=0;

        for(Map.Entry<String,String> entry : productCount.entrySet()) {
            String product = entry.getKey();
            String count = entry.getValue();

            Log.d("myLogs2", "prod is " + product);
            Log.d("myLogs2", "count is " + count);

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.setWeightSum(2);
            tableRow.setPadding(0,5,0,5);
            TextView tv = new TextView(this);
            tv.setWidth(0);
            tv.setLayoutParams(new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT,1.0f));
            tv.setGravity(Gravity.LEFT);
            tv.setText(product);
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
            tv.setTextSize(25);
            tv.setTypeface(Typeface.DEFAULT_BOLD);
            tv.setShadowLayer(2.0f, 2, 2, Color.BLACK);

            TextView tv2 = new TextView(getApplicationContext());
            tv2.setWidth(0);
            tv2.setLayoutParams(new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT,1.0f));
            tv2.setGravity(Gravity.RIGHT);
            tv2.setText(count);
            tv2.setTextColor(getResources().getColor(R.color.colorWhite));
            tv2.setTextSize(25);
            tv2.setTypeface(Typeface.DEFAULT_BOLD);
            tv2.setShadowLayer(2.0f, 2, 2, Color.BLACK);


            tableRow.addView(tv, 0);
            tableRow.addView(tv2, 1);

            tableLayout.addView(tableRow, i);
            i++;

//            TableRow tableRow2 = new TableRow(this);
//            TextView tv2 = new TextView(this);
//            tv.setText(count);

        }

//        for (String key: productCount.keySet()) {
//
//        } {
//
//            TableRow tableRow = new TableRow(this);
//            tableRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT));
//
//            TextView tv = new TextView(this);
//            tv.setText(productCount.get());
//
//            for (int j = 0; j < COLUMNS; j++) {
//                ImageView imageView = new ImageView(this);
//                imageView.setImageResource(R.drawable.book);
//
//                tableRow.addView(imageView, j);
//            }
//
//            tableLayout.addView(tableRow, i);
//        }

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

        if (id == R.id.nav_camera) {

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
