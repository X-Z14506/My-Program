package org.example.util;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;



//数据库连接类--使用单例--懒汉模式（双重校验锁）
public class DBUtil {
    private static volatile DataSource dataSource = null;

    private static Connection initConnection() {
        if (dataSource == null) {
            synchronized (DBUtil.class) {
                if (dataSource == null) {
                    initDataSource();
                }
            }
        }
        //Connection对象是线程不安全的--每个线程必须拥有自己的Connection对象
        //一个线程只有一个Connection对象就不存在线程安全问题了
        //使用ThreadLocal实现，每个线程都有自己的Connection对象
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //线程局部变量ThreadLocal，用于实现线程内的数据共享，即一个线程共享一份数据，另一个线程共享另外一份数据，互不干扰
    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
          return initConnection();
        }
    };

    public static Connection getConnection() {
        return connectionThreadLocal.get();
    }

    //jdbc操作连接数据库
    private static void initDataSource() {

        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        String url = "jdbc:sqlite://"+getDatabasePath();
        sqLiteDataSource.setUrl(url);
        dataSource = sqLiteDataSource;
    }

    //获取文件路径
    private static String getDatabasePath() {
        try{
            String classesPath = DBUtil.class.getProtectionDomain()
                    .getCodeSource().getLocation().getFile();
            File classDir = new File(URLDecoder.decode(classesPath,"UTF-8"));
            String path = classDir.getParent() + File.separator+"searcher.db";
            LogUtil.log("数据库文件路径为:%s",path);
            return path;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


}
