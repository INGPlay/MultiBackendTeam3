package multi.backend.project.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoPageDto {
    private int page;
    private int size;
    private String orderBy;
    private String searchWord;
    private String searchOption;

}
