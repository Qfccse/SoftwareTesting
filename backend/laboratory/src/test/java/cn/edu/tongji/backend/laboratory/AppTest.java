package cn.edu.tongji.backend.laboratory;
//
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//
import cn.edu.tongji.backend.laboratory.LaboratoryApplication;
import cn.edu.tongji.backend.laboratory.pojo.Feedback;
import cn.edu.tongji.backend.laboratory.pojo.Laboratory;
import cn.edu.tongji.backend.laboratory.pojo.Result;
import cn.edu.tongji.backend.laboratory.service.LaboratoryService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.testng.annotations.Test;
//

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Unit test for simple App.
 */

@SpringBootTest
@SpringJUnitConfig(LaboratoryApplication.class)
public class AppTest
{
    @Autowired
    private LaboratoryService laboratoryService;

    /**
     * 等价类，前4个是有效等价类，后2个是无效等价类
     */
    @ParameterizedTest
    @MethodSource("jsonDataProvider")//数据文件的路径，可以根据自己的情况而定
    void testSelectCourseLabs(int c_id, Result result){
        Assertions.assertEquals(result, laboratoryService.getCourseLabs(c_id));
    }

    static String JSON_FILE_PATH = "src/main/resources/CourseLabs.json";
    static Stream<Arguments> jsonDataProvider() throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        return jsonArray.stream()
                .map(obj -> {
                    JSONObject jsonObj = (JSONObject) obj;
                    int c_id = jsonObj.getIntValue("c_id");
                    Result<List<Laboratory>> laboratories = jsonObj.getObject("result", new TypeReference<Result<List<Laboratory>>>(){});
                    return Arguments.of(c_id, laboratories);
                });
    }

    @ParameterizedTest
    @MethodSource("jsonDataProvider1")//数据文件的路径，可以根据自己的情况而定
    void testAddLab(Laboratory laboratory, Result result){
        Assertions.assertEquals(result, laboratoryService.addLab(laboratory));
    }

    static String JSON_FILE_PATH1 = "src/main/resources/AddLab.json";
    static Stream<Arguments> jsonDataProvider1() throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH1)));
        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        return jsonArray.stream()
                .map(obj -> {
                    JSONObject jsonObj = (JSONObject) obj;
                    Laboratory laboratory = jsonObj.getObject("laboratory", Laboratory.class);
                    Result result = jsonObj.getObject("result", Result.class);
                    return Arguments.of(laboratory, result);
                });
    }

    @ParameterizedTest
    @MethodSource("jsonDataProvider2")//数据文件的路径，可以根据自己的情况而定
    void testUpdateLab(Laboratory laboratory, Result result){
        Assertions.assertEquals(result, laboratoryService.updateLab(laboratory));
    }

    static String JSON_FILE_PATH2 = "src/main/resources/UpdateLab.json";
    static Stream<Arguments> jsonDataProvider2() throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH2)));
        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        return jsonArray.stream()
                .map(obj -> {
                    JSONObject jsonObj = (JSONObject) obj;
                    Laboratory laboratory = jsonObj.getObject("laboratory", Laboratory.class);
                    Result result = jsonObj.getObject("result", Result.class);
                    return Arguments.of(laboratory, result);
                });
    }

    @ParameterizedTest
    @MethodSource("jsonDataProvider3")//数据文件的路径，可以根据自己的情况而定
    void testCreateFeedback(Feedback feedback, Result result){
        Assertions.assertEquals(result, laboratoryService.createFeedback(feedback));
    }

    static String JSON_FILE_PATH3 = "src/main/resources/CreateFeedback.json";
    static Stream<Arguments> jsonDataProvider3() throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH3)));
        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        return jsonArray.stream()
                .map(obj -> {
                    JSONObject jsonObj = (JSONObject) obj;
                    Feedback feedback = jsonObj.getObject("feedback", Feedback.class);
                    Result result = jsonObj.getObject("result", Result.class);
                    return Arguments.of(feedback, result);
                });
    }
}

