package multi.backend.project.pathMap.mapper;

import multi.backend.project.pathMap.domain.favorite.FavoriteDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FavoriteMapper {

    @Select("select count(*) from Favorite \n" +
            "where \n" +
            "user_id = (select user_id from memberuser where user_name = #{username}) \n" +
            "AND path_id = ${pathId}")
    int checkFavorite(FavoriteDto favoriteDto);

    @Insert("insert into favorite (favorite_id, user_id, path_id) \n" +
            "values (\n" +
            "favorite_sequence.nextval, \n" +
            "(select user_id from memberuser where user_name = #{username}), \n" +
            "${pathId} \n" +
            ")")
    void insertFavorite(FavoriteDto favoriteDto);

    @Delete("delete from favorite \n" +
            "where \n" +
            "user_id = (select user_id from memberuser where user_name = #{username}) \n" +
            "AND path_id = ${pathId}")
    void deleteFavorite(FavoriteDto favoriteDto);
}
