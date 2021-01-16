import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2020-12-19
 * Time:14:35
 */


public class JDBCTest {

    public static void main(String[] args) throws SQLException {
        select("张三");
    }
    //创建数据库连接池
    static DataSource dataSource = new MysqlDataSource();

    public static  void select(String name) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ((MysqlDataSource) dataSource).setUrl("jdbc:mysql://localhost:3306/db2020?useUnicode=true&useSSL=false&characterEncoding=UTF-8");
            ((MysqlDataSource) dataSource).setPassword("1450618603");
            ((MysqlDataSource) dataSource).setUser("root");

            //第一步：创建数据库连接
            c = dataSource.getConnection();
            System.out.println(c);
            String sql = "select id ,who,what from messages where who like ?";

            //第二步：创建了操作命令对象（是、带占位符？的sql再数据库中预编译，可以提高效率，占位符的方式，可以防止sql注入）
            ps = c.prepareStatement(sql);

            //替换占位符
            ps.setString(1,"%"+name+"%");
            System.out.println(ps);

            //第三步：执行sql
            rs = ps.executeQuery();

            //第四步：处理结果集
            while (rs.next()) {
                int id = rs.getInt("id");
                String who = rs.getString("who");
                String what = rs.getString("what");
                System.out.printf("id = %s,who = %s,what = %s%n",id,who,what);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放资源
            try {
                //释放资源的顺序严格按照从小到大的顺序，先关闭结果集，再关闭操作对象，最后关闭数据库连接
                if (rs!=null)
                    rs.close();
                if (ps!=null)
                    ps.close();
                if (c!=null)
                    c.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
