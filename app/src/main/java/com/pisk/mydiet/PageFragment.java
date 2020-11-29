package com.pisk.mydiet;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PageFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARGUMENT_DAY_NUMBER = "arg_day_number";
    static final String ARGUMENT_PROGRAM_NUMBER = "arg_program_number";
    static final String ARGUMENT_DATE = "arg_date";
    static final String ARGUMENT_LIGHT_COLOR = "arg_light_color";
    static final String ARGUMENT_TRIGGER = "arg_trirrer"; //true - loving recipe page, false - recipe page
    static final String ARGUMENT_ID = "arg_id"; //id of loving recipe

    int pageNumber;
    int id;
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
    String additionInfo = "";
    int category;
    int kcalCount;
    boolean isLovingPage = false;
    //for time

    String backColor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    Button likeButton, unLikeButton;
    ImageView heartImage;
    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;

    //TextView viewDate;

    static PageFragment newInstance(int page, int dayNumber, int programNumber, String date, String backColor) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putInt(ARGUMENT_DAY_NUMBER, dayNumber);
        arguments.putInt(ARGUMENT_PROGRAM_NUMBER, programNumber);
        arguments.putString(ARGUMENT_DATE, date);
        arguments.putString(ARGUMENT_LIGHT_COLOR, backColor);
        arguments.putBoolean(ARGUMENT_TRIGGER, false);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    static PageFragment newInstance(int id, String backColor) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_ID, id);
        arguments.putString(ARGUMENT_LIGHT_COLOR, backColor);
        arguments.putBoolean(ARGUMENT_TRIGGER, true);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        isLovingPage = getArguments().getBoolean(ARGUMENT_TRIGGER);
        backColor = getArguments().getString(ARGUMENT_LIGHT_COLOR);
        if (isLovingPage) {
            id = getArguments().getInt(ARGUMENT_ID);
        } else {
            pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
            dayNumber = getArguments().getInt(ARGUMENT_DAY_NUMBER);
            programNumber = getArguments().getInt(ARGUMENT_PROGRAM_NUMBER);
            date = getArguments().getString(ARGUMENT_DATE);
        }





        dbHelper = new DatabaseHelper(this.getContext());
        // создаем базу данных
        dbHelper.create_db();
        db = dbHelper.open();

        String table = "";
        String columns[] = { };
        String WHERE = "";
        if (isLovingPage) {
            table = "loving_recipes as R";

            columns = new String[]{"R.recipe as recipe", "R.ingridients as ingridients",
                    "R.kkal as kkal", "R.category as category",
                    "R.image as image", "R.name as name", "R.additionInfo as additionInfo"};

            WHERE = "id='" + id + "'";
        } else {
            table = "recipes as R inner join images as I on R.image_id = I.id";

            columns = new String[] {"R.recipe as recipe", "R.ingridients as ingridients",
                    "R.kkal as kkal", "R.kkalCount as kkalCount", "R.category as category",
                    "I.image as image", "R.name as name", "R.additionInfo as additionInfo"};

            WHERE = dbHelper.PROGRAM_NUMBER + "='" + programNumber + "' AND " +
                    dbHelper.FOOD_TIME + "='" + pageNumber + "' AND " +
                    dbHelper.DAY + "='" + dayNumber + "'";
        }

        try {
            cursor = db.query(table, columns, WHERE, null, null, null, null);
        } catch (Exception ex) {
            Log.d("dblogs",ex.getMessage());
        }
        int kkalCountColIndex = 0;
        try {
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                // определяем номера столбцов по имени в выборке
                int recipeColIndex = cursor.getColumnIndex(dbHelper.RECIPE);
                int ingridientsColIndex = cursor.getColumnIndex(dbHelper.INRIDIENTS);
                int kkalColIndex = cursor.getColumnIndex(dbHelper.KKAL);
                if (!isLovingPage) {
                    kkalCountColIndex = cursor.getColumnIndex(dbHelper.KKAL_COUNT);
                }
                int imageColIndex = cursor.getColumnIndex(dbHelper.IMAGE);
                int nameColIndex = cursor.getColumnIndex(dbHelper.NAME);
                int additionColIndex = cursor.getColumnIndex(dbHelper.ADIITION);
                int categoryColIndex = cursor.getColumnIndex(dbHelper.CATEGORY_RECIPE);

                //do {
                    products = cursor.getString(ingridientsColIndex);
                    recipeText = cursor.getString(recipeColIndex);
                    kcalText = cursor.getString(kkalColIndex);
                    if (!isLovingPage) {
                        kcalCount = cursor.getInt(kkalCountColIndex);
                    }
                    image = cursor.getString(imageColIndex);
                    name = cursor.getString(nameColIndex);
                    category = cursor.getInt(categoryColIndex);
                    additionInfo = cursor.getString(additionColIndex);
                    String b64 = image;

                    byte[] decodedString = Base64.decode(b64, Base64.DEFAULT);
                    bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                //}
            }
            cursor.close();
        } else {

        } }
        catch (Exception ex) {
            Log.d("dblogs",ex.getMessage());
        }



        dbHelper.close();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);

        TextView ingridients = (TextView) view.findViewById(R.id.ingridients);
        final TextView recipe = (TextView) view.findViewById(R.id.recipe);
        TextView kcal = (TextView) view.findViewById(R.id.tvPage3);
        TextView nameDish = view.findViewById(R.id.nameDish);
        TextView addition = view.findViewById(R.id.addition);
        TextView additionTitle = view.findViewById(R.id.tvAddition);
        likeButton = view.findViewById(R.id.likeButton);
        unLikeButton = view.findViewById(R.id.unLikeButton);
        heartImage = view.findViewById(R.id.heartImage);

        dbHelper = new DatabaseHelper(this.getContext());
        db = dbHelper.open();



        String table = "loving_recipes as R";

        String columns[] = { "R.name as name"};

        String WHERE = null;
        if (!isLovingPage) {
            WHERE = dbHelper.NAME + "='" + CommonFunctions.getTheKeyLovingRecipes(name, kcalCount) + "'";
        } else {
            WHERE = dbHelper.NAME + "='" + name + "'";
        }
        try {
            cursor = db.query(table, columns, WHERE, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    likeButton.setVisibility(View.GONE);
                    unLikeButton.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception ex) {
            Log.d("dblogs",ex.getMessage());
        }

        dbHelper.close();



        final Drawable drawable = heartImage.getDrawable();

        likeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                likeButton.setClickable(false);
                unLikeButton.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        likeButton.setClickable(true);
                        unLikeButton.setClickable(true);
                    }
                }, 1500);

                String lovingName = name;
                if (!isLovingPage) {
                    lovingName = CommonFunctions.getTheKeyLovingRecipes(name, kcalCount);
                }
                CommonFunctions.AddRecipeToLoving(dbHelper, lovingName, category, recipeText, products, kcalText, image, additionInfo);
                likeButton.setVisibility(View.GONE);
                unLikeButton.setVisibility(View.VISIBLE);
                heartImage.setAlpha(0.70f);

                if (drawable instanceof AnimatedVectorDrawableCompat) {
                    avd = (AnimatedVectorDrawableCompat) drawable;
                    avd.start();
                } else if (drawable instanceof AnimatedVectorDrawable) {
                    avd2 = (AnimatedVectorDrawable) drawable;
                    avd2.start();
                }

                Toast.makeText(getContext(),"Рецепт добавлен в избранное",Toast.LENGTH_SHORT).show();

            }
        });

        unLikeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                likeButton.setClickable(false);
                unLikeButton.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        likeButton.setClickable(true);
                        unLikeButton.setClickable(true);
                    }
                }, 1500);
                if (!isLovingPage) {
                    String lovingName = CommonFunctions.getTheKeyLovingRecipes(name, kcalCount);
                    CommonFunctions.removeRecipeToLoving(dbHelper, lovingName);
                } else {
                    CommonFunctions.removeRecipeToLoving(dbHelper, name);
                }

                unLikeButton.setVisibility(View.GONE);
                likeButton.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"Рецепт удален из избранного",Toast.LENGTH_SHORT).show();

            }
        });


            if (additionInfo != null && additionInfo.length() > 0) {
                additionTitle.setVisibility(View.VISIBLE);
                addition.setText(additionInfo);
                addition.setVisibility(View.VISIBLE);
            }

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
            view.setBackgroundColor(Color.parseColor(backColor));
            try {
                nameDish.setText(name);
            }catch (Exception ex) {
                //nameDish.setText("55");
                Log.d("dblogs",ex.getMessage());
            }


        try {
            tvImage.setImageBitmap(bm);
        }catch (Exception ex) {
            //nameDish.setText("55");
            Log.d("dblogs",ex.getMessage());
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
