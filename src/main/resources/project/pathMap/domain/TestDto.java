package multi.backend.project.pathMap.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@AllArgsConstructor
@Getter
public class TestDto {

    private Integer testId;
    private String testName;
    private Date testDate;

}
