package com.java.web.moviefinal2.service;

import com.java.web.moviefinal2.dto.CommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICommentService {
    void saveComment(String content,long movieId,String username);
    List<CommentDTO> getAllCommentByMovieId(Long id);
}
