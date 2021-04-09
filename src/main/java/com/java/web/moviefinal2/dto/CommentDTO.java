package com.java.web.moviefinal2.dto;

import com.java.web.moviefinal2.entity.AppUser;
import com.java.web.moviefinal2.entity.MovieEntity;
import lombok.Data;

@Data
public class CommentDTO {

    private Long userId;
    private Long movieId;
    private String content;
    private MovieEntity movieEntity;
    private AppUser appUser;
    private String postingTime;
}
