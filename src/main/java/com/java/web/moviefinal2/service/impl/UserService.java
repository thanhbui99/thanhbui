package com.java.web.moviefinal2.service.impl;

import com.java.web.moviefinal2.converter.UserConverter;
import com.java.web.moviefinal2.dto.UserRegister;
import com.java.web.moviefinal2.entity.*;
import com.java.web.moviefinal2.repository.*;
import com.java.web.moviefinal2.repository.custom.IAppUserRepositoryCustom;
import com.java.web.moviefinal2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private IAppUserRepositoryCustom iAppUserRepositoryCustom;

    @Override
    @Transactional
    public void save(UserRegister userRegister) {
        try {
            userRegister.setEnabled(true);
            userRegister.setImages("39");
            AppUser appUser = userConverter.convertToEntity(userRegister);
            userRepository.save(appUser);
            List<AppUser> users = new ArrayList<>();
            AppUser user = userRepository.findByEmail(appUser.getEmail());
            ImageEntity imageEntity1 = imageRepository.findByKeyImage("viewers.jpg");
            user.setImage(imageEntity1);
            users.add(user);
            imageEntity1.setUser(users);
            imageService.save(imageEntity1);
            long userId = userRepository.findByEmail(appUser.getEmail()).getUserId();
            UserRole ur = new UserRole(appUser,new AppRole((long) 2,"ROLE_USER"));
            roleUserRepository.save(ur);
        }catch (Exception e){

        }
    }

    @Override
    @Transactional
    public void saveStaff(UserRegister userRegister) {
        try {
            userRegister.setEnabled(true);
            userRegister.setImages("39");
            AppUser appUser = userConverter.convertToEntity(userRegister);
            userRepository.save(appUser);
            List<AppUser> users = new ArrayList<>();
            AppUser user = userRepository.findByEmail(appUser.getEmail());
            ImageEntity imageEntity1 = imageRepository.findByKeyImage("viewers.jpg");
            user.setImage(imageEntity1);
            users.add(user);
            imageEntity1.setUser(users);
            imageService.save(imageEntity1);
            long userId = userRepository.findByEmail(appUser.getEmail()).getUserId();
            UserRole ur = new UserRole(appUser,new AppRole((long) 3,"ROLE_STAFF"));
            roleUserRepository.save(ur);
        }catch (Exception e){

        }
    }

    @Override
    @Transactional
    public void deleteStaff(Long id) {
        Optional<AppUser> staff = userRepository.findById(id);
        staff.get().setEnabled(false);
        userRepository.save(staff.get());
    }

    @Override
    public boolean getByUserName(String username) {
        if(userRepository.findByUserName(username)!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean getByEmail(String email) {
        if (userRepository.findByEmail(email)!=null){
            return true;
        }
        return false;
    }

    @Override
    public UserRegister getOneById(Long id) {
        return userConverter.convertToDTOno(userRepository.findById(id).get());
    }

    @Override
    public List<UserRegister> getUser(String username) {
        List<UserRegister> list = new ArrayList<>();
        AppUser user = userRepository.findByUserName(username);
        UserRegister userRegister = userConverter.convertToDTOno(user);
        list.add(userRegister);
        return list;
    }

    @Override
    public List<UserRegister> getAllStaff() {
        List<UserRegister> userRegisters = new ArrayList<>();
        List<AppUser> staff = iAppUserRepositoryCustom.getStaff();
        for (AppUser appUser : staff){
            UserRegister userRegister = userConverter.convertToDTOno(appUser);
            userRegisters.add(userRegister);
        }
        return userRegisters;
    }

}
