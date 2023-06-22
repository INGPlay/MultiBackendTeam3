package multi.backend.project.pathMap.mapper;

import multi.backend.project.pathMap.domain.area.AreaLargeDto;
import multi.backend.project.pathMap.domain.area.AreaSmallDto;
import multi.backend.project.pathMap.domain.area.InsertAreaLargeDto;
import multi.backend.project.pathMap.domain.area.InsertAreaSmallDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AreaMapper {

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
