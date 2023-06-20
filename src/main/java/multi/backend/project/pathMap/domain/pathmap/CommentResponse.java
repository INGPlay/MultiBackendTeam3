package multi.backend.project.pathMap.domain.pathmap;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private String username;
    private String content;
    private Date createDate;
    private Date updateDate;
    private Long commentGroup;
    private Long commentDepth;
}
