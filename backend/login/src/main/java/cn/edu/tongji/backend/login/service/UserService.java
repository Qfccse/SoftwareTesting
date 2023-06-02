package cn.edu.tongji.backend.login.service;

import cn.edu.tongji.backend.login.pojo.Result;
import cn.edu.tongji.backend.login.pojo.User;
import cn.edu.tongji.backend.login.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 登录
     * @param u_id ,password
     * @return Result
     */
    public String checkPassword(String u_id, String password) {
        return userMapper.checkUserPassword(u_id, password);
    }

    public Result<User> findPassword(User user, String email, String code){

        Result<User> result = new Result<>();
        int [] tf = new int[3];
        for (int i = 0;i<3;i++)
            tf[i]=1;
        boolean etf=true;

        if (user.getU_id().length()!=5 && user.getU_id().length()!=7){
            tf[0]=2;
        }else if (countUser(user.getU_id())==0){
            tf[0]=3;
        }

        if (email==null || email.equals("")) {
            tf[1] = 2;
        } else if  (checkEmail(user.getEmail())==0) {
            tf[1]=3;
        }

        //        etf = Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
//        if (!etf)
//            tf[1]=2;

        if (code.length()!=6)
            tf[2]=2;
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//这个也行
        Matcher isNum = pattern.matcher(code);
        if (!isNum.matches()) {
            tf[2]=2;
        }
        String voCode = user.getCode();
        if (!code.equals(voCode) && tf[2]!=2)
            tf[2]=3;
        StringBuilder t = new StringBuilder(tf[0] + "");
        for (int i =1;i<3;i++)
            t.append(tf[i]);
        result.setOnehot(String.valueOf(t));
        return result;
    }

    public int countUser(String u_id){
        return userMapper.countUser(u_id);
    }

    public Result<User> login(String u_id, String password){
        System.out.println(u_id + " " + password);
        Result<User> result = new Result<>();
        try {
            if (countUser(u_id)==0)
            {
                result.setMsg("uid错误");
                result.setErrorCode(1);
            }
            else if (checkPassword(u_id,password)==null){
                result.setMsg("密码错误");
                result.setErrorCode(2);
            }
            else if(selectUserStatus(u_id)!=1){
                result.setMsg("未激活");
                result.setErrorCode(3);
            }
            else {
                result.setMsg("登陆成功");
                result.setErrorCode(0);
                result.setDetail(selectUserInfo(u_id));
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
    /**
     * 更改密码
     */
    public void changePassword(String u_id,String password_new){
        userMapper.updateUserPassword(u_id,password_new);
    }

    public int selectCountUser(String u_id,String password){
        return userMapper.selectCountUser(u_id,password);
    }

    public User selectUserInfo(String u_id){
        return userMapper.checkDetail(u_id);
    }

    public int selectUserStatus(String u_id){
        return userMapper.selectUserStatus(u_id);
    }

    public void updateUserEmailStatus(String u_id, String email,String password,int status){
        userMapper.insertEmailAndStatus(u_id, email, password,status);
    }

    public int checkEmail(String email){
        return userMapper.checkEmail(email);
    }

    public void updateEmail(String u_id, String email){
        userMapper.updateUserEmail(u_id, email);
    }
}
