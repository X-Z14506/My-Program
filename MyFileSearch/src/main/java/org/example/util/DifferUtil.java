package org.example.util;

import org.example.model.FileMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



//处理数据库存在的文件和扫描到的文件的差集
public class DifferUtil {

    //找到文件1中有的而文件2中不存在的所有文件
    public static <E> List<E> differentSet(List<E> list1 , List<E> list2) {
        List<E> resultList = new ArrayList<>();
        for (E item : list1) {
            if (!list2.contains(item)) {//contains内部其实是调用的equals方法
                resultList.add(item);
            }
        }
        return resultList;
    }

    /*public static void main(String[] args) {

        List<FileMeta> list1 = Arrays.asList(
                new FileMeta(new File("F:\\java错误录\\编译错误1.docx")),
                new FileMeta(new File("F:\\java错误录\\编译错误2.txt")),
                new FileMeta(new File("F:\\java错误录\\运行错误1.docx"))//此文件1list1中存在而list2中不存在
        );
        List<FileMeta> list2 = Arrays.asList(
                new FileMeta(new File("F:\\java错误录\\编译错误1.docx")),
                new FileMeta(new File("F:\\java错误录\\编译错误2.txt")),
                new FileMeta(new File("F:\\java错误录\\编行错误2.docx"))//此文件list2中存在list1中不存在
        );
        System.out.println("list1中存在而list2中不存在的文件");
        System.out.println(differentSet(list1,list2));
        System.out.println("list2中存在而list1中不存在的文件");
        System.out.println(differentSet(list2,list1));
    }*/

}
