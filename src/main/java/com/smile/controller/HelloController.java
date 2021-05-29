package com.smile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 *
 * @author smilePlus
 * @version 1.0
 * @date 2021/3/4 16:59
 */
@Controller
public class HelloController {
    /**
     * TODO 测试页面跳转
     * @param model 数据
     * @author smilePlus
     * @date 2021/3/4 17:33
     * @return java.lang.String
     */
    @RequestMapping("/sayHello")
    public String sayHello(Model model) {
        model.addAttribute("name","xn");
        return "hello";
    }
}
