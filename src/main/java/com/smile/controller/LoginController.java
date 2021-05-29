package com.smile.controller;

import com.smile.service.UserService;
import com.smile.vo.LoginVo;
import com.smile.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * TODO
 *
 * @author smilePlus
 * @version 1.0
 * @date 2021/3/4 20:54
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo,
                            HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse) {
        return userService.doLogin(loginVo,httpServletRequest,httpServletResponse);
    }
}
