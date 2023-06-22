package multi.backend.project.pathMap.domain.area;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TourInfoKeywordRequest {
    private String keyword;
    private String areaLargeCode;
    private String areaSmallCode;
    private Integer pageSize;
    private Integer pageNo;
    private String contentTypeCode;
}
