package multi.backend.project.pathMap.domain.pathmap.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PathThreadPageDto {
    private int page;
    private int size;
    private String orderBy;
    private String searchWord;
}
