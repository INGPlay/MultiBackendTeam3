package multi.backend.project.pathMap.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PathMapMapper {

    @Insert("INSERT INTO \"PATH\" p (path_id, user_id, path_title, create_date, update_date, path_views, path_recommends)\n" +
            "VALUES (\n" +
            "${pathId}, \n" +
            "(SELECT m.user_id FROM MEMBERUSER m WHERE m.user_name = #{username}),\n" +
            "#{title},\n" +
            "SYSDATE,\n" +
            "SYSDATE,\n" +
            "0,\n" +
            "0\n" +
            ")")
    void insertPathMap(Long pathId, String username, String title);

    @Select("SELECT MARK_SEQUENCE.NEXTVAL FROM DUAL")
    Long getPathmapNextval();

    // XML 파일
    void insertMarksBatch(List<Map<String, Object>> markInfoRequests);
}
