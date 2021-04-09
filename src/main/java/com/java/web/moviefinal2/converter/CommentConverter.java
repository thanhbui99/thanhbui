package com.java.web.moviefinal2.converter;

import com.java.web.moviefinal2.dto.CommentDTO;
import com.java.web.moviefinal2.entity.CommentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {
    ModelMapper modelMapper = null;
    public CommentConverter() {
        this.modelMapper = new ModelMapper();
    }

    public CommentDTO convertToDTO(CommentEntity entity){
        CommentDTO map = modelMapper.map(entity, CommentDTO.class);
        return map;
    }
}
