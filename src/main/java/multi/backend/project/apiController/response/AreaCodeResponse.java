package multi.backend.project.apiController.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AreaCodeResponse {
    private Long code;
    private String name;
}
