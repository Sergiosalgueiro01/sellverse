package com.main.es.sellverse.util;

public class NumberArrayUtil {
    public static String[] getArrayOfSpecificNumbers(int initialNumber,int finalNumber){
        String[] array = new String[finalNumber-initialNumber+1];
        int j=0;
        for(int i =initialNumber;i<finalNumber;i++){
            array[j]=i+"";
            j++;
        }
        return array;
    }
}
