package com.hand.util;

/**
 * Created by Hand on 2016/12/28.
 */
public class TypeUtils {

    public static String objToString(Object obj){
        if(obj==null){
            return null;
        }
        return String.valueOf(obj);
    }

    public static Integer objToInteger(Object obj){
        if(obj==null){
            return null;
        }
        return Integer.valueOf(String.valueOf(obj));
    }

    public static Long objToLong(Object obj){
        if(obj==null){
            return null;
        }
        return Long.valueOf(String.valueOf(obj));
    }

    public static double objTodouble(Object obj){
        if(obj==null){
            return 0;
        }
        return Double.parseDouble(String.valueOf(obj));
    }

    public static int objToInt(Object obj){
        if(obj==null){
            return 0;
        }
        return Integer.parseInt(String.valueOf(obj));
    }

}
