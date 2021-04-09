package com.java.web.moviefinal2.repository;

import com.java.web.moviefinal2.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity,Long>{

    List<ImageEntity> findAllByKeyImage(String key);
    ImageEntity findByKeyImage(String key);
}
