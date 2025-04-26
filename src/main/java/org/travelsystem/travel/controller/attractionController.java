package org.travelsystem.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.travelsystem.travel.entity.Attraction;
import org.travelsystem.travel.service.attractionService;
import org.travelsystem.travel.service.impl.AttractionServiceImpl;

import javax.naming.spi.DirStateFactory;
import javax.xml.transform.Result;

@RestController("/attraction")
public class attractionController {
    // 定义一个私有常量，用于存储AttractionServiceImpl对象
    private final AttractionServiceImpl attractionServiceImpl;

    // 使用@Autowired注解自动注入attractionService对象
    @Autowired
    private attractionService attractionService;
// 定义一个构造函数，用于创建attractionController对象
    public attractionController(AttractionServiceImpl attractionServiceImpl) {
    // 将传入的AttractionServiceImpl对象赋值给当前对象的attractionServiceImpl属性
        this.attractionServiceImpl = attractionServiceImpl;
    }







    // 其他代码...
}
