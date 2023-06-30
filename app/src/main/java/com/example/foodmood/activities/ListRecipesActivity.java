package com.example.foodmood.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodmood.R;
import com.example.foodmood.adapters.RecipeCursorAdapter;
import com.example.foodmood.data.DbConnection;
import com.example.foodmood.extras.RecipesExtra;

public class ListRecipesActivity extends AppCompatActivity
{
    private SQLiteDatabase _db;
    private Cursor _cursor;
    private ListView _recipesView;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes);

        _recipesView = findViewById(R.id.recipesView);

        invalidate();
    }

    private void invalidate()
    {
        SQLiteOpenHelper dbHelper = new DbConnection(this);
        _db = dbHelper.getReadableDatabase();
        _cursor = DbConnection.queryAllProperties(_db);
        _recipesView.setAdapter(new RecipeCursorAdapter(this, _cursor));

        AdapterView.OnItemClickListener listener = (adapterView, view, position, i) ->
        {
            SQLiteCursor cursor = (SQLiteCursor) adapterView.getItemAtPosition(position);

            startReadRecipeActivity(cursor);
        };

        _recipesView.setOnItemClickListener(listener);
    }

    @Override
    protected void onResume()
    {
        invalidate();
        super.onResume();
    }

    @SuppressLint("Range")
    private void startReadRecipeActivity(Cursor cursor)
    {
        int id = cursor.getInt(cursor.getColumnIndex("_id"));

        Bundle bundle = RecipesExtra.buildBundle(id, null, null, null, "");
        Intent intent = new Intent(this, ReadRecipeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        _cursor.close();
        _db.close();
    }

    public void onCreateButtonClicked(View view)
    {
        Intent intent = new Intent(this, CreateRecipeActivity.class);
        startActivity(intent);
    }
}