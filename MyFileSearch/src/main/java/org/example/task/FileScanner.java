package org.example.task;

import org.example.model.FileMeta;
import org.example.service.FileService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


//发生点击事件后，进入此类对文件夹进行扫描
public class FileScanner {
    private final FileService fileService = new FileService();
  /*  //int count = 0;
    public  void scanner(File rootFile) {
        if (!rootFile.isDirectory()) {
            return;
        }
        File[] childFile = rootFile.listFiles();
        if (childFile == null) {
            return;
        }
        List<FileMeta> scanResult = new ArrayList<>();
        for (File child:childFile) {
            scanner(child);
            if (child.isFile()) {
                //count++;
                scanResult.add(new FileMeta(child));
            }
        }

        //System.out.println(scanResult);
        //System.out.println(count);

        //扫描完成，扫描到的结果集和保存在数据库中的结果集进行对比
        fileService.service(rootFile.getParentFile().getAbsolutePath(),scanResult);
    }*/

    public void scan(File root) {
        scanDir(root);
    }

    private void scanDir(File root) {
        if (!root.isDirectory()) {
            return;
        }

        File[] children = root.listFiles();
        if (children == null) {
            return;
        }

        List<FileMeta> scanResult = new ArrayList<>();
        for (File child : children) {
            scanDir(child);
            if (child.isFile()) {
                scanResult.add(new FileMeta(child));
            }
        }

        //扫描完成，扫描到的结果集和保存在数据库中的结果集进行对比
        fileService.service(root.getParentFile().getAbsolutePath(),scanResult);
    }




   /* public  List<FileMeta>  scanner2(File rootFile) {
        if (!rootFile.isDirectory()) {
            return null;
        }
        File[] childFile = rootFile.listFiles();
        if (childFile == null) {
            return null;
        }
        List<FileMeta> scanResult = new ArrayList<>();
        for (File child:childFile) {
            scanner2(child);
            if (child.isFile()) {
                //count++;
                scanResult.add(new FileMeta(child));
            }
        }
        return scanResult;*/

        //System.out.println(scanResult);
        //System.out.println(count);

        //扫描完成，扫描到的结果集和保存在数据库中的结果集进行对比
        //fileService.service(rootFile.getAbsolutePath(),scanResult);
    //}

    /*public static void main(String[] args) {
        FileScanner scanner = new FileScanner();
        List<FileMeta> list = scanner.scanner2(new File("F:\\java错误录"));
        int count = 0;
        for (FileMeta fileMeta : list){
            count++;
            System.out.println(fileMeta);
        }
        System.out.println(count);
    }*/

}
