package org.example.service;

import org.example.dao.AddDAO;
import org.example.dao.DeleteDAO;
import org.example.dao.QueryDAO;
import org.example.model.FileMeta;
import org.example.task.FileScanner;
import org.example.util.DifferUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



/**
 *  主要提供给文件扫描时，对比扫描结果和DB中的结果差集时使用
 */
public class FileService {

    private final AddDAO addDAO = new AddDAO();
    private final DeleteDAO deleteDAO = new DeleteDAO();
    private final QueryDAO queryDAO = new QueryDAO();

    //扫描完成后，扫描得到的结果和已经保存在数据库的结果进行对比
    public void service(String path, List<FileMeta> scanResultList) {
        //根据路径找到目前数据库存了哪些文件
        List<FileMeta> queryResultList = queryDAO.queryPath(path);

        //1.删除数据库有的，但是扫描出来的结果集中没有的数据
        List<FileMeta> ds1 = DifferUtil.differentSet(queryResultList,scanResultList);
        deleteFileList(ds1);
        System.out.println("ds1的数据为:"+ds1);

        //2.添加数据库中没有，但是扫描出来的结果集中有的文件
        List<FileMeta> ds2 = DifferUtil.differentSet(scanResultList,queryResultList);
        addFileList(ds2);
        System.out.println("ds2的数据为:"+ds2);
    }


       /*public static void main(String[] args) {
        //扫描到的结果集
        FileScanner fileScanner = new FileScanner();
        List<FileMeta> list = fileScanner.scanner2(new File("F:\\java错误录"));
        for (FileMeta fileMeta : list) {
            System.out.println(fileMeta);
        }
        //查询到的结果集
        QueryDAO queryDAO = new QueryDAO();
        List<FileMeta> list1 = queryDAO.queryPath("F:\\java\\EditPlus\\新建文件夹\\day05 作业");
        for (FileMeta fileMeta : list1) {
            System.out.println(fileMeta);
        }*/

/* List<FileMeta> list2 = DifferUtil.differentSet(list1,list);
        System.out.println("list2数据为："+list2);
        for (FileMeta fileMeta : list2) {
            System.out.println(fileMeta);
        }*//*

        FileService fileService = new FileService();
        fileService.service("F:\\java\\EditPlus\\新建文件夹\\day05 作业",list);

    }
*/

    private void addFileList(List<FileMeta> fileList) {
        System.out.println(fileList);
        addDAO.addFile(fileList);
    }

    //文件扫描时，处理结果和DB中结果差集时使用，用于删除文件
    private void deleteFileList(List<FileMeta> fileList) {
        List<Integer> idList = fileList.stream().map(FileMeta::getId).collect(Collectors.toList());
        System.out.println(idList);
        deleteDAO.delete(idList);
    }

    public List<FileMeta> query(String keyword) {
        return queryDAO.query(keyword);
    }




}
