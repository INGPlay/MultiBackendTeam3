package multi.backend.project.pathMap.mapper;

import multi.backend.project.pathMap.domain.TestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestMapper {

    @Select("select * from testtable")
    public List<TestDto> testMapper();

    String showDate();
}
