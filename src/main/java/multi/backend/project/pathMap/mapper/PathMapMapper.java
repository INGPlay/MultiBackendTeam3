package multi.backend.project.pathMap.mapper;

import multi.backend.project.pathMap.domain.pathmap.MarkInfoResponse;
import multi.backend.project.pathMap.domain.pathmap.PathInfoResponse;
import org.apache.ibatis.annotations.*;

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

    @Update("UPDATE PATH \n" +
            "SET\n" +
            "PATH_TITLE = #{title},\n" +
            "UPDATE_DATE = SYSDATE\n" +
            "where PATH_ID = ${pathId}")
    void updatePathMap(Long pathId, String title);

    @Delete("Delete from PATH \n" +
            "where path_id = ${pathId}")
    void deletePathMap(Long pathId);

    @Delete("delete from mark\n" +
            "where path_id = ${pathId}")
    void deleteMarksInPath(Long pathId);

    @Select("SELECT PATH_SEQUENCE.NEXTVAL FROM DUAL")
    Long getPathmapNextval();

    @Select("SELECT COUNT(mark_id) from Mark")
    Long getMarkCount();

    @Select("SELECT p.PATH_ID, m.USER_NAME, p.CREATE_DATE, p.UPDATE_DATE, p.PATH_TITLE, p.PATH_VIEWS, p.PATH_RECOMMENDS\n" +
            "FROM PATH p JOIN MEMBERUSER m\n" +
            "ON p.USER_ID = m.USER_ID\n" +
            "Where p.PATH_ID = ${pathId}")
    PathInfoResponse selectPathInfo(Long pathId);

    // XML 파일
    void insertMarksBatch(List<Map<String, Object>> markInfoRequests);

    List<PathInfoResponse> selectPathInfoList();

    List<MarkInfoResponse> selectMarkInfoByPathId(Long pathId);
}
