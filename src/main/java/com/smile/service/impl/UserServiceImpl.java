package com.smile.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smile.exception.GlobalException;
import com.smile.mapper.UserMapper;
import com.smile.pojo.User;
import com.smile.service.UserService;
import com.smile.utils.CookieUtil;
import com.smile.utils.MD5Util;
import com.smile.vo.LoginVo;
import com.smile.vo.RespBean;
import com.smile.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author thePassionate
 * @since 2021-03-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private UserMapper userMapper;

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        // 使用注解解决下面的判断
        // if (!(StringUtils.hasLength(mobile) && StringUtils.hasLength(password))) {
        //     return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        // }
        // if (!PhoneFormatCheckUtils.isChinaPhoneLegal(mobile)) {
        //     return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        // }
        User user = userMapper.selectById(mobile);
        if (user == null) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
            // return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }else if (!MD5Util.formPassToDBPass(password,user.getSalt()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
            // return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        String ticket = IdUtil.simpleUUID();
        // httpServletRequest.getSession().setAttribute(ticket,user);
        // 将用户信息存入session中
        redisTemplate.opsForValue().set("user:"+ticket, user);
        // 生成cookies
        CookieUtil.writeLoginToken(httpServletRequest,httpServletResponse,ticket);
        return RespBean.success(ticket);
    }

    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.hasLength(userTicket)) {
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null) {
            CookieUtil.writeLoginToken(request, response, userTicket);
        }
        return user;
    }

    @Override
    public RespBean updateUserPassword(String userTicket, String password, HttpServletRequest request, HttpServletResponse response) {
        User user = getUserByCookie(userTicket, request, response);
        if (user == null) {
            throw new GlobalException(RespBeanEnum.USER_NOT_EXIST);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password, user.getSalt()));
        int result = userMapper.updateById(user);
        if (result == 1) {
            redisTemplate.opsForValue().set("user:"+userTicket, user);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.PASSWORD_MODIFY_FAIL);
    }
}
