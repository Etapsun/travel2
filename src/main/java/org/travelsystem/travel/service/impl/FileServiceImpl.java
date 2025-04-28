package org.travelsystem.travel.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.travelsystem.travel.DTO.FileResponseDTO;
import org.travelsystem.travel.DTO.FileUploadDTO;
import org.travelsystem.travel.entity.ImageStorage;
import org.travelsystem.travel.exception.BusinessException;
import org.travelsystem.travel.mapper.ImageStorageMapper;
import org.travelsystem.travel.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final ImageStorageMapper imageStorageMapper;

    @Value("${file.upload.path}") // 从配置文件注入路径
    private String uploadPath;

    @Override
    @Transactional
    public FileResponseDTO uploadFile(FileUploadDTO dto) {
        // 1. 验证文件
        validateFile(dto.getFile());

        try {
            // 2. 生成存储路径
            String directoryPath = generateFilePath(dto);
            String storedFilename = generateFilename(dto.getFile());

            // 3. 存储文件到磁盘
            storeFile(dto.getFile(), directoryPath, storedFilename);

            // 4. 保存记录到数据库
            ImageStorage imageStorage = ImageStorage.builder()
                    .imageUrl(buildFileUrl(directoryPath, storedFilename))
                    .uploadUserId(dto.getUserId())
                    .attractionId(dto.getAttractionId())
                    .imageType(dto.getFileType())
                    .uploadTime(LocalDateTime.now())
                    .fileSize(dto.getFile().getSize())
                    .originalName(dto.getFile().getOriginalFilename())
                    .build();
            imageStorageMapper.insert(imageStorage);

            // 5. 构建返回结果
            return FileResponseDTO.builder()
                    .url(imageStorage.getImageUrl())
                    .fileType(getTypeName(dto.getFileType()))
                    .uploadTime(imageStorage.getUploadTime())
                    .fileSize(imageStorage.getFileSize())
                    .originalName(imageStorage.getOriginalName())
                    .build();

        } catch (IOException e) {
            throw new BusinessException("文件存储失败: " + e.getMessage());
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("文件不能为空");
        }
        if (file.getSize() > 5 * 1024 * 1024) { // 5MB限制
            throw new BusinessException("文件大小超过5MB限制");
        }
    }

    private String generateFilePath(FileUploadDTO dto) {
        // 检查文件存储路径是否为空，如果为空则抛出业务异常
        if (uploadPath == null) {
            throw new BusinessException("文件存储路径未配置");
        }
        
        // 简化逻辑，直接返回上传路径
        return uploadPath;
    }

    private String generateFilename(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null ?
                originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        return UUID.randomUUID() + extension;
    }

    private void storeFile(MultipartFile file, String directoryPath, String filename) throws IOException {
        // 定义可能的上传路径列表
        List<String> possiblePaths = new ArrayList<>();
        possiblePaths.add(uploadPath);
        possiblePaths.add("src/main/resources/static/uploads");
        possiblePaths.add("target/classes/static/uploads");
        
        System.out.println("尝试将文件保存到多个位置");
        
        boolean atLeastOneSaved = false;
        
        // 遍历所有可能的路径进行保存
        for (String path : possiblePaths) {
            try {
                // 使用完整的绝对路径确保目录存在
                Path uploadDir = Paths.get(path).toAbsolutePath();
                System.out.println("尝试保存到路径: " + uploadDir);
                
                if (!Files.exists(uploadDir)) {
                    try {
                        Files.createDirectories(uploadDir);
                        System.out.println("创建上传目录: " + uploadDir);
                    } catch (IOException e) {
                        System.err.println("无法创建上传目录: " + e.getMessage());
                        continue; // 跳过当前路径，尝试下一个
                    }
                }
                
                // 确保目录存在后，构建文件完整路径
                Path fileDestination = uploadDir.resolve(filename);
                System.out.println("文件将保存到: " + fileDestination);
                
                try {
                    // 复制文件到当前路径
                    Files.copy(file.getInputStream(), fileDestination, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("文件成功保存到: " + fileDestination);
                    atLeastOneSaved = true;
                    
                    // 检查文件是否已创建
                    if (Files.exists(fileDestination)) {
                        System.out.println("确认文件已保存，大小: " + Files.size(fileDestination) + " 字节");
                    } else {
                        System.err.println("文件保存后检查失败，文件不存在: " + fileDestination);
                    }
                } catch (IOException e) {
                    System.err.println("保存到 " + fileDestination + " 失败: " + e.getMessage());
                }
            } catch (Exception e) {
                System.err.println("处理路径 " + path + " 时出错: " + e.getMessage());
            }
        }
        
        if (!atLeastOneSaved) {
            throw new BusinessException("无法将文件保存到任何目录");
        }
    }

    private String buildFileUrl(String directoryPath, String filename) {
        // 构建简单的URL格式，不包含..或其他导航符号
        String url = "/uploads/" + filename;
        System.out.println("生成的文件URL: " + url);
        return url;
    }

    private String getTypeName(Integer typeCode) {
        return Map.of(
                1, "avatar",
                2, "dynamic",
                3, "cover"
        ).getOrDefault(typeCode, "unknown");
    }
}