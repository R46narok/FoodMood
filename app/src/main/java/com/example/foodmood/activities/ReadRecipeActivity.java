package com.example.foodmood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodmood.MainActivity;
import com.example.foodmood.R;
import com.example.foodmood.data.DbConnection;
import com.example.foodmood.extras.RecipesExtra;
import com.example.foodmood.media.BitmapImage;

public class ReadRecipeActivity extends AppCompatActivity
{
    private SQLiteDatabase _db;
    private Cursor _cursor;

    private TextView _nameView;
    private TextView _descriptionView;
    private TextView _instructionsView;
    private ImageView _imageView;

    private int _recipeId;
    private String _recipeName;
    private String _recipeDescription;
    private String _recipeInstructions;
    private String _recipeImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_recipe);

        SQLiteOpenHelper openHelper = new DbConnection(this);
        _db = openHelper.getReadableDatabase();

        initializeFromIntent();
        _cursor = DbConnection.queryAllProperties(_db, _recipeId);
        initializeView();


        populateView();
    }

    private void initializeView()
    {
        _nameView = findViewById(R.id.nameView);
        _descriptionView = findViewById(R.id.descriptionView);
        _instructionsView = findViewById(R.id.instructionsView);
        _imageView = findViewById(R.id.imageView);
    }

    private void initializeFromIntent()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        _recipeId = bundle.getInt(RecipesExtra.ID);
    }

    @SuppressLint("Range")
    private void populateView()
    {
        if (!_cursor.moveToFirst())
        {
            return;
        }

        _recipeName = _cursor.getString(_cursor.getColumnIndex("NAME"));
        _recipeDescription = _cursor.getString(_cursor.getColumnIndex("SHORT_DESCRIPTION"));
        _recipeInstructions = _cursor.getString(_cursor.getColumnIndex("INSTRUCTIONS"));
        _recipeImagePath = _cursor.getString(_cursor.getColumnIndex("IMAGE_PATH"));

        _nameView.setText(_recipeName);
        _descriptionView.setText(_recipeDescription);
        _instructionsView.setText(_recipeInstructions);

        if (_recipeImagePath != null && !_recipeImagePath.isEmpty())
        {
            BitmapImage.displayFromUri(this, _imageView, Uri.parse(_recipeImagePath));
        }
    }

    public void onDeleteButtonClicked(View view)
    {
        DbConnection.deleteRecipe(_db, _recipeId);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onUpdateButtonClicked(View view)
    {
        Intent intent = new Intent(this, UpdateRecipeActivity.class);
        Bundle bundle = RecipesExtra.buildBundle(_recipeId, _recipeName, _recipeDescription, _recipeInstructions, _recipeImagePath);
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
}