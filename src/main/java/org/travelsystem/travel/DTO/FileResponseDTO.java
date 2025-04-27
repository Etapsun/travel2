package org.travelsystem.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponseDTO {
    private String url;
    private String fileType;
    private LocalDateTime uploadTime;
    private Long fileSize;
    private String originalName;
}