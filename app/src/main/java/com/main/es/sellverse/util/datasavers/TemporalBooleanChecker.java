package com.main.es.sellverse.util.datasavers;



public class TemporalBooleanChecker {
    private static TemporalBooleanChecker instance;
    public boolean checker=false;
    public boolean hasChange=false;

    public static TemporalBooleanChecker getInstance() {
        if (instance == null) {
            instance = new TemporalBooleanChecker();
        }
        return instance;
    }
}
