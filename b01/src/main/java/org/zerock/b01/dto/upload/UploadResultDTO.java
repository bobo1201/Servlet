package org.zerock.b01.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {

    private String uuid;

    private String fileName;

    private boolean img;

    // 나중에 JSON으로 처리될 때 link라는 속성으로 자동 처리됨
    // private로 하면 값이 전달안되고 link라는 변수가 json 형태로 전달되지 않음
    // public으로 하면 link라는 변수로 전달됨
    public String getLink(){
        if(img){
            return "s_" + uuid + "_" + fileName; // 이미지인 경우 섬네일
        } else{
            return uuid + "_" + fileName;
        }
    }
}
