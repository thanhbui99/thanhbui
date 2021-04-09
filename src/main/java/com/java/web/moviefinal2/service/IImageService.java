package com.java.web.moviefinal2.service;

import com.java.web.moviefinal2.dto.ImageDTO;
import com.java.web.moviefinal2.entity.ImageEntity;

import java.util.List;

public interface IImageService {
    void save(ImageEntity imageEntity);
    List<ImageDTO> getByKeyImage(String keyImage);
}
