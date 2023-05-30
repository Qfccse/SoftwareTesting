package cn.edu.tongji.backend.laboratory.pojo;

import lombok.Data;

/**
 * 向前端返回信息封装
 * @param <T> 可变类型
 */
@Data
public class Result<T> {
    private String msg;
    private int code;
    private T detail;
}
