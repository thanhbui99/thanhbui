package com.java.web.moviefinal2.repository.custom;

import com.java.web.moviefinal2.entity.CommentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ICommentRepositoryCustom {
    List<CommentEntity> getAllByMovieId(Long movieId);
}
