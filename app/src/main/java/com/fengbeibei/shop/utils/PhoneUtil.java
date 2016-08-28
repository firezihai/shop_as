package com.fengbeibei.shop.utils;


/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-28 17:07
 */
public class PhoneUtil {
    public static String addStarFormat(String phone,int num){
        String startStr = phone.substring(0,3);
        String endStr = phone.substring(phone.length()-4,phone.length());
        String star = "";
        num = num <=0 ? 4 : num;
        while (num>0){
            star += "*";
            num--;
        }
        return startStr+star+endStr;
    }

    public static String addHyphen(String phone,String hyphen){
        String startStr = phone.substring(0,3);
        String middleStr = phone.substring(3,7);
        String endStr = phone.substring(phone.length()-4,phone.length());
        return startStr+hyphen+middleStr+hyphen+endStr;
    }
    public static String delHyphen(String phone,String hyphen){
        return phone.replace(hyphen,"");
    }
}
