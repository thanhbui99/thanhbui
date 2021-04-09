package com.java.web.moviefinal2.enums;

public enum TypeEnum {
    LE("Phim lẻ"),
    BO("Phim bộ"),
    THUYET_MINH_LE("Phim thuyết minh lẻ"),
    THUYET_MINH_BO("Phim thuyết bộ");

    private final String typeValue;

    TypeEnum(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getTypeValue() {
        return typeValue;
    }
}
