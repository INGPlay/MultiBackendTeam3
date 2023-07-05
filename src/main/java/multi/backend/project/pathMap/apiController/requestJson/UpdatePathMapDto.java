package multi.backend.project.pathMap.apiController.requestJson;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdatePathMapDto {
    private String title;
    private String markers;
    private Long pathId;
}
