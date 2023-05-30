package cn.edu.tongji.backend.report.mapper;

import cn.edu.tongji.backend.report.pojo.ReportTemplate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReportTemplateMapper {

    @Insert("insert into report_template VALUES(null,#{l_id},#{t_id},#{title},#{order},#{placeholder},#{required},#{type})")
    void insertIntoReportTemplate(ReportTemplate reportTemplate);

    @Select("select * from report_template where l_id = #{l_id}")
    List<ReportTemplate> getLabReportTemplate(int l_id);

    @Select("select count(*) from report_template where l_id=#{l_id}")
    int selectTemplateCount(int l_id);

    @Select("select required from report_template where l_id=#{l_id}")
    List<Boolean> selectRequire(int l_id);

    @Select("select l_id from laboratory")
    List<Integer> selectAllLabId();

    @Select("select count(l_id) from teaches left join laboratory l on teaches.c_id = l.c_id where l_id=#{l_id} and teaches.t_id=#{t_id}")
    Integer checkIfTeaches(int l_id,String t_id);
}
