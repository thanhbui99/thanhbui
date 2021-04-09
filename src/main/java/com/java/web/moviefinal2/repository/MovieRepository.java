package com.java.web.moviefinal2.repository;


import com.java.web.moviefinal2.entity.MovieEntity;
import com.java.web.moviefinal2.repository.custom.IMovieRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long>, IMovieRepositoryCustom, PagingAndSortingRepository<MovieEntity, Long> {

    @Query("select m FROM MovieEntity m WHERE m.type='LE'")
    Page<MovieEntity> findByType(Pageable pageable);


    @Query(value="select * from movie m where m.category= ?1",
            countQuery = "select count(id) from movie m where m.category= ?1",
            nativeQuery = true)
    Page<MovieEntity> findByCategory(String category,Pageable pageable);


    @Query(value="select * from movie m where m.type='LE' and m.year= ?1",
            countQuery = "select count(id) from movie m where m.type='LE' and m.year= ?1",
            nativeQuery = true)
    Page<MovieEntity> findByTypeAndYear(String year,Pageable pageable);

    @Query(value="select * from movie m where m.type='BO' and m.country like ?1",
            countQuery = "select count(id) from movie m where m.type='BO' and m.country like ?1",
            nativeQuery = true)
    Page<MovieEntity> findByTypeAndCountry(String country,Pageable pageable);

    @Query(value="select * from movie m where and m.country like ?1",
            countQuery = "select count(id) from movie m where and m.country like ?1",
            nativeQuery = true)
    Page<MovieEntity> findByCountry(String country,Pageable pageable);

    @Query("select m FROM MovieEntity m WHERE m.type like 'BO'")
    Page<MovieEntity> findByTypeSeriesMovie(Pageable pageable);

    Page<MovieEntity> findAllByCountry(Pageable pageable,String country);
    List<MovieEntity> findAllByCode(String code);

}
