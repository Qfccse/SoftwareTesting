package cn.edu.tongji.backend.course.service;

import cn.edu.tongji.backend.course.CourseApplication;
import cn.edu.tongji.backend.course.service.CourseService;
import cn.edu.tongji.backend.course.mapper.*;
import cn.edu.tongji.backend.course.pojo.Course;
import cn.edu.tongji.backend.course.pojo.Teaches;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitConfig(CourseApplication.class)
class CourseServiceTest {
    @Autowired
    private CourseService courseService;

    //数据文件的路径，可以根据自己的情况而定
    @ParameterizedTest
    @CsvFileSource(resources="/CourseTest.csv", numLinesToSkip = 1)//数据文件的路径，可以根据自己的情况而定
    void testAddCourse(String t_id, int c_id, int code){
        Course course = new Course();
        course.setC_id(c_id);
        Teaches teaches = new Teaches();
        teaches.setT_id(t_id);
        assertEquals(courseService.addCourse(course, teaches).getMap().get("code"), code);
//        assertEquals(userService.login(u_id,password).getErrorCode(),code);
    }

    @ParameterizedTest
    @CsvFileSource(resources="/TeacherTest.csv", numLinesToSkip = 1)//数据文件的路径，可以根据自己的情况而定
    void testGetCoursesAsTeacher(String t_id, int code) {
        assertEquals(courseService.getCoursesAsTeacher(t_id).getMap().get("code"), code);
    }

    @ParameterizedTest
    @CsvFileSource(resources="/StudentTest.csv", numLinesToSkip = 1)//数据文件的路径，可以根据自己的情况而定
    void testGetCoursesAsStudent(String s_id, int code) {
        assertEquals(courseService.getCoursesAsStudent(s_id).getMap().get("code"), code);
    }
}