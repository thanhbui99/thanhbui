package com.java.web.moviefinal2.enums;

public enum CountryEnum {
    JP("Nhật Bản"),
    VN("Việt Nam"),
    CN("Trung Quốc"),
    FR("Pháp"),
    CA("Canada"),
    HK("Hồng Kong"),
    TW("Đài Loan"),
    US("Mỹ"),
    IN("Ấn độ"),
    AU("Úc"),
    KR("Hàn Quốc"),
    TH("Thái Lan"),
    UK("Anh");
    private String CountryValue;

    CountryEnum(String countryValue) {
        CountryValue = countryValue;
    }

    public String getCountryValue() {
        return CountryValue;
    }
}
