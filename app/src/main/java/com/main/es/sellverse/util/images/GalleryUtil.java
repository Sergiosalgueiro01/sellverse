package com.main.es.sellverse.util.images;

import android.content.Intent;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

public class GalleryUtil {
    public static final int GALLERY_REQUEST_CODE =1234;

    public static void pickFromGallery(AppCompatActivity activity){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        String[] arrayTypes= new String[]{"image/jpeg", "image/png", "image/jpg"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayTypes);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
}
