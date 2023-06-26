package multi.backend.project.pathMap.domain.pathmap.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PathThreadPageDto {
    private int page;
    private int size;
    private String orderBy;
    private String searchWord;
    private String searchOption;
    private Boolean isFavorite;
    private String username;
}
