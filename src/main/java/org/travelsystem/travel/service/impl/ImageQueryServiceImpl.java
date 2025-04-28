package org.travelsystem.travel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.travelsystem.travel.entity.ImageStorage;
import org.travelsystem.travel.mapper.ImageStorageMapper;
import org.travelsystem.travel.service.ImageQueryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageQueryServiceImpl implements ImageQueryService {
    // 注入ImageStorageMapper，用于数据库操作
    private final ImageStorageMapper imageStorageMapper;

    // 获取用户头像的方法
    @Override
    public String getUserAvatar(Integer userId) {
        // 根据用户ID和图片类型查询用户头像图片列表
        List<ImageStorage> images = imageStorageMapper.findByTypeAndRefId(
                ImageStorage.Type.USER_AVATAR,
                userId
        );
        // 如果图片列表为空，返回null；否则返回第一张图片的URL
        images.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        return images.get(0).getImageUrl();
    }

    // 获取景点封面图片的方法
    @Override
    public String getAttractionCover(Integer attractionId) {
        // 根据景点ID和图片类型查询景点封面图片列表
        List<ImageStorage> images = imageStorageMapper.findByAttractionId(
                ImageStorage.Type.ATTRACTION_COVER,
                Math.toIntExact(attractionId)
        );
        images.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        return images.get(0).getImageUrl();
    }

    // 获取景点详情图片的方法
    @Override
    public String getAttractionDetails(Long attractionId) {
        // 根据景点ID和图片类型查询景点详情图片列表
        return imageStorageMapper.findByTypeAndRefId(
                        ImageStorage.Type.ATTRACTION_DETAIL,
                        Math.toIntExact(attractionId)
                ).stream()
                // 将图片列表转换为URL字符串列表
                .map(ImageStorage::getImageUrl)
                // 将URL字符串列表转换为字符串并返回
                .collect(Collectors.toList()).toString();
    }
}