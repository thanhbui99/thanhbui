package com.java.web.moviefinal2.service;

import com.java.web.moviefinal2.dto.UserRegister;

import java.util.List;
import java.util.Optional;


public interface IUserService {
    void save(UserRegister userRegister);
    List<UserRegister> getUser(String username);
    List<UserRegister> getAllStaff();
    void saveStaff(UserRegister userRegister);
    void deleteStaff(Long id);
    boolean getByUserName(String username);
    boolean getByEmail(String email);
    UserRegister getOneById(Long id);
}
