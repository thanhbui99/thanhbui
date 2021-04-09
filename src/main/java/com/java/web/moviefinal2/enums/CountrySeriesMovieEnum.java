package com.java.web.moviefinal2.enums;

public enum CountrySeriesMovieEnum {
    CN("Trung Quốc"),
    HK("Hồng Kong"),
    TW("Đài Loan"),
    US("Mỹ"),
    KR("Hàn Quốc"),
    TH("Thái Lan");

    private String CountrySeriesMovieEnumValue;

    CountrySeriesMovieEnum(String countrySeriesMovieEnumValue) {
        CountrySeriesMovieEnumValue = countrySeriesMovieEnumValue;
    }

    public String getCountrySeriesMovieEnumValue() {
        return CountrySeriesMovieEnumValue;
    }
}
