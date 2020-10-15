package com.pisk.mydiet;

import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH; // полный путь к базе данных
    private static String DB_NAME = "Food.db";
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "recipes"; // название таблицы в бд
    static final String TABLE_PRODUCTS = "products"; // название таблицы в бд
    static final String TABLE_IMAGES = "images"; // название таблицы в бд
    // названия столбцов TABLE
    static final String PROGRAM_NUMBER = "program_number";
    static final String DAY = "day";
    static final String FOOD_TIME = "food_time";
    static final String RECIPE = "recipe";
    static final String INRIDIENTS = "ingridients";
    static final String KKAL = "kkal";
    static final String IMAGE = "image";
    static final String NAME = "name";

    // названия столбцов TABLE_PRODUCTS
    static final String WEEK = "week";
    static final String PRODUCT = "product";
    static final String COUNT = "count";
    static final String PROGRAM_NUMBER_PR = "program";
    static final String CATEGORY = "category";

    // названия столбцов TABLE_Programs
    static final String PrNAME = "name";
    static final String PrIMAGE = "image";
    static final String PrCOLOR = "color";
    static final String PrlightColor = "lightColor";
    static final String PrdayColor = "dayColor";
    static final String PrCountDays = "countDays";
    static final String PrCountMeal = "countMeal";
    static final String Prid = "id";


    private Context myContext;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
    }

    void create_db(){
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                Log.d("dblogs", "jj");
                this.getReadableDatabase();
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DB_NAME);
                // Путь к новой бд
                String outFileName = DB_PATH;

                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch(IOException ex){
            Log.d("DatabaseHelper", ex.getMessage());
        }
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
    }
}