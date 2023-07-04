package multi.backend.project.review.VO;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("PlaceVO")
public class PlaceVO {
    private int contentId;
    private String contentName;


    public PlaceVO(int contentId, String contentName) {
        this.contentId = contentId;
        this.contentName = contentName;
    }

    public PlaceVO() {
        this.contentId=-1;
        this.contentName="";
    }
}
