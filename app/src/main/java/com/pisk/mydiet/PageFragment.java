package com.pisk.mydiet;

import java.util.Random;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PageFragment extends Fragment {

    static final String TAG2 = "myLogs2";

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARGUMENT_DAY_NUMBER = "arg_day_number";
    static final String ARGUMENT_PROGRAM_NUMBER = "arg_program_number";

    int pageNumber;
    int dayNumber;
    int programNumber;


    // for time
    String products;
    String kcalText;
    String recipeText;
    //for time

    int backColor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    static PageFragment newInstance(int page, int dayNumber, int programNumber) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putInt(ARGUMENT_DAY_NUMBER, dayNumber);
        arguments.putInt(ARGUMENT_PROGRAM_NUMBER, programNumber);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        dayNumber = getArguments().getInt(ARGUMENT_DAY_NUMBER);
        programNumber = getArguments().getInt(ARGUMENT_PROGRAM_NUMBER);

        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        dbHelper = new DatabaseHelper(this.getContext());
        // создаем базу данных
        dbHelper.create_db();
        db = dbHelper.open();

        String[] columns = new String[]{dbHelper.RECIPE,dbHelper.INRIDIENTS,dbHelper.KKAL};
//        String selection = dbHelper.PROGRAM_NUMBER + "= ?";
//         String[] selectionArgs = new String[] { "1" };

        Log.d(TAG2, "programNumber is: " + programNumber);
        Log.d(TAG2, "pageNumber is: " + pageNumber);
        Log.d(TAG2, "dayNumber is: " + dayNumber);

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
                //String str;
                do {
                    products = cursor.getString(ingridientsColIndex);
                    recipeText = cursor.getString(recipeColIndex);
                    kcalText = cursor.getString(kkalColIndex);
                    //Log.d(TAG2, products);
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

        ImageView tvImage = (ImageView) view.findViewById(R.id.photoRecept);

        Log.d(TAG2, "page : " + pageNumber);

        ingridients.setText(products);
        recipe.setText(recipeText);
        kcal.setText(kcal.getText() + kcalText);
        view.setBackgroundColor(backColor);

        tvImage.setImageDrawable(getResources().getDrawable(R.drawable.balance));

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
