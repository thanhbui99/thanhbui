package com.java.web.moviefinal2.repository.custom.impl;


import com.java.web.moviefinal2.dto.SearchMovie;
import com.java.web.moviefinal2.entity.AppUser;
import com.java.web.moviefinal2.entity.MovieEntity;
import com.java.web.moviefinal2.repository.custom.IMovieRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieRepositoryImpl implements IMovieRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    //thao tac du lieu bang jpa thuan
    @Override
    public List<MovieEntity> findAllByCodeAndEpisode(String code, String episode) {
        try {
            // thao tac voi db
            String sql = "select * from movie as m where m.code = ? and m.episode = ?";
            Query query = entityManager.createNativeQuery(sql, MovieEntity.class);
            query.setParameter(1, code);
            query.setParameter(2, episode);
            //-----------------------------------------------------------------------
            // lay ket qua ra
            return query.getResultList();
            // neu loi thi xuong catch
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieEntity> findALlSearch(SearchMovie searchMovie) {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM movie AS m where 1 = 1 ");
            buildSql(searchMovie, sql);

            Query query = entityManager.createNativeQuery(sql.toString(), MovieEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieEntity> findALlSearchForViewer(SearchMovie searchMovie) {
        try {
            String sql = "SELECT * FROM movie AS m where m.`name` like '%" + searchMovie.getSearch() + "%' or m.author like '%" + searchMovie.getSearch() + "%';";
            Query query = entityManager.createNativeQuery(sql, MovieEntity.class);
            System.out.println(sql);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();

        }
    }

    @Override
    public List<MovieEntity> findFIlmLe8() {
        try {
            String sql = "SELECT * FROM movie AS m where m.type='LE' and m.hot = 1 limit 12;";
            Query query = entityManager.createNativeQuery(sql, MovieEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieEntity> findFIlmLe4() {
        try {
            String sql = "SELECT * FROM movie AS m where m.type='LE'limit 4;";
            Query query = entityManager.createNativeQuery(sql, MovieEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieEntity> getMyMovie(Long userId) {
        String sql = "select * from movie m join my_movie mm on  m.id = mm.movie_id where mm.viewer_id =?;";
        Query query = entityManager.createNativeQuery(sql, MovieEntity.class);
        query.setParameter(1, userId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteMovieInMyMovie(Long movieId) {
        String sql = "delete from my_movie where movie_id = ?;";
        Query query = entityManager.createNativeQuery(sql, MovieEntity.class);
        query.setParameter(1, movieId);
        query.executeUpdate();
    }

    @Override
    public List<MovieEntity> findAllSeries() {
        try {
            String sql = "SELECT * FROM movie AS m where m.type='BO' and m.hot =1 limit 12;";
            Query query = entityManager.createNativeQuery(sql, MovieEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<MovieEntity> findAllCartoon() {
        try {
            String sql = "SELECT * FROM movie AS m where m.category = 'PHIM_HOAT_HINH' and m.hot = 1 limit 12;";
            Query query = entityManager.createNativeQuery(sql, MovieEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void buildSql(SearchMovie searchMovie, StringBuilder sql) {
        try {
            Field[] fields = SearchMovie.class.getDeclaredFields();
            for (Field field :
                    fields) {
                field.setAccessible(true);
                if (field.getType().getSimpleName().equals("String")) {
                    if (field.get(searchMovie) != null && field.get(searchMovie) != "") {
                        sql.append(" and m." + field.getName().toLowerCase() + " like '%" + field.get(searchMovie) + "%' ");
                    }
                } else if (field.getType().getSimpleName().equals("Integer")) {
                    if (!field.getName().equals("fromYear") && !field.getName().equals("toYear")) {
                        if (field.get(searchMovie) != null) {
                            System.out.println(field.getName());
                            sql.append(" and m." + field.getName().toLowerCase() + "=" + field.get(searchMovie) + " ");
                        }
                    }
                }
            }
            if (searchMovie.getFromYear() != null || searchMovie.getToYear() != null) {
                if (searchMovie.getFromYear() != null) {
                    sql.append(" and m.year >=" + searchMovie.getFromYear());
                }
                if (searchMovie.getToYear() != null) {
                    sql.append(" and m.year <=" + searchMovie.getToYear());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
