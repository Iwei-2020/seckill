package com.smile;

import cn.hutool.core.io.file.FileAppender;
import cn.hutool.http.HttpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smile.pojo.User;
import com.smile.service.UserService;
import com.smile.utils.MD5Util;
import com.smile.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@Slf4j
class SeckillDemo2ApplicationTests {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    void contextLoads() {
    }


    @Test
    void createUser() throws IOException {
        // int count = 400;
        // List<User> users = new ArrayList<>(count);
        // for (int i = 0; i < count; i++) {
        //     User user = new User();
        //     user.setId(18300000000L + i + "");
        //     user.setNickname("user" + i);
        //     user.setSalt("1a2b3c4d");
        //     user.setPassword(MD5Util.inputPassToDBPass("123456",user.getSalt()));
        //     users.add(user);
        // }
        // userService.saveBatch(users);
        List<User> users = userService.list();
        File file = new File("C:\\Users\\93970\\Desktop\\config.txt");
		FileAppender appender =
				new FileAppender(file, 50, true);
		for (User user : users) {
			String urlString = "http://localhost:9123/login/doLogin";
			HashMap<String,Object> paramMap = new HashMap<String, Object>(2) {
				{
					put("mobile", user.getId());
					put("password", MD5Util.inputPassToFormPass("123456"));
				}
			};
			String result = HttpRequest.post(urlString)
					.form(paramMap) //表单内容
					.timeout(20000) //超时，毫秒
					.execute().body();
			ObjectMapper objectMapper = new ObjectMapper();
			RespBean respBean = objectMapper.readValue(result, RespBean.class);
			String row = user.getId() + "," + respBean.getData().toString();
			appender.append(row);
			System.out.println("write user：" + user.getId());
		}
		appender.flush();
    }
}
