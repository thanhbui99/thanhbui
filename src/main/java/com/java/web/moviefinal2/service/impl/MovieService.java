package com.java.web.moviefinal2.service.impl;

import com.java.web.moviefinal2.converter.MovieConverter;
import com.java.web.moviefinal2.dto.MovieDTO;
import com.java.web.moviefinal2.dto.SearchMovie;
import com.java.web.moviefinal2.entity.AppUser;
import com.java.web.moviefinal2.entity.CommentEntity;
import com.java.web.moviefinal2.entity.MovieEntity;
import com.java.web.moviefinal2.repository.CommentRepository;
import com.java.web.moviefinal2.repository.MovieRepository;
import com.java.web.moviefinal2.repository.UserRepository;
import com.java.web.moviefinal2.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService implements IMovieService {


    private MovieConverter movieConverter = null;

    public MovieService() {
        this.movieConverter = new MovieConverter();
    }

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Long count() {
        return movieRepository.count();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<MovieDTO> findAll() {
        List<MovieDTO> results = new ArrayList<>();
        List<MovieEntity> entities = movieRepository.findAll();
        for (MovieEntity entity :
                entities) {
            MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
            results.add(mo);
        }
        return results;
    }

    @Override
    public MovieDTO findById(Long id) {
        try {
            Optional<MovieEntity> entity = movieRepository.findById(id);
            MovieDTO dto = movieConverter.convertToDTO(entity);
            return dto;
        } catch (Exception e) {
            return new MovieDTO();
        }
    }

    //LUU PHIM
    @Override
    @Transactional
    public void save(MovieDTO movieDTO) {
        MovieEntity me = movieConverter.convertToEntity(movieDTO);
        movieRepository.save(me);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        List<CommentEntity> getAllCommentByMovieId = commentRepository.getAllByMovieId(id);
        for (CommentEntity commentEntity:
                getAllCommentByMovieId) {
            commentRepository.deleteById(commentEntity.getId());
        }
        MovieEntity movie = movieRepository.findById(id).get();
        movie.setComments(null);
        movieRepository.deleteById(id);
    }

    @Override
    public List<MovieEntity> getAllMovie(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<MovieEntity> pagedResult = movieRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieEntity> getMovieOddFilm(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<MovieEntity> pagedResult = movieRepository.findByType(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieEntity> getMovieSeriesMovie(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<MovieEntity> pagedResult = movieRepository.findByTypeSeriesMovie(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieEntity> getMovieCountry(String country,Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<MovieEntity> pagedResult = movieRepository.findAllByCountry(paging,country);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieEntity> getMovieByCategory(String category, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<MovieEntity> pagedResult = movieRepository.findByCategory(category,paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieEntity> getByTypeAndYear(String year, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<MovieEntity> pagedResult = movieRepository.findByTypeAndYear(year,paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieEntity> getByTypeAndCountrySeriesFilm(String country, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<MovieEntity> pagedResult = movieRepository.findByTypeAndCountry(country,paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieEntity> getMovieByCountry(String country, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<MovieEntity> pagedResult = movieRepository.findByCountry(country,paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }


    @Override
    public List<MovieDTO> searchMovie(SearchMovie searchMovie) {
        try {
            List<MovieDTO> results = new ArrayList<>();
            List<MovieEntity> entities = movieRepository.findALlSearch(searchMovie);
            for (MovieEntity entity :
                    entities) {
                MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
                results.add(mo);
            }
            return results;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieDTO> getMovieByCode(String code) {
        try {
            List<MovieDTO> results = new ArrayList<>();
            List<MovieEntity> entities = movieRepository.findAllByCode(code);
            for (MovieEntity entity :
                    entities) {
                MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
                results.add(mo);
            }
            return results;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieDTO> getMovieByCodeAndEpisode(String code, String episode) {
        try {
            List<MovieDTO> results = new ArrayList<>();
            List<MovieEntity> entities = movieRepository.findAllByCodeAndEpisode(code,episode);
            for (MovieEntity entity :
                    entities) {
                MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
                results.add(mo);
            }
            return results;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieDTO> searchMovieForViewer(SearchMovie searchMovie) {
        try {
            List<MovieDTO> results = new ArrayList<>();
            List<MovieEntity> entities = movieRepository.findALlSearchForViewer(searchMovie);
            for (MovieEntity entity :
                    entities) {
                MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
                results.add(mo);
            }
            return results;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieDTO> findFIlmLe8() {
        try {
            List<MovieDTO> results = new ArrayList<>();
            List<MovieEntity> entities = movieRepository.findFIlmLe8();
            for (MovieEntity entity :
                    entities) {
                MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
                results.add(mo);
            }
            return results;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieDTO> findFIlmLe4() {
        try {
            List<MovieDTO> results = new ArrayList<>();
            List<MovieEntity> entities = movieRepository.findFIlmLe4();
            for (MovieEntity entity :
                    entities) {
                MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
                results.add(mo);
            }
            return results;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void saveMyMovie(Long movieId, String username) {
        MovieEntity movieEntity = movieRepository.findById(movieId).get();
        AppUser byUserName = userRepository.findByUserName(username);
        List<AppUser> appUsers = new ArrayList<>();
        appUsers.add(byUserName);
        movieEntity.setViewers(appUsers);
        movieRepository.save(movieEntity);
    }



    @Override
    public List<MovieDTO> getMyMovie(String username) {
        AppUser byUserName = userRepository.findByUserName(username);
        try {
            List<MovieDTO> results = new ArrayList<>();
            List<MovieEntity> entities = movieRepository.getMyMovie(byUserName.getUserId());
            for (MovieEntity entity :
                    entities) {
                MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
                results.add(mo);
            }
            return results;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void deleteMovieInMyMovie(Long movieId) {
        movieRepository.deleteMovieInMyMovie(movieId);
    }

    @Override
    public List<MovieDTO> getAllSeries() {
        try {
            List<MovieDTO> results = new ArrayList<>();
            List<MovieEntity> entities = movieRepository.findAllSeries();
            for (MovieEntity entity :
                    entities) {
                MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
                results.add(mo);
            }
            return results;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieDTO> getAllCartoon() {
        try {
            List<MovieDTO> results = new ArrayList<>();
            List<MovieEntity> entities = movieRepository.findAllCartoon();
            for (MovieEntity entity :
                    entities) {
                MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
                results.add(mo);
            }
            return results;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}

