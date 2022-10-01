package com.main.es.sellverse.util.cropper;


import com.theartofdev.edmodo.cropper.CropImageView;

public interface Cropper {
    CropImageView.CropShape getCropeShape();
    String getTitle();
}
