package com.lrkj.core;

/**
 * Created by hou on 02/03/2017.
 */
public enum ApiResult {
    SUCCESS(200, "成功"),

    FAILURE(400, "失败"),
    FAILURE_REDIRECT(401, "数据有误"),
    FAILURE_UPDATE(402, "数据库操作失败"),
    OPERATION_FORBIDDEN(403, "无权限操作"),
    NOT_FOUND(404, "接口不存在"),
    USER_FORBIDDEN(405, "用户被禁用"),
    FIND_NOT_CODE(406, "code不存在"),
    PARAM_ERROR(1000, "参数错误"),
    USERNAME_CANT_NULL(1001, "用户名不能为空"),
    PASSWORD_CANT_NULL(1002, "密码不能为空"),
    PHONE_WRONG(1003, "手机号格式错误"),
    PASSWORD_WRONG(1004, "密码格式错误"),
    USERNAME_EXIT(1005, "账号已存在!"),

    FILE_TOO_BIG(10201, "文件太大"),
    FILE_MOVE_FAIL(10202, "文件移动失败"),
    FILE_IS_NULL(10203, "文件不能为空"),
    FILE_FORMAT_ERR(10204, "文件格式不正确!"),

    USER_NOT_EXIT(2001, "用户不存在"),
    USER_PASSWORD_ERROR(2002, "密码错误"),
    USER_STATUS_INVALID(2003, "账号已被禁用"),
    USER_CODE_ERROR(2004, "验证码错误"),
    SEND_CODE_INCORRECT(2005, "验证码发送失败"),
    OLD_PWD_ERROR(2006, "原密码错误"),
    PHONE_EXIST(2007, "手机号已存在");



    private int code;
    private String detail;

    ApiResult(int code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    public static ApiResult valueOf(int code) {
        ApiResult[] colors = values();
        int len$ = colors.length;
        byte i$ = 0;
        if (i$ < len$) {
            ApiResult c = colors[i$];
            return c;
        } else {
            return FAILURE;
        }
    }

    public String getDetail() {
        return this.detail;
    }

    public int getCode() {
        return this.code;
    }

    public ApiResult setCode(int code) {
        this.code = code;
        return this;
    }
}
