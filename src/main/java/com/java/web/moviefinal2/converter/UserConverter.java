package com.java.web.moviefinal2.converter;

import com.java.web.moviefinal2.dto.UserRegister;
import com.java.web.moviefinal2.entity.AppUser;
import com.java.web.moviefinal2.entity.ImageEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private ModelMapper modelMapper = null;

    public UserConverter() {
        this.modelMapper = new ModelMapper();
    }

    public UserRegister convertToDTOno(AppUser entity){
        UserRegister mo = modelMapper.map(entity,UserRegister.class);
        String url = entity.getImage().getObjectUrl();
        mo.setImages(url);

        return mo;
    }

    public AppUser convertToEntity(UserRegister dto){
        AppUser me = modelMapper.map(dto, AppUser.class);
        return me;
    }

}
