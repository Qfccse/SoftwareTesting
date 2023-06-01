package cn.edu.tongji.backend.report.service;

import cn.edu.tongji.backend.report.mapper.ReportTemplateMapper;
import cn.edu.tongji.backend.report.pojo.ReportTemplate;
import cn.edu.tongji.backend.report.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static cn.edu.tongji.backend.report.utils.ReportStatusCode.*;

@Service
public class ReportTemplateService {
    @Autowired
    private ReportTemplateMapper reportTemplateMapper;

    public Result<Integer> insertReportTemplate(ReportTemplate reportTemplate) {
        Result<Integer> result = new Result<>();
        if(reportTemplate.getT_id().length()!=5){
            result.setMsg("t_id格式错误");
            result.setCode(ERROR_TID);
            return result;
        }
        if(reportTemplate.getTitle().isEmpty()){
            result.setMsg("单元标题不能为空");
            result.setCode(ERROR_EMPTY_TITLE);
            return result;
        }
        if(!Objects.equals(reportTemplate.getType(), "text") && !Objects.equals(reportTemplate.getType(), "image")){
            result.setMsg("错误的单元类型");
            result.setCode(ERROR_UNSUPPORTED_FORM_TYPE);
            return result;
        }

        if(!reportTemplateMapper.selectAllLabId().contains(reportTemplate.getL_id())){
            result.setMsg("错误的l_id");
            result.setCode(ERROR_LID_NOT_FOUND);
            return result;
        }

        if(reportTemplateMapper.checkIfTeaches(reportTemplate.getL_id(), reportTemplate.getT_id())==0){
            result.setMsg("授课教师不匹配");
            result.setCode(ERROR_UNFITTED_TEACHER);
            return result;
        }


        reportTemplateMapper.insertIntoReportTemplate(reportTemplate);
        result.setMsg("创建成功");
        result.setCode(SUCCESS);
        return result;
    }

    public List<ReportTemplate> selectLabReportTemplate(int l_id){
        return reportTemplateMapper.getLabReportTemplate(l_id);
    }

    public int selectReportTemplateCount(int l_id){
        return reportTemplateMapper.selectTemplateCount(l_id);
    }
}
