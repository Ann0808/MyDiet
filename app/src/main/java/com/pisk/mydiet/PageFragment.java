package com.pisk.mydiet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PageFragment extends Fragment {

    static final String TAG2 = "myLogs2";

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARGUMENT_DAY_NUMBER = "arg_day_number";
    static final String ARGUMENT_PROGRAM_NUMBER = "arg_program_number";
    static final String ARGUMENT_DATE = "arg_date";

    int pageNumber;
    int dayNumber;
    int programNumber;
    String date = null;


    // for time
    String products;
    String kcalText;
    String recipeText;
    String image;
    String name;
    Bitmap bm;
    //for time

    int backColor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    //TextView viewDate;

    static PageFragment newInstance(int page, int dayNumber, int programNumber, String date) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putInt(ARGUMENT_DAY_NUMBER, dayNumber);
        arguments.putInt(ARGUMENT_PROGRAM_NUMBER, programNumber);
        arguments.putString(ARGUMENT_DATE, date);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        dayNumber = getArguments().getInt(ARGUMENT_DAY_NUMBER);
        programNumber = getArguments().getInt(ARGUMENT_PROGRAM_NUMBER);
        date = getArguments().getString(ARGUMENT_DATE);


        //Random rnd = new Random();
        //backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        //pager.setBackgroundColor(Color.BLACK);

        if(programNumber ==1) {
            backColor = getResources().getColor(R.color.colorSuperFitLight);
        } else if(programNumber ==2) {
            backColor = getResources().getColor(R.color.colorFitLight);
        } else if(programNumber ==3) {
            backColor = getResources().getColor(R.color.colorBalanceLight);
        } else {
            backColor = getResources().getColor(R.color.colorStrongLight);
        }


        dbHelper = new DatabaseHelper(this.getContext());
        // создаем базу данных
        dbHelper.create_db();
        db = dbHelper.open();

        String[] columns = new String[]{dbHelper.RECIPE,dbHelper.INRIDIENTS,dbHelper.KKAL,
                dbHelper.IMAGE,dbHelper.NAME};
//        String selection = dbHelper.PROGRAM_NUMBER + "= ?";
//         String[] selectionArgs = new String[] { "1" };

//        Log.d(TAG2, "programNumber is: " + programNumber);
//        Log.d(TAG2, "pageNumber is: " + pageNumber);
//        Log.d(TAG2, "dayNumber is: " + dayNumber);

        String WHERE = dbHelper.PROGRAM_NUMBER + "='" + programNumber + "' AND " +
                dbHelper.FOOD_TIME + "='" + pageNumber + "' AND " +
                dbHelper.DAY + "='" + dayNumber + "'";
        cursor = db.query(dbHelper.TABLE, columns, WHERE, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                // определяем номера столбцов по имени в выборке
                int recipeColIndex = cursor.getColumnIndex(dbHelper.RECIPE);
                int ingridientsColIndex = cursor.getColumnIndex(dbHelper.INRIDIENTS);
                int kkalColIndex = cursor.getColumnIndex(dbHelper.KKAL);
                int imageColIndex = cursor.getColumnIndex(dbHelper.IMAGE);
                int nameColIndex = cursor.getColumnIndex(dbHelper.NAME);
                Log.d(TAG2, "image index: " + imageColIndex);
                //String str;
                do {
                    products = cursor.getString(ingridientsColIndex);
                    recipeText = cursor.getString(recipeColIndex);
                    kcalText = cursor.getString(kkalColIndex);
                    image = cursor.getString(imageColIndex);
                    name = cursor.getString(nameColIndex);
                    //bm = BitmapFactory.decodeByteArray(image, 0, image.length);

                    //String b64 = "iVBORw0KGgoAAAANSUhEUgAAAgAAAAIACAYAAAD0eNT6AAABS2lUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4KPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxMzggNzkuMTU5ODI0LCAyMDE2LzA5LzE0LTAxOjA5OjAxICAgICAgICAiPgogPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4KICA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIi8+CiA8L3JkZjpSREY+CjwveDp4bXBtZXRhPgo8P3hwYWNrZXQgZW5kPSJyIj8+IEmuOgAACLlJREFUeJzt2LFtw0AUBUHLYCnM2QZLZhvM2cu5Agc2RJ2gnckP98LFf4wxxhcAkPI9ewAA8HoCAACCBAAABAkAAAgSAAAQJAAAIEgAAECQAACAIAEAAEECAACCBAAABAkAAAgSAAAQJAAAIEgAAECQAACAIAEAAEECAACCBAAABAkAAAgSAAAQJAAAIEgAAECQAACAoGX2gN8c5zV7AgBvaN/W2RM+ggsAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBjzHGmD2C5zvOa/YE4Cb7ts6ewAdwAQCAIAEAAEECAACCBAAABAkAAAgSAAAQJAAAIEgAAECQAACAIAEAAEECAACCBAAABAkAAAgSAAAQJAAAIEgAAECQAACAIAEAAEECAACCBAAABAkAAAgSAAAQJAAAIEgAAECQAACAIAEAAEECAACCBAAABAkAAAgSAAAQJAAAIEgAAECQAACAIAEAAEECAACCBAAABAkAAAgSAAAQJAAAIEgAAECQAACAIAEAAEECAACCBAAABAkAAAgSAAAQJAAAIEgAAECQAACAIAEAAEECAACCBAAABAkAAAgSAAAQJAAAIEgAAECQAACAIAEAAEECAACCBAAABAkAAAgSAAAQJAAAIEgAAECQAACAIAEAAEECAACCBAAABAkAAAgSAAAQJAAAIEgAAECQAACAIAEAAEECAACCBAAABAkAAAgSAAAQJAAAIEgAAECQAACAIAEAAEECAACCBAAABAkAAAhaZg8A4G+O83rJP/u2vuQf5nABAIAgAQAAQQIAAIIEAAAECQAACBIAABAkAAAgSAAAQJAAAIAgAQAAQQIAAIIEAAAECQAACBIAABAkAAAgSAAAQJAAAIAgAQAAQQIAAIIEAAAECQAACBIAABAkAAAgSAAAQJAAAIAgAQAAQQIAAIIEAAAECQAACBIAABAkAAAgSAAAQJAAAIAgAQAAQQIAAIIEAAAECQAACBIAABAkAAAgSAAAQJAAAIAgAQAAQQIAAIIEAAAECQAACBIAABAkAAAgSAAAQJAAAIAgAQAAQQIAAIIEAAAECQAACBIAABAkAAAgSAAAQJAAAIAgAQAAQQIAAIIEAAAECQAACBIAABAkAAAgSAAAQJAAAIAgAQAAQQIAAIIEAAAECQAACBIAABAkAAAgSAAAQJAAAIAgAQAAQQIAAIIEAAAECQAACBIAABAkAAAgSAAAQJAAAIAgAQAAQQIAAIIEAAAECQAACBIAABC0zB7APfZt/ffb47yeuASAd+QCAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAELTMHgAAdzjOa/aEt+YCAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABA0DJ7AO9n39bZEwC4mQsAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAECQAACBIAABAkAAAgCABAABBAgAAggQAAAQJAAAIEgAAEPQDpX8bARdnfd8AAAAASUVORK5CYII=";
                    String b64 = image;
                    Log.d(TAG2, "image : " + image);

                    byte[] decodedString = Base64.decode(b64, Base64.DEFAULT);
                    bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    //Log.d(TAG2, image.toString());
                    // Log.d(LOG_TAG, str);

                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            //Log.d(TAG, "zero cursor");

        }



        dbHelper.close();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);

        TextView ingridients = (TextView) view.findViewById(R.id.ingridients);
        TextView recipe = (TextView) view.findViewById(R.id.recipe);
        TextView kcal = (TextView) view.findViewById(R.id.tvPage3);
        TextView nameDish = view.findViewById(R.id.nameDish);

        TextView viewDate = (TextView) view.findViewById(R.id.date);
        ViewGroup.LayoutParams params = viewDate.getLayoutParams();

        if (date == null) {

            container.removeView(viewDate);

        } else {

            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            viewDate.setLayoutParams(params);
            viewDate.setText(date);
            viewDate.setVisibility(View.VISIBLE);

        }


        ImageView tvImage = (ImageView) view.findViewById(R.id.photoRecept);

        Log.d(TAG2, "page : " + pageNumber);

        ingridients.setText(products);
        recipe.setText(recipeText);
        kcal.setText(kcal.getText() + kcalText);
        view.setBackgroundColor(backColor);
        nameDish.setText(name);

        //tvImage.setImageDrawable(getResources().getDrawable(R.drawable.balance));
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.strong);

        tvImage.setImageBitmap(bm);

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        cursor.close();
    }

}
