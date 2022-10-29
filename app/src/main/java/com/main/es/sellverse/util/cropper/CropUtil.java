package com.main.es.sellverse.util.cropper;


import android.graphics.Color;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropUtil {
    public static final int CROP_REQUEST_CODE= CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;
    public static final int CROP_ERROR_CODE= CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE;

    public static void launchImageCrop(Uri uri, Cropper cropper, AppCompatActivity activity){
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .setCropShape(cropper.getCropeShape())
                .setActivityTitle(cropper.getTitle())
                .setBackgroundColor(Color.TRANSPARENT)
                .setGuidelinesThickness(0F)
                .setBorderCornerThickness(0F)
                .start(activity);
    }
}
