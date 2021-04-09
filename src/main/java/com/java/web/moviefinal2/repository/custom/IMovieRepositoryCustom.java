package com.java.web.moviefinal2.repository.custom;

import com.java.web.moviefinal2.dto.SearchMovie;
import com.java.web.moviefinal2.entity.AppUser;
import com.java.web.moviefinal2.entity.MovieEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IMovieRepositoryCustom {
    List<MovieEntity> findALlSearch(SearchMovie searchMovie);
    List<MovieEntity> findALlSearchForViewer(SearchMovie searchMovie);
    List<MovieEntity> findAllByCodeAndEpisode(String code,String episode);
    List<MovieEntity> findFIlmLe8();
    List<MovieEntity> findFIlmLe4();
    List<MovieEntity> getMyMovie(Long userId);
    void deleteMovieInMyMovie(Long movieId);
    List<MovieEntity> findAllSeries();
    List<MovieEntity> findAllCartoon();
}
