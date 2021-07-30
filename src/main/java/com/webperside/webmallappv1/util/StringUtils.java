package com.webperside.webmallappv1.util;

public class StringUtils {

    public static boolean isNumeric(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return false;
    }
}
