package com.java.web.moviefinal2.repository;

import com.java.web.moviefinal2.entity.CommentEntity;
import com.java.web.moviefinal2.repository.custom.ICommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity,Long>, ICommentRepositoryCustom {
}
