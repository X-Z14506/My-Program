package org.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;


//自定义打印日志类
public class LogUtil {
    //此处可变参数列表类型之所以用Object超类，是因为这样可变参数可以为任意类型，增加程序的健壮性
    public static void log(String format, Object... args) {
        String messages = String.format(format,args);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String nowTime = simpleDateFormat.format(date);
        System.out.printf("%s:%s\n",nowTime,messages);
    }

    public static void main(String[] args) {
        log("zdy学号是%d",123);
        log("%dhaha%d %s",1,2,"zdy");
    }
}

