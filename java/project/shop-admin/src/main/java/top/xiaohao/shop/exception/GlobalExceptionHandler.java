package top.xiaohao.shop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.xiaohao.shop.utils.Result;

/**
 * AOP异常监听
 * @author Twac
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public Result<?> systemExceptionHandler(SystemException e) {
        log.error("SystemException:", e);
        return Result.errorResult(e.getCode(), e.getMsg());
    }
}
