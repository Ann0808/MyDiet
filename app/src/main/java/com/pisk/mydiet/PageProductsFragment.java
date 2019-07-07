package com.pisk.mydiet;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;
import java.util.TreeMap;

public class PageProductsFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    static final String TAG2 = "myLogs2";

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARGUMENT_PROGRAM_NUMBER = "arg_program_number";
    static final String ARGUMENT_WEEK = "arg_date";

    int pageNumber;
    int weekNumber;
    int programNumber;


    Map<String,String> productCount;

    int backColor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    //TextView viewDate;

    static PageProductsFragment newInstance(int page, int programNumber, int week) {
        PageProductsFragment pageFragment = new PageProductsFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putInt(ARGUMENT_PROGRAM_NUMBER, programNumber);
        arguments.putInt(ARGUMENT_WEEK, week);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        weekNumber = getArguments().getInt(ARGUMENT_WEEK);
        programNumber = getArguments().getInt(ARGUMENT_PROGRAM_NUMBER);


        if(programNumber ==1) {
            backColor = getResources().getColor(R.color.colorSuperFitLight);
        } else if(programNumber ==2) {
            backColor = getResources().getColor(R.color.colorFitLight);
        } else if(programNumber ==3) {
            backColor = getResources().getColor(R.color.colorBalanceLight);
        } else {
            backColor = getResources().getColor(R.color.colorStrongLight);
        }


        productCount = new TreeMap<>();

        dbHelper = new DatabaseHelper(this.getContext());
        // создаем базу данных
        dbHelper.create_db();
        db = dbHelper.open();

//        String[] columns = new String[]{dbHelper.RECIPE,dbHelper.INRIDIENTS,dbHelper.KKAL,
//                dbHelper.IMAGE,dbHelper.NAME};
//        String selection = dbHelper.PROGRAM_NUMBER + "= ?";
//         String[] selectionArgs = new String[] { "1" };

//        Log.d(TAG2, "programNumber is: " + programNumber);
//        Log.d(TAG2, "pageNumber is: " + pageNumber);
//        Log.d(TAG2, "dayNumber is: " + dayNumber);

//        String table = "recipes as R inner join images as I on R.image_id = I.id";

        String[] columns = new String[]{dbHelper.PRODUCT,dbHelper.COUNT};
        String WHERE = dbHelper.PROGRAM_NUMBER_PR + "='" + programNumber + "' AND " +
                dbHelper.CATEGORY + "='" + pageNumber + "' AND " +
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

        } else {


        }

        cursor.close();
        dbHelper.close();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.products_fragment, null);


        TextView viewWeek = (TextView) view.findViewById(R.id.weekProduct);

        viewWeek.setText(weekNumber + " неделя");


        ImageView tvImage = (ImageView) view.findViewById(R.id.photo);

        if (pageNumber == 1) {
            tvImage.setImageResource(R.drawable.fruits);
        } else if (pageNumber == 2) {
            tvImage.setImageResource(R.drawable.meat);
        } else if (pageNumber==3) {
            tvImage.setImageResource(R.drawable.milk);
        } else if (pageNumber==4) {
            tvImage.setImageResource(R.drawable.bak);
        } else {
            tvImage.setImageResource(R.drawable.other);
        }

        Log.d(TAG2, "page : " + pageNumber);

        view.setBackgroundColor(backColor);

        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);

        int i=0;
        Typeface typeface = ResourcesCompat.getFont(this.getContext(), R.font.robotoreg);

        for(Map.Entry<String,String> entry : productCount.entrySet()) {
            String product = entry.getKey();
            String count = entry.getValue();

            Log.d("myLogs2", "prod is " + product);
            Log.d("myLogs2", "count is " + count);

            TableRow tableRow = new TableRow(this.getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.setWeightSum(3);
            tableRow.setPadding(10,15,10,15);

            TextView tv = new TextView(this.getContext());
            tv.setWidth(0);
            tv.setLayoutParams(new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT,2.0f));
            tv.setGravity(Gravity.LEFT);
            tv.setText(product);
            tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            tv.setTextSize(24);
            tv.setTypeface(typeface);
            //tv.setShadowLayer(2.0f, 2, 2, Color.BLACK);

            ShapeDrawable border = new ShapeDrawable(new RectShape());
            border.getPaint().setStyle(Paint.Style.STROKE);
            border.getPaint().setColor(Color.WHITE);
            border.getPaint().setStrokeWidth(5);

            tableRow.setBackgroundDrawable(border);



            TextView tv2 = new TextView(this.getContext());
            tv2.setWidth(0);
            tv2.setLayoutParams(new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT,1.0f));
            tv2.setGravity(Gravity.RIGHT);
            tv2.setText(count);
            tv2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            tv2.setTextSize(24);
            tv2.setTypeface(typeface);
            //tv2.setShadowLayer(2.0f, 2, 2, Color.BLACK);


            tableRow.addView(tv, 0);
            tableRow.addView(tv2, 1);

            tableLayout.addView(tableRow, i);
            i++;

//            TableRow tableRow2 = new TableRow(this);
//            TextView tv2 = new TextView(this);
//            tv.setText(count);

        }


        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        cursor.close();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
