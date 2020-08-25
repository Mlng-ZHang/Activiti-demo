package com.zm.demo.utils;

/**
 * @Name: DateUtils
 * @Author: zhangming
 * @Date 2020/8/13 16:37
 * @Description:
 */
public class DateUtils {

    /**
     * 将 (s,m,h,d)转换为对应的时间毫秒
     * @param time
     * @return
     */
    public static long calculateExpireTime(String time){
        long num = Long.valueOf(time.replaceAll("[^0-9]",""));
        String unit = time.replaceAll("[^a-zA-Z]","");
        long expireTime = 0L;
        switch (unit){
            case "s":
                expireTime = num * 1000;break;
            case "m":
                expireTime = num * 60 * 1000;break;
            case "h":
                expireTime = num * 60 * 60 * 1000;break;
            case "d":
                expireTime = num * 24 * 60 * 60 * 1000;break;
            default:
                break;
        }
        return expireTime;
    }
}
