package multi.backend.project.pathMap.domain.area;

import lombok.AllArgsConstructor;
import lombok.Getter;
import multi.backend.project.pathMap.domain.tour.ContentType;

@Getter
@AllArgsConstructor
public class KeywordDto {
    private String keyword;
    private String largeCode;
    private String smallCode;
}
