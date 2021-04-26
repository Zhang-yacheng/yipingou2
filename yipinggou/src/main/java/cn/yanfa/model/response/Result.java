package cn.yanfa.model.response;


import cn.yanfa.model.enumPackage.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dushan
 * @version 1.0
 * @Package com.gov.geological.model
 * @Description: 后台响应结果
 * @date 2020/5/5 16:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    public static String CODE = "code";
    public static String MSG = "msg";
    public static String DATA = "data";

    //    @ApiModelProperty("交互数据")
    private T data;
    //    @ApiModelProperty("后台响应码：0/200表示后台响应成功，521表示登录过期，其余皆是后台响应失败")
    private Integer code;
    //    @ApiModelProperty("code响应失败的错误信息")
    private String msg;

    public static <T> Result<T> succeed() {
        return succeedWith(null, CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
    }

    public static <T> Result<T> succeed(String msg) {
        return succeedWith(null, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T model, String msg) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T model) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
    }

    public static <T> Result<T> succeedWith(T datas, Integer code, String msg) {
        return new Result<T>(datas, code, msg);
    }

    public static <T> Result<T> failed() {
        return failedWith(null, CodeEnum.ERROR.getCode(), CodeEnum.ERROR.getMsg());
    }

    public static <T> Result<T> failed(String msg) {
        return failedWith(null, CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> Result<T> failed(T model) {
        return failedWith(model, CodeEnum.ERROR.getCode(), CodeEnum.ERROR.getMsg());
    }

    public static <T> Result<T> failed(T model, String msg) {
        return failedWith(model, CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> Result<T> failed(T model, Integer code, String msg) {
        return failedWith(model, code, msg);
    }

    public static <T> Result<T> failedWith(T datas, Integer code, String msg) {
        return new Result<T>(datas, code, msg);
    }

    public static <T> Result<T> inputNull() {
        return baseWith(null, CodeEnum.INPUT_NULL);
    }

    public static <T> Result<T> paramsErr() {
        return baseWith(null, CodeEnum.PARAM_ERR);
    }

    public static <T> Result<T> paramsErr(String msg) {
        return baseWith(null, CodeEnum.PARAM_ERR.getCode(), msg);
    }

    public static <T> Result<T> baseWith(T datas, CodeEnum anEnum) {
        return baseWith(datas, anEnum.getCode(), anEnum.getMsg());
    }

    public static <T> Result<T> baseWith(T datas, Integer code, String msg) {
        return new Result<T>(datas, code, msg);
    }

    public boolean isSucceed() {
        return this.code.equals(CodeEnum.SUCCESS.getCode());
    }

    public boolean isFailed() {
        return !isSucceed();
    }

    public static <T> Result<T> codeError(T model, String msg) {
        return codeErrorWith(null, CodeEnum.CODE_ERROR.getCode(), msg);
    }

    public static <T> Result<T> codeErrorWith(T datas, Integer code, String msg) {
        return new Result<T>(null, code, msg);
    }

    public static <T> Result<T> passwordErr(T model, String msg) {
        return codeErrorWith(null, CodeEnum.PARAM_ERR.getCode(), msg);
    }

    public static <T> Result<T> passwordErrorWith(T datas, Integer code, String msg) {
        return new Result<T>(null, code, msg);
    }

}
