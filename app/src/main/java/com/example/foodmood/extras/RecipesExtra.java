package com.example.foodmood.extras;

import android.os.Bundle;

import org.jetbrains.annotations.Nullable;

public final class RecipesExtra
{
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SHORT_DESCRIPTION = "short_description";
    public static final String INSTRUCTIONS = "instructions";
    public static final String IMAGE_PATH = "image_resource_id";

    public static Bundle buildBundle(
            int id,
            @Nullable String name,
            @Nullable String shortDescription,
            @Nullable String instructions,
            @Nullable String imagePath
    )
    {
        Bundle bundle = new Bundle();

        bundle.putInt(ID, id);
        bundle.putString(NAME, name);
        bundle.putString(SHORT_DESCRIPTION, shortDescription);
        bundle.putString(INSTRUCTIONS, instructions);
        bundle.putString(IMAGE_PATH, imagePath);

        return bundle;
    }
}
