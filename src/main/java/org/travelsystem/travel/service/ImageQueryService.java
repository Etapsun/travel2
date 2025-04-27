package org.travelsystem.travel.service;

public interface ImageQueryService {
    String getUserAvatar(Integer userId);

    String getAttractionCover(Integer attractionId);

    String getAttractionDetails(Long attractionId);

    // 获取景点详情图列表

}