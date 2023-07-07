package org.zerock.b01.dto.upload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadFileDTO {
    // 파일 업로드 처리하려면 MultipartFile API 이용하면 됨
    private List<MultipartFile> files;
}
