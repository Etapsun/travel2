package org.travelsystem.travel.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.travelsystem.travel.DTO.FileResponseDTO;
import org.travelsystem.travel.DTO.FileUploadDTO;
import org.travelsystem.travel.entity.ImageStorage;
import org.travelsystem.travel.mapper.FileMapper;
import org.travelsystem.travel.mapper.ImageStorageMapper;
import org.travelsystem.travel.service.FileService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final ImageStorageMapper imageStorageMapper;

    @Value("${file.upload.path}")
    private String uploadPath;

    @Transactional
    @Override
    public FileResponseDTO uploadFile(FileUploadDTO dto) {
        // 1. 文件校验
        validateFile(dto.getFile());

        // 2. 生成存储路径
        String filePath = generateFilePath(dto);

        // 3. 存储文件
        storeFile(dto.getFile(), filePath);

        // 4. 保存数据库记录
        ImageStorage entity = fileMapper.toEntity(dto);
        entity.setUploadTime(LocalDateTime.now());
        entity.setFileSize(dto.getFile().getSize());
        entity.setOriginalName(dto.getFile().getOriginalFilename());
        imageStorageMapper.save(entity);

        return imageStorageMapper.toResponseDTO(entity);
    }

    private void validateFile(MultipartFile file) {
        // 实现文件类型、大小校验逻辑
    }

    private String generateFilePath(FileUploadDTO dto) {
        // 生成带分类目录的存储路径
    }

    private void storeFile(MultipartFile file, String filePath) {
        // 实现文件存储逻辑
    }
}