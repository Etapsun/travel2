package org.travelsystem.travel.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDTO {
    @NotNull
    private MultipartFile file;
    private Integer fileType; // [avatar|attraction_cover|attraction_detail|product]
    private Integer userId; // 上传用户ID
    private Integer attractionId; // 景点ID
}

