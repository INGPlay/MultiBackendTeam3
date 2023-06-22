package multi.backend.project.pathMap.domain.pathmap.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MarkInfoResponse {
    private String title;

    private String addr1;
    private String addr2;

    private Long contentId;
    private String contentTypeId;
    private String contentType;

    private String firstImageURI;

    // 썸네일
    private String firstImageURI2;

    private double posX;
    private double posY;

    private String tel;

    private Long placeOrder;
}
