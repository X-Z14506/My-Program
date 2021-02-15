package org.example.dao;

import org.example.model.FileMeta;
import org.example.util.DBUtil;
import org.example.util.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class QueryDAO {
    public List<FileMeta> query(String keyword) {
        String sql = "SELECT id,name,pinyin,pinyin_first,path,is_directory,size,last_modified FROM file_meta WHERE name LIKE ? OR pinyin LIKE ? OR pinyin_first LIKE ?";
        Connection c = DBUtil.getConnection();
        try (PreparedStatement s = c.prepareStatement(sql)){
            s.setString(1,"%"+keyword+"%");
            s.setString(2,"%"+keyword+"%");
            s.setString(3,"%"+keyword+"%");

            LogUtil.log("执行SQL:%s,%s",sql,keyword);

            List<FileMeta> result = new ArrayList<>();
            try (ResultSet r = s.executeQuery()){
                while (r.next()) {
                    int id = r.getInt("id");
                    String name = r.getString("name");
                    String pinyin = r.getString("pinyin");
                    String pinyinFirst = r.getString("pinyin_first");
                    String path = r.getString("path");
                    boolean directory = r.getBoolean("is_directory");
                    long length = r.getLong("size");
                    long lastModified = r.getLong("last_modified");

                    FileMeta fileMeta = new FileMeta(id,name,pinyin,pinyinFirst,path,directory,length,lastModified);
                    result.add(fileMeta);
                }

                LogUtil.log("一共查询出%d个文件",result.size());
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FileMeta> queryPath(String searchPath) {
        String sql = "SELECT id,name,pinyin,pinyin_first,path,is_directory,size,last_modified FROM file_meta WHERE path LIKE ? ";
        Connection c = DBUtil.getConnection();
        try (PreparedStatement s = c.prepareStatement(sql)){
            s.setString(1,searchPath+"%");

            LogUtil.log("执行SQL:%s,%s",sql,searchPath);

            List<FileMeta> result = new ArrayList<>();
            try (ResultSet r = s.executeQuery()){
                while (r.next()) {
                    int id = r.getInt("id");
                    String name = r.getString("name");
                    String pinyin = r.getString("pinyin");
                    String pinyinFirst = r.getString("pinyin_first");
                    String path = r.getString("path");
                    boolean directory = r.getBoolean("is_directory");
                    long length = r.getLong("size");
                    long lastModified = r.getLong("last_modified");

                    FileMeta fileMeta = new FileMeta(id,name,pinyin,pinyinFirst,path,directory,length,lastModified);
                    result.add(fileMeta);
                }

                LogUtil.log("一共查询出%d个文件",result.size());
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        QueryDAO queryDAO = new QueryDAO();
        List<FileMeta> list = queryDAO.query("");
        for (FileMeta fileMeta : list) {
            System.out.println(fileMeta);
        }
    }
}
