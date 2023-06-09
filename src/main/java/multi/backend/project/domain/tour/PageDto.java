package multi.backend.project.domain.tour;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageDto {
    private int pageSize;
    private int pageNo;
}
