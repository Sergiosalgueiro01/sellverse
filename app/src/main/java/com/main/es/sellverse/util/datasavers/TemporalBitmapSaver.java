package com.main.es.sellverse.util.datasavers;

import android.graphics.Bitmap;


/**
 * Singleton class for saving an uri
 */
public class TemporalBitmapSaver {
    private static TemporalBitmapSaver instance;
   public Bitmap temporalBitmap;

    public static TemporalBitmapSaver getInstance() {
        if (instance == null) {
            instance = new TemporalBitmapSaver();
        }
        return instance;
    }
}
