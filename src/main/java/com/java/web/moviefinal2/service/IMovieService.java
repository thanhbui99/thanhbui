package com.java.web.moviefinal2.service;


import com.java.web.moviefinal2.dto.MovieDTO;
import com.java.web.moviefinal2.dto.SearchMovie;
import com.java.web.moviefinal2.entity.AppUser;
import com.java.web.moviefinal2.entity.MovieEntity;

import java.util.List;

public interface IMovieService {
    Long count();
    List<MovieDTO> findAll();
    MovieDTO findById(Long id);
    void save(MovieDTO movieDTO);
    void deleteById(Long id);
    List<MovieEntity> getAllMovie(Integer pageNo, Integer pageSize, String sortBy);
    List<MovieEntity> getMovieOddFilm(Integer pageNo, Integer pageSize, String sortBy);
    List<MovieEntity> getMovieSeriesMovie(Integer pageNo, Integer pageSize, String sortBy);
    List<MovieEntity> getMovieCountry(String country,Integer pageNo, Integer pageSize, String sortBy);
    List<MovieEntity> getMovieByCategory(String category,Integer pageNo, Integer pageSize, String sortBy);
    List<MovieEntity> getByTypeAndYear(String year,Integer pageNo, Integer pageSize, String sortBy);
    List<MovieEntity> getByTypeAndCountrySeriesFilm(String country,Integer pageNo, Integer pageSize, String sortBy);
    List<MovieEntity> getMovieByCountry(String country,Integer pageNo, Integer pageSize, String sortBy);
    List<MovieDTO> searchMovie(SearchMovie searchMovie);
    List<MovieDTO> getMovieByCode(String code);
    List<MovieDTO> getMovieByCodeAndEpisode(String code,String episode);
    List<MovieDTO> searchMovieForViewer(SearchMovie searchMovie);
    List<MovieDTO> findFIlmLe8();
    List<MovieDTO> findFIlmLe4();
    void saveMyMovie(Long movieId,String username);
    List<MovieDTO> getMyMovie(String username);
    void deleteMovieInMyMovie(Long movieId);
    List<MovieDTO> getAllSeries();
    List<MovieDTO> getAllCartoon();
}
