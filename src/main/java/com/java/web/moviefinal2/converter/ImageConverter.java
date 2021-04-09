package com.java.web.moviefinal2.converter;

import com.java.web.moviefinal2.dto.ImageDTO;
import com.java.web.moviefinal2.dto.MovieDTO;
import com.java.web.moviefinal2.entity.ImageEntity;
import com.java.web.moviefinal2.entity.MovieEntity;
import com.java.web.moviefinal2.enums.CategoryEnum;
import com.java.web.moviefinal2.enums.TypeEnum;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ImageConverter {

    ModelMapper modelMapper = null;
    public ImageConverter() {
        this.modelMapper = new ModelMapper();
    }

    public ImageDTO convertToDTO(ImageEntity entity){
        ImageDTO mo = new ImageDTO();
        mo.setKeyImage(entity.getKeyImage());
        return mo;
    }
//    public MovieDTO convertToDTOno(MovieEntity entity){
//        MovieDTO mo = modelMapper.map(entity,MovieDTO.class);
//        return mo;
//    }
//
//    public MovieEntity convertToEntity(MovieDTO dto){
//        MovieEntity me = modelMapper.map(dto,MovieEntity.class);
//        return me;
//    }
}

