package com.plan.tour.mapper;

import com.plan.tour.pathMap.domain.area.InsertAreaLargeDto;
import com.plan.tour.pathMap.domain.area.InsertAreaSmallDto;
import com.plan.tour.pathMap.mapper.AreaMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AreaMapperTest {

    @Autowired private AreaMapper areaMapper;

    @Test
    void insertAreaLarge() {
        areaMapper.insertAreaLarge(new InsertAreaLargeDto("1", "서울"));
    }

    @Test
    void insertAreaSmall() {
        areaMapper.insertAreaLarge(new InsertAreaLargeDto("1", "서울"));
        areaMapper.insertAreaSmall(new InsertAreaSmallDto("1", "1", "서울시"));
    }
}