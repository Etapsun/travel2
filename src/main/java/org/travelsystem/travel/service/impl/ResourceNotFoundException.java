package org.travelsystem.travel.service.impl;

public class ResourceNotFoundException extends RuntimeException {
    // 调用父类构造器传递错误信息
    public ResourceNotFoundException(String message) {
        super(message);
    }
}