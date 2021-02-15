package org.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;


//统一文件大小和文件修改日期的显示格式
public class OutputUtil {
    public static String formatLength(Long length) {
        if (length < 1024) {
            return length+"Byte";
        }
        if (length < 1024*1024) {
            return (length/1024)+"KB";
        }
        if (length < 1024*1024*1024){
            return (length/1024/1024)+"MB";
        }
        return (length / 1024 /1024 /1024)+"GB";
    }

    public static String formatTimeStamp(Long lastModifyTimeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(lastModifyTimeStamp);
        return format.format(date);
    }

  /*  public static void main(String[] args) {
        String s = formatLength((long)188321180);
        System.out.println(s);
    }*/
}
