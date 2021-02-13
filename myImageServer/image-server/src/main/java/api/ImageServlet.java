package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Image;
import dao.ImageDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2021-02-07
 * Time:12:05
 */


public class ImageServlet extends HttpServlet {


    /**
     * 查询所有图片/指定图片的内容
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //通过URL中是否带有imageId参数来区分是查看所有图片还是指定图片
        //例如：URL为  /image/imageId=100 name则是查看imageId为100的图片信息
        //     如果URL后面的键值对都不存在或者只存在键无值，就查询所有图片信息
        String imageId = request.getParameter("imageId");
        if (imageId == null || imageId.equals("")) {
            //查看所有图片信息
            selectAll(request,response);
        }else {
            //查看指定图片信息
            selectById(imageId,response);
        }
    }

    /**
     *  查看指定图片信息
     * @param imageId
     * @param response
     */
    private void selectById(String imageId, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        //1.创建一个ImageDao对象，根据指定id在数据库中查找
        ImageDao imageDao = new ImageDao();
        Image image = imageDao.selectById(Integer.parseInt(imageId));

        //2.创建Gson对象，将查到的数据转换成JSON字符串格式，并且写给响应response对象
        Gson gson = new GsonBuilder().create();
        String jsonData = gson.toJson(image);

        //发送响应给前端
        PrintWriter writer = response.getWriter();
        writer.println(jsonData);
    }

    private void selectAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        ImageDao imageDao = new ImageDao();
        List<Image> images = imageDao.selectAll();
        Gson gson = new GsonBuilder().create();
        String jsonData = gson.toJson(images);
        PrintWriter writer = response.getWriter();
        writer.println(jsonData);
    }

    /**
     *  上传图片
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //1.获取图片信息，并存入数据库
        //  1.1需要创建一个factory对象和upload对象,为了获取到图片属性做的准备工作
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        //  1.2通过upload对象进一步解析请求（解析HTTP请求中的 body 部分）
        //  理论上来说，HTTP 支持一个请求中同时上传多个文件，FileItem 就代表上传的一个文件对象，多个文件用 List 组织
        List<FileItem> items = null;
        try {
             items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            //出现异常说明解析出错
            e.printStackTrace();
            //告诉客户端出现的具体错误信息
            PrintWriter writer = response.getWriter();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            writer.write("{\"ok\":false \"reason\":\"上传图片请求解析失败\"}");
            return;
        }
        //   1.3将FileItem中的对象提取出来转换成Image对象，才能存到数据库中
        FileItem fileItem = items.get(0);
        Image image = new Image();
        image.setImageName(fileItem.getName());
        image.setSize((int)fileItem.getSize());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        image.setUploadTime(time);
        image.setContentType(fileItem.getContentType());
        //计算md5的值
        image.setMd5(DigestUtils.md5Hex(fileItem.get()));// md5 值是十六进制的字符串 图片内容相同时则md5值相同，图片内容不同时，md5值不同
        //自定义存储路径，加上数据保证不会出现连续插入两张图片报错，因为每时每刻时间戳是唯一的
        image.setPath("./image/"+image.getMd5());

        //存入数据库
        ImageDao imageDao = new ImageDao();
        //看看数据库中是否存在相同的md5值的图片，不存在则返回null
        Image existImage = imageDao.selectByMD5(image.getMd5());
        imageDao.insert(image);

        //2.获取图片的内容信息，如果相册中不存在该图片，写入磁盘文件中
        if (existImage==null) {
            File file = new File(image.getPath());
            try {
                fileItem.write(file);
            } catch (Exception e) {
                e.printStackTrace();
                //出现异常说明写入文件失败
                PrintWriter writer = response.getWriter();
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json");
                writer.write("{\"ok\":false \"reason\":\"图片内容写入磁盘失败\"}");
                return;
            }
        }

        response.sendRedirect("index.html");
    }

    /**
     *  删除指定图片
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.先获取请求中的imageId
        String imageId = req.getParameter("imageId");
        if (imageId==null || imageId.equals("")) {
            resp.setStatus(200);
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write("{\"ok\":\"false\" \"reason\":\"解析请求失败\"}");
            return;
        }

        //2.创建ImageDao对象，查看到该图片对象对应的相关属性，若存在则要删除
        //  既要删除数据库中的内容，也要删除磁盘中存储的对应图片，因此要知道该 imageId 对应的图片对象的存储路径
        ImageDao imageDao = new ImageDao();
        Image image = imageDao.selectById(Integer.parseInt(imageId));
        if (image == null) {
            //说明请求中传入的id在数据库中不存在
            resp.setStatus(200);
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write("{\"ok\":\"false\" \"reason\":\"数据库中不存在该数据\"}");
            return;
        }

        //3.删除数据库中的记录
        imageDao.delete(Integer.parseInt(imageId));

        // 4.删除本地磁盘文件（注意：数据库中可能有多张相同的图片，但是磁盘中只写入了一张图片，删除要注意）
        //   刚刚已经从数据库中删除了那张图片，若此时数据库中还存有相同的图片，那么不应该删除磁盘图片文件
        //   相同图片的 md5 值是相同的
        Image existImage = imageDao.selectByMD5(image.getMd5());
        // 如果删除完毕后，数据库中没有相同的图片了，那么就删除磁盘文件，否则就不删除
        if (existImage == null) {
            File file = new File(image.getPath());
            file.delete();
            resp.setStatus(200);
        }
    }
}
