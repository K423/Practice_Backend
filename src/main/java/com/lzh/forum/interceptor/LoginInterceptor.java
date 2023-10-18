package com.lzh.forum.interceptor;

import com.lzh.forum.entity.ForumUser;
import com.lzh.forum.service.impl.ForumUserServiceImpl;
import com.lzh.forum.service.impl.LoginAndRegisterServiceImpl;
import com.lzh.forum.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;


/**
 * 登录拦截处理，在访问之前
 * 先进行登录验证，若有登录证明
 * 则通过访问连接 否则阻止访问
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
    private ForumUserServiceImpl forumUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String path = request.getRequestURI();
        log.info("访问拦截, path : {}", path);

//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            String headerValue = request.getHeader(headerName);
//            log.info(headerName + " : " + headerValue);
//        }


        //获取header的token参数
        String token = request.getHeader("token");
        log.info("登录校检开始 -> token: {}", token);
        if (token == null || token.isEmpty()){
            log.info("token 为空, 请求被拦截");
            //下面可以做异常处理
            return false;
        }

        //验证token 解析jwt
        Claims claims = jwtUtils.paresJwt(token);
        if (claims == null){
            log.warn("claims为空, token 无效, 请求被拦截");
            return false;
            //throw new CustomException(401, "请求被拦截");
        }else {
            //获取token中的=name值
            String name = (String) claims.get("sub");
            log.info("{}-用户登录", name);
            //获取用户数据
            ForumUser user = forumUserService.validate(name);
            if (user != null){
                log.info("用户为：" + user.getName());
                return true;
            }else {
                log.info("用户不存在....请注册！");
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("-------------LoginInterceptor-----------");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("LoginInterceptor 结束");
    }
}
