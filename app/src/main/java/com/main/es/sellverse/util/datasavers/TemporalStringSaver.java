package com.main.es.sellverse.util.datasavers;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class TemporalStringSaver {
    private static TemporalStringSaver instance;
    public List<String>list = new ArrayList<>();
    public String  string;

    public static TemporalStringSaver getInstance() {
        if (instance == null) {
            instance = new TemporalStringSaver();
        }
        return instance;
    }
}
