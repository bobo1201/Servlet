package org.zerock.b01.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.b01.dto.upload.UploadFileDTO;
import org.zerock.b01.dto.upload.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
// 파일 업로드와 파일을 보여주는 기능을 메소드로 처리함
public class UpDownController {

    // 파일 저장 경로 지정 : application.properties에서 지정
    // application.properties의 파일 설정 정보를 읽어 변수의 값으로 사용할 수 있음
    // uploadPath는 나중에 파일을 업로드하는 경로로 사용
    @Value("${org.zerock.upload.path}") // import 시에 springframework로 시작하는 Value
    private String uploadPath;
    
    @ApiOperation(value = "Upload POST", notes = "POST 방식으로 파일 등록")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> upload(UploadFileDTO uploadFileDTO){
        log.info(uploadFileDTO);

        if(uploadFileDTO.getFiles() != null){
            final List<UploadResultDTO> list = new ArrayList<>();

            uploadFileDTO.getFiles().forEach(multipartFile -> {

                String originalName = multipartFile.getOriginalFilename();
                log.info(originalName);

                // 실제 파일 저장시 같은 이름의 파일이 문제 됨.
                // 이 문제를 해결하기 위해서는 java.util.UUID를 이용해 새로운 값을 만들어 냄
                // UUID는 중복될 가능성이 거의 없는 코드 값을 생성
                String uuid = UUID.randomUUID().toString();

                // UUID(16자리) + _ + 원래 파일명
                Path savePath = Paths.get(uploadPath, uuid+"_"+originalName);

                boolean image = false;

                try{
                    // 지정한 경로 C:\\upload에 실제 파일 저장됨
                    multipartFile.transferTo(savePath); // 실제 파일 저장

                    // 이미지 파일의 종류라면 (thumbnailator 사용)
                    if(Files.probeContentType(savePath).startsWith("image")){
                        image = true;
                        File thumbFile = new File(uploadPath, "s_" + uuid+ "_" + originalName);
                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200,200);
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }

                list.add(UploadResultDTO.builder()
                                .uuid(uuid)
                        .fileName(originalName)
                        .img(image).build());
            }); // end each

            return list;
        } // end if

        return null;
    }
}
