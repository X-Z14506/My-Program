package org.example.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.example.exception.AppException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2020-12-06
 * Time:23:04
 */
public class JDBCUtil {

    private static final DataSource DS = new MysqlDataSource();

    //多线程学了之后，会优化双重校验锁的单例模式来使用DataSource
    static {
        ((MysqlDataSource) DS).setUrl("jdbc:mysql://localhost:3306/chong_blog?user=root&password=1450618603&useUnicode=true&useSSL=false&characterEncoding=UTF-8");
    }

    //获取数据库连接
    public static Connection getConnection() {
        try {
            return DS.getConnection();
        }catch (SQLException e) {
            throw new AppException("JDBC001","获取数据库链接失败",e);
        }
    }

    //释放资源
    public static void close(Connection c, Statement s) {
        close(c,s,null);
    }
    public static void close(Connection c, Statement s, ResultSet r) {
        try {
            if (r!=null){
                r.close();
            }
            if (s!=null) {
                s.close();
            }
            if (c!=null) {
                c.close();
            }
        } catch (SQLException e) {
            throw new AppException("JDBC002","释放数据库资源失败",e);
        }
    }
}
