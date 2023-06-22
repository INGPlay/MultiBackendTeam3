package multi.backend.project.pathMap.domain.pathmap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertPathCommentDto {
    private Long pathId;
    private String username;
    private String commentContent;
}
