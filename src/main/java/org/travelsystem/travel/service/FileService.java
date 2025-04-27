package org.travelsystem.travel.service;

import jakarta.transaction.Transactional;
import org.travelsystem.travel.DTO.FileResponseDTO;
import org.travelsystem.travel.DTO.FileUploadDTO;

public interface FileService {

    @Transactional
    FileResponseDTO uploadFile(FileUploadDTO dto);
}
