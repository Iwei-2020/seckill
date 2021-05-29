package com.smile.utils;
import com.smile.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author smilePlus
 * @description:
 * @date 2021/3/22 10:24
 */
@Component
public class CreateUserUtil {
    private static DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        CreateUserUtil.dataSource = dataSource;
    }

    public static void create(int count) throws SQLException {
        List<User> users = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setId(18300000000L + i + "");
            user.setNickname("");
            user.setSalt("1a2b3c4d");
            user.setPassword(MD5Util.formPassToDBPass("123456",user.getSalt()));
            Connection conn = dataSource.getConnection();
        }
    }
}
