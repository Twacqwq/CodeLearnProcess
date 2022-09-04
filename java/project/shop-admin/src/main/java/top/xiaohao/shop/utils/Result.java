package top.xiaohao.shop.utils;

import lombok.Data;

/**
 * 自定义响应类
 * @author Twac
 */
@Data
public class Result<T> {

    private Integer code;

    private String msg;

    private T details;

    public Result() {}

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T details) {
        this.code = code;
        this.msg = msg;
        this.details = details;
    }

    public Result(Integer code, T details) {
        this.code = code;
        this.details = details;
    }
    public Result<?> error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public static Result<?> errorResult(Integer code, String msg) {
        Result<?> result = new Result<>();
        return result.error(code, msg);
    }


}
