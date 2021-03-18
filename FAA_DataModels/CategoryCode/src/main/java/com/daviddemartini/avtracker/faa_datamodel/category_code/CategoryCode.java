package com.daviddemartini.avtracker.faa_datamodel.category_code;

import java.util.HashMap;
import java.util.Map;

public class CategoryCode {

    private static Map<String,String> categoryCode = new HashMap();

    // init
    static {
        categoryCode.put("1","Land");
        categoryCode.put("2","Sea");
        categoryCode.put("3","Amphibian");
    }

    /**
     * Generlized getter method
     *
     * @param key
     * @return
     */
    public static String get(String key){
        if(categoryCode.containsKey(key)){
            return categoryCode.get(key).toString();
        }
        return null;
    }
    public static String get(int intKey){
        String key = ((Integer) intKey).toString();
        if(categoryCode.containsKey(key)){
            return categoryCode.get(key).toString();
        }
        return null;
    }
}
