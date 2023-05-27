package cn.edu.tongji.backend.test.mapper;

import cn.edu.tongji.backend.test.pojo.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from user where u_id=#{u_id}")
    User selectUserById(String u_id);
}
