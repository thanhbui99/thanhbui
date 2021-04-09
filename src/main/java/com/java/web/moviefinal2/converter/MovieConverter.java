package com.java.web.moviefinal2.converter;

import com.java.web.moviefinal2.dto.MovieDTO;
import com.java.web.moviefinal2.entity.MovieEntity;
import com.java.web.moviefinal2.enums.CategoryEnum;
import com.java.web.moviefinal2.enums.TypeEnum;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MovieConverter {

    ModelMapper modelMapper = null;
    public MovieConverter() {
        this.modelMapper = new ModelMapper();
    }

    public MovieDTO convertToDTO(Optional<MovieEntity> entity){
        MovieDTO mo = modelMapper.map(entity.get(),MovieDTO.class);
        mo.setCategoryValue(CategoryEnum.valueOf(mo.getCategory()).getCategoryValue());
        mo.setTypeValue(TypeEnum.valueOf(mo.getType()).getTypeValue());
        return mo;
    }
    public MovieDTO convertToDTOno(MovieEntity entity){
        MovieDTO mo = modelMapper.map(entity,MovieDTO.class);
        return mo;
    }

    public MovieEntity convertToEntity(MovieDTO dto){
        MovieEntity me = modelMapper.map(dto,MovieEntity.class);
        return me;
    }
}

