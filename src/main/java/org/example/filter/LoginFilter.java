package org.example.filter;

import org.example.model.JSONResponse;
import org.example.model.User;
import org.example.util.JSONUtil;
import sun.management.snmp.jvmmib.JVM_MANAGEMENT_MIBOidTable;

import javax.jws.soap.SOAPBinding;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2020-12-07
 * Time:22:17
 */


@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //访问/jsp/...如果没有登录，跳转/view/login.html
        //访问/login, 其他的前端资源，不校验session（登录，注册，注销）
        //其他的后端资源，校验session，没有登录返回401状态码，json字符串。通过doFilter
        String path = req.getServletPath();//Servlet路径
        if(path.startsWith("/js/") || path.startsWith("/static/")
                || path.startsWith("/view/") || path.equals("/login")){
            chain.doFilter(request, response);
        }else{
            HttpSession session = req.getSession(false);
            if(session == null){
                //跳转或返回json
                unauthorized(req, resp);
            }else{
                User user = (User) session.getAttribute("user");
                if(user == null){
                    //跳转或返回json
                    unauthorized(req, resp);
                }else{
                    chain.doFilter(request, response);
                }
            }
        }
    }

    public static void  unauthorized(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String schema = req.getScheme();//获取协议http
        String host = req.getServerName();//主机ip
        int port = req.getServerPort();//主机port
        String contextPath = req.getContextPath();//应用上下文路径
        String path = req.getServletPath();//Servlet路径
        resp.setCharacterEncoding("UTF-8");
        if(path.startsWith("/jsp/")){//前端敏感资源：跳转登录页面
            String basePath = schema+"://"+host+":"+port+contextPath;
            resp.sendRedirect(basePath+"/view/login.html");
        }else{//后端的敏感资源：状态码401，返回json
            resp.setContentType("application/json");
            resp.setStatus(401);
            JSONResponse json = new JSONResponse();
            json.setCode("LOG000");
            json.setMessage("用户没有登录，不允许访问");
            PrintWriter pw = resp.getWriter();
            pw.println(JSONUtil.serialize(json));
            pw.flush();
            pw.close();
        }
    }

    @Override
    public void destroy() {

    }
}

/*

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        //访问/jsp/...如果没有登录，跳转/view/login.html
        //访问/login，除了jsp外其他的前端资源，不校验session
        //访问其他的后端资源，校验session，如果没有登陆返回401状态码，并且为json字符串，如果校验通过，调用doFilter
        String schema =req.getScheme();//获取http协议
        String host = req.getServerName();//主机ip
        int port = req.getServerPort();//主机port
        String contextPath = req.getContextPath();//应用上下文路径
        String path = req.getServletPath();//Servlet路径
        if (path.startsWith("/js/")|| path.startsWith("/static/")
                || path.startsWith("/view/") || path.equals("/login/")) {
            chain.doFilter(request,response);
        }else {
            HttpSession session =req.getSession(false);
            if (session == null) {
                //跳转或返回json
            }else {
                User user = (User) session.getAttribute("user");
                if (user == null) {
                    //跳转或返回json
                }else {
                    chain.doFilter(request,response);
                }
            }
        }

        public static void unauthorized(HttpServletRequest req,HttpServletResponse resp) {

        }


    }

    @Override
    public void destroy() {

    }
}
*/
