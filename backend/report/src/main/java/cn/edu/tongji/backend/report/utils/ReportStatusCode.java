package cn.edu.tongji.backend.report.utils;

public class ReportStatusCode {
    public static final int SUCCESS = 200;
    public static final int SUCCESS_CREATE_REPORT = 201;
    public static final int SUCCESS_GET_REPORT = 202;
    public static final int SUCCESS_NOT_SUBMIT_REPORT = 203;
    public static final int SUCCESS_HAVE_SUBMIT_REPORT = 204;
    public static final int SUCCESS_NOT_SUBMIT_REPORT_DEFAULT = 205;
    public static final int SUCCESS_HAVE_SUBMIT_REPORT_DEFAULT = 205;
    public static final int SUCCESS_INSERT_FORM = 206;
    public static final int SUCCESS_UPDATE_FORM = 207;


    public static final int ERROR = 220;
    public static final int ERROR_SID = 221;
    public static final int ERROR_TID = 222;
    public static final int ERROR_STATUS = 223;
    public static final int ERROR_TITLE_LEN =224;
    public static final int ERROR_EMPTY_TITLE =225;
    public static final int ERROR_END_TIME = 226;
    public static final int ERROR_UNFILLED_FORM = 227;
    public static final int ERROR_UNSUPPORTED_FORM_TYPE = 228;
    public static final int ERROR_LID_NOT_FOUND = 229;
    public static final int ERROR_UNFITTED_TEACHER = 230;
    public static final int ERROR_EMPTY_FILE_NAME = 231;
    public static final int ERROR_IMAGE_SUFFIX = 232;
    public static final int ERROR_PATH_FORM = 233;
    public static final int ERROR_TEMPLATE_NOT_MATCH = 234;
}
