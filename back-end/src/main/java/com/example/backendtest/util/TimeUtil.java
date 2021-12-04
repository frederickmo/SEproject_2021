package com.example.backendtest.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于处理数据库时间的工具类
 */
public class TimeUtil {
    // 全局变量
    public static final String TIME_FULL_SPRIT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间转换成字符串
     *
     * @param date 日期
     * @param formartStr 格式
     * @return String
     */
    public static String dateToString(Date date, String formartStr) {
        String strDate = null;
        if (date != null && formartStr != null && !"".equals(formartStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat(formartStr);
            strDate = sdf.format(date);
        }
        return strDate;
    }

    /**
     * 获得当前的时间按Timestamp返回
     * @return Timestamp
     */
    public  static Timestamp getTimestampNow()
{
    String now = dateToString(new Date(), TIME_FULL_SPRIT);
    Timestamp time = Timestamp.valueOf(now );
    return time;
}

    /**
     * 时间戳比较函数
     * @param now
     * @param before
     * @return int 2表示时间相等 1表示前者早于后者 0表示前者晚于后者
     */
    public static  int TimestampNowBeforeEnd(Timestamp now,Timestamp before)
{
    if(now.getTime()==before.getTime())
    {
        return 2;
    }
    else
    {
        if(now.getTime()<before.getTime())
            return 1;
        else return 0;
    }
}

}
