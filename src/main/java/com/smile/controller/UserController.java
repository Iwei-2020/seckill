package com.smile.controller;


import com.smile.pojo.User;
import com.smile.rabbitmq.MsgSender;
import com.smile.service.UserService;
import com.smile.utils.CookieUtil;
import com.smile.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author thePassionate
 * @since 2021-03-04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private MsgSender msgSender;


    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user) {
        return RespBean.success(user);
    }

    @RequestMapping("/update")
    public RespBean updateUserPassword(String password,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        String userTicket = CookieUtil.readLoginToken(request);
        return userService.updateUserPassword(userTicket, password, request, response);
    }

    /**
     * TODO 测试RabbitMQ
     * @author smilePlus
     * @date 2021/3/28 11:02
     */
    @RequestMapping("/mq")
    @ResponseBody
    public void mq() {
        msgSender.sendMsgByFanout("Hello World!");
    }

    /**
     * TODO Fanout模式
     * @author smilePlus
     * @date 2021/3/28 11:02
     */
    @RequestMapping("/mq/fanout")
    @ResponseBody
    public void fanout() {
        msgSender.sendMsgByFanout("hello fanout");
    }


    /**
     * TODO Fanout模式
     * @author smilePlus
     * @date 2021/3/28 11:02
     */
    @RequestMapping("/mq/direct01")
    @ResponseBody
    public void direct01() {
        msgSender.sendMsgByDirect("hello red", "queue.red");
    }

    /**
     * TODO Fanout模式
     * @author smilePlus
     * @date 2021/3/28 11:02
     */
    @RequestMapping("/mq/direct02")
    @ResponseBody
    public void direct02() {
        msgSender.sendMsgByDirect("hello green", "queue.green");
    }

    /**
     * TODO Fanout模式
     * @author smilePlus
     * @date 2021/3/28 11:02
     */
    @RequestMapping("/mq/topic01")
    @ResponseBody
    public void topic01() {
        msgSender.sendMsgByTopic("hello green", "queue.green.red");
    }

    /**
     * TODO Fanout模式
     * @author smilePlus
     * @date 2021/3/28 11:02
     */
    @RequestMapping("/mq/topic02")
    @ResponseBody
    public void topic02() {
        msgSender.sendMsgByTopic("hello green", "good.queue.green.xxx");
    }
    

    @Autowired
    public void setMsgSender(MsgSender msgSender) {
        this.msgSender = msgSender;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}

