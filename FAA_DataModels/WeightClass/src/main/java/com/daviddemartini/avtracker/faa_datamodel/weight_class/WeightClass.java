package com.daviddemartini.avtracker.faa_datamodel.weight_class;

import javax.inject.Singleton;
import java.lang.annotation.Inherited;
import java.util.HashMap;
import java.util.Map;

public class WeightClass {

    protected static Map<String,String> weightClass = new HashMap();

    static {
        weightClass.put("1","Up to 12,499");
        weightClass.put("2","12,500 - 19,999");
        weightClass.put("3","20,000 and over.");
        weightClass.put("4","UAV up to 55");
    }

    /**
     * Generlized getter method
     *
     * @param key
     * @return
     */
    public static String get(String key){
        if(weightClass.containsKey(key)){
            return weightClass.get(key).toString();
        }
        return null;
    }
    public static String get(int intKey){
        String key = ((Integer) intKey).toString();
        if(weightClass.containsKey(key)){
            return weightClass.get(key).toString();
        }
        return null;
    }

}
