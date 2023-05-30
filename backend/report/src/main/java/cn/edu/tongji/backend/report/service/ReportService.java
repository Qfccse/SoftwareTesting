package cn.edu.tongji.backend.report.service;

import cn.edu.tongji.backend.report.mapper.ReportFormMapper;
import cn.edu.tongji.backend.report.mapper.ReportMapper;
import cn.edu.tongji.backend.report.mapper.ReportTemplateMapper;
import cn.edu.tongji.backend.report.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportMapper reportMapper;

    public void insertReport(Report report){
        System.out.println(report);
        reportMapper.insertIntoReport(report);
    }

    public Integer getReportId(int l_id, String s_id){
        return reportMapper.selectIdFromReport(l_id, s_id);
    }

    public int getReportCount(int l_id, String s_id){
        return reportMapper.selectCountFromReport(l_id, s_id);
    }

    public String[] getReportFormImage(int rf_id){
        return reportMapper.selectImagesFromReportImage(rf_id);
    }

    public void updateReportStatus(Report report){
        reportMapper.updateReport(report);
    }

    public Report selectStuReport(int l_id, String s_id){
        return reportMapper.selectReportById(l_id,s_id);
    }

    public List<Report> selectLabAllReport(int l_id){
        return reportMapper.selectLabReport(l_id);
    }

    public String selectStuName(String s_id){
        return reportMapper.getStuNameByID(s_id);
    }


    // 以下是对report的测试

    // test1
    public Result<Integer> postReport(Report report){
        Result<Integer> result = new Result<>();
        if(report.getName().length()<=4){
            result.setCode(1);
            result.setMsg("实验名称过短");
            result.setDetail(0);
            return result;
        }

        if(report.getStatus()!=0&&report.getStatus()!=1&&report.getStatus()!=2){
            result.setCode(2);
            result.setMsg("报告状态错误");
            result.setDetail(0);
            return result;
        }
        Timestamp ed= reportMapper.selectLabEndTime(report.getL_id());
        //Timestamp cur = new Timestamp(System.currentTimeMillis());
        Timestamp cur = report.getSubmit_time();
        System.out.println(ed);
        System.out.println(cur);
        if(cur.after(ed)){
            result.setCode(3);
            result.setMsg("实验已截止");
            result.setDetail(0);
            return result;
        }

        Integer reportNum = getReportCount(report.getL_id(), report.getS_id());
        System.out.println(reportNum);
        if(reportNum==0)
        {
            insertReport(report);
            //System.out.println("创建报告" + report.getR_id() + " + " + report.getL_id() + " + " + report.getS_id());
            result.setMsg("新建报告");
            result.setDetail(report.getR_id());
        }
        else {
            //System.out.println("查看报告" + report.getR_id() + " + " + report.getL_id() + " + " + report.getS_id());
            result.setDetail(selectStuReport(report.getL_id(), report.getS_id()).getR_id());
            result.setMsg("查看报告");
        }
        return result;
    }


    @Autowired
    private ReportFormMapper reportFormMapper;
    @Autowired
    private ReportTemplateMapper reportTemplateMapper;
    // test2
    public Result<List<ReportRow>> getReportFiller(int l_id,String s_id){
        Result<List<ReportRow>> result = new Result<>();
        result.setMsg("");
        int count = reportTemplateMapper.selectTemplateCount(l_id);
        int l_id_tmp = l_id;
        if(s_id.length()!=7){
            result.setMsg("s_id错误");
            return result;
        }
        if(count==0)
        {
            // System.out.println("目前实验没有模板，使用默认模板");
            result.setMsg("使用默认模板");
            l_id_tmp = 1;
        }
        List<ReportTemplate> reportTemplates = reportTemplateMapper.getLabReportTemplate(l_id_tmp);
        //System.out.println(reportTemplates);
        Report report = selectStuReport(l_id, s_id);
        //System.out.println(l_id + " " +" " + s_id +" " + report);
        List<ReportRow> reportRows = new ArrayList<>();
        if(report.getStatus()==0){
            // System.out.println("未提交实验报告");
            result.appendMsg("未提交实验报告");
            for (ReportTemplate reportTemplate : reportTemplates) {
                reportRows.add(new ReportRow(reportTemplate,"",0));
            }
            result.setDetail(reportRows);
            return result;
        }
        else {
            result.appendMsg("已提交");
            Result<List<ReportRow>> res = getReport(l_id, s_id);
            res.setMsg(result.getMsg());
            return res;
        }

    }

    public Result<List<ReportRow>> getReport(int l_id,String s_id){
        Result<List<ReportRow>> result = new Result<>();
        int r_id = getReportId(l_id, s_id);
        //System.out.println(r_id);
        List<ReportForm> reportForms = reportFormMapper.getLabReportForm(r_id);
        int count = reportTemplateMapper.selectTemplateCount(l_id);
        int l_id_tmp = l_id;
        if(count==0)
        {
            //System.out.println("目前实验没有模板，使用默认模板");
            l_id_tmp = 1;
        }
        List<ReportTemplate> reportTemplates = reportTemplateMapper.getLabReportTemplate(l_id_tmp);
        //System.out.println(reportTemplates);
        //System.out.println("获取 " + l_id + " + " +s_id);
        //System.out.println(reportForms);
        //System.out.println(reportTemplates );
        List<ReportRow> reportRows = new ArrayList<ReportRow>();
        for (ReportForm reportForm : reportForms) {
            for (ReportTemplate reportTemplate : reportTemplates) {
                if(reportForm.getRt_id() == reportTemplate.getRt_id()){
                    reportRows.add(new ReportRow(reportTemplate,reportForm.getContent(),reportForm.getRf_id()));
                    break;
                }
            }
        }

        result.setDetail(reportRows);
        return result;
    }
}
