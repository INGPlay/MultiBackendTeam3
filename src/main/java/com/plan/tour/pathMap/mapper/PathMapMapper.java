package com.plan.tour.pathMap.mapper;

import com.plan.tour.pathMap.domain.pathmap.*;
import com.plan.tour.pathMap.domain.pathmap.paging.PathThreadPageDto;
import com.plan.tour.pathMap.domain.pathmap.response.CommentResponse;
import com.plan.tour.pathMap.domain.pathmap.response.MarkInfoResponse;
import com.plan.tour.pathMap.domain.pathmap.response.PathInfoResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface PathMapMapper {

    @Insert("INSERT INTO \"PATH\" p (path_id, user_id, path_title, create_date, update_date, path_views, path_recommends, path_starting_area, path_destination_area)\n" +
            "VALUES ( \n" +
            "${pathId}, \n" +
            "(SELECT m.user_id FROM MEMBERUSER m WHERE m.user_name = #{username}), \n" +
            "#{title}, \n" +
            "SYSDATE, \n" +
            "SYSDATE, \n" +
            "0, \n" +
            "0, \n" +
            "#{startingArea}, \n" +
            "#{destinationArea} \n" +
            ")")
    void insertPathMap(@Param("pathId") Long pathId, @Param("username") String username, @Param("title") String title,
                       @Param("startingArea") String startingArea, @Param("destinationArea") String destinationArea);

    @Update("UPDATE PATH \n" +
            "SET\n" +
            "PATH_TITLE = #{title}, \n" +
            "UPDATE_DATE = SYSDATE, \n" +
            "PATH_Starting_area = #{startingArea}, \n" +
            "PATH_Destination_area = #{destinationArea} \n" +
            "where PATH_ID = ${pathId}")
    void updatePathMap(@Param("pathId") Long pathId, @Param("title") String title,
                       @Param("startingArea") String startingArea, @Param("destinationArea") String destinationArea);

    @Update("UPDATE PATH \n" +
            "SET \n" +
            "PATH_VIEWS = (PATH_VIEWS + 1)\n" +
            "where PATH_ID = ${pathId}")
    void updatePathMapViews(Long pathId);

    @Delete("Delete from PATH \n" +
            "where path_id = ${pathId}")
    void deletePathMap(Long pathId);

    @Delete("delete from mark\n" +
            "where path_id = ${pathId}")
    void deleteMarksInPath(Long pathId);

    @Select("SELECT PATH_SEQUENCE.NEXTVAL FROM DUAL")
    Long getPathmapNextval();

    @Select("SELECT NVL(MAX(mark_id), 0) from Mark")
    Long getMarkCount();

    @Select("SELECT p.PATH_ID, m.USER_NAME, p.CREATE_DATE, p.UPDATE_DATE, p.PATH_TITLE, p.PATH_VIEWS, p.PATH_RECOMMENDS, p.PATH_STARTING_AREA, p.PATH_DESTINATION_AREA \n" +
            "FROM PATH p JOIN MEMBERUSER m\n" +
            "ON p.USER_ID = m.USER_ID\n" +
            "Where p.PATH_ID = ${pathId}")
    PathInfoResponse selectPathInfo(Long pathId);

    @Select("select m.user_name, p.content, p.create_date, p.update_date, p.comment_id, p.comment_group, p.comment_depth \n" +
            "FROM PATH_COMMENT p JOIN MEMBERUSER m \n" +
            "ON p.user_id = m.user_id \n" +
            "where p.PATH_ID = ${pathId}")
    List<CommentResponse> selectPathComment(Long pathId);

    @Select("select m.user_name, p.content, p.create_date, p.update_date, p.comment_id, p.comment_group, p.comment_depth \n" +
            "FROM PATH_COMMENT p JOIN MEMBERUSER m \n" +
            "ON p.user_id = m.user_id \n" +
            "where p.comment_id = ${commentId}")
    CommentResponse selectComment(Long commentId);

    @Insert("insert into path_comment (comment_id, path_id, create_date, update_date, content, comment_group, comment_depth, user_id) \n" +
            "values ( \n" +
            "Path_Comment_Sequence.nextval,\n" +
            "${pathId}, \n" +
            "sysdate, \n" +
            "sysdate, \n" +
            "#{commentContent}, \n" +
            "0, \n" +
            "0, \n" +
            "(select user_id from MemberUser u where u.user_name = #{username}) \n" +
            ")")
    void insertPathComment(InsertPathCommentDto insertPathCommentDto);

    @Delete("delete from path_comment \n" +
            "where comment_id = ${commentId}")
    void deletePathComment(Long commentId);

    // XML 파일
    void insertMarksBatch(List<Map<String, Object>> markInfoRequests);

    List<PathInfoResponse> selectPathInfoList(PathThreadPageDto pathThreadPageDto);

    List<MarkInfoResponse> selectMarkInfoByPathId(Long pathId);
}
