package cn.edu.tongji.backend.report.service;

import cn.edu.tongji.backend.report.mapper.ReportFormMapper;
import cn.edu.tongji.backend.report.pojo.ReportForm;
import cn.edu.tongji.backend.report.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static cn.edu.tongji.backend.report.utils.ReportStatusCode.*;

@Service
public class ReportFormService {
    @Autowired
    private ReportFormMapper reportFormMapper;

    public void insertReportForm(ReportForm reportForm) {
        reportFormMapper.insertIntoReportForm(reportForm);
    }

    public void updateReportForm(ReportForm reportForm){
        reportFormMapper.updateReportForm(reportForm);
    }
    public List<ReportForm> selectLabReportForm(int r_id){
        return reportFormMapper.getLabReportForm(r_id);
    }

    public List<String> selectLabReportImages(int rf_id){
        return reportFormMapper.getReportImages(rf_id);
    }

    public Result<Integer> insertImage(int rf_id,String path,String file_name){
        Result<Integer> result = new Result<>();
        if(file_name.length()<5){
            result.setCode(ERROR_EMPTY_FILE_NAME);
            result.setMsg("文件名错误");
            return result;
        }
        try {
            String suffix = file_name.split("\\.")[1];
            String[] array = {"jpg", "png", "jpeg", "bmp"};
            List<String> list = Arrays.asList(array);
            if(!list.contains(suffix.toLowerCase())){
                result.setCode(ERROR_IMAGE_SUFFIX);
                result.setMsg("文件后缀错误");
                return result;
            }
        }
        catch (Exception e)  {
            result.setCode(ERROR_IMAGE_SUFFIX);
            result.setMsg("文件后缀错误");
            return result;
        }

        String type = reportFormMapper.selectReportFormType(rf_id);
        if(type==null|| !type.equals("image")){
            result.setCode(ERROR_TEMPLATE_NOT_MATCH);
            result.setMsg("该条目不支持图片");
            return result;
        }
        String timestampString = path.split("\\.")[0];
        //System.out.println(timestampString);
        //System.out.println(timestampString.length());
        if (timestampString.length() == 13) {
            try {
                long timestamp = Long.parseLong(timestampString);
                long currentTime = System.currentTimeMillis();
                // 判断是否大于当前时间戳
                if (timestamp > currentTime) {
                    System.out.println("时间超前");
                    result.setCode(ERROR_PATH_FORM);
                    result.setMsg("保存路径错误");
                    return result;
                }
            } catch (NumberFormatException e) {
                System.out.println("不是时间");
                result.setCode(ERROR_PATH_FORM);
                result.setMsg("保存路径错误");
                return result;
            }
        } else {
            System.out.println("时间错误");
            result.setCode(ERROR_PATH_FORM);
            result.setMsg("保存路径错误");
            return result;
        }


        reportFormMapper.insertIntoReportImages(rf_id,path,file_name);

        result.setMsg("添加成功");
        result.setCode(SUCCESS);
        return result;
    }

    public int checkFormExist(int r_id, int fr_id){
        return reportFormMapper.selectCountReportFormCheck(r_id, fr_id);
    }

    public int selectRfTd(int r_id, int fr_id){
        return reportFormMapper.selectRfId(r_id, fr_id);
    }
}
