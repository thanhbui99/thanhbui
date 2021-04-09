package com.java.web.moviefinal2.service.impl;

import com.java.web.moviefinal2.converter.CommentConverter;
import com.java.web.moviefinal2.dto.CommentDTO;
import com.java.web.moviefinal2.entity.AppUser;
import com.java.web.moviefinal2.entity.CommentEntity;
import com.java.web.moviefinal2.entity.MovieEntity;
import com.java.web.moviefinal2.repository.CommentRepository;
import com.java.web.moviefinal2.repository.MovieRepository;
import com.java.web.moviefinal2.repository.UserRepository;
import com.java.web.moviefinal2.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CommentConverter commentConverter;


    @Override
    @Transactional
    public void saveComment(String content, long movieId, String username) {

        Optional<MovieEntity> byId = movieRepository.findById(movieId);
        AppUser byUserName = userRepository.findByUserName(username);

        CommentEntity ce = new CommentEntity();
        Date date = new Date();
        ce.setPostingTime(getString(date));
        ce.setMovie(byId.get());
        ce.setContent(content);
        ce.setAppUser(byUserName);

        commentRepository.save(ce);

    }

    @Override
    public List<CommentDTO> getAllCommentByMovieId(Long id) {
        try {
            List<CommentDTO> result = new ArrayList<>();
            List<CommentEntity> entities = commentRepository.getAllByMovieId(id);
            for (CommentEntity entity :
                    entities) {
                CommentDTO commentDTO = commentConverter.convertToDTO(entity);
                result.add(commentDTO);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public static String getString(Date d) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
    }
}
