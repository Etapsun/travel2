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

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public FileResponseDTO uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileType") Integer fileType,
            @RequestParam(value = "userId", required = false) Integer userId) {

        FileUploadDTO dto = FileUploadDTO.builder()
                .file(file)
                .fileType(fileType)
                .userId(userId)
                .build();

        return fileService.uploadFile(dto);
    }
}