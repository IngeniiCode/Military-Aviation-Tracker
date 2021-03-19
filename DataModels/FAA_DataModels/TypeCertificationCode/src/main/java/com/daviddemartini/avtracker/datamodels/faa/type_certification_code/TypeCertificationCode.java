package com.daviddemartini.avtracker.datamodels.faa.type_certification_code;

import java.util.HashMap;
import java.util.Map;

public class TypeCertificationCode {

    private static Map<String,String> typeCode = new HashMap();

    // init
    static {
        typeCode.put("0","Type Certificated");
        typeCode.put("1","Not Type Certificated");
        typeCode.put("2","Light Sport");
    }

    /**
     * Generlized getter method
     *
     * @param key
     * @return
     */
    public static String get(String key){
        if(typeCode.containsKey(key)){
            return typeCode.get(key).toString();
        }
        return null;
    }
    public static String get(int intKey){
        String key = ((Integer) intKey).toString();
        if(typeCode.containsKey(key)){
            return typeCode.get(key).toString();
        }
        return null;
    }
}

