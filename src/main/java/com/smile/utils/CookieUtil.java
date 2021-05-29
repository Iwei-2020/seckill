package com.smile.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {

    public final static String COOKIE_DOMAIN = "localhost";
    public final static String COOKIE_NAME = "smile_login_token";

    //从请求中读取cookie
    public static String readLoginToken(HttpServletRequest request){
        if (request != null) {
            Cookie[] cks = request.getCookies();
            if(cks != null){
                for(Cookie ck : cks){
                    log.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    if(StringUtils.equals(ck.getName(), COOKIE_NAME)){
                        log.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                        return ck.getValue();
                    }
                }
            }
        }
        return null;
    }

    //往响应中写cookie
    public static void writeLoginToken(HttpServletRequest httpServletRequest, HttpServletResponse response,String cookieValue){//这里的cookie的名字就是smile_login_token，而值就是token,这里的token就是sessionID
        Cookie cookie = new Cookie(COOKIE_NAME, cookieValue);
        //将cookie设置在根目录下面 // 设置cookies可以访问的请求
        if (httpServletRequest != null) {
            String domain = httpServletRequest.getServerName();
            if (!domain.equals("localhost")) {
                log.info("domain:{}", domain);
                // 设置cookies可以访问的域名.smile.com 表示 以.smile.com结尾的域名可以访问
                cookie.setDomain(COOKIE_DOMAIN);
            }
        }
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        //设置cookie的有效期，单位是秒(一年)
        //如果这个maxAge不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        cookie.setMaxAge(60*60*24*365);
        log.info("write cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
        response.addCookie(cookie);
    }

    //删除cookie(从请求中读，往响应中写,已经删除完了的)
    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(StringUtils.equals(cookie.getName(), COOKIE_NAME)){
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    log.info("del cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }

}