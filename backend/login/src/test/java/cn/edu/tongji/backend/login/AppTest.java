package cn.edu.tongji.backend.login;

import cn.edu.tongji.backend.login.pojo.User;
import cn.edu.tongji.backend.login.service.UserService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
/**
 * Unit test for simple App.
 */

@SpringBootTest
@SpringJUnitConfig(LoginApplication.class)
public class AppTest
{
    @Autowired
    private UserService userService;

    /**
     * 等价类，前2个是有效等价类，后2个是无效等价类
     */
    // _Serv_Login_001
    @ParameterizedTest
    @CsvFileSource(resources="/LoginCodeTest.csv", numLinesToSkip = 1)//数据文件的路径，可以根据自己的情况而定
    void testLogin1(String u_id, String password, int code){
        assertEquals(userService.login(u_id,password).getErrorCode(),code);
    }
    /**
     * 边界值，前2个是有效等价类，后2个是无效等价类
     */
    @ParameterizedTest
    @CsvFileSource(resources="/LoginCodeTest2.csv", numLinesToSkip = 1)//数据文件的路径，可以根据自己的情况而定
    void testLogin2(String u_id, String password, int code){
        assertEquals(userService.login(u_id,password).getErrorCode(),code);
    }
    @ParameterizedTest
    @MethodSource("userDataProvider")
    void userInfoTest(String u_id, User user) {
        assertEquals(user, userService.selectUserInfo(u_id));
    }
    static String USER_JSON_FILE_PATH = "src/main/resources/UserInfo.json";
    static Stream<Arguments> userDataProvider() throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(USER_JSON_FILE_PATH)));
        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        return jsonArray.stream()
                .map(obj -> {
                    JSONObject jsonObj = (JSONObject) obj;
                    String id = jsonObj.getString("u_id");
                    User user = jsonObj.getObject("User",User.class);
                    return Arguments.of(id,user);
                });
    }

}
