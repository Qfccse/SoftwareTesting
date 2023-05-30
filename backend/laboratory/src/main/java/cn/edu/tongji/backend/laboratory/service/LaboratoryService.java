package cn.edu.tongji.backend.laboratory.service;

import cn.edu.tongji.backend.laboratory.mapper.LaboratoryMapper;
import cn.edu.tongji.backend.laboratory.pojo.Feedback;
import cn.edu.tongji.backend.laboratory.pojo.Laboratory;
import cn.edu.tongji.backend.laboratory.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaboratoryService {
    @Autowired
    private LaboratoryMapper laboratoryMapper;

    public Result<List<Laboratory>> getCourseLabs(int c_id){
        Result<List<Laboratory>> result = new Result<>();
        if (c_id < 420203100 || c_id > 420205000) {
            result.setMsg("课程编号不合法");
            result.setCode(400);
        }
        else {
            result.setCode(200);
        }
        result.setDetail(laboratoryMapper.selectCourseLabs(c_id));
        return result;
    }

    public Laboratory getLabById(int l_id){
        return laboratoryMapper.selectLabByID(l_id);
    }

    public Result addLab(Laboratory laboratory){
        Result result = new Result<>();
        if (laboratory.getC_id() < 420203100 || laboratory.getC_id() > 420205000) {
            result.setCode(400);
            result.setMsg("课程编号不合法");
            return result;
        }
        if (laboratory.getEnd_time().before(laboratory.getStart_time())) {
            result.setCode(400);
            result.setMsg("实验截止时间需晚于起始时间");
            return result;
        }
        if (laboratory.getDesc() == null) {
            result.setCode(400);
            result.setMsg("实验描述不能为空");
        }
        if (laboratory.getName().length() < 4 || laboratory.getName().length() > 30) {
            result.setCode(400);
            result.setMsg("实验名不规范（长度应在4-30之间）");
        }

        laboratoryMapper.insertIntoLab(laboratory);
        result.setCode(200);
        return result;
    }

    public Result updateLab(Laboratory laboratory){

        Result result = new Result<>();
        if (laboratory.getC_id() < 420203100 || laboratory.getC_id() > 420205000) {
            result.setCode(400);
            result.setMsg("课程编号不合法");
            return result;
        }
        if (laboratory.getEnd_time().before(laboratory.getStart_time())) {
            result.setCode(400);
            result.setMsg("实验截止时间需晚于起始时间");
            return result;
        }
        if (laboratory.getDesc() == null) {
            result.setCode(400);
            result.setMsg("实验描述不能为空");
        }
        if (laboratory.getName().length() < 4 || laboratory.getName().length() > 30) {
            result.setCode(400);
            result.setMsg("实验名不规范（长度应在4-30之间）");
        }

        laboratoryMapper.updateLab(laboratory);
        result.setCode(200);
        return result;
    }

    public void removeLab(int l_id){
        laboratoryMapper.deleteFromLab(l_id);
    }

    public Result createFeedback(Feedback feedback){
        Result result = new Result();
        if (feedback.getFeedback().equals("")) {
            result.setCode(400);
            result.setMsg("反馈不能为空");
            return result;
        }
        laboratoryMapper.insertFeedback(feedback);
        result.setCode(200);
        return result;
    }
}
