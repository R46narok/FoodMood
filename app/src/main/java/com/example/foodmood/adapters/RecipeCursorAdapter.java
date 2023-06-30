package com.example.foodmood.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.foodmood.R;
import com.example.foodmood.media.BitmapImage;

public class RecipeCursorAdapter extends SimpleCursorAdapter
{
    private Context _context;
    private Cursor _cursor;

    public RecipeCursorAdapter(Context context, Cursor c)
    {
        super(context, R.layout.list_item_layout, c,
                new String[]{"NAME", "SHORT_DESCRIPTION"},
                new int[]{R.id.recipeTitleTextView, R.id.recipeDescriptionTextView}, 0);

        _context = context;
        _cursor = c;
    }

    @SuppressLint("Range")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView1 = view.findViewById(R.id.recipeTitleTextView);
        TextView textView2 = view.findViewById(R.id.recipeDescriptionTextView);
        ImageView imageView = view.findViewById(R.id.recipeImageView);

        textView1.setTextSize(25);
        textView2.setTextSize(16);

        BitmapImage.displayFromUri(_context, imageView, Uri.parse(
                _cursor.getString(_cursor.getColumnIndex("IMAGE_PATH"))
        ));

        return view;
    }
}
