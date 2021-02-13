package dao;

import java.sql.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2021-02-06
 * Time:16:56
 */

/*
创建一个单例类来辅助创建连接，关闭连接
 */
public class DBUtil {
    //volatile修饰的属性能保证主内存的可见性，代码执行顺序不会被重排序
    private static volatile DataSource dataSource = null; //管理数据源

    // 通过这个方法来创建 DataSource 的实例
    public static DataSource getDataSource() {
        //单例模式(双重校验+锁+volatile的饿汉模式)
        //加锁实际上会牺牲效率，所以不要每次都加锁，只要判断datasource不为空，就直接返回dataSource对象，保证效率
        if (dataSource == null) {
            // 此处的 synchronized 其实就是给当前这一小块加锁
            // synchronized 要么修饰一个方法，要么得传个对象，此处修饰整个方法的话锁的粒度太大
            synchronized (DBUtil.class) {
                if (dataSource == null) {
                    dataSource = new MysqlDataSource();
                    MysqlDataSource tmpDataSource = (MysqlDataSource) dataSource;
                    tmpDataSource.setURL("jdbc:mysql://localhost:3306/image_server_project?user=root&password=1450618603&useSSL=false&characterEncoding=UTF-8");
                }
            }
        }
        return dataSource;
    }

    //建立数据库连接方法
    public static Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
           throw new RuntimeException("数据库连接失败");
        }
    }

    //关闭数据库连接方法
    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

/*
public class DBUtil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/image_server_project?characterEncoding=utf8&useSSL=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1450618603";

    //volatile修饰的属性能保证主内存的可见性，代码执行顺序不会被重排序
    private static volatile DataSource datasource = null;

    public static DataSource getDatasource() {
        //单例模式(双重校验+锁+volatile的饿汉模式)
        //加锁实际上会牺牲效率，所以不要每次都加锁，只要判断datasource不为空，就直接返回dataSource对象，保证效率
        if (datasource == null) {
            synchronized (DBUtil.class) {
                if (datasource == null) {
                    datasource = new MysqlDataSource();
                    MysqlDataSource tempDataSource = (MysqlDataSource)datasource;
                    tempDataSource.setUrl(URL);
                    tempDataSource.setUser(USERNAME);
                    tempDataSource.setPassword(PASSWORD);
                }
            }
        }
        return datasource;
    }

    public static Connection getConnection()  {
        try {
            return getDatasource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //先打开的资源后关闭，类似于栈的出入顺序
    public static void close(Connection c, PreparedStatement s, ResultSet r) {
        try {
            if (r!=null) {
                r.close();
            }
            if (s!=null) {
                s.close();
            }
            if (c!=null) {
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
*/
