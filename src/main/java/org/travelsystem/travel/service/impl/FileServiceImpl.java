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
        // 新增空值校验
        if (uploadPath == null) {
            throw new BusinessException("文件存储路径未配置");
        }

        Map<Integer, String> typeMapping = Map.of(
                1, "avatar",
                2, "dynamic",
                3, "cover"
        );

        // 获取类型目录时添加默认值
        String typeDir = typeMapping.getOrDefault(dto.getFileType(), "unknown");

        return Paths.get(uploadPath)
                .resolve(typeDir)
                .resolve(LocalDate.now().toString())
                .toString();
    }

    private String generateFilename(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null ?
                originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        return UUID.randomUUID() + extension;
    }

    private void storeFile(MultipartFile file, String directoryPath, String filename) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        Files.copy(file.getInputStream(), path.resolve(filename),
                StandardCopyOption.REPLACE_EXISTING);
    }

    private String buildFileUrl(String directoryPath, String filename) {
        // 示例：将物理路径转换为访问URL（根据实际部署环境调整）
        return "/uploads/" + Paths.get(directoryPath)
                .relativize(Paths.get(uploadPath))
                .resolve(filename)
                .toString().replace("\\", "/");
    }

    private String getTypeName(Integer typeCode) {
        return Map.of(
                1, "avatar",
                2, "dynamic",
                3, "cover"
        ).getOrDefault(typeCode, "unknown");
    }
}