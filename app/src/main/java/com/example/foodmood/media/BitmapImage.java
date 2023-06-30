package com.example.foodmood.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

public class BitmapImage
{
    public static void displayFromUri(Context context, ImageView imageView, Uri uri)
    {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}
