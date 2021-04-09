package com.java.web.moviefinal2.dto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserEdit {
    private String keyImage;
    private MultipartFile fileDatas;
    private String passCurrent;
    private String newPass;
    private String confirmNewPass;
}
