package cn.edu.tongji.backend.report;

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

}
