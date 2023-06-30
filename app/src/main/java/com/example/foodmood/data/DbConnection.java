package com.example.foodmood.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.foodmood.R;

import org.jetbrains.annotations.NotNull;

public class DbConnection extends SQLiteOpenHelper
{
    private static int DB_VERSION = 1;
    public static String DB_NAME = "FoodMood34ss";

    public DbConnection(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        initializeDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        initializeDatabase(db, oldVersion, newVersion);
    }

    private void initializeDatabase(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        final String recipeQuery = "CREATE TABLE RECIPES(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "SHORT_DESCRIPTION TEXT," +
                "INSTRUCTIONS TEXT," +
                "IMAGE_PATH TEXT);";

        db.execSQL(recipeQuery);

        seedDatabase(db);
    }

    private void seedDatabase(SQLiteDatabase db)
    {
//        createRecipe(db, "Pizza", "Very good", "Steps123", "");
    }

    public static void createRecipe(
            @NotNull SQLiteDatabase db,
            @NotNull String name,
            @NotNull String shortDescription,
            @NotNull String instructions,
            @NotNull String path)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put("NAME", name);
        contentValues.put("SHORT_DESCRIPTION", shortDescription);
        contentValues.put("INSTRUCTIONS", instructions);
        contentValues.put("IMAGE_PATH", path);

        db.insertOrThrow("RECIPES", null, contentValues);
    }

    public static void deleteRecipe(
            @NotNull SQLiteDatabase db,
            int id)
    {
        db.delete("RECIPES", "_id = ?", new String[]{Integer.toString(id)});
    }


    public static void updateRecipe(
            @NotNull SQLiteDatabase db,
            int id,
            @NotNull String name,
            @NotNull String shortDescription,
            @NotNull String instructions)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put("NAME", name);
        contentValues.put("SHORT_DESCRIPTION", shortDescription);
        contentValues.put("INSTRUCTIONS", instructions);
//        contentValues.put("IMAGE_PATH", imageResourceId);

        db.update("RECIPES", contentValues, "_id = ?", new String[]{Integer.toString(id)});
    }

    public static Cursor queryAllProperties(@NotNull SQLiteDatabase database)
    {
        return database.query("RECIPES", new String[]{"_id", "NAME", "SHORT_DESCRIPTION", "INSTRUCTIONS", "IMAGE_PATH"},
                null, null, null, null, null);
    }

    public static Cursor queryAllProperties(@NotNull SQLiteDatabase database, int id)
    {
        return database.query("RECIPES", new String[]{"_id", "NAME", "SHORT_DESCRIPTION", "INSTRUCTIONS", "IMAGE_PATH"},
                "_id = ?",new String[]{Integer.toString(id)}, null, null, null);
    }
}
