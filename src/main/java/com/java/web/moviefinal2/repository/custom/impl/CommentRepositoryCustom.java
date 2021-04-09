package com.java.web.moviefinal2.repository.custom.impl;

import com.java.web.moviefinal2.entity.CommentEntity;
import com.java.web.moviefinal2.repository.custom.ICommentRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentRepositoryCustom implements ICommentRepositoryCustom{

    @Autowired
    private EntityManager entityManager;


    public List<CommentEntity> getAllByMovieId(Long movieId) {
        try {
            String sql = "SELECT * FROM COMMENT WHERE movieId =? ;";
            Query query = entityManager.createNativeQuery(sql,CommentEntity.class);
            query.setParameter(1,movieId);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
