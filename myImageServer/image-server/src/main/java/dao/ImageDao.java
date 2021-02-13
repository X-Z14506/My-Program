package dao;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2021-02-06
 * Time:17:48
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *  用来对数据库的增删改查的一个类
 *  JDBC的操作流程
 *     1.获取数据库连接
 *     2.创建并拼装SQL语句
 *     3.执行SQL语句
 *     4.关闭结果集resultSet对象、关闭statement对象，关闭连接
 */



public class ImageDao {

    /**
     * 把一个 Image 对象插入到数据库中
     */
    public boolean insert(Image image){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            //1.获取数据库连接
            connection = DBUtil.getConnection();

            //2.创建并拼接sql语句
            String sql = "insert into image_table values(null,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,image.getImageName());
            statement.setInt(2,image.getSize());
            statement.setString(3,image.getUploadTime());
            statement.setString(4,image.getContentType());
            statement.setString(5,image.getPath());
            statement.setString(6,image.getMd5());

            //3.执行sql语句
            int ret = statement.executeUpdate();
            if (ret!=1) {
                throw new RuntimeException("插入数据库出错");
            }else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //4.关闭资源
            DBUtil.close(connection,statement,null);
        }
        return false;
    }


    /**
     *  查找数据库中所有图片的属性信息，并存储在 List 列表中
     * @return 所有图片信息
     */
    public List<Image> selectAll(){
        List<Image> images = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet= null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from image_table";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Image image = new Image();
                image.setImageId(resultSet.getInt("imageId"));
                image.setImageName(resultSet.getString("imageName"));
                image.setSize(resultSet.getInt("size"));
                image.setUploadTime(resultSet.getString("uploadTime"));
                image.setContentType(resultSet.getString("contentType"));
                image.setPath(resultSet.getString("path"));
                image.setMd5(resultSet.getString("md5"));

                images.add(image);
            }
            return images;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭结果集，statement对象和连接
            DBUtil.close(connection,statement,resultSet);
        }
        return null;//出错的话返回空的结果集
    }


    /**
     * 根据 imageId 查找指定图片的属性信息
     * @param imageId
     * @return 返回根据id查找到的图片信息
     */
    public Image selectById(int imageId){
        Connection  connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from image_table where imageId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,imageId);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Image image = new Image();
                image.setImageId(resultSet.getInt("imageId"));
                image.setImageName(resultSet.getString("imageName"));
                image.setSize(resultSet.getInt("size"));
                image.setUploadTime(resultSet.getString("uploadTime"));
                image.setContentType(resultSet.getString("contentType"));
                image.setPath(resultSet.getString("path"));
                image.setMd5(resultSet.getString("md5"));
                return image;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            // 5.关闭连接和statement对象
           DBUtil.close(connection,statement,resultSet);
        }
        return null;  //如果失败的话返回null
    }


    /**
     * 根据 imageId 删除指定图片
     * @param imageId
     */
    public void delete(int imageId){
        Connection connection = null;
        String sql = "delete  from image_table where imageId = ?";
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1,imageId);
            int ret = statement.executeUpdate();
            if (ret != 1) {
                throw  new RuntimeException("删除数据库异常");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,statement,null);
        }
    }

    //根据MD5的值在数据库中查找数据
    public Image selectByMD5(String md5) {
        Connection  connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from image_table where md5 = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,md5);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Image image = new Image();
                image.setImageId(resultSet.getInt("imageId"));
                image.setImageName(resultSet.getString("imageName"));
                image.setSize(resultSet.getInt("size"));
                image.setUploadTime(resultSet.getString("uploadTime"));
                image.setContentType(resultSet.getString("contentType"));
                image.setPath(resultSet.getString("path"));
                image.setMd5(resultSet.getString("md5"));
                return image;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            // 5.关闭连接和statement对象
            DBUtil.close(connection,statement,resultSet);
        }
        return null;  //如果失败的话返回null
    }


    public static void main(String[] args) {

        //测试插入数据是否正确
        Image image = new Image();
        image.setImageName("1.png");
        image.setSize(100);
        image.setUploadTime("20210206");
        image.setContentType("image/png");
        image.setPath("./data/1.png");
        image.setMd5("11223344");
        ImageDao imageDao = new ImageDao();
        imageDao.insert(image);

    /*    //测试查找所有图片信息功能
        ImageDao imageDao = new ImageDao();
        List<Image> list = imageDao.selectAll();
        //迭代器遍历
        Iterator<Image> iterator = list.iterator();
        while (iterator.hasNext()) {
            Image image = iterator.next();
            System.out.println(image);
        }*/

        //测试根据id删除图片信息的功能
        //ImageDao imageDao = new ImageDao();
     /*   imageDao.delete(9);
        List<Image> list = imageDao.selectAll();
        Iterator<Image> iterator = list.iterator();
        while (iterator.hasNext()) {
            Image image = iterator.next();
            System.out.println(image);
        }*/

        //测试id查找图片信息的功能
        /*Image image = imageDao.selectById(10);
        System.out.println(image);
        Image image1 = imageDao.selectById(9);
        System.out.println(image1);*/
    }
}
