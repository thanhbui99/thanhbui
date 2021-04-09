package com.java.web.moviefinal2.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movie")
@Data
public class MovieEntity extends BaseEntity{
    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "code",nullable = false)
    private String code;

    @Column(name = "category")
    private String category;

    @Column(name = "content",length = 2048)
    private String content;

    @Column(name = "author")
    private String author;

    @Column(name = "showdate")
    private String showDate;

    @Column(name = "showdates")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date showDates;

    @Column(name = "year")
    private Integer year;

    @Column(name = "time",length = 24)
    private String time;

    @Column(name = "language")
    private String language;

    @Column(name = "image",length = 1024)
    private String image;

    @Column(name = "resolution")
    private String resolution;

    @Column(name = "country")
    private String country;

    @Column(name = "description",length = 1024)
    private String description;

    @Column(name = "type",nullable = false)
    private String type;

    @Column(name = "status",nullable = false)
    private boolean status;

    @Column(name = "typeimage")
    private String typeImage;

    @Column(name = "episode ")
    private Integer episode ;

    @Column(name = "completionstatus ")
    private String completionStatus ;

    @Column(name = "linkmovie ",length = 1024)
    private String linkMovie ;

    @Column(name = "hot")
    private boolean hot;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "my_movie", joinColumns = @JoinColumn(name = "movie_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "viewer_id", nullable = false))
    private List<AppUser> viewers = new ArrayList<>();

    @OneToMany(mappedBy = "appUser",cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments = new ArrayList<>();

}
