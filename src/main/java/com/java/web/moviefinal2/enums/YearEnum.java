package com.java.web.moviefinal2.enums;

public enum  YearEnum {
    N_2021("Phim lẻ 2021","2021"),
    N_2020("Phim lẻ 2020","2020"),
    N_2019("Phim lẻ 2019","2019"),
    N_2018("Phim lẻ 2018","2018"),
    N_2017("Phim lẻ 2017","2017"),
    N_2016("Phim lẻ 2016","2016"),
    N_2015("Phim lẻ 2015","2015"),
    N("Phim lẻ trước 2015","");

    private String yearValue;
    private String yearNumberValue;

    YearEnum(String yearValue, String yearNumberValue) {
        this.yearValue = yearValue;
        this.yearNumberValue = yearNumberValue;
    }

    public String getYearValue() {
        return yearValue;
    }

    public String getYearNumberValue() {
        return yearNumberValue;
    }
}
