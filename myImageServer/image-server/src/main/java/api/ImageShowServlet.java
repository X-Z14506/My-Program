package api;

import dao.Image;
import dao.ImageDao;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.FileSystems;
import java.util.HashSet;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2021-02-07
 * Time:12:05
 */

public class ImageShowServlet extends HttpServlet {
    //基于白名单的防盗链机制
    static private HashSet<String> whiteList = new HashSet<>();
    //白名单
    static {
        whiteList.add("http://localhost:8080/index.html");
        whiteList.add("http://localhost:8080/image_server/index.html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //防盗链功能，因为服务器同时访问人太多容易崩溃
        String referer = request.getHeader("Referer");
        if (!whiteList.contains(referer)) {
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write("{ \"ok\":false,\"reason\":\"该网站未授权访问\" }");
            return;
        }

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        //1.解析出imageId
        String imageId = request.getParameter("imageId");
        if (imageId==null || imageId.equals("")) {
            PrintWriter writer = response.getWriter();
            writer.write("{\"ok\":\"false\" \"reason\":\"数据解析失败\"}");
            return;
        }
        //2.根据imageId查找数据库，得到对应的图片属性信息（需要知道图片存储的路径）
        ImageDao imageDao = new ImageDao();
        Image image = imageDao.selectById(Integer.parseInt(imageId));
        //3.根据路径打开文件，读取其中的内容，写入到响应体中
        response.setContentType(image.getContentType());
        File file = new File(image.getPath());

        //由于图片是二进制文件，因此使用字节流方式读取
        FileInputStream fileInputStream = new FileInputStream(file);
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int len = fileInputStream.read(buffer);
            if (len == -1) {
                //文件读取完毕
                break;
            }
            //此时已经读到一部分数据，在buffer中存着，应该写到响应对象中
            outputStream.write(buffer);
        }
        fileInputStream.close();
        outputStream.close();
    }

}
