package cn.edu.tongji.backend.report;

import cn.edu.tongji.backend.report.pojo.Report;
import cn.edu.tongji.backend.report.service.ReportService;
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
@SpringJUnitConfig(ReportApplication.class)
public class AppTest
{
    @Autowired
    private ReportService reportService;

    /**
     * 等价类，前4个是有效等价类，后2个是无效等价类
     */
    @ParameterizedTest
    @CsvFileSource(resources="/ReportId.csv", numLinesToSkip = 1)//数据文件的路径，可以根据自己的情况而定
    void testSelectReport(Integer r_id, String s_id, int l_id){
        assertEquals(r_id,reportService.getReportId(l_id,s_id));
    }

    @ParameterizedTest
    @MethodSource("postReportDataProvider")
    void postReportTest(String msg, Report report) {
        System.out.println(report);
        // 这个测试应该是前两个通过，最后一个不通过
        assertEquals(msg, reportService.postReport(report).getMsg());
    }
    static String POST_JSON_FILE_PATH = "src/main/resources/postReport.json";
    static Stream<Arguments> postReportDataProvider() throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(POST_JSON_FILE_PATH)));
        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        return jsonArray.stream()
                .map(obj -> {
                    JSONObject jsonObj = (JSONObject) obj;
                    String msg = jsonObj.getString("msg");
                    Report report = jsonObj.getObject("Report",Report.class);
                    return Arguments.of(msg,report);
                });
    }

    @ParameterizedTest
    @MethodSource("getReportFillerDataProvider")
    void reportTest(String msg, int l_id, String s_id) {
        // 这个测试应该是前两个通过，最后一个不通过
        assertEquals(msg, reportService.getReportFiller(l_id, s_id).getMsg());
    }
    static String GET_JSON_FILE_PATH = "src/main/resources/getReportFiller.json";
    static Stream<Arguments> getReportFillerDataProvider() throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(GET_JSON_FILE_PATH)));
        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        return jsonArray.stream()
                .map(obj -> {
                    JSONObject jsonObj = (JSONObject) obj;
                    String msg = jsonObj.getString("msg");
                    int l_id = jsonObj.getIntValue("l_id");
                    String s_id = jsonObj.getString("s_id");
                    return Arguments.of(msg,l_id,s_id);
                });
    }

}
