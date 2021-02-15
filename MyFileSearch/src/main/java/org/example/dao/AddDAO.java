package org.example.dao;

import org.example.model.FileMeta;
import org.example.util.DBUtil;
import org.example.util.LogUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


/**
 * 最终用户，保存扫描出的文件列表
 */
public class AddDAO {
    public void addFile(List<FileMeta> fileList) {

        //记录添加了多少条数据，记录日志用
        int count = 0;
        String sql = "INSERT INTO file_meta (name,path,is_directory,pinyin,pinyin_first,size,last_modified) VALUES (?,?,?,?,?,?,?)";
        Connection c = DBUtil.getConnection();
        try (PreparedStatement s = c.prepareStatement(sql)){
            for (FileMeta file : fileList) {
                count++;
                //s.setInt(1,file.getId());
                s.setString(1,file.getName());
                s.setString(2,file.getPath());
                s.setBoolean(3,file.isDirectory());
                s.setString(4,file.getPinyin());
                s.setString(5,file.getPinyinFirst());
                s.setLong(6,file.getLength());
                s.setLong(7,file.getLastModifyTimeStamp());

                s.executeUpdate();
                LogUtil.log("执行SQL:%s,%s",sql,file);
            }
            LogUtil.log("共插入了%d条数据",count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //将文件存入List集合
        List<FileMeta> fileMetas = Arrays.asList(
            new FileMeta(new File("F:\\java错误录\\编译错误2.txt")),
            new FileMeta(new File("F:\\java错误录\\编译错误1.docx")),
            new FileMeta(new File("F:\\java错误录\\运行错误1.docx")),
            new FileMeta(new File("F:\\java错误录\\运行错误2.txt"))
        );
        AddDAO addDAO = new AddDAO();
        addDAO.addFile(fileMetas);
    }
}
