package cn.edu.tongji.backend.course.service;

import cn.edu.tongji.backend.course.mapper.*;
import cn.edu.tongji.backend.course.pojo.*;
import cn.edu.tongji.backend.course.pojo.info.CourseAndRole;
import cn.edu.tongji.backend.course.pojo.tools.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeachesMapper teachesMapper;

    @Autowired
    private LaboratoryMapper laboratoryMapper;

    @Autowired
    private TakesMapper takesMapper;

    @Autowired
    private TodoMapper todoMapper;

//    public List<Course> getCourses() {
//        return courseMapper.getAllCourses();
//    }

    private Message returnMessage(int code, String msg){
        Message message = new Message();
        message.set("code", code);
        message.set("msg", msg);
        // 0成功 1课程id错误 2教师id错误
        return message;
    }

    public Message getAllCourses() {
        Message message = new Message();
        message.set("coursesList", courseMapper.getAllCourses());
        return message;
    }

    public Message addCourse(Course course, Teaches teaches) {
        int code = 0;
        // course
        int courseID = course.getC_id();
        if (courseID<420203100 | courseID>420205000){
            code += 1;
        }
        courseMapper.addCourse(course); //测试时不使用

        //teach
        teaches.setC_id(courseID);
        teaches.setRole("1");
        String teachID = teaches.getT_id();
        if (teachID.length()!=5){
            code += 2;
        }

        teachesMapper.addTeaches(teaches); //测试时不使用
        Message message = new Message();
        message.set("course", course);
        message.set("teaches", teaches);
        message.set("code", code);
        message.set("msg", "成功");
        return message;
    }

    //查询课程
    public Message getCoursesAsTeacher(String id) {
        int code = 0;
        if (id.length()!=5){
            code += 1;
        }
        Message message = new Message();
        List<CourseAndRole> coursesAsHT = new ArrayList<>();
        List<CourseAndRole> coursesAsTeacher = new ArrayList<>();

        for (CourseAndRole c : courseMapper.getAllCoursesAsTeacher(id)) {
            if (c.getRole().equals("1")) {
                coursesAsHT.add(c);
            }
            else {
                coursesAsTeacher.add(c);
            }
        }

        message.set("CoursesAsHeadTeacher", coursesAsHT);
        message.set("CoursesAsTeacher", coursesAsTeacher);
        message.set("code", code);

        return message;
    }
    public Message getCoursesAsStudent(String id) {
        int code = 0;
        if (id.length()!=7){
            code += 1;
        }

        Message message = new Message();
        List<CourseAndRole> coursesAsStudent = new ArrayList<>();
        List<CourseAndRole> coursesAsTA = new ArrayList<>();

        for (CourseAndRole c : courseMapper.getAllCoursesAsStudent(id)) {
            if (c.getRole().equals("4")) {
                coursesAsStudent.add(c);
            }
            else {
                coursesAsTA.add(c);
            }
        }

        message.set("CoursesAsStudent", coursesAsStudent);
        message.set("CoursesAsTA", coursesAsTA);
        message.set("code", code);
        return message;
    }

    public Result<Course> updateCourse(Course course) {
        Result result = new Result<>();
        int [] tf = new int[4];
        if (course.getC_id()<420203100 || course.getC_id() > 420205000)
        {
            if (course.getC_id()<420203100)
                tf[0]=1;
            else 
                tf[0]=2;
        }
        if (course.getName().length()<4 || course.getName().length()>20){
            if (course.getName().length()<4)
                tf[1]=1;
            else 
                tf[1]=2;
        }
        if (course.getStatus() == 1)
            tf[2]=0;
        else if (course.getStatus() == 2) {
            tf[2]=1;
        } else if (course.getStatus() == 0) {
            tf[2]=2;
        }
        if (course.getDesc() == null){
            tf[3]=1;
        }
        StringBuilder t = new StringBuilder(tf[0] + "");
        for (int i =1;i<4;i++)
            t.append(tf[i]);

        result.setOnehot(String.valueOf(t));
        // courseMapper.updateCourse(course); // not in test
        return result;
    }

    public Result<Laboratory> updateLab(Laboratory laboratory) {
        Result result = new Result<>();

        laboratoryMapper.updateLab(laboratory);
        result.setCode(300);
        return result;
    }

    public boolean deleteCourse(int id) {
        Course course = courseMapper.getById(id);
        if (course.getStatus() == 2) {
            courseMapper.deleteById(id);
            return true;
        }

        return false;
    }

    public Course getCourseById(int c_id) {
        return courseMapper.getById(c_id);
    }


    // 不用测、重复了
    public Message getLabsByCid(int c_id) {
        Message message = new Message();
        message.set("labs", laboratoryMapper.getLabsByCid(c_id));
        return message;
    }

    public Message getFacultyByCid(int c_id) {
        Message message = new Message();
        message.set("teachers", teachesMapper.getTeachersByCid(c_id));
        message.set("students", takesMapper.getStudentsByCid(c_id));

        return message;
    }


    public Message getFeedbackAsTeacher(int c_id) {
        Message message = new Message();
        message.set("feedbacks", laboratoryMapper.getFeedbackAsTeacher(c_id));
        return message;
    }

    public Message getFeedbackAsStudent(int c_id, String s_id) {
        Message message = new Message();
        message.set("feedbacks", laboratoryMapper.getFeedbackAsStudent(c_id, s_id));
        return message;
    }

    public Message getAllTodo() {
        Message message = new Message();

        message.set("todos", todoMapper.getAllTodo());
        return message;
    }

    public Message getTodoByCid(int c_id) {
        Message message = new Message();
        message.set("todos", todoMapper.getTodoByCid(c_id));
        return message;
    }

    public Message addTodo(Todo todo) {
        Timestamp curtime = new Timestamp(System.currentTimeMillis());
//        System.out.println(time1);

        todo.setEnd_time(curtime);
        todo.setStart_time(curtime);

        todoMapper.addTodo(todo);
        return new Message();
    }



}
