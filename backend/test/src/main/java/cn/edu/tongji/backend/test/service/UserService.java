package cn.edu.tongji.backend.test.service;

import cn.edu.tongji.backend.test.mapper.UserMapper;
import cn.edu.tongji.backend.test.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectUserById(String u_id){
        return userMapper.selectUserById(u_id);
    }
}
