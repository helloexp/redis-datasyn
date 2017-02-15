package com.hand.util;

/**
 * Created by Hand on 2017/2/7.
 */
public class Ascii {

    private static final String sign = "|";

    //字符转ASCII
    public static int charToAsc(char c){
        return (int)c;
    }

    //ASCII转字符
    public static char ascToChar(int asc){
        return (char)asc;
    }

    //字符蹿转ASCII数组
    public static int[] stringToAscs(String str){
        char[] chars = str.toCharArray();
        int[] ascs = new int[chars.length];
        for(int i=0; i<chars.length; i++){
            ascs[i] = (int)chars[i];
        }
        return ascs;
    }

    //字符串转ASCII组合字符串
    public static String stringToAscStr(String str){
        char[] chars = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<chars.length; i++){
            sb.append((int)chars[i]);
            if(i<chars.length-1){
                sb.append(sign);
            }
        }
        return sb.toString();
    }

    //ASCII组合字符串转字符串
    public static String ascStrToString(String ascStr){
        String[] ascs = ascStr.split(sign);
        StringBuffer sb = new StringBuffer();
        for(String asc:ascs){
            int ascNum = Integer.parseInt(asc);
            sb.append((char)ascNum);
        }
        return sb.toString();
    }
}
