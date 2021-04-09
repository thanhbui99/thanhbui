package com.java.web.moviefinal2.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class MovieDTO extends AbstractDTO<MovieDTO>{

    private Long id;

    private String name;

    private String code;

    private String category;

    private String categoryValue;

    private String content;

    private String author;

    private String showDate;

    private Integer year;

    private String time;

    private String language;

    private String image;

    private String resolution;

    private String country;

    private String description;

    private String type;

    private String typeValue;

    private boolean status;

    private String typeImage;

    private String typeMovie;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date showDates;

    private Integer episode ;

    private String completionStatus;

    private String linkMovie ;

    private boolean hot;

}
