package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2021-02-10
 * Time:17:44
 */
//图片服务器项目----单元测试
public class ImageDaoTest {
    //在执行某一个测试方法前都会执行
    @Before
    public void before() {
        System.out.println("=====开始测试======");
    }
    //在执行某次测试方法后会执行
    @After
    public void after() {
        System.out.println("=====测试结束======");
    }

    //忽略测试该方法
    @Ignore
    @Test
    public void insert() {
        ImageDao imageDao = new ImageDao();
        Image image = new Image();
        image.setImageName("test.png");
        image.setSize(1024);
        image.setUploadTime("2021-02-10 17:55:01");
        image.setPath("./image/9f44c38db7342195d7d287159f79fab9");
        image.setContentType("image/png");
        image.setMd5("708595c05102e4dd399416717cb6304d");
        boolean flag = imageDao.insert(image);
        System.out.println("result:"+flag);
    }

    @Test
    public void selectAll() {
        ImageDao imageDao = new ImageDao();
        List<Image> list = imageDao.selectAll();
        System.out.println(list.size());
        for (Image image : list) {
            System.out.println(image);
        }
    }

    @Test
    public void selectById() {
        ImageDao imageDao = new ImageDao();
        //id存在情况
        Image image = imageDao.selectById(3);
        System.out.println(image);
        //id不存在情况
        Image image1 = imageDao.selectById(100);
        System.out.println(image1);
    }

    @Test
    public void delete() {
        ImageDao imageDao = new ImageDao();
        //id存在的情况
        imageDao.delete(16);
        //id不存在的情况
        imageDao.delete(18);
    }

    @Test
    public void selectByMD5() {
        ImageDao imageDao = new ImageDao();
        //md5值存在的情况
        Image image = imageDao.selectByMD5("9f44c38db7342195d7d287159f79fab9");
        System.out.println(image);
        //md5值不存在的情况
        Image image1 = imageDao.selectByMD5("test");
        System.out.println(image1);
    }
}