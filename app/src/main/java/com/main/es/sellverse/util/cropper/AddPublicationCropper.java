package com.main.es.sellverse.util.cropper;

import android.app.Activity;
import android.content.Context;

import com.theartofdev.edmodo.cropper.CropImageView;
import com.main.es.sellverse.R;

public class AddPublicationCropper implements Cropper{
    private Activity activity;
    public AddPublicationCropper(Activity activity){
        this.activity=activity;
    }
    @Override
    public CropImageView.CropShape getCropeShape() {
        return CropImageView.CropShape.RECTANGLE;
    }

    @Override
    public String getTitle() {
        return activity.getString(R.string.new_image);
    }
}
