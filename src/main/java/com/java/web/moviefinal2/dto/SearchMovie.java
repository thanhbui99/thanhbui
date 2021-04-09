package com.java.web.moviefinal2.dto;

import lombok.Data;

@Data
public class SearchMovie {
    private String search;
    private Integer id;
    private String name;
    private String type;
    private String category;
    private Integer hot;
    private Integer status;
    private Integer episode;
    private String country;
    private Integer fromYear;
    private Integer toYear;
}
