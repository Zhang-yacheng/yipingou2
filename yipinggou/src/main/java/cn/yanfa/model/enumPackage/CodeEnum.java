package cn.yanfa.model.enumPackage;

import lombok.Getter;

/**
 * @author
 * @version 1.0
 * @Package
 * @Description: 统一返回码，返回值信息
 * @date 2019/9/25 16:31
 */
@Getter
public enum CodeEnum {
    SUCCESS(200, "操作成功"),
    ERROR(100, "操作失败"),
    PARAM_ERR(400, "请求参数异常"),
    REQUEST_NOT_FOUND(404, "请求路径不存在"),
    REQUEST_NOT_ALLOWED_FOUND(405, "请求受限"),
    INTERNAL_SERVER_ERROR(500, "未知异常"),
    INPUT_NULL(501, "参数为空"),
    SIGN_ERROR(504, "签名异常"),
    CODE_ERROR(505, "验证码失效"),
    PASSWORD_ERROR(506, "密码错误"),
    DATA_INTEGRITY_VIOLATION(507, "违反数据唯一性原则"),
    //登录过期，或者没有登录
    OAUTH_UN_LOAD(521, "沒有登录"),
    OAUTH_EXPIRED(522, "登录过期"),
    OAUTH_INVALID(523, "无效授权码"),
    SYSTEM_ERROE(9999, "系统错误"),
    ;

    private Integer code;
    private String msg;

    CodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
