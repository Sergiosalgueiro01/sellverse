package com.main.es.sellverse.util.datasavers;

import java.util.ArrayList;
import java.util.List;

public class TemporalTimeSaver {
    private static TemporalTimeSaver instance;
    public int minutes;
    public int hours;
    public int day;
    public int month;
    public int year;

    public static TemporalTimeSaver getInstance() {
        if (instance == null) {
            instance = new TemporalTimeSaver();
        }
        return instance;
    }
}
