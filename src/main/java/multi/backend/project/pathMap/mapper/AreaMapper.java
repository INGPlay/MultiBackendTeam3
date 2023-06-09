package multi.backend.project.pathMap.mapper;

import multi.backend.project.pathMap.domain.area.InsertAreaLargeDto;
import multi.backend.project.pathMap.domain.area.InsertAreaSmallDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AreaMapper {
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
