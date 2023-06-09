package multi.backend.project.pathMap.domain.tour;

import lombok.Getter;

/**
 * 관광타입 : contentTypeId
 * - 관광지 : 12
 * - 문화시설 : 14
 * - 행사/공연/축제 : 15
 * - 여행코스 : 25
 * - 레포츠 : 28
 * - 숙박 : 32
 * - 쇼핑 : 38
 * - 음식점 : 39
 */
@Getter
public enum ContentType {
    TOUR_SPOT("관광지", "12"),
    CURTURE_SITE("문화시설", "12"),
    FESTIVAL("행사/공연/축제", "15"),
    TOUR_COURSE("여행코스", "25"),
    LEPORTS("레포츠", "28"),
    ACCOMODATION("숙박", "32"),
    SHOPPING("쇼핑", "38"),
    RESTAURANT("식당", "39");

    private String name;
    private String code;

    ContentType(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
