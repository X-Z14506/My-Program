package org.example.servlet;

import org.example.dao.ArticleDAO;
import org.example.model.Article;
import org.example.model.User;
import org.example.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2020-12-07
 * Time:19:03
 */

@WebServlet("/articleAdd")
public class ArticleAddServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //通过session获取用户id
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");

        //请求数据类型为application/json，需要从输入流获取
        InputStream is = req.getInputStream();

//        OutputStream os = new FileOutputStream("F:\\java\\读到的请求体.txt");
//
//        byte[] buf = new byte[1024];
//        while (true) {
//            int n = is.read(buf);
//            if (n==-1) {
//                break;
//            }
//            os.write(buf,0,n);
//        }
//        os.close();

        //请求体数据存在于输入流，json格式需要反序列化
        Article article = JSONUtil.deserialize(is,Article.class);
        article.setUseId(user.getId());
        int num = ArticleDAO.insert(article);
        return null;
    }
}
