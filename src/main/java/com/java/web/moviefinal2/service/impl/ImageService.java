package com.java.web.moviefinal2.service.impl;

import com.java.web.moviefinal2.converter.ImageConverter;
import com.java.web.moviefinal2.dto.ImageDTO;
import com.java.web.moviefinal2.entity.ImageEntity;
import com.java.web.moviefinal2.repository.ImageRepository;
import com.java.web.moviefinal2.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageConverter imageConverter;

    @Override
    public void save(ImageEntity imageEntity) {
        imageRepository.save(imageEntity);
    }

    @Override
    public List<ImageDTO> getByKeyImage(String keyImage) {
        List<ImageDTO> result = new ArrayList<>();
        try {
            List<ImageEntity> entities = imageRepository.findAllByKeyImage(keyImage);
            for (ImageEntity entity :
                    entities) {
                ImageDTO imageDTO = imageConverter.convertToDTO(entity);
                result.add(imageDTO);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
