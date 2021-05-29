package com.smile.config;

import com.smile.pojo.User;
import com.smile.service.UserService;
import com.smile.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author smilePlus
 * @description: 自定义用户参数
 * @date 2021/3/9 11:18
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /*
     * TODO
     * @param parameter
     * @author smilePlus
     * @date 2021/3/9 11:20
     * @return boolean 返回true执行下面的resolveArgument
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        String ticket = CookieUtil.readLoginToken(request);
        if (!StringUtils.hasLength(ticket)) {
            return null;
        }
        return userService.getUserByCookie(ticket,request,response);
    }
}
