package org.example.dao;

import jdk.nashorn.internal.scripts.JD;
import org.example.exception.AppException;
import org.example.model.Article;
import org.example.util.JDBCUtil;
import org.example.util.JSONUtil;

import javax.sql.rowset.JdbcRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2020-12-07
 * Time:12:06
 */
public class ArticleDAO {
    public static List<Article> queryByUserId(int userId) {
        List<Article> articles = new ArrayList<>();
        //1.创建一个Connection的对象
        Connection c = null;
        //2.创建一个语句处理对象
        PreparedStatement ps = null;
        //3.创建一个结果集对象
        ResultSet rs = null;
        try {
            //4.得到一个数据库的连接对象并赋值给C
            c = JDBCUtil.getConnection();
            //5.创建一个查询语句
            String sql = "select id, title, content, user_id, create_time, view_count from article where user_id=?";
            //6.将创建的查询语句放入语句处理对象中
            ps = c.prepareStatement(sql);

            //7.设置占位符的值
            ps.setInt(1,userId);
            //8.对数据库进行查询并将结果赋值给rs
            rs =ps.executeQuery();
            //9.遍历结果集
            while (rs.next()) {
                Article a =new Article();
                a.setId(rs.getInt("id"));
                a.setTitle(rs.getString("title"));
                articles.add(a);
            }
            return articles;
        }catch (Exception e) {
            //这个是自己定义的异常对象
            throw new AppException("ART001","查询文章列表出错",e);
        }finally {
            //10.无论如何关闭创建的3个对象
            JDBCUtil.close(c,ps,rs);
        }
    }

    public static int delete(String[] ids) {
        Connection c = null;
        PreparedStatement ps = null;
        try{
            c = JDBCUtil.getConnection();
            StringBuilder sql = new StringBuilder("delete from article where id in(");
            for(int i = 0; i<ids.length;i++) {
                if (i!=0) {
                    sql.append(",");
                }
                sql.append("?");
            }
            sql.append(")");
            ps = c.prepareStatement(sql.toString());

            //设置占位符：替换值
            for (int i = 0;i<ids.length;i++) {
                ps.setInt(i+1,Integer.parseInt(ids[i]));
            }
            return ps.executeUpdate();
        }catch (Exception e) {
            throw new AppException("ART004","删除文章出错",e);
        }finally {
            JDBCUtil.close(c,ps);
        }
    }

    public static int insert(Article article) {
        Connection c = null;
        PreparedStatement ps = null;
        try{
            c = JDBCUtil.getConnection();
            String sql = "insert into article(title,content,user_id) values (?,?,?)";
            ps = c.prepareStatement(sql);
            ps.setString(1,article.getTitle());
            ps.setString(2,article.getContent());
            ps.setInt(3,article.getUseId());
            return ps.executeUpdate();
        }catch (Exception e) {
            throw new AppException("ART005","插入文章操作出错",e);
        }finally {
            JDBCUtil.close(c,ps);
        }
    }

    public static Article query(int id) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            c= JDBCUtil.getConnection();
            String sql ="select title , content from article where id = ?";
            ps = c.prepareStatement(sql);
            //设置占位符，替换值
            ps.setInt(1,id);
            rs = ps.executeQuery();
            Article article = null;
            while (rs.next()) {
                article = new Article();
                article.setId(id);
                article.setTitle(rs.getString("title"));
                article.setContent(rs.getString("content"));
            }
            return article;
        }catch (Exception e) {
            throw new AppException("ART006","查询文章详情出错",e);
        }finally {
            JDBCUtil.close(c,ps,rs);
        }
    }

    public static int update(Article article) {
        Connection c = null;
        PreparedStatement ps = null;
        try{

            c = JDBCUtil.getConnection();
            String sql = "update article set title =?,content=? where id=?";
            ps = c.prepareStatement(sql);
            ps.setString(1,article.getTitle());
            ps.setString(2,article.getContent());
            ps.setInt(3,article.getId());
            return ps.executeUpdate();
        }catch (Exception e) {
            throw new AppException("ART007", "修改文章操作出错",e);
        }finally {
            JDBCUtil.close(c,ps);
        }
    }
}
