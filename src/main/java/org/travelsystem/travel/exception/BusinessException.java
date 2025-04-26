package org.travelsystem.travel.exception;

import lombok.Getter;

@Getter // 使用Lombok注解自动生成getter方法
public class BusinessException extends RuntimeException { // 定义一个自定义异常类，继承自RuntimeException
    // 可扩展字段（如错误码）
    private final Integer code; // 定义一个final类型的Integer字段，用于存储错误码

    // 基础构造器
    public BusinessException(String message) { // 构造器，接受一个字符串参数message
        super(message); // 调用父类RuntimeException的构造器，传入message
        this.code = 400; // 默认错误码
    }

    // 带错误码的构造器
// 这个构造器用于创建一个BusinessException对象，并初始化错误码和错误信息
    public BusinessException(Integer code, String message) {
    // 调用父类Exception的构造器，传入错误信息message
        super(message);
    // 初始化当前类的code属性，用于存储错误码
        this.code = code;
    }
}

