package com.java.web.moviefinal2.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserRegister {

    private Long userId;

    @NotNull(message = "Enter your User Name")
    private String userName;

    private String encrytedPassword;

    @NotNull(message = "Enter your password")
    @Length(min = 6,message = "Password must  be at least 6 characters")
    private String password;

    private boolean enabled;

    @NotNull(message = "Enter your email")
    @Email(message = "Enter your valid email address")
    private String email;

    private String images;

    private String image;
}
