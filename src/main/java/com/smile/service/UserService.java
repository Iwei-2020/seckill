package com.smile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smile.pojo.User;
import com.smile.vo.LoginVo;
import com.smile.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author thePassionate
 * @since 2021-03-04
 */
public interface UserService extends IService<User> {

    /**
     * TODO 登录
     *
     * @param loginVo             登录临vo
     * @param httpServletRequest  request
     * @param httpServletResponse response
     * @return com.smile.vo.RespBean
     * @author smilePlus
     * @date 2021/3/5 19:22
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    /**
     * TODO 根据cookie从redis中获取数据
     *
     * @param userTicket cookies(uuid)
     * @return com.smile.pojo.User
     * @author smilePlus
     * @date 2021/3/9 10:03
     */
    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);

    RespBean updateUserPassword(String userTicket, String password, HttpServletRequest request, HttpServletResponse response);
}
