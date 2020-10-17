package com.pisk.mydiet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CommonFunctions {

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
}
