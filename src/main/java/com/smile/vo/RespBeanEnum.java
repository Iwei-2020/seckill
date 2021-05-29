package com.smile.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public enum RespBeanEnum {
    // 通用
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "服务端异常"),
    // 登录模块500xxx
    LOGIN_ERROR(500001,"用户名或者密码不正确"),
    MOBILE_ERROR(500002,"手机号码格式不正确"),
    BIND_ERROR(500003,"参数校验异常:"),
    USER_UN_LOGIN(500004, "用户未登录异常"),
    // 秒杀模块501xxx
    STOCK_LACK(501001,"商品库存不足"),
    REPEAT_ERROR(501002, "该商品每人限购一件"),
    // 修改用户信息模块502xxx
    USER_NOT_EXIST(502001,"用户不存在"),
    PASSWORD_MODIFY_FAIL(502002,"密码修改失败");
    private Integer code;
    private String message;
}
