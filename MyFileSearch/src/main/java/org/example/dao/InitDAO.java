package org.example.dao;

import org.example.util.DBUtil;
import org.example.util.LogUtil;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.sql.*;
import java.util.Scanner;


/*
找到init.sql文件，并且读取其内容，获取sql
得到一组sql语句String[]
init.sql十一组以";"作为间隔的多个sql语句
 */
public class InitDAO {
    private String[] getSQLs() {
        try (InputStream is = InitDAO.class.getClassLoader().getResourceAsStream("init.sql")){
            StringBuilder sb = new StringBuilder();
            Scanner sc = new Scanner(is,"UTF-8");
            while (sc.hasNextLine()) {
                String sql = sc.nextLine();
                sb.append(sql);
            }
            return sb.toString().split(";");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void init() {
        try {
            Connection c = DBUtil.getConnection();
            String[] sqls = getSQLs();
            for (String sql : sqls) {
                LogUtil.log("执行SQL:%s",sql);
                try (PreparedStatement s = c.prepareStatement(sql)){
                    if (sql.toUpperCase().startsWith("SELECT ")) {
                        //如果sql已select开头，则执行jdbc的查询逻辑
                        try (ResultSet r = s.executeQuery()){
                            ResultSetMetaData metaData = r.getMetaData();
                            int columnCount = metaData.getColumnCount();
                            //记录查询到多少行
                            int rowCount = 0;
                            while (r.next()) {
                                StringBuilder messages = new StringBuilder("|");
                                for (int i = 1;i < columnCount;i++) {
                                    String value = r.getString(i);
                                    messages.append(value).append("|");
                                }
                                LogUtil.log(messages.toString());
                                rowCount++;
                            }
                            LogUtil.log("总共查询出%d行",rowCount);
                        }
                    }else {
                        //走到这里说明sql是增或删或改，则进入jdbc增删改逻辑
                        int i = s.executeUpdate();
                        LogUtil.log("受到影响的一共有%d行",i);
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        InitDAO initDAO = new InitDAO();
        initDAO.init();
    }
}
