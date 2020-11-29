package com.pisk.mydiet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class CommonFunctions {

    final static String SAVED_PROGRAM = "saved_program";
    final static String DATE_START = "date_start";
    final static String HINT_DAYS = "hint_days";
    final static String MY_SEX = "my_sex";
    final static String MY_WEIGHT = "my_weight";
    final static String MY_HEIGHT = "my_height";
    final static String MY_AGE = "my_age";
    final static String CHOOSE_NEW = "choose_new";
    final static String START_AGAIN = "start_again";
    final static String FROM_SETTINGS = "from_settings";
    final static String OLDDBDELETED = "oldDBdeleted";
    

    public static Drawable decodeDrawable(Context context, String base64) {
        Drawable ret = null;
        if (!base64.equals("")) {
            ByteArrayInputStream bais = new ByteArrayInputStream(
                    Base64.decode(base64.getBytes(), Base64.DEFAULT));
            ret = Drawable.createFromResourceStream(context.getResources(),
                    null, bais, null, null);
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    public static ProgramInfo getProgramInfoFromDatabase(DatabaseHelper dbHelper, int savedProg) {

        dbHelper.create_db();
        SQLiteDatabase db = dbHelper.open();
        String table = "programs as R ";
        String columns[] = { "R.id as id", "R.name as name", "R.image as image", "R.color as color", "R.countMeal as countMeal",
                "R.dayColor as dayColor", "R.lightColor as lightColor", "R.countDays as countDays"};

        String WHERE = dbHelper.Prid + "='" + savedProg + "'";
        Cursor cursor = db.query(table, columns, WHERE, null, null, null, null);

        ProgramInfo prInro = new ProgramInfo();

        if (cursor != null) {
            if (cursor.moveToFirst())  {
                prInro.lName = cursor.getString(cursor.getColumnIndex(dbHelper.PrNAME));
                prInro.lImage = cursor.getString(cursor.getColumnIndex(dbHelper.PrIMAGE));
                prInro.lColor = cursor.getString(cursor.getColumnIndex(dbHelper.PrCOLOR));
                prInro.lColorDay = cursor.getString(cursor.getColumnIndex(dbHelper.PrdayColor));
                prInro.lLightColor = cursor.getString(cursor.getColumnIndex(dbHelper.PrlightColor));

                prInro.countDays = cursor.getInt(cursor.getColumnIndex(dbHelper.PrCountDays));
                prInro.countMeal = cursor.getInt(cursor.getColumnIndex(dbHelper.PrCountMeal));

            }
        }
        dbHelper.close();
        return prInro;

    }

    public static Map<Integer,CharSequence> getTitlesMap(int numberPages) {
        Map<Integer, CharSequence> hashMap = new HashMap<>();
        if (numberPages == 6) {
            hashMap.put(0, "Завтрак ⟶");
            hashMap.put(1, "Ланч ⟶");
            hashMap.put(2, "Обед ⟶");
            hashMap.put(3, "Полдник ⟶");
            hashMap.put(4, "Ужин ⟶");
            hashMap.put(5, "⟵ Перекус");
        } else if(numberPages == 5){
            hashMap.put(0, "Завтрак ⟶");
            hashMap.put(1, "Перекус ⟶");
            hashMap.put(2, "Обед ⟶");
            hashMap.put(3, "Полдник ⟶");
            hashMap.put(4, "⟵ Ужин");
        } else if(numberPages == 4) {
            hashMap.put(0, "Завтрак ⟶");
            hashMap.put(1, "Обед ⟶");
            hashMap.put(2, "Полдник ⟶");
            hashMap.put(3, "⟵ Ужин");
        } else {
            hashMap.put(0, "Завтрак ⟶");
            hashMap.put(1, "Обед ⟶");
            hashMap.put(2, "⟵ Ужин");
        }

        return hashMap;
    }

    public static int countDayNorm(boolean isWoman, int weight, int height, double goalCoef, double activityCoef, int age)
    {
        int norm = 0;
        if (isWoman) {
            norm = (int) (activityCoef * goalCoef *(447.6 + (9.2 * (double) weight) + (3.1 * (double) height) - (4.3 *(double) age)));
        } else {
            norm = (int) (activityCoef * goalCoef *(88.36 + (13.4 * (double) weight) + (4.8 * (double) height) - (5.7 *(double) age)));
        }

        return (norm%100 < 50) ? norm/100*100 : norm/100*100 + 100;
    }

    public static void AddRecipeToLoving(DatabaseHelper dbHelper, String name, int category, String recipe, String ingridients, String kkal, String image, String additionInfo){

        dbHelper.create_db();
        SQLiteDatabase db = dbHelper.open();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("category", category);
        cv.put("recipe", recipe);
        cv.put("ingridients", ingridients);
        cv.put("kkal", kkal);
        cv.put("image", image);
        cv.put("additionInfo", additionInfo);
        try {
            db.insert("loving_recipes", null, cv);
        } catch (Exception ex) {
            Log.d("dblogs", ex.getMessage());
        }
        dbHelper.close();

    }

    public static void removeRecipeToLoving(DatabaseHelper dbHelper, String name){

        dbHelper.create_db();
        SQLiteDatabase db = dbHelper.open();
        try {
            db.delete("loving_recipes",
                    "name = ?",
                    new String[] {name});
            Log.d("dblogs", name);
        } catch (Exception ex) {
            Log.d("dblogs", ex.getMessage());
        }

        dbHelper.close();

    }


    public static String getTheKeyLovingRecipes(String name, int kkal){

      return name + "\n" + Integer.toString(kkal) + " ккал";

    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
