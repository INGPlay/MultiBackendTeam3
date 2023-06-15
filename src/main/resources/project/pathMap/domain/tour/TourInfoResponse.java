package multi.backend.project.pathMap.domain.tour;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourInfoResponse {
    private String title;

    private String addr1;
    private String addr2;

    private Long contentId;
    private ContentType contentType;

    // 선택한 지점에서의 거리
    private Double dist;

    private String firstImageURI;

    // 썸네일
    private String firstImageURI2;

    private double posX;
    private double posY;

    private String tel;
}
