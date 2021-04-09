package com.java.web.moviefinal2.enums;

public enum CategoryEnum {
    PHIM_HANH_DONG("Phim hành động", "  PHIM-HANH-DONG"),
    PHIM_HAI_HUOC("Phim hài hước", "PHIM-HAI-HUOC"),
    PHIM_VIEN_TUONG("Phim viễn tưởng", "PHIM-VIEN-TUONG"),
    PHIM_CO_TRANG("Phim cổ trang", "PHIM-CO-TRANG"),
    PHIM_CHINH_KICH_DRAMA("Phim chính kịch-Drama", "PHIM-CHINH-KICH-DRAMA"),
    PHIM_VO_THUAT("Phim võ thuật", "PHIM-VO-THUAT"),
    PHIM_THAN_THOAI("phim thần thoại", "PHIM-THAN-THOAI"),
    PHIM_CHIEN_TRANH("Phim chiến tranh", "PHIM-CHIEN-TRANH"),
    PHIM_GIA_DINH("Phim gia đình", "PHIM-GIA-DINH"),
    PHIM_KINH_DI("Phim kinh dị", "PHIM-KINH-DI"),
    PHIM_TAM_LY("Phim tâm lý", "PHIM-TAM-LY"),
    PHIM_HINH_SU("Phim hình sự", "PHIM-HINH-SU"),
    PHIM_TAI_LIEU("Phim tài liệu", "PHIM-TAI-LIEU"),
    PHIM_HOI_HOP_GAY_CAN("Phim hồi hộp-Gây cấn", "PHIM-HOI-HOP-GAY-CAN"),
    PHIM_HOAT_HINH("Phim hoạt hình", "PHIM-HOAT-HINH"),
    PHIM_THE_THAO_AM_NHAC("phim thể thao-Âm nhạc", "PHIM-THE-THAO-AM-NHAC"),
    PHIM_PHIEU_LUU("Phim phiêu lưu", "PHIM-PHIEU-LUU"),
    PHIM_BI_AN_SIEU_NHAN("Phim bí ẩn-Siêu nhân", "PHIM-BI-AN-SIEU-NHAN"),
    PHIM_TINH_CAM_LANG_MAN("Phim tình cảm-Lãng mạn", "PHIM-TINH-CAM-LANG-MAN");

    private final String categoryValue;
    private final String categoryAddress;

    CategoryEnum(String categoryValue, String categoryAddress) {
        this.categoryValue = categoryValue;
        this.categoryAddress = categoryAddress;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public String getCategoryAddress() {
        return categoryAddress;
    }
}
