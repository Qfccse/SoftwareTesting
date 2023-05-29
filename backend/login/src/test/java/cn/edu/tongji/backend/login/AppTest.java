package cn.edu.tongji.backend.login;

import cn.edu.tongji.backend.login.service.UserService;
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
    @ParameterizedTest
    @CsvFileSource(resources="/LoginCodeTest.csv", numLinesToSkip = 1)//数据文件的路径，可以根据自己的情况而定
    void testSelectReport(String u_id, String password, int code){
        assertEquals(userService.login(u_id,password).getErrorCode(),code);
    }

}
