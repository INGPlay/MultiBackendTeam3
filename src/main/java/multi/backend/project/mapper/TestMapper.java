package multi.backend.project.mapper;

import multi.backend.project.domain.TestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestMapper {

    @Select("select * from testtable")
    public List<TestDto> testMapper();

    String showDate();
}
