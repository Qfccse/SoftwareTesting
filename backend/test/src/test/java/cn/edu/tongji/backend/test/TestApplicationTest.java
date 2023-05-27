package cn.edu.tongji.backend.test;

import cn.edu.tongji.backend.test.pojo.User;
import cn.edu.tongji.backend.test.service.UserService;
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



@SpringBootTest
@SpringJUnitConfig(TestApplication.class)
public class TestApplicationTest {
    public static Triangle triangle;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        triangle = new Triangle();
    }
    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    // 直接传参
    static Stream<Arguments> testDataProvider() {
        return Stream.of(
                Arguments.of(50, 50, 1, "等腰三角形"),
                Arguments.of(50, 50, 2, "等腰三角形"),
                Arguments.of(50, 50, 50, "等边三角形"),
                Arguments.of(50, 50, 99, "等腰三角形")
        );
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    void testWithMethodSource(int a, int b, int c, String tri) {
        assertEquals(tri, triangle.judgeTriangle(a, b, c));
    }

    // 读取csv
    @ParameterizedTest
    @CsvFileSource(resources="/TriangleData.csv", numLinesToSkip = 1)//数据文件的路径，可以根据自己的情况而定
    void testWithCsvFileSource(int a,int b,int c,String tri) {
        assertEquals(tri,triangle.judgeTriangle(a,b,c));
    }

    // 直接写csv
    @ParameterizedTest
    @CsvSource({
           "50,50,1,等腰三角形",
            "50,50,2,等腰三角形",
            "50,50,50,等边三角形",
            "50,50,99,等腰三角形"
    })
    void testWithCsvSource(int a,int b,int c,String tri) {
        assertEquals(tri,triangle.judgeTriangle(a,b,c));
    }

    // 简单数据的json
    @ParameterizedTest
    @MethodSource("jsonDataProvider")
    void testWithJsonSource(int a, int b, int c, String tri) {
        assertEquals(tri, triangle.judgeTriangle(a, b, c));
    }

    static String JSON_FILE_PATH = "src/main/resources/TriangleData.json";
    static Stream<Arguments> jsonDataProvider() throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        return jsonArray.stream()
                .map(obj -> {
                    JSONObject jsonObj = (JSONObject) obj;
                    int a = jsonObj.getIntValue("a");
                    int b = jsonObj.getIntValue("b");
                    int c = jsonObj.getIntValue("c");
                    String tri = jsonObj.getString("tri");
                    return Arguments.of(a, b, c, tri);
                });
    }

    // 对象的json
    // 假设执行一个函数，它是从数据库中select 一个 user
    @Autowired
    private UserService userService;
    @ParameterizedTest
    @MethodSource("userDataProvider")
    void userTest(String id,User user) {
        // System.out.println(user);
        // 这个测试应该是前两个通过，最后一个不通过
        assertEquals(user, userService.selectUserById(id));
    }
    static String USER_JSON_FILE_PATH = "src/main/resources/UserTest.json";
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






