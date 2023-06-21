package multi.backend.project.pathMap.domain.favorite;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavoriteDto {
    private String username;
    private Long pathId;
}
