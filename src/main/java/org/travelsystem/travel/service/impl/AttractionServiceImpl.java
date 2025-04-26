package org.travelsystem.travel.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.travelsystem.travel.entity.Attraction;
import org.travelsystem.travel.respository.AttractionRepository;
import org.travelsystem.travel.service.attractionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 景点类主要写以下方法：
 * 基本增删改查景点的基本信息，其中包括id，名字，景点详细介绍，景点地理位置，景点图片，景点封面，景点门票价格，景点电话，
 * 自运算景点的浏览量
 * 前后端交互景点的点赞数
 */
@Service
@Transactional
public class AttractionServiceImpl implements attractionService {
    /**
     * attraction
     * @return
     * 保存或更新实体：save() 方法会根据实体对象的状态决定是执行 INSERT（新增）还是 UPDATE（更新）操作。
     * 如果 attraction 是一个新对象（例如其主键字段为 null 或数据库中不存在对应主键），则会向数据库插入一条新记录。
     * 如果 attraction 的主键已存在于数据库中，则会更新对应的记录。
     */
    private final AttractionRepository attractionRepository;
    @Autowired
    public AttractionServiceImpl(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    /**
     * 该方法增加浏览量
     * 并创造一个景点
     * @param attraction
     * @return
     */
    @Override
    public Attraction createattraction(Attraction attraction) {
        attraction.setPageViews(0);//设置景点页面浏览量为0
        validateAttraction(attraction);
        return attractionRepository.save(attraction);
    }

    /**
     *检测，如果删除到没有的id，就抛出异常，如果有，就删除所要删除景点的id
     * @param id 景点id（唯一值）
     */

    @Override
    public void deleteattraction(Long id) throws ResourceNotFoundException {
        if(!attractionRepository.existsById(id)) {
            throw new ResourceNotFoundException("景点不存在ID" + id);
        }
        attractionRepository.deleteById(id);
    }

    /**
     *
     * @param id 要改的景点id
     * @param updateattraction 要改的景点
     * @return
     * @throws ResourceNotFoundException 如果找不到id，则抛出异常
     */
    @Override
    public Attraction updateattraction(Long id, Attraction updateattraction) throws ResourceNotFoundException {
        Attraction existing=attractionRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("景点不存在Id为"+id));
        updayeFields(existing,updateattraction);
        return attractionRepository.save(updateattraction);

    }

    /**
     * 获得所有的景点
     * @return
     */
    @Override
    public List<Attraction> getAllattraction() {
        return attractionRepository.findAll();
    }

    /**
     * 通过id查询
     * @param id
     * @return返回结果，如果没有id则抛出异常
     * @throws ResourceNotFoundException
     */
    @Override
    public Attraction getattractionById(Long id) throws ResourceNotFoundException {
        return attractionRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("景点不存在，Id"+id));
    }




    /**
     *评论检查器，如果评分小于0，会弹出评分不能为复数
     * @param attraction
     */
    private void validateAttraction(Attraction attraction) {
        if(attraction.getAttractionRating().compareTo(BigDecimal.ZERO) < 0)
        {
            throw new IllegalArgumentException("评分不能为复数");
        }
    }

    /**
     * 如果updated.getAttractionName()不为null，返回包含该值的Optional。如果为null，返回一个空的Optional避免直接操作可能为null的值导致空指针异常
     * ifPresent：当Optional包含非空值时，调用传入的方法
     * @param existing
     * @param updated
     */

    private void updayeFields(Attraction existing, Attraction updated) {
        Optional.ofNullable(updated.getAttractionName()).ifPresent(existing::setAttractionName);
        Optional.ofNullable(updated.getAttractionRating()).ifPresent(existing::setAttractionRating);
    }






}
