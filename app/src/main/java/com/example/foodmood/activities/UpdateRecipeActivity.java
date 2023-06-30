package com.example.foodmood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.foodmood.MainActivity;
import com.example.foodmood.R;
import com.example.foodmood.data.DbConnection;
import com.example.foodmood.extras.RecipesExtra;
import com.example.foodmood.media.BitmapImage;

public class UpdateRecipeActivity extends AppCompatActivity
{
    private EditText _nameEdit;
    private EditText _descriptionEdit;
    private EditText _instructionsEdit;

    private int _recipeId;
    private String _recipeName;
    private String _recipeDescription;
    private String _recipeInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);

        initializeView();
        initializeFromIntent();
    }

    private void initializeView()
    {
        _nameEdit = findViewById(R.id.nameView);
        _descriptionEdit = findViewById(R.id.descriptionView);
        _instructionsEdit = findViewById(R.id.instructionsView);
    }

    private void initializeFromIntent()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        _recipeId = bundle.getInt(RecipesExtra.ID);
        _recipeName = bundle.getString(RecipesExtra.NAME);
        _recipeDescription = bundle.getString(RecipesExtra.SHORT_DESCRIPTION);
        _recipeInstructions = bundle.getString(RecipesExtra.INSTRUCTIONS);

        _nameEdit.setHint(_recipeName);
        _descriptionEdit.setHint(_recipeDescription);
        _instructionsEdit.setHint(_recipeInstructions);
    }

    @SuppressWarnings("ConstantConditions")
    public void onSaveButtonClicked(View view)
    {
        String recipeName = _nameEdit.getText().toString();
        String recipeDescription = _descriptionEdit.getText().toString();
        String recipeInstructions = _instructionsEdit.getText().toString();

        recipeName = (recipeName != null && !recipeName.isEmpty()) ? recipeName : _recipeName;
        recipeDescription = (recipeDescription != null && !recipeDescription.isEmpty()) ? recipeDescription : _recipeDescription;
        recipeInstructions = (recipeInstructions != null && !recipeInstructions.isEmpty()) ? recipeInstructions : _recipeInstructions;

        SQLiteOpenHelper dbOpenHelper = new DbConnection(this);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        DbConnection.updateRecipe(db, _recipeId, recipeName, recipeDescription, recipeInstructions);

        successfulUpdate();
    }

    private void successfulUpdate()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}