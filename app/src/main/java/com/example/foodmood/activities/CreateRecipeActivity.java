package com.example.foodmood.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodmood.MainActivity;
import com.example.foodmood.R;
import com.example.foodmood.data.DbConnection;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateRecipeActivity extends AppCompatActivity
{
    private EditText _nameView;
    private EditText _descriptionView;
    private EditText _instructionsView;
    private String _filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        initializeView();
    }

    private void initializeView()
    {
        _nameView = findViewById(R.id.nameView);
        _descriptionView = findViewById(R.id.descriptionView);
        _instructionsView = findViewById(R.id.instructionsView);

        Button selectBtn = findViewById(R.id.selectBtn);
        selectBtn.setOnClickListener(v -> showDialog(this));
    }

    public void showDialog(@NotNull Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Image");
        builder.setItems(new CharSequence[]{"Camera", "Gallery"}, (dialog, which) ->
        {
            if (which == 0)
            {
                dispatchTakePictureIntent();
            } else
            {
                dispatchOpenGalleryIntent();
            }
        });
        builder.show();
    }


    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    private Uri imageUri;

    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            File photoFile = createImageFile();
            if (photoFile != null)
            {
                imageUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile()
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try
        {
            File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
            return imageFile;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK)
        {
            Uri selectedImageUri = data.getData();
            _filePath = selectedImageUri.toString();
            Toast.makeText(this, "Image selected successfully", Toast.LENGTH_SHORT).show();
        }
    }


    private void dispatchOpenGalleryIntent()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
    }

    public void onCreateButtonClicked(View view)
    {
        String name = _nameView.getText().toString();
        String description = _descriptionView.getText().toString();
        String instructions = _instructionsView.getText().toString();

        SQLiteOpenHelper dbOpenHelper = new DbConnection(this);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        DbConnection.createRecipe(db, name, description, instructions, _filePath);
        successfulCreate();
    }

    private void successfulCreate()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}