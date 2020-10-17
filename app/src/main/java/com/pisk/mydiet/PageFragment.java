package com.pisk.mydiet;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PageFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARGUMENT_DAY_NUMBER = "arg_day_number";
    static final String ARGUMENT_PROGRAM_NUMBER = "arg_program_number";
    static final String ARGUMENT_DATE = "arg_date";
    static final String ARGUMENT_LIGHT_COLOR = "arg_light_color";

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

    String backColor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    //TextView viewDate;

    static PageFragment newInstance(int page, int dayNumber, int programNumber, String date, String backColor) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putInt(ARGUMENT_DAY_NUMBER, dayNumber);
        arguments.putInt(ARGUMENT_PROGRAM_NUMBER, programNumber);
        arguments.putString(ARGUMENT_DATE, date);
        arguments.putString(ARGUMENT_LIGHT_COLOR, backColor);
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
        backColor = getArguments().getString(ARGUMENT_LIGHT_COLOR);


        dbHelper = new DatabaseHelper(this.getContext());
        // создаем базу данных
        dbHelper.create_db();
        db = dbHelper.open();


        String table = "recipes as R inner join images as I on R.image_id = I.id";

        String columns[] = { "R.recipe as recipe", "R.ingridients as ingridients",
                "R.kkal as kkal",
                "I.image as image",  "R.name as name",};

        String WHERE = dbHelper.PROGRAM_NUMBER + "='" + programNumber + "' AND " +
                dbHelper.FOOD_TIME + "='" + pageNumber + "' AND " +
                dbHelper.DAY + "='" + dayNumber + "'";
        cursor = db.query(table, columns, WHERE, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                // определяем номера столбцов по имени в выборке
                int recipeColIndex = cursor.getColumnIndex(dbHelper.RECIPE);
                int ingridientsColIndex = cursor.getColumnIndex(dbHelper.INRIDIENTS);
                int kkalColIndex = cursor.getColumnIndex(dbHelper.KKAL);
                int imageColIndex = cursor.getColumnIndex(dbHelper.IMAGE);
                int nameColIndex = cursor.getColumnIndex(dbHelper.NAME);

                do {
                    products = cursor.getString(ingridientsColIndex);
                    recipeText = cursor.getString(recipeColIndex);
                    kcalText = cursor.getString(kkalColIndex);
                    image = cursor.getString(imageColIndex);
                    name = cursor.getString(nameColIndex);
                    String b64 = image;

                    byte[] decodedString = Base64.decode(b64, Base64.DEFAULT);
                    bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {

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

        ingridients.setText(products);
        recipe.setText(recipeText);
        kcal.setText(kcal.getText() + kcalText);
        //GradientDrawable draw = (GradientDrawable) view.getBackground(); //test
        view.setBackgroundColor(Color.parseColor(backColor));
        nameDish.setText(name);


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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
