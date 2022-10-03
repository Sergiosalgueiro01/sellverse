package com.main.es.sellverse.util.datasavers;

import android.net.Uri;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Singleton class for saving an uri
 */
public class TemporalUriSaver {
    private static TemporalUriSaver instance;
    public boolean hasChange=false;
    public Uri temporalUri;
    public ImageButton lastButtonChanged=null;
    public ImageView lastImageViewChanged=null;

    public static TemporalUriSaver getInstance() {
        if (instance == null) {
            instance = new TemporalUriSaver();
        }
        return instance;
    }
}
