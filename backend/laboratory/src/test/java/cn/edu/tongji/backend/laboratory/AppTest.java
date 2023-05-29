package cn.edu.tongji.backend.laboratory;
//
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//
import cn.edu.tongji.backend.laboratory.pojo.Laboratory;
import cn.edu.tongji.backend.laboratory.service.LaboratoryService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
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
    void testSelectCourseLabs(int c_id, List<Laboratory> laboratories){
        Assertions.assertEquals(laboratories, laboratoryService.getCourseLabs(c_id));
    }

    static String JSON_FILE_PATH = "src/main/resources/CourseLabs.json";
    static Stream<Arguments> jsonDataProvider() throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        return jsonArray.stream()
                .map(obj -> {
                    JSONObject jsonObj = (JSONObject) obj;
                    int c_id = jsonObj.getIntValue("c_id");
                    List<Laboratory> laboratories = jsonObj.getJSONArray("laboratories").toJavaList(Laboratory.class);
                    return Arguments.of(c_id, laboratories);
                });
    }
}

