package multi.backend.project.pathMap.mapper;

import multi.backend.project.pathMap.domain.area.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AreaMapper {

    @Select("select l.large_code, l.large_name, s.small_code, s.small_name from AREA_LARGE l \n" +
            "Join AREA_SMALL s \n" +
            "on l.large_id = s.large_id_fk \n" +
            "where l.large_code = #{areaCode} \n" +
            "and s.small_code = #{sigunguCode}")
    AreaLargeSmallDto selectAreaByCodes(@Param("areaCode") String areaCode, @Param("sigunguCode") String sigunguCode);

    @Select("SELECT large_code, large_name FROM AREA_LARGE")
    List<AreaLargeDto> selectAreaLarge();

    @Select("SELECT small_code, small_name FROM AREA_small s WHERE LARGE_ID_FK = (SELECT al.large_id FROM AREA_LARGE al WHERE large_code = #{largeCode})")
    List<AreaSmallDto> selectAreaSmall(String largeCode);

    @Insert("Insert into area_large (large_id, large_code, large_name)" +
            " values(large_id_seq.nextval, #{largeCode}, #{largeName})")
    void insertAreaLarge(InsertAreaLargeDto insertAreaLargeDto);

    @Insert("Insert into area_small (small_id, small_code, small_name, large_id_fk)" +
            " values(" +
            "small_id_seq.nextval," +
            " #{smallCode}," +
            " #{smallName}," +
            " (select large_id from area_large al where al.large_code = ${largeCode})" +
            ")")
    void insertAreaSmall(InsertAreaSmallDto insertAreaSmallDto);
}
