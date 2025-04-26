package org.travelsystem.travel.controller.advice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.travelsystem.travel.exception.BusinessException;
import org.travelsystem.travel.utils.ResultVO;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理请求体参数校验异常（如@RequestBody）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("请求体参数校验失败: {}", errorMsg, ex);
        return ResultVO.error(400, errorMsg);
    }

    // 处理URL/Query参数校验异常（如@RequestParam）
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO<?> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMsg = ex.getConstraintViolations().stream()
                .map(cv -> cv.getMessage())
                .collect(Collectors.joining(", "));
        log.error("请求参数校验失败: {}", errorMsg, ex);
        return ResultVO.error(400, errorMsg);
    }

    // 处理自定义业务异常
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO<?> handleBusinessException(BusinessException ex) {
        log.error("业务异常: {}", ex.getMessage(), ex);
        return ResultVO.error(400, ex.getMessage());
    }

    // 处理非法参数异常（如手动校验抛出的IllegalArgumentException）
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("参数异常: {}", ex.getMessage(), ex);
        return ResultVO.error(400, ex.getMessage());
    }

    // 空指针异常视为服务器内部错误（500）
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO<?> handleNullPointerException(NullPointerException ex) {
        log.error("空指针异常: ", ex);
        return ResultVO.error(500, "系统内部错误，请联系管理员");
    }

    // 兜底处理其他所有未捕获异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO<?> handleAllExceptions(Exception ex) {
        log.error("系统异常: ", ex);
        return ResultVO.error(500, "系统繁忙，请稍后再试"); // 补全返回语句
    }
}