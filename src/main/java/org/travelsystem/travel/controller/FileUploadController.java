package org.travelsystem.travel.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.travelsystem.travel.DTO.FileResponseDTO;
import org.travelsystem.travel.DTO.FileUploadDTO;
import org.travelsystem.travel.entity.ImageStorage;
import org.travelsystem.travel.service.FileService;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "文件上传", description = "文件上传管理接口")
public class FileUploadController {
    private final FileService fileService;

    /**
     * 上传文件
     * @param file 要上传的文件
     * @param fileType 文件类型
     * @param userId 用户ID（可选）
     * @return 文件上传后的响应
     */
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public FileResponseDTO uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileType") Integer fileType,
            @RequestParam("userId") Integer userId) {

        FileUploadDTO dto = FileUploadDTO.builder()
                .file(file)
                .fileType(fileType)
                .userId(userId)
                .build();
        return fileService.uploadFile(dto);
    }

    @PostMapping("/attraction")
    @ResponseStatus(HttpStatus.CREATED)
    public FileResponseDTO uploadATFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileType") Integer fileType,
            @RequestParam("attractionId") Integer attractionId) {
        FileUploadDTO dto = FileUploadDTO.builder()
               .file(file)
               .fileType(fileType)
               .attractionId(attractionId)
               .build();
        return fileService.uploadFile(dto);
    }
}