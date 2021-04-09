package com.java.web.moviefinal2.service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AmazonS3ClientService
{
    void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess);

    void deleteFileFromS3Bucket(String fileName);

    List<String> listFiles();

    String nameBucket();
}
