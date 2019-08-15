package com.zhenghe.kindofdemo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zh on 2019/8/15.
 */
public class TimeUtil {
    /**
     * 获取当天0点的毫秒值
     * @return
     */
    public long getTodayZeroMillis(){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //当天0点
        long zero = calendar.getTimeInMillis();
        return zero;
    }

    /**
     * 获取0点的毫秒值
     * @param day 根据传入的时间去计算，0表示当前零点，1表示后一天，-1表示前一天
     * @return
     */
    public long getEveryZeroMollis(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long zero = calendar.getTimeInMillis();
        return zero;
    }

    /**
     * 格式化毫秒形式的时间
     * @param l
     * @return
     */
    public static String getSimpleTime(long l) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


        Date date = new Date(l);

        String format = formatter.format(date);
        return format;
    }
}
