package multi.backend.project.pathMap.domain.pathmap;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class PathInfoResponse {
    private Long pathId;
    private String username;
    private Date createDate;
    private Date updateDate;
    private String pathTitle;
    private Long pathViews;
    private Long pathRecommends;
}
