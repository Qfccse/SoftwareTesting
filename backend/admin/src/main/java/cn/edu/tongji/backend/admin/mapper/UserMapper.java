package cn.edu.tongji.backend.admin.mapper;


import cn.edu.tongji.backend.admin.pojo.Student;
import cn.edu.tongji.backend.admin.pojo.Teacher;
import cn.edu.tongji.backend.admin.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    @Insert("insert into user values (#{u_id},#{name},#{password},#{status},#{role},#{email})")
    void insertIntoUser(User user);

    @Select("select * from user where u_id=#{u_id}")
    User selectUserById(String u_id);


    @Select("select * from user")
    List<User> selectAllUser();

    @Delete("delete from user where u_id=#{u_id}")
    void deleteUser(String u_id);

}
