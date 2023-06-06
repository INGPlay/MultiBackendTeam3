package multi.backend.project.domain.area;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class InsertAreaSmallDto {
    private Long largeCode;
    private Long smallCode;
    private String smallName;
}
