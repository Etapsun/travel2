package org.travelsystem.travel.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.travelsystem.travel.DTO.AttractionDTO;
import org.travelsystem.travel.entity.Attraction;
import org.travelsystem.travel.exception.BusinessException;
import org.travelsystem.travel.mapper.AttractionMapper;
import org.travelsystem.travel.respository.AttractionRepository;
import org.travelsystem.travel.service.AttractionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {
    // 自动注入AttractionMapper，用于数据库操作
    private final AttractionMapper attractionMapper;
    // 自动注入AttractionRepository，用于数据库操作
    private final AttractionRepository attractionRepository;


    @Override
    public AttractionDTO getAttractionById(Long id) throws ResourceNotFoundException {
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("景点不存在，Id"+id));

        return AttractionDTO.builder()
                .attractionId(attraction.getAttractionId())
                .attractionName(attraction.getAttractionName())
                .attractionRating(attraction.getAttractionRating())
                .attractionIntroduction(attraction.getAttractionIntroduction() )
                .attractionLocation(attraction.getAttractionLocation())
                .attractionImages(attraction.getAttractionImages() )
                .attractionCover(attraction.getAttractionCover())
                .officialGuide(attraction.getOfficialGuide()) // 处理空值
                .attractionStatus(attraction.getAttractionStatus())
                .businessHours(attraction.getBusinessHours())
                .ticketPrice(attraction.getTicketPrice() != null ? attraction.getTicketPrice() : BigDecimal.ZERO)
                .attractionPhone(attraction.getAttractionPhone())
                .pageViews(attraction.getPageViews())
                .discount(attraction.getDiscount()!= null? attraction.getDiscount() : BigDecimal.ZERO)
                // 其他字段映射...
                .build();
    }


    /**
     * 根据名称模糊查询景点信息
     * @param name 景点名称
     * @return 符合名称的景点列表
     */
    @Override
    @Transactional
    public List<AttractionDTO> searchAttractionsByName(String name) {
        // 根据名称模糊查询景点信息
        List<Attraction> attractions = attractionRepository.findByAttractionNameContainingIgnoreCase(name);
        // 将查询结果转换为DTO列表并返回
        return attractions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    /**
     * 根据评分范围查询景点信息
     * @param min 最小评分
     * @param max 最大评分
     * @return 符合评分范围的景点列表
     */
    // 修改服务层查询逻辑（移除空列表异常抛出）
    public List<AttractionDTO> getAttractionsByRatingRange(BigDecimal min, BigDecimal max) {
        if (min.compareTo(max) > 0) {
            throw new BusinessException("评分范围无效");
        }
        if (min == null || max == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        return attractionRepository.findByAttractionRatingBetween(min, max)
                .stream()
                .map(attraction -> {
                    // 增加空值保护
                    return AttractionDTO.builder()
                            .attractionId(attraction.getAttractionId())
                            .attractionName(attraction.getAttractionName() != null ? attraction.getAttractionName() : "")
                            .attractionRating(attraction.getAttractionRating() != null ? attraction.getAttractionRating() : BigDecimal.ZERO)
                            .attractionIntroduction(attraction.getAttractionIntroduction()!= null? attraction.getAttractionIntroduction() : "")
                            .attractionLocation(attraction.getAttractionLocation() != null ? attraction.getAttractionLocation() : "")
                            .attractionImages(attraction.getAttractionImages()!= null? attraction.getAttractionImages() : "")
                            .attractionCover(attraction.getAttractionCover()!= null? attraction.getAttractionCover() : "")
                            .officialGuide(attraction.getOfficialGuide()!= null? attraction.getOfficialGuide() : "") // 处理空值
                            .attractionStatus(attraction.getAttractionStatus())
                            .businessHours(attraction.getBusinessHours()!= null? attraction.getBusinessHours() : "")
                            .ticketPrice(attraction.getTicketPrice()!= null? attraction.getTicketPrice() : BigDecimal.ZERO)
                            .attractionPhone(attraction.getAttractionPhone())
                            .pageViews(attraction.getPageViews())
                            .discount(attraction.getDiscount()!= null? attraction.getDiscount() : BigDecimal.ZERO)
                            // 其他字段添加类似空值处理...
                            .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * 将DTO转换为实体并插入数据库
     * 注意：这里的DTO是前端传递过来的，需要进行校验和转换
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public AttractionDTO createAttraction(AttractionDTO dto) {
        // 将DTO转换为景点实体
        Attraction attraction = convertToEntity(dto);
        // 插入景点信息到数据库
        attractionMapper.insert(attraction);
        // 将插入后的景点实体转换为DTO并返回
        return convertToDTO(attraction);
    }

    @Override
    @Transactional
    /**
     * 更新景点信息
     * @param dto 包含更新信息的DTO对象
     * @return 更新后的景点DTO对象
     */
    public AttractionDTO updateAttraction(AttractionDTO dto) {
        if (dto.getAttractionId() == null) {
            throw new IllegalArgumentException("景点ID不能为空");
        }

        Attraction existing = attractionRepository.findById(dto.getAttractionId())
                .orElseThrow(() -> new ResourceNotFoundException("景点不存在"));

        // 使用现有实体作为基础，仅更新DTO中有值的字段
        Attraction attraction = updateEntityFromDTO(existing, dto);
        attractionMapper.update(attraction);

        return convertToDTO(attraction);
    }


    /**
     * 根据ID删除景点信息
     * @param id 景点ID
     */
    @Override
    @Transactional
    public void deleteAttraction(Long id) {
        int affected = attractionMapper.deleteById(id);
        if (affected == 0) {
            throw new ResourceNotFoundException("景点不存在");
        }
    }

    /**
     * 增加景点的页面浏览次数
     * @param id 景点ID
     */

    @Override
    @Transactional
    public void incrementPageViews(Long id) {
        // 增加景点的页面浏览量
        attractionRepository.incrementPageViews(id);
    }

    // 将景点实体转换为DTO
    private AttractionDTO convertToDTO(Attraction attraction) {
        return AttractionDTO.builder()
                .attractionId(attraction.getAttractionId())
                .attractionName(attraction.getAttractionName() != null ? attraction.getAttractionName() : "")
                .attractionRating(attraction.getAttractionRating() != null ? attraction.getAttractionRating() : BigDecimal.ZERO)
                .attractionIntroduction(attraction.getAttractionIntroduction() != null ? attraction.getAttractionIntroduction() : "")
                .attractionLocation(attraction.getAttractionLocation() != null ? attraction.getAttractionLocation() : "")
                .attractionImages(attraction.getAttractionImages() != null ? attraction.getAttractionImages() : "")
                .attractionCover(attraction.getAttractionCover() != null ? attraction.getAttractionCover() : "")
                .officialGuide(attraction.getOfficialGuide() != null ? attraction.getOfficialGuide() : "") // 处理空值
                .attractionStatus(attraction.getAttractionStatus())
                .businessHours(attraction.getBusinessHours() != null ? attraction.getBusinessHours() : "")
                .ticketPrice(attraction.getTicketPrice() != null ? attraction.getTicketPrice() : BigDecimal.ZERO)
                .attractionPhone(attraction.getAttractionPhone() != null ? attraction.getAttractionPhone() : "")
                .pageViews(attraction.getPageViews())
                .discount(attraction.getDiscount()!= null? attraction.getDiscount() : BigDecimal.ZERO)
                .build();
    }

    // 将DTO转换为景点实体
    private Attraction convertToEntity(AttractionDTO dto) {
        return Attraction.builder()
                .attractionId(dto.getAttractionId())
                .attractionName(dto.getAttractionName())
                .attractionRating(dto.getAttractionRating())
                .attractionIntroduction(dto.getAttractionIntroduction())
                .attractionLocation(dto.getAttractionLocation())
                .attractionImages(dto.getAttractionImages())
                .attractionCover(dto.getAttractionCover())
                .officialGuide(dto.getOfficialGuide())  // 修复字段名称拼写错误
                .attractionStatus(dto.getAttractionStatus())
                .businessHours(dto.getBusinessHours())
                .ticketPrice(dto.getTicketPrice())
                .attractionPhone(dto.getAttractionPhone())
                .pageViews(Optional.ofNullable(dto.getPageViews()).orElse(0))
                .discount(dto.getDiscount())
                .build();
    }

    private Attraction updateEntityFromDTO(Attraction existing, AttractionDTO dto) {
        return existing.toBuilder()
                .attractionName(dto.getAttractionName() != null ? dto.getAttractionName() : existing.getAttractionName())
                .attractionRating(dto.getAttractionRating() != null ? dto.getAttractionRating() : existing.getAttractionRating())
                .attractionIntroduction(dto.getAttractionIntroduction() != null ? dto.getAttractionIntroduction() : existing.getAttractionIntroduction())
                .attractionLocation(dto.getAttractionLocation() != null ? dto.getAttractionLocation() : existing.getAttractionLocation())
                .attractionImages(dto.getAttractionImages() != null ? dto.getAttractionImages() : existing.getAttractionImages())
                .attractionCover(dto.getAttractionCover() != null ? dto.getAttractionCover() : existing.getAttractionCover())
                .officialGuide(dto.getOfficialGuide() != null ? dto.getOfficialGuide() : existing.getOfficialGuide())
                .attractionStatus(dto.getAttractionStatus() != null ? dto.getAttractionStatus() : existing.getAttractionStatus())
                .businessHours(dto.getBusinessHours() != null ? dto.getBusinessHours() : existing.getBusinessHours())
                .ticketPrice(dto.getTicketPrice() != null ? dto.getTicketPrice() : existing.getTicketPrice())
                .attractionPhone(dto.getAttractionPhone() != null ? dto.getAttractionPhone() : existing.getAttractionPhone())
                .discount(dto.getDiscount() != null ? dto.getDiscount() : existing.getDiscount())
                .build();
    }
}