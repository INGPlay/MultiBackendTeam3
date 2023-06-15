package multi.backend.project.pathMap.domain.wheather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WheatherResponse {
    private String forecastDateTime;

    private String dayMinTemparature;
    private String dayMaxTemparature;

    private String hourTemparature;
    private String rainMilli;
    private String skyForm;

    private String rainForm;
    private String rainProbability;
    private String rainAmount;

    private String snowAmount;
}
