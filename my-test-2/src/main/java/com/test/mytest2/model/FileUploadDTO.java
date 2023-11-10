package com.test.mytest2.model;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadDTO {

    /**
     * id
     */
    private Long id;
}
